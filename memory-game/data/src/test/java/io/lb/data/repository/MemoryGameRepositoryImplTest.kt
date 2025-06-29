package io.lb.data.repository

import io.lb.common.data.model.AstorCard
import io.lb.common.data.model.Score
import io.lb.data.datasource.MemoryGameDataSource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemoryGameRepositoryImplTest {
    private lateinit var dataSource: MemoryGameDataSource
    private lateinit var repository: MemoryGameRepositoryImpl

    @BeforeEach
    fun setUp() {
        dataSource = mockk(relaxed = true)
        repository = MemoryGameRepositoryImpl(dataSource)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When get astor pairs, expect a list of astor pairs`() = runTest {
        coEvery { dataSource.getAstorPairs(2) } returns listOf(
            AstorCard(1, "Bulbasaur.png", "Bulbasaur"),
            AstorCard(2, "Ivysaur.png", "Ivysaur")
        )

        val astorPairs = repository.getAstorPairs(2)

        assertEquals(2, astorPairs.size)
        assertEquals(
            listOf(
                AstorCard(1, "Bulbasaur.png", "Bulbasaur"),
                AstorCard(2, "Ivysaur.png", "Ivysaur")
            ),
            astorPairs
        )
    }

    @Test
    fun `When get scores, expect a score list`() = runTest {
        coEvery { dataSource.getScores() } returns listOf(
            Score(1, 1000),
            Score(2, 2000)
        )

        val scores = repository.getScores()

        assertEquals(2, scores.size)
        assertEquals(
            listOf(Score(1, 1000), Score(2, 2000)),
            scores
        )
    }

    @Test
    fun `When insert score, expect score to be inserted`() = runTest {
        coEvery { dataSource.insertScore(1000) } returns Unit
        repository.insertScore(1000)
    }
}
