package ink.chyk.worldstation.controller

import ink.chyk.worldstation.dto.ApiResponseDTO
import ink.chyk.worldstation.dto.MotdDTO
import ink.chyk.worldstation.repository.MotdRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/motd")
class MotdController(private val repository: MotdRepository) {
    @GetMapping()
    fun getMotd(): ApiResponseDTO<List<MotdDTO>> {
        return ApiResponseDTO(
            data = repository.getAllMotd()
        )
    }
}
