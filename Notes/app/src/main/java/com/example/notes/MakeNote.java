package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashSet;

public class MakeNote extends AppCompatActivity {
    Intent intent;
    EditText editText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);
        intent = getIntent();
        editText=findViewById(R.id.editText);
        position = intent.getIntExtra("position",-1);
        if(position==-1){
            newNote();
        }else{
            editNote(position);
        }
        setTextChangeListener();
    }

    private void setTextChangeListener() {
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // Do something after Text Change
                if(position==-1){
                    MainActivity.notesArray.add(editText.getText().toString());
                    MainActivity.notesSet = new HashSet<String>(MainActivity.notesArray);
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", MODE_PRIVATE);
                    sharedPreferences.edit().putStringSet("notes", MainActivity.notesSet).apply();
                    position = MainActivity.notesArray.size() - 1;
                    Log.i("info",String.valueOf(position));

                }else {
                    MainActivity.notesArray.set(position, editText.getText().toString());
                    MainActivity.notesSet = new HashSet<String>(MainActivity.notesArray);
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", MODE_PRIVATE);
                    sharedPreferences.edit().putStringSet("notes", MainActivity.notesSet).apply();

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do something before Text Change
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do something while Text Change
            }
        });
    }

    private void editNote(int position) {
        editText.setText(MainActivity.notesArray.get(position));
    }

    private void newNote() {
        editText.setText("");
    }

}
