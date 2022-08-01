package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickView;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.GameAdapters.BacklogXboxGamesAdapter;
import com.cw.playnxt.databinding.ActivityBacklogBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.AddBacklogList.AddBacklogListParaRes;
import com.cw.playnxt.model.CalenderDataModel.AddEvent.AddEventParaRes;
import com.cw.playnxt.model.CalenderDataModel.GetEvent.GetEventResponse;
import com.cw.playnxt.model.DeleteBacklogList.DeleteBacklogListParaRes;
import com.cw.playnxt.model.EditBacklogList.EditBacklogListParaRes;
import com.cw.playnxt.model.GetBacklogList.Count;
import com.cw.playnxt.model.GetBacklogList.GetMyBacklogListResponse;
import com.cw.playnxt.model.NewCheckSubscription.NewCheckSubscriptionResponse;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BacklogActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    LayoutInflater mInflater;
    EditText etName,etEditName;
    LinearLayout btnCreateList,btnEditList;
    private ActivityBacklogBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    private PopupWindow mDropdown = null;
    Long backlog_ID ;
    String planType = "";
    int total_backlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBacklogBinding.inflate(getLayoutInflater());
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
        context = BacklogActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.Backlog);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        if (Constants.isInternetConnected(context)) {
            NewCheckSubscriptionAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.llCreateBacklogList.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.llCreateBacklogList:
                if (planType.equals(Constants.PLAN_TYPE_FREE)) {
                    if(total_backlog == 0){
                        startActivity(new Intent(context, SubscriptionActivity.class));
                    }else{
                        showBottomSheetFilterDialog();
                    }
                }else if(planType.equals(Constants.PLAN_TYPE_PAID)){
                    showBottomSheetFilterDialog();
                }
                break;
        }
    }

    private void showBottomSheetFilterDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_filter);
        etName = bottomSheetDialog.findViewById(R.id.etName);
        btnCreateList = bottomSheetDialog.findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etName.getText().toString().equals("")){
                    if(etName.getText().toString().length() < 20){
                        bottomSheetDialog.dismiss();
                        if (Constants.isInternetConnected(context)) {
                            AddBacklogListAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, getResources().getString(R.string.name_should_be_less_than_20_charactrs), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(context, "Please enter the name for your backlog list", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bottomSheetDialog.show();
    }

    private PopupWindow initiatePopupWindow(View viewEdit, String name) {
        try {
            mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.custom_popup_edit_delete, null);

            final TextView menu_edit = (TextView) layout.findViewById(R.id.menu_edit);
            final TextView menu_delete = (TextView) layout.findViewById(R.id.menu_delete);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT, true);
            Drawable background = getResources().getDrawable(android.R.drawable.alert_light_frame);

            mDropdown.setBackgroundDrawable(background);
            mDropdown.showAsDropDown(viewEdit, 5, 5);

            menu_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDropdown.dismiss();
                    showBottomSheetEditBacklogListDialog(name);
                }
            });
            menu_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDropdown.dismiss();
                    openDeleteDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;
    }

    private void showBottomSheetEditBacklogListDialog(String name) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bootom_sheet_edit_backloglist);
        etEditName = bottomSheetDialog.findViewById(R.id.etEditName);
        btnEditList = bottomSheetDialog.findViewById(R.id.btnEditList);

        etEditName.setText(name);
        btnEditList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etEditName.getText().toString().equals("")){
                    if(etEditName.getText().toString().length() < 20){
                        bottomSheetDialog.dismiss();
                        if(backlog_ID != null){
                            if (Constants.isInternetConnected(context)) {
                                EditBacklogListAPI();
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(context, getResources().getString(R.string.name_should_be_less_than_20_charactrs), Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(context, "Please enter the name for your backlog list", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bottomSheetDialog.show();
    }

    private void openDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isInternetConnected(context)) {
                    DeleteBacklogListAPI();
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

    public void AddBacklogListAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        AddBacklogListParaRes addBacklogListParaRes = new AddBacklogListParaRes();
        addBacklogListParaRes.setName(etName.getText().toString().trim());

        jsonPlaceHolderApi.AddBacklogListAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), addBacklogListParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            NewCheckSubscriptionAPI();
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

    public void GetBacklogListAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.GetBacklogListAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyBacklogListResponse>() {
            @Override
            public void onResponse(Call<GetMyBacklogListResponse> call, Response<GetMyBacklogListResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        if(total_backlog == 0){
                            binding.ivAdd.setVisibility(View.GONE);
                            binding.ivSubscription.setVisibility(View.VISIBLE);
                        }else{
                            binding.ivAdd.setVisibility(View.VISIBLE);
                            binding.ivSubscription.setVisibility(View.GONE);
                        }
                        if(response.body().getData().getCount()!= null){
                            if(response.body().getData().getCount().size() != 0){
                                binding.recyclerView.setVisibility(View.VISIBLE);
                                binding.llNoData.setVisibility(View.GONE);
                                setBacklogListData(response.body().getData().getCount());
                            }else{
                                binding.recyclerView.setVisibility(View.GONE);
                                binding.llNoData.setVisibility(View.VISIBLE);
                            }
                        }else{
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.llNoData.setVisibility(View.VISIBLE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyBacklogListResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void setBacklogListData(List<Count> countList) {
        BacklogXboxGamesAdapter adapter = new BacklogXboxGamesAdapter(context, countList, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                if (type.equals("XboxGames")) {
                    startActivity(new Intent(context, XboxGamesActivity.class)
                            .putExtra("category_list_item_id",countList.get(position).getId().toString())
                    .putExtra("category_list_item_name",countList.get(position).getListName()));
                }

            }
        }, new ItemClickView() {
            @Override
            public void onItemClick(Long id, View view,String name) {
                backlog_ID = id;
                Log.d("TAG","backlog_ID>>"+backlog_ID);
                initiatePopupWindow(view,name);
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    public void EditBacklogListAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        EditBacklogListParaRes editBacklogListParaRes = new EditBacklogListParaRes();
        editBacklogListParaRes.setBacklogId(backlog_ID);
        editBacklogListParaRes.setBacklogName(etEditName.getText().toString().trim());

        jsonPlaceHolderApi.EditBacklogListAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(),editBacklogListParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetBacklogListAPI();
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

    public void DeleteBacklogListAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        DeleteBacklogListParaRes deleteBacklogListParaRes = new DeleteBacklogListParaRes();
        deleteBacklogListParaRes.setType(Constants.BACKLOG_LIST);
        deleteBacklogListParaRes.setListId(backlog_ID);

        jsonPlaceHolderApi.DeleteBacklogListAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), deleteBacklogListParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetBacklogListAPI();
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
    //*********************************************************CHECK SUBSCRIPTION****************************************************
    public void NewCheckSubscriptionAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.NewCheckSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<NewCheckSubscriptionResponse>() {
            @Override
            public void onResponse(Call<NewCheckSubscriptionResponse> call, Response<NewCheckSubscriptionResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        planType =  response.body().getData().getSubscription().getType();
                        total_backlog =  response.body().getData().getSubscription().getTotalBacklog();
                        if (Constants.isInternetConnected(context)) {
                            GetBacklogListAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<NewCheckSubscriptionResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}