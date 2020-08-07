package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            SQLiteDatabase database = this.openOrCreateDatabase("users", MODE_PRIVATE, null);

            database.execSQL("CREATE TABLE IF NOT EXISTS newUsers(name VARCHAR,age INT(3),id INTEGER PRIMARY KEY )");

            database.execSQL("INSERT INTO newUsers (name,age) VALUES ('Vaishnav',16)");
            database.execSQL("INSERT INTO newUsers (name,age) VALUES ('Saanvi',14)");
            database.execSQL("INSERT INTO newUsers (name,age) VALUES ('Tavishi',18)");

            database.execSQL("DELETE FROM newUsers WHERE id = '6'");

            //Cursor c = database.rawQuery("SELECT * FROM newUsers WHERE age>=18 AND name LIKE '%a%' LIMIT 1", null);
            Cursor c = database.rawQuery("SELECT * FROM newUsers", null);
            c.moveToFirst();

            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");

            while (!c.isAfterLast()) {
                Log.i("info", c.getColumnName(nameIndex) + " is " + c.getString(nameIndex));
                Log.i("info", c.getColumnName(ageIndex) + " is " + c.getInt(ageIndex));
                Log.i("info", c.getColumnName(idIndex) + " is " + c.getInt(idIndex));

                c.moveToNext();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
