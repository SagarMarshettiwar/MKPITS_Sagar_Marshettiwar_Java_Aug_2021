package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.DialogFragment;

import com.trustbank.Model.BottomDynamicMenuModel;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.fragment.WelcomeMessageDiagligFragment;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SessionManager;
import com.trustbank.util.SetTheme;
import com.trustbank.util.SharePreferenceUtils;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

public class FingurePrintActivity extends AppCompatActivity implements AlertDialogOkListener {
    String ActiveScan;
    private TrustMethods method;
    private CoordinatorLayout coordinatorLayout;
    AlertDialogOkListener alertDialogOkListener = this;
    private SessionManager sessionManager;
    private SharePreferenceUtils sharePreferenceUtils;
    ArrayList<DynamicMenuModel> DynamicMenuModels;
    ArrayList<DynamicMenuModel> DynamicSubMenuModels;
    HashMap<String, List<DynamicMenuModel>>SubMenuMap;
    ArrayList<BottomDynamicMenuModel> bottomDynamicMenuModels;
    ArrayList<BottomDynamicMenuModel> BottomDynamicSubMenuModel;
    HashMap<String,List<BottomDynamicMenuModel>>BottomSubMenuMap;
    TextView msgtex;
    ImageView fingureprint, backButton_new;
    TextView toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SetTheme.changeToTheme(FingurePrintActivity.this, false);
        setContentView(R.layout.activity_fingure_print);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        method=new TrustMethods(FingurePrintActivity.this);
        sessionManager = new SessionManager(FingurePrintActivity.this);
        AppConstants.setUSERMOBILENUMBER(sessionManager.getMobileNUmber(SessionManager.KEY_MOBILE_NO, SessionManager.KEY_MOBILE_NO_OLD));
        coordinatorLayout=findViewById(R.id.coordinatorLayoutId);
        fingureprint=findViewById(R.id.fingureprint);
        fingureprint.setBackgroundResource(R.drawable.lock_fp);
        msgtex = findViewById(R.id.msgtext);
        backButton_new = findViewById(R.id.backButton_new);
        backButton_new.setVisibility(View.GONE);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(getResources().getString(R.string.FingerprintAuthentication));
        sharePreferenceUtils = new SharePreferenceUtils(FingurePrintActivity.this);
        sharePreferenceUtils.clearValue(AppConstants.AUTH_TOKEN);
        Executor executor = ContextCompat.getMainExecutor(this);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(FingurePrintActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if(errString.equals("Cancel")){
                    Intent i = new Intent(FingurePrintActivity.this, LockActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(FingurePrintActivity.this, LockActivity.class);
                    Toast.makeText(FingurePrintActivity.this, getResources().getString(R.string.Pleasewaitfor15secorUseMpin), Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                fingureprint.setBackgroundResource(R.drawable.success_fp);
                new AuthenticateFingureAsyncTask(FingurePrintActivity.this, AppConstants.getUSERMOBILENUMBER(), "9999", sessionManager.getClientId()).execute();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle(getResources().getString(R.string.Authentication))
                .setDescription(getResources().getString(R.string.UseyourfingerprinttoScan)).setNegativeButtonText(getResources().getString(R.string.btn_cancel)).build();

        biometricPrompt.authenticate(promptInfo);


        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // this means we can use biometric sensor
            case BiometricManager.BIOMETRIC_SUCCESS:
                msgtex.setText(getResources().getString(R.string.Youcanusethefingerprintsensortologin));
                msgtex.setTextColor(Color.parseColor("#fafafa"));
                break;

            // this means that the device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msgtex.setText(getResources().getString(R.string.Thisdevicedoesnothaveafingerprintsensor));

                break;

            // this means that biometric sensor is not available
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msgtex.setText(getResources().getString(R.string.Thebiometricsensoriscurrentlyunavailable));

                break;

            // this means that the device doesn't contain your fingerprint
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msgtex.setText(getResources().getString(R.string.Yourdevicedoesnthavefingerprintsavedpleasecheckyoursecuritysettings));

                break;
        }

    }

    @Override
    public void onDialogOk(int resultCode) {
        switch (resultCode) {
            case 0:
                Intent intent = new Intent(FingurePrintActivity.this, VerifyMobileNumber.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case 1:
                finish();
                break;
            default:
                break;
        }
    }

    private class AuthenticateFingureAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String mMobileNo;
        String mMpin, mClientId;
        String result;

        public AuthenticateFingureAsyncTask(Context ctx, String mobileNo, String mpin, String mClientId) {
            this.ctx = ctx;
            this.mMobileNo = mobileNo;
            this.mMpin = mpin;
            this.mClientId = mClientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FingurePrintActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.AuthenticateUserUrl();
                String jsonString = "{\"mobile_number\":\"" + mMobileNo + "\",\"login_type\":\""+1+"\",\"mpin\":\"" + mMpin + "\", \"custid\":\"" + mClientId + "\"}";
                TrustMethods.LogMessage("", "json string for change pass : " + jsonString);

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithoutHeader(url, jsonString);
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
                    JSONObject dataObject = jsonResponse.getJSONObject("response");
                    response = dataObject.has("auth_token") ? dataObject.getString("auth_token") : "NA";
                    AppConstants.setAuth_token(response);
                    sharePreferenceUtils.putString(AppConstants.AUTH_TOKEN, response);
                } else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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
                }

                if (!this.error.equals("")) {
                    TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    shakeAnimation();
                } else {
                    if (response != null) {

                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FingurePrintActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(FingurePrintActivity.this)) {
                                new GetMenuListAsyncTask(FingurePrintActivity.this, mClientId).execute();
//                                new GetGLCodeAsync(FingurePrintActivity.this, mClientId).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(FingurePrintActivity.this);
                            //   }
                        }
                    } else {
                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.WrongPasscode), coordinatorLayout);

                        //vibrate the dots layout
                        shakeAnimation();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetMenuListAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String result, mClientId;
        String actionName = "GET_MENUS";

        public GetMenuListAsyncTask(Context ctx, String mClientId) {
            this.ctx = ctx;
            this.mClientId = mClientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FingurePrintActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetMenuListUrl();
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
                    JSONObject dataObject = jsonResponse.getJSONObject("response");
                    if (dataObject.has("error")) {
                        error = dataObject.getString("error");
                        return error;
                    }

                    JSONObject rowJsonObject = dataObject.getJSONObject("row");
                    if (rowJsonObject.has("error")) {
                        error = rowJsonObject.getString("error");
                        return error;
                    }


                    AppConstants.setMnu_accounts(rowJsonObject.has("mnu_accounts") ? rowJsonObject.getString("mnu_accounts") : "0");
                    AppConstants.setMnu_account_overview(rowJsonObject.has("mnu_account_overview") ? rowJsonObject.getString("mnu_account_overview") : "0");
                    AppConstants.setMnu_account_details(rowJsonObject.has("mnu_account_details") ? rowJsonObject.getString("mnu_account_details") : "0");
                    AppConstants.setMnu_statement(rowJsonObject.has("mnu_statement") ? rowJsonObject.getString("mnu_statement") : "0");
                    AppConstants.setMnu_borrow(rowJsonObject.has("mnu_borrow") ? rowJsonObject.getString("mnu_borrow") : "0");
                    AppConstants.setMnu_loan_catalogue(rowJsonObject.has("mnu_loan_catalogue") ? rowJsonObject.getString("mnu_loan_catalogue") : "0");
                    AppConstants.setMnu_open_loan_account(rowJsonObject.has("mnu_open_loan_account") ? rowJsonObject.getString("mnu_open_loan_account") : "1");//todo in db
                    AppConstants.setMnu_loan_eligibility(rowJsonObject.has("mnu_loan_eligibility") ? rowJsonObject.getString("mnu_loan_eligibility") : "0");
                    AppConstants.setMnu_loan_simulator(rowJsonObject.has("mnu_loan_simulator") ? rowJsonObject.getString("mnu_loan_simulator") : "0");
                    AppConstants.setMnu_save(rowJsonObject.has("mnu_save") ? rowJsonObject.getString("mnu_save") : "0");
                    AppConstants.setMnu_savings_account(rowJsonObject.has("mnu_savings_account") ? rowJsonObject.getString("mnu_savings_account") : "0");
                    AppConstants.setMnu_open_saving_account(rowJsonObject.has("mnu_open_saving_account") ? rowJsonObject.getString("mnu_open_saving_account") : "0");
                    AppConstants.setMnu_cheque_book_request(rowJsonObject.has("mnu_cheque_book_request") ? rowJsonObject.getString("mnu_cheque_book_request") : "0");
                    AppConstants.setMnu_stop_cheque(rowJsonObject.has("mnu_stop_cheque") ? rowJsonObject.getString("mnu_stop_cheque") : "0");
                    AppConstants.setMnu_cheque_status(rowJsonObject.has("mnu_cheque_status") ? rowJsonObject.getString("mnu_cheque_status") : "0");
                    AppConstants.setMnu_debit_card(rowJsonObject.has("mnu_save_debit_card") ? rowJsonObject.getString("mnu_save_debit_card") : "0");
                    AppConstants.setMnu_investment(rowJsonObject.has("mnu_investment") ? rowJsonObject.getString("mnu_investment") : "0");
                    AppConstants.setMnu_investment_account(rowJsonObject.has("mnu_investment_account") ? rowJsonObject.getString("mnu_investment_account") : "0");
                    AppConstants.setMnu_open_investment_account(rowJsonObject.has("mnu_open_investment_account") ? rowJsonObject.getString("mnu_open_investment_account") : "0");
                    AppConstants.setMnu_investment_certificate(rowJsonObject.has("mnu_investment_certificate") ? rowJsonObject.getString("mnu_investment_certificate") : "0");
                    AppConstants.setMnu_close_investment_account(rowJsonObject.has("mnu_close_investment_account") ? rowJsonObject.getString("mnu_close_investment_account") : "0");
                    AppConstants.setMnu_investment_simulator(rowJsonObject.has("mnu_investment_simulator") ? rowJsonObject.getString("mnu_investment_simulator") : "0");
                    AppConstants.setMnu_saving_catalogue(rowJsonObject.has("mnu_saving_catalogue") ? rowJsonObject.getString("mnu_saving_catalogue") : "0");
                    AppConstants.setMnu_investment_catalogue(rowJsonObject.has("mnu_investment_catalogue") ? rowJsonObject.getString("mnu_investment_catalogue") : "0");
                    AppConstants.setMnu_transactions(rowJsonObject.has("mnu_transactions") ? rowJsonObject.getString("mnu_transactions") : "0");
                    AppConstants.setMnu_loan_repayment(rowJsonObject.has("mnu_loan_repayment") ? rowJsonObject.getString("mnu_loan_repayment") : "0");
                    AppConstants.setMnu_intra_bank_transfer(rowJsonObject.has("mnu_intra_bank_transfer") ? rowJsonObject.getString("mnu_intra_bank_transfer") : "0");
                    AppConstants.setMnu_self_account_transfer(rowJsonObject.has("mnu_self_account_transfer") ? rowJsonObject.getString("mnu_self_account_transfer") : "0");
                    AppConstants.setMnu_interbank_transfer(rowJsonObject.has("mnu_interbank_transfer") ? rowJsonObject.getString("mnu_interbank_transfer") : "0");
                    AppConstants.setMnu_manage_beneficiaries(rowJsonObject.has("mnu_manage_beneficiaries") ? rowJsonObject.getString("mnu_manage_beneficiaries") : "0");
                    AppConstants.setMnu_cheque(rowJsonObject.has("mnu_cheque") ? rowJsonObject.getString("mnu_cheque") : "0");
                    AppConstants.setMnu_debit_card1(rowJsonObject.has("mnu_trans_debit_card") ? rowJsonObject.getString("mnu_trans_debit_card") : "0");
                    AppConstants.setMnu_bills_payment(rowJsonObject.has("mnu_bills_payment") ? rowJsonObject.getString("mnu_bills_payment") : "0");
                    AppConstants.setMnu_visit_us(rowJsonObject.has("mnu_visit_us") ? rowJsonObject.getString("mnu_visit_us") : "0");
                    AppConstants.setMnu_locate_agencies(rowJsonObject.has("mnu_locate_agencies") ? rowJsonObject.getString("mnu_locate_agencies") : "0");
                    AppConstants.setMnu_locate_atm(rowJsonObject.has("mnu_locate_atm") ? rowJsonObject.getString("mnu_locate_atm") : "0");
                    AppConstants.setMnu_contact(rowJsonObject.has("mnu_contact") ? rowJsonObject.getString("mnu_contact") : "0");
                    AppConstants.setMnu_schedule_visit(rowJsonObject.has("mnu_schedule_visit") ? rowJsonObject.getString("mnu_schedule_visit") : "0");
                    AppConstants.setMnu_profile_settings(rowJsonObject.has("mnu_profile_settings") ? rowJsonObject.getString("mnu_profile_settings") : "0");
                    AppConstants.setMnu_personal_profile(rowJsonObject.has("mnu_personal_profile") ? rowJsonObject.getString("mnu_personal_profile") : "0");
                    AppConstants.setMnu_security_center(rowJsonObject.has("mnu_security_center") ? rowJsonObject.getString("mnu_security_center") : "0");
                    AppConstants.setMnu_transaction_limit(rowJsonObject.has("mnu_transaction_limit") ? rowJsonObject.getString("mnu_transaction_limit") : "0");
                    AppConstants.setMnu_feedback(rowJsonObject.has("mnu_feedback") ? rowJsonObject.getString("mnu_feedback") : "0");
                    AppConstants.setMnu_fingure_print(rowJsonObject.has("mnu_setting_fingure_print") ? rowJsonObject.getString("mnu_setting_fingure_print") : "0");

                    JSONObject mainscreenmenus=rowJsonObject.getJSONObject("main_screen_menus");
                    JSONArray menus=mainscreenmenus.getJSONArray("menu");
                    if(menus != null) {
                        DynamicMenuModels=new ArrayList<>();
                        SubMenuMap=new HashMap();
                        for (int i = 0; i < menus.length(); i++) {
                            JSONObject menuarry = menus.getJSONObject(i);
                            String menuCode = menuarry.getString("menu_code");
                            String caption = menuarry.getString("menu_caption");

                            DynamicMenuModel dynamicMenuModel=new DynamicMenuModel();
                            dynamicMenuModel.setMenucode(menuCode);
                            dynamicMenuModel.setCaption(caption);
                            DynamicMenuModels.add(dynamicMenuModel);

                            if (menuarry.has("sub_menus")) {
                                JSONObject mainscreensubmenus = menuarry.getJSONObject("sub_menus");
                                JSONArray submenus=mainscreensubmenus.getJSONArray("menu");
                                if(submenus != null){
                                    DynamicSubMenuModels=new ArrayList<>();
                                    for(int j=0;j<submenus.length();j++){
                                        JSONObject submenuarry = submenus.getJSONObject(j);
                                        String submenuCode = submenuarry.getString("menu_code");
                                        String subcaption = submenuarry.getString("menu_caption");

                                        DynamicMenuModel dynamicsubMenuModel=new DynamicMenuModel();
                                        dynamicsubMenuModel.setSubmenucode(submenuCode);
                                        dynamicsubMenuModel.setCaption(subcaption);
                                        DynamicSubMenuModels.add(dynamicsubMenuModel);
                                    }
                                    if(DynamicMenuModels.size()>0){
                                        SubMenuMap.put(menuCode,DynamicSubMenuModels);
                                        AppConstants.setSubmenu(SubMenuMap);
                                    }
                                }
                            }
                        }
                        AppConstants.setParentlist(DynamicMenuModels);
                    }

                } else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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
                }

                if (!this.error.equals("")) {
                    TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    if (error.equalsIgnoreCase("auth token expired.")) {
                        AlertDialogMethod.alertDialogOk(FingurePrintActivity.this,
                                getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(FingurePrintActivity.this, this.error, "", getResources().getString(R.string.btn_ok),
                                1, false, alertDialogOkListener);
                    }
                } else {
                    //.......................TODO..................... 13-12-2018
                    //sagarM
                    new GetWelcomeMessageAsyncTask(FingurePrintActivity.this, mClientId).execute();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //sagar
    @SuppressLint("StaticFieldLeak")
    private class GetWelcomeMessageAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        JSONArray welcome_res;
        ProgressDialog pDialog;
        String result, mClientId;
        String actionName = "GET_WELCOME_MESSAGE";

        public GetWelcomeMessageAsyncTask(Context ctx, String mClientId) {
            this.ctx = ctx;
            this.mClientId = mClientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FingurePrintActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetMenuListUrl();
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
                    welcome_res = jsonResponse.getJSONObject("response").getJSONArray("welcome");
                    Log.e("welcome_res", String.valueOf(welcome_res));

                }else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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
                }

                if (!this.error.equals("")) {
                    TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    if (error.equalsIgnoreCase("auth token expired.")) {
                        AlertDialogMethod.alertDialogOk(FingurePrintActivity.this,
                                getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(FingurePrintActivity.this, this.error, "", getResources().getString(R.string.btn_ok),
                                1, false, alertDialogOkListener);
                    }
                } else {
                    //.......................TODO..................... 13-12-2018
                    sessionManager.createLoginSession(AppConstants.getUSERMOBILENUMBER(), mClientId);
                    AppConstants.setCLIENTID(sessionManager.getClientId());
                    String welcomemessage="";
                    String welcomemessageexitflag="";

                    if(welcome_res != null && welcome_res.length() > 0) {
                        for(int i=0;i<welcome_res.length();i++)
                        {
                            JSONObject data = welcome_res.getJSONObject(i);
                            welcomemessage= data.getString("welcome_message");
                            welcomemessageexitflag= data.getString("welcome_message_exit_flag");

                            DialogFragment dialogFragment=new WelcomeMessageDiagligFragment(welcomemessage,welcomemessageexitflag);
                            dialogFragment.show(getSupportFragmentManager(),"My  Fragment");
                        }
                    }else {
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        method.activityOpenAnimation();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LockActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
        method.activityOpenAnimation();
    }
}