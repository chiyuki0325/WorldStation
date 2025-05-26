package ink.chyk.worldstation.dto

import ink.chyk.worldstation.enum.DownloadProvider
import ink.chyk.worldstation.enum.GameVersion

data class WorldMapDTO(
    val id: Int? = null,
    val title: String,
    val uploader: Int,
    val author: String?,
    val gameVersion: GameVersion,
    val downloadProvider: DownloadProvider,
    val downloadUrl: String
)
