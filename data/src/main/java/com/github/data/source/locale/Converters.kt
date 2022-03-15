package com.github.data.source.locale

import androidx.room.TypeConverter
import com.github.data.source.locale.entity.GenreDbEntity
import com.github.data.source.locale.entity.RatingDbEntity
import com.github.data.source.locale.entity.ScreenshotDbEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class RatingConverter {

  @TypeConverter fun fromJson(json: String?): List<RatingDbEntity> = Gson().fromJson(json, object : TypeToken<List<RatingDbEntity>>() {}.type)

  @TypeConverter fun toJson(ratings: List<RatingDbEntity>): String = Gson().toJson(ratings)
}

class ScreenshotConverter {

  @TypeConverter fun fromJson(json: String?): List<ScreenshotDbEntity> = Gson().fromJson(json, object : TypeToken<List<ScreenshotDbEntity>>() {}.type)

  @TypeConverter fun toJson(ratings: List<ScreenshotDbEntity>): String = Gson().toJson(ratings)
}

class GenreConverter {

  @TypeConverter fun fromJson(json: String?): List<GenreDbEntity> = Gson().fromJson(json, object : TypeToken<List<GenreDbEntity>>() {}.type)

  @TypeConverter fun toJson(ratings: List<GenreDbEntity>): String = Gson().toJson(ratings)
}
