package com.example.koolkidsoperativesapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class userList extends AppCompatActivity {

    ListView listView;
    ArrayList<String> usersList;
    ArrayAdapter<String> arrayAdapter;
    Runnable runnable;
   Handler handler;
    boolean postit;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share)
            newMission();
        if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.map) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.edit) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.emergency) {
            ParseQuery query = ParseQuery.getQuery("Emergency");
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null && objects.size() > 0) {
                        for (ParseObject object : objects) {
                            object.put("username",ParseUser.getCurrentUser().getUsername());
                            object.put("emergency", "true");
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Log.i("info", "emergency triggered!");
                                    }
                                }
                            });
                        }
                    }else if(objects.size()==0) {
                        ParseObject emergency = new ParseObject("Emergency");
                        emergency.put("username",ParseUser.getCurrentUser().getUsername());
                        emergency.put("emergency","true");
                        emergency.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("info", "emergency triggered!");
                                }
                            }
                        });
                    }
                }
            });
        }
            return super.onOptionsItemSelected(item);
        }

    private void emergencyOver(String username) {
        ParseQuery query = ParseQuery.getQuery("Emergency");
        query.whereEqualTo("username",username);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        object.put("username",ParseUser.getCurrentUser().getUsername());
                        object.put("emergency", "false");
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("info","emergency handled!");
                                }
                            }
                        });
                    }
                }
            }
        });
    }



    private void newMission() {
        Intent intent = new Intent(getApplicationContext(),NewMission.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("Operatives List");

        listView = (ListView) findViewById(R.id.listView2);
        usersList = new ArrayList<String>();

        arrayAdapter = new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1, usersList);
        listView.setAdapter(arrayAdapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>(){
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null && objects.size() > 0) {
                    for (ParseUser user : objects) {
                        if(ParseUser.getCurrentUser().getUsername().equals(user.getUsername())){
                            usersList.add("Your Mission's");
                        }else{
                        usersList.add(user.getUsername());}
                    }
                    arrayAdapter.notifyDataSetChanged();
                }else{
                    if (e != null) {
                        Log.i("info",e.toString());
                    }else{
                        Log.i("info","No users found");
                    }

                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),MissionList.class);
                String username = usersList.get(i);
                if(username.equals("Your Mission's")){
                    intent.putExtra("username",ParseUser.getCurrentUser().getUsername());
                }else{
                    intent.putExtra("username",usersList.get(i));
                }

                startActivity(intent);

            }
        });
        postit = false;
        handler = new Handler();
         runnable = new Runnable() {

            @Override
            public void run() {
                Log.i("info","runnable running");
                ParseQuery query = new ParseQuery("Emergency");
                query.whereEqualTo("emergency","true");
                query.setLimit(1);
                query.findInBackground(new FindCallback<ParseObject>() {
                                           @Override
                                           public void done(final List<ParseObject> objects, ParseException e) {
                                               if(e==null && objects.size()>0){
                                                   Log.i("info","emergency");

                                                       new AlertDialog.Builder(userList.this)
                                                               .setIcon(android.R.drawable.alert_dark_frame)
                                                               .setTitle("There is an Emergency!")
                                                               .setMessage("User " + objects.get(0).getString("username") + " has faced an emergency!")
                                                               .setPositiveButton("Emergency Handled!", new DialogInterface.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(DialogInterface dialog, int which) {
                                                                       handler.postDelayed(runnable, 3000);
                                                                       emergencyOver(objects.get(0).getString("username"));

                                                                   }
                                                               })
                                                               .show();
                                                       postit = false;

                                               }else if(objects.size()==0){
                                                   postit = true;
                                                   Log.i("info"," no emergency");
                                                   handler.postDelayed(runnable, 3000);
                                               }
                                           }
                                       });
                }


        };

        handler.post(runnable);


    }
}

