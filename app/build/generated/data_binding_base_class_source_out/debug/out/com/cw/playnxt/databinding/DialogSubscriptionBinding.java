// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class DialogSubscriptionBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView image;

  @NonNull
  public final TextView text1;

  @NonNull
  public final TextView txtOk;

  private DialogSubscriptionBinding(@NonNull RelativeLayout rootView, @NonNull ImageView image,
      @NonNull TextView text1, @NonNull TextView txtOk) {
    this.rootView = rootView;
    this.image = image;
    this.text1 = text1;
    this.txtOk = txtOk;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogSubscriptionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogSubscriptionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_subscription, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogSubscriptionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.image;
      ImageView image = ViewBindings.findChildViewById(rootView, id);
      if (image == null) {
        break missingId;
      }

      id = R.id.text1;
      TextView text1 = ViewBindings.findChildViewById(rootView, id);
      if (text1 == null) {
        break missingId;
      }

      id = R.id.txt_ok;
      TextView txtOk = ViewBindings.findChildViewById(rootView, id);
      if (txtOk == null) {
        break missingId;
      }

      return new DialogSubscriptionBinding((RelativeLayout) rootView, image, text1, txtOk);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
