package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Intent intent;
    static HashSet<String> notesSet;
    static ArrayList<String> notesArray;
    ListView listView;
    static ArrayAdapter<String> arrayAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.newNote){
            createNewNote();
            return true;
        }else{
            return false;
        }
    }

    private void createNewNote() {
        intent.putExtra("position",-1);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        sharedPreferences = this.getSharedPreferences("com.example.notes",MODE_PRIVATE);
        intent = new Intent(getApplicationContext(),MakeNote.class);
        listView.setLongClickable(true);
        makeNoteList();
        openNotes();
        noteDeleter();
    }

    private void noteDeleter() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean  onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete This?")
                        .setMessage("Do you want to delete this note!?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notesArray.remove(position);
                                notesSet = new HashSet<String>(notesArray);
                                arrayAdapter.notifyDataSetChanged();
                                sharedPreferences.edit().putStringSet("notes",notesSet).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }

        });
    }

    private void openNotes() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    private void makeNoteList() {
        notesSet = new HashSet<String>();
        notesSet = (HashSet<String>) sharedPreferences.getStringSet("notes",new HashSet<String>());
        if(notesSet.size()<1){
            notesSet.add("Example note");
            sharedPreferences.edit().putStringSet("notes",notesSet).apply();
        }
        notesArray = new ArrayList<String>(notesSet);
       arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notesArray);
        listView.setAdapter(arrayAdapter);
    }
}
