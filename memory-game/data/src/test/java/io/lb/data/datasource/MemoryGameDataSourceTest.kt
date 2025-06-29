package io.lb.data.datasource

import io.lb.common.data.model.AstorCard
import io.lb.common.data.model.Score
import io.lb.common.data.service.ClientService
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
    private lateinit var clientService: ClientService
    private lateinit var databaseService: DatabaseService
    private lateinit var dataSource: MemoryGameDataSource

    @BeforeEach
    fun setUp() {
        clientService = mockk()
        databaseService = mockk()
        dataSource = MemoryGameDataSource(databaseService, clientService)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When get scores, expect a score list`() = runTest {
        coEvery { databaseService.getScores() } returns listOf(
            Score(1, 1000),
            Score(2, 2000)
        )

        val scores = dataSource.getScores()

        assertEquals(2, scores.size)
        assertEquals(listOf(Score(1, 1000), Score(2, 2000)), scores)
    }

    @Test
    fun `When insert score, expect score to be inserted`() = runTest {
        coEvery { databaseService.insertScore(1000) } just Runs
        dataSource.insertScore(1000)
        coVerify { databaseService.insertScore(1000) }
    }

    @Test
    fun `When get astor pairs, expect a list of astor pairs`() = runTest {
        coEvery { clientService.getAstor(2) } returns listOf(
            AstorCard(1, "Bulbasaur.png", "Bulbasaur"),
            AstorCard(2, "Ivysaur.png", "Ivysaur")
        )

        val astorPairs = dataSource.getAstorPairs(2)

        assertEquals(2, astorPairs.size)
        assertEquals(
            listOf(
                AstorCard(1, "Bulbasaur.png", "Bulbasaur"),
                AstorCard(2, "Ivysaur.png", "Ivysaur")
            ),
            astorPairs
        )
    }
}
