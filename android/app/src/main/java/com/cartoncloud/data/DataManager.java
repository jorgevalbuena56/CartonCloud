package com.cartoncloud.data;

import android.util.Log;

import com.cartoncloud.model.WeatherInfo;
import com.cartoncloud.react.ReactNativeJSONConverter;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Data manager class used to retrieve the relevant information from the MetaWeather API
 */
public class DataManager {
    public static final String SERVICE_WOEID_BRISBANE = "1100661";
    public static final String TAG = "DataManager";
    /**
     * Method used to make and asynchronous call to the backend requesting the weather information
     * It uses a callback to notify the UI about the answer.
     *
     * @param weatherDate
     * @param statusCallback
     */
    public static void getYesterdayWeatherByCity(String weatherDate, final Callback statusCallback) {
        MetaWeatherService mWeatherService = MetaWeatherServiceFactory.getInstance().createRetrofitService();
        Observable<WeatherInfo> weatherInformation =
                mWeatherService.getYesterdayWeather(SERVICE_WOEID_BRISBANE, weatherDate);

        weatherInformation.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherInfo>() {
                    @Override
                    public void call(WeatherInfo weatherInfoResponse) {
                        String jsonStr = new Gson().toJson(weatherInfoResponse);
                        WritableMap response;
                        try {
                            response = ReactNativeJSONConverter.
                                    convertJsonToMap(new JSONObject(jsonStr));
                            response.putInt("statusCode", 200);
                            statusCallback.invoke(response);
                        } catch (JSONException error) {
                            statusCallback.invoke(buildErrorResponse(error.getMessage()));
                        }
                    }
                }, new Action1<Throwable>() {

                    @Override
                    public void call(Throwable error) {
                        statusCallback.invoke(buildErrorResponse(error.getMessage()));
                    }
                });
    }

    /**
     * Helper methos to build an error response
     * @param errorMessage
     */
    private static WritableMap buildErrorResponse(String errorMessage) {
        Log.d(TAG, "Error getting information: " + errorMessage);
        WritableMap map = new WritableNativeMap();
        map.putInt("statusCode", -200);
        map.putString("errorMessage", errorMessage);
        return map;
    }
}
