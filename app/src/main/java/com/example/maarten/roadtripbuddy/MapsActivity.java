package com.example.maarten.roadtripbuddy;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "poep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng rotterdam = new LatLng(51.9176154, 4.4851675);
        mMap.addMarker(new MarkerOptions().position(rotterdam).title("Marker in Rotterdam"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rotterdam, 10));

        // Click on map, add marker there, center the camera to the new marker
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
            getCityName(point);
            Log.d(TAG, "Value: " + point);
            }
        });
    }

    public void createMarker(LatLng point, String cityName){

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

        mMap.addMarker(new MarkerOptions().position(point).title(cityName));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
    }

    public void getCityName(final LatLng point) {
        Log.d(TAG, "getCityName: " + point);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://maps.googleapis.com/maps/api/geocode/json?latlng=" + point.latitude+","+ point.longitude + "&key=AIzaSyA5aCPbDJlQW05DJWMKyj-x5Qb5-jAsFws&result_type=administrative_area_level_2";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject js = new JSONObject(response);
                            String cityName = js.getJSONArray("results").getJSONObject(0)
                                            .getJSONArray("address_components")
                                            .getJSONObject(0)
                                            .get("long_name").toString();
                            Log.d(TAG, "" + cityName);

                            createMarker(point, cityName);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "Kan cityName niet vinden");
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "That didn't work!: ");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}