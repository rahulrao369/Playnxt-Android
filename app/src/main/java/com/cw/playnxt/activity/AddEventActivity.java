package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityAddEventBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.CalenderDataModel.AddEvent.AddEventParaRes;
import com.cw.playnxt.model.CalenderDataModel.GetEvent.Event;
import com.cw.playnxt.model.EditCalenderEvent.EditCalenderEventParaRes;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    String START_DATE;
    String END_DATE;
    private ActivityAddEventBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    Event eventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEventBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    @SuppressLint("SetTextI18n")
    public void init() {
        context = AddEventActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.AddEvent);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        try {
            Intent intent = getIntent();
            if (intent != null) {
                eventData = (Event) intent.getSerializableExtra("eventData");
                Log.d("TAG", "eventData>>" + eventData);
                if(!eventData.getTitle().equals("")){
                    headerBinding.tvHeading.setText(R.string.Edit_event);
                }
                binding.etTitle.setText(eventData.getTitle());
                binding.tvStartDate.setText(eventData.getStartDate());
                binding.tvEndDate.setText(eventData.getEndDate());
                binding.etAddNote.setText(eventData.getNote());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.llSelectStartDate.setOnClickListener(this);
        binding.llSelectEndDate.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.llSelectStartDate:
                openDialogSelectStartDate();
                break;

            case R.id.llSelectEndDate:
                openDialogSelectEndDate();
                break;

            case R.id.btnSave:
                if(isValidate()){
                    if(eventData == null){
                        if (Constants.isInternetConnected(context)) {
                            AddEventAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if (Constants.isInternetConnected(context)) {
                            EditCalenderEventAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

        }
    }

    private void openDialogSelectEndDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String fm = "" + month;
                String fd = "" + dayOfMonth;
                if (month < 10) {
                    fm = "0" + month;
                }
                if (dayOfMonth < 10) {
                    fd = "0" + dayOfMonth;
                }
               // String date = "" + year + "/" + fm + "/" + fd;
                String date = "" + fm + "/" + fd + "/" + year;
                binding.tvEndDate.setText("" + fm + "/" + fm + "/" + year);
                END_DATE = date;
            }
        }, mYear, mMonth, mDay);
        dpd.show();
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private void openDialogSelectStartDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String fm = "" + month;
                String fd = "" + dayOfMonth;
                if (month < 10) {
                    fm = "0" + month;
                }
                if (dayOfMonth < 10) {
                    fd = "0" + dayOfMonth;
                }
                String date = "" + fm + "/" + fd + "/" + year;
                binding.tvStartDate.setText("" + fm + "/" + fd + "/" + year);
                START_DATE = date;
            }
        }, mYear, mMonth, mDay);
        dpd.show();
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }


    public void AddEventAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        AddEventParaRes addEventParaRes= new AddEventParaRes();
        addEventParaRes.setTitle(binding.etTitle.getText().toString().trim());
        addEventParaRes.setStartDate(binding.tvStartDate.getText().toString().trim());
        addEventParaRes.setEndDate(binding.tvEndDate.getText().toString().trim());
        addEventParaRes.setNote(binding.etAddNote.getText().toString().trim());

        jsonPlaceHolderApi.AddEventAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(),addEventParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
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

    //********************************************EDIT EVENT API*************************************
    public void EditCalenderEventAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        EditCalenderEventParaRes editCalenderEventParaRes= new EditCalenderEventParaRes();
        editCalenderEventParaRes.setEventId(String.valueOf(eventData.getId()));
        editCalenderEventParaRes.setTitle(binding.etTitle.getText().toString().trim());
        editCalenderEventParaRes.setStartDate(binding.tvStartDate.getText().toString().trim());
        editCalenderEventParaRes.setEndDate(binding.tvEndDate.getText().toString().trim());
        editCalenderEventParaRes.setNote(binding.etAddNote.getText().toString().trim());

        jsonPlaceHolderApi.EditCalenderEventAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(),editCalenderEventParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
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

    private Boolean isValidate() {
       if (binding.etTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please add any title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.tvStartDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please select any start date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.tvEndDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please select any end date", Toast.LENGTH_SHORT).show();
            return false;
        } /*else if (binding.etAddNote.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please add any note", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }
}