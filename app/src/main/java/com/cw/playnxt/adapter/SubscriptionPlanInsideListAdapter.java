package com.cw.playnxt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListSubscriptionPlanInsideListLayoutBinding;
import com.cw.playnxt.model.StaticModel.AnnualPlanDataModel;
import com.cw.playnxt.model.SubscriptionPlan.StatisSubscriptionPlansListWithSymbol;

import java.util.List;

public class SubscriptionPlanInsideListAdapter extends RecyclerView.Adapter<SubscriptionPlanInsideListAdapter.RecyclerViewHolder> {
    Context context;
    List<StatisSubscriptionPlansListWithSymbol> list;
    ItemClick itemClick;

    public SubscriptionPlanInsideListAdapter(Context context, List<StatisSubscriptionPlansListWithSymbol> list,ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public SubscriptionPlanInsideListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_subscription_plan_inside_list_layout, parent, false);
        SubscriptionPlanInsideListAdapter.RecyclerViewHolder recyclerViewHolder = new SubscriptionPlanInsideListAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionPlanInsideListAdapter.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(list.get(position).getSymbol() == 0){
            holder.binding.ivRight.setVisibility(View.GONE);
            holder.binding.ivCross.setVisibility(View.VISIBLE);
            holder.binding.ivinfinity.setVisibility(View.GONE);
        }else if(list.get(position).getSymbol() == 1){
            holder.binding.ivRight.setVisibility(View.VISIBLE);
            holder.binding.ivCross.setVisibility(View.GONE);
            holder.binding.ivinfinity.setVisibility(View.GONE);
        }else if(list.get(position).getSymbol() == 2){
            holder.binding.ivRight.setVisibility(View.GONE);
            holder.binding.ivCross.setVisibility(View.GONE);
            holder.binding.ivinfinity.setVisibility(View.VISIBLE);
        }
        holder.binding.tvFeatures.setText(list.get(position).getListName());
        holder.binding.llMainItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"Item");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListSubscriptionPlanInsideListLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListSubscriptionPlanInsideListLayoutBinding.bind(itemView);

        }
    }
}
