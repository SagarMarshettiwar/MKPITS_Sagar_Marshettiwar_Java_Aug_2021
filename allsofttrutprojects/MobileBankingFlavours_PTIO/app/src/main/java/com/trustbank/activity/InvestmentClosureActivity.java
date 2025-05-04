package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.R;
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

import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvestmentClosureActivity extends AppCompatActivity implements AlertDialogOkListener, View.OnClickListener{

    private CoordinatorLayout coordinatorLayout;
    private ImageView backButton_new;
    TextView toolbar, product_name, cert_no, maturity_amt, invest_acc;
    EditText et_remarks;
    TrustMethods methods;
    Spinner spinnerFrmActId;
    Button btn_proceed;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    CardView cv_invest_details;
    String accNo;
    AlertDialogOkListener alertDialogOkListener = this;

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
                        TrustMethods.naviagteToSplashScreen(InvestmentClosureActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(InvestmentClosureActivity.this, false);
        setContentView(R.layout.activity_investment_closure);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(getResources().getString(R.string.InvestmentClosure));

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
        methods = new TrustMethods(InvestmentClosureActivity.this);
        backButton_new = findViewById(R.id.backButton_new);
        spinnerFrmActId = findViewById(R.id.spinnerFrmActId);
        cv_invest_details= findViewById(R.id.cv_invest_details);
        product_name= findViewById(R.id.product_name);
        cert_no= findViewById(R.id.cert_no);
        maturity_amt= findViewById(R.id.maturity_amt);
        invest_acc= findViewById(R.id.invest_acc);
        coordinatorLayout= findViewById(R.id.coordinatorLayout);
        btn_proceed= findViewById(R.id.btn_proceed);
        et_remarks= findViewById(R.id.et_remarks);
        backButton_new.setOnClickListener(this);
        btn_proceed.setOnClickListener(this);

        setAccountNoSpinner();
    }

    private void setAccountNoSpinner() {
        try {
            accountsArrayList = methods.getArrayList(InvestmentClosureActivity.this, "AccountListPref");
            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                List<String> accountList = new ArrayList<>();

                accountList.add(0, getResources().getString(R.string.SelectAccountNumber));
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (TrustMethods.investmentACC(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(InvestmentClosureActivity.this, android.R.layout.simple_spinner_item, accountList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFrmActId.setAdapter(adapter);
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

                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(InvestmentClosureActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(InvestmentClosureActivity.this)) {
                                new GetInvestmentClosureAsyncTask(InvestmentClosureActivity.this, accNo).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(InvestmentClosureActivity.this);
                        }
                    } else {
                        cv_invest_details.setVisibility(View.GONE);
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
                Intent intent = new Intent(InvestmentClosureActivity.this, InvestmentMenus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_proceed:
                String remarks= et_remarks.getText().toString();
                if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(InvestmentClosureActivity.this)) {
                    if (NetworkUtil.getConnectivityStatus(InvestmentClosureActivity.this)) {
                        new SendInvestmentClosureAsyncTask(InvestmentClosureActivity.this, accNo, remarks).execute();
                    } else {
                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                    }
                } else {
                    TrustMethods.displaySimErrorDialog(InvestmentClosureActivity.this);
                }
                break;
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        switch (resultCode) {
            case 0:
                Intent intent = new Intent(getApplicationContext(), InvestmentMenus.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                methods.activityCloseAnimation();
                break;
            default:
                break;
        }
    }

    private class GetInvestmentClosureAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "CLOSE_INVESTMENT";
        String result, accno, accountId, certificateNumber, maturityAmount, schemeName, accountNumberForDisplay;
        private String errorCode;

        public GetInvestmentClosureAsyncTask(Context ctx, String accno) {
            this.ctx = ctx;
            this.accno = accno;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InvestmentClosureActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getInvestmentClosure(accno,"0");

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
                    JSONArray data = jsonResponse.getJSONObject("response").getJSONArray("data");
                    JSONObject jsonObject = data.getJSONObject(0);
                    accountId= jsonObject.has("AccountId") ? jsonObject.getString("AccountId") : "";
                    maturityAmount= jsonObject.has("Maturity Amount") ? jsonObject.getString("Maturity Amount") : "";
                    schemeName= jsonObject.has("Scheme Name") ? jsonObject.getString("Scheme Name") : "";
                    certificateNumber= jsonObject.has("CertificateNumber") ? jsonObject.getString("CertificateNumber") : "";
                    accountNumberForDisplay= jsonObject.has("AccountNumberForDisplay") ? jsonObject.getString("AccountNumberForDisplay") : "";
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
                if(error.equals("")) {
                    cv_invest_details.setVisibility(View.VISIBLE);
                    product_name.setText(schemeName);
                    cert_no.setText(certificateNumber);
                    maturity_amt.setText(" " + maturityAmount + " ");
                    invest_acc.setText(accountNumberForDisplay);
                }else{
                    TrustMethods.message(ctx, error);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SendInvestmentClosureAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "CLOSE_INVESTMENT";
        String result, accno, remarks;
        private String errorCode;

        public SendInvestmentClosureAsyncTask(Context ctx, String accno, String remarks) {
            this.ctx = ctx;
            this.accno = accno;
            this.remarks = remarks;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InvestmentClosureActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getInvestmentClosure(accno,"1", remarks, AppConstants.getCLIENTID());

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
                if(!error.equals("")){
                    AlertDialogMethod.alertDialogOk(InvestmentClosureActivity.this,
                            getResources().getString(R.string.ErrorMessage), error, getResources().getString(R.string.btn_ok),
                            0, false, alertDialogOkListener);
                }else{
                    Intent i=new Intent(InvestmentClosureActivity.this,SuccessSplashActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("Title",getResources().getString(R.string.ThankYou)+accno);
                    i.putExtra("Activity", "OpenInvestmentAccountActivity");
                    startActivity(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}