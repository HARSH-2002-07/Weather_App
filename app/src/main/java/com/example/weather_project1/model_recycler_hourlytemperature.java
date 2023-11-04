package com.example.weather_project1;

public class model_recycler_hourlytemperature {
    int hourlytemperature;
    String time, houlyimage;

    public model_recycler_hourlytemperature(String houlyimage, int hourlytemperature, String time){
        this.houlyimage = houlyimage;
        this.hourlytemperature = hourlytemperature;
        this.time = time;
    }
    public String getImageUrl(){return houlyimage;}
}
