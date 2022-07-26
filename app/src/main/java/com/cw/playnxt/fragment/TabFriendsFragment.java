package com.cw.playnxt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cw.playnxt.Interface.ItemClickLikeUnlikeFriend;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.FriendsProfileActivity;
import com.cw.playnxt.adapter.FriendsAdapters.TabFriendsAdapter;
import com.cw.playnxt.databinding.FragmentTabFriendsBinding;
import com.cw.playnxt.model.CommunityData.CapsulCommunityData;
import com.cw.playnxt.model.FollowFriend.FollowFriendParaRes;
import com.cw.playnxt.model.FollowFriend.FollowFriendResponse;
import com.cw.playnxt.model.GetMyFriendList.GetMyFriendListDataCapsul;
import com.cw.playnxt.model.GetMyFriendList.GetMyFriendListResponse;
import com.cw.playnxt.model.LikeAndUnlikeProfile.LikeAndUnlikeProfileParaRes;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.UnfollowFriend.UnfollowFriendParaRes;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFriendsFragment extends Fragment {
    Context context;
    private FragmentTabFriendsBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTabFriendsBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    public void init() {
        context = getActivity();
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);

        if (Constants.isInternetConnected(context)) {
            GetMyFriendListAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    public void GetMyFriendListAPI() {
        binding.rlProgressBar.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);

        jsonPlaceHolderApi.GetMyFriendListAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyFriendListResponse>() {
            @Override
            public void onResponse(Call<GetMyFriendListResponse> call, Response<GetMyFriendListResponse> response) {
                if (response.isSuccessful()) {
                    binding.rlProgressBar.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                    if(response.body().getStatus() != null){
                        Boolean status = response.body().getStatus();
                        if (status)
                        {
                            if(response.body().getData() != null){
                                if(response.body().getData().getCapsul().size() != 0){
                                    binding.recyclerView.setVisibility(View.VISIBLE);
                                    binding.llNoData.setVisibility(View.GONE);
                                    FriendsListDataSet(response.body().getData().getCapsul());
                                }else{
                                    binding.recyclerView.setVisibility(View.GONE);
                                    binding.llNoData.setVisibility(View.VISIBLE);
                                }

                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(context,"Status Null", Toast.LENGTH_LONG).show();
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.llNoData.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyFriendListResponse> call, Throwable t) {
                binding.rlProgressBar.setVisibility(View.GONE);
                binding.llMain.setVisibility(View.VISIBLE);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
    private void FriendsListDataSet(List<GetMyFriendListDataCapsul> capsulMyFriendsList) {
        TabFriendsAdapter adapter = new TabFriendsAdapter(context, capsulMyFriendsList, new ItemClickLikeUnlikeFriend() {
            @Override
            public void onItemClick(String type, GetMyFriendListDataCapsul data, String statusLike) {
                if(type.equals("UnFav")){
                    if (Constants.isInternetConnected(context)) {
                        LikeAndUnlikeProfileAPI(data,statusLike);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else if(type.equals("Fav")){
                    if (Constants.isInternetConnected(context)) {
                        LikeAndUnlikeProfileAPI(data, statusLike);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else if(type.equals("unfollow")){
                    if (Constants.isInternetConnected(context)) {
                        UnfollowFriendAPI(data);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else if(type.equals("CommunityFragmentMain")){
                    startActivity(new Intent(context, FriendsProfileActivity.class)
                            .putExtra("key","1")
                            .putExtra("friends_id",data.getUserId().toString())
                    );
                }


            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    //************************************LIKE AND UNLIKE PROFILE API*********************************************
    public void LikeAndUnlikeProfileAPI(GetMyFriendListDataCapsul data, String statusLike) {
        Customprogress.showPopupProgressSpinner(context, true);
        LikeAndUnlikeProfileParaRes likeAndUnlikeProfileParaRes= new LikeAndUnlikeProfileParaRes();
        likeAndUnlikeProfileParaRes.setUserId(data.getUserId());
        likeAndUnlikeProfileParaRes.setStatus(statusLike);

        jsonPlaceHolderApi.LikeAndUnlikeProfileAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), likeAndUnlikeProfileParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                       // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetMyFriendListAPI();
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

    //************************************FOLLOW FRIEND API*********************************************
    public void UnfollowFriendAPI(GetMyFriendListDataCapsul data) {
        Customprogress.showPopupProgressSpinner(context, true);
        UnfollowFriendParaRes unfollowFriendParaRes = new UnfollowFriendParaRes();
        unfollowFriendParaRes.setUserId(data.getUserId());

        jsonPlaceHolderApi.UnfollowFriendAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), unfollowFriendParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                     //   Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetMyFriendListAPI();
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