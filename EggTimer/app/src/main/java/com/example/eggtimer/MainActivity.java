package com.example.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    CountDownTimer timer;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    int buttonState = 0;
    SeekBar seekBar;
    Button button;
    TextView textView;
    int l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.sound);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(300);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void changeTime(int progress) {
        int minutes = progress/60;
        int seconds = progress % 60;

        String minutesString = String.format("%02d",minutes);
        String secondsString = String.format("%02d",seconds);

        textView.setText(minutesString + " : " + secondsString);
    }

    public void buttonClicked(View view){

        if(buttonState == 0){
            startTimer(seekBar.getProgress());
            buttonState = 1;
            button.setText("STOP");
            seekBar.setEnabled(false);
        }
        else{
            stopTimer();
            buttonState = 0;
            button.setText("START");
            seekBar.setEnabled(true);
        }



    }

    private void stopTimer() {
        seekBar.setProgress(l);
        timer.cancel();

    }

    public void startTimer(int progress) {

        long z = (long) progress*1000;
        timer = new CountDownTimer(z,1000){
            public void onTick(long millisecondsUntilDone){

                l = (int) millisecondsUntilDone/1000;

                changeTime(l);
            }

            @Override
            public void onFinish() {

                mediaPlayer.start();
                buttonState = 0;
                button.setText("START");
                seekBar.setProgress(30);

                seekBar.setEnabled(true);
            }
        };

        timer.start();

        
    }
}
