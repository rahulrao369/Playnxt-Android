package com.cw.playnxt.server;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class MySharedPref {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    Gson gson;

    private static final String KEY_USERID = "user_id";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_FCM_TOKEN = "fcm_token";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String INFO_STATUS = "info_status";
    private static final String STRIPE_PUBLIC_KEY = "stripe_public_key";

    public MySharedPref(Context context){
        sharedPreferences = context.getSharedPreferences("VivDateSF",0);
        myEdit = sharedPreferences.edit();
        gson = new Gson();
    }

    //*****************LOGIN************************
    public void saveLogin(boolean str){
        myEdit.putBoolean("is_login", str);
        myEdit.commit();
    }
    public boolean isLogin(){
        return sharedPreferences.getBoolean("is_login", false);
    }

    //****************LATITUDE********************
    public void setLatitude(String latitude) {
        myEdit.putString(LATITUDE,latitude );
        myEdit.commit();
        myEdit.apply();
    }
    public String getLatitude() {
        return sharedPreferences.getString(LATITUDE ,"");
    }

    //****************LONGITUDE********************
    public void setLongitude(String longitude) {
        myEdit.putString(LONGITUDE,longitude );
        myEdit.commit();
        myEdit.apply();
    }
    public String getLongitude() {
        return sharedPreferences.getString(LONGITUDE ,"");
    }



    public void setLang(String language) {
        myEdit.putString("language",language );
        myEdit.commit();
        myEdit.apply();
    }

    public String getLang() {
        return sharedPreferences.getString("language" ,"");
    }





    public void setSavedDeviceid(String userid) {
        myEdit.putString(KEY_DEVICE_ID, userid);
        myEdit.commit();
    }


    public String getSavedStringDeviceid() {
        return sharedPreferences.getString(KEY_DEVICE_ID, "");
    }

    public void saveFcmToken(String str){
        myEdit.putString("fcm_token", str);
        myEdit.commit();
        myEdit.apply();
    }

    public String getFcmToken(){
        return sharedPreferences.getString("fcm_token", "");
    }


    public void setSavedUserid(String userid) {
        myEdit.putString(KEY_USERID, userid);
        myEdit.commit();
    }
    public String getSavedUserid() {
        return sharedPreferences.getString(KEY_USERID,"");
    }



    public void setSavedAccessToken(String accessToken) {
        myEdit.putString(KEY_ACCESS_TOKEN, accessToken);
        myEdit.commit();
    }
    public String getSavedAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN,"");
    }
}
