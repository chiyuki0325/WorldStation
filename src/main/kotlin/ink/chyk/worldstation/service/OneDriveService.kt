package ink.chyk.worldstation.service

import ink.chyk.worldstation.configuration.OneDriveConfig
import ink.chyk.worldstation.enum.UploadFileKind
import ink.chyk.worldstation.util.ContentTypeUtils
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.InputStream
import java.net.URI

@Service
class OneDriveService(
    private val config: OneDriveConfig,
    private val client: RestTemplate
) {
    companion object {
        private val logger = LoggerFactory.getLogger(OneDriveService::class.java)
        private val picbedLimit: Long = 30 * 1024 * 1024 // 图床上传文件大小限制为 30MB
    }

    private final val alistToken = config.alistToken ?: throw IllegalArgumentException("Alist token is not configured")

    private final val alistUrl = config.alist ?: throw IllegalArgumentException("Alist URL is not configured")

    private fun getUploadFilePath(
        uploadKind: UploadFileKind,
        fileName: String,
        principal: OAuth2User
    ): String {
        return when (uploadKind) {
            UploadFileKind.WORLDMAP -> {
                // 配置中设置的世界地图文件夹
                var path = config.worldmapPath ?: throw IllegalArgumentException("Worldmap path is not configured")

                // 根据文件名的首字母分类存储
                path += if (fileName[0].isLetter()) {
                    "/${fileName[0].uppercaseChar()}"
                } else if (fileName[0].isDigit()) {
                    "/0-9"
                } else {
                    "/Others"
                }

                // 最终路径加上文件名
                path += "/$fileName"
                path
            }

            UploadFileKind.PICBED -> {
                // 配置中设置的图床文件夹
                var path = config.picbedPath ?: throw IllegalArgumentException("Picbed path is not configured")
                // 根据上传者的 ID 分类存储
                path += "/picbed_${principal.getAttribute<String>("id")}/${fileName}"
                path
            }
        }
    }

    private fun alistHttpHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", alistToken)
        return headers
    }

    private fun makeDirectory(path: String) {
        val headers = alistHttpHeaders()
        val request = HttpEntity("""{"path": "$path"}""", headers)
        client.postForEntity(
            "$alistUrl/api/fs/mkdir",
            request,
            String::class.java
        )
    }

    private fun refreshFileSystem(path: String) {
        // 刷新 alist 文件系统中某个文件夹的缓存
        val headers = alistHttpHeaders()
        val request = HttpEntity("""{"path": "$path", "refresh": true}""", headers)
        val resp = client.postForEntity(
            "$alistUrl/api/fs/list",
            request,
            String::class.java,
        )
        logger.debug("刷新文件系统响应: {}", resp.body)
    }

    fun limitPicbedContentLength(contentLength: Long): Boolean {
        // 限制图床类上传文件大小为 30MB
        return contentLength <= picbedLimit
    }

    fun uploadFileStreamToOneDrive(
        uploadKind: UploadFileKind,
        fileName: String,
        principal: OAuth2User,
        contentLength: Long,
        inputStream: InputStream
    ): Result<String> {
        val uploadPath = getUploadFilePath(uploadKind, fileName, principal)
        val parentPath = uploadPath.substringBeforeLast("/")
        logger.debug("文件路径: {}", uploadPath)

        val contentType = ContentTypeUtils.guessUploadFileContentType(fileName, uploadKind)
        logger.debug("Content-Type: {}", contentType)

        if (contentType == null) {
            return Result.failure(IllegalArgumentException("无法识别的文件类型"))
        }

        val api = URI("$alistUrl/api/fs/put")

        val headers = alistHttpHeaders().apply {
            set("Content-Type", contentType)
            set("Content-Length", contentLength.toString())
            set("File-Path", uploadPath)
        }

        // 图床场景下需要检查文件大小并创建目录
        if (uploadKind == UploadFileKind.PICBED) {
            if (!limitPicbedContentLength(contentLength)) {
                return Result.failure(
                    FileSizeLimitExceededException("上传的文件大小超过限制", contentLength, picbedLimit)
                )
            }
            makeDirectory(parentPath) // 确保目录存在
        }

        return try {
            // 执行流式转发
            client.execute<Any>(
                api,
                HttpMethod.PUT,
                { request ->
                    // 复制请求头
                    headers.forEach { (key, values) ->
                        values.forEach { value ->
                            request.headers.add(key, value)
                        }
                    }
                    // 设置请求体
                    inputStream.transferTo(request.body)
                },
                { response -> logger.debug("Response is: {}", response.body) }
            )
            // 刷新文件系统缓存
            refreshFileSystem(parentPath)
            Result.success("$alistUrl/d$uploadPath")
        } catch (e: Exception) {
            logger.error("上传过程中发生错误: ${e.message}", e)
            Result.failure(e)
        }
    }
}
