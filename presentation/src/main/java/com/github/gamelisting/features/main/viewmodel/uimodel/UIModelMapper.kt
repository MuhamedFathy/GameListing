package com.github.gamelisting.features.main.viewmodel.uimodel

import com.github.domain.entity.GameEntity

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

fun GameEntity.toGameUIModel() = GameUIModel(
  id = id,
  name = name,
  poster = backgroundImage,
  rating = rating.toString(),
  releaseDate = released
)