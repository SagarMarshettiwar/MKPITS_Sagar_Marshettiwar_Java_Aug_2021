package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.CrediCalModel;
import com.trustbank.R;
import com.trustbank.helper.LocaleHelper;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CreditSimulatorActivity extends AppCompatActivity implements View.OnClickListener {
    TrustMethods methods;
    public int mYear;
    public int mMonth;
    public int mDay;
    public String months;
    public String calenderlang;
    private ImageView backButton_new;
    private TextView toolbar, txt_amountinwords, txt_credit_date, et_expiry_date, txt_insurance, et_due_date;
    private EditText sp_repayment_day,et_credit_amount, et_credit_period_yrs, et_credit_period_months, et_insurance_rate, et_intrst_cal, et_intrst_rate, et_grace_period;
    private Spinner sp_product_name, sp_installment_freq, sp_chart_type;
    private Button btn_cal;
    String old_expdate,old_expdate2,old_expdate3;
    private String amountInWords, installemntID, chartId, loanperiod,insurance="0";
    List<String> installmentFreqlist, chartTypeList;
    HashMap<String,String> installmentFreqMap, chartTypeMap,prodctid;
    String Scheamid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(CreditSimulatorActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(CreditSimulatorActivity.this, false);
        setContentView(R.layout.activity_credit_simulator);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.CreditSimulator);

        inIt();
        setDate();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }
    private void setDate() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            txt_credit_date.setText(formatter.format(date));
            new AsyncTaskGetInstalDate(CreditSimulatorActivity.this,txt_credit_date.getText().toString(),"","").execute();
        }
    }
    private void inIt() {
        methods = new TrustMethods(CreditSimulatorActivity.this);
        backButton_new = findViewById(R.id.backButton_new);
        txt_amountinwords = findViewById(R.id.txt_amountinwords);
        txt_credit_date = findViewById(R.id.txt_credit_date);
        et_expiry_date = findViewById(R.id.et_expiry_date);
        txt_insurance = findViewById(R.id.txt_insurance);
        et_due_date = findViewById(R.id.et_due_date);
        et_credit_amount = findViewById(R.id.et_credit_amount);
        et_credit_period_yrs = findViewById(R.id.et_credit_period_yrs);
        et_credit_period_months = findViewById(R.id.et_credit_period_months);
        et_insurance_rate = findViewById(R.id.et_insurance_rate);
        et_intrst_cal = findViewById(R.id.et_intrst_cal);
        et_intrst_rate = findViewById(R.id.et_intrst_rate);
        et_grace_period = findViewById(R.id.et_grace_period);
        sp_product_name = findViewById(R.id.sp_product_name);
        sp_repayment_day = findViewById(R.id.sp_repayment_day);
        sp_installment_freq = findViewById(R.id.sp_installment_freq);
        sp_chart_type = findViewById(R.id.sp_chart_type);
        btn_cal = findViewById(R.id.btn_cal);
        backButton_new.setOnClickListener(this);

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(CreditSimulatorActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(CreditSimulatorActivity.this)) {
                new AsyncTaskGetSavinglookups(CreditSimulatorActivity.this).execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(CreditSimulatorActivity.this);
        }


        et_credit_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                et_credit_amount.removeTextChangedListener(this);
                try{
                    if(!et_credit_amount.getText().toString().equals("")) {

                        amountInWords = methods.amountInWords(et_credit_amount.getText().toString());
                        txt_amountinwords.setText(amountInWords);
                    }else{
                        txt_amountinwords.setText(getResources().getString(R.string.AmountinWords));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                et_credit_amount.addTextChangedListener(this);
            }
        });

        et_credit_period_yrs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if(!et_credit_period_yrs.getText().toString().equals("")){
                        String dt = txt_credit_date.getText().toString();  // Start date
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Calendar c = Calendar.getInstance();
                        c.setTime(sdf.parse(dt));
                        c.add(Calendar.YEAR, Integer.parseInt(editable.toString()));  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                        et_expiry_date.setText(sdf1.format(c.getTime()));
                        old_expdate = et_expiry_date.getText().toString();
                    }else{
                        et_expiry_date.setText("");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        et_credit_period_months.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if(!et_credit_period_months.getText().toString().equals("")){
                        String dt = et_expiry_date.getText().toString();  // Start date
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Calendar c = Calendar.getInstance();
                        c.setTime(sdf.parse(dt));
                        c.add(Calendar.MONTH, Integer.parseInt(editable.toString()));  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                        et_expiry_date.setText(sdf1.format(c.getTime()));
                        old_expdate2=et_expiry_date.getText().toString();
                    }else{
                        et_expiry_date.setText(old_expdate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        txt_credit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang = LocaleHelper.getPersistedData(CreditSimulatorActivity.this, "en");
                LocaleHelper.setLocale(CreditSimulatorActivity.this, lang);

                if(lang.equalsIgnoreCase("sp")){
                    calenderlang="es";
                }else{
                    calenderlang="en";
                }
                Locale locale = new Locale(calenderlang);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                /*  Context context = this; // Use the activity context here*/
               /* Resources resources = CreditSimulatorActivity.this.getResources();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                resources.updateConfiguration(config, displayMetrics);*/

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreditSimulatorActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDay = dayOfMonth;

                    switch (mMonth) {
                        case 0:
                            months = "Jan";
                            break;
                        case 1:
                            months = "Feb";
                            break;
                        case 2:
                            months = "Mar";
                            break;
                        case 3:
                            months = "Apr";
                            break;
                        case 4:
                            months = "May";
                            break;
                        case 5:
                            months = "Jun";
                            break;
                        case 6:
                            months = "Jul";
                            break;
                        case 7:
                            months = "Aug";
                            break;
                        case 8:
                            months = "Sep";
                            break;
                        case 9:
                            months = "Oct";
                            break;
                        case 10:
                            months = "Nov";
                            break;
                        case 11:
                            months = "Dec";
                            break;
                        default:
                            break;
                    }
                    if (view.isShown()) {
                        String day = String.valueOf(mDay).trim();
                        String month = String.valueOf(mMonth).trim();
                        if (day.length() == 1) {
                            day = "0" + day;
                        }
                        if (month.length() == 1) {
                            month = "0" + month;
                        }
                        txt_credit_date.setText(month + "/" + day + "/" + mYear);
                        sp_product_name.setSelection(0);
                        et_intrst_rate.setText("");
                        new AsyncTaskGetInstalDate(CreditSimulatorActivity.this,txt_credit_date.getText().toString(),"","").execute();

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String creditAmount=et_credit_amount.getText().toString();
                String creditDate=txt_credit_date.getText().toString();
                String expiry_date=et_expiry_date.getText().toString();
                String insurance_rate=et_insurance_rate.getText().toString();
                String intrst_cal=et_intrst_cal.getText().toString();
                String intrst_rate=et_intrst_rate.getText().toString();
                String due_date=et_due_date.getText().toString();
                Intent i=new Intent(CreditSimulatorActivity.this,DisplayAmortizationChartActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("installemntID",installemntID);
                i.putExtra("chartId", chartId);
                i.putExtra("expiry_date", expiry_date);
                i.putExtra("due_date", due_date);
                i.putExtra("creditDate", creditDate);
                i.putExtra("creditAmount", creditAmount);
                i.putExtra("intrst_rate", intrst_rate);
                i.putExtra("loanperiod", loanperiod);
                i.putExtra("insurance", insurance);
                i.putExtra("insurance_rate", insurance_rate);
                i.putExtra("intrst_cal", intrst_cal);
                startActivity(i);
            }
        });
    }
    private class AsyncTaskGetProduct extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        TrustMethods methods;
        JSONArray data;
        String result;
        String actionName ="GET_CREDIT_PRODUCTS";
        List<CrediCalModel> crediCalModelList;

        public AsyncTaskGetProduct(Context ctx) {
            this.error = "";
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getProduct("81","IsInsurance,InsuranceRate,IntBaseDays");
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

                    if (data != null && data.length() > 0) {
                        crediCalModelList=new ArrayList<>();
                        prodctid=new HashMap<>();
                        for (int i=0; i< data.length(); i++){
                            JSONObject jsonObj = data.getJSONObject(i);
                            String schemeid = jsonObj.has("schemeid") ? jsonObj.getString("schemeid"):"";
                            String schemename = jsonObj.has("schemename") ? jsonObj.getString("schemename"):"";
                            String IsInsurance = jsonObj.has("IsInsurance") ? jsonObj.getString("IsInsurance"):"";
                            String IntBaseDays = jsonObj.has("IntBaseDays") ? jsonObj.getString("IntBaseDays"):"";
                            String InsuranceRate = jsonObj.has("InsuranceRate") ? jsonObj.getString("InsuranceRate"):"";

                            prodctid.put(schemename,schemeid);
                            CrediCalModel model = new CrediCalModel();
                            model.setSchemename(schemename);
                            model.setInsuranceRate(InsuranceRate);
                            model.setIsInsurance(IsInsurance);
                            model.setIntBaseDays(IntBaseDays);
                            crediCalModelList.add(model);
                        }
                    }else {
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
            }
            List<String>stringList=new ArrayList<>();
            stringList.add(0,getResources().getString(R.string.SelectProduct));
            for(int j=0;j<crediCalModelList.size();j++){
                stringList.add(crediCalModelList.get(j).getSchemename());
            }
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, stringList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_product_name.setAdapter(adapter1);
            sp_product_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=0){
                        String selectedproduct = (String) parent.getItemAtPosition(position);
                        Scheamid=prodctid.get(selectedproduct);
                        String InsuranceRate = crediCalModelList.get(position-1).getInsuranceRate();
                        String IsInsurance = crediCalModelList.get(position-1).getIsInsurance();
                        String IntBaseDays = crediCalModelList.get(position-1).getIntBaseDays();

                        if(IsInsurance.equalsIgnoreCase("true")){
                            txt_insurance.setText("Yes");
                            insurance="1";
                        }else{
                            txt_insurance.setText("No");
                            insurance="0";
                        }
                        et_insurance_rate.setText(InsuranceRate);
                        et_intrst_cal.setText(IntBaseDays);

                        if (TextUtils.isEmpty(et_credit_period_yrs.getText().toString())) {
                            et_credit_period_yrs.setError(getResources().getString(R.string.pleaseentercredityear));
                        }else if(TextUtils.isEmpty(et_credit_period_months.getText().toString())){
                            et_credit_period_months.setError(getResources().getString(R.string.pleaseentercreditmonth));
                        } else if (TextUtils.isEmpty(et_credit_amount.getText().toString())){
                            et_credit_amount.setError(getResources().getString(R.string.pleaseenteramount));
                        }else{
                            loanperiod= String.valueOf((Integer.parseInt(et_credit_period_yrs.getText().toString()) * 12));
                            loanperiod= String.valueOf((Integer.parseInt(et_credit_period_months.getText().toString()) + Integer.parseInt(loanperiod)));
                            txt_insurance.setEnabled(false);
                            et_insurance_rate.setEnabled(false);
                            et_intrst_cal.setEnabled(false);

                            new AsyncTaskGetInterestRate(CreditSimulatorActivity.this,Scheamid,txt_credit_date.getText().toString(),et_credit_amount.getText().toString(),loanperiod).execute();
                        }
                    }else{
                        txt_insurance.setText("");
                        et_insurance_rate.setText("");
                        et_intrst_cal.setText("");
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    private class AsyncTaskGetSavinglookups extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        TrustMethods methods;
        JSONArray data;
        JSONArray installmentFreq, chartType;
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
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
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
                    data = jsonResponse.getJSONObject("response").getJSONArray("data");
                    if (data.length() == 0) {
                        error = AppConstants.NO_RECORDS_FOUND;
                        return error;
                    }
                    if (data != null && data.length() > 0) {
                        //Chart Types
                        chartType = jsonResponse.getJSONObject("response").getJSONArray("Table8");
                        chartTypeList = new ArrayList<>();
                        chartTypeMap= new HashMap<>();
                        chartTypeList.add(0,getResources().getString(R.string.Select));
                        if (chartType != null && chartType.length() > 0) {
                            for (int i = 0; i < chartType.length(); i++) {
                                JSONObject JsonObject = chartType.getJSONObject(i);
                                String installmentID = JsonObject.has("valueid") ? JsonObject.getString("valueid") : "NA";
                                String installmentName = JsonObject.has("typevalue") ? JsonObject.getString("typevalue") : "NA";
                                chartTypeList.add(installmentName);
                                chartTypeMap.put(installmentName, installmentID);
                            }
                        }

                        //Installment Freq
                        installmentFreq = jsonResponse.getJSONObject("response").getJSONArray("Table9");
                        installmentFreqlist = new ArrayList<>();
                        installmentFreqMap = new HashMap<>();
                        installmentFreqlist.add(0,getResources().getString(R.string.Select));
                        if (installmentFreq != null && installmentFreq.length() > 0) {
                            for (int i = 0; i < installmentFreq.length(); i++) {
                                JSONObject JsonObject = installmentFreq.getJSONObject(i);
                                String installmentID = JsonObject.has("valueid") ? JsonObject.getString("valueid") : "NA";
                                String installmentName = JsonObject.has("typevalue") ? JsonObject.getString("typevalue") : "NA";
                                installmentFreqlist.add(installmentName);
                                installmentFreqMap.put(installmentName, installmentID);
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
            }else {
                //Installment Freq
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, installmentFreqlist);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_installment_freq.setAdapter(adapter);
                sp_installment_freq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            if (position != 0) {
                                String selectedproduct = (String) parent.getItemAtPosition(position);
                                installemntID = installmentFreqMap.get(selectedproduct);
                                if (installemntID.equals("1008")) {
                                    String dt = et_due_date.getText().toString();  // Start date
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(sdf.parse(dt));
                                    c.add(Calendar.MONTH, Integer.parseInt(loanperiod) - 1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                                    et_expiry_date.setText(sdf1.format(c.getTime()));
                                } else if (installemntID.equals("1010")) {
                                    String dt = et_due_date.getText().toString();  // Start date
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(sdf.parse(dt));
                                    c.add(Calendar.MONTH, (Integer.parseInt(loanperiod) * 3) -3);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                                    et_expiry_date.setText(sdf1.format(c.getTime()));
                                } else if (installemntID.equals("1013")) {
                                    String dt = et_due_date.getText().toString();  // Start date
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(sdf.parse(dt));
                                    c.add(Calendar.MONTH, (Integer.parseInt(loanperiod) * 6) -6);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                                    et_expiry_date.setText(sdf1.format(c.getTime()));
                                } else if (installemntID.equals("1019")) {
                                    String dt = et_due_date.getText().toString();  // Start date
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(sdf.parse(dt));
                                    c.add(Calendar.MONTH, (Integer.parseInt(loanperiod) * 12) -12);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                                    et_expiry_date.setText(sdf1.format(c.getTime()));
                                }
                                new AsyncTaskGetInstalDate(CreditSimulatorActivity.this,txt_credit_date.getText().toString(),et_grace_period.getText().toString(),sp_repayment_day.getText().toString()).execute();

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //Chart Type
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, chartTypeList);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_chart_type.setAdapter(adapter1);
                sp_chart_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            String selectedproduct = (String) parent.getItemAtPosition(position);
                            chartId = chartTypeMap.get(selectedproduct);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                new AsyncTaskGetProduct(CreditSimulatorActivity.this).execute();
            }
        }
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(CreditSimulatorActivity.this, BorrowMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private class AsyncTaskGetInstalDate extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response,firstinstdate;
        TrustMethods methods;
        String formattedDate;
        JSONObject data;
        String result,date,s,s1;
        String actionName ="GET_INSTALLMENT_DATE";

        public AsyncTaskGetInstalDate(Context ctx, String date, String s, String s1) {
            this.error = "";
            this.ctx = ctx;
            this.date = date;
            this.s = s;
            this.s1 = s1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getInstallDate(date,s,s1);
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
                    data = jsonResponse.getJSONObject("response").getJSONObject("misc");
                    if (data.length() == 0) {
                        error = AppConstants.NO_RECORDS_FOUND;
                        return error;
                    }
                    if (data != null && data.length() > 0) {
                        firstinstdate=data.getString("p_Out_FirstInstDate");
                        firstinstdate=firstinstdate.replaceAll(" ","/");
                        firstinstdate=firstinstdate.replaceAll("/ ","/0");
                        firstinstdate=firstinstdate.replaceAll("//","/");
                        firstinstdate =firstinstdate.substring(0,firstinstdate.lastIndexOf("/"));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
                        Calendar c = Calendar.getInstance();
                        c.setTime(dateFormat.parse(firstinstdate));
                        formattedDate = dateFormat.format(c.getTime());
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
                        formattedDate = dateFormat1.format(c.getTime());
                    } else {
                        String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            } catch (ParseException e) {
                throw new RuntimeException(e);
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
            }
            et_due_date.setText(formattedDate.toString());
        }
    }

    private class AsyncTaskGetInterestRate extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error,errorobject="";
        Context ctx;
        String response,Imterestrate,scheamid,Date,money,loanperiod;
        TrustMethods methods;
        JSONObject data;
        String result,date,s,s1;
        String actionName ="GET_CREDIT_INTERESTRATE";
        public AsyncTaskGetInterestRate(Context ctx, String scheamid, String Date, String money, String loanperiod) {
            this.ctx=ctx;
            this.scheamid=scheamid;
            this.Date=Date;
            this.money=money;
            this.loanperiod=loanperiod;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getInterestRate(scheamid,Date,money,loanperiod);
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
                    data = jsonResponse.getJSONObject("response").getJSONObject("misc");
                    Imterestrate=data.getString("p_out_InterestRate");
                    errorobject=data.getString("error");
                    if (data.length() == 0) {
                        error = AppConstants.NO_RECORDS_FOUND;
                        return error;
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
            if (this.error != null) {
                methods.message(this.ctx, error);
            }
            if (errorobject != null && !errorobject.equalsIgnoreCase("null")) {
                methods.message(this.ctx, errorobject);
            }
            et_intrst_rate.setText(Imterestrate);
        }
    }
}