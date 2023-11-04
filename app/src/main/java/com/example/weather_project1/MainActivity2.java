package com.example.weather_project1;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class MainActivity2 extends AppCompatActivity {

    private static final int Req_code = 100;
    ViewPager viewPager;
    TabLayout tabLayout;



    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);



        ViewPagerWeatherAdaptor adaptor = new ViewPagerWeatherAdaptor(getSupportFragmentManager());
        viewPager.setAdapter(adaptor);

        tabLayout.setupWithViewPager(viewPager);
        //Asking For Permission If Not Given
        if(checkper()){
            Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Asking For Permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION,ACCESS_BACKGROUND_LOCATION},Req_code);
        }
    }


    //If Permission Given Or Denied
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==Req_code){
            if (grantResults.length>0){
                int fineloc =  grantResults[0];
                int backloc = grantResults[1];

                boolean checkfine = fineloc == PackageManager.PERMISSION_GRANTED;
                boolean checkback = backloc == PackageManager.PERMISSION_GRANTED;

                if(checkback && checkfine){
                    Toast.makeText(this, "Permissions Given", Toast.LENGTH_SHORT).show();
                    //Work With Permission Access
                }
                else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    //Work Without Permission Access
                }
            }
        }
    }

    //Check Permission Access
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public boolean checkper(){
        int resultfine = ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION);
        int resultback = ActivityCompat.checkSelfPermission(this,ACCESS_BACKGROUND_LOCATION);
        return resultfine == PackageManager.PERMISSION_GRANTED && resultback == PackageManager.PERMISSION_GRANTED;
    }
}