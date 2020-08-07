package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeed extends AppCompatActivity {

    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_feed);
        layout = (LinearLayout)findViewById(R.id.layout);
        Intent intent = getIntent();

        String username=intent.getStringExtra("username");
        setTitle(username + "'s Photos");
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Image");
        parseQuery.whereEqualTo("username",username);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    for (ParseObject object : objects){
                        ParseFile file = (ParseFile)object.get("image");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e == null && data != null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));
                                    imageView.setImageBitmap(bitmap);
                                    layout.addView(imageView);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
