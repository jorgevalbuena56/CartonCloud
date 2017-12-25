package com.cartoncloud.react;

import android.app.Application;

import com.facebook.react.BuildConfig;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;

/**
 * Singleton to retrieve in a thread-afe way a unique instance of ReactiveInstantManager
 */

public class ReactInstantManagerHolder {
    private static ReactInstanceManager mReactInstanceManager;

    private ReactInstantManagerHolder(){

    }

    public static ReactInstanceManager getInstance(Application application) {
        if (mReactInstanceManager == null) {
            mReactInstanceManager = ReactInstanceManager.builder()
                    .setApplication(application)
                    .setBundleAssetName("index.android.bundle")
                    .setJSMainModulePath("index")
                    .addPackage(new MainReactPackage())
                    .addPackage(new ViewPackager())
                    .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setInitialLifecycleState(LifecycleState.RESUMED).build();
        }
        return mReactInstanceManager;
    }
}
