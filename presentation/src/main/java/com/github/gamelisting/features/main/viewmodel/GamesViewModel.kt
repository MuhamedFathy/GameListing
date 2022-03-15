package com.github.gamelisting.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.github.core.extensions.log
import com.github.domain.interactor.GetGamesUseCase
import com.github.gamelisting.features.main.viewmodel.uimodel.GameUIModel
import com.github.gamelisting.features.main.viewmodel.uimodel.toGameUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class GamesViewModel @Inject constructor(
  private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  private val _gamesLiveData = MutableLiveData<PagingData<GameUIModel>>()
  val gamesLiveData: LiveData<PagingData<GameUIModel>> = _gamesLiveData

  init {
    loadGames()
  }

  private fun loadGames() {
    getGamesUseCase.build()
      .cachedIn(viewModelScope)
      .subscribe({ gamePagingData -> _gamesLiveData.value = gamePagingData.map { it.toGameUIModel() } }, { it.log() })
      .addTo(compositeDisposable)
  }

  override fun onCleared() {
    compositeDisposable.clear()
  }
}