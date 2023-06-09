// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public final class ActivitySuggestNeFeatureBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final HeaderLayoutBinding bindingHeader;

  @NonNull
  public final LinearLayout btnSubmit;

  @NonNull
  public final EditText etSuggestion;

  @NonNull
  public final TextView txtBtn;

  private ActivitySuggestNeFeatureBinding(@NonNull RelativeLayout rootView,
      @NonNull HeaderLayoutBinding bindingHeader, @NonNull LinearLayout btnSubmit,
      @NonNull EditText etSuggestion, @NonNull TextView txtBtn) {
    this.rootView = rootView;
    this.bindingHeader = bindingHeader;
    this.btnSubmit = btnSubmit;
    this.etSuggestion = etSuggestion;
    this.txtBtn = txtBtn;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySuggestNeFeatureBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySuggestNeFeatureBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_suggest_ne_feature, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySuggestNeFeatureBinding bind(@NonNull View rootView) {
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

      id = R.id.btnSubmit;
      LinearLayout btnSubmit = ViewBindings.findChildViewById(rootView, id);
      if (btnSubmit == null) {
        break missingId;
      }

      id = R.id.etSuggestion;
      EditText etSuggestion = ViewBindings.findChildViewById(rootView, id);
      if (etSuggestion == null) {
        break missingId;
      }

      id = R.id.txtBtn;
      TextView txtBtn = ViewBindings.findChildViewById(rootView, id);
      if (txtBtn == null) {
        break missingId;
      }

      return new ActivitySuggestNeFeatureBinding((RelativeLayout) rootView, binding_bindingHeader,
          btnSubmit, etSuggestion, txtBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
