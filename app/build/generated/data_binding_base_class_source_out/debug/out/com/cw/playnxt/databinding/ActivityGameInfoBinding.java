// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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

public final class ActivityGameInfoBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final HeaderLayoutBinding bindingHeader;

  @NonNull
  public final LinearLayout btnAdd;

  @NonNull
  public final LinearLayout btnAddToBacklog;

  @NonNull
  public final LinearLayout btnAddToWishList;

  @NonNull
  public final RatingBar ratingBar;

  @NonNull
  public final TextView tvGameDescription;

  @NonNull
  public final TextView tvGameGenre;

  @NonNull
  public final TextView tvGamePlatform;

  @NonNull
  public final TextView tvGenre;

  @NonNull
  public final TextView tvPlatform;

  @NonNull
  public final TextView tvRating;

  private ActivityGameInfoBinding(@NonNull RelativeLayout rootView,
      @NonNull HeaderLayoutBinding bindingHeader, @NonNull LinearLayout btnAdd,
      @NonNull LinearLayout btnAddToBacklog, @NonNull LinearLayout btnAddToWishList,
      @NonNull RatingBar ratingBar, @NonNull TextView tvGameDescription,
      @NonNull TextView tvGameGenre, @NonNull TextView tvGamePlatform, @NonNull TextView tvGenre,
      @NonNull TextView tvPlatform, @NonNull TextView tvRating) {
    this.rootView = rootView;
    this.bindingHeader = bindingHeader;
    this.btnAdd = btnAdd;
    this.btnAddToBacklog = btnAddToBacklog;
    this.btnAddToWishList = btnAddToWishList;
    this.ratingBar = ratingBar;
    this.tvGameDescription = tvGameDescription;
    this.tvGameGenre = tvGameGenre;
    this.tvGamePlatform = tvGamePlatform;
    this.tvGenre = tvGenre;
    this.tvPlatform = tvPlatform;
    this.tvRating = tvRating;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityGameInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityGameInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_game_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityGameInfoBinding bind(@NonNull View rootView) {
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

      id = R.id.btnAdd;
      LinearLayout btnAdd = ViewBindings.findChildViewById(rootView, id);
      if (btnAdd == null) {
        break missingId;
      }

      id = R.id.btnAddToBacklog;
      LinearLayout btnAddToBacklog = ViewBindings.findChildViewById(rootView, id);
      if (btnAddToBacklog == null) {
        break missingId;
      }

      id = R.id.btnAddToWishList;
      LinearLayout btnAddToWishList = ViewBindings.findChildViewById(rootView, id);
      if (btnAddToWishList == null) {
        break missingId;
      }

      id = R.id.ratingBar;
      RatingBar ratingBar = ViewBindings.findChildViewById(rootView, id);
      if (ratingBar == null) {
        break missingId;
      }

      id = R.id.tvGameDescription;
      TextView tvGameDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvGameDescription == null) {
        break missingId;
      }

      id = R.id.tvGameGenre;
      TextView tvGameGenre = ViewBindings.findChildViewById(rootView, id);
      if (tvGameGenre == null) {
        break missingId;
      }

      id = R.id.tvGamePlatform;
      TextView tvGamePlatform = ViewBindings.findChildViewById(rootView, id);
      if (tvGamePlatform == null) {
        break missingId;
      }

      id = R.id.tvGenre;
      TextView tvGenre = ViewBindings.findChildViewById(rootView, id);
      if (tvGenre == null) {
        break missingId;
      }

      id = R.id.tvPlatform;
      TextView tvPlatform = ViewBindings.findChildViewById(rootView, id);
      if (tvPlatform == null) {
        break missingId;
      }

      id = R.id.tvRating;
      TextView tvRating = ViewBindings.findChildViewById(rootView, id);
      if (tvRating == null) {
        break missingId;
      }

      return new ActivityGameInfoBinding((RelativeLayout) rootView, binding_bindingHeader, btnAdd,
          btnAddToBacklog, btnAddToWishList, ratingBar, tvGameDescription, tvGameGenre,
          tvGamePlatform, tvGenre, tvPlatform, tvRating);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
