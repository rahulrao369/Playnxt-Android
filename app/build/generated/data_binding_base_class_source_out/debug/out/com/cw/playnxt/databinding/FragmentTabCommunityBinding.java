// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentTabCommunityBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final LinearLayout llMain;

  @NonNull
  public final LinearLayout llNoData;

  @NonNull
  public final ProgressBar progressBar1;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final RelativeLayout rlProgressBar;

  @NonNull
  public final SwipeRefreshLayout swipeLayout;

  private FragmentTabCommunityBinding(@NonNull FrameLayout rootView, @NonNull LinearLayout llMain,
      @NonNull LinearLayout llNoData, @NonNull ProgressBar progressBar1,
      @NonNull RecyclerView recyclerView, @NonNull RelativeLayout rlProgressBar,
      @NonNull SwipeRefreshLayout swipeLayout) {
    this.rootView = rootView;
    this.llMain = llMain;
    this.llNoData = llNoData;
    this.progressBar1 = progressBar1;
    this.recyclerView = recyclerView;
    this.rlProgressBar = rlProgressBar;
    this.swipeLayout = swipeLayout;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentTabCommunityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTabCommunityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_tab_community, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTabCommunityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.llMain;
      LinearLayout llMain = ViewBindings.findChildViewById(rootView, id);
      if (llMain == null) {
        break missingId;
      }

      id = R.id.llNoData;
      LinearLayout llNoData = ViewBindings.findChildViewById(rootView, id);
      if (llNoData == null) {
        break missingId;
      }

      id = R.id.progressBar1;
      ProgressBar progressBar1 = ViewBindings.findChildViewById(rootView, id);
      if (progressBar1 == null) {
        break missingId;
      }

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.rlProgressBar;
      RelativeLayout rlProgressBar = ViewBindings.findChildViewById(rootView, id);
      if (rlProgressBar == null) {
        break missingId;
      }

      id = R.id.swipeLayout;
      SwipeRefreshLayout swipeLayout = ViewBindings.findChildViewById(rootView, id);
      if (swipeLayout == null) {
        break missingId;
      }

      return new FragmentTabCommunityBinding((FrameLayout) rootView, llMain, llNoData, progressBar1,
          recyclerView, rlProgressBar, swipeLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
