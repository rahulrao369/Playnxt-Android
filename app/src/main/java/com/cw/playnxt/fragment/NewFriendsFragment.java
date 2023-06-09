package com.cw.playnxt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cw.playnxt.R;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.activity.MyProfileActivity;
import com.cw.playnxt.adapter.ViewPagerAdapter;
import com.cw.playnxt.databinding.FragmentNewFriendsBinding;
import com.cw.playnxt.model.CheckSubscriptionFinal.CheckSubscriptionFinalResponse;
import com.cw.playnxt.model.GetMyProfile.GetMyProfileResponse;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFriendsFragment extends Fragment implements View.OnClickListener {
    FragmentNewFriendsBinding binding;
    Context context;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewFriendsBinding.inflate(inflater, container, false);
     //  ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_home,false);
       ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_friends,true);
        System.out.println("check tab test>>>");
        InitView();
        GetData();
        onclicks();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Constants.isInternetConnected(context)) {
            GetMyProfileAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitView() {
        context = binding.getRoot().getContext();
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        if (Constants.isInternetConnected(context)) {
            NewCheckSubscriptionAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void GetData() {
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                int tab_selected = bundle.getInt("key", 0);
                System.out.println("tab_selected >>>>>>>>>>" + tab_selected);

                binding.tablayout1.addTab(binding.tablayout1.newTab().setText("Community"));
                binding.tablayout1.addTab(binding.tablayout1.newTab().setText("Friends"));
                binding.tablayout1.setTabGravity(TabLayout.GRAVITY_FILL);
                ViewPagerAdapter adapter = new ViewPagerAdapter(((AppCompatActivity) context).getSupportFragmentManager());
                binding.viewpager1.setAdapter(adapter);


                binding.tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        System.out.println("onTabSelected>>>>>>>>>>>>>> " + tab.getPosition());
                        binding.viewpager1.setCurrentItem(tab.getPosition());
                        int currentPosition = binding.viewpager1.getCurrentItem();
                        System.out.println("onCurrentPosition 1>>>>>>>>>>>>>> " + currentPosition);
                        adapter.notifyDataSetChanged();
                        binding.viewpager1.setAdapter(null);
                        binding.viewpager1.setAdapter(adapter);
                        System.out.println("onCurrentPosition 2>>>>>>>>>>>>>> " + currentPosition);
                        binding.viewpager1.setCurrentItem(currentPosition);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
//                binding.tablayout1.getTabAt(tab_selected);
                Log.d("TAG","tab_selected****"+tab_selected);
                binding.viewpager1.setCurrentItem(tab_selected);

                binding.viewpager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout1));

            }else{
                ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_friends,true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onclicks() {
        binding.llMyProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMyProfile:
                startActivity(new Intent(context, MyProfileActivity.class).putExtra("key", "1")
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
    //*********************************************************CHECK SUBSCRIPTION****************************************************
    public void NewCheckSubscriptionAPI() {
        jsonPlaceHolderApi.NewCheckSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckSubscriptionFinalResponse>() {
            @Override
            public void onResponse(Call<CheckSubscriptionFinalResponse> call, Response<CheckSubscriptionFinalResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        if (response.body().getData().getSubscribed().equals(Constants.YES)) {
                            binding.btnAdsShow.setVisibility(View.GONE);
                        }else{
                            binding.btnAdsShow.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckSubscriptionFinalResponse> call, Throwable t) {
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}