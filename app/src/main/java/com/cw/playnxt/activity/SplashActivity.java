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
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityAddCardBinding;
import com.cw.playnxt.databinding.ActivitySplashBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.AddDownload.AddDownloadReq;
import com.cw.playnxt.model.AddDownload.AddDownloadResponse;
import com.cw.playnxt.model.SubscriptionPlan.GetPaymentResponse;
import com.cw.playnxt.model.SubscriptionPlan.GetPaymentSummaryREq;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.GpsTracker;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    Context context;
    String device_id = "";
    private ActivitySplashBinding binding;
    private MySharedPref mySharedPref;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    public void init() {
        context = SplashActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("device id>>>>  "+device_id);
        if (Constants.isInternetConnected(context)) {
            adddownloadAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
        startApp();
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

    public void adddownloadAPI() {
        AddDownloadReq getPaymentSummaryREq = new AddDownloadReq();
        getPaymentSummaryREq.setDeviceId(device_id);
        getPaymentSummaryREq.setDevice("android");
        jsonPlaceHolderApi.adddownload(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(),getPaymentSummaryREq).enqueue(new Callback<AddDownloadResponse>() {
            @Override
            public void onResponse(Call<AddDownloadResponse> call, Response<AddDownloadResponse> response) {
                if (response.isSuccessful()) {
//                    System.out.println("payment response>>> "+response.body().getData().getActualprice());
                    try{
                        Boolean status = response.body().getStatus();
                        if (status) {

                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<AddDownloadResponse> call, Throwable t) {
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

}