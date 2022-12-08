package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.MyProfileAdapters.SettingsAdapter;
import com.cw.playnxt.databinding.ActivitySettingBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.CheckSubscriptionFinal.CheckSubscriptionFinalResponse;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.Allurls;
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
    String subscribed = "";
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

    @Override
    protected void onStart() {
        super.onStart();
        if (Constants.isInternetConnected(context)) {
            NewCheckSubscriptionAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
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
        list.add(new GameModel(R.drawable.ic_s_manage_playnxt_premium, "Manage Playnxt Premium"));
        list.add(new GameModel(R.drawable.ic_s_suggest_new_features, "Suggest New Features"));
        list.add(new GameModel(R.drawable.ic_s_contact_us, "Contact Us"));
        list.add(new GameModel(R.drawable.ic_s_about_us, "About Us"));
        list.add(new GameModel(R.drawable.ic_s_rate_play_nxt, "Rate Playnxt"));
        list.add(new GameModel(R.drawable.ic_s_invite_friend, "Invite a Friend"));
        list.add(new GameModel(R.drawable.ic_s_invite_friend, "Privacy Policy"));
        list.add(new GameModel(R.drawable.ic_s_logout, "Logout"));

        SettingsAdapter adapter = new SettingsAdapter(context, list, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                if (position == 0) {
                    if (subscribed.equals(Constants.YES)) {
                        startActivity(new Intent(context, PlaynxtPremiumActivity.class));
                    } else {
                        startActivity(new Intent(context, SubscriptionActivityFinal.class));
                    }

                } else if (position == 1) {
                    startActivity(new Intent(context, SuggestNewFeatureActivity.class));
                } else if (position == 2) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:"+Constants.CLIENT_EMAIL+"?subject=" + Uri.encode("subject") + "&body=" + Uri.encode("body"));
                    intent.setData(data);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ABOUT_US_LINK));
                    startActivity(browserIntent);
                    // startActivity(new Intent(context,AboutUsActivity.class));
                } else if (position == 4) {
                    shareAppLinkToRateApp();
                    /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Allurls.share_app_url));
                    startActivity(browserIntent);*/
                } else if (position == 5) {
                    shareAppLink();
                } else if (position == 6) {
                  startActivity(new Intent(context,PrivacyPolicyActivity.class));
                }else if (position == 7) {
                    openLogoutDialog();
                }
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    private void shareAppLink() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Playnxt");

            String app_msg = getResources().getString(R.string.share_app_url_string);
            String app_link = "https://play.google.com/store/apps/details?id=com.cw.playnxt";

            String shareMessage =app_msg + "\n\n" + app_link ;
            Log.d("TAG", "shareMessage>>" + shareMessage);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void shareAppLinkToRateApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Playnxt");

            String app_msg = getResources().getString(R.string.rate_app);
            String app_link = "https://play.google.com/store/apps/details?id=com.cw.playnxt";

            String shareMessage =app_msg + "\n\n" + app_link ;
            Log.d("TAG", "shareMessage>>" + shareMessage);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)));
        } catch (Exception e) {
            //e.toString();
        }
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

        jsonPlaceHolderApi.LogoutAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
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

    //*********************************************************CHECK SUBSCRIPTION****************************************************
    public void NewCheckSubscriptionAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.NewCheckSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckSubscriptionFinalResponse>() {
            @Override
            public void onResponse(Call<CheckSubscriptionFinalResponse> call, Response<CheckSubscriptionFinalResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        subscribed = response.body().getData().getSubscribed();
                        Log.d("TAG", "subscribed>>>>" + subscribed);
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckSubscriptionFinalResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}