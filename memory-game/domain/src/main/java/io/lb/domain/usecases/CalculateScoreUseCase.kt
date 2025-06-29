package io.lb.domain.usecases

/**
 * Use case to calculate the score.
 */
class CalculateScoreUseCase {
    /**
     * Calculates the score based on the amount of cards and the number of attempts.
     *
     * @param amount The amount of cards.
     * @param combos The list of combos.
     * @param mismatches The number of attempts.
     * @return The calculated score.
     */
    operator fun invoke(
        amount: Int,
        combos: List<Int>,
        mismatches: Int
    ): Int {
        if (amount <= 1) {
            return 0
        }

        val maxScore = MAX_SCORE_MULTIPLIER * amount
        val penalty = mismatches * PENALTY
        var score = (maxScore - penalty).coerceAtLeast(0)

        combos.forEach {
            score += (it) * COMBO_MULTIPLIER
        }

        return score
    }

    companion object {
        private const val PENALTY = 10
        private const val COMBO_MULTIPLIER = 10
        private const val MAX_SCORE_MULTIPLIER = 100
    }
}
