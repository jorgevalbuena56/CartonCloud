package com.cartoncloud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.cartoncloud.react.ReactInstantManagerHolder;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

/**
 * Main Activity that integrates the react native framework into android native
 */

public class MainActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    public static int OVERLAY_PERMISSION_REQ_CODE = 10;

    @SuppressLint("InlinedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReactRootView reactRootView = new ReactRootView(this);
        reactRootView.startReactApplication(
                ReactInstantManagerHolder.getInstance(getApplication()),
                "CartonCloudReactApp", null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }

        setContentView(reactRootView);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "Permissions not Granted",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        ReactInstantManagerHolder
                .getInstance(getApplication()).onBackPressed();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            ReactInstantManagerHolder
                    .getInstance(getApplication())
                    .showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        ReactInstantManagerHolder
                .getInstance(getApplication())
                .onHostPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ReactInstantManagerHolder
                .getInstance(getApplication())
                .onHostResume(this, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ReactInstantManagerHolder
                .getInstance(getApplication())
                .onHostDestroy(this);
    }
}
