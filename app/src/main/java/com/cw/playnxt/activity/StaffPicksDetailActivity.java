package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityStaffPicksDetailBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.StaffPicks.Capsul;
import com.cw.playnxt.server.Allurls;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StaffPicksDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivityStaffPicksDetailBinding binding;
    private HeaderLayoutBinding headerBinding;
    Capsul staffPicksList ;
    String buyNowLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaffPicksDetailBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = StaffPicksDetailActivity.this;
        headerBinding.tvHeading.setText(R.string.GameInfo);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

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
                staffPicksList = (Capsul) intent.getSerializableExtra("staffPicksList");
                setStaffPicksDetailData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setStaffPicksDetailData() {
        binding.tvGameTitle.setText(staffPicksList.getTitle());
        binding.tvGameDescription.setText(staffPicksList.getDescription());
        Picasso.get().load(Allurls.IMAGEURL+"/"+staffPicksList.getImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(binding.gameImg);

    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnBuyNow.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnBuyNow:
                buyNowLink = staffPicksList.getAffiliation();
                Log.d("TAG","buyNowLink>>"+buyNowLink);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(buyNowLink));
                startActivity(browserIntent);
                break;

        }
    }

}