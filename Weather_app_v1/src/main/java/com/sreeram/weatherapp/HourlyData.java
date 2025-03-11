package com.sreeram.weatherapp;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class HourlyData {

    private List<String> time;
    
    @SerializedName("temperature_2m")
    private List<Double> temperature2m;

    // Getters and setters
    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getTemperature2m() {
        return temperature2m;
    }

    public void setTemperature2m(List<Double> temperature2m) {
        this.temperature2m = temperature2m;
    }

    @Override
    public String toString() {
        return "HourlyData [time=" + time + "temperature_2m=" + temperature2m + "]";
    }
}
