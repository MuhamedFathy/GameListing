package com.github.data.source.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.data.source.locale.entity.GamesDbEntity
import com.github.data.source.locale.entity.GamesDbRemoteKeysEntity

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Database(
  entities = [GamesDbEntity::class, GamesDbRemoteKeysEntity::class],
  version = 1,
  exportSchema = false
)
@TypeConverters(RatingConverter::class, ScreenshotConverter::class, GenreConverter::class)
abstract class GamesDatabase : RoomDatabase() {

  abstract fun gamesDao(): GamesDao

  abstract fun gamesRemoteKeysDao(): GamesRemoteKeysDao
}