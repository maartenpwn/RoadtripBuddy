package com.example.maarten.roadtripbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyCitysActivity extends AppCompatActivity {

    TextView detailRecords;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_citys);


        detailRecords = (TextView) findViewById(R.id.detailRecords);

        dbHandler = new MyDBHandler(this, null, null, 1);
        printCitys();
    }

    //Print the database
    public void printCitys(){
        String dbString = dbHandler.TESTdatabaseToString();

        detailRecords.setText(dbString);
    }


}
