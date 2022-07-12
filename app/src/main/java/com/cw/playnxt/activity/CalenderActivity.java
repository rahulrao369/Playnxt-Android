package com.cw.playnxt.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.cw.playnxt.Interface.ItemClickEditCalenderEvent;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.DiscoverAdapters.GameListAdapter;
import com.cw.playnxt.adapter.GameAdapters.CalenderEventAdapter;
import com.cw.playnxt.databinding.ActivityCalenderBinding;
import com.cw.playnxt.databinding.ActivityWishlistBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.CalenderDataModel.AddEvent.AddEventParaRes;
import com.cw.playnxt.model.CalenderDataModel.GetEvent.Event;
import com.cw.playnxt.model.CalenderDataModel.GetEvent.GetEventParaRes;
import com.cw.playnxt.model.CalenderDataModel.GetEvent.GetEventResponse;
import com.cw.playnxt.model.DeleteCalenderEvent.DeleteCalenderEventParaRes;
import com.cw.playnxt.model.DeleteGameNote.DeleteGameNoteParaRes;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalenderActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private ActivityCalenderBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String selected_date = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalenderBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (Constants.isInternetConnected(context)) {
            GetEventAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }
    public void init() {
        context = CalenderActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.Calender_tool);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);


        Date cDate = new Date();
        selected_date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        Log.d("TAG","current Date DATE>>    "+   selected_date);


        binding.calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

            }
        });

        binding.calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

            }
        });

        binding.calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                System.out.println("cc"+eventDay.getCalendar().getTime());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                selected_date = format.format(eventDay.getCalendar().getTime());
                Log.d("TAG","Formated DATE>>    "+   selected_date);
                if (Constants.isInternetConnected(context)) {
                    GetEventAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        headerBinding.btnAdd.setOnClickListener(this);
        binding.btnAddEvent.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnAddEvent:
                startActivity(new Intent(context, AddEventActivity.class));
                break;
        }
    }

    public void GetEventAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        GetEventParaRes getEventParaRes = new GetEventParaRes();
        getEventParaRes.setDate(selected_date);
        jsonPlaceHolderApi.GetEventAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(),getEventParaRes).enqueue(new Callback<GetEventResponse>() {
            @Override
            public void onResponse(Call<GetEventResponse> call, Response<GetEventResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        if(response.body().getData().getEvent() != null){
                            if(response.body().getData().getEvent().size() != 0){
                                binding.recyclerView.setVisibility(View.VISIBLE);
                                setEventData(response.body().getData().getEvent());
                            }else{
                                binding.recyclerView.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetEventResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void setEventData(List<Event> eventList) {
        CalenderEventAdapter adapter = new CalenderEventAdapter(context, eventList, new ItemClickEditCalenderEvent() {
            @Override
            public void onItemClick(int position, String type, Event eventData) {
                if (type.equals("Edit")) {
                    startActivity(new Intent(context, AddEventActivity.class)
                    .putExtra("eventData",eventData));
                } else if (type.equals("Delete")) {
                    openCalenderEventDeleteDialog(eventData.getId());
                }
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    private void openCalenderEventDeleteDialog(Long event_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this event?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isInternetConnected(context)) {
                    DeleteCalenderEventAPI(event_id);
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
    //*************************************************DELETE EVENT API***************************************
    public void DeleteCalenderEventAPI(Long event_id) {
        Customprogress.showPopupProgressSpinner(context, true);
        DeleteCalenderEventParaRes deleteCalenderEventParaRes = new DeleteCalenderEventParaRes();
        deleteCalenderEventParaRes.setEventId(event_id.toString());

        jsonPlaceHolderApi.DeleteCalenderEventAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), deleteCalenderEventParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetEventAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
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