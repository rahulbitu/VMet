package com.vmet;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vmet.BaseApplication.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Chatting extends AppCompatActivity {


    @BindView(R.id.PicOfPersonWithChat)
    CircleImageView PicOfPersonWithChat;
    @BindView(R.id.nameTxt)
    TextView nameTxt;
    @BindView(R.id.videoCallImg)
    ImageView videoCallImg;
    @BindView(R.id.voiceCallImg)
    ImageView voiceCallImg;
    @BindView(R.id.cameraImg)
    ImageView cameraImg;
    @BindView(R.id.messageEditText)
    EditText messageEditText;
    @BindView(R.id.mediaImg)
    ImageView mediaImg;
    @BindView(R.id.emojiImg)
    ImageView emojiImg;
    @BindView(R.id.constraintLayout_chatting)
    LinearLayout constraintLayoutChatting;
    Firebase reference1, reference2, roomReferance, reference3, reference4;
    @BindView(R.id.sendImg)
    ImageView sendImg;
    Bitmap myBitmap;
    Uri picUri;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private final String TAG = "Chatting=> ";
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private DatabaseReference databaseReference;

    private String sessionUserId;
    // private String sessionUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final String chatWith = getIntent().getExtras().getString("chatWith");

        String name = getIntent().getExtras().getString("name");
        nameTxt.setText(name);

        sessionUserId = BaseApplication.getInstance().getSession().getUserId();
        //  sessionUserName = BaseApplication.getInstance().getSession().getUserName();

        Firebase.setAndroidContext(this);

        reference1 = new Firebase("https://vmet-ab630.firebaseio.com/messages/" + sessionUserId + "_" + chatWith);
        reference2 = new Firebase("https://vmet-ab630.firebaseio.com/messages/" + chatWith + "_" + sessionUserId);

        reference3 = new Firebase("https://vmet-ab630.firebaseio.com/users/" + sessionUserId);
        reference4 = new Firebase("https://vmet-ab630.firebaseio.com/users/" + chatWith);


        final DatabaseReference databaseReference12 = databaseReference.child("users")
                .child(BaseApplication.getInstance().getSession().getUserId()).child("rooms").child(sessionUserId + "_" + chatWith);


        //  roomReferance = new Firebase("https://vmet-ab630.firebaseio.com/" + BaseApplication.getInstance().getSession().getUserId());


        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user_id", sessionUserId);
                    map.put("receiver_id",chatWith);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);



                    //reference3.push().setValue(map);
                    //reference4.push().setValue(map);


                    //HashMap<String, Object> result = new HashMap<>();
                    //databaseReference12.setValue(sessionUserId + "_" + chatWith);

                    databaseReference12.push().setValue(map);

                    //databaseReference12.updateChildren(result);


                    /*  roomReferance.child("rooms").push().setValue(map);*/
                }
            }
        });

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() == 0) {
                    mediaImg.setVisibility(View.VISIBLE);
                    emojiImg.setVisibility(View.VISIBLE);
                    sendImg.setVisibility(View.GONE);
                } else {
                    mediaImg.setVisibility(View.GONE);
                    emojiImg.setVisibility(View.GONE);
                    sendImg.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userId = map.get("user_id").toString();

                if (userId.equals(sessionUserId)) {
                    addMessageBox(/*"You:-\n" +*/ message, 1);
                } else {
                    addMessageBox(/*chatWith + ":-\n" +*/ message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @OnClick({R.id.messageEditText, R.id.videoCallImg, R.id.voiceCallImg, R.id.cameraImg, R.id.mediaImg, R.id.emojiImg})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.messageEditText:
                break;
            case R.id.videoCallImg:
                break;
            case R.id.voiceCallImg:
                break;
            case R.id.cameraImg:
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
                break;
            case R.id.mediaImg:
                if (checkPermission()) {

                    startActivityForResult(getPickImageChooserIntent(), 200);

                } else {
                    requestPermission();
                }
                break;
            case R.id.emojiImg:
                break;

        }

    }

    public void addMessageBox(String message, int type) {
        final TextView textView = new TextView(Chatting.this);
        textView.setText(message);
        textView.setTextColor(getResources().getColor(R.color.unSelect));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textView.setMaxWidth(800);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 10, 20, 10);
        textView.setLayoutParams(lp);

        messageEditText.setText("");


        if (type == 1) {
            lp.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        } else {
            lp.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        constraintLayoutChatting.addView(textView);

        /* scrollView.fullScroll(View.FOCUS_DOWN);*/

    }

    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);

        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);

        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }
        Intent mainIntent = allIntents.get(allIntents.size() - 1);

        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        /*Log.e(TAG, "onActivityResult: " );*/

        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {


            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                Log.d(TAG, "picuri:" + picUri);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                   /* myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);*

                    CircleImageView croppedImageView = findViewById(R.id.profilePicImageView);
                    croppedImageView.setImageBitmap(myBitmap);
                    /* profilePicImageView.setImageBitmap(myBitmap);*/

                    // uploadImages();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                bitmap = (Bitmap) data.getExtras().get("data");
                myBitmap = bitmap;
                CircleImageView croppedImageView = findViewById(R.id.profilePicImageView);
                if (croppedImageView != null) {
                    croppedImageView.setImageBitmap(myBitmap);
                }

                /* profilePicImageView.setImageBitmap(myBitmap);*/

            }

        }

    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }

        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }

        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Chatting.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /*@Override
    public void onBackPressed() {

        Intent intent = new Intent(Chatting.this, Fragment_chat.class);
        startActivity(intent);
        super.onBackPressed();
    }*/

}
