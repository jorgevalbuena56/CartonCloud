package com.cartoncloud

import com.cartoncloud.data.MetaWeatherService
import com.cartoncloud.data.WeatherGsonDeserializer
import com.cartoncloud.model.WeatherInfo
import com.google.gson.GsonBuilder
import junit.framework.Assert
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.observers.TestSubscriber


/**
 * Unit Test for error, one result only and multiple results.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class WeatherUnitTest {

    private lateinit var mockWebServer : MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
    }


    @Test
    fun justOneResultSuccessResponse() {
        mockWebServer.start()
        val responseInputStream =
                this.javaClass.classLoader.getResourceAsStream("one_weather_response.json")
        val jsonResponseStr = java.util.Scanner(responseInputStream).useDelimiter("\\A")
        val mockResponse = MockResponse().apply {
            addHeader("Content-Type", "application/json; charset=utf-8")
        }.setBody(jsonResponseStr.next())

        mockWebServer.enqueue(mockResponse)

        val weatherService = createTestRetrofitService()
        val observableWeather =
                weatherService.getYesterdayWeather("1100661", "2017/12/23")
        val testSubscriber = TestSubscriber<WeatherInfo>()
        observableWeather.subscribe(testSubscriber)

        val expectedWeatherInfo = WeatherInfo().apply {
            id = 2118123520
            weatherStateName = "Light Cloud"
            weatherStateAbbr = "lc"
            windDirectionCompass = "NNE"
            created = "2017-12-20T11:52:00.533560Z"
            applicableDate = "2017-12-20"
            minTemp = 21.534
            maxTemp = 30.921999999999997
            theTemp = 30.09
            windSpeed = 8.007244799002624
            windDirection = 26.871354291294015
            airPressure = 1011.85
            humidity = 56
            visibility = 12.792168734589994
            predictability = 70

        }
        testSubscriber.assertNoErrors()
        //every single value has to be the same as the expected information
        testSubscriber.assertValue(expectedWeatherInfo)
        mockWebServer.shutdown()
    }

    @Test
    fun moreThanOneResultSuccessResponse() {
        mockWebServer.start()
        val responseInputStream =
                this.javaClass.classLoader.getResourceAsStream("full_response.json")
        val jsonResponseStr = java.util.Scanner(responseInputStream).useDelimiter("\\A")
        val mockResponse = MockResponse().apply {
            addHeader("Content-Type", "application/json; charset=utf-8")
        }.setBody(jsonResponseStr.next())

        mockWebServer.enqueue(mockResponse)

        val weatherService = createTestRetrofitService()
        val observableWeather =
                weatherService.getYesterdayWeather("1100661", "2017/12/23")
        val testSubscriber = TestSubscriber<WeatherInfo>()
        observableWeather.subscribe(testSubscriber)

        val expectedWeatherInfo = WeatherInfo().apply {
            id = 239075328
            weatherStateName = "Showers"
            weatherStateAbbr = "s"
            windDirectionCompass = "NE"
            created = "2017-12-11T14:52:24.080960Z"
            applicableDate = "2017-12-20"
            minTemp = 20.03
            maxTemp = 28.9675
            theTemp = 24.3
            windSpeed = 7.7333538800074235
            windDirection = 45.7254819657352
            airPressure = 1006.99
            humidity = 54
            visibility = null
            predictability = 73

        }
        testSubscriber.assertNoErrors()
        //every single value has to be the same as the expected information
        testSubscriber.assertValue(expectedWeatherInfo)
        mockWebServer.shutdown()
    }

    @Test
    fun errorResponse() {
        mockWebServer.start()
        val mockResponse = MockResponse().setResponseCode(-200).setBody("")

        mockWebServer.enqueue(mockResponse)

        val weatherService = createTestRetrofitService()
        val observableWeather =
                weatherService.getYesterdayWeather("1100661", "2017/12/23")
        val testSubscriber = TestSubscriber<WeatherInfo>()
        observableWeather.subscribe(testSubscriber)

        Assert.assertNotNull(testSubscriber.onErrorEvents)
        mockWebServer.shutdown()
    }

    /**
     * Function to create the retrofit service without using interceptor and getting the path from
     * the mock server
     */
    private fun createTestRetrofitService() : MetaWeatherService {
        val gson = GsonBuilder()
                .registerTypeAdapter(WeatherInfo::class.java, WeatherGsonDeserializer())
                .create()
        return Retrofit.Builder().apply {
            addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create(gson))
            baseUrl(mockWebServer.url("/").toString())
            client(OkHttpClient())
        }.build().create(MetaWeatherService::class.java)
    }
}
