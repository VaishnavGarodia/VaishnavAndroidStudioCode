package com.example.timersdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000,1000){
            public void onTick(long millisecondsUntilDone){
                Log.i("info",Long.toString(millisecondsUntilDone));
            }

            public void onFinish(){
                Log.i("info","Countdown finished");
                runner();
            }
        }.start();



    }

    public void runner(){
    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("info","one second passed");
            handler.postDelayed(this,1000);
        }
    };

    handler.post(runnable);
    }
}
