package com.example.maarten.roadtripbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCitysActivity extends AppCompatActivity {

    TextView detailRecords;
    MyDBHandler dbHandler;

    private static final String TAG = "poep";

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
        String dbString = dbHandler.CitydatabaseToString();

        // Hier ergens moet dbString naar array toe
        // Tijdelijk een array aangemaakt

        createCustomListAdapter();

        detailRecords.setText(dbString);
    }

    public void createCustomListAdapter() {
        String[] citys = {"Delft", "Rotterdam", "Vlaardingen", "Uku"};

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

                        Toast.makeText(MyCitysActivity.this, city, Toast.LENGTH_LONG).show();



                    }
                }
        );
    }

}
