// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cw.playnxt.R;
import com.google.android.gms.ads.AdView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomeSearchBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AdView adView;

  @NonNull
  public final RelativeLayout btnAdsShow;

  @NonNull
  public final ImageView btnBack;

  @NonNull
  public final CheckBox cbGames;

  @NonNull
  public final CheckBox cbUser;

  @NonNull
  public final EditText etSearch;

  @NonNull
  public final LinearLayout ivSearch;

  @NonNull
  public final LinearLayout llGame;

  @NonNull
  public final LinearLayout llNoGamesResult;

  @NonNull
  public final LinearLayout llNoUserResult;

  @NonNull
  public final LinearLayout llUser;

  @NonNull
  public final LinearLayout lytCheckBox;

  @NonNull
  public final RecyclerView rvGames;

  @NonNull
  public final RecyclerView rvUser;

  @NonNull
  public final TextView tvAddGame;

  @NonNull
  public final TextView tvGame;

  @NonNull
  public final TextView tvHeading;

  @NonNull
  public final TextView tvUser;

  private ActivityHomeSearchBinding(@NonNull RelativeLayout rootView, @NonNull AdView adView,
      @NonNull RelativeLayout btnAdsShow, @NonNull ImageView btnBack, @NonNull CheckBox cbGames,
      @NonNull CheckBox cbUser, @NonNull EditText etSearch, @NonNull LinearLayout ivSearch,
      @NonNull LinearLayout llGame, @NonNull LinearLayout llNoGamesResult,
      @NonNull LinearLayout llNoUserResult, @NonNull LinearLayout llUser,
      @NonNull LinearLayout lytCheckBox, @NonNull RecyclerView rvGames,
      @NonNull RecyclerView rvUser, @NonNull TextView tvAddGame, @NonNull TextView tvGame,
      @NonNull TextView tvHeading, @NonNull TextView tvUser) {
    this.rootView = rootView;
    this.adView = adView;
    this.btnAdsShow = btnAdsShow;
    this.btnBack = btnBack;
    this.cbGames = cbGames;
    this.cbUser = cbUser;
    this.etSearch = etSearch;
    this.ivSearch = ivSearch;
    this.llGame = llGame;
    this.llNoGamesResult = llNoGamesResult;
    this.llNoUserResult = llNoUserResult;
    this.llUser = llUser;
    this.lytCheckBox = lytCheckBox;
    this.rvGames = rvGames;
    this.rvUser = rvUser;
    this.tvAddGame = tvAddGame;
    this.tvGame = tvGame;
    this.tvHeading = tvHeading;
    this.tvUser = tvUser;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomeSearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomeSearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_home_search, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomeSearchBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.adView;
      AdView adView = ViewBindings.findChildViewById(rootView, id);
      if (adView == null) {
        break missingId;
      }

      id = R.id.btnAdsShow;
      RelativeLayout btnAdsShow = ViewBindings.findChildViewById(rootView, id);
      if (btnAdsShow == null) {
        break missingId;
      }

      id = R.id.btnBack;
      ImageView btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.cbGames;
      CheckBox cbGames = ViewBindings.findChildViewById(rootView, id);
      if (cbGames == null) {
        break missingId;
      }

      id = R.id.cbUser;
      CheckBox cbUser = ViewBindings.findChildViewById(rootView, id);
      if (cbUser == null) {
        break missingId;
      }

      id = R.id.etSearch;
      EditText etSearch = ViewBindings.findChildViewById(rootView, id);
      if (etSearch == null) {
        break missingId;
      }

      id = R.id.ivSearch;
      LinearLayout ivSearch = ViewBindings.findChildViewById(rootView, id);
      if (ivSearch == null) {
        break missingId;
      }

      id = R.id.llGame;
      LinearLayout llGame = ViewBindings.findChildViewById(rootView, id);
      if (llGame == null) {
        break missingId;
      }

      id = R.id.llNoGamesResult;
      LinearLayout llNoGamesResult = ViewBindings.findChildViewById(rootView, id);
      if (llNoGamesResult == null) {
        break missingId;
      }

      id = R.id.llNoUserResult;
      LinearLayout llNoUserResult = ViewBindings.findChildViewById(rootView, id);
      if (llNoUserResult == null) {
        break missingId;
      }

      id = R.id.llUser;
      LinearLayout llUser = ViewBindings.findChildViewById(rootView, id);
      if (llUser == null) {
        break missingId;
      }

      id = R.id.lytCheckBox;
      LinearLayout lytCheckBox = ViewBindings.findChildViewById(rootView, id);
      if (lytCheckBox == null) {
        break missingId;
      }

      id = R.id.rvGames;
      RecyclerView rvGames = ViewBindings.findChildViewById(rootView, id);
      if (rvGames == null) {
        break missingId;
      }

      id = R.id.rvUser;
      RecyclerView rvUser = ViewBindings.findChildViewById(rootView, id);
      if (rvUser == null) {
        break missingId;
      }

      id = R.id.tvAddGame;
      TextView tvAddGame = ViewBindings.findChildViewById(rootView, id);
      if (tvAddGame == null) {
        break missingId;
      }

      id = R.id.tvGame;
      TextView tvGame = ViewBindings.findChildViewById(rootView, id);
      if (tvGame == null) {
        break missingId;
      }

      id = R.id.tvHeading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      id = R.id.tvUser;
      TextView tvUser = ViewBindings.findChildViewById(rootView, id);
      if (tvUser == null) {
        break missingId;
      }

      return new ActivityHomeSearchBinding((RelativeLayout) rootView, adView, btnAdsShow, btnBack,
          cbGames, cbUser, etSearch, ivSearch, llGame, llNoGamesResult, llNoUserResult, llUser,
          lytCheckBox, rvGames, rvUser, tvAddGame, tvGame, tvHeading, tvUser);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
