package io.lb.domain.usecases

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculateScoreUseCaseTest {
    private lateinit var useCase: CalculateScoreUseCase

    @BeforeEach
    fun setUp() {
        useCase = CalculateScoreUseCase()
    }

    @Test
    fun `When calculate score with no combos and no mismatches, expect base score`() {
        val result = useCase(amount = 2, combos = emptyList(), mismatches = 0)
        assertEquals(200, result)
    }

    @Test
    fun `When calculate score with no combos and some mismatches, expect penalized score`() {
        val result = useCase(amount = 2, combos = emptyList(), mismatches = 1)
        assertEquals(190, result)
    }

    @Test
    fun `When calculate score with combos and mismatches, expect score with combo bonus`() {
        val result = useCase(amount = 12, combos = listOf(3, 2), mismatches = 12)
        assertEquals(1130, result)
    }

    @Test
    fun `When calculate score with large combos, expect higher bonus`() {
        val result = useCase(amount = 10, combos = listOf(5, 4, 3), mismatches = 5)
        assertEquals(1070, result)
    }

    @Test
    fun `When calculate score with amount 1, expect 0`() {
        val result = useCase(amount = 1, combos = listOf(5), mismatches = 0)
        assertEquals(0, result)
    }

    @Test
    fun `When calculate score with many mismatches, expect minimum 0`() {
        val result = useCase(amount = 2, combos = emptyList(), mismatches = 30)
        assertEquals(0, result)
    }
}
