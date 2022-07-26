package com.cw.playnxt.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cw.playnxt.fragment.TabCommunityFragment;
import com.cw.playnxt.fragment.TabFriendsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new TabCommunityFragment();
         else if (position == 1)
            fragment = new TabFriendsFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
