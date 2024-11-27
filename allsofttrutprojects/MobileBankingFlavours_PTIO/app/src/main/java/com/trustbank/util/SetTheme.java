package com.trustbank.util;

import android.app.Activity;
import android.content.Context;
import com.trustbank.R;


public class SetTheme {

    private static int sTheme;
    public final static int THEME_BLUE = 1;
    public final static int THEME_BLUE_NO_ACTION_BAR = 4;

    Activity activity;
    int theme;
    private static SharePreferenceUtils sharePreferenceUtils;

    public static void changeToTheme(Context activity, boolean isNoActionBar) {
        sharePreferenceUtils = new SharePreferenceUtils(activity);
        if (activity.getPackageName().equals("com.trustbank.ptio")) {
            sTheme = 1;
        }

        switch (sTheme) {
            case 1:
                if (isNoActionBar) {
                    onActivityCreateSetTheme(activity, SetTheme.THEME_BLUE_NO_ACTION_BAR);
                } else {
                    onActivityCreateSetTheme(activity, SetTheme.THEME_BLUE);
                }
                break;
        }

    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Context activity, int theme) {
        switch (theme) {
            case THEME_BLUE:
                activity.setTheme(R.style.AppThemeBlue);
                break;
            case THEME_BLUE_NO_ACTION_BAR:
                activity.setTheme(R.style.NoActionBarThemeBlue);
                break;
            default:
                activity.setTheme(R.style.AppThemeBlue);
                break;
        }
    }
}
