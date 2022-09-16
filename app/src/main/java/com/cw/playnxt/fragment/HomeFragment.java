package com.cw.playnxt.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.FriendsProfileActivity;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.activity.HomeSearchActivity;
import com.cw.playnxt.activity.MainGameInfoActivity;
import com.cw.playnxt.activity.MyProfileActivity;
import com.cw.playnxt.adapter.HomeAdapters.FriendsListAdapter;
import com.cw.playnxt.databinding.FragmentHomeBinding;
import com.cw.playnxt.model.HomeButton.HomeButtonResponse;
import com.cw.playnxt.model.HomeData.Following;
import com.cw.playnxt.model.HomeData.HomeApiResponse;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener{
    Context context;
    private FragmentHomeBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    Vibrator vibe;
    String friend_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_home,true);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        onclicks();
        CLick();
        initializeRefreshListener();
        return binding.getRoot();
    }
    void initializeRefreshListener() {
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Constants.isInternetConnected(context)) {
                    HomeAPI();
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
    private void CLick() {
        binding.tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment someFragment = new NewFriendsFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("key", 1);
                someFragment.setArguments(bundle2);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameContainer, someFragment );
                transaction.addToBackStack("dvcsdvcd");
                transaction.commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        init();

    }

    public void init() {
        context = binding.getRoot().getContext();
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Log.d("TAG","Home Token>>>"+mySharedPref.getSavedAccessToken());

        // Initialize the Mobile Ads SDK
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        if (Constants.isInternetConnected(context)) {
            HomeAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }


    public void onclicks() {
        binding.btnPlaynxt.setOnClickListener(this);
        binding.llMyProfile.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMyProfile:
                startActivity(new Intent(context, MyProfileActivity.class)
                        .putExtra("key","1"));
                break;

                case R.id.ivSearch:
                startActivity(new Intent(context, HomeSearchActivity.class));
                break;

            case R.id.btnPlaynxt:
                vibe.vibrate(80);
                if (Constants.isInternetConnected(context)) {
                    HomeButtonAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    //**********************************************HOME API***************************************
    public void HomeAPI() {
        stopSwipeRefesh();
        binding.rlProgressBar.setVisibility(View.VISIBLE);
        binding.llHomeMain.setVisibility(View.GONE);
        jsonPlaceHolderApi.HomeAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<HomeApiResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<HomeApiResponse> call, Response<HomeApiResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus() != null){
                        Boolean status = response.body().getStatus();
                        binding.rlProgressBar.setVisibility(View.GONE);
                        binding.llHomeMain.setVisibility(View.VISIBLE);
                        if (status)
                        {
                            if(response.body().getData() != null){
                                Picasso.get().load(Allurls.IMAGEURL+response.body().getData().getProfile().getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(binding.cvMyProfile);
                                String[] splitStr = response.body().getData().getProfile().getName().split("\\s+");
                                String userName = splitStr[0].substring(0, 1).toUpperCase() + splitStr[0].substring(1).toLowerCase();
                                binding.tvUserName.setText("Hi, "+userName);
                                if(response.body().getData().getFollowing().size() != 0){
                                    binding.tvNoFriendsYet.setVisibility(View.GONE);
                                    binding.llFriendsList.setVisibility(View.VISIBLE);
                                    setMyFriendsProfileListData(response.body().getData().getFollowing());
                                }else{
                                    binding.tvNoFriendsYet.setVisibility(View.VISIBLE);
                                    binding.llFriendsList.setVisibility(View.GONE);
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
            public void onFailure(Call<HomeApiResponse> call, Throwable t) {
                binding.rlProgressBar.setVisibility(View.GONE);
                binding.llHomeMain.setVisibility(View.VISIBLE);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void setMyFriendsProfileListData(List<Following> followingList) {
        FriendsListAdapter adapter = new FriendsListAdapter(context, followingList, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                startActivity(new Intent(context, FriendsProfileActivity.class)
                        .putExtra("key","1")
                        .putExtra("show_key", Constants.FRIENDS)
                        .putExtra("friends_id",followingList.get(position).getUserId().toString())
                );
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setAdapter(adapter);
    }
    //**********************************************HOME BUTTON API***************************************

    public void HomeButtonAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.HomeButtonAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<HomeButtonResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<HomeButtonResponse> call, Response<HomeButtonResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus() != null){
                        Boolean status = response.body().getStatus();
                        Customprogress.showPopupProgressSpinner(context, false);
                        if (status)
                        {
                            if(response.body().getPresence() == 0){
                                startActivity(new Intent(context, MainGameInfoActivity.class)
                                        .putExtra("game_id", response.body().getData().getGameId().toString())
                                        .putExtra("key", Constants.SELF_GAME_VIEW)
                                );
                            }else{
                                startActivity(new Intent(context, HomeSearchActivity.class));
                              /*  DiscoverFragment nextFrag= new DiscoverFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.Layout_container, nextFrag, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();*/
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
            public void onFailure(Call<HomeButtonResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
}