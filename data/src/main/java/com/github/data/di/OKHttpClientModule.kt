package com.github.data.di

import com.github.data.BuildConfig
import com.github.data.network.interceptor.NetworkInterceptor
import com.github.data.network.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
class OKHttpClientModule {

  @Singleton
  @Provides fun provideOkHttpClient(
    tokenInterceptor: TokenInterceptor,
    loggingInterceptor: HttpLoggingInterceptor,
    networkInterceptor: NetworkInterceptor
  ): OkHttpClient = Builder()
    .connectTimeout(20, SECONDS)
    .readTimeout(30, SECONDS)
    .writeTimeout(20, SECONDS)
    .retryOnConnectionFailure(true)
    .addInterceptor(tokenInterceptor)
    .addInterceptor(loggingInterceptor)
    .addInterceptor(networkInterceptor)
    .build()

  @Singleton
  @Provides fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply { setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE) }
}
