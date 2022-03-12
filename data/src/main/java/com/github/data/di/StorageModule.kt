package com.github.data.di

import android.app.Application
import androidx.room.Room
import com.github.data.source.locale.GamesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

  @Singleton
  @Provides fun provideGamesDatabase(
    @ApplicationContext context: Application
  ) = Room.databaseBuilder(context, GamesDatabase::class.java, "games_rawg").build()
}