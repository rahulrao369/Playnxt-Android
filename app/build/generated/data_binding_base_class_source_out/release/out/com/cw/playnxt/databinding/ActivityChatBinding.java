// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cw.playnxt.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityChatBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout btnBack;

  @NonNull
  public final LinearLayout btnBack1;

  @NonNull
  public final CircleImageView imgUserProfile;

  @NonNull
  public final LinearLayout lytName;

  @NonNull
  public final LinearLayout lytUserProfile;

  @NonNull
  public final TextView tvUserName;

  @NonNull
  public final WebView webView;

  private ActivityChatBinding(@NonNull RelativeLayout rootView, @NonNull LinearLayout btnBack,
      @NonNull LinearLayout btnBack1, @NonNull CircleImageView imgUserProfile,
      @NonNull LinearLayout lytName, @NonNull LinearLayout lytUserProfile,
      @NonNull TextView tvUserName, @NonNull WebView webView) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.btnBack1 = btnBack1;
    this.imgUserProfile = imgUserProfile;
    this.lytName = lytName;
    this.lytUserProfile = lytUserProfile;
    this.tvUserName = tvUserName;
    this.webView = webView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_chat, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityChatBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBack;
      LinearLayout btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.btnBack1;
      LinearLayout btnBack1 = ViewBindings.findChildViewById(rootView, id);
      if (btnBack1 == null) {
        break missingId;
      }

      id = R.id.imgUserProfile;
      CircleImageView imgUserProfile = ViewBindings.findChildViewById(rootView, id);
      if (imgUserProfile == null) {
        break missingId;
      }

      id = R.id.lyt_name;
      LinearLayout lytName = ViewBindings.findChildViewById(rootView, id);
      if (lytName == null) {
        break missingId;
      }

      id = R.id.lytUserProfile;
      LinearLayout lytUserProfile = ViewBindings.findChildViewById(rootView, id);
      if (lytUserProfile == null) {
        break missingId;
      }

      id = R.id.tvUserName;
      TextView tvUserName = ViewBindings.findChildViewById(rootView, id);
      if (tvUserName == null) {
        break missingId;
      }

      id = R.id.webView;
      WebView webView = ViewBindings.findChildViewById(rootView, id);
      if (webView == null) {
        break missingId;
      }

      return new ActivityChatBinding((RelativeLayout) rootView, btnBack, btnBack1, imgUserProfile,
          lytName, lytUserProfile, tvUserName, webView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
