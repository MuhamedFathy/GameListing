package com.github.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.github.data.di.qualifier.IOThread
import com.github.data.model.Result
import com.github.data.source.locale.GamesDatabase
import com.github.data.source.locale.entity.GameDbEntity
import com.github.data.source.locale.entity.GameDbRemoteKeysEntity
import com.github.data.source.locale.toDbEntity
import com.github.data.source.remote.GamesPlatforms.PLAY_STATION_4
import com.github.data.source.remote.GamesPlatforms.XBOX_ONE
import io.reactivex.Scheduler
import io.reactivex.Single
import java.io.InvalidObjectException
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

private const val INVALID_PAGE = -1

@ExperimentalPagingApi
class GamesRxRemoteMediator @Inject constructor(
  private val gamesRemoteDS: GamesRemoteDS,
  private val gamesDatabase: GamesDatabase,
  @IOThread private val ioThread: Scheduler
) : RxRemoteMediator<Int, GameDbEntity>() {

  override fun loadSingle(loadType: LoadType, state: PagingState<Int, GameDbEntity>): Single<MediatorResult> {
    return Single.just(loadType)
      .subscribeOn(ioThread)
      .map { toReturnPageNumber(loadType, state) }
      .flatMap { toRequestNetworkDataAndSaveToLocale(it, loadType, state) }
      .onErrorReturn { MediatorResult.Error(throwable = it) }
  }

  private fun toReturnPageNumber(loadType: LoadType, state: PagingState<Int, GameDbEntity>): Int {
    val remoteKeys: GameDbRemoteKeysEntity?
    return when (loadType) {
      REFRESH -> {
        remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
        remoteKeys?.nextKey?.minus(1) ?: 1
      }
      PREPEND -> {
        remoteKeys = getRemoteKeyForFirstItem(state) ?: throw InvalidObjectException("Room remote key for first item == null")
        remoteKeys.prevKey ?: INVALID_PAGE
      }
      APPEND -> {
        remoteKeys = getRemoteKeyForLastItem(state) ?: throw InvalidObjectException("Room remote key for last item == null")
        remoteKeys.nextKey ?: INVALID_PAGE
      }
    }
  }

  private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GameDbEntity>): GameDbRemoteKeysEntity? {
    return state.anchorPosition?.let { position ->
      state.closestItemToPosition(position)?.id?.let { id -> gamesDatabase.gamesRemoteKeysDao().remoteKeyByGameId(id) }
    }
  }

  private fun getRemoteKeyForFirstItem(state: PagingState<Int, GameDbEntity>): GameDbRemoteKeysEntity? {
    return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { game ->
      gamesDatabase.gamesRemoteKeysDao().remoteKeyByGameId(game.id)
    }
  }

  private fun getRemoteKeyForLastItem(state: PagingState<Int, GameDbEntity>): GameDbRemoteKeysEntity? {
    return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { game ->
      gamesDatabase.gamesRemoteKeysDao().remoteKeyByGameId(game.id)
    }
  }

  private fun toRequestNetworkDataAndSaveToLocale(page: Int, loadType: LoadType, state: PagingState<Int, GameDbEntity>): Single<MediatorResult> {
    return if (page == INVALID_PAGE) {
      Single.just(MediatorResult.Success(endOfPaginationReached = true))
    } else {
      gamesRemoteDS.getGames(page, state.config.pageSize, platform = PLAY_STATION_4)
        .zipWith(gamesRemoteDS.getGames(page, state.config.pageSize, platform = XBOX_ONE)) { psGames, xboxGames -> collectList2(psGames, xboxGames) }
        .map { insertToDatabase(page, endOfPagination = it.isEmpty(), loadType, it) }
        .map<MediatorResult> { endOfPagination -> MediatorResult.Success(endOfPaginationReached = endOfPagination) }
        .onErrorReturn { MediatorResult.Error(it) }
    }
  }

  private fun collectList2(psGames: List<Result>, xboxGames: List<Result>): List<GameDbEntity> {
    val mutablePSEntityGames = psGames.map { it.toDbEntity() }.toMutableList()
    val xboxEntityGames = xboxGames.map { it.toDbEntity() }
    mutablePSEntityGames.addAll(xboxEntityGames)
    return mutablePSEntityGames.distinctBy { it.id }.filter { it.backgroundImage.isNotBlank() }.sortedByDescending { it.released }
  }

  private fun insertToDatabase(page: Int, endOfPagination: Boolean, loadType: LoadType, games: List<GameDbEntity>): Boolean {
    gamesDatabase.runInTransaction {
      if (loadType == REFRESH) {
        gamesDatabase.gamesRemoteKeysDao().clearRemoteKeys()
        gamesDatabase.gamesDao().clearAll()
      }

      val prevKey = if (page == 1) null else page.minus(1)
      val nextKey = if (endOfPagination) null else page.plus(1)

      val keys = games.map { GameDbRemoteKeysEntity(id = it.id, prevKey = prevKey, nextKey = nextKey) }
      gamesDatabase.gamesRemoteKeysDao().insertAll(keys)
      gamesDatabase.gamesDao().insertAll(games)
    }
    return endOfPagination
  }
}
