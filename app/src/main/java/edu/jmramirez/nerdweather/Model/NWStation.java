package edu.jmramirez.nerdweather.Model;

public class NWStation {
    String mStationName, mIcao, mTemperature;

    public NWStation(String stationName, String icao, String temperature) {
        mStationName = stationName;
        mIcao = icao;
        mTemperature = temperature;
    }

    public String getStationName() {
        return mStationName;
    }

    public void setStationName(String stationName) {
        mStationName = stationName;
    }

    public String getIcao() {
        return mIcao;
    }

    public void setIcao(String icao) {
        mIcao = icao;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature(String temperature) {
        mTemperature = temperature;
    }
}