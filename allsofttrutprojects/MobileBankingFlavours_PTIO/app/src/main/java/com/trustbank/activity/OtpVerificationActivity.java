package com.trustbank.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputLayout;
import com.trustbank.Model.FundTransferSubModel;
import com.trustbank.Model.GenerateStanRRNModel;
import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogListener;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.tcpconnection.tmessage.TMessage;
import com.trustbank.tcpconnection.tmessage.TMessageUtil;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;
import com.trustbank.util.Validation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
public class OtpVerificationActivity extends AppCompatActivity implements View.OnClickListener,
        AlertDialogOkListener, AlertDialogListener {

    private String mSecurityCode;
    private TrustMethods method;
    private EditText etFundTransferMpin;
    PinView etFundTransferOtp;
    private TextView txtResendOtp;
    private CoordinatorLayout coordinatorLayout;
    private TextView txtCounter;
    private String accountType;
    private ArrayList<FundTransferSubModel> fundTransferModalDataList;
    private AlertDialogOkListener alertDialogOkListener = this;
    private AlertDialogListener alertDialogListener = this;
    private String bMobileNo, bNickname, bAccName, bAccNo, bIfscCode, bBenfMobNo, bMmid, bBen_type, upi_id;
    private boolean bWithinChecked;
    private String mmid;
    private String mmidRequestType;
    private String strSecurityPin, strPIN, strConfirmPin;
    private String limitPerDay, trf_type, limitType, accountNo, umrnNo, ecsMandateId;
    private String mClientId;
    private String debitcardNo, debitCardMode, otherReasonDebitCard;
    private String benAcNameUpi, upiid;
    private ImageView backButton_new;
    private TextView toolbar;

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
                        TrustMethods.naviagteToSplashScreen(OtpVerificationActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(OtpVerificationActivity.this, false);
        setContentView(R.layout.activity_fund_transfer_otp);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

        inIt();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }


    private void inIt() {

        try {
            method = new TrustMethods(OtpVerificationActivity.this);
            TextInputLayout etTpinId = findViewById(R.id.etTpinId);
            etFundTransferOtp = findViewById(R.id.etFundTransferOtpId);
            etFundTransferMpin = findViewById(R.id.etFundTransferMpinId);
            txtResendOtp = findViewById(R.id.txtResendOtpId);
            txtCounter = findViewById(R.id.txtCounterId);
            Button btnWithBankNext = findViewById(R.id.btnWithBankNextId);
            coordinatorLayout = findViewById(R.id.coordinatorLayout);
            backButton_new = findViewById(R.id.backButton_new);

            backButton_new.setOnClickListener(this);
            btnWithBankNext.setOnClickListener(this);
            txtResendOtp.setOnClickListener(this);

            accountType = getIntent().getStringExtra("checkTransferType");
            if (Objects.requireNonNull(accountType).equalsIgnoreCase("addBeneficiary")) {

                bMobileNo = getIntent().getStringExtra("mobileNo");
                bNickname = getIntent().getStringExtra("nickname");
                bAccName = getIntent().getStringExtra("accName");
                bAccNo = getIntent().getStringExtra("accNo");
                bIfscCode = getIntent().getStringExtra("ifscCode");
                bBenfMobNo = getIntent().getStringExtra("benfMobNo");
                bMmid = getIntent().getStringExtra("mmid");
                bWithinChecked = getIntent().getBooleanExtra("withinChecked", false);
                bBen_type = getIntent().getStringExtra("ben_type");
                upi_id = getIntent().getStringExtra("upi_id");

                String actionName = "GENERATE_OTP_FOR_MBANK_REG";
                toolbar.setText(R.string.txt_add_ben);
                generateOtp(actionName);


            }  else if (accountType.equalsIgnoreCase("ResetTPIn")) {
                etTpinId.setVisibility(View.GONE);
                strSecurityPin = getIntent().getStringExtra("strSecurityPin");
                strPIN = getIntent().getStringExtra("strPIN");
                strConfirmPin = getIntent().getStringExtra("strConfirmPin");
                String actionName = "GENERATE_OTP_FOR_MBANK_REG";
                toolbar.setText(R.string.txt_reset_tpin);
                generateOtp(actionName);

            } else if (accountType.equalsIgnoreCase("MPinActivation")) {
                etTpinId.setVisibility(View.GONE);
                strSecurityPin = getIntent().getStringExtra("strSecurityPin");
                strPIN = getIntent().getStringExtra("strPIN");
                strConfirmPin = getIntent().getStringExtra("strConfirmPin");
                mClientId = getIntent().getStringExtra("mClientId");
                String actionName = "GENERATE_OTP_FOR_MBANK_REG";
                toolbar.setText(R.string.txt_reset_tpin);
                generateOtp(actionName);

            } else if (accountType.equalsIgnoreCase("setTransactionLimit")) {
                limitPerDay = getIntent().getStringExtra("limitPerDay");
                trf_type = getIntent().getStringExtra("trf_type");
                limitType = getIntent().getStringExtra("limitType");
                accountNo = getIntent().getStringExtra("accountNo");
                String actionName = "GENERATE_OTP_FOR_MBANK_REG";
                toolbar.setText(R.string.setTransactionLimit);
                generateOtp(actionName);
            }else {
                toolbar.setText(R.string.lbl_fund_transfer);
                fundTransferModalDataList = (ArrayList<FundTransferSubModel>) getIntent().getSerializableExtra("fundTransferDataList");
                String actionName = "GENERATE_OTP_FOR_FT";
                generateOtp(actionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateOtp(String actionName) {
        try {
            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
                if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                    new GenerateOtpAsyncTask(OtpVerificationActivity.this, actionName).execute();
                } else {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                }
            } else {
                TrustMethods.displaySimErrorDialog(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.backButton_new:
                Intent intent = new Intent(OtpVerificationActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.btnWithBankNextId:
                try {
                    TrustMethods.hideSoftKeyboard(OtpVerificationActivity.this);

                    if (accountType.equalsIgnoreCase("ResetTPIn")) {
                        String otp = etFundTransferOtp.getText().toString().trim();
                        if (otp.isEmpty()) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_blank_otp), coordinatorLayout);
                        } else {
                            if (getIntent().getExtras() != null) {
                                if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
                                    if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                                        new ResetTPinAsyncTask(strSecurityPin, strPIN, strConfirmPin, otp).execute();
                                    } else {
                                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                    }

                                }
                            }
                        }

                    } else if (accountType.equalsIgnoreCase("MPinActivation")) {
                        String otp = etFundTransferOtp.getText().toString().trim();
                        if (otp.isEmpty()) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_blank_otp), coordinatorLayout);
                        } else {
                            if (getIntent().getExtras() != null) {
                                if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {

                                    if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                                        new PinActivationAsyncTask(this, strSecurityPin, strPIN, strConfirmPin, otp).execute();
                                    } else {
                                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                    }

                                }
                            }
                        }

                    } else {
                        if (Validation.validationFundTransferOtp(coordinatorLayout, OtpVerificationActivity.this, etFundTransferOtp, etFundTransferMpin)) {
                            String mpin = etFundTransferMpin.getText().toString().trim();
                            String otp = etFundTransferOtp.getText().toString().trim();

                            if (getIntent().getExtras() != null) {
                                if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {

                                     if (accountType.equals("withinBank")) {
                                        if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                                            new FundTransferWithinBankAsyncTask(OtpVerificationActivity.this, accountType,
                                                    fundTransferModalDataList, mpin, otp).execute();
                                        } else {
                                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                        }
                                    } else if (accountType.equals("SelfTransferToAccount")) {
                                        if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                                            new FundTransferToSelfAccountAsyncTask(OtpVerificationActivity.this, accountType, fundTransferModalDataList, mpin, otp).execute();
                                        } else {
                                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                        }
                                    } else if (accountType.equalsIgnoreCase("addBeneficiary")) {

                                        if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                                            addBeneficiary(bMobileNo, bNickname, bAccName, bAccNo, bIfscCode,
                                                    bBenfMobNo, bMmid, bWithinChecked, bBen_type, otp, mpin, upi_id);
                                        } else {
                                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                        }
                                    } else if (accountType.equalsIgnoreCase("setTransactionLimit")) {/**/

                                        if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                                            new TransactionLimitSubmit(limitPerDay, trf_type, limitType, accountNo, otp, mpin).execute();
                                        } else {
                                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                        }
                                    } else if (accountType.equalsIgnoreCase("blockDebitCard")) {

                                        if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                                            new DebitCardBlock(accountNo, debitcardNo, otp, mpin).execute();
                                        } else {
                                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                        }
                                    }else if (accountType.equalsIgnoreCase("Loanrepayment")) {

                                         if (NetworkUtil.getConnectivityStatus(OtpVerificationActivity.this)) {
                                             new LoanRepaymentAsyncTask(OtpVerificationActivity.this, accountType,
                                                     fundTransferModalDataList, mpin, otp).execute();
                                         } else {
                                             TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                         }
                                     }
                                } else {
                                    TrustMethods.displaySimErrorDialog(this);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.txtResendOtpId:
                try {
                    String actionName;
                    if (accountType.equalsIgnoreCase("addBeneficiary") || accountType.equalsIgnoreCase("ResetTPIn") || accountType.equalsIgnoreCase("MPinActivation")
                            || accountType.equalsIgnoreCase("setTransactionLimit") || accountType.equalsIgnoreCase("blockDebitCard")) {
                        actionName = "GENERATE_OTP_FOR_MBANK_REG";
                    } else {
                        actionName = "GENERATE_OTP_FOR_FT";
                    }
                    generateOtp(actionName);
                    clearFields();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void addBeneficiary(String mobileNo, String nickname, String accName, String accNo,

                                String ifscCode, String benfMobNo, String mmid, boolean withinChecked, String ben_type,
                                String otp, String mpin, String upi_id) {


        new AddBeneficiaryAsyncTask(OtpVerificationActivity.this
                , mobileNo
                , nickname
                , accName
                , accNo
                , ifscCode
                , benfMobNo
                , mmid
                , withinChecked
                , ben_type, otp, mpin, upi_id).execute();

    }

    private void clearFields() {
        etFundTransferMpin.setText("");
        etFundTransferOtp.setText("");
    }

    @SuppressLint("StaticFieldLeak")
    private class GenerateOtpAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String otp_sent_msg;
        String sms;
        ProgressDialog pDialog;
        String actionName;
        String purposeCode = "";
        String result;
        private String errorCode;
        private String customerId = AppConstants.getCLIENTID();

        public GenerateOtpAsyncTask(Context ctx, String actionName) {
            this.ctx = ctx;
            this.actionName = actionName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                if (accountType.equals("withinBank")) {
                    purposeCode = "MBANK_FT_OWN_BANK";
                } else if (accountType.equalsIgnoreCase("addBeneficiary")) {
                    purposeCode = "MBANK_BEN";
                } else if (accountType.equalsIgnoreCase("ResetTPIn")) {
                    purposeCode = "RESET_TPIN";
                } else if (accountType.equalsIgnoreCase("MPinActivation")) {
                    purposeCode = "FRGT_MPIN";
                    customerId = mClientId;
                } else if (accountType.equalsIgnoreCase("SelfTransferToAccount")) {
                    purposeCode = "MBANK_FT_SELF";
                } else if (accountType.equalsIgnoreCase("setTransactionLimit")) {
                    purposeCode = "MBANK_CHANGE_LIMIT";
                } else if (accountType.equalsIgnoreCase("blockDebitCard")) {
                    purposeCode = "BLOCK_DEBIT";
                }else if (accountType.equalsIgnoreCase("Loanrepayment")) {
                    purposeCode = "MBANK_FT_LOAN_REPAYMENT";
                }

                String url = TrustURL.GenerateOtpFundTransferUrl();
                String jsonString = "{\"for\":\"" + AppConstants.getUSERMOBILENUMBER() + "\",\"custid\":\"" + customerId + "\",\"purpose_code\":\"" + purposeCode + "\"}";

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
                    JSONObject responseJsonObject = jsonResponse.getJSONObject("response");
                    if (responseJsonObject.has("error")) {
                        error = responseJsonObject.getString("error");
                        return error;
                    }
                    JSONObject otpJsonObject = responseJsonObject.getJSONObject("otp");
                    if (otpJsonObject.has("error")) {
                        error = otpJsonObject.getString("error");
                        return error;
                    }
                    sms = otpJsonObject.has("sms") ? otpJsonObject.getString("sms") : "NA";
                    String mobile_number = otpJsonObject.has("mobile_number") ? otpJsonObject.getString("mobile_number") : "NA";
                    otp_sent_msg = otpJsonObject.has("otp_sent_msg") ? otpJsonObject.getString("otp_sent_msg") : "NA";


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
            return otp_sent_msg;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else if (!TextUtils.isEmpty(error) && TrustMethods.isSessionExpiredWithString(error)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        //TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                } else {
                    if (otp_sent_msg != null) {
                        TrustMethods.showSnackBarMessage(this.otp_sent_msg, coordinatorLayout);
                        Log.d("OTP", sms);
                        txtCounter.setVisibility(View.VISIBLE);
                        txtResendOtp.setTextColor(getResources().getColor(R.color.colorLightGray));
                        txtResendOtp.setEnabled(false);

                        new CountDownTimer(60000, 1000) {
                            @SuppressLint("SetTextI18n")
                            public void onTick(long millisUntilFinished) {
                                txtCounter.setText(getResources().getString(R.string.msg_resend_otp_enable)+ " " + millisUntilFinished / 1000 + " " + getResources().getString(R.string.seconds));
                            }

                            public void onFinish() {
                                txtResendOtp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtCounter.setVisibility(View.GONE);
                                txtResendOtp.setEnabled(true);
                            }
                        }.start();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class FundTransferToSelfAccountAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String finalResponse;
        ProgressDialog pDialog;
        String mAccountType;
        String tpin;
        String otp;
        GenerateStanRRNModel generateStanRRNModel;
        ArrayList<FundTransferSubModel> mFundTransferModalDataList;
        private TMessage msg;
        String response;
        String actionName = "FUND_TRANSFER_OWN_BANK";
        //        String action = "send_to_switch";
        String result;
        private String errorCode;

        public FundTransferToSelfAccountAsyncTask(Context ctx, String accountType, ArrayList<FundTransferSubModel> fundTransferModalDataList, String tpin, String otp) {
            this.ctx = ctx;
            this.mAccountType = accountType;
            this.tpin = tpin;
            this.otp = otp;
            this.mFundTransferModalDataList = fundTransferModalDataList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try {
                //   String jsonString = "{\"filter\":[\"rrn_cbs\"]}";
                String jsonString = "{\"filter\":[\"rrn_cbs\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(OtpVerificationActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());

                String url = TrustURL.getURLForFundTransferOwnAndNeft();

                if (generateStanRRNModel.getError() != null) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                } else {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tpin", tpin); //TODO
                    jsonObject.put("otp", otp);
                    jsonObject.put("rrn", generateStanRRNModel.getCbs_rrn());
                    jsonObject.put("ben_id", "");
                    jsonObject.put("ben_ac_no", mFundTransferModalDataList.get(0).getToAccNo());
                    jsonObject.put("rem_ac_no", mFundTransferModalDataList.get(0).getAccNo());
                    jsonObject.put("amount", mFundTransferModalDataList.get(0).getAmt());
                    jsonObject.put("remarks", mFundTransferModalDataList.get(0).getRemark());
                    jsonObject.put("is_self", "1");


                    if (!url.equals("")) {
                        result = HttpClientWrapper.postWithActionAuthToken(url, jsonObject.toString(), actionName, AppConstants.getAuth_token());
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
                        finalResponse = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";
                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
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
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                      //  TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                    clearFields();
                } else {
                    if (finalResponse != null) {
                        String message = getResources().getString(R.string.YourAccountNo) + mFundTransferModalDataList.get(0).getAccNo() + getResources().getString(R.string.debitedDallar) + mFundTransferModalDataList.get(0).getAmt() + " & "+getResources().getString(R.string.CreditedtoAccountNo) + mFundTransferModalDataList.get(0).getToAccNo() + ".("+getResources().getString(R.string.TxnRefNo) + generateStanRRNModel.getCbs_rrn() + ")";
                        Intent intent = new Intent(OtpVerificationActivity.this, TransactionSuccessActivity.class);
                        intent.putExtra("Description", message);
                        intent.putExtra("Title", getResources().getString(R.string.TransactionSuccessful));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class FundTransferWithinBankAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String finalResponse;
        ProgressDialog pDialog;
        String mAccountType;
        String mpin;
        String otp;
        GenerateStanRRNModel generateStanRRNModel;
        ArrayList<FundTransferSubModel> mFundTransferModalDataList;
        private TMessage msg;
        String response;
        String actionName = "FUND_TRANSFER_OWN_BANK";
        String result;
        private String errorCode;

        public   FundTransferWithinBankAsyncTask(Context ctx, String accountType,
                                               ArrayList<FundTransferSubModel> fundTransferModalDataList, String mpin, String otp) {
            this.ctx = ctx;
            this.mAccountType = accountType;
            this.mpin = mpin;
            this.otp = otp;
            this.mFundTransferModalDataList = fundTransferModalDataList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try {
                //String jsonString = "{\"filter\":[\"rrn_cbs\"]}";
                String jsonString = "{\"filter\":[\"rrn_cbs\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(OtpVerificationActivity.this,
                        jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());

                String url = TrustURL.getURLForFundTransferOwnAndNeft();

                if (generateStanRRNModel.getError() != null) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                } else {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tpin", mpin); //TODO
                    jsonObject.put("otp", otp);
                    jsonObject.put("rrn", generateStanRRNModel.getCbs_rrn());
                    jsonObject.put("ben_id", mFundTransferModalDataList.get(0).getBenId());
                    jsonObject.put("ben_ac_no", mFundTransferModalDataList.get(0).getBenAccNo());
                    jsonObject.put("rem_ac_no", mFundTransferModalDataList.get(0).getAccNo());
                    jsonObject.put("amount", mFundTransferModalDataList.get(0).getAmt());
                    jsonObject.put("remarks", mFundTransferModalDataList.get(0).getRemark());
                    jsonObject.put("is_self", "0");

                    if (!url.equals("")) {
                        result = HttpClientWrapper.postWithActionAuthToken(url, jsonObject.toString(),
                                actionName, AppConstants.getAuth_token());
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
                        finalResponse = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";
                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
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
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                    clearFields();
                } else {
                    if (finalResponse != null) {
                        if (mAccountType.equalsIgnoreCase("withinBank")){
                            Intent intent = new Intent(OtpVerificationActivity.this, TransactionSuccessActivity.class);
                            intent.putExtra("Description", getResources().getString(R.string.YourAccountNo) + mFundTransferModalDataList.get(0).getAccNo() + getResources().getString(R.string.debitedDallar) + mFundTransferModalDataList.get(0).getAmt() + " & "+getResources().getString(R.string.CreditedtoAccountNo)+ mFundTransferModalDataList.get(0).getBenAccNo() + ".("+getResources().getString(R.string.TxnRefNo) + generateStanRRNModel.getCbs_rrn() + ")");
                            intent.putExtra("Title", "Transaction Successful");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        try {
            switch (resultCode) {
                case -1:
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    clearFields();
                    finish();
                    method.activityCloseAnimation();
                    break;
                case 55:
                    clearFields();
                    break;
                case 0:
                    Intent intentLogin = new Intent(getApplicationContext(), LockActivity.class);
                    intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentLogin);
                    method.activityCloseAnimation();
                    clearFields();
                    break;

                case 1:
                    Intent fundTransferMenuIntent = new Intent(getApplicationContext(), MenuActivity.class);
                    fundTransferMenuIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(fundTransferMenuIntent);
                    method.activityOpenAnimation();
                    clearFields();
                    break;

                case 10:
                    Intent benintent = new Intent(getApplicationContext(), MenuActivity.class);
                    benintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(benintent);
                    method.activityOpenAnimation();
                    clearFields();
                    break;

                case 2:
                    Intent mmidIntent = new Intent(getApplicationContext(), MenuActivity.class);
                    mmidIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mmidIntent);
                    method.activityOpenAnimation();
                    clearFields();
                    break;

                case 3:
                    Intent intentGenerateTpin = new Intent(OtpVerificationActivity.this, TPinActivateActivity.class);
                    intentGenerateTpin.putExtra("mSecurityCode", mSecurityCode);
                    intentGenerateTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentGenerateTpin);
                    method.activityOpenAnimation();
                    finish();
                    clearFields();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDialogCancel(int resultCode) {

        if (resultCode == 5) {
            Intent intent1 = new Intent(getApplicationContext(), MenuActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            finish();
            method.activityCloseAnimation();
            clearFields();
        }
    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i=new Intent(OtpVerificationActivity.this,MenuActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


    @SuppressLint("StaticFieldLeak")
    private class AddBeneficiaryAsyncTask extends AsyncTask<Void, Void, String> {
        private String error = "";
        private Context ctx;
        private String response, benActivateTimeInMins;
        private ProgressDialog pDialog;
        private String mMobileNo;
        private String mNickname;
        private String mAccName;
        private String mAccNo;
        private String mIfscCode;
        private String mMmid;
        private String mBenfMobNo;
        private String ben_type;
        private String actionName = "ADD_BENEFICIARY";
        private String result;
        private String otp;
        private String mpin;
        private String upi_id;
        private Boolean mWithinChecked;
        private String errorCode;

        public AddBeneficiaryAsyncTask(Context ctx, String mobileNo, String nickname, String accName, String accNo, String ifscCode, String benfMobNo, String mmid,
                                       boolean withinChecked, String ben_type, String otp, String mpin, String upi_id) {
            this.ctx = ctx;
            this.mMobileNo = mobileNo;
            this.mNickname = nickname;
            this.mAccName = accName;
            this.mAccNo = accNo;
            this.mIfscCode = ifscCode;
            this.mBenfMobNo = benfMobNo;
            this.mMmid = mmid;
            this.mWithinChecked = withinChecked;
            this.ben_type = ben_type;
            this.otp = otp;
            this.mpin = mpin;
            this.upi_id = upi_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();

                String jsonString = "{\"mobile_number\":\"" + mMobileNo + "\",\"ben_nickname\":\"" + mNickname + "\"," + "\"ben_ac_name\":\"" + mAccName + "\"," + "\"ben_type\":\"" + ben_type + "\",\"ben_ac_no\":\"" + mAccNo + "\"," + "\"ben_mobile_number\":\"" + mBenfMobNo + "\",\"otp\":\"" + otp + "\",\"tpin\":\"" + mpin + "\"}";

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

                    JSONArray responseArray = responseObject.getJSONArray("Table1");
                    if (responseArray.length() > 0) {
                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject dataObject = responseArray.getJSONObject(i);
                            response = dataObject.has("ben_id") ? dataObject.getString("ben_id") : "NA";
                            benActivateTimeInMins = dataObject.has("ben_activate_after_mins") ? dataObject.getString("ben_activate_after_mins") : "NA";
                        }
                    }
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
                }
                if (response != null) {
                    String message = getResources().getString(R.string.Beneficiaryissuccessfullyaddedas) + mNickname + ". " + getResources().getString(R.string.Itwillbeactivatedafter) + benActivateTimeInMins + getResources().getString(R.string.Minutes);
                    AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, "", message, getResources().getString(R.string.btn_ok), 10, false, alertDialogOkListener);
                }
                if (!this.error.equals("")) {
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.SessionExpired), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ResetTPinAsyncTask extends AsyncTask<Void, Void, String> {
        private String error = "";
        private String response;
        private ProgressDialog pDialog;
        private String result;
        private String otp;
        private String tpin;
        private String strConfirmPin;
        private String errorCode;
        private String action = "RESET_TPIN";
        private String purposeCode = "RESET_TPIN";
        private String strSecurityPin;


        public ResetTPinAsyncTask(String strSecurityPin, String strPIN, String strConfirmPin, String otp) {
            this.strSecurityPin = strSecurityPin;
            this.otp = otp;
            this.tpin = strPIN;
            this.strConfirmPin = strConfirmPin;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();
                String jsonString = "{\"pin_type\":\"" + "tpin" + "\",\"tpin\":\"" + tpin + "\",\"confirm_tpin\":\"" + strConfirmPin + "\", \"mobile_number\":\"" + AppConstants.getUSERMOBILENUMBER() + "\", \"security_code\":\"" + strSecurityPin + "\", \"otp\":\"" + otp + "\", \"custid\":\"" + AppConstants.getCLIENTID() + "\", \"otp_purpose_code\":\"" + purposeCode + "\"}";

                TrustMethods.LogMessage("", "json string for change pass : " + jsonString);


                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, action, AppConstants.getAuth_token());

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
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equalsIgnoreCase("1")) {
                    response = getResources().getString(R.string.ResetTPinSuccessfully);
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
                }
                if (response != null) {
                    AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, "", response, getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
                }
                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.SessionExpired), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }
                    if (TrustMethods.isSessionExpiredWithString(error)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.SessionExpired), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        //TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class PinActivationAsyncTask extends AsyncTask<Void, Void, String> {
        private String error = "";
        private Context ctx;
        private String response;
        private ProgressDialog pDialog;
        private String mCnfmMpin;
        private String mMpin;
        private String mMobileNo, otp;
        private String result;
        private String purposeCode = "FRGT_MPIN";

        public PinActivationAsyncTask(Context ctx, String securityCode, String mpin, String cnfmMpin, String otp) {
            this.ctx = ctx;
            mSecurityCode = securityCode;
            this.mMpin = mpin;
            this.otp = otp;
            this.mCnfmMpin = cnfmMpin;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GeneratePinUrl();

                String jsonString = "{\"pin_type\":\"" + "mpin" + "\",\"pin\":\"" + mMpin + "\",\"pin_confirmation\":\"" + mCnfmMpin + "\", \"mobile_number\":\"" + AppConstants.getUSERMOBILENUMBER() + "\", \"security_code\":\"" + mSecurityCode + "\", \"otp\":\"" + otp + "\", \"custid\":\"" + mClientId + "\", \"otp_purpose_code\":\"" + purposeCode + "\"}";

                TrustMethods.LogMessage("", "json string for change pass : " + jsonString);


                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithoutHeader(url, jsonString);
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
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {
                    response = getResources().getString(R.string.PinSuccessfullyActivated);
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
                    if (!this.error.equals("")) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                } else {
                    if (response != null) {

                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, "", response, getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class DebitCardBlock extends AsyncTask<Void, Integer, String> {

        private String error = "";
        private String response;
        private String  debitCardNo, accNo, otp, tpin;
        private String actionName = "BLOCK_DEBIT_CARD";
        private String result;
        private ProgressDialog pDialog;
        private String errorCode;

        public DebitCardBlock(String accountNo, String debitCardNo, String otp, String tpin) {

            this.debitCardNo = debitCardNo;
            this.accNo = accountNo;
            this.otp = otp;
            this.tpin = tpin;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMax(45);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();
                String jsonString = "{\"acc_no\":\"" + accNo + "\",\"card_no\":\"" + debitCardNo + "\",\"otp\":\"" + otp + "\",\"tpin\":\"" + tpin + "\",\"req_mode\":\"" + debitCardMode + "\",\"reason\":\"" + otherReasonDebitCard + "\"}";

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
                    JSONArray accountJsonArray = jsonResponse.getJSONObject("response").getJSONArray("response_msg");
                    JSONObject jsonObject = accountJsonArray.getJSONObject(0);
                    response = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
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
                pDialog.dismiss();
                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }
                    if (!TextUtils.isEmpty(error) && TrustMethods.isSessionExpiredWithString(error)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                } else {
                    if (response != null) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, "", response, getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class TransactionLimitSubmit extends AsyncTask<Void, Integer, String> {
        private String error = "";
        private String response;
        private String transLimit, trfType, limitType, accNo, otp, tpin;
        private String actionName = "ACCOUNT_LIMIT_SET";
        private String result;
        private ProgressDialog pDialog;
        private String errorCode;

        public TransactionLimitSubmit(String transLimit, String trfType, String limitType, String accNo, String otp, String tpin) {

            this.transLimit = transLimit;
            this.trfType = trfType;
            this.limitType = limitType;
            this.accNo = accNo;
            this.otp = otp;
            this.tpin = tpin;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMax(45);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();
                String jsonString = "{\"ac_no\":\"" + accNo + "\",\"trf_type\":\"" + trfType + "\",\"limit_type\":\"" + limitType + "\",\"limit_value\":\"" + transLimit + "\",\"otp\":\"" + otp + "\",\"tpin\":\"" + tpin + "\"}";

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

                    JSONObject responseJsonObject = jsonResponse.has("response") ? jsonResponse.getJSONObject("response") : null;
                    if (responseJsonObject != null) {
                        JSONArray dataArray = responseJsonObject.has("Table") ? responseJsonObject.getJSONArray("Table") : null;
                        if (dataArray != null) {
                            response = dataArray.getJSONObject(0).getString("limit_msg");
                        }
                    }


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
                pDialog.dismiss();
                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }
                    if (!TextUtils.isEmpty(error) && TrustMethods.isSessionExpiredWithString(error)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        //TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                } else {
                    if (response != null) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, "", response, getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(OtpVerificationActivity.this);
    }


    public void alertDialogOk(Context context, String title, String message,
                              String button) {

        try {
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setCancelable(true);
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setPositiveButton(button,
                    (dialog, arg1) -> {
                    });

            final AlertDialog alertDialog = alert.create();
            Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.dialogTheme;
            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                alertDialog.dismiss();

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LoanRepaymentAsyncTask  extends AsyncTask<Void, Void, String>{
        private ProgressDialog pDialog;
        String error = "";
        Context ctx;
        String finalResponse;
        String mAccountType;
        String mpin;
        String otp;
        ArrayList<FundTransferSubModel> mFundTransferModalDataList;
        String response;
        String actionName = "FUND_TRANSFER_LOAN_REPAYMENT";
        String result;
        private String errorCode;
        public LoanRepaymentAsyncTask(Context ctx, String accountType, ArrayList<FundTransferSubModel> fundTransferModalDataList, String mpin, String otp) {
            this.ctx = ctx;
            this.mAccountType = accountType;
            this.mpin = mpin;
            this.otp = otp;
            this.mFundTransferModalDataList = fundTransferModalDataList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpVerificationActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String url = TrustURL.getURLForFundTransferOwnAndNeft();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String currentDate = sdf.format(new Date());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tpin", mpin); //TODO
                    jsonObject.put("otp", otp);
                    jsonObject.put("to_ac_no", mFundTransferModalDataList.get(0).getToAccNo());
                    jsonObject.put("from_ac_no", mFundTransferModalDataList.get(0).getAccNo());
                    jsonObject.put("amount", mFundTransferModalDataList.get(0).getAmt());
                    jsonObject.put("is_loan", "0");
                    jsonObject.put("working_date",currentDate);//"04/04/2023"
                    if (!url.equals("")) {
                        result = HttpClientWrapper.postWithActionAuthToken(url, jsonObject.toString(),
                                actionName, AppConstants.getAuth_token());
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
                        finalResponse = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                pDialog.dismiss();
                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }
                    if (!TextUtils.isEmpty(error) && TrustMethods.isSessionExpiredWithString(error)) {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(OtpVerificationActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                } else {
                    if (response == null) {
                        String message = getResources().getString(R.string.YourCreditAmountTransfer)+" $" + mFundTransferModalDataList.get(0).getAmt() +" "+ getResources().getString(R.string.successfullydone);
                        Intent intent = new Intent(OtpVerificationActivity.this, TransactionSuccessActivity.class);
                        intent.putExtra("Description", message);
                        intent.putExtra("Title", getResources().getString(R.string.TransactionSuccessful));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}