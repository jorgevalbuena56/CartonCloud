package com.cartoncloud

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Toast
import com.cartoncloud.utils.ReactInstantManagerHolder
import com.facebook.react.ReactRootView
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler


const val OVERLAY_PERMISSION_REQ_CODE = 10

class MainActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler {

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val reactRootView = ReactRootView(this)
        reactRootView.startReactApplication(
                ReactInstantManagerHolder.getInstance(application).mReactInstanceManager,
                "CartonCloudReactApp", null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + packageName))
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
            }
        }

        setContentView(reactRootView)
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "Permissions not Granted",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        ReactInstantManagerHolder
                .getInstance(application)
                .mReactInstanceManager.onBackPressed()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            ReactInstantManagerHolder
                    .getInstance(application)
                    .mReactInstanceManager.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        ReactInstantManagerHolder
                .getInstance(application)
                .mReactInstanceManager.onHostPause(this)
    }

    override fun onResume() {
        super.onResume()
        ReactInstantManagerHolder
                .getInstance(application)
                .mReactInstanceManager.onHostResume(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ReactInstantManagerHolder
                .getInstance(application)
                .mReactInstanceManager.onHostDestroy(this)
    }
}
