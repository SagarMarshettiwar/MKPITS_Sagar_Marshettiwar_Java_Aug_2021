package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.AccountOverviewModel;
import com.trustbank.Model.CheckBoxAccountOverviewModel;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.Module.ImageCatalogueModel;
import com.trustbank.R;
import com.trustbank.adapter.AccountOverViewAdapter;
import com.trustbank.adapter.CheckBoxAccountOverviewAdapter;
import com.trustbank.interfaces.AccountOverviewListener;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountOverviewActivity extends AppCompatActivity implements View.OnClickListener, AccountOverviewListener {
    private TrustMethods method;
    private ImageView backButton_new;
    private ListView listview;
    AccountOverviewListener accountOverviewListener=this;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    private TextView toolbar;
    ArrayList<AccountOverviewModel> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(AccountOverviewActivity.this);
                }
            }
        }

        setContentView(R.layout.activity_account_overview);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.AccountOverview);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

        inIt();
    }

    private void inIt() {
        method = new TrustMethods(AccountOverviewActivity.this);
        listview = findViewById(R.id.listview);
        backButton_new = findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);
        loadListViewer();
    }

    private void loadListViewer() {
        List<String> accountList = null;
        List<String> investmentList = null;
        List<String> creditList = null;
        try {
            accountsArrayList = method.getArrayList(AccountOverviewActivity.this, "AccountListPref");
            accountList = new ArrayList<>();
            accountList.add(0, getResources().getString(R.string.SelectAccountNumber));
            investmentList = new ArrayList<>();
            investmentList.add(0, getResources().getString(R.string.SelectAccountNumber));
            creditList = new ArrayList<>();
            creditList.add(0, getResources().getString(R.string.SelectAccountNumber));
            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (TrustMethods.savingACC(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        accountList.add(accNo);
                    } else if (TrustMethods.investmentACC(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        investmentList.add(accNo);
                    } else if (TrustMethods.creditACC(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        creditList.add(accNo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemList.add(new AccountOverviewModel(R.drawable.cps_license, getResources().getString(R.string.SavingsAccount), accountList));
        itemList.add(new AccountOverviewModel(R.drawable.analytics_certification_exam,  getResources().getString(R.string.InvestmentsAccount), investmentList));
        itemList.add(new AccountOverviewModel(R.drawable.financing, getResources().getString(R.string.CreditAccount), creditList));

        AccountOverViewAdapter adapter = new AccountOverViewAdapter(this, R.layout.item_account_overview, itemList,accountOverviewListener);
        listview.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
            if (menuModel.getMenucode().equalsIgnoreCase("mnu_account_overview")) {
                Intent intent = new Intent(AccountOverviewActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }

        Intent intent = new Intent(AccountOverviewActivity.this, AccountsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public void selectedAccount(String acc) {
//        Toast.makeText(AccountOverviewActivity.this, "acc  "+acc, Toast.LENGTH_SHORT).show();
        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(AccountOverviewActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(AccountOverviewActivity.this)) {
                new AsyncTaskGetOverviews(AccountOverviewActivity.this,acc).execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(AccountOverviewActivity.this);
        }
    }
    private class AsyncTaskGetOverviews extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        String checkbook;
        String bankbook;
        TrustMethods methods;
        String result,acc;
        String actionName ="ACC_OVERVIEW";

        public AsyncTaskGetOverviews(Context ctx, String acc) {
            this.error = "";
            this.acc = acc;
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.Getoverview(AppConstants.getCLIENTID(),acc);
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
                    if (data.length() == 0) {
                        error = AppConstants.NO_RECORDS_FOUND;
                        return error;
                    }
                    if(data !=null && data.length()>0) {
                         JSONObject JsonObject = data.getJSONObject(0);
                         checkbook = JsonObject.has("ChequeBookFacility") ? JsonObject.getString("ChequeBookFacility") : "NA";
                         bankbook = JsonObject.has("BankBookFacility") ? JsonObject.getString("BankBookFacility") : "NA";
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
                methods.message(this.ctx, error);
                return;
            }
            /*Toast.makeText(ctx, " "+checkbook+" "+bankbook, Toast.LENGTH_SHORT).show();*/
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            ArrayList<CheckBoxAccountOverviewModel> itemList = new ArrayList<>();
            itemList.add(new CheckBoxAccountOverviewModel(getResources().getString(R.string.Chequebook),Boolean.parseBoolean(checkbook)));
            itemList.add(new CheckBoxAccountOverviewModel(getResources().getString(R.string.Passbook),Boolean.parseBoolean(bankbook)));
            CheckBoxAccountOverviewAdapter adapter = new CheckBoxAccountOverviewAdapter(getApplicationContext(), itemList);
            recyclerView.setAdapter(adapter);

        }
    }
}