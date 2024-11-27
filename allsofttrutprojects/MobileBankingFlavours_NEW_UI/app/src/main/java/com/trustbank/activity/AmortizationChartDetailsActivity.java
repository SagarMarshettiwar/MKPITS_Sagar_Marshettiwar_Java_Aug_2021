package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TableLayout;

import com.trustbank.Model.AmortizationChartDetailsListInfo;
import com.trustbank.Model.FDModel;
import com.trustbank.R;
import com.trustbank.adapter.AmortizationChartDetailsAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AmortizationChartDetailsActivity extends AppCompatActivity {
    private static final String TAG = AmortizationChartDetailsActivity.class.getSimpleName();
    TrustMethods trustMethods;
    RecyclerView recyclerView;
    ArrayList<AmortizationChartDetailsListInfo> amortizationChartDetailsList = new ArrayList<AmortizationChartDetailsListInfo>();

    AmortizationChartDetailsListInfo amortizationChartDetailsListInfo;
    AmortizationChartDetailsAdapter amortizationChartDetailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        SetTheme.changeToTheme(AmortizationChartDetailsActivity.this, false);
        setContentView(R.layout.activity_amortization_chart_details);
        inIt();

        if (getIntent().getExtras() != null) {
            Intent i = getIntent();

            String method = i.getStringExtra("method");
            String expiryDate = i.getStringExtra("expiryDate");
            String interestCompoundingFrequency = i.getStringExtra("interestCompoundingFrequency");
            String installmentAppliedFrequency = i.getStringExtra("installmentAppliedFrequency");
            String loanAmount = i.getStringExtra("loanAmount");
            String disbursementDate = i.getStringExtra("disbursementDate");
            String firstInstallmentDate = i.getStringExtra("firstInstallmentDate");
            String loanPeriod = i.getStringExtra("loanPeriod");
            String intrestRate = i.getStringExtra("interestRate");

            TrustMethods.LogMessage(TAG, "expiryDate-->" + expiryDate);
            TrustMethods.LogMessage(TAG, "method -->" + method);
            TrustMethods.LogMessage(TAG, "interestRate -->" + intrestRate);

            if (NetworkUtil.getConnectivityStatus(AmortizationChartDetailsActivity.this)) {
                new AsyncTaskAmortizationChartDetails(AmortizationChartDetailsActivity.this, method, expiryDate, interestCompoundingFrequency, installmentAppliedFrequency, loanAmount, disbursementDate, firstInstallmentDate, loanPeriod, intrestRate).execute();
            } else {
                TrustMethods.message(AmortizationChartDetailsActivity.this, getResources().getString(R.string.error_check_internet));
            }
        //}
        }
    }

    private void inIt() {
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            trustMethods =new TrustMethods(AmortizationChartDetailsActivity.this);
            trustMethods.activityOpenAnimation();
            recyclerView = (RecyclerView) findViewById(R.id.recyclerAmortization_Id);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class AsyncTaskAmortizationChartDetails extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        JSONArray data;
        String result;
        String actionName ="GET_AMORTIZATION_CHART";
        String mExpiryDate, mDisbursementDate, mFirstInstallmentDate;
        String mLoanAmount, mLoanPeriod, mIntrestRate;
        String mMethod, mInstallmentAppliedFrequency, mIntrestCompoundingFrequency;


        public AsyncTaskAmortizationChartDetails(Context ctx, String method, String expiryDate, String intrestCompoundingFrequency, String installmentAppliedFrequency, String loanAmount, String disbursementDate, String firstInstallmentDate, String loanPeriod, String intrestRate) {

            this.error = "";
            this.ctx = ctx;
            this.mExpiryDate = expiryDate;
            this.mMethod = method;
            this.mIntrestCompoundingFrequency = intrestCompoundingFrequency;
            this.mInstallmentAppliedFrequency = installmentAppliedFrequency;
            this.mLoanAmount = loanAmount;
            this.mDisbursementDate = disbursementDate;
            this.mFirstInstallmentDate = firstInstallmentDate;
            this.mLoanPeriod = loanPeriod;
            this.mIntrestRate = intrestRate;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AmortizationChartDetailsActivity.this);
            pDialog.setMessage("Loading Amortization Chart Details....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetAmortizationChart(mExpiryDate,mMethod,mIntrestCompoundingFrequency,mInstallmentAppliedFrequency,mLoanAmount,mDisbursementDate,mFirstInstallmentDate,mLoanPeriod,mIntrestRate);
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
                    Log.e("welcome_res", String.valueOf(data));
                    if (data.length() == 0) {
                        error = AppConstants.NO_RECORDS_FOUND;
                        return error;
                    }

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jsonObjectEnquiryDetailsList = data.getJSONObject(i);

                        String Amortization_Installment_No = jsonObjectEnquiryDetailsList.getString("Install.No.");
                        String Amortization_Expected_Date = jsonObjectEnquiryDetailsList.getString("Expected Date");
                        String Amortization_Interest_Amount = jsonObjectEnquiryDetailsList.getString("Expected Int Amuont");
                        String Amortization_Principle_Amount = jsonObjectEnquiryDetailsList.getString("PrincipalAmount");
                        String Amortization_Installment_Amount = jsonObjectEnquiryDetailsList.getString("EMI");
                        String Amortization_Balance = jsonObjectEnquiryDetailsList.getString("Expected Balance");


                        amortizationChartDetailsListInfo = new AmortizationChartDetailsListInfo();
                        amortizationChartDetailsListInfo.setInstallmentNo(Amortization_Installment_No);
                        amortizationChartDetailsListInfo.setExpectedDate(Amortization_Expected_Date);
                        amortizationChartDetailsListInfo.setInterestAmount((new DecimalFormat("###########.00")).format(Float.parseFloat(Amortization_Interest_Amount)));
                        amortizationChartDetailsListInfo.setPrincipleAmount((new DecimalFormat("###########.00")).format(Float.parseFloat(Amortization_Principle_Amount)));
                        amortizationChartDetailsListInfo.setInstallmentAmount((new DecimalFormat("###########.00")).format(Float.parseFloat(Amortization_Installment_Amount)));
                        amortizationChartDetailsListInfo.setBalance((new DecimalFormat("###########.00")).format(Float.parseFloat(Amortization_Balance)));

                        amortizationChartDetailsList.add(amortizationChartDetailsListInfo);

                        TrustMethods.LogMessage(TAG, "Amortization_Installment_No 1 ->" + (new DecimalFormat("###########.00")).format(Float.parseFloat(Amortization_Installment_No)));
                        TrustMethods.LogMessage(TAG, "Amortization_Expected_Date 2 ->" + Amortization_Expected_Date);
                        TrustMethods.LogMessage(TAG, "Amortization_Intrest_Amount 3 ->" + Amortization_Interest_Amount);
                        TrustMethods.LogMessage(TAG, "Amortization_Intrest_Amount 3 ->" + Amortization_Interest_Amount);
                        TrustMethods.LogMessage(TAG, "Amortization_Principle_Amount 4 ->" + Amortization_Principle_Amount);
                        TrustMethods.LogMessage(TAG, "Amortization_Installment_Amount 5 ->" + Amortization_Installment_Amount);
                        TrustMethods.LogMessage(TAG, "Amortization_Balance 6 ->" + Amortization_Balance);
                    }

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
                trustMethods.message(this.ctx, error);
                return;
            }
            TrustMethods.LogMessage(TAG, "amortizationChartDetailsList size ->" + amortizationChartDetailsList.size());
            amortizationChartDetailsAdapter = new AmortizationChartDetailsAdapter(AmortizationChartDetailsActivity.this, amortizationChartDetailsList);
            recyclerView.setAdapter(amortizationChartDetailsAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.smoothScrollToPosition(amortizationChartDetailsAdapter.getItemCount() - 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                trustMethods.activityCloseAnimation();
                Intent intent = new Intent(AmortizationChartDetailsActivity.this, MenuActivity.class);
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
        super.onBackPressed();
        trustMethods.activityOpenAnimation();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}