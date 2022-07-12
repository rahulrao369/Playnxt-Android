package com.cw.playnxt.adapter.SettingAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cw.playnxt.fragment.AnnualFragment;
import com.cw.playnxt.fragment.MonthlyFragment;


public class PlaynextPremiumTabAdapter extends FragmentPagerAdapter {
    int tabcount;
    public PlaynextPremiumTabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 : return  new MonthlyFragment();
            case 1 : return  new AnnualFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
