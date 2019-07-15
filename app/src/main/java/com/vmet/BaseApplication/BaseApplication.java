package com.vmet.BaseApplication;

import android.app.Application;

import com.vmet.SessionManager.SessionManager;

public class BaseApplication extends Application {

    private static BaseApplication mInstance;
    public static SessionManager sessionManager;


    @Override
    public void onCreate() {
        super.onCreate();

        if(mInstance==null){
            mInstance = this;
        }
    }

    public static synchronized BaseApplication getInstance(){
        return mInstance;
    }
    public SessionManager getSession(){
        if(sessionManager == null){
            sessionManager = new SessionManager(getApplicationContext());
        }
        return sessionManager;
    }
}

