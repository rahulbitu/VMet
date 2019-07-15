package com.vmet;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class WeMet_Web extends AppCompatActivity {

    Toolbar toolbar_WeMetWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_met__web);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.myStatusBarColor));

        toolbar_WeMetWeb = findViewById(R.id.toolbar_WeMetWeb);
        setSupportActionBar(toolbar_WeMetWeb);
        getSupportActionBar().setTitle("Scan code");


    }
}
