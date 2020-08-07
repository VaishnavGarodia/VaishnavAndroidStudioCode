package com.example.imagechanger;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void switchImage(View view){
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.image2);

    }
}
