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
    fun `When calculate score, expect a score of 200`() {
        val result = useCase(2, 0)
        assertEquals(200, result)
    }

    @Test
    fun `When calculate score, expect a score of 190`() {
        val result = useCase(2, 1)
        assertEquals(190, result)
    }

    @Test
    fun `When calculate score, expect a score of 180`() {
        val result = useCase(12, 12)
        assertEquals(1080, result)
    }
}
