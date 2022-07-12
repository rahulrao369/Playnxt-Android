package com.cw.playnxt.adapter.HomeAdapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListFriendsLayoutBinding;
import com.cw.playnxt.model.GetMyFriendList.GetMyFriendListDataCapsul;
import com.cw.playnxt.model.HomeData.Following;
import com.cw.playnxt.server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.RecyclerViewHolder> {
    Context context;
    List<Following> list;
    ItemClick itemClick ;


    public FriendsListAdapter(Context context, List<Following> list,  ItemClick itemClick ) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_friends_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(Allurls.IMAGEURL+list.get(position).getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(holder.binding.cvProfileImg);
        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"FriendsList");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListFriendsLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListFriendsLayoutBinding.bind(itemView);

        }
    }
}


