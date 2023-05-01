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
import com.cw.playnxt.databinding.ItemListSearchBacklogBinding;
import com.cw.playnxt.databinding.ItemListSearchUsersBinding;
import com.cw.playnxt.model.HomeSearch.BacklogSearch.SearchBacklogDataResult;
import com.cw.playnxt.model.HomeSearch.UserSearch.SearchUserDataResult;
import com.cw.playnxt.server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchBacklogAdapter extends RecyclerView.Adapter<SearchBacklogAdapter.RecyclerViewHolder> {
    Context context;
    List<SearchBacklogDataResult> list;
    ItemClick itemClick ;

    public SearchBacklogAdapter(Context context, List<SearchBacklogDataResult> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public SearchBacklogAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_search_backlog, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvName.setText(list.get(position).getListName());

        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"UserSearch");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListSearchBacklogBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListSearchBacklogBinding.bind(itemView);

        }
    }
}
