package com.cw.playnxt.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cw.playnxt.Interface.ItemClickGameInfoBacklogList;
import com.cw.playnxt.R;
import com.cw.playnxt.adapter.GameAdapters.XboxGamesAdapter;
import com.cw.playnxt.databinding.ActivityXboxGamesBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.ViewMyBacklogGame.Game;
import com.cw.playnxt.model.ViewMyBacklogGame.ViewMyBacklogGameParaRes;
import com.cw.playnxt.model.ViewMyBacklogGame.ViewMyBacklogGameResponse;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XboxGamesActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    String category_list_item_id = "";
    String category_list_item_name = "";
    private ActivityXboxGamesBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityXboxGamesBinding.inflate(getLayoutInflater());
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
        context = XboxGamesActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.VISIBLE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        try {
            Intent intent = getIntent();
            if (intent != null) {
                category_list_item_id = intent.getStringExtra("category_list_item_id");
                category_list_item_name = intent.getStringExtra("category_list_item_name");
                Log.d("TAG", "category_list_item_id>>" + category_list_item_id);
                headerBinding.tvHeading.setText(category_list_item_name);
                if (Constants.isInternetConnected(context)) {
                    ViewMyBacklogGameAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        headerBinding.btnAdd.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnAdd:
                startActivity(new Intent(context, AddGameFromBacklogListActivity.class)
                        .putExtra("category_list_item_id",category_list_item_id)
                        .putExtra("category_list_item_name", category_list_item_name)
                );
                break;
        }
    }

    public void ViewMyBacklogGameAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        ViewMyBacklogGameParaRes viewMyBacklogGameParaRes = new ViewMyBacklogGameParaRes();
        viewMyBacklogGameParaRes.setListId(Long.valueOf(category_list_item_id));
        jsonPlaceHolderApi.ViewMyBacklogGameAPI("application/json", "Bearer " + mySharedPref.getSavedAccessToken(), viewMyBacklogGameParaRes).enqueue(new Callback<ViewMyBacklogGameResponse>() {
            @Override
            public void onResponse(Call<ViewMyBacklogGameResponse> call, Response<ViewMyBacklogGameResponse> response) {
                if (response.isSuccessful()) {
                    Customprogress.showPopupProgressSpinner(context, false);
                    Boolean status = response.body().getStatus();
                    if (status) {
                        if (response.body().getData().getGames().size() != 0) {
                            binding.recyclerView.setVisibility(View.VISIBLE);
                            binding.llNoData.setVisibility(View.GONE);
                            ViewMyBacklogGameListDataSet(response.body().getData().getGames());
                        }else{
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.llNoData.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewMyBacklogGameResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                binding.recyclerView.setVisibility(View.GONE);
                binding.llNoData.setVisibility(View.VISIBLE);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void ViewMyBacklogGameListDataSet(List<Game> list) {
        XboxGamesAdapter adapter = new XboxGamesAdapter(context, list, new ItemClickGameInfoBacklogList() {
            @Override
            public void onItemClick(int position, Game gamesList) {
                startActivity(new Intent(context, XboxGameInfoActivity.class)
                        .putExtra("gameData", (Serializable) gamesList)
                        .putExtra("category_list_item_id", category_list_item_id)
                );
            }
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        binding.recyclerView.setAdapter(adapter);
    }

}