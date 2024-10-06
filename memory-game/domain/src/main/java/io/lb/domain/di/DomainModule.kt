package io.lb.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.domain.repository.MemoryGameRepository
import io.lb.domain.use_cases.GetPokemonPairsUseCase
import io.lb.domain.use_cases.GetScoresUseCase
import io.lb.domain.use_cases.MemoryGameUseCases
import io.lb.domain.use_cases.SaveScoreUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun providesUseCases(repository: MemoryGameRepository): MemoryGameUseCases {
        return MemoryGameUseCases(
            getScoresUseCase = GetScoresUseCase(repository),
            saveScoreUseCase = SaveScoreUseCase(repository),
            getMemoryGameUseCase = GetPokemonPairsUseCase(repository),
        )
    }
}
