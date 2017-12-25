package com.cartoncloud.data;

import com.cartoncloud.BuildConfig;
import com.cartoncloud.model.WeatherInfo;
import com.cartoncloud.utils.StubInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Factory to create and initialize the http client
 */

public class MetaWeatherServiceFactory {
    private static final String SERVICE_ENDPOINT = "https://www.metaweather.com/";
    private static OkHttpClient okHttpClient;
    private static MetaWeatherServiceFactory ourInstance;

    public static MetaWeatherServiceFactory getInstance() {
        if (ourInstance == null) {
            ourInstance = new MetaWeatherServiceFactory();
            initHttpClient();
        }
        return ourInstance;
    }

    /**
     * Initializes the Http Client for the first time
     */
    private static void initHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        StubInterceptor interceptor = new StubInterceptor();
        if (BuildConfig.USE_NETWORK_STUB) {
            if (BuildConfig.USE_ERROR_NETWORK_RESPONSE) {
                interceptor.clearResponses();
                interceptor.addErrorResponse();
            }
            builder.addInterceptor(interceptor);
        }
        okHttpClient = builder.build();
    }

    private MetaWeatherServiceFactory() {

    }

    /**
     *  Configure and initialize the retrofit service
     * @return
     */
    public MetaWeatherService createRetrofitService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherInfo.class, new WeatherGsonDeserializer()).create();
        return new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                           .addConverterFactory(GsonConverterFactory.create(gson))
                           .baseUrl(SERVICE_ENDPOINT)
                           .client(okHttpClient).build().create(MetaWeatherService.class);
    }
}
