package com.example.maarten.roadtripbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class HttpRequest extends AsyncTask<Void,Void,String> {

    /* **************************************************************** */
    // For this school exercise i have to show that i am able to send
    // a post request to a webservice. Because im not required to create
    // a webservice i send static data to my webservice just to prove.
    /* **************************************************************** */

    private static final String TAG = "myLog";
    Context context;
    ProgressDialog pd;

    // The url
    String urlAddress="https://stud.hosted.hr.nl/0882911/restfull/books";

    // The fields we create static
    String titleField = "veld11";
    String authorField = "veld11";
    String genreField = "veld11";

    public HttpRequest(Context c) {
        this.context = c;
    }

    // Progress dialog if people have to wait
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(context);
        pd.setTitle("Send");
        pd.setMessage("Sending..Please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }

    // When we get a response we dismiss the progress dialog
    // and receive a string if we are successful
    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        pd.dismiss();

        if(response != null) {
            //SUCCESS
            Log.d(TAG, "onPostExecute: post has been successful");
        }
        else {
            //NO SUCCESS
            Log.d(TAG, "onPostExecute: post has NOT been successful");
        }
    }

    // Send the data
    private String send()
    {
        HttpURLConnection con = connect(urlAddress);

        if(con==null) {
            return null;
        }

        try {
            OutputStream os = con.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(packData());

            bw.flush();

            bw.close();
            os.close();

            // Get the response code
            int responseCode = con.getResponseCode();

            // If the response code is succesful
            if(responseCode == con.HTTP_OK)
            {
                // Get the response
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line;

                // Read line by line
                while ((line = br.readLine()) != null)
                {
                    response.append(line);
                }

                br.close();

                return response.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Create a connection with the network and make a post request
    public static HttpURLConnection connect(String urlAddress) {

        try
        {
            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //SET PROPERTIES
            con.setRequestMethod("POST");
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setDoInput(true);
            con.setDoOutput(true);

            //RETURN
            return con;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    // Create a jsonobject with the data we want to post
    public String packData()
    {
        JSONObject jo = new JSONObject();
        StringBuffer packedData = new StringBuffer();

        try
        {
            jo.put("title", titleField);
            jo.put("author", authorField);
            jo.put("genre", genreField);

            Boolean firstValue = true;

            Iterator it = jo.keys();

            do {
                String key = it.next().toString();
                String value = jo.get(key).toString();

                if(firstValue) {
                    firstValue = false;
                }
                else {
                    packedData.append("&");
                }

                packedData.append(URLEncoder.encode(key,"UTF-8"));
                packedData.append("=");
                packedData.append(URLEncoder.encode(value,"UTF-8"));

            }while (it.hasNext());

            return packedData.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}