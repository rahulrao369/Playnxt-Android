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

import com.cw.playnxt.Interface.ItemClickFavUnFavCommunity;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.FriendsProfileActivity;
import com.cw.playnxt.adapter.FriendsAdapters.TabCommunityAdapter;
import com.cw.playnxt.databinding.FragmentTabCommunityBinding;
import com.cw.playnxt.model.CommunityData.CapsulCommunityData;
import com.cw.playnxt.model.CommunityData.GetCommunityListResponse;
import com.cw.playnxt.model.FollowFriend.FollowFriendParaRes;
import com.cw.playnxt.model.FollowFriend.FollowFriendResponse;
import com.cw.playnxt.model.LikeAndUnlikeProfile.LikeAndUnlikeProfileParaRes;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabCommunityFragment extends Fragment {
    Context context;
    private FragmentTabCommunityBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTabCommunityBinding.inflate(inflater, container, false);
        init();
        initializeRefreshListener();
        return binding.getRoot();
    }
    void initializeRefreshListener() {
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Constants.isInternetConnected(context)) {
                    GetCommunityListAPI();
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

        if (Constants.isInternetConnected(context)) {
            GetCommunityListAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void GetCommunityListAPI() {
        stopSwipeRefesh();
        binding.rlProgressBar.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);

        jsonPlaceHolderApi.GetCommunityListAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetCommunityListResponse>() {
            @Override
            public void onResponse(Call<GetCommunityListResponse> call, Response<GetCommunityListResponse> response) {
                if (response.isSuccessful()) {
                    binding.rlProgressBar.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                    if (response.body().getStatus() != null) {
                        Boolean status = response.body().getStatus();
                        if (status) {
                            if (response.body().getData() != null) {
                                if (response.body().getData().getCapsul().size() != 0) {
                                    binding.recyclerView.setVisibility(View.VISIBLE);
                                    binding.llNoData.setVisibility(View.GONE);
                                    CommunityListDataSet(response.body().getData().getCapsul());
                                } else {
                                    binding.recyclerView.setVisibility(View.GONE);
                                    binding.llNoData.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                binding.recyclerView.setVisibility(View.GONE);
                                binding.llNoData.setVisibility(View.VISIBLE);
                            }

                        } else {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Status Null", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCommunityListResponse> call, Throwable t) {
                binding.rlProgressBar.setVisibility(View.GONE);
                binding.llMain.setVisibility(View.VISIBLE);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void CommunityListDataSet(List<CapsulCommunityData> capsulCommunityDataList) {
        TabCommunityAdapter adapter = new TabCommunityAdapter(context, capsulCommunityDataList, new ItemClickFavUnFavCommunity() {
            @Override
            public void onItemClick(String type, CapsulCommunityData data, String statusLike) {
                if (type.equals("UnFav")) {
                    if (Constants.isInternetConnected(context)) {
                        LikeAndUnlikeProfileAPI(data, statusLike);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                } else if (type.equals("Fav")) {
                    if (Constants.isInternetConnected(context)) {
                        LikeAndUnlikeProfileAPI(data, statusLike);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                } else if (type.equals("follow")) {
                    if (Constants.isInternetConnected(context)) {
                        FollowFriendAPI(data);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                } else if (type.equals("CommunityFragmentMain")) {
                    startActivity(new Intent(context, FriendsProfileActivity.class)
                            .putExtra("key", "1")
                            .putExtra("show_key", Constants.COMMUNITY)
                            .putExtra("friends_id", data.getUserId().toString())
                    );
                    Log.d("TAG", "friends_id>>" + data.getUserId().toString());
                }


            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    //************************************LIKE AND UNLIKE PROFILE API*********************************************
    public void LikeAndUnlikeProfileAPI(CapsulCommunityData data, String statusLike) {
        Customprogress.showPopupProgressSpinner(context, true);
        LikeAndUnlikeProfileParaRes likeAndUnlikeProfileParaRes = new LikeAndUnlikeProfileParaRes();
        likeAndUnlikeProfileParaRes.setUserId(data.getUserId());
        likeAndUnlikeProfileParaRes.setStatus(statusLike);

        jsonPlaceHolderApi.LikeAndUnlikeProfileAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), likeAndUnlikeProfileParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        //  Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                       /* if (Constants.isInternetConnected(context)) {
                            GetCommunityListAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }*/
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
    public void FollowFriendAPI(CapsulCommunityData data) {
        Customprogress.showPopupProgressSpinner(context, true);
        FollowFriendParaRes followFriendParaRes = new FollowFriendParaRes();
        followFriendParaRes.setUserId(data.getUserId().toString());

        jsonPlaceHolderApi.FollowFriendAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), followFriendParaRes).enqueue(new Callback<FollowFriendResponse>() {
            @Override
            public void onResponse(Call<FollowFriendResponse> call, Response<FollowFriendResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        //   Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetCommunityListAPI();
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
}