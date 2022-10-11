package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SearchAdapters.SearchGamesAdapter;
import com.cw.playnxt.adapter.SearchAdapters.SearchUserAdapter;
import com.cw.playnxt.databinding.ActivityHomeSearchBinding;
import com.cw.playnxt.model.CheckSubscriptionFinal.CheckSubscriptionFinalResponse;
import com.cw.playnxt.model.HomeSearch.GameSearch.SearchGameDataResult;
import com.cw.playnxt.model.HomeSearch.GameSearch.SearchGameResponse;
import com.cw.playnxt.model.HomeSearch.SearchParaRes;
import com.cw.playnxt.model.HomeSearch.UserSearch.SearchUserDataResult;
import com.cw.playnxt.model.HomeSearch.UserSearch.SearchUserResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeSearchActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    String filter_type = "game";
    int free_backlog;
    private ActivityHomeSearchBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = HomeSearchActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        binding.tvHeading.setText(R.string.Search);

        Log.d("TAG", "Games cb  " + binding.cbGames.isChecked());
        Log.d("TAG", "User cb  " + binding.cbUser.isChecked());

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);


        if (Constants.isInternetConnected(context)) {
            NewCheckSubscriptionAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

        binding.cbUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.cbGames.setChecked(false);
                    filter_type = "user";
                    if (Constants.isInternetConnected(context)) {
                        SearchUserAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(binding.cbGames.isChecked()){

                    }else{
                        binding.llGame.setVisibility(View.GONE);
                        binding.llUser.setVisibility(View.GONE);
                    }
                }
            }
        });
        binding.cbGames.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.cbUser.setChecked(false);
                    filter_type = "game";
                    if (Constants.isInternetConnected(context)) {
                        SearchGameAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(binding.cbUser.isChecked()){

                    }else{
                        binding.llGame.setVisibility(View.GONE);
                        binding.llUser.setVisibility(View.GONE);
                    }
                }
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    binding.llGame.setVisibility(View.GONE);
                    binding.llUser.setVisibility(View.GONE);
                } else {
                  /*  if(filter_type.equals("user")){
                        if (Constants.isInternetConnected(context)) {
                            SearchUserAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }else if(filter_type.equals("game")){
                        if (Constants.isInternetConnected(context)) {
                            SearchGameAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }*/
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.d("TAG", "Enter pressed");
                    if (!binding.etSearch.getText().toString().equals("")) {
                        if (filter_type.equals("user")) {
                            if (Constants.isInternetConnected(context)) {
                                SearchUserAPI();
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            }
                        } else if (filter_type.equals("game")) {
                            if (Constants.isInternetConnected(context)) {
                                SearchGameAPI();
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            }
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
        binding.btnBack.setOnClickListener(this);
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
                    if (filter_type.equals("user")) {
                        if (Constants.isInternetConnected(context)) {
                            SearchUserAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else if (filter_type.equals("game")) {
                        if (Constants.isInternetConnected(context)) {
                            SearchGameAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(context, "Search keyword is required", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tvAddGame:
                startActivity(new Intent(context, AddGameActivity.class));
                break;
        }
    }

    public void SearchGameAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        if (binding.cbUser.isChecked()) {
            filter_type = "user";
        } else if (binding.cbGames.isChecked()) {
            filter_type = "game";
        }
        Log.d("TAG", "filter_type  " + filter_type);

        SearchParaRes searchParaRes = new SearchParaRes();
        searchParaRes.setKey(binding.etSearch.getText().toString().trim());
        searchParaRes.setType(filter_type);

        jsonPlaceHolderApi.SearchGameAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), searchParaRes).enqueue(new Callback<SearchGameResponse>() {
            @Override
            public void onResponse(Call<SearchGameResponse> call, Response<SearchGameResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        binding.llGame.setVisibility(View.VISIBLE);
                        binding.tvGame.setVisibility(View.VISIBLE);
                        binding.rvGames.setVisibility(View.VISIBLE);
                        binding.tvUser.setVisibility(View.GONE);
                        binding.llUser.setVisibility(View.GONE);
                        if (response.body().getData().getResult().size() != 0) {
                            binding.llNoUserResult.setVisibility(View.GONE);
                            binding.llNoGamesResult.setVisibility(View.GONE);
                            SearchGamesListDataSet(response.body().getData().getResult());
                        } else {
                            binding.rvGames.setVisibility(View.GONE);
                            binding.llNoGamesResult.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchGameResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    public void SearchUserAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        Log.d("TAG", "filter_type  " + filter_type);

        SearchParaRes searchParaRes = new SearchParaRes();
        searchParaRes.setKey(binding.etSearch.getText().toString().trim());
        searchParaRes.setType(filter_type);

        jsonPlaceHolderApi.SearchUserAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), searchParaRes).enqueue(new Callback<SearchUserResponse>() {
            @Override
            public void onResponse(Call<SearchUserResponse> call, Response<SearchUserResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        binding.llGame.setVisibility(View.GONE);
                        binding.tvGame.setVisibility(View.GONE);
                        binding.tvUser.setVisibility(View.VISIBLE);
                        binding.llUser.setVisibility(View.VISIBLE);
                        binding.rvUser.setVisibility(View.VISIBLE);
                        if (response.body().getData().getResult().size() != 0) {
                            binding.llNoUserResult.setVisibility(View.GONE);
                            binding.llNoGamesResult.setVisibility(View.GONE);
                            SearchUsersListDataSet(response.body().getData().getResult());
                        } else {
                            binding.rvUser.setVisibility(View.GONE);
                            binding.llNoUserResult.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchUserResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void SearchGamesListDataSet(List<SearchGameDataResult> resultGameList) {
        SearchGamesAdapter adapter = new SearchGamesAdapter(context, resultGameList, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                startActivity(new Intent(context, MainGameInfoActivity.class)
                        .putExtra("game_id", resultGameList.get(position).getGameId().toString())
                        .putExtra("key", Constants.USER_GAME_VIEW));
            }
        });
        binding.rvGames.setHasFixedSize(true);
        binding.rvGames.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rvGames.setAdapter(adapter);
    }

    private void SearchUsersListDataSet(List<SearchUserDataResult> resultUserList) {
        SearchUserAdapter adapter = new SearchUserAdapter(context, resultUserList, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                startActivity(new Intent(context, FriendsProfileActivity.class)
                        .putExtra("key", "1")
                        .putExtra("show_key", Constants.COMMUNITY)
                        .putExtra("friends_id", resultUserList.get(position).getId().toString()));
            }
        });
        binding.rvUser.setHasFixedSize(true);
        binding.rvUser.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rvUser.setAdapter(adapter);
    }

    //*********************************************************CHECK SUBSCRIPTION****************************************************
    public void NewCheckSubscriptionAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.NewCheckSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckSubscriptionFinalResponse>() {
            @Override
            public void onResponse(Call<CheckSubscriptionFinalResponse> call, Response<CheckSubscriptionFinalResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        if (response.body().getData().getSubscribed().equals(Constants.YES)) {
                            binding.btnAdsShow.setVisibility(View.GONE);
                        } else {
                            binding.btnAdsShow.setVisibility(View.VISIBLE);
                        }
                        free_backlog = response.body().getData().getFree_backlog();
                        Log.d("TAG", "free_backlog>>>>" + free_backlog);
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