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
            AstorCard(1, "Bulbasaur.png", "Bulbasaur"),
            AstorCard(2, "Ivysaur.png", "Ivysaur")
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
