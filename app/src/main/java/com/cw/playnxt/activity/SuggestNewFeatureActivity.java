package com.cw.playnxt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityConfirmationBinding;
import com.cw.playnxt.databinding.ActivitySuggestNeFeatureBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.DeleteCalenderEvent.DeleteCalenderEventParaRes;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.SuggestionData.SuggestionParaRes;
import com.cw.playnxt.model.SuggestionData.SuggestionResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestNewFeatureActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivitySuggestNeFeatureBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestNeFeatureBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = SuggestNewFeatureActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.Suggestnewfeature);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

                case R.id.btnSubmit:
                    if(!binding.etSuggestion.getText().toString().equals("")){
                        if (Constants.isInternetConnected(context)) {
                            SuggestionAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Suggestion is required", Toast.LENGTH_SHORT).show();
                    }
                break;
        }
    }
    public void SuggestionAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        SuggestionParaRes suggestionParaRes = new SuggestionParaRes();
        suggestionParaRes.setText(binding.etSuggestion.getText().toString().trim());

        jsonPlaceHolderApi.SuggestionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), suggestionParaRes).enqueue(new Callback<SuggestionResponse>() {
            @Override
            public void onResponse(Call<SuggestionResponse> call, Response<SuggestionResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        openThankyouDialog();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SuggestionResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void openThankyouDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_thankyou);
        TextView txtOk = dialog.findViewById(R.id.txtOk);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }
}