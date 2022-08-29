package com.cw.playnxt.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityLoginBinding;
import com.cw.playnxt.model.LoginSignup.LoginParaRes;
import com.cw.playnxt.model.LoginSignup.LoginResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.cw.playnxt.utils.GpsTracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
   /* GpsTracker gpsTracker;
    double lat = 0.0, lng = 0.0;
    String city = "";
    String state = "";
    Geocoder geocoder;
    List<Address> addresses;*/
    private ActivityLoginBinding binding;
    private boolean isShowPassword = false;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    private String fcm_token = "test";
    private String device_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        onclicks();

        binding.passwordToggleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowPassword = !isShowPassword;
                showPassword(isShowPassword);
            }
        });
        showPassword(isShowPassword);
    }

    public void showPassword(Boolean isShow) {
        if (isShow) {
            binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.passwordToggleImageView.setImageResource(R.drawable.ic_hide_password);
        } else {
            binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.passwordToggleImageView.setImageResource(R.drawable.ic_show_password);
        }
    }

    private void onclicks() {
        binding.btnLogin.setOnClickListener(this);
        binding.llDontHaveAccount.setOnClickListener(this);
        binding.lytForgotPassword.setOnClickListener(this);
    }

    public void init() {
        context = LoginActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        firebaseData();
     /*   if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            getGpsLocation();
        }*/
    }
    private void firebaseData() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        fcm_token = task.getResult();
                        mySharedPref.saveFcmToken(fcm_token);
                        Log.d("TAG", "fcm_token>>>>" + fcm_token);
                    }
                });
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        mySharedPref.setSavedDeviceid(device_id);
        Log.d("TAG", "device_id " + device_id);
    }

  /*  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
    }*/

  /*  public void getGpsLocation() {
        gpsTracker = new GpsTracker((AppCompatActivity) context);
        if (gpsTracker.canGetLocation()) {
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();
            Log.d("TAG", "lat>>" + lat);
            Log.d("TAG", "lng>>" + lng);

            mySharedPref.setLatitude(String.valueOf(lat));
            mySharedPref.setLongitude(String.valueOf(lng));

            Log.d("TAG", "mySharedPref.getLatitude()>>" + mySharedPref.getLatitude());
            Log.d("TAG", "mySharedPref.getLongitude()>>" + mySharedPref.getLongitude());
            try {
                geocoder = new Geocoder((AppCompatActivity) context, Locale.getDefault());
                addresses = geocoder.getFromLocation(lat, lng, 1);
                Log.d("TAG", "   addresses     " + addresses);

                String address = addresses.get(0).getAddressLine(0);
                Log.d("TAG", "   address     " + address);

                city = addresses.get(0).getLocality();
                Log.d("TAG", "   city     " + city);

                state = addresses.get(0).getAdminArea();
                Log.d("TAG", "   state     " + state);

            } catch (Exception e) {
                //  Toast.makeText((AppCompatActivity) context, "Location >>"+e.toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("TAG", "");
        }
    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if (isValidate()) {
                    if (Constants.isInternetConnected(context)) {
                        loginAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.llDontHaveAccount:
                startActivity(new Intent(context, SignupActivity.class));
                break;

            case R.id.lytForgotPassword:
                startActivity(new Intent(context, ForgotPasswordActivity.class));
                break;
        }
    }

    public void loginAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        LoginParaRes loginParaRes = new LoginParaRes();
        loginParaRes.setEmail(binding.etEmail.getText().toString().trim());
        loginParaRes.setPassword(binding.etPassword.getText().toString().trim());
        loginParaRes.setRole(Long.valueOf(Constants.ROLE));
        loginParaRes.setLat("0.0");
        loginParaRes.setLng("0.0");
        loginParaRes.setDeviceToken(device_id);
        loginParaRes.setFcmToken(fcm_token);

        jsonPlaceHolderApi.loginAPI(Constants.CONTENT_TYPE, loginParaRes).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        mySharedPref.saveLogin(true);
                        mySharedPref.setSavedAccessToken(String.valueOf(response.body().getData().getToken()));
                        mySharedPref.setSavedUserid(String.valueOf(response.body().getData().getUserId()));
                        Log.d("TAG", "ACCESS_TOKEN" + mySharedPref.getSavedAccessToken());
                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private Boolean isValidate() {
        if (binding.etEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, R.string.please_enter_your_email, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString().trim()).matches()) {
            Toast.makeText(context, R.string.please_enter_valid_email, Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, R.string.please_enter_your_password, Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etPassword.getText().toString().trim().length() < 6) {
            Toast.makeText(context, R.string.password_should_be_atleast_6_digits, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}