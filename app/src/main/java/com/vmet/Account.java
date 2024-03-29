package com.vmet;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    TextView privacyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.myStatusBarColor));


        privacyTextView = findViewById(R.id.privacyTextView);
        privacyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Privacy.class);
                startActivity(intent);
            }
        });
    }
}
