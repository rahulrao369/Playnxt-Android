package com.cw.playnxt.activity;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;
import static com.cw.playnxt.utils.Constants.CATEGORY_BACKLOG;
import static com.cw.playnxt.utils.Constants.CATEGORY_WISHLIST;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickGameNote;
import com.cw.playnxt.Interface.ItemClickId;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.GameAdapters.BottomSheetCurrentStatusListAdapter;
import com.cw.playnxt.adapter.GameAdapters.XboxGamesInfoAdapter;
import com.cw.playnxt.adapter.SearchAdapters.GetCategoryNameListAdapter;
import com.cw.playnxt.adapter.SearchAdapters.GetCategoryNameWishListAdapter;
import com.cw.playnxt.databinding.ActivityMainGameInfoBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.AddBacklogList.AddBacklogListParaRes;
import com.cw.playnxt.model.AddFriendGame.AddFriendGameParaRes;
import com.cw.playnxt.model.AddFriendGame.AddFriendGameResponse;
import com.cw.playnxt.model.AddGameNote.AddGameNoteParaRes;
import com.cw.playnxt.model.AddGameStatus.AddGameStatusParaRes;
import com.cw.playnxt.model.AddWishlist.AddWishlistParaRes;
import com.cw.playnxt.model.CheckPlan.CheckPlanResponse;
import com.cw.playnxt.model.CheckSubscriptionFinal.CheckSubscriptionFinalResponse;
import com.cw.playnxt.model.DeleteGame.DeleteGameParaRes;
import com.cw.playnxt.model.DeleteGameNote.DeleteGameNoteParaRes;
import com.cw.playnxt.model.EditGameNote.EditGameNoteParaRes;
import com.cw.playnxt.model.GameInformation.GetGameInformationParaRes;
import com.cw.playnxt.model.GameInformation.GetGameInformationResponse;
import com.cw.playnxt.model.GetCategoryListName.Backlog;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryBacklogListNameParaRes;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryBacklogListNameResponse;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryWishListNameParaRes;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryWishListNameResponse;
import com.cw.playnxt.model.GetCategoryListName.Wishlist;
import com.cw.playnxt.model.GetGameNote.Capsul;
import com.cw.playnxt.model.GetGameNote.GetGameNoteParaRes;
import com.cw.playnxt.model.GetGameNote.GetGameNoteResponse;
import com.cw.playnxt.model.NewCheckSubscription.NewCheckSubscriptionResponse;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainGameInfoActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    RecyclerView rvBottomSheet;
    LayoutInflater mInflaterHeader;
    EditText etNote;
    LinearLayout btnSave;
    String selected_status = Constants.CURRENTLY_PLAYING;
    TextView tvBtn;
    String selected_game_status = "";
    String game_id, game_view;
    private ActivityMainGameInfoBinding binding;
    private HeaderLayoutBinding headerBinding;
    private PopupWindow mDropdownHeader = null;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    String category_type = "";
    TextView tvCreateNewList;
    ImageView ivAddNewList;
    RecyclerView recyclerView;
    LinearLayout btnAdd,llSelectAnyList,llCreateNewList;
    Long category_list_item_id;
    String category_list_item_name = " ";
    String gameId = "" ;
    Double rating ;
    EditText etName,etWishlistName;
    LinearLayout btnCreateList,btnCreateWishlist;
    String subscribed = "";
    int free_backlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainGameInfoBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = MainGameInfoActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.GameInfo);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);


        try {
            Intent intent = getIntent();
            if (intent != null) {
                game_id = intent.getStringExtra("game_id");
                game_view = intent.getStringExtra("key");

                if (game_view.equals(Constants.USER_GAME_VIEW)) {
                    binding.btnAddNewNote.setVisibility(View.GONE);
                    binding.llFriendsGameInfo.setVisibility(View.VISIBLE);
                    binding.btnAddToList.setVisibility(View.VISIBLE);
                    binding.llMyGameInfo.setVisibility(View.GONE);
                    headerBinding.btnEdit.setVisibility(View.GONE);

                } else {
                    binding.btnAddNewNote.setVisibility(View.VISIBLE);
                    binding.llFriendsGameInfo.setVisibility(View.GONE);
                    binding.btnAddToList.setVisibility(View.GONE);
                    binding.llMyGameInfo.setVisibility(View.VISIBLE);
                    headerBinding.btnEdit.setVisibility(View.VISIBLE);

                }
                Log.d("TAG", "game_id>>" + game_id);
                if (Constants.isInternetConnected(context)) {
                    NewCheckSubscriptionAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.btnAddNewNote.setOnClickListener(this);
        binding.btnEditCurrentStatus.setOnClickListener(this);
        headerBinding.btnEdit.setOnClickListener(this);
        binding.btnAddToBacklog.setOnClickListener(this);
        binding.btnAddToWishList.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnEdit:
                initiatePopupWindowHeader();
                break;

            case R.id.btnAddNewNote:
                showBottomSheetAddNoteDialog();
                break;

            case R.id.btnEditCurrentStatus:
                showBottomSheetEditCurrentStatusDialog(selected_game_status);
                break;

            case R.id.btnAddToBacklog:
                category_type = CATEGORY_BACKLOG;
                Log.d("TAG", "category_type>>" + category_type);

//                if(free_backlog == 1){
//                    if (Constants.isInternetConnected(context)) {
//                        GetCategoryBacklogListNameAPI(category_type);
//                    } else {
//                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                        if(subscribed.equals(Constants.YES)){
//                            if (Constants.isInternetConnected(context)) {
//                                GetCategoryBacklogListNameAPI(category_type);
//                            } else {
//                                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
//                            }
//                        }else{
//                            startActivity(new Intent(context, SubscriptionActivityFinal.class));
//                        }
//
//                }
                GetCategoryBacklogListNameAPI(category_type);

                break;

            case R.id.btnAddToWishList:
                category_type = CATEGORY_WISHLIST;
                Log.d("TAG", "category_type>>" + category_type);
                if(subscribed.equals(Constants.YES)){
                    if (Constants.isInternetConnected(context)) {
                        GetCategoryWishListNameAPI(category_type);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    startActivity(new Intent(context, SubscriptionActivityFinal.class));
                }
                break;

        }
    }

    private void showBottomSheetAddNoteDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_new_note);
        tvBtn = bottomSheetDialog.findViewById(R.id.tvBtn);
        etNote = bottomSheetDialog.findViewById(R.id.etNote);
        btnSave = bottomSheetDialog.findViewById(R.id.btnSave);
        tvBtn.setText("Save");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNote.getText().toString().equals("")) {
                    if (game_id != null) {
                        bottomSheetDialog.dismiss();
                        if (Constants.isInternetConnected(context)) {
                            AddGameNoteAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Game id is null", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Please enter the note", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bottomSheetDialog.show();
    }

    private void showBottomSheetEditCurrentStatusDialog(String selected_game_status) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_edit_current_status);

        rvBottomSheet = bottomSheetDialog.findViewById(R.id.rvBottomSheet);

        BottomSheetCurrentStatusListDataSet(rvBottomSheet, bottomSheetDialog, selected_game_status);
        bottomSheetDialog.show();
    }

    private void BottomSheetCurrentStatusListDataSet(RecyclerView rvBottomSheet, BottomSheetDialog bottomSheetDialog, String selected_game_status) {
        List<GameModel> list = new ArrayList<>();
        list.add(new GameModel(R.drawable.m1, Constants.CURRENTLY_PLAYING));
        list.add(new GameModel(R.drawable.m1, Constants.ON_THE_SHELF));
        list.add(new GameModel(R.drawable.m1, Constants.ROLLED_CREDIT));
        list.add(new GameModel(R.drawable.m1, Constants.HUNDRED_PERCENT_COMPLETED));
        list.add(new GameModel(R.drawable.m1, Constants.TAKING_BREAK));

        BottomSheetCurrentStatusListAdapter adapter = new BottomSheetCurrentStatusListAdapter(context, list, selected_game_status, new ItemClick() {
            @Override
            public void onItemClick(int position, String type) {
                Log.d("TAG", "type>>" + type);
                if (Constants.isInternetConnected(context)) {
                    selected_status = type;
                    AddGameStatusAPI();
                    bottomSheetDialog.dismiss();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvBottomSheet.setHasFixedSize(true);
        rvBottomSheet.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvBottomSheet.setAdapter(adapter);
    }

    private PopupWindow initiatePopupWindowHeader() {
        try {
            mInflaterHeader = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflaterHeader.inflate(R.layout.custom_popup_header, null);

            final TextView menu_delete = (TextView) layout.findViewById(R.id.menu_delete);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdownHeader = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT, true);
            Drawable background = getResources().getDrawable(android.R.drawable.alert_light_frame);

            mDropdownHeader.setBackgroundDrawable(background);
            mDropdownHeader.showAsDropDown(headerBinding.btnEdit, 5, 5);

            menu_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDropdownHeader.dismiss();
                    openDeleteDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdownHeader;
    }

    private void openDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isInternetConnected(context)) {
                    DeleteGameAPI(Long.valueOf(game_id));
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

    //************************************DELETE GAME API*********************************************
    public void DeleteGameAPI(Long game_id) {
        Customprogress.showPopupProgressSpinner(context, true);
        DeleteGameParaRes deleteGameParaRes = new DeleteGameParaRes();
        deleteGameParaRes.setGameId(game_id);

        jsonPlaceHolderApi.DeleteGameAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), deleteGameParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
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

    //***********************************ADD GAME NOTE************************************************

    public void AddGameNoteAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        AddGameNoteParaRes addGameNoteParaRes = new AddGameNoteParaRes();
        addGameNoteParaRes.setGameId(Long.valueOf(game_id));
        addGameNoteParaRes.setNote(etNote.getText().toString().trim());

        jsonPlaceHolderApi.AddGameNoteAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), addGameNoteParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetGameNoteAPI();
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

    public void GetGameNoteAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        GetGameNoteParaRes getGameNoteParaRes = new GetGameNoteParaRes();
        getGameNoteParaRes.setGameId(Long.valueOf(game_id));
        jsonPlaceHolderApi.GetGameNoteAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), getGameNoteParaRes).enqueue(new Callback<GetGameNoteResponse>() {
            @Override
            public void onResponse(Call<GetGameNoteResponse> call, Response<GetGameNoteResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        if (response.body().getData().getCapsul() != null) {
                            if (response.body().getData().getCapsul().size() != 0) {
                                binding.recyclerView.setVisibility(View.VISIBLE);
                                getGameNoteDataSet(response.body().getData().getCapsul());
                            } else {
                                binding.recyclerView.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetGameNoteResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void getGameNoteDataSet(List<Capsul> capsulList) {
        XboxGamesInfoAdapter adapter = new XboxGamesInfoAdapter(context, capsulList, new ItemClickGameNote() {
            @Override
            public void onItemClick(int position, Long game_id, Long note_id, String type) {
                if (type.equals("Edit")) {
                    showBottomSheetEditGameNoteDialog(game_id, note_id);
                } else if (type.equals("Delete")) {
                    openGameNoteDeleteDialog(game_id, note_id);
                }
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    //*************************************EDIT GAME NOTE API************************************

    private void showBottomSheetEditGameNoteDialog(Long game_id, Long note_id) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_new_note);
        tvBtn = bottomSheetDialog.findViewById(R.id.tvBtn);
        etNote = bottomSheetDialog.findViewById(R.id.etNote);
        btnSave = bottomSheetDialog.findViewById(R.id.btnSave);
        tvBtn.setText("Edit");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNote.getText().toString().equals("")) {
                    if (game_id != null) {
                        bottomSheetDialog.dismiss();
                        if (Constants.isInternetConnected(context)) {
                            EditGameNoteAPI(game_id, note_id);
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Game id is null", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Please enter the note", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bottomSheetDialog.show();
    }

    public void EditGameNoteAPI(Long game_id, Long note_id) {
        Customprogress.showPopupProgressSpinner(context, true);
        EditGameNoteParaRes editGameNoteParaRes = new EditGameNoteParaRes();
        editGameNoteParaRes.setGameId(game_id);
        editGameNoteParaRes.setNoteId(note_id);
        editGameNoteParaRes.setNewnote(etNote.getText().toString().trim());

        jsonPlaceHolderApi.EditGameNoteAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), editGameNoteParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetGameNoteAPI();
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

    //*************************************DELETE GAME NOTE API************************************

    private void openGameNoteDeleteDialog(Long game_id, Long note_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isInternetConnected(context)) {
                    DeleteGameNoteAPI(game_id, note_id);
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

    public void DeleteGameNoteAPI(Long game_id, Long note_id) {
        Customprogress.showPopupProgressSpinner(context, true);
        DeleteGameNoteParaRes deleteGameNoteParaRes = new DeleteGameNoteParaRes();
        deleteGameNoteParaRes.setGameId(game_id);
        deleteGameNoteParaRes.setNoteId(note_id);

        jsonPlaceHolderApi.DeleteGameNoteAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), deleteGameNoteParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetGameNoteAPI();
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

    //******************************************Add Game Status***************************

    public void AddGameStatusAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        AddGameStatusParaRes addGameStatusParaRes = new AddGameStatusParaRes();
        addGameStatusParaRes.setGameId(Long.valueOf(game_id));
        addGameStatusParaRes.setStatus(selected_status);

        jsonPlaceHolderApi.AddGameStatusAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken(), addGameStatusParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (Constants.isInternetConnected(context)) {
                            GetGameInformationAPI(game_view);
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

    //***************************************GET GAME INFO API******************************
    public void GetGameInformationAPI(String game_view) {
        Customprogress.showPopupProgressSpinner(context, true);
        GetGameInformationParaRes getGameInformationParaRes = new GetGameInformationParaRes();
        getGameInformationParaRes.setGameId(Long.valueOf(game_id));
        getGameInformationParaRes.setGame_view(game_view);
        jsonPlaceHolderApi.GetGameInformationAPI("application/json", "Bearer " + mySharedPref.getSavedAccessToken(), getGameInformationParaRes).enqueue(new Callback<GetGameInformationResponse>() {
            @Override
            public void onResponse(Call<GetGameInformationResponse> call, Response<GetGameInformationResponse> response) {
                if (response.isSuccessful()) {
                    Customprogress.showPopupProgressSpinner(context, false);
                    Boolean status = response.body().getStatus();
                    if (status) {
                        if (response.body().getData().getCapsul() != null) {
                            if (Constants.isInternetConnected(context)) {
                                GetGameNoteAPI();
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            }
                            gameId = String.valueOf(response.body().getData().getCapsul().getGameId());
                            rating =response.body().getData().getCapsul().getRating();
                            GameInfoActivityDataSetAfterStatusChange(response.body().getData().getCapsul());
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetGameInformationResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }
    
    private void GameInfoActivityDataSetAfterStatusChange(com.cw.playnxt.model.GameInformation.Capsul capsulData) {
      String typeImage = capsulData.getImage_type();
        Log.d("TAG","typeImage>>"+typeImage);
        if(typeImage.equals("thirdparty")){
            Picasso.get().load("https:"+capsulData.getGImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(binding.gameImg);

        }else{
            Picasso.get().load(Allurls.IMAGEURL+capsulData.getGImage()).error(R.drawable.app_logo).placeholder(R.drawable.app_logo).into(binding.gameImg);
        }
        String gameName = capsulData.getGTitle().substring(0, 1).toUpperCase() +capsulData.getGTitle().substring(1).toLowerCase();
        binding.tvGameTitle.setText(gameName);
        binding.tvCurrentStatus.setText(capsulData.getGameStatus());
        binding.tvRating.setText(String.valueOf(capsulData.getRating()));
        binding.ratingBar.setRating(Float.parseFloat(String.valueOf(capsulData.getRating())));
        selected_game_status = capsulData.getGameStatus();
        Log.d("TAG", "selected_game_status>>" + selected_game_status);
        if (!capsulData.getDescription().equals("")) {
            binding.tvGameDescription.setVisibility(View.VISIBLE);
            binding.tvGameDescription.setText(HtmlCompat.fromHtml(capsulData.getDescription(), 0));
        } else {
            binding.tvGameDescription.setVisibility(View.GONE);
        }
        if (!capsulData.getPlatform().equals("")) {
            binding.tvPlatform.setVisibility(View.VISIBLE);
            binding.tvGamePlatform.setText(capsulData.getPlatform());
        } else {
            binding.tvPlatform.setVisibility(View.GONE);
        }
        if (!capsulData.getGenre().equals("")) {
            binding.tvGenre.setVisibility(View.VISIBLE);
            binding.tvGameGenre.setText(capsulData.getGenre());
        } else {
            binding.tvGenre.setVisibility(View.GONE);
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
                    showBottomSheetCreateNewBacklogListDialog();
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
                if (category_list_item_id != null) {
                    bottomSheetDialog.dismiss();
                    if (Constants.isInternetConnected(context)) {
                        AddFriendGameAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please select any list type", Toast.LENGTH_SHORT).show();
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
                if (category_list_item_id != null) {
                    bottomSheetDialog.dismiss();
                    if (Constants.isInternetConnected(context)) {
                        AddFriendGameAPI();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please select any list type", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bottomSheetDialog.show();
    }

    public void AddFriendGameAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        AddFriendGameParaRes addFriendGameParaRes = new AddFriendGameParaRes();
        addFriendGameParaRes.setGameId(Long.valueOf(gameId));
        addFriendGameParaRes.setCategory(category_type);
        addFriendGameParaRes.setListId(category_list_item_id);
        addFriendGameParaRes.setListName(category_list_item_name);
        addFriendGameParaRes.setRate(Double.valueOf(rating));

        jsonPlaceHolderApi.AddFriendGameAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(), addFriendGameParaRes).enqueue(new Callback<AddFriendGameResponse>() {
            @Override
            public void onResponse(Call<AddFriendGameResponse> call, Response<AddFriendGameResponse> response) {
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
            public void onFailure(Call<AddFriendGameResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
                            if (Constants.isInternetConnected(context)) {
                                GetCategoryBacklogListNameAPI(category_type);
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
                            if (Constants.isInternetConnected(context)) {
                                GetCategoryWishListNameAPI(category_type);
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
        jsonPlaceHolderApi.NewCheckSubscriptionAPI(Constants.CONTENT_TYPE, "Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<CheckSubscriptionFinalResponse>() {
            @Override
            public void onResponse(Call<CheckSubscriptionFinalResponse> call, Response<CheckSubscriptionFinalResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status) {
                        if (response.body().getData().getSubscribed().equals(Constants.YES)) {
                            binding.btnAdsShow.setVisibility(View.GONE);
                        }else{
                            binding.btnAdsShow.setVisibility(View.VISIBLE);
                        }

                        subscribed = response.body().getData().getSubscribed();
                        Log.d("TAG","subscribed>>**"+subscribed);
                        //planType =  response.body().getData().getSubscription().getType();
                        free_backlog =  response.body().getData().getFree_backlog();
                        Log.d("TAG","free_backlog>>**"+free_backlog);

                        if (Constants.isInternetConnected(context)) {
                            GetGameInformationAPI(game_view);
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<CheckSubscriptionFinalResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}