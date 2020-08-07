package com.example.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void ,String>{

        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection httpURLConnection = null;
            String result = null ;
            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                int data = inputStreamReader.read();

                while (data!=-1){
                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }
            } catch(Exception e){
                e.printStackTrace();
                result = "failed";
            }

            return result;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask downloadTask = new DownloadTask();
        String result =null;

        try {
            result = downloadTask.execute("https://zappycode.com/").get();
        }catch (Exception e){
            e.printStackTrace();
            result= "failed";
        }

        Log.i("info",result);
    }
}
