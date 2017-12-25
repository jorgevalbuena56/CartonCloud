package com.cartoncloud;

import com.cartoncloud.data.MetaWeatherService;
import com.cartoncloud.data.WeatherGsonDeserializer;
import com.cartoncloud.model.WeatherInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Unit Test for error, one result only and multiple results.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4.class)
public class WeatherUnitTest {
    private MockWebServer mockWebServer;

    @Before
    public void setup() {
        mockWebServer = new MockWebServer();
    }


    @Test
    public void justOneResultSuccessResponse() throws IOException {
        WeatherInfo expectedWeatherInfo = new WeatherInfo();
        expectedWeatherInfo.setId(5119190818095104L);
        expectedWeatherInfo.setWeatherStateName("Light Cloud");
        expectedWeatherInfo.setWeatherStateAbbr("lc");
        expectedWeatherInfo.setWindDirectionCompass("NNE");
        expectedWeatherInfo.setCreated ("2017-12-20T11:52:00.533560Z");
        expectedWeatherInfo.setApplicableDate("2017-12-20");
        expectedWeatherInfo.setMinTemp(21.534);
        expectedWeatherInfo.setMaxTemp(30.921999999999997);
        expectedWeatherInfo.setTheTemp(30.09);
        expectedWeatherInfo.setWindSpeed(8.007244799002624);
        expectedWeatherInfo.setWindDirection(26.871354291294015);
        expectedWeatherInfo.setAirPressure(1011.85);
        expectedWeatherInfo.setHumidity(56);
        expectedWeatherInfo.setVisibility(12.792168734589994);
        expectedWeatherInfo.setPredictability(70);

        configureWeatherRequest("one_weather_response.json", expectedWeatherInfo);
    }

    @Test
    public void moreThanOneResultSuccessResponse() throws IOException {
        WeatherInfo expectedWeatherInfo = new WeatherInfo();
        expectedWeatherInfo.setId(5904742752452608L);
        expectedWeatherInfo.setWeatherStateName("Showers");
        expectedWeatherInfo.setWeatherStateAbbr("s");
        expectedWeatherInfo.setWindDirectionCompass("NE");
        expectedWeatherInfo.setCreated ("2017-12-11T14:52:24.080960Z");
        expectedWeatherInfo.setApplicableDate("2017-12-20");
        expectedWeatherInfo.setMinTemp(20.03);
        expectedWeatherInfo.setMaxTemp(28.9675);
        expectedWeatherInfo.setTheTemp(24.3);
        expectedWeatherInfo.setWindSpeed(7.7333538800074235);
        expectedWeatherInfo.setWindDirection(45.7254819657352);
        expectedWeatherInfo.setAirPressure(1006.99);
        expectedWeatherInfo.setHumidity(54);
        expectedWeatherInfo.setVisibility(0.0);
        expectedWeatherInfo.setPredictability(73);

        configureWeatherRequest("full_response.json", expectedWeatherInfo);
    }

    @Test
    public void errorResponse() throws IOException {
        mockWebServer.start();
        MockResponse mockResponse = new MockResponse().setResponseCode(-200).setBody("");

        mockWebServer.enqueue(mockResponse);

        MetaWeatherService weatherService = createTestRetrofitService();
        Observable<WeatherInfo> observableWeather =
                weatherService.getYesterdayWeather("1100661", "2017/12/23");
        TestSubscriber<WeatherInfo> testSubscriber = new TestSubscriber<>();
        observableWeather.subscribe(testSubscriber);
        Assert.assertNotNull(testSubscriber.getOnErrorEvents());
        mockWebServer.shutdown();
    }

    /**
     * Function to create the retrofit service without using interceptor and getting the path from
     * the mock server
     */
    private MetaWeatherService createTestRetrofitService()  {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherInfo.class, new WeatherGsonDeserializer())
                .create();
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mockWebServer.url("/").toString())
                .client(new OkHttpClient()).build().create(MetaWeatherService.class);
    }

    private void configureWeatherRequest(String jsonFileName, WeatherInfo expectedWeatherInfo) throws IOException {
        mockWebServer.start();
        InputStream responseInputStream =
                getClass().getClassLoader().getResourceAsStream(jsonFileName);
        Scanner jsonResponseScanner = new Scanner(responseInputStream).useDelimiter("\\A");
        MockResponse mockResponse = new MockResponse();
        mockResponse.addHeader("Content-Type", "application/json; charset=utf-8");
        mockResponse.setBody(jsonResponseScanner.next());

        mockWebServer.enqueue(mockResponse);

        MetaWeatherService weatherService = createTestRetrofitService();
        Observable<WeatherInfo> observableWeather =
                weatherService.getYesterdayWeather("1100661", "2017/12/23");
        TestSubscriber<WeatherInfo> testSubscriber = new TestSubscriber<>();
        observableWeather.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        //every single value has to be the same as the expected information
        testSubscriber.assertValue(expectedWeatherInfo);
        mockWebServer.shutdown();

    }
}
