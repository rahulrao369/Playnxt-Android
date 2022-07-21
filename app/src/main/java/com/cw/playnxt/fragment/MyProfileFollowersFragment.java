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
import com.cw.playnxt.activity.ChatActivity;
import com.cw.playnxt.activity.FriendsProfileActivity;
import com.cw.playnxt.adapter.FriendsAdapters.TabFollowersAdapter;
import com.cw.playnxt.adapter.MyProfileAdapters.MyProfileTabFollowersAdapter;
import com.cw.playnxt.adapter.MyProfileAdapters.MyProfileTabGamesAdapter;
import com.cw.playnxt.databinding.FragmentMyProfileFollowersBinding;
import com.cw.playnxt.databinding.FragmentTabFollowersBinding;
import com.cw.playnxt.model.CommunityData.CapsulCommunityData;
import com.cw.playnxt.model.FollowFriend.FollowFriendParaRes;
import com.cw.playnxt.model.FollowFriend.FollowFriendResponse;
import com.cw.playnxt.model.GetMyProfile.Follower;
import com.cw.playnxt.model.GetMyProfile.Game;
import com.cw.playnxt.model.GetMyProfile.GetMyProfileResponse;
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

public class MyProfileFollowersFragment extends Fragment {
    Context context;
    private FragmentMyProfileFollowersBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyProfileFollowersBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }
 /*   @Override
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
                            if(response.body().getData().getFollower().size() != 0){
                                binding.rvFollowersList.setVisibility(View.VISIBLE);
                                binding.llNoData.setVisibility(View.GONE);
                                FollowersListDataSet(response.body().getData().getFollower());
                            }else{
                                binding.rvFollowersList.setVisibility(View.GONE);
                                binding.llNoData.setVisibility(View.VISIBLE);
                            }
                        } else {
                            binding.rvFollowersList.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        binding.rvFollowersList.setVisibility(View.GONE);
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
    private void FollowersListDataSet(List<Follower> followerList) {
        MyProfileTabFollowersAdapter adapter = new MyProfileTabFollowersAdapter(context, followerList, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long id, String type) {
                if(type.equals("follow")){
                    if (Constants.isInternetConnected(context)) {
                        FollowFriendAPI(id);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else  if(type.equals("unfollow")){
                    if (Constants.isInternetConnected(context)) {
                        UnfollowFriendAPI(id);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else if(type.equals("FollowerAdapter")){
                    startActivity(new Intent(context, FriendsProfileActivity.class)
                            .putExtra("key","1")
                            .putExtra("friends_id",id.toString()));
                    Log.d("TAG", "friends_id>>"+id);
                }else if(type.equals("message")){
                    startActivity(new Intent(context, ChatActivity.class)
                            .putExtra("receiverId", id.toString())
                            .putExtra("receiverName", followerList.get(position).getName())
                            .putExtra("receiverImage", followerList.get(position).getImage().toString()));
                    Log.d("TAG", "friends_id>>"+id);
                }
            }
        });
        binding.rvFollowersList.setHasFixedSize(true);
        binding.rvFollowersList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rvFollowersList.setAdapter(adapter);
    }

    //************************************FOLLOW FRIEND API*********************************************
    public void FollowFriendAPI(Long user_id) {
        Customprogress.showPopupProgressSpinner(context, true);
        FollowFriendParaRes followFriendParaRes = new FollowFriendParaRes();
        followFriendParaRes.setUserId(user_id.toString());

        jsonPlaceHolderApi.FollowFriendAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), followFriendParaRes).enqueue(new Callback<FollowFriendResponse>() {
            @Override
            public void onResponse(Call<FollowFriendResponse> call, Response<FollowFriendResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                      //  Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetMyProfileAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<FollowFriendResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
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
                      //  Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetMyProfileAPI();
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