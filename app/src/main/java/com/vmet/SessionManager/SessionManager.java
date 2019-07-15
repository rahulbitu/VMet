package com.vmet.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.vmet.Helper.Constants;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE=0;

    public SessionManager(Context context){
        pref = context.getSharedPreferences(Constants.PREFS,PRIVATE_MODE);
        editor = pref.edit();
    }
    public void clearSession(){
        editor.clear();
        editor.commit();
    }
    public Boolean isLoggedIn(){
        Boolean isLonged = pref.getBoolean(Constants.IS_LOGIN,false);
        return isLonged;
    }
    public void setIsLoggedIn(){
        editor.putBoolean(Constants.IS_LOGIN,true);
        editor.commit();
    }
    public void setUserId(String user_Id){
        editor.putString(Constants.USER_ID,user_Id);
        editor.commit();

    }
    public String getUserId(){
       return  pref.getString(Constants.USER_ID,"");

    }
    public void setChatWith(String userWithChat){
        editor.putString(Constants.USER_WITH_CHAT,userWithChat);
        editor.commit();

    }
    public String getChatWith(){
        return  pref.getString(Constants.USER_WITH_CHAT,"");

    }
/*
    public void setUserName(String user_name){
        editor.putString(Constants.USER_NAME,user_name);
        editor.commit();
    }
    public String getUserName(){
        return  pref.getString(Constants.USER_NAME,"");

    }*/

}
