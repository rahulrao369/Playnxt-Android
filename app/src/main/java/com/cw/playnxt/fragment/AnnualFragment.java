package com.cw.playnxt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.activity.AddCardActivity;
import com.cw.playnxt.activity.FriendsProfileActivity;
import com.cw.playnxt.activity.HomeSearchActivity;
import com.cw.playnxt.activity.MyProfileActivity;
import com.cw.playnxt.adapter.SettingAdapters.AnnualPlanAdapter;
import com.cw.playnxt.databinding.FragmentAnuualBinding;
import com.cw.playnxt.model.StaticModel.AnnualPlanDataModel;

import java.util.ArrayList;
import java.util.List;

public class AnnualFragment extends Fragment implements View.OnClickListener{
    Context context;
    private FragmentAnuualBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnuualBinding.inflate(inflater, container, false);
        init();
        onClicks();
        return binding.getRoot();
    }

    private void onClicks() {
        binding.btnCheckout.setOnClickListener(this);
    }

    public void init() {
        context = binding.getRoot().getContext();
        AnnualListDataSet();
    }

    private void AnnualListDataSet() {
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

        AnnualPlanAdapter adapter = new AnnualPlanAdapter(context, list, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {

            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheckout:
                startActivity(new Intent(context, AddCardActivity.class));
                break;
        }
    }
}