package io.lb.impl.ktor.client.model

import io.lb.common.data.model.PokemonCard
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
internal data class PokemonAPIResponse(
    val id: Int,
    val name: String,
    val sprites: PokemonSprite
) {
    fun toPokemon(): PokemonCard {
        return PokemonCard(
            id = id,
            name = name.capitalize(Locale.ROOT),
            imageUrl = sprites.url
        )
    }
}
