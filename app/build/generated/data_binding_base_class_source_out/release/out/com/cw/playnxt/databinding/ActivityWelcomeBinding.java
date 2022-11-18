// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityWelcomeBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout btnNext;

  @NonNull
  public final LinearLayout llBottom;

  @NonNull
  public final TextView txtBtn;

  @NonNull
  public final ViewPager viewPager;

  private ActivityWelcomeBinding(@NonNull RelativeLayout rootView, @NonNull LinearLayout btnNext,
      @NonNull LinearLayout llBottom, @NonNull TextView txtBtn, @NonNull ViewPager viewPager) {
    this.rootView = rootView;
    this.btnNext = btnNext;
    this.llBottom = llBottom;
    this.txtBtn = txtBtn;
    this.viewPager = viewPager;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityWelcomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityWelcomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_welcome, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityWelcomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnNext;
      LinearLayout btnNext = ViewBindings.findChildViewById(rootView, id);
      if (btnNext == null) {
        break missingId;
      }

      id = R.id.ll_bottom;
      LinearLayout llBottom = ViewBindings.findChildViewById(rootView, id);
      if (llBottom == null) {
        break missingId;
      }

      id = R.id.txtBtn;
      TextView txtBtn = ViewBindings.findChildViewById(rootView, id);
      if (txtBtn == null) {
        break missingId;
      }

      id = R.id.view_pager;
      ViewPager viewPager = ViewBindings.findChildViewById(rootView, id);
      if (viewPager == null) {
        break missingId;
      }

      return new ActivityWelcomeBinding((RelativeLayout) rootView, btnNext, llBottom, txtBtn,
          viewPager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}