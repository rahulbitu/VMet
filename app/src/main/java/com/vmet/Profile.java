package com.vmet;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vmet.BaseApplication.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Profile extends Base_Activity {

    @BindView(R.id.fbCamera)
    FloatingActionButton fbCamera;
    @BindView(R.id.profilePicImageView)
    CircleImageView profilePicImageView;
    Bitmap myBitmap;
    Uri picUri;

    FirebaseStorage storage;
    StorageReference storageReference;
    private final String TAG = "Profile=> ";
    private static final int PERMISSION_REQUEST_CODE = 200;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.emailTextView)
    TextView emailTextView;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        Log.d(TAG, BaseApplication.getInstance().getSession().getUserId());

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        nameTextView.setText(name);*/

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        getUserList();


    }

    @OnClick({R.id.fbCamera, R.id.profilePicImageView})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.fbCamera:
                if (checkPermission()) {

                    startActivityForResult(getPickImageChooserIntent(), 200);

                } else {
                    requestPermission();
                }
                break;
            case R.id.profilePicImageView:
                break;

        }

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

                    uploadImages();

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

    /*  private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

          ExifInterface ei = new ExifInterface(selectedImage.getPath());
          int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

          switch (orientation) {
              case ExifInterface.ORIENTATION_ROTATE_90:
                  return rotateImage(img, 90);
              case ExifInterface.ORIENTATION_ROTATE_180:
                  return rotateImage(img, 180);
              case ExifInterface.ORIENTATION_ROTATE_270:
                  return rotateImage(img, 270);
              default:
                  return img;
          }
      }
      private static Bitmap rotateImage(Bitmap img, int degree) {
          Matrix matrix = new Matrix();
          matrix.postRotate(degree);
          Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
          img.recycle();
          return rotatedImg;
      }
      public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
          int width = image.getWidth();
          int height = image.getHeight();

          float bitmapRatio = (float) width / (float) height;
          if (bitmapRatio > 0) {
              width = maxSize;
              height = (int) (width / bitmapRatio);
          } else {
              height = maxSize;
              width = (int) (height * bitmapRatio);
          }
          return Bitmap.createScaledBitmap(image, width, height, true);
      }
  */
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
        new AlertDialog.Builder(Profile.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void uploadImages() {
        final ProgressDialog progressDialog = createProgressBar(Profile.this);
        progressDialog.show();

        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        ref.putFile(picUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                        final Uri uri = taskSnapshot.getDownloadUrl();

                        Log.d(TAG, "task" + taskSnapshot.getDownloadUrl());



                       /* databaseReference.child(BaseApplication.getInstance().getSession().getUserId())
                                .child("userPic")
                                .setValue(taskSnapshot.getDownloadUrl()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                Toast.makeText(Profile.this, "Profile picture uploaded successfully", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onComplete");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: " );
                            }
                        });*/

                        DatabaseReference databaseReference1 = databaseReference.child(BaseApplication.getInstance().getSession().getUserId());
                        databaseReference1.updateChildren(new User(uri.toString()).toMap1());
/*
                        Glide.with(Profile.this)
                                .load(uri)
                                .into(profilePicImageView);*/


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Profile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");

                    }
                });
    }


   /* public void getUserList() {


        final ProgressDialog progressDialog = createProgressBar();
        progressDialog.show();

        Query query = databaseReference.child("user_id").equalTo(BaseApplication.getInstance().getSession().getUserId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        // do something with the individual "issues


                        Log.d(TAG, "onDataChange: " + dataSnapshot1);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/

    public void getUserList() {

        final ProgressDialog progressDialog = createProgressBar(Profile.this);
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

                        nameTextView.setText(userList.get(i).getUsername());
                        emailTextView.setText(userList.get(i).getEmail());

                        Glide.with(Profile.this)
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
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}



