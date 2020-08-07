package com.example.animations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Boolean bartshows = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bartClicked(View view){
        ImageView bart = (ImageView) findViewById(R.id.bart);
        ImageView homer = (ImageView) findViewById(R.id.homer);
if(bartshows) {
    bart.animate().alpha(0).setDuration(2000);
    homer.animate().alpha(1).setDuration(2000);
    bartshows=false;
}else{

    homer.animate().alpha(0).setDuration(2000);
    bart.animate().alpha(1).setDuration(2000);
    bartshows = true;
}
    }

}
