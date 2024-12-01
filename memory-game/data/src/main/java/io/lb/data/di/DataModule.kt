package io.lb.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
    ): MemoryGameDataSource {
        return MemoryGameDataSource(databaseService)
    }

    @Provides
    fun providesRepository(
        @ApplicationContext context: Context,
        dataSource: MemoryGameDataSource
    ): MemoryGameRepository {
        return MemoryGameRepositoryImpl(context, dataSource)
    }
}
