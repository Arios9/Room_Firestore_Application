package com.example.room_firestore_application.MyActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.room_firestore_application.R;

public class WebManualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_manual);

        WebView manual = (WebView)findViewById(R.id.myManual);
        manual.setWebViewClient(new WebViewClient());
        manual.loadUrl("https://docs.google.com/document/d/172vtxqmoygmII3ELQx2mjirxfL8uUJ5vkN6Mdh-e8FI/edit");
    }
}