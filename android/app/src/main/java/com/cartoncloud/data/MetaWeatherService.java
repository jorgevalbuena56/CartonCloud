package com.cartoncloud.data;

import com.cartoncloud.model.WeatherInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Retrofit Interface with the method used to retrieve information from the backend
 */
public interface MetaWeatherService {
    @GET("/api/location/{woeid}/{yesterday}/")
    Observable<WeatherInfo> getYesterdayWeather(@Path("woeid") String placeId,
                                                @Path(value = "yesterday", encoded = true)
                                                        String yesterdayDate);
}
