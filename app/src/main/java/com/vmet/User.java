package com.vmet;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {

    public String userid;
    public String username;
    public String email;
    public Date time;
    public String  userPic;
   // private boolean isSelected = false;


    public User(){

   }

    public User(String userPic) {
        this.userPic = userPic;
    }

    public User(String userid, String username, String email, Date time, String userPic) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.time = time;
        this.userPic = userPic;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

   /* public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }*/


    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", time=" + time +
                ", userPic='" + userPic + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userid", userid);
        result.put("username", username);
        result.put("email", email);
        result.put("time", time);


        return result;
    }

    @Exclude
    public Map<String, Object> toMap1() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userPic", userPic);



        return result;
    }
}
