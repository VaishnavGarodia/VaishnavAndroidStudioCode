package com.example.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View view){
        Log.i("Info","button is clicked!");
        EditText useredittext = (EditText) findViewById(R.id.usereditText);
        EditText passedittext = (EditText) findViewById(R.id.passeditText);
        Log.i("username",useredittext.getText().toString());
        Log.i("password",passedittext.getText().toString());
        Toast.makeText(this,"hello" + useredittext.getText().toString() )

    }
}
