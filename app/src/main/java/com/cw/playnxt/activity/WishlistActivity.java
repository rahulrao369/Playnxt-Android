package com.cw.playnxt.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickView;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.GameAdapters.BacklogXboxGamesAdapter;
import com.cw.playnxt.adapter.GameAdapters.WishlistXboxGamesAdapter;
import com.cw.playnxt.databinding.ActivityBacklogBinding;
import com.cw.playnxt.databinding.ActivityWishlistBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.AddBacklogList.AddBacklogListParaRes;
import com.cw.playnxt.model.AddWishlist.AddWishlistParaRes;
import com.cw.playnxt.model.DeleteBacklogList.DeleteBacklogListParaRes;
import com.cw.playnxt.model.EditBacklogList.EditBacklogListParaRes;
import com.cw.playnxt.model.EditWishlist.EditWishlistParaRes;
import com.cw.playnxt.model.GetBacklogList.GetMyBacklogListResponse;
import com.cw.playnxt.model.GetWishlist.Count;
import com.cw.playnxt.model.GetWishlist.GetMyWishlistResponse;
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

public class WishlistActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ActivityWishlistBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    LayoutInflater mInflater;
    private PopupWindow mDropdown = null;
    EditText etName,etEditName;
    LinearLayout btnCreateList,btnEditList;
    Long wishlist_ID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishlistBinding.inflate(getLayoutInflater());
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
        context = WishlistActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.Wishlist);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        if (Constants.isInternetConnected(context)) {
            GetWishlistAPI();
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
                showBottomSheetAddNewWishlistDialog();
                break;
        }
    }

    private void showBottomSheetAddNewWishlistDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_new_wishlist);
        etName = bottomSheetDialog.findViewById(R.id.etName);
        btnCreateList = bottomSheetDialog.findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etName.getText().toString().equals("")){
                    bottomSheetDialog.dismiss();
                    if (Constants.isInternetConnected(context)) {
                        AddWishlistAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Please enter the name for your wishlist", Toast.LENGTH_SHORT).show();
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
                    showBottomSheetEditWishlistDialog(name);
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

    private void showBottomSheetEditWishlistDialog(String name) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bootom_sheet_edit_backloglist);
        etEditName = bottomSheetDialog.findViewById(R.id.etEditName);
        btnEditList = bottomSheetDialog.findViewById(R.id.btnEditList);
        etEditName.setText(name);
        btnEditList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etEditName.getText().toString().equals("")){
                    bottomSheetDialog.dismiss();
                    if(wishlist_ID != null){
                        if (Constants.isInternetConnected(context)) {
                            EditWishlistAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(context, "Please enter the name for your backlog list", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bottomSheetDialog.show();
    }

    public void EditWishlistAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        EditWishlistParaRes editWishlistParaRes= new EditWishlistParaRes();
        editWishlistParaRes.setWishlistId(wishlist_ID);
        editWishlistParaRes.setWishlistName(etEditName.getText().toString().trim());

        jsonPlaceHolderApi.EditWishlistAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(),editWishlistParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetWishlistAPI();
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
    private void openDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isInternetConnected(context)) {
                    DeleteWishListAPI();
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

    public void AddWishlistAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        AddWishlistParaRes addWishlistParaRes = new AddWishlistParaRes();
        addWishlistParaRes.setName(etName.getText().toString().trim());

        jsonPlaceHolderApi.AddWishlistAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), addWishlistParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetWishlistAPI();
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

    public void GetWishlistAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.GetWishlistAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyWishlistResponse>() {
            @Override
            public void onResponse(Call<GetMyWishlistResponse> call, Response<GetMyWishlistResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        if(response.body().getData().getCount()!= null){
                            if(response.body().getData().getCount().size() != 0){
                                binding.recyclerView.setVisibility(View.VISIBLE);
                                binding.llNoData.setVisibility(View.GONE);
                                setWishlistListData(response.body().getData().getCount());
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
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyWishlistResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void setWishlistListData(List<Count> countlist) {
        WishlistXboxGamesAdapter adapter = new WishlistXboxGamesAdapter(context, countlist, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                startActivity(new Intent(context, WishlistXboxGameActivity.class)
                        .putExtra("category_list_item_id",countlist.get(position).getId().toString())
                        .putExtra("category_list_item_name",countlist.get(position).getListName()));
            }
        }, new ItemClickView() {
            @Override
            public void onItemClick(Long id, View view,String name) {
                wishlist_ID = id;
                initiatePopupWindow(view,name);
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }
    public void DeleteWishListAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        DeleteBacklogListParaRes deleteBacklogListParaRes = new DeleteBacklogListParaRes();
        deleteBacklogListParaRes.setType(Constants.WISHLIST);
        deleteBacklogListParaRes.setListId(wishlist_ID);

        jsonPlaceHolderApi.DeleteBacklogListAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), deleteBacklogListParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetWishlistAPI();
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