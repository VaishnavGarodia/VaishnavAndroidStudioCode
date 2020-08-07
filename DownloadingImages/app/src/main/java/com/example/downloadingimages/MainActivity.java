package com.example.downloadingimages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView2);
    }

    public void downloadImage(View view){

        ImageDownload i = new ImageDownload();
        try {
            Bitmap imageBitmap = i.execute("https://img.favpng.com/6/9/19/sherlock-holmes-museum-221b-baker-street-the-adventures-of-sherlock-holmes-png-favpng-u3sT86AY4VCbVcVbcgkwjFdJT.jpg").get();
            imageView.setImageBitmap(imageBitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.i("info",e.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("info",e.toString());
        }

    }

    public class ImageDownload extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream in = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                Log.i("info","downloading image" + strings[0]);
                return bitmap;

            } catch (Exception e) {
                Log.i("info",e.toString());
                return  null;
            }
        }
    }
}
