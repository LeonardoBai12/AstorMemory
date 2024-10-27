package io.lb.room.database.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.common.data.service.DatabaseService
import io.lb.room.database.MemoryGameDatabase
import io.lb.room.database.service.DatabaseServiceImpl

@Module
@InstallIn(ViewModelComponent::class)
internal object DatabaseModule {
    @Provides
    fun providesDatabase(app: Application): MemoryGameDatabase {
        return Room.databaseBuilder(
            app,
            MemoryGameDatabase::class.java,
            "lbmeals.db"
        ).build()
    }

    @Provides
    fun providesDatabaseService(database: MemoryGameDatabase): DatabaseService {
        return DatabaseServiceImpl(database.memoryGameDao)
    }
}
