/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;

import com.parse.ParseException;

import com.parse.ParseUser;

import com.parse.SignUpCallback;



public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

  RelativeLayout relativeLayout;
  ImageView imageView;
  EditText username;
  EditText password;
  Button main;
  int state = 1;
  ParseUser user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    imageView = (ImageView)findViewById(R.id.logo);
      imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideKeyBoard(view);
        }
    });
    setTitle("Instagram");

    relativeLayout = (RelativeLayout) findViewById(R.id.bg);
    username =(EditText) findViewById(R.id.username);
    password =(EditText) findViewById(R.id.password);
    main = (Button) findViewById(R.id.main);
    password.setOnKeyListener(this);
    relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideKeyBoard(view);
        }
    });
    user = new ParseUser();
    if(ParseUser.getCurrentUser() != null){
        Log.i("info","logged in as " + ParseUser.getCurrentUser().getUsername() );
        Intent intent = new Intent(MainActivity.this,userList.class);
        startActivity(intent);
    }
    /*
     How to put objects in

    ParseObject score = new ParseObject("Score");
    score.put("username","nick");
    score.put("score",99);
    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e==null){
          Log.i("info","successful");
        }else{
          Log.i("info",e.toString());
        }
      }
    });*/
    /* THis is on how to use query to get objects
    ParseQuery query = ParseQuery.getQuery("Score");
    query.getInBackground("BlnxYeCAvR", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(e==null){
          object.put("score",55);
          object.saveInBackground();
          String username = object.getString("username");
        Log.i("info",username);
        int score = object.getInt("score");
        Log.i("info",Integer.toString(score));}
        else{
          Log.i("info",e.toString());
        }
      }
    });*/
    /* This is on how to use query to find objects


    ParseQuery query = ParseQuery.getQuery("Score");
    query.whereGreaterThan("score",60);
    query.findInBackground(new FindCallback<ParseObject>(){
      @Override
      public void done(List<ParseObject> objects, ParseException e) {

        if (e == null && objects.size() > 0) {
          for (ParseObject object : objects) {
            object.put("score", object.getInt("score") + 20);
            object.saveInBackground();
            String username = object.getString("username");
            int score = object.getInt("score");
            Log.i("info", username);
            Log.i("info", Integer.toString(score));
          }
        }else{
          assert e != null;
          Log.i("info",e.toString());
        }
      }
    });*/
    /*
    //Sign up code
    ParseUser user = new ParseUser();
    user.setUsername("vaishnav");
    user.setPassword("myPass");

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if( e == null){
          Log.i("info","sign up successful!");
        }else{
          Log.i("info",e.toString());
        }
      }
    });*/
    /*//Log in code

    ParseUser parseUser = new ParseUser();
    parseUser.logInInBackground("vaishnav", "myPass", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(e == null && user != null){
          Log.i("info","signed in as " + user.getUsername() );
        }else{
          Log.i("info",e.toString());
        }
      }
    });
  */
    /* //Check log in
    if(ParseUser.getCurrentUser()!=null){
      Log.i("info","Logged in as" + ParseUser.getCurrentUser().getUsername());
    }else{
      Log.i("info","Not logged in");
    }*/
    /*LOg out code

   ParseUser.getCurrentUser().logOut();
   */

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

    private void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public void main(View view){
    final String name = username.getText().toString();
    final String pass = password.getText().toString();
    if(state == 0){
      //sign up
        signUp(name,pass);



            }else{
        //log in

        signIn(name,pass);
      }
  }

    private void signIn(String name, String pass) {
        ParseUser.logInInBackground(name, pass , new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null && user != null){
                    Log.i("info","logged in as " + user.getUsername() );
                    Intent intent = new Intent(MainActivity.this,userList.class);
                    startActivity(intent);
                }else{
                    if(e.getCode()==101){
                        Toast.makeText(getApplicationContext(),"username or password is incorrect",Toast.LENGTH_SHORT).show();
                    }
                    Log.i("info",e.toString());
                }
            }
        });
    }

    private void signUp(String name, String pass) {
        if(!name.matches("") && !pass.matches("")){
            if(password.length() >= 8){
                user.setUsername(name);
                user.setPassword(pass);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //Signed up
                            Log.i("info", "successful!");
                            Intent intent = new Intent(MainActivity.this,userList.class);
                            startActivity(intent);
                        } else {

                            if (e.getCode() == ParseException.USERNAME_TAKEN) {// report error
                                Toast.makeText(getApplicationContext(), "Sorry this Username is already taken ,try something else", Toast.LENGTH_SHORT).show();
                            }
                            else if (e.getCode() == ParseException.USERNAME_TAKEN) {// report error
                                Toast.makeText(getApplicationContext(), "Sorry this Username is already taken ,try something else", Toast.LENGTH_SHORT).show();
                            }
                            else {// Something else went wrong
                                Log.i("info", e.toString());
                                Log.i("info", String.valueOf(e.getCode()));
                                Toast.makeText(getApplicationContext(), "Error occurred" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }});}else{
                Toast.makeText(getApplicationContext(),"Password must be minimum 8 characters",Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(getApplicationContext(), "Enter a username and a password!", Toast.LENGTH_SHORT).show();
        }
    }

    public void change(View view){

    TextView change = (TextView) findViewById(R.id.change);
    if(state == 0){
      state = 1;
      change.setText("SIGN UP,INSTEAD");
      main.setText("LOG IN!");

    }else{
      state = 0;
      change.setText("LOG IN,INSTEAD");
      main.setText("SIGN UP!");
    }

  }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
      if(i == KeyEvent.KEYCODE_ENTER&&keyEvent.getAction()==KeyEvent.ACTION_DOWN){
          main(view);
        }
        return false;
    }
}