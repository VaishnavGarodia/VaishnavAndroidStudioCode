package com.example.sharedpreferencesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("me");
        arrayList.add("mom");
        arrayList.add("dad");
        ObjectSerializer objectSerializer = new ObjectSerializer();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.sharedpreferencesdemo", Context.MODE_PRIVATE);
        try {
            sharedPreferences.edit().putString("string",objectSerializer.serialize(arrayList)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Log.i("info", String.valueOf((ArrayList<String>) objectSerializer.deserialize(sharedPreferences.getString("string", String.valueOf(new ArrayList<String>())))));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
