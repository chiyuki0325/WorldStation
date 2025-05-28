package ink.chyk.worldstation.controller

import ink.chyk.worldstation.dto.ApiResponseDTO
import ink.chyk.worldstation.enum.GameVersion
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/versions")
class VersionsController {
    @GetMapping
    fun getAllVersions(): ApiResponseDTO<List<String>> {
        return ApiResponseDTO(
            data = GameVersion.entries.map { it.name }
        )
    }
}
