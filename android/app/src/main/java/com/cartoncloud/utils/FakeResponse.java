package com.cartoncloud.utils;

/**
 * Class to store the information used to Fake a response
 */

public class FakeResponse {
    private String pattern;
    private int statusCode;
    private String response;
    private String errorMessage;
    public FakeResponse(String pattern,
                        int statusCode,
                        String response,
                        String errorMessage) {
        this.pattern = pattern;
        this.statusCode = statusCode;
        this.response = response;
        this.errorMessage = errorMessage;
    }

    public String getPattern() {
        return pattern;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponse() {
        return response;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
