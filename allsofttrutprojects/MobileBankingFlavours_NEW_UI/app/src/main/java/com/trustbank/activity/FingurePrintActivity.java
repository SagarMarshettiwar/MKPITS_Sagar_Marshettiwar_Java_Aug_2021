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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageView fingureprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        if(TrustMethods.isEmulator())
        {
            TrustMethods.message(this, this.getResources().getString(R.string.device_emulator_message));
        }
        SetTheme.changeToTheme(FingurePrintActivity.this, false);
        setContentView(R.layout.activity_fingure_print);

        method=new TrustMethods(FingurePrintActivity.this);
        sessionManager = new SessionManager(FingurePrintActivity.this);
        AppConstants.setUSERMOBILENUMBER(sessionManager.getMobileNUmber(SessionManager.KEY_MOBILE_NO, SessionManager.KEY_MOBILE_NO_OLD));
        coordinatorLayout=findViewById(R.id.coordinatorLayoutId);
        fingureprint=findViewById(R.id.fingureprint);
        fingureprint.setBackgroundResource(R.drawable.lock_fp);
        msgtex = findViewById(R.id.msgtext);

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
                    Toast.makeText(FingurePrintActivity.this, "Please wait for 15 sec or Use Mpin", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                /*Toast.makeText(FingurePrintActivity.this, ""+result.getCryptoObject().getCipher(), Toast.LENGTH_SHORT).show();*/
                fingureprint.setBackgroundResource(R.drawable.success_fp);
                new AuthenticateFingureAsyncTask(FingurePrintActivity.this, AppConstants.getUSERMOBILENUMBER(), "9999", sessionManager.getClientId()).execute();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Authentication ")
                .setDescription("Use your fingerprint to Scan ").setNegativeButtonText("Cancel").build();

        biometricPrompt.authenticate(promptInfo);


        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // this means we can use biometric sensor
            case BiometricManager.BIOMETRIC_SUCCESS:
                msgtex.setText("You can use the fingerprint sensor to login");
                msgtex.setTextColor(Color.parseColor("#fafafa"));
                break;

            // this means that the device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msgtex.setText("This device doesnot have a fingerprint sensor");

                break;

            // this means that biometric sensor is not available
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msgtex.setText("The biometric sensor is currently unavailable");

                break;

            // this means that the device doesn't contain your fingerprint
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msgtex.setText("Your device doesn't have fingerprint saved,please check your security settings");

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
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(FingurePrintActivity.this);
                            //   }
                        }
                    } else {
                        TrustMethods.showSnackBarMessage("Wrong Pass code", coordinatorLayout);

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
    private class GetGLCodeAsync extends AsyncTask<Void, Void, String> {
        private String error = "";
        private Context ctx;
        List<String> neftlist,within_dr,within_cr,impslist,upilist,self_dr,self_cr,card_list,balance_list,ministatement_list,mpassbook_list;
        private ProgressDialog pDialog;
        private String result;
        private String mClientId;
        private String neft_tran,within_dr_tran,self_dr_tran,self_cr_tran,upi_tran,within_cr_tran,imps_tran,card_tran,ministatement_tran,mpassbook_tran,balance_tran;
        private String actionName = "GET_GLCODE";

        public GetGLCodeAsync(Context ctx, String mClientId) {
            this.ctx = ctx;
            this.mClientId = mClientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(ctx.getResources().getString(R.string.loading_wait));
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
                JSONObject jsonResponse = new JSONObject(result);

                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }

                neftlist = new ArrayList<>();
                within_dr = new ArrayList<>();
                impslist  = new ArrayList<>();
                upilist   = new ArrayList<>();
                self_dr   = new ArrayList<>();
                self_cr   = new ArrayList<>();
                within_cr = new ArrayList<>();
                card_list = new ArrayList<>();
                balance_list= new ArrayList<>();
                ministatement_list= new ArrayList<>();
                mpassbook_list= new ArrayList<>();
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {
                    JSONObject dataObject = jsonResponse.getJSONObject("response");

                    if (dataObject.has("error")) {
                        error = dataObject.getString("error");
                        return error;
                    }

                    JSONObject rowJsonObject = dataObject.getJSONObject("data");

                    if (rowJsonObject.has("error")) {
                        error = rowJsonObject.getString("error");
                        return error;
                    }

                    if (rowJsonObject.has("gl_allowed")) {
                        JSONObject glTypeData = rowJsonObject.getJSONObject("gl_allowed");
                        neft_tran = glTypeData.optString("neft_tran", "");

                        imps_tran = glTypeData.optString("imps_tran", "");

                        within_dr_tran = glTypeData.optString("within_dr_tran", "");

                        self_dr_tran = glTypeData.optString("self_dr_tran", "");

                        self_cr_tran = glTypeData.optString("self_cr_tran", "");

                        upi_tran = glTypeData.optString("upi_tran", "");

                        within_cr_tran = glTypeData.optString("within_cr_tran", "");

                        card_tran = glTypeData.optString("card_tran", "");
                        ministatement_tran = glTypeData.optString("mini_tran", "");
                        mpassbook_tran = glTypeData.optString("mpassbook_tran", "");
                        balance_tran = glTypeData.optString("bal_tran", "");

                        String[] res = neft_tran.split(",", 0);
                        String[] res1 = within_dr_tran.split(",", 0);
                        String[] res2 = self_dr_tran.split(",", 0);
                        String[] res3 = self_cr_tran.split(",", 0);
                        String[] res4 = upi_tran.split(",", 0);
                        String[] res5 = within_cr_tran.split(",", 0);
                        String[] res6 = imps_tran.split(",", 0);
                        String[] res7 = card_tran.split(",", 0);
                        String[] res8 = ministatement_tran.split(",", 0);
                        String[] res9 = mpassbook_tran.split(",", 0);
                        String[] res10 = balance_tran.split(",", 0);

                        neftlist.addAll(Arrays.asList(res));
                        within_dr.addAll(Arrays.asList(res1));
                        impslist.addAll(Arrays.asList(res6));
                        upilist.addAll(Arrays.asList(res4));
                        self_dr.addAll(Arrays.asList(res2));
                        self_cr.addAll(Arrays.asList(res3));
                        within_cr.addAll(Arrays.asList(res5));
                        card_list.addAll(Arrays.asList(res7));
                        mpassbook_list.addAll(Arrays.asList(res9));
                        ministatement_list.addAll(Arrays.asList(res8));
                        balance_list.addAll(Arrays.asList(res10));

                    }
                    AppConstants.setNeft_list(neftlist);
                    AppConstants.setWithin_list(within_dr);
                    AppConstants.setImpslist(impslist);
                    AppConstants.setSelf_dr(self_dr);
                    AppConstants.setUpilist(upilist);
                    AppConstants.setSelf_cr(self_cr);
                    AppConstants.setWithin_cr(within_cr);
                    AppConstants.setCard_list(card_list);
                    AppConstants.setMpassbook_list(mpassbook_list);
                    AppConstants.setMiniststement_list(ministatement_list);
                    AppConstants.setBalance_list(balance_list);

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
            return error;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!error.equals("")) {
                    TrustMethods.showSnackBarMessage(error, coordinatorLayout);

                    if (error.equalsIgnoreCase("auth token expired.")) {
                        AlertDialogMethod.alertDialogOk(ctx, ctx.getResources().getString(R.string.error_session_expire),
                                "", ctx.getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(ctx, error, "", ctx.getResources().getString(R.string.btn_ok),
                                1, false, alertDialogOkListener);
                    }
                }else{
                    new GetWelcomeMessageAsyncTask(FingurePrintActivity.this, mClientId).execute();
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
                    AppConstants.setMnu_fundtransfer(rowJsonObject.has("mnu_fundtransfer") ? rowJsonObject.getString("mnu_fundtransfer") : "0");
                    AppConstants.setMnu_locate_atms(rowJsonObject.has("mnu_locate_atms") ? rowJsonObject.getString("mnu_locate_atms") : "0");
                    AppConstants.setMnu_locate_branch(rowJsonObject.has("mnu_locate_branch") ? rowJsonObject.getString("mnu_locate_branch") : "0");
                    AppConstants.setMnu_contact_us(rowJsonObject.has("mnu_contact_us") ? rowJsonObject.getString("mnu_contact_us") : "0");
                    AppConstants.setMnu_about_us(rowJsonObject.has("mnu_about_us") ? rowJsonObject.getString("mnu_about_us") : "0");
                    AppConstants.setMnu_accounts_menu_accountdetails(rowJsonObject.has("mnu_accounts_menu_accountdetails") ? rowJsonObject.getString("mnu_accounts_menu_accountdetails") : "0");
                    AppConstants.setMnu_accounts_menu_balenquiry(rowJsonObject.has("mnu_accounts_menu_balenquiry") ? rowJsonObject.getString("mnu_accounts_menu_balenquiry") : "0");
                    AppConstants.setMnu_accounts_menu_ministatemnt(rowJsonObject.has("mnu_accounts_menu_ministatemnt") ? rowJsonObject.getString("mnu_accounts_menu_ministatemnt") : "0");
                    AppConstants.setMnu_accounts_menu_showmmid(rowJsonObject.has("mnu_accounts_menu_showmmid") ? rowJsonObject.getString("mnu_accounts_menu_showmmid") : "0");
                    AppConstants.setMnu_accounts_menu_last5imps(rowJsonObject.has("mnu_accounts_menu_last5imps") ? rowJsonObject.getString("mnu_accounts_menu_last5imps") : "0");
                    AppConstants.setMnu_accounts_menu_neftenquiry(rowJsonObject.has("mnu_accounts_menu_neftenquiry") ? rowJsonObject.getString("mnu_accounts_menu_neftenquiry") : "0");
                    AppConstants.setMnu_fundtransfer_ownbank(rowJsonObject.has("mnu_fundtransfer_ownbank") ? rowJsonObject.getString("mnu_fundtransfer_ownbank") : "0");
                    AppConstants.setMnu_fundtransfer_impstoaccount(rowJsonObject.has("mnu_fundtransfer_impstoaccount") ? rowJsonObject.getString("mnu_fundtransfer_impstoaccount") : "0");
                    AppConstants.setMnu_fundtransfer_nefttoaccount(rowJsonObject.has("mnu_fundtransfer_nefttoaccount") ? rowJsonObject.getString("mnu_fundtransfer_nefttoaccount") : "0");
                    AppConstants.setMnu_fundtransfer_impstomobile(rowJsonObject.has("mnu_fundtransfer_impstomobile") ? rowJsonObject.getString("mnu_fundtransfer_impstomobile") : "0");
                    AppConstants.setMnu_fundtransfer_upi_collect_money(rowJsonObject.has("mnu_fundtransfer_upi_collect_money") ? rowJsonObject.getString("mnu_fundtransfer_upi_collect_money") : "0");
                    AppConstants.setMnu_fundtransfer_mngbenefeciaries(rowJsonObject.has("mnu_fundtransfer_mngbenefeciaries") ? rowJsonObject.getString("mnu_fundtransfer_mngbenefeciaries") : "0");
                    AppConstants.setMnu_faq(rowJsonObject.has("mnu_FAQ") ? rowJsonObject.getString("mnu_FAQ") : "0");
                    AppConstants.setMnu_checkbook_request(rowJsonObject.has("mnu_accounts_menu_cheqbkreq") ? rowJsonObject.getString("mnu_accounts_menu_cheqbkreq") : "0");
                    AppConstants.setMnu_account_statement(rowJsonObject.has("mnu_accounts_menu_acc_stmnt") ? rowJsonObject.getString("mnu_accounts_menu_acc_stmnt") : "0");
                    AppConstants.setMnu_self_transfer_to_account(rowJsonObject.has("mnu_fundtransfer_menu_selftrf") ? rowJsonObject.getString("mnu_fundtransfer_menu_selftrf") : "0");
                    AppConstants.setMnu_Change_MPin(rowJsonObject.has("mnu_settings_menu_change_mpin") ? rowJsonObject.getString("mnu_settings_menu_change_mpin") : "0");
                    AppConstants.setMnu_Change_TPin(rowJsonObject.has("mnu_settings_menu_change_tpin") ? rowJsonObject.getString("mnu_settings_menu_change_tpin") : "0");
                    AppConstants.setMnu_Reset_TPin(rowJsonObject.has("mnu_settings_menu_reset_tpin") ? rowJsonObject.getString("mnu_settings_menu_reset_tpin") : "0");
                    AppConstants.setMnu_Limit_Transaction(rowJsonObject.has("mnu_settings_menu_change_limit") ? rowJsonObject.getString("mnu_settings_menu_change_limit") : "0");
                    AppConstants.setMnu_setting(rowJsonObject.has("mnu_settings") ? rowJsonObject.getString("mnu_settings") : "0");
                    AppConstants.setStopChequebookStatus(rowJsonObject.has("mnu_accounts_stop_chq") ? rowJsonObject.getString("mnu_accounts_stop_chq") : "0");
                    AppConstants.setInqueriChquebookStatus(rowJsonObject.has("mnu_accounts_chq_status") ? rowJsonObject.getString("mnu_accounts_chq_status") : "0");
                    AppConstants.setMnu_accounts_menu_balenquiry_cbs(rowJsonObject.has("mnu_accounts_menu_balenquiry_cbs") ? rowJsonObject.getString("mnu_accounts_menu_balenquiry_cbs") : "0");
                    AppConstants.setMnu_accounts_menu_ministatemnt_cbs(rowJsonObject.has("mnu_accounts_menu_ministatemnt_cbs") ? rowJsonObject.getString("mnu_accounts_menu_ministatemnt_cbs") : "0");
                    AppConstants.setMnu_accounts_menu_showmmid_cbs(rowJsonObject.has("mnu_accounts_menu_showmmid_cbs") ? rowJsonObject.getString("mnu_accounts_menu_showmmid_cbs") : "0");
                    AppConstants.setMnu_accounts_menu_last5imps_cbs(rowJsonObject.has("mnu_accounts_menu_last5imps_cbs") ? rowJsonObject.getString("mnu_accounts_menu_last5imps_cbs") : "0");
                    AppConstants.setMnu_beneficiary_own_bank(rowJsonObject.has("mnu_beneficiary_own_bank") ? rowJsonObject.getString("mnu_beneficiary_own_bank") : "0");
                    AppConstants.setMnu_beneficiary_imps_neft_account_bank(rowJsonObject.has("mnu_beneficiary_imps_neft_account_bank") ? rowJsonObject.getString("mnu_beneficiary_imps_neft_account_bank") : "0");
                    AppConstants.setMnu_beneficiary_imps_mobile_bank(rowJsonObject.has("mnu_beneficiary_imps_mobile_bank") ? rowJsonObject.getString("mnu_beneficiary_imps_mobile_bank") : "0");
                    AppConstants.setMnu_pps_request(rowJsonObject.has("mnu_accounts_menu_pps_request") ? rowJsonObject.getString("mnu_accounts_menu_pps_request") : "0");
                    AppConstants.setMnu_pps_request_enquiry(rowJsonObject.has("mnu_accounts_menu_pps_request_enquiry") ? rowJsonObject.getString("mnu_accounts_menu_pps_request_enquiry") : "0");
                    AppConstants.setMnu_block_debit_card(rowJsonObject.has("mnu_account_block_debit_card") ? rowJsonObject.getString("mnu_account_block_debit_card") : "0");
                    AppConstants.setMnu_verify_beneficiary_name(rowJsonObject.has("mnu_verify_beneficiary_name") ? rowJsonObject.getString("mnu_verify_beneficiary_name") : "0");
                    AppConstants.setMnu_check_imps_transaction_status(rowJsonObject.has("mnu_imps_transction_status") ? rowJsonObject.getString("mnu_imps_transction_status") : "0");
                    AppConstants.setMnu_neft_trans_switch_transaction(rowJsonObject.has("mnu_neft_switch_transaction") ? rowJsonObject.getString("mnu_neft_switch_transaction") : "0");
                    AppConstants.setMnu_bill_pay(rowJsonObject.has("mnu_bill_pay") ? rowJsonObject.getString("mnu_bill_pay") : "0");
                    AppConstants.setMnu_bill_pay_complaint_management(rowJsonObject.has("mnu_bill_pay_complaint_management") ? rowJsonObject.getString("mnu_bill_pay_complaint_management") : "0");
                    AppConstants.setMnu_bill_pay_register_complaints(rowJsonObject.has("mnu_bill_pay_register_complaints") ? rowJsonObject.getString("mnu_bill_pay_register_complaints") : "0");
                    AppConstants.setMnu_bill_pay_track_complaints(rowJsonObject.has("mnu_bill_pay_track_complaints") ? rowJsonObject.getString("mnu_bill_pay_track_complaints") : "0");
                    AppConstants.setMnu_fundtransfer_upi(rowJsonObject.has("mnu_fundtransfer_upi") ? rowJsonObject.getString("mnu_fundtransfer_upi") : "0");
                    AppConstants.setCheckImpsTransStatusFundTransfer(rowJsonObject.has("imps_trans_status_ft") ? rowJsonObject.getString("imps_trans_status_ft") : "0"); //after successfull imps transaction call transaction status apis.
                    AppConstants.setMnu_block_debit_card_switch(rowJsonObject.has("mnu_block_debit_card_switch") ? rowJsonObject.getString("mnu_block_debit_card_switch") : "0"); //TODO
                    AppConstants.setMnu_block_debit_card_cbs(rowJsonObject.has("mnu_block_debit_card_cbs") ? rowJsonObject.getString("mnu_block_debit_card_cbs") : "0"); //TODO
                    AppConstants.setMnu_debit_card_pin_generation(rowJsonObject.has("mnu_debit_card_pin_generation") ? rowJsonObject.getString("mnu_debit_card_pin_generation") : "0");//TODO
                    AppConstants.setMnu_mandate_cancel(rowJsonObject.has("mnu_mandate_cancel") ? rowJsonObject.getString("mnu_mandate_cancel") : "0");
                    AppConstants.setMnu_fundtransfer_upi_get_qr(rowJsonObject.has("mnu_fetch_qr") ? rowJsonObject.getString("mnu_fetch_qr") : "0");
                    AppConstants.setMnu_fundtransfer_upi_create_qr(rowJsonObject.has("mnu_create_new_qr") ? rowJsonObject.getString("mnu_create_new_qr") : "0");
                    AppConstants.setMnu_UPI(rowJsonObject.has("mnu_upi") ? rowJsonObject.getString("mnu_upi") : "0");
                    AppConstants.setMnu_bill(rowJsonObject.has("mnu_bill") ? rowJsonObject.getString("mnu_bill") : "0");
                    AppConstants.setMnu_rd_fd_reckoner(rowJsonObject.has("mnu_rd_fd_reckoner") ? rowJsonObject.getString("mnu_rd_fd_reckoner") : "0");
                    AppConstants.setMnu_amortization_chart(rowJsonObject.has("mnu_amortization_chart") ? rowJsonObject.getString("mnu_amortization_chart") : "0");
                    AppConstants.setMnu_cards(rowJsonObject.has("mnu_cards") ? rowJsonObject.getString("mnu_cards") : "0");
                    AppConstants.setMnu_services(rowJsonObject.has("mnu_Services") ? rowJsonObject.getString("mnu_Services") : "0");
                    AppConstants.setMnu_locate_us(rowJsonObject.has("mnu_locate_us") ? rowJsonObject.getString("mnu_locate_us") : "0");
                    AppConstants.setMnu_need_help(rowJsonObject.has("mnu_need_help") ? rowJsonObject.getString("mnu_need_help") : "0");
                    AppConstants.setMnu_nominee_management(rowJsonObject.has("mnu_nominee_management") ? rowJsonObject.getString("mnu_nominee_management") : "0");
                    AppConstants.setMnu_fingure_print(rowJsonObject.has("mnu_setting_fingure_print") ? rowJsonObject.getString("mnu_setting_fingure_print") : "0");

                    AppConstants.setMpassbook_menu(rowJsonObject.has("mpassbook_menu") ? rowJsonObject.getString("mpassbook_menu") : "0");

                    AppConstants.setMnu_debit_card_limit(rowJsonObject.has("mnu_debit_card_limit") ? rowJsonObject.getString("mnu_debit_card_limit") : "0");
                    AppConstants.setMnu_temp_block_debit_card(rowJsonObject.has("mnu_temp_block_debit_card") ? rowJsonObject.getString("mnu_temp_block_debit_card") : "0");

                    AppConstants.setMnu_bbps(rowJsonObject.has("mnu_bbps_finacus") ? rowJsonObject.getString("mnu_bbps_finacus") : "0");
                    AppConstants.setMnu_bbps_bill_payment_finacus(rowJsonObject.has("mnu_bbps_bill_payment_finacus") ? rowJsonObject.getString("mnu_bbps_bill_payment_finacus") : "0");
                    AppConstants.setMnu_bbps_transaction_history_finacus(rowJsonObject.has("mnu_bbps_transaction_history_finacus") ? rowJsonObject.getString("mnu_bbps_transaction_history_finacus") : "0");
                    AppConstants.setMnu_bbps_complaint_management_finacus(rowJsonObject.has("mnu_bbps_complaint_management_finacus") ? rowJsonObject.getString("mnu_bbps_complaint_management_finacus") : "0");
                    AppConstants.setMnu_bbps_complaint_status_finacus(rowJsonObject.has("mnu_bbps_complaint_status_finacus") ? rowJsonObject.getString("mnu_bbps_complaint_status_finacus") : "0");
                    AppConstants.setMnu_bbps_complaint_history_finacus(rowJsonObject.has("mnu_bbps_complaint_history_finacus") ? rowJsonObject.getString("mnu_bbps_complaint_history_finacus") : "0");
                    AppConstants.setMnu_bbps_transaction_inquiry_finacus(rowJsonObject.has("mnu_bbps_transaction_inquiry_finacus") ? rowJsonObject.getString("mnu_bbps_transaction_inquiry_finacus") : "0");
                    AppConstants.setMnu_bbps_get_duplicate_recipt_finacus(rowJsonObject.has("mnu_bbps_get_duplicate_receipt_finacus") ? rowJsonObject.getString("mnu_bbps_get_duplicate_receipt_finacus") : "0");
                    AppConstants.setMnu_debit_card_pin_verify_finacus(rowJsonObject.has("mnu_debit_card_pin_verify_finacus") ? rowJsonObject.getString("mnu_debit_card_pin_verify_finacus") : "0");
                    AppConstants.setMnu_rdfd_acc_open(rowJsonObject.has("mnu_rdfd_acc_open") ? rowJsonObject.getString("mnu_rdfd_acc_open") : "0");
                    AppConstants.setMnu_privacypolicy(rowJsonObject.has("mnu_privacypolicy") ? rowJsonObject.getString("mnu_privacypolicy") : "0");
                    AppConstants.setMnu_debit_card_set_channel(rowJsonObject.has("mnu_debit_card_set_channel") ? rowJsonObject.getString("mnu_debit_card_set_channel") : "0");
                    AppConstants.setMnu_form15GH(rowJsonObject.has("mnu_form15GH") ? rowJsonObject.getString("mnu_form15GH") : "0");
                    AppConstants.setMnu_standing_instructions(rowJsonObject.has("mnu_standing_instructions") ? rowJsonObject.getString("mnu_standing_instructions") : "0");


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
                    JSONObject bottommenu=rowJsonObject.getJSONObject("bottom_menus");
                    JSONArray botommenus=bottommenu.getJSONArray("menu");
                    if(botommenus != null) {
                        bottomDynamicMenuModels=new ArrayList<>();
                        BottomSubMenuMap=new HashMap<>();
                        for (int i = 0; i < botommenus.length(); i++) {
                            JSONObject bottommenuarry = botommenus.getJSONObject(i);
                            String bmenuCode = bottommenuarry.getString("menu_code");
                            String bcaption = bottommenuarry.getString("menu_caption");

                            BottomDynamicMenuModel bottomDynamicMenuModel=new BottomDynamicMenuModel();
                            bottomDynamicMenuModel.setBottommenucode(bmenuCode);
                            bottomDynamicMenuModel.setBottomcaption(bcaption);
                            bottomDynamicMenuModels.add(bottomDynamicMenuModel);


                            if (bottommenuarry.has("sub_menus")) {
                                JSONObject bottomsubmenus = bottommenuarry.getJSONObject("sub_menus");
                                JSONArray bottomsubmenusarr=bottomsubmenus.getJSONArray("menu");
                                if(bottomsubmenusarr != null){
                                    BottomDynamicSubMenuModel=new ArrayList<>();
                                    for(int j=0;j<bottomsubmenusarr.length();j++){
                                        JSONObject submenuarry = bottomsubmenusarr.getJSONObject(j);
                                        String bsubmenuCode = submenuarry.getString("menu_code");
                                        String bsubcaption = submenuarry.getString("menu_caption");

                                        BottomDynamicMenuModel bottomdynamicsubMenuModel=new BottomDynamicMenuModel();
                                        bottomdynamicsubMenuModel.setBottomsubmenucode(bsubmenuCode);
                                        bottomdynamicsubMenuModel.setBottomcaption(bsubcaption);
                                        BottomDynamicSubMenuModel.add(bottomdynamicsubMenuModel);
                                    }
                                    if(bottomDynamicMenuModels.size()>0){
                                        BottomSubMenuMap.put(bmenuCode,BottomDynamicSubMenuModel);
                                        //AppConstants.setBottomsubmenu(BottomSubMenuMap);
                                    }
                                }
                            }
                        }
                        AppConstants.setBottomparentlist(bottomDynamicMenuModels);
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
                    new GetGLCodeAsync(FingurePrintActivity.this, mClientId).execute();
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