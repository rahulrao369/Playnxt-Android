package com.cw.playnxt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickGameInfoRecentList;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SettingAdapters.MonthlyPlanAdapter;
import com.cw.playnxt.databinding.ItemListSubscriptionPlanListMainLayoutBinding;
import com.cw.playnxt.model.GetRecentGame.GetRecentGameDataCapsul;
import com.cw.playnxt.model.StaticModel.AnnualPlanDataModel;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.SubscriptionPlan.Plan;
import com.cw.playnxt.model.SubscriptionPlan.StatisSubscriptionPlansListWithSymbol;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionPlanListAdapterMain extends RecyclerView.Adapter<SubscriptionPlanListAdapterMain.RecyclerViewHolder> {
    Context context;
    List<Plan> list;
    int selectPosition = -1;
    ItemClickId itemClick;
    public SubscriptionPlanListAdapterMain(Context context, List<Plan> list,  ItemClickId itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
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

        if (selectPosition == position){
            holder.binding.llUpper.setBackgroundResource(R.color.selected);
            holder.binding.btnPlan.setBackgroundResource(R.drawable.bg_selected_btn);
        }
        else{
            holder.binding.llUpper.setBackgroundResource(R.color.border);
            holder.binding.btnPlan.setBackgroundResource(R.drawable.bg_white_stroke);
        }
        Log.d("TAG","selectPosition"+selectPosition);
        holder.binding.tvPlanTitle.setText(list.get(position).getTitle());
        holder.binding.tvplanForUser.setText(list.get(position).getTitle()+" For User");
        String description = list.get(position).getDescription().substring(0, 1).toUpperCase() + list.get(position).getDescription().substring(1).toLowerCase();
        holder.binding.tvPlan.setText(description);
        holder.binding.tvAmount.setText(String.valueOf("$"+list.get(position).getAmount()));
        if(!list.get(position).getDuration().equals("")){
            String duration = list.get(position).getDuration().substring(0, 1).toUpperCase() + list.get(position).getDuration().substring(1).toLowerCase();
            holder.binding.tvBtn.setText(duration);
        }
        if(list.get(position).getTitle().equals("Free")){
            holder.binding.ivPlan.setImageResource(R.drawable.ic_free);
            holder.binding.tvBtn.setText("Free");
        }else if(list.get(position).getTitle().equals("Silver Plan")){
            holder.binding.ivPlan.setImageResource(R.drawable.silver_medal);
        }else if(list.get(position).getTitle().equals("Premium Plan")){
            holder.binding.ivPlan.setImageResource(R.drawable.diamond);
        }
        List<StatisSubscriptionPlansListWithSymbol> subList = new ArrayList<>();
        subList.add(new StatisSubscriptionPlansListWithSymbol("Ad-Free Experience",list.get(position).getAdFree()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("Manage Backlogs",list.get(position).getBacklog()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("Manage Wishlists",list.get(position).getWishlist()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("Calendar Tool ",list.get(position).getCalender()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("Personal Stats",list.get(position).getPersonalStats()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("View Community Activity",list.get(position).getCommActivity()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("Follow Friends",list.get(position).getFollowFriends()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("View Friend Backlog and Wishlists ",list.get(position).getViewFriendBW()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("Message Friends",list.get(position).getMessageFnd()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("Barcode Scanning Tool (coming soon) ",list.get(position).getScanningTool()));
        subList.add(new StatisSubscriptionPlansListWithSymbol("Early Access to New Features",list.get(position).getAccessNewFeatures()));

        SubscriptionPlanInsideListAdapter adapter = new SubscriptionPlanInsideListAdapter(context, subList, new ItemClick() {
            @Override
            public void onItemClick(int position_item, String type) {
                Log.d("TAG","Clicked");
                selectPosition = position;
                Log.d("TAG","selectPosition item"+selectPosition);
                itemClick.onItemClick(position,1l,"subscription");
                notifyDataSetChanged();
            }
        });
        holder.binding.recyclerView.setHasFixedSize(true);
        holder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.binding.recyclerView.setAdapter(adapter);

        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                itemClick.onItemClick(position,1l,"subscription");
                notifyDataSetChanged();
            }
        });
           holder.binding.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                itemClick.onItemClick(position,1l,"subscription");
                notifyDataSetChanged();
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
