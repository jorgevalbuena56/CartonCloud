package com.cartoncloud

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import java.util.*

/**
 * Packager to provide ViewModule to React Instance Manager
 */
class ViewPackager : ReactPackage{
    override fun createNativeModules(reactContext: ReactApplicationContext?):
            MutableList<NativeModule> = mutableListOf(ViewModule(reactContext))

    override fun createViewManagers(reactContext: ReactApplicationContext?):
            MutableList<ViewManager<View, ReactShadowNode<*>>> = Collections.emptyList()
}