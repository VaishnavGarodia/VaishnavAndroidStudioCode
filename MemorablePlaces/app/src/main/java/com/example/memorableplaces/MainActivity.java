package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static  ArrayList<String> arrayList;
    static ArrayList<LatLng> latLngs;
    static ArrayAdapter<String> arrayAdapter;
    Intent mapsActivtyintent;
    static SharedPreferences sharedPreferences;
    static ObjectSerializer objectSerializer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);
        objectSerializer = new ObjectSerializer();
        latLngs = new ArrayList<LatLng>();
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        arrayList.add("Add a new place...");
        latLngs.add(new LatLng(0,0));




        if(sharedPreferences.getString("list","").length()>0&&sharedPreferences.getString("lat","").length()>0&&sharedPreferences.getString("lon","").length()>0){
            try {
                arrayList = (ArrayList<String>) objectSerializer.deserialize(sharedPreferences.getString("list", String.valueOf(new ArrayList<String>())));
                ArrayList<Double> lat = (ArrayList<Double>) objectSerializer.deserialize(sharedPreferences.getString("lat",String.valueOf(new ArrayList<String>())));
                ArrayList<Double> lon = (ArrayList<Double>) objectSerializer.deserialize(sharedPreferences.getString("lon",String.valueOf(new ArrayList<String>())));
                for(int i=0;i<lat.size();i++) {
                    latLngs.add(new LatLng(lat.get(i),lon.get(i)));
                }
                MainActivity.sharedPreferences.edit().putString("lon",MainActivity.objectSerializer.serialize(lon)).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

            try {
                sharedPreferences.edit().putString("list",objectSerializer.serialize(arrayList)).apply();
                sharedPreferences.edit().putString("lat",objectSerializer.serialize(latLngs.get(0).latitude)).apply();
                sharedPreferences.edit().putString("list",objectSerializer.serialize(latLngs.get(0).longitude)).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        mapsActivtyintent = new Intent(getApplicationContext(),MapsActivity.class);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    mapsActivtyintent.putExtra("position",-1);
                    startActivity(mapsActivtyintent);
                }else{
                    mapsActivtyintent.putExtra("position",position);
                    startActivity(mapsActivtyintent);
                }
            }
        });
    }
}
