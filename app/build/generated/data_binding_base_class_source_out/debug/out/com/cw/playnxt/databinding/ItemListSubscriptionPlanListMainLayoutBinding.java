// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemListSubscriptionPlanListMainLayoutBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView ivPlan;

  @NonNull
  public final RelativeLayout layoutMain;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final TextView tvPlanTitle;

  private ItemListSubscriptionPlanListMainLayoutBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageView ivPlan, @NonNull RelativeLayout layoutMain,
      @NonNull RecyclerView recyclerView, @NonNull TextView tvPlanTitle) {
    this.rootView = rootView;
    this.ivPlan = ivPlan;
    this.layoutMain = layoutMain;
    this.recyclerView = recyclerView;
    this.tvPlanTitle = tvPlanTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemListSubscriptionPlanListMainLayoutBinding inflate(
      @NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemListSubscriptionPlanListMainLayoutBinding inflate(
      @NonNull LayoutInflater inflater, @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_list_subscription_plan_list_main_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemListSubscriptionPlanListMainLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ivPlan;
      ImageView ivPlan = ViewBindings.findChildViewById(rootView, id);
      if (ivPlan == null) {
        break missingId;
      }

      RelativeLayout layoutMain = (RelativeLayout) rootView;

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.tvPlanTitle;
      TextView tvPlanTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvPlanTitle == null) {
        break missingId;
      }

      return new ItemListSubscriptionPlanListMainLayoutBinding((RelativeLayout) rootView, ivPlan,
          layoutMain, recyclerView, tvPlanTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}