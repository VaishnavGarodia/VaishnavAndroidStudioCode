package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View view){
        EditText text = (EditText) findViewById(R.id.editText);
        String stringRupee = text.getText().toString();
        Double dollar = Double.parseDouble(stringRupee)/75.98;
        String stringDollar = String.format("%.2f",dollar);
        Toast.makeText(this, text.getText().toString() + " rupees is equal to " + stringDollar + " dollars.",Toast.LENGTH_LONG).show();



    }
}
