package com.cw.playnxt.adapter.FriendsAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cw.playnxt.fragment.TabActivityFragment;
import com.cw.playnxt.fragment.TabFollowersFragment;
import com.cw.playnxt.fragment.TabFollowingFragment;


public class TabLayoutAdapter extends FragmentPagerAdapter {
    int tabcount;
    String friendsId;
    public TabLayoutAdapter(@NonNull FragmentManager fm, int behavior, String friends_id) {
        super(fm, behavior);
        tabcount=behavior;
        friendsId=friends_id;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 : return  new TabActivityFragment(friendsId);
            case 1 : return  new TabFollowersFragment(friendsId);
            case 2 : return  new TabFollowingFragment(friendsId);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
