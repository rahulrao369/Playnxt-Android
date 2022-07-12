package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickGameInfoBacklogList;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListXboxGamesLayoutBinding;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.ViewMyBacklogGame.Game;
import com.cw.playnxt.server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class XboxGamesAdapter extends RecyclerView.Adapter<XboxGamesAdapter.RecyclerViewHolder> {
    Context context;
    List<Game> list;
    ItemClickGameInfoBacklogList itemClick ;

    public XboxGamesAdapter(Context context, List<Game> list,  ItemClickGameInfoBacklogList itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public XboxGamesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_xbox_games_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(Allurls.IMAGEURL+list.get(position).getImage()).placeholder(R.drawable.progress_animation).error(R.drawable.progress_animation).into(holder.binding.gameImg);
        String gameName = list.get(position).getTitle().substring(0, 1).toUpperCase() +list.get(position).getTitle().substring(1).toLowerCase();
        holder.binding.gameName.setText(gameName);
        holder.binding.tvGameStatus.setText(list.get(position).getStatus());
        holder.binding.tvLastUpdate.setText(list.get(position).getLastUpdate());

        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
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
        private ItemListXboxGamesLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListXboxGamesLayoutBinding.bind(itemView);

        }
    }
}



