package com.github.data.source.locale

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.github.data.source.locale.entity.GamesDbEntity
import com.github.data.source.locale.entity.GamesDbRemoteKeysEntity

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Dao
interface GamesDao {

  @Insert(onConflict = REPLACE) fun insertAll(games: List<GamesDbEntity>)

  @Query("SELECT * FROM games") fun selectAll(): PagingSource<Int, GamesDbEntity>

  @Query("DELETE FROM games") fun clearAll()
}

@Dao
interface GamesRemoteKeysDao {

  @Insert(onConflict = REPLACE) fun insertAll(remoteKeys: List<GamesDbRemoteKeysEntity>)

  @Query("SELECT * FROM games_remote_keys WHERE id = :gameId") fun remoteKeyByGameId(gameId: Long): GamesDbRemoteKeysEntity?

  @Query("DELETE FROM games_remote_keys") fun clearRemoteKeys()
}
