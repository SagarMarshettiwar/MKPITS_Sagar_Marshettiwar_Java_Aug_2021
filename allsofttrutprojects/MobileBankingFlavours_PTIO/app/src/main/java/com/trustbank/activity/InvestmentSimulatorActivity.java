package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvestmentSimulatorActivity extends AppCompatActivity implements View.OnClickListener,AlertDialogOkListener{
    TrustMethods methods;
    private CoordinatorLayout coordinatorLayout;
    Spinner sp_projtype, compfreq_spinner;
    LinearLayout ll_radiogroup,ll_idurmon,ll_intrateannual,ll_invstdate,ll_totalinstvalue,ll_invstvalue,ll_compfreqsp,ll_extintrate;
    private ImageView backButton_new;
    private TextView toolbar,txt_amountinwords, editTextToDate, txt_totalamountinwords;
    Button btn_continue;
    private RadioGroup radioGroup;
    String calMethod, compFreqValue = "0", amountInWords;
    EditText et_investmentvalue, et_invstduration, et_totalinvestmentvalue, et_interestrate, et_extrainterestrate ;
    String calType;
    List<String> compFreqSpinnerValue;
    private AlertDialogOkListener alertDialogOkListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(InvestmentSimulatorActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(InvestmentSimulatorActivity.this, false);
        setContentView(R.layout.activity_investment_simulator);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.InvestmentSimulator);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

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
        methods = new TrustMethods(InvestmentSimulatorActivity.this);
        backButton_new = findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);
        txt_amountinwords=findViewById(R.id.txt_amountinwords);
        sp_projtype=findViewById(R.id.sp_projtype);
        coordinatorLayout=findViewById(R.id.coordinatorLayout);
        ll_radiogroup=findViewById(R.id.ll_radiogroup);
        ll_idurmon=findViewById(R.id.ll_idurmon);
        ll_intrateannual=findViewById(R.id.ll_intrateannual);
        ll_totalinstvalue=findViewById(R.id.ll_totalinstvalue);
        ll_compfreqsp=findViewById(R.id.ll_compfreqsp);
        ll_extintrate=findViewById(R.id.ll_extintrate);
        ll_invstvalue=findViewById(R.id.ll_invstvalue);
        ll_invstdate=findViewById(R.id.ll_invstdate);
        btn_continue=findViewById(R.id.btn_continue);
        et_investmentvalue=findViewById(R.id.et_investmentvalue);
        et_invstduration=findViewById(R.id.et_invstduration);
        et_totalinvestmentvalue=findViewById(R.id.et_totalinvestmentvalue);
        et_interestrate=findViewById(R.id.et_interestrate);
        et_extrainterestrate=findViewById(R.id.et_extrainterestrate);
        compfreq_spinner=findViewById(R.id.compfreq_spinner);
        editTextToDate=findViewById(R.id.editTextToDate);
        txt_totalamountinwords=findViewById(R.id.txt_totalamountinwords);
        editTextToDate.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        radioGroup=findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                String type= (String) radioButton.getText();
                if(type.equalsIgnoreCase(getResources().getString(R.string.Compounding))){
                    calMethod="C";
                    ll_compfreqsp.setVisibility(View.VISIBLE);
                }else{
                    calMethod="S";
                    ll_compfreqsp.setVisibility(View.GONE);
                }
            }
        });

        et_investmentvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                et_investmentvalue.removeTextChangedListener(this);
                try{
                    if(!et_investmentvalue.getText().toString().equals("")) {
                        amountInWords = methods.amountInWords(et_investmentvalue.getText().toString());
                        txt_amountinwords.setText(amountInWords);
                    }else{
                        txt_amountinwords.setText(getResources().getString(R.string.AmountinWords));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                et_investmentvalue.addTextChangedListener(this);
            }
        });

        et_totalinvestmentvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                et_totalinvestmentvalue.removeTextChangedListener(this);
                try{
                    if(!et_totalinvestmentvalue.getText().toString().equals("")) {
                        amountInWords = methods.amountInWords(et_totalinvestmentvalue.getText().toString());
                        txt_totalamountinwords.setText(amountInWords);
                    }else{
                        txt_totalamountinwords.setText(getResources().getString(R.string.AmountinWords));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                et_totalinvestmentvalue.addTextChangedListener(this);
            }
        });
        InitSpinner();
        compFreqSpinner();
    }

    private void compFreqSpinner() {
        List<String> compType=new ArrayList<>();
        compType.add(getResources().getString(R.string.PleaseSelectCompoundingFrequencyType));
        compType.add(getResources().getString(R.string.Monthly));
        compType.add(getResources().getString(R.string.Quaterly));
        compType.add(getResources().getString(R.string.HalfYearly));
        compType.add(getResources().getString(R.string.Yearly));
//        1.Monthly, 2.Quaterly, 3.Half-Yearly, 4.Yearly
        compFreqSpinnerValue = Arrays.asList(getResources().getStringArray(R.array.Compounding_Frequency_Types));
        Log.e("LIST", compFreqSpinnerValue.toString());
        ArrayAdapter ad= new ArrayAdapter(InvestmentSimulatorActivity.this,android.R.layout.simple_spinner_item, compType);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        compfreq_spinner.setAdapter(ad);

        compfreq_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position !=0){
                    compFreqValue = compFreqSpinnerValue.get(position).toString();
                    Log.e("VAlue", compFreqValue);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void InitSpinner() {
        List<String> stringList=new ArrayList<>();
        stringList.add(getResources().getString(R.string.sp_select_Projection_Type));
        stringList.add(getResources().getString(R.string.ExpectedInvestmentValue1));
        stringList.add(getResources().getString(R.string.ExpectedReturnRate1));
        stringList.add(getResources().getString(R.string.StartingInvestmentValue1));
        stringList.add(getResources().getString(R.string.InvestmentDuration1));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(InvestmentSimulatorActivity.this, android.R.layout.simple_spinner_item, stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_projtype.setAdapter(adapter);

        sp_projtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position !=0){
                    String  selectedProjection = (String) parent.getItemAtPosition(position);
                    clear();

                    if(selectedProjection.equalsIgnoreCase(getResources().getString(R.string.ExpectedInvestmentValue1))) {
                        calType = "1";
                        ll_totalinstvalue.setVisibility(View.GONE);
                        txt_totalamountinwords.setVisibility(View.GONE);
                        ll_radiogroup.setVisibility(View.VISIBLE);
                        ll_intrateannual.setVisibility(View.VISIBLE);
                        ll_invstdate.setVisibility(View.VISIBLE);
                        ll_invstvalue.setVisibility(View.VISIBLE);
                        txt_amountinwords.setVisibility(View.VISIBLE);
                        ll_idurmon.setVisibility(View.VISIBLE);
                        ll_extintrate.setVisibility(View.VISIBLE);
                    } else if (selectedProjection.equalsIgnoreCase(getResources().getString(R.string.ExpectedReturnRate1))) {
                        calType = "2";
                        ll_intrateannual.setVisibility(View.VISIBLE);
                        ll_extintrate.setVisibility(View.GONE);
                        ll_invstvalue.setVisibility(View.VISIBLE);
                        txt_amountinwords.setVisibility(View.VISIBLE);
                        ll_invstdate.setVisibility(View.VISIBLE);
                        ll_radiogroup.setVisibility(View.VISIBLE);
                        ll_idurmon.setVisibility(View.VISIBLE);
                        ll_totalinstvalue.setVisibility(View.VISIBLE);
                        txt_totalamountinwords.setVisibility(View.VISIBLE);
                    }else if (selectedProjection.equalsIgnoreCase(getResources().getString(R.string.StartingInvestmentValue1))) {
                        calType = "3";
                        ll_intrateannual.setVisibility(View.VISIBLE);
                        txt_amountinwords.setVisibility(View.GONE);
                        ll_extintrate.setVisibility(View.GONE);
                        ll_invstvalue.setVisibility(View.GONE);
                        ll_radiogroup.setVisibility(View.VISIBLE);
                        ll_invstdate.setVisibility(View.VISIBLE);
                        ll_idurmon.setVisibility(View.VISIBLE);
                        ll_totalinstvalue.setVisibility(View.VISIBLE);
                        txt_totalamountinwords.setVisibility(View.VISIBLE);
                    }else if (selectedProjection.equalsIgnoreCase(getResources().getString(R.string.InvestmentDuration1))) {
                        calType = "4";
                        ll_idurmon.setVisibility(View.GONE);
                        ll_extintrate.setVisibility(View.GONE);
                        ll_invstdate.setVisibility(View.GONE);
                        ll_invstvalue.setVisibility(View.VISIBLE);
                        txt_amountinwords.setVisibility(View.VISIBLE);
                        ll_radiogroup.setVisibility(View.VISIBLE);
                        ll_intrateannual.setVisibility(View.VISIBLE);
                        ll_totalinstvalue.setVisibility(View.VISIBLE);
                        txt_totalamountinwords.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void clear(){
        et_investmentvalue.setText("");
        txt_amountinwords.setText(R.string.AmountinWords);
        editTextToDate.setText("");
        et_invstduration.setText("");
        et_totalinvestmentvalue.setText("");
        txt_totalamountinwords.setText(R.string.AmountinWords);
        et_interestrate.setText("");
        et_extrainterestrate.setText("");
    }
    //back button event
    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.backButton_new:
                for (DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_investment_simulator")) {
                        Intent intent = new Intent(InvestmentSimulatorActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
                Intent intent = new Intent(InvestmentSimulatorActivity.this, InvestmentMenus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_continue:
                String investmentValue = et_investmentvalue.getText().toString();
                String invstduration = et_invstduration.getText().toString();
                String totalInvestmentValue = et_totalinvestmentvalue.getText().toString();
                String interestRate = et_interestrate.getText().toString();
                String extraInterestRate = et_extrainterestrate.getText().toString();
                if(investmentValue.equals("")){
                    investmentValue="0";
                }
                if(invstduration.equals("")){
                    invstduration="0";
                }
                if(totalInvestmentValue.equals("")){
                    totalInvestmentValue="0";
                }
                if(interestRate.equals("")){
                    interestRate="0";
                }
                if(extraInterestRate.equals("")){
                    extraInterestRate="0";
                }

                if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(InvestmentSimulatorActivity.this)) {
                    if (NetworkUtil.getConnectivityStatus(InvestmentSimulatorActivity.this)) {
                        new AsyncTaskCalculate(InvestmentSimulatorActivity.this, calType, calMethod, investmentValue,
                                invstduration, totalInvestmentValue, interestRate, extraInterestRate, compFreqValue).execute();
                    } else {
                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                    }
                } else {
                    TrustMethods.displaySimErrorDialog(InvestmentSimulatorActivity.this);
                }
                break;

            case R.id.editTextToDate :
                methods.datePicker(this,editTextToDate);
                break;
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        if(resultCode == 1){
            Intent intent = new Intent(InvestmentSimulatorActivity.this, InvestmentMenus.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private class AsyncTaskCalculate extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response, result, calType, calMethod, compFreqValue, investmentValue, totalInvestmentValue, invstduration,
                interestRate, extraInterestRate;
        String actionName ="INVESTMENT_CALCULATOR";
        String p_out_interest_amount, p_out_interest_rate, p_out_investment_duration, p_out_investment_value;

        public AsyncTaskCalculate(Context ctx, String calType, String calMethod, String investmentValue, String invstduration,
                                  String totalInvestmentValue, String interestRate, String extraInterestRate, String compFreqValue) {
            this.error = "";
            this.ctx = ctx;
            this.calType = calType;
            this.calMethod = calMethod;
            this.investmentValue = investmentValue;
            this.invstduration = invstduration;
            this.totalInvestmentValue = totalInvestmentValue;
            this.interestRate = interestRate;
            this.extraInterestRate = extraInterestRate;
            this.compFreqValue = compFreqValue;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InvestmentSimulatorActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.investmentCalculator(calType, calMethod, investmentValue, invstduration, totalInvestmentValue, interestRate, extraInterestRate, compFreqValue);
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
                if (responseCode.equals("1")) {
                    JSONObject response = jsonResponse.getJSONObject("response");
                    JSONObject misc = response.getJSONObject("misc");
                    p_out_interest_amount = misc.has("p_out_interest_amount") ? misc.getString("p_out_interest_amount") : "";
                    p_out_interest_rate = misc.has("p_out_interest_rate") ? misc.getString("p_out_interest_rate") : "";
                    p_out_investment_duration = misc.has("p_out_investment_duration") ? misc.getString("p_out_investment_duration") : "";
                    p_out_investment_value = misc.has("p_out_investment_value") ? misc.getString("p_out_investment_value") : "";

                }else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                    error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                }
            }catch (Exception ex) {
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
            }else{
                if(p_out_interest_amount != null && calType.equals("1") && calMethod.equals("S")){
                    AlertDialogMethod.alertDialogOk(InvestmentSimulatorActivity.this, getResources().getString(R.string.ExpectedInvestmentValue1),getResources().getString(R.string.InvestmentValue)+" : " + investmentValue + "\n"+getResources().getString(R.string.InterestRate1)+" : " + (Double.parseDouble(interestRate)+Double.parseDouble(extraInterestRate))+"%" + "\n"+getResources().getString(R.string.InterestAmount)+" : " + p_out_interest_amount + "\n"+getResources().getString(R.string.ExpectedInvestmentValue1)+" : "  + (Double.parseDouble(investmentValue) + Double.parseDouble(p_out_interest_amount)),getResources().getString(R.string.btn_ok),1,false,alertDialogOkListener);
                }else if(p_out_interest_amount != null && calType.equals("1") && calMethod.equals("C")){
                    AlertDialogMethod.alertDialogOk(InvestmentSimulatorActivity.this, getResources().getString(R.string.ExpectedInvestmentValue1),getResources().getString(R.string.InvestmentValue)+" : " + investmentValue + "\n"+getResources().getString(R.string.InterestRate1)+" : " + (Double.parseDouble(interestRate)+Double.parseDouble(extraInterestRate))+"%" + "\n"+getResources().getString(R.string.InterestAmount)+" : " + p_out_interest_amount + "\n"+getResources().getString(R.string.ExpectedInvestmentValue1)+" : " + (Double.parseDouble(investmentValue) + Double.parseDouble(p_out_interest_amount)),getResources().getString(R.string.btn_ok),1,false,alertDialogOkListener);
                }

                if(p_out_interest_rate != null && calType.equals("2") && calMethod.equals("S")){
                    AlertDialogMethod.alertDialogOk(InvestmentSimulatorActivity.this, getResources().getString(R.string.ExpectedReturnRate1),getResources().getString(R.string.InitialInvestment)+" : " + investmentValue + "\n"+getResources().getString(R.string.ExpectedValueonInvestment)+" : " + totalInvestmentValue + "\n"+getResources().getString(R.string.InvestmentDurationinDays)+" : " + invstduration + "\n"+getResources().getString(R.string.ExpectedReturnRate)+" : " + p_out_interest_rate+"%","OK",1,false,alertDialogOkListener);
                }else if(p_out_interest_rate != null && calType.equals("2") && calMethod.equals("C")){
                    AlertDialogMethod.alertDialogOk(InvestmentSimulatorActivity.this, getResources().getString(R.string.ExpectedReturnRate1),getResources().getString(R.string.InitialInvestment)+" : " + investmentValue + "\n"+getResources().getString(R.string.ExpectedValueonInvestment)+" : " + totalInvestmentValue + "\n"+getResources().getString(R.string.InvestmentDurationinDays)+" : " + invstduration + "\n"+getResources().getString(R.string.ExpectedReturnRate)+"  : " + p_out_interest_rate+"%","OK",1,false,alertDialogOkListener);
                }

                if(p_out_investment_duration != null && calType.equals("4") && calMethod.equals("S")){
                    AlertDialogMethod.alertDialogOk(InvestmentSimulatorActivity.this, getResources().getString(R.string.InvestmentDuration1),getResources().getString(R.string.InvestmentValue)+" : " + investmentValue + "\n"+getResources().getString(R.string.InterestRate1)+" : " + interestRate+"%" + "\n"+getResources().getString(R.string.ExpectedValueonInvestment)+" : " + totalInvestmentValue + "\n"+getResources().getString(R.string.InvestmentDurationYears)+" : " + p_out_investment_duration,"OK",1,false,alertDialogOkListener);
                }else if(p_out_investment_duration != null && calType.equals("4") && calMethod.equals("C")){
                    AlertDialogMethod.alertDialogOk(InvestmentSimulatorActivity.this, getResources().getString(R.string.InvestmentDuration1),getResources().getString(R.string.InvestmentValue)+" : " + investmentValue + "\n"+getResources().getString(R.string.InterestRate1)+" : " + interestRate+"%" + "\n"+getResources().getString(R.string.ExpectedValueonInvestment)+" : " + totalInvestmentValue + "\n"+getResources().getString(R.string.InvestmentDurationYears)+" : " + p_out_investment_duration,"OK",1,false,alertDialogOkListener);
                }

                if(p_out_investment_value != null && calType.equals("3") && calMethod.equals("S")){
                    AlertDialogMethod.alertDialogOk(InvestmentSimulatorActivity.this, getResources().getString(R.string.StartingInvestmentValue1),getResources().getString(R.string.InterestRate1)+" : " + interestRate+"%" + "\n"+getResources().getString(R.string.ExpectedValueonInvestment)+" : " + totalInvestmentValue + "\n"+getResources().getString(R.string.InvestmentDurationinDays)+" : " + invstduration + "\n"+getResources().getString(R.string.StartingInvestmentValue1)+" : " + p_out_investment_value,"OK",1,false,alertDialogOkListener);
                }else if(p_out_investment_value != null && calType.equals("3") && calMethod.equals("C")){
                    AlertDialogMethod.alertDialogOk(InvestmentSimulatorActivity.this, getResources().getString(R.string.StartingInvestmentValue1),getResources().getString(R.string.InterestRate1)+" : " + interestRate+"%" + "\n"+getResources().getString(R.string.ExpectedValueonInvestment)+" : " + totalInvestmentValue + "\n"+getResources().getString(R.string.InvestmentDurationinDays)+" : " + invstduration + "\n"+getResources().getString(R.string.StartingInvestmentValue1)+" : " + p_out_investment_value,"OK",1,false,alertDialogOkListener);
                }
            }
        }
    }
}