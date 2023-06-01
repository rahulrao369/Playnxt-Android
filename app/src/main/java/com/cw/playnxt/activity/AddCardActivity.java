package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityAddCardBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.PurchasePlan.PurchasePlanParaRes;
import com.cw.playnxt.model.PurchasePlan.PurchasePlanResponse;
import com.cw.playnxt.model.SubscriptionPlan.GetPaymentResponse;
import com.cw.playnxt.model.SubscriptionPlan.GetPaymentSummaryREq;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    String cardHolderName, cardNumber, expiryDate, monthCard, yearCard, cvvCard, plan_ID,couponCode="";
    String recurring = "0";
    private ActivityAddCardBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCardBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = AddCardActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.Addcard);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        binding.cbrecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Log.d("TAG","b>>"+b);
                    recurring = "1";
                } else {
                    Log.d("TAG","b>>>>"+b);
                    recurring = "0";
                }
            }
        });

        String text1 = "Active Recurring Payment.\n";
        String text2 = "(Payment will automatically deducted every month)";
        SpannableString spannableString1 = new SpannableString(text1);
        SpannableString spannableString2 = new SpannableString(text2);

        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new RelativeSizeSpan(0.9f), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.edt_text)), 0, text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new RelativeSizeSpan(0.8f), 0, text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new StyleSpan(Typeface.NORMAL), 0, text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(spannableString1);
        stringBuilder.append(spannableString2);
        binding.tvrecurring.setText(stringBuilder);


        try {
            Intent intent = getIntent();
            if (intent != null) {
                plan_ID = intent.getStringExtra("plan_ID");
//                couponCode = intent.getStringExtra("code");
                Log.d("TAG", "plan_ID>>>" + plan_ID);
                Log.d("TAG", "Coupon>>>" + couponCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setAccountHolderNameOnCard();
        setAccountNumberOnCard();
        setExpiryDateOnCard();
        setCvvOnCard();
    }


    private void setAccountHolderNameOnCard() {
        binding.etAccountHolderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cardHolderName = binding.etAccountHolderName.getText().toString();
                binding.creditCardView.setCardHolderName(cardHolderName.toUpperCase());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setAccountNumberOnCard() {
        binding.etAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cardNumber = binding.etAccountNo.getText().toString();
                binding.creditCardView.setCardNumber(cardNumber);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setExpiryDateOnCard() {
        binding.etExpringDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                expiryDate = binding.etExpringDate.getText().toString();
                binding.creditCardView.setCardExpiry(expiryDate);
                int textLength = binding.etExpringDate.getText().length();
                if (textLength == 3) {
                    if (!expiryDate.contains("/")) {
                        binding.etExpringDate.setText(new StringBuilder(binding.etExpringDate.getText().toString()).insert(expiryDate.length() - 1, "/").toString());
                        binding.etExpringDate.setSelection(binding.etExpringDate.getText().length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setCvvOnCard() {
        binding.etCVV.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cvvCard = binding.etCVV.getText().toString();
                binding.creditCardView.setCVV(cvvCard);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnPayNow.setOnClickListener(this);
        binding.btnApply.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnPayNow:
                if (Constants.isInternetConnected(context)) {
                    String[] separated = expiryDate.split("/");
                    monthCard = separated[0];
                    yearCard = separated[1];
                    PurchasePlanAPI(cardHolderName, cardNumber, monthCard, yearCard, cvvCard,couponCode);
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
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
        }
    }

    public void PurchasePlanAPI(String cardHolderName, String cardNumber, String monthCard, String yearCard, String cvvCard, String code) {
        Customprogress.showPopupProgressSpinner(context, true);
        PurchasePlanParaRes purchasePlanParaRes = new PurchasePlanParaRes();
        purchasePlanParaRes.setPlanId(Long.valueOf(plan_ID));
        purchasePlanParaRes.setName(cardHolderName);
        purchasePlanParaRes.setCardNumber(cardNumber);
        purchasePlanParaRes.setMonth(Long.valueOf(monthCard));
        purchasePlanParaRes.setYear(yearCard);
        purchasePlanParaRes.setCvv(cvvCard);
        purchasePlanParaRes.setCode(code);
        purchasePlanParaRes.setRecurring(recurring);

        jsonPlaceHolderApi.PurchasePlanAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), purchasePlanParaRes).enqueue(new Callback<PurchasePlanResponse>() {
            @Override
            public void onResponse(Call<PurchasePlanResponse> call, Response<PurchasePlanResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, ConfirmationActivity.class));
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