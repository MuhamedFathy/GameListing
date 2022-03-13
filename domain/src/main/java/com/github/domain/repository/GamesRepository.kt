package com.github.domain.repository

import androidx.paging.PagingData
import com.github.domain.entity.GameEntity
import io.reactivex.Flowable

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
interface GamesRepository {

  fun getGames(): Flowable<PagingData<GameEntity>>
}