package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListYourStatsBinding;
import com.cw.playnxt.databinding.ItemListYourStatsNewBinding;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.StaticModel.YourStatsModel;
import com.cw.playnxt.model.StatsData.staticStatDataModel;

import java.util.ArrayList;
import java.util.List;

public class YourStatAapter extends RecyclerView.Adapter<YourStatAapter.RecyclerViewHolder> {
    Context context;
    ArrayList<staticStatDataModel> list;
    ItemClick itemClick ;

    public YourStatAapter(Context context, ArrayList<staticStatDataModel> list,  ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public YourStatAapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_your_stats_new, parent, false);
        RecyclerViewHolder recyclerViewHolder = new  RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
       /* if(position == 0){
            holder.binding.rlMain.setCardBackgroundColor(Color.parseColor("#14274D"));
            holder.binding.tvHeading1.setText(list.get(position).getH1());
            holder.binding.tvHeading2.setText(list.get(position).getH2());
            holder.binding.tvHeading3.setText(list.get(position).getH3());
            holder.binding.tvnumber1.setText(String.valueOf(list.get(position).getNumber1()));
            holder.binding.tvnum1.setText(String.valueOf(list.get(position).getNum1()));
            holder.binding.tvnumber2.setText("15");
            holder.binding.tvnum2.setText("20%");
            holder.binding.tvnumber3.setText("40");
            holder.binding.tvnum3.setText("40");
        }else if(position == 1){
            holder.binding.rlMain.setCardBackgroundColor(Color.parseColor("#441818"));
            holder.binding.llMain3.setVisibility(View.GONE);
            holder.binding.tvHeading1.setText(list.get(position).getH1());
            holder.binding.tvHeading2.setText(list.get(position).getH2());
            holder.binding.tvnumber1.setText(String.valueOf(list.get(position).getNumber1()));
            holder.binding.tvnum1.setText(String.valueOf(list.get(position).getNum1()));
            holder.binding.tvnumber2.setText("25");
            holder.binding.tvnum2.setText("20%");
        }else if(position == 2){
            holder.binding.rlMain.setCardBackgroundColor(Color.parseColor("#181829"));
            holder.binding.tvHeading1.setText(list.get(position).getH1());
            holder.binding.tvHeading2.setText(list.get(position).getH2());
            holder.binding.tvHeading3.setText(list.get(position).getH3());
            holder.binding.tvnumber1.setText(String.valueOf(list.get(position).getNumber1()));
            holder.binding.tvnum1.setText(String.valueOf(list.get(position).getNum1()));
            holder.binding.tvnumber2.setText("04");
            holder.binding.tvnum2.setText("04");
            holder.binding.tvnumber3.setText("06");
            holder.binding.tvnum3.setText("10%");
        }
        else if(position == 3){
            holder.binding.rlMain.setCardBackgroundColor(Color.parseColor("#14274D"));
            holder.binding.llMain3.setVisibility(View.GONE);
            holder.binding.tvHeading1.setText(list.get(position).getH1());
            holder.binding.tvHeading2.setText(list.get(position).getH2());
            holder.binding.tvnumber1.setText(String.valueOf(list.get(position).getNumber1()));
            holder.binding.tvnum1.setText(String.valueOf(list.get(position).getNum1()));
            holder.binding.tvnumber2.setText("06");
            holder.binding.tvnum2.setText("06");
        }*/

        holder.binding.tvHeading.setText(String.valueOf(list.get(position).getAvgrate()));
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"YourStatAdapter");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListYourStatsNewBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListYourStatsNewBinding.bind(itemView);

        }
    }
}

