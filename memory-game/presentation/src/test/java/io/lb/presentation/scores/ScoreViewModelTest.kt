package io.lb.presentation.scores

import app.cash.turbine.test
import io.lb.common.data.model.Score
import io.lb.domain.repository.MemoryGameRepository
import io.lb.domain.use_cases.CalculateScoreUseCase
import io.lb.domain.use_cases.GetPokemonPairsUseCase
import io.lb.domain.use_cases.GetScoresUseCase
import io.lb.domain.use_cases.MemoryGameUseCases
import io.lb.domain.use_cases.SaveScoreUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class ScoreViewModelTest {
    private lateinit var repository: MemoryGameRepository
    private lateinit var useCases: MemoryGameUseCases
    private lateinit var viewModel: ScoreViewModel

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCases = MemoryGameUseCases(
            GetScoresUseCase(repository),
            SaveScoreUseCase(repository),
            GetPokemonPairsUseCase(repository),
            CalculateScoreUseCase(),
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When get scores, expect success`() = runTest {
        coEvery { repository.getScores() } returns listOf(
            Score(1, 100),
            Score(2, 200),
        )

        viewModel = ScoreViewModel(useCases)
        assert(viewModel.state.value.isLoading)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assertFalse(emission.isLoading)
            assertNull(emission.message)
            assertEquals(2, emission.  scores.size)
            assertEquals(listOf(Score(1, 100), Score(2, 200)), emission.scores)
        }
    }

    @Test
    fun `When get scores, expect error`() = runTest {
        coEvery { repository.getScores() } throws Exception("Failed to get scores")

        viewModel = ScoreViewModel(useCases)
        assert(viewModel.state.value.isLoading)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assertFalse(emission.isLoading)
            assertEquals("Failed to get scores", emission.message)
            assert(emission.scores.isEmpty())
        }
    }
}
