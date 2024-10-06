package io.lb.impl.ktor.client.model

import io.lb.common.data.model.Pokemon
import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonAPIResponse(
    val id: Int,
    val species: PokemonSpecies,
    val sprites: PokemonSprite
) {
    fun toPokemon(): Pokemon {
        return Pokemon(
            id = id,
            name = species.name,
            imageUrl = sprites.url
        )
    }
}
