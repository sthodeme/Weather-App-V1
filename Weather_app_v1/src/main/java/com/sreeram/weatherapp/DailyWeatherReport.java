package com.sreeram.weatherapp;
//import com.google.gson.annotations.SerializedName;

public class DailyWeatherReport {

    //@Expose (serialize = true, deserialize = true)

    //collecting only the "hourly" data, which contains the time, & temperature (and rain) data
    //@SerializedName("hourly")
    private HourlyData hourly;

    //getters and setters
    public HourlyData getHourly() {
        return hourly;
    }

    public void setHourly(HourlyData hourly) {
        this.hourly = hourly;
    }

    @Override
    public String toString() {
        return "DailyWeatherReport [hourly=" + hourly + "]";
    }

}
