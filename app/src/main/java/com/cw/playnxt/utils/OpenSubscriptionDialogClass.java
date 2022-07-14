package com.cw.playnxt.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cw.playnxt.R;
import com.cw.playnxt.activity.SubscriptionActivity;


public  class OpenSubscriptionDialogClass {
    public static void openSubscriptionDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_subscription);
        TextView txt_ok = dialog.findViewById(R.id.txt_ok);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(context, SubscriptionActivity.class);
                context.startActivity(intent);
            }
        });
        dialog.show();
    }
}
