package com.example.maarten.roadtripbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddCityActivity extends AppCompatActivity {

    private static final String TAG = "myTag";

    EditText detailUserInput;
    TextView detailRecords;
    MyDBHandler dbHandler;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        // Get the intended data
        Bundle theData = getIntent().getExtras();
        cityName = theData.getString("cityName");

        TextView detailCityName = (TextView) findViewById(R.id.detailCityName);
        detailCityName.setText(cityName);

        detailUserInput = (EditText) findViewById(R.id.detailUserInput);
        detailRecords = (TextView) findViewById(R.id.detailRecords);

        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();
    }

    // Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString(cityName);

        detailRecords.setText(dbString);
        detailUserInput.setText("");
    }

    // When you click add we add the activty to the selected city and also safe it in the db
    public void addButtonClicked(View view){
        Activities activity = new Activities(detailUserInput.getText().toString(), cityName);
        dbHandler.addActivity(activity);
        printDatabase();

        /* **************************************************************** */
        // For this school exercise i have to show that i am able to send
        // a post request to a webservice. Because im not required to create
        // a webservice i send static data to my webservice just to prove
        /* **************************************************************** */

        // create a post request and execute it
        HttpRequest theRequest = new HttpRequest (this);
        theRequest.execute();
    }

    // Function to delete a activity from the list
    public void deleteButtonClicked(View view){
        // dbHandler delete needs string to find in the db
        String inputText = detailUserInput.getText().toString();
        dbHandler.deleteActivity(inputText);
        printDatabase();
    }
}
