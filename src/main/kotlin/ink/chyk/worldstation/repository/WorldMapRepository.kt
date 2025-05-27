package ink.chyk.worldstation.repository

import ink.chyk.worldstation.dto.WorldMapDTO
import ink.chyk.worldstation.entity.WorldMap
import ink.chyk.worldstation.enum.GameVersion
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class WorldMapRepository {
    fun newWorldMap(worldMapDto: WorldMapDTO): Int = transaction {
        WorldMap.insert {
            it[title] = worldMapDto.title
            it[author] = worldMapDto.author
            it[uploader] = worldMapDto.uploader
            it[gameVersion] = worldMapDto.gameVersion
            it[downloadProvider] = worldMapDto.downloadProvider
            it[downloadUrl] = worldMapDto.downloadUrl
        } get WorldMap.id
    }

    fun updateWorldMap(worldMapDto: WorldMapDTO): Boolean {
        TODO("not implemented")
    }

    fun getWorldMapById(id: Int): WorldMapDTO? {
        TODO("Not yet implemented")
    }

    fun getWorldMapsByUploader(uploader: Int): List<WorldMapDTO> = transaction {
        WorldMap.selectAll().where { WorldMap.uploader eq uploader }
            .map {
                WorldMapDTO(
                    id = it[WorldMap.id],
                    title = it[WorldMap.title],
                    uploader = uploader,
                    author = it[WorldMap.author],
                    gameVersion = it[WorldMap.gameVersion],
                    downloadProvider = it[WorldMap.downloadProvider],
                    downloadUrl = it[WorldMap.downloadUrl]
                )
            }
    }

    fun getAllWorldMaps(): List<WorldMapDTO> = transaction {
        WorldMap.selectAll().map {
            WorldMapDTO(
                id = it[WorldMap.id],
                title = it[WorldMap.title],
                uploader = it[WorldMap.uploader],
                author = it[WorldMap.author],
                gameVersion = it[WorldMap.gameVersion],
                downloadProvider = it[WorldMap.downloadProvider],
                downloadUrl = it[WorldMap.downloadUrl]
            )
        }
    }

    fun getWorldMapsByVersions(versions: List<GameVersion>): List<WorldMapDTO> {
        TODO("Not yet implemented")
    }

    fun searchWorldMaps(query: String): List<WorldMapDTO> {
        TODO("Not yet implemented")
    }
}