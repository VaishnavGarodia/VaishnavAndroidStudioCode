package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class userList extends AppCompatActivity {

    ListView listView;
    ArrayList<String> usersList;
    ArrayAdapter<String> arrayAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.share_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share)
            addMission();
        if(item.getItemId() == R.id.logout)
        {
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("Users List");

        listView = (ListView) findViewById(R.id.listView);
        usersList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1, usersList);
        listView.setAdapter(arrayAdapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>(){
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null && objects.size() > 0) {
                    for (ParseUser user : objects) {
                        usersList.add(user.getUsername());
                    }
                    arrayAdapter.notifyDataSetChanged();
                }else{
                    if (e != null) {
                        Log.i("info",e.toString());
                    }else{
                        Log.i("info","No users found");
                    }

                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),UserFeed.class);
                intent.putExtra("username",usersList.get(i));
                startActivity(intent);

            }
        });


    }

    private void addMission() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && data!=null && resultCode==RESULT_OK){
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] bytes = stream.toByteArray();
                ParseFile file = new ParseFile("image.png",bytes);
                ParseObject object = new ParseObject("Image");
                object.put("image",file);
                object.put("username",ParseUser.getCurrentUser().getUsername());
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                        Toast.makeText(userList.this,"Image has been shared!",Toast.LENGTH_SHORT).show();}
                        else{
                            Log.i("info",e.toString());
                            Toast.makeText(userList.this,"There was an error uploading the image :(",Toast.LENGTH_SHORT).show();
                    }}
                });

            } catch (IOException e) {
                Log.i("info",e.toString());
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                addMission();
            }
        }
    }
}

