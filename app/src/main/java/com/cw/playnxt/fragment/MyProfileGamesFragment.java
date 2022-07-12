package com.cw.playnxt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.MainGameInfoActivity;
import com.cw.playnxt.adapter.FriendsAdapters.TabActivityAdapter;
import com.cw.playnxt.adapter.MyProfileAdapters.MyProfileTabGamesAdapter;
import com.cw.playnxt.databinding.FragmentMyProfileGamesBinding;

import com.cw.playnxt.model.GetMyProfile.Game;
import com.cw.playnxt.model.GetMyProfile.GetMyProfileResponse;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileGamesFragment extends Fragment {
    Context context;
    private FragmentMyProfileGamesBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyProfileGamesBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }
  /*  @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }*/
    public void init() {
        context = binding.getRoot().getContext();
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        if (Constants.isInternetConnected(context)) {
            GetMyProfileAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }
    public void GetMyProfileAPI() {
        binding.rlProgressBar.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);
        jsonPlaceHolderApi.GetMyProfileAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyProfileResponse>() {
            @Override
            public void onResponse(Call<GetMyProfileResponse> call, Response<GetMyProfileResponse> response) {
                if (response.isSuccessful()) {
                    binding.rlProgressBar.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                    if(response.body().getStatus() != null){
                        Boolean status = response.body().getStatus();
                        if (status)
                        {
                            if(response.body().getData().getGames().size() != 0){
                                binding.rvActivityList.setVisibility(View.VISIBLE);
                                binding.llNoData.setVisibility(View.GONE);
                                ActivityListDataSet(response.body().getData().getGames());
                            }else{
                                binding.rvActivityList.setVisibility(View.GONE);
                                binding.llNoData.setVisibility(View.VISIBLE);
                            }
                        } else {
                            binding.rvActivityList.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        binding.rvActivityList.setVisibility(View.GONE);
                        binding.llNoData.setVisibility(View.VISIBLE);
                        Toast.makeText(context,"Status Null", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyProfileResponse> call, Throwable t) {
                binding.rlProgressBar.setVisibility(View.GONE);
                binding.llMain.setVisibility(View.VISIBLE);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void ActivityListDataSet(List<Game> gamesList) {
        MyProfileTabGamesAdapter adapter = new MyProfileTabGamesAdapter(context, gamesList, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long game_id,String type) {
                startActivity(new Intent(context, MainGameInfoActivity.class)
                        .putExtra("game_id", game_id.toString())
                        .putExtra("key", Constants.SELF_GAME_VIEW)
                );
            }
        });
        binding.rvActivityList.setHasFixedSize(true);
        binding.rvActivityList.setLayoutManager(new GridLayoutManager(context,2));
        binding.rvActivityList.setAdapter(adapter);
    }
}


