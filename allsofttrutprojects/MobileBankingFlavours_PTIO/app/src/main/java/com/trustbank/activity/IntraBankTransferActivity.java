package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Model.FundTransferSubModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.R;
import com.trustbank.fragment.EnterAmountFragment;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.interfaces.GetAmountInterface;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
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

public class IntraBankTransferActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogOkListener, GetAmountInterface {

    private Spinner spinnerFrmAct;
    private Spinner spinnerToBeneficery;
    private EditText etAccountName;
    private EditText etAccountNo, etRemarks;
    private CoordinatorLayout coordinatorLayout;
    private TrustMethods trustMethods;
    ArrayList<GetUserProfileModal> accountsArrayList;
    List<String> accountList;
    List<String> beneficiaryList;
    private LinearLayout setFundTransferLayout;
    ArrayList<BeneficiaryModal> beneficiaryArrayList;
    private TextView txtToBenName, txtToAccNo, txtToAccName;
    ArrayList<BeneficiaryModal> beneficiaryModalsSortedData;
    private String benId = "";
    private String accountNo;
    private String toBeneficiaryName;
    private String remitterAccName;
    private String beneficiaryNickName;
    private LinearLayout formLayoutFundTransfer;
    private AlertDialogOkListener alertDialogOkListener = this;
    private GetAmountInterface getAmountInterface = this;
    private TextView toolbar;
    private ImageView backButton_new;

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
                        TrustMethods.naviagteToSplashScreen(IntraBankTransferActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(IntraBankTransferActivity.this, false);
        setContentView(R.layout.activity_intra_bank_transfer);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.IntraBankTransfer);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

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
            backButton_new = findViewById(R.id.backButton_new);
            backButton_new.setOnClickListener(this);

            trustMethods = new TrustMethods(IntraBankTransferActivity.this);
            coordinatorLayout = findViewById(R.id.coordinatorLayout);
            spinnerFrmAct = findViewById(R.id.spinnerFrmAct);
            spinnerToBeneficery = findViewById(R.id.spinnerToBeneficery);
            etAccountName = findViewById(R.id.etAccountName);
            etAccountNo = findViewById(R.id.etAccountNo);
            txtToBenName = findViewById(R.id.txtToBenNameId);
            txtToAccNo = findViewById(R.id.txtToAccNoId);
            txtToAccName = findViewById(R.id.txtToAccNameId);
            setFundTransferLayout = findViewById(R.id.setFundTransferLayoutId);
            formLayoutFundTransfer = findViewById(R.id.formLayoutFundTransferId);
            etRemarks = findViewById(R.id.etRemarks);


            Button btnWithBankNext = findViewById(R.id.btnWithBankNextId);

            btnWithBankNext.setOnClickListener(this);

