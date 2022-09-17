package com.cw.playnxt.activity;

import static com.cw.playnxt.utils.Constants.CATEGORY_BACKLOG;
import static com.cw.playnxt.utils.Constants.CATEGORY_WISHLIST;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.SearchAdapters.FilterGameTitleAdapter;
import com.cw.playnxt.adapter.SearchAdapters.GetCategoryNameListAdapter;
import com.cw.playnxt.adapter.SearchAdapters.GetCategoryNameWishListAdapter;
import com.cw.playnxt.databinding.ActivityAddGameFromBacklogListBinding;
import com.cw.playnxt.databinding.ActivityAddGameFromWishlistBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.GetCategoryListName.Backlog;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryBacklogListNameParaRes;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryBacklogListNameResponse;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryWishListNameParaRes;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryWishListNameResponse;
import com.cw.playnxt.model.GetCategoryListName.Wishlist;
import com.cw.playnxt.model.GetGameByFilter.GetGameByFilterParaRes;
import com.cw.playnxt.model.GetGameByFilter.GetGameByFilterResponse;
import com.cw.playnxt.model.GetGameByFilter.Newdatum;
import com.cw.playnxt.model.GetPlatformGenre.GetPlatformGenreResponse;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.cw.playnxt.utils.Filepath;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGameFromWishlistActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    String selectedPath = "";
    String path = "";
    RecyclerView recyclerView;
    LinearLayout btnAdd;
    String category_type = "";
    String category_list_item_id;
    String category_list_item_name = " ";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ActivityAddGameFromWishlistBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    private String userChoosenTask;
    String gameRating= "";

    List<Newdatum> gameTitleSearchList = new ArrayList<Newdatum>();
    ArrayAdapter platformAdapter;
    ArrayAdapter genreAdapter;
    String platform = "";
    String genre = "";
    List<String> platformStatic = new ArrayList<>();
    List<String> genreStatic = new ArrayList<>();
    String gameType = "";
    String gameId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddGameFromWishlistBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }
    public void init() {
        context = AddGameFromWishlistActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.AddGame);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        if (Constants.isInternetConnected(context)) {
            getPlatformGenreAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

        try {
            Intent intent = getIntent();
            if (intent != null) {
                category_list_item_id = intent.getStringExtra("category_list_item_id");
                category_list_item_name = intent.getStringExtra("category_list_item_name");
                Log.d("TAG", "category_list_item_name>>" + category_list_item_name);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.tvRating.setText(String.valueOf(binding.ratingBar.getRating()));
        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                gameRating = String.valueOf(rating);
                binding.tvRating.setText(gameRating);
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    binding.rvGameTitle.setVisibility(View.GONE);
                } else {
                    if (Constants.isInternetConnected(context)) {
                        getGameByFilterAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
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

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.tvUploadGameImg.setOnClickListener(this);
        binding.btnAddToWishList.setOnClickListener(this);
        binding.llSelectImage.setOnClickListener(this);
        binding.llCross.setOnClickListener(this);
        binding.autoCompleteGameTitle.setOnClickListener(this);
        binding.btnGo.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.llCross:
                binding.llMain.setVisibility(View.VISIBLE);
                binding.llSearch.setVisibility(View.GONE);
                break;

            case R.id.btnGo:
                if(!binding.etSearch.getText().toString().equals("")){
                    binding.autoCompleteGameTitle.setText(binding.etSearch.getText().toString().trim());
                    binding.llMain.setVisibility(View.VISIBLE);
                    binding.llSearch.setVisibility(View.GONE);
                }

                break;

            case R.id.autoCompleteGameTitle:
                binding.etSearch.setText("");
                binding.llMain.setVisibility(View.GONE);
                binding.llSearch.setVisibility(View.VISIBLE);
                break;

            case R.id.tvUploadGameImg:
                selectImage();
                break;

            case R.id.llSelectImage:
                selectImage();
                break;

            case R.id.btnPayNow:
                startActivity(new Intent(context, ConfirmationActivity.class));
                break;

            case R.id.btnAddToWishList:
                category_type = CATEGORY_WISHLIST;
                Log.d("TAG", "category_type>>" + category_type);
                if (isValidate()) {
                    if (Constants.isInternetConnected(context)) {
                        AddGameAPI(selectedPath);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                try {
                    onSelectFromGalleryResultProfile(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResultProfile(data);
            }
        }
    }

    private Boolean isValidate() {
        if (binding.autoCompleteGameTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please select any game title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (platform.equals("")) {
            Toast.makeText(context, "Please select any game platform", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void AddGameAPI(String pathMain) {
        Customprogress.showPopupProgressSpinner(context, true);
        if(!pathMain.equals("")){
            gameType = Constants.MANNUAL;
        }else{
            gameType = Constants.ADMIN_GAME;
        }

        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("title", createRequestBody(binding.autoCompleteGameTitle.getText().toString().trim()));
        data.put("platform", createRequestBody(platform));
        data.put("description", createRequestBody(binding.etGameDescription.getText().toString().trim()));
        data.put("genre", createRequestBody(genre));
        data.put("category", createRequestBody(category_type));
        data.put("list_name", createRequestBody(category_list_item_name));
        data.put("list_id", createRequestBody(String.valueOf(category_list_item_id)));
        data.put("rate", createRequestBody(gameRating));
        data.put("game_type", createRequestBody(gameType));
        data.put("game_id", createRequestBody(gameId));


        Log.d("TAG2", "GameId>>"+gameId);
        Log.d("TAG2", "game_type>>"+gameType);
        Log.d("TAG2", "GameImage>>"+pathMain);
        Log.d("TAG2", "GameTitle>>"+binding.autoCompleteGameTitle.getText().toString().trim());
        Log.d("TAG2", "GameDescription>>"+binding.etGameDescription.getText().toString().trim());
        Log.d("TAG2", "GamePlatform>>"+platform);
        Log.d("TAG2", "GameGenre>>"+genre);
        Log.d("TAG2", "gameRating>>"+gameRating);

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
                            finish();
                        } else if (category_type.equals(CATEGORY_WISHLIST)) {
                            finish();
                        }

                        //putExtra("category_list_item_id",category_list_item_id.toString())
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

    //************************************SELECT IMAGE*********************************************
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Upload Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (ContextCompat.checkSelfPermission(AddGameFromWishlistActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddGameFromWishlistActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                    } else {
                        cameraIntent();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (ContextCompat.checkSelfPermission(AddGameFromWishlistActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddGameFromWishlistActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_FILE);
                    } else {
                        galleryIntent();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        System.out.println("GALLERY OPEN 22");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        try {
            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onSelectFromGalleryResultProfile(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BitmapDrawable d = new BitmapDrawable(bm);
        int left = 0;
        int top = 0;
        int right = 40;
        int bottom = 40;
        Rect r = new Rect(left, top, right, bottom);
        d.setBounds(r);
        Uri tempUri = getImageUri(context, bm);
        System.out.println("data.getData() " + data.getData());
        selectedPath = getRealPathFromURI(tempUri);
        System.out.println("ProfilePicPath  " + selectedPath);
        binding.ivGame.setVisibility(View.VISIBLE);
        binding.ivGameIcon.setVisibility(View.GONE);
        binding.ivGame.setImageURI(tempUri);

    }

    private void onCaptureImageResultProfile(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), "" + System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri tempUri = getImageUri(context, thumbnail);
        selectedPath = getRealPathFromURI(tempUri);
        binding.ivGame.setVisibility(View.VISIBLE);
        binding.ivGameIcon.setVisibility(View.GONE);
        binding.ivGame.setImageURI(tempUri);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "" + System.currentTimeMillis(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void getGameByFilterAPI() {

        GetGameByFilterParaRes getGameByFilterParaRes = new GetGameByFilterParaRes();
        getGameByFilterParaRes.setTitle(binding.etSearch.getText().toString().trim());

        jsonPlaceHolderApi.getGameByFilterAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), getGameByFilterParaRes).enqueue(new Callback<GetGameByFilterResponse>() {
            @Override
            public void onResponse(Call<GetGameByFilterResponse> call, Response<GetGameByFilterResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    if (status) {
                        if (response.body().getData().getNewdata().size() != 0) {
                            binding.rvGameTitle.setVisibility(View.VISIBLE);
                            binding.llNoData.setVisibility(View.GONE);
                            gameTitleSearchList = response.body().getData().getNewdata();
                            SearchGamesTilteListDataSet(gameTitleSearchList);

                        } else {
                            binding.rvGameTitle.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetGameByFilterResponse> call, Throwable t) {
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void SearchGamesTilteListDataSet(List<Newdatum> gameTitleList) {
        FilterGameTitleAdapter adapter = new FilterGameTitleAdapter(context, gameTitleList, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                binding.llMain.setVisibility(View.VISIBLE);
                binding.llSearch.setVisibility(View.GONE);
                binding.rvGameTitle.setVisibility(View.GONE);
                binding.ivGame.setVisibility(View.VISIBLE);
                binding.ivGameIcon.setVisibility(View.GONE);
                gameId =  String.valueOf(gameTitleList.get(position).getId());
                Picasso.get().load(Allurls.IMAGEURL + gameTitleList.get(position).getImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(binding.ivGame);
                binding.autoCompleteGameTitle.setText(gameTitleList.get(position).getTitle());

              /*  List<String> platformList = new ArrayList<>();
                for(int i = 0; i< gameTitleList.get(position).getPlatform().size(); i++){
                    platformList.add(gameTitleList.get(position).getPlatform().get(i));
                }*/
                binding.etGameDescription.setText(gameTitleList.get(position).getDescription().toString().trim());
                platformDataSet(gameTitleList.get(position).getPlatform());
                genreDataSet(gameTitleList.get(position).getGenre());

                Log.d("TAG2", "GameId>>"+gameId);
                Log.d("TAG2", "GameImage>>"+Allurls.IMAGEURL + gameTitleList.get(position).getImage());
                Log.d("TAG2", "GameTitle>>"+gameTitleList.get(position).getTitle());
                Log.d("TAG2", "GameDescription>>"+gameTitleList.get(position).getDescription());
                Log.d("TAG2", "GamePlatform>>"+gameTitleList.get(position).getPlatform());
                Log.d("TAG2", "GameGenre>>"+gameTitleList.get(position).getGenre());
            }
        });
        binding.rvGameTitle.setHasFixedSize(true);
        binding.rvGameTitle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rvGameTitle.setAdapter(adapter);
    }

    private void platformDataSet(List<String> platformList) {
        platformList.add(0, "Game Platform");
        platformAdapter = new ArrayAdapter(this, R.layout.spinner_item1, platformList);
        platformAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item1);
        binding.spGamePlatform.setAdapter(platformAdapter);
        binding.spGamePlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!platformList.get(i).equals("Game Platform")) {
                    platform = platformList.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void genreDataSet(List<String> genreList) {
        genreList.add(0, "Genre");
        genreAdapter = new ArrayAdapter(this, R.layout.spinner_item1, genreList);
        genreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item1);
        binding.spGameGenre.setAdapter(genreAdapter);
        binding.spGameGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!genreList.get(i).equals("Genre")) {
                    genre = genreList.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void getPlatformGenreAPI() {
        jsonPlaceHolderApi.getPlatformGenreAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetPlatformGenreResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPlatformGenreResponse> call, Response<GetPlatformGenreResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        List<String> allPlatformList = new ArrayList<>();
                        List<String> allGenreList = new ArrayList<>();
                        for(int i = 0; i < response.body().getData().getPlatform().size(); i++){
                            allPlatformList.add(response.body().getData().getPlatform().get(i).getName());
                        }
                        for(int i = 0; i < response.body().getData().getGenre().size(); i++){
                            allGenreList.add(response.body().getData().getGenre().get(i).getName());
                        }

                        platformDataSet(allPlatformList);
                        genreDataSet(allGenreList);

                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetPlatformGenreResponse> call, Throwable t) {
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
}