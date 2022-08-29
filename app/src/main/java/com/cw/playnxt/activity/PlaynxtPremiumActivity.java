package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SettingAdapters.PlaynextPremiumTabAdapter;
import com.cw.playnxt.databinding.ActivityPlaynxtPremiumBinding;
import com.cw.playnxt.databinding.ActivitySettingPalynxtPremiumBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.ChatList.ChatListResponse;
import com.cw.playnxt.model.MyActivePlan.ActivePlan;
import com.cw.playnxt.model.MyActivePlan.MyActivePlanResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaynxtPremiumActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivityPlaynxtPremiumBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaynxtPremiumBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = PlaynxtPremiumActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText("Playnext Premium");
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
        if (Constants.isInternetConnected(context)) {
            MyActivePlanAPI();
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

    public void MyActivePlanAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.MyActivePlanAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<MyActivePlanResponse>() {
            @Override
            public void onResponse(Call<MyActivePlanResponse> call, Response<MyActivePlanResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                       // Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if(response.body().getData().getActivePlan() != null){
                            ActivePlanDataSet(response.body().getData().getActivePlan());
                        }

                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<MyActivePlanResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ActivePlanDataSet(ActivePlan activePlan) {
        binding.tvPlanTitle.setText(activePlan.getTitle());
        if(activePlan.getTitle().equals("Monthly")){
            binding.tvValidUpto.setText("Valid Upto 1 month");
        }else if(activePlan.getTitle().equals("Yearly")){
            binding.tvValidUpto.setText("Valid Upto 12 months");
        }
        binding.tvPurchaseDate.setText(activePlan.getStartDate());
        binding.tvExpiryDate.setText(activePlan.getEndDate());
      //  binding.tvDescription.setText(activePlan.getDescription());
        binding.tvPrice.setText(activePlan.getAmount());
    }

}