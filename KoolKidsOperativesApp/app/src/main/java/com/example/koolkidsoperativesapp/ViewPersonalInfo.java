package com.example.koolkidsoperativesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ViewPersonalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpersonalinfo);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        ParseQuery query = ParseQuery.getQuery("PersonalInfo");
        query.whereEqualTo("username",username);
        query.findInBackground(new FindCallback<ParseObject>(){
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null && objects.size() > 0) {
                    for (ParseObject info : objects) {
                        String information = "Name: " + info.getString("name") + "\r\nemail: "+
                        info.get("email")+ "\r\naddress: "+
                        info.get("address")+"\r\nphone: "+
                        info.get("phone")+"\r\nemergency Phone Number: "+
                        info.get("emergencyPhone");
                        Log.i("info",information);
                        TextView editText = findViewById(R.id.textView);
                        editText.setText(information);
                    }
                }else if(objects.size()==0) {
                    String information = "The Operative hasn't saved \r\n any personal Information yet! ";
                    Log.i("info",information);
                    TextView editText = findViewById(R.id.textView);
                    editText.setText(information);
                }else{

                        Log.i("info",e.toString());
                    }
                }



        });

    }
    public void goBack(View view){
        Intent intent = new Intent(getApplicationContext(),userList.class);
        startActivity(intent);
    }
}