package com.cartoncloud.data

import com.cartoncloud.BuildConfig
import com.cartoncloud.model.WeatherInfo
import com.cartoncloud.utils.StubInterceptor
import com.google.gson.GsonBuilder

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Creates the retrofit adapter used to interact with the backend
 */

class MetaWeatherServiceFactory private constructor() {

  /**
   * Returns the http client used in the tests unit
   * @return
   */
  val okHttpClient: OkHttpClient

  init {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.USE_NETWORK_STUB) {
        builder.addInterceptor(StubInterceptor.instance)
    }
    okHttpClient = builder.build()
  }

  fun createRetrofitService(): MetaWeatherService {
    val gson = GsonBuilder()
            .registerTypeAdapter(WeatherInfo::class.java, WeatherGsonDeserializer())
            .create()
    val restAdapter = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(SERVICE_ENDPOINT)
            .client(okHttpClient)
            .build()
    return restAdapter.create(MetaWeatherService::class.java)
  }

  companion object {
    private val SERVICE_ENDPOINT = "https://www.metaweather.com/"

    val instance: MetaWeatherServiceFactory = MetaWeatherServiceFactory()
  }
}
