package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SettingAdapters.PlaynextPremiumTabAdapter;
import com.cw.playnxt.databinding.ActivitySettingPalynxtPremiumBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.google.android.material.tabs.TabLayout;

public class SettingPlaynextPremiumActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivitySettingPalynxtPremiumBinding binding;
    private HeaderLayoutBinding headerBinding;
    PlaynextPremiumTabAdapter tabLayoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingPalynxtPremiumBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = SettingPlaynextPremiumActivity.this;
        headerBinding.tvHeading.setText("Playnext Premium");
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        tabLayoutAdapter = new PlaynextPremiumTabAdapter(getSupportFragmentManager(), binding.tablayout.getTabCount());
        binding.viewpager.setAdapter(tabLayoutAdapter);

        binding.tablayout.addTab(binding.tablayout.newTab().setText("Monthly"));
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Annual"));


        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
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
}