package io.lb.data.repository

import android.content.Context
import io.lb.common.data.model.Score
import io.lb.data.datasource.MemoryGameDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemoryGameRepositoryImplTest {
    private lateinit var dataSource: MemoryGameDataSource
    private lateinit var context: Context
    private lateinit var repository: MemoryGameRepositoryImpl

    @BeforeEach
    fun setUp() {
        dataSource = mockk(relaxed = true)
        context = mockk(relaxed = true)
        repository = MemoryGameRepositoryImpl(context, dataSource)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When get scores, expect a score list`() = runTest {
        val scores = listOf(
            Score(score = 1000, amount = 5, timeMillis = 123456789L),
            Score(score = 2000, amount = 10, timeMillis = 987654321L)
        )
        coEvery { dataSource.getScores() } returns scores

        val result = repository.getScores()

        assertEquals(2, result.size)
        assertEquals(scores, result)
    }

    @Test
    fun `When get scores by amount, expect a score list for that amount`() = runTest {
        val scores = listOf(
            Score(score = 1000, amount = 5, timeMillis = 123456789L),
            Score(score = 1500, amount = 5, timeMillis = 987654321L)
        )
        coEvery { dataSource.getScoresByAmount(5) } returns scores

        val result = repository.getScoresByAmount(5)

        assertEquals(2, result.size)
        assertEquals(scores, result)
    }

    @Test
    fun `When insert score, expect score to be inserted`() = runTest {
        coEvery { dataSource.insertScore(1000, 5) } returns Unit

        repository.insertScore(1000, 5)

        coVerify { dataSource.insertScore(1000, 5) }
    }
}
