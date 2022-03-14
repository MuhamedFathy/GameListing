package com.github.gamelisting.extension

import androidx.lifecycle.MutableLiveData
import com.github.gamelisting.resource.Resource
import com.github.gamelisting.resource.ResourceState.ERROR
import com.github.gamelisting.resource.ResourceState.LOADING
import com.github.gamelisting.resource.ResourceState.SUCCESS

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
fun <T> MutableLiveData<Resource<T>>.setLoading() {
  value = Resource(LOADING, value?.data)
}

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T) {
  value = Resource(SUCCESS, data)
}

fun <T> MutableLiveData<Resource<T>>.setError() {
  value = Resource(ERROR, value?.data)
}
