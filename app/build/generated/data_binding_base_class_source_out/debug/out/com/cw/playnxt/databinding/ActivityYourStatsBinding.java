// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

public final class ActivityYourStatsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final HeaderLayoutBinding bindingHeader;

  @NonNull
  public final TextView countAvgRating;

  @NonNull
  public final TextView countCompleted;

  @NonNull
  public final TextView countCurrentlyPlaying;

  @NonNull
  public final TextView countOnTheShelf;

  @NonNull
  public final TextView countRolledCredit;

  @NonNull
  public final TextView countTakingBreak;

  @NonNull
  public final TextView countTotalGames;

  @NonNull
  public final TextView countTotalRating;

  @NonNull
  public final TextView countWishlist;

  @NonNull
  public final TextView countbacklog;

  @NonNull
  public final CardView rlMain;

  private ActivityYourStatsBinding(@NonNull RelativeLayout rootView,
      @NonNull HeaderLayoutBinding bindingHeader, @NonNull TextView countAvgRating,
      @NonNull TextView countCompleted, @NonNull TextView countCurrentlyPlaying,
      @NonNull TextView countOnTheShelf, @NonNull TextView countRolledCredit,
      @NonNull TextView countTakingBreak, @NonNull TextView countTotalGames,
      @NonNull TextView countTotalRating, @NonNull TextView countWishlist,
      @NonNull TextView countbacklog, @NonNull CardView rlMain) {
    this.rootView = rootView;
    this.bindingHeader = bindingHeader;
    this.countAvgRating = countAvgRating;
    this.countCompleted = countCompleted;
    this.countCurrentlyPlaying = countCurrentlyPlaying;
    this.countOnTheShelf = countOnTheShelf;
    this.countRolledCredit = countRolledCredit;
    this.countTakingBreak = countTakingBreak;
    this.countTotalGames = countTotalGames;
    this.countTotalRating = countTotalRating;
    this.countWishlist = countWishlist;
    this.countbacklog = countbacklog;
    this.rlMain = rlMain;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityYourStatsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityYourStatsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_your_stats, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityYourStatsBinding bind(@NonNull View rootView) {
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

      id = R.id.countAvgRating;
      TextView countAvgRating = ViewBindings.findChildViewById(rootView, id);
      if (countAvgRating == null) {
        break missingId;
      }

      id = R.id.countCompleted;
      TextView countCompleted = ViewBindings.findChildViewById(rootView, id);
      if (countCompleted == null) {
        break missingId;
      }

      id = R.id.countCurrentlyPlaying;
      TextView countCurrentlyPlaying = ViewBindings.findChildViewById(rootView, id);
      if (countCurrentlyPlaying == null) {
        break missingId;
      }

      id = R.id.countOnTheShelf;
      TextView countOnTheShelf = ViewBindings.findChildViewById(rootView, id);
      if (countOnTheShelf == null) {
        break missingId;
      }

      id = R.id.countRolledCredit;
      TextView countRolledCredit = ViewBindings.findChildViewById(rootView, id);
      if (countRolledCredit == null) {
        break missingId;
      }

      id = R.id.countTakingBreak;
      TextView countTakingBreak = ViewBindings.findChildViewById(rootView, id);
      if (countTakingBreak == null) {
        break missingId;
      }

      id = R.id.countTotalGames;
      TextView countTotalGames = ViewBindings.findChildViewById(rootView, id);
      if (countTotalGames == null) {
        break missingId;
      }

      id = R.id.countTotalRating;
      TextView countTotalRating = ViewBindings.findChildViewById(rootView, id);
      if (countTotalRating == null) {
        break missingId;
      }

      id = R.id.countWishlist;
      TextView countWishlist = ViewBindings.findChildViewById(rootView, id);
      if (countWishlist == null) {
        break missingId;
      }

      id = R.id.countbacklog;
      TextView countbacklog = ViewBindings.findChildViewById(rootView, id);
      if (countbacklog == null) {
        break missingId;
      }

      id = R.id.rlMain;
      CardView rlMain = ViewBindings.findChildViewById(rootView, id);
      if (rlMain == null) {
        break missingId;
      }

      return new ActivityYourStatsBinding((RelativeLayout) rootView, binding_bindingHeader,
          countAvgRating, countCompleted, countCurrentlyPlaying, countOnTheShelf, countRolledCredit,
          countTakingBreak, countTotalGames, countTotalRating, countWishlist, countbacklog, rlMain);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
