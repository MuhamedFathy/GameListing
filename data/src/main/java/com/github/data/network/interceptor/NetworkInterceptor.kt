package com.github.data.network.interceptor

import android.content.Context
import com.github.data.extensions.isNetworkAvailable
import com.github.domain.exception.NetworkNotAvailableException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class NetworkInterceptor @Inject constructor(
  @ApplicationContext private val context: Context
) : Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain: Chain): Response {
    val request = chain.request()
    if (context.isNetworkAvailable().not()) throw NetworkNotAvailableException()
    return chain.proceed(request)
  }
}
