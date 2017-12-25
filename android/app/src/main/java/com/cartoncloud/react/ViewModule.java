package com.cartoncloud.react;

import com.cartoncloud.data.DataManager;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * React Module to handle button click
 */
public class ViewModule extends ReactContextBaseJavaModule {

    public ViewModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    @Override
    public String getName() {
        return "ViewModule";
    }

    @ReactMethod
    public void onPressGetWeather(Callback statusCallback) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DataManager.getYesterdayWeatherByCity(dateFormat.format(cal.getTime()), statusCallback);
    }
}
