package com.cartoncloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Main model object to store the weather information.
 */
class WeatherInfo {
    @SerializedName("id")
    @Expose
    val id: Int? = null
    @SerializedName("weather_state_name")
    @Expose
    val weatherStateName: String? = null
    @SerializedName("weather_state_abbr")
    @Expose
    val weatherStateAbbr: String? = null
    @SerializedName("wind_direction_compass")
    @Expose
    val windDirectionCompass: String? = null
    @SerializedName("created")
    @Expose
    val created: String? = null
    @SerializedName("applicable_date")
    @Expose
    val applicableDate: String? = null
    @SerializedName("min_temp")
    @Expose
    val minTemp: Any? = null
    @SerializedName("max_temp")
    @Expose
    val maxTemp: Any? = null
    @SerializedName("the_temp")
    @Expose
    val theTemp: Double? = null
    @SerializedName("wind_speed")
    @Expose
    val windSpeed: Double? = null
    @SerializedName("wind_direction")
    @Expose
    val windDirection: Double? = null
    @SerializedName("air_pressure")
    @Expose
    val airPressure: Double? = null
    @SerializedName("humidity")
    @Expose
    val humidity: Int? = null
    @SerializedName("visibility")
    @Expose
    val visibility: Double? = null
    @SerializedName("predictability")
    @Expose
    val predictability: Int? = null
}