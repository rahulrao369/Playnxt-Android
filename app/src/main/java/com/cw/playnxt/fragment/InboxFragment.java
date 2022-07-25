package com.cw.playnxt.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.adapter.InboxAdapters.ChatListAdapter;
import com.cw.playnxt.databinding.FragmentInboxBinding;
import com.cw.playnxt.model.ChatList.ChatListResponse;
import com.cw.playnxt.model.ChatList.Inbox;
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


public class InboxFragment extends Fragment {
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
        return binding.getRoot();
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
                openDialogBigProfile(inboxList.get(position).getImage());
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    public void onclicks() {
        // binding.tvSeeAll.setOnClickListener(this);
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
}