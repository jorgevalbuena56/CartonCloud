package com.cartoncloud.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Main model object to store the weather information.
 */
public class WeatherInfo implements Serializable {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("weather_state_name")
    @Expose
    private String weatherStateName;
    @SerializedName("weather_state_abbr")
    @Expose
    private String weatherStateAbbr;
    @SerializedName("wind_direction_compass")
    @Expose
    private String windDirectionCompass;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("applicable_date")
    @Expose
    private String applicableDate;
    @SerializedName("min_temp")
    @Expose
    private double minTemp;
    @SerializedName("max_temp")
    @Expose
    private double maxTemp;
    @SerializedName("the_temp")
    @Expose
    private double theTemp;
    @SerializedName("wind_speed")
    @Expose
    private double windSpeed;
    @SerializedName("wind_direction")
    @Expose
    private double windDirection;
    @SerializedName("air_pressure")
    @Expose
    private double airPressure;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("visibility")
    @Expose
    private double visibility;
    @SerializedName("predictability")
    @Expose
    private int predictability;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    public String getWeatherStateAbbr() {
        return weatherStateAbbr;
    }

    public void setWeatherStateAbbr(String weatherStateAbbr) {
        this.weatherStateAbbr = weatherStateAbbr;
    }

    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public void setWindDirectionCompass(String windDirectionCompass) {
        this.windDirectionCompass = windDirectionCompass;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getTheTemp() {
        return theTemp;
    }

    public void setTheTemp(double theTemp) {
        this.theTemp = theTemp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(double airPressure) {
        this.airPressure = airPressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public int getPredictability() {
        return predictability;
    }

    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherInfo)) return false;

        WeatherInfo that = (WeatherInfo) o;

        if (getId() != that.getId()) return false;
        if (Double.compare(that.getMinTemp(), getMinTemp()) != 0) return false;
        if (Double.compare(that.getMaxTemp(), getMaxTemp()) != 0) return false;
        if (Double.compare(that.getTheTemp(), getTheTemp()) != 0) return false;
        if (Double.compare(that.getWindSpeed(), getWindSpeed()) != 0) return false;
        if (Double.compare(that.getWindDirection(), getWindDirection()) != 0) return false;
        if (Double.compare(that.getAirPressure(), getAirPressure()) != 0) return false;
        if (getHumidity() != that.getHumidity()) return false;
        if (Double.compare(that.getVisibility(), getVisibility()) != 0) return false;
        if (getPredictability() != that.getPredictability()) return false;
        if (getWeatherStateName() != null ? !getWeatherStateName().equals(that.getWeatherStateName()) : that.getWeatherStateName() != null)
            return false;
        if (getWeatherStateAbbr() != null ? !getWeatherStateAbbr().equals(that.getWeatherStateAbbr()) : that.getWeatherStateAbbr() != null)
            return false;
        if (getWindDirectionCompass() != null ? !getWindDirectionCompass().equals(that.getWindDirectionCompass()) : that.getWindDirectionCompass() != null)
            return false;
        if (getCreated() != null ? !getCreated().equals(that.getCreated()) : that.getCreated() != null)
            return false;
        return getApplicableDate() != null ? getApplicableDate().equals(that.getApplicableDate()) : that.getApplicableDate() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getWeatherStateName() != null ? getWeatherStateName().hashCode() : 0);
        result = 31 * result + (getWeatherStateAbbr() != null ? getWeatherStateAbbr().hashCode() : 0);
        result = 31 * result + (getWindDirectionCompass() != null ? getWindDirectionCompass().hashCode() : 0);
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        result = 31 * result + (getApplicableDate() != null ? getApplicableDate().hashCode() : 0);
        temp = Double.doubleToLongBits(getMinTemp());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getMaxTemp());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getTheTemp());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getWindSpeed());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getWindDirection());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getAirPressure());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getHumidity();
        temp = Double.doubleToLongBits(getVisibility());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getPredictability();
        return result;
    }
}
