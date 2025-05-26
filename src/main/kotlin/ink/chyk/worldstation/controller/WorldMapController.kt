package ink.chyk.worldstation.controller

import ink.chyk.worldstation.dto.WorldMapDTO
import ink.chyk.worldstation.repository.WorldMapRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/worldmaps")
class WorldMapController(private val repository: WorldMapRepository) {
    @GetMapping
    fun getAllWorldMaps(): List<WorldMapDTO> {
        return repository.getAllWorldMaps()
    }
}
