package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickView;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListBacklogXboxGamesBinding;
import com.cw.playnxt.model.GetBacklogList.Count;
import com.cw.playnxt.model.StaticModel.GameModel;

import java.util.List;


public class BacklogXboxGamesAdapter extends RecyclerView.Adapter<BacklogXboxGamesAdapter.RecyclerViewHolder> {
    Context context;
    List<Count> list;
    ItemClick itemClick ;
    ItemClickView itemClickView ;

    public BacklogXboxGamesAdapter(Context context, List<Count>  list,  ItemClick itemClick, ItemClickView itemClickView) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
        this.itemClickView = itemClickView;
    }

    @NonNull
    @Override
    public BacklogXboxGamesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_backlog_xbox_games, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvListName.setText(list.get(position).getListName());
        holder.binding.tvTotalGame.setText(list.get(position).getTotalGame()+" Games");

        holder.binding.llXboxGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   if(list.get(position).getTotalGame() > 0){

                }else{
                    Toast.makeText(context, "No game is added", Toast.LENGTH_SHORT).show();
                }*/
                itemClick.onItemClick(position,"XboxGames");
                notifyDataSetChanged();

            }
        });
        holder.binding.llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickView.onItemClick(list.get(position).getId(),view,list.get(position).getListName());
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListBacklogXboxGamesBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListBacklogXboxGamesBinding.bind(itemView);

        }
    }
}
