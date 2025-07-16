package ink.chyk.worldstation.dto

import ink.chyk.worldstation.entity.Motd
import org.jetbrains.exposed.v1.core.ResultRow

data class MotdDTO(
    val id: Int,
    val content: String,
    val enabled: Boolean,
) {
    companion object {
        fun fromEntity(entity: ResultRow): MotdDTO {
            return MotdDTO(
                id = entity[Motd.id],
                content = entity[Motd.content],
                enabled = entity[Motd.enabled]
            )
        }
    }
}
