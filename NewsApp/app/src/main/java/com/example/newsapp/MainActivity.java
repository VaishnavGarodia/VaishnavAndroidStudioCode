package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    ArrayList<String> titleList;
    ArrayAdapter arrayAdapter;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeDatabase();
        makeList();


    }

    private void makeDatabase() {
        DownloadHtml downloadHtml = new DownloadHtml();
        try {

            database = this.openOrCreateDatabase("news", MODE_PRIVATE, null);

            database.execSQL("CREATE TABLE IF NOT EXISTS news(id INTEGER PRIMARY KEY,newsID VARCHAR,title VARCHAR,url VARCHAR)");
            database.execSQL("DELETE FROM news");
            String s = downloadHtml.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();

            String crappyPrefix = "null";

            if (s.startsWith(crappyPrefix)) {
                s = s.substring(crappyPrefix.length());
            }

            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                if (i > 20) {
                    break;
                } else {
                    String id = jsonArray.getString(i);
                    String[] result = downloadFromId(id);
                    String title = result[0];
                    String url = result[1];
                    //String html = "";
                    //html = downloadHtml.execute(url).get();
                    //Log.i("info",html);
                    String sql = "INSERT INTO news (newsID, title, url) VALUES (?, ?, ?)";
                    SQLiteStatement statement = database.compileStatement(sql);
                    statement.bindString(1,id);
                    statement.bindString(2,title);
                    statement.bindString(3,url);

                    statement.execute();
                }
            }
            makeList();
        } catch (Exception e) {
            Log.i("info", e.toString());

        }
    }
    private void makeList() {
        titleList = new ArrayList<String>();
        Cursor c = database.rawQuery("SELECT title FROM news", null);
        c.moveToFirst();

        int titleIndex = c.getColumnIndex("title");

        while (!c.isAfterLast()) {
            titleList.add(c.getString(titleIndex));
            c.moveToNext();
        }
        listView =findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,titleList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ArticleReader.class);
                int id2 = position+1;
                Cursor cursor = database.rawQuery("SELECT url FROM news WHERE id = " + id2 + " LIMIT 1", null);
                cursor.moveToFirst();
                int urlIndex = cursor.getColumnIndex("url");
                String url = cursor.getString(urlIndex);
                intent.putExtra("URL",url);
                startActivity(intent);
            }
        });

    }
    private String[] downloadFromId(String id){
        String[] result = new String[]{"",""};
        DownloadHtml downloadHtml = new DownloadHtml();
        try {
            String s = downloadHtml.execute("https://hacker-news.firebaseio.com/v0/item/"+id+".json?print=pretty").get();
            String crappyPrefix = "null";

            if(s.startsWith(crappyPrefix)){
                s= s.substring(crappyPrefix.length());
            }

            JSONObject jsonObject = new JSONObject(s);
            String title =  jsonObject.getString("title");
            String url = jsonObject.getString("url");
            result[0] = title;
            result[1] = url;
        } catch (Exception e) {
            Log.i("info", e.toString());

        }
        return result;
    }
    public class DownloadHtml extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection = null;
            String result = null;
                try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                int data = inputStreamReader.read();
                while(data!=-1){
                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }



            } catch (Exception e) {
                Log.i("info",e.toString());
            }

            return result;


        }

    }
}
