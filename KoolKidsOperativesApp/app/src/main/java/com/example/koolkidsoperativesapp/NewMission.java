package com.example.koolkidsoperativesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewMission extends AppCompatActivity {

    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Mission");
        setContentView(R.layout.activity_new_mission);

        editText = findViewById(R.id.editTextTextMultiLine);
        button = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseObject mission = new ParseObject("Mission");
                mission.put("username", ParseUser.getCurrentUser().getUsername());
                mission.put("mission",editText.getText().toString());
                mission.saveInBackground();
                Intent intent = new Intent(getApplicationContext(),userList.class);
                startActivity(intent);
            }
        });

    }
}