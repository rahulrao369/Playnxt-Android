package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SearchAdapters.SearchGamesAdapter;
import com.cw.playnxt.adapter.SearchAdapters.SearchUserAdapter;
import com.cw.playnxt.databinding.ActivityHomeSearchBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.CheckPlan.CheckPlanResponse;
import com.cw.playnxt.model.HomeSearch.SearchFollowing;
import com.cw.playnxt.model.HomeSearch.SearchGame;
import com.cw.playnxt.model.HomeSearch.SearchParaRes;
import com.cw.playnxt.model.HomeSearch.SearchProfile;
import com.cw.playnxt.model.HomeSearch.SearchResponse;
import com.cw.playnxt.model.MyActivePlan.MyActivePlanResponse;
import com.cw.playnxt.model.StaticModel.GameModel;
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

public class HomeSearchActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivityHomeSearchBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String filter_byUser="";
    String filter_byGame="";
    String activePlan = "";
    String planType = "";
    int totalBacklog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeSearchBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = HomeSearchActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.Search);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        Log.d("TAG","Games cb  "+binding.cbGames.isChecked());
        Log.d("TAG","User cb  "+binding.cbUser.isChecked());
        if (Constants.isInternetConnected(context)) {
            CheckPlanAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.d("TAG","Enter pressed");
                    if (!binding.etSearch.getText().toString().equals("")) {
                        if (Constants.isInternetConnected(context)) {
                            SearchAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Search keyword is required", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
        binding.tvAddGame.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.ivSearch:
                if (!binding.etSearch.getText().toString().equals("")) {
                    if (Constants.isInternetConnected(context)) {
                        SearchAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Search keyword is required", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tvAddGame:
                if (activePlan.equals(Constants.ACTIVE_PLAN_YES)) {
                    if(planType.equals(Constants.PLAN_TYPE_PAID)){
                        startActivity(new Intent(context, AddGameActivity.class));
                    }else {
                       /* if(totalBacklog == 0){
                            startActivity(new Intent(context, SubscriptionActivity.class));
                        }else{
                            startActivity(new Intent(context, AddGameActivity.class));
                        }*/
                        startActivity(new Intent(context, AddGameActivity.class));

                    }
                } else {
                    startActivity(new Intent(context, SubscriptionActivity.class));
                }
                break;
        }
    }

    public void SearchAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        if(binding.cbUser.isChecked()){
            filter_byUser = "1";
        }else{
            filter_byUser = "0";
        }
        if(binding.cbGames.isChecked()){
            filter_byGame = "1";
        }else{
            filter_byGame = "0";
        }
        Log.d("TAG","Games filter  "+filter_byGame);
        Log.d("TAG","User filter  "+filter_byUser);

        SearchParaRes searchParaRes = new SearchParaRes();
        searchParaRes.setKey(binding.etSearch.getText().toString().trim());
        searchParaRes.setFilterByuser(filter_byUser);
        searchParaRes.setFilterBygame(filter_byGame);

        jsonPlaceHolderApi.SearchAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), searchParaRes).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        if(filter_byUser.equals("1")){
                            if(response.body().getData().getFollowing().size() != 0){
                                binding.rvUser.setVisibility(View.VISIBLE);
                                binding.llNoUserResult.setVisibility(View.GONE);
                                binding.rvGames.setVisibility(View.GONE);
                                binding.llNoGamesResult.setVisibility(View.VISIBLE);
                                SearchUsersListDataSet(response.body().getData().getFollowing());
                            }else{
                                binding.rvUser.setVisibility(View.GONE);
                                binding.llNoUserResult.setVisibility(View.VISIBLE);
                                binding.rvGames.setVisibility(View.GONE);
                                binding.llNoGamesResult.setVisibility(View.VISIBLE);
                            }
                        }else if(filter_byGame.equals("1")){
                            if(response.body().getData().getGames().size() != 0){
                                binding.rvGames.setVisibility(View.VISIBLE);
                                binding.llNoGamesResult.setVisibility(View.GONE);
                                SearchGamesListDataSet(response.body().getData().getGames());
                                binding.rvUser.setVisibility(View.GONE);
                                binding.llNoUserResult.setVisibility(View.VISIBLE);
                            }else{
                                binding.rvGames.setVisibility(View.GONE);
                                binding.llNoGamesResult.setVisibility(View.VISIBLE);
                                binding.rvUser.setVisibility(View.GONE);
                                binding.llNoUserResult.setVisibility(View.VISIBLE);
                            }
                        }else if(filter_byUser.equals("1") && filter_byGame.equals("1")){
                            if(response.body().getData().getFollowing().size() != 0){
                                binding.rvUser.setVisibility(View.VISIBLE);
                                binding.llNoUserResult.setVisibility(View.GONE);
                                SearchUsersListDataSet(response.body().getData().getFollowing());
                            }else if(response.body().getData().getGames().size() != 0){
                                binding.rvGames.setVisibility(View.VISIBLE);
                                binding.llNoGamesResult.setVisibility(View.GONE);
                                SearchGamesListDataSet(response.body().getData().getGames());
                            }else{
                                binding.rvGames.setVisibility(View.GONE);
                                binding.llNoGamesResult.setVisibility(View.VISIBLE);
                                binding.rvUser.setVisibility(View.GONE);
                                binding.llNoUserResult.setVisibility(View.VISIBLE);
                            }
                        }else{
                            binding.rvGames.setVisibility(View.GONE);
                            binding.llNoGamesResult.setVisibility(View.VISIBLE);
                            binding.rvUser.setVisibility(View.GONE);
                            binding.llNoUserResult.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
    private void SearchGamesListDataSet(List<SearchGame> gamesList) {
        SearchGamesAdapter adapter = new SearchGamesAdapter(context, gamesList, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {

            }
        });
        binding.rvGames.setHasFixedSize(true);
        binding.rvGames.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rvGames.setAdapter(adapter);
    }

    private void SearchUsersListDataSet(List<SearchFollowing> followingList) {
        SearchUserAdapter adapter = new SearchUserAdapter(context, followingList, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {

            }
        });
        binding.rvUser.setHasFixedSize(true);
        binding.rvUser.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rvUser.setAdapter(adapter);
    }
    //*********************************************************CHECK PLAN****************************************************
    public void CheckPlanAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.CheckPlanAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckPlanResponse>() {
            @Override
            public void onResponse(Call<CheckPlanResponse> call, Response<CheckPlanResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        activePlan = response.body().getData().getActivePlan();
                        planType =  response.body().getData().getActplan();
                        totalBacklog =  response.body().getData().getTotalBacklog();
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<CheckPlanResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}