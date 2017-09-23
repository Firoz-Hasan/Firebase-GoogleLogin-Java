package com.bdjobs.training.firebaseexercise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by FIROZ HASAN on 8/21/2017.
 */

public class SessionManagement {
    SharedPreferences sharedPreferences;
    Context context;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "FirebaseSharedPref";
    private static final String IS_LOGIN = "IsLoggedIn";
        public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MSG_INFO = "message";
    public static final String  KEY_PHOTOURL_INFO = "photourl";

    public SessionManagement(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setUserName(String name) {
        editor.putString(KEY_NAME, name);
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void setEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getUserName() {
        String userName = sharedPreferences.getString("name", "");
        return userName;
    }

    public String getEmail() {
        String email = sharedPreferences.getString("email", "");
        return email;
    }

    public String getData(String key) {
        String data = sharedPreferences.getString(key, "");
        return data;
    }


    public void logoutUser() {

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void savedData(String info) {
        editor.putString(KEY_MSG_INFO, info);
        editor.commit();
    }


    public String getData() {
        String returnData = sharedPreferences.getString("message", "");
        return returnData;
    }

    public void setPhotoUrl(String photourl) {
        editor.putString(KEY_PHOTOURL_INFO, photourl);
        editor.commit();
    }


    public String getPhotoUrl() {
        String getphotourl = sharedPreferences.getString("photourl", "");
        return getphotourl;
    }


}
