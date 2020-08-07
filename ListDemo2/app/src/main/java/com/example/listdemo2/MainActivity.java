package com.example.listdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> friends;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friends = new ArrayList<String>();
        friendFiller();
        listView = findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,friends);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textMaker(position);

            }
        });
    }

    private void textMaker(int position) {
        Toast.makeText(this,friends.get(position),Toast.LENGTH_LONG).show();
    }

    private void friendFiller() {
        friends.add("trish");
        friends.add("ishan");
        friends.add("shakeb");
        friends.add("kanha");
    }


}
