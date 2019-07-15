package com.vmet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vmet.BaseApplication.BaseApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Fragment_chat extends Fragment {

    private RecyclerView chatRecyclerView;
    private Toolbar toolbar;
    private Context context;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private final String TAG = "Fragment_chat=> ";
    final List<User> userList = new ArrayList<>();
    private ArrayList<UserModel> userModelArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_fragment, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();


        toolbar = view.findViewById(R.id.main_app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

       /* ArrayList<Model_chat> myChatArray = new ArrayList<>();

        myChatArray.add(new Model_chat(R.drawable.mensclothes,"john","hello","12.00"));
        myChatArray.add(new Model_chat(R.drawable.mensclothes,"john","hello","12.00"));
        myChatArray.add(new Model_chat(R.drawable.mensclothes,"john","hello","12.00"));
        myChatArray.add(new Model_chat(R.drawable.mensclothes,"john","hello","12.00"));
        myChatArray.add(new Model_chat(R.drawable.mensclothes,"john","hello","12.00"));*/

        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getUserList();

        return view;

    }


    public void getUserList() {


        final ProgressDialog progressDialog = new Base_Activity().createProgressBar(getActivity());
        progressDialog.show();


        /*FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User userInfo = postSnapshot.getValue(User.class);
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    *//* user.getUid();*//*
                    Log.d(TAG,"userid  "+user.getUid());

                    if (!userInfo.getUserid().equals(user.getUid())) {

                        userList.add(userInfo);
                        Log.d(TAG, "userlist");
                    }

                    Log.d(TAG, "loop");
                }
                Log.d(TAG, "userlist" + userList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



        databaseReference.child(BaseApplication.getInstance().getSession().getUserId())
                .child("rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                progressDialog.dismiss();

                userModelArrayList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Log.d(TAG, "snap" + postSnapshot.getValue());

                    HashMap<Object , Object > hashMap = (HashMap<Object, Object>) postSnapshot.getValue();

                    Log.d(TAG, "onDataChange: "+hashMap.get("receiver_id"));
                    userModelArrayList.add(new UserModel(hashMap.get("receiver_id")));

                }
                Log.d(TAG, "userModelArrayList" + userModelArrayList.size());

                //   Toast.makeText(getActivity(), "userInfo "+userList+"/n", Toast.LENGTH_SHORT).show();

                Adapter_chat adapter = new Adapter_chat(getActivity(), userModelArrayList);
                chatRecyclerView.setAdapter(adapter);
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
