package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OpenLoanAccountActivity extends AppCompatActivity implements View.OnClickListener{
    CoordinatorLayout coordinatorLayout;
    String productid, freqId, amountInWords;
    List<String> savingschemeslist, installemntFreqList;
    Spinner product_spi, spi_install_freq;
    HashMap<String,String> savingschemesmap, installemntFreqMap;
    TrustMethods methods;
    Button btn_submit;
    ImageView backButton_new;
    TextView toolbar, txt_amountinwords, txt_application_date, txt_expiry_date;
    EditText et_credit_amt,et_tenor_period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(OpenLoanAccountActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(OpenLoanAccountActivity.this, false);
        setContentView(R.layout.activity_open_credit_request);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.OpenCreditAccount);

        inIT();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }
    private void inIT() {
        backButton_new = findViewById(R.id.backButton_new);
        methods=new TrustMethods(OpenLoanAccountActivity.this);
        product_spi=findViewById(R.id.product_spi);
        btn_submit=findViewById(R.id.btn_submit);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        et_credit_amt = findViewById(R.id.et_credit_amt);
        txt_amountinwords = findViewById(R.id.txt_amountinwords);
        spi_install_freq = findViewById(R.id.spi_install_freq);
        et_tenor_period = findViewById(R.id.et_tenor_period);
        txt_application_date = findViewById(R.id.txt_application_date);
        txt_expiry_date = findViewById(R.id.txt_expiry_date);

        backButton_new.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        txt_application_date.setText(formatter.format(date));

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(OpenLoanAccountActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(OpenLoanAccountActivity.this)) {
                new AsyncTaskGetSavinglookups(OpenLoanAccountActivity.this).execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(OpenLoanAccountActivity.this);
        }

        et_credit_amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                et_credit_amt.removeTextChangedListener(this);
                try{
                    if(!et_credit_amt.getText().toString().equals("")) {
                        amountInWords = methods.amountInWords(et_credit_amt.getText().toString());
                        txt_amountinwords.setText(amountInWords);
                    }else{
                        txt_amountinwords.setText(getResources().getString(R.string.AmountinWords));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                et_credit_amt.addTextChangedListener(this);
            }
        });

        et_tenor_period.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                et_tenor_period.removeTextChangedListener(this);
                try{
                    if(!et_tenor_period.getText().toString().equals("")){
                        String dt = txt_application_date.getText().toString();  // Start date
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        c.setTime(sdf.parse(dt));
                        c.add(Calendar.MONTH, Integer.parseInt(editable.toString()));  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                        txt_expiry_date.setText(sdf1.format(c.getTime()));
                    }else{
                        txt_expiry_date.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                et_tenor_period.addTextChangedListener(this);
            }
        });
    }

    public void clear(){
        et_credit_amt.setText("");
        txt_amountinwords.setText(R.string.AmountinWords);
        et_tenor_period.setText("");
        txt_expiry_date.setHint(getResources().getString(R.string.dd_MMM_yyyy));
        spi_install_freq.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton_new:
                for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_open_loan_account")) {
                        Intent intent = new Intent(OpenLoanAccountActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
                /*if(AppConstants.account_group){
                    Intent intent = new Intent(AccountOverviewActivity.this, AccountsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.upi_group) {
                    Intent intent = new Intent(AccountOverviewActivity.this, UPIActivityMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.service_group) {
                    Intent intent = new Intent(AccountOverviewActivity.this, ServiceRequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.cards_group) {
                    Intent intent = new Intent(AccountOverviewActivity.this, Cards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.locate_us_group) {
                    Intent intent = new Intent(AccountOverviewActivity.this, LocateUs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }else if (AppConstants.need_help_group) {
                    Intent intent = new Intent(AccountOverviewActivity.this, NeedHelp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }*/
                Intent intent = new Intent(OpenLoanAccountActivity.this, BorrowMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_submit :
                submit();
                break;
        }
    }

    private void submit() {
        if (product_spi.getSelectedItem().equals(getResources().getString(R.string.SelectProductType))) {
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.PleaseSelectProducttype), Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(et_credit_amt.getText().toString())) {
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.ErrCreditAmount), Toast.LENGTH_SHORT).show();
        }else if (spi_install_freq.getSelectedItem().equals(getResources().getString(R.string.InstallmentFrequency))) {
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.ErrInstallmentFrequency), Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(et_tenor_period.getText().toString())) {
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.ErrTenorPeriod), Toast.LENGTH_SHORT).show();
        }else{
            String creditAmt = et_credit_amt.getText().toString();
            String applicationDate = txt_application_date.getText().toString();
            String expiryDate = txt_expiry_date.getText().toString();
            String tenorPeriod = et_tenor_period.getText().toString();

            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(OpenLoanAccountActivity.this)) {
                if (NetworkUtil.getConnectivityStatus(OpenLoanAccountActivity.this)) {
                    new AsyncTaskSubmit(OpenLoanAccountActivity.this, productid, creditAmt, tenorPeriod, freqId, applicationDate, expiryDate).execute();
                } else {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                }
            } else {
                TrustMethods.displaySimErrorDialog(OpenLoanAccountActivity.this);
            }
        }
    }

    private class AsyncTaskGetSavinglookups extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        TrustMethods methods;
        JSONArray data;
        JSONArray savingSchemes, installmentFreq;
        String result;
        String actionName ="GET_SAVING_LOOKUPS";

        public AsyncTaskGetSavinglookups(Context ctx) {
            this.error = "";
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetCatalogue();
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
                    savingSchemes = jsonResponse.getJSONObject("response").getJSONArray("Table4");
                    if (savingSchemes.length() > 0) {
                        savingschemeslist = new ArrayList<>();
                        savingschemesmap = new HashMap<>();
                        savingschemeslist.add(0, getResources().getString(R.string.SelectProductType));
                        if (savingSchemes != null && savingSchemes.length() > 0) {
                            for (int i = 0; i < savingSchemes.length(); i++) {
                                JSONObject JsonObject = savingSchemes.getJSONObject(i);
                                //IdentificationType
                                String schemeidID = JsonObject.has("schemeid") ? JsonObject.getString("schemeid") : "NA";
                                String schemename = JsonObject.has("schemename") ? JsonObject.getString("schemename") : "NA";
                                savingschemeslist.add(schemename);
                                savingschemesmap.put(schemename, schemeidID);
                            }
                        }

                        installmentFreq = jsonResponse.getJSONObject("response").getJSONArray("Table9");
                        if (installmentFreq.length() > 0) {
                            installemntFreqList = new ArrayList<>();
                            installemntFreqMap = new HashMap<>();
                            installemntFreqList.add(0, getResources().getString(R.string.InstallmentFrequency));
                            if (installmentFreq != null && installmentFreq.length() > 0) {
                                for (int i = 0; i < installmentFreq.length(); i++) {
                                    JSONObject JsonObject = installmentFreq.getJSONObject(i);
                                    //IdentificationType
                                    String freqid = JsonObject.has("valueid") ? JsonObject.getString("valueid") : "NA";
                                    String freqvalue = JsonObject.has("typevalue") ? JsonObject.getString("typevalue") : "NA";
                                    installemntFreqList.add(freqvalue);
                                    installemntFreqMap.put(freqvalue, freqid);
                                }
                            }
                        } else {
                            String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                            error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                        }
                    }
                }
            }catch(JSONException e){
                e.printStackTrace();
                error = e.getMessage();
                return error;
            } catch(Exception ex){
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (this.error != "") {
                methods.message(this.ctx, error);
                return;
            }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, installemntFreqList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spi_install_freq.setAdapter(adapter1);
            spi_install_freq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){
                        String selectedfreq = (String) parent.getItemAtPosition(position);
                        freqId=installemntFreqMap.get(selectedfreq);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, savingschemeslist);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            product_spi.setAdapter(adapter2);
            product_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){
                        String selectedproduct = (String) parent.getItemAtPosition(position);
                        productid=savingschemesmap.get(selectedproduct);
                        clear();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private class AsyncTaskSubmit extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        String remarks, result, productid, creditAmt, freqId, applicationDate, expiryDate, tenorPeriod;
        String actionName = "LOAN_REQUEST";

        public AsyncTaskSubmit(Context ctx, String productId, String creditAmt, String tenorPeriod,  String freqId, String applicationDate, String expiryDate) {
            this.error = "";
            this.ctx = ctx;
            this.productid = productId;
            this.creditAmt = creditAmt;
            this.tenorPeriod = tenorPeriod;
            this.freqId =freqId;
            this.applicationDate = applicationDate;
            this.expiryDate = expiryDate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OpenLoanAccountActivity.this);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getURLForFundTransferOwnAndNeft();
                Log.e("URL", url);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("clientid", AppConstants.getCLIENTID());
                jsonObject.put("productid", productid);
                jsonObject.put("loanamount", creditAmt);
                jsonObject.put("periodInmonths", tenorPeriod);
                jsonObject.put("instfrequency", freqId);
                jsonObject.put("applicationdate", TrustMethods.formatDate(applicationDate, "dd/MM/yyyy", "yyyy-MM-dd"));
                jsonObject.put("expirydate", TrustMethods.formatDate(expiryDate, "dd/MM/yyyy", "yyyy-MM-dd"));

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
                Log.e("Response Code", responseCode);

                if (responseCode.equals("1")) {

                    Log.e("RESULT", result);
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

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (this.error != "") {
                methods.message(this.ctx, error);
                return;
            }
            Intent i=new Intent(OpenLoanAccountActivity.this,SuccessSplashActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("Title","ThankYou for Submitting Details for Loan Account Opening");
            i.putExtra("Activity", "OpenLoanAccountActivity");
            startActivity(i);
        }
    }
}