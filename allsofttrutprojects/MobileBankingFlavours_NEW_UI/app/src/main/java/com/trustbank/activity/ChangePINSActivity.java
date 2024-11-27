package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SessionManager;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePINSActivity extends AppCompatActivity implements AlertDialogOkListener {

    private String formType;
    private SessionManager session;
    private EditText etoldTpin, etnewPinId, etConfrimPINId;
    private Button btnActivation;
    private CoordinatorLayout coordinatorLayoutPin;
    AlertDialogOkListener alertDialogOkListener = this;
    private TrustMethods trustMethods;
    private TextView textChangePin;
    private TextInputLayout etoldPinTextInput,etnewPinTextInput,etConfirmPinTextInput;
    RecyclerView recyclerViewHoriListId;


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
                        TrustMethods.naviagteToSplashScreen(ChangePINSActivity.this);
                    }
                }
            }

        SetTheme.changeToTheme(ChangePINSActivity.this, false);
        setContentView(R.layout.activity_change_t_p_i_n);
            trustMethods = new TrustMethods(ChangePINSActivity.this);

        inicomponent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void inicomponent() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        trustMethods = new TrustMethods(ChangePINSActivity.this);
        session = new SessionManager(ChangePINSActivity.this);
        etoldPinTextInput = findViewById(R.id.etoldPinTextInput);
        etnewPinTextInput = findViewById(R.id.etnewPinTextInput);
        etConfirmPinTextInput = findViewById(R.id.etConfirmPinTextInput);
        textChangePin = findViewById(R.id.textChangePinId);
        etoldTpin = findViewById(R.id.etoldTpin);
        etnewPinId = findViewById(R.id.etnewPinId);
        etConfrimPINId = findViewById(R.id.etConfrimPINId);
        btnActivation = findViewById(R.id.btnActivationDone);
        coordinatorLayoutPin = findViewById(R.id.coordinatorLayoutPin);


        Intent intent = getIntent();
        if (intent.hasExtra("formType")) {

            formType = intent.getStringExtra("formType");

           if (!TextUtils.isEmpty(formType)){
               if (formType.equalsIgnoreCase("changeTpin")) {
                   getSupportActionBar().setTitle("Change TPin");
                   etoldPinTextInput.setHint(getResources().getString(R.string.hint_old_tpin));
                   etnewPinTextInput.setHint(getResources().getString(R.string.hint_new_tpin));
                   etConfirmPinTextInput.setHint(getResources().getString(R.string.hint_confirm_tpin));
                   textChangePin.setText("Change TPIN");
                   horizontalRecyclerView();
                   if(AppConstants.four_digit_pin_enable){
                       etoldTpin.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
                       etnewPinId.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
                       etConfrimPINId.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
                   }else{
                       etoldTpin.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
                       etnewPinId.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
                       etConfrimPINId.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
                   }

               } else if (formType.equalsIgnoreCase("changeMpin")) {
                   getSupportActionBar().setTitle("Change MPin");
                   etoldPinTextInput.setHint(getResources().getString(R.string.hint_old_mpin));
                   etnewPinTextInput.setHint(getResources().getString(R.string.hint_new_mpin));
                   etConfirmPinTextInput.setHint(getResources().getString(R.string.hint_confirm_mpin));
                   textChangePin.setText("Change MPIN");
                   horizontalRecyclerView();
                   if(AppConstants.four_digit_pin_enable){
                       etoldTpin.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
                       etnewPinId.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
                       etConfrimPINId.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
                   }else{
                       etoldTpin.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
                       etnewPinId.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
                       etConfrimPINId.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
                   }
               }
           }

        }


        btnActivation.setOnClickListener(view -> {

            try {
                TrustMethods.hideSoftKeyboard(ChangePINSActivity.this);
                if(AppConstants.four_digit_pin_enable) {
                    if (TextUtils.isEmpty(etoldTpin.getText().toString())) {
                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_old_tpin), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_old_mpin), coordinatorLayoutPin);
                        }
                    } else if (TextUtils.isEmpty(etnewPinId.getText().toString())) {

                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.hint_tpin), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.hint_mpin), coordinatorLayoutPin);
                        }
                    } else if (TextUtils.isEmpty(etConfrimPINId.getText().toString())) {
                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_confirm_tpin), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_confirm_mpin), coordinatorLayoutPin);
                        }
                    } else if (!etnewPinId.getText().toString().equals(etConfrimPINId.getText().toString())) {
                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_mismatch_tpin), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_mismatch_mpin), coordinatorLayoutPin);
                        }
                    } else if (etnewPinId.getText().toString().length() != 4 || etConfrimPINId.getText().toString().length() != 4) {
                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_tpin_lenght), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_mpin_lenght), coordinatorLayoutPin);
                        }
                    } else {
                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(ChangePINSActivity.this)) {
                            String strOldPin = etoldTpin.getText().toString();
                            String strNewPIN = etnewPinId.getText().toString();
                            String strConfirmPin = etConfrimPINId.getText().toString();
                            if (formType.equalsIgnoreCase("changeMpin")) {
                                if (NetworkUtil.getConnectivityStatus(ChangePINSActivity.this)) {
                                    new ChangeMpinAsyncTask(ChangePINSActivity.this, strOldPin, strNewPIN, strConfirmPin).execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayoutPin);
                                }
                            } else {
                                if (NetworkUtil.getConnectivityStatus(ChangePINSActivity.this)) {
                                    new ChangeTpinAsyncTask(ChangePINSActivity.this, strOldPin, strNewPIN, strConfirmPin).execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayoutPin);
                                }
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(ChangePINSActivity.this);
                        }
                    }
                }else{

                    if (TextUtils.isEmpty(etoldTpin.getText().toString())) {
                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_old_tpin), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_old_mpin), coordinatorLayoutPin);
                        }
                    } else if (TextUtils.isEmpty(etnewPinId.getText().toString())) {

                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.hint_tpin), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.hint_mpin), coordinatorLayoutPin);
                        }
                    } else if (TextUtils.isEmpty(etConfrimPINId.getText().toString())) {
                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_confirm_tpin), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_confirm_mpin), coordinatorLayoutPin);
                        }
                    } else if (!etnewPinId.getText().toString().equals(etConfrimPINId.getText().toString())) {
                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_mismatch_tpin), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_mismatch_mpin), coordinatorLayoutPin);
                        }
                    } else if (etnewPinId.getText().toString().length() != 6 || etConfrimPINId.getText().toString().length() != 6) {
                        if (formType.equalsIgnoreCase("changeTpin")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_tpin_6_lenght), coordinatorLayoutPin);
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_mpin_6_lenght), coordinatorLayoutPin);
                        }
                    } else {
                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(ChangePINSActivity.this)) {
                            String strOldPin = etoldTpin.getText().toString();
                            String strNewPIN = etnewPinId.getText().toString();
                            String strConfirmPin = etConfrimPINId.getText().toString();
                            if (formType.equalsIgnoreCase("changeMpin")) {
                                if (NetworkUtil.getConnectivityStatus(ChangePINSActivity.this)) {
                                    new ChangeMpinAsyncTask(ChangePINSActivity.this, strOldPin, strNewPIN, strConfirmPin).execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayoutPin);
                                }
                            } else {
                                if (NetworkUtil.getConnectivityStatus(ChangePINSActivity.this)) {
                                    new ChangeTpinAsyncTask(ChangePINSActivity.this, strOldPin, strNewPIN, strConfirmPin).execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayoutPin);
                                }
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(ChangePINSActivity.this);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        });
    }

    @Override
    public void onDialogOk(int resultCode) {
        if (resultCode == 0) {
            Intent intent = new Intent(getApplicationContext(), LockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            trustMethods.activityCloseAnimation();
            finish();
        } else if (resultCode == 1) {
            trustMethods.activityCloseAnimation();
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ChangeTpinAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response, strOldPin, strNewPIN, strConfirmPin;
        ProgressDialog pDialog;

        String actionName = "CHANGE_TPIN";
        private String errorCode;
        private String result;

        public ChangeTpinAsyncTask(Context ctx, String strOldPin,
                                   String strNewPIN, String strConfirmPin) {
            this.ctx = ctx;
            this.strOldPin = strOldPin;
            this.strNewPIN = strNewPIN;
            this.strConfirmPin = strConfirmPin;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePINSActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();

                String jsonString = "{\"mobile_number\":\"" + AppConstants.getUSERMOBILENUMBER() + "\",\"pin_type\":\"" + "tpin" + "\"," + "\"old_tpin\":\"" + strOldPin + "\"," + "\"tpin\":\"" + strNewPIN + "\",\"confirm_tpin\":\"" + strConfirmPin + "\"}";  //TODO
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
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";
                if (responseCode.equals("1")) {
                    JSONObject responseObject = jsonResponse.getJSONObject("response");
                    if (responseObject.has("error")) {
                        error = jsonResponse.getString("error");
                        return error;
                    }
                    response = "TPin Changed Successfully.";

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
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (response != null) {
                AlertDialogMethod.alertDialogOk(ChangePINSActivity.this, response, "", getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
            }
            if (!this.error.equals("")) {
                if (TrustMethods.isSessionExpired(errorCode)) {
                    AlertDialogMethod.alertDialogOk(ChangePINSActivity.this, "Session Expired!", "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                } else if (TrustMethods.isSessionExpiredWithString(error)) {
                    AlertDialogMethod.alertDialogOk(ChangePINSActivity.this, "Session Expired!", "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                } else {
                    TrustMethods.showSnackBarMessage(this.error, coordinatorLayoutPin);
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ChangeMpinAsyncTask extends AsyncTask<Void, Void, String> {

        String error = "";
        Context ctx;
        String response, strOldPin, strNewPIN, strConfirmPin;
        ProgressDialog pDialog;
        String actionName = "CHANGE_MPIN";
        private String errorCode;
        private String result;

        public ChangeMpinAsyncTask(Context ctx, String strOldPin, String strNewPIN, String strConfirmPin) {
            this.ctx = ctx;
            this.strOldPin = strOldPin;
            this.strNewPIN = strNewPIN;
            this.strConfirmPin = strConfirmPin;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePINSActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();
                String jsonString = "{\"mobile_number\":\"" + AppConstants.getUSERMOBILENUMBER() + "\",\"pin_type\":\"" + "mpin" + "\"," + "\"old_mpin\":\"" + strOldPin + "\"," + "\"mpin\":\"" + strNewPIN + "\",\"confirm_mpin\":\"" + strConfirmPin + "\"}";  //TODO
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
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {
                    JSONObject responseObject = jsonResponse.getJSONObject("response");
                    if (responseObject.has("error")) {
                        error = jsonResponse.getString("error");
                        return error;
                    }
                    response = "MPin Changed Successfully.";

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
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (response != null) {
                AlertDialogMethod.alertDialogOk(ChangePINSActivity.this, response, "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
            }
            if (!this.error.equals("")) {
                if (TrustMethods.isSessionExpired(errorCode)) {
                    AlertDialogMethod.alertDialogOk(ChangePINSActivity.this, "Session Expired!",
                            "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                } else if (TrustMethods.isSessionExpiredWithString(error)) {
                    AlertDialogMethod.alertDialogOk(ChangePINSActivity.this, "Session Expired!",
                            "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                } else {
                    TrustMethods.showSnackBarMessage(this.error, coordinatorLayoutPin);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ChangePINSActivity.this, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(ChangePINSActivity.this);
    }
    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        trustMethods.horizontalRecyclerView(ChangePINSActivity.this, recyclerViewHoriListId);
    }


}