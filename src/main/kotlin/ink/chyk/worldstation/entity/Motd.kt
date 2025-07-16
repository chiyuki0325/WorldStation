package ink.chyk.worldstation.entity

import org.jetbrains.exposed.v1.core.Table

object Motd: Table("motd") {
    // 自增 ID
    val id = integer("id").autoIncrement()
    // 内容
    val content = text("content")
    // 是否启用
    val enabled = bool("enabled").default(true)

    override val primaryKey = PrimaryKey(id)
}
