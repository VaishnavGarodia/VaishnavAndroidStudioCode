package com.example.audiodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar volumeControl;
    SeekBar audioControl;

    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioControl = (SeekBar) findViewById(R.id.scrubSeekBar);
        mediaPlayer = MediaPlayer.create(this,R.raw.closer);
        volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumeControl.setMax(maxVolume);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeControl.setProgress(currentVolume);
        volumeControl.setMax(mediaPlayer.getDuration());
        audioControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                stopa();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                playa();

            }
        });

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioControl.setProgress(mediaPlayer.getCurrentPosition());

            }
        },0,100);

    }
    public void play(View view){

        playa();
    }
    public void stop(View view){

        stopa();
    }

    public void playa(){

        mediaPlayer.start();
    }
    public void stopa(){

        mediaPlayer.pause();
    }


}
