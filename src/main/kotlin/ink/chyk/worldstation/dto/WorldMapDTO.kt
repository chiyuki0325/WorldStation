package ink.chyk.worldstation.dto

import ink.chyk.worldstation.entity.WorldMap
import ink.chyk.worldstation.enum.DownloadProvider
import ink.chyk.worldstation.enum.GameVersion
import org.jetbrains.exposed.v1.core.ResultRow

data class WorldMapDTO(
    val id: Int? = null,
    val title: String,
    val uploader: Int,
    val author: String?,
    val gameVersion: GameVersion,
    val downloadProvider: DownloadProvider,
    val downloadUrl: String
) {
    companion object {
        fun fromEntity(entity: ResultRow): WorldMapDTO {
            return WorldMapDTO(
                id = entity[WorldMap.id],
                title = entity[WorldMap.title],
                uploader = entity[WorldMap.uploader],
                author = entity[WorldMap.author],
                gameVersion = entity[WorldMap.gameVersion],
                downloadProvider = entity[WorldMap.downloadProvider],
                downloadUrl = entity[WorldMap.downloadUrl]
            )
        }
    }
}
