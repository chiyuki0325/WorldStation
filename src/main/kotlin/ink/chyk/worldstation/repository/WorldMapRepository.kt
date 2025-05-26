package ink.chyk.worldstation.repository

import ink.chyk.worldstation.dto.WorldMapDTO
import ink.chyk.worldstation.enum.GameVersion

interface WorldMapRepository {
    fun newWorldMap(worldMapDto: WorldMapDTO): Int
    fun updateWorldMap(worldMapDto: WorldMapDTO): Boolean
    fun getWorldMapById(id: Int): WorldMapDTO?
    fun getWorldMapsByUploader(uploader: Int): List<WorldMapDTO>
    fun getAllWorldMaps(): List<WorldMapDTO>
    fun getWorldMapsByVersions(versions: List<GameVersion>): List<WorldMapDTO>
    fun searchWorldMaps(query: String): List<WorldMapDTO>
    // fun deleteWorldMap(id: Long): Boolean
}
