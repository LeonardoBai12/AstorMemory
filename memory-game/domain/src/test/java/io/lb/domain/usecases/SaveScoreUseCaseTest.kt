package io.lb.domain.usecases

import io.lb.domain.repository.MemoryGameRepository
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

class SaveScoreUseCaseTest {
    private lateinit var repository: MemoryGameRepository
    private lateinit var useCase: SaveScoreUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = SaveScoreUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When save score, expect insertScore to be called`() = runTest {
        coEvery { repository.insertScore(100) } just Runs
        useCase(100)
        coVerify { repository.insertScore(100) }
    }
}
