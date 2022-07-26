package com.cw.playnxt.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityHomeBinding;
import com.cw.playnxt.fragment.DiscoverFragment;
import com.cw.playnxt.fragment.GameFragment;
import com.cw.playnxt.fragment.HomeFragment;
import com.cw.playnxt.fragment.InboxFragment;
import com.cw.playnxt.fragment.NewFriendsFragment;
import com.cw.playnxt.server.MySharedPref;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    public static ChipNavigationBar chipNavigationBar;
    Context context;
    private ActivityHomeBinding binding;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment()).commit();
        init();
    }
    public void init() {
        context = HomeActivity.this;
        mySharedPref = new MySharedPref(context);
        Log.d("TAG","ACCESS_TOKEN>>>>>>>>>>>>>>"+mySharedPref.getSavedAccessToken());
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        chipNavigationBar.setItemSelected(R.id.home, true);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {

                switch (chipNavigationBar.getSelectedItemId()) {
                    case R.id.menu_home:
                        callFragment(new HomeFragment());
                        break;

                    case R.id.menu_discover:
                        callFragment(new DiscoverFragment());
                        break;

                    case R.id.menu_games:
                        callFragment(new GameFragment());
                        break;

                    case R.id.menu_friends:
                        Fragment friendsfrgamnet1 = new NewFriendsFragment();
//                        Fragment friendsfrgamnet1 = new FriendsFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("key", 0);
                        friendsfrgamnet1.setArguments(bundle1);

                        FragmentTransaction transaction;
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameContainer, friendsfrgamnet1);
                        transaction.addToBackStack("jkhjb");
                        transaction.commit();
                        break;

                    case R.id.menu_inbox:
                        callFragment(new InboxFragment());
                        break;
                }
            }
        });
    }

    private void callFragment(Fragment fragment) {
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.are_you_sure_you_want_to_exit))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity(); // Close all activites
                        System.exit(0);
                    }
                })
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }
}