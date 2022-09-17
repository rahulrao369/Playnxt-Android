// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public final class ActivityXboxGamesBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AdView adView;

  @NonNull
  public final HeaderLayoutBinding bindingHeader;

  @NonNull
  public final RelativeLayout btnAdsShow;

  @NonNull
  public final LinearLayout llNoData;

  @NonNull
  public final RecyclerView recyclerView;

  private ActivityXboxGamesBinding(@NonNull RelativeLayout rootView, @NonNull AdView adView,
      @NonNull HeaderLayoutBinding bindingHeader, @NonNull RelativeLayout btnAdsShow,
      @NonNull LinearLayout llNoData, @NonNull RecyclerView recyclerView) {
    this.rootView = rootView;
    this.adView = adView;
    this.bindingHeader = bindingHeader;
    this.btnAdsShow = btnAdsShow;
    this.llNoData = llNoData;
    this.recyclerView = recyclerView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityXboxGamesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityXboxGamesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_xbox_games, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityXboxGamesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.adView;
      AdView adView = ViewBindings.findChildViewById(rootView, id);
      if (adView == null) {
        break missingId;
      }

      id = R.id.bindingHeader;
      View bindingHeader = ViewBindings.findChildViewById(rootView, id);
      if (bindingHeader == null) {
        break missingId;
      }
      HeaderLayoutBinding binding_bindingHeader = HeaderLayoutBinding.bind(bindingHeader);

      id = R.id.btnAdsShow;
      RelativeLayout btnAdsShow = ViewBindings.findChildViewById(rootView, id);
      if (btnAdsShow == null) {
        break missingId;
      }

      id = R.id.llNoData;
      LinearLayout llNoData = ViewBindings.findChildViewById(rootView, id);
      if (llNoData == null) {
        break missingId;
      }

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      return new ActivityXboxGamesBinding((RelativeLayout) rootView, adView, binding_bindingHeader,
          btnAdsShow, llNoData, recyclerView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
