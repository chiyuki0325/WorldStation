package ink.chyk.worldstation.controller

import ink.chyk.worldstation.*
import ink.chyk.worldstation.configuration.*
import ink.chyk.worldstation.enum.*
import ink.chyk.worldstation.util.*
import org.slf4j.*
import org.springframework.http.*
import org.springframework.http.client.*
import org.springframework.security.core.annotation.*
import org.springframework.security.oauth2.core.user.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.*
import org.springframework.web.servlet.mvc.method.annotation.*
import java.io.*
import java.net.*


@RestController
@RequestMapping("/api/onedrive")
class OneDriveController(
    private val config: OneDriveConfig,
    private val restTemplate: RestTemplate
) {
    private final val logger = LoggerFactory.getLogger(OneDriveController::class.java)

    private final val alistToken: String = config.alistToken
        ?: throw IllegalArgumentException("Alist token is not configured")

    private final val alistUrl: String = config.alist
        ?: throw IllegalArgumentException("Alist URL is not configured")

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

    private fun makeDirectory(path: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", alistToken)
        val request = HttpEntity("""{"path": "$path"}""", headers)
        restTemplate.postForEntity(
            "$alistUrl/api/fs/mkdir",
            request,
            String::class.java
        )
    }

    fun limitPicbedContentLength(contentLength: Long): Boolean {
        // 限制图床类上传文件大小为 30MB
        return contentLength <= 30 * 1024 * 1024
    }

    // Fixed by DeepSeek
    @PutMapping("/upload")
    fun uploadFileStreamToOneDrive(
        @RequestParam("upload_kind") uploadKind: UploadFileKind,
        @RequestParam("file_name") fileName: String,
        @AuthenticationPrincipal principal: OAuth2User,
        @RequestHeader("Content-Length") contentLength: Long,
        inputStream: InputStream,
    ): ResponseEntity<StreamingResponseBody> {
        val uploadPath = getUploadFilePath(uploadKind, fileName, principal)
        logger.debug("文件路径: $uploadPath")

        val contentType = ContentTypeUtils.guessUploadFileContentType(fileName, uploadKind)
        logger.debug("Content-Type: $contentType")

        if (contentType == null) {
            return ResponseEntity.badRequest().body(null)
        }

        val api = URI("$alistUrl/api/fs/put")

        val headers = HttpHeaders().apply {
            set("Content-Type", contentType)
            set("Content-Length", contentLength.toString())
            set("Authorization", alistToken)
            set("File-Path", uploadPath)
        }

        // 图床场景下需要检查文件大小并创建目录
        if (uploadKind == UploadFileKind.PICBED) {
            if (!limitPicbedContentLength(contentLength)) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(null) // 返回 413 Payload Too Large
            }
            makeDirectory(uploadPath.substringBeforeLast("/")) // 确保目录存在
        }

        try {
            val responseBody = StreamingResponseBody { outputStream ->
                // 执行流式转发
                restTemplate.execute<Any>(
                    api,
                    HttpMethod.PUT,
                    { request ->
                        // 复制请求头
                        headers.forEach { key, values ->
                            values.forEach { value ->
                                request.headers.add(key, value)
                            }
                        }
                        // 设置请求体
                        inputStream.transferTo(request.body)
                    },
                    { response ->
                        // 将响应内容写入输出流
                        response.body.transferTo(outputStream)
                        null
                    }
                )
            }

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody)
        } catch (e: Exception) {
            logger.error("上传过程中发生错误: ${e.message}", e)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}
