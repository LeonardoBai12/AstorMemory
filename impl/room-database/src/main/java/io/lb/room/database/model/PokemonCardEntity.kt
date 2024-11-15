package io.lb.room.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.lb.common.data.model.PokemonCard
import java.util.UUID

@Entity(tableName = "pokemon_cards")
data class PokemonCardEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val pokemonId: Int,
    val imageUrl: String,
    val imageData: ByteArray,
    val name: String
) {
    fun toPokemonCard() = PokemonCard(
        id = id.toString(),
        pokemonId = pokemonId,
        imageUrl = imageUrl,
        imageData = imageData,
        name = name
    )
}
