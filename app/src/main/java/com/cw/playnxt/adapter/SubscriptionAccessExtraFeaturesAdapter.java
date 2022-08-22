package com.cw.playnxt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding;
import com.cw.playnxt.model.StaticModel.AccessExtraFeatures;
import java.util.List;


public class SubscriptionAccessExtraFeaturesAdapter extends RecyclerView.Adapter<SubscriptionAccessExtraFeaturesAdapter.RecyclerViewHolder> {
    Context context;
    List<AccessExtraFeatures> list;
    ItemClickId itemClick;

    public SubscriptionAccessExtraFeaturesAdapter(Context context, List<AccessExtraFeatures> list,ItemClickId itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public SubscriptionAccessExtraFeaturesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_subscription_plan_access_extra_features_layout, parent, false);
        SubscriptionAccessExtraFeaturesAdapter.RecyclerViewHolder recyclerViewHolder = new SubscriptionAccessExtraFeaturesAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAccessExtraFeaturesAdapter.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvFeatures.setText(list.get(position).getFeaturesName());
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,1l,"Item");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding.bind(itemView);

        }
    }
}
