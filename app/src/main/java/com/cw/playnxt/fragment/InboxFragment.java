package com.cw.playnxt.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.ChatActivity;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.activity.MyProfileActivity;
import com.cw.playnxt.adapter.InboxAdapters.ChatListAdapter;
import com.cw.playnxt.databinding.FragmentInboxBinding;
import com.cw.playnxt.model.ChatList.ChatListResponse;
import com.cw.playnxt.model.ChatList.Inbox;
import com.cw.playnxt.model.GetMyProfile.GetMyProfileResponse;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InboxFragment extends Fragment implements View.OnClickListener{
    Context context;
    private FragmentInboxBinding binding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_inbox, true);
        binding = FragmentInboxBinding.inflate(inflater, container, false);
        init();
        onclicks();
        initializeRefreshListener();
        return binding.getRoot();
    }
    void initializeRefreshListener() {
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Constants.isInternetConnected(context)) {
                    ChatListAPI();
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
    @Override
    public void onStart() {
        super.onStart();
        if (Constants.isInternetConnected(context)) {
            GetMyProfileAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }
    public void init() {
        context = binding.getRoot().getContext();
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        if (Constants.isInternetConnected(context)) {
            ChatListAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }
    private void InboxListDataSet(List<Inbox> inboxList) {
        ChatListAdapter adapter = new ChatListAdapter(context, inboxList, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                if(type.equals("ChatListAdapter")){
                    openDialogBigProfile(inboxList.get(position).getImage());
                }else{
                    startActivity(new Intent(context, ChatActivity.class)
                            .putExtra("receiverId", inboxList.get(position).getId().toString())
                            .putExtra("receiverName", inboxList.get(position).getName())
                            .putExtra("receiverImage", inboxList.get(position).getImage().toString()));
                    Log.d("TAG", "inboxList.get(position).getId()>>"+inboxList.get(position).getId());
                }

            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
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

    public void openDialogBigProfile(String image) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_big_profile_image);
        AppCompatImageView iv_profile_image = (AppCompatImageView) dialog.findViewById(R.id.iv_profile_image);
        MaterialCardView cv_cross = (MaterialCardView) dialog.findViewById(R.id.cv_cross);

        Picasso.get().load(Allurls.IMAGEURL + image).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(iv_profile_image);
        cv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void ChatListAPI() {
        stopSwipeRefesh();
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.ChatListAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        if (response.body().getData().getInbox().size() == 0) {
                            binding.llRecyclerview.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                        } else {
                            binding.llRecyclerview.setVisibility(View.VISIBLE);
                            binding.llNoData.setVisibility(View.GONE);
                            InboxListDataSet(response.body().getData().getInbox());
                        }
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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