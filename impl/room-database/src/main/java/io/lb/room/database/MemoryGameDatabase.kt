package io.lb.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.lb.room.database.dao.MemoryGameDao
import io.lb.room.database.model.AstorCardEntity
import io.lb.room.database.model.ScoreEntity

/**
 * The Room database for the memory game.
 */
@Database(
    version = 3,
    entities = [
        ScoreEntity::class,
        AstorCardEntity::class
    ],
    exportSchema = false
)
internal abstract class MemoryGameDatabase : RoomDatabase() {
    abstract val memoryGameDao: MemoryGameDao
}
