package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.BatchUpdateException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button ansButton1;
    Button ansButton2;
    Button ansButton3;
    Button ansButton4;
    Button startButton;
    Button playAgainButton;
    TextView scoreTextView;
    TextView questionTextView;
    TextView timeLeftTextView;
    TextView doneTextView;
    boolean gameFinished = false;
    int rightAnswerBox;

    int number1;
    int number2;
    int answer;
    Random random;
    int correctAnswer;
    int totalAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ansButton1 = findViewById(R.id.ansButton1);
        ansButton2 = findViewById(R.id.ansButton2);
        ansButton3 = findViewById(R.id.ansButton3);
        ansButton4 = findViewById(R.id.ansButton4);
        startButton = findViewById(R.id.startButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        questionTextView = findViewById(R.id.questionTextView);
        timeLeftTextView = findViewById(R.id.timeLeftTextView);
        doneTextView = findViewById(R.id.doneTextView);
        random = new Random();

    }

    public void startGame(View view){

        doneTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        ansButton1.setVisibility(View.VISIBLE);
        ansButton2.setVisibility(View.VISIBLE);
        ansButton3.setVisibility(View.VISIBLE);
        ansButton4.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
        timeLeftTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);

        doneTextView.setText("");

        timeLeftTextView.setText("30s");

        gameFinished = false;
        totalAnswers = 0;
        correctAnswer = 0;
        fillScore(correctAnswer,totalAnswers);



        startClock();
        makeQuestion();
        fillAnswers();


    }

    private void fillAnswers() {

        rightAnswerBox = random.nextInt(3)+1;
        int wrongAnswer1 = random.nextInt(40)+1;
        int wrongAnswer2=random.nextInt(40)+1;
        int wrongAnswer3=random.nextInt(40)+1;


        while(wrongAnswer1 == answer){
            wrongAnswer1 = random.nextInt(40)+1;
        }
        while(wrongAnswer2 == answer || wrongAnswer2==wrongAnswer1){
            wrongAnswer2 = random.nextInt(40)+1;
        }


        while(wrongAnswer3 == answer || wrongAnswer3==wrongAnswer1 || wrongAnswer3==wrongAnswer2){
            wrongAnswer3 = random.nextInt(40)+1;
        }


        if(rightAnswerBox == 1){
            ansButton1.setText(Integer.toString(answer));
            ansButton2.setText(Integer.toString(wrongAnswer1));
            ansButton3.setText(Integer.toString(wrongAnswer2));
            ansButton4.setText(Integer.toString(wrongAnswer3));

        }else if(rightAnswerBox == 2){
            ansButton2.setText(Integer.toString(answer));
            ansButton1.setText(Integer.toString(wrongAnswer1));
            ansButton3.setText(Integer.toString(wrongAnswer2));
            ansButton4.setText(Integer.toString(wrongAnswer3));
        }else if(rightAnswerBox == 3){
            ansButton3.setText(Integer.toString(answer));
            ansButton2.setText(Integer.toString(wrongAnswer1));
            ansButton1.setText(Integer.toString(wrongAnswer2));
            ansButton4.setText(Integer.toString(wrongAnswer3));
        }else{
            ansButton4.setText(Integer.toString(answer));
            ansButton2.setText(Integer.toString(wrongAnswer1));
            ansButton3.setText(Integer.toString(wrongAnswer2));
            ansButton1.setText(Integer.toString(wrongAnswer3));
        }

    }

    private void makeQuestion() {

        number1 =random.nextInt(20) + 1;
        number2 = random.nextInt(20) + 1;
        answer = number1 + number2;
        questionTextView.setText(Integer.toString(number1) + " + " + Integer.toString(number2) );
        fillAnswers();


    }

    private void startClock() {


        CountDownTimer timer = new CountDownTimer(30000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                int i = (int) millisUntilFinished/1000;
                timeLeftTextView.setText(Integer.toString(i) + "s");
            }

            @Override
            public void onFinish() {

                gameFinished = true;
                doneTextView.setText("Done!");
                playAgainButton.setVisibility(View.VISIBLE);


            }
        };

        timer.start();

    }

    public void checkAnswer(View view){
        if(gameFinished == false){

        Button clicked = (Button) view;
        totalAnswers = totalAnswers + 1;
        if(Integer.parseInt(clicked.getTag().toString()) == rightAnswerBox){
            correctAnswer = correctAnswer + 1;
            doneTextView.setText("CORRECT!");

        }else{doneTextView.setText("WRONG!");}
        makeQuestion();
        fillScore(correctAnswer,totalAnswers);
    }}

    private void fillScore(int correctAnswer,int totalAnswers) {
        scoreTextView.setText(Integer.toString(correctAnswer) + " / " + Integer.toString(totalAnswers));


    }


}
