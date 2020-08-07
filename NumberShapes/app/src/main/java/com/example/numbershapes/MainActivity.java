package com.example.numbershapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    class Number{
        int number;
        public boolean itsSquare(){
            int x = 1;
            int square = 0;
            while(number>square){
                square = x*x;
                if(number==square){

                    return true;
                }
                x++;
            }

            return false;
        }
        public boolean itsTriangle(){
            int x = 1;
            int triangle = 0;
            while(number>triangle){
                triangle = triangle+x;
                if(number==triangle){

                    return true;
                }
                x++;
            }

            return false;
        }


    }  public void buttonPressed(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        if(editText.getText().toString().isEmpty()){
            Toast.makeText(this,"Please enter a number",Toast.LENGTH_LONG).show();
        }else {
            String numberString = editText.getText().toString();

            Integer no = Integer.parseInt(numberString);
            Number firstnumber = new Number();
            firstnumber.number = no;
            boolean Square = firstnumber.itsSquare();
            boolean Triangle = firstnumber.itsTriangle();
            if (Triangle && Square) {
                Toast.makeText(this, "Its both triangular and square number!", Toast.LENGTH_LONG).show();
            } else if (Square) {
                Toast.makeText(this, "Its a square number!", Toast.LENGTH_LONG).show();
            } else if (Triangle) {
                Toast.makeText(this, "its a Triangular number", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "its neither of them", Toast.LENGTH_LONG).show();
            }
        }



    }
}
