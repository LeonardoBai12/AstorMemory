package io.lb.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.lb.room.database.dao.MemoryGameDao
import io.lb.room.database.model.PokemonCardEntity
import io.lb.room.database.model.ScoreEntity

/**
 * The Room database for the memory game.
 */
@Database(
    version = 2,
    entities = [
        ScoreEntity::class,
        PokemonCardEntity::class
    ],
    exportSchema = false
)
internal abstract class MemoryGameDatabase : RoomDatabase() {
    abstract val memoryGameDao: MemoryGameDao
}
