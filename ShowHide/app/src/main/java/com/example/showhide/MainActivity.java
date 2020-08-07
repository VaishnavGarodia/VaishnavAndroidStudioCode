package com.example.showhide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;


    public void show(View view){

        textView = findViewById(R.id.t);
        textView.setVisibility(View.VISIBLE);

    }

    public void hide(View view){
        textView = findViewById(R.id.t);
        textView.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
