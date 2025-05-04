package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenInvestmentAccountActivity extends AppCompatActivity implements View.OnClickListener {

    CoordinatorLayout coordinatorLayout;
    List<String> Identificationlist;
    String identificationid,productid,branchid,needCheque="0";
    List<String> branchlist;
    List<String> savingschemeslist;
    Spinner Identification_spi;
    Spinner product_spi;
    Spinner branch_spi;
    TextView tv_investmentdate;
    EditText et_identificationno, et_name, et_email, et_address, et_remarks;
    HashMap<String,String> Identificationmap;
    HashMap<String,String> branchmap;
    HashMap<String,String> savingschemesmap;
    TrustMethods methods;
    Button btn_submit;
    private ImageView backButton_new;
    private TextView toolbar;
    CheckBox Checkbox;

    String name, investmentDate, email, identificationNumber, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(OpenInvestmentAccountActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(OpenInvestmentAccountActivity.this, false);
        setContentView(R.layout.activity_open_savings_account);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.OpenInvestmentAccount);

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
        backButton_new.setOnClickListener(this);
        methods=new TrustMethods(OpenInvestmentAccountActivity.this);
        product_spi=findViewById(R.id.product_spi);
        Checkbox=findViewById(R.id.Checkbox);
        Checkbox.setVisibility(View.GONE);
        et_remarks=findViewById(R.id.et_remarks);
        btn_submit=findViewById(R.id.btn_submit);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        btn_submit.setOnClickListener(this);

        /*Identification_spi=findViewById(R.id.Identification_spi);
        branch_spi=findViewById(R.id.branch_spi);
        et_identificationno=findViewById(R.id.et_identificationno);
        tv_investmentdate=findViewById(R.id.tv_investmentdate);
        tv_investmentdate.setOnClickListener(this);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_address = findViewById(R.id.et_address);*/



        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(OpenInvestmentAccountActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(OpenInvestmentAccountActivity.this)) {
                new AsyncTaskGetSavinglookups(OpenInvestmentAccountActivity.this).execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(OpenInvestmentAccountActivity.this);
        }


        Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    needCheque="1";
                }else{
                    needCheque="0";
                }
            }
        });
        /*et_identificationno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(Identification_spi.getSelectedItemPosition()==6 && et_identificationno.length() ==13 ){
                        String action1="VALIDATE_RUC";
                        new AsyncTaskverifyIdentificationno(OpenInvestmentAccountActivity.this,et_identificationno.getText().toString(),action1).execute();

                    }else if(Identification_spi.getSelectedItemPosition()==1 && et_identificationno.length() ==10){
                        String action="VALIDATE_CEDULA";
                        new AsyncTaskverifyIdentificationno(OpenInvestmentAccountActivity.this,et_identificationno.getText().toString(),action).execute();
                    }else{
                        if(Identification_spi.getSelectedItemPosition()==6 && et_identificationno.length() !=13){
                            Toast.makeText(OpenInvestmentAccountActivity.this, "Please Enter 13 Digits RUC Number", Toast.LENGTH_SHORT).show();
                        } else if (Identification_spi.getSelectedItemPosition()==1 && et_identificationno.length() !=10) {
                            Toast.makeText(OpenInvestmentAccountActivity.this, "Please Enter 10 Digits CEDULA Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });*/
    }

    /*private class AsyncTaskverifyIdentificationno extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        TrustMethods methods;
        String result ,idno,act;

        public AsyncTaskverifyIdentificationno(Context ctx, String idno, String act) {
            this.error = "";
            this.ctx = ctx;
            this.idno = idno;
            this.act = act;
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

                if(act.equalsIgnoreCase("VALIDATE_CEDULA")){
                    String url = TrustURL.ValidateCedula(idno);
                    if (!url.equals("")) {
                        result = HttpClientWrapper.getcedulaverifyGET(url, act);
                    }
                }else {
                    String url = TrustURL.ValidateRuc(idno);
                    if (!url.equals("")) {
                        result = HttpClientWrapper.getcedulaverifyGET(url, act);
                    }
                    result = HttpClientWrapper.getcedulaverifyGET(url,act);
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
            Toast.makeText(ctx, "Identification Number Verified", Toast.LENGTH_SHORT).show();

        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton_new:
                for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_open_investment_account")) {
                        Intent intent = new Intent(OpenInvestmentAccountActivity.this, MenuActivity.class);
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

                Intent intent = new Intent(OpenInvestmentAccountActivity.this, InvestmentMenus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            /*case R.id.tv_investmentdate :
                methods.datePickerymd(this,tv_investmentdate);
                break;*/

            case R.id.btn_submit :
                submit();
                break;
        }
    }

    private void submit() {
        String remarks=et_remarks.getText().toString();
        /*name = et_name.getText().toString();
        investmentDate = tv_investmentdate.getText().toString();
        email = et_email.getText().toString();
        identificationNumber = et_identificationno.getText().toString();
        address = et_address.getText().toString();*/
        if (product_spi.getSelectedItem().equals(getResources().getString(R.string.SelectProductType))) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.PleaseSelectProducttype), Toast.LENGTH_SHORT).show();
        }else{
            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(OpenInvestmentAccountActivity.this)) {
                if (NetworkUtil.getConnectivityStatus(OpenInvestmentAccountActivity.this)) {
                    new AsyncTaskSubmit(OpenInvestmentAccountActivity.this, productid, remarks,needCheque).execute();
                } else {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                }
            } else {
                TrustMethods.displaySimErrorDialog(OpenInvestmentAccountActivity.this);
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
        JSONArray savingSchemes;
        JSONArray branch;
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
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
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
                    savingSchemes = jsonResponse.getJSONObject("response").getJSONArray("Table3");
                    if (savingSchemes != null && savingSchemes.length() > 0) {
                        savingschemeslist = new ArrayList<>();
                        savingschemesmap = new HashMap<>();
                        savingschemeslist.add(0,getResources().getString(R.string.SelectProductType));
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

                    } else {
                        String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (this.error != "") {
                methods.message(this.ctx, error);
                return;
            }

            /*ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, Identificationlist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Identification_spi.setAdapter(adapter);

            Identification_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){
                        String selectedidentification = (String) parent.getItemAtPosition(position);
                        identificationid=Identificationmap.get(selectedidentification);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, savingschemeslist);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            product_spi.setAdapter(adapter1);
            product_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){
                        String selectedproduct = (String) parent.getItemAtPosition(position);
                        productid=savingschemesmap.get(selectedproduct);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*ArrayAdapter<String> adapter2 = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, branchlist);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            branch_spi.setAdapter(adapter2);
            branch_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){
                        String selectedbranch = (String) parent.getItemAtPosition(position);
                        branchid=branchmap.get(selectedbranch);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
        }
    }

    private class AsyncTaskSubmit extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        JSONArray data;
        String result, productId, remarks,needCheque;
        String actionName = "SAVE_OPEN_ACCOUNT";

        public AsyncTaskSubmit(Context ctx, String productId, String remarks, String needCheque) {

            this.error = "";
            this.ctx = ctx;
            this.productId = productId;
            this.remarks=remarks;
            this.needCheque=needCheque;
            /*this.name = name;
            this.investmentDate = investmentDate;
            this.email = email;
            this.identificationid = identificationid;
            this.identificationNumber = identificationNumber;
            this.address = address;
            this.branchId = branchId;*/
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OpenInvestmentAccountActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetOpenAccount(AppConstants.getCLIENTID(),productid,remarks,"Investment Account", needCheque);
                Log.e("URL", url);
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
                Log.e("Response Code", responseCode);

                if (responseCode.equals("1")) {
                    Log.e("SaveAsyncTask", "Result-->"+result);
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
            Intent i=new Intent(OpenInvestmentAccountActivity.this,SuccessSplashActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("Title",getResources().getString(R.string.ThankYou1));
            i.putExtra("Activity", "OpenInvestmentAccountActivity");
            startActivity(i);
        }
    }
}