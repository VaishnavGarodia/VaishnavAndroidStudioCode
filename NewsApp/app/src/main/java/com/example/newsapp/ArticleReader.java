package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleReader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_reader);
        WebView webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        Log.i("info",getIntent().getStringExtra("URL"));
        webView.loadUrl(getIntent().getStringExtra("URL"));
       //webView.loadData("<html><body><h1>Hello World!</h1><p1>This is my cool new app!</p1></body></html>","text/html","utf-8");
    }
}
