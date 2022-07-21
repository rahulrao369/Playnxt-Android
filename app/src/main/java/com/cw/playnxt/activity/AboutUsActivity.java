package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityAboutUsBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.AboutUs.AboutUsResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivityAboutUsBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
//

    //
    public void init() {
        context = AboutUsActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.AboutUs);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        if (Constants.isInternetConnected(context)) {
            AboutUsAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

    public void AboutUsAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.AboutUsAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.tvAboutUs.setText(Html.fromHtml(response.body().getData().getAboutApp(), Html.FROM_HTML_MODE_COMPACT));
                            Log.d("TAG", "" + Html.fromHtml(response.body().getData().getAboutApp()));
                        } else {
                            binding.tvAboutUs.setText(Html.fromHtml(response.body().getData().getAboutApp()));
                            Log.d("TAG", "" + Html.fromHtml(response.body().getData().getAboutApp()));
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
}
//