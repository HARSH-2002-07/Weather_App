package com.example.weather_project1;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import androidx.appcompat.widget.SearchView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Today#newInstance} factory method to
 * create an instance of this fragment.
 */



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Today extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   // Define the spacing in pixels

    TextView Humidity, TempDayNight, TempCurrent;
    TextView Dew_Point,Feelsliketemp;
    TextView Pressure,DateTime;
    TextView UV_Index;
    //ImageView TimeImage;
    RecyclerView recyclerView, recyclerhourlytemperature;
    int RainVolume=0;
    CardView cardView;
    String searchText;
    ImageView imageView;

    // Set a semi-transparent red background

    ArrayList<model_recycler_precipitation> arrprecipitationchance = new ArrayList<>();
    ArrayList<model_recycler_hourlytemperature> arrhourlytemperature = new ArrayList<>();
    SearchView searchView;
    // current data api give q = Location
    // Photo api
    //String photoApi = "https://api.unsplash.com/size/?condition";

    private String urlcurrent = "http://api.weatherapi.com/v1/current.json?key=1c1d113cb6094371a80200025230804&q=London&aqi=no";
    private String urlforecast = "http://api.weatherapi.com/v1/forecast.json?key=1c1d113cb6094371a80200025230804&q=London&aqi=no";
    public Today() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Today.
     */
    // TODO: Rename and change types and number of parameters
    public static Today newInstance(String param1, String param2) {
        Today fragment = new Today();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //searchView = getView().findViewById(R.id.SearchView);
        //String location = (String) searchView.getQuery();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);
        Humidity = rootView.findViewById(R.id.Humidity);
        Dew_Point = rootView.findViewById(R.id.DewPoint);
        searchView = rootView.findViewById(R.id.SearchView);
        Pressure = rootView.findViewById(R.id.Pressure);
        UV_Index = rootView.findViewById(R.id.UVIndex);
        TempDayNight = rootView.findViewById(R.id.tempdaynight);
        TempCurrent = rootView.findViewById(R.id.tempcurrent);
        Feelsliketemp = rootView.findViewById(R.id.anything);
        recyclerView = rootView.findViewById(R.id.recyclerprecipitation);
        recyclerhourlytemperature = rootView.findViewById(R.id.hourlytemperature);
        DateTime = rootView.findViewById(R.id.datetime);
        cardView = rootView.findViewById(R.id.cardview1);
        imageView = rootView.findViewById(R.id.timeimage);
        String sizeTimeImage = "450x350";


        //cardView.setCardBackgroundColor();
        AndroidNetworking.initialize(getActivity().getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.setLayoutManager(layoutManager);
        recyclerhourlytemperature.setLayoutManager(layoutManager1);
        recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(spacingInPixels));
        recyclerhourlytemperature.addItemDecoration(new HorizontalSpaceItemDecoration(spacingInPixels));
        searchView = rootView.findViewById(R.id.SearchView);
        // Call the searchViewQuery method to get the updated URLs
        UpdatedUrls updatedUrls = searchViewQuery(urlforecast, urlcurrent, searchView);




// Access the updated URLs as two separate strings
        String updatedUrlForecast = updatedUrls.getUpdatedUrlForecast();
        String updatedUrlCurrent = updatedUrls.getUpdatedUrlCurrent();

// Now you have the updated URLs in updatedUrlForecast and updatedUrlCurrent

        AndroidNetworking.get(urlcurrent)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    //Fetching Data for 2nd Card View
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check if the "current" key exists in the JSON response
                            if (response.has("current")) {
                                JSONObject currentData = response.getJSONObject("current");

                                // Check if the "humidity" key exists in the "current" data
                                if (currentData.has("humidity")) {
                                    Long humidity = currentData.getLong("humidity");
                                    Humidity.setText("Humidity: " + Math.toIntExact(humidity));
                                }
                                else {
                                    // Handle the case when "humidity" key is missing
                                    Humidity.setText("Humidity not available");
                                }
                            }
                            else {
                                // Handle the case when the "current" key is missing
                                Humidity.setText("Current data not available");
                            }
                            if (response.has("current")) {
                                JSONObject currentData = response.getJSONObject("current");

                                // Check if the "humidity" key exists in the "current" data
                                if (currentData.has("wind_degree")) {
                                    Long dew_Point = currentData.getLong("wind_degree");
                                    Dew_Point.setText("Dew Point: " + Math.toIntExact(dew_Point));
                                }
                                else {
                                    // Handle the case when "humidity" key is missing
                                    Dew_Point.setText("Humidity not available");
                                }
                            }
                            else {
                                // Handle the case when the "current" key is missing
                                Dew_Point.setText("Current data not available");
                            }

                            if (response.has("current")) {
                                JSONObject currentData = response.getJSONObject("current");

                                // Check if the "humidity" key exists in the "current" data
                                if (currentData.has("pressure_in")) {
                                    Long pressure = currentData.getLong("pressure_in");
                                    Pressure.setText("Pressure: " + Math.toIntExact(pressure));
                                }
                                else {
                                    // Handle the case when "humidity" key is missing
                                    Pressure.setText("Pressure not available");
                                }
                            }
                            else {
                                // Handle the case when the "current" key is missing
                                Pressure.setText("Current data not available");
                            }

                            if (response.has("current")) {
                                JSONObject currentData = response.getJSONObject("current");

                                // Check if the "humidity" key exists in the "current" data
                                if (currentData.has("uv")) {
                                    Long uvindex = currentData.getLong("uv");
                                    UV_Index.setText("UV Index: " + Math.toIntExact(uvindex));
                                }
                                else {
                                    // Handle the case when "humidity" key is missing
                                    UV_Index.setText("UV Index not available");
                                }
                            }
                            else {
                                // Handle the case when the "current" key is missing
                                UV_Index.setText("Current data not available");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                        }
                    }

                    @Override
                    public void onError(com.androidnetworking.error.ANError error) {
                        // Handle network request error
                        Log.e("Error", error.getMessage());
                    }
                });

        AndroidNetworking.get(urlforecast)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        if (response.has("forecast")) {
                            JSONObject forecastresult;
                            try {
                                forecastresult = response.getJSONObject("forecast");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONArray forecastarray;
                            try {
                                forecastarray = forecastresult.getJSONArray("forecastday");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONObject hourlydata1;
                            try {
                                hourlydata1 = forecastarray.getJSONObject(0);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONArray hourlydata;
                            try {
                                hourlydata = hourlydata1.getJSONArray("hour");
                                JSONObject maxmintemp = hourlydata1.getJSONObject("day");
                                String maxtemp0 = maxmintemp.getString("maxtemp_c");
                                String mintemp0 = maxmintemp.getString("mintemp_c");
                                TempDayNight.setText(maxtemp0+"°C"+"  "+mintemp0+"°C");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            recyclerprecipitationchanceadaptor adaptor = new recyclerprecipitationchanceadaptor(getActivity(),arrprecipitationchance);
                            adaptorhourlytemperaturerecyclerview adaptor2 = new adaptorhourlytemperaturerecyclerview(getActivity(),arrhourlytemperature);
                            for (int i = 0; i < hourlydata.length(); i++) {
                                JSONObject hourlyEntry;
                                try {
                                    hourlyEntry = hourlydata.getJSONObject(i);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    int chanceOfRain;
                                    String apiDateTime = hourlyEntry.getString("time");
                                    int hourlytemp = hourlyEntry.getInt("temp_c");
                                    String time = DateTimeUtils.extractTime(apiDateTime);
                                    JSONObject conditiontemp = hourlyEntry.getJSONObject("condition");
                                    String hourlytempurl = conditiontemp.getString("icon");
                                    //Picasso.get().load("https:"+ hourlytempurl).into(imageView);
                                    Log.d("Url", "URLimage:"+hourlytempurl);

                                    chanceOfRain = hourlyEntry.getInt("chance_of_rain");
                                    model_recycler_hourlytemperature modeltemperature = new model_recycler_hourlytemperature(hourlytempurl, hourlytemp, time);
                                    model_recycler_precipitation modelprecipitatioin = new model_recycler_precipitation(R.drawable.raindrop, chanceOfRain, RainVolume, time);
                                    arrhourlytemperature.add(modeltemperature);
                                    arrprecipitationchance.add(modelprecipitatioin);
                                }
                                catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            recyclerView.setAdapter(adaptor);
                            recyclerhourlytemperature.setAdapter(adaptor2);
                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        AndroidNetworking.get(urlcurrent)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("location")) {
                            try {
                                JSONObject locationdat = response.getJSONObject("location");
                                String time = locationdat.getString("localtime");
                                DateTime.setText(time);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (response.has("current")){
                            try {
                                JSONObject currentdata = response.getJSONObject("current");
                                String feeltemp = currentdata.getString("feelslike_c");
                                String originaltemp = currentdata.getString("temp_c");
                                TempCurrent.setText(originaltemp+"°C");
                                Feelsliketemp.setText("feelslike"+feeltemp+"°C");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });

        AndroidNetworking.get(urlforecast)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("current")) {
                            JSONObject currentDetails;
                            try {
                                currentDetails = response.getJSONObject("current");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONObject condition;
                            try {
                                condition = currentDetails.getJSONObject("condition");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                String imageUrl = condition.getString("icon");
                                Log.d("IMAGEURL", imageUrl);
                                Picasso.get().load("https:"+imageUrl).into(imageView);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
        return rootView;
    }

    public class UpdatedUrls {
        private String updatedUrlForecast;
        private String updatedUrlCurrent;

        public UpdatedUrls(String updatedUrlForecast, String updatedUrlCurrent) {
            this.updatedUrlForecast = updatedUrlForecast;
            this.updatedUrlCurrent = updatedUrlCurrent;
        }

        public String getUpdatedUrlForecast() {
            Log.d("URL", "update"+ updatedUrlForecast);
            return updatedUrlForecast;
        }

        public String getUpdatedUrlCurrent() {
            Log.d("URL", "update:"+ updatedUrlCurrent);
            return updatedUrlCurrent;
        }

    }

    private UpdatedUrls searchViewQuery(String urlforecast, String urlcurrent, SearchView searchView) {
        final String[] updatedUrlForecast = {urlforecast};
        final String[] updatedUrlCurrent = {urlcurrent};

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SearchView", "Query submitted: " + query);
                searchText = query; // This is the text entered by the user as a String
                updatedUrlForecast[0] = urlforecast.replace("London", searchText);
                updatedUrlCurrent[0] = urlcurrent.replace("London", searchText);

                makeApiRequest(updatedUrlForecast,updatedUrlCurrent);
                // Log the updated URLs
                Log.d("URL", "Updated Forecast URL: " + updatedUrlForecast[0]);
                Log.d("URL", "Updated Current URL: " + updatedUrlCurrent[0]);

                // Make the API requests with the updated URLs here
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText; // This is the current text entered by the user as a String
                return true;
            }
        });
        Log.d("URL", "updates"+updatedUrlCurrent+updatedUrlForecast);
        return new UpdatedUrls(updatedUrlForecast[0], updatedUrlCurrent[0]);

    }

    private void makeApiRequest(String[] updatedUrlForecast, String[] updatedUrlCurrent) {

        AndroidNetworking.get(updatedUrlCurrent[0])
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    //Fetching Data for 2nd Card View
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check if the "current" key exists in the JSON response
                            if (response.has("current")) {
                                JSONObject currentData = response.getJSONObject("current");

                                // Check if the "humidity" key exists in the "current" data
                                if (currentData.has("humidity")) {
                                    Long humidity = currentData.getLong("humidity");
                                    Humidity.setText("Humidity: " + Math.toIntExact(humidity));
                                }
                                else {
                                    // Handle the case when "humidity" key is missing
                                    Humidity.setText("Humidity not available");
                                }
                            }
                            else {
                                // Handle the case when the "current" key is missing
                                Humidity.setText("Current data not available");
                            }
                            if (response.has("current")) {
                                JSONObject currentData = response.getJSONObject("current");

                                // Check if the "humidity" key exists in the "current" data
                                if (currentData.has("wind_degree")) {
                                    Long dew_Point = currentData.getLong("wind_degree");
                                    Dew_Point.setText("Dew Point: " + Math.toIntExact(dew_Point));
                                }
                                else {
                                    // Handle the case when "humidity" key is missing
                                    Dew_Point.setText("Humidity not available");
                                }
                            }
                            else {
                                // Handle the case when the "current" key is missing
                                Dew_Point.setText("Current data not available");
                            }

                            if (response.has("current")) {
                                JSONObject currentData = response.getJSONObject("current");

                                // Check if the "humidity" key exists in the "current" data
                                if (currentData.has("pressure_in")) {
                                    Long pressure = currentData.getLong("pressure_in");
                                    Pressure.setText("Pressure: " + Math.toIntExact(pressure));
                                }
                                else {
                                    // Handle the case when "humidity" key is missing
                                    Pressure.setText("Pressure not available");
                                }
                            }
                            else {
                                // Handle the case when the "current" key is missing
                                Pressure.setText("Current data not available");
                            }

                            if (response.has("current")) {
                                JSONObject currentData = response.getJSONObject("current");

                                // Check if the "humidity" key exists in the "current" data
                                if (currentData.has("uv")) {
                                    Long uvindex = currentData.getLong("uv");
                                    UV_Index.setText("UV Index: " + Math.toIntExact(uvindex));
                                }
                                else {
                                    // Handle the case when "humidity" key is missing
                                    UV_Index.setText("UV Index not available");
                                }
                            }
                            else {
                                // Handle the case when the "current" key is missing
                                UV_Index.setText("Current data not available");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                        }
                    }

                    @Override
                    public void onError(com.androidnetworking.error.ANError error) {
                        // Handle network request error
                        Log.e("Error", error.getMessage());
                    }
                });

        AndroidNetworking.get(updatedUrlForecast[0])
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        if (response.has("forecast")) {
                            JSONObject forecastresult;
                            try {
                                forecastresult = response.getJSONObject("forecast");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONArray forecastarray;
                            try {
                                forecastarray = forecastresult.getJSONArray("forecastday");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONObject hourlydata1;
                            try {
                                hourlydata1 = forecastarray.getJSONObject(0);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONArray hourlydata;
                            try {
                                hourlydata = hourlydata1.getJSONArray("hour");
                                JSONObject maxmintemp = hourlydata1.getJSONObject("day");
                                String maxtemp0 = maxmintemp.getString("maxtemp_c");
                                String mintemp0 = maxmintemp.getString("mintemp_c");
                                TempDayNight.setText(maxtemp0+"°C"+"  "+mintemp0+"°C");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            recyclerprecipitationchanceadaptor adaptor = new recyclerprecipitationchanceadaptor(getActivity(),arrprecipitationchance);
                            adaptorhourlytemperaturerecyclerview adaptor2 = new adaptorhourlytemperaturerecyclerview(getActivity(),arrhourlytemperature);
                            for (int i = 0; i < hourlydata.length(); i++) {
                                JSONObject hourlyEntry;
                                try {
                                    hourlyEntry = hourlydata.getJSONObject(i);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    int chanceOfRain;
                                    String apiDateTime = hourlyEntry.getString("time");
                                    int hourlytemp = hourlyEntry.getInt("temp_c");
                                    String time = DateTimeUtils.extractTime(apiDateTime);
                                    JSONObject conditiontemp = hourlyEntry.getJSONObject("condition");
                                    String hourlytempurl = conditiontemp.getString("icon");

                                    Log.d("Url", "URLimage:"+hourlytempurl);
                                    chanceOfRain = hourlyEntry.getInt("chance_of_rain");
                                    model_recycler_hourlytemperature modeltemperature = new model_recycler_hourlytemperature(hourlytempurl, hourlytemp, time);
                                    model_recycler_precipitation modelprecipitatioin = new model_recycler_precipitation(R.drawable.raindrop, chanceOfRain, RainVolume, time);
                                    arrhourlytemperature.add(modeltemperature);
                                    arrprecipitationchance.add(modelprecipitatioin);
                                }
                                catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            recyclerView.setAdapter(adaptor);
                            recyclerhourlytemperature.setAdapter(adaptor2);
                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        AndroidNetworking.get(updatedUrlCurrent[0])
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("location")) {
                            try {
                                JSONObject locationdat = response.getJSONObject("location");
                                String time = locationdat.getString("localtime");
                                DateTime.setText(time);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (response.has("current")){
                            try {
                                JSONObject currentdata = response.getJSONObject("current");
                                String feeltemp = currentdata.getString("feelslike_c");
                                String originaltemp = currentdata.getString("temp_c");
                                TempCurrent.setText(originaltemp+"°C");
                                Feelsliketemp.setText("feelslike"+feeltemp+"°C");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}