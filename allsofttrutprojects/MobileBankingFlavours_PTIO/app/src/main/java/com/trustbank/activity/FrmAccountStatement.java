package com.trustbank.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.util.ArrayList;
import java.util.List;

public class FrmAccountStatement extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = FrmAccountStatement.class.getSimpleName();
    private TextView editTextDateTo, editTextDateFrom;
    private Button buttonDisplay;
    private TrustMethods trustMethods;
    private Spinner spinnerAccountNumber;
    private RadioGroup radioGroup;
    public static String dateFrom;
    public static String dateTo;
    public String tr_type;
    public EditText et_minaccount,et_maxaccount,et_chequeno,et_remark;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    private ImageView backButton_new;
    private TextView toolbar,tv_advanceSearch;
    private LinearLayout ll_advsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                if (savedInstanceState != null) {
                    Object currentPID = String.valueOf(android.os.Process.myPid());
                    // Check current PID with old PID
                    if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                        // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                        TrustMethods.naviagteToSplashScreen(FrmAccountStatement.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(FrmAccountStatement.this, false);
        setContentView(R.layout.activity_frm_enquiry);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.lbl_activity_account_statement);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

        init();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        trustMethods = new TrustMethods(FrmAccountStatement.this);
        trustMethods.activityOpenAnimation();

        editTextDateTo = findViewById(R.id.editTextToDate);
        editTextDateFrom = findViewById(R.id.editTextFromDate);
        buttonDisplay = findViewById(R.id.buttonDisplay);
        et_minaccount=findViewById(R.id.et_minaccount);
        et_maxaccount=findViewById(R.id.et_maxaccount);
        et_chequeno=findViewById(R.id.et_chequeno);
        et_remark=findViewById(R.id.et_remark);
        ll_advsearch=findViewById(R.id.ll_advsearch);
        tv_advanceSearch=findViewById(R.id.tv_advanceSearch);
        tv_advanceSearch.setOnClickListener(this);
        editTextDateTo.setOnClickListener(this);
        editTextDateFrom.setOnClickListener(this);
        buttonDisplay.setOnClickListener(this);
        spinnerAccountNumber = findViewById(R.id.spinnerAccount_Id);
        radioGroup=findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String type= (String) radioButton.getText();
                if(type.equalsIgnoreCase("Debit")){
                    tr_type="DR";
                }else{
                    tr_type="CR";
                }
            }
        });
        backButton_new = findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);
        setAccountNoSpinner();
        setDate();
    }

    private void setDate() {
        editTextDateFrom.setText(TrustMethods.getCurrentMonthFirstDate());
        editTextDateTo.setText(TrustMethods.getMonthYear());
    }
    private void setAccountNoSpinner() {
        try {
            accountsArrayList = trustMethods.getArrayList(FrmAccountStatement.this, "AccountListPref");
            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                List<String> accountList = new ArrayList<>();
                accountList.add(0,  getResources().getString(R.string.SelectAccountNumber));
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (TrustMethods.isAccountTypeValidstatement(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FrmAccountStatement.this, android.R.layout.simple_spinner_item, accountList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAccountNumber.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {

                case R.id.backButton_new:
                    for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                        if (menuModel.getMenucode().equalsIgnoreCase("mnu_statement")) {
                            Intent intent = new Intent(FrmAccountStatement.this, MenuActivity.class);
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

                    Intent intent = new Intent(FrmAccountStatement.this, AccountsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.editTextFromDate:
                    trustMethods.datePickerDisableFuturesDate(FrmAccountStatement.this, editTextDateFrom);
                    break;

                case R.id.tv_advanceSearch:
                    if (ll_advsearch.getVisibility() == View.VISIBLE) {
                        ll_advsearch.setVisibility(View.GONE);
                    } else {
                        ll_advsearch.setVisibility(View.VISIBLE);
                    }
                    break;

                case R.id.editTextToDate:
                    trustMethods.datePickerDisableFuturesDate(FrmAccountStatement.this, editTextDateTo);
                    break;
                case R.id.buttonDisplay:
                    dateFrom = editTextDateFrom.getText().toString();
                    dateTo = editTextDateTo.getText().toString();
                    String minAmount = et_minaccount.getText().toString();
                    String maxAmount = et_maxaccount.getText().toString();
                    String chequeno = et_chequeno.getText().toString();
                    String remark = et_remark.getText().toString();
                    String accountNo = spinnerAccountNumber.getSelectedItem().toString();
                    if (spinnerAccountNumber.getSelectedItemPosition() == 0) {
                        trustMethods.message(getApplicationContext(),getResources().getString(R.string.error_select_acc_no));
                        return;
                    }

                    if (dateTo.trim().length() == 0 && dateFrom.trim().length() == 0) {
                        trustMethods.message(getApplicationContext(), getResources().getString(R.string.Pleaseselectdates));
                        return;
                    }

                    if (dateFrom.trim().length() == 0) {
                        trustMethods.message(getApplicationContext(), getResources().getString(R.string.Pleaseselectfromdate));
                        return;
                    }

                    if (dateTo.trim().length() == 0) {
                        trustMethods.message(getApplicationContext(), getResources().getString(R.string.Pleaseselecttodate));
                        return;
                    }

                    if (TrustMethods.isFromDateGreaterThanToDate(dateFrom.trim(), dateTo.trim())) {
                        trustMethods.message(getApplicationContext(), getResources().getString(R.string.Fromdatecannotbegreaterthantodate));
                        return;
                    }

                    String accNo = "";
                    if (accountNo.contains("-")) {
                        String[] accounts = accountNo.split("-");
                        accNo = accounts[0];
                    } else {
                        accNo = accountNo;
                    }

                    Intent intentShowReport = new Intent(getApplicationContext(), FrmAccountStatementDetails.class);
                    intentShowReport.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    /*intentShowReport.putExtra("fromDate", "2023-03-25");//2023-03-25
                    intentShowReport.putExtra("toDate", "2023-10-31");//2023-10-31
                    intentShowReport.putExtra("accountNo", "440110000072");
                    intentShowReport.putExtra("trType", "");
                    intentShowReport.putExtra("amtMin", "");
                    intentShowReport.putExtra("amtMax", "");
                    intentShowReport.putExtra("cheque", "");
                    intentShowReport.putExtra("remark", "");*/
                    intentShowReport.putExtra("fromDate", dateFrom);//2023-03-01
                    intentShowReport.putExtra("toDate", dateTo);//2023-10-31
                    intentShowReport.putExtra("accountNo", accNo);
                    intentShowReport.putExtra("trType", tr_type);
                    intentShowReport.putExtra("amtMin", minAmount);
                    intentShowReport.putExtra("amtMax", maxAmount);
                    intentShowReport.putExtra("cheque", chequeno);
                    intentShowReport.putExtra("remark", remark);
                    startActivity(intentShowReport);
                    trustMethods.activityOpenAnimation();
                    break;

                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(FrmAccountStatement.this);
    }
}
