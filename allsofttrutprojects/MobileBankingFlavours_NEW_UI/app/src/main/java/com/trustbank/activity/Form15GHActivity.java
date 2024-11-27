package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Form15GHActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv_custid,tv_dob,tv_pan,tv_TDSExemptonToDate,tv_TDSExemptonFromDate,tv_custname,tv_uid,tv_TotalNo15h,tv_radioGroup15H,tv_EstimatedTotalIncome,tv_AggregateAmount;
    Spinner exemptiontypespinner,assessmentyearspinner;
    RadioGroup radioGroupassessed,radioGroup15H;
    LinearLayout exemptiontypeLL,letterLL,ll_15hg,Exemption_ll,ll_assessmentyear,ll_TotalNo15h,ll_AggregateAmount,ll_mainExemptionlayout,ll_custdet;
    EditText et_TotalNo15h,et_EstimatedTotalIncome,et_AggregateAmount,et_TdsRate,et_incomecertificate,et_TdsExemptionRatelimit,et_otherincmesource;
    List<String> ecemptiontype =new ArrayList<>();
    List<String> assessmentyear =new ArrayList<>();
    String selectedType,selectedassyear;
    int AssessedToIT1961=0;
    Button btn_Submit,btn_confirm;
    TrustMethods trustMethods;
    CheckBox ck_declarationpan;
    int FormFiledInPY=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    TrustMethods.naviagteToSplashScreen(Form15GHActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(Form15GHActivity.this, false);
        setContentView(R.layout.activity_form15_ghactivity);
        inIt();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }

    private void inIt() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        trustMethods =new TrustMethods(Form15GHActivity.this);
        tv_custid=findViewById(R.id.tv_custid);
        tv_custname=findViewById(R.id.tv_custname);
        tv_uid=findViewById(R.id.tv_uid);
        exemptiontypespinner=findViewById(R.id.exemptiontypespinner);
        assessmentyearspinner=findViewById(R.id.assessmentyearspinner);
        radioGroupassessed=findViewById(R.id.radioGroupassessed);
        radioGroup15H=findViewById(R.id.radioGroup15H);
        et_TotalNo15h=findViewById(R.id.et_TotalNo15h);
        et_EstimatedTotalIncome=findViewById(R.id.et_EstimatedTotalIncome);
        et_AggregateAmount=findViewById(R.id.et_AggregateAmount);
        exemptiontypeLL=findViewById(R.id.exemptiontypeLL);
        tv_TotalNo15h=findViewById(R.id.tv_TotalNo15h);
        tv_radioGroup15H=findViewById(R.id.tv_radioGroup15H);
        tv_EstimatedTotalIncome=findViewById(R.id.tv_EstimatedTotalIncome);
        tv_AggregateAmount=findViewById(R.id.tv_AggregateAmount);
        ck_declarationpan=findViewById(R.id.ck_declarationpan);
        ll_custdet=findViewById(R.id.ll_custdet);
        letterLL=findViewById(R.id.letterLL);
        ll_15hg=findViewById(R.id.ll_15hg);
        tv_pan=findViewById(R.id.tv_pan);
        tv_dob=findViewById(R.id.tv_dob);
        Exemption_ll=findViewById(R.id.Exemption_ll);
        btn_Submit=findViewById(R.id.btn_Submit);
        tv_TDSExemptonToDate=findViewById(R.id.tv_TDSExemptonToDate);
        tv_TDSExemptonFromDate=findViewById(R.id.tv_TDSExemptonFromDate);
        et_TdsRate=findViewById(R.id.et_TdsRate);
        ll_assessmentyear=findViewById(R.id.ll_assessmentyear);
        et_TdsExemptionRatelimit=findViewById(R.id.et_TdsExemptionRatelimit);
        et_otherincmesource=findViewById(R.id.et_otherincmesource);
        et_incomecertificate=findViewById(R.id.et_incomecertificate);
        ll_TotalNo15h=findViewById(R.id.ll_TotalNo15h);
        ll_AggregateAmount=findViewById(R.id.ll_AggregateAmount);
        ll_mainExemptionlayout=findViewById(R.id.ll_mainExemptionlayout);
        btn_confirm=findViewById(R.id.btn_confirm);
        tv_TDSExemptonToDate.setOnClickListener(this);
        tv_TDSExemptonFromDate.setOnClickListener(this);
        ll_assessmentyear.setVisibility(View.GONE);
        ll_TotalNo15h.setVisibility(View.GONE);
        ll_AggregateAmount.setVisibility(View.GONE);
        ll_mainExemptionlayout.setVisibility(View.GONE);
        ll_custdet.setVisibility(View.VISIBLE);

        tv_custid.setText(AppConstants.getCLIENTID());
        tv_custname.setText(AppConstants.getBankcustname());

        StringBuilder maskedCardNo= new StringBuilder(AppConstants.getBankcustPAN());
        for (int i = 3; i < maskedCardNo.length()-2; i++) {
            maskedCardNo.setCharAt(i, 'X');
        }
        tv_pan.setText(maskedCardNo);

        String[] parts = AppConstants.getBankcustDob().split(" ");
        List<String> filteredList = new ArrayList<>();
        for (String part : parts) {
            if (part.trim().length() > 0) {
                filteredList.add(part.trim());
            }
        }

        String dob = filteredList.get(0) +"-" +filteredList.get(1)+ "-" + filteredList.get(2);
        dob = dob.replaceAll("-(\\d)(?=-)", "-0$1");
        tv_dob.setText(dob);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy");
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        //System.out.println("Age: " + age.getYears() + " years, " + age.getMonths() + " months, and " + age.getDays() + " days.");
        int clientage=age.getYears();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ck_declarationpan.isChecked()){
                    Toast.makeText(Form15GHActivity.this, "Please Check the Checkbox", Toast.LENGTH_SHORT).show();
                    ll_mainExemptionlayout.setVisibility(View.GONE);
                }else{
                    ll_custdet.setVisibility(View.GONE);
                    ll_mainExemptionlayout.setVisibility(View.VISIBLE);
                    if(clientage <= 60){
                        exemptiontypespinner.setSelection(1);
                        exemptiontypespinner.setEnabled(false);
                    }else{
                        exemptiontypespinner.setSelection(2);
                        exemptiontypespinner.setEnabled(false);
                    }
                }
            }
        });
        radioGroupassessed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton.getText().equals("Yes")){
                    AssessedToIT1961=1;
                    ll_assessmentyear.setVisibility(View.VISIBLE);
                }else{
                    AssessedToIT1961=0;
                    ll_assessmentyear.setVisibility(View.GONE);
                }
            }
        });

        radioGroup15H.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton.getText().equals("Yes")){
                    FormFiledInPY=1;
                    ll_TotalNo15h.setVisibility(View.VISIBLE);
                    ll_AggregateAmount.setVisibility(View.VISIBLE);
                }else{
                    FormFiledInPY=0;
                    ll_TotalNo15h.setVisibility(View.GONE);
                    ll_AggregateAmount.setVisibility(View.GONE);
                }
            }
        });

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(Form15GHActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(Form15GHActivity.this)) {
                new loaddataform15ghAsyncTask(Form15GHActivity.this,"L").execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(Form15GHActivity.this);
        }
        exemptiontypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        Exemption_ll.setVisibility(View.VISIBLE);
                        selectedType = (String) adapterView.getItemAtPosition(position);

                        if(selectedType.contains("15G")){
                            tv_TotalNo15h.setText("Total No. of Form No 15G");
                            tv_radioGroup15H.setText("Is Form No.15G is filed in p.y.");
                            tv_EstimatedTotalIncome.setText("Estimated Total Income of the P.Y in which income mention in column 16 to be included(6)");
                            tv_AggregateAmount.setText("Aggregate Amount of Form No. 15G");
                            letterLL.setVisibility(View.GONE);
                            ll_15hg.setVisibility(View.VISIBLE);
                            new loaddataUIDAsyncTask(Form15GHActivity.this,selectedType,"U").execute();
                        }else if(selectedType.contains("15H")){
                            tv_TotalNo15h.setText("Total No. of Form No 15H");
                            tv_radioGroup15H.setText("Is Form No.15H is filed in p.y.");
                            tv_EstimatedTotalIncome.setText("Estimated Total Income of the P.Y in which income mention in column 15 to be included(5)");
                            tv_AggregateAmount.setText("Aggregate Amount of Form No. 15H");
                            letterLL.setVisibility(View.GONE);
                            ll_15hg.setVisibility(View.VISIBLE);
                            new loaddataUIDAsyncTask(Form15GHActivity.this,selectedType,"U").execute();
                        }else{
                            letterLL.setVisibility(View.VISIBLE);
                            ll_15hg.setVisibility(View.GONE);
                        }
                    }else{
                        Exemption_ll.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        /*assessmentyearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        selectedassyear = (String) adapterView.getItemAtPosition(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String assemyear="";
                String exemptiontype=selectedType;
                String UIDno=tv_uid.getText().toString();
                if(!assessmentyearspinner.getSelectedItem().toString().equalsIgnoreCase("Select Assessment Year")){
                    assemyear=assessmentyearspinner.getSelectedItem().toString();
                }
                String totalform=et_TotalNo15h.getText().toString();
                int totalincome= Integer.parseInt(et_EstimatedTotalIncome.getText().toString());
                String aggrigateamt=et_AggregateAmount.getText().toString();
                String tdsrate=et_TdsRate.getText().toString();
                String tdsexemptionlimit=et_TdsExemptionRatelimit.getText().toString();
                String incomeothersource=et_otherincmesource.getText().toString();
                String certifnumber=et_incomecertificate.getText().toString();
                String fromdate=tv_TDSExemptonFromDate.getText().toString();
                String todate=tv_TDSExemptonToDate.getText().toString();

                new FormSubmitAsyncTask(Form15GHActivity.this, exemptiontype,UIDno,assemyear,totalform,totalincome,
                        aggrigateamt,tdsrate,tdsexemptionlimit,incomeothersource,certifnumber,fromdate,todate).execute();

            }
        });
    }
    private class FormSubmitAsyncTask extends AsyncTask<Void, Integer, String> {
        String error = "";
        Context ctx;
        String response;
        String uiDno,exemptiontype,assemyear,totalform,aggrigateamt,tdsrate,
                tdsexemptionlimit,incomeothersource,certifnumber,fromdate,todate;
        String actionName = "SUBMIT_Form15GH";
        String result;
        int totalincome;
        public ProgressDialog pDialog;
        private Handler handler;

        public FormSubmitAsyncTask(Context ctx, String exemptiontype, String uiDno, String assemyear, String totalform, int totalincome, String aggrigateamt, String tdsrate, String tdsexemptionlimit, String incomeothersource, String certifnumber, String fromdate, String todate) {
            this.ctx=ctx;
            this.uiDno=uiDno;
            this.exemptiontype=exemptiontype;
            this.assemyear=assemyear;
            this.totalform=totalform;
            this.totalincome=totalincome;
            this.aggrigateamt=aggrigateamt;
            this.tdsrate=tdsrate;
            this.tdsexemptionlimit=tdsexemptionlimit;
            this.incomeothersource=incomeothersource;
            this.certifnumber=certifnumber;
            this.fromdate=fromdate;
            this.todate=todate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Form15GHActivity.this);
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
                object.put("client_id",AppConstants.getCLIENTID());
                object.put("exemptiontype",exemptiontype);
                object.put("IsAssessedToIT1961",AssessedToIT1961);
                object.put("IsFormFiledInPY",FormFiledInPY);
                object.put("UIDNumber",uiDno);
                object.put("AssessmentYear",assemyear);
                object.put("TotalFormFiledInPY",totalform);
                object.put("TotalIncome",totalincome);
                object.put("AggAmtofFormFiledInPY",aggrigateamt);
                object.put("TDSRate",tdsrate);
                object.put("TdsExceptionLimit",tdsexemptionlimit);
                object.put("incomeOthSource",incomeothersource);
                object.put("CertificateNo",certifnumber);
                object.put("TdsExceptionFromDate",TrustMethods.formatDate(fromdate, "dd/MM/yyyy", "yyyy-MM-dd"));
                object.put("TdsExceptionToDate",TrustMethods.formatDate(todate, "dd/MM/yyyy", "yyyy-MM-dd"));
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
                    response = "Form  Successfully Submitted.";
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
                Toast.makeText(ctx,error, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx,response , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tv_TDSExemptonFromDate:
                    trustMethods.datePickerDisableFuturesDate(Form15GHActivity.this, tv_TDSExemptonFromDate);
                    break;
                case R.id.tv_TDSExemptonToDate:
                    trustMethods.datePickerDisableFuturesDate(Form15GHActivity.this, tv_TDSExemptonToDate);
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class loaddataform15ghAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "LOAD_Form15GH";
        private String errorCode;
        String result;
        String type;

        public loaddataform15ghAsyncTask(Context ctx, String type) {
            this.ctx = ctx;
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Form15GHActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.loaddataform15gh(AppConstants.getCLIENTID(),"",type);

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
                    JSONArray JsonArray = jsonResponse.getJSONObject("response").getJSONArray("Table");
                    ecemptiontype =new ArrayList<>();
                    ecemptiontype.add("Select Exemption Type");
                    for(int i=0 ;i<JsonArray.length();i++){
                        JSONObject JsonObject = JsonArray.getJSONObject(i);
                        String name = JsonObject.has("NAME") ? JsonObject.getString("NAME") : "";
                        ecemptiontype.add(name);
                    }

                    JSONArray JsonArrayAy = jsonResponse.getJSONObject("response").getJSONArray("Table1");
                    assessmentyear=new ArrayList<>();
                    assessmentyear.add("Select Assessment Year");
                    for(int j=0 ;j<JsonArray.length();j++){
                        JSONObject AyJsonObject = JsonArrayAy.getJSONObject(j);
                        String Ay = AyJsonObject.has("AY") ? AyJsonObject.getString("AY") : "";
                        assessmentyear.add(Ay);
                    }

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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Form15GHActivity.this, android.R.layout.simple_spinner_item, ecemptiontype);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                exemptiontypespinner.setAdapter(adapter);

                ArrayAdapter<String> adapterass = new ArrayAdapter<>(Form15GHActivity.this, android.R.layout.simple_spinner_item, assessmentyear);
                adapterass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                assessmentyearspinner.setAdapter(adapterass);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class loaddataUIDAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "LOAD_Form15GH";
        private String errorCode;
        String result,uid;
        String type,Exemptionname;

        public loaddataUIDAsyncTask(Context ctx,String Exemptionname,String type) {
            this.ctx = ctx;
            this.type = type;
            this.Exemptionname = Exemptionname;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Form15GHActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.loaddataform15gh(AppConstants.getCLIENTID(),Exemptionname,type);

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
                    JSONArray JsonArray = jsonResponse.getJSONObject("response").getJSONArray("Table");
                    JSONObject JsonObject = JsonArray.getJSONObject(0);
                    uid = JsonObject.has("Column3") ? JsonObject.getString("Column3") : "";
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
                tv_uid.setText(uid);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                Intent intent = new Intent(Form15GHActivity.this, ServiceRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}