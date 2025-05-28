package ink.chyk.worldstation.dto

data class ApiResponseDTO<T> (
    val code: Int = 0,
    val message: String? = null,
    val data: T? = null,
)
