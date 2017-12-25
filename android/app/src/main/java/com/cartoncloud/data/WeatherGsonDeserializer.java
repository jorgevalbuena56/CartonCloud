package com.cartoncloud.data;

import com.cartoncloud.model.WeatherInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Deserializer to obtain only the last element or the only element if that is the case.
 */
public class WeatherGsonDeserializer implements JsonDeserializer<WeatherInfo> {

    @Override
    public WeatherInfo deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonArray()) {
            JsonArray weatherArr = json.getAsJsonArray();
            Gson gson = new Gson();
            if (weatherArr.size() == 1) {
                return gson.fromJson(weatherArr.get(0).getAsJsonObject(), WeatherInfo.class);
            } else {
                return gson.fromJson(weatherArr.get(weatherArr.size()-1).getAsJsonObject(),
                        WeatherInfo.class);
            }
        } else {
            return new WeatherInfo();
        }
    }
}
