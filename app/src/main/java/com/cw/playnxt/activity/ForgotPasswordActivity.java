package com.cw.playnxt.activity;

import static com.cw.playnxt.utils.Constants.CATEGORY_BACKLOG;
import static com.cw.playnxt.utils.Constants.CATEGORY_WISHLIST;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityForgotPasswordBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.AddFriendGame.AddFriendGameParaRes;
import com.cw.playnxt.model.AddFriendGame.AddFriendGameResponse;
import com.cw.playnxt.model.ForgotPassword.ForgotPasswordParaRes;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivityForgotPasswordBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = ForgotPasswordActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText("Forgot Password");
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
    }

    public void onclicks() {
        binding.btnSend.setOnClickListener(this);
        headerBinding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnSend:
                if (isValidate()) {
                    if (Constants.isInternetConnected(context)) {
                        ForgotPasswordAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void ForgotPasswordAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        ForgotPasswordParaRes forgotPasswordParaRes = new ForgotPasswordParaRes();
        forgotPasswordParaRes.setEmail(binding.etEmail.getText().toString());

        jsonPlaceHolderApi.ForgotPasswordAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(), forgotPasswordParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        openForgotPasswordDialog();
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseSatusMessage> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openForgotPasswordDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_forgot_password);
        TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
        // headerBinding.txtHeading.setText(R.string.Forgot_Password);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });

        dialog.show();
    }
    private Boolean isValidate() {
        if (binding.etEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, R.string.please_enter_your_email, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString().trim()).matches()) {
            Toast.makeText(context, R.string.please_enter_valid_email, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}