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
import com.cw.playnxt.activity.FriendsProfileActivity;
import com.cw.playnxt.activity.GameInfoActivity;
import com.cw.playnxt.activity.MainGameInfoActivity;
import com.cw.playnxt.adapter.DiscoverAdapters.GameListAdapter;
import com.cw.playnxt.adapter.FriendsAdapters.FriendsListFriendsFragmentAdapter;
import com.cw.playnxt.adapter.FriendsAdapters.TabActivityAdapter;
import com.cw.playnxt.databinding.FragmentGameBinding;
import com.cw.playnxt.databinding.FragmentTabActivityBinding;
import com.cw.playnxt.model.GetMyFriendProfile.GetMyFriendProfileParaRes;
import com.cw.playnxt.model.GetMyFriendProfile.GetMyFriendProfileResponse;
import com.cw.playnxt.model.GetMyFriendProfile.MyFriendProfileGame;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabActivityFragment extends Fragment {
    Context context;
    private FragmentTabActivityBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String FRIEND_ID;

    public TabActivityFragment(String friendsId) {
        FRIEND_ID =friendsId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTabActivityBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }
/*    @Override
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
            GetMyFriendProfileAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
        Log.d("TAG","FRIEND_ID>>"+FRIEND_ID);
    }

    public void GetMyFriendProfileAPI() {
        binding.rlProgressBar.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);
        GetMyFriendProfileParaRes getMyFriendProfileParaRes = new GetMyFriendProfileParaRes();
        getMyFriendProfileParaRes.setUserId(Long.valueOf(FRIEND_ID));

        jsonPlaceHolderApi.GetMyFriendProfileAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), getMyFriendProfileParaRes).enqueue(new Callback<GetMyFriendProfileResponse>() {
            @Override
            public void onResponse(Call<GetMyFriendProfileResponse> call, Response<GetMyFriendProfileResponse> response) {
                if (response.isSuccessful()) {
                    binding.rlProgressBar.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                    if (response.body().getStatus() != null) {
                        Boolean status = response.body().getStatus();
                        if (status) {
                            if (response.body().getData() != null) {
                                if(response.body().getData().getGames().size() != 0){
                                    binding.rvActivityList.setVisibility(View.VISIBLE);
                                    binding.llNoData.setVisibility(View.GONE);
                                    ActivityListDataSet(response.body().getData().getGames());
                                }else{
                                    binding.rvActivityList.setVisibility(View.GONE);
                                    binding.llNoData.setVisibility(View.VISIBLE);
                                }

                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            binding.rlProgressBar.setVisibility(View.GONE);
                            binding.llMain.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(context, "Status Null", Toast.LENGTH_LONG).show();
                        binding.rlProgressBar.setVisibility(View.GONE);
                        binding.llMain.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyFriendProfileResponse> call, Throwable t) {
                binding.rlProgressBar.setVisibility(View.GONE);
                binding.llMain.setVisibility(View.VISIBLE);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void ActivityListDataSet(List<MyFriendProfileGame> gamesList) {
        TabActivityAdapter adapter = new TabActivityAdapter(context, gamesList, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long game_id,String type) {
                startActivity(new Intent(context, MainGameInfoActivity.class)
                        .putExtra("game_id", game_id.toString())
                        .putExtra("key", Constants.USER_GAME_VIEW));
            }
        });
        binding.rvActivityList.setHasFixedSize(true);
        binding.rvActivityList.setLayoutManager(new GridLayoutManager(context,2));
        binding.rvActivityList.setAdapter(adapter);
    }
}