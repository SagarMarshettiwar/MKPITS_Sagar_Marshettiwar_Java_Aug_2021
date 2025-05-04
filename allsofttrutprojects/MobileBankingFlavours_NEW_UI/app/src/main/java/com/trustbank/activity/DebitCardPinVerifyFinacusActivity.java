package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.DatePickerDialog;
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
import android.widget.Spinner;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import org.intellij.lang.annotations.JdkConstants;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DebitCardPinVerifyFinacusActivity extends AppCompatActivity {

    private TrustMethods trustMethods;
    private CoordinatorLayout coordinatorlay;
    private Spinner spinnerFrmAct;
    private EditText et_card_no, et_exp_date, et_pin;
    private Button btnSubmit;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    private List<String> accountList;
    private String accNo;

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
                    // Check current PID with old PID
                    if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                        // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                        TrustMethods.naviagteToSplashScreen(DebitCardPinVerifyFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SetTheme.changeToTheme(DebitCardPinVerifyFinacusActivity.this, false);
        setContentView(R.layout.activity_debit_card_pin_verify_finacus);
        trustMethods = new TrustMethods(DebitCardPinVerifyFinacusActivity.this);
        inIt();
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
    private void inIt() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        trustMethods = new TrustMethods(DebitCardPinVerifyFinacusActivity.this);
        coordinatorlay = findViewById(R.id.coordinatorlayId);
        spinnerFrmAct = findViewById(R.id.spinnerFrmActId);
        et_card_no = findViewById(R.id.et_card_no);
        et_exp_date = findViewById(R.id.et_exp_date);
        et_pin = findViewById(R.id.et_pin);
        btnSubmit = findViewById(R.id.btnSubmit);

        accNumberSpinner();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNo =et_card_no.getText().toString();
                String expDate = et_exp_date.getText().toString();
                String pin = et_pin.getText().toString();

                if (spinnerFrmAct.getSelectedItem().equals("Select Account Number")) {
                    TrustMethods.showSnackBarMessage("Please Select Account Number", coordinatorlay);
                } else if (TextUtils.isEmpty(cardNo)) {
                    TrustMethods.showSnackBarMessage("Please enter card number", coordinatorlay);
                } else if (TextUtils.isEmpty(expDate) || expDate.length()!=5) {
                    TrustMethods.showSnackBarMessage("Please enter expiry date in MMYY format", coordinatorlay);
                } else if (TextUtils.isEmpty(pin)) {
                    TrustMethods.showSnackBarMessage("Please enter pin", coordinatorlay);

                } else  {
                    Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, OtpVerificationActivity.class);
                    intent.putExtra("checkTransferType", "debitCardPinVerifyFinacus");
                    intent.putExtra("accountNo", accNo.trim());
                    intent.putExtra("cardPin", pin);
                    intent.putExtra("cardNo", cardNo);
                    intent.putExtra("ExpDate", expDate);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        et_exp_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String exp= s.toString();
                if(exp.length()==4 && !exp.contains("/")){
                    String formatDate = exp.substring(0,2)+'/'+exp.substring(2);
                    et_exp_date.setText(formatDate);
                    et_exp_date.setSelection(et_exp_date.getText().length());
                }
            }
        });
    }

    private void accNumberSpinner() {
        try {
            accountsArrayList = trustMethods.getArrayList(DebitCardPinVerifyFinacusActivity.this, "AccountListPref");

            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                accountList = new ArrayList<>();
                accountList.add(0, "Select Account Number");
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (TrustMethods.isAccountTypeValidcard(getUserProfileModal.getHeadid(),AppConstants.getCard_list(), getUserProfileModal.getCardActive())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo + " - " + accTypeCode);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DebitCardPinVerifyFinacusActivity.this, android.R.layout.simple_spinner_item, accountList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFrmAct.setAdapter(adapter);
            }

            spinnerFrmAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    try {
                        if (position != 0) {
                            String selectedAccNo = (String) adapterView.getItemAtPosition(position);
                            accNo = "";
                            if (selectedAccNo.contains("-")) {
                                String[] accounts = selectedAccNo.split("-");
                                accNo = accounts[0];
                            } else {
                                accNo = selectedAccNo;
                            }
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                if (menuModel.getMenucode().equalsIgnoreCase("mnu_debit_card_pin_verify_finacus")) {
                    Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }
            }

            if(AppConstants.account_group){
                Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, AccountsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            } else if (AppConstants.upi_group) {
                Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, UPIActivityMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            } else if (AppConstants.service_group) {
                Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, ServiceRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            } else if (AppConstants.cards_group) {
                Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, Cards.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            } else if (AppConstants.locate_us_group) {
                Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, LocateUs.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }else if (AppConstants.need_help_group) {
                Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, NeedHelp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }

            Intent intent = new Intent(DebitCardPinVerifyFinacusActivity.this, Cards.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(DebitCardPinVerifyFinacusActivity.this);
    }
}