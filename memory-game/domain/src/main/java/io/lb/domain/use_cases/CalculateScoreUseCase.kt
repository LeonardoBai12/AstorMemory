package io.lb.domain.use_cases

/**
 * Use case to calculate the score.
 */
class CalculateScoreUseCase {
    /**
     * Calculates the score based on the amount of cards and the number of attempts.
     *
     * @param amount The amount of cards.
     * @param attempts The number of attempts.
     * @return The calculated score.
     */
    fun invoke(amount: Int, attempts: Int): Int {
        val maxScore = 100 * amount
        val penalty = attempts * 10
        return (maxScore - penalty).coerceAtLeast(0)
    }
}
