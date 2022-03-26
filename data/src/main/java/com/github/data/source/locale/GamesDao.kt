package com.github.data.source.locale

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.github.data.source.locale.entity.GameDbEntity
import com.github.data.source.locale.entity.GameDbRemoteKeysEntity

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Dao
interface GamesDao {

  @Insert(onConflict = REPLACE) fun insertAll(games: List<GameDbEntity>)

  @Query("SELECT * FROM games ORDER BY released DESC") fun selectAll(): PagingSource<Int, GameDbEntity>

  @Query("SELECT * FROM games WHERE id = :gameId") fun select(gameId: Long): GameDbEntity?

  @Query("DELETE FROM games") fun clearAll()
}

@Dao
interface GamesRemoteKeysDao {

  @Insert(onConflict = REPLACE) fun insertAll(remoteKeys: List<GameDbRemoteKeysEntity>)

  @Query("SELECT * FROM games_remote_keys WHERE id = :gameId") fun remoteKeyByGameId(gameId: Long): GameDbRemoteKeysEntity?

  @Query("DELETE FROM games_remote_keys") fun clearRemoteKeys()
}
