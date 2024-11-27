package com.trustbank.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Model.CheckSimInfoModel;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.google.gson.Gson;
import com.trustbank.activity.SplashScreenActivity;
import com.trustbank.activity.VerifyMobileNumber;
import com.trustbank.adapter.FundsTransferAdapter;
import com.trustbank.adapter.HorizontalMenuAdapter;
import com.trustbank.helper.LocaleHelper;
import com.trustbank.interfaces.AlertDialogOkListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.trustbank.util.AppConstants.DISPLAY_NAME;
import static com.trustbank.util.AppConstants.LoginInfo_isHookedDeviceDetectionEnabled;
import static com.trustbank.util.AppConstants.LoginInfo_isLogEnabled;
import static com.trustbank.util.AppConstants.LoginInfo_isRootDetectionEnabled;
import static com.trustbank.util.AppConstants.MOB_NO;
import static com.trustbank.util.AppConstants.SIM_NUMBER;
import static com.trustbank.util.AppConstants.SIM_SUBSCRIPTION_ID;
import static com.trustbank.util.AppConstants.SLOT_INDEX;
import static com.trustbank.util.AppConstants.isAutoReadOTPEnabled;

@SuppressLint({"SimpleDateFormat", "Registered"})
public class TrustMethods extends Activity {
    public static final String PREFS_NAME = "ACCOUNT_LIST";
    public static final String Fingure_status = "Fingure_status";
    public static final String CEDULA_ID = "CEDULA_ID";
    public static final String BEN_ACC_LIST = "BEN_ACC_LIST";
    public static final String Profileset = "Profile_status";
    TextView editTextDate;
    public int mYear;
    public int mMonth;
    public int mDay;
    public String months;
    public String calenderlang;
    Context mContext;
    Activity activity;
    private static String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public TrustMethods(Context context) {
        this.mContext = context;
        this.activity = (Activity) context;
    }

