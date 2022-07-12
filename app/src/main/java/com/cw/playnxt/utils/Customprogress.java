package com.cw.playnxt.utils;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import com.cw.playnxt.R;

public class Customprogress {


    private static Dialog progressDialog = null;
    private static ProgressBar progressBar;


    public static void showPopupProgressSpinner(Context context, Boolean isShowing) {
        if (isShowing == true) {
            progressDialog = new Dialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.popup_progressbar);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressBar = (ProgressBar) progressDialog.findViewById(R.id.progressBar1);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#E26B43"), android.graphics.PorterDuff.Mode.MULTIPLY);
            progressDialog.show();
            progressDialog.show();

        } else if (isShowing == false) {
            progressDialog.dismiss();
        }
    }

}
