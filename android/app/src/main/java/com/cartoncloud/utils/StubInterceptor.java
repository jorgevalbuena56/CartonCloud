package com.cartoncloud.utils;

import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Interceptor class used to mock different responses to the UI
 */
public class StubInterceptor implements Interceptor {
    private List<FakeResponse> fakeResponses = new ArrayList<>();

    public StubInterceptor() {
        this.addResponse(new FakeResponse(".*location.*1100661.*",
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
                ""));
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = chain.request().url().toString();

        for (FakeResponse fakeResponse : fakeResponses) {
            Pattern pattern = Pattern.compile(fakeResponse.getPattern());
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    //ignored exception
                }

                return createStubbedResponse(chain, fakeResponse);
            }
        }

        return chain.proceed(chain.request());
    }

    /**
     * Add fake response to the list
     * @param fakeResponse
     */
    private void addResponse(FakeResponse fakeResponse) {
        fakeResponses.add(fakeResponse);
    }

    /**
     * Build a generic error response
     */
    public void addErrorResponse() {
        this.addResponse(new FakeResponse(".*location.*1100661.*",
                -1,
                "", "Error retrieving Weather Information"));
    }

    /**
     * Clear responses from the list
     */
    public void clearResponses() {
        fakeResponses.clear();
    }

    /**
     * Build the Stubbed Response to mock answer
     * @param chain
     * @param fakeResponse
     * @return
     * @throws IOException
     */
    private Response createStubbedResponse(Interceptor.Chain chain,
                                           FakeResponse fakeResponse) throws IOException {
        if (!TextUtils.isEmpty(fakeResponse.getErrorMessage())) {
            throw new IOException(fakeResponse.getErrorMessage());
        }

        return new Response.Builder()
                .code(fakeResponse.getStatusCode())
                .message(fakeResponse.getResponse())
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .body(ResponseBody.create(MediaType.parse("application/json"),
                        fakeResponse.getResponse()))
            .addHeader("content-type", "application/json")
                .build();
    }
}
