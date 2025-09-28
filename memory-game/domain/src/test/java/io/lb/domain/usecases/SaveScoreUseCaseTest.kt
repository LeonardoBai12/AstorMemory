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
    fun `When save score with positive score, expect insertScore to be called`() = runTest {
        coEvery { repository.insertScore(100, 5) } just Runs
        useCase(100, 5)
        coVerify { repository.insertScore(100, 5) }
    }

    @Test
    fun `When save score with zero score, expect insertScore not to be called`() = runTest {
        coEvery { repository.insertScore(0, 5) } just Runs
        useCase(0, 5)
        coVerify(exactly = 0) { repository.insertScore(any(), any()) }
    }

    @Test
    fun `When save score with negative score, expect insertScore not to be called`() = runTest {
        coEvery { repository.insertScore(-10, 5) } just Runs
        useCase(-10, 5)
        coVerify(exactly = 0) { repository.insertScore(any(), any()) }
    }
}
