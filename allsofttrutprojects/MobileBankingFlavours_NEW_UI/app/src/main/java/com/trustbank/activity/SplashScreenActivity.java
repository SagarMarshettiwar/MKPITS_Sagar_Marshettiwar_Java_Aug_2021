package com.trustbank.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.AskPermissions;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SessionManager;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONException;
import org.json.JSONObject;

import static com.trustbank.util.MBank.loadAppLogo;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity implements AlertDialogOkListener {

    SessionManager session;
    TrustMethods methods;
    TextView app_title;
    AlertDialogOkListener alertDialogOkListener = this;
    private AskPermissions askPermissions;
    private static final int MY_REQUEST_CODE = 100;
    private static final int MY_REQUEST_CODE1 = 200;

    private ImageView ivAppLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        SetTheme.changeToTheme(SplashScreenActivity.this, true);
        setContentView(R.layout.content_splash);
        app_title = findViewById(R.id.app_title);
        ivAppLogo = findViewById(R.id.ivAppLogo);
        loadAppLogo(ivAppLogo);
        // Session class instance
        session = new SessionManager(SplashScreenActivity.this);
        methods = new TrustMethods(SplashScreenActivity.this);

        ArrayList<String> list = new ArrayList<>();
        String subId = methods.getSubscripId(SplashScreenActivity.this);

        if (!subId.isEmpty()) {
            SubscriptionManager subscriptionManager = (SubscriptionManager) SplashScreenActivity.this.getSystemService(SplashScreenActivity.this.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

            for (int i = 0; i < subscriptionInfoList.size(); i++) {
                list.add(String.valueOf(subscriptionInfoList.get(i).getSubscriptionId()));
            }

            if(list.contains(subId)){
                if (NetworkUtil.getConnectivityStatus(SplashScreenActivity.this)) {
                    new LoadHintsMessageAsyncTask(SplashScreenActivity.this).execute();
                } else {
                    TrustMethods.message(SplashScreenActivity.this, getResources().getString(R.string.error_check_internet));
                }
            }else{
                methods.setSubscripId(SplashScreenActivity.this, "");

                AlertDialogMethod.alertDialogOk(SplashScreenActivity.this,
                        "Privacy Alert!", "Registered Sim card not detected", getResources().getString(R.string.btn_ok),
                        1, false, alertDialogOkListener);
            }
        }else {

            if (NetworkUtil.getConnectivityStatus(SplashScreenActivity.this)) {
                new LoadHintsMessageAsyncTask(SplashScreenActivity.this).execute();
            } else {
                TrustMethods.message(SplashScreenActivity.this, getResources().getString(R.string.error_check_internet));
            }
        }

        if(TrustMethods.isEmulator())
        {
            TrustMethods.message(this, this.getResources().getString(R.string.device_emulator_message));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDialogOk(int resultCode) {
        if (resultCode == 1) {
            finish();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class LoadHintsMessageAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String securityHint, restricted_app_package1,sms_verify_number,mobile_number_verify,update_type, is_Dynamic_keybord_enable, restricted_app_package2,
                play_store_validate, play_store_mobile, play_store_clientid, is_4_digit_pin_enable,is_screenshotenable, isMPassbook,bankName, bankAddress,
                switchForCardNo, crypt_algo, restart_reg,show_tpin, imps_charges_msg,privacypolicyurl,IMPS_registered;
        int autoReadOtpTimeout, registrationTime;
        private String errorCode = "";

        public LoadHintsMessageAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SplashScreenActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.SecurityHintUrl();
                if (!url.equals("")) {
                    response = HttpClientWrapper.getResponseWithoutAuth(url);
                }
                if (response == null || response.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResult = (new JSONObject(response));
                if (jsonResult.has("error")) {
                    error = jsonResult.getString("error");
                    return error;
                }

                JSONObject messageObject = (new JSONObject(jsonResult.getString("message")));
                if (messageObject.has("error")) {
                    error = messageObject.getString("error");
                    return error;
                }
                securityHint = messageObject.has("security_code_hint") ? messageObject.getString("security_code_hint") : "NA";
                play_store_validate = messageObject.has("play_store") ? messageObject.getString("play_store") : "0";
                play_store_mobile = messageObject.has("play_store_mobile") ? messageObject.getString("play_store_mobile") : "";
                play_store_clientid = messageObject.has("play_store_clientid") ? messageObject.getString("play_store_clientid") : "";
                autoReadOtpTimeout = messageObject.has("otp_auto_read_timeout_seconds") ? messageObject.getInt("otp_auto_read_timeout_seconds") : 120000;
                is_4_digit_pin_enable = messageObject.has("is_4_digit_pin_enable") ? messageObject.getString("is_4_digit_pin_enable") : "";
                restricted_app_package1 = messageObject.has("restricted_app_package1") ? messageObject.getString("restricted_app_package1") : "";
                restricted_app_package2 = messageObject.has("restricted_app_package2") ? messageObject.getString("restricted_app_package2") : "";
                is_Dynamic_keybord_enable = messageObject.has("is_Dynamic_keybord_enable") ? messageObject.getString("is_Dynamic_keybord_enable") : "";
                update_type = messageObject.has("update_type") ? messageObject.getString("update_type") : "";
                mobile_number_verify= messageObject.has("mobile_number_verify") ? messageObject.getString("mobile_number_verify") : "";
                sms_verify_number= messageObject.has("sms_verify_number") ? messageObject.getString("sms_verify_number") : "";
                is_screenshotenable= messageObject.has("is_screenshotenable") ? messageObject.getString("is_screenshotenable") : "";
                isMPassbook= messageObject.has("is_mpassbook") ? messageObject.getString("is_mpassbook") : "";
                registrationTime= messageObject.has("registration_time") ? messageObject.getInt("registration_time") : 120000;
                bankName= messageObject.has("bank_name") ? messageObject.getString("bank_name") : "";
                bankAddress= messageObject.has("bank_address") ? messageObject.getString("bank_address") : "";
                switchForCardNo= messageObject.has("switch_for_card_no") ? messageObject.getString("switch_for_card_no") : "";
                crypt_algo= messageObject.has("crypt_algo") ? messageObject.getString("crypt_algo") : "0";
                restart_reg= messageObject.has("restart_reg") ? messageObject.getString("restart_reg") : "0";
                imps_charges_msg= messageObject.has("imps_charges_msg") ? messageObject.getString("imps_charges_msg") : "";
                privacypolicyurl = messageObject.has("privacy_policy_url") ? messageObject.getString("privacy_policy_url") : "NA";
                IMPS_registered = messageObject.has("IMPS_registered") ? messageObject.getString("IMPS_registered") : "1";
                show_tpin = messageObject.has("show_tpin") ? messageObject.getString("show_tpin") : "0";

                AppConstants.setSecurityCodeHint(securityHint);
                AppConstants.setAutoReadOtpTimeout(autoReadOtpTimeout);
                AppConstants.setPlay_store_validate(play_store_validate);
                AppConstants.setPlayStoreDemoUserMobile("7000010132");
                AppConstants.setPlayStoreDemoPasswordClientid("141985");
                AppConstants.setRestrictedapppackage1(restricted_app_package1);
                AppConstants.setRestrictedapppackage2(restricted_app_package2);
                AppConstants.setMobile_number_verify(mobile_number_verify);
                AppConstants.setSms_verify_number(sms_verify_number);
                AppConstants.setIs_screenshotenable(is_screenshotenable);
                AppConstants.setIsMPassbook(isMPassbook);
                AppConstants.setRegistrationTime(registrationTime);
                AppConstants.setBank_name(bankName);
                AppConstants.setBank_address(bankAddress);
                AppConstants.setSwitchForCardNo(switchForCardNo);
                AppConstants.setCrypt_algo(crypt_algo);
                AppConstants.setImps_charges_msg(imps_charges_msg);
                AppConstants.setPrivacypolicyurl(privacypolicyurl);
                AppConstants.setIMPS_registered(IMPS_registered);
                AppConstants.setShow_tpin(show_tpin);


                if (is_Dynamic_keybord_enable.equalsIgnoreCase("true")) {
                    AppConstants.Dynamic_keybord_enable = true;
                } else if (is_Dynamic_keybord_enable.equalsIgnoreCase("false")) {
                    AppConstants.Dynamic_keybord_enable = false;
                } else {
                    AppConstants.Dynamic_keybord_enable = false;
                }

                if (is_4_digit_pin_enable.equalsIgnoreCase("true")) {
                    AppConstants.four_digit_pin_enable = true;
                } else if (is_4_digit_pin_enable.equalsIgnoreCase("false")) {
                    AppConstants.four_digit_pin_enable = false;
                } else {
                    AppConstants.four_digit_pin_enable = true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (!this.error.equals("")) {
                    TrustMethods.message(SplashScreenActivity.this, error);
                } else {
                    if(isMPassbook.equals("1")){
                        app_title.setText("Mobile MPassbook");
                    }

                    if(restart_reg.equals("1")) {
                        boolean clearSharedPref = methods.getClearSharedPref(SplashScreenActivity.this);
                        if(!clearSharedPref){
                            SharedPreferences sharedPreferences = SplashScreenActivity.this.getSharedPreferences("BankApp", Context.MODE_PRIVATE);
                            SharedPreferences.Editor  editor = sharedPreferences.edit();
                            editor.remove("IsLoggedIn");
                            editor.apply();

                            methods.setClearSharedPref(SplashScreenActivity.this,true);
                        }
                    }

                    if(update_type.equalsIgnoreCase("flexible")){
                        Checkforupdateflexible();
                    }else if(update_type.equalsIgnoreCase("urgent")){
                        Checkforupdateurgent();
                    }else{
                        Checkforupdatedisable();
                   }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void threadCallSession() {
        Thread background;
        background = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                    session.checkLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        /// start thread
        background.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        askPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void Checkforupdateflexible(){
        try {
            AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

            try{
            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                AppUpdateType.FLEXIBLE,
                                this,
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        askPermissions = new AskPermissions(SplashScreenActivity.this, session);
                        if (askPermissions.checkAndRequestPermissions()) {
                            threadCallSession();
                        }
                    }
                }
            });
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Checkforupdatedisable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            askPermissions = new AskPermissions(SplashScreenActivity.this, session);
            if (askPermissions.checkAndRequestPermissions()) {
                threadCallSession();
            }
        }
    }

    private void Checkforupdateurgent() {
        try {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // an activity result launcher registered via registerForActivityResult
                                AppUpdateType.IMMEDIATE,
                                this,
                                // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                                // flexible updates.
                                MY_REQUEST_CODE1);
                    } catch (IntentSender.SendIntentException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        askPermissions = new AskPermissions(SplashScreenActivity.this, session);
                        if (askPermissions.checkAndRequestPermissions()) {
                            threadCallSession();
                        }
                    }
                }
        });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE1) {
            if (resultCode == RESULT_OK) {

            } else if (resultCode == RESULT_CANCELED) {
                System.exit(0);
            }
        }

        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    askPermissions = new AskPermissions(SplashScreenActivity.this, session);
                    if (askPermissions.checkAndRequestPermissions()) {
                        threadCallSession();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    askPermissions = new AskPermissions(SplashScreenActivity.this, session);
                    if (askPermissions.checkAndRequestPermissions()) {
                        threadCallSession();
                    }
                }
            }
        }
    }
}