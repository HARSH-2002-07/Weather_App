package com.example.weather_project1;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TenDays#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TenDays extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerViewDays;
    SearchView searchView;
    String searchText;
    public String urlforecast = "http://api.weatherapi.com/v1/forecast.json?key=1c1d113cb6094371a80200025230804&q=London&days=10&aqi=no&alerts=no";
    ArrayList<model_recycler_tendays> arrTenDays = new ArrayList<>();

    public TenDays() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TenDays.
     */
    // TODO: Rename and change types and number of parameters
    public static TenDays newInstance(String param1, String param2) {
        TenDays fragment = new TenDays();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AndroidNetworking.initialize(getActivity().getApplicationContext());
        View rootView = inflater.inflate(R.layout.fragment_ten_days, container, false);
        searchView = rootView.findViewById(R.id.search_view);
        recyclerViewDays = rootView.findViewById(R.id.recyclerviewdays);
        recyclerViewDays.setLayoutManager(new LinearLayoutManager(getActivity()));
        TenDays.UpdatedUrls updatedUrls = searchViewQuery(urlforecast, searchView);

        AndroidNetworking.get((urlforecast))
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
                            adaptorweathercondition adaptor = null;
                            adaptor = new adaptorweathercondition(getActivity(), arrTenDays);

                            for (int i = 0; i < forecastarray.length(); i++) {
                                JSONObject dailycondition;
                                try {
                                    dailycondition = forecastarray.getJSONObject(i);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                String day;
                                try {
                                    day = dailycondition.getString("date");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                JSONObject condition;
                                try {
                                    condition = dailycondition.getJSONObject("day");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                String maxtemp;
                                try {
                                    maxtemp = condition.getString("maxtemp_c");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                String mintemp;
                                try {
                                    mintemp = condition.getString("mintemp_c");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                JSONObject condition_;
                                try {
                                    condition_ = condition.getJSONObject("condition");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                String weathercondition;
                                try {
                                    weathercondition = condition_.getString("text");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                String imageurl;
                                try {
                                    imageurl = condition_.getString("icon");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                model_recycler_tendays modelcondition = new model_recycler_tendays(day, weathercondition, maxtemp, mintemp, imageurl);
                                arrTenDays.add(modelcondition);
                            }
                            recyclerViewDays.setAdapter(adaptor);

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

        public UpdatedUrls(String updatedUrlForecast) {
            this.updatedUrlForecast = updatedUrlForecast;
        }

        public String getUpdatedUrlForecast() {
            return updatedUrlForecast;
        }
    }

    private UpdatedUrls searchViewQuery(String urlforecast,SearchView searchView) {
        final String[] updatedUrlForecast = {urlforecast};
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query; // This is the text entered by the user as a String
                updatedUrlForecast[0] = urlforecast.replace("London", searchText);

                makeApiRequest(updatedUrlForecast);
                // Log the updated URLs
                // Make the API requests with the updated URLs here
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText; // This is the current text entered by the user as a String
                return true;
            }
        });
        return new UpdatedUrls(updatedUrlForecast[0]);

    }
    private void makeApiRequest(String[] updatedUrlForecast){AndroidNetworking.get(Arrays.toString(updatedUrlForecast))
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
                        adaptorweathercondition adaptor = null;
                        for (int i = 0; i < forecastarray.length(); i++) {
                            JSONObject dailycondition;
                            try {
                                dailycondition = forecastarray.getJSONObject(i);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            String day;
                            try {
                                day = dailycondition.getString("date");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONObject condition;
                            try {
                                condition = dailycondition.getJSONObject("day");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            String maxtemp;
                            try {
                                maxtemp = condition.getString("maxtemp_c");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            String mintemp;
                            try {
                                mintemp = condition.getString("mintemp_c");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONObject condition_;
                            try {
                                condition_ = condition.getJSONObject("condition");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            String weathercondition;
                            try {
                                weathercondition = condition_.getString("text");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            String imageurl;
                            try {
                                imageurl = condition_.getString("icon");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            adaptor = new adaptorweathercondition(getActivity(), arrTenDays);
                            model_recycler_tendays modelcondition = new model_recycler_tendays(day, weathercondition, maxtemp, mintemp, imageurl);
                            arrTenDays.add(modelcondition);
                        }
                        recyclerViewDays.setAdapter(adaptor);

                    }
                }

                @Override
                public void onError(ANError anError) {

                }
            });}
}