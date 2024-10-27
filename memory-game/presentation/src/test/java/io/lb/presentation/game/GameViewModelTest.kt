package io.lb.presentation.game

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.lb.common.data.model.PokemonCard
import io.lb.domain.repository.MemoryGameRepository
import io.lb.domain.use_cases.CalculateScoreUseCase
import io.lb.domain.use_cases.GetPokemonPairsUseCase
import io.lb.domain.use_cases.GetScoresUseCase
import io.lb.domain.use_cases.MemoryGameUseCases
import io.lb.domain.use_cases.SaveScoreUseCase
import io.lb.presentation.game.model.GameCard
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GameViewModelTest {
    private lateinit var repository: MemoryGameRepository
    private lateinit var useCases: MemoryGameUseCases
    private lateinit var viewModel: GameViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk()
        useCases = MemoryGameUseCases(
            GetScoresUseCase(repository),
            SaveScoreUseCase(repository),
            GetPokemonPairsUseCase(repository),
            CalculateScoreUseCase(),
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `When the view model is created, expect cards to be fetched`() =  runTest {
        coEvery { repository.getPokemonPairs(5) } returns pokemonCards()

        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle["amount"] ?: 0 } returns 5
        viewModel = GameViewModel(useCases, savedStateHandle)
        assert(viewModel.state.value.isLoading)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assert(emission.isLoading.not())
            assertEquals(gameCards(), emission.cards)
            assertNull(emission.message)
        }
    }

    @Test
    fun `When a card is flipped, expect the card to be updated`() = runTest {
        coEvery { repository.getPokemonPairs(5) } returns pokemonCards()

        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle["amount"] ?: 0 } returns 5
        viewModel = GameViewModel(useCases, savedStateHandle)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assertEquals(
                gameCards(),
                emission.cards
            )
            advanceUntilIdle()

            viewModel.onEvent(GameEvent.CardFlipped(0))

            val emission2 = awaitItem()
            assertEquals(
                gameCards().map {
                    if (it.pokemonCard.id == 1)
                        it.copy(isFlipped = true) else it
                },
                emission2.cards
            )
        }
    }

    @Test
    fun `When a card is matched, expect the card to be updated`() = runTest {
        coEvery { repository.getPokemonPairs(5) } returns pokemonCards()

        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle["amount"] ?: 0 } returns 5
        viewModel = GameViewModel(useCases, savedStateHandle)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assertEquals(
                gameCards(),
                emission.cards
            )
            advanceUntilIdle()

            viewModel.onEvent(GameEvent.CardMatched(1))

            val emission2 = awaitItem()
            assertEquals(
                gameCards().map {
                    if (it.pokemonCard.id == 1)
                        it.copy(isMatched = true) else it
                },
                emission2.cards
            )
        }
    }

    @Test
    fun `When a card is mismatched, expect the card to be updated`() = runTest {
        coEvery { repository.getPokemonPairs(5) } returns pokemonCards()

        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle["amount"] ?: 0 } returns 5
        viewModel = GameViewModel(useCases, savedStateHandle)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assertEquals(
                gameCards(),
                emission.cards
            )
            advanceUntilIdle()
            viewModel.onEvent(GameEvent.CardFlipped(2))
            advanceUntilIdle()

            val emission2 = awaitItem()
            assertEquals(
                gameCards().map {
                    if (it.pokemonCard.id == 3)
                        it.copy(isFlipped = true) else it
                },
                emission2.cards
            )

            viewModel.onEvent(GameEvent.CardFlipped(3))
            advanceUntilIdle()

            val emission3 = awaitItem()
            assertEquals(
                gameCards().map {
                    if (it.pokemonCard.id == 4 || it.pokemonCard.id == 3)
                        it.copy(isFlipped = true) else it
                },
                emission3.cards
            )

            viewModel.onEvent(GameEvent.CardMismatched)
            advanceUntilIdle()

            val emission4 = awaitItem()
            assertEquals(
                gameCards(),
                emission4.cards
            )
        }
    }

    private fun pokemonCards(): List<PokemonCard> = listOf(
        PokemonCard(1, "Bulbasaur", "https://pokeapi.co/api/v2/pokemon/1"),
        PokemonCard(2, "Ivysaur", "https://pokeapi.co/api/v2/pokemon/2"),
        PokemonCard(3, "Venusaur", "https://pokeapi.co/api/v2/pokemon/3"),
        PokemonCard(4, "Charmander", "https://pokeapi.co/api/v2/pokemon/4"),
        PokemonCard(5, "Charmeleon", "https://pokeapi.co/api/v2/pokemon/5"),
    )

    private fun gameCards(): List<GameCard> = pokemonCards().map {
        GameCard(pokemonCard = it)
    }
}
