package io.lb.domain.use_cases

/**
 * Use cases for the memory game.
 *
 * @property getScoresUseCase The use case to get the scores.
 * @property saveScoreUseCase The use case to save a score.
 * @property getMemoryGameUseCase The use case to get the memory game.
 */
data class MemoryGameUseCases(
    val getScoresUseCase: GetScoresUseCase,
    val saveScoreUseCase: SaveScoreUseCase,
    val getMemoryGameUseCase: GetPokemonPairsUseCase,
)
