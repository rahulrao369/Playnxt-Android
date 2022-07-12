package com.cw.playnxt.adapter.FriendsAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListFriendsFriendsFragmentLayoutBinding;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendsListFriendsFragmentAdapter extends RecyclerView.Adapter<FriendsListFriendsFragmentAdapter.RecyclerViewHolder> {
    Context context;
    List<GameModel> list;
    ItemClick itemClick;

    public FriendsListFriendsFragmentAdapter(Context context, List<GameModel> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public FriendsListFriendsFragmentAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_friends_friends_fragment_layout, parent, false);
       RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(list.get(position).isChecked()){
            holder.binding.ivFav.setVisibility(View.VISIBLE);
            holder.binding.ivUnfav.setVisibility(View.GONE);

        }else{
            holder.binding.ivFav.setVisibility(View.GONE);
            holder.binding.ivUnfav.setVisibility(View.VISIBLE);
        }
        Picasso.get().load(list.get(position).getGame_img()).into(holder.binding.cvFriendsProfile);
        holder.binding.tvName.setText(list.get(position).getGame_name());

        holder.binding.llLikeUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).isChecked()){
                    list.get(position).setChecked(false);
                    notifyDataSetChanged();
                }else{
                    list.get(position).setChecked(true);
                    notifyDataSetChanged();
                }

                //itemClick.onItemClick(position,"ActivityFragment");
            }
        });
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"ActivityFragment");
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



