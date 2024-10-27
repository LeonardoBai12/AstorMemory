package io.lb.domain.use_cases

import io.lb.common.data.model.PokemonCard
import io.lb.common.shared.flow.Resource
import io.lb.domain.repository.MemoryGameRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetPokemonPairsUseCaseTest {
    private lateinit var repository: MemoryGameRepository
    private lateinit var useCase: GetPokemonPairsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetPokemonPairsUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When get pokemon pairs, expect a list of pokemon pairs`() = runTest {
        val pokemonPairs = listOf(
            PokemonCard(1, "Bulbasaur.png", "Bulbasaur"),
            PokemonCard(2, "Ivysaur.png", "Ivysaur")
        )
        coEvery { repository.getPokemonPairs(2) } returns pokemonPairs
        val states = mutableListOf<Resource<List<PokemonCard>>>()
        val result = useCase(2).toCollection(states)

        assert(states.first() is Resource.Loading)
        assert(result.last() is Resource.Success)
        assertEquals(pokemonPairs, result.last().data)
    }

    @Test
    fun `When get pokemon pairs, expect an error`() = runTest {
        val error = Exception("Error")
        coEvery { repository.getPokemonPairs(2) } throws error

        val states = mutableListOf<Resource<List<PokemonCard>>>()
        val result = useCase(2).toCollection(states)

        assert(states.first() is Resource.Loading)
        assert(result.last() is Resource.Error)
        assertEquals("Error", result.last().message)
    }
}
