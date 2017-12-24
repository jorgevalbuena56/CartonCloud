package com.cartoncloud.data

import android.util.Log
import com.cartoncloud.model.WeatherInfo
import com.cartoncloud.utils.ReactNativeJSONConverter
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.WritableNativeMap
import com.google.gson.Gson
import org.json.JSONObject
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription


/**
 * Data manager class used to retrieve the relevant information from the MetaWeather API
 */
const val SERVICE_WOEID_BRISBANE = "1100661"

object DataManager {
  private val SERVICE_ENDPOINT = "https://www.metaweather.com/"
  private val mWeatherService: MetaWeatherService =
          MetaWeatherServiceFactory.instance.createRetrofitService(SERVICE_ENDPOINT)
  /**
   * Method used to make and asynchronous call to the backend requesting the weather information
   * It uses a callback to notify the UI about the answer.
   *
   * @param weatherDate
   * @param statusCallback
   */
  fun getYesterdayWeatherByCity(weatherDate: String, statusCallback: Callback) {
    Log.d("DataManager", "Data received: " + weatherDate)
    val weatherInformation = mWeatherService.getYesterdayWeather(SERVICE_WOEID_BRISBANE, weatherDate)

    weatherInformation.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<WeatherInfo>() {
              override fun onCompleted() {

              }

              override fun onError(e: Throwable) {
                Log.d(this@DataManager.javaClass.canonicalName,
                      "Error getting information {$e.message}")

                statusCallback.invoke(WritableNativeMap().apply {
                    putInt("statusCode", -200)
                    putString("errorMessage", e.message)
                })
              }

              override fun onNext(weatherInfoResponse: WeatherInfo) {
                  val jsonStr = Gson().toJson(weatherInfoResponse)
                  val response = ReactNativeJSONConverter.
                          convertJsonToMap(JSONObject(jsonStr))
                  response.putInt("statusCode", 200)
                  statusCallback.invoke(response)
              }
            })
  }
}
