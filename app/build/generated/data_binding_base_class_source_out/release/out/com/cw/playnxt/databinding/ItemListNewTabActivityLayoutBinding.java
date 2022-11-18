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
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemListNewTabActivityLayoutBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final LinearLayout btnView;

  @NonNull
  public final ImageView gameImg;

  @NonNull
  public final TextView gameName;

  @NonNull
  public final LinearLayout layoutMain;

  @NonNull
  public final TextView txtBtn;

  private ItemListNewTabActivityLayoutBinding(@NonNull CardView rootView,
      @NonNull LinearLayout btnView, @NonNull ImageView gameImg, @NonNull TextView gameName,
      @NonNull LinearLayout layoutMain, @NonNull TextView txtBtn) {
    this.rootView = rootView;
    this.btnView = btnView;
    this.gameImg = gameImg;
    this.gameName = gameName;
    this.layoutMain = layoutMain;
    this.txtBtn = txtBtn;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemListNewTabActivityLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemListNewTabActivityLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_list_new_tab_activity_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemListNewTabActivityLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnView;
      LinearLayout btnView = ViewBindings.findChildViewById(rootView, id);
      if (btnView == null) {
        break missingId;
      }

      id = R.id.gameImg;
      ImageView gameImg = ViewBindings.findChildViewById(rootView, id);
      if (gameImg == null) {
        break missingId;
      }

      id = R.id.gameName;
      TextView gameName = ViewBindings.findChildViewById(rootView, id);
      if (gameName == null) {
        break missingId;
      }

      id = R.id.layoutMain;
      LinearLayout layoutMain = ViewBindings.findChildViewById(rootView, id);
      if (layoutMain == null) {
        break missingId;
      }

      id = R.id.txtBtn;
      TextView txtBtn = ViewBindings.findChildViewById(rootView, id);
      if (txtBtn == null) {
        break missingId;
      }

      return new ItemListNewTabActivityLayoutBinding((CardView) rootView, btnView, gameImg,
          gameName, layoutMain, txtBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}