package com.example.maarten.roadtripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MyCitysActivity extends AppCompatActivity {

    // TextView detailRecords;
    MyDBHandler dbHandler;

    private static final String TAG = "poep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_citys);


        // detailRecords = (TextView) findViewById(R.id.detailRecords);

        dbHandler = new MyDBHandler(this, null, null, 1);
        printCitys();
    }

    //Print the database
    public void printCitys(){
        String dbString = dbHandler.CitydatabaseToString();

        // Convert the string to array
        String[] citys = dbString.split(",");

        createCustomListAdapter(citys);

        // detailRecords.setText(dbString);
    }

    public void createCustomListAdapter(String[] citys) {


        // Adding the custom adapter here.
        ListAdapter myAdapter = new CustomAdapter(this, citys);
        ListView myListView = (ListView) findViewById(R.id.myListView);

        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Haal uit de lijst van een bepaalde positie de item
                        // value op (naam) en convert die in een string
                        String city = String.valueOf(parent.getItemAtPosition(position));

                        Intent i = new Intent(MyCitysActivity.this, AddCityActivity.class);
                        i.putExtra("cityName", city);
                        startActivity(i);

                    }
                }
        );
    }

}
