package io.lb.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.common.data.service.ClientService
import io.lb.data.repository.MemoryGameRepositoryImpl
import io.lb.domain.repository.MemoryGameRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object DataModule {
    @Provides
    fun providesRepository(
        service: ClientService
    ): MemoryGameRepository {
        return MemoryGameRepositoryImpl(service)
    }
}
