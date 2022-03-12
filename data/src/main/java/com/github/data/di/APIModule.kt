package com.github.data.di

import com.github.data.network.api.GamesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class APIModule {

  @Singleton
  @Provides fun provideGamesAPI(retrofit: Retrofit): GamesAPI = retrofit.create(GamesAPI::class.java)
}
