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

public final class ActivityWishlistBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final HeaderLayoutBinding bindingHeader;

  @NonNull
  public final LinearLayout btnAdd;

  @NonNull
  public final ImageView ivSubscribeNow;

  @NonNull
  public final LinearLayout llCreateBacklogList;

  @NonNull
  public final LinearLayout llNoData;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final TextView txtBtn;

  private ActivityWishlistBinding(@NonNull RelativeLayout rootView,
      @NonNull HeaderLayoutBinding bindingHeader, @NonNull LinearLayout btnAdd,
      @NonNull ImageView ivSubscribeNow, @NonNull LinearLayout llCreateBacklogList,
      @NonNull LinearLayout llNoData, @NonNull RecyclerView recyclerView,
      @NonNull TextView txtBtn) {
    this.rootView = rootView;
    this.bindingHeader = bindingHeader;
    this.btnAdd = btnAdd;
    this.ivSubscribeNow = ivSubscribeNow;
    this.llCreateBacklogList = llCreateBacklogList;
    this.llNoData = llNoData;
    this.recyclerView = recyclerView;
    this.txtBtn = txtBtn;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityWishlistBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityWishlistBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_wishlist, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityWishlistBinding bind(@NonNull View rootView) {
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

      id = R.id.ivSubscribeNow;
      ImageView ivSubscribeNow = ViewBindings.findChildViewById(rootView, id);
      if (ivSubscribeNow == null) {
        break missingId;
      }

      id = R.id.llCreateBacklogList;
      LinearLayout llCreateBacklogList = ViewBindings.findChildViewById(rootView, id);
      if (llCreateBacklogList == null) {
        break missingId;
      }

      id = R.id.llNoData;
      LinearLayout llNoData = ViewBindings.findChildViewById(rootView, id);
      if (llNoData == null) {
        break missingId;
      }

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.txtBtn;
      TextView txtBtn = ViewBindings.findChildViewById(rootView, id);
      if (txtBtn == null) {
        break missingId;
      }

      return new ActivityWishlistBinding((RelativeLayout) rootView, binding_bindingHeader, btnAdd,
          ivSubscribeNow, llCreateBacklogList, llNoData, recyclerView, txtBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
