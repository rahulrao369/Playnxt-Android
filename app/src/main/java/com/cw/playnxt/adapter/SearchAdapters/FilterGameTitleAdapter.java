package com.cw.playnxt.adapter.SearchAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListSearchGamesBinding;
import com.cw.playnxt.model.GetGameByFilter.Newdatum;
import com.cw.playnxt.server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FilterGameTitleAdapter extends RecyclerView.Adapter<FilterGameTitleAdapter.RecyclerViewHolder> {
    Context context;
    List<Newdatum> list;
    ItemClick itemClick ;


    public FilterGameTitleAdapter(Context context, List<Newdatum> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public FilterGameTitleAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_search_games, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(Allurls.IMAGEURL+list.get(position).getImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(holder.binding.ivImage);
        holder.binding.tvName.setText(list.get(position).getTitle());

        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"GameTitleSearch");
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
