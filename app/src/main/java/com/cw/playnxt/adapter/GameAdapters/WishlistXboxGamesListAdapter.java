package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickGameInfoWishlist;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListDiscoverLayoutBinding;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.ViewMyWishlistGame.Game;
import com.cw.playnxt.server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;


public class WishlistXboxGamesListAdapter extends RecyclerView.Adapter<WishlistXboxGamesListAdapter.RecyclerViewHolder> {
    Context context;
    List<Game> list;
    ItemClickGameInfoWishlist itemClick ;
    String typeImage = "";

    public WishlistXboxGamesListAdapter(Context context, List<Game> list,  ItemClickGameInfoWishlist itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public WishlistXboxGamesListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_discover_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new  RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        typeImage = list.get(position).getImage_type();
        Log.d("TAG","typeImage>>"+typeImage);
        if(typeImage.equals("thirdparty")){
            Picasso.get().load("https:"+list.get(position).getImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(holder.binding.gameImg);

        }else{
            Picasso.get().load(Allurls.IMAGEURL+list.get(position).getImage()).error(R.drawable.app_logo).placeholder(R.drawable.app_logo).into(holder.binding.gameImg);
        }
        String gameName = list.get(position).getTitle().substring(0, 1).toUpperCase() +list.get(position).getTitle().substring(1).toLowerCase();
        holder.binding.gameName.setText(gameName);
       // holder.binding.tvLastUpdate.setText(list.get(position).getLastUpdate());
        holder.binding.btnView.setOnClickListener(new View.OnClickListener() {
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
        private ItemListDiscoverLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListDiscoverLayoutBinding.bind(itemView);

        }
    }
}

