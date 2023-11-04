package com.example.weather_project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerWeatherAdaptor extends FragmentPagerAdapter {
    public ViewPagerWeatherAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0)
        {return new Today();}
        else if (position==1) {
            return new Tomorrow();
        }
        else {
            return new TenDays();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0)
        {
            return "TODAY";

        } else if (position==1) {
            return "TOMORROW";

        }
        else {
            return "AFTERWARDS";
        }
    }
}
