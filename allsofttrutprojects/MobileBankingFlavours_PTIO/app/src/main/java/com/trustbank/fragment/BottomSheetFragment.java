package com.trustbank.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.trustbank.R;
import com.trustbank.activity.MenuActivity;
import com.trustbank.helper.LocaleHelper;

import java.util.Locale;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    ImageView tickEnglish;
    ImageView tickHindi;
    Locale myLocale;
    Context context=null;

    public BottomSheetFragment(Context context) {
        this.context=context;
    }
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(view);
        tickEnglish = (ImageView) view.findViewById(R.id.tick_english);
        tickEnglish.setVisibility(View.GONE);
        tickHindi = (ImageView) view.findViewById(R.id.tick_hindi);
        tickHindi.setVisibility(View.GONE);

        switch(LocaleHelper.getLanguage(context))
        {
            case "en":
                tickEnglish.setVisibility(View.VISIBLE);
                break;
            case "sp":
                tickHindi.setVisibility(View.VISIBLE);
                break;
            default:
                System.out.println("no match");
        }

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = params.getBehavior();

        ImageView imageViewClose = (ImageView) view.findViewById(R.id.imageView);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert behavior instanceof BottomSheetBehavior;
                ((BottomSheetBehavior<?>) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        ImageView flagEnglish = (ImageView) view.findViewById(R.id.flagView_english);
        flagEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("en",context);
                assert behavior instanceof BottomSheetBehavior;
                ((BottomSheetBehavior<?>) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        ImageView flagHindi = (ImageView) view.findViewById(R.id.flagView_hindi);
        flagHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("sp",context);
                assert behavior instanceof BottomSheetBehavior;
                ((BottomSheetBehavior<?>) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });
    }

    public void setLocale(String localeName, Context context) {
            LocaleHelper.setLocale(context, localeName);
            myLocale = new Locale(localeName);
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf,dm);
            Intent refresh = new Intent(context, MenuActivity.class);
            refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(refresh);
    }
}
