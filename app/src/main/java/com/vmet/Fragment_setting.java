package com.vmet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vmet.BaseApplication.BaseApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Fragment_setting extends Fragment {

    @BindView(R.id.logOutText)
    TextView logOutText;
    Unbinder unbinder;
    private Context context;
    private final String TAG = "LoginPage=> ";
    private FirebaseAuth firebaseAuth;
  //  private DatabaseReference databaseReference;

    ImageView profilePicImageView, accountImageView, chatImageView, notificationImageView, dataUsagesImageView,
            helpImageView, inviteImageView, groupImageView, broadcastImageView, webImageView, starMessageImageView;
    TextView editProfileTextView, accountTextView, chatsTextView, notificationsTextView, dataAndStorageUsageTextView,
            helpTextView, InviteAFriendTextView, addGroupTextView, newBroadcastTextView, weMetWebTextView, starredMessageTextView,
            pscTextView, bhwTextView, mgcTextView, nuaTextView, fcpTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_fragment, container, false);
       // databaseReference = FirebaseDatabase.getInstance().getReference("users");


        firebaseAuth = FirebaseAuth.getInstance();
       // getUserList();

        addGroupTextView = view.findViewById(R.id.addGroupTextView);
        newBroadcastTextView = view.findViewById(R.id.newBroadcastTextView);
        weMetWebTextView = view.findViewById(R.id.weMetWebTextView);
        starredMessageTextView = view.findViewById(R.id.starredMessageTextView);
        accountTextView = view.findViewById(R.id.accountTextView);
        chatsTextView = view.findViewById(R.id.chatsTextView);
        notificationsTextView = view.findViewById(R.id.notificationsTextView);
        dataAndStorageUsageTextView = view.findViewById(R.id.dataAndStorageUsageTextView);
        helpTextView = view.findViewById(R.id.helpTextView);
        pscTextView = view.findViewById(R.id.pscTextView);
        bhwTextView = view.findViewById(R.id.bhwTextView);
        mgcTextView = view.findViewById(R.id.mgcTextView);
        nuaTextView = view.findViewById(R.id.nuaTextView);
        fcpTextView = view.findViewById(R.id.fcpTextView);
        editProfileTextView = view.findViewById(R.id.editProfileTextView);

        addGroupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), New_Group.class);
                startActivity(intent);
            }
        });
        newBroadcastTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Broadcast.class);
                startActivity(intent);
            }
        });
        weMetWebTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeMet_Web.class);
                startActivity(intent);
            }
        });
        starredMessageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StarredMessages.class);
                startActivity(intent);
            }
        });
        accountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Account.class);
                startActivity(intent);
            }
        });
        pscTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Account.class);
                startActivity(intent);
            }
        });
        chatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Chats.class);
                startActivity(intent);
            }
        });
        bhwTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Chats.class);
                startActivity(intent);
            }
        });
        notificationsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notification.class);
                startActivity(intent);
            }
        });
        mgcTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notification.class);
                startActivity(intent);
            }
        });
        dataAndStorageUsageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DataAndStorageUsage.class);
                startActivity(intent);
            }
        });
        nuaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DataAndStorageUsage.class);
                startActivity(intent);
            }
        });
        helpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Help.class);
                startActivity(intent);
            }
        });
        fcpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Help.class);
                startActivity(intent);
            }
        });
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
            }
        });


        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.logOutText)
    public void onViewClicked() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You want to Log out");
        builder.setPositiveButton("Log out",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Log.d(TAG, "signInWithEmail:success");
                        Toast.makeText(getActivity(), "Log out", Toast.LENGTH_SHORT).show();
                        logout();
                        BaseApplication.getInstance().getSession().clearSession();
                        getActivity().finishAffinity();
                    }
                });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();

            }
        });
        builder.show();

    }

    private void logout() {

        firebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginPage.class);
        getActivity().startActivity(intent);
    }


    /*public void getUserList() {

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
                    userList.add(userInfo);
                }
                Log.d(TAG, "userlist " + userList);

                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).getUserid().equals(BaseApplication.getInstance().getSession().getUserId())) {

                        Log.d(TAG, "loop");

                        Glide.with(getActivity())
                                .load(userList.get(i).userPic)
                                .into(profilePicImageView);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

                Log.d(TAG, databaseError.getMessage());
            }
        });
    }*/
}