            setAccountNoSpinner();
            setBeneficiarySpinner();
            getSpinnerData();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAccountNoSpinner() {
        try {
            accountsArrayList = trustMethods.getArrayList(IntraBankTransferActivity.this, "AccountListPref");

            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                accountList = new ArrayList<>();
                accountList.add(0, getResources().getString(R.string.error_select_acc_no));
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (TrustMethods.isAccountTypeValid(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(IntraBankTransferActivity.this, android.R.layout.simple_spinner_item, accountList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFrmAct.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBeneficiarySpinner() {

        try {
            beneficiaryArrayList =  trustMethods.getBenArrayList(IntraBankTransferActivity.this, "BenAccList");
            beneficiaryList = new ArrayList<>();
            beneficiaryList.add(0, getResources().getString(R.string.Selectben));

            beneficiaryModalsSortedData = new ArrayList<>();
            if (beneficiaryArrayList != null && beneficiaryArrayList.size() > 0) {
                for (int i = 0; i < beneficiaryArrayList.size(); i++) {
                    BeneficiaryModal beneficiaryModal = beneficiaryArrayList.get(i);
                    if (beneficiaryModal.getBenType().equals("1")) {
                        BeneficiaryModal beneficiaryModal1 = new BeneficiaryModal();
                        beneficiaryModal1.setBenId(beneficiaryModal.getBenId());
                        beneficiaryModal1.setBenNickname(beneficiaryModal.getBenNickname());
                        beneficiaryModal1.setBanAccName(beneficiaryModal.getBanAccName());
                        beneficiaryModal1.setBenAccNo(beneficiaryModal.getBenAccNo());
                        beneficiaryModalsSortedData.add(beneficiaryModal1);
                        beneficiaryList.add(beneficiaryModal.getBenNickname());
                    }
                }
            } else {
                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_benef_not_added), coordinatorLayout);
                setFundTransferLayout.setVisibility(View.GONE);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(IntraBankTransferActivity.this, android.R.layout.simple_spinner_item, beneficiaryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerToBeneficery.setAdapter(adapter);

            if (!TextUtils.isEmpty(beneficiaryNickName)) {
                formLayoutFundTransfer.setVisibility(View.GONE);
                setFundTransferLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < beneficiaryModalsSortedData.size(); i++) {
                    if (beneficiaryNickName.equalsIgnoreCase(beneficiaryModalsSortedData.get(i).getBenNickname())) {
                        benId = beneficiaryModalsSortedData.get(i).getBenId(); //Added for trnasfer fund direct from manage beneficiary
                        txtToBenName.setText(beneficiaryModalsSortedData.get(i).getBenNickname());
                        txtToAccName.setText(beneficiaryModalsSortedData.get(i).getBanAccName());
                        txtToAccNo.setText(beneficiaryModalsSortedData.get(i).getBenAccNo());
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
        spinnerFrmAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != 0) {
                        accountNo = TrustMethods.getValidAccountNo((String) parent.getItemAtPosition(position));
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerToBeneficery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != 0) {
                        toBeneficiaryName = (String) parent.getItemAtPosition(position);
                        benId = beneficiaryModalsSortedData.get(position - 1).getBenId();
                        String strAcctName = beneficiaryModalsSortedData.get(position - 1).getBanAccName();
                        String strAcctNumber = beneficiaryModalsSortedData.get(position - 1).getBenAccNo();
                        TrustMethods.LogMessage("strAcctNumber", strAcctNumber);
                        etAccountName.setText(strAcctName);
                        etAccountNo.setText(strAcctNumber);
                        etAccountName.setClickable(false);
                        etAccountNo.setClickable(false);

                    } else {
                        etAccountNo.setText("");
                        etAccountName.setText("");
                        etAccountName.setClickable(true);
                        etAccountNo.setClickable(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.backButton_new:
                Intent intent = new Intent(IntraBankTransferActivity.this, TransactionMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.btnWithBankNextId:

                FragmentManager manager = getSupportFragmentManager();
                DialogFragment newFragment = new EnterAmountFragment(IntraBankTransferActivity.this,getAmountInterface,etAccountNo.getText().toString());
                newFragment.show(manager, "dialog");
                break;

            default:
                break;
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        try {
            switch (resultCode) {

                case 0:
                    Intent intent = new Intent(IntraBankTransferActivity.this, LockActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    trustMethods.activityCloseAnimation();
                    break;

                case 55:

                    break;

                case 70:


                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(IntraBankTransferActivity.this);
    }

    @Override
    public void getAmount(String amount, String remark) {
            TrustMethods.hideSoftKeyboard(IntraBankTransferActivity.this);
        try {
                if (spinnerFrmAct.getSelectedItem().equals(getResources().getString(R.string.error_select_acc_no))) {
                    AlertDialogMethod.alertDialogOk(IntraBankTransferActivity.this," ", getResources().getString(R.string.error_select_acc_debit_no),getResources().getString(R.string.btn_ok), 70, false, alertDialogOkListener);
                } else if (spinnerToBeneficery.getSelectedItem().equals(getResources().getString(R.string.Selectben))) {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_select_to_benf), coordinatorLayout);
                } else if (TextUtils.isEmpty(etAccountName.getText().toString())) {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_enter_ben_acc_nm), coordinatorLayout);
                } else if (TextUtils.isEmpty(etAccountNo.getText().toString())) {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_enter_ben_acc_no), coordinatorLayout);
                } else if (TextUtils.isEmpty(amount)) {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_blank_amt), coordinatorLayout);
                } else if (TrustMethods.isAmoutLessThanZero(amount)) {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_valid_amt), coordinatorLayout);
                }  else {
                    String benAccName = etAccountName.getText().toString().trim();
                    String benAccNo = etAccountNo.getText().toString().trim();

                    List<FundTransferSubModel> fundTransferSubModalList = new ArrayList<>();
                    FundTransferSubModel fundTransferSubModel = new FundTransferSubModel();
                    fundTransferSubModel.setAccNo(accountNo);
                    fundTransferSubModel.setToBenName(toBeneficiaryName);
                    fundTransferSubModel.setRemitterAccName(remitterAccName);
                    fundTransferSubModel.setBenId(benId);
                    fundTransferSubModel.setBenAccName(benAccName);
                    fundTransferSubModel.setBenAccNo(benAccNo);
                    fundTransferSubModel.setAmt(amount);
                    fundTransferSubModel.setRemark(remark);
                    fundTransferSubModalList.add(fundTransferSubModel);

                    checkAmountValidation(amount, accountNo, fundTransferSubModalList);

                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAmountValidation(String amount, String accountNo, List<FundTransferSubModel> fundTransferSubModalList) {
        if (NetworkUtil.getConnectivityStatus(IntraBankTransferActivity.this)) {
            new WithinBankAddValidationAsyncTask(IntraBankTransferActivity.this, accountNo, amount, fundTransferSubModalList).execute();
        } else {
            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
        }
    }

    private class WithinBankAddValidationAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String finalResponse;
        ProgressDialog pDialog;
        String response;
        String actionName = "VALIDATE_FT";
        String result;
        private String errorCode;
        private String accountNo;
        private String otpActionName;
        private String amount;
        private List<FundTransferSubModel> fundTransferSubModalList;

        public WithinBankAddValidationAsyncTask(Context ctx, String accountNo, String amount,
                                                List<FundTransferSubModel> fundTransferSubModalList) {
            this.ctx = ctx;
            this.otpActionName = actionName;
            this.accountNo = accountNo;
            this.amount = amount;
            this.fundTransferSubModalList = fundTransferSubModalList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(IntraBankTransferActivity.this);
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
                jsonObject.put("trf_type", 1);
                jsonObject.put("ben_id", fundTransferSubModalList.get(0).getBenId());
                jsonObject.put("ben_ifsc", "");
                jsonObject.put("ben_ac_no", fundTransferSubModalList.get(0).getBenAccNo());
                jsonObject.put("amount", fundTransferSubModalList.get(0).getAmt());

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonObject.toString(), actionName, AppConstants.getAuth_token());

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
                        AlertDialogMethod.alertDialogOk(IntraBankTransferActivity.this, getResources().getString(R.string.error_session_expire),
                                "",
                                getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(IntraBankTransferActivity.this, " ",
                                this.error,
                                getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }

                } else {
                    //response..
                    Intent intent = new Intent(IntraBankTransferActivity.this, OtpVerificationActivity.class);
                    intent.putExtra("checkTransferType", "withinBank");
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
}