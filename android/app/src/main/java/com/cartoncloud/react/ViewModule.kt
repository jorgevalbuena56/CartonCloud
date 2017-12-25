package com.cartoncloud.react

import com.cartoncloud.data.DataManager
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.text.SimpleDateFormat
import java.util.*


/**
 * React Module to handle button click
 */
class ViewModule(reactContext: ReactApplicationContext?) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String = "ViewModule"

    @ReactMethod
    fun onPressGetWeather(statusCallback: Callback) {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        DataManager.getYesterdayWeatherByCity(dateFormat.format(cal.time), statusCallback)
    }
}