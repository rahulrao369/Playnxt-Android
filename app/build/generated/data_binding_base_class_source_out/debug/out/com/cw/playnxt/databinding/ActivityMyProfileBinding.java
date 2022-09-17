// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.cw.playnxt.R;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMyProfileBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AdView adView;

  @NonNull
  public final RelativeLayout btnAdsShow;

  @NonNull
  public final ImageView btnBack;

  @NonNull
  public final LinearLayout btnEditProfile;

  @NonNull
  public final LinearLayout btnSetting;

  @NonNull
  public final CircleImageView cvUserProfile;

  @NonNull
  public final LinearLayout llMain;

  @NonNull
  public final TabLayout tablayout;

  @NonNull
  public final TextView tvHeading;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView txtBtn;

  @NonNull
  public final ViewPager viewpager;

  private ActivityMyProfileBinding(@NonNull RelativeLayout rootView, @NonNull AdView adView,
      @NonNull RelativeLayout btnAdsShow, @NonNull ImageView btnBack,
      @NonNull LinearLayout btnEditProfile, @NonNull LinearLayout btnSetting,
      @NonNull CircleImageView cvUserProfile, @NonNull LinearLayout llMain,
      @NonNull TabLayout tablayout, @NonNull TextView tvHeading, @NonNull TextView tvName,
      @NonNull TextView txtBtn, @NonNull ViewPager viewpager) {
    this.rootView = rootView;
    this.adView = adView;
    this.btnAdsShow = btnAdsShow;
    this.btnBack = btnBack;
    this.btnEditProfile = btnEditProfile;
    this.btnSetting = btnSetting;
    this.cvUserProfile = cvUserProfile;
    this.llMain = llMain;
    this.tablayout = tablayout;
    this.tvHeading = tvHeading;
    this.tvName = tvName;
    this.txtBtn = txtBtn;
    this.viewpager = viewpager;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMyProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMyProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_my_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMyProfileBinding bind(@NonNull View rootView) {
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

      id = R.id.btnEditProfile;
      LinearLayout btnEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (btnEditProfile == null) {
        break missingId;
      }

      id = R.id.btnSetting;
      LinearLayout btnSetting = ViewBindings.findChildViewById(rootView, id);
      if (btnSetting == null) {
        break missingId;
      }

      id = R.id.cvUserProfile;
      CircleImageView cvUserProfile = ViewBindings.findChildViewById(rootView, id);
      if (cvUserProfile == null) {
        break missingId;
      }

      id = R.id.llMain;
      LinearLayout llMain = ViewBindings.findChildViewById(rootView, id);
      if (llMain == null) {
        break missingId;
      }

      id = R.id.tablayout;
      TabLayout tablayout = ViewBindings.findChildViewById(rootView, id);
      if (tablayout == null) {
        break missingId;
      }

      id = R.id.tvHeading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      id = R.id.tvName;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.txtBtn;
      TextView txtBtn = ViewBindings.findChildViewById(rootView, id);
      if (txtBtn == null) {
        break missingId;
      }

      id = R.id.viewpager;
      ViewPager viewpager = ViewBindings.findChildViewById(rootView, id);
      if (viewpager == null) {
        break missingId;
      }

      return new ActivityMyProfileBinding((RelativeLayout) rootView, adView, btnAdsShow, btnBack,
          btnEditProfile, btnSetting, cvUserProfile, llMain, tablayout, tvHeading, tvName, txtBtn,
          viewpager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
