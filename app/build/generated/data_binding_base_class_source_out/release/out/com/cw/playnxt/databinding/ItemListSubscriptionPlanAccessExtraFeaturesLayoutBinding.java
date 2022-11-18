// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout llMain;

  @NonNull
  public final TextView tvFeatures;

  private ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout llMain, @NonNull TextView tvFeatures) {
    this.rootView = rootView;
    this.llMain = llMain;
    this.tvFeatures = tvFeatures;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding inflate(
      @NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding inflate(
      @NonNull LayoutInflater inflater, @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_list_subscription_plan_access_extra_features_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding bind(
      @NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout llMain = (LinearLayout) rootView;

      id = R.id.tvFeatures;
      TextView tvFeatures = ViewBindings.findChildViewById(rootView, id);
      if (tvFeatures == null) {
        break missingId;
      }

      return new ItemListSubscriptionPlanAccessExtraFeaturesLayoutBinding((LinearLayout) rootView,
          llMain, tvFeatures);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}