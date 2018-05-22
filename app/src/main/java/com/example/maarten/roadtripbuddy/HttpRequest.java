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

    private static final String TAG = "myLog";
    Context context;
    ProgressDialog pd;

    // The post url
    String urlAddress="https://stud.hosted.hr.nl/0882911/restfull/books";

    // The fields we create static
    String titleField = "veld10";
    String authorField = "veld10";
    String genreField = "veld10";

    /*
        1.OUR CONSTRUCTOR
        2.RECEIVE CONTEXT,URL ADDRESS AND EDITTEXTS FROM OUR MAINACTIVITY
    */
    public HttpRequest(Context c) {
        this.context = c;
    }

    /*
        1.SHOW PROGRESS DIALOG WHILE DOWNLOADING DATA
    */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(context);
        pd.setTitle("Send");
        pd.setMessage("Sending..Please wait");
        pd.show();
    }

    /*
    1.WHERE WE SEND DATA TO NETWORK
    2.RETURNS FOR US A STRING
     */
    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }

    /*
      1. CALLED WHEN JOB IS OVER
      2. WE DISMISS OUR PD
      3.RECEIVE A STRING FROM DOINBACKGROUND
   */
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

    /*
    SEND DATA OVER THE NETWORK
    RECEIVE AND RETURN A RESPONSE
     */
    private String send()
    {
        //CONNECT
        HttpURLConnection con = connect(urlAddress);

        if(con==null) {
            return null;
        }

        try {
            OutputStream os = con.getOutputStream();

            //WRITE
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(packData());

            bw.flush();

            //RELEASE RES
            bw.close();
            os.close();

            //HAS IT BEEN SUCCESSFUL?
            int responseCode = con.getResponseCode();

            if(responseCode == con.HTTP_OK)
            {
                //GET EXACT RESPONSE
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line;

                //READ LINE BY LINE
                while ((line = br.readLine()) != null)
                {
                    response.append(line);
                }

                //RELEASE RES
                br.close();

                return response.toString();

            }else
            {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Losse connect class eigenlijk
    /*
    1.SHALL HELP US ESTABLISH A CONNECTION TO THE NETWORK
    2. WE ARE MAKING A POST REQUEST
    */
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


    /*
        SECTION 2
        1.PACK THEM INTO A JSON OBJECT
        2. READ ALL THIS DATA AND ENCODE IT INTO A FROMAT THAT CAN BE SENT VIA NETWORK
    */
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