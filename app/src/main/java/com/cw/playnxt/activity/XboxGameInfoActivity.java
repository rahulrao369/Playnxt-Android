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
import androidx.recyclerview.widget.RecyclerView;

import com.cw.playnxt.Interface.ItemClick;
import com.cw.playnxt.Interface.ItemClickGameNote;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.GameAdapters.BottomSheetCurrentStatusListAdapter;
import com.cw.playnxt.adapter.GameAdapters.XboxGamesInfoAdapter;
import com.cw.playnxt.databinding.ActivityXboxGameInfoBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.AddGameNote.AddGameNoteParaRes;
import com.cw.playnxt.model.AddGameStatus.AddGameStatusParaRes;
import com.cw.playnxt.model.DeleteGame.DeleteGameParaRes;
import com.cw.playnxt.model.DeleteGameNote.DeleteGameNoteParaRes;
import com.cw.playnxt.model.EditGameNote.EditGameNoteParaRes;
import com.cw.playnxt.model.GameInformation.GetGameInformationParaRes;
import com.cw.playnxt.model.GameInformation.GetGameInformationResponse;
import com.cw.playnxt.model.GetGameNote.Capsul;
import com.cw.playnxt.model.GetGameNote.GetGameNoteParaRes;
import com.cw.playnxt.model.GetGameNote.GetGameNoteResponse;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.StaticModel.GameModel;
import com.cw.playnxt.model.ViewMyBacklogGame.Game;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XboxGameInfoActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    RecyclerView rvBottomSheet;
    LayoutInflater mInflaterHeader;
    Game gameData;
    EditText etNote;
    LinearLayout btnSave;
    String selected_status = Constants.CURRENTLY_PLAYING;
    String category_list_item_id = "";
    TextView tvBtn;
    String selected_game_status = "";
    private ActivityXboxGameInfoBinding binding;
    private HeaderLayoutBinding headerBinding;
    private PopupWindow mDropdownHeader = null;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityXboxGameInfoBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();
    }

    public void init() {
        context = XboxGameInfoActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.GameInfo);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.VISIBLE);
        headerBinding.btnEdit.setVisibility(View.VISIBLE);

        try {
            Intent intent = getIntent();
            if (intent != null) {
                gameData = (Game) intent.getSerializableExtra("gameData");
                category_list_item_id = intent.getStringExtra("category_list_item_id");
                Log.d("TAG", "gameData.getId()>>" + gameData.getId());
                Log.d("TAG", "category_list_item_id>>" + category_list_item_id);
                if (Constants.isInternetConnected(context)) {
                    GetGameInformationAPI();
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
                    if (gameData.getId() != null) {
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

        BottomSheetCurrentStatusListDataSet(rvBottomSheet, bottomSheetDialog,selected_game_status);
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
                Log.d("TAG","type>>"+type);
                if (Constants.isInternetConnected(context)) {
              /*      if (position == 0) {
                        selected_status = Constants.CURRENTLY_PLAYING;
                    } else if (position == 1) {
                        selected_status = Constants.ON_THE_SHELF;
                    } else if (position == 2) {
                        selected_status = Constants.ROLLED_CREDIT;
                    } else if (position == 3) {
                        selected_status = Constants.HUNDRED_PERCENT_COMPLETED;
                    } else if (position == 4) {
                        selected_status = Constants.TAKING_BREAK;
                    }*/
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
                    DeleteGameAPI(gameData.getId());
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
        addGameNoteParaRes.setGameId(gameData.getId());
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
        getGameNoteParaRes.setGameId(gameData.getId());
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
                    if (gameData.getId() != null) {
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
        addGameStatusParaRes.setGameId(gameData.getId());
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
                            GetGameInformationAPI();
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
    public void GetGameInformationAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        GetGameInformationParaRes getGameInformationParaRes = new GetGameInformationParaRes();
        getGameInformationParaRes.setGameId(gameData.getId());
        getGameInformationParaRes.setGame_view(Constants.SELF_GAME_VIEW);
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
        Picasso.get().load(Allurls.IMAGEURL + capsulData.getGImage()).placeholder(R.drawable.progress_animation).error(R.drawable.progress_animation).into(binding.gameImg);
        String gameName = capsulData.getGTitle().substring(0, 1).toUpperCase() +capsulData.getGTitle().substring(1).toLowerCase();
        binding.tvGameTitle.setText(gameName);
        binding.tvCurrentStatus.setText(capsulData.getGameStatus());
        selected_game_status = capsulData.getGameStatus();
        Log.d("TAG", "selected_game_status>>" + selected_game_status);
    }
}