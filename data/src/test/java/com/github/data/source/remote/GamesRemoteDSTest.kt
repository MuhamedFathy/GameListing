package com.github.data.source.remote

import com.github.data.network.api.GamesAPI
import com.github.data.source.remote.GamesPlatforms.PLAY_STATION_4
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

private const val MOCK_SERVER_PORT = 8000

@RunWith(MockitoJUnitRunner::class)
class GamesRemoteDSTest {

  private val mockServer = MockWebServer()

  private lateinit var gamesAPI: GamesAPI
  private lateinit var gamesRemoteDS: GamesRemoteDS

  private val gamesSuccessJson by lazy {
    val json: String
    InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream("games_success.json")).apply {
      json = readText()
      close()
    }
    json
  }
  private val gamesErrorJson by lazy {
    val json: String
    InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream("games_fail.json")).apply {
      json = readText()
      close()
    }
    json
  }

  private val gameDetailsSuccessJson by lazy {
    val json: String
    InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream("game_details_success.json")).apply {
      json = readText()
      close()
    }
    json
  }


  private val gameDetailsErrorJson by lazy {
    val json: String
    InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream("game_details_fail.json")).apply {
      json = readText()
      close()
    }
    json
  }

  @Before fun setup() {
    mockServer.start(MOCK_SERVER_PORT)
    gamesAPI = Retrofit.Builder()
      .baseUrl(mockServer.url("/"))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(Gson()))
      .build()
      .create(GamesAPI::class.java)
    gamesRemoteDS = GamesRemoteDS(gamesAPI)
  }

  @Test fun `call games list api and then return success`() {
    mockServer.enqueue(MockResponse().setBody(gamesSuccessJson))
    gamesRemoteDS.getGames(pageNumber = 1, pageSize = 2, PLAY_STATION_4)
      .test()
      .awaitDone(2, SECONDS)
      .assertComplete()
      .assertValue { it[0].id != null }
  }

  @Test fun `call games list api and then return json syntax exception`() {
    mockServer.enqueue(MockResponse().setBody(gamesErrorJson))
    gamesRemoteDS.getGames(pageNumber = 1, pageSize = 2, PLAY_STATION_4)
      .test()
      .awaitDone(2, SECONDS)
      .assertError(JsonSyntaxException::class.java)
  }


  @Test fun `call game details api with id and then return success`() {
    mockServer.enqueue(MockResponse().setBody(gameDetailsSuccessJson))
    gamesRemoteDS.getGameDetails(3498L)
      .test()
      .awaitDone(2, SECONDS)
      .assertComplete()
      .assertValue { it.id != null }
  }

  @Test fun `call games details api with id and then return json syntax exception`() {
    mockServer.enqueue(MockResponse().setBody(gameDetailsErrorJson))
    gamesRemoteDS.getGameDetails(3498L)
      .test()
      .awaitDone(2, SECONDS)
      .assertError(JsonSyntaxException::class.java)
  }

  @After fun shutDown() {
    mockServer.shutdown()
  }
}
