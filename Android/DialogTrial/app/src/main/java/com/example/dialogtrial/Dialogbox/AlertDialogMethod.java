package com.example.dialogtrial.Dialogbox;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.example.dialogtrial.Interfaces.AlertDialogOkListener;
import com.example.dialogtrial.R;

import java.util.Objects;

public class AlertDialogMethod {
    private Context mContext;
    private final Activity activity;

    public AlertDialogMethod(Context context) {
        this.mContext = context;
        this.activity = (Activity) context;
    }
    /*
    Use this dialog for single button
   */
    public static void alertDialogOk(Context context, String title, String message,
                                     String button, final int resultCode, boolean setCancel,
                                     final AlertDialogOkListener alertDialogOkListener) {

        try {
//            final AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.customDialogue);
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setCancelable(setCancel);
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setPositiveButton(button,
                    (dialog, arg1) -> {
                    });

            final AlertDialog alertDialog = alert.create();
            Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.dialogTheme;
            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                if (alertDialogOkListener != null) {
                    alertDialogOkListener.onDialogOk(resultCode);
                    alertDialog.dismiss();
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
