package com.cw.playnxt.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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
        firebaseData();

        String text1 = "By joining you are confirming to receive email updates, promotions, and offers from Playnxt, and agree to Playnxt’s Privacy Policy and Terms & Conditions.";
        SpannableString spannableString1 = new SpannableString(text1);
        ClickableSpan clickableSpan11 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent11 = new Intent(context, PrivacyPolicyActivity.class);
                startActivity(intent11);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ClickableSpan clickableSpan12 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent11 = new Intent(context, TermsAndConditionActivity.class);
                startActivity(intent11);

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        spannableString1.setSpan(clickableSpan11, 115, 130, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(clickableSpan12, 135, 153, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_theme)), 115, 130, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_theme)), 135, 153, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvCheck.setText(spannableString1);
        binding.tvCheck.setMovementMethod(LinkMovementMethod.getInstance());
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
//                        mySharedPref.saveLogin(true);
//                        mySharedPref.setSavedAccessToken(String.valueOf(response.body().getData().getToken()));
//                        mySharedPref.setSavedUserid(String.valueOf(response.body().getData().getUserId()));
//                        Log.d("TAG", "ACCESS_TOKEN" + mySharedPref.getSavedAccessToken());
//                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();

                        dialogSignup(response.body().getMessage());
                        /*Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();*/
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

    private void dialogSignup(String msg) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_signup);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView textmsg = dialog.findViewById(R.id.textmsg);
        textmsg.setText(msg);
        TextView txt_ok = dialog.findViewById(R.id.txt_ok);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

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
        }else if (!binding.cbAgree.isChecked()) {
            Toast.makeText(context, R.string.please_check, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}