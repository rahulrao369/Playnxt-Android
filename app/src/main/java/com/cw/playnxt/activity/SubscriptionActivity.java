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
    Long plan_ID = null;
    String selected_plane_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        onclicks();
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
         plan_ID = null;
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
                if(plan_ID == null){
                    Toast.makeText(context, "Please select any plan", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("TAG1","plan_ID"+plan_ID);
                    Log.d("TAG1","selected_plane_type"+selected_plane_type);
                    if(selected_plane_type.equals("Free")){
                        Toast.makeText(context, "For Free plan API not available", Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(context,HomeActivity.class));
                    }else{
                        startActivity(new Intent(context,AddCardActivity.class)
                                .putExtra("plan_ID",plan_ID.toString()));
                    }
                }

                // startActivity(new Intent(context,AddCardActivity.class));
             /*   if(plan_ID == null){
                    Toast.makeText(context, "Please select any plan", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(SubscriptionActivity.this, CardEditActivity.class);
                    startActivityForResult(intent, GET_NEW_CARD);

                }*/
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
                selected_plane_type = planList.get(position).getType();
                if(type.equals("subscription")){
                    Log.d("TAG","id"+id);
                    plan_ID = id;
                }else if(type.equals("not_selected")){
                    plan_ID = null;
                }
            }
        });
        binding.rvSubscriptionPlan.setHasFixedSize(true);
        binding.rvSubscriptionPlan.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvSubscriptionPlan.setAdapter(adapter);
    }
}