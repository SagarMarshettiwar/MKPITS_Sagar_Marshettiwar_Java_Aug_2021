package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.fragment.WelcomeMessageDiagligFragment;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RdFdRecknorActivity extends AppCompatActivity implements AlertDialogOkListener{
    private static final String TAG = RdFdRecknorActivity.class.getSimpleName();
   private TextView textViewDepositType_Id,textViewIntrestCal_Id,textViewPeriodUnit_Id,textViewPeriodCompoundUnit_Id;
   private EditText editTextFdAmmount_Id,editTextFromDate,editTextPeriod_Id,editTextInterestRate_Id,editTextInstallationAmount_Id;
   private Spinner spinnerDepositType_Id,spinnerIntrestCal_Id,spinnerPeriodUnit_Id,spinnerPeriodCompoundUnit_Id;
   private CheckBox checkbox;

    public String mAmount, mDepositDate, mPeriod, mInterestRate, mInstallationAmount, mRepaymentFreq, mCompoundingFreq;
   private Button buttonCancel,buttonFinish;
    public String periodUnit;
   TrustMethods trustMethods;
   private String checkFd;
   private String accountType;
   private LinearLayout linear_compound_spinner,LinerLayoutIntrestCal_id,linear_spinner,ll_enterAmount,ll_installmentamount;
   private RecyclerView recyclerViewHoriListId;
   private List<String> interestCalculationValue;
   private List<String> periodUnitValue;
    public String interestCalMethod;
    public int availablescheme;
    DatePickerDialog datePickerDialog;
    boolean checked;
    AlertDialogOkListener alertDialogOkListener = this;
    String extraInterestRate = "";
    public String showAvailScheme;
   private List<String> periodUnitCompoundValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                if (savedInstanceState != null) {
                    Object currentPID = String.valueOf(android.os.Process.myPid());
                    //Check current PID with old PID
                    if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                        //If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                        TrustMethods.naviagteToSplashScreen(RdFdRecknorActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(RdFdRecknorActivity.this, false);
        setContentView(R.layout.activity_rd_fd_recknor);
        initcomponent();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }
    private void initcomponent() {
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            trustMethods = new TrustMethods(RdFdRecknorActivity.this);
            trustMethods.activityOpenAnimation();
            horizontalRecyclerView();
            textViewDepositType_Id = findViewById(R.id.textViewDepositType_Id);
            spinnerDepositType_Id = findViewById(R.id.spinnerDepositType_Id);
            textViewIntrestCal_Id = findViewById(R.id.textViewIntrestCal_Id);
            spinnerIntrestCal_Id = findViewById(R.id.spinnerIntrestCal_Id);
            checkbox = findViewById(R.id.checkbox);
            editTextFdAmmount_Id = findViewById(R.id.editTextFdAmmount_Id);
            editTextFromDate = findViewById(R.id.editTextFromDate);
            editTextPeriod_Id = findViewById(R.id.editTextPeriod_Id);
            textViewPeriodUnit_Id = findViewById(R.id.textViewPeriodUnit_Id);
            spinnerPeriodUnit_Id = findViewById(R.id.spinnerPeriodUnit_Id);
            textViewPeriodCompoundUnit_Id = findViewById(R.id.textViewPeriodCompoundUnit_Id);
            spinnerPeriodCompoundUnit_Id = findViewById(R.id.spinnerPeriodCompoundUnit_Id);
            editTextInterestRate_Id = findViewById(R.id.editTextInterestRate_Id);
            editTextInstallationAmount_Id = findViewById(R.id.editTextInstallationAmount_Id);
            linear_compound_spinner=findViewById(R.id.linear_compound_spinner);
            LinerLayoutIntrestCal_id=findViewById(R.id.LinerLayoutIntrestCal_id);
            linear_spinner=findViewById(R.id.linear_spinner);
            ll_installmentamount=findViewById(R.id.ll_installmentamount);
            ll_enterAmount=findViewById(R.id.ll_enterAmount);
            buttonCancel = findViewById(R.id.buttonCancel);
            buttonFinish = findViewById(R.id.buttonFinish);
            editTextInterestRate_Id.setHint("Enter Interest Rate");
            setAllSpinner();
            spinnerClickListener();
            TrustMethods.LogMessage(TAG, "showAvailScheme->" + showAvailScheme);
        }catch (Exception e){
            e.printStackTrace();
        }
        editTextFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trustMethods.datePickerymd(RdFdRecknorActivity.this, editTextFromDate);
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = ((CheckBox) v).isChecked();
                if (checked) {
                    editTextInterestRate_Id.setHint("Enter Extra Interest Rate");
                } else {
                    editTextInterestRate_Id.setHint("Enter Interest Rate");
                }
                TrustMethods.LogMessage(TAG, "showAvailScheme->" + showAvailScheme);
            }
        });

        editTextInterestRate_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();

                try {
                    if (input != null) {
                        if (Float.parseFloat(input.trim()) > 100.0f) {
                            editTextInterestRate_Id.setText("");
                            Toast.makeText(getApplicationContext(), "Enter Valid Interest Rate", Toast.LENGTH_SHORT).show();
                        } else {
                            if (input.contains(".") && s.charAt(s.length() - 1) != '.') {
                                if (input.indexOf(".") + 3 <= input.length() - 1) {
                                    String formatted = input.substring(0, input.indexOf(".") + 3);
                                    editTextInterestRate_Id.setText(formatted);
                                    editTextInterestRate_Id.setSelection(formatted.length());
                                }
                            } else if (input.contains(",") && s.charAt(s.length() - 1) != ',') {
                                if (input.indexOf(",") + 3 <= input.length() - 1) {
                                    String formatted = input.substring(0, input.indexOf(",") + 3);
                                    editTextInterestRate_Id.setText(formatted);
                                    editTextInterestRate_Id.setSelection(formatted.length());
                                }
                            }
                        }
                    }
                } catch (NumberFormatException ex) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RdFdRecknorActivity.this, ServiceRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        buttonFinish = (Button) findViewById(R.id.buttonFinish);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAmount = editTextFdAmmount_Id.getText().toString();
                mDepositDate = editTextFromDate.getText().toString();
                mPeriod = editTextPeriod_Id.getText().toString();
                mInterestRate = editTextInterestRate_Id.getText().toString();
                mInstallationAmount = editTextInstallationAmount_Id.getText().toString();
                showAvailScheme = String.valueOf(checked);

                if (checkFd.equals("FD")){
                    mRepaymentFreq = "0";
                    if (validationFD()) {
                        if(NetworkUtil.getConnectivityStatus(Objects.requireNonNull(RdFdRecknorActivity.this))){
                            if (checked) {
                                extraInterestRate = mInterestRate;
                                Intent intent = new Intent(RdFdRecknorActivity.this, FdShowActivity.class);

                                Bundle mBundle = new Bundle();
                                mBundle.putString("accountType", accountType);
                                mBundle.putString("interestCalMethod", interestCalMethod);
                                mBundle.putString("mInstallationAmount", mAmount);
                                mBundle.putString("mDepositeDate", mDepositDate);
                                mBundle.putString("mPeriod", mPeriod);
                                mBundle.putString("periodUnit", periodUnit);
                                mBundle.putString("mIntrestRate", "0");
                                mBundle.putString("extraInterestRate", extraInterestRate);
                                mBundle.putString("mRepaymentFreq", mRepaymentFreq);
                                mBundle.putString("mCompoundingFreq", mCompoundingFreq);
                                mBundle.putString("showAvailScheme", showAvailScheme);
                                intent.putExtras(mBundle);
                                intent.putExtra("checkFd", checkFd);
                                startActivity(intent);

                            }else {
                                extraInterestRate = "0";
                                TrustMethods.LogMessage("mCompoundingFreq", mCompoundingFreq);
                                TrustMethods.LogMessage("periodUnit", periodUnit);

                                new AsyncTaskEnquiryDetailsFD_RD(RdFdRecknorActivity.this, accountType, interestCalMethod, mAmount, mDepositDate, mPeriod, periodUnit, mInterestRate, extraInterestRate, showAvailScheme, mRepaymentFreq, mCompoundingFreq).execute();
                            }
                        }else{
                            AlertDialogMethod.alertDialogOk(RdFdRecknorActivity.this,
                                    "",getResources().getString(R.string.error_check_internet), getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                        }
                    }
                }else{
                    if (validationRD()) {
                        if(NetworkUtil.getConnectivityStatus(Objects.requireNonNull(RdFdRecknorActivity.this))){
                            if (checked) {
                                extraInterestRate = mInterestRate;
                                Intent intent = new Intent(RdFdRecknorActivity.this, FdShowActivity.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putString("accountType", accountType);
                                mBundle.putString("interestCalMethod", interestCalMethod);
                                mBundle.putString("mInstallationAmount", mInstallationAmount);
                                mBundle.putString("mDepositeDate", mDepositDate);
                                mBundle.putString("mPeriod", mPeriod);
                                mBundle.putString("periodUnit", periodUnit);
                                mBundle.putString("mIntrestRate", "0");
                                mBundle.putString("extraInterestRate", extraInterestRate);
                                mBundle.putString("showAvailScheme", showAvailScheme);
                                intent.putExtras(mBundle);
                                intent.putExtra("checkFd", checkFd);
                                startActivity(intent);
                            }else {

                                extraInterestRate = "0";
                                new AsyncTaskEnquiryDetailsFD_RD(RdFdRecknorActivity.this, accountType, interestCalMethod, mInstallationAmount, mDepositDate, mPeriod, periodUnit, mInterestRate, extraInterestRate, showAvailScheme).execute();
                            }
                        }else{
                            AlertDialogMethod.alertDialogOk(RdFdRecknorActivity.this,
                                    "",getResources().getString(R.string.error_check_internet), getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                        }
                    }
                }
            }

        });
    }

    private boolean validationFD() {
        if(TextUtils.isEmpty(editTextFdAmmount_Id.getText().toString().trim())){
            editTextFdAmmount_Id.setError("Required Amount");
            return false;
        }
        else if(TextUtils.isEmpty(editTextFromDate.getText().toString().trim())){
            editTextFromDate.setError("Required Date");
            return false;
        }
        else if(TextUtils.isEmpty(editTextPeriod_Id.getText().toString().trim())){
            editTextPeriod_Id.setError("Required Period");
            return false;
        }
        else if(TextUtils.isEmpty(editTextInterestRate_Id.getText().toString().trim())){
            editTextInterestRate_Id.setError("Required Interest Rate");
            return false;
        }
        return true;
    }

    private boolean validationRD() {
        if(TextUtils.isEmpty(editTextFromDate.getText().toString().trim())){
            editTextFromDate.setError("Required Date");
            return false;
        }
        else if(TextUtils.isEmpty(editTextPeriod_Id.getText().toString().trim())){
            editTextPeriod_Id.setError("Required Period");
            return false;
        }
        else if(TextUtils.isEmpty(editTextInterestRate_Id.getText().toString().trim())){
            editTextInterestRate_Id.setError("Required Interest Rate");
            return false;
        }
        else if(TextUtils.isEmpty(editTextInstallationAmount_Id.getText().toString().trim())){
            editTextInstallationAmount_Id.setError("Required Installment amount");
            return false;
        }
        return true;
    }

    private void setAllSpinner() {
        List<String> depositType=new ArrayList<>();
        depositType.add(0, "Select Deposit Type");
        depositType.add(1, "Fixed Deposit");
        depositType.add(2, "Recurring Deposit");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(RdFdRecknorActivity.this, android.R.layout.simple_spinner_item, depositType);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepositType_Id.setAdapter(spinnerArrayAdapter);

        List<String> calculationmethod=new ArrayList<>();
        calculationmethod.add(0, "Select Calculation method");
        calculationmethod.add(1, "Simple To Principle");
        calculationmethod.add(2, "Simple To Payable");
        calculationmethod.add(3, "Compound To Principal");
        calculationmethod.add(4, "Compound To Payable");
        calculationmethod.add(5, "Recurring Simple To Account");
        calculationmethod.add(6, "Recurring Discounted To Account");
        calculationmethod.add(7, "MONEY Multiplying");

        ArrayAdapter<String> spinnerArrayAdapterIntrest = new ArrayAdapter<String>(RdFdRecknorActivity.this, android.R.layout.simple_spinner_item, calculationmethod);
        spinnerArrayAdapterIntrest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIntrestCal_Id.setAdapter(spinnerArrayAdapterIntrest);

        List<String> Periodtype=new ArrayList<>();
        Periodtype.add(0,"Select Period Type");
        Periodtype.add(1,"Months");
        Periodtype.add(2,"Days");

        ArrayAdapter<String> spinnerArrayAdapterPeriodUnit = new ArrayAdapter<String>(RdFdRecknorActivity.this, android.R.layout.simple_spinner_item, Periodtype);
        spinnerArrayAdapterPeriodUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodUnit_Id.setAdapter(spinnerArrayAdapterPeriodUnit);

        interestCalculationValue = Arrays.asList(getResources().getStringArray(R.array.Interest_Cal_Value));
        periodUnitValue = Arrays.asList(getResources().getStringArray(R.array.Period_Unit_Value));

        periodUnitCompoundValue = Arrays.asList(getResources().getStringArray(R.array.Period_Unit_compound_Value));
        List<String> periodCompoundUnit=new ArrayList<>();
        periodCompoundUnit.add(0,"Select Compounding Frequency");
        periodCompoundUnit.add(1,"Monthly");
        periodCompoundUnit.add(2,"Quarterly");
        periodCompoundUnit.add(3,"Half Yearly");
        periodCompoundUnit.add(4,"Yearly");
        ArrayAdapter<String> spinnerArrayCompoundAdapterPeriodUnit = new ArrayAdapter<String>(RdFdRecknorActivity.this, android.R.layout.simple_spinner_item, periodCompoundUnit);
        spinnerArrayCompoundAdapterPeriodUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodCompoundUnit_Id.setAdapter(spinnerArrayCompoundAdapterPeriodUnit);
    }
    private void spinnerClickListener() {
        spinnerDepositType_Id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    ll_enterAmount.setVisibility(View.VISIBLE);
                    spinnerIntrestCal_Id.setVisibility(View.VISIBLE);
                    LinerLayoutIntrestCal_id.setVisibility(View.VISIBLE);
                    ll_installmentamount.setVisibility(View.GONE);
                    checkFd="FD";
                    accountType="75";

                    List<String> calculationmethod=new ArrayList<>();
                    calculationmethod.add(0, "Select Calculation method");
                    calculationmethod.add(1, "Simple To Principle");
                    calculationmethod.add(2, "Simple To Payable");
                    calculationmethod.add(3, "Compound To Principal");
                    calculationmethod.add(4, "Compound To Payable");
                    calculationmethod.add(5, "Recurring Simple To Account");
                    calculationmethod.add(6, "Recurring Discounted To Account");
                    calculationmethod.add(7, "MONEY Multiplying");

                    ArrayAdapter<String> spinnerArrayAdapterIntrest = new ArrayAdapter<String>(RdFdRecknorActivity.this, android.R.layout.simple_spinner_item, calculationmethod);
                    spinnerArrayAdapterIntrest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIntrestCal_Id.setAdapter(spinnerArrayAdapterIntrest);

                    List<String> Periodtype=new ArrayList<>();
                    Periodtype.add(0,"Select Period Type");
                    Periodtype.add(1,"Months");
                    Periodtype.add(2,"Days");

                    ArrayAdapter<String> spinnerArrayAdapterPeriodUnit = new ArrayAdapter<String>(RdFdRecknorActivity.this, android.R.layout.simple_spinner_item, Periodtype);
                    spinnerArrayAdapterPeriodUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerPeriodUnit_Id.setAdapter(spinnerArrayAdapterPeriodUnit);
                    interestCalculationValue = Arrays.asList(getResources().getStringArray(R.array.Interest_Cal_Value));
                    periodUnitValue = Arrays.asList(getResources().getStringArray(R.array.Period_Unit_Value));
                    spinnerIntrestCal_Id.setSelection(1);
                }else{
                    linear_compound_spinner.setVisibility(View.GONE);
                    spinnerPeriodUnit_Id.setEnabled(true);
                    ll_enterAmount.setVisibility(View.GONE);
                    spinnerIntrestCal_Id.setVisibility(View.VISIBLE);
                    LinerLayoutIntrestCal_id.setVisibility(View.VISIBLE);
                    ll_installmentamount.setVisibility(View.VISIBLE);
                    accountType="77";
                    checkFd="RD";
                    List<String> Periodtype=new ArrayList<>();
                    Periodtype.add(0,"Select Period Type");
                    Periodtype.add(1,"Months");
                    Periodtype.add(2,"Days");

                    ArrayAdapter<String> spinnerArrayAdapterPeriodUnit = new ArrayAdapter<String>(RdFdRecknorActivity.this, android.R.layout.simple_spinner_item, Periodtype);
                    spinnerArrayAdapterPeriodUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerPeriodUnit_Id.setAdapter(spinnerArrayAdapterPeriodUnit);

                    List<String> calculationmethod=new ArrayList<>();
                    calculationmethod.add(0, "Select Calculation method");
                    calculationmethod.add(1, "Simple To Principle");
                    calculationmethod.add(2, "Compound To Principal/Payable");

                    ArrayAdapter<String> spinnerArrayAdapterIntrest = new ArrayAdapter<String>(RdFdRecknorActivity.this, android.R.layout.simple_spinner_item, calculationmethod);
                    spinnerArrayAdapterIntrest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerIntrestCal_Id.setAdapter(spinnerArrayAdapterIntrest);
                    spinnerPeriodUnit_Id.setSelection(1);
                    spinnerIntrestCal_Id.setSelection(1);
                    interestCalculationValue = Arrays.asList(getResources().getStringArray(R.array.calculate_method));
                    periodUnitValue = Arrays.asList(getResources().getStringArray(R.array.Period_Unit_Value_rd_number));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerIntrestCal_Id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interestCalMethod = interestCalculationValue.get(position);
                TrustMethods.LogMessage(TAG, "intrest val" + interestCalMethod);
                if (interestCalMethod.equalsIgnoreCase("273") || interestCalMethod.equalsIgnoreCase("274")) {
                    if (checkFd.equals("FD")) {
                        linear_compound_spinner.setVisibility(View.VISIBLE);
                        spinnerPeriodUnit_Id.setEnabled(false);
                    } else {
                        spinnerPeriodUnit_Id.setEnabled(true);
                    }

                    spinnerPeriodUnit_Id.setSelection(1);
                    spinnerPeriodCompoundUnit_Id.setSelection(1);
                    spinnerPeriodCompoundUnit_Id.setEnabled(true);
                    mCompoundingFreq = "71";
                    textViewPeriodCompoundUnit_Id.setText("Compunding Frequency");

                } else if (interestCalMethod.equalsIgnoreCase("271") || interestCalMethod.equalsIgnoreCase("272")) {
                    spinnerPeriodUnit_Id.setSelection(1);
                    spinnerPeriodUnit_Id.setEnabled(true);
                    linear_compound_spinner.setVisibility(View.GONE);
                    mCompoundingFreq = "0";

                } else if (interestCalMethod.equalsIgnoreCase("276")) {
                    linear_compound_spinner.setVisibility(View.VISIBLE);
                    spinnerPeriodUnit_Id.setSelection(1);
                    spinnerPeriodUnit_Id.setEnabled(false);
                    mCompoundingFreq = "71";
                    periodUnit = "69";
                    spinnerPeriodCompoundUnit_Id.setSelection(1);
                    spinnerPeriodCompoundUnit_Id.setEnabled(false);
                    linear_compound_spinner.setVisibility(View.VISIBLE);
                    textViewPeriodCompoundUnit_Id.setText("Payment Frequency");

                } else if (interestCalMethod.equalsIgnoreCase("277") || interestCalMethod.equalsIgnoreCase("275")) {
                    linear_compound_spinner.setVisibility(View.VISIBLE);
                    spinnerPeriodUnit_Id.setSelection(1);
                    spinnerPeriodUnit_Id.setEnabled(false);
                    spinnerPeriodCompoundUnit_Id.setSelection(1);
                    spinnerPeriodCompoundUnit_Id.setEnabled(true);
                    mCompoundingFreq = "71";
                    textViewPeriodCompoundUnit_Id.setText("Payment Frequency");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerPeriodUnit_Id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCompoundingFreq = "0";
                periodUnit = periodUnitValue.get(position);

                TrustMethods.LogMessage("periodUnit", periodUnit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerPeriodCompoundUnit_Id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (interestCalMethod.equalsIgnoreCase("271") || interestCalMethod.equalsIgnoreCase("272")) {
                    mCompoundingFreq = "0";
                } else {
                    mCompoundingFreq = periodUnitCompoundValue.get(position);
                }
                TrustMethods.LogMessage("periodUnitcompound", mCompoundingFreq);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDialogOk(int resultCode) {
        if(resultCode==1){
            finish();
        }
        if(resultCode==0){
            Intent intent = new Intent(getApplicationContext(), LockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    private class AsyncTaskEnquiryDetailsFD_RD extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        JSONArray data;
        String mAmount, mDepositDate, mPeriod, periodUnit, mInterestRate, exatraIntrestRate, showAvailScheme,mRepaymentFreq, interestCalMethod,mCompoundingFreq,accountType;
        String result;
        String actionName = "GET_FD_RD_RECKNOR";

        public AsyncTaskEnquiryDetailsFD_RD(RdFdRecknorActivity ctx, String accountType,String interestCalMethod, String mAmount, String mDepositDate, String mPeriod, String periodUnit, String mInterestRate, String extraInterestRate, String showAvailScheme, String mRepaymentFreq, String mCompoundingFreq) {
            this.error = "";
            this.ctx = ctx;
            this.accountType = accountType;
            this.mAmount = mAmount;
            this.mDepositDate = mDepositDate;
            this.mPeriod = mPeriod;
            this.periodUnit = periodUnit;
            this.mInterestRate = mInterestRate;
            this.exatraIntrestRate = extraInterestRate;
            this.showAvailScheme = showAvailScheme;
            this.mRepaymentFreq = mRepaymentFreq;
            this.mCompoundingFreq = mCompoundingFreq;
            this.interestCalMethod = interestCalMethod;
        }
        public AsyncTaskEnquiryDetailsFD_RD(RdFdRecknorActivity ctx,String accountType, String interestCalMethod, String mAmount, String mDepositDate, String mPeriod, String periodUnit, String mInterestRate, String extraInterestRate, String showAvailScheme) {
            this.error = "";
            this.ctx = ctx;
            this.accountType = accountType;
            this.mAmount = mAmount;
            this.mDepositDate = mDepositDate;
            this.mPeriod = mPeriod;
            this.periodUnit = periodUnit;
            this.mInterestRate = mInterestRate;
            this.mRepaymentFreq = "0";
            this.mCompoundingFreq = "0";
            this.exatraIntrestRate = extraInterestRate;
            this.showAvailScheme = showAvailScheme;
            this.interestCalMethod = interestCalMethod;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RdFdRecknorActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetRdFDRecknortUrl(accountType,mAmount,mDepositDate,mPeriod,periodUnit,
                        mInterestRate,exatraIntrestRate,showAvailScheme,interestCalMethod,mRepaymentFreq,mCompoundingFreq);
                if (!url.equals("")) {
                    result = HttpClientWrapper.getResponseGET(url, actionName, AppConstants.getAuth_token());
                }
                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));
                Log.e("response288",result);
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
                String interestamount="";
                String maturiyamount="";
                String interestrate="";
                    for(int i=0;i<data.length();i++)
                    {
                        JSONObject dataarray = data.getJSONObject(i);
                        interestamount= dataarray.getString("interest_amount");
                        maturiyamount= dataarray.getString("maturiy_amount");
                        /*interestrate= dataarray.getString("interest_rate");*/
                    }
                AlertDialogMethod.alertDialogOk(RdFdRecknorActivity.this,
                        "",
                        "Interest Amount: " + (new DecimalFormat("###########.00")).format(Float.parseFloat(interestamount))
                                + "\nMaturity Amount: " + (new DecimalFormat("###########.00")).format(Float.parseFloat(maturiyamount))
                                /*+ "\nInterest Rate " + (new DecimalFormat("###########.00")).format(Float.parseFloat(interestrate))*/,
                        getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_rd_fd_reckoner")) {
                        Intent intent = new Intent(RdFdRecknorActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }

                if(AppConstants.account_group){
                    Intent intent = new Intent(RdFdRecknorActivity.this, AccountsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.upi_group) {
                    Intent intent = new Intent(RdFdRecknorActivity.this, UPIActivityMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.service_group) {
                    Intent intent = new Intent(RdFdRecknorActivity.this, ServiceRequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.cards_group) {
                    Intent intent = new Intent(RdFdRecknorActivity.this, Cards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.locate_us_group) {
                    Intent intent = new Intent(RdFdRecknorActivity.this, LocateUs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }else if (AppConstants.need_help_group) {
                    Intent intent = new Intent(RdFdRecknorActivity.this, NeedHelp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }
                Intent intent = new Intent(RdFdRecknorActivity.this, ServiceRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(RdFdRecknorActivity.this);
    }
    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        trustMethods.horizontalRecyclerView(RdFdRecknorActivity.this, recyclerViewHoriListId);
    }
}