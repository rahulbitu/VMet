package com.vmet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vmet.BaseApplication.BaseApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginPage extends Base_Activity {

    TextView signUpTextView;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.resetTextView)
    TextView resetTextView;
    @BindView(R.id.googleCircleView)
    CircleImageView googleCircleView;
    @BindView(R.id.fbCircleView)
    CircleImageView fbCircleView;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference databaseReference;

    private final String TAG = "LoginPage=> ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ButterKnife.bind(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.myLoginBarColor));

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        signUpTextView = findViewById(R.id.signUpTextView);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginPage.this, SigninPage.class);
                startActivity(intent);
            }
        });

        resetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, Reset_Password.class);
                startActivity(intent);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



    }

    @OnClick({R.id.loginButton, R.id.googleCircleView,R.id.fbCircleView})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.loginButton:
                checkDataValidity();
                hideKeyboardFrom(LoginPage.this, view);
                break;
            case R.id.googleCircleView:
                signIn();
                break;
            case R.id.fbCircleView:
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
                            Toast.makeText(LoginPage.this, "Login successful.", Toast.LENGTH_SHORT).show();
                            Date currentTime = Calendar.getInstance().getTime();
                            String username = usernameFromEmail(task.getResult().getUser().getEmail());
                            writeNewUser(task.getResult().getUser().getUid(), username, task.getResult().getUser().getEmail(), currentTime);
                            Intent intent = new Intent(LoginPage.this, MainActivity.class);
                            startActivity(intent);
                            BaseApplication.getInstance().getSession().setIsLoggedIn();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            BaseApplication.getInstance().getSession().setUserId(user.getUid());
                          //  BaseApplication.getInstance().getSession().setUserName(username);
                            finish();
                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginPage.this, "Not able to connect with Google", Toast.LENGTH_SHORT).show();
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

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailEditText.getText().toString().isEmpty()) {
            emailEditText.setError("Enter email address");
            emailEditText.requestFocus();
        } else if (emailEditText.getText().toString().trim().matches(emailPattern)) {

            if (passwordEditText.getText().toString().length() < 4 && !isValidPassword(passwordEditText.getText().toString())) {
                passwordEditText.setError("Invalid password");
                passwordEditText.requestFocus();
            } else {

               /* getUserList();*/
                signIn(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());

            }
        } else {
            emailEditText.setError("Invalid email address");
            emailEditText.requestFocus();
        }

    }

    private void signIn(String email, String password) {

        final ProgressDialog progressDialog = createProgressBar(LoginPage.this);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginPage.this, "Login successful.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginPage.this, MainActivity.class);
                            startActivity(intent);
                            BaseApplication.getInstance().getSession().setIsLoggedIn();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            BaseApplication.getInstance().getSession().setUserId(user.getUid());


                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage.this, "Authentication failed. check Email or password",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void writeNewUser(String userId, String name, String email, Date time) {
        User user = new User(userId, name, email, time,"");

        databaseReference.child("users").child(userId).setValue(user);
    }

   /* public void getUserList() {

        final ProgressDialog progressDialog = createProgressBar(LoginPage.this);
        progressDialog.show();

        final List<User> userList = new ArrayList<>();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                progressDialog.dismiss();

                userList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User userInfo = postSnapshot.getValue(User.class);
                    userList.add(userInfo);
                }
                Log.d(TAG, "userlist " + userList);

                for (int i = 0; i < userList.size(); i++) {
                    if (!userList.get(i).getEmail().equals(emailEditText.getText().toString().trim())) {

                        Log.d(TAG,"loop");

                        Toast.makeText(LoginPage.this, "User not exits", Toast.LENGTH_SHORT).show();

                    } else {

                        signIn(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
                Log.d(TAG, databaseError.getMessage());
            }
        });
*/    }

