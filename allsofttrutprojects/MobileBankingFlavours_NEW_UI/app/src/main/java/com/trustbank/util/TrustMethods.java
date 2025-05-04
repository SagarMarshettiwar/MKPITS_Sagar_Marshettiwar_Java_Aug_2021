package com.trustbank.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Model.BottomDynamicMenuModel;
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
import static com.trustbank.util.AppConstants.Detuct_Application;
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
    public static final String BBPSFAV_NAME = "BBPSFAV";
    public static final String Fingure_status = "Fingure_status";
    public static final String Profileset = "Profile_status";
    public static final String BEN_ACC_LIST = "BEN_ACC_LIST";
    public static final String CLEAR_SHARED_PREF = "CLEAR_SHARED_PREF";
    public static final String SUBSCRIPTION_ID = "SUBSCRIPTION_ID";
    public static final String PROFILE_ID = "PROFILE_ID";
    TextView editTextDate;
    public int mYear;
    public int mMonth;
    public int mDay;
    public String months;
    Context mContext;
    Activity activity;
//    public static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "." + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

    private static String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public TrustMethods(Context context) {
        this.mContext = context;
        this.activity = (Activity) context;
    }

    public void datePickerymd(final Context context, TextView editText) {
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
    public void datePicker(final Context context, TextView editText) {
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

    public void disableMindatePicker(final Context context, TextView editText) {
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
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void datePickerDisableFuturesDate(final Context context, TextView editText) {
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
    public static boolean isEmulator() {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator");
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

        // Changing message text color
        snackbar.setActionTextColor(Color.YELLOW);

        // Changing action button text color
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

    public void saveBBpsfavList(Context context, ArrayList<String> list, String key) {
        try {
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            settings = context.getSharedPreferences(BBPSFAV_NAME, Context.MODE_PRIVATE);
            editor = settings.edit();
            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(list);
            editor.putString(key, jsonFavorites);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setProfileId(Context context, String profileId) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PROFILE_ID, Context.MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.clear();
            myEdit.putString("ProfileID", profileId);
            myEdit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProfileId(Context context) {
        try {
            SharedPreferences sh = context.getSharedPreferences(PROFILE_ID, Context.MODE_PRIVATE);
            return sh.getString("ProfileID", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getBBpsfavList(Context context, String key) {
        SharedPreferences settings;
        List<String> favorites;
        settings = context.getSharedPreferences(BBPSFAV_NAME, Context.MODE_PRIVATE);
        if (settings.contains(key)) {
            String jsonFavorites = settings.getString(key, null);
            if (!jsonFavorites.equals("null")) {
                Gson gson = new Gson();
                String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);
                favorites = Arrays.asList(favoriteItems);
                favorites = new ArrayList<String>(favorites);
                return (ArrayList<String>) favorites;
            } else {
                return null;
            }
        }

        return null;
    }

    public void setProfilepicture(Context context, Bitmap v) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            v.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String encoded = Base64.encodeToString(b, Base64.DEFAULT);
            SharedPreferences sharedPreferences = context.getSharedPreferences(Profileset, Context.MODE_PRIVATE);
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
            SharedPreferences sh = context.getSharedPreferences(Profileset, Context.MODE_PRIVATE);
            String s1 = sh.getString("status", "iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAQAAAD/5HvMAAADVElEQVRoBe3BS2hUVwDH4X9oM9M4k9BFpmMtSB5CkW4qGsQmRLFVuygITcGFoqsi+AjoJiHLbrpqLFpoV4mhFSzdKEkwxNTJQ0MIqaUgaYUSAk27CUNw02Rmwvy6Lbn33HPunRvjYr5PqqqqekWR4hR9DDHHMnmKFMmzzBxD9HGSXXp5yHKdGYoEKTDNNd7SduMYI2ziqsQwndouHOcJUcxwVHFjDz9SiTvsVnw4wwsqtUaX4kCS74jLLRKqDA3kiNND0oqODE+J2wIZRUMDT7EpM8pZmqklQQvneIDdAvUKjyQ5bJ7Rpi04zCI2EyQUFt9iM0FKPkjzCJubCocz2DwjJQPSLGLzqdyxhxcEK9OmABzBZo2sXHEXm1FZMIbN93LDcezOyoLz2HXKBU+wa5YFrdhNyY5juKiVBUlcdMiGEVwkZEESF/cVjCybuGiRBftwUSKjIFzHzTlZcAE33QrCDG4eyIJx3EzKjBRFXB1WANpxtUGdTDiFu0XSMqCe57g7IRP6COMRafmgnknC6JUJQ4SzyBFtQTvPCWdQJswR3hjnaSVJkn1cYJzwZmXCMjthSSbk2QmrMqHITijIhCI7oSAT8uyEVZmwTHjr/E6OYYaZ5A/WCW9JJszhqsyvfEUXLdTof6ihlc/o5zfczcqEIVzMc5m3ZcE7dLOAiwGZ0EewMnc5qBBo4yfKBOuRCScJMs8BRcAhfiHIRzJhF0VM+nlNEfE6NzHZoE5mTOPvC1WIL/GXUxCu4WeKGlWIGh7j56qCkKWE18eKAZ/gVaRRwRjGq0Ex4E287smGTrw+Vwy4iNcHsmOGrf7lfVWIg6yzVU4uOIrXCntVAZr4m63KtMsNd/D6k72KiCaW8LotV+xmDa8VDigCDvEPXnkyckcXfta5qFCo4RIb+DmtcPgGf+O8K0fs52f83VBYJHiIvxID7JcF7zHIJv7GqFV41LOA2TSXaJIPmrnMY8zmSSkaMiwQ7C9GuEEPV7hCL18zygrB5mlUdNQzQZzGSKkyJLhFXPqpVRzoYo1K5Tmt+JDlB6Irc5uM4kYnU0SRo0PbhQ7uU8JVkXu0a7uRoZtJCgTZIMdVGvXyUMcJehlkliVWKVBglSVmGaCHD3lDVVVVr6j/AO9d1vmVHdP3AAAAAElFTkSuQmCC");
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void Setfingureprintpref(Context context, Boolean v) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Fingure_status, Context.MODE_PRIVATE);
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
            SharedPreferences sh = context.getSharedPreferences(Fingure_status, Context.MODE_PRIVATE);
            Boolean s1 = sh.getBoolean("status", false);
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setSubscripId(Context context, String selectedSimId) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SUBSCRIPTION_ID, Context.MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.clear();
            myEdit.putString("SelectedSubscriptionId", selectedSimId);
            myEdit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSubscripId(Context context) {
        try {
            SharedPreferences sh = context.getSharedPreferences(SUBSCRIPTION_ID, Context.MODE_PRIVATE);
            return sh.getString("SelectedSubscriptionId", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setClearSharedPref(Context context, Boolean b) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(CLEAR_SHARED_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.clear();
            myEdit.putBoolean("status", b);
            myEdit.apply();
            myEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getClearSharedPref(Context context) {
        try {
            SharedPreferences sh = context.getSharedPreferences(CLEAR_SHARED_PREF, Context.MODE_PRIVATE);
            boolean s1 = sh.getBoolean("status", false);
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String encodeBase64(String decodeString) {
        byte[] data = decodeString.getBytes();
        return Base64.encodeToString(data, Base64.DEFAULT);
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
            if (context.getPackageName().equalsIgnoreCase("com.trustbank.shivajibank") ||
                    context.getPackageName().equalsIgnoreCase("com.trustbank.vmucbbank") ||
                    context.getPackageName().equalsIgnoreCase("com.trustbank.punepeoplesbank") ||
                    context.getPackageName().equalsIgnoreCase("com.trustbank.gondiamahilabank")) {
                if (!Pattern.matches("[0-9a-zA-Z]{7,25}", accno)) {
                    check = false;
                } else {
                    check = true;
                }
            } else {
                if (!Pattern.matches("[0-9a-zA-Z]{7,25}", accno)) {
                    check = false;
                } else {
                    check = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return check;
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
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
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            List<SubscriptionInfo> subsList = null;
            SubscriptionManager subsManager = null;
            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
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
      /*{
            "imei": "",
            "icc_no": "",
            "android_id": ""
        }*/
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
         /*   } else if (type.equals("icc_no")) {
                jsonObject.put("imei", "");
                jsonObject.put("icc_no", deviceID);
                jsonObject.put("android_id", "");
                return jsonObject.toString();
            } else {
                return "{}";
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}";
        }

    }

    public static boolean isRooted(Context context) {
        if (LoginInfo_isRootDetectionEnabled) {
            Process process = null;
            try {
                String[] binaryPaths =
                        {
                                "/system/xbin/su",
                                "/system/bin/su",
                                "/system/sbin/su",
                                "/sbin/su",
                                "/system/su",
                                "/data/local/xbin/su",
                                "/data/local/bin/su",
                                "/data/local/su",
                                "/su/bin/su"
                        };

                for (String path : binaryPaths) {
                    if (new File(path).exists()) {
                        return true;
                    }
                }

                String[] apkPaths = {
                        "/system/app/Superuser.apk",
                        "/system/priv-app/Superuser.apk"
                };

                for (String path : apkPaths) {
                    if (new File(path).exists()) {
                        return true;
                    }
                }

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
    public boolean packageoneIdentify(Context c){
        if (Detuct_Application) {
            String a1 = AppConstants.getRestrictedapppackage1();

            if(!TextUtils.isEmpty(a1))
            {
                return arePackagesInstalled(a1,c);
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    private boolean arePackagesInstalled(String packageNames, Context context) {
        String[] packages = packageNames.split(",");
        PackageManager packageManager = context.getPackageManager();
        Intent intent;
        for (String packageName : packages) {
             intent = packageManager.getLaunchIntentForPackage(packageName.trim());
            if (intent != null) {
                return true;
            }
        }
        return false;
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
            return null;
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
        return acctType.equalsIgnoreCase("78") || acctType.equalsIgnoreCase("79")
                || acctType.equalsIgnoreCase("82") ;
    }

    public static boolean isAccountTypeValidneft(String acctType, List<String> neft) {
        for (String value : neft) {
            if (acctType.trim().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
//    public static boolean isAccountTypeValid(String acctType, List<String> neft) {
//        for (String value : neft) {
//            if (acctType.equalsIgnoreCase(value)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static boolean isAccountTypeMpassbook(String acctType) {
        return acctType.equalsIgnoreCase("78") || acctType.equalsIgnoreCase("79")
                || acctType.equalsIgnoreCase("82") || acctType.equalsIgnoreCase("77")
                || acctType.equalsIgnoreCase("80") || acctType.equalsIgnoreCase("81");
    }


    public static boolean isAccountTypeValidwthinbank(String headcode, List<String> within) {
        for (String value : within) {
            if (headcode.trim().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isAccountTypeValidcard(String headcode, List<String> within, String cardActive) {
        for (String value : within) {
            if (headcode.trim().equalsIgnoreCase(value) && cardActive.equals("1")) {
                return true;
            }
        }
        return false;
    }
    public static boolean isAccountTypeValidCard(String headcode, List<String> within) {
        for (String value : within) {
            if (headcode.trim().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAccountTypeValidAjara(String acctType) {
        return acctType.equalsIgnoreCase("78") || acctType.equalsIgnoreCase("79");
    }

    public static boolean isAccountTypeValidRDLN(String acctType) {
        return acctType.equalsIgnoreCase("78") || acctType.equalsIgnoreCase("79")
                || acctType.equalsIgnoreCase("82") || acctType.equalsIgnoreCase("77") ||
                acctType.equalsIgnoreCase("81");
    }



    public static boolean isAccountTypeIsImpsRegValid(String acctType, String impsReg,List<String> imps) {
        for (String value : imps) {
            if (acctType.trim().equalsIgnoreCase(value) /*&& impsReg.equalsIgnoreCase("1")*/) {
                return true;
            }
        }

        return false;
    }


    public static boolean isAccountTypeIsImpsRegValidfortrans(String acctType, String impsReg, List<String> imps, String imps_registered) {
        for (String value : imps) {
            if(imps_registered.equals("1")){
                if (acctType.trim().equalsIgnoreCase(value) && impsReg.equalsIgnoreCase("1")) {
                    return true;
                }
            }else{
                if (acctType.trim().equalsIgnoreCase(value) /*&& impsReg.equalsIgnoreCase("1")*/) {
                    return true;
                }
            }
        }

        return false;
    }



    public static boolean isAccountTypeQRValid(String acctType) {
        return (acctType.equalsIgnoreCase("78") || acctType.equalsIgnoreCase("79")
                || acctType.equalsIgnoreCase("82") || acctType.equalsIgnoreCase("81") || acctType.equalsIgnoreCase("173"));

    }

    public static boolean sItoacc(String acctType) {
        return (acctType.equalsIgnoreCase("78") || acctType.equalsIgnoreCase("79")
                || acctType.equalsIgnoreCase("82") );

    }

    public static boolean sIfromacc(String acctType) {
        return (acctType.equalsIgnoreCase("77")|| /*acctType.equalsIgnoreCase("75")||*/ acctType.equalsIgnoreCase("81") );

    }

    public static boolean isAccountTypeIsImpsRegValidAjara(String acctType, String impsReg) {
        return (acctType.equalsIgnoreCase("78") || acctType.equalsIgnoreCase("79"))
                && impsReg.equalsIgnoreCase("1");
    }

    public static String getIp(String value) {
        String[] ip = AppConstants.IP.split("//");
        String[] ip1 = ip[1].split(":");
        return ip1[0];
    }

    public static File createPdfFolder(Context context) {
        File f = null;
        try {
            String appName = context.getResources().getString(R.string.app_name);
            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + appName + File.separator + "TermsAndconditions";
            f = new File(rootPath);
            if (!f.exists()) {
                f.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
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
      /*{
            "imei": "",
            "icc_no": "",
            "android_id": ""
        }*/
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

          /*  jsonObject.put("icc_no", iccId);
            jsonObject.put("android_id", androidId);*/
            jsonObject.put("application_version", versionName);
            jsonObject.put("android_version", androidVersion);

            return jsonObject.toString();
         /*   } else if (type.equals("icc_no")) {
                jsonObject.put("imei", "");
                jsonObject.put("icc_no", deviceID);
                jsonObject.put("android_id", "");
                return jsonObject.toString();
            } else {
                return "{}";
            }*/
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

    public static String getFrequency(String frequncyId) {

        String frequncy = "";
        switch (frequncyId) {
            case "71":
                frequncy = "Monthly";
                break;

            case "72":
                frequncy = "Quarterly";
                break;

            case "73":
                frequncy = "Half Yearly";
                break;

            case "74":
                frequncy = "Yearly";
                break;

            case "240":
                frequncy = "Bi Monthly";
                break;

            case "241":
                frequncy = "Adhoc";
                break;

            case "242":
                frequncy = "Intra Day";
                break;

            case "243":
                frequncy = "Daily";
                break;

            case "244":
                frequncy = "Weekly";

                break;

            case "245":
                frequncy = "Semi Annually";
                break;
        }
        return frequncy;
    }

    public  static void horizontalRecyclerView(Activity context, RecyclerView recyclerViewHoriListId) {
        ArrayList<ImageTextMenuModel> collectlist = null;
        try {
            List<ImageTextMenuModel> menulist = new ArrayList<>();
            menulist.add(new ImageTextMenuModel("Home", R.drawable.hhome1,"1", "Home"));

            for (BottomDynamicMenuModel parentmenu : AppConstants.getBottomparentlist()) {
                if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts")) {
                    menulist.add(new ImageTextMenuModel("Accounts", R.drawable.haccount, AppConstants.getMnu_accounts(), "Accounts"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_fundtransfer_ownbank")) {
                    menulist.add(new ImageTextMenuModel("Within Bank Transfer", R.drawable.hwithin, AppConstants.getMnu_fundtransfer_ownbank(), "Within Bank"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_fundtransfer_nefttoaccount")) {
                    menulist.add(new ImageTextMenuModel("NEFT Transfer To Account", R.drawable.hneft, AppConstants.getMnu_fundtransfer_nefttoaccount(), "NEFT"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_fundtransfer_impstoaccount")) {
                    menulist.add(new ImageTextMenuModel("IMPS Transfer To Account", R.drawable.himps, AppConstants.getMnu_fundtransfer_impstoaccount(), "IMPS"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_fundtransfer_impstomobile")) {
                    menulist.add(new ImageTextMenuModel("IMPS Transfer To Mobile Phone", R.drawable.himps, AppConstants.getMnu_fundtransfer_impstomobile(), "IMPS P2P"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_fundtransfer_menu_selftrf")) {
                    menulist.add(new ImageTextMenuModel("Self Transfer To Account", R.drawable.hself1, AppConstants.getMnu_self_transfer_to_account(), "Self Transfer"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_upi")) {
                    menulist.add(new ImageTextMenuModel("UPI", R.drawable.upii, AppConstants.getMnu_UPI(), "UPI"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_bill")) {
                    menulist.add(new ImageTextMenuModel("Bill Pay", R.drawable.bbps, AppConstants.getMnu_bill(), "Bill pay"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_acc_stmnt")) {
                    menulist.add(new ImageTextMenuModel("mPassBook", R.drawable.hpassbook, AppConstants.getMnu_account_statement(), "mPassBook"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_ministatemnt")) {
                    menulist.add(new ImageTextMenuModel("Statement Request", R.drawable.hministatement, AppConstants.getMnu_accounts_menu_ministatemnt(), "Mini Statement "));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_ministatemnt_cbs")) {
                    menulist.add(new ImageTextMenuModel("Statement Request CBS", R.drawable.hministatement, AppConstants.getMnu_accounts_menu_ministatemnt_cbs(), "Mini Statement "));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_cards")) {
                    menulist.add(new ImageTextMenuModel("Cards", R.drawable.hcards, AppConstants.getMnu_cards(), "Cards"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_Services")) {
                    menulist.add(new ImageTextMenuModel("Service Request", R.drawable.hservice1, AppConstants.getMnu_services(), "Services"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_fundtransfer_mngbenefeciaries")) {
                    menulist.add(new ImageTextMenuModel("Manage Beneficiaries", R.drawable.hben, AppConstants.getMnu_fundtransfer_mngbenefeciaries(), "Beneficiaries"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_settings")) {
                    menulist.add(new ImageTextMenuModel("Settings", R.drawable.ic_setting, AppConstants.getMnu_setting(), "Settings"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_locate_us")) {
                    menulist.add(new ImageTextMenuModel("Locate US", R.drawable.locate_atm_one, AppConstants.getMnu_locate_atms(), "Locate US"));
                } else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_need_help")) {
                    menulist.add(new ImageTextMenuModel("Need Help", R.drawable.hi2, AppConstants.getMnu_need_help(), "Need Help"));
                }
                else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_accountdetails")) {
                    menulist.add(new ImageTextMenuModel("Account Details", R.drawable.accountsdetailswhite, AppConstants.getMnu_accounts_menu_accountdetails(), "Account Details"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_balenquiry")) {
                    menulist.add(new ImageTextMenuModel("Balance Enquiry", R.drawable.balanceenquirywhite, AppConstants.getMnu_accounts_menu_balenquiry(), "Balance Enquiry"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_balenquiry_cbs")) {
                    menulist.add(new ImageTextMenuModel("Balance Enquiry CBS", R.drawable.balanceenquirywhite, AppConstants.getMnu_accounts_menu_balenquiry_cbs(), "Balance Enquiry"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_last5imps")) {
                    menulist.add(new ImageTextMenuModel("Last 5 Transactions", R.drawable.last5white, AppConstants.getMnu_accounts_menu_last5imps(), "Last 5 Transactions"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_last5imps_cbs")) {
                    menulist.add(new ImageTextMenuModel("Last 5 Transactions CBS", R.drawable.last5white, AppConstants.getMnu_accounts_menu_last5imps_cbs(), "Last 5 Transactions CBS"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_showmmid")) {
                    menulist.add(new ImageTextMenuModel("Show MMID", R.drawable.showmmidwhite, AppConstants.getMnu_accounts_menu_showmmid(), "Show MMID"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_showmmid_cbs")) {
                    menulist.add(new ImageTextMenuModel("Show MMID CBS", R.drawable.showmmidwhite, AppConstants.getMnu_accounts_menu_showmmid_cbs(), "Show MMID"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_neftenquiry")) {
                    menulist.add(new ImageTextMenuModel("NEFT Enquiry", R.drawable.neftenquirywhite, AppConstants.getMnu_accounts_menu_neftenquiry(), "NEFT Enquiry"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_imps_transction_status")) {
                    menulist.add(new ImageTextMenuModel("Check Transaction Status", R.drawable.checktransactionstatuswhite, AppConstants.getMnu_check_imps_transaction_status(), "Check Transaction Status"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_fundtransfer_upi")) {
                    menulist.add(new ImageTextMenuModel("UPI Transfer", R.drawable.upi, AppConstants.getMnu_fundtransfer_upi(),"UPI Transfer"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_create_new_qr")) {
                    menulist.add(new ImageTextMenuModel("Create New QR Code", R.drawable.createnewqrwhite, AppConstants.getMnu_fundtransfer_upi_create_qr(),"Create QR"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_fetch_qr")) {
                    menulist.add(new ImageTextMenuModel("Display QR Code", R.drawable.displayqrwhite, AppConstants.getMnu_fundtransfer_upi_get_qr(),"Display QR"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_cheqbkreq")) {
                    menulist.add(new ImageTextMenuModel("Cheque Book Request", R.drawable.chequebookrequestwhite, AppConstants.getMnu_checkbook_request(),"Cheque Book Request"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_chq_status")) {
                    menulist.add(new ImageTextMenuModel("Cheque Status", R.drawable.chequestauswhite,AppConstants.getInqueriChquebookStatus(),"Cheque Status"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_stop_chq")) {
                    menulist.add(new ImageTextMenuModel("Stop Cheque", R.drawable.stopchequewhite,AppConstants.getStopChequebookStatus(),"Stop Cheque"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_pps_request")) {
                    menulist.add(new ImageTextMenuModel("Positive Pay Request", R.drawable.positivepayrequestwhite,AppConstants.getMnu_pps_request(),"Positive Pay Request"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_accounts_menu_pps_request_enquiry")) {
                    menulist.add(new ImageTextMenuModel("Positive Pay Enquiry", R.drawable.positivepayrequestenquirywhite,AppConstants.getMnu_pps_request_enquiry(),"Positive Pay Enquiry"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_mandate_cancel")) {
                    menulist.add(new ImageTextMenuModel("ECS Mandate Cancellation", R.drawable.ecsmandadecancelationwhite, AppConstants.getMnu_mandate_cancel(),"ECS Cancellation") );
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_rd_fd_reckoner")) {
                    menulist.add(new ImageTextMenuModel("FD/RD Reckoner", R.drawable.rdfdrecknorwhite, AppConstants.getMnu_rd_fd_reckoner(),"Reckoner") );
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_amortization_chart")) {
                    menulist.add(new ImageTextMenuModel("EMI Calculator", R.drawable.amortizationchartwhite, AppConstants.getMnu_amortization_chart(),"EMI Calculator"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_FAQ")) {
                    menulist.add(new ImageTextMenuModel("FAQs", R.drawable.faqwhite, AppConstants.getMnu_faq(),"FAQs"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_contact_us")) {
                    menulist.add(new ImageTextMenuModel("Contact US", R.drawable.contactuswhite, AppConstants.getMnu_contact_us(),"Contact US"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_about_us")) {
                    menulist.add(new ImageTextMenuModel("About US", R.drawable.aboutuswhite, AppConstants.getMnu_about_us(),"About US"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_locate_atms")) {
                    menulist.add(new ImageTextMenuModel("Locate ATM", R.drawable.locateatmwhite,AppConstants.getMnu_locate_atms(),"Locate ATM") );
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_locate_branch")) {
                    menulist.add(new ImageTextMenuModel("Locate Branch", R.drawable.locatebranchwhite,AppConstants.getMnu_locate_branch() ,"Locate Branch"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_block_debit_card_cbs")) {
                    menulist.add(new ImageTextMenuModel("Block Debit Card CBS", R.drawable.blockdebitcardwhite, AppConstants.getMnu_block_debit_card_cbs(),"Block Debit Card CBS") );
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_account_block_debit_card")) {
                    menulist.add(new ImageTextMenuModel("Block Debit Card", R.drawable.blockdebitcardwhite, AppConstants.getMnu_block_debit_card(),"Block Debit Card"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_debit_card_pin_generation")) {
                    menulist.add(new ImageTextMenuModel("Debit Card Pin Generation", R.drawable.debitcardpingenerationwhite,AppConstants.getMnu_debit_card_pin_generation(),"Debit Card Pin Generation"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_block_debit_card_switch")) {
                    menulist.add(new ImageTextMenuModel("Permanent Block Debit Card Switch", R.drawable.blockdebitcardswitchwhte, AppConstants.getMnu_block_debit_card_switch(),"Permanent Block Debit Card Switch"));
                }else if (parentmenu.getBottommenucode().equalsIgnoreCase("mnu_bbps_finacus")) {
                    menulist.add(new ImageTextMenuModel("Bill Pay", R.drawable.bbps_mnemonic, AppConstants.getMnu_bbps(),"Bill Pay"));
                }else {

                }
            }

            collectlist = new ArrayList<>();
            for (ImageTextMenuModel menuModel : menulist) {
                if(menuModel.getIsEnabled().trim().equals("1")) {
                    collectlist.add(menuModel);
                }
            }
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
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
                if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_accountdetails")) {
                    oldItemData.add(new ImageTextMenuModel("Account Details", R.drawable.accountsd, AppConstants.getMnu_accounts_menu_accountdetails(), "Account Details"));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_balenquiry")) {
                    oldItemData.add(new ImageTextMenuModel("Balance Enquiry", R.drawable.balance, AppConstants.getMnu_accounts_menu_balenquiry(), "Balance Enquiry"));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_balenquiry_cbs")) {
                    oldItemData.add(new ImageTextMenuModel("Balance Enquiry CBS", R.drawable.balance, AppConstants.getMnu_accounts_menu_balenquiry_cbs(), "Balance Enquiry"));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_last5imps")) {
                    oldItemData.add(new ImageTextMenuModel("Last 5 Transactions", R.drawable.last5impstr, AppConstants.getMnu_accounts_menu_last5imps(), "Last 5 Transactions"));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_last5imps_cbs")) {
                    oldItemData.add(new ImageTextMenuModel("Last 5 Transactions CBS", R.drawable.last5impstr, AppConstants.getMnu_accounts_menu_last5imps_cbs(), "Last 5 Transactions CBS"));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_showmmid")) {
                    oldItemData.add(new ImageTextMenuModel("Show MMID", R.drawable.mmid, AppConstants.getMnu_accounts_menu_showmmid(), "Show MMID"));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_showmmid_cbs")) {
                    oldItemData.add(new ImageTextMenuModel("Show MMID CBS", R.drawable.mmid, AppConstants.getMnu_accounts_menu_showmmid_cbs(), "Show MMID"));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_neftenquiry")) {
                    oldItemData.add(new ImageTextMenuModel("NEFT Enquiry", R.drawable.neftenquery, AppConstants.getMnu_accounts_menu_neftenquiry(), "NEFT Enquiry"));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_imps_transction_status")) {
                    oldItemData.add(new ImageTextMenuModel("Check Transaction Status", R.drawable.check_trx_status, AppConstants.getMnu_check_imps_transaction_status(), "Check Transaction Status"));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_fundtransfer_upi")) {
                    oldItemData.add(new ImageTextMenuModel("UPI Transfer", R.drawable.upi, AppConstants.getMnu_fundtransfer_upi()));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_create_new_qr")) {
                    oldItemData.add(new ImageTextMenuModel("Create New QR Code", R.drawable.create_new, AppConstants.getMnu_fundtransfer_upi_create_qr()));
                } else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_fetch_qr")) {
                    oldItemData.add(new ImageTextMenuModel("Display QR Code", R.drawable.display_qr, AppConstants.getMnu_fundtransfer_upi_get_qr()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_cheqbkreq")) {
                    oldItemData.add(new ImageTextMenuModel("Cheque Book Request", R.drawable.chique, AppConstants.getMnu_checkbook_request()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_chq_status")) {
                    oldItemData.add(new ImageTextMenuModel("Cheque Status", R.drawable.chiquesta,AppConstants.getInqueriChquebookStatus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_stop_chq")) {
                    oldItemData.add(new ImageTextMenuModel("Stop Cheque", R.drawable.stopchique,AppConstants.getStopChequebookStatus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_pps_request")) {
                    oldItemData.add(new ImageTextMenuModel("Positive Pay Request", R.drawable.last5,AppConstants.getMnu_pps_request()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_accounts_menu_pps_request_enquiry")) {
                    oldItemData.add(new ImageTextMenuModel("Positive Pay Enquiry", R.drawable.ppe,AppConstants.getMnu_pps_request_enquiry()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_mandate_cancel")) {
                    oldItemData.add(new ImageTextMenuModel("ECS Mandate Cancellation", R.drawable.ecs, AppConstants.getMnu_mandate_cancel()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_rd_fd_reckoner")) {
                    oldItemData.add(new ImageTextMenuModel("FD/RD Reckoner", R.drawable.rdfd_recknor, AppConstants.getMnu_rd_fd_reckoner()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_amortization_chart")) {
                    oldItemData.add(new ImageTextMenuModel("EMI Calculator", R.drawable.amortization_chart, AppConstants.getMnu_amortization_chart()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_FAQ")) {
                    oldItemData.add(new ImageTextMenuModel("FAQs", R.drawable.faqs, AppConstants.getMnu_faq()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_contact_us")) {
                    oldItemData.add(new ImageTextMenuModel("Contact US", R.drawable.contactus, AppConstants.getMnu_contact_us()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_about_us")) {
                    oldItemData.add(new ImageTextMenuModel("About US", R.drawable.aboutus, AppConstants.getMnu_about_us()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_locate_atms")) {
                    oldItemData.add(new ImageTextMenuModel("Locate ATM", R.drawable.locateatm,AppConstants.getMnu_locate_atms()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_locate_branch")) {
                    oldItemData.add(new ImageTextMenuModel("Locate Branch", R.drawable.locatebranch,AppConstants.getMnu_locate_branch() ));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_block_debit_card_cbs")) {
                    oldItemData.add(new ImageTextMenuModel("Block Debit Card CBS", R.drawable.block_debit_card_cbs, AppConstants.getMnu_block_debit_card_cbs()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_debit_card_pin_generation")) {
                    oldItemData.add(new ImageTextMenuModel("Debit Card Pin Generation", R.drawable.debit_card_pin_generation,AppConstants.getMnu_debit_card_pin_generation()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_account_block_debit_card")) {
                    oldItemData.add(new ImageTextMenuModel("Block Debit Card", R.drawable.block_debit_card, AppConstants.getMnu_block_debit_card()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_block_debit_card_switch")) {
                    oldItemData.add(new ImageTextMenuModel("Permanent Block Debit Card Switch", R.drawable.block_debit_card, AppConstants.getMnu_block_debit_card_switch()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_nominee_management")) {
                    oldItemData.add(new ImageTextMenuModel("Nominee Management", R.drawable.nominies_ic, AppConstants.getMnu_nominee_management()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_debit_card_limit")) {
                    oldItemData.add(new ImageTextMenuModel("Debit Card Limit", R.drawable.debit_card_limit, AppConstants.getMnu_debit_card_limit()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_temp_block_debit_card")) {
                    oldItemData.add(new ImageTextMenuModel("Temporary Block Debit Card", R.drawable.temporary_block_debit_card, AppConstants.getMnu_temp_block_debit_card()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_bbps_bill_payment_finacus")) {
                    oldItemData.add(new ImageTextMenuModel("BBPS Bill Payment", R.drawable.bbps_bill_payments, AppConstants.getMnu_bbps_bill_payment_finacus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_bbps_transaction_history_finacus")) {
                    oldItemData.add(new ImageTextMenuModel("BBPS Transaction History", R.drawable.bbps_transaction_history, AppConstants.getMnu_bbps_transaction_history_finacus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_bbps_complaint_management_finacus")) {
                    oldItemData.add(new ImageTextMenuModel("BBPS Complaint Management", R.drawable.bbps_complaint_management_systems, AppConstants.getMnu_bbps_complaint_management_finacus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_bbps_complaint_status_finacus")) {
                    oldItemData.add(new ImageTextMenuModel("BBPS Complaint Status", R.drawable.bbps_complaint_status, AppConstants.getMnu_bbps_complaint_status_finacus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_bbps_complaint_history_finacus")) {
                    oldItemData.add(new ImageTextMenuModel("BBPS Complaint History", R.drawable.bbps_complaint_history, AppConstants.getMnu_bbps_complaint_history_finacus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_bbps_transaction_inquiry_finacus")) {
                    oldItemData.add(new ImageTextMenuModel("BBPS Transaction Inquiry", R.drawable.bbps_transaction_inquiry, AppConstants.getMnu_bbps_transaction_inquiry_finacus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_bbps_get_duplicate_receipt_finacus")) {
                    oldItemData.add(new ImageTextMenuModel("BBPS Duplicate Receipt", R.drawable.bbps_duplicate_recipt, AppConstants.getMnu_bbps_get_duplicate_recipt_finacus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_debit_card_pin_verify_finacus")) {
                    oldItemData.add(new ImageTextMenuModel("Debit Card Pin Verification", R.drawable.debit_card_pin_generation, AppConstants.getMnu_debit_card_pin_verify_finacus()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_rdfd_acc_open")) {
                    oldItemData.add(new ImageTextMenuModel("RD/FD Acc Opening", R.drawable.billpay, AppConstants.getMnu_rdfd_acc_open()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_debit_card_set_channel")) {
                    oldItemData.add(new ImageTextMenuModel("Debit Card Set Channel", R.drawable.debit_card_limit, AppConstants.getMnu_debit_card_set_channel()));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_form15GH")) {
                    oldItemData.add(new ImageTextMenuModel("Form 15GH", R.drawable.accountsd, AppConstants.getMnu_form15GH(), "Form 15GH"));
                }else if (aDynamicMenuModel.getSubmenucode().equalsIgnoreCase("mnu_standing_instructions")) {
                    oldItemData.add(new ImageTextMenuModel("Standing Instruction", R.drawable.accountsd, AppConstants.getMnu_standing_instructions(), "Standing Instruction"));
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