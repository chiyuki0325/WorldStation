package ink.chyk.worldstation.util

import ink.chyk.worldstation.enum.UploadFileKind

class ContentTypeUtils {
    companion object {
        fun guessUploadFileContentType(
            fileName: String,
            kind: UploadFileKind
        ): String? {
            val ext = fileName.substringAfterLast('.', "").lowercase()
            if (ext.isEmpty()) return null  // 不支持不带扩展名的文件上传
            return when (kind) {
                UploadFileKind.WORLDMAP -> guessWorldMapContentType(ext)
                UploadFileKind.PICBED -> guessPicbedContentType(ext)
            }
        }

        private fun guessWorldMapContentType(
            ext: String
        ): String? {
            return when (ext) {
                "zip" -> "application/zip"
                "gz" -> "application/gzip"
                "zst" -> "application/x-zstd"
                "rar" -> "application/x-rar-compressed"
                "7z" -> "application/x-7z-compressed"
                "exe" -> "application/x-ms-dos-executable"
                else -> null  // 不支持的地图文件类型
            }
        }

        private fun guessPicbedContentType(
            ext: String
        ): String? {
            return when (ext) {
                "jpg", "jpeg" -> "image/jpeg"
                "png" -> "image/png"
                "gif" -> "image/gif"
                "bmp" -> "image/bmp"
                "webp" -> "image/webp"
                "svg" -> "image/svg+xml"
                else -> null  // 不支持的图床文件类型
            }
        }

        fun testContentType(
            contentType: String,
            kind: UploadFileKind
        ): Boolean {
            return when (kind) {
                UploadFileKind.WORLDMAP -> contentType in setOf(
                    "application/zip", "application/gzip", "application/x-zstd",
                    "application/x-rar-compressed", "application/x-7z-compressed",
                    "application/x-ms-dos-executable"
                )
                UploadFileKind.PICBED -> contentType in setOf(
                    "image/jpeg", "image/png", "image/gif",
                    "image/bmp", "image/webp", "image/svg+xml"
                )
            }
        }
    }
}
