package com.cw.playnxt.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.HomeActivity;
import com.cw.playnxt.adapter.DiscoverAdapters.GameListAdapter;
import com.cw.playnxt.adapter.InboxAdapters.ChatListAdapter;
import com.cw.playnxt.databinding.FragmentFriendsBinding;
import com.cw.playnxt.databinding.FragmentInboxBinding;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.Allurls;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class InboxFragment extends Fragment {
    Context context;
    private FragmentInboxBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).chipNavigationBar.setItemSelected(R.id.menu_inbox,true);
        binding = FragmentInboxBinding.inflate(inflater, container, false);
        init();
        onclicks();
        return binding.getRoot();
    }

    public void init() {
        context = binding.getRoot().getContext();
        InboxListDataSet();
    }

    private void InboxListDataSet() {
        List<GameModel> list = new ArrayList<>();
        list.add(new GameModel(R.drawable.m1,"Smith Mathew"));
        list.add(new GameModel(R.drawable.m2,"Merry An."));
        list.add(new GameModel(R.drawable.m3,"John Walton"));
        list.add(new GameModel(R.drawable.m1,"Smith Mathew"));
        list.add(new GameModel(R.drawable.m2,"Merry An."));
        list.add(new GameModel(R.drawable.m3,"John Walton"));

        ChatListAdapter adapter = new ChatListAdapter(context, list, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                openDialogBigProfile();
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    public void onclicks() {
        // binding.tvSeeAll.setOnClickListener(this);
    }

    public void openDialogBigProfile() {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_big_profile_image);
        AppCompatImageView iv_profile_image = (AppCompatImageView) dialog.findViewById(R.id.iv_profile_image);
        MaterialCardView cv_cross = (MaterialCardView) dialog.findViewById(R.id.cv_cross);

        cv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}