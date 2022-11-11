package com.cw.playnxt.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.GameInfoActivity;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.activity.MainGameInfoActivity;
import com.cw.playnxt.activity.StaffPicksDetailActivity;
import com.cw.playnxt.adapter.DiscoverAdapters.GameListAdapter;
import com.cw.playnxt.adapter.HomeAdapters.FriendsListAdapter;
import com.cw.playnxt.databinding.FragmentDiscoverBinding;
import com.cw.playnxt.databinding.FragmentHomeBinding;
import com.cw.playnxt.model.CheckSubscriptionFinal.CheckSubscriptionFinalResponse;
import com.cw.playnxt.model.HomeButton.HomeButtonResponse;
import com.cw.playnxt.model.StaffPicks.Capsul;
import com.cw.playnxt.model.StaffPicks.StaffPicksResponse;
import com.cw.playnxt.model.StaticModel.FriendsModel;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends Fragment implements View.OnClickListener{
    Context context;
    private FragmentDiscoverBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_discover,true);
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        init();
        onclicks();
        initializeRefreshListener();
        return binding.getRoot();
    }
    void initializeRefreshListener() {
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Constants.isInternetConnected(context)) {
                    StaffPicksAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void stopSwipeRefesh(){
        if(binding.swipeLayout.isRefreshing()) {
            binding.swipeLayout.setRefreshing(false);
        }
    }
    public void init() {
        context = binding.getRoot().getContext();
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        if (Constants.isInternetConnected(context)) {
            NewCheckSubscriptionAPI();
            StaffPicksAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void onclicks() {
       // binding.tvSeeAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSeeAll:
                // startActivity(new Intent(context, SeeAllJobsAndServicesActivity.class));
                break;
        }
    }


    public void StaffPicksAPI() {
        stopSwipeRefesh();
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.StaffPicksAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<StaffPicksResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<StaffPicksResponse> call, Response<StaffPicksResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus() != null){
                        Boolean status = response.body().getStatus();
                        Customprogress.showPopupProgressSpinner(context, false);
                        if (status)
                        {
                            if(response.body().getData().getCapsul().size() != 0){
                                binding.recyclerView.setVisibility(View.VISIBLE);
                                binding.llNoData.setVisibility(View.GONE);
                                staffPicksDataSet(response.body().getData().getCapsul());
                            }else{
                                binding.recyclerView.setVisibility(View.GONE);
                                binding.llNoData.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(context,"Status Null", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StaffPicksResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void staffPicksDataSet(List<Capsul> capsulList) {
        GameListAdapter adapter = new GameListAdapter(context, capsulList, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long id, String type) {
                if(type.equals("StaffPicksFragment")){

                }else if(type.equals("Buy")){
                    startActivity(new Intent(context, StaffPicksDetailActivity.class)
                            .putExtra("staffPicksList",(Serializable) capsulList.get(position)));
                }
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        binding.recyclerView.setAdapter(adapter);
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