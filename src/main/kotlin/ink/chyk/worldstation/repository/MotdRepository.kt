package ink.chyk.worldstation.repository

import ink.chyk.worldstation.dto.MotdDTO
import ink.chyk.worldstation.entity.Motd
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class MotdRepository {
    fun getAllMotd(): List<MotdDTO> = transaction {
        Motd.selectAll().map(MotdDTO::fromEntity)
    }
}
