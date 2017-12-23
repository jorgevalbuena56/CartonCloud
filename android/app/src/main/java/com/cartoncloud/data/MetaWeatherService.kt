package com.cartoncloud.data

import com.cartoncloud.model.WeatherInfo

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


/**
 * Retrofit Interface with the method used to retrieve information from the backend
 */

interface MetaWeatherService {

  @GET("/api/location/{woeid}/{yesterday}/")
  fun getYesterdayWeather(@Path("woeid") placeId: String,
                          @Path("yesterday", encoded = true) yesterdayDate: String): Observable<WeatherInfo>


}
