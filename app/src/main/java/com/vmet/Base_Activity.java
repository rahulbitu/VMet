package com.vmet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.vmet.BaseApplication.BaseApplication;

public class Base_Activity extends AppCompatActivity {
    private final String TAG = "BaseActivity=> ";
    private ProgressDialog pDialog;
/*    private Context context;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_class);
    }

   /* public ProgressDialog createProgressBar() {

        pDialog = new ProgressDialog(Base_Activity.this);
        *//*pDialog.setMessage("Please wait...");*//*
       *//* pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*//*

        return pDialog;
       // pDialog.show();
    }*/


   public static ProgressDialog createProgressBar(Context context){
        ProgressDialog pDialog = new ProgressDialog(context);
        try {
            pDialog.show();
        }catch (WindowManager.BadTokenException e){

        }
       pDialog.setCancelable(false);
       pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       pDialog.setContentView(R.layout.progress_bar);
        return pDialog;
   }

    public static void hideKeyboardFrom(Activity activity, View view) {
       final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

   /* protected void onStart(){
        super.onStart();

        String device_id = Settings.Secure.getString(Base_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d(TAG,"device_id"+device_id);
        BaseApplication.getInstance().getSession();

    }*/

}
