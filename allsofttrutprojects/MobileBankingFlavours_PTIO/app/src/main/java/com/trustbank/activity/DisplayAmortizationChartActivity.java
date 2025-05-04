package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.AmortizationChartModel;
import com.trustbank.R;
import com.trustbank.adapter.AccountsBalanceAdapter;
import com.trustbank.adapter.AmortizationChartAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayAmortizationChartActivity extends AppCompatActivity implements View.OnClickListener{
    public TextView toolbar;
    public ImageView backButton_new;
     RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(DisplayAmortizationChartActivity.this);
                }
            }
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        SetTheme.changeToTheme(DisplayAmortizationChartActivity.this, false);
        setContentView(R.layout.activity_display_amortization_chart);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.AmortizationChart);

        inIt();
    }

    private void inIt() {
        backButton_new=findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);
        recycler_view=findViewById(R.id.recycler_view);

        Intent intent = getIntent();
        String installemntID = intent.getStringExtra("installemntID");
        String chartId = intent.getStringExtra("chartId");
        String expiry_date = intent.getStringExtra("expiry_date");
        String due_date = intent.getStringExtra("due_date");
        String creditDate = intent.getStringExtra("creditDate");
        String creditAmount = intent.getStringExtra("creditAmount");
        String intrst_rate = intent.getStringExtra("intrst_rate");
        String loanperiod = intent.getStringExtra("loanperiod");
        String insurance = intent.getStringExtra("insurance");
        String insurance_rate = intent.getStringExtra("insurance_rate");
        String intrst_cal = intent.getStringExtra("intrst_cal");

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(DisplayAmortizationChartActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(DisplayAmortizationChartActivity.this)) {
                new AsyncTaskGetLoanRepayment(DisplayAmortizationChartActivity.this, installemntID, chartId, expiry_date,due_date,creditDate,creditAmount,intrst_rate,loanperiod, insurance,insurance_rate,intrst_cal).execute();
            } else {
                Toast.makeText(DisplayAmortizationChartActivity.this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(DisplayAmortizationChartActivity.this);
        }
    }

    private class AsyncTaskGetLoanRepayment extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        TrustMethods methods;
        JSONArray data;
        List<AmortizationChartModel> amortChartList;
        String result, installemntID, chartId, expiryDate,dueDate,creditDate,creditAmount,intrstRate,loanperiod,insurance,insuranceRate,intrstCal;
        String actionName ="GET_LOAN_REPAYMENT";

        public AsyncTaskGetLoanRepayment(Context ctx, String installemntID, String chartId, String expiryDate, String dueDate, String creditDate, String creditAmount, String intrstRate, String loanperiod, String insurance, String insuranceRate, String intrstCal) {
            this.error = "";
            this.ctx=ctx;
            this.installemntID=installemntID;
            this.chartId=chartId;
            this.expiryDate=expiryDate;
            this.dueDate=dueDate;
            this.creditDate=creditDate;
            this.creditAmount=creditAmount;
            this.intrstRate=intrstRate;
            this.loanperiod=loanperiod;
            this.insurance=insurance;
            this.insuranceRate=insuranceRate;
            this.intrstCal=intrstCal;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DisplayAmortizationChartActivity.this);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getLoanRepayment(chartId,expiryDate,installemntID, installemntID,dueDate,dueDate,creditAmount,intrstRate,creditDate,loanperiod,"1", insurance, insuranceRate, intrstCal);
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

                    amortChartList = new ArrayList<>();
                    for(int i=0; i<data.length(); i++){
                        JSONObject JsonObject = data.getJSONObject(i);
                        String InstNo=JsonObject.has("InstNo") ? JsonObject.getString("InstNo") : "NA";
                        String Duedate=JsonObject.has("Duedate") ? JsonObject.getString("Duedate") : "NA";
                        String InsAmt=JsonObject.has("InsAmt") ? JsonObject.getString("InsAmt") : "NA";
                        String IntBalAmt=JsonObject.has("IntBalAmt") ? JsonObject.getString("IntBalAmt") : "NA";
                        String IntInstAmt=JsonObject.has("IntInstAmt") ? JsonObject.getString("IntInstAmt") : "NA";
                        String IsIntDuedate=JsonObject.has("IsIntDuedate") ? JsonObject.getString("IsIntDuedate") : "NA";
                        String IsPrDuedate=JsonObject.has("IsPrDuedate") ? JsonObject.getString("IsPrDuedate") : "NA";
                        String Noofdays=JsonObject.has("Noofdays") ? JsonObject.getString("Noofdays") : "NA";
                        String PrBalAmt=JsonObject.has("PrBalAmt") ? JsonObject.getString("PrBalAmt") : "NA";
                        String PrInstAmt=JsonObject.has("PrInstAmt") ? JsonObject.getString("PrInstAmt") : "NA";
                        String TotBalamt=JsonObject.has("TotBalamt") ? JsonObject.getString("TotBalamt") : "NA";
                        String TotInstAmt=JsonObject.has("TotInstAmt") ? JsonObject.getString("TotInstAmt") : "NA";
                        Duedate=Duedate.substring(0,Duedate.indexOf("T"));

                        AmortizationChartModel amortModel = new AmortizationChartModel();
                        amortModel.setInstNo(InstNo);
                        amortModel.setDuedate(Duedate);
                        amortModel.setInsAmt(InsAmt);
                        amortModel.setIntBalAmt(IntBalAmt);
                        amortModel.setIntInstAmt(IntInstAmt);
                        amortModel.setIsIntDuedate(IsIntDuedate);
                        amortModel.setIsPrDuedate(IsPrDuedate);
                        amortModel.setNoofdays(Noofdays);
                        amortModel.setPrBalAmt(PrBalAmt);
                        amortModel.setPrInstAmt(PrInstAmt);
                        amortModel.setTotBalamt(TotBalamt);
                        amortModel.setTotInstAmt(TotInstAmt);
                        amortChartList.add(amortModel);
                    }
                }else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (this.error != "") {
                methods.message(this.ctx, error);
                return;
            }
                AmortizationChartAdapter adapter=new AmortizationChartAdapter(DisplayAmortizationChartActivity.this,amortChartList);
                recycler_view.setHasFixedSize(true);
                recycler_view.setLayoutManager(new LinearLayoutManager(DisplayAmortizationChartActivity.this));
                recycler_view.setAdapter(adapter);

        }
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(DisplayAmortizationChartActivity.this, CreditSimulatorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}