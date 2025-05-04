package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AmortizationChartActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AmortizationChartActivity.class.getSimpleName();
    TrustMethods trustMethods;
    RecyclerView recyclerViewHoriListId;

    EditText editTextExpiryDate,editTextDisbursementDate,editTextFirstInstallmentDate,editTextLoanAmount,editTextLoanPeriod,editTextIntrestRate,edNumberOfInstallment,edFirstInterestInstallmentDate;
    Button buttonShow,buttonCancel;
    LinearLayout layoutLoanPeriodUnit,layoutFirstInterestInstallmentDate,layoutNumberOfInstallment,layoutInterestAppliedFrequency,layoutInterestCompoundFrequency,layoutInterestRepaymentFrequency,layoutPrincipleRepaymentFrequency;
    Spinner spinnerMethod,spPrincipalRepaymentFrequency,spInterestRepaymentFrequency,spinnerIntrestCompoundingFrequency,spinnerInstallmentAppliedFrequency,spLoanPeriodUnit;

    public String expiryDate, disbursementDate, firstInstallmentDate, firstInterestInstallmentDate, noOfInstal;
    public String loanAmount, loanPeriod, loanPeriodUnit, interestRate;
    public String method, installmentAppliedFrequency, interestCompoundingFrequency, interestRepaymentFrequency, principalRepaymentFrequency;

    List<String> methodValue, loanPeriodUnitValue;
    List<String> installmentAppliedFrequencyValue;
    List<String> interestCompoundingFrequencyValue;
    List<String> interestRepaymentFrequencyValue;
    List<String> principleRepaymentFrequencyValue;
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
                        TrustMethods.naviagteToSplashScreen(AmortizationChartActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(AmortizationChartActivity.this, false);
        setContentView(R.layout.activity_amortization_chart);
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
            trustMethods = new TrustMethods(AmortizationChartActivity.this);
            trustMethods.activityOpenAnimation();
            horizontalRecyclerView();

            editTextExpiryDate = (EditText) findViewById(R.id.editTextExpiryDate);
            editTextDisbursementDate = (EditText) findViewById(R.id.editTextDisbursementDate);
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = sdf.format(date);
            editTextDisbursementDate.setText(dateString);
            editTextFirstInstallmentDate = (EditText) findViewById(R.id.editTextFirstInstallmentDate);
            editTextLoanAmount = (EditText) findViewById(R.id.editTextLoanAmount_Id1);
            editTextLoanPeriod = (EditText) findViewById(R.id.editTextLoanPeriod_Id);
            editTextIntrestRate = (EditText) findViewById(R.id.editTextInterestRate_Id);
            edNumberOfInstallment = (EditText) findViewById(R.id.edNumberOfInstallment);
            edFirstInterestInstallmentDate = (EditText) findViewById(R.id.edFirstInterestInstallmentDate);

            buttonShow = (Button) findViewById(R.id.buttonShow);
            buttonCancel = (Button) findViewById(R.id.buttonCancelAm);

            buttonShow.setOnClickListener(this);
            buttonCancel.setOnClickListener(this);
            editTextDisbursementDate.setOnClickListener(this);
            editTextFirstInstallmentDate.setOnClickListener(this);

            layoutLoanPeriodUnit = findViewById(R.id.layoutLoanPeriodUnit);
            layoutInterestAppliedFrequency = findViewById(R.id.layoutInterestAppliedFrequency);
            layoutInterestCompoundFrequency = findViewById(R.id.layoutInterestCompoundFrequency);
            layoutInterestRepaymentFrequency = findViewById(R.id.layoutInterestRepaymentFrequency);
            layoutPrincipleRepaymentFrequency = findViewById(R.id.layoutPrincipleRepaymentFrequency);
            layoutNumberOfInstallment = findViewById(R.id.layoutNumberOfInstallment);
            layoutFirstInterestInstallmentDate = findViewById(R.id.layoutFirstInterestInstallmentDate);

            spinnerMethod = (Spinner) findViewById(R.id.spinnerMethod_Id);
            spLoanPeriodUnit = (Spinner) findViewById(R.id.spLoanPeriodUnit);
            spinnerInstallmentAppliedFrequency = (Spinner) findViewById(R.id.spinnerInstallmentAppliedFrequency_Id);
            spinnerIntrestCompoundingFrequency = (Spinner) findViewById(R.id.spinnerIntrestCompoundingFrequency_Id);
            spInterestRepaymentFrequency = findViewById(R.id.spInterestRepaymentFrequency);
            spPrincipalRepaymentFrequency = findViewById(R.id.spPrincipalRepaymentFrequency);

            loadSpinners();
            listner();
            SpinnerClickListener();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSpinners() {
        layoutInterestAppliedFrequency.setVisibility(View.VISIBLE);
        layoutInterestCompoundFrequency.setVisibility(View.VISIBLE);
        editTextExpiryDate.setVisibility(View.VISIBLE);
        layoutLoanPeriodUnit.setVisibility(View.GONE);
        layoutInterestRepaymentFrequency.setVisibility(View.GONE);
        layoutPrincipleRepaymentFrequency.setVisibility(View.GONE);
        layoutNumberOfInstallment.setVisibility(View.GONE);
        layoutFirstInterestInstallmentDate.setVisibility(View.GONE);


        //List<String> loanPeriodUnit;

        methodValue = Arrays.asList(getResources().getStringArray(R.array.Method_Value));
        List<String> method=new ArrayList<>();
        method.add("Select Method");
        method.add("Simple");
        method.add("Compound");
        method.add("Flat");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AmortizationChartActivity.this, android.R.layout.simple_spinner_item, method);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMethod.setAdapter(spinnerArrayAdapter);

        installmentAppliedFrequencyValue = Arrays.asList(getResources().getStringArray(R.array.Installment_Applied_Frequency_Value));
        List<String> installmentAppliedFrequency=new ArrayList<>();
        installmentAppliedFrequency.add("Select Installment Applied Frequency");
        installmentAppliedFrequency.add("Monthly");
        installmentAppliedFrequency.add("Quarterly");
        installmentAppliedFrequency.add("Half Yearly");
        installmentAppliedFrequency.add("Yearly");

        ArrayAdapter<String> spinnerArrayAdapterInstallmentAppliedFrequency = new ArrayAdapter<String>(AmortizationChartActivity.this, android.R.layout.simple_spinner_item, installmentAppliedFrequency);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstallmentAppliedFrequency.setAdapter(spinnerArrayAdapterInstallmentAppliedFrequency);

        interestCompoundingFrequencyValue = Arrays.asList(getResources().getStringArray(R.array.Interest_Compounding_Frequency_Value));
        List<String> intrestCompoundingFrequency=new ArrayList<>();
        intrestCompoundingFrequency.add("Select Intrest Compounding Frequency");
        intrestCompoundingFrequency.add("Monthly");
        intrestCompoundingFrequency.add("Quarterly");
        intrestCompoundingFrequency.add("Semi Yearly");
        intrestCompoundingFrequency.add("Yearly");

        ArrayAdapter<String> spinnerArrayAdapterIntrestCompoundingFrequency = new ArrayAdapter<String>(AmortizationChartActivity.this, android.R.layout.simple_spinner_item, intrestCompoundingFrequency);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIntrestCompoundingFrequency.setAdapter(spinnerArrayAdapterIntrestCompoundingFrequency);

        editTextIntrestRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String input = s.toString();

                try {
                    if (!TextUtils.isEmpty(input)) {
                        if (Float.parseFloat(input.trim()) > 100.0f) {
                            editTextIntrestRate.setText("");
                            Toast.makeText(getApplicationContext(), "Enter Valid Interest Rate", Toast.LENGTH_SHORT).show();
                        } else {
                            if (input.contains(".") && s.charAt(s.length() - 1) != '.') {
                                if (input.indexOf(".") + 3 <= input.length() - 1) {
                                    String formatted = input.substring(0, input.indexOf(".") + 3);
                                    editTextIntrestRate.setText(formatted);
                                    editTextIntrestRate.setSelection(formatted.length());
                                }
                            } else if (input.contains(",") && s.charAt(s.length() - 1) != ',') {
                                if (input.indexOf(",") + 3 <= input.length() - 1) {
                                    String formatted = input.substring(0, input.indexOf(",") + 3);
                                    editTextIntrestRate.setText(formatted);
                                    editTextIntrestRate.setSelection(formatted.length());
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void listner() {
        /*if (getPackageName().equals("com.trustbank.dib")) {

            editTextLoanPeriod.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0) {
                        updateNumberOfInstallment();
                    }
                }
            });

            editTextDisbursementDate.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0) {
                        updateFirstInstallmentDate();
                    }
                }
            });

        }*/
    }
    private void SpinnerClickListener() {
        spinnerMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int position, long arg3) {
                method = methodValue.get(position);
                TrustMethods.LogMessage(TAG, "method->" + method);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        spinnerInstallmentAppliedFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                installmentAppliedFrequency = installmentAppliedFrequencyValue.get(position);
                TrustMethods.LogMessage(TAG, "installmentAppliedFrequency->" + installmentAppliedFrequency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerIntrestCompoundingFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                interestCompoundingFrequency = interestCompoundingFrequencyValue.get(position);
                TrustMethods.LogMessage(TAG, "interestCompoundingFrequency->" + interestCompoundingFrequency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //***************For DIB****************/
/*        spInterestRepaymentFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    interestRepaymentFrequency = interestRepaymentFrequencyValue.get(position);
                    principalRepaymentFrequency = principleRepaymentFrequencyValue.get(position);
                    spPrincipalRepaymentFrequency.setSelection(position);
                    TrustMethods.LogMessage(TAG, "InterestRepaymentFrequency->" + interestRepaymentFrequency);
                    TrustMethods.LogMessage(TAG, "PrincipalRepaymentFrequency->" + principalRepaymentFrequency);

                    updateNumberOfInstallment();
                    updateFirstInstallmentDate();

                }catch (Exception e){
                    TrustMethods.message(AmortizationChartActivity.this,"Error : "+e.getMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
        //***************For DIB****************/

        editTextLoanPeriod.addTextChangedListener(new TextWatcher() {

            int strAmt = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                TrustMethods.LogMessage("check", editTextDisbursementDate.getText().toString());
                if (!TextUtils.isEmpty(editTextDisbursementDate.getText().toString())) {
                    disbursementDate = editTextDisbursementDate.getText().toString();
                    String strLoanAmt = editTextLoanPeriod.getText().toString();
                    int strAmt = 0;
                    try {
                        if (!TextUtils.isEmpty(strLoanAmt)) {
                            strAmt = Integer.parseInt(strLoanAmt);
                        }
                    } catch (Exception e) {
                        Toast.makeText(AmortizationChartActivity.this, "Please Enter Valid Loan Period", Toast.LENGTH_SHORT).show();
                    }
                    ConvertToDate(disbursementDate, strAmt, editTextExpiryDate);

                } else {
                    Toast.makeText(AmortizationChartActivity.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void ConvertToDate(String dateString, int strAmt, EditText editTextExpiryDate) {
        Date convertedDate;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            convertedDate = dateFormat.parse(dateString);
            addMonth(convertedDate, strAmt, editTextExpiryDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addMonth(Date date, int i, EditText editTextExpiryDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        cal.getTime();
        Date date1 = cal.getTime();
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String reportDate = df.format(date1);
        editTextExpiryDate.setText(reportDate);
        TrustMethods.LogMessage("date", String.valueOf(reportDate));
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editTextDisbursementDate:
                trustMethods.datePickerymd(AmortizationChartActivity.this, editTextDisbursementDate);
                break;

            case R.id.editTextFirstInstallmentDate:
                trustMethods.datePickerymd(AmortizationChartActivity.this,editTextFirstInstallmentDate);
                break;

            case R.id.buttonShow:

                    expiryDate = editTextExpiryDate.getText().toString();
                    disbursementDate = editTextDisbursementDate.getText().toString();
                    firstInstallmentDate = editTextFirstInstallmentDate.getText().toString();
                    loanAmount = editTextLoanAmount.getText().toString();
                    loanPeriod = editTextLoanPeriod.getText().toString();
                    interestRate = editTextIntrestRate.getText().toString();

                    if (ValidateETFields()) {

                        Intent intentShow = new Intent(AmortizationChartActivity.this, AmortizationChartDetailsActivity.class);
                        intentShow.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intentShow.putExtra("method", method);
                        intentShow.putExtra("expiryDate", expiryDate);
                        intentShow.putExtra("interestCompoundingFrequency", interestCompoundingFrequency);
                        intentShow.putExtra("installmentAppliedFrequency", installmentAppliedFrequency);
                        intentShow.putExtra("loanAmount", loanAmount);
                        intentShow.putExtra("disbursementDate", disbursementDate);
                        intentShow.putExtra("firstInstallmentDate", firstInstallmentDate);
                        intentShow.putExtra("loanPeriod", loanPeriod);
                        intentShow.putExtra("interestRate", interestRate);
                        startActivity(intentShow);
                    }

                //}
                break;

            case R.id.buttonCancelAm:
                Intent intent = new Intent(AmortizationChartActivity.this, ServiceRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private boolean ValidateETFields() {
        if (TextUtils.isEmpty(editTextDisbursementDate.getText().toString().trim())) {
            editTextDisbursementDate.setError("Please Select  disbursement Date");
            return false;
        } else if (TextUtils.isEmpty(editTextLoanAmount.getText().toString().trim())) {
            editTextLoanAmount.setError("Please Enter loan Amount");
            return false;
        } else if (TextUtils.isEmpty(editTextLoanPeriod.getText().toString().trim())) {
            editTextLoanPeriod.setError("Please Enter loan Period");
            return false;
        } else if (TextUtils.isEmpty(editTextFirstInstallmentDate.getText().toString().trim())) {
            editTextFirstInstallmentDate.setError("Please Select first Installment Date ");
            return false;
        } else if (TextUtils.isEmpty(editTextIntrestRate.getText().toString().trim())) {
            editTextIntrestRate.setError("Please Enter interest Rate");
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_amortization_chart")) {
                        Intent intent = new Intent(AmortizationChartActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }

                if(AppConstants.account_group){
                    Intent intent = new Intent(AmortizationChartActivity.this, AccountsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.upi_group) {
                    Intent intent = new Intent(AmortizationChartActivity.this, UPIActivityMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.service_group) {
                    Intent intent = new Intent(AmortizationChartActivity.this, ServiceRequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.cards_group) {
                    Intent intent = new Intent(AmortizationChartActivity.this, Cards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.locate_us_group) {
                    Intent intent = new Intent(AmortizationChartActivity.this, LocateUs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }else if (AppConstants.need_help_group) {
                    Intent intent = new Intent(AmortizationChartActivity.this, NeedHelp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }

                Intent intent = new Intent(AmortizationChartActivity.this, ServiceRequest.class);
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
        TrustMethods.showBackButtonAlert(AmortizationChartActivity.this);
    }
    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        trustMethods.horizontalRecyclerView(AmortizationChartActivity.this, recyclerViewHoriListId);
    }

}