package com.cw.playnxt.adapter.FriendsAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClickFavUnFavCommunity;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListFriendsFriendsFragmentLayoutBinding;
import com.cw.playnxt.model.CommunityData.CapsulCommunityData;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TabCommunityAdapter extends RecyclerView.Adapter<TabCommunityAdapter.RecyclerViewHolder> {
    Context context;
    List<CapsulCommunityData> list;
    ItemClickFavUnFavCommunity itemClick;
    String like_status = "";
    Long total_like_count;

    public TabCommunityAdapter(Context context, List<CapsulCommunityData> list, ItemClickFavUnFavCommunity itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public TabCommunityAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_friends_friends_fragment_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(Allurls.IMAGEURL + list.get(position).getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(holder.binding.cvFriendsProfile);
        String userName = list.get(position).getName().substring(0, 1).toUpperCase() + list.get(position).getName().substring(1).toLowerCase();
        holder.binding.tvName.setText(userName);
        holder.binding.tvTotalLike.setText(list.get(position).getTotalLike().toString());
        Log.d("TAG", "like>>" + list.get(position).getLike());

        if (list.get(position).getLike() == 1) {
            holder.binding.ivFav.setVisibility(View.VISIBLE);
            holder.binding.ivUnfav.setVisibility(View.GONE);
        } else {
            holder.binding.ivFav.setVisibility(View.GONE);
            holder.binding.ivUnfav.setVisibility(View.VISIBLE);
        }

        if (list.get(position).getTitle().equals("")) {
            holder.binding.tvaddtobacklog.setVisibility(View.GONE);
        } else {
            holder.binding.tvaddtobacklog.setVisibility(View.VISIBLE);
            holder.binding.tvaddtobacklog.setText("Added " + list.get(position).getTitle() + " to " + list.get(position).getListName());
        }
        if (list.get(position).getAddedTime().equals("")) {
            holder.binding.llTimeStamp.setVisibility(View.GONE);
        } else {
            holder.binding.llTimeStamp.setVisibility(View.VISIBLE);
            holder.binding.tvTimeStamps.setText(list.get(position).getAddedTime());
        }

        holder.binding.llLikeUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getLike() == 1) {
                    like_status = Constants.UNLIKE_STATUS;
                    itemClick.onItemClick("UnFav", list.get(position), like_status);

                    list.get(position).setLike(0);
                    total_like_count = list.get(position).getTotalLike();
                    total_like_count = total_like_count - 1;
                    list.get(position).setTotalLike(total_like_count);
                    holder.binding.ivFav.setVisibility(View.GONE);
                    holder.binding.ivUnfav.setVisibility(View.VISIBLE);
                    holder.binding.tvTotalLike.setText(String.valueOf(total_like_count));
                    //notifyDataSetChanged();
                } else if (list.get(position).getLike() == 0) {
                    like_status = Constants.LIKE_STATUS;
                    itemClick.onItemClick("Fav", list.get(position), like_status);

                    list.get(position).setLike(1);
                    total_like_count = list.get(position).getTotalLike();
                    total_like_count = total_like_count + 1;
                    list.get(position).setTotalLike(total_like_count);
                    holder.binding.ivFav.setVisibility(View.VISIBLE);
                    holder.binding.ivUnfav.setVisibility(View.GONE);
                    holder.binding.tvTotalLike.setText(String.valueOf(total_like_count));
                    // notifyDataSetChanged();
                }
            }
        });
        holder.binding.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick("follow", list.get(position), like_status);
            }
        });
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick("CommunityFragmentMain", list.get(position), like_status);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListFriendsFriendsFragmentLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListFriendsFriendsFragmentLayoutBinding.bind(itemView);

        }
    }
}


