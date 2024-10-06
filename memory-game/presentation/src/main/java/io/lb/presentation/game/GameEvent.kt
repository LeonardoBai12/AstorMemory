package io.lb.presentation.game

/**
 * Represents an event that occurs in the game.
 */
sealed interface GameEvent {
    /**
     * Represents the game being started.
     *
     * @property amount The amount of cards to get.
     */
    data class GetCards(val amount: Int) : GameEvent

    /**
     * Represents a card being flipped.
     *
     * @property index The index of the card that was flipped.
     */
    data class CardFlipped(val index: Int) : GameEvent

    /**
     * Represents a card being matched.
     *
     * @property index The index of the card that was matched.
     */
    data class CardMatched(val index: Int) : GameEvent

    /**
     * Represents a card being mismatched.
     *
     * @property index The index of the card that was mismatched.
     */
    data class CardMismatched(val index: Int) : GameEvent

    /**
     * Represents the game being finished.
     */
    data object GameFinished : GameEvent
}
