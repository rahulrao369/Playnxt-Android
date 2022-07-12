package com.cw.playnxt.adapter.SettingAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListAnuualLayoutBinding;
import com.cw.playnxt.model.StaticModel.AnnualPlanDataModel;

import java.util.List;

public class AnnualPlanAdapter extends RecyclerView.Adapter<AnnualPlanAdapter.RecyclerViewHolder> {
    Context context;
    List<AnnualPlanDataModel> list;
    ItemClick itemClick;

    public AnnualPlanAdapter(Context context, List<AnnualPlanDataModel> list,ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_anuual_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position == 0){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
        }else if (position == 1){
            holder.binding.ivCrossFreeUser.setVisibility(View.GONE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.VISIBLE);
            holder.binding.tvFreeUser.setText("1 Only");

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.GONE);
            holder.binding.tvPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setText("Unlimited");
        }else if (position == 2){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.GONE);
            holder.binding.tvPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setText("Unlimited");
        }else  if (position == 3){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
        }else  if (position == 4){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
        }else  if (position == 5){
            holder.binding.ivCrossFreeUser.setVisibility(View.GONE);
            holder.binding.ivRightFreeUser.setVisibility(View.VISIBLE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
        }else if (position == 6){
            holder.binding.ivCrossFreeUser.setVisibility(View.GONE);
            holder.binding.ivRightFreeUser.setVisibility(View.VISIBLE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
        } if (position == 7){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
        }else  if (position == 8){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
        }else  if (position == 9){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
        }else  if (position ==10){
            holder.binding.ivCrossFreeUser.setVisibility(View.VISIBLE);
            holder.binding.ivRightFreeUser.setVisibility(View.GONE);
            holder.binding.tvFreeUser.setVisibility(View.GONE);

            holder.binding.ivCrossPremiumUser.setVisibility(View.GONE);
            holder.binding.ivRightPremiumUser.setVisibility(View.VISIBLE);
            holder.binding.tvPremiumUser.setVisibility(View.GONE);
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
        private ItemListAnuualLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListAnuualLayoutBinding.bind(itemView);

        }
    }
}
