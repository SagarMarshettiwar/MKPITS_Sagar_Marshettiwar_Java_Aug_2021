package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import com.trustbank.Model.InvestSlabModel;
import com.trustbank.Model.SlabDetailsModel;
import com.trustbank.R;
import com.trustbank.adapter.AccountsBalanceAdapter;
import com.trustbank.adapter.InvestmentAccountsAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class InvestmentAccountActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView toolbar;
    private ImageView backButton_new;
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
                    TrustMethods.naviagteToSplashScreen(InvestmentAccountActivity.this);
                }
            }
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        SetTheme.changeToTheme(InvestmentAccountActivity.this, false);
        setContentView(R.layout.activity_investment_account);
        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.InvestmentsAccount);

        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));
        inIt();
    }

    private void inIt() {
        backButton_new=findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);
        recycler_view=findViewById(R.id.recycler_view);

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(InvestmentAccountActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(InvestmentAccountActivity.this)) {
                new AsyncTaskInvestment(InvestmentAccountActivity.this).execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(InvestmentAccountActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(InvestmentAccountActivity.this, SaveMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private class AsyncTaskInvestment extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        TrustMethods methods;
        List<InvestSlabModel> Alldata;
        JSONArray data;
        String result;
        String actionName ="GET_ACC_SLAB";

        public AsyncTaskInvestment(Context ctx) {
            this.error = "";
            this.ctx = ctx;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InvestmentAccountActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.Getslab(AppConstants.getCLIENTID(),"I");
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

                    Alldata=new ArrayList<>();
                    for(int i=0 ;i<data.length();i++){
                        JSONObject JsonObject = data.getJSONObject(i);
                        String acno=JsonObject.has("AccountNumberForDisplay") ? JsonObject.getString("AccountNumberForDisplay") : "NA";
                        String schemeName=JsonObject.has("SchemeName") ? JsonObject.getString("SchemeName") : "NA";
                        String depositdate=JsonObject.has("DepositDate") ? JsonObject.getString("DepositDate") : "NA";
                        String maturitydate=JsonObject.has("MaturityDate") ? JsonObject.getString("MaturityDate") : "NA";
                        String depositamt=JsonObject.has("DepositAmount") ? JsonObject.getString("DepositAmount") : "NA";
                        String maturityamt=JsonObject.has("MaturityAmount") ? JsonObject.getString("MaturityAmount") : "NA";
                        String interestrate=JsonObject.has("InterestRate") ? JsonObject.getString("InterestRate") : "NA";

                        InvestSlabModel investSlabModel=new InvestSlabModel();
                        investSlabModel.setAcno(acno);
                        investSlabModel.setSchemeName(schemeName);
                        depositdate=depositdate.substring(0,depositdate.indexOf("T"));
                        investSlabModel.setDepositdate(depositdate);
                        maturitydate=maturitydate.substring(0,maturitydate.indexOf("T"));
                        investSlabModel.setMaturitydate(maturitydate);
                        investSlabModel.setDepositamt(depositamt);
                        investSlabModel.setMaturityamt(maturityamt);
                        investSlabModel.setInterestrate(interestrate);

                        Alldata.add(investSlabModel);
                    }
                }
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
            InvestmentAccountsAdapter adapter=new InvestmentAccountsAdapter(InvestmentAccountActivity.this,Alldata);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new LinearLayoutManager(InvestmentAccountActivity.this));
            recycler_view.setAdapter(adapter);
        }
    }
}