package com.example.openme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Second_Activity extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_);
        editText = findViewById(R.id.editText2);

        final SharedPreferences sharedPreferences = getSharedPreferences("com.example.openme",MODE_PRIVATE);
        editText.setText(sharedPreferences.getString("text",""));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                sharedPreferences.edit().putString("text",editText.getText().toString()).apply();
            }
        });
    }

}
