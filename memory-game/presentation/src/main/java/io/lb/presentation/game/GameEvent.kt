package io.lb.presentation.game

/**
 * Represents an event that occurs in the game.
 */
sealed interface GameEvent {
    /**
     * Represents the request for games.
     */
    data object OnRequestGames : GameEvent

    /**
     * Represents a card being flipped.
     *
     * @property index The index of the card that was flipped.
     */
    data class CardFlipped(val index: Int) : GameEvent

    /**
     * Represents a card being matched.
     *
     * @property id The id of the card that was matched.
     */
    data class CardMatched(val id: Int) : GameEvent

    /**
     * Represents a card being mismatched.
     */
    data object CardMismatched : GameEvent

    /**
     * Represents the game being finished.
     */
    data object GameFinished : GameEvent
}
