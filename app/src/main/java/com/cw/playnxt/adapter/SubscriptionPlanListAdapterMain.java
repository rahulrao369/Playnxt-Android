package com.cw.playnxt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickGameInfoRecentList;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SettingAdapters.MonthlyPlanAdapter;
import com.cw.playnxt.databinding.ItemListSubscriptionPlanListMainLayoutBinding;
import com.cw.playnxt.model.GetRecentGame.GetRecentGameDataCapsul;
import com.cw.playnxt.model.StaticModel.AnnualPlanDataModel;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.SubscriptionPlan.Plan;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionPlanListAdapterMain extends RecyclerView.Adapter<SubscriptionPlanListAdapterMain.RecyclerViewHolder> {
    Context context;
    List<Plan> list;
    public SubscriptionPlanListAdapterMain(Context context, List<Plan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubscriptionPlanListAdapterMain.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_subscription_plan_list_main_layout, parent, false);
        SubscriptionPlanListAdapterMain.RecyclerViewHolder recyclerViewHolder = new SubscriptionPlanListAdapterMain.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionPlanListAdapterMain.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvPlanTitle.setText(list.get(position).getTitle());
        if(list.get(position).getTitle().equals("Free")){
            holder.binding.ivPlan.setImageResource(R.drawable.ic_free);
        }else if(list.get(position).getTitle().equals("Silver Plan")){
            holder.binding.ivPlan.setImageResource(R.drawable.silver_medal);
        }else if(list.get(position).getTitle().equals("Premium Plan")){
            holder.binding.ivPlan.setImageResource(R.drawable.diamond);
        }
        List<AnnualPlanDataModel> list = new ArrayList<>();
        list.add(new AnnualPlanDataModel("Ad-Free Experience", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("Manage Backlogs", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("Manage Wishlists", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("Calendar Tool ", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("Personal Stats", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("View Community Activity", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("Follow Friends", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("View Friend Backlog and Wishlists ", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("Message Friends", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("Barcode Scanning Tool (coming soon) ", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));
        list.add(new AnnualPlanDataModel("Early Access to New Features", R.drawable.ic_arrow_right, R.drawable.ic_arrow_left));

        SubscriptionPlanInsideListAdapter adapter = new SubscriptionPlanInsideListAdapter(context, list, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {

            }
        });
        holder.binding.recyclerView.setHasFixedSize(true);
        holder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.binding.recyclerView.setAdapter(adapter);

        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListSubscriptionPlanListMainLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListSubscriptionPlanListMainLayoutBinding.bind(itemView);
        }
    }
}
