package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SubscriptionPlanListAdapterMain;
import com.cw.playnxt.databinding.ActivitySubscriptionBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.ForgotPassword.ForgotPasswordParaRes;
import com.cw.playnxt.model.PurchasePlan.PurchasePlanParaRes;
import com.cw.playnxt.model.PurchasePlan.PurchasePlanResponse;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.SubscriptionPlan.Plan;
import com.cw.playnxt.model.SubscriptionPlan.SubscriptionPlanResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {
    final int GET_NEW_CARD = 2;
    Context context;
    private ActivitySubscriptionBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String cardHolderName,cardNumber,expiryDate,monthCard,yearCard,cvvCard;
    Long plan_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());

    }

    public void init() {
        context = SubscriptionActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText("Playnext Premium");
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
        if (Constants.isInternetConnected(context)) {
            SubscriptionPlanAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnContinue.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnContinue:

                // startActivity(new Intent(context,AddCardActivity.class));
                if(plan_ID == null){
                    Toast.makeText(context, "Please select any plan", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(SubscriptionActivity.this, CardEditActivity.class);
                    startActivityForResult(intent, GET_NEW_CARD);

                }
                break;
        }
    }

    public void SubscriptionPlanAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.SubscriptionPlanAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<SubscriptionPlanResponse>() {
            @Override
            public void onResponse(Call<SubscriptionPlanResponse> call, Response<SubscriptionPlanResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        //   Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        SubscriptionPlanListDataSet(response.body().getData().getPlan());
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubscriptionPlanResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SubscriptionPlanListDataSet(List<Plan> planList) {
        SubscriptionPlanListAdapterMain adapter = new SubscriptionPlanListAdapterMain(context, planList, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long id, String type) {
                plan_ID = id;
            }
        });
        binding.rvSubscriptionPlan.setHasFixedSize(true);
        binding.rvSubscriptionPlan.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvSubscriptionPlan.setAdapter(adapter);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {

             cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
             cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
             expiryDate = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
             cvvCard = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            String[] separated = expiryDate.split("/");
            monthCard = separated[0];
            yearCard = separated[1];
             Log.d("TAG","cardHolderName>>"+cardHolderName);
             Log.d("TAG","cardNumber>>"+cardNumber);
             Log.d("TAG","expiryDate>>"+expiryDate);
             Log.d("TAG","monthCard>>"+monthCard);
             Log.d("TAG","yearCard>>"+yearCard);
             Log.d("TAG","cvvCard>>"+cvvCard);

            if (Constants.isInternetConnected(context)) {
                PurchasePlanAPI(cardHolderName,cardNumber,monthCard,yearCard,cvvCard);
            } else {
                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void PurchasePlanAPI(String cardHolderName, String cardNumber, String monthCard, String yearCard, String cvvCard) {
        Customprogress.showPopupProgressSpinner(context, true);
        PurchasePlanParaRes purchasePlanParaRes = new PurchasePlanParaRes();
        purchasePlanParaRes.setPlanId(plan_ID);
        purchasePlanParaRes.setName(cardHolderName);
        purchasePlanParaRes.setCardNumber(cardNumber);
        purchasePlanParaRes.setMonth(Long.valueOf(monthCard));
        purchasePlanParaRes.setYear(yearCard);
        purchasePlanParaRes.setCvv(cvvCard);

        jsonPlaceHolderApi.PurchasePlanAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(), purchasePlanParaRes).enqueue(new Callback<PurchasePlanResponse>() {
            @Override
            public void onResponse(Call<PurchasePlanResponse> call, Response<PurchasePlanResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context,ConfirmationActivity.class));
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<PurchasePlanResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        init();
        onclicks();
    }
}