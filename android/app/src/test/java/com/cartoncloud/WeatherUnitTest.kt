package com.cartoncloud

import com.cartoncloud.data.MetaWeatherService
import com.cartoncloud.data.MetaWeatherServiceFactory
import com.cartoncloud.data.WeatherGsonDeserializer
import com.cartoncloud.helper.StubHelper
import com.cartoncloud.model.WeatherInfo
import com.cartoncloud.utils.StubInterceptor
import com.google.gson.GsonBuilder
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers
import io.appflate.restmock.utils.RequestMatchers.pathContains
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.*
import rx.observers.TestSubscriber




/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class WeatherUnitTest {

    private lateinit var mockWebServer : MockWebServer

    @Before
    fun setup() {

    }

    @Test
    fun getSuccessResponse() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse())

        StubHelper.simulateOneObjectResponse()
        val weatherService =
                MetaWeatherServiceFactory.instance.
                        createRetrofitService(mockWebServer.url("/").toString())
        val observableWeather = weatherService.getYesterdayWeather("11106", "2017/12/23")

        val testSubscriber = TestSubscriber<WeatherInfo>()
        observableWeather.subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        val weatherInfo = testSubscriber.onNextEvents

        mockWebServer.shutdown()
    }
}
