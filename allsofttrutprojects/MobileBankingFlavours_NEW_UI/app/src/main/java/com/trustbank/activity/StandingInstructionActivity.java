package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.Model.SIModel;
import com.trustbank.R;
import com.trustbank.adapter.ExistingDetaillistAdapter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandingInstructionActivity extends AppCompatActivity implements View.OnClickListener , AlertDialogOkListener {
    Spinner spinnerFrmActId,spinnerToActId,spinnerFrequency;
    Button btn_Submit;
    TextView tv_ExecutionDate;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    private List<String> accountList;
    AlertDialogOkListener AlertDialogOkListener=this;
    private List<String> toaccountList;
    TrustMethods method;
    EditText et_ExecutionPeriod,et_Amount,et_remark;
    ListView lv_exist_list;
    private List<String> frequencyValue;
    String selectedFreqNo,ToaccNo,FromaccNo;
    LinearLayout ll_exectuiondetails,lv_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.changeToTheme(StandingInstructionActivity.this, false);
        setContentView(R.layout.activity_standing_instruction);
        Init();
    }
    private void Init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        method = new TrustMethods(StandingInstructionActivity.this);
        spinnerFrmActId=findViewById(R.id.spinnerFrmActId);
        lv_header=findViewById(R.id.lv_header);
        lv_exist_list=findViewById(R.id.lv_exist_list);
        spinnerToActId=findViewById(R.id.spinnerToActId);
        tv_ExecutionDate=findViewById(R.id.tv_ExecutionDate);
        tv_ExecutionDate.setOnClickListener(this);
        spinnerFrequency=findViewById(R.id.spinnerFrequency);
        et_ExecutionPeriod=findViewById(R.id.et_ExecutionPeriod);
        et_Amount=findViewById(R.id.et_Amount);
        et_remark=findViewById(R.id.et_remark);
        btn_Submit=findViewById(R.id.btn_Submit);
        ll_exectuiondetails=findViewById(R.id.ll_exectuiondetails);
        btn_Submit.setOnClickListener(this);
        ll_exectuiondetails.setVisibility(View.GONE);
        lv_header.setVisibility(View.GONE);

        loadAccounts();


    }

    private void loadAccounts() {
        try {
            accountsArrayList = method.getArrayList(StandingInstructionActivity.this, "AccountListPref");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (accountsArrayList != null && accountsArrayList.size() > 0) {
            accountList = new ArrayList<>();
            accountList.add(0, "Select Account Number");

            toaccountList = new ArrayList<>();
            toaccountList.add(0, "Select Account Number");
            for (int i = 0; i < accountsArrayList.size(); i++) {
                GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                if (TrustMethods.sItoacc(getUserProfileModal.getActType())) {
                    String accNo = getUserProfileModal.getAccNo();
                    String accTypeCode = getUserProfileModal.getAcTypeCode();
                    accountList.add(accNo + " - " + accTypeCode);
                }

                if (TrustMethods.sIfromacc(getUserProfileModal.getActType())) {
                    String accNo = getUserProfileModal.getAccNo();
                    String accTypeCode = getUserProfileModal.getAcTypeCode();
                    toaccountList.add(accNo + " - " + accTypeCode);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(StandingInstructionActivity.this, android.R.layout.simple_spinner_item, accountList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFrmActId.setAdapter(adapter);

            ArrayAdapter<String> fadapter = new ArrayAdapter<>(StandingInstructionActivity.this, android.R.layout.simple_spinner_item, toaccountList);
            fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerToActId.setAdapter(fadapter);

            frequencyValue = Arrays.asList(getResources().getStringArray(R.array.Standing_instruction_Freq));
            List<String> flist=new ArrayList<>();
            flist.add("Select Execution Frequency");
            flist.add("Monthly");
            flist.add("Quarterly ");
            flist.add("Half Yearly ");
            flist.add("Yearly");
            ArrayAdapter<String> freqadapter = new ArrayAdapter<>(StandingInstructionActivity.this, android.R.layout.simple_spinner_item, flist);
            freqadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFrequency.setAdapter(freqadapter);
        }

        spinnerFrmActId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        String selectedAccNo = (String) adapterView.getItemAtPosition(position);
                        FromaccNo = "";
                        if (selectedAccNo.contains("-")) {
                            String[] accounts = selectedAccNo.split("-");
                            FromaccNo = accounts[0];
                        } else {
                            FromaccNo = selectedAccNo;
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

        spinnerToActId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        String selectedAccNo = (String) adapterView.getItemAtPosition(position);
                        ToaccNo = "";
                        if (selectedAccNo.contains("-")) {
                            String[] accounts = selectedAccNo.split("-");
                            ToaccNo = accounts[0];
                        } else {
                            ToaccNo = selectedAccNo;
                        }

                        if(selectedAccNo.contains("RD")){
                            et_Amount.setEnabled(false);
                        } else if (selectedAccNo.contains("LN")) {
                            et_Amount.setEnabled(true);
                        }

                        new FormGETDetailsAsyncTask(StandingInstructionActivity.this,ToaccNo).execute();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        spinnerFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        selectedFreqNo = frequencyValue.get(position);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               /* for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_accounts_chq_status")) {
                        Intent intent = new Intent(ChequeStatusActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }
                if(AppConstants.account_group){
                    Intent intent = new Intent(ChequeStatusActivity.this, AccountsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.upi_group) {
                    Intent intent = new Intent(ChequeStatusActivity.this, UPIActivityMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.service_group) {
                    Intent intent = new Intent(ChequeStatusActivity.this, ServiceRequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.cards_group) {
                    Intent intent = new Intent(ChequeStatusActivity.this, Cards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.locate_us_group) {
                    Intent intent = new Intent(ChequeStatusActivity.this, LocateUs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }else if (AppConstants.need_help_group) {
                    Intent intent = new Intent(ChequeStatusActivity.this, NeedHelp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }
*/
                Intent intent = new Intent(StandingInstructionActivity.this, ServiceRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tv_ExecutionDate:
                    method.disableMindatePicker(StandingInstructionActivity.this, tv_ExecutionDate);
                    break;

                    case R.id.btn_Submit:
                        submit();
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void submit() {
        String debitAcc = FromaccNo;
        String creditAcc = ToaccNo;
        String execDate = tv_ExecutionDate.getText().toString().trim();
        String execFrequency = selectedFreqNo;
        String execPeriod = et_ExecutionPeriod.getText().toString().trim();
        String tranAmt = et_Amount.getText().toString().trim();
        String remark = et_remark.getText().toString().trim();

        if (debitAcc == null || debitAcc.isEmpty()) {
            Toast.makeText(this, "Debit account number cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (creditAcc == null || creditAcc.isEmpty()) {
            Toast.makeText(this, "Credit account number cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (execDate.isEmpty()) {
           // tv_ExecutionDate.setError("Execution date cannot be empty.");
            Toast.makeText(this, "Execution date cannot be empty.", Toast.LENGTH_SHORT).show();

            return;
        }

        if (execFrequency == null || execFrequency.isEmpty()) {
            Toast.makeText(this, "Execution frequency cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (execPeriod.isEmpty()) {
            et_ExecutionPeriod.setError("Execution period cannot be empty.");
            return;
        }

        if (tranAmt.isEmpty()) {
            et_Amount.setError("Transaction amount cannot be empty.");
            return;
        }

        if (remark.isEmpty()) {
            et_remark.setError("Remark cannot be empty.");
            return;
        }

        new FormValidateAsyncTask(StandingInstructionActivity.this, debitAcc, creditAcc, execDate, execFrequency, execPeriod, tranAmt, remark).execute();
    }

    @Override
    public void onDialogOk(int resultCode) {
        switch (resultCode) {
            case 0:

            default:
                break;
        }
    }
    private class FormGETDetailsAsyncTask extends AsyncTask<Void, Integer, String>  {
        String error = "";
        Context ctx;
        String response,toacc;
        String actionName = "VERIFIED_STANDING_INSTRUCTION";
        String result;
        List<SIModel> siModels;
        public ProgressDialog pDialog;
        private Handler handler;


        public FormGETDetailsAsyncTask(Context ctx ,String toacc) {
            this.ctx=ctx;
            this.toacc=toacc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StandingInstructionActivity.this);
            pDialog.setMax(45);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();
                JSONObject object=new JSONObject();
                //object.put("debitaccno","");
                object.put("creditaccno",toacc);
               /* object.put("firstexecdate","");
                object.put("execfrequency",);
                object.put("execperiod",);
                object.put("transferamount",);*/
                object.put("tag","GET_EXISTING_DETAILS");
                /*object.put("remark",);*/
                String jsonString=object.toString();

                if (!url.equals("")) {
                    TrustMethods.LogMessage("TAG", "URL:-" + url);
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, actionName,AppConstants.getAuth_token());
                    TrustMethods.LogMessage("TAG", "Frm Enquiry details response-->" + response);
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
                    siModels=new ArrayList<>();
                    JSONArray misc = jsonResponse.getJSONObject("response").getJSONArray("Table");
                    for(int i=0;i<misc.length();i++){
                        JSONObject jsonObjectDetailsList = misc.getJSONObject(i);
                        String ScheduleExecutionDate =jsonObjectDetailsList.getString("ScheduleExecutionDate");
                        String LoanAmount =jsonObjectDetailsList.getString("Amount");
                        SIModel siModel=new SIModel();
                        siModel.setScheduleExecutionDate(ScheduleExecutionDate);
                        siModel.setLoanAmount(LoanAmount);
                        siModels.add(siModel);
                    }

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
            super.onPostExecute(value);
            pDialog.dismiss();

            if (!this.error.equals("")) {
                AlertDialogMethod.alertDialogOk(StandingInstructionActivity.this,
                        "Error !!", error, getResources().getString(R.string.btn_ok),
                        0, false, AlertDialogOkListener);
                // Toast.makeText(ctx,error, Toast.LENGTH_LONG).show();
            } else {
                if(siModels.isEmpty()){
                    lv_header.setVisibility(View.GONE);
                    lv_exist_list.setVisibility(View.GONE);
                    ll_exectuiondetails.setVisibility(View.VISIBLE);
                    new FormGETAmountAsyncTask(StandingInstructionActivity.this,ToaccNo).execute();
                }else{
                    lv_header.setVisibility(View.VISIBLE);
                    lv_exist_list.setVisibility(View.VISIBLE);
                    ll_exectuiondetails.setVisibility(View.GONE);
                    ExistingDetaillistAdapter adapter=new ExistingDetaillistAdapter(StandingInstructionActivity.this, siModels);
                    lv_exist_list.setAdapter(adapter);
                }
            }
        }
    }

    private class FormGETAmountAsyncTask extends AsyncTask<Void, Integer, String>  {
        String error = "";
        Context ctx;
        String response,toacc;
        String actionName = "VERIFIED_STANDING_INSTRUCTION";
        String result;
        public ProgressDialog pDialog;
        private Handler handler;


        public FormGETAmountAsyncTask(Context ctx ,String toacc) {
            this.ctx=ctx;
            this.toacc=toacc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StandingInstructionActivity.this);
            pDialog.setMax(45);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();
                JSONObject object=new JSONObject();
                //object.put("debitaccno","");
                object.put("creditaccno",toacc);
               /* object.put("firstexecdate","");
                object.put("execfrequency",);
                object.put("execperiod",);
                object.put("transferamount",);*/
                object.put("tag","GET_AMOUNT");
                /*object.put("remark",);*/
                String jsonString=object.toString();

                if (!url.equals("")) {
                    TrustMethods.LogMessage("TAG", "URL:-" + url);
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, actionName,AppConstants.getAuth_token());
                    TrustMethods.LogMessage("TAG", "Frm Enquiry details response-->" + response);
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
                    JSONArray misc = jsonResponse.getJSONObject("response").getJSONArray("Table");
                    JSONObject jsonObjectAmount = misc.getJSONObject(0);
                    String standingInstructionId = jsonObjectAmount.has("Amount") ? jsonObjectAmount.getString("Amount") : "NA";
                    response = standingInstructionId;

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
            super.onPostExecute(value);
            pDialog.dismiss();

            if (!this.error.equals("")) {
                AlertDialogMethod.alertDialogOk(StandingInstructionActivity.this,
                        "Error !!", error, getResources().getString(R.string.btn_ok),
                        0, false, AlertDialogOkListener);
                // Toast.makeText(ctx,error, Toast.LENGTH_LONG).show();
            } else {
                et_Amount.setText(response);
            }
        }
    }

    private class FormValidateAsyncTask extends AsyncTask<Void, Integer, String>  {
        String error = "";
        Context ctx;
        String response,debitacc,creditacc,execdate,execFrequency,execPeriod,tranAmt,remark;
        String actionName = "VERIFIED_STANDING_INSTRUCTION";
        String result;
        public ProgressDialog pDialog;
        private Handler handler;


        public FormValidateAsyncTask(Context ctx, String debitacc, String creditacc, String execdate, String execFrequency, String execPeriod, String tranAmt, String remark) {
            this.ctx=ctx;
            this.debitacc=debitacc;
            this.creditacc=creditacc;
            this.execdate=execdate;
            this.execFrequency=execFrequency;
            this.execPeriod=execPeriod;
            this.tranAmt=tranAmt;
            this.remark=remark;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StandingInstructionActivity.this);
            pDialog.setMax(45);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();
                JSONObject object=new JSONObject();
                object.put("debitaccno",debitacc);
                object.put("creditaccno",creditacc);
                object.put("firstexecdate",TrustMethods.formatDate(execdate, "dd/MM/yyyy", "yyyy-MM-dd"));
                object.put("execfrequency",execFrequency);
                object.put("execperiod",execPeriod);
                object.put("transferamount",tranAmt);
                object.put("tag","VALIDATE");
                object.put("remark",remark);
                String jsonString=object.toString();

                if (!url.equals("")) {
                    TrustMethods.LogMessage("TAG", "URL:-" + url);
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, actionName,AppConstants.getAuth_token());
                    TrustMethods.LogMessage("TAG", "Frm Enquiry details response-->" + response);
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
                   response = "Form  Successfully Verified.";
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
            super.onPostExecute(value);
            pDialog.dismiss();

            if (!this.error.equals("")) {
                AlertDialogMethod.alertDialogOk(StandingInstructionActivity.this,
                        "Error !!", error, getResources().getString(R.string.btn_ok),
                        0, false, AlertDialogOkListener);
               // Toast.makeText(ctx,error, Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(ctx,response , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StandingInstructionActivity.this, OtpVerificationActivity.class);
                intent.putExtra("checkTransferType", "Standing_Instruction");
                intent.putExtra("debitaccno",debitacc);
                intent.putExtra("creditaccno",creditacc);
                intent.putExtra("firstexecdate",TrustMethods.formatDate(execdate, "dd/MM/yyyy", "yyyy-MM-dd"));
                intent.putExtra("execfrequency",execFrequency);
                intent.putExtra("execperiod",execPeriod);
                intent.putExtra("transferamount",tranAmt);
                intent.putExtra("remark",remark);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                method.activityOpenAnimation();

            }
        }
    }
}