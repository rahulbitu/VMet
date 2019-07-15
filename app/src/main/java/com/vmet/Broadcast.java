package com.vmet;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class Broadcast extends AppCompatActivity {

    RecyclerView newBroadcastRecyclerView;
    Toolbar toolbar_newBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.myStatusBarColor));

        newBroadcastRecyclerView = findViewById(R.id.newBroadcastRecyclerView);
        toolbar_newBroadcast = findViewById(R.id.toolbar_newBroadcast);
        setSupportActionBar(toolbar_newBroadcast);
        getSupportActionBar().setTitle("New Broadcast");

        ArrayList<Model_newBroadcast> myNewBroadcastArray = new ArrayList<>();
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewBroadcastArray.add(new Model_newBroadcast(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));


        newBroadcastRecyclerView.setHasFixedSize(true);
        newBroadcastRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Adapter_broadcast adapter = new Adapter_broadcast(myNewBroadcastArray, getApplicationContext());
        newBroadcastRecyclerView.setAdapter(adapter);


    }
}
