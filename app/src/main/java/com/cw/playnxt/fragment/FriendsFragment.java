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

import com.cw.playnxt.R;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.activity.MyProfileActivity;
import com.cw.playnxt.adapter.FriendsAdapters.MainTabLayoutAdapterCommunityFriends;
import com.cw.playnxt.databinding.FragmentFriendsBinding;
import com.cw.playnxt.model.GetMyProfile.GetMyProfileResponse;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment implements View.OnClickListener {
    Context context;
    MainTabLayoutAdapterCommunityFriends tabLayoutAdapter1;
    String tab_selected = "0";
    private FragmentFriendsBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_friends, true);
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        init();
        onclicks();
        return binding.getRoot();
    }

    public void init() {
        context = binding.getRoot().getContext();
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);

   /*     try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                tab_selected = bundle.getString("key");
                Log.d("TAG", "tab>>" + tab_selected);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        tabLayoutAdapter1 = new MainTabLayoutAdapterCommunityFriends(getActivity().getSupportFragmentManager(), binding.tablayout1.getTabCount());
        binding.viewpager1.setAdapter(tabLayoutAdapter1);
        binding.tablayout1.addTab(binding.tablayout1.newTab().setText("Community"));
        binding.tablayout1.addTab(binding.tablayout1.newTab().setText("Friends"));

        binding.tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager1.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.viewpager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout1));
        binding.viewpager1.post(new Runnable() {
            @Override
            public void run() {
                binding.viewpager1.setCurrentItem(1);
            }
        });


        if (Constants.isInternetConnected(context)) {
            GetMyProfileAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void onclicks() {
        binding.llMyProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMyProfile:
                startActivity(new Intent(context, MyProfileActivity.class)
                        .putExtra("key", "1")
                );
                break;
        }
    }

    public void GetMyProfileAPI() {
        // Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.GetMyProfileAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyProfileResponse>() {
            @Override
            public void onResponse(Call<GetMyProfileResponse> call, Response<GetMyProfileResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() != null) {
                        Boolean status = response.body().getStatus();
                        //   Customprogress.showPopupProgressSpinner(context, false);
                        if (status) {
                            if (response.body().getData() != null) {
                                Picasso.get().load(Allurls.IMAGEURL + response.body().getData().getProfile().getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(binding.cvMyProfile);
                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Status Null", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyProfileResponse> call, Throwable t) {
                //  Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
}

/*
  if (tab.equals("1")) {
          binding.viewpager1.post(new Runnable() {
@Override
public void run() {
        binding.viewpager1.setCurrentItem(1);
        }
        });
        }else {
        binding.viewpager1.post(new Runnable() {
@Override
public void run() {
        binding.viewpager1.setCurrentItem(0);
        }
        });
        }*/
