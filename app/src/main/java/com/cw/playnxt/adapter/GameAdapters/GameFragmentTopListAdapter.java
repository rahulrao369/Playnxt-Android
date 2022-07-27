package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListGameFragmentBinding;
import com.cw.playnxt.model.StaticModel.GameFragmentModel;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class GameFragmentTopListAdapter extends RecyclerView.Adapter<GameFragmentTopListAdapter.RecyclerViewHolder> {
    Context context;
    List<GameFragmentModel> list;
    ItemClick itemClick;

    public GameFragmentTopListAdapter(Context context, List<GameFragmentModel> list,  ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public GameFragmentTopListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_game_fragment, parent, false);
     RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(position == 0){
            holder.binding.ivSubscribeNow.setVisibility(View.GONE);
        }else{
            if(list.get(position).getPlan_type().equals(Constants.PLAN_TYPE_PAID)){
                holder.binding.ivSubscribeNow.setVisibility(View.GONE);
            }else{
                holder.binding.ivSubscribeNow.setVisibility(View.VISIBLE);
            }
        }
        holder.binding.icon.setImageResource(list.get(position).getIcon());
        holder.binding.tvName.setText(list.get(position).getHeading());

        if(list.get(position).getNumber().equals("")){
            holder.binding.tvNumber.setText("");
        }else if(list.get(position).getNumber().equals("0")){
            holder.binding.tvNumber.setText("");
        }else{
            holder.binding.tvNumber.setText("("+list.get(position).getNumber()+")"+"");
        }
        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"GameFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListGameFragmentBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListGameFragmentBinding.bind(itemView);

        }
    }
}


