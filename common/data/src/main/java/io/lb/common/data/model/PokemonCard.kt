package io.lb.common.data.model

import kotlinx.serialization.Serializable

/**
 * Data class representing a Pokemon.
 *
 * @property id The ID of the Pokemon.
 * @property name The name of the Pokemon.
 */
@Serializable
data class PokemonCard(
    val id: Int,
    val imageUrl: String,
    val name: String
)
