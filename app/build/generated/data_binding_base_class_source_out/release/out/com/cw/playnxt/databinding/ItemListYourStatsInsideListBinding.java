// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ItemListYourStatsInsideListBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView tvHeading;

  @NonNull
  public final TextView tvnumber1;

  @NonNull
  public final TextView tvnumber2;

  private ItemListYourStatsInsideListBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView tvHeading, @NonNull TextView tvnumber1, @NonNull TextView tvnumber2) {
    this.rootView = rootView;
    this.tvHeading = tvHeading;
    this.tvnumber1 = tvnumber1;
    this.tvnumber2 = tvnumber2;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemListYourStatsInsideListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemListYourStatsInsideListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_list_your_stats_inside_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemListYourStatsInsideListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tvHeading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      id = R.id.tvnumber1;
      TextView tvnumber1 = ViewBindings.findChildViewById(rootView, id);
      if (tvnumber1 == null) {
        break missingId;
      }

      id = R.id.tvnumber2;
      TextView tvnumber2 = ViewBindings.findChildViewById(rootView, id);
      if (tvnumber2 == null) {
        break missingId;
      }

      return new ItemListYourStatsInsideListBinding((RelativeLayout) rootView, tvHeading, tvnumber1,
          tvnumber2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}