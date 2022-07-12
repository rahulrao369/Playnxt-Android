package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivitySignupBinding;
import com.cw.playnxt.model.LoginSignup.SignupParaRes;
import com.cw.playnxt.model.LoginSignup.SignupResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivitySignupBinding binding;
    private boolean isShowPassword = false;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    private String fcm_token = "test";
    private String device_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
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
        binding.llAlreadyHaveAccount.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
    }

    public void init() {
        context = SignupActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignup:
                if (isValidate()) {
                    if (Constants.isInternetConnected(context)) {
                        registrationAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.llAlreadyHaveAccount:
                startActivity(new Intent(context, LoginActivity.class));
                break;
        }
    }

    public void registrationAPI() {
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Customprogress.showPopupProgressSpinner(context, true);
        SignupParaRes signupParaRes= new SignupParaRes();
        signupParaRes.setUserName(binding.etName.getText().toString().trim());
        signupParaRes.setEmail(binding.etEmail.getText().toString().trim());
        signupParaRes.setPassword(binding.etPassword.getText().toString().trim());
        signupParaRes.setRole(Long.valueOf(Constants.ROLE));
       /* if(binding.cbSpecialPrice.isChecked()){
            signupParaRes.setSpecialPriceNotification(Long.valueOf(1));
        }*/
       // signupParaRes.setDeviceType(Constants.DEVICE_TYPE);
        signupParaRes.setDeviceToken(device_id);
        signupParaRes.setFcmToken(fcm_token);

        jsonPlaceHolderApi.registrationAPI(signupParaRes).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
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
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private Boolean isValidate() {
        if (binding.etName.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, R.string.Please_enter_your_name, Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.etName.getText().toString().trim().length() < 3) {
            Toast.makeText(context, R.string.name_should_be_more_than_or_equal_to_3_character, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.etEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, R.string.please_enter_your_email, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString().trim()).matches()) {
            Toast.makeText(context, R.string.please_enter_valid_email, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.etPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, R.string.please_enter_your_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.etPassword.getText().toString().trim().length() < 6) {
            Toast.makeText(context, R.string.password_should_be_atleast_6_digits, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}