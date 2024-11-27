package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.trustbank.Model.AccountDetailsModel;
import com.trustbank.Model.AccountOverviewModel;
import com.trustbank.Model.FundTransferSubModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.R;
import com.trustbank.adapter.AccountOverViewAdapter;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditRepaymentActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogOkListener {
    private CoordinatorLayout coordinatorLayout;
    private ImageView backButton_new;
    private TextView toolbar, product_name, credit_amt, due_amt;
    EditText inst_amt;
    TrustMethods methods;
    Spinner spinnerFrmActId, spn_saving_acc;
    Button btn_proceed;
    AlertDialogOkListener alertDialogOkListener=this;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    CardView cardRepaymentCardView;
    String accNo, saveAccNo;
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
                        TrustMethods.naviagteToSplashScreen(CreditRepaymentActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(CreditRepaymentActivity.this, false);
        setContentView(R.layout.activity_credit_repayment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(getResources().getString(R.string.CreditRepayment));

        init();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        methods = new TrustMethods(CreditRepaymentActivity.this);
        backButton_new = findViewById(R.id.backButton_new);
        spinnerFrmActId = findViewById(R.id.spinnerFrmActId);
        product_name = findViewById(R.id.product_name);
        credit_amt = findViewById(R.id.credit_amt);
        inst_amt = findViewById(R.id.inst_amt);
        due_amt = findViewById(R.id.due_amt);
        btn_proceed = findViewById(R.id.btn_proceed);
        spn_saving_acc = findViewById(R.id.spn_saving_acc);
        cardRepaymentCardView = findViewById(R.id.cardRepaymentCardView);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        backButton_new.setOnClickListener(this);
        btn_proceed.setOnClickListener(this);

        setAccountNoSpinner();
    }
    private void setAccountNoSpinner() {
        try {
            accountsArrayList = methods.getArrayList(CreditRepaymentActivity.this, "AccountListPref");
            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                List<String> accountList = new ArrayList<>();
                List<String> saveAccList = new ArrayList<>();

                accountList.add(0, getResources().getString(R.string.SelectAccountNumber));
                saveAccList.add(0, getResources().getString(R.string.SelectAccountNumber));
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (TrustMethods.creditACC(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo);
                    }

                    if (TrustMethods.savingACC(getUserProfileModal.getActType())) {
                        String saveAcc = getUserProfileModal.getAccNo();
                        saveAccList.add(saveAcc);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CreditRepaymentActivity.this, android.R.layout.simple_spinner_item, accountList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFrmActId.setAdapter(adapter);

                ArrayAdapter<String> saveAdapter = new ArrayAdapter<>(CreditRepaymentActivity.this, android.R.layout.simple_spinner_item, saveAccList);
                saveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_saving_acc.setAdapter(saveAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinnerFrmActId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        String selectedAccNo = (String) adapterView.getItemAtPosition(position);
                        accNo = "";
                        if (selectedAccNo.contains("-")) {
                            String[] accounts = selectedAccNo.split("-");
                            accNo = accounts[0];
                        } else {
                            accNo = selectedAccNo;
                        }

                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(CreditRepaymentActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(CreditRepaymentActivity.this)) {
                                new GetCreditRepaymentAsyncTask(CreditRepaymentActivity.this, accNo).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(CreditRepaymentActivity.this);
                        }
                    } else {
                        cardRepaymentCardView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spn_saving_acc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        String selectedAccNo = (String) adapterView.getItemAtPosition(position);
                        saveAccNo = "";
                        if (selectedAccNo.contains("-")) {
                            String[] accounts = selectedAccNo.split("-");
                            saveAccNo = accounts[0];
                        } else {
                            saveAccNo = selectedAccNo;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backButton_new:
                Intent intent = new Intent(CreditRepaymentActivity.this, TransactionMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_proceed:
                String instAmt = inst_amt.getText().toString();
                if(TextUtils.isEmpty(instAmt)) {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.EnterInstallmentAmount), coordinatorLayout);
                }else {
                    List<FundTransferSubModel> fundTransferSubModalList = new ArrayList<>();
                    FundTransferSubModel fundTransferSubModel = new FundTransferSubModel();
                    fundTransferSubModel.setAccNo(saveAccNo.trim());
                    fundTransferSubModel.setToAccNo(accNo.trim());
                    fundTransferSubModel.setAmt(instAmt);
                    fundTransferSubModalList.add(fundTransferSubModel);

                    Intent i = new Intent(CreditRepaymentActivity.this, OtpVerificationActivity.class);
                    i.putExtra("checkTransferType", "Loanrepayment");
                    i.putExtra("fundTransferDataList", (Serializable) fundTransferSubModalList);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    methods.activityOpenAnimation();
               /* if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(CreditRepaymentActivity.this)) {
                    if (NetworkUtil.getConnectivityStatus(CreditRepaymentActivity.this)) {
                        new AccountAddValidationAsyncTask(CreditRepaymentActivity.this, saveAccNo,instAmt,fundTransferSubModalList).execute();
                    } else {
                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                    }
                } else {
                    TrustMethods.displaySimErrorDialog(CreditRepaymentActivity.this);
                }*/
                }
                break;
        }
    }

    @Override
    public void onDialogOk(int resultCode) {

    }

 /*   private class AccountAddValidationAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String finalResponse;
        ProgressDialog pDialog;
        String response;
        String actionName = "VALIDATE_FT";
        String result;
        private String errorCode;
        private String accountNo;
        private String otpActionName;
        private String amount;
        private List<FundTransferSubModel> fundTransferSubModalList;

        public AccountAddValidationAsyncTask(Context ctx, String accountNo, String amount,
                                                List<FundTransferSubModel> fundTransferSubModalList) {
            this.ctx = ctx;
            this.otpActionName = actionName;
            this.accountNo = accountNo;
            this.amount = amount;
            this.fundTransferSubModalList = fundTransferSubModalList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreditRepaymentActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {

            try {
                String url = TrustURL.getURLForFundTransferOwnAndNeft();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rem_ac_no", fundTransferSubModalList.get(0).getToAccNo());
                jsonObject.put("trf_type", 1);
                jsonObject.put("ben_id", "");
                jsonObject.put("ben_ifsc", "");
                jsonObject.put("ben_ac_no", fundTransferSubModalList.get(0).getAccNo());
                jsonObject.put("amount", fundTransferSubModalList.get(0).getAmt());
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
                        AlertDialogMethod.alertDialogOk(CreditRepaymentActivity.this, getResources().getString(R.string.error_session_expire),
                                "",
                                getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(CreditRepaymentActivity.this, " ",
                                this.error,
                                getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                } else {
                    //response..
                    Intent i = new Intent(CreditRepaymentActivity.this, OtpVerificationActivity.class);
                    i.putExtra("checkTransferType", "Loanrepayment");
                    i.putExtra("fundTransferDataList", (Serializable) fundTransferSubModalList);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    methods.activityOpenAnimation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    private class GetCreditRepaymentAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "GET_LOAN_REPAYMENT_DUE_DATE";
        String result, accNo, CapitalDue, InsuranceDue, InterestDue, ProductName, LoanAmount;
        private String errorCode;
        JSONArray data;

        public GetCreditRepaymentAsyncTask(Context ctx, String accNo) {
            this.ctx = ctx;
            this.accNo = accNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreditRepaymentActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String currentDate = sdf.format(new Date());

                String url = TrustURL.getLoanRepaymentDueDate(accNo, currentDate);

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
                    data = jsonResponse.getJSONObject("response").getJSONArray("data");
                    if (data.length() == 0) {
                        error = AppConstants.NO_RECORDS_FOUND;
                        return error;
                    }
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject JsonObject = data.getJSONObject(i);
                        CapitalDue = JsonObject.has("CapitalDue") ? JsonObject.getString("CapitalDue") : "NA";
                        InsuranceDue = JsonObject.has("InsuranceDue") ? JsonObject.getString("InsuranceDue") : "NA";
                        InterestDue = JsonObject.has("InterestDue") ? JsonObject.getString("InterestDue") : "NA";
                        ProductName = JsonObject.has("ProductName") ? JsonObject.getString("ProductName") : "NA";
                        LoanAmount = JsonObject.has("LoanAmount") ? JsonObject.getString("LoanAmount") : "NA";
                    }
                    } else {
                    errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                    error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
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
                cardRepaymentCardView.setVisibility(View.VISIBLE);
                Double dueAmt = Double.parseDouble(CapitalDue) +Double.parseDouble(InsuranceDue)+Double.parseDouble(InterestDue);
                product_name.setText(ProductName);
                credit_amt.setText(LoanAmount);
                due_amt.setText(dueAmt.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}