package com.vmet;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity=> ";
    BottomNavigationView appBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.myStatusBarColor));

        appBottomNavigation = findViewById(R.id.appBottomNavigation);

        loadFragment(new Fragment_chat());

        appBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.chat:
                        fragment = new Fragment_chat();
                        loadFragment(fragment);
                        break;
                    case R.id.status:
                        fragment = new Fragment_status();
                        loadFragment(fragment);
                        break;
                    case R.id.call:
                        fragment = new Fragment_call();
                        loadFragment(fragment);
                        break;
                    case R.id.people:
                        fragment = new Fragment_people();
                        loadFragment(fragment);
                        break;
                    case R.id.setting:
                        fragment = new Fragment_setting();
                        loadFragment(fragment);
                        break;
                }
                return true;
            }
        });
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayout, fragment );
      //  transaction.addToBackStack(null);
        transaction.commit();
    }

}


