package ink.chyk.worldstation.controller

import ink.chyk.worldstation.*
import ink.chyk.worldstation.configuration.*
import ink.chyk.worldstation.dto.ApiResponseDTO
import ink.chyk.worldstation.enum.*
import ink.chyk.worldstation.service.OneDriveService
import ink.chyk.worldstation.util.*
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
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
    private val service: OneDriveService,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(OneDriveController::class.java)
    }

    // Fixed by DeepSeek
    @PutMapping("/upload")
    fun uploadFileStreamToOneDrive(
        @RequestParam("upload_kind") uploadKind: UploadFileKind,
        @RequestParam("file_name") fileName: String,
        @AuthenticationPrincipal principal: OAuth2User,
        @RequestHeader("Content-Type") contentType: String,
        @RequestHeader("Content-Length") contentLength: Long,
        inputStream: InputStream,
    ): ResponseEntity<ApiResponseDTO<Any>> {
        val uploadResult = service.uploadFileStreamToOneDrive(
            uploadKind, fileName, principal, contentType, contentLength, inputStream
        )
        return if (uploadResult.isSuccess) {
            ResponseEntity.ok(
                ApiResponseDTO(
                    data = uploadResult.getOrNull(),
                    message = "文件上传成功"
                )
            )
        } else {
            when (val exc = uploadResult.exceptionOrNull()) {
                is FileSizeLimitExceededException -> {
                    logger.warn("File size exceeds limit: ${uploadResult.exceptionOrNull()?.message}")
                    ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(
                        ApiResponseDTO(
                            code = 413,
                            message = "文件大小超过限制，请确保文件小于 ${exc.permittedSize} 字节"
                        )
                    )
                }

                is IllegalArgumentException -> {
                    // 无法识别的文件类型
                    logger.error(
                        "Invalid argument: ${uploadResult.exceptionOrNull()?.message}",
                        uploadResult.exceptionOrNull()
                    )
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ApiResponseDTO(
                            code = 400,
                            message = "无法识别的文件类型"
                        )
                    )
                }

                else -> {
                    logger.error(
                        "Error uploading file: ${uploadResult.exceptionOrNull()?.message}",
                        uploadResult.exceptionOrNull()
                    )
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        ApiResponseDTO(
                            code = 500,
                            message = "文件上传失败，未知原因"
                        )
                    )
                }
            }
        }
    }
}
