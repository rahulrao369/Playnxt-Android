// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

public final class ActivityAddGameBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final HeaderLayoutBinding bindingHeader;

  @NonNull
  public final LinearLayout btnAddToBacklog;

  @NonNull
  public final LinearLayout btnAddToWishList;

  @NonNull
  public final EditText etGameDescription;

  @NonNull
  public final EditText etGameGenre;

  @NonNull
  public final EditText etGamePlatform;

  @NonNull
  public final EditText etGameTitle;

  @NonNull
  public final ImageView ivGame;

  @NonNull
  public final ImageView ivGameIcon;

  @NonNull
  public final LinearLayout llGiveRating;

  @NonNull
  public final RelativeLayout llImage;

  @NonNull
  public final LinearLayout llSelectImage;

  @NonNull
  public final RatingBar ratingBar;

  @NonNull
  public final TextView tvRating;

  @NonNull
  public final TextView tvUploadGameImg;

  private ActivityAddGameBinding(@NonNull RelativeLayout rootView,
      @NonNull HeaderLayoutBinding bindingHeader, @NonNull LinearLayout btnAddToBacklog,
      @NonNull LinearLayout btnAddToWishList, @NonNull EditText etGameDescription,
      @NonNull EditText etGameGenre, @NonNull EditText etGamePlatform,
      @NonNull EditText etGameTitle, @NonNull ImageView ivGame, @NonNull ImageView ivGameIcon,
      @NonNull LinearLayout llGiveRating, @NonNull RelativeLayout llImage,
      @NonNull LinearLayout llSelectImage, @NonNull RatingBar ratingBar, @NonNull TextView tvRating,
      @NonNull TextView tvUploadGameImg) {
    this.rootView = rootView;
    this.bindingHeader = bindingHeader;
    this.btnAddToBacklog = btnAddToBacklog;
    this.btnAddToWishList = btnAddToWishList;
    this.etGameDescription = etGameDescription;
    this.etGameGenre = etGameGenre;
    this.etGamePlatform = etGamePlatform;
    this.etGameTitle = etGameTitle;
    this.ivGame = ivGame;
    this.ivGameIcon = ivGameIcon;
    this.llGiveRating = llGiveRating;
    this.llImage = llImage;
    this.llSelectImage = llSelectImage;
    this.ratingBar = ratingBar;
    this.tvRating = tvRating;
    this.tvUploadGameImg = tvUploadGameImg;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAddGameBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAddGameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_add_game, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAddGameBinding bind(@NonNull View rootView) {
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

      id = R.id.etGameDescription;
      EditText etGameDescription = ViewBindings.findChildViewById(rootView, id);
      if (etGameDescription == null) {
        break missingId;
      }

      id = R.id.etGameGenre;
      EditText etGameGenre = ViewBindings.findChildViewById(rootView, id);
      if (etGameGenre == null) {
        break missingId;
      }

      id = R.id.etGamePlatform;
      EditText etGamePlatform = ViewBindings.findChildViewById(rootView, id);
      if (etGamePlatform == null) {
        break missingId;
      }

      id = R.id.etGameTitle;
      EditText etGameTitle = ViewBindings.findChildViewById(rootView, id);
      if (etGameTitle == null) {
        break missingId;
      }

      id = R.id.ivGame;
      ImageView ivGame = ViewBindings.findChildViewById(rootView, id);
      if (ivGame == null) {
        break missingId;
      }

      id = R.id.ivGameIcon;
      ImageView ivGameIcon = ViewBindings.findChildViewById(rootView, id);
      if (ivGameIcon == null) {
        break missingId;
      }

      id = R.id.llGiveRating;
      LinearLayout llGiveRating = ViewBindings.findChildViewById(rootView, id);
      if (llGiveRating == null) {
        break missingId;
      }

      id = R.id.llImage;
      RelativeLayout llImage = ViewBindings.findChildViewById(rootView, id);
      if (llImage == null) {
        break missingId;
      }

      id = R.id.llSelectImage;
      LinearLayout llSelectImage = ViewBindings.findChildViewById(rootView, id);
      if (llSelectImage == null) {
        break missingId;
      }

      id = R.id.ratingBar;
      RatingBar ratingBar = ViewBindings.findChildViewById(rootView, id);
      if (ratingBar == null) {
        break missingId;
      }

      id = R.id.tvRating;
      TextView tvRating = ViewBindings.findChildViewById(rootView, id);
      if (tvRating == null) {
        break missingId;
      }

      id = R.id.tvUploadGameImg;
      TextView tvUploadGameImg = ViewBindings.findChildViewById(rootView, id);
      if (tvUploadGameImg == null) {
        break missingId;
      }

      return new ActivityAddGameBinding((RelativeLayout) rootView, binding_bindingHeader,
          btnAddToBacklog, btnAddToWishList, etGameDescription, etGameGenre, etGamePlatform,
          etGameTitle, ivGame, ivGameIcon, llGiveRating, llImage, llSelectImage, ratingBar,
          tvRating, tvUploadGameImg);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
