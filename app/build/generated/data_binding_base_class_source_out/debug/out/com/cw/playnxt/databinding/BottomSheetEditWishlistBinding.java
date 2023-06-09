// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public final class BottomSheetEditWishlistBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout btnAdsShow;

  @NonNull
  public final LinearLayout btnEditList;

  @NonNull
  public final EditText etEditName;

  @NonNull
  public final TextView tvHeading;

  private BottomSheetEditWishlistBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout btnAdsShow, @NonNull LinearLayout btnEditList,
      @NonNull EditText etEditName, @NonNull TextView tvHeading) {
    this.rootView = rootView;
    this.btnAdsShow = btnAdsShow;
    this.btnEditList = btnEditList;
    this.etEditName = etEditName;
    this.tvHeading = tvHeading;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetEditWishlistBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetEditWishlistBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_edit_wishlist, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetEditWishlistBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAdsShow;
      LinearLayout btnAdsShow = ViewBindings.findChildViewById(rootView, id);
      if (btnAdsShow == null) {
        break missingId;
      }

      id = R.id.btnEditList;
      LinearLayout btnEditList = ViewBindings.findChildViewById(rootView, id);
      if (btnEditList == null) {
        break missingId;
      }

      id = R.id.etEditName;
      EditText etEditName = ViewBindings.findChildViewById(rootView, id);
      if (etEditName == null) {
        break missingId;
      }

      id = R.id.tvHeading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      return new BottomSheetEditWishlistBinding((LinearLayout) rootView, btnAdsShow, btnEditList,
          etEditName, tvHeading);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
