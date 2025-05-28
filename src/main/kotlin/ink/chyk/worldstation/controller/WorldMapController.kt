package ink.chyk.worldstation.controller

import ink.chyk.worldstation.dto.*
import ink.chyk.worldstation.enum.GameVersion
import ink.chyk.worldstation.repository.WorldMapRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/worldmaps")
class WorldMapController(private val repository: WorldMapRepository) {
    @PostMapping
    fun newWorldMap(@RequestBody worldMapDTO: WorldMapDTO): ApiResponseDTO<WorldMapDTO> {
        return ApiResponseDTO(data = repository.newWorldMap(worldMapDTO))
    }

    @PutMapping
    fun updateWorldMap(@RequestBody worldMapDTO: WorldMapDTO): ApiResponseDTO<Boolean> {
        val successOrNot = repository.updateWorldMap(worldMapDTO)
        if (successOrNot) {
            return ApiResponseDTO(data = true)
        } else {
            return ApiResponseDTO(code = 404, message = "请求的地图不存在")
        }
    }

    @GetMapping("/worldmap/{id}")
    fun getWorldMapById(@PathVariable id: Int): ApiResponseDTO<WorldMapDTO> {
        val worldMap = repository.getWorldMapById(id)
        return if (worldMap != null) {
            ApiResponseDTO(data = worldMap)
        } else {
            ApiResponseDTO(code = 404, message = "请求的地图不存在")
        }
    }

    @GetMapping()
    fun searchWorldMaps(
        // 竟然都要手工写 required=false，那很不咳特灵了
        @RequestParam(required = false) query: String? = null,
        @RequestParam(required = false) pageSize: Int = 20,
        @RequestParam(required = false) page: Int = 0,
        @RequestParam(required = false) versions: List<String>? = null,
        @RequestParam(required = false) uploader: Int? = null
    ): ApiResponseDTO<List<WorldMapDTO>> {
        // 处理游戏版本号
        val versions = versions?.map {
            try {
                GameVersion.valueOf(it)
            } catch (e: IllegalArgumentException) {
                return@searchWorldMaps ApiResponseDTO<List<WorldMapDTO>>(
                    code = 400,
                    message = "无效的游戏版本号: $it"
                )
            }
        }
        // 查询
        val worldMaps = repository.queryWorldMaps(
            query = query,
            pageSize = pageSize,
            pageNumber = page,
            versions = versions,
            uploader = uploader
        )
        return ApiResponseDTO(data = worldMaps)
    }
}
