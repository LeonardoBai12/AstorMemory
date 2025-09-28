package io.lb.domain.usecases

import io.lb.common.data.model.AstorCard
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

class GetAstorPairsUseCaseTest {
    private lateinit var repository: MemoryGameRepository
    private lateinit var useCase: GetAstorPairsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetAstorPairsUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When get astor pairs, expect a list of astor pairs`() = runTest {
        val astorPairs = listOf(
            AstorCard(
                id = "1",
                astorId = 1,
                name = "Astor1",
                imageUrl = "https://example.com/astor1.jpg",
                imageData = byteArrayOf(1, 2, 3)
            ),
            AstorCard(
                id = "2",
                astorId = 2,
                name = "Astor2",
                imageUrl = "https://example.com/astor2.jpg",
                imageData = byteArrayOf(4, 5, 6)
            )
        )
        coEvery { repository.getAstorPairs(2) } returns astorPairs
        val states = mutableListOf<Resource<List<AstorCard>>>()
        val result = useCase(2).toCollection(states)

        assert(states.first() is Resource.Loading)
        assert(result.last() is Resource.Success)
        assertEquals(astorPairs, result.last().data)
    }

    @Test
    fun `When get astor pairs, expect an error`() = runTest {
        val error = Exception("Error")
        coEvery { repository.getAstorPairs(2) } throws error

        val states = mutableListOf<Resource<List<AstorCard>>>()
        val result = useCase(2).toCollection(states)

        assert(states.first() is Resource.Loading)
        assert(result.last() is Resource.Error)
        assertEquals("Error", result.last().message)
    }
}
