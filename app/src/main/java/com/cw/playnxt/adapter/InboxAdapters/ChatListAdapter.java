package com.cw.playnxt.adapter.InboxAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListInboxLayoutBinding;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.RecyclerViewHolder> {
    Context context;
    List<GameModel> list;
    ItemClick itemClick ;

    public ChatListAdapter(Context context, List<GameModel> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ChatListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_inbox_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new  RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(list.get(position).getGame_img()).into(holder.binding.cvFriendsProfile);
        holder.binding.tvName.setText(list.get(position).getGame_name());

        holder.binding.cvFriendsProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"ChatListAdapter");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListInboxLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListInboxLayoutBinding.bind(itemView);

        }
    }
}

