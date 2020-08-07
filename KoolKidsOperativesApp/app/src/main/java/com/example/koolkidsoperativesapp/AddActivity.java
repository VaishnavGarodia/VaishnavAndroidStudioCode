package com.example.koolkidsoperativesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setTitle("Add your Personal Information");

    }
    public void save(View view){
        final EditText namebox = findViewById(R.id.editTextTextPersonName);
        final EditText emailbox = findViewById(R.id.editTextTextEmailAddress);
        final EditText addressbox = findViewById(R.id.editTextTextPostalAddress);
        final EditText phonebox = findViewById(R.id.editTextPhone);
        final EditText emergencyPhonebox = findViewById(R.id.emergencycontact);
        final String name = namebox.getText().toString();
        final String email = emailbox.getText().toString();
        final String address = addressbox.getText().toString();
        final String phone = phonebox.getText().toString();
        final String emergencyPhone = emergencyPhonebox.getText().toString();

        ParseQuery query = ParseQuery.getQuery("PersonalInfo");
        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>(){
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null && objects.size() > 0) {
                    for (ParseObject info : objects) {
                        info.put("username", ParseUser.getCurrentUser().getUsername());
                        info.put("name",name);
                        info.put("email",email);
                        info.put("address",address);
                        info.put("phone",phone);
                        info.put("emergencyPhone",emergencyPhone);
                        info.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    Log.i("info","successful!! new");
                                    Toast.makeText(getApplicationContext(),"Personal Info Updated!",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),userList.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }else if(objects.size()==0){
                    ParseObject info = new ParseObject("PersonalInfo");
                    info.put("username", ParseUser.getCurrentUser().getUsername());
                    info.put("name",name);
                    info.put("email",email);
                    info.put("address",address);
                    info.put("phone",phone);
                    info.put("emergencyPhone",emergencyPhone);
                    info.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                           if(e==null){
                               Log.i("info","successful!! new");
                               Toast.makeText(getApplicationContext(),"Personal Info Updated!",Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(getApplicationContext(),userList.class);
                               startActivity(intent);
                           }
                        }
                    });


                }else{
                    Log.i("info",e.toString());
                    Toast.makeText(getApplicationContext(),"There was an error, Please try again later.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}