package com.example.weather_project1;

public class model_recycler_tendays {
    String day,Weather_Condition,tempmax, tempmin;
    String Condition_image;

    public model_recycler_tendays(String day, String Weather_Condition, String tempmax, String tempmin, String Condition_image){
        this.day = day;
        this.Condition_image = Condition_image;
        this.Weather_Condition = Weather_Condition;
        this.tempmax = tempmax;
        this.tempmin = tempmin;
    }

    public String getImageUrl() {
        return Condition_image;
    }
}
