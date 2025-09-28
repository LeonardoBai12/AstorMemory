package io.lb.data.datasource

import io.lb.common.data.model.Score
import io.lb.common.data.service.DatabaseService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MemoryGameDataSourceTest {
    private lateinit var databaseService: DatabaseService
    private lateinit var dataSource: MemoryGameDataSource

    @BeforeEach
    fun setUp() {
        databaseService = mockk()
        dataSource = MemoryGameDataSource(databaseService)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When get scores, expect a score list`() = runTest {
        coEvery { databaseService.getScores() } returns listOf(
            Score(score = 1000, amount = 5, timeMillis = 123456789L),
            Score(score = 2000, amount = 10, timeMillis = 987654321L)
        )

        val scores = dataSource.getScores()

        assertEquals(2, scores.size)
        assertEquals(
            listOf(
                Score(score = 1000, amount = 5, timeMillis = 123456789L),
                Score(score = 2000, amount = 10, timeMillis = 987654321L)
            ),
            scores
        )
    }

    @Test
    fun `When get scores by amount, expect a score list for that amount`() = runTest {
        coEvery { databaseService.getScoresByAmount(5) } returns listOf(
            Score(score = 1000, amount = 5, timeMillis = 123456789L),
            Score(score = 1500, amount = 5, timeMillis = 987654321L)
        )

        val scores = dataSource.getScoresByAmount(5)

        assertEquals(2, scores.size)
        assertEquals(
            listOf(
                Score(score = 1000, amount = 5, timeMillis = 123456789L),
                Score(score = 1500, amount = 5, timeMillis = 987654321L)
            ),
            scores
        )
    }

    @Test
    fun `When insert score, expect score to be inserted`() = runTest {
        coEvery { databaseService.insertScore(1000, 5) } just Runs

        dataSource.insertScore(1000, 5)

        coVerify { databaseService.insertScore(1000, 5) }
    }
}
