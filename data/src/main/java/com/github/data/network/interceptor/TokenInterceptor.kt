package com.github.data.network.interceptor

import com.github.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class TokenInterceptor @Inject constructor() : Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain: Chain): Response {
    val request = chain.request()
    val url = request.url.newBuilder().addQueryParameter("key", BuildConfig.API_KEY).build()
    return chain.proceed(request.newBuilder().url(url).build())
  }
}