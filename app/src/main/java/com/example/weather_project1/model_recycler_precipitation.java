package com.example.weather_project1;

public class model_recycler_precipitation {
    int imageUrl;
    int Precipitation_Chance, Rain_Volume;
    String Time;

    public model_recycler_precipitation(int imageUrl, int Precipitation_Chance, int Rain_Volume, String Time) {
        this.imageUrl = imageUrl;
        this.Precipitation_Chance = Precipitation_Chance;
        this.Rain_Volume = Rain_Volume;
        this.Time = Time;
    }

}