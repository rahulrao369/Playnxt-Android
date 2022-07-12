package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClickGameInfoRecentList;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListRecentGamesBinding;
import com.cw.playnxt.model.GetRecentGame.GetRecentGameDataCapsul;
import com.cw.playnxt.model.StaticModel.FriendsModel;
import com.cw.playnxt.server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentGamesAdapter extends RecyclerView.Adapter<RecentGamesAdapter.RecyclerViewHolder> {
    Context context;
    List<GetRecentGameDataCapsul> list;
    ItemClickGameInfoRecentList itemClick;
    public RecentGamesAdapter(Context context, List<GetRecentGameDataCapsul> list, ItemClickGameInfoRecentList itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecentGamesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_recent_games, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(Allurls.IMAGEURL+list.get(position).getGameImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(holder.binding.gameImg);
        String gameName = list.get(position).getTitle().substring(0, 1).toUpperCase() + list.get(position).getTitle().substring(1).toLowerCase();
        holder.binding.gameName.setText(gameName);
        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListRecentGamesBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListRecentGamesBinding.bind(itemView);
        }
    }
}
