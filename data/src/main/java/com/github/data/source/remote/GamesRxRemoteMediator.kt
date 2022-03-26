package com.github.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingSource.LoadResult.Page
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

private const val INITIAL_PAGE_NUMBER = 1
private const val INVALID_PAGE = -1

@ExperimentalPagingApi
class GamesRxRemoteMediator @Inject constructor(
  private val gamesRemoteDS: GamesRemoteDS,
  private val gamesDatabase: GamesDatabase,
  @IOThread private val ioThread: Scheduler
) : RxRemoteMediator<Int, GameDbEntity>() {

  private val oldGames = mutableListOf<GameDbEntity>()

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
        remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_NUMBER
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
        .zipWith(gamesRemoteDS.getGames(page, state.config.pageSize, platform = XBOX_ONE)) { ps, xbox ->
          mergeListsToCurrentData(
            ps,
            xbox,
            state.pages
          )
        }
        .map { insertToDatabase(page, loadType, it) }
        .map<MediatorResult> { endOfPagination -> MediatorResult.Success(endOfPaginationReached = endOfPagination) }
        .onErrorReturn { MediatorResult.Error(it) }
    }
  }

  private fun mergeListsToCurrentData(psGames: List<Result>, xboxGames: List<Result>, pages: List<Page<Int, GameDbEntity>>): List<GameDbEntity> {
    val mergedGames = divideToChunks(
      psGames.map { it.toDbEntity() }.plus(xboxGames.map { it.toDbEntity() })
        .distinctBy { it.id }.filter { it.backgroundImage.isNotBlank() }
    )
    oldGames.clear()
    pages.forEach { oldGames.plus(it.data) }
    return divideToChunks(oldGames.plus(mergedGames)).distinctBy { it.id }
  }

  private fun insertToDatabase(page: Int, loadType: LoadType, games: List<GameDbEntity>): Boolean {
    val endOfPagination = games.isEmpty()
    gamesDatabase.runInTransaction {
      if (loadType == REFRESH) {
        gamesDatabase.gamesRemoteKeysDao().clearRemoteKeys()
        gamesDatabase.gamesDao().clearAll()
      }

      val prevKey = if (page == INITIAL_PAGE_NUMBER) null else page.minus(1)
      val nextKey = if (endOfPagination) null else page.plus(1)

      val keys = games.map { GameDbRemoteKeysEntity(id = it.id, prevKey = prevKey, nextKey = nextKey) }
      gamesDatabase.gamesRemoteKeysDao().insertAll(keys)
      gamesDatabase.gamesDao().insertAll(games)
    }
    return endOfPagination
  }

  private fun divideToChunks(list: List<GameDbEntity>): List<GameDbEntity> {
    if (list.count() <= 1) return list
    val middle = list.count().div(2)
    val left = list.subList(0, middle)
    val right = list.subList(middle, list.count())
    return merge(divideToChunks(left), divideToChunks(right))
  }

  private fun merge(left: List<GameDbEntity>, right: List<GameDbEntity>): List<GameDbEntity> {
    var indexLeft = 0
    var indexRight = 0
    val newList = mutableListOf<GameDbEntity>()

    while ((indexLeft < left.count()).and(indexRight < right.count())) {
      if (left[indexLeft].released >= right[indexRight].released) {
        newList.add(left[indexLeft])
        indexLeft++
      } else {
        newList.add(right[indexRight])
        indexRight++
      }
    }

    while (indexLeft < left.count()) {
      newList.add(left[indexLeft])
      indexLeft++
    }

    while (indexRight < right.count()) {
      newList.add(right[indexRight])
      indexRight++
    }
    return newList
  }
}
