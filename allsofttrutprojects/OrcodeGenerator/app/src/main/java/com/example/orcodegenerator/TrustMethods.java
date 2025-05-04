package com.example.orcodegenerator;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;

import com.example.orcodegenerator.Interfaces.AlertDialogOKListner;

import java.util.Objects;

public class TrustMethods {
    public void qr_token(Context c,String key, String value){
        SharedPreferences preferences = c.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String retreveqr_token(Context ctx,String key){
        SharedPreferences sh = ctx.getSharedPreferences("MyAppPreferences",Context.MODE_PRIVATE);
        String retrievedValue = sh.getString( key, "");
        return retrievedValue;
    }

    public static void alertDialogOk(Context context, String title, String message,
                                     String button, final int resultCode, boolean setCancel,
                                     final AlertDialogOKListner alertDialogOkListener) {

        try {
//
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
