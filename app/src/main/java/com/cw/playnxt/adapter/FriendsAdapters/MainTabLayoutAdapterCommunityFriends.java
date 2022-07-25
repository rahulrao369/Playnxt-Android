package com.cw.playnxt.adapter.FriendsAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cw.playnxt.fragment.TabActivityFragment;
import com.cw.playnxt.fragment.TabCommunityFragment;
import com.cw.playnxt.fragment.TabFollowersFragment;
import com.cw.playnxt.fragment.TabFollowingFragment;
import com.cw.playnxt.fragment.TabFriendsFragment;


public class MainTabLayoutAdapterCommunityFriends extends FragmentStatePagerAdapter {
    int tabcount;
    public MainTabLayoutAdapterCommunityFriends(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return  new TabCommunityFragment();

            case 1 : return  new TabFriendsFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


}
