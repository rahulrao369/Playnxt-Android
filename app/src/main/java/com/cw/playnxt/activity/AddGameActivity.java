package com.cw.playnxt.activity;

import static com.cw.playnxt.utils.Constants.CATEGORY_BACKLOG;
import static com.cw.playnxt.utils.Constants.CATEGORY_WISHLIST;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SearchAdapters.GetCategoryNameListAdapter;
import com.cw.playnxt.adapter.SearchAdapters.GetCategoryNameWishListAdapter;
import com.cw.playnxt.databinding.ActivityAddGameBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.AddBacklogList.AddBacklogListParaRes;
import com.cw.playnxt.model.AddWishlist.AddWishlistParaRes;
import com.cw.playnxt.model.CheckPlan.CheckPlanResponse;
import com.cw.playnxt.model.GetCategoryListName.Backlog;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryBacklogListNameParaRes;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryBacklogListNameResponse;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryWishListNameParaRes;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryWishListNameResponse;
import com.cw.playnxt.model.GetCategoryListName.Wishlist;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.cw.playnxt.utils.Filepath;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGameActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    String selectedPath = "";
    TextView tvCreateNewList;
    ImageView ivAddNewList;
    RecyclerView recyclerView;
    LinearLayout btnAdd,llSelectAnyList,llCreateNewList;
    String category_type = "";
    Long category_list_item_id;
    String category_list_item_name = " ";
    private ActivityAddGameBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String gameRating= "";
    EditText etName,etWishlistName;
    LinearLayout btnCreateList,btnCreateWishlist;
    String activePlan = "";
    String planType = "";
    String backlogRemain = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddGameBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = AddGameActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.AddGame);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        binding.tvRating.setText(String.valueOf(binding.ratingBar.getRating()));
        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                gameRating = String.valueOf(rating);
                binding.tvRating.setText(gameRating);
            }
        });
        if (Constants.isInternetConnected(context)) {
            CheckPlanAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.tvUploadGameImg.setOnClickListener(this);
        binding.btnAddToBacklog.setOnClickListener(this);
        binding.btnAddToWishList.setOnClickListener(this);
        binding.llSelectImage.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.tvUploadGameImg:
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
                break;

            case R.id.llSelectImage:
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
                break;

            case R.id.btnPayNow:
                startActivity(new Intent(context, ConfirmationActivity.class));
                break;

            case R.id.btnAddToBacklog:
                category_type = CATEGORY_BACKLOG;
                Log.d("TAG", "category_type>>" + category_type);

                if (isValidate()) {
                    if (Constants.isInternetConnected(context)) {
                        GetCategoryBacklogListNameAPI(category_type);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.btnAddToWishList:
                category_type = CATEGORY_WISHLIST;
                Log.d("TAG", "category_type>>" + category_type);

                if(planType.equals(Constants.PLAN_TYPE_PAID)){
                    if (isValidate()) {
                        if (Constants.isInternetConnected(context)) {
                            GetCategoryWishListNameAPI(category_type);
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    startActivity(new Intent(context, SubscriptionActivity.class));
                }


                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                binding.ivGame.setVisibility(View.VISIBLE);
                binding.ivGameIcon.setVisibility(View.GONE);
                binding.ivGame.setImageURI(resultUri);
                selectedPath = Filepath.getPathFromUri(context, resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    //******************************Category List Name*************************
    public void GetCategoryBacklogListNameAPI(String category_type) {
        Customprogress.showPopupProgressSpinner(context, true);
        GetCategoryBacklogListNameParaRes getCategoryBacklogListNameParaRes = new GetCategoryBacklogListNameParaRes();
        getCategoryBacklogListNameParaRes.setCategory(category_type);
        jsonPlaceHolderApi.GetCategoryBacklogListNameAPI("application/json", "Bearer " + mySharedPref.getSavedAccessToken(), getCategoryBacklogListNameParaRes).enqueue(new Callback<GetCategoryBacklogListNameResponse>() {
            @Override
            public void onResponse(Call<GetCategoryBacklogListNameResponse> call, Response<GetCategoryBacklogListNameResponse> response) {
                if (response.isSuccessful()) {
                    Customprogress.showPopupProgressSpinner(context, false);
                    Boolean status = response.body().getStatus();
                    if (status) {
                        if (response.body().getData() != null) {
                            backlogRemain = response.body().getData().getBacklog_remain();
                            showBottomSheetCategoryBacklogListDialog(response.body().getData().getBacklog());
                        }

                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCategoryBacklogListNameResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void showBottomSheetCategoryBacklogListDialog(List<Backlog> backlogList) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_get_category_list);
        tvCreateNewList = bottomSheetDialog.findViewById(R.id.tvCreateNewList);
        ivAddNewList = bottomSheetDialog.findViewById(R.id.ivAddNewList);
        recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);
        btnAdd = bottomSheetDialog.findViewById(R.id.btnAdd);
        llSelectAnyList = bottomSheetDialog.findViewById(R.id.llSelectAnyList);
        llCreateNewList = bottomSheetDialog.findViewById(R.id.llCreateNewList);

        if(backlogList.size() != 0){
            llSelectAnyList.setVisibility(View.VISIBLE);
            llCreateNewList.setVisibility(View.GONE);
        }else{
            llSelectAnyList.setVisibility(View.GONE);
            llCreateNewList.setVisibility(View.VISIBLE);
            tvCreateNewList.setText("Create New Backlog List");

            ivAddNewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //condition
                    if(backlogRemain.equals("0")){
                        startActivity(new Intent(context, SubscriptionActivity.class));
                    }else{
                        showBottomSheetCreateNewBacklogListDialog();
                    }
                    bottomSheetDialog.dismiss();
                }
            });
        }

        GetCategoryNameListAdapter adapter = new GetCategoryNameListAdapter(context, backlogList, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long id,String type) {
                category_list_item_id = id;
                category_list_item_name = backlogList.get(position).getName();

                Log.d("TAG", "category_list_item_id>>" + category_list_item_id);
                Log.d("TAG", "category_list_item_name>>" + category_list_item_name);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(backlogList.size() != 0){
                    if (category_list_item_id != null) {
                        bottomSheetDialog.dismiss();
                        if (Constants.isInternetConnected(context)) {
                            AddGameAPI(selectedPath);
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Please select any list type", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Please create any backlog list from the add button", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bottomSheetDialog.show();
    }

    public void GetCategoryWishListNameAPI(String category_type) {
        Customprogress.showPopupProgressSpinner(context, true);
        GetCategoryWishListNameParaRes getCategoryWishListNameParaRes = new GetCategoryWishListNameParaRes();
        getCategoryWishListNameParaRes.setCategory(category_type);
        jsonPlaceHolderApi.GetCategoryWishListNameAPI("application/json", "Bearer " + mySharedPref.getSavedAccessToken(), getCategoryWishListNameParaRes).enqueue(new Callback<GetCategoryWishListNameResponse>() {
            @Override
            public void onResponse(Call<GetCategoryWishListNameResponse> call, Response<GetCategoryWishListNameResponse> response) {
                if (response.isSuccessful()) {
                    Customprogress.showPopupProgressSpinner(context, false);
                    Boolean status = response.body().getStatus();
                    if (status) {
                        if (response.body().getData() != null) {
                            showBottomSheetCategoryWishListDialog(response.body().getData().getWishlist());
                        }

                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCategoryWishListNameResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void showBottomSheetCategoryWishListDialog(List<Wishlist> wishlist) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_get_category_list);
        tvCreateNewList = bottomSheetDialog.findViewById(R.id.tvCreateNewList);
        recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);
        btnAdd = bottomSheetDialog.findViewById(R.id.btnAdd);
        llSelectAnyList = bottomSheetDialog.findViewById(R.id.llSelectAnyList);
        llCreateNewList = bottomSheetDialog.findViewById(R.id.llCreateNewList);
        ivAddNewList = bottomSheetDialog.findViewById(R.id.ivAddNewList);

        if(wishlist.size() != 0){
            llSelectAnyList.setVisibility(View.VISIBLE);
            llCreateNewList.setVisibility(View.GONE);
        }else{
            llSelectAnyList.setVisibility(View.GONE);
            llCreateNewList.setVisibility(View.VISIBLE);
            tvCreateNewList.setText("Create New Wishlist");
            ivAddNewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBottomSheetCreateNewWishlistDialog();
                    bottomSheetDialog.dismiss();
                }
            });
        }

        GetCategoryNameWishListAdapter adapter = new GetCategoryNameWishListAdapter(context, wishlist, new ItemClickId() {
            @Override
            public void onItemClick(int position, Long id,String type) {
                category_list_item_id = id;
                category_list_item_name = wishlist.get(position).getName();

                Log.d("TAG", "category_list_item_id>>" + category_list_item_id);
                Log.d("TAG", "category_list_item_name>>" + category_list_item_name);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wishlist.size() != 0){
                    if (category_list_item_id != null) {
                        bottomSheetDialog.dismiss();
                        if (Constants.isInternetConnected(context)) {
                            AddGameAPI(selectedPath);
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Please select any list type", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Please create any  Wishlist from the add button", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bottomSheetDialog.show();
    }

    private Boolean isValidate() {
        if (selectedPath.isEmpty()) {
            Toast.makeText(context, "Please select any image", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etGameTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please select any game title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etGamePlatform.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please select any game platform", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etGameDescription.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please select any game description", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etGameGenre.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please select any game genre", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void AddGameAPI(String pathMain) {
        Customprogress.showPopupProgressSpinner(context, true);
        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("title", createRequestBody(binding.etGameTitle.getText().toString().trim()));
        data.put("platform", createRequestBody(binding.etGamePlatform.getText().toString().trim()));
        data.put("description", createRequestBody(binding.etGameDescription.getText().toString().trim()));
        data.put("genre", createRequestBody(binding.etGameGenre.getText().toString().trim()));
        data.put("category", createRequestBody(category_type));
        data.put("list_name", createRequestBody(category_list_item_name));
        data.put("list_id", createRequestBody(String.valueOf(category_list_item_id)));
        data.put("rate", createRequestBody(gameRating));

        MultipartBody.Part image = null;
        if (pathMain != null && !pathMain.equals("")) {
            File file = new File(pathMain);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }
        jsonPlaceHolderApi.AddGameAPI("Bearer " + mySharedPref.getSavedAccessToken(), data, image).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        if (category_type.equals(CATEGORY_BACKLOG)) {
                            startActivity(new Intent(context, BacklogActivity.class));
                            finish();
                        } else if (category_type.equals(CATEGORY_WISHLIST)) {
                            startActivity(new Intent(context, WishlistActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSatusMessage> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), s);
    }

    //***************************************CREATE NEW BACKLOG LIST API*****************************************8888
    private void showBottomSheetCreateNewBacklogListDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_filter);
        etName = bottomSheetDialog.findViewById(R.id.etName);
        btnCreateList = bottomSheetDialog.findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etName.getText().toString().equals("")){
                    bottomSheetDialog.dismiss();
                    if (Constants.isInternetConnected(context)) {
                        AddBacklogListAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Please enter the name for your backlog list", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bottomSheetDialog.show();
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
                      //  Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (isValidate()) {
                            if (Constants.isInternetConnected(context)) {
                                GetCategoryBacklogListNameAPI(category_type);
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            }
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

    //***************************************CREATE NEW WISH LIST API*****************************************8888

    private void showBottomSheetCreateNewWishlistDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_new_wishlist);
        etWishlistName = bottomSheetDialog.findViewById(R.id.etName);
        btnCreateWishlist = bottomSheetDialog.findViewById(R.id.btnCreateList);
        btnCreateWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etWishlistName.getText().toString().equals("")){
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
    public void AddWishlistAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        AddWishlistParaRes addWishlistParaRes = new AddWishlistParaRes();
        addWishlistParaRes.setName(etWishlistName.getText().toString().trim());

        jsonPlaceHolderApi.AddWishlistAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), addWishlistParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                       // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (isValidate()) {
                            if (Constants.isInternetConnected(context)) {
                                GetCategoryWishListNameAPI(category_type);
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            }
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

    //*********************************************************CHECK PLAN****************************************************
    public void CheckPlanAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        jsonPlaceHolderApi.CheckPlanAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckPlanResponse>() {
            @Override
            public void onResponse(Call<CheckPlanResponse> call, Response<CheckPlanResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        activePlan = response.body().getData().getActivePlan();
                        planType =  response.body().getData().getActplan();
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<CheckPlanResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}