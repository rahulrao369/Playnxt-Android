package com.cw.playnxt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cw.playnxt.R;
import com.cw.playnxt.fragment.FirstFragment;
import com.cw.playnxt.fragment.SecondFragment;
import com.cw.playnxt.fragment.ThirdFragment;
import com.cw.playnxt.utils.PrefManager;

public class WelcomeActivity extends FragmentActivity {
    ViewPager pager;
    private LinearLayout btnNext;
    private TextView txtBtn;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);
        changeStatusBarColor();
        btnNext = findViewById(R.id.btnNext);
        txtBtn = findViewById(R.id.txtBtn);
        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < 3) {
                    pager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            // addBottomDots(position);
            if (position == 2) {
                txtBtn.setText(getResources().getString(R.string.GetStarted));
            } else {
                txtBtn.setText(getResources().getString(R.string.Next));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return FirstFragment.newInstance("FirstFragment, Instance 1");
                case 1:
                    return SecondFragment.newInstance("SecondFragment, Instance 1");
                case 2:
                    return ThirdFragment.newInstance("ThirdFragment, Instance 1");
                default:
                    return ThirdFragment.newInstance("SixthFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}