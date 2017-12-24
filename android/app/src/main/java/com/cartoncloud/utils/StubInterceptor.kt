package com.cartoncloud.utils

import android.text.TextUtils
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.util.*
import java.util.regex.Pattern

class StubInterceptor private constructor() : Interceptor {

  private val fakeResponses = ArrayList<FakeResponse>()

  init {

    this.addResponse(FakeResponse(".*location.*1100661.*",
            HTTP_OK,
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

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val url = chain.request().url().toString()

    for (fakeResponse in fakeResponses) {
      val pattern = Pattern.compile(fakeResponse.pattern)
      val matcher = pattern.matcher(url)
      if (matcher.find()) {
        delayRequestFor(1000)
        return createStubbedResponse(chain, fakeResponse)
      }
    }

    return chain.proceed(chain.request())
  }

  private fun delayRequestFor(millis: Int) {
    try {
      Thread.sleep(millis.toLong())
    } catch (ignored: InterruptedException) {
    }

  }

  fun addResponse(fakeResponse: FakeResponse) {
    fakeResponses.add(fakeResponse)
  }

  fun addErrorResponse() {
    this.addResponse(StubInterceptor.FakeResponse(".*location.*1100661.*",
            -1,
            "", "Error retrieving Weather Information"))
  }
  fun clearResponses() {
    fakeResponses.clear()
  }

  @Throws(IOException::class)
  private fun createStubbedResponse(chain: Interceptor.Chain, fakeResponse: FakeResponse): Response {
    if (!TextUtils.isEmpty(fakeResponse.errorMessage)) {
      throw IOException(fakeResponse.errorMessage)
    }

    return Response.Builder()
            .code(fakeResponse.statusCode)
            .message(fakeResponse.response)
            .request(chain.request())
            .protocol(Protocol.HTTP_2)
            .body(ResponseBody.create(MediaType.parse("application/json"), fakeResponse.response?.toByteArray()))
            .addHeader("content-type", "application/json")
            .build()
  }

  class FakeResponse(val pattern: String, val statusCode: Int, val response: String, val errorMessage: String)

  companion object {
    val instance: StubInterceptor = StubInterceptor()
  }
}
