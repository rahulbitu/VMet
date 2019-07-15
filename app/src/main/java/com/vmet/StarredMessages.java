package com.vmet;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class StarredMessages extends AppCompatActivity {

    Toolbar toolbar_StarredMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred_messages);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.myStatusBarColor));
        toolbar_StarredMessage = findViewById(R.id.toolbar_StarredMessage);
        setSupportActionBar(toolbar_StarredMessage);
        getSupportActionBar().setTitle("Starred messages");
    }
}
