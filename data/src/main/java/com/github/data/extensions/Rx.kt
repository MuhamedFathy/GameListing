package com.github.data.extensions

import com.github.domain.exception.DataRetrievingFailException
import com.github.domain.exception.NoDataException
import io.reactivex.Single
import retrofit2.Response

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
fun <T> Single<Response<T>>.parseResponse(): Single<T> {
  return flatMap { response ->
    if (response.isSuccessful) {
      if (response.body() != null) Single.just(response.body()) else Single.error(NoDataException())
    } else {
      Single.error(DataRetrievingFailException())
    }
  }
}