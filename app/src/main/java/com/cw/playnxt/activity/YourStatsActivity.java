package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.GameAdapters.YourStatAapter;
import com.cw.playnxt.adapter.MyProfileAdapters.SettingsAdapter;
import com.cw.playnxt.databinding.ActivityCalenderBinding;
import com.cw.playnxt.databinding.ActivityYourStatsBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.StaticModel.YourStatsModel;

import java.util.ArrayList;
import java.util.List;

public class YourStatsActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivityYourStatsBinding binding;
    private HeaderLayoutBinding headerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYourStatsBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = YourStatsActivity.this;
        headerBinding.tvHeading.setText(R.string.Stat);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.VISIBLE);

        YourStatsListDataSet();
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        headerBinding.btnAdd.setOnClickListener(this);
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

    private void YourStatsListDataSet() {
        List<YourStatsModel> list = new ArrayList<>();
        list.add(new YourStatsModel("Total On the Shelf ","% vs. total games in backlog","Total Currently Playing ",30,30));
        list.add(new YourStatsModel("Total currently playing/month","% vs. total games in backlog","",40,40));
        list.add(new YourStatsModel("Total Finished","Total Finished/Month","% vs. total games in backlog",15,15));
        list.add(new YourStatsModel("Total games added to backlog (by month)","Total games rated","",05,05));


        YourStatAapter adapter = new YourStatAapter(context, list, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                if (position == 0) {
                    startActivity(new Intent(context,SettingPlaynextPremiumActivity.class));
                }
                else if (position == 1) {
                    startActivity(new Intent(context,SuggestNewFeatureActivity.class));
                }
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }
}