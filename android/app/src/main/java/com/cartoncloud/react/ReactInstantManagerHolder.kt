package com.cartoncloud.react

import android.app.Application
import com.cartoncloud.BuildConfig
import com.cartoncloud.utils.SingletonHolder
import com.facebook.react.ReactInstanceManager
import com.facebook.react.common.LifecycleState
import com.facebook.react.shell.MainReactPackage

/**
 * Singleton to retrieve in a thread-afe way a unique instance of ReactiveInstantManager
 */
class ReactInstantManagerHolder private constructor(application: Application) {
    var mReactInstanceManager : ReactInstanceManager = ReactInstanceManager.builder().apply {
        setApplication(application)
        setBundleAssetName("index.android.bundle")
        setJSMainModulePath("index")
        addPackage(MainReactPackage())
        addPackage(ViewPackager())
        setUseDeveloperSupport(BuildConfig.DEBUG)
        setInitialLifecycleState(LifecycleState.RESUMED)
    }.build()

    companion object : SingletonHolder<ReactInstantManagerHolder, Application>(::ReactInstantManagerHolder)

}