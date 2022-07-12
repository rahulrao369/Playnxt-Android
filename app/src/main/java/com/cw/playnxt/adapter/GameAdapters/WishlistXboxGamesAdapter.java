package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickView;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListBacklogXboxGamesBinding;
import com.cw.playnxt.model.GetWishlist.Count;
import com.cw.playnxt.model.StaticModel.GameModel;

import java.util.List;

public class WishlistXboxGamesAdapter extends RecyclerView.Adapter<WishlistXboxGamesAdapter.RecyclerViewHolder> {
    Context context;
    List<Count> list;
    ItemClick itemClick ;
    ItemClickView itemClickView;
    public WishlistXboxGamesAdapter(Context context, List<Count> list, ItemClick itemClick, ItemClickView itemClickView) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
        this.itemClickView = itemClickView;
    }

    @NonNull
    @Override
    public WishlistXboxGamesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_backlog_xbox_games, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvListName.setText(list.get(position).getListName());
        holder.binding.tvTotalGame.setText(list.get(position).getTotalGame()+" Games added");

        holder.binding.llXboxGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"WishlistAdapter");
                notifyDataSetChanged();
            }
        });
        holder.binding.llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickView.onItemClick(list.get(position).getId(),view,list.get(position).getListName());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListBacklogXboxGamesBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListBacklogXboxGamesBinding.bind(itemView);

        }
    }
}