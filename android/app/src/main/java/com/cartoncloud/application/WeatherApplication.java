package com.cartoncloud.application;

import android.app.Application;

import com.cartoncloud.BuildConfig;
import com.cartoncloud.react.ViewPackager;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Application class to handle the ViewModule
 */
public class WeatherApplication extends Application implements ReactApplication {

    private ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            List<ReactPackage> packages = new ArrayList<>();
            packages.add(new MainReactPackage());
            packages.add(new ViewPackager());
            return packages;
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //SoLoader.init(this, /* native exopackage */ false)

    }
}
