package com.example.higherorlowergame;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int no = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberGenerator();



    }
    public void buttonClicked(View view){

        EditText input = (EditText) findViewById(R.id.editText);
        String guess = input.getText().toString();
        int guessno = Integer.parseInt(guess);
        if(guessno==no){
            Toast.makeText(this,"Your guess is right!,Try again",Toast.LENGTH_LONG).show();
            numberGenerator();

        }
        else if(guessno < no){
            Toast.makeText(this,"The number is higher!",Toast.LENGTH_LONG).show();
        }
        else if(guessno > no){
            Toast.makeText(this,"The number is lower!",Toast.LENGTH_LONG).show();
        }
     }
     public void numberGenerator(){
         Random number = new Random();
         no = number.nextInt(20) + 1 ;

     }
}
