package com.cw.playnxt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickGameInfoRecentList;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.BacklogActivity;
import com.cw.playnxt.activity.CalenderActivity;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.activity.MainGameInfoActivity;
import com.cw.playnxt.activity.SubscriptionActivity;
import com.cw.playnxt.activity.SubscriptionActivityFinal;
import com.cw.playnxt.activity.WishlistActivity;
import com.cw.playnxt.activity.YourStatsActivity;
import com.cw.playnxt.adapter.GameAdapters.GameFragmentTopListAdapter;
import com.cw.playnxt.adapter.GameAdapters.RecentGamesAdapter;
import com.cw.playnxt.databinding.FragmentGameBinding;
import com.cw.playnxt.model.CheckPlan.CheckPlanResponse;
import com.cw.playnxt.model.CheckSubscriptionFinal.CheckSubscriptionFinalResponse;
import com.cw.playnxt.model.GetRecentGame.GetRecentGameDataCapsul;
import com.cw.playnxt.model.GetRecentGame.GetRecentGameResponse;
import com.cw.playnxt.model.NewCheckSubscription.NewCheckSubscriptionResponse;
import com.cw.playnxt.model.StaticModel.GameFragmentModel;
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

public class GameFragment extends Fragment implements View.OnClickListener {
    Context context;
    String backlog_count = "", wish_count = "";
    private FragmentGameBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String planType = "";
    String subscribed = "";
    int free_backlog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_games, true);
        binding = FragmentGameBinding.inflate(inflater, container, false);
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
                    NewCheckSubscriptionAPI();
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
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Constants.isInternetConnected(context)) {
            NewCheckSubscriptionAPI();
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

    private void GameListDataSet() {
        List<GameFragmentModel> list = new ArrayList<>();
        list.add(new GameFragmentModel(R.drawable.ic_backlog, "Backlog", "" + backlog_count + "",subscribed));
        list.add(new GameFragmentModel(R.drawable.ic_wishlist, "Wishlist", "" + wish_count + "",subscribed));
        list.add(new GameFragmentModel(R.drawable.ic_calender_tool, "Calendar Tool", "",subscribed));
        list.add(new GameFragmentModel(R.drawable.ic_your_stats, "Your Stats", "",subscribed));

        GameFragmentTopListAdapter adapter = new GameFragmentTopListAdapter(context, list, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                if (position == 0) {
                    if(free_backlog == 1){
                        startActivity(new Intent(context, BacklogActivity.class));
                    }else{
                     /*   if(subscribed.equals(Constants.YES)){
                            startActivity(new Intent(context, BacklogActivity.class));
                        }else{
                            startActivity(new Intent(context, SubscriptionActivityFinal.class));
                        }*/
                        startActivity(new Intent(context, BacklogActivity.class));
                    }

                } else if (position == 1) {
                    if(subscribed.equals(Constants.YES)){
                        startActivity(new Intent(context, WishlistActivity.class));
                    }else{
                        startActivity(new Intent(context, SubscriptionActivityFinal.class));
                    }
                } else if (position == 2) {
                    if(subscribed.equals(Constants.YES)){
                        startActivity(new Intent(context, CalenderActivity.class));
                    }else{
                        startActivity(new Intent(context, SubscriptionActivityFinal.class));
                    }
                } else if (position == 3) {
                    if(subscribed.equals(Constants.YES)){
                        startActivity(new Intent(context, YourStatsActivity.class));
                    }else{
                        startActivity(new Intent(context, SubscriptionActivityFinal.class));
                    }
                }
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    //*********************************************RECENT GAMES API*******************************
    public void GetRecentGameAPI() {
        binding.rlProgressBar.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);
        jsonPlaceHolderApi.GetRecentGameAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetRecentGameResponse>() {
            @Override
            public void onResponse(Call<GetRecentGameResponse> call, Response<GetRecentGameResponse> response) {
                if (response.isSuccessful()) {
                    binding.rlProgressBar.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                    Boolean status = response.body().getStatus();
                    if (status) {
                        if (response.body().getData() != null) {
                            backlog_count = String.valueOf(response.body().getData().getBacklog_count());
                            wish_count = String.valueOf(response.body().getData().getWish_count());
                            GameListDataSet();
                            if (response.body().getData().getCapsul() != null) {
                                if (response.body().getData().getCapsul().size() != 0) {
                                    binding.llRecentlyAdded.setVisibility(View.VISIBLE);
                                    Log.d("TAG","backlog_count>>>>"+backlog_count);
                                    Log.d("TAG","wish_count>>>>"+wish_count);
                                    RecentGameListDataSet(response.body().getData().getCapsul());
                                } else {
                                    binding.llRecentlyAdded.setVisibility(View.GONE);
                                }
                            }
                        } else {

                        }

                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    binding.rlProgressBar.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetRecentGameResponse> call, Throwable t) {
                binding.rlProgressBar.setVisibility(View.GONE);
                binding.llMain.setVisibility(View.VISIBLE);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void RecentGameListDataSet(List<GetRecentGameDataCapsul> recentGameDataCapsulList) {
        RecentGamesAdapter adapter = new RecentGamesAdapter(context, recentGameDataCapsulList, new ItemClickGameInfoRecentList() {
            @Override
            public void onItemClick(int position, GetRecentGameDataCapsul getRecentGameDataCapsulList) {
                startActivity(new Intent(context, MainGameInfoActivity.class)
                        .putExtra("game_id", getRecentGameDataCapsulList.getGameId().toString())
                        .putExtra("key", Constants.SELF_GAME_VIEW)
                );
            }
        });
        binding.rvRecentGames.setHasFixedSize(true);
        binding.rvRecentGames.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvRecentGames.setAdapter(adapter);
    }

    //*********************************************************CHECK SUBSCRIPTION****************************************************
    public void NewCheckSubscriptionAPI() {
        stopSwipeRefesh();
       Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.NewCheckSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckSubscriptionFinalResponse>() {
            @Override
            public void onResponse(Call<CheckSubscriptionFinalResponse> call, Response<CheckSubscriptionFinalResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        planType =  response.body().getData().getSubscription().getType();
                        subscribed = response.body().getData().getSubscribed();
                        free_backlog =  response.body().getData().getFree_backlog();
                        Log.d("TAG","subscribed>>>>"+subscribed);
                        Log.d("TAG","free_backlog>>>>"+free_backlog);
                        if (Constants.isInternetConnected(context)) {
                            GetRecentGameAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<CheckSubscriptionFinalResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}