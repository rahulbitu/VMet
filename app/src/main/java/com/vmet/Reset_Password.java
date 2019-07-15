package com.vmet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Reset_Password extends Base_Activity {

    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.nextButton)
    Button nextButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.myLoginBarColor));

        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.nextButton)

    public void onViewClicked() {

        checkdataValidity();

    }

    private void checkdataValidity() {


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (emailEditText.getText().toString().isEmpty()) {
            emailEditText.setError("Enter email address");
            emailEditText.requestFocus();
        } else {
            if (emailEditText.getText().toString().trim().matches(emailPattern)) {

                String email = emailEditText.getText().toString().trim();
                Intent intent = new Intent(Reset_Password.this, Access_Account.class);
                intent.putExtra("email", email);
                startActivity(intent);

            } else {

                emailEditText.setError("Invalid email address");
                emailEditText.requestFocus();
            }
        }

    }
}
