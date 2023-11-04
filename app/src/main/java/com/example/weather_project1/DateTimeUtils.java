package com.example.weather_project1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    // Method to extract time from a date-time string
    public static String extractTime(String dateTimeString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

            Date date = inputFormat.parse(dateTimeString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle the error appropriately
        }
    }
}
