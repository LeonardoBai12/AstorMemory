package io.lb.domain.use_cases

import io.lb.common.data.model.Score
import io.lb.common.shared.flow.Resource
import io.lb.domain.repository.MemoryGameRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetScoresUseCaseTest {
    private lateinit var repository: MemoryGameRepository
    private lateinit var useCase: GetScoresUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetScoresUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When get scores, expect a list of scores`() = runTest {
        val scores = listOf(
            Score(1, 100),
            Score(2, 200)
        )
        coEvery { repository.getScores() } returns scores
        val states = mutableListOf<Resource<List<Score>>>()
        val result = useCase().toCollection(states)

        assert(states.first() is Resource.Loading)
        assert(result.last() is Resource.Success)
        assertEquals(scores, result.last().data)
    }

    @Test
    fun `When get scores, expect an error`() = runTest {
        val error = Exception("Error")
        coEvery { repository.getScores() } throws error

        val states = mutableListOf<Resource<List<Score>>>()
        val result = useCase().toCollection(states)

        assert(states.first() is Resource.Loading)
        assert(result.last() is Resource.Error)
        assertEquals("Error", result.last().message)
    }
}
