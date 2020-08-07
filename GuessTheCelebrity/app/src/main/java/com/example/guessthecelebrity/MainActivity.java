package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button ansButton1;
    Button ansButton2;
    Button ansButton3;
    Button ansButton4;
    int rightAnswerBox;
    Random random;
    ImageView image;
    DownloadImage downloadImage;
    DownloadInfo downloadInfo;
    ArrayList<String> imageAddresses=new ArrayList<String>();
    ArrayList <String>celebNames=new ArrayList<String>();
    String htmlCode = null;
    String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ansButton1 = findViewById(R.id.button1);
        ansButton2 = findViewById(R.id.button2);
        ansButton3 = findViewById(R.id.button3);
        ansButton4 = findViewById(R.id.button4);
        image = findViewById(R.id.imageView);
        random = new Random();
        downloadImage= new DownloadImage() ;
        downloadInfo = new DownloadInfo();
        formatHtml();
    }

    private void formatHtml() {
        DownloadInfo downloadInfo = new DownloadInfo();
        String result = null;
        try {
            result = downloadInfo.execute("http://www.posh24.se/kandisar").get();
            String[] splitResult = result.split("<div class=\"listedArticles\">");
            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);


            while(m.find()){
                imageAddresses.add(m.group(1));
            }

            p = Pattern.compile("alt=\"(.*?)\"/>");
            m = p.matcher(splitResult[0]);


            while(m.find()){
                celebNames.add(m.group(1));

            }
            int listSize = celebNames.size();

            for (int i = 0; i<listSize; i++){
                Log.i("info", celebNames.get(i));
            }
            listSize = imageAddresses.size();

            for (int i = 0; i<listSize; i++){
                Log.i("info", imageAddresses.get(i));
            }
        } catch (Exception e) {
            Log.i("info", e.toString());
        }

        createQuestion();
    }



    public class DownloadImage extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection;
            Bitmap bitmap = null;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);


            } catch (Exception e) {
                Log.i("info",e.toString());
            }



            return bitmap;



        }
    }

    public class DownloadInfo extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection = null;
            String result= null;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                int data = inputStreamReader.read();
                char current;
                while (data!=-1) {
                    current = (char) data;
                    result += current;
                    data = inputStreamReader.read();

                }
            } catch (Exception e) {
                Log.i("info",e.toString());
            }
            return result;


    }}

    private void createQuestion() {
        int noofcelebs = celebNames.size();
        int answerNumber = random.nextInt(noofcelebs);

        answer = celebNames.get(answerNumber);
        rightAnswerBox = random.nextInt(3)+1;
        String wrongAnswer1 = celebNames.get(random.nextInt(noofcelebs));
        String wrongAnswer2=celebNames.get(random.nextInt(noofcelebs));
        String wrongAnswer3=celebNames.get(random.nextInt(noofcelebs));


        while(wrongAnswer1.equals(answer)){
            wrongAnswer1 = celebNames.get(random.nextInt(noofcelebs));
        }
        while(wrongAnswer2.equals(answer) || wrongAnswer2.equals(wrongAnswer1)){
            wrongAnswer2 = celebNames.get(random.nextInt(noofcelebs));
        }


        while(wrongAnswer3.equals(answer) || wrongAnswer3.equals(wrongAnswer1) || wrongAnswer3.equals(wrongAnswer2)){
            wrongAnswer3 = celebNames.get(random.nextInt(noofcelebs));
        }


        if(rightAnswerBox == 1){
            ansButton1.setText(answer);
            ansButton2.setText(wrongAnswer1);
            ansButton3.setText(wrongAnswer2);
            ansButton4.setText(wrongAnswer3);

        }else if(rightAnswerBox == 2){
            ansButton2.setText(answer);
            ansButton1.setText(wrongAnswer1);
            ansButton3.setText(wrongAnswer2);
            ansButton4.setText(wrongAnswer3);
        }else if(rightAnswerBox == 3){
            ansButton3.setText(answer);
            ansButton2.setText(wrongAnswer1);
            ansButton1.setText(wrongAnswer2);
            ansButton4.setText(wrongAnswer3);
        }else{
            ansButton4.setText(answer);
            ansButton2.setText(wrongAnswer1);
            ansButton3.setText(wrongAnswer2);
            ansButton1.setText(wrongAnswer3);
        }

        DownloadImage downloadImage = new DownloadImage();
        try {
            Bitmap bitmap = downloadImage.execute(imageAddresses.get(answerNumber)).get();
            image.setImageBitmap(bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




    public void checkAnswer(View view){

        Button clicked = (Button) view;
        if (Integer.parseInt(clicked.getTag().toString()) == rightAnswerBox){

            Toast.makeText(this,"Right Answer", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this,"Wrong Answer,Right answer is "+answer,Toast.LENGTH_LONG).show();
        }

        createQuestion();

    }
}
