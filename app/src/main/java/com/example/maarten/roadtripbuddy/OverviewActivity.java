package com.example.maarten.roadtripbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
    }

    public void mapButtonClicked(View v) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void cityButtonClicked(View v) {
        Intent i = new Intent(this, MyCitysActivity.class);
        startActivity(i);
    }

}
