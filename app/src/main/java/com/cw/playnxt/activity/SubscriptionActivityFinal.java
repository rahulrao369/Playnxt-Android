package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SubscriptionAccessExtraFeaturesAdapter;
import com.cw.playnxt.adapter.SubscriptionPlanListAdapterMain;
import com.cw.playnxt.databinding.ActivitySubscriptionBinding;
import com.cw.playnxt.databinding.ActivitySubscriptionFinalBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.GET_PLAN.GetPlanResponse;
import com.cw.playnxt.model.PurchaseFreePlan.PurchaseFreePlanParaRes;
import com.cw.playnxt.model.PurchaseFreePlan.PurchaseFreePlanResponse;
import com.cw.playnxt.model.StaticModel.AccessExtraFeatures;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionActivityFinal extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivitySubscriptionFinalBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    Long plan_ID_MONTHLY;
    Long plan_ID_YEARLY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionFinalBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = SubscriptionActivityFinal.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText("Playnxt Premium");
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        if (Constants.isInternetConnected(context)) {
            GET_PLAN_API();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private void setAccessExtraFeaturesData() {
        List<AccessExtraFeatures> featuresList = new ArrayList<>();
        featuresList.add(new AccessExtraFeatures("Add free experience"));
        featuresList.add(new AccessExtraFeatures("Unlimited backlogs and wishlists"));
        featuresList.add(new AccessExtraFeatures("Access to backlogs stats"));
        featuresList.add(new AccessExtraFeatures("View your friend's backlog"));
        featuresList.add(new AccessExtraFeatures("Calender tool to help manage tackling your backlog"));
        SubscriptionAccessExtraFeaturesAdapter adapter = new SubscriptionAccessExtraFeaturesAdapter(context, featuresList, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long id, String type) {

            }
        });
        binding.rvExtraFeatures.setHasFixedSize(true);
        binding.rvExtraFeatures.setLayoutManager(new LinearLayoutManager(context));
        binding.rvExtraFeatures.setAdapter(adapter);
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnMonth.setOnClickListener(this);
        binding.btnYear.setOnClickListener(this);
        binding.btnYear.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnMonth:
                startActivity(new Intent(context,AddCardActivity.class)
                        .putExtra("plan_ID",plan_ID_MONTHLY.toString()));
//                startActivity(new Intent(context,SubscriptionActivityCoupon.class)
//                        .putExtra("plan_ID",plan_ID_MONTHLY.toString()));
                break;

            case R.id.btnYear:
                startActivity(new Intent(context,AddCardActivity.class)
                        .putExtra("plan_ID",plan_ID_YEARLY.toString()));
//                startActivity(new Intent(context,SubscriptionActivityCoupon.class)
//                        .putExtra("plan_ID",plan_ID_YEARLY.toString()));
                break;
        }
    }

    public void GET_PLAN_API() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.GET_PLAN_API(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetPlanResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPlanResponse> call, Response<GetPlanResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        plan_ID_MONTHLY = response.body().getData().getPlan().get(0).getId();
                        plan_ID_YEARLY = response.body().getData().getPlan().get(1).getId();
                        binding.llMain.setVisibility(View.VISIBLE);
                        binding.llNoData.setVisibility(View.GONE);
                        binding.txtBtnMonth.setText("$"+response.body().getData().getPlan().get(0).getPrice()+" per month"/*response.body().getData().getPlan().get(0).getType()*/);
                        binding.txtbtnYear.setText("$"+response.body().getData().getPlan().get(1).getPrice()+" per year"/*response.body().getData().getPlan().get(1).getType()*/);

                        setAccessExtraFeaturesData();
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        binding.llMain.setVisibility(View.VISIBLE);
                        binding.llNoData.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(Call<GetPlanResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                binding.llMain.setVisibility(View.VISIBLE);
                binding.llNoData.setVisibility(View.GONE);
            }
        });
    }
}