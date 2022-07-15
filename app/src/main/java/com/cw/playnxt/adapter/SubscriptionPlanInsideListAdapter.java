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

import java.util.List;

public class SubscriptionPlanInsideListAdapter extends RecyclerView.Adapter<SubscriptionPlanInsideListAdapter.RecyclerViewHolder> {
    Context context;
    List<AnnualPlanDataModel> list;
    ItemClick itemClick;

    public SubscriptionPlanInsideListAdapter(Context context, List<AnnualPlanDataModel> list,ItemClick itemClick) {
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
        if (position == 0){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }else if (position == 1){
            holder.binding.ivCrossFreeUser.setVisibility(View.GONE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.VISIBLE);
            holder.binding.tvFreeUser.setText("1 Only");

        }else if (position == 2){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }else  if (position == 3){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }else  if (position == 4){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }else  if (position == 5){
            holder.binding.ivCrossFreeUser.setVisibility(View.GONE);
            holder.binding.ivRightFreeUser.setVisibility(View.VISIBLE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }else if (position == 6){
            holder.binding.ivCrossFreeUser.setVisibility(View.GONE);
            holder.binding.ivRightFreeUser.setVisibility(View.VISIBLE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        } if (position == 7){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }else  if (position == 8){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }else  if (position == 9){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }else  if (position ==10){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

        }

        holder.binding.tvFeatures.setText(list.get(position).getFreatures());

     /*   holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"Setting");
            }
        });*/
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
