package com.github.data.source.locale.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Entity(tableName = "games_remote_keys")
data class GameDbRemoteKeysEntity(
  @PrimaryKey(autoGenerate = false) val id: Long,
  val prevKey: Int?,
  val nextKey: Int?
)
