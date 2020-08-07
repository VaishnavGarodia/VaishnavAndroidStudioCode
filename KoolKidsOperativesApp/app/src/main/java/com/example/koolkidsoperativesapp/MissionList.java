package com.example.koolkidsoperativesapp;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MissionList extends AppCompatActivity {


    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_list);
        arrayList = new ArrayList<>();
        arrayList.add("View Personal Information.");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        Intent intent = getIntent();
        listView = findViewById(R.id.listView2);
        listView.setAdapter(arrayAdapter);
        final String username=intent.getStringExtra("username");
        if(username.equals(ParseUser.getCurrentUser().getUsername())){
            setTitle("Your Mission's");
        }
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Mission");
        parseQuery.whereEqualTo("username",username);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    for (ParseObject object : objects){
                        String mission = object.getString("mission");
                        arrayList.add(mission);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent = new Intent(getApplicationContext(),ViewPersonalInfo.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
            }
        });
    }
}
