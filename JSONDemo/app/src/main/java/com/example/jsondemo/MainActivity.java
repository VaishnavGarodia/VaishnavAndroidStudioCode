package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadWeather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection = null;
            String result = null;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                int data = inputStreamReader.read();
                while(data!=-1){
                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }



            } catch (Exception e) {
                Log.i("info",e.toString());
            }

            return result;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String crappyPrefix = "null";

            if(s.startsWith(crappyPrefix)){
                s= s.substring(crappyPrefix.length());
            }
            try {
            JSONObject jsonObject = new JSONObject(s);
            String weather = jsonObject.getString("weather");
            Log.i("info",weather);
            JSONArray jsonArray = new JSONArray(weather);
                JSONObject jsonPart=null;
            for(int i=0;i<jsonArray.length();i++) {
                jsonPart = jsonArray.getJSONObject(i);

                Log.i("info", jsonPart.getString("main"));
                Log.i("info", jsonPart.getString("description"));
            }


            } catch (Exception e) {
                Log.i("info",e.toString());
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String result = null;

        DownloadWeather downloadWeather = new DownloadWeather();
        try {
            result = downloadWeather.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02").get();


        } catch (Exception e) {
            Log.i("info",e.toString());




    }
}
