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

public final class ItemListBottomSheetCurrentStatusBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout llMain;

  @NonNull
  public final LinearLayout llRightImg;

  @NonNull
  public final TextView tvStatus;

  @NonNull
  public final View viewLine;

  private ItemListBottomSheetCurrentStatusBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout llMain, @NonNull LinearLayout llRightImg, @NonNull TextView tvStatus,
      @NonNull View viewLine) {
    this.rootView = rootView;
    this.llMain = llMain;
    this.llRightImg = llRightImg;
    this.tvStatus = tvStatus;
    this.viewLine = viewLine;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemListBottomSheetCurrentStatusBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemListBottomSheetCurrentStatusBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_list_bottom_sheet_current_status, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemListBottomSheetCurrentStatusBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout llMain = (LinearLayout) rootView;

      id = R.id.llRightImg;
      LinearLayout llRightImg = ViewBindings.findChildViewById(rootView, id);
      if (llRightImg == null) {
        break missingId;
      }

      id = R.id.tvStatus;
      TextView tvStatus = ViewBindings.findChildViewById(rootView, id);
      if (tvStatus == null) {
        break missingId;
      }

      id = R.id.viewLine;
      View viewLine = ViewBindings.findChildViewById(rootView, id);
      if (viewLine == null) {
        break missingId;
      }

      return new ItemListBottomSheetCurrentStatusBinding((LinearLayout) rootView, llMain,
          llRightImg, tvStatus, viewLine);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
