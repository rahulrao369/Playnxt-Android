package com.cw.playnxt.adapter.MyProfileAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListFriendsProfileFollowersFollowingLayoutBinding;
import com.cw.playnxt.model.GetMyProfile.Following;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;



public class MyProfileTabFollowingAdapter extends RecyclerView.Adapter<MyProfileTabFollowingAdapter.RecyclerViewHolder> {
    Context context;
    List<Following> list;
    ItemClickId itemClick;

    public MyProfileTabFollowingAdapter(Context context, List<Following> list, ItemClickId itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MyProfileTabFollowingAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_friends_profile_followers_following_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.btnFollow.setVisibility(View.GONE);
        holder.binding.btnUnfollow.setVisibility(View.VISIBLE);
        holder.binding.btnUnfollow.getBackground().setTint(context.getResources().getColor(R.color.app_theme));
        holder.binding.tvBtn.setText("Following");
        Picasso.get().load(Allurls.IMAGEURL+list.get(position).getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(holder.binding.cvFriendsProfile);
        String userName = list.get(position).getName().substring(0, 1).toUpperCase() +list.get(position).getName().substring(1).toLowerCase();
        holder.binding.tvName.setText(userName);
        if(list.get(position).getMutual_friend() == 0){
            holder.binding.tvMutualFriend.setText("No Mutual Friends");
        }else{
            holder.binding.tvMutualFriend.setText(list.get(position).getMutual_friend()+" "+"of Mutual Friends ");
        }
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position, Long.valueOf(list.get(position).getUserId()),"FollowingAdapter");
            }
        });
        holder.binding.btnUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position, Long.valueOf(list.get(position).getUserId()),"unfollow");
            }
        });
        holder.binding.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position, Long.valueOf(list.get(position).getUserId()),"message");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListFriendsProfileFollowersFollowingLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListFriendsProfileFollowersFollowingLayoutBinding.bind(itemView);

        }
    }
}

