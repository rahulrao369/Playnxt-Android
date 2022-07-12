package com.cw.playnxt.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cw.playnxt.databinding.ActivitySplashBinding;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.GpsTracker;

import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    Context context;
    String device_id = "";
    private ActivitySplashBinding binding;
    private MySharedPref mySharedPref;
    GpsTracker gpsTracker;
    double lat = 0.0, lng = 0.0;
    String city = "";
    String state = "";
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    public void init() {
        context = SplashActivity.this;
        mySharedPref = new MySharedPref(context);
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

      /*  if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            getGpsLocation();
        }*/
        startApp();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("Request Code >>>>>>>" + requestCode);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getGpsLocation();
                } else {
                    Toast.makeText((AppCompatActivity) context, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void startApp() {
        new Handler().postDelayed(() -> {
            if (!mySharedPref.isLogin()) {
                Intent i = new Intent(getBaseContext(), WelcomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(getBaseContext(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }

           /* if (!mySharedPref.isLogin()) {
                if (localeSharedPref.getLanguage() == null || !localeSharedPref.getLanguage().equals("1")) {
                    Intent intent = new Intent(getBaseContext(), SelectLanguageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else if(prefManager.isFirstTimeLaunch()){
                    startActivity(new Intent(context, WelcomeActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }else {
                    Intent i = new Intent(getBaseContext(), HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }

            } else {
                Intent i = new Intent(getBaseContext(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }*/
        }, 3000);
    }

    public void getGpsLocation() {
        gpsTracker = new GpsTracker((AppCompatActivity)context);
        if (gpsTracker.canGetLocation()) {
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
            Log.d("TAG","lat>>"+lat);
            Log.d("TAG","lng>>"+lng);

            mySharedPref.setLatitude(String.valueOf(lat));
            mySharedPref.setLongitude(String.valueOf(lng));

            Log.d("TAG","mySharedPref.getLatitude()>>"+mySharedPref.getLatitude());
            Log.d("TAG","mySharedPref.getLongitude()>>"+mySharedPref.getLongitude());
            try {
                geocoder = new Geocoder((AppCompatActivity)context, Locale.getDefault());
                addresses = geocoder.getFromLocation(lat, lng, 1);
                Log.d("TAG", "   addresses     " + addresses);

                String address = addresses.get(0).getAddressLine(0);
                Log.d("TAG", "   address     " + address);

                city = addresses.get(0).getLocality();
                Log.d("TAG", "   city     " + city);

                state = addresses.get(0).getAdminArea();
                Log.d("TAG", "   state     " + state);

            } catch (Exception e) {
                Toast.makeText((AppCompatActivity)context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }else{
            Log.d("TAG","!!!!");
        }
    }

}