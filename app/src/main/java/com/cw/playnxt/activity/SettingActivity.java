package com.cw.playnxt.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.FriendsAdapters.FriendsListFriendsFragmentAdapter;
import com.cw.playnxt.adapter.MyProfileAdapters.SettingsAdapter;
import com.cw.playnxt.databinding.ActivityBacklogBinding;
import com.cw.playnxt.databinding.ActivitySettingBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.GetRecentGame.GetRecentGameResponse;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivitySettingBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = SettingActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.Settings);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        SettingListDataSet();
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

    private void SettingListDataSet() {
        List<GameModel> list = new ArrayList<>();
        list.add(new GameModel(R.drawable.ic_s_manage_playnxt_premium,"Manage Playnxt Premium"));
        list.add(new GameModel(R.drawable.ic_s_suggest_new_features,"Suggest New Features"));
        list.add(new GameModel(R.drawable.ic_s_contact_us,"Contact Us"));
        list.add(new GameModel(R.drawable.ic_s_about_us,"About Us"));
        list.add(new GameModel(R.drawable.ic_s_rate_play_nxt,"Rate Play Next"));
        list.add(new GameModel(R.drawable.ic_s_invite_friend,"Invite a Friend"));
        list.add(new GameModel(R.drawable.ic_s_logout,"Logout"));

        SettingsAdapter adapter = new SettingsAdapter(context, list, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                if (position == 0) {
                    startActivity(new Intent(context,SettingPlaynextPremiumActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(context,SuggestNewFeatureActivity.class));
                }  else if (position == 2) {
                    startActivity(new Intent(context,ContactUsActivity.class));
                }  else if (position == 3) {
                    startActivity(new Intent(context,AboutUsActivity.class));
                }  else if (position == 4) {

                }  else if (position == 5) {

                } else if (position == 6) {
                    openLogoutDialog();
                }
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    private void openLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getResources().getString(R.string.do_you_want_to_logout));
        builder.setCancelable(true);
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isInternetConnected(context)) {
                    LogoutAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
            }
        });
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //***************************************LOGOUT API**************************************
    public void LogoutAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.LogoutAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        mySharedPref.saveLogin(false);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSatusMessage> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

}