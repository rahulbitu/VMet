package com.vmet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Access_Account extends Base_Activity {

    @BindView(R.id.sendEmailTextView)
    TextView sendEmailTextView;
    @BindView(R.id.sendMessageTextView)
    TextView sendMessageTextView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access__account);
        ButterKnife.bind(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.myLoginBarColor));
        firebaseAuth = FirebaseAuth.getInstance();


    }

    @OnClick({R.id.sendEmailTextView, R.id.sendMessageTextView})



    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.sendEmailTextView:

                final ProgressDialog progressDialog = createProgressBar(Access_Account.this);
                progressDialog.show();

                String email = getIntent().getExtras().getString("email");

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(Access_Account.this, "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Access_Account.this, "Fail to send reset password email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            case R.id.sendMessageTextView:
                break;
        }
    }
}
