package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SubscriptionAccessExtraFeaturesAdapter;
import com.cw.playnxt.databinding.ActivitySubscriptionCouponBinding;
import com.cw.playnxt.databinding.ActivitySubscriptionFinalBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.GET_PLAN.GetPlanResponse;
import com.cw.playnxt.model.GetRecentGame.GetRecentGameResponse;
import com.cw.playnxt.model.StaticModel.AccessExtraFeatures;
import com.cw.playnxt.model.SubscriptionPlan.GetPaymentResponse;
import com.cw.playnxt.model.SubscriptionPlan.GetPaymentSummaryREq;
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

public class SubscriptionActivityCoupon extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivitySubscriptionCouponBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    Long plan_ID_MONTHLY;
    Long plan_ID_YEARLY;
    String plan_ID = "";
    String couponCode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionCouponBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());

        try {
            Intent intent = getIntent();
            if (intent != null) {
                plan_ID = intent.getStringExtra("plan_ID");
                Log.d("TAG", "plan_ID>>>" + plan_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();
        onclicks();
    }
    public void init() {
        context = SubscriptionActivityCoupon.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText("Playnxt Subscription");
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        if (Constants.isInternetConnected(context)) {

        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }


    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnApply.setOnClickListener(this);
        binding.txtBtn.setOnClickListener(this);
        binding.txtSkip.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnApply:
                String code =  binding.etEmail.getText().toString();
                couponCode = code;
                if(code.isEmpty()){
                    Toast.makeText(context, "Enter Code", Toast.LENGTH_SHORT).show();
                }else{
                    GetRecentGameAPI(code);
                }


                break;
            case R.id.txtBtn:
                startActivity(new Intent(context,AddCardActivity.class)
                        .putExtra("plan_ID",plan_ID.toString()).putExtra("code",couponCode));
                break;
            case R.id.txtSkip:
                startActivity(new Intent(context,AddCardActivity.class)
                        .putExtra("plan_ID",plan_ID.toString()).putExtra("code",couponCode));
                break;


        }
    }

    public void GetRecentGameAPI(String code) {
        binding.rlProgressBar.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);
        GetPaymentSummaryREq getPaymentSummaryREq = new GetPaymentSummaryREq();
        getPaymentSummaryREq.setPlanId(Integer.parseInt(plan_ID));
        getPaymentSummaryREq.setCode(code);
        jsonPlaceHolderApi.getpaymentsummary(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(),getPaymentSummaryREq).enqueue(new Callback<GetPaymentResponse>() {
            @Override
            public void onResponse(Call<GetPaymentResponse> call, Response<GetPaymentResponse> response) {
                if (response.isSuccessful()) {
//                    System.out.println("payment response>>> "+response.body().getData().getActualprice());
                    try{
                        Boolean status = response.body().getStatus();
                        if (status) {
                            if (response.body().getData() != null) {
                                dialogPaymentSummary();

                                binding.tvActualAmt.setText(response.body().getData().getActualprice());
                                binding.tvDiscount.setText(response.body().getData().getDiscount());
                                binding.tvTotal.setText(response.body().getData().getFinalprice());

                            } else {

                            }

                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){

                    }
                    binding.rlProgressBar.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);

                } else {
                    binding.rlProgressBar.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetPaymentResponse> call, Throwable t) {
                binding.rlProgressBar.setVisibility(View.GONE);
                binding.llMain.setVisibility(View.VISIBLE);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void dialogPaymentSummary() {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_payment_summary);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView txtbtn = dialog.findViewById(R.id.txtBtn);
        txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                binding.llNext.setVisibility(View.VISIBLE);
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

}