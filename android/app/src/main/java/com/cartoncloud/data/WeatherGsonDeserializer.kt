package com.cartoncloud.data

import com.cartoncloud.model.WeatherInfo
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Deserializer to obtain only the last element or the only element if that is the case.
 */
open class WeatherGsonDeserializer : JsonDeserializer<WeatherInfo> {
    override fun deserialize(json: JsonElement, typeOfT: Type,
                             context: JsonDeserializationContext?): WeatherInfo {

        return if (json.isJsonArray) {
            val weatherArr = json.asJsonArray
            val gson = Gson()
            when(weatherArr.size()) {
                1 -> gson.fromJson(weatherArr.get(0).asJsonObject,
                        WeatherInfo::class.java)
                else -> gson.fromJson(weatherArr.get(weatherArr.size()-1).asJsonObject,
                        WeatherInfo::class.java)
            }
        } else {
            WeatherInfo()
        }
    }
}