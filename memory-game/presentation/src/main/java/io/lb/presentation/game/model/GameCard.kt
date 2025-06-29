package io.lb.presentation.game.model

import io.lb.common.data.model.AstorCard

/**
 * Represents a game card.
 *
 * @property isFlipped Whether the card is flipped or not.
 * @property isMatched Whether the card is matched or not.
 * @property astorCard The [AstorCard] associated with the card.
 */
data class GameCard(
    var isFlipped: Boolean = false,
    var isMatched: Boolean = false,
    val astorCard: AstorCard
)
