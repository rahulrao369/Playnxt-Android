package com.cw.playnxt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.cw.playnxt.adapter.FriendsAdapters.TabActivityAdapter;
import com.cw.playnxt.adapter.FriendsAdapters.TabFollowingAdapter;
import com.cw.playnxt.databinding.FragmentTabActivityBinding;
import com.cw.playnxt.databinding.FragmentTabFollowersBinding;
import com.cw.playnxt.databinding.FragmentTabFollowingBinding;
import com.cw.playnxt.model.GetMyFriendProfile.GetMyFriendProfileParaRes;
import com.cw.playnxt.model.GetMyFriendProfile.GetMyFriendProfileResponse;
import com.cw.playnxt.model.GetMyFriendProfile.MyFriendProfileFollowing;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.UnfollowFriend.UnfollowFriendParaRes;
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

public class TabFollowingFragment extends Fragment {
    Context context;
    private FragmentTabFollowingBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String FRIEND_ID;

    public TabFollowingFragment(String friendsId) {
        FRIEND_ID = friendsId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTabFollowingBinding.inflate(inflater, container, false);
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
                                if(response.body().getData().getFollowing().size() != 0){
                                    binding.rvFollowingList.setVisibility(View.VISIBLE);
                                    binding.llNoData.setVisibility(View.GONE);
                                    FollowingListDataSet(response.body().getData().getFollowing());
                                }else{
                                    binding.rvFollowingList.setVisibility(View.GONE);
                                    binding.llNoData.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            binding.rvFollowingList.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(context, "Status Null", Toast.LENGTH_LONG).show();
                        binding.rvFollowingList.setVisibility(View.GONE);
                        binding.llNoData.setVisibility(View.VISIBLE);
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
    private void FollowingListDataSet(List<MyFriendProfileFollowing> followingList) {
        TabFollowingAdapter adapter = new TabFollowingAdapter(context, followingList, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long id, String type) {
                if(type.equals("unfollow")){
                    if (Constants.isInternetConnected(context)) {
                        UnfollowFriendAPI(id);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else if(type.equals("FollowingAdapter")){
                    startActivity(new Intent(context, FriendsProfileActivity.class)
                            .putExtra("key","1")
                            .putExtra("show_key", Constants.COMMUNITY)
                            .putExtra("friends_id",id.toString()));
                }
            }
        });
        binding.rvFollowingList.setHasFixedSize(true);
        binding.rvFollowingList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rvFollowingList.setAdapter(adapter);
    }
    //************************************FOLLOW FRIEND API*********************************************
    public void UnfollowFriendAPI(Long user_id) {
        Customprogress.showPopupProgressSpinner(context, true);
        UnfollowFriendParaRes unfollowFriendParaRes = new UnfollowFriendParaRes();
        unfollowFriendParaRes.setUserId(user_id);

        jsonPlaceHolderApi.UnfollowFriendAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), unfollowFriendParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        //   Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetMyFriendProfileAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseSatusMessage> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
}