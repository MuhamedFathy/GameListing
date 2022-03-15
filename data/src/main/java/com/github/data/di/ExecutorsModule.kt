package com.github.data.di

import com.github.data.di.qualifier.IOThread
import com.github.data.di.qualifier.UIThread
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
class ExecutorsModule {

  @Singleton
  @Provides
  @IOThread fun provideIOExecutor(): Scheduler = Schedulers.io()

  @Singleton
  @Provides
  @UIThread fun provideMainThreadExecutor(): Scheduler = AndroidSchedulers.mainThread()
}