package com.vmet;

import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class New_Group extends Base_Activity {

    Toolbar toolbar_newGroup;
    RecyclerView newGroupRecyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private final String TAG = "Fragment_people=> ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__group);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.myStatusBarColor));
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        newGroupRecyclerView = findViewById(R.id.newGroupRecyclerView);

        toolbar_newGroup = findViewById(R.id.toolbar_newGroup);
        setSupportActionBar(toolbar_newGroup);
        getSupportActionBar().setTitle("New Group");
        /*getSupportActionBar().setSubtitle("Add participants");*/

        /*ArrayList<Model_newGroup> myNewGroupArray = new ArrayList<>();

        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
        myNewGroupArray.add(new Model_newGroup(R.drawable.mensclothes,"Raman","Hey there! I am using WeMet"));
*/



        newGroupRecyclerView.setHasFixedSize(true);
        newGroupRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getUserList();
      /*  Adapter_newGroup adapter = new Adapter_newGroup(myNewGroupArray, getApplicationContext());
        newGroupRecyclerView.setAdapter(adapter);*/


    }


    public void getUserList() {

        /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();*/
        final ProgressDialog progressDialog = createProgressBar(New_Group.this);
        progressDialog.show();

        final List<User> userList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                progressDialog.dismiss();

                userList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User userInfo = postSnapshot.getValue(User.class);
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    /* user.getUid();*/
                    Log.d(TAG,"userid  "+user.getUid());

                    if (!userInfo.getUserid().equals(user.getUid())) {
                        userList.add(userInfo);
                        Log.d(TAG, "userlist");
                    }

                    Log.d(TAG, "loop");
                }
                Log.d(TAG, "userlist" + userList);

                //   Toast.makeText(getActivity(), "userInfo "+userList+"/n", Toast.LENGTH_SHORT).show();

                Adapter_newGroup adapter = new Adapter_newGroup(getApplicationContext(), userList);
                newGroupRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }
}
