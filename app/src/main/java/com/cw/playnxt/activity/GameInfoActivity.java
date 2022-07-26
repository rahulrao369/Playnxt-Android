package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityBacklogBinding;
import com.cw.playnxt.databinding.ActivityGameInfoBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;

public class GameInfoActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivityGameInfoBinding binding;
    private HeaderLayoutBinding headerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameInfoBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = GameInfoActivity.this;
        headerBinding.tvHeading.setText(R.string.GameInfo);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        binding.tvGamePlatform.setText("The Sony PlayStation\n" +
                "\n"+
                "Microsoft's Xbox\n" +
                "\n"+
                "Nintendo's Switch\n" +
                "\n"+
                "PCS\n" +
                "\n"+
                "Mobile\n");

        binding.ratingBar.setRating(2f);

    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        headerBinding.btnFilter.setOnClickListener(this);
        binding.btnAddToBacklog.setOnClickListener(this);
        binding.btnAddToWishList.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnShare:
                break;
            case R.id.btnAddToBacklog:
                startActivity(new Intent(context, BacklogActivity.class));
                break;
            case R.id.btnAddToWishList:
                startActivity(new Intent(context, WishlistActivity.class));
                break;

        }
    }

}