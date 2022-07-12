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
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListItemSettingLayoutBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView imgIcon;

  @NonNull
  public final LinearLayout llMain;

  @NonNull
  public final TextView tvName;

  private ListItemSettingLayoutBinding(@NonNull RelativeLayout rootView, @NonNull ImageView imgIcon,
      @NonNull LinearLayout llMain, @NonNull TextView tvName) {
    this.rootView = rootView;
    this.imgIcon = imgIcon;
    this.llMain = llMain;
    this.tvName = tvName;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListItemSettingLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListItemSettingLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.list_item_setting_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListItemSettingLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imgIcon;
      ImageView imgIcon = ViewBindings.findChildViewById(rootView, id);
      if (imgIcon == null) {
        break missingId;
      }

      id = R.id.llMain;
      LinearLayout llMain = ViewBindings.findChildViewById(rootView, id);
      if (llMain == null) {
        break missingId;
      }

      id = R.id.tvName;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      return new ListItemSettingLayoutBinding((RelativeLayout) rootView, imgIcon, llMain, tvName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
