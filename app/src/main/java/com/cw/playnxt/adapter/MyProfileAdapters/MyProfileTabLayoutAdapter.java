package com.cw.playnxt.adapter.MyProfileAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cw.playnxt.fragment.MyProfileFollowersFragment;
import com.cw.playnxt.fragment.MyProfileFollowingFragment;
import com.cw.playnxt.fragment.MyProfileGamesFragment;


public class MyProfileTabLayoutAdapter extends FragmentPagerAdapter {
    int tabcount;
    public MyProfileTabLayoutAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 : return  new MyProfileGamesFragment();
            case 1 : return  new MyProfileFollowersFragment();
            case 2 : return  new MyProfileFollowingFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
