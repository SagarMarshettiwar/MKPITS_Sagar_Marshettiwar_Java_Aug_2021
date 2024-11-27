package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.Model.NomineeModel;
import com.trustbank.Model.RelationModel;
import com.trustbank.R;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NomineeActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogOkListener, AdapterView.OnItemSelectedListener
{
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout setGuardianLayoutId;
    RecyclerView recyclerViewHoriListId;
    Spinner spinnerForAcc,spinnerNominee,spinnerRelation,spinnerNomineeAction, spinnerGuardianRelation;
    Button btnSaveNominee;
    TextView textNominee;
    private EditText etName1,etName2,etName3,etDOB,etNomineeCity,etNomineePercent,etGuardianName,etGuardianAddress;
    private AlertDialogOkListener alertDialogOkListener = this;
    private List<String> accountList;

    private List<String> actionList;

    private List<String> relationList;
    private List<String> nomineeList;
    private ArrayList<NomineeModel> nomineeArrayList;
    private ArrayList<RelationModel> relationArrayList;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    double percentSum = 0;
    TrustMethods methods;

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
                        TrustMethods.naviagteToSplashScreen(NomineeActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(NomineeActivity.this, false);
        setContentView(R.layout.activity_nominee);

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
        generateId();

        if (NetworkUtil.getConnectivityStatus(NomineeActivity.this)) {
            new GetRelationAsyncTask(this).execute();
        } else {
            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
        }

        registerEvent();
    }

    private void registerEvent()
    {
        try
        {
            etDOB.addTextChangedListener(new TextWatcher()
            {
                public void onTextChanged(CharSequence s, int start, int before,int count)
                {
                    if(!s.equals("") &&  etDOB.getText().toString().length()>0)
                    {
                        String dt=etDOB.getText().toString();
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        cal.add(Calendar.DAY_OF_YEAR, -6574);
                        String dateTo =  dateFormat.format(new Date(cal.getTimeInMillis()));

                        if(TrustMethods.isFromDateGreaterThanToDate(dt.trim(), dateTo.trim()))
                        {
                            //Toast.makeText(NomineeActivity.this, "Minor", Toast.LENGTH_SHORT).show();
                            setGuardianLayoutId.setVisibility(LinearLayout.VISIBLE);
                        }
                        else
                        {
                            //Toast.makeText(NomineeActivity.this, "Adult", Toast.LENGTH_SHORT).show();
                            setGuardianLayoutId.setVisibility(LinearLayout.GONE);
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });

            btnSaveNominee.setOnClickListener(view ->
            {
                int nomineeAction = spinnerNomineeAction.getSelectedItemPosition();
                if(nomineeAction==2)
                    CalculatePercent(spinnerNominee.getSelectedItem().toString());
                TrustMethods.hideSoftKeyboard(NomineeActivity.this);
                if (spinnerForAcc.getSelectedItem().equals("Select Account Number")) {
                    AlertDialogMethod.alertDialogOk(NomineeActivity.this," ", getResources().getString(R.string.error_select_to_acc_no),getResources().getString(R.string.btn_ok), 70, false, alertDialogOkListener);
                }
                else if (spinnerNomineeAction.getSelectedItem().equals("Select Action")) {
                    TrustMethods.showSnackBarMessage("Please Select Nominee Action", coordinatorLayout);
                }
                else if (spinnerNomineeAction.getSelectedItemPosition()>1 && spinnerNominee.getSelectedItem().equals("Select Nominee")) {
                    TrustMethods.showSnackBarMessage("Please Select Nominee", coordinatorLayout);
                }
                else if (nomineeAction==1 && TextUtils.isEmpty(etName1.getText().toString())) {
                    TrustMethods.showSnackBarMessage("Please Enter Nominee First Name", coordinatorLayout);
                }
                else if (nomineeAction==1 && TextUtils.isEmpty(etName2.getText().toString())) {
                    TrustMethods.showSnackBarMessage("Please Enter Nominee Middle Name", coordinatorLayout);
                }
                else if (nomineeAction==1 && TextUtils.isEmpty(etName3.getText().toString())) {
                    TrustMethods.showSnackBarMessage("Please Enter Nominee Last Name", coordinatorLayout);
                }
                else if ((nomineeAction==1 || nomineeAction==2) && (TextUtils.isEmpty(etDOB.getText().toString()) || etDOB.getText().toString().equals("null"))) {
                    TrustMethods.showSnackBarMessage("Please Enter Date Of Birth", coordinatorLayout);
                } else if (nomineeAction==1 && TextUtils.isEmpty(etNomineeCity.getText().toString())) {
                    TrustMethods.showSnackBarMessage("Please Enter City", coordinatorLayout);
                } else if (TrustMethods.isAmoutLessThanZero(etNomineePercent.getText().toString().trim())) {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_valid_percent), coordinatorLayout);
                } else if (Double.parseDouble(etNomineePercent.getText().toString().trim())>100 ||
                        ((nomineeAction==1 || nomineeAction==2) && (Double.parseDouble(etNomineePercent.getText().toString().trim())+percentSum)>100)) {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_valid_percent), coordinatorLayout);
                }
                else if(nomineeAction==1 && percentSum >100)
                {
                    TrustMethods.showSnackBarMessage("Nominee already added with 100%", coordinatorLayout);
                }
                else
                {
                    String srNo = "";
                    String nominee="";
                    if (nomineeAction>1)
                    {
                        srNo = nomineeArrayList.get(spinnerNominee.getSelectedItemPosition()-1).getSrNo();
                        nominee = spinnerNominee.getSelectedItem().toString();
                    }
                    String accountNo = accountsArrayList.get(spinnerForAcc.getSelectedItemPosition()-1).getAccNo();

                    String name1 = etName1.getText().toString().trim();
                    String name2 = etName2.getText().toString().trim();
                    String name3 = etName3.getText().toString().trim();
                    String dob = etDOB.getText().toString().trim();
                    String nomineeCity = etNomineeCity.getText().toString().trim();
                    String nomineePercent = etNomineePercent.getText().toString().trim();
                    String nomineeRelation = spinnerRelation.getSelectedItem().toString();
                    String guardianName = etGuardianName.getText().toString();
                    String guardianAddress = etGuardianAddress.getText().toString();
                    String guardianRelation = spinnerGuardianRelation.getSelectedItem().toString();
                    String relationCode = relationArrayList.get(spinnerRelation.getSelectedItemPosition()-1).getRelationCode();
                    String guardianRelationCode="";
                    if(guardianName.length()>0)
                        guardianRelationCode =relationArrayList.get(spinnerGuardianRelation.getSelectedItemPosition()-1).getRelationCode();

                    if (NetworkUtil.getConnectivityStatus(NomineeActivity.this)) {
                        new SaveNomineeAsyncTask(this,srNo,accountNo,nominee,nomineeAction,name1,name2,name3,dob,nomineeCity,nomineePercent,nomineeRelation,relationCode,guardianName,guardianAddress,guardianRelation,guardianRelationCode).execute();
                    } else {
                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateId()
    {
        try
        {
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            methods = new TrustMethods(NomineeActivity.this);
            setGuardianLayoutId= findViewById(R.id.setGuardianLayoutId);
            coordinatorLayout = findViewById(R.id.coordinatorLayout);
            textNominee = findViewById(R.id.textNominee);
            spinnerForAcc = findViewById(R.id.spinnerForAcc);
            spinnerRelation = findViewById(R.id.spinnerRelation);
            spinnerGuardianRelation = findViewById(R.id.spinnerGuardianRelation);
            spinnerNominee = findViewById(R.id.spinnerNominee);
            spinnerNomineeAction = findViewById(R.id.spinnerNomineeAction);
            etName1 = findViewById(R.id.etName1);
            etName2 = findViewById(R.id.etName2);
            etName3 = findViewById(R.id.etName3);
            etDOB = findViewById(R.id.etDOB);
            etNomineeCity = findViewById(R.id.etNomineeCity);
            etNomineePercent = findViewById(R.id.etNomineePercent);
            etGuardianName = findViewById(R.id.etGuardianName);
            etGuardianAddress = findViewById(R.id.etGuardianAddress);
            btnSaveNominee = findViewById(R.id.btnSaveNominee);
            horizontalRecyclerView();
            etDOB.setOnClickListener(NomineeActivity.this);

            actionList=new ArrayList<>();
            actionList.add("Select Action");
            actionList.add("Add Nominee");
            actionList.add("Update Nominee");
            actionList.add("Remove Nominee");

            ArrayAdapter<String> actionAdapter = new ArrayAdapter<>(NomineeActivity.this, android.R.layout.simple_spinner_item,
                    actionList);
            actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerNomineeAction.setAdapter(actionAdapter);

            spinnerNomineeAction.setOnItemSelectedListener(NomineeActivity.this);

            accountNoSpinner();
            //prepareRelationSpinner();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void accountNoSpinner() {
        try {
            accountsArrayList = methods.getArrayList(NomineeActivity.this, "AccountListPref");

            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                accountList = new ArrayList<>();
                accountList.add(0, "Select Account Number");
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);

                    if (TrustMethods.isAccountTypeValid(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo + " - " + accTypeCode);
                    }
                }
                ArrayAdapter<String> accountAdapter = new ArrayAdapter<>(NomineeActivity.this, android.R.layout.simple_spinner_item,
                        accountList);
                accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerForAcc.setAdapter(accountAdapter);
                spinnerForAcc.setOnItemSelectedListener(NomineeActivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(NomineeActivity.this, MenuActivity.class);
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
        TrustMethods.showBackButtonAlert(NomineeActivity.this);
    }

    @Override
    public void onDialogOk(int resultCode)
    {
        try {
            if (resultCode == 0) {
                Intent intent = new Intent(NomineeActivity.this, LockActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                methods.activityCloseAnimation();
            }else if(resultCode == 55){
            }else if(resultCode == 70){
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        if(adapterView.getId()==R.id.spinnerNomineeAction)
        {
            clearFields();
            spinnerNominee.setSelection(0);
            if(spinnerNomineeAction.getSelectedItem().toString().equals("Select Action"))
            {
                //btnSaveNominee.setText("Save");
                spinnerNominee.setVisibility(Spinner.GONE);
                textNominee.setVisibility(TextView.GONE);
            }
            else if(spinnerNomineeAction.getSelectedItem().toString().equals("Add Nominee"))
            {
                if(nomineeArrayList!=null) {
                    btnSaveNominee.setText("Save Nominee");
                    percentSum=0;
                    if (nomineeArrayList.size() > 0) {
                        for (int cnt = 0; cnt < nomineeArrayList.size(); cnt++) {

                            percentSum = percentSum + Double.parseDouble(nomineeArrayList.get(cnt).getNomineePercentage());
                        }
                    }

                    if (percentSum < 100) {
                        spinnerNominee.setVisibility(Spinner.GONE);
                        textNominee.setVisibility(TextView.GONE);
                    } else
                        TrustMethods.showSnackBarMessage("Nominee already added with 100%", coordinatorLayout);
                }
            }
            else if(spinnerNomineeAction.getSelectedItem().toString().equals("Update Nominee"))
            {
                if(nomineeList!=null) {
                    btnSaveNominee.setText("Update Nominee");
                    spinnerNominee.setVisibility(Spinner.VISIBLE);
                    textNominee.setVisibility(TextView.VISIBLE);
                }
                else
                    TrustMethods.showSnackBarMessage("Nominee Not Added", coordinatorLayout);
            }
            else
            {
                if(nomineeList!=null) {
                    btnSaveNominee.setText("Remove Nominee");
                    spinnerNominee.setVisibility(Spinner.VISIBLE);
                    textNominee.setVisibility(TextView.VISIBLE);
                }
                else
                    TrustMethods.showSnackBarMessage("Nominee Not Added", coordinatorLayout);
            }
        }
        else if(adapterView.getId()==R.id.spinnerForAcc)
        {
            nomineeArrayList=new ArrayList<>();
            nomineeList=new ArrayList<>();
            if(!spinnerForAcc.getSelectedItem().toString().equals("Select Account Number"))
            {
                percentSum = 0;

                String accountNo=accountsArrayList.get(spinnerForAcc.getSelectedItemPosition()-1).getAccNo();// spinnerForAcc.getSelectedItem().toString();
                if (NetworkUtil.getConnectivityStatus(NomineeActivity.this)) {
                    new GetNomineeAsyncTask(this,accountNo,4).execute();

                } else {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                }
            }
        }
        else if(adapterView.getId()==R.id.spinnerNominee)
        {
            if (!spinnerNominee.getSelectedItem().toString().equals("Select Nominee"))
            {
                NomineeModel nomineeModel=nomineeArrayList.get(spinnerNominee.getSelectedItemPosition()-1);
                if(!nomineeModel.getNomineeDob().equals("null"))
                    etDOB.setText(nomineeModel.getNomineeDob());
                etName1.setText(nomineeModel.getName1());
                etName2.setText(nomineeModel.getName2());
                etName3.setText(nomineeModel.getName3());
                etNomineeCity.setText(nomineeModel.getNomineeCity());
                etNomineePercent.setText(nomineeModel.getNomineePercentage());
                int position=0;
                for(int in=0;in<relationArrayList.size();in++)
                {
                    Log.e("NOMINEE_TAG",nomineeModel.getNomineeRelation()+" : "+relationArrayList.get(in).getRelation());
                    if(nomineeModel.getNomineeRelation().equals(relationArrayList.get(in).getRelation()))
                    {
                        position=in;
                        break;
                    }
                }
                spinnerRelation.setSelection(position+1);

                String dt=nomineeModel.getNomineeDob();
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                cal.add(Calendar.DAY_OF_YEAR, -6574);
                String dateTo =  dateFormat.format(new Date(cal.getTimeInMillis()));

                if(TrustMethods.isFromDateGreaterThanToDate(dt.trim(), dateTo.trim()))
                {
                    setGuardianLayoutId.setVisibility(LinearLayout.VISIBLE);
                    etGuardianName.setText(nomineeModel.getNomineeGuarName());
                    etGuardianAddress.setText(nomineeModel.getNomineeGuarAdd());
                    position=0;
                    for(int in=0;in<relationArrayList.size();in++)
                    {
                        if(nomineeModel.getNomineeGuarRelation().equals(relationArrayList.get(in).getRelation()))
                        {
                            position=in;
                            break;
                        }
                    }
                    spinnerGuardianRelation.setSelection(position);
                }
                else
                {
                    setGuardianLayoutId.setVisibility(LinearLayout.GONE);
                    etGuardianName.setText("");
                    etGuardianAddress.setText("");
                    spinnerGuardianRelation.setSelection(0);
                }

            }
            else {
                clearFields();
            }
        }
        else if(adapterView.getId()==R.id.spinnerRelation)
        {
            if (!spinnerRelation.getSelectedItem().toString().equals("Select Relation")
                    && spinnerNomineeAction.getSelectedItem().toString().equals("Add Nominee"))
            {
                for(int j=0;j<nomineeArrayList.size();j++)
                {
                    if(spinnerRelation.getSelectedItem().toString().equals(nomineeArrayList.get(j).getNomineeRelation()))
                    {
                        TrustMethods.showSnackBarMessage("Relation Already Selected", coordinatorLayout);
                        spinnerRelation.setSelection(0);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.etDOB)
        {
            methods.datePicker(NomineeActivity.this, etDOB);
        }
    }

    private void clearFields()
    {
        etDOB.setText("");
        etName1.setText("");
        etName2.setText("");
        etName3.setText("");
        etNomineeCity.setText("");
        etNomineePercent.setText("");
        etGuardianName.setText("");
        etGuardianAddress.setText("");
        spinnerRelation.setSelection(0);
        spinnerGuardianRelation.setSelection(0);
    }

    private void CalculatePercent(String nominee)
    {
        percentSum=0;
        for(int i=0;i<nomineeArrayList.size();i++)
        {
            if(!nominee.equals(nomineeArrayList.get(i).getName1()+" "+nomineeArrayList.get(i).getName2()+" "+nomineeArrayList.get(i).getName3()))
                percentSum=percentSum+Double.parseDouble(nomineeArrayList.get(i).getNomineePercentage());
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetRelationAsyncTask extends AsyncTask<Void, Void, String> {
        private String error = "";
        private Context ctx;
        private String response;
        private ProgressDialog pDialog;
        private String result;
        private String actionName = "RELATION_DATA";
        private String errorCode;
        private String operationType="";

        public GetRelationAsyncTask(Context ctx) {
            this.ctx=ctx;
            operationType="0";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NomineeActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                String url = TrustURL.MobileNoVerifyUrl();
                String jsonString = "{\"operation_type\":\"" + operationType + "\"}";
                TrustMethods.LogMessage("", "json string for fetch nominee : " + jsonString);

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, actionName, AppConstants.getAuth_token());
                }

                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));
                TrustMethods.LogMessage("", "jsonResponse json string for fetch nominee : " + jsonResponse);
                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1"))
                {
                    JSONObject responseObject = jsonResponse.getJSONObject("response");
                    if (responseObject.has("error")) {
                        error = responseObject.getString("error");
                        return error;
                    }

                    JSONArray jsonArray = responseObject.getJSONArray("relation_list");
                    if (jsonArray.length() > 0)
                    {
                        relationArrayList=new ArrayList<>();
                        relationList=new ArrayList<>();
                        relationList.add("Select Relation");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject dataJsonObject = jsonArray.getJSONObject(i);
                            RelationModel relationModel = new RelationModel();
                            relationModel.setRelation(dataJsonObject.has("Name") ? dataJsonObject.getString("Name") : "NA");
                            relationModel.setRelationCode(dataJsonObject.has("relationcode") ? dataJsonObject.getString("relationcode") : "NA");
                            relationArrayList.add(relationModel);
                            relationList.add(relationModel.getRelation());
                        }
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
        protected void onPostExecute(String value)
        {
            super.onPostExecute(value);
            try
            {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }
                    if (!TextUtils.isEmpty(error) && TrustMethods.isSessionExpiredWithString(error)) {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.lbl_manage_nominee), this.error, getResources().getString(R.string.btn_ok),
                                2, false, alertDialogOkListener);
                    }
                } else {
                    ArrayAdapter<String> relationAdapter = new ArrayAdapter<>(NomineeActivity.this, android.R.layout.simple_spinner_item,
                            relationList);
                    relationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRelation.setAdapter(relationAdapter);
                    spinnerGuardianRelation.setAdapter(relationAdapter);
                    spinnerRelation.setOnItemSelectedListener(NomineeActivity.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetNomineeAsyncTask extends AsyncTask<Void, Void, String> {
        private String error = "";
        private Context ctx;
        private String response;
        private ProgressDialog pDialog;
        private String result;
        private int operationType;
        private String accNo;
        private String actionName = "NOMINEE_DATA";
        private String errorCode;

        public GetNomineeAsyncTask(Context ctx, String accNo, int operationType) {
            this.ctx=ctx;
            this.accNo = accNo;
            this.operationType = operationType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NomineeActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                String url = TrustURL.MobileNoVerifyUrl();
                String jsonString = "{\"account_number\":\"" + accNo + "\", \"operation_type\":\"" + operationType + "\"}";
                TrustMethods.LogMessage("", "json string for fetch nominee : " + jsonString);

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, actionName, AppConstants.getAuth_token());
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

                if (responseCode.equals("1"))
                {
                    JSONObject responseObject = jsonResponse.getJSONObject("response");
                    if (responseObject.has("error")) {
                        error = responseObject.getString("error");
                        return error;
                    }

                    JSONArray jsonArray = responseObject.getJSONArray("nominee_list");
                    if (jsonArray.length() > 0)
                    {
                        nomineeList.add("Select Nominee");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject dataJsonObject = jsonArray.getJSONObject(i);

                            NomineeModel nomineeModal = new NomineeModel();
                            nomineeModal.setSrNo(dataJsonObject.has("SrNo") ? dataJsonObject.getString("SrNo") : "NA");
                            nomineeModal.setNomineeId(dataJsonObject.has("NomineeId") ? dataJsonObject.getString("NomineeId") : "NA");
                            nomineeModal.setName1(dataJsonObject.has("name1") ? dataJsonObject.getString("name1") : "NA");
                            nomineeModal.setName2(dataJsonObject.has("name2") ? dataJsonObject.getString("name2") : "NA");
                            nomineeModal.setName3(dataJsonObject.has("name3") ? dataJsonObject.getString("name3") : "NA");
                            nomineeModal.setNomineeCity(dataJsonObject.has("cityname") ? dataJsonObject.getString("cityname") : "NA");
                            nomineeModal.setNomineeDob(dataJsonObject.has("dateofbirth") ? dataJsonObject.getString("dateofbirth") : "NA");
                            nomineeModal.setNomineePercentage(dataJsonObject.has("Percentage") ? dataJsonObject.getString("Percentage") : "NA");
                            nomineeModal.setNomineeRelation(dataJsonObject.has("relation") ? dataJsonObject.getString("relation") : "NA");
                            nomineeModal.setNomineeGuarName(dataJsonObject.has("NomineeGuarName") ? dataJsonObject.getString("NomineeGuarName") : "NA");
                            nomineeModal.setNomineeGuarAdd(dataJsonObject.has("NomineeGuarAdd") ? dataJsonObject.getString("NomineeGuarAdd") : "NA");
                            nomineeModal.setNomineeGuarRelation(dataJsonObject.has("NomineeGuarRelation") ? dataJsonObject.getString("NomineeGuarRelation") : "NA");
                            nomineeModal.setNomineeGuarRelationCode(dataJsonObject.has("NomineeGuarRelationCode") ? dataJsonObject.getString("NomineeGuarRelationCode") : "NA");

                            nomineeArrayList.add(nomineeModal);
                            nomineeList.add(nomineeModal.getName1()+" "+nomineeModal.getName2()+" "+nomineeModal.getName3());
                        }
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
        protected void onPostExecute(String value)
        {
            super.onPostExecute(value);
            try
            {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }
                    if (!TextUtils.isEmpty(error) && TrustMethods.isSessionExpiredWithString(error)) {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.lbl_manage_nominee), this.error, getResources().getString(R.string.btn_ok),
                                2, false, alertDialogOkListener);
                    }
                } else {
                    if(nomineeList!=null && nomineeList.size()>0) {
                        ArrayAdapter<String> nomineeAdapter = new ArrayAdapter<>(NomineeActivity.this, android.R.layout.simple_spinner_item,
                                nomineeList);
                        nomineeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerNominee.setAdapter(nomineeAdapter);

                        if (nomineeList.size() == 1)
                            spinnerNomineeAction.setSelection(1);
                        spinnerNominee.setOnItemSelectedListener(NomineeActivity.this);
                        if (spinnerNomineeAction.getSelectedItemPosition() == 0 || spinnerNomineeAction.getSelectedItemPosition() == 1) {
                            spinnerNominee.setVisibility(Spinner.GONE);
                            textNominee.setVisibility(TextView.GONE);
                        }
                    }
                    else
                    {
                        TrustMethods.showSnackBarMessage("Nominee Not Added", coordinatorLayout);
                        spinnerNominee.setVisibility(Spinner.GONE);
                        textNominee.setVisibility(TextView.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveNomineeAsyncTask extends AsyncTask<Void, Void, String> {

        private String error = "";
        private Context ctx;
        private String response;
        private ProgressDialog pDialog;
        private String result;
        private int operationType;
        private String accNo, nominee,name1,name2,name3, nomineeDob, nomineeCity, nomineePercent, nomineeRelation,guardianName,
                guardianAddress,guardianRelation,guardianRelationCode,relationCode,srNo;
        private String actionName = "SAVE_NOMINEE";
        private String errorCode;

        public SaveNomineeAsyncTask(Context ctx, String srNo, String accNo, String nominee, int operationType, String name1,String name2,String name3, String nomineeDob, String nomineeCity, String nomineePercent, String nomineeRelation, String relationCode, String guardianName, String guardianAddress, String guardianRelation, String guardianRelationCode) {
            this.ctx=ctx;
            this.srNo = srNo;
            this.accNo = accNo;
            this.nominee = nominee;
            this.operationType = operationType;
            this.name1 = name1;
            this.name2 = name2;
            this.name3 = name3;
            this.nomineeDob = nomineeDob;
            this.nomineeCity = nomineeCity;
            this.nomineePercent = nomineePercent;
            this.nomineeRelation = nomineeRelation;
            this.guardianName = guardianName;
            this.guardianAddress = guardianAddress;
            this.guardianRelation = guardianRelation;
            this.relationCode = relationCode;
            this.guardianRelationCode = guardianRelationCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NomineeActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                String url = TrustURL.MobileNoVerifyUrl();
                JSONObject jObj=new JSONObject();
                jObj.put("sr_no",srNo);
                jObj.put("account_number",accNo);
                jObj.put("operation_type",operationType);
                jObj.put("name1",name1);
                jObj.put("name2",name2);
                jObj.put("name3",name3);
                jObj.put("nominee_dob",nomineeDob);
                jObj.put("nominee_city",nomineeCity);
                jObj.put("nominee_percent",nomineePercent);
                jObj.put("guardian_name",guardianName);
                jObj.put("guardian_address",guardianAddress);
                jObj.put("guardian_relation",guardianRelation);
                jObj.put("guardian_relation_code",guardianRelationCode);
                jObj.put("relation_code",relationCode);
                jObj.put("nominee_relation",nomineeRelation);

                String jsonString = jObj.toString();// "{\"account_number\":\"" + accNo + "\", \"operation_type\":\"" + operationType + "\", \"name1\":\"" + name1 + "\", \"name2\":\"" + name2 + "\", \"name3\":\"" + name3 + "\", \"nominee_dob\":\"" + nomineeDob + "\", \"nominee_city\":\"" + nomineeCity + "\",\"nominee_percent\":\""+nomineePercent+"\",\"guardian_name\"\""+guardianName+"\",\"guardian_address\":\""+guardianAddress+"\",\"guardian_relation\":\""+guardianRelation+"\",\"guardian_relation_code\":\""+guardianRelationCode+"\",\"relation_code\":\""+relationCode+"\",\"nominee_relation\":\""+nomineeRelation+"\"}";
                TrustMethods.LogMessage("", "json string for save nominee : " + jsonString);

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, actionName, AppConstants.getAuth_token());
                }

                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));
                TrustMethods.LogMessage("", "response json string for save nominee : " + jsonResponse.toString());
                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1"))
                {
                    if(operationType==1)
                        response="Nominee Added Successfully";
                    else if(operationType==2)
                        response="Nominee Updated Successfully";
                    if(operationType==3)
                        response="Nominee Removed Successfully";


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
        protected void onPostExecute(String value)
        {
            super.onPostExecute(value);
            try
            {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }
                    if (!TextUtils.isEmpty(error) && TrustMethods.isSessionExpiredWithString(error)) {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.lbl_manage_nominee), this.error, getResources().getString(R.string.btn_ok),
                                2, false, alertDialogOkListener);
                    }
                } else {
                    AlertDialogMethod.alertDialogOk(NomineeActivity.this, getResources().getString(R.string.lbl_manage_nominee), this.response, getResources().getString(R.string.btn_ok),
                            1, false, alertDialogOkListener);
                    spinnerForAcc.setSelection(0);
                    spinnerNomineeAction.setSelection(0);
                    spinnerNominee.setSelection(0);
                    clearFields();
                    if (NetworkUtil.getConnectivityStatus(NomineeActivity.this)) {
                        new GetNomineeAsyncTask(NomineeActivity.this,accNo,4).execute();
                    } else {
                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        methods.horizontalRecyclerView(NomineeActivity.this, recyclerViewHoriListId);
    }
}