package com.vmet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vmet.BaseApplication.BaseApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class SigninPage extends Base_Activity {

    private static final String TAG = "SigninPage=> ";

    TextView signInTextView;
    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.confirmPasswordEditText)
    EditText confirmPasswordEditText;
    @BindView(R.id.signUpButton)
    Button signUpButton;
    @BindView(R.id.googleCircleView)
    CircleImageView googleCircleView;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private int  RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.myLoginBarColor));

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //showProgressBar();

        signInTextView = findViewById(R.id.signInTextView);
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SigninPage.this, LoginPage.class);
                startActivity(intent);

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }


    @OnClick({R.id.signUpButton,R.id.googleCircleView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signUpButton:
                checkDataValidity();
                break;
            case R.id.googleCircleView:
                signIn();
                break;
        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(SigninPage.this, "User signed in", Toast.LENGTH_SHORT).show();
                            /*onBackPressed();*/
                            Date currentTime = Calendar.getInstance().getTime();
                            String username = usernameFromEmail(task.getResult().getUser().getEmail());
                            writeNewUser(task.getResult().getUser().getUid(),username,task.getResult().getUser().getEmail(),currentTime);
                            Intent intent = new Intent(SigninPage.this,MainActivity.class);
                            startActivity(intent);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            BaseApplication.getInstance().getSession().setUserId(user.getUid());
                          //  BaseApplication.getInstance().getSession().setUserName(username);
                          //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SigninPage.this, "Not able to connect with Google", Toast.LENGTH_SHORT).show();
                           /* Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);*/
                        }

                        // ...
                    }
                });
    }


    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public void checkDataValidity() {

        if (validate()) {

            registerUser(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
        }

    }

    private boolean validate() {

        boolean flag = true;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError("write name");
            nameEditText.requestFocus();
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (emailEditText.getText().toString().trim().isEmpty()) {
            emailEditText.setError("Enter email address");
            emailEditText.requestFocus();
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (!emailEditText.getText().toString().trim().matches(emailPattern)) {
            emailEditText.setError("invalid email");
            emailEditText.requestFocus();
            flag = false;
        } else if (passwordEditText.getText().toString().length() < 6 && !isValidPassword(passwordEditText.getText().toString())) {
            passwordEditText.setError("Invalid password");
            passwordEditText.requestFocus();
            flag = false;
        } else if (!passwordEditText.getText().toString().trim().equals(confirmPasswordEditText.getText().toString().trim())) {
            confirmPasswordEditText.setError("not same");
            confirmPasswordEditText.requestFocus();
            flag = false;
        } else {
            Toast.makeText(this, "All entries are correct", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    private void registerUser(String email, String password) {

        final ProgressDialog progressDialog = createProgressBar(SigninPage.this);
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SigninPage.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                           /* onBackPressed();*/
                            Date currentTime = Calendar.getInstance().getTime();
                            writeNewUser(task.getResult().getUser().getUid(),nameEditText.getText().toString().trim(), emailEditText.getText().toString().trim(),currentTime);
                            Intent intent = new Intent(SigninPage.this,MainActivity.class);
                            startActivity(intent);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            BaseApplication.getInstance().getSession().setUserId(user.getUid());
                           // BaseApplication.getInstance().getSession().setUserName(nameEditText.getText().toString().trim());

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SigninPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void writeNewUser(String userId, String name, String email,Date time) {
        User user = new User(userId,name,email,time,"");

        databaseReference.child("users").child(userId).setValue(user);
    }
}