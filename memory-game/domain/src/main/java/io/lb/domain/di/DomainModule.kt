package io.lb.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.domain.repository.MemoryGameRepository
import io.lb.domain.usecases.CalculateScoreUseCase
import io.lb.domain.usecases.GetPokemonPairsUseCase
import io.lb.domain.usecases.GetScoresByAmountUseCase
import io.lb.domain.usecases.GetScoresUseCase
import io.lb.domain.usecases.MemoryGameUseCases
import io.lb.domain.usecases.SaveScoreUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun providesUseCases(repository: MemoryGameRepository): MemoryGameUseCases {
        return MemoryGameUseCases(
            getScoresUseCase = GetScoresUseCase(repository),
            getScoresByAmountUseCase = GetScoresByAmountUseCase(repository),
            saveScoreUseCase = SaveScoreUseCase(repository),
            getMemoryGameUseCase = GetPokemonPairsUseCase(repository),
            calculateScoreUseCase = CalculateScoreUseCase()
        )
    }
}
