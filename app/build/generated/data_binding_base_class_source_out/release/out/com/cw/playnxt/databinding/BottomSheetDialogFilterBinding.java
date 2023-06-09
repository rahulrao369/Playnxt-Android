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

public final class BottomSheetDialogFilterBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout btnAdsShow;

  @NonNull
  public final LinearLayout btnCreateList;

  @NonNull
  public final EditText etName;

  @NonNull
  public final TextView tvHeading;

  private BottomSheetDialogFilterBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout btnAdsShow, @NonNull LinearLayout btnCreateList,
      @NonNull EditText etName, @NonNull TextView tvHeading) {
    this.rootView = rootView;
    this.btnAdsShow = btnAdsShow;
    this.btnCreateList = btnCreateList;
    this.etName = etName;
    this.tvHeading = tvHeading;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetDialogFilterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetDialogFilterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_dialog_filter, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetDialogFilterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAdsShow;
      LinearLayout btnAdsShow = ViewBindings.findChildViewById(rootView, id);
      if (btnAdsShow == null) {
        break missingId;
      }

      id = R.id.btnCreateList;
      LinearLayout btnCreateList = ViewBindings.findChildViewById(rootView, id);
      if (btnCreateList == null) {
        break missingId;
      }

      id = R.id.etName;
      EditText etName = ViewBindings.findChildViewById(rootView, id);
      if (etName == null) {
        break missingId;
      }

      id = R.id.tvHeading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      return new BottomSheetDialogFilterBinding((LinearLayout) rootView, btnAdsShow, btnCreateList,
          etName, tvHeading);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
