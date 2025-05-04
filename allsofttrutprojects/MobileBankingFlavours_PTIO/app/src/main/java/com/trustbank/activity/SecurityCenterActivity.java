package com.trustbank.activity;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;

public class SecurityCenterActivity extends AppCompatActivity implements View.OnClickListener{
    private TrustMethods trustMethods;
    private TextView toolbar, fp_text;
    private ImageView backButton_new;
    LinearLayout txt_change_mpin_img, txt_change_tpin_img, txt_reset_tpin_img, switch_button_ll;
    boolean flag=true;
    Switch switch_button;
    Executor executor;
    String login_type;
    BiometricManager biometricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                if (savedInstanceState != null) {
                    Object currentPID = String.valueOf(android.os.Process.myPid());
                    // Check current PID with old PID
                    if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                        // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                        TrustMethods.naviagteToSplashScreen(SecurityCenterActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(SecurityCenterActivity.this, false);
        setContentView(R.layout.activity_security_center);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.SecurityCenter);
        initcomponent();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }

    private void initcomponent() {
        trustMethods = new TrustMethods(SecurityCenterActivity.this);
        backButton_new = findViewById(R.id.backButton_new);
        txt_change_mpin_img = findViewById(R.id.txt_change_mpin_img);
        txt_change_tpin_img = findViewById(R.id.txt_change_tpin_img);
        txt_reset_tpin_img = findViewById(R.id.txt_reset_tpin_img);
        switch_button_ll = findViewById(R.id.switch_button_ll);
        switch_button = findViewById(R.id.switch_button);
        fp_text=findViewById(R.id.fp_text);
        executor = ContextCompat.getMainExecutor(SecurityCenterActivity.this);

        if (AppConstants.getMnu_profile_settings().equalsIgnoreCase("1")) {
            txt_change_mpin_img.setVisibility(View.VISIBLE);
        } else {
            txt_change_mpin_img.setVisibility(View.GONE);
        }

        if (AppConstants.getMnu_profile_settings().equalsIgnoreCase("1")) {
            txt_change_tpin_img.setVisibility(View.VISIBLE);
        } else {
            txt_change_tpin_img.setVisibility(View.GONE);
        }

        if (AppConstants.getMnu_profile_settings().equalsIgnoreCase("1")) {
            txt_reset_tpin_img.setVisibility(View.VISIBLE);
        } else {
            txt_reset_tpin_img.setVisibility(View.GONE);
        }

        if (AppConstants.getMnu_profile_settings().equalsIgnoreCase("1")) {
            switch_button_ll.setVisibility(View.VISIBLE);
            new SettingStatusAsyncTask(SecurityCenterActivity.this).execute();
        } else {
            switch_button_ll.setVisibility(View.GONE);
        }

        backButton_new.setOnClickListener(this);
        txt_change_mpin_img.setOnClickListener(this);
        txt_change_tpin_img.setOnClickListener(this);
        txt_reset_tpin_img.setOnClickListener(this);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(SecurityCenterActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                login_type = "1";
                new FingurePrintsyncTask(SecurityCenterActivity.this, login_type).execute();
                trustMethods.Setfingureprintpref(SecurityCenterActivity.this,true);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                switch_button.setChecked(false);
            }
        });

        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(flag) {
                        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle(getResources().getString(R.string.Authentication))
                                .setDescription(getResources().getString(R.string.UseyourfingerprinttoScan)).setNegativeButtonText(getResources().getString(R.string.btn_cancel)).build();
                        biometricPrompt.authenticate(promptInfo);
                        biometricManager = androidx.biometric.BiometricManager.from(SecurityCenterActivity.this);
                        switch (biometricManager.canAuthenticate()) {
                            // this means we can use biometric sensor
                            case BiometricManager.BIOMETRIC_SUCCESS:
                                fp_text.setText(getResources().getString(R.string.Youcanusethefingerprintsensortologin));
                                break;
                            // this means that the device doesn't have fingerprint sensor
                            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                                fp_text.setText(getResources().getString(R.string.Thisdevicedoesnothaveafingerprintsensor));
                                break;
                            // this means that biometric sensor is not available
                            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                                fp_text.setText(getResources().getString(R.string.Thebiometricsensoriscurrentlyunavailable));
                                break;
                            // this means that the device doesn't contain your fingerprint
                            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                                fp_text.setText(getResources().getString(R.string.Yourdevicedoesnthavefingerprintsavedpleasecheckyoursecuritysettings));
                                break;
                        }
                    }
                } else {
                    login_type="0";
                    new FingurePrintsyncTask(SecurityCenterActivity.this,login_type).execute();
                    trustMethods.Setfingureprintpref(SecurityCenterActivity.this,false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton_new:
                Intent intent = new Intent(SecurityCenterActivity.this, ProfileSettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.txt_change_mpin_img:
                Intent intentMpin = new Intent(SecurityCenterActivity.this, ChangePINSActivity.class);
                intentMpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentMpin.putExtra("formType", "changeMpin");
                startActivity(intentMpin);
                finish();
                break;

            case R.id.txt_change_tpin_img:
                Intent intentTpin = new Intent(SecurityCenterActivity.this, ChangePINSActivity.class);
                intentTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentTpin.putExtra("formType", "changeTpin");
                startActivity(intentTpin);
                finish();
                break;

            case R.id.txt_reset_tpin_img:
                Intent intentResetTpin = new Intent(SecurityCenterActivity.this, ResetTPinActivity.class);
                intentResetTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentResetTpin);
                finish();
                break;

            default :
                    break;
        }
    }

    private class FingurePrintsyncTask extends AsyncTask<Void, Void, String> {

        String error = "";
        Context ctx;
        String response,responseCode;
        ProgressDialog pDialog;
        String result,login_type;
        String actionName = "SET_LOGIN_TYPE";
        private String errorCode;


        public FingurePrintsyncTask(Context ctx, String login_type) {
            this.ctx = ctx;
            this.login_type = login_type;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecurityCenterActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                String url = TrustURL.MobileNoVerifyUrl();
                // String  = "{\"profile_id\":\"" + AppConstants.getProfileID() + "\" ,\"login_type\":\"" + login_type + "\"}";
                String jsonString = "{\"profile_id\":\"" + AppConstants.getProfileID() + "\" ,\"login_type\":\"" + login_type + "\",\"tag\":\"SET\"}";

                TrustMethods.LogMessage("", "json string for change pass : " + jsonString);

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, actionName, AppConstants.getAuth_token());
                }

                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));
                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }
                responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {
                    JSONObject responseObject = jsonResponse.getJSONObject("response");
                    response = getResources().getString(R.string.FingerPrintActivated);
                } else {

                    errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                    error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
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
                    if(!error.equals("")){
                        Toast.makeText(ctx, ""+error, Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                switch_button.setChecked(false);
                            }
                        });
                    }else{
                        Intent in =new Intent(SecurityCenterActivity.this, SplashScreenActivity.class);
                        startActivity(in);
                        finish();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class SettingStatusAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        String result;
        String actionName = "GET_LOGIN_TYPE";
        ProgressDialog pDialog;
        String active_fingureprint;
        private String errorCode;
        JSONArray table;

        public SettingStatusAsyncTask(Context ctx) {
            this.ctx = ctx;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecurityCenterActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getSettingStatustype(AppConstants.getProfileID());
                if (!url.equals("")) {
                    result = HttpClientWrapper.getResponseGET(url, actionName, AppConstants.getAuth_token());
                }
                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));
                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {
                    table=jsonResponse.getJSONObject("response").getJSONArray("Table");
                    JSONObject JsonObject = table.getJSONObject(0);
                    response = JsonObject.has("login_option") ? JsonObject.getString("login_option") : "";
                    active_fingureprint=response;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(active_fingureprint != null){
                                if(active_fingureprint.equals("1"))
                                {
                                    flag=false;
                                    switch_button.setChecked(true);
                                }else {
                                    switch_button.setChecked(false);
                                }
                            }else{
                                switch_button.setChecked(false);
                            }
                        }
                    });
                } else {
                    errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                    error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        Toast.makeText(ctx, ""+error, Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}