package com.github.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import androidx.paging.rxjava2.mapAsync
import com.github.data.model.mappers.toEntity
import com.github.data.source.locale.GamesDatabase
import com.github.data.source.locale.toEntity
import com.github.data.source.remote.GamesRemoteDS
import com.github.data.source.remote.GamesRxRemoteMediator
import com.github.domain.entity.GameEntity
import com.github.domain.repository.GamesRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@ExperimentalPagingApi
class GamesRepositoryImpl @Inject constructor(
  private val gamesRxRemoteMediator: GamesRxRemoteMediator,
  private val gamesRemoteDS: GamesRemoteDS,
  private val database: GamesDatabase
) : GamesRepository {

  override fun getGames(): Flowable<PagingData<GameEntity>> {
    return Pager(
      config = PagingConfig(
        pageSize = 40,
        enablePlaceholders = true,
        prefetchDistance = 5,
        initialLoadSize = 40
      ),
      remoteMediator = gamesRxRemoteMediator,
      pagingSourceFactory = { database.gamesDao().selectAll() }
    ).flowable
      .map { it.mapAsync { gameDbEntity -> Single.just(gameDbEntity.toEntity()) } }
  }

  override fun getGamDetails(gameId: Long): Single<GameEntity> {
    return gamesRemoteDS.getGameDetails(gameId)
      .map { gameDetailsResponse ->
        val gameDbEntity = database.gamesDao().select(gameId)
        gameDetailsResponse.toEntity(gameDbEntity?.screenshots?.map { it.toEntity() } ?: emptyList())
      }
  }
}