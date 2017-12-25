package com.cartoncloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Main model object to store the weather information.
 */
class WeatherInfo {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("weather_state_name")
    @Expose
    var weatherStateName: String? = null
    @SerializedName("weather_state_abbr")
    @Expose
    var weatherStateAbbr: String? = null
    @SerializedName("wind_direction_compass")
    @Expose
    var windDirectionCompass: String? = null
    @SerializedName("created")
    @Expose
    var created: String? = null
    @SerializedName("applicable_date")
    @Expose
    var applicableDate: String? = null
    @SerializedName("min_temp")
    @Expose
    var minTemp: Any? = null
    @SerializedName("max_temp")
    @Expose
    var maxTemp: Any? = null
    @SerializedName("the_temp")
    @Expose
    var theTemp: Double? = null
    @SerializedName("wind_speed")
    @Expose
    var windSpeed: Double? = null
    @SerializedName("wind_direction")
    @Expose
    var windDirection: Double? = null
    @SerializedName("air_pressure")
    @Expose
    var airPressure: Double? = null
    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null
    @SerializedName("visibility")
    @Expose
    var visibility: Double? = null
    @SerializedName("predictability")
    @Expose
    var predictability: Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WeatherInfo) return false

        if (id != other.id) return false
        if (weatherStateName != other.weatherStateName) return false
        if (weatherStateAbbr != other.weatherStateAbbr) return false
        if (windDirectionCompass != other.windDirectionCompass) return false
        if (created != other.created) return false
        if (applicableDate != other.applicableDate) return false
        if (minTemp != other.minTemp) return false
        if (maxTemp != other.maxTemp) return false
        if (theTemp != other.theTemp) return false
        if (windSpeed != other.windSpeed) return false
        if (windDirection != other.windDirection) return false
        if (airPressure != other.airPressure) return false
        if (humidity != other.humidity) return false
        if (visibility != other.visibility) return false
        if (predictability != other.predictability) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (weatherStateName?.hashCode() ?: 0)
        result = 31 * result + (weatherStateAbbr?.hashCode() ?: 0)
        result = 31 * result + (windDirectionCompass?.hashCode() ?: 0)
        result = 31 * result + (created?.hashCode() ?: 0)
        result = 31 * result + (applicableDate?.hashCode() ?: 0)
        result = 31 * result + (minTemp?.hashCode() ?: 0)
        result = 31 * result + (maxTemp?.hashCode() ?: 0)
        result = 31 * result + (theTemp?.hashCode() ?: 0)
        result = 31 * result + (windSpeed?.hashCode() ?: 0)
        result = 31 * result + (windDirection?.hashCode() ?: 0)
        result = 31 * result + (airPressure?.hashCode() ?: 0)
        result = 31 * result + (humidity ?: 0)
        result = 31 * result + (visibility?.hashCode() ?: 0)
        result = 31 * result + (predictability ?: 0)
        return result
    }
}