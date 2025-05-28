package ink.chyk.worldstation.entity

import ink.chyk.worldstation.enum.DownloadProvider
import ink.chyk.worldstation.enum.GameVersion
import org.jetbrains.exposed.v1.core.Table

// 标题最长 72 字符
const val MAX_TITLE_LENGTH = 72

// 作者最长 32 字符
const val MAX_AUTHOR_LENGTH = 32

object WorldMap: Table("maps") {
    // 地图自增 ID，数字形式
    val id = integer("id").autoIncrement()
    // 地图标题
    val title = varchar("title", MAX_TITLE_LENGTH)
    // 地图索引
    val title_lower = varchar("title_lower", MAX_TITLE_LENGTH)
    // 地图作者
    val author = varchar("author", MAX_AUTHOR_LENGTH).nullable().index()
    // 上传者的用户 id
    val uploader = integer("uploader").index()
    // 游戏版本
    val gameVersion = enumeration("game_version", GameVersion::class).index()
    // 下载方式
    val downloadProvider = enumeration("download_provider", DownloadProvider::class)
    // 下载链接
    val downloadUrl = varchar("download_url", 1024)

    override val primaryKey = PrimaryKey(id)
}
