package com.example.maarten.roadtripbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "myTag";

    private final View mWindow;
    private Context mContext;
    private String city;

    // Create a custom info window and add the cityname
    public CustomInfoWindowAdapter(Context mContext, String cityName) {
        this.mContext = mContext;
        city = cityName;

        mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view) {
        TextView windowCityName = (TextView) view.findViewById(R.id.windowCityName);
        windowCityName.setText(city);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}

