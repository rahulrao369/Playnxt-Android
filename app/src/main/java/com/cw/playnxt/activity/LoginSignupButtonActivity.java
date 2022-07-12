package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityLoginSignupButtonBinding;

public class LoginSignupButtonActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivityLoginSignupButtonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginSignupButtonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    private void onclicks() {
        binding.btnLogin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
    }

    public void init() {
        context = LoginSignupButtonActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                startActivity(new Intent(context, LoginActivity.class));
                break;

            case R.id.btnSignup:
                startActivity(new Intent(context, SignupActivity.class));
                break;
        }
    }
}