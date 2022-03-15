package com.github.gamelisting.features.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.core.extensions.log
import com.github.data.di.qualifier.IOThread
import com.github.data.di.qualifier.UIThread
import com.github.domain.interactor.GetGameDetailsUseCase
import com.github.gamelisting.extension.setError
import com.github.gamelisting.extension.setLoading
import com.github.gamelisting.extension.setSuccess
import com.github.gamelisting.features.details.viewmodel.uimodel.GameDetailsUIModel
import com.github.gamelisting.features.details.viewmodel.uimodel.toGameDetailsUIModel
import com.github.gamelisting.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@HiltViewModel
class GameDetailsViewModel @Inject constructor(
  private val gameDetailsUseCase: GetGameDetailsUseCase,
  @IOThread private val ioThread: Scheduler,
  @UIThread private val uiThread: Scheduler
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  private val _gameDetailsLivedata = MutableLiveData<Resource<GameDetailsUIModel>>()
  val gameDetailsLivedata get() = _gameDetailsLivedata as LiveData<Resource<GameDetailsUIModel>>

  fun loadGameDetails(gameId: Long) {
    gameDetailsUseCase.build(gameId)
      .subscribeOn(ioThread)
      .doOnSubscribe { _gameDetailsLivedata.setLoading() }
      .observeOn(uiThread)
      .subscribe({ _gameDetailsLivedata.setSuccess(it.toGameDetailsUIModel()) }, {
        _gameDetailsLivedata.setError()
        it.log()
      }).addTo(compositeDisposable)
  }

  override fun onCleared() {
    compositeDisposable.clear()
  }
}