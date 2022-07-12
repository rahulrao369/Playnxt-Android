package com.cw.playnxt.adapter.GameAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClickEditCalenderEvent;
import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ItemListCalenderEventsLayoutBinding;
import com.cw.playnxt.databinding.ItemListCalenderScreenLayoutBinding;
import com.cw.playnxt.model.CalenderDataModel.GetEvent.Event;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CalenderEventAdapter extends RecyclerView.Adapter<CalenderEventAdapter.RecyclerViewHolder> {
    Context context;
    List<Event> list;
    ItemClickEditCalenderEvent itemClick;
    public CalenderEventAdapter(Context context,List<Event> list,ItemClickEditCalenderEvent itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public CalenderEventAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_calender_screen_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvGameTitle.setText(list.get(position).getTitle());
        holder.binding.tvPlannedStart.setText("Planned Start Date : "+list.get(position).getStartDate());
        holder.binding.tvPlannedEnd.setText("Planned End Date : "+list.get(position).getEndDate());

        holder.binding.ivEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"Edit",list.get(position));
            }
        });
        holder.binding.ivDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,"Delete",list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListCalenderScreenLayoutBinding binding;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListCalenderScreenLayoutBinding.bind(itemView);
        }
    }
}
