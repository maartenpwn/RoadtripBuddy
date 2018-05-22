package com.example.maarten.roadtripbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddCityActivity extends AppCompatActivity {

    EditText detailUserInput;
    TextView detailRecords;
    MyDBHandler dbHandler;
    String cityName;

    private static final String TAG = "poep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        // Get the data if intended.
        Bundle theData = getIntent().getExtras();

        cityName = theData.getString("cityName");

        TextView detailCityName = (TextView) findViewById(R.id.detailCityName);
        detailCityName.setText(cityName);

        // DB shit
        detailUserInput = (EditText) findViewById(R.id.detailUserInput);
        detailRecords = (TextView) findViewById(R.id.detailRecords);

        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();
    }

    //Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString(cityName);

        detailRecords.setText(dbString);
        detailUserInput.setText("");
    }

    //add your elements onclick methods.
    //Add a product to the database
    public void addButtonClicked(View view){
        // dbHandler.add needs an object parameter.
        Activities activity = new Activities(detailUserInput.getText().toString(), cityName);

        Log.d(TAG, "addButtonClicked: stad " + cityName);

        dbHandler.addActivity(activity);
        printDatabase();

        // asdfasdf afadsfadsf
        // asdfsdfdasfadfadsfs
        // asdfasdfadsfdsf
        HttpRequest theRequest = new HttpRequest (this);
        theRequest.execute();
        // asdfasdf afadsfadsf
        // asdfsdfdasfadfadsfs
        // asdfasdfadsfdsf
    }

    //Delete items
    public void deleteButtonClicked(View view){
        // dbHandler delete needs string to find in the db
        String inputText = detailUserInput.getText().toString();
        dbHandler.deleteActivity(inputText);
        printDatabase();
    }
}
