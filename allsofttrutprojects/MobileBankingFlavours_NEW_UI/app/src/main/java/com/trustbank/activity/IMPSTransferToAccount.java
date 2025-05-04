package com.trustbank.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Model.FundTransferSubModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.adapter.MenuAdapter;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.DecimalDigitsInputFilter;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IMPSTransferToAccount extends AppCompatActivity implements View.OnClickListener, AlertDialogOkListener {
    private TextView txtToBenName, txtToAccNo, txtToIfscCode, txt_msg,txt_msg1,txt_msg2,txt_amt,txt_amt1,txt_amt2;
    private EditText etAccountNo;

    LinearLayout ll_title,ll_row3,ll_row2,ll_row1;
    private EditText etConfirmAccountNo;
    private TextInputLayout layoutConfirmAcctNo;
    private EditText etIFSC;
    private EditText etAmount;
    private EditText etRemarks;
    private Spinner spinnerFrmAct;
    private Spinner spinnerToBenImpsAcc;
    private LinearLayout setFundTransferLayout;
    private LinearLayout formLayoutFundTransfer;
    private CoordinatorLayout coordinatorLayout;
    private TrustMethods trustMethods;
    private String accountNo, beneficiary, benAccountFullName = "";
    private ArrayList<GetUserProfileModal> accountsArrayList;
    private ArrayList<BeneficiaryModal> beneficiaryModalsSortedData;
    private ArrayList<BeneficiaryModal> beneficiaryArrayList;
    private String remitterAccName;
    private String beneficiaryNickName;
    private String beneficiaryName;
    private boolean isAccountNoMatch = false;
    private AlertDialogOkListener alertDialogOkListener = this;
    RecyclerView recyclerViewHoriListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                if (savedInstanceState != null) {
                    Object currentPID = String.valueOf(android.os.Process.myPid());
                    // Check current PID with old PID
                    if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                        // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                        TrustMethods.naviagteToSplashScreen(IMPSTransferToAccount.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(IMPSTransferToAccount.this, false);
        setContentView(R.layout.activity_impstransfer_to_account);
        trustMethods = new TrustMethods(IMPSTransferToAccount.this);
        if (trustMethods.packageoneIdentify(IMPSTransferToAccount.this)) {
            AlertDialogMethod.alertDialogOk(IMPSTransferToAccount.this, "Security Alert", "Security Alert Following installed apps can be used by " +
                            "fraudsters to steal your money Please uninstall Remote Access Apps to continue using Mbank",
                    getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
        }else{
            initcomponent();
        }



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

    private void initcomponent() {
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            trustMethods = new TrustMethods(IMPSTransferToAccount.this);
            txtToBenName = findViewById(R.id.txtToBenNameId);
            txtToAccNo = findViewById(R.id.txtToAccNoId);
            txtToIfscCode = findViewById(R.id.txtToIfscCodeId);
            etAccountNo = findViewById(R.id.etAccountNoId);
            etConfirmAccountNo = findViewById(R.id.etConfirmAccountNoId);
            layoutConfirmAcctNo = findViewById(R.id.layoutConfirmAcctNo);
            etIFSC = findViewById(R.id.etIFSCId);
            etAmount = findViewById(R.id.etAmountId);
            etRemarks = findViewById(R.id.etRemarksId);
            spinnerFrmAct = findViewById(R.id.spinnerFrmAct);
            spinnerToBenImpsAcc = findViewById(R.id.spinnerToBenImpsAccId);
            setFundTransferLayout = findViewById(R.id.setFundTransferLayoutId);
            formLayoutFundTransfer = findViewById(R.id.formLayoutFundTransferId);
            coordinatorLayout = findViewById(R.id.coordinatorLayout);
            txt_msg = findViewById(R.id.txt_msg);
            txt_msg1 = findViewById(R.id.txt_msg1);
            txt_msg2 = findViewById(R.id.txt_msg2);
            txt_amt = findViewById(R.id.txt_amt);
            txt_amt1 = findViewById(R.id.txt_amt1);
            txt_amt2 = findViewById(R.id.txt_amt2);
            ll_title = findViewById(R.id.ll_title);
            ll_row3 = findViewById(R.id.ll_row3);
            ll_row2 = findViewById(R.id.ll_row2);
            ll_row1 = findViewById(R.id.ll_row1);
            Button btnWithBankNext = findViewById(R.id.btnWithBankNextId);
            btnWithBankNext.setOnClickListener(this);

            if(!AppConstants.getImps_charges_msg().isEmpty()){
                ll_title.setVisibility(View.VISIBLE);
                ll_row1.setVisibility(View.VISIBLE);
                ll_row2.setVisibility(View.VISIBLE);
                ll_row3.setVisibility(View.VISIBLE);
                Map<String, String> list= AppConstants.getImpsrangelist();
                List<Map.Entry<String, String>> entryList = new ArrayList<>(list.entrySet());
                Map.Entry<String, String> entry = entryList.get(1);
                String key = entry.getKey();
                String value = entry.getValue();
                txt_msg.setText(key);
                txt_amt.setText(value+"₹ "+"+GST");

                Map.Entry<String, String> entry1 = entryList.get(0);
                String key1 = entry1.getKey();
                String value1 = entry1.getValue();
                txt_msg1.setText(key1);
                txt_amt1.setText(value1+"₹ "+"+GST");

                Map.Entry<String, String> entry2 = entryList.get(2);
                String key2 = entry2.getKey();
                String value2 = entry2.getValue();
                txt_msg2.setText(key2);
                txt_amt2.setText(value2+"₹ "+"+GST");
            }else{
                ll_title.setVisibility(View.GONE);
                ll_row1.setVisibility(View.GONE);
                ll_row2.setVisibility(View.GONE);
                ll_row3.setVisibility(View.GONE);
            }


            etAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
            etAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        String s1 = s.toString();
                        if (s1.length() == 1 && s1.equalsIgnoreCase("0")) {
                            etAmount.setText("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            horizontalRecyclerView();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setAccountNoSpinner();
        setBeneficiarySpinner();
        getSpinnerData();
        commonListener();
    }

    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        trustMethods.horizontalRecyclerView(this, recyclerViewHoriListId);
    }

    private void commonListener() {

        try {
            etConfirmAccountNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!etAccountNo.getText().toString().equals(etConfirmAccountNo.getText().toString())) {
                        isAccountNoMatch = false;
                        etConfirmAccountNo.setError("Account No. not match");
                    } else {
                        isAccountNoMatch = true;
                        etConfirmAccountNo.setError(null);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAccountNoSpinner() {
        try {
            accountsArrayList = trustMethods.getArrayList(IMPSTransferToAccount.this, "AccountListPref");

            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                List<String> accountList = new ArrayList<>();
                accountList.add(0, "Select Account Number");
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (getPackageName().equals("com.trustbank.janataajarambank")) {
                        if (TrustMethods.isAccountTypeIsImpsRegValidAjara(getUserProfileModal.getActType(), getUserProfileModal.getIs_imps_reg())) {
                            String accNo = getUserProfileModal.getAccNo();
                            String accTypeCode = getUserProfileModal.getAcTypeCode();
                            accountList.add(accNo + " - " + accTypeCode);
                        }
                    } else {
                        if (TrustMethods.isAccountTypeIsImpsRegValidfortrans(getUserProfileModal.getHeadid(), getUserProfileModal.getIs_imps_reg(),AppConstants.getImpslist(),AppConstants.getIMPS_registered())) {
                            String accNo = getUserProfileModal.getAccNo();
                            String accTypeCode = getUserProfileModal.getAcTypeCode();
                            accountList.add(accNo + " - " + accTypeCode);
                        }
                    }


                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(IMPSTransferToAccount.this, android.R.layout.simple_spinner_item, accountList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFrmAct.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBeneficiarySpinner() {
        try {
            if (getIntent().getExtras() != null) {
                beneficiaryArrayList = (ArrayList<BeneficiaryModal>) getIntent().getSerializableExtra("beneficiaryList");
                beneficiaryNickName = getIntent().getStringExtra("beneficiaryNickName");
                beneficiaryName = getIntent().getStringExtra("beneficiaryName");
            }

            List<String> beneficiaryList = new ArrayList<>();
            beneficiaryList.add(0, "Select Beneficiary");
            beneficiaryArrayList =  trustMethods.getBenArrayList(IMPSTransferToAccount.this, "BenAccList");
            beneficiaryModalsSortedData = new ArrayList<>();
            if (beneficiaryArrayList != null && beneficiaryArrayList.size() > 0) {
                for (int i = 0; i < beneficiaryArrayList.size(); i++) {
                    BeneficiaryModal beneficiaryModal = beneficiaryArrayList.get(i);
                    if (beneficiaryModal.getBenType().equals("2")) {
                        BeneficiaryModal beneficiaryModal1 = new BeneficiaryModal();
                        beneficiaryModal1.setBenNickname(beneficiaryModal.getBenNickname());
                        beneficiaryModal1.setBenAccNo(beneficiaryModal.getBenAccNo());
                        beneficiaryModal1.setBenIfscCode(beneficiaryModal.getBenIfscCode());
                        beneficiaryModal1.setBanAccName(beneficiaryModal.getBanAccName());
                        beneficiaryModalsSortedData.add(beneficiaryModal1);
                        beneficiaryList.add(beneficiaryModal.getBenNickname());
                    }
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(IMPSTransferToAccount.this, android.R.layout.simple_spinner_item, beneficiaryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerToBenImpsAcc.setAdapter(adapter);

            if (!TextUtils.isEmpty(beneficiaryNickName)) {
                formLayoutFundTransfer.setVisibility(View.GONE);
                setFundTransferLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < beneficiaryModalsSortedData.size(); i++) {
                    if (beneficiaryNickName.equalsIgnoreCase(beneficiaryModalsSortedData.get(i).getBenNickname())) {
                        txtToBenName.setText(beneficiaryModalsSortedData.get(i).getBenNickname());
                        txtToAccNo.setText(beneficiaryModalsSortedData.get(i).getBenAccNo());
                        txtToIfscCode.setText(beneficiaryModalsSortedData.get(i).getBenIfscCode());
                    }
                }
            } else {
                formLayoutFundTransfer.setVisibility(View.VISIBLE);
                setFundTransferLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSpinnerData() {
        try {
            spinnerFrmAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    if (pos != 0) {
                        accountNo = TrustMethods.getValidAccountNo((String) adapterView.getItemAtPosition(pos));
                        //Get Profile Info List to get remitter name for the selected account number
                        if (accountsArrayList != null && accountsArrayList.size() > 0) {
                            for (int i = 0; i < accountsArrayList.size(); i++) {
                                GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                                if (getUserProfileModal.getAccNo().equalsIgnoreCase(accountNo.trim())) {
                                    remitterAccName = getUserProfileModal.getName();
                                    Log.d("remitterAccName", remitterAccName);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerToBenImpsAcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    try {
                        if (position != 0) {
                            beneficiary = (String) adapterView.getItemAtPosition(position);
                            String strAccountNumber = beneficiaryModalsSortedData.get(position - 1).getBenAccNo();
                            String strIFSCCode = beneficiaryModalsSortedData.get(position - 1).getBenIfscCode();
                            etAccountNo.setText(strAccountNumber);
                            etIFSC.setText(strIFSCCode);
                            etAccountNo.setClickable(false);
                            etIFSC.setClickable(false);
                            layoutConfirmAcctNo.setVisibility(View.GONE);
                            benAccountFullName = beneficiaryModalsSortedData.get(position - 1).getBanAccName();
                        } else {
                            etAccountNo.setText("");
                            etIFSC.setText("");
                            benAccountFullName = "";
                            etAccountNo.setClickable(true);
                            etIFSC.setClickable(true);
                            layoutConfirmAcctNo.setVisibility(View.GONE);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnWithBankNextId:
                try {
                    TrustMethods.hideSoftKeyboard(IMPSTransferToAccount.this);
                    if (!TextUtils.isEmpty(beneficiaryNickName)) {
                        if (spinnerFrmAct.getSelectedItem().equals("Select Account Number")) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_select_acc_no), coordinatorLayout);
                        } else if (TextUtils.isEmpty(etAmount.getText().toString())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_blank_amt), coordinatorLayout);
                        } else if (TrustMethods.isAmoutLessThanZero(etAmount.getText().toString().trim())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_valid_amt), coordinatorLayout);
                        } else if (TextUtils.isEmpty(etRemarks.getText().toString().trim())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_enter_remark), coordinatorLayout);
                        } else {
                            String benAccNo = txtToAccNo.getText().toString().trim();
                            String benIfscCode = txtToIfscCode.getText().toString().trim();
                            String amt = etAmount.getText().toString().trim();
                            String remark = etRemarks.getText().toString().trim();

                            List<FundTransferSubModel> fundTransferSubModalList = new ArrayList<>();
                            FundTransferSubModel fundTransferSubModel = new FundTransferSubModel();
                            fundTransferSubModel.setAccNo(accountNo);
                            fundTransferSubModel.setRemitterAccName(remitterAccName);
                            fundTransferSubModel.setBenAccNo(benAccNo);
                            fundTransferSubModel.setBenIfscCode(benIfscCode);
                            fundTransferSubModel.setAmt(amt);
                            fundTransferSubModel.setBenAccName(beneficiaryName);
                            fundTransferSubModel.setRemark(remark);
                            fundTransferSubModalList.add(fundTransferSubModel);

                            checkAmountValidation(amt, accountNo, fundTransferSubModalList);
                        }
                    } else {
                        if (spinnerFrmAct.getSelectedItem().equals("Select Account Number")) {
                            //TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_select_acc_no), coordinatorLayout);
                            AlertDialogMethod.alertDialogOk(IMPSTransferToAccount.this, " ",
                                    getResources().getString(R.string.error_select_acc_debit_no), getResources().getString(R.string.btn_ok), 70, false, alertDialogOkListener);
                        } else if (spinnerToBenImpsAcc.getSelectedItem().equals("Select Beneficiary")) {
                            TrustMethods.showSnackBarMessage("Please select beneficiary", coordinatorLayout);
                        } else if (TextUtils.isEmpty(etAccountNo.getText().toString())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_enter_ben_acc_no), coordinatorLayout);
                        } else if (!trustMethods.isValidAccNo(etAccountNo.getText().toString(), IMPSTransferToAccount.this)) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_invalid_ben_acc_no), coordinatorLayout);
                        } else if (spinnerToBenImpsAcc.getSelectedItem().toString().equals("New Beneficiary") && !isAccountNoMatch) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_validate_match_acct), coordinatorLayout);
                        } else if (TextUtils.isEmpty(etIFSC.getText().toString())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_enter_ifsc_code), coordinatorLayout);
                        } else if (!trustMethods.isValidImeI(etIFSC.getText().toString())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_invalid_ifsc_code), coordinatorLayout);
                        } else if (TextUtils.isEmpty(etAmount.getText().toString())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_blank_amt), coordinatorLayout);
                        } else if (TrustMethods.isAmoutLessThanZero(etAmount.getText().toString().trim())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_valid_amt), coordinatorLayout);
                        } else if (TextUtils.isEmpty(etRemarks.getText().toString().trim())) {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_enter_remark), coordinatorLayout);
                        } else {
                            String benAccNo = etAccountNo.getText().toString().trim();
                            String benIfscCode = etIFSC.getText().toString().trim();
                            String amt = etAmount.getText().toString().trim();
                            String remark = etRemarks.getText().toString().trim();
                            String benename = spinnerToBenImpsAcc.getSelectedItem().toString();

                            List<FundTransferSubModel> fundTransferSubModalList = new ArrayList<>();
                            FundTransferSubModel fundTransferSubModel = new FundTransferSubModel();
                            fundTransferSubModel.setAccNo(accountNo);
                            fundTransferSubModel.setRemitterAccName(remitterAccName);
                            fundTransferSubModel.setBenAccNo(benAccNo);
                            fundTransferSubModel.setBenIfscCode(benIfscCode);
                            fundTransferSubModel.setAmt(amt);
                            fundTransferSubModel.setRemark(remark);
                            fundTransferSubModel.setBenAccName(benAccountFullName);
                            fundTransferSubModalList.add(fundTransferSubModel);

                            checkAmountValidation(amt, accountNo, fundTransferSubModalList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(IMPSTransferToAccount.this, MenuActivity.class);
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
        TrustMethods.showBackButtonAlert(IMPSTransferToAccount.this);
    }

    private void checkAmountValidation(String amount, String accountNo, List<FundTransferSubModel> fundTransferSubModalList) {
        if (NetworkUtil.getConnectivityStatus(Objects.requireNonNull(IMPSTransferToAccount.this))) {
            new IMPSAddValidationAsyncTask(IMPSTransferToAccount.this, accountNo, amount, fundTransferSubModalList).execute();
        } else {
            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
        }
    }

    //check validation api call.
    @SuppressLint("StaticFieldLeak")
    private class IMPSAddValidationAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String finalResponse;
        ProgressDialog pDialog;
        String response;
        String actionName = "VALIDATE_FT";  //2
        String result;
        private String errorCode;
        private String accountNo;
        private String otpActionName;
        private String amount;
        private List<FundTransferSubModel> fundTransferSubModalList;

        public IMPSAddValidationAsyncTask(Context ctx, String accountNo, String amount,
                                          List<FundTransferSubModel> fundTransferSubModalList) {
            this.ctx = ctx;
            this.accountNo = accountNo;
            this.amount = amount;
            this.fundTransferSubModalList = fundTransferSubModalList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(IMPSTransferToAccount.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {

            try {
                String url = TrustURL.getURLForFundTransferOwnAndNeft();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rem_ac_no", fundTransferSubModalList.get(0).getAccNo());
                jsonObject.put("trf_type", 3);
                jsonObject.put("ben_ifsc", fundTransferSubModalList.get(0).getBenIfscCode());
                jsonObject.put("ben_ac_no", fundTransferSubModalList.get(0).getBenAccNo());
                jsonObject.put("amount", fundTransferSubModalList.get(0).getAmt());

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonObject.toString(),
                            actionName, AppConstants.getAuth_token());

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
                    finalResponse = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";
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
                if (!this.error.equals("")) {
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(IMPSTransferToAccount.this, getResources().getString(R.string.error_session_expire),
                                "",
                                getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        //TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                        AlertDialogMethod.alertDialogOk(IMPSTransferToAccount.this, " ", this.error,
                                getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }

                } else {
                    //response..
                    Intent intent = new Intent(IMPSTransferToAccount.this, OtpVerificationActivity.class);
                    intent.putExtra("checkTransferType", "impsToAccount");
                    intent.putExtra("fundTransferDataList", (Serializable) fundTransferSubModalList);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    trustMethods.activityOpenAnimation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        try {
            switch (resultCode) {
                case 0:
                    Intent intent = new Intent(IMPSTransferToAccount.this, LockActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    trustMethods.activityCloseAnimation();
                    break;
                case 55:

                    break;

                case 70:

                    break;
                case 1:
                    finish();

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}