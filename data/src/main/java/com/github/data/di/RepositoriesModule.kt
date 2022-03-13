package com.github.data.di

import androidx.paging.ExperimentalPagingApi
import com.github.data.repository.GamesRepositoryImpl
import com.github.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

  @ExperimentalPagingApi
  @Binds abstract fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository
}