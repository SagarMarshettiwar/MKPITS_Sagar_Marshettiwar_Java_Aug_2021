package com.trustbank.util;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogListener;
import com.trustbank.interfaces.AlertDialogOkListener;

import java.util.Objects;

public class AlertDialogMethod {
    private Context mContext;
    private final Activity activity;

    public AlertDialogMethod(Context context) {
        this.mContext = context;
        this.activity = (Activity) context;

    }
    /*  Use this dialog for two buttons*/
    /*public static void alertDialog(Context context, String title, String message, String positiveButton,
                                   String negativeButton, final int resultCode, boolean setCancel,
                                   final AlertDialogListener alertDialogListener1) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setCancelable(setCancel);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(positiveButton,
                (dialog, arg1) -> {
                });

        if (!TextUtils.isEmpty(negativeButton)){
            alert.setNegativeButton(negativeButton,
                    (dialog, arg1) -> {
                    });
        }

        final AlertDialog alertDialog1 = alert.create();
//        Objects.requireNonNull(alertDialog1.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            alertDialogListener1.onDialogOk(resultCode);
            if (alertDialog1.isShowing()){
                alertDialog1.dismiss();
            }

        });
        alertDialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(view -> {
            alertDialogListener1.onDialogCancel(resultCode);
            alertDialog1.dismiss();
        });
    }*/
    public static void alertDialog(Context context, String title, String message, String positiveButton,
                                   String negativeButton, final int resultCode, boolean setCancel,
                                   final AlertDialogListener alertDialogListener1) {
        View view;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        view = LayoutInflater.from(context).inflate(R.layout.layoutwarningdialog,null);
        builder.setView(view);
        builder.setCancelable(false);
        if(title.equalsIgnoreCase("")){
            ((TextView) view.findViewById(R.id.textTitle)).setVisibility(View.GONE);
        }
        if(negativeButton.equalsIgnoreCase("")){
            ((TextView) view.findViewById(R.id.buttonNo)).setVisibility(View.GONE);
        }
        ((TextView) view.findViewById(R.id.textTitle)).setText(title);
        ((TextView) view.findViewById(R.id.textMessage)).setText(message);
        ((Button) view.findViewById(R.id.buttonYes)).setText(positiveButton);
        ((Button) view.findViewById(R.id.buttonNo)).setText(negativeButton);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogListener1.onDialogOk(resultCode);
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogListener1.onDialogCancel(resultCode);
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    /*
    Use this dialog for single button
   */
   /* public static void alertDialogOk(Context context, String title, String message,
                                     String button, final int resultCode, boolean setCancel,
                                     final AlertDialogOkListener alertDialogOkListener) {

        try {
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
    }*/
    public static void alertDialogOk(Context context, String title, String message,
                                     String button, final int resultCode, boolean setCancel,
                                     final AlertDialogOkListener alertDialogOkListener) {

        View view;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        view = LayoutInflater.from(context).inflate(R.layout.layoutwarningdialog,null);
        builder.setView(view);
        builder.setCancelable(false);
        if(title.equalsIgnoreCase("")){
            ((TextView) view.findViewById(R.id.textTitle)).setVisibility(View.GONE);
        }
        ((TextView) view.findViewById(R.id.textTitle)).setText(title);
        ((TextView) view.findViewById(R.id.textMessage)).setText(message);
        ((Button) view.findViewById(R.id.buttonYes)).setText(button);
        ((Button) view.findViewById(R.id.buttonNo)).setVisibility(View.GONE);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogOkListener.onDialogOk(resultCode);
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
