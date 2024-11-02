package io.lb.database.service

import io.lb.common.data.model.Score
import io.lb.room.database.dao.MemoryGameDao
import io.lb.room.database.model.ScoreEntity
import io.lb.room.database.service.DatabaseServiceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class DatabaseServiceImplTest {
    private lateinit var dao: MemoryGameDao
    private lateinit var service: DatabaseServiceImpl

    @BeforeEach
    fun setUp() {
        dao = mockk()
        service = DatabaseServiceImpl(dao)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @org.junit.jupiter.api.Test
    fun `When gets scores, expect a list of scores`() = runTest {
        coEvery { dao.getScores() } returns listOf(
            ScoreEntity(score = 1, timeMillis = 1L),
            ScoreEntity(score = 2, timeMillis = 2L),
            ScoreEntity(score = 3, timeMillis = 3L)
        )

        val response = service.getScores()

        assertEquals(3, response.size)
        assertEquals(
            listOf(
                Score(1, 1L),
                Score(2, 2L),
                Score(3, 3L)
            ),
            response
        )
    }

    @org.junit.jupiter.api.Test
    fun `When inserts a score, expect the score to be inserted`() = runTest {
        coEvery { dao.getScores() } returns listOf(
            ScoreEntity(score = 1, timeMillis = 1L),
            ScoreEntity(score = 2, timeMillis = 2L),
            ScoreEntity(score = 3, timeMillis = 3L)
        )
        coEvery { dao.insertScore(any()) } returns Unit
        var error = false

        runCatching {
            service.insertScore(1)
        }.onFailure {
            throw it
        }

        assertFalse(error)
    }

    @org.junit.jupiter.api.Test
    fun `When inserts a score, expect an exception`() = runTest {
        coEvery { dao.getScores() } returns listOf(
            ScoreEntity(score = 1, timeMillis = 1L),
            ScoreEntity(score = 2, timeMillis = 2L),
            ScoreEntity(score = 3, timeMillis = 3L)
        )
        coEvery { dao.insertScore(any()) } throws Exception()
        var error = false

        runCatching {
            service.insertScore(1)
        }.onFailure {
            error = true
        }

        assert(error)
    }

    @org.junit.jupiter.api.Test
    fun `When inserts a score, expect it to be ignored`() = runTest {
        coEvery { dao.getScores() } returns listOf(
            ScoreEntity(score = 100, timeMillis = 1L),
            ScoreEntity(score = 200, timeMillis = 2L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
        )
        coEvery { dao.insertScore(any()) } returns Unit

        service.insertScore(90)

        coVerify(exactly = 0) {
            dao.insertScore(any())
        }
    }

    @org.junit.jupiter.api.Test
    fun `When inserts a score, expect it to be inserted`() = runTest {
        coEvery { dao.getScores() } returns listOf(
            ScoreEntity(score = 100, timeMillis = 1L),
            ScoreEntity(score = 200, timeMillis = 2L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
            ScoreEntity(score = 300, timeMillis = 3L),
        )
        coEvery { dao.insertScore(any()) } returns Unit

        service.insertScore(110)

        coVerify {
            dao.insertScore(any())
        }
    }
}
