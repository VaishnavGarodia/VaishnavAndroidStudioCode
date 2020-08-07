package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void play(View view){
        Button button = (Button) view;
        String tag = button.getTag().toString();
        player = MediaPlayer.create(this, getResources().getIdentifier(tag,"raw",getPackageName()));
        player.start();

    }
}
