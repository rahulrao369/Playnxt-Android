// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public final class BottomSheetGetCategoryListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout btnAdd;

  @NonNull
  public final ImageView ivAddNewList;

  @NonNull
  public final ImageView ivSubscribeNow;

  @NonNull
  public final LinearLayout llCreateNewList;

  @NonNull
  public final LinearLayout llSelectAnyList;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final TextView tvAddBtn;

  @NonNull
  public final TextView tvCreateNewList;

  @NonNull
  public final TextView tvHeading;

  private BottomSheetGetCategoryListBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout btnAdd, @NonNull ImageView ivAddNewList,
      @NonNull ImageView ivSubscribeNow, @NonNull LinearLayout llCreateNewList,
      @NonNull LinearLayout llSelectAnyList, @NonNull RecyclerView recyclerView,
      @NonNull TextView tvAddBtn, @NonNull TextView tvCreateNewList, @NonNull TextView tvHeading) {
    this.rootView = rootView;
    this.btnAdd = btnAdd;
    this.ivAddNewList = ivAddNewList;
    this.ivSubscribeNow = ivSubscribeNow;
    this.llCreateNewList = llCreateNewList;
    this.llSelectAnyList = llSelectAnyList;
    this.recyclerView = recyclerView;
    this.tvAddBtn = tvAddBtn;
    this.tvCreateNewList = tvCreateNewList;
    this.tvHeading = tvHeading;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetGetCategoryListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetGetCategoryListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_get_category_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetGetCategoryListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAdd;
      LinearLayout btnAdd = ViewBindings.findChildViewById(rootView, id);
      if (btnAdd == null) {
        break missingId;
      }

      id = R.id.ivAddNewList;
      ImageView ivAddNewList = ViewBindings.findChildViewById(rootView, id);
      if (ivAddNewList == null) {
        break missingId;
      }

      id = R.id.ivSubscribeNow;
      ImageView ivSubscribeNow = ViewBindings.findChildViewById(rootView, id);
      if (ivSubscribeNow == null) {
        break missingId;
      }

      id = R.id.llCreateNewList;
      LinearLayout llCreateNewList = ViewBindings.findChildViewById(rootView, id);
      if (llCreateNewList == null) {
        break missingId;
      }

      id = R.id.llSelectAnyList;
      LinearLayout llSelectAnyList = ViewBindings.findChildViewById(rootView, id);
      if (llSelectAnyList == null) {
        break missingId;
      }

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.tvAddBtn;
      TextView tvAddBtn = ViewBindings.findChildViewById(rootView, id);
      if (tvAddBtn == null) {
        break missingId;
      }

      id = R.id.tvCreateNewList;
      TextView tvCreateNewList = ViewBindings.findChildViewById(rootView, id);
      if (tvCreateNewList == null) {
        break missingId;
      }

      id = R.id.tvHeading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      return new BottomSheetGetCategoryListBinding((LinearLayout) rootView, btnAdd, ivAddNewList,
          ivSubscribeNow, llCreateNewList, llSelectAnyList, recyclerView, tvAddBtn, tvCreateNewList,
          tvHeading);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}