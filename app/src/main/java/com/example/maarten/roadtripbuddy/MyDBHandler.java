package com.example.maarten.roadtripbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final String TAG = "myTag";
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "activityDB.db";

    public static final String TABLE_ACTIVITIES = "activities";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACTIVITYNAME = "activityname";
    public static final String COLUMN_CITYNAME = "cityname";

    // Pass information to the superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // In the oncreate we make up the query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ACTIVITIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACTIVITYNAME + " TEXT, " +
                COLUMN_CITYNAME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
        onCreate(db);
    }

    // Add a new to row to the database
    // we save the activityname and the cityname
    public void addActivity(Activities activity){
        ContentValues values = new ContentValues();

        values.put(COLUMN_ACTIVITYNAME, activity.get_activityname());
        values.put(COLUMN_CITYNAME, activity.get_cityname());

        Log.d(TAG, "addActivity: " + values);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ACTIVITIES, null, values);
        db.close();
    }

    // Delete a row from the database
    public void deleteActivity(String activityName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ACTIVITIES + " WHERE " + COLUMN_ACTIVITYNAME + "=\"" + activityName + "\";");
    }

    // Function to print activities of the selected city
    public String databaseToString(String selectedCity){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ACTIVITIES + " WHERE 1";

        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        // While we have not reached the last item of the recordset
        while (!recordSet.isAfterLast()) {
            // If the record has a activityname
            if (recordSet.getString(recordSet.getColumnIndex("activityname")) != null) {

                String city = recordSet.getString(recordSet.getColumnIndex("cityname"));

                // If the city from the record equals the selectedCity
                // print the activity's from this city
                if(city.equals(selectedCity)){
                    dbString += recordSet.getString(recordSet.getColumnIndex("activityname"));
                    dbString += "\n";
                }

            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

    // Function to print all saved city's
    public String CitydatabaseToString(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ACTIVITIES + " WHERE 1";

        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        // While we have not reached the last item of the recordset
        while (!recordSet.isAfterLast()) {
            // If the record has a activityname
            if (recordSet.getString(recordSet.getColumnIndex("cityname")) != null) {

                // Check if the city is not already in the dbString
                if(!dbString.contains(recordSet.getString(recordSet.getColumnIndex("cityname")))){
                    dbString += recordSet.getString(recordSet.getColumnIndex("cityname"));
                    dbString += ",";
                }

            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

}
