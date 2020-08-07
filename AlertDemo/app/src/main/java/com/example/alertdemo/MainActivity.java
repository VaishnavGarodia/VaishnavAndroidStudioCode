package com.example.alertdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String language;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.changeLanguage:
                sharedPreferences.edit().putString("language","none").apply();
                setLanguage();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLanguage();



    }

    private void setText(String language) {
        TextView textView = findViewById(R.id.textView);
        textView.setText(language);
    }

    private void setLanguage() {
        sharedPreferences = this.getSharedPreferences("com.example.alertdemo",MODE_PRIVATE);
        language = sharedPreferences.getString("language","none");
        if(language == "none"){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Choose a language")
                    .setMessage("Do you want to use this app in English or Hindi")
                    .setPositiveButton("English",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            sharedPreferences.edit().putString("language","english").apply();
                            language="english";
                            setText(language);

                        }
                    })
                    .setNegativeButton("Hindi",new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferences.edit().putString("language","hindi").apply();
                            language = "hindi";
                            setText(language);
                        }
                    })
                    .show();
        }else{
            setText(language);
        }
    }
}
