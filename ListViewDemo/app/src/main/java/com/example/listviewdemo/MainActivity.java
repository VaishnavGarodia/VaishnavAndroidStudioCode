package com.example.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> family = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);

        family.add("papa");
        family.add("mummy");
        family.add("sanu");
        family.add("me");

        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,family);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = family.get(position);
                Log.i("the person is",name);
            }
        });


    }
}
