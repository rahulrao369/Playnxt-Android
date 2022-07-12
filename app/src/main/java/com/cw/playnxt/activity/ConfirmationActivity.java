package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityAddCardBinding;
import com.cw.playnxt.databinding.ActivityConfirmationBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;

public class ConfirmationActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivityConfirmationBinding binding;
    private HeaderLayoutBinding headerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmationBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = ConfirmationActivity.this;
        headerBinding.tvHeading.setText(R.string.Confirmation);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }
}