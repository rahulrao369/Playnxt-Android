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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityWishlistGameInfoBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final HeaderLayoutBinding bindingHeader;

  @NonNull
  public final LinearLayout btnAddNewNote;

  @NonNull
  public final LinearLayout btnEditCurrentStatus;

  @NonNull
  public final ImageView gameImg;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final TextView tvCurrentStatus;

  @NonNull
  public final TextView tvGameTitle;

  private ActivityWishlistGameInfoBinding(@NonNull RelativeLayout rootView,
      @NonNull HeaderLayoutBinding bindingHeader, @NonNull LinearLayout btnAddNewNote,
      @NonNull LinearLayout btnEditCurrentStatus, @NonNull ImageView gameImg,
      @NonNull RecyclerView recyclerView, @NonNull TextView tvCurrentStatus,
      @NonNull TextView tvGameTitle) {
    this.rootView = rootView;
    this.bindingHeader = bindingHeader;
    this.btnAddNewNote = btnAddNewNote;
    this.btnEditCurrentStatus = btnEditCurrentStatus;
    this.gameImg = gameImg;
    this.recyclerView = recyclerView;
    this.tvCurrentStatus = tvCurrentStatus;
    this.tvGameTitle = tvGameTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityWishlistGameInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityWishlistGameInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_wishlist_game_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityWishlistGameInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bindingHeader;
      View bindingHeader = ViewBindings.findChildViewById(rootView, id);
      if (bindingHeader == null) {
        break missingId;
      }
      HeaderLayoutBinding binding_bindingHeader = HeaderLayoutBinding.bind(bindingHeader);

      id = R.id.btnAddNewNote;
      LinearLayout btnAddNewNote = ViewBindings.findChildViewById(rootView, id);
      if (btnAddNewNote == null) {
        break missingId;
      }

      id = R.id.btnEditCurrentStatus;
      LinearLayout btnEditCurrentStatus = ViewBindings.findChildViewById(rootView, id);
      if (btnEditCurrentStatus == null) {
        break missingId;
      }

      id = R.id.gameImg;
      ImageView gameImg = ViewBindings.findChildViewById(rootView, id);
      if (gameImg == null) {
        break missingId;
      }

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.tvCurrentStatus;
      TextView tvCurrentStatus = ViewBindings.findChildViewById(rootView, id);
      if (tvCurrentStatus == null) {
        break missingId;
      }

      id = R.id.tvGameTitle;
      TextView tvGameTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvGameTitle == null) {
        break missingId;
      }

      return new ActivityWishlistGameInfoBinding((RelativeLayout) rootView, binding_bindingHeader,
          btnAddNewNote, btnEditCurrentStatus, gameImg, recyclerView, tvCurrentStatus, tvGameTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}