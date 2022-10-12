package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityPlaynxtPremiumBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.CancelSubscription.CancelSubscriptionResponse;
import com.cw.playnxt.model.CheckSubscriptionFinal.CheckSubscriptionFinalResponse;
import com.cw.playnxt.model.MyActivePlan.ActivePlan;
import com.cw.playnxt.model.MyActivePlan.MyActivePlanResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaynxtPremiumActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    int grace_taken;
    int recurring;
    String recurring_payment = "";
    String subscribed = "";
    private ActivityPlaynxtPremiumBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaynxtPremiumBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = PlaynxtPremiumActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText("Playnxt Premium");
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
            MyActivePlanAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnCancleSubscription.setOnClickListener(this);
        binding.btnBuy.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnCancleSubscription:
                openCancelSubscriptionDialog();
                break;

            case R.id.btnBuy:
                startActivity(new Intent(context, SubscriptionActivityFinal.class));
                break;

        }
    }

    //*********************************************************CHECK SUBSCRIPTION****************************************************
    public void NewCheckSubscriptionAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.NewCheckSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckSubscriptionFinalResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CheckSubscriptionFinalResponse> call, Response<CheckSubscriptionFinalResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        grace_taken = response.body().getData().getSubscription().getGrace_taken();
                        recurring = response.body().getData().getSubscription().getRecurring();
                        recurring_payment = response.body().getData().getSubscription().getRecurring_payment();
                        subscribed = response.body().getData().getSubscribed();
                        Log.d("TAG!!", "grace_taken>>>>" + grace_taken);
                        Log.d("TAG!!", "recurring>>>>" + recurring);
                        Log.d("TAG!!", "recurring_payment>>>>" + recurring_payment);
                        Log.d("TAG!!", "subscribed>>>>" + subscribed);

                        if (subscribed.equals("Yes")) {
                            binding.btnBuy.setVisibility(View.GONE);
                            binding.llGrace.setVisibility(View.GONE);
                        } else if(subscribed.equals("No")){
                            binding.btnBuy.setVisibility(View.GONE);
                            binding.llGrace.setVisibility(View.GONE);
                        }else{
                            binding.btnBuy.setVisibility(View.VISIBLE);
                            binding.btnCancleSubscription.setVisibility(View.GONE);
                            binding.llGrace.setVisibility(View.GONE);
                        }


                        if (recurring_payment.equals("Yes")) {
                            binding.btnCancleSubscription.setVisibility(View.VISIBLE);
                            binding.btnBuy.setVisibility(View.GONE);
                            binding.llGrace.setVisibility(View.GONE);
                        } else if(recurring_payment.equals("No")){
                            binding.btnCancleSubscription.setVisibility(View.GONE);
                            binding.btnBuy.setVisibility(View.GONE);
                            binding.llGrace.setVisibility(View.GONE);
                        }

                        if (grace_taken == 0) {
                            binding.llGrace.setVisibility(View.GONE);
                        } else if (grace_taken == 1) {
                            binding.llGrace.setVisibility(View.VISIBLE);
                            binding.tvRemainingDays.setText("Remaining days: "+response.body().getData().getRemaining_days());
                            binding.btnBuy.setVisibility(View.VISIBLE);
                        }

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

    public void MyActivePlanAPI() {
        jsonPlaceHolderApi.MyActivePlanAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<MyActivePlanResponse>() {
            @Override
            public void onResponse(Call<MyActivePlanResponse> call, Response<MyActivePlanResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        if (response.body().getData().getActivePlan() != null) {
                            ActivePlanDataSet(response.body().getData().getActivePlan());
                        }

                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyActivePlanResponse> call, Throwable t) {
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ActivePlanDataSet(ActivePlan activePlan) {
        binding.tvPlanTitle.setText(activePlan.getTitle());
        if (activePlan.getTitle().equals("Monthly")) {
            binding.tvValidUpto.setText("Valid Upto 1 month");
        } else if (activePlan.getTitle().equals("Yearly")) {
            binding.tvValidUpto.setText("Valid Upto 12 months");
        }
        binding.tvPurchaseDate.setText(activePlan.getStartDate());
        binding.tvExpiryDate.setText(activePlan.getEndDate());
        //  binding.tvDescription.setText(activePlan.getDescription());
        binding.tvPrice.setText("$" + activePlan.getAmount());
    }

    public void cancelSubscriptionAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.cancelSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CancelSubscriptionResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CancelSubscriptionResponse> call, Response<CancelSubscriptionResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                      /*  startActivity(new Intent(context, SubscriptionActivityFinal.class));
                        finish();*/
                        onStart();
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelSubscriptionResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void openCancelSubscriptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to cancel subscription ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isInternetConnected(context)) {
                    cancelSubscriptionAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}