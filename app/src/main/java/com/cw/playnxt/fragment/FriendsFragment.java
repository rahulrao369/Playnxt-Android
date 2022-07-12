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
import com.cw.playnxt.R;
import com.cw.playnxt.activity.FriendsProfileActivity;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.activity.HomeSearchActivity;
import com.cw.playnxt.activity.MyProfileActivity;
import com.cw.playnxt.adapter.DiscoverAdapters.GameListAdapter;
import com.cw.playnxt.adapter.FriendsAdapters.FriendsListFriendsFragmentAdapter;
import com.cw.playnxt.adapter.FriendsAdapters.MainTabLayoutAdapterCommunityFriends;
import com.cw.playnxt.adapter.FriendsAdapters.TabLayoutAdapter;
import com.cw.playnxt.databinding.FragmentFriendsBinding;
import com.cw.playnxt.databinding.FragmentGameBinding;
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

public class FriendsFragment extends Fragment implements View.OnClickListener{
    Context context;
    private FragmentFriendsBinding binding;
    MainTabLayoutAdapterCommunityFriends tabLayoutAdapter1;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String tab= "0";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_friends,true);
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        init();
        onclicks();
        return binding.getRoot();
    }
    public void init() {
        context = binding.getRoot().getContext();
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);

        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                tab = bundle.getString("key");
                Log.d("TAG", "tab>>" + tab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       setSelectedTab();

        if (Constants.isInternetConnected(context)) {
            GetMyProfileAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void setSelectedTab() {
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

        if (tab.equals("1")) {
            binding.tablayout1.getTabAt(1).select();
        }else {
            binding.tablayout1.getTabAt(0).select();
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
                        .putExtra("key","1")
                );
                break;
        }
    }
    public void GetMyProfileAPI() {
       // Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.GetMyProfileAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyProfileResponse>() {
            @Override
            public void onResponse(Call<GetMyProfileResponse> call, Response<GetMyProfileResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus() != null){
                        Boolean status = response.body().getStatus();
                     //   Customprogress.showPopupProgressSpinner(context, false);
                        if (status)
                        {
                            if(response.body().getData() != null){
                                Picasso.get().load(Allurls.IMAGEURL+response.body().getData().getProfile().getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(binding.cvMyProfile);
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
            public void onFailure(Call<GetMyProfileResponse> call, Throwable t) {
              //  Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
}