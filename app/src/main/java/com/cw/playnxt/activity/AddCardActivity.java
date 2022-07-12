package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityAddCardBinding;
import com.cw.playnxt.databinding.ActivityEditProfileBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivityAddCardBinding binding;
    private HeaderLayoutBinding headerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCardBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = AddCardActivity.this;
        headerBinding.tvHeading.setText(R.string.Addcard);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnPayNow.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnPayNow:
              startActivity(new Intent(context,ConfirmationActivity.class));
                break;
        }
    }
}