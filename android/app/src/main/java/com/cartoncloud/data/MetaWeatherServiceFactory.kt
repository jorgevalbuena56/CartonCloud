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
const val SERVICE_ENDPOINT = "https://www.metaweather.com/"

object MetaWeatherServiceFactory {

  private val okHttpClient: OkHttpClient

  init {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.USE_NETWORK_STUB) {
        if (BuildConfig.USE_ERROR_NETWORK_RESPONSE) {
            StubInterceptor.clearResponses()
            StubInterceptor.addErrorResponse()
        }
        builder.addInterceptor(StubInterceptor)
    }
    okHttpClient = builder.build()
  }

  fun createRetrofitService(): MetaWeatherService {
    val gson = GsonBuilder()
            .registerTypeAdapter(WeatherInfo::class.java, WeatherGsonDeserializer())
            .create()
    return Retrofit.Builder().apply {
           addCallAdapterFactory(RxJavaCallAdapterFactory.create())
           addConverterFactory(GsonConverterFactory.create(gson))
           baseUrl(SERVICE_ENDPOINT)
           client(okHttpClient)
    }.build().create(MetaWeatherService::class.java)
  }
}
