package ink.chyk.worldstation.controller

import ink.chyk.worldstation.dto.WorldMapDTO
import ink.chyk.worldstation.repository.WorldMapRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/worldmaps")
class WorldMapController(private val repository: WorldMapRepository) {
    @GetMapping
    fun getAllWorldMaps(): List<WorldMapDTO> {
        return repository.getAllWorldMaps()
    }

    @PostMapping
    fun newWorldMap(@RequestBody worldMapDTO: WorldMapDTO): WorldMapDTO {
        return repository.newWorldMap(worldMapDTO)
    }

    @PutMapping
    fun updateWorldMap(@RequestBody worldMapDTO: WorldMapDTO): Boolean {
        return repository.updateWorldMap(worldMapDTO)
    }
}
