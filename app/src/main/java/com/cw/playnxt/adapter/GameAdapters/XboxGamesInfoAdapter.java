package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickGameNote;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListXboxGameInfoBinding;
import com.cw.playnxt.model.GetGameNote.Capsul;
import com.cw.playnxt.model.StaticModel.GameModel;

import java.util.List;

public class XboxGamesInfoAdapter extends RecyclerView.Adapter<XboxGamesInfoAdapter.RecyclerViewHolder> {
    Context context;
    List<Capsul> list;
    ItemClickGameNote itemClick ;

    public XboxGamesInfoAdapter(Context context, List<Capsul> list,  ItemClickGameNote itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public XboxGamesInfoAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_xbox_game_info, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvCreatedOn.setText(list.get(position).getCreateOn());
        holder.binding.tvGameNote.setText(list.get(position).getNote());

        holder.binding.ivEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,list.get(position).getGameId(),list.get(position).getNoteId(),"Edit");
            }
        });
        holder.binding.ivDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,list.get(position).getGameId(),list.get(position).getNoteId(),"Delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListXboxGameInfoBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListXboxGameInfoBinding.bind(itemView);

        }
    }
}



