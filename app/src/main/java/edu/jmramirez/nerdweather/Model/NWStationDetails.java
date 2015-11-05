package edu.jmramirez.nerdweather.Model;

import java.util.List;

public class NWStationDetails {

    String mWeatherCondition, mClouds, mWindDirection,
            mIcao, mElevation, mCountryCode, mLongitude, mTemperature,
            mDewPoint, mWindSpeed, mHumidity, mStationName, mDateTime, mLatitude;

    public NWStationDetails(){}

    public NWStationDetails(String weatherCondition, String clouds, String windDirection, String icao, String elevation, String countryCode, String longitude, String temperature, String dewPoint, String windSpeed, String humidity, String stationName, String dateTime, String latitude) {
        mWeatherCondition = weatherCondition;
        mClouds = clouds;
        mWindDirection = windDirection;
        mIcao = icao;
        mElevation = elevation;
        mCountryCode = countryCode;
        mLongitude = longitude;
        mTemperature = temperature;
        mDewPoint = dewPoint;
        mWindSpeed = windSpeed;
        mHumidity = humidity;
        mStationName = stationName;
        mDateTime = dateTime;
        mLatitude = latitude;
    }

    public String getWeatherCondition() {
        return mWeatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        mWeatherCondition = weatherCondition;
    }

    public String getClouds() {
        return mClouds;
    }

    public void setClouds(String clouds) {
        mClouds = clouds;
    }

    public String getWindDirection() {
        return mWindDirection;
    }

    public void setWindDirection(String windDirection) {
        mWindDirection = windDirection;
    }

    public String getIcao() {
        return mIcao;
    }

    public void setIcao(String icao) {
        mIcao = icao;
    }

    public String getElevation() {
        return mElevation;
    }

    public void setElevation(String elevation) {
        mElevation = elevation;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        mCountryCode = countryCode;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature(String temperature) {
        mTemperature = temperature;
    }

    public String getDewPoint() {
        return mDewPoint;
    }

    public void setDewPoint(String dewPoint) {
        mDewPoint = dewPoint;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        mWindSpeed = windSpeed;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }

    public String getStationName() {
        return mStationName;
    }

    public void setStationName(String stationName) {
        mStationName = stationName;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }
}
