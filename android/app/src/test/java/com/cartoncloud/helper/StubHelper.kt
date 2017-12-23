package com.cartoncloud.helper

import com.cartoncloud.utils.StubInterceptor
import java.net.HttpURLConnection

/**
 * Simulate different responses to Unit Test the app
 */
object StubHelper {
    fun simulateErrorResponse() {
        val stubInterceptor = StubInterceptor.instance
        stubInterceptor.clearResponses()

        stubInterceptor.addResponse(StubInterceptor.FakeResponse(".*location.*1100661.*",
                -1,
                "", "Error retrieving Weather Information"))
    }
    fun simulateOneObjectResponse() {
        val stubInterceptor = StubInterceptor.instance
        stubInterceptor.clearResponses()

        stubInterceptor.addResponse(StubInterceptor.FakeResponse(".*location.*2017/04/20.*",
                HttpURLConnection.HTTP_OK,
                "[{\"id\":366945,\"weather_state_name\":\"Light Rain\"," +
                        "\"weather_state_abbr\":\"lr\",\"wind_direction_compass\":\"N\"," +
                        "\"created\":\"2013-04-27T22:52:57.403100Z\",\"applicable_date\":\"2013-04-27\"," +
                        "\"min_temp\":3.0699999999999998,\"max_temp\":10.01,\"the_temp\":null," +
                        "\"wind_speed\":9.8499999999999996,\"wind_direction\":358.0," +
                        "\"air_pressure\":null,\"humidity\":74,\"visibility\":9.9978624830987037," +
                        "\"predictability\":75}]",
                ""))

    }

    fun simulateMoreThanOneObjectResponse() {
        val stubInterceptor = StubInterceptor.instance
        stubInterceptor.clearResponses()

        stubInterceptor.addResponse(StubInterceptor.FakeResponse(".*location.*1100661.*",
                HttpURLConnection.HTTP_OK,
                "[{\"id\":366945,\"weather_state_name\":\"Light Rain\"," +
                        "\"weather_state_abbr\":\"lr\",\"wind_direction_compass\":\"N\"," +
                        "\"created\":\"2013-04-27T22:52:57.403100Z\",\"applicable_date\":\"2013-04-27\"," +
                        "\"min_temp\":3.0699999999999998,\"max_temp\":10.01,\"the_temp\":null," +
                        "\"wind_speed\":9.8499999999999996,\"wind_direction\":358.0," +
                        "\"air_pressure\":null,\"humidity\":74,\"visibility\":9.9978624830987037," +
                        "\"predictability\":75},{\"id\":373220,\"weather_state_name\":\"Light Rain\"," +
                        "\"weather_state_abbr\":\"lr\",\"wind_direction_compass\":\"N\"," +
                        "\"created\":\"2013-04-27T20:52:55.929470Z\",\"applicable_date\":\"2013-04-27\"," +
                        "\"min_temp\":3.0699999999999998,\"max_temp\":10.01,\"the_temp\":null," +
                        "\"wind_speed\":9.8499999999999996,\"wind_direction\":358.0," +
                        "\"air_pressure\":null,\"humidity\":74,\"visibility\":9.9978624830987037," +
                        "\"predictability\":75}]",
                ""))
    }
}