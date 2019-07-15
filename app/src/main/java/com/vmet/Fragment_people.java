package com.vmet;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Fragment_people extends Fragment {

    RecyclerView peopleRecyclerView;
    Toolbar toolbar;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private final String TAG = "Fragment_people=> ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_people, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        toolbar = view.findViewById(R.id.toolbar_people);
        toolbar.setTitle("People");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        peopleRecyclerView = view.findViewById(R.id.peopleRecyclerView);
        peopleRecyclerView.setHasFixedSize(true);
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getUserList();

        return view;
    }

    public void getUserList() {

        /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();*/
        final ProgressDialog progressDialog = new Base_Activity().createProgressBar(getActivity());
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

                Adapter_people adapter = new Adapter_people(getActivity(), userList);
                peopleRecyclerView.setAdapter(adapter);
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
