package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Element;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.SavingAccLevelModel;
import com.trustbank.Model.SlabDetailsModel;
import com.trustbank.R;
import com.trustbank.adapter.AccountsBalanceAdapter;
import com.trustbank.util.TrustURL;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SavingsAccountActivity extends AppCompatActivity implements View.OnClickListener{
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
                    TrustMethods.naviagteToSplashScreen(SavingsAccountActivity.this);
                }
            }
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        SetTheme.changeToTheme(SavingsAccountActivity.this, false);
        setContentView(R.layout.activity_savings_account);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.SavingsAccount);

        inIt();
    }

    private void inIt() {
        backButton_new=findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);
        recycler_view=findViewById(R.id.recycler_view);

        /*AccountsBalanceAdapter adapter=new AccountsBalanceAdapter(SavingsAccountActivity.this);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(SavingsAccountActivity.this));
        recycler_view.setAdapter(adapter);*/

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(SavingsAccountActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(SavingsAccountActivity.this)) {
                new AsyncTaskSaving(SavingsAccountActivity.this).execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(SavingsAccountActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SavingsAccountActivity.this, SaveMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private class AsyncTaskSaving extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        List<SavingAccLevelModel> slab;
        List<SlabDetailsModel> Alldata;
        TrustMethods methods;
        JSONArray data;
        String result;
        String actionName ="GET_ACC_SLAB";

        public AsyncTaskSaving(Context ctx) {

            this.error = "";
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SavingsAccountActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.Getslab(AppConstants.getCLIENTID(),"S");
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
                        String acno=JsonObject.has("Accountnumberfordisplay") ? JsonObject.getString("Accountnumberfordisplay") : "NA";
                        String balance=JsonObject.has("balance") ? JsonObject.getString("balance") : "NA";
                        String schemeName=JsonObject.has("SchemeName") ? JsonObject.getString("SchemeName") : "NA";
                        String minimumbalance=JsonObject.has("minimumbalance") ? JsonObject.getString("minimumbalance") : "NA";
                        String maxtranbalanceamount=JsonObject.has("maxtranbalanceamount") ? JsonObject.getString("maxtranbalanceamount") : "NA";

                        SlabDetailsModel slabDetailsModel=new SlabDetailsModel();
                        slabDetailsModel.setAcno(acno);
                        slabDetailsModel.setBalance(balance);
                        slabDetailsModel.setSchemeName(schemeName);
                        slabDetailsModel.setMinimumbalance(minimumbalance);
                        slabDetailsModel.setMaxtranbalanceamount(maxtranbalanceamount);

                        String intSlab=JsonObject.has("intSlab") ? JsonObject.getString("intSlab") : "NA";
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(intSlab)));
                            NodeList nodeList = document.getElementsByTagName("int_slab");
                            slab=new ArrayList<>();
                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                String fromLevel = element.getElementsByTagName("Fromlevel").item(0).getTextContent();
                                String toLevel = element.getElementsByTagName("Tolevel").item(0).getTextContent();
                                String interestRate = element.getElementsByTagName("interestrate").item(0).getTextContent();
                                SavingAccLevelModel slabDetailsModel1=new SavingAccLevelModel();
                                slabDetailsModel1.setFromLevel(fromLevel);
                                slabDetailsModel1.setToLevel(toLevel);
                                slabDetailsModel1.setInterestRate(interestRate);
                                slab.add(slabDetailsModel1);
                            }
                            slabDetailsModel.setSlablevels(slab);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Alldata.add(slabDetailsModel);
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
            AccountsBalanceAdapter adapter=new AccountsBalanceAdapter(SavingsAccountActivity.this,Alldata);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new LinearLayoutManager(SavingsAccountActivity.this));
            recycler_view.setAdapter(adapter);
        }
    }
}