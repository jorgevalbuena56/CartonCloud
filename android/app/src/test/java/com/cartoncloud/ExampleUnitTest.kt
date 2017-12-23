package com.cartoncloud

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.cartoncloud.data.DataManager
import com.cartoncloud.helper.StubHelper
import com.facebook.react.bridge.Callback
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleUnitTest {
    @Test
    fun getSingleResult() {
        StubHelper.simulateOneObjectResponse()
        DataManager.getYesterdayWeatherByCity("2017/04/03", Callback {

        })
    }
}
