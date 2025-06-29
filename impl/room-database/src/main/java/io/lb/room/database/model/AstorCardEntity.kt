package io.lb.room.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.lb.common.data.model.AstorCard
import java.util.UUID

@Entity(tableName = "astor_cards")
data class AstorCardEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val astorId: Int,
    val imageUrl: String,
    val imageData: ByteArray,
    val name: String
) {
    fun toAstorCard() = AstorCard(
        id = id.toString(),
        astorId = astorId,
        imageUrl = imageUrl,
        imageData = imageData,
        name = name
    )
}
