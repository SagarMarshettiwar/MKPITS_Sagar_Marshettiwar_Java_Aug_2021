package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.FDModel;
import com.trustbank.R;
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

import java.text.DecimalFormat;
import java.util.ArrayList;


public class FdShowActivity extends AppCompatActivity {
    public static final String TAG = FdShowActivity.class.getSimpleName();
    ArrayList<FDModel> fdModels;
    private TrustMethods trustMethods;
    String checkFd;
    TableLayout tableLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        SetTheme.changeToTheme(FdShowActivity.this, false);
        setContentView(R.layout.activity_fd_show);

        inIt();
        if (getIntent().getExtras() != null) {

            checkFd = getIntent().getExtras().getString("checkFd");

            if (checkFd != null) {

                if (NetworkUtil.getConnectivityStatus(FdShowActivity.this)) {
                    if (checkFd.equals("FD")) {

                        Bundle getBundle;
                        getBundle = this.getIntent().getExtras();

                        String  accountType= getBundle.getString("accountType");
                        String interestCalMethod = getBundle.getString("interestCalMethod");
                        String mAmount = getBundle.getString("mInstallationAmount");
                        String mDepositeDate = getBundle.getString("mDepositeDate");
                        String mPeriod = getBundle.getString("mPeriod");
                        String periodUnit = getBundle.getString("periodUnit");
                        String mIntrestRate = getBundle.getString("mIntrestRate");
                        String extraInterestRate = getBundle.getString("extraInterestRate");
                        String showAvailScheme = getBundle.getString("showAvailScheme");
                        String mRepaymentFreq = getBundle.getString("mRepaymentFreq");
                        String mCompoundingFreq = getBundle.getString("mCompoundingFreq");

                        new AsyncTaskEnquiryDetailsFD_RD(FdShowActivity.this,accountType,interestCalMethod, mAmount, mDepositeDate, mPeriod, periodUnit, mIntrestRate, extraInterestRate, showAvailScheme, mRepaymentFreq, mCompoundingFreq).execute();
                    } else {
                        Bundle getBundle;
                        getBundle = this.getIntent().getExtras();
                        String accountType = getBundle.getString("accountType");
                        String mInstallationAmount = getBundle.getString("mInstallationAmount");
                        String mDepositeDate = getBundle.getString("mDepositeDate");
                        String mPeriod = getBundle.getString("mPeriod");
                        String periodUnit = getBundle.getString("periodUnit");
                        String mIntrestRate = getBundle.getString("mIntrestRate");
                        String extraInterestRate = getBundle.getString("extraInterestRate");
                        String showAvailScheme = getBundle.getString("showAvailScheme");
                        String interestCalMethod = getBundle.getString("interestCalMethod");

                       new AsyncTaskEnquiryDetailsFD_RD(FdShowActivity.this,accountType,interestCalMethod, mInstallationAmount, mDepositeDate, mPeriod, periodUnit, mIntrestRate, extraInterestRate, showAvailScheme).execute();

                    }
                } else {
                    TrustMethods.message(FdShowActivity.this, getResources().getString(R.string.error_check_internet));
                }
            }

        }
    }
    public void inIt() {
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            trustMethods = new TrustMethods(FdShowActivity.this);
            trustMethods.activityOpenAnimation();
            fdModels = new ArrayList<FDModel>();
            tableLayout = (TableLayout) findViewById(R.id.table1);
            tableLayout.removeAllViewsInLayout();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private class AsyncTaskEnquiryDetailsFD_RD extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String amount,accType, depositDate, period, periodUnit, intrestRate, exatraIntrestRate, RepaymentFreq, CompoundingFreq;
        String mChecked, mInterestCalMethod;
        String mName;
        String mValue;
        JSONArray data;

        String result;
        String actionName = "GET_FD_RD_RECKNOR";

        public AsyncTaskEnquiryDetailsFD_RD(FdShowActivity fdShowActivity,String accType, String interestCalMethod, String mAmount, String mDepositDate, String mPeriod, String mPeriodUnit, String mIntrestRate, String mExtraIntrestRate, String showAvailScheme, String mRepaymentFreq, String mCompoundingFreq) {
            this.error = "";
            this.ctx = fdShowActivity;
            this.accType = accType;
            this.amount = mAmount;
            this.depositDate = mDepositDate;
            this.period = mPeriod;
            this.periodUnit = mPeriodUnit;
            this.intrestRate = mIntrestRate;
            this.exatraIntrestRate = mExtraIntrestRate;
            this.mChecked = showAvailScheme;
            this.RepaymentFreq = mRepaymentFreq;
            this.CompoundingFreq = mCompoundingFreq;
            this.mInterestCalMethod = interestCalMethod;
        }

        public AsyncTaskEnquiryDetailsFD_RD(FdShowActivity fdShowActivity,String accType, String interestCalMethod, String mInstallationAmount, String mDepositeDate, String mPeriod, String periodUnit, String mIntrestRate, String extraInterestRate, String showAvailScheme) {
            this.error = "";
            this.ctx = fdShowActivity;
            this.accType = accType;
            this.amount = mInstallationAmount;
            this.depositDate = mDepositeDate;
            this.period = mPeriod;
            this.periodUnit = periodUnit;
            this.intrestRate = mIntrestRate;
            this.RepaymentFreq ="0";
            this.CompoundingFreq ="0";
            this.exatraIntrestRate = extraInterestRate;
            this.mChecked = showAvailScheme;
            this.mInterestCalMethod = interestCalMethod;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FdShowActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetRdFDRecknortSchemeUrl(accType,mInterestCalMethod, amount, depositDate, period, periodUnit, intrestRate, exatraIntrestRate, mChecked, RepaymentFreq, CompoundingFreq);
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
                if (this.error != "") {
                    trustMethods.message(this.ctx, error);
                    return;
                }
                if(data.length()>0) {
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jsonObject1 = (JSONObject) data.get(i);

                        FDModel fdModel = new FDModel();
                        mName = jsonObject1.getString("name");
                        mValue = jsonObject1.getString("value");

                        if (mName.contentEquals(mValue)) {
                            fdModel.setTitle(mName);
                            fdModel.setName("");
                            fdModel.setValue("");
                        } else {
                            fdModel.setTitle("");

                            if (mName.contains(";")) {
                                String replaceNbsp = mName.replace("&nbsp;", "");
                                fdModel.setName(replaceNbsp);
                                fdModel.setValue(mValue);
                                TrustMethods.LogMessage("if Name Value: ", replaceNbsp + " " + mValue);
                            } else {

                                fdModel.setName(mName);
                                fdModel.setValue(mValue);
                                TrustMethods.LogMessage("Else Name Value: ", mName + " " + mValue);
                            }
                        }
                        fdModels.add(fdModel);
                    }
                }else{
                    Toast.makeText(ctx, "No scheme is available", Toast.LENGTH_SHORT).show();
                }
                loadData();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void loadData() {

        for (int i = 0; i < fdModels.size(); i++) {
            String title = null;
            String name = null;
            String value = null;

            if (!fdModels.get(i).getTitle().equals("")) {

                title = fdModels.get(i).getTitle();

            } else {

                name = fdModels.get(i).getName();

                value = fdModels.get(i).getValue();

            }
            TableRow tableRow = new TableRow(FdShowActivity.this);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 15;
            int topMargin = 5;
            int rightMargin = 15;
            int bottomMargin = 5;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            tableRow.setLayoutParams(tableRowParams);
            tableRow.setId(i);

            if (!fdModels.get(i).getTitle().equals("")) {

                TextView textViewTitle = new TextView(FdShowActivity.this,null,R.attr.textColorAccent);
                textViewTitle.setTextSize(16);
                textViewTitle.setText(title);
                textViewTitle.setFreezesText(true);
                textViewTitle.setWidth(550);
                textViewTitle.setTypeface(null, Typeface.BOLD);
                tableRow.addView(textViewTitle);

            }

            if (name != null) {
                TextView textViewName = new TextView(FdShowActivity.this,null,R.attr.textColorPrimary);
                textViewName.setTextSize(14);
                textViewName.setText(name);
                textViewName.setFreezesText(true);
                textViewName.setWidth(450);
                tableRow.addView(textViewName);

            }

            if (value != null) {
                TextView textViewValue = new TextView(FdShowActivity.this,null,R.attr.textColorPrimary);
                textViewValue.setTextSize(14);
                textViewValue.setText(value);
                textViewValue.setFreezesText(true);
                textViewValue.setWidth(450);
                tableRow.addView(textViewValue);
            }

            tableLayout.addView(tableRow);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                trustMethods.activityCloseAnimation();
                Intent intent = new Intent(FdShowActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!fdModels.isEmpty()) {
            fdModels.clear();
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