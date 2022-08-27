package com.github.data.source.remote

import com.github.data.extensions.sortDescending
import com.github.data.model.GamesResponse
import com.github.data.source.locale.entity.GameDbEntity
import com.github.data.source.locale.toDbEntity
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.InputStreamReader

/**
 * Authored by Mohamed Fathy on 26 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

@RunWith(MockitoJUnitRunner::class)
class GamesRxRemoteMediatorTest {

  private var gson: Gson = Gson()

  private val gamesSuccessJson by lazy {
    val json: String
    InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream("games_success.json")).apply {
      json = readText()
      close()
    }
    json
  }

  @Test fun `check if list sorted correctly and return success`() {
    val games: List<GameDbEntity> = gson.fromJson(gamesSuccessJson, GamesResponse::class.java).results?.map { it.toDbEntity() }!!
    assertEquals(true, games.sortDescending().zipWithNext { a, b -> a.released >= b.released }.all { it })
  }

  @Test fun `check if list not sorted and return success`() {
    val games: List<GameDbEntity> = gson.fromJson(gamesSuccessJson, GamesResponse::class.java).results?.map { it.toDbEntity() }!!
    assertEquals(true, games.zipWithNext { a, b -> a.released <= b.released }.all { it })
  }
}