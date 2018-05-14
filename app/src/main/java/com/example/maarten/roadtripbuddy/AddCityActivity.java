package com.example.maarten.roadtripbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AddCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        // Get the data if intended.
        Bundle theData = getIntent().getExtras();

        String cityName = theData.getString("cityName");

        TextView detailCityName = (TextView) findViewById(R.id.detailCityName);
        detailCityName.setText(cityName);
    }
}
