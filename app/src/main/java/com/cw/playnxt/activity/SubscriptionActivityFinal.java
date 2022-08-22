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
import com.cw.playnxt.model.StaticModel.AccessExtraFeatures;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionActivityFinal extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivitySubscriptionFinalBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
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
        headerBinding.tvHeading.setText("Playnext Premium");
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
    //    plan_ID = null;
       /* if (Constants.isInternetConnected(context)) {
            SubscriptionPlanAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }*/
        setAccessExtraFeaturesData();
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
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnContinue:
       /*         if(plan_ID == null){
                    Toast.makeText(context, "Please select any plan", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("TAG1","plan_ID"+plan_ID);
                    Log.d("TAG1","selected_plane_type"+selected_plane_type);
                    if(selected_plane_type.equals("Free")){
                       *//* if (Constants.isInternetConnected(context)) {
                            PurchaseFreePlanAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }*//*
                    }else{
                        startActivity(new Intent(context,AddCardActivity.class)
                                .putExtra("plan_ID",plan_ID.toString()));
                    }
                }*/
                break;
        }
    }
}