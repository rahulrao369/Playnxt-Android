package com.cw.playnxt.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Constants {
    public static String DEVICE_TYPE = "android";
    public static String ROLE = "1";
    public static String CONTENT_TYPE = "application/json";
    public static String CATEGORY_BACKLOG = "backlog";
    public static String CATEGORY_WISHLIST = "wishlist";
    public static String BACKLOG_LIST = "backloglist";
    public static String WISHLIST = "wishlist";
    public static String CURRENTLY_PLAYING = "Currently playing";
    public static String ON_THE_SHELF = "On the shelf";
    public static String ROLLED_CREDIT = "Rolled Credits";
    public static String HUNDRED_PERCENT_COMPLETED = "100% completed";
    public static String TAKING_BREAK = "Taking break";
    public static String LIKE_STATUS = "like";
    public static String UNLIKE_STATUS = "unlike";
    public static String SELF_GAME_VIEW = "self";
    public static String USER_GAME_VIEW = "user";
    public static String ACTIVE_PLAN_YES = "Yes";
    public static String PLAN_TYPE_FREE= "Free";
    public static String PLAN_TYPE_PAID= "Paid";
    public static String COMMUNITY= "community";
    public static String FRIENDS= "friends";

    public static boolean isInternetConnected(Context mContext) {

        try {
            ConnectivityManager connect = null;
            connect = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connect != null) {
                NetworkInfo resultMobile = connect
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                NetworkInfo resultWifi = connect
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                return (resultMobile != null && resultMobile
                        .isConnectedOrConnecting())
                        || (resultWifi != null && resultWifi
                        .isConnectedOrConnecting());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
