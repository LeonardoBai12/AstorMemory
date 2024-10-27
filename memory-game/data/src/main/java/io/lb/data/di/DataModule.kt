package io.lb.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.common.data.service.ClientService
import io.lb.common.data.service.DatabaseService
import io.lb.data.datasource.MemoryGameDataSource
import io.lb.data.repository.MemoryGameRepositoryImpl
import io.lb.domain.repository.MemoryGameRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object DataModule {
    @Provides
    fun providesDataSource(
        databaseService: DatabaseService,
        clientService: ClientService
    ): MemoryGameDataSource {
        return MemoryGameDataSource(
            databaseService,
            clientService,
        )
    }

    @Provides
    fun providesRepository(
        dataSource: MemoryGameDataSource
    ): MemoryGameRepository {
        return MemoryGameRepositoryImpl(dataSource)
    }
}
