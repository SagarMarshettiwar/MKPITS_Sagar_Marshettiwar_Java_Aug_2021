package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
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

public class SettingActivity extends AppCompatActivity {

    private CardView cvChangeMpinId, cvChangeTpinId, cvResetTpinId, cvTransactionLimitId, registerAnotherClientId, debitCardPinGenId;
    private TrustMethods trustMethods;
    TextView fp_text;
    Switch switch_button;
    boolean flag=true;
    Executor executor;
    LinearLayout switch_button_ll;
    BiometricManager biometricManager;
    String login_type;
    private RecyclerView recyclerViewHoriListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                if (savedInstanceState != null) {
                    Object currentPID = String.valueOf(android.os.Process.myPid());
                    // Check current PID with old PID
                    if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                        // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                        TrustMethods.naviagteToSplashScreen(SettingActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(SettingActivity.this, false);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        trustMethods = new TrustMethods(SettingActivity.this);
        executor = ContextCompat.getMainExecutor(SettingActivity.this);
        cvChangeMpinId = findViewById(R.id.cvChangeMpinId);
        cvChangeTpinId = findViewById(R.id.cvChangeTpinId);
        cvResetTpinId = findViewById(R.id.cvResetTpinId);
        cvTransactionLimitId = findViewById(R.id.transactionLimitId);
        registerAnotherClientId = findViewById(R.id.registerAnotherClientId);
        debitCardPinGenId = findViewById(R.id.debitCardPinGenId);
        switch_button = findViewById(R.id.switch_button);
        fp_text=findViewById(R.id.fp_text);
        switch_button_ll=findViewById(R.id.switch_button_ll);

        if (AppConstants.getMnu_Change_MPin().equalsIgnoreCase("1")) {
            cvChangeMpinId.setVisibility(View.VISIBLE);
        } else {
            cvChangeMpinId.setVisibility(View.GONE);
        }

        if (AppConstants.getMnu_Change_TPin().equalsIgnoreCase("1")) {
            cvChangeTpinId.setVisibility(View.VISIBLE);
        } else {
            cvChangeTpinId.setVisibility(View.GONE);
        }

        if (AppConstants.getMnu_Reset_TPin().equalsIgnoreCase("1")) {
            cvResetTpinId.setVisibility(View.VISIBLE);
        } else {
            cvResetTpinId.setVisibility(View.GONE);
        }

        if (AppConstants.getMnu_Limit_Transaction().equalsIgnoreCase("1")) {
            cvTransactionLimitId.setVisibility(View.VISIBLE);
        } else {
            cvTransactionLimitId.setVisibility(View.GONE);
        }

        if (AppConstants.getMnu_fingure_print().equalsIgnoreCase("1")) {
            switch_button_ll.setVisibility(View.VISIBLE);
            new SettingStatusAsyncTask(SettingActivity.this).execute();
        } else {
            switch_button_ll.setVisibility(View.GONE);
        }

        //This feature is shiftd to cards menu hence its visiblity is gone
        if (AppConstants.getMnu_debit_card_pin_generation().equalsIgnoreCase("1")) {
            debitCardPinGenId.setVisibility(View.GONE);
        } else {
            debitCardPinGenId.setVisibility(View.GONE);
        }


        listener();
        horizontalRecyclerView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }

    private void listener() {

        cvChangeMpinId.setOnClickListener(v -> {
            Intent intentChangeTpin = new Intent(this, ChangePINSActivity.class);
            intentChangeTpin.putExtra("formType", "changeMpin");
            intentChangeTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentChangeTpin);
            trustMethods.activityOpenAnimation();
        });

        cvChangeTpinId.setOnClickListener(v -> {
            Intent intentChangeTpin = new Intent(this, ChangePINSActivity.class);
            intentChangeTpin.putExtra("formType", "changeTpin");
            intentChangeTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentChangeTpin);
            trustMethods.activityOpenAnimation();

        });

        cvResetTpinId.setOnClickListener(v -> {
            Intent intentChangeTpin = new Intent(this, ResetTPinActivity.class);
            intentChangeTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentChangeTpin);
            trustMethods.activityOpenAnimation();

        });
        cvTransactionLimitId.setOnClickListener(v -> {
            Intent intentChangeTpin = new Intent(this, TransactionLimitActivity.class);
            intentChangeTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentChangeTpin);
            trustMethods.activityOpenAnimation();
        });

        registerAnotherClientId.setOnClickListener(v -> {
            Intent intentChangeTpin = new Intent(this, RegisterClientId.class);
            intentChangeTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentChangeTpin);
            trustMethods.activityOpenAnimation();
        });

        debitCardPinGenId.setOnClickListener(v -> {
            Intent intentChangeTpin = new Intent(this, DebitCardPinGenerationActivity.class);
            intentChangeTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentChangeTpin);
            trustMethods.activityOpenAnimation();
        });

        final BiometricPrompt biometricPrompt = new BiometricPrompt(SettingActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                login_type = "1";
                new FingurePrintsyncTask(SettingActivity.this, login_type).execute();
                trustMethods.Setfingureprintpref(SettingActivity.this,true);
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
                        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Authentication ")
                                .setDescription("Use your fingerprint to Scan ").setNegativeButtonText("Cancel").build();
                        biometricPrompt.authenticate(promptInfo);
                        biometricManager = androidx.biometric.BiometricManager.from(SettingActivity.this);
                        switch (biometricManager.canAuthenticate()) {

                            // this means we can use biometric sensor
                            case BiometricManager.BIOMETRIC_SUCCESS:
                                fp_text.setText("You can use the fingerprint sensor to login");
                                fp_text.setTextColor(Color.parseColor("#fafafa"));
                                break;

                            // this means that the device doesn't have fingerprint sensor
                            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                                fp_text.setText("This device doesnot have a fingerprint sensor");

                                break;

                            // this means that biometric sensor is not available
                            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                                fp_text.setText("The biometric sensor is currently unavailable");

                                break;

                            // this means that the device doesn't contain your fingerprint
                            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                                fp_text.setText("Your device doesn't have fingerprint saved,please check your security settings");

                                break;
                        }

                    }

                } else {
                    login_type="0";
                    new FingurePrintsyncTask(SettingActivity.this,login_type).execute();
                    trustMethods.Setfingureprintpref(SettingActivity.this,false);
                }
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(SettingActivity.this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(SettingActivity.this);
    }

    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        trustMethods.horizontalRecyclerView(this, recyclerViewHoriListId);
    }
    @SuppressLint("StaticFieldLeak")
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
            pDialog = new ProgressDialog(SettingActivity.this);
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
                    response = "FingerPrint Activated";
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
                        Intent in =new Intent(SettingActivity.this, SplashScreenActivity.class);
                        startActivity(in);
                        finish();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
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
            pDialog = new ProgressDialog(SettingActivity.this);
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
