package io.lb.presentation.game

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.lb.common.data.model.AstorCard
import io.lb.domain.repository.MemoryGameRepository
import io.lb.domain.usecases.CalculateScoreUseCase
import io.lb.domain.usecases.GetAstorPairsUseCase
import io.lb.domain.usecases.GetScoresByAmountUseCase
import io.lb.domain.usecases.GetScoresUseCase
import io.lb.domain.usecases.MemoryGameUseCases
import io.lb.domain.usecases.SaveScoreUseCase
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
import kotlin.test.assertContentEquals
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
            getScoresUseCase = GetScoresUseCase(repository),
            saveScoreUseCase = SaveScoreUseCase(repository),
            getMemoryGameUseCase = GetAstorPairsUseCase(repository),
            calculateScoreUseCase = CalculateScoreUseCase(),
            getScoresByAmountUseCase = GetScoresByAmountUseCase(repository)
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `When the view model is created, expect cards to be fetched`() = runTest {
        coEvery { repository.getAstorPairs(5) } returns astorCards()

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
        coEvery { repository.getAstorPairs(5) } returns astorCards()

        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle["amount"] ?: 0 } returns 5
        viewModel = GameViewModel(useCases, savedStateHandle)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assertContentEquals(
                gameCards(),
                emission.cards
            )
            advanceUntilIdle()

            viewModel.onEvent(GameEvent.CardFlipped(0))

            val emission2 = awaitItem()
            assertContentEquals(
                gameCards().map {
                    if (it.astorCard.id == "1") {
                        it.copy(isFlipped = true)
                    } else {
                        it
                    }
                },
                emission2.cards
            )
        }
    }

    @Test
    fun `When a card is matched, expect the card to be updated`() = runTest {
        coEvery { repository.getAstorPairs(5) } returns astorCards()

        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle["amount"] ?: 0 } returns 5
        viewModel = GameViewModel(useCases, savedStateHandle)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assertContentEquals(
                gameCards(),
                emission.cards
            )
            advanceUntilIdle()

            viewModel.onEvent(GameEvent.CardMatched(1))

            val emission2 = awaitItem()
            assertContentEquals(
                gameCards().map {
                    if (it.astorCard.id == "1") {
                        it.copy(isMatched = true)
                    } else {
                        it
                    }
                },
                emission2.cards
            )
        }
    }

    @Test
    fun `When a card is mismatched, expect the card to be updated`() = runTest {
        coEvery { repository.getAstorPairs(5) } returns astorCards()

        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle["amount"] ?: 0 } returns 5
        viewModel = GameViewModel(useCases, savedStateHandle)
        delay(200)

        viewModel.state.test {
            val emission = awaitItem()
            assertContentEquals(
                gameCards(),
                emission.cards
            )
            advanceUntilIdle()
            viewModel.onEvent(GameEvent.CardFlipped(2))
            advanceUntilIdle()

            val emission2 = awaitItem()
            assertContentEquals(
                gameCards().map {
                    if (it.astorCard.id == "3") {
                        it.copy(isFlipped = true)
                    } else {
                        it
                    }
                },
                emission2.cards
            )

            viewModel.onEvent(GameEvent.CardFlipped(3))
            advanceUntilIdle()

            val emission3 = awaitItem()
            assertContentEquals(
                gameCards().map {
                    if (it.astorCard.id == "4" || it.astorCard.id == "3") {
                        it.copy(isFlipped = true)
                    } else {
                        it
                    }
                },
                emission3.cards
            )

            viewModel.onEvent(GameEvent.CardMismatched)
            advanceUntilIdle()

            val emission4 = awaitItem()
            assertContentEquals(
                gameCards(),
                emission4.cards
            )
        }
    }

    private fun astorCards(): List<AstorCard> = listOf(
        AstorCard(
            "1",
            1,
            "https://astorapi.co/api/v2/astor/1",
            ByteArray(0),
            "Astorbasaur"
        ),
        AstorCard(
            "2",
            2,
            "https://astorapi.co/api/v2/astor/2",
            ByteArray(0),
            "Astorsaur"
        ),
        AstorCard(
            "3",
            3,
            "https://astorapi.co/api/v2/astor/3",
            ByteArray(0),
            "Astorusaur"
        ),
        AstorCard(
            "4",
            4,
            "https://astorapi.co/api/v2/astor/4",
            ByteArray(0),
            "Astormander"
        ),
        AstorCard(
            "5",
            5,
            "https://astorapi.co/api/v2/astor/5",
            ByteArray(0),
            "Astormeleon"
        ),
    )

    private fun gameCards(): List<GameCard> = astorCards().map {
        GameCard(astorCard = it)
    }
}
