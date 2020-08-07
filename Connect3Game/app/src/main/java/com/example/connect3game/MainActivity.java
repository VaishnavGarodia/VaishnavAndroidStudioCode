package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String message;
    int activePlayer = 1;
    int[] gamestate = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void dropIn(View view){

        ImageView counter = (ImageView) view;
        int tappedCounter =Integer.parseInt(counter.getTag().toString());
        if(gamestate[tappedCounter] == 2 && gameActive){
        gamestate[tappedCounter] = activePlayer;

        counter.setTranslationY(-1500);
        if(activePlayer == 1){
        counter.setImageResource(R.drawable.yellow);
        activePlayer = 0;} else{
            counter.setImageResource(R.drawable.red);
            activePlayer = 1;
        }
        counter.animate().translationYBy(1500).setDuration(300);
        for(int[] winningPosition : winningPositions){
            if (gamestate[winningPosition[0]]==gamestate[winningPosition[1]]&&gamestate[winningPosition[1]]==gamestate[winningPosition[2]]&&gamestate[winningPosition[2]] != 2){
                if(activePlayer == 1){
                     message = "Red has won";
                }else{
                     message = "yellow has won";
                }


                gameActive = false;
                Button button = (Button) findViewById(R.id.button);
                TextView textView =(TextView) findViewById(R.id.textView);
                textView.setText(message);
                textView.setTextSize(20);
                button.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);


            }else if(evenOneEmpty()==false){
                message = "game is draw";
                gameActive = false;
                Button button = (Button) findViewById(R.id.button);
                TextView textView =(TextView) findViewById(R.id.textView);
                textView.setText(message);
                textView.setTextSize(20);
                button.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        }


        }

    }

    public void playAgain(View view){

        Button playAgainbutton = (Button) findViewById(R.id.button);
        TextView textView2 =(TextView) findViewById(R.id.textView);

        playAgainbutton.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        gameActive= true;
        activePlayer=1;
        androidx.gridlayout.widget.GridLayout gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0;i<gridLayout.getChildCount();i++){
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);

        }
        for(int i=0;i<gamestate.length;i++){
            gamestate[i] = 2;
        }




    }
    public boolean evenOneEmpty()
    {
        Integer first = 2;
        for (int i=0; i<gamestate.length; i++)
            if (gamestate[i] == first){
                return true;}
        return false;
    }

}
