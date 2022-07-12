package com.cw.playnxt.adapter.SearchAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListGetCategoryNameLayoutBinding;
import com.cw.playnxt.model.GetCategoryListName.Backlog;

import java.util.List;

public class GetCategoryNameListAdapter extends RecyclerView.Adapter<GetCategoryNameListAdapter.RecyclerViewHolder> {
    Context context;
    List<Backlog> list;
    ItemClickId itemClick ;
    int selectedPosition = -1;


    public GetCategoryNameListAdapter(Context context, List<Backlog> list, ItemClickId itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public GetCategoryNameListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_get_category_name_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvName.setText(list.get(position).getName());
        if(position == selectedPosition){
            holder.binding.cb.setChecked(true);
        }else{
            holder.binding.cb.setChecked(false);
        }
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                // Call listener
                itemClick.onItemClick(position, list.get(position).getId(),"CategoryNameList");
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListGetCategoryNameLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListGetCategoryNameLayoutBinding.bind(itemView);

        }
    }
}
