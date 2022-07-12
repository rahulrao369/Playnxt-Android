package com.cw.playnxt.adapter.GameAdapters;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListBottomSheetCurrentStatusBinding;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.utils.Constants;

import java.util.List;


public class BottomSheetCurrentStatusListAdapter extends RecyclerView.Adapter<BottomSheetCurrentStatusListAdapter.RecyclerViewHolder> {
    Context context;
    List<GameModel> list;
    ItemClick itemClick ;
    int selectPosition = 0;
    String selected_game_status;

    public BottomSheetCurrentStatusListAdapter(Context context, List<GameModel> list,String selected_game_status,  ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
        this.selected_game_status = selected_game_status;
    }

    @NonNull
    @Override
    public BottomSheetCurrentStatusListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_bottom_sheet_current_status, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("TAG","selected_game_status>>>"+selected_game_status);
        if (selected_game_status.equals(list.get(position).getGame_name())){
            holder.binding.llRightImg.setVisibility(View.VISIBLE);
        }else{
            holder.binding.llRightImg.setVisibility(View.GONE);
        }

        holder.binding.tvStatus.setText(list.get(position).getGame_name());

        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                selected_game_status = list.get(position).getGame_name();
                itemClick.onItemClick(position,selected_game_status);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListBottomSheetCurrentStatusBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListBottomSheetCurrentStatusBinding.bind(itemView);

        }
    }
}



