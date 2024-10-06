package io.lb.presentation.game.model

import io.lb.common.data.model.PokemonCard

/**
 * Represents a game card.
 *
 * @property isFlipped Whether the card is flipped or not.
 * @property isMatched Whether the card is matched or not.
 * @property pokemonCard The [PokemonCard] associated with the card.
 */
data class GameCard(
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false,
    val pokemonCard: PokemonCard
)
