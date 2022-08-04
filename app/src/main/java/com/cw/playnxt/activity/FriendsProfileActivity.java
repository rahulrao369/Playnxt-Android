package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.cw.playnxt.R;
import com.cw.playnxt.adapter.FriendsAdapters.TabLayoutAdapter;
import com.cw.playnxt.databinding.ActivityFriendsProfileBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.FollowFriend.FollowFriendParaRes;
import com.cw.playnxt.model.FollowFriend.FollowFriendResponse;
import com.cw.playnxt.model.GetMyFriendProfile.GetMyFriendProfileParaRes;
import com.cw.playnxt.model.GetMyFriendProfile.GetMyFriendProfileResponse;
import com.cw.playnxt.model.GetMyFriendProfile.MyFriendProfile;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    TabLayoutAdapter tabLayoutAdapter;
    Long game_count, followers_count, following_count;
    String friends_id;
    String tab;
    String profile_image = "";
    MyFriendProfile friendsProfileDetail = null;
    String show_key = "";
    private ActivityFriendsProfileBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendsProfileBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = FriendsProfileActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.FriendsProfile);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        try {
            Intent intent = getIntent();
            if (intent != null) {
                tab = intent.getStringExtra("key");
                show_key = intent.getStringExtra("show_key");
                friends_id = intent.getStringExtra("friends_id");
                Log.d("TAG", "friends_id>>" + friends_id);
                Log.d("TAG", "tab>>" + tab);
                Log.d("TAG", "show_key>>" + show_key);

                if (show_key.equals(Constants.COMMUNITY)) {
                    binding.btnFollow.setVisibility(View.VISIBLE);
                    binding.btnMessage.setVisibility(View.GONE);
                } else {
                    binding.btnFollow.setVisibility(View.GONE);
                    binding.btnMessage.setVisibility(View.VISIBLE);
                }

                if (Constants.isInternetConnected(context)) {
                    GetMyFriendProfileAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.cvFriendsProfile.setOnClickListener(this);
        binding.btnMessage.setOnClickListener(this);
        binding.btnFollow.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.cvFriendsProfile:
                openDialogBigProfile();
                break;

            case R.id.btnMessage:
                if (friendsProfileDetail != null) {
                    startActivity(new Intent(context, ChatActivity.class)
                            .putExtra("receiverId", friendsProfileDetail.getId().toString())
                            .putExtra("receiverName", friendsProfileDetail.getName())
                            .putExtra("receiverImage", friendsProfileDetail.getImage().toString()));
                }
                break;
            case R.id.btnFollow:
                if (Constants.isInternetConnected(context)) {
                    FollowFriendAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void openDialogBigProfile() {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_big_profile_image);
        AppCompatImageView iv_profile_image = (AppCompatImageView) dialog.findViewById(R.id.iv_profile_image);
        MaterialCardView cv_cross = (MaterialCardView) dialog.findViewById(R.id.cv_cross);
        Picasso.get().load(Allurls.IMAGEURL + profile_image).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(iv_profile_image);

        cv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void GetMyFriendProfileAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        GetMyFriendProfileParaRes getMyFriendProfileParaRes = new GetMyFriendProfileParaRes();
        getMyFriendProfileParaRes.setUserId(Long.valueOf(friends_id));

        jsonPlaceHolderApi.GetMyFriendProfileAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), getMyFriendProfileParaRes).enqueue(new Callback<GetMyFriendProfileResponse>() {
            @Override
            public void onResponse(Call<GetMyFriendProfileResponse> call, Response<GetMyFriendProfileResponse> response) {
                if (response.isSuccessful()) {
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (response.body().getStatus() != null) {
                        Boolean status = response.body().getStatus();
                        if (status) {
                            if (response.body().getData() != null) {
                                game_count = response.body().getData().getTotelGame();
                                followers_count = response.body().getData().getTotelFollower();
                                following_count = response.body().getData().getTotelFollowing();
                                profile_image = response.body().getData().getProfile().getImage();
                                setMyFriendsProfileData(response.body().getData().getProfile());
                                friendsProfileDetail = response.body().getData().getProfile();
                                tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), binding.tablayout.getTabCount(), friends_id);
                                binding.viewpager.setAdapter(tabLayoutAdapter);

                                binding.tablayout.addTab(binding.tablayout.newTab().setText(game_count + "\n" +
                                        "Games"));
                                binding.tablayout.addTab(binding.tablayout.newTab().setText(followers_count + "\n" +
                                        "Followers"));
                                binding.tablayout.addTab(binding.tablayout.newTab().setText(following_count + "\n" +
                                        "Following"));

                                binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {
                                        binding.viewpager.setCurrentItem(tab.getPosition());
                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });
                                binding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
                                if (tab.equals("1")) {
                                    binding.tablayout.getTabAt(0).select();
                                } else if (tab.equals("3")) {
                                    binding.tablayout.getTabAt(2).select();
                                } else {
                                    binding.tablayout.getTabAt(0).select();
                                }
                            }
                        } else {
                            Customprogress.showPopupProgressSpinner(context, false);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Customprogress.showPopupProgressSpinner(context, false);
                        Toast.makeText(context, "Status Null", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyFriendProfileResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void setMyFriendsProfileData(MyFriendProfile profileData) {
        Picasso.get().load(Allurls.IMAGEURL + profileData.getImage()).placeholder(R.drawable.default_user).error(R.drawable.default_user).into(binding.cvFriendsProfile);
        String userName = profileData.getName().substring(0, 1).toUpperCase() + profileData.getName().substring(1).toLowerCase();
        binding.tvName.setText(userName);
    }

    //************************************FOLLOW FRIEND API*********************************************
    public void FollowFriendAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        FollowFriendParaRes followFriendParaRes = new FollowFriendParaRes();
        followFriendParaRes.setUserId(friends_id.toString());

        jsonPlaceHolderApi.FollowFriendAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), followFriendParaRes).enqueue(new Callback<FollowFriendResponse>() {
            @Override
            public void onResponse(Call<FollowFriendResponse> call, Response<FollowFriendResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        binding.btnFollow.setVisibility(View.GONE);
                        binding.btnMessage.setVisibility(View.VISIBLE);
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