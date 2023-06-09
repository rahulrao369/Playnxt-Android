package com.cw.playnxt.adapter.SearchAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListSearchGamesBinding;
import com.cw.playnxt.model.HomeSearch.GameSearch.SearchGameDataResult;
import com.cw.playnxt.server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchGamesAdapter extends RecyclerView.Adapter<SearchGamesAdapter.RecyclerViewHolder> {
    Context context;
    List<SearchGameDataResult> list;
    ItemClick itemClick ;
    String typeImage = "";

    public SearchGamesAdapter(Context context, List<SearchGameDataResult> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public SearchGamesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_search_games, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        typeImage = list.get(position).getImage_type();
        Log.d("TAG","typeImage>>"+typeImage);
        if(typeImage.equals("thirdparty")){
            Picasso.get().load("https:"+list.get(position).getImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(holder.binding.ivImage);

        }else{
            Picasso.get().load(Allurls.IMAGEURL+list.get(position).getImage()).error(R.drawable.app_logo).placeholder(R.drawable.app_logo).into(holder.binding.ivImage);
        }

        holder.binding.tvName.setText(list.get(position).getTitle());
        holder.binding.tvPlatformType.setVisibility(View.VISIBLE);
        holder.binding.tvPlatformType.setText("Platform Type : "+list.get(position).getPlatform());

        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"GameSearch");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListSearchGamesBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListSearchGamesBinding.bind(itemView);

        }
    }
}
