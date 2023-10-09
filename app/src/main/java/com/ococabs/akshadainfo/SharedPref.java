package com.ococabs.akshadainfo;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static final String SHARED_PREF_NAME = "mySharedPref";

    private static com.ococabs.akshadainfo.SharedPref mInstance;
    private static Context mCtx;

    private SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized com.ococabs.akshadainfo.SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new com.ococabs.akshadainfo.SharedPref(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public void saveToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
    public void removeToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", "");
        editor.apply();
    }
    //this method will fetch the token from shared preferences
    public String getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString("token", "");
    }
}