    public void timePicker(final Context context, TextView editText) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                String AM_PM ;
                if(hourOfDay < 12) {
                    AM_PM = "AM";
                } else {
                    AM_PM = "PM";
                }
                editText.setText(hourOfDay + ":" + minute + AM_PM);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    public void datePickerymd(Context context, TextView editText) {
        String lang = LocaleHelper.getPersistedData(context, "en");
       LocaleHelper.setLocale(context, lang);

        if(lang.equalsIgnoreCase("sp")){
            calenderlang="es";
        }else{
             calenderlang="en";
        }
        Locale locale = new Locale(calenderlang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        /* Context context = this; // Use the activity context here*/
        /* Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(config, displayMetrics);*/

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        editTextDate = editText;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;

            switch (mMonth) {
                case 0:
                    months = "Jan";
                    break;
                case 1:
                    months = "Feb";
                    break;
                case 2:
                    months = "Mar";
                    break;
                case 3:
                    months = "Apr";
                    break;
                case 4:
                    months = "May";
                    break;
                case 5:
                    months = "Jun";
                    break;
                case 6:
                    months = "Jul";
                    break;
                case 7:
                    months = "Aug";
                    break;
                case 8:
                    months = "Sep";
                    break;
                case 9:
                    months = "Oct";
                    break;
                case 10:
                    months = "Nov";
                    break;
                case 11:
                    months = "Dec";
                    break;
                default:
                    break;
            }
            if (view.isShown()) {
                String day = String.valueOf(mDay).trim();
                String month = String.valueOf(mMonth).trim();
                if (day.length() == 1) {
                    day = "0" + day;
                }
                if (month.length() == 1) {
                    month = "0" + month;
                }
                editTextDate.setText(mYear + "/" + month + "/" + day);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void datePickermdy(final Context context, TextView editText) {
        String lang = LocaleHelper.getPersistedData(context, "en");
        LocaleHelper.setLocale(context, lang);

        if(lang.equalsIgnoreCase("sp")){
            calenderlang="es";
        }else{
            calenderlang="en";
        }
        Locale locale = new Locale(calenderlang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        /*  Context context = this; // Use the activity context here*/
       /* Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(config, displayMetrics);*/

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        editTextDate = editText;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;

            switch (mMonth) {
                case 0:
                    months = "Jan";
                    break;
                case 1:
                    months = "Feb";
                    break;
                case 2:
                    months = "Mar";
                    break;
                case 3:
                    months = "Apr";
                    break;
                case 4:
                    months = "May";
                    break;
                case 5:
                    months = "Jun";
                    break;
                case 6:
                    months = "Jul";
                    break;
                case 7:
                    months = "Aug";
                    break;
                case 8:
                    months = "Sep";
                    break;
                case 9:
                    months = "Oct";
                    break;
                case 10:
                    months = "Nov";
                    break;
                case 11:
                    months = "Dec";
                    break;
                default:
                    break;
            }
            if (view.isShown()) {
                String day = String.valueOf(mDay).trim();
                String month = String.valueOf(mMonth).trim();
                if (day.length() == 1) {
                    day = "0" + day;
                }
                if (month.length() == 1) {
                    month = "0" + month;
                }
                editTextDate.setText(month + "/" + day + "/" + mYear);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void datePicker(final Context context, TextView editText) {
        String lang = LocaleHelper.getPersistedData(context, "en");
        LocaleHelper.setLocale(context, lang);

        if(lang.equalsIgnoreCase("sp")){
            calenderlang="es";
        }else{
            calenderlang="en";
        }
        Locale locale = new Locale(calenderlang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        /*  Context context = this; // Use the activity context here*/
       /* Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(config, displayMetrics);*/

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        editTextDate = editText;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;

            switch (mMonth) {
                case 0:
                    months = "Jan";
                    break;
                case 1:
                    months = "Feb";
                    break;
                case 2:
                    months = "Mar";
                    break;
                case 3:
                    months = "Apr";
                    break;
                case 4:
                    months = "May";
                    break;
                case 5:
                    months = "Jun";
                    break;
                case 6:
                    months = "Jul";
                    break;
                case 7:
                    months = "Aug";
                    break;
                case 8:
                    months = "Sep";
                    break;
                case 9:
                    months = "Oct";
                    break;
                case 10:
                    months = "Nov";
                    break;
                case 11:
                    months = "Dec";
                    break;
                default:
                    break;
            }
            if (view.isShown()) {
                String day = String.valueOf(mDay).trim();
                String month = String.valueOf(mMonth).trim();
                if (day.length() == 1) {
                    day = "0" + day;
                }
                if (month.length() == 1) {
                    month = "0" + month;
                }
                editTextDate.setText(day + "/" + month + "/" + mYear);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void datePickerDisableFuturesDate(final Context context, TextView editText) {
        String lang = LocaleHelper.getPersistedData(context, "en");
        LocaleHelper.setLocale(context, lang);

        if(lang.equalsIgnoreCase("sp")){
            calenderlang="es";
        }else{
            calenderlang="en";
        }
        Locale locale = new Locale(calenderlang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        /*  Context context = this; // Use the activity context here*/
       /* Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(config, displayMetrics);*/

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        editTextDate = editText;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;

            switch (mMonth) {
                case 0:
                    months = "Jan";
                    break;
                case 1:
                    months = "Feb";
                    break;
                case 2:
                    months = "Mar";
                    break;
                case 3:
                    months = "Apr";
                    break;
                case 4:
                    months = "May";
                    break;
                case 5:
                    months = "Jun";
                    break;
                case 6:
                    months = "Jul";
                    break;
                case 7:
                    months = "Aug";
                    break;
                case 8:
                    months = "Sep";
                    break;
                case 9:
                    months = "Oct";
                    break;
                case 10:
                    months = "Nov";
                    break;
                case 11:
                    months = "Dec";
                    break;
                default:
                    break;
            }
            if (view.isShown()) {
                String day = String.valueOf(mDay).trim();
                String month = String.valueOf(mMonth).trim();
                if (day.length() == 1) {
                    day = "0" + day;
                }
                if (month.length() == 1) {
                    month = "0" + month;
                }
                editTextDate.setText(day + "/" + month + "/" + mYear);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    public static void message(Context context, String toastMessage) {
        Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static String getMonthYear() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return df.format(c.getTime());
    }

    public void activityOpenAnimation() {
        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void activityCloseAnimation() {
        activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    public static void showMessage(Context context, String toastMessage) {
        Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showSnackBarMessage(String message, CoordinatorLayout coordinatorLayout) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, 7000);
        snackbar.setActionTextColor(Color.RED);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }
    public static void customSnackbar(Context context, String message, CoordinatorLayout coordinatorLayout, String action) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE).setAction(action, view -> {
        });
        snackbar.setActionTextColor(Color.YELLOW);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
    public static boolean validateEmailAddress(String emailAddress) {
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
    public static boolean validateEmail(String emailAddress) {
        if (!emailAddress.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
           return true;
        } else {
           return false;
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (activity.getCurrentFocus() != null && inputManager != null) {
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//        }
    }
    public void setProfilepicture(Context context, Bitmap v) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            v.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String encoded = Base64.encodeToString(b, Base64.DEFAULT);
            SharedPreferences sharedPreferences = context.getSharedPreferences(Profileset, MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.clear();
            myEdit.putString("status", encoded);
            myEdit.apply();
            myEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String GetProfilepicture(Context context) {
        try {
            SharedPreferences sh = context.getSharedPreferences(Profileset, MODE_PRIVATE);
            String s1 = sh.getString("status", "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");
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String amountInWords(String amount){

        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(Locale.US ,RuleBasedNumberFormat.SPELLOUT);
        String amountInWords="";
        if(amount.contains(".")){
            if(amount.charAt(amount.length()-1) != '.') {
                String[] arr = amount.replaceAll(",", "").split("\\.");
                String len = arr.length + "";
                Log.e("length", len);

                String str1 = ruleBasedNumberFormat.format(Double.parseDouble(arr[0]));
                Log.e("String1", str1);
                amountInWords = str1 + " dollars and ";

                String str2 = ruleBasedNumberFormat.format(Double.parseDouble(arr[1]));
                Log.e("String2", str2);
                amountInWords += str2 + " cents";
            }
        }else {
            amountInWords = ruleBasedNumberFormat.format(Double.parseDouble(amount.replaceAll(",", ""))) + " dollars";
            Log.e("AMOunt", amountInWords);
        }
        Log.e("Amount in Words", amountInWords);

        return amountInWords;
    }

    public void Setfingureprintpref(Context context, Boolean v) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Fingure_status, MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.clear();
            myEdit.putBoolean("status", v);
            myEdit.apply();
            myEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean Getfingureprintpref(Context context) {
        try {
            SharedPreferences sh = context.getSharedPreferences(Fingure_status, MODE_PRIVATE);
            Boolean s1 = sh.getBoolean("status", false);
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //SharedPreferences Method to get & set arrayList.....
    public void saveArrayList(Context context, ArrayList<GetUserProfileModal> list, String key) {
        try {
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            editor = settings.edit();
            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(list);
            editor.putString(key, jsonFavorites);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveCedulaId(Context context, String key, String cedulaId){
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(CEDULA_ID, MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.clear();
            myEdit.putString(key, cedulaId);
            myEdit.apply();
            myEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getCedulaId(Context context, String key){
        try {
            SharedPreferences sh = context.getSharedPreferences(CEDULA_ID, MODE_PRIVATE);
            String s1 = sh.getString(key, "");
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //for amount in words
    public String amountInWords(Context ctx, String amount){

        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(Locale.US ,RuleBasedNumberFormat.SPELLOUT);
        String amountInWords="";
        if(amount.contains(".")){
            if(amount.charAt(amount.length()-1) != '.') {
                String[] arr = amount.replaceAll(",", "").split("\\.");
                String len = arr.length + "";
                Log.e("length", len);

                String str1 = ruleBasedNumberFormat.format(Double.parseDouble(arr[0]));
                Log.e("String1", str1);
                amountInWords = str1 + " dollars and ";

                String str2 = ruleBasedNumberFormat.format(Double.parseDouble(arr[1]));
                Log.e("String2", str2);
                amountInWords += str2 + " cents";
            }
        }else {
            amountInWords = ruleBasedNumberFormat.format(Double.parseDouble(amount.replaceAll(",", ""))) + " dollars";
            Log.e("AMOunt", amountInWords);
        }

        Log.e("Amount in Words", amountInWords);

        return amountInWords;
    }

    //SharedPreferences Method to get & set arrayList.....
    public void saveBenArrayList(Context context, ArrayList<BeneficiaryModal> list, String key) {
        try {
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            settings = context.getSharedPreferences(BEN_ACC_LIST, Context.MODE_PRIVATE);
            editor = settings.edit();
            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(list);
            editor.putString(key, jsonFavorites);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void clearAccountsArrayList(Context context) {
        try {
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            editor = settings.edit();
            editor.clear();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GetUserProfileModal> getArrayList(Context context, String key) {
        SharedPreferences settings;
        List<GetUserProfileModal> favorites;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (settings.contains(key)) {
            String jsonFavorites = settings.getString(key, null);
            Gson gson = new Gson();
            GetUserProfileModal[] favoriteItems = gson.fromJson(jsonFavorites, GetUserProfileModal[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<GetUserProfileModal>(favorites);
        } else return null;
        return (ArrayList<GetUserProfileModal>) favorites;
    }

    //get ben from shared pref
    public ArrayList<BeneficiaryModal> getBenArrayList(Context context, String key) {
        SharedPreferences settings;
        List<BeneficiaryModal> favorites;
        settings = context.getSharedPreferences(BEN_ACC_LIST, Context.MODE_PRIVATE);

        if (settings.contains(key)) {
            String jsonFavorites = settings.getString(key, null);
            Gson gson = new Gson();
            BeneficiaryModal[] favoriteItems = gson.fromJson(jsonFavorites, BeneficiaryModal[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<BeneficiaryModal>(favorites);
        } else return null;
        return (ArrayList<BeneficiaryModal>) favorites;
    }

    public boolean checkLocation(Context context) {
        LocationManager mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public static String getVersionName(Context context) {
        String imeiNo = "";
        try {
            imeiNo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeiNo;
    }

    public void refreshToken(Context context) {
        Toast.makeText(getApplicationContext(), "Refresh Token Call", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String decodeBase64(String decodeString) {
        byte[] responseByteArray = Base64.decode(decodeString, Base64.DEFAULT);
        return new String(responseByteArray, StandardCharsets.UTF_8);
    }

    //Detect OTP
    public static String getVerificationCode(String message, String senderAddress, String fromActivity) {
        String code = null;
        if (fromActivity.equalsIgnoreCase("verifyMobileNumber")) {
            int index = message.indexOf(AppConstants.REG_CODE_MSG);
            if (index != -1) {
                int start = index + 18;//4
                int length = 6;
                code = message.substring(start, start + length);
                if (code.matches("[0-9]+") && code.length() == 6) {
                    return code;
                } else {
                    int start1 = index + 4;//18
                    int length1 = 6;
                    code = message.substring(start1, start1 + length1);
                    if (code.matches("[0-9]+") && code.length() == 6) {
                        return code;
                    } else {
                        return "NA";
                    }
                }

            }
        }
        return code;
    }
    public boolean isValidImeI(String ImeiNo) {
        boolean check;
        if (!Pattern.matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$", ImeiNo)) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }

    public boolean isValidAccNo(String accno, Context context) {
        boolean check = false;
        try {
            if (!Pattern.matches("[0-9a-zA-Z]{9,18}", accno)) {
                check = false;
            } else {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void LogMessage(String TAG, String message) {
        if (LoginInfo_isLogEnabled) {
            Log.e(TAG, message);
        }
    }

    public static void systemMessage(String systemMessage) {
        if (LoginInfo_isLogEnabled) {
            System.out.println(systemMessage);
        }
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getDeviceID(Context context) {
        try {
            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = "";
                if (Objects.requireNonNull(telephonyManager).getDeviceId() != null) {
                    imei = telephonyManager.getDeviceId();
                }
                String iccId = getICCID(context);
                String androidId = getAndroidID(context);
                return prepareJsonObjectForDeviceID("imei", imei, iccId, androidId, context);
            } else {
                String iccId = "";
                List<SubscriptionInfo> subsList = null;
                SubscriptionManager subsManager = null;
                subsManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                subsList = Objects.requireNonNull(subsManager).getActiveSubscriptionInfoList();
                iccId = subsList.get(0).getIccId();
                String androidId = getAndroidID(context);
                return prepareJsonObjectForDeviceID("imei", "", iccId, androidId, context);
            }
        } catch (Exception e) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (Objects.requireNonNull(telephonyManager).getDeviceId() != null && !TextUtils.isEmpty(telephonyManager.getDeviceId())) {
                    String iccId = getICCID(context);
                    String androidId = getAndroidID(context);
                    return prepareJsonObjectForDeviceID("imei", telephonyManager.getDeviceId(), iccId, androidId, context);
                } else {
                    try {
                        String iccId = getICCID(context);
                        String androidId = getAndroidID(context);
                        return prepareJsonObjectForDeviceID("imei", "", iccId, androidId, context);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        String androidId = getAndroidID(context);
                        return prepareJsonObjectForDeviceID("imei", "", "", androidId, context);
                    }

                }
            } catch (Exception ex) {
                try {
                    String iccId = getICCID(context);
                    String androidId = getAndroidID(context);
                    return prepareJsonObjectForDeviceID("imei", "", iccId, androidId, context);
                } catch (Exception ex1) {
                    String androidId = getAndroidID(context);
                    return prepareJsonObjectForDeviceID("imei", "", "", androidId, context);
                }
            }

        }
    }

    @SuppressLint("MissingPermission")
    public static String getICCID(Context context) {
        String iccId = "";
        try {
            List<SubscriptionInfo> subsList = null;
            SubscriptionManager subsManager = null;
            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                subsManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                if (subsManager != null) {
                    if (subsManager.getActiveSubscriptionInfoList() != null) {
                        subsList = subsManager.getActiveSubscriptionInfoList();
                        if (!TextUtils.isEmpty(subsList.get(0).getIccId())) {
                            iccId = subsList.get(0).getIccId();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
        return iccId;
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String prepareJsonObjectForDeviceID(String type, String imei, String iccId, String androidId, Context context) {
        try {
            String androidVersion = Build.VERSION.RELEASE;

            String versionName = TrustMethods.getVersionName(context);
            JSONObject jsonObject = new JSONObject();
//            if (type.equals("imei")) {
            jsonObject.put("imei", imei);
            jsonObject.put("icc_no", iccId);
            jsonObject.put("android_id", androidId);
            jsonObject.put("application_version", versionName);
            jsonObject.put("android_version", androidVersion);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}";
        }

    }
    public static boolean isRooted(Context context) {
        if (LoginInfo_isRootDetectionEnabled) {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec("su");
                return true;
            } catch (Exception e) {
                return false;
            } finally {
                if (process != null) {
                    try {
                        process.destroy();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            return false;
        }
    }
    public static boolean isHookUpDevice(Context context) {
        boolean isHookuDevice = false;
        if (LoginInfo_isHookedDeviceDetectionEnabled) {
            try {
                PackageManager packageManager = context.getPackageManager();
                List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
                for (ApplicationInfo applicationInfo : applicationInfoList) {
                    if (applicationInfo.packageName.equals("de.robv.android.xposed.installer")) {
                        Log.wtf("HookDetection", "Xposed found on the system.");
                        isHookuDevice = true;
                        return isHookuDevice;

                    } else if (applicationInfo.packageName.equals("com.saurik.substrate")) {
                        Log.wtf("HookDetection", "Substrate found on the system.");
                        isHookuDevice = true;
                        return isHookuDevice;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return isHookuDevice;
            }
        }
        return isHookuDevice;
    }


    @SuppressLint("MissingPermission")
    public static boolean isSimAvailable(Context context) {
        if (isAutoReadOTPEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                SubscriptionManager sManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                SubscriptionInfo infoSim1 = sManager.getActiveSubscriptionInfoForSimSlotIndex(0);
                SubscriptionInfo infoSim2 = sManager.getActiveSubscriptionInfoForSimSlotIndex(1);
                if (infoSim1 != null || infoSim2 != null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                //========getting only defualt sim one serial number===============//
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager.getSimSerialNumber() != null) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static boolean isSimDetected(FragmentActivity activity, int sub_id, String mobile_no) {

        if (isAutoReadOTPEnabled) {
            CheckSimInfoModel checkSimInfoModel = new CheckSimInfoModel();
            if (sub_id != -1) {

                checkSimInfoModel.setSIM_SUBSCRIPTION_ID(String.valueOf(sub_id));
                SubscriptionInfo infoSim1;
                SubscriptionInfo infoSim2;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    SubscriptionManager sManager = (SubscriptionManager) activity.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                    infoSim1 = sManager.getActiveSubscriptionInfoForSimSlotIndex(0);
                    infoSim2 = sManager.getActiveSubscriptionInfoForSimSlotIndex(1);
                    if (infoSim1 != null || infoSim2 != null) {
                        if (infoSim1 != null) {
                            if (Objects.requireNonNull(infoSim1).getSubscriptionId() == sub_id) {
                                checkSimInfoModel.setDisplayName((String) infoSim1.getDisplayName());
                                checkSimInfoModel.setSimSerialNumber(String.valueOf(infoSim1.getIccId()));
                                checkSimInfoModel.setSlot(String.valueOf(infoSim1.getSimSlotIndex()));
                                checkSimInfoModel.setMobileNumber(mobile_no);
                                setSimDetails(activity, checkSimInfoModel);
                                return true;
                            } else if (infoSim2 != null) {
                                if (Objects.requireNonNull(infoSim2).getSubscriptionId() == sub_id) {
                                    checkSimInfoModel.setDisplayName((String) infoSim2.getDisplayName());
                                    checkSimInfoModel.setSimSerialNumber(String.valueOf(infoSim2.getIccId()));
                                    checkSimInfoModel.setSlot(String.valueOf(infoSim2.getSimSlotIndex()));
                                    checkSimInfoModel.setMobileNumber(mobile_no);
                                    setSimDetails(activity, checkSimInfoModel);
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else if (infoSim2 != null) {
                            if (Objects.requireNonNull(infoSim2).getSubscriptionId() == sub_id) {
                                checkSimInfoModel.setDisplayName((String) infoSim2.getDisplayName());
                                checkSimInfoModel.setSimSerialNumber(String.valueOf(infoSim2.getIccId()));
                                checkSimInfoModel.setSlot(String.valueOf(infoSim2.getSimSlotIndex()));
                                checkSimInfoModel.setMobileNumber(mobile_no);
                                setSimDetails(activity, checkSimInfoModel);
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    //========getting only defualt sim one serial number===============//
                    TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager.getSimSerialNumber() != null) {
                        if (Objects.requireNonNull(telephonyManager.getSubscriberId()).equals(String.valueOf(sub_id))) {
                            checkSimInfoModel.setDisplayName(telephonyManager.getSimOperatorName());
                            checkSimInfoModel.setSimSerialNumber(telephonyManager.getSimSerialNumber());
                            checkSimInfoModel.setSlot("0");
                            checkSimInfoModel.setMobileNumber(mobile_no);
                            setSimDetails(activity, checkSimInfoModel);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }

            }

            return false;
        } else {
            return true;
        }

    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static boolean isSimVerified(FragmentActivity activity) {
        if (isAutoReadOTPEnabled) {
            CheckSimInfoModel checkSimInfoModel = getSimDetails(activity);
            if (!checkSimInfoModel.getSIM_SUBSCRIPTION_ID().equals("-1")) {
                int sub_id = Integer.parseInt(checkSimInfoModel.getSIM_SUBSCRIPTION_ID());
                int slot = Integer.parseInt(checkSimInfoModel.getSlot());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    SubscriptionManager sManager = (SubscriptionManager) activity.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                    SubscriptionInfo infoSim1 = sManager.getActiveSubscriptionInfoForSimSlotIndex(0);
                    SubscriptionInfo infoSim2 = sManager.getActiveSubscriptionInfoForSimSlotIndex(1);
                    if (infoSim1 != null || infoSim2 != null) {
                        if (infoSim1 != null) {
                            if (Objects.requireNonNull(infoSim1).getSubscriptionId() == sub_id && Objects.requireNonNull(infoSim1).getSimSlotIndex() == slot) {
                                if (checkSimInfoModel.getSimSerialNumber().equals(String.valueOf(infoSim1.getIccId()))) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else if (infoSim2 != null) {
                                if (Objects.requireNonNull(infoSim2).getSubscriptionId() == sub_id && Objects.requireNonNull(infoSim2).getSimSlotIndex() == slot) {
                                    if (checkSimInfoModel.getSimSerialNumber().equals(String.valueOf(infoSim2.getIccId()))) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else if (infoSim2 != null) {
                            if (Objects.requireNonNull(infoSim2).getSubscriptionId() == sub_id && Objects.requireNonNull(infoSim2).getSimSlotIndex() == slot) {
                                if (checkSimInfoModel.getSimSerialNumber().equals(String.valueOf(infoSim2.getIccId()))) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    //========getting only defualt sim one serial number===============//
                    TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager.getSimSerialNumber() != null) {
                        if (Objects.requireNonNull(telephonyManager.getSubscriberId()).equals(String.valueOf(sub_id)) && "0".equals(String.valueOf(slot))) {
                            if (checkSimInfoModel.getSimSerialNumber().equals(String.valueOf(telephonyManager.getSimSerialNumber()))) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public static void displaySimErrorDialog(FragmentActivity activity) {
        if (!isSimAvailable(activity.getApplicationContext())) {
            AlertDialogMethod.alertDialogOk(activity, "NO SIM Card", "No " +
                            "Sim Card Not Detected. Please insert the sim in slot and try again",
                    activity.getResources().getString(R.string.btn_ok), 1, false, new AlertDialogOkListener() {
                        @Override
                        public void onDialogOk(int resultCode) {
                            if (resultCode == 1) {
                                System.exit(0);
                            }
                        }
                    });
        } else if (!TrustMethods.isSimVerified(activity)) {
            CheckSimInfoModel checkSimInfoModel = TrustMethods.getSimDetails(activity);
            AlertDialogMethod.alertDialogOk(activity, "SIM not detected for mobile number " + checkSimInfoModel.getMobileNumber(), "" +
                            "Click ok to register with new number",
            activity.getResources().getString(R.string.btn_ok), 0, false, new AlertDialogOkListener() {
                @Override
                public void onDialogOk(int resultCode) {
                    if (resultCode == 0) {
                        Intent intent = new Intent(activity, VerifyMobileNumber.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                }
            });
        }
    }

    public static void setSimDetails(FragmentActivity activity, CheckSimInfoModel checkSimInfoModel) {
        SharePreferenceUtils sharePreferenceUtils = new SharePreferenceUtils(activity.getApplicationContext());
        sharePreferenceUtils.putString(DISPLAY_NAME, checkSimInfoModel.getDisplayName());
        sharePreferenceUtils.putString(SIM_NUMBER, checkSimInfoModel.getSimSerialNumber());
        sharePreferenceUtils.putString(SLOT_INDEX, checkSimInfoModel.getSlot());
        sharePreferenceUtils.putInteger(SIM_SUBSCRIPTION_ID, Integer.parseInt(checkSimInfoModel.getSIM_SUBSCRIPTION_ID()));
        sharePreferenceUtils.putString(MOB_NO, checkSimInfoModel.getMobileNumber());
    }
    public static CheckSimInfoModel getSimDetails(FragmentActivity activity) {
        SharePreferenceUtils sharePreferenceUtils = new SharePreferenceUtils(activity.getApplicationContext());
        CheckSimInfoModel checkSimInfoModel = new CheckSimInfoModel();
        checkSimInfoModel.setDisplayName(sharePreferenceUtils.getString(DISPLAY_NAME));
        checkSimInfoModel.setSimSerialNumber(sharePreferenceUtils.getString(SIM_NUMBER));
        checkSimInfoModel.setSlot(sharePreferenceUtils.getString(SLOT_INDEX));
        checkSimInfoModel.setSIM_SUBSCRIPTION_ID(String.valueOf(sharePreferenceUtils.getInt(SIM_SUBSCRIPTION_ID, -1)));
        checkSimInfoModel.setMobileNumber(sharePreferenceUtils.getString(MOB_NO));
        return checkSimInfoModel;
    }
    public static String formatDate(String date, String initDateFormat, String endDateFormat) {

        try {
            Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
            assert initDate != null;
            return formatter.format(initDate);
        } catch (ParseException e) {
            return e.getMessage();
        }
    }
    public static String subString(String value, String concateWith) {
        return value.substring(0, value.length() - concateWith.length()) + concateWith;
    }
    public static boolean isSessionExpired(String errorMsg) {
        try {
            return !TextUtils.isEmpty(errorMsg) && (errorMsg.equalsIgnoreCase("9004") || errorMsg.equalsIgnoreCase("9006") || errorMsg.equalsIgnoreCase("9003"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isLogoutSessionExpired(String errorMsg) {
        try {
            return !TextUtils.isEmpty(errorMsg) && (errorMsg.equalsIgnoreCase("9004") || errorMsg.equalsIgnoreCase("9006") || errorMsg.equalsIgnoreCase("9003") || errorMsg.equalsIgnoreCase("9002"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isSessionExpiredWithString(String errorMsg) {

        try {
            return errorMsg.equalsIgnoreCase("Invalid Auth Token.") || errorMsg.equalsIgnoreCase("Auth Token Expired.") || errorMsg.equalsIgnoreCase("Old Token Expired.");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isFromDateGreaterThanToDate(String strFromDate, String strToDate) {
        boolean isToDateGreater = false;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = formatter.parse(strFromDate);
            Date toDate = formatter.parse(strToDate);
            if (fromDate != null && toDate != null) {
                if (toDate.compareTo(fromDate) < 0) {
                    System.out.println("fromDate is Greater than my toDate");
                    isToDateGreater = true;
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
            isToDateGreater = false;
        }
        return isToDateGreater;
    }

    public static void showBackButtonAlert(Context context) {
        try {
            AlertDialogMethod.alertDialogOk(context, "Info", context.getResources().getString(R.string.msg_back_button), context.getResources().getString(R.string.btn_ok),
                    3, false, resultCode -> {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAccountTypeValid(String acctType) {
        return acctType.equalsIgnoreCase("4");
    }

    public static boolean isAccountTypeValidstatement(String acctType) {
        return acctType.equalsIgnoreCase("4") || acctType.equalsIgnoreCase("5")
                || acctType.equalsIgnoreCase("6");
    }
    public static boolean savingACC(String acctType) {
        return acctType.equalsIgnoreCase("4");
    }
    public static boolean investmentACC(String acctType) {
        return acctType.equalsIgnoreCase("5");
    }
    public static boolean creditACC(String acctType) {
        return acctType.equalsIgnoreCase("6");
    }

    public static boolean isAccountTypeIsImpsRegValid(String acctType, String impsReg) {
        return (acctType.equalsIgnoreCase("78") || acctType.equalsIgnoreCase("79")
                || acctType.equalsIgnoreCase("82")) && impsReg.equalsIgnoreCase("1");
    }
    public static String getIp(String value) {
        String[] ip = AppConstants.IP.split("//");
        String[] ip1 = ip[1].split(":");
        return ip1[0];
    }

    public static String getValidAccountNo(String selectedAccNo) {
        if (selectedAccNo.contains("-")) {
            String[] accounts = selectedAccNo.split("-");
            return accounts[0].trim();
        } else {
            return selectedAccNo.trim();
        }
    }

    public static String getCurrentMonthFirstDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c.getTime());
    }

    public static int getColorPrimary(Activity activity) {
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.textColorPrimary, typedValue, true);
        return typedValue.data;
    }

    public static boolean isAmoutLessThanZero(String amount) {
        double amt = Double.parseDouble(amount);
        return amt < 1;
    }

    public static String getValueCommaSeparated(String value) {
        try {
            if (value.equalsIgnoreCase("0.00") || value.equalsIgnoreCase("0.0")) {
                return value;
            }
            double doubleValue = Double.parseDouble(value);
            DecimalFormat formatter = new DecimalFormat("#,##,##,##,###.00");
            return formatter.format(doubleValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String trimWithPrefixCommsepareted(String amount) {
        try {
            if (amount.contains("Rs.")) {
                String[] amountArray = amount.split(" ");
                return "Rs. " + getValueCommaSeparated(amountArray[1]);
            } else if (amount.contains("Cr")) {
                String[] amountArray = amount.split(" ");
                return getValueCommaSeparated(amountArray[0]) + " Cr";
            } else if (amount.contains("Dr")) {
                String[] amountArray = amount.split(" ");
                return getValueCommaSeparated(amountArray[0]) + " Dr";
            } else {
                return amount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;

    }

    public static void setEditTextMaxLength(int length, EditText editText) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(filterArray);
    }

    public static void naviagteToSplashScreen(Activity activity) {
        Intent intent = new Intent(activity, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public static String convertdmyintoymd(String date) {
        try {
            Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(initDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }


    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getDeviceBilPayID(Context context) {
        try {
            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = "";
                if (Objects.requireNonNull(telephonyManager).getDeviceId() != null) {
                    imei = telephonyManager.getDeviceId();
                }
                String iccId = getICCID(context);
                String androidId = getAndroidID(context);
                return prepareJsonObjectForDeviceIDBillPay("imei", imei, iccId, androidId, context);
            } else {
                String iccId = "";
                List<SubscriptionInfo> subsList = null;
                SubscriptionManager subsManager = null;
                subsManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                subsList = Objects.requireNonNull(subsManager).getActiveSubscriptionInfoList();
                iccId = subsList.get(0).getIccId();
                String androidId = getAndroidID(context);
                return prepareJsonObjectForDeviceIDBillPay("imei", "", iccId, androidId, context);
            }
        } catch (Exception e) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Objects.requireNonNull(telephonyManager).getDeviceId() != null && !TextUtils.isEmpty(telephonyManager.getDeviceId())) {
                String iccId = getICCID(context);
                String androidId = getAndroidID(context);
                return prepareJsonObjectForDeviceIDBillPay("imei", telephonyManager.getDeviceId(), iccId, androidId, context);
            } else {
                try {
                    String iccId = getICCID(context);
                    String androidId = getAndroidID(context);
                    return prepareJsonObjectForDeviceIDBillPay("imei", "", iccId, androidId, context);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    String androidId = getAndroidID(context);
                    return prepareJsonObjectForDeviceIDBillPay("imei", "", "", androidId, context);
                }
            }
        }
    }

    public static String prepareJsonObjectForDeviceIDBillPay(String type, String imei, String iccId, String androidId, Context context) {
        try {
            String androidVersion = Build.VERSION.RELEASE;

            String versionName = TrustMethods.getVersionName(context);
            JSONObject jsonObject = new JSONObject();
//            if (type.equals("imei")) {
            if (!TextUtils.isEmpty(imei)) {
                jsonObject.put("IMEI", imei);
            } else if (!TextUtils.isEmpty(iccId)) {
                jsonObject.put("IMEI", iccId);
            } else if (!TextUtils.isEmpty(androidId)) {
                jsonObject.put("IMEI", androidId);
            }
            jsonObject.put("application_version", versionName);
            jsonObject.put("android_version", androidVersion);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}";
        }

    }
    public static boolean validateUPI(String upi) {
        final Pattern VALID_UPI_ADDRESS_REGEX = Pattern.compile("^(.+)@(.+)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_UPI_ADDRESS_REGEX.matcher(upi);
        return matcher.find();
    }

    public static void horizontalRecyclerView(Activity context, RecyclerView recyclerViewHoriListId) {
        try {
            List<ImageTextMenuModel> menulist = new ArrayList<>();
            menulist.add(new ImageTextMenuModel(context.getString(R.string.Home), R.drawable.homeb,"1",context.getResources().getString(R.string.Home)));
            menulist.add(new ImageTextMenuModel(context.getResources().getString(R.string.Profile), R.drawable.profleb, "1", context.getResources().getString(R.string.Profile)));
            menulist.add(new ImageTextMenuModel(context.getResources().getString(R.string.Settings), R.drawable.settingb, "0", context.getResources().getString(R.string.Settings)));
            menulist.add(new ImageTextMenuModel(context.getResources().getString(R.string.Logout), R.drawable.logoutb,"1", context.getResources().getString(R.string.Logout)));
            menulist.add(new ImageTextMenuModel(context.getResources().getString(R.string.language), R.drawable.language,"1", context.getResources().getString(R.string.language)));

            List<ImageTextMenuModel>collectlist = new ArrayList<>();
            for (ImageTextMenuModel olditem : menulist) {
                if(olditem.getIsEnabled().trim().equals("1")) {
                    collectlist.add(olditem);
                }
            }

            GridLayoutManager mLayoutManager = new GridLayoutManager(context,4, GridLayoutManager.VERTICAL, false);
            recyclerViewHoriListId.setLayoutManager(mLayoutManager);
            HorizontalMenuAdapter accountsAdapter = new HorizontalMenuAdapter(context, collectlist,AppConstants.getSubmenu());
            recyclerViewHoriListId.setAdapter(accountsAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Submenuselectorfund(Activity context, RecyclerView recyclerAccounts) {
        try{
            List<ImageTextMenuModel> oldItemData=new ArrayList<>();
            for(DynamicMenuModel aDynamicMenuModel:AppConstants.getSubmenuList()) {
                if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_account_overview")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.AccountOverview), R.drawable.accountoverview, AppConstants.getMnu_account_overview()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_account_details")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.AccountDetails), R.drawable.accountdetails, AppConstants.getMnu_account_details()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_statement")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.Statement), R.drawable.statement, AppConstants.getMnu_statement()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_loan_catalogue")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.CreditCatalogue), R.drawable.catalogue, AppConstants.getMnu_loan_catalogue()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_loan_eligibility")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.CreditEligibility), R.drawable.eligibility, AppConstants.getMnu_loan_eligibility()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_loan_simulator")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.CreditSimulator), R.drawable.simulator, AppConstants.getMnu_loan_simulator()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_savings_account")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.SavingsAccount), R.drawable.savingaccount, AppConstants.getMnu_savings_account()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_open_saving_account")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.OpenSavingAccount) , R.drawable.opensavingaccount, AppConstants.getMnu_open_saving_account()));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_cheque_book_request")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.ChequeBookRequest) , R.drawable.chequebookrequest, AppConstants.getMnu_cheque_book_request()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_stop_cheque")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.StopCheque) , R.drawable.stopcheque, AppConstants.getMnu_stop_cheque()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_cheque_status")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.ChequeStatus) , R.drawable.chequesatus, AppConstants.getMnu_cheque_status()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_save_debit_card")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.DebitCard) , R.drawable.debitcard, AppConstants.getMnu_debit_card()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_investment_account")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.InvestmentAccount) , R.drawable.investmentaccount, AppConstants.getMnu_investment_account()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_open_investment_account")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.OpenInvestmentAccount) , R.drawable.openinvestmentaccount,AppConstants.getMnu_open_investment_account()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_investment_certificate")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.InvestmentCertificate) , R.drawable.investmentcertificate,AppConstants.getMnu_investment_certificate()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_close_investment_account")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.CloseInvestmentAccount) , R.drawable.closeinvestmentaccount,AppConstants.getMnu_close_investment_account()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_investment_simulator")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.InvestmentSimulator) , R.drawable.investmentsimulator,AppConstants.getMnu_investment_simulator()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_loan_repayment")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.LoanRepayment) , R.drawable.loanrepayment,AppConstants.getMnu_loan_repayment()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_intra_bank_transfer")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.IntraBankTransfer) , R.drawable.intrabanktransfer,AppConstants.getMnu_intra_bank_transfer()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_self_account_transfer")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.SelfAccountTransfer) , R.drawable.selfaccounttransfer, AppConstants.getMnu_self_account_transfer()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_interbank_transfer")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.InterbankTransfer) , R.drawable.interbanktransfer, AppConstants.getMnu_interbank_transfer()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_manage_beneficiaries")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.ManageBeneficiaries) , R.drawable.managebeneficiaries, AppConstants.getMnu_manage_beneficiaries()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_cheque")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.Cheque) , R.drawable.cheque, AppConstants.getMnu_cheque()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_trans_debit_card")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.DebitCard) , R.drawable.debitcard,AppConstants.getMnu_debit_card1()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_bills_payment")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.BillsPayment) , R.drawable.billpayment,AppConstants.getMnu_bills_payment()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_locate_agencies")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.LocateAgencies) , R.drawable.locateagencies, AppConstants.getMnu_locate_agencies()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_locate_atm")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.LocateATM) , R.drawable.locateatm,AppConstants.getMnu_locate_atm()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_contact")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.Contact) , R.drawable.contactus, AppConstants.getMnu_contact()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_schedule_visit")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.ScheduleVisit) , R.drawable.contact, AppConstants.getMnu_schedule_visit()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_saving_catalogue")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.SavingCatalogue) , R.drawable.saving_catalogue, AppConstants.getMnu_saving_catalogue()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_investment_catalogue")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.InvestmentCataligue) , R.drawable.invest_catalog, AppConstants.getMnu_investment_catalogue()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_open_loan_account")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.OpenCreditAccount) , R.drawable.invest_catalog, AppConstants.getMnu_open_loan_account()));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_security_center")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.SecurityCenter) , R.drawable.security_center, AppConstants.getMnu_security_center()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_transaction_limit")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.TransactionLimit) , R.drawable.transaction_limit, AppConstants.getMnu_transaction_limit()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_feedback")) {
                    oldItemData.add(new ImageTextMenuModel(context.getResources().getString(R.string.Feedback) , R.drawable.feedback, AppConstants.getMnu_feedback()));
                }
            }
            List<ImageTextMenuModel>collectlist = new ArrayList<>();
            for (ImageTextMenuModel olditem : oldItemData) {
                if(olditem.getIsEnabled().trim().equals("1")) {
                    collectlist.add(olditem);
                }
            }
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            recyclerAccounts.setLayoutManager(gridLayoutManager);
            FundsTransferAdapter fundsTransferAdapter = new FundsTransferAdapter(context,collectlist);
            recyclerAccounts.setAdapter(fundsTransferAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}