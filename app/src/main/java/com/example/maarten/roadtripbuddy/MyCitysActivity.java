package com.example.maarten.roadtripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MyCitysActivity extends AppCompatActivity {

    private static final String TAG = "myTag";
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_citys);

        dbHandler = new MyDBHandler(this, null, null, 1);
        printCitys();
    }

    //Print the database
    public void printCitys(){
        String dbString = dbHandler.CitydatabaseToString();

        // Convert the string to array and split on the ,
        String[] citys = dbString.split(",");

        // Put the array in the custom list
        createCustomListAdapter(citys);
    }

    // function to create the custom adapter
    public void createCustomListAdapter(String[] citys) {
        // Adding the custom adapter here.
        ListAdapter myAdapter = new CustomAdapter(this, citys);
        ListView myListView = (ListView) findViewById(R.id.myListView);

        myListView.setAdapter(myAdapter);

        // Onclick go the addcity class and send the selected cityname with it
        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String city = String.valueOf(parent.getItemAtPosition(position));

                        Intent i = new Intent(MyCitysActivity.this, AddCityActivity.class);
                        i.putExtra("cityName", city);
                        startActivity(i);

                    }
                }
        );
    }

}
