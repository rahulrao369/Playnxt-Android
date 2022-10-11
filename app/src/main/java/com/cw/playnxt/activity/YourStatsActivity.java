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

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.GameAdapters.YourStatAapter;
import com.cw.playnxt.adapter.MyProfileAdapters.SettingsAdapter;
import com.cw.playnxt.databinding.ActivityCalenderBinding;
import com.cw.playnxt.databinding.ActivityYourStatsBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.GetPlatformGenre.GetPlatformGenreResponse;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.StaticModel.YourStatsModel;
import com.cw.playnxt.model.StatsData.Data;
import com.cw.playnxt.model.StatsData.GetStatsResponse;
import com.cw.playnxt.model.StatsData.staticStatDataModel;
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

public class YourStatsActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivityYourStatsBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
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
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.Stat);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.VISIBLE);

        if (Constants.isInternetConnected(context)) {
            getStatAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
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

    public void getStatAPI() {
        Customprogress.showPopupProgressSpinner(context,true);
        jsonPlaceHolderApi.getStatAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetStatsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetStatsResponse> call, Response<GetStatsResponse> response) {
                Customprogress.showPopupProgressSpinner(context,false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        binding.countTotalGames.setText(String.valueOf(response.body().getData().getTotalgames()));
                        binding.countbacklog.setText(String.valueOf(response.body().getData().getBacklogcount()));
                        binding.countWishlist.setText(String.valueOf(response.body().getData().getWishlistcount()));
                        binding.countOnTheShelf.setText(String.valueOf(response.body().getData().getOntheshelfcount())+"%");
                        binding.countRolledCredit.setText(String.valueOf(response.body().getData().getRolledcreditcount())+"%");
                        binding.countCompleted.setText(String.valueOf(response.body().getData().getCompletedcount())+"%");
                        binding.countCurrentlyPlaying.setText(String.valueOf(response.body().getData().getCurrentplayingcount())+"%");
                        binding.countTakingBreak.setText(String.valueOf(response.body().getData().getTakingbreakcount())+"%");
                        binding.countTotalRating.setText(String.valueOf(response.body().getData().getRatingtotal()));
                        binding.countAvgRating.setText(String.valueOf(response.body().getData().getAvgrate()));
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetStatsResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context,false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }


   /* private void YourStatsListDataSet() {
        List<YourStatsModel> list = new ArrayList<>();
        list.add(new YourStatsModel("Total On the Shelf ","% vs. total games in backlog","Total Currently Playing ",30,30));
        list.add(new YourStatsModel("Total currently playing/month","% vs. total games in backlog","",40,40));
        list.add(new YourStatsModel("Total Finished","Total Finished/Month","% vs. total games in backlog",15,15));
        list.add(new YourStatsModel("Total games added to backlog (by month)","Total games rated","",05,05));


        YourStatAapter adapter = new YourStatAapter(context, list, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                if (position == 0) {

                }
                else if (position == 1) {

                }
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }*/
}