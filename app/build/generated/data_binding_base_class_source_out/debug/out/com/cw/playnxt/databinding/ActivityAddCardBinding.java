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
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAddCardBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final HeaderLayoutBinding bindingHeader;

  @NonNull
  public final LinearLayout btnPayNow;

  @NonNull
  public final CreditCardView creditCardView;

  @NonNull
  public final EditText etAccountHolderName;

  @NonNull
  public final EditText etAccountNo;

  @NonNull
  public final EditText etCVV;

  @NonNull
  public final EditText etExpringDate;

  @NonNull
  public final TextView txtBtn;

  private ActivityAddCardBinding(@NonNull RelativeLayout rootView,
      @NonNull HeaderLayoutBinding bindingHeader, @NonNull LinearLayout btnPayNow,
      @NonNull CreditCardView creditCardView, @NonNull EditText etAccountHolderName,
      @NonNull EditText etAccountNo, @NonNull EditText etCVV, @NonNull EditText etExpringDate,
      @NonNull TextView txtBtn) {
    this.rootView = rootView;
    this.bindingHeader = bindingHeader;
    this.btnPayNow = btnPayNow;
    this.creditCardView = creditCardView;
    this.etAccountHolderName = etAccountHolderName;
    this.etAccountNo = etAccountNo;
    this.etCVV = etCVV;
    this.etExpringDate = etExpringDate;
    this.txtBtn = txtBtn;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAddCardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAddCardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_add_card, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAddCardBinding bind(@NonNull View rootView) {
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

      id = R.id.btnPayNow;
      LinearLayout btnPayNow = ViewBindings.findChildViewById(rootView, id);
      if (btnPayNow == null) {
        break missingId;
      }

      id = R.id.creditCardView;
      CreditCardView creditCardView = ViewBindings.findChildViewById(rootView, id);
      if (creditCardView == null) {
        break missingId;
      }

      id = R.id.etAccountHolderName;
      EditText etAccountHolderName = ViewBindings.findChildViewById(rootView, id);
      if (etAccountHolderName == null) {
        break missingId;
      }

      id = R.id.etAccountNo;
      EditText etAccountNo = ViewBindings.findChildViewById(rootView, id);
      if (etAccountNo == null) {
        break missingId;
      }

      id = R.id.etCVV;
      EditText etCVV = ViewBindings.findChildViewById(rootView, id);
      if (etCVV == null) {
        break missingId;
      }

      id = R.id.etExpringDate;
      EditText etExpringDate = ViewBindings.findChildViewById(rootView, id);
      if (etExpringDate == null) {
        break missingId;
      }

      id = R.id.txtBtn;
      TextView txtBtn = ViewBindings.findChildViewById(rootView, id);
      if (txtBtn == null) {
        break missingId;
      }

      return new ActivityAddCardBinding((RelativeLayout) rootView, binding_bindingHeader, btnPayNow,
          creditCardView, etAccountHolderName, etAccountNo, etCVV, etExpringDate, txtBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
