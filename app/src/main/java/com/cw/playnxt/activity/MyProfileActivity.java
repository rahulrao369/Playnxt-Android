package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cw.playnxt.R;
import com.cw.playnxt.adapter.FriendsAdapters.TabLayoutAdapter;
import com.cw.playnxt.adapter.MyProfileAdapters.MyProfileTabLayoutAdapter;
import com.cw.playnxt.databinding.ActivityFriendsProfileBinding;
import com.cw.playnxt.databinding.ActivityMyProfileBinding;

import com.cw.playnxt.model.CheckSubscriptionFinal.CheckSubscriptionFinalResponse;
import com.cw.playnxt.model.GetMyProfile.GetMyProfileResponse;
import com.cw.playnxt.model.GetMyProfile.Profile;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivityMyProfileBinding binding;
    MyProfileTabLayoutAdapter tabLayoutAdapter;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    Long activity_count,followers_count,following_count;
    String tab;
    String profile_image = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = MyProfileActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        tabLayoutAdapter = new MyProfileTabLayoutAdapter(getSupportFragmentManager(), binding.tablayout.getTabCount());
        binding.viewpager.setAdapter(tabLayoutAdapter);

        if (Constants.isInternetConnected(context)) {
            NewCheckSubscriptionAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        try {
            Intent intent = getIntent();
            if (intent != null) {
                tab = intent.getStringExtra("key");
                Log.d("TAG", "tab>>" + tab);

                if (Constants.isInternetConnected(context)) {
                    GetMyProfileAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onclicks() {
        binding.btnBack.setOnClickListener(this);
        binding.btnSetting.setOnClickListener(this);
        binding.btnEditProfile.setOnClickListener(this);
        binding.cvUserProfile.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnSetting:
                startActivity(new Intent(context,SettingActivity.class));
                break;

            case R.id.btnEditProfile:
                startActivity(new Intent(context,EditProfileActivity.class));
                break;

            case R.id.cvUserProfile:
                openDialogBigProfile();
                break;
        }
    }

    public void openDialogBigProfile() {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_big_profile_image);
        AppCompatImageView iv_profile_image = (AppCompatImageView) dialog.findViewById(R.id.iv_profile_image);
        MaterialCardView cv_cross = (MaterialCardView) dialog.findViewById(R.id.cv_cross);

        Picasso.get().load(Allurls.IMAGEURL+profile_image).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(iv_profile_image);

        cv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    public void GetMyProfileAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.GetMyProfileAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyProfileResponse>() {
            @Override
            public void onResponse(Call<GetMyProfileResponse> call, Response<GetMyProfileResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus() != null){
                        Boolean status = response.body().getStatus();
                        Customprogress.showPopupProgressSpinner(context, false);
                        if (status)
                        {
                            if(response.body().getData() != null){
                                binding.llMain.setVisibility(View.VISIBLE);
                                activity_count = response.body().getData().getTotelGame();
                                followers_count = response.body().getData().getTotelFollower();
                                following_count = response.body().getData().getTotelFollowing();
                                profile_image = response.body().getData().getProfile().getImage();
                                setMyProfileData(response.body().getData().getProfile());

                                binding.tablayout.addTab(binding.tablayout.newTab().setText(activity_count+"\n" +
                                        "My Activity"));
                                binding.tablayout.addTab(binding.tablayout.newTab().setText(followers_count+"\n" +
                                        "Followers"));
                                binding.tablayout.addTab(binding.tablayout.newTab().setText(following_count+"\n" +
                                        "Following"));

                                binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {
                                        binding.viewpager.setCurrentItem(tab.getPosition());
                                        int currentPosition = binding.viewpager.getCurrentItem();
                                        tabLayoutAdapter.notifyDataSetChanged();
                                        binding.viewpager.setAdapter(null);
                                        binding.viewpager.setAdapter(tabLayoutAdapter);
                                        binding.viewpager.setCurrentItem(currentPosition);
                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });
                                binding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
                                if (tab.equals("1")) {
                                    binding.tablayout.getTabAt(0).select();
                                } else if (tab.equals("3")) {
                                    binding.tablayout.getTabAt(2).select();
                                } else {
                                    binding.tablayout.getTabAt(0).select();
                                }
                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context,"Status Null", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyProfileResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void setMyProfileData(Profile profileData) {
        Picasso.get().load(Allurls.IMAGEURL+profileData.getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(binding.cvUserProfile);
        String userName = profileData.getName().substring(0, 1).toUpperCase() + profileData.getName().substring(1).toLowerCase();
        binding.tvName.setText(userName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.isInternetConnected(context)) {
            GetUpdatedMyProfileAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void GetUpdatedMyProfileAPI() {

        jsonPlaceHolderApi.GetMyProfileAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyProfileResponse>() {
            @Override
            public void onResponse(Call<GetMyProfileResponse> call, Response<GetMyProfileResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus() != null){
                        Boolean status = response.body().getStatus();
                        if (status)
                        {
                            if(response.body().getData() != null){
                                binding.llMain.setVisibility(View.VISIBLE);
                                setUpdatedData(response.body().getData().getProfile());
                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context,"Status Null", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyProfileResponse> call, Throwable t) {
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void setUpdatedData(Profile profileData) {
        if(!profileData.getImage().equals("")){
            Picasso.get().load(Allurls.IMAGEURL+profileData.getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(binding.cvUserProfile);
            Log.d("TAG","Image>>>>"+Allurls.IMAGEURL+profileData.getImage());
        }else{
            //   Picasso.get().load(profileData.getImage()).into(binding.cvUserProfile);
        }
        binding.tvName.setText(profileData.getName());
    }
    //*********************************************************CHECK SUBSCRIPTION****************************************************
    public void NewCheckSubscriptionAPI() {
        jsonPlaceHolderApi.NewCheckSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckSubscriptionFinalResponse>() {
            @Override
            public void onResponse(Call<CheckSubscriptionFinalResponse> call, Response<CheckSubscriptionFinalResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        if (response.body().getData().getSubscribed().equals(Constants.YES)) {
                            binding.btnAdsShow.setVisibility(View.GONE);
                        }else{
                            binding.btnAdsShow.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckSubscriptionFinalResponse> call, Throwable t) {
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}