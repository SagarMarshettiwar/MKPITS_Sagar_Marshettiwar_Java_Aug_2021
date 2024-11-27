package com.trustbank.activity;

import static com.trustbank.util.TrustMethods.LogMessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.AccountDetailsModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.Model.OldJoinListModel;
import com.trustbank.Model.ViewJoinModel;
import com.trustbank.R;
import com.trustbank.dialog.ViewOldJoineeDialog;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrmClientManagementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    TrustMethods trustMethods;
    CoordinatorLayout coordinatorLayout;
    private Spinner spinnerFrmAct, spin_acc_open_type, spinnerBranch, categorySpinner,ind_modeofoperation_spinner, nonind_modeofoperation_spinner;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    private RadioGroup radio_grp;
    String accountNo="", cgltype="", branchId="", selectedCategory="", modeofoperation="", balance="";
    private LinearLayout ll_acc_open, ll_branch,ll_category,ll_modeofoperation, joinDtlLayout;
    private EditText jedcustomerid;
    private Button btn_add_join, btn_view_join, btn_save, btn_cancel;
    ArrayList<String> branchlist=new ArrayList<>();
    HashMap<String,String> branchmap=new HashMap<>();
    ArrayList<String> categorylist=new ArrayList<>();
    HashMap<String,String> categorymap=new HashMap<>();
    ArrayList<String> modeofoperationlist=new ArrayList<>();
    HashMap<String,String> modeofoperationmap =new HashMap<>();
    private ArrayList<OldJoinListModel> jointList;
    ArrayList<ViewJoinModel> viewJoinList;
    boolean custFound=false, joinRadioChecked=false;

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
                        TrustMethods.naviagteToSplashScreen(FrmClientManagementActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(FrmClientManagementActivity.this, false);
        setContentView(R.layout.activity_frm_client_management);
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        trustMethods = new TrustMethods(FrmClientManagementActivity.this);
        trustMethods.activityOpenAnimation();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        spinnerFrmAct = findViewById(R.id.spinnerFrmAct);
        ll_acc_open = findViewById(R.id.ll_acc_open);
        spin_acc_open_type = findViewById(R.id.spin_acc_open_type);
        radio_grp = findViewById(R.id.radio_grp);
        ll_branch = findViewById(R.id.ll_branch);
        spinnerBranch = findViewById(R.id.spinnerBranch);
        ll_category = findViewById(R.id.ll_category);
        categorySpinner = findViewById(R.id.categorySpinner);
        ll_modeofoperation = findViewById(R.id.ll_modeofoperation);
        ind_modeofoperation_spinner = findViewById(R.id.ind_modeofoperation_spinner);
        nonind_modeofoperation_spinner = findViewById(R.id.nonind_modeofoperation_spinner);
        joinDtlLayout = findViewById(R.id.joinDtlLayout);
        jedcustomerid = findViewById(R.id.jedcustomerid);
        btn_add_join = findViewById(R.id.btn_add_join);
        btn_view_join = findViewById(R.id.btn_view_join);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_add_join.setOnClickListener(this);
        btn_view_join.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        spinnerFrmAct.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        spin_acc_open_type.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ll_acc_open.setVisibility(View.GONE);
        radio_grp.setVisibility(View.GONE);
        ll_branch.setVisibility(View.GONE);
        ll_category.setVisibility(View.GONE);
        ll_modeofoperation.setVisibility(View.GONE);
        joinDtlLayout.setVisibility(View.GONE);
        btn_save.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);

        setAccountNoSpinner();

        radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId != -1){
                    if(!accountNo.isEmpty()){
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                        String branch = radioButton.getText().toString();
                        categorySpinner.setSelection(0);
                        if(branch.equals("Home Branch")){
                            ll_branch.setVisibility(View.GONE);
                            spinnerBranch.setSelection(0);
                            ll_category.setVisibility(View.VISIBLE);
                            new CreateProfileAsyncTask(FrmClientManagementActivity.this, accountNo).execute();

                        }else if(branch.equals("Other Branch")){
                            ll_branch.setVisibility(View.VISIBLE);
                        }
                    }else{
                        TrustMethods.showSnackBarMessage("Select Account Number", coordinatorLayout);
                    }
                }else{
                    ll_branch.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setAccountNoSpinner() {
        try {
            accountsArrayList = trustMethods.getArrayList(FrmClientManagementActivity.this, "AccountListPref");
            List<String> accountList = new ArrayList<>();
            accountList.add(0, "Select Account Number");
            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (TrustMethods.isAccountTypeValidAjara(getUserProfileModal.getActType())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo + " - " + accTypeCode);
                    }
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(FrmClientManagementActivity.this, android.R.layout.simple_spinner_item, accountList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFrmAct.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void accTypeSpinner() {
        List<String> gltype = new ArrayList<>();
        gltype.add("Select Account Opening Type");
        gltype.add("Fixed Deposit");
        gltype.add("Recurring Deposit");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FrmClientManagementActivity.this, android.R.layout.simple_spinner_item, gltype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_acc_open_type.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.spinnerFrmAct:
                try {
                    if (position != 0) {
                        accountNo = TrustMethods.getValidAccountNo((String) parent.getItemAtPosition(position));
                        ll_acc_open.setVisibility(View.VISIBLE);
                        accTypeSpinner();
                    }else{
                        accountNo="";
                        ll_acc_open.setVisibility(View.GONE);
                    }
                    radio_grp.setVisibility(View.GONE);
                    ll_branch.setVisibility(View.GONE);
                    ll_category.setVisibility(View.GONE);
                    ll_modeofoperation.setVisibility(View.GONE);
                    joinDtlLayout.setVisibility(View.GONE);
                    btn_save.setVisibility(View.GONE);
                    btn_cancel.setVisibility(View.GONE);
                    ind_modeofoperation_spinner.setSelection(0);
                    categorySpinner.setSelection(0);
                    spin_acc_open_type.setSelection(0);
                    Log.e("ACCOUNT", position+accountNo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R .id.spin_acc_open_type:
                try {
                    if(position!=0) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                        String selected = (String) parent.getItemAtPosition(position);
                        if(selected.equalsIgnoreCase("Fixed Deposit"))
                        {
                            cgltype="FD";
                        }
                        else if (selected.equalsIgnoreCase("Recurring Deposit"))
                        {
                            cgltype="RD";
                        }

                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FrmClientManagementActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(FrmClientManagementActivity.this)) {
                                new LookUpAsyncTask(FrmClientManagementActivity.this).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(FrmClientManagementActivity.this);
                        }
                        radio_grp.setVisibility(View.VISIBLE);
                    }else {
                        cgltype="";
                        radio_grp.setVisibility(View.GONE);
                    }
                    radio_grp.clearCheck();
                    ll_category.setVisibility(View.GONE);
                    ll_modeofoperation.setVisibility(View.GONE);
                    joinDtlLayout.setVisibility(View.GONE);
                    btn_save.setVisibility(View.GONE);
                    btn_cancel.setVisibility(View.GONE);
                    ind_modeofoperation_spinner.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add_join:
                AddJoinDetails();
                break;

            case R.id.btn_view_join:
                ViewJoinDetails();
                break;

            case R.id.btn_save:
                save();
                break;

            case R.id.btn_cancel:
                Intent intent = new Intent(FrmClientManagementActivity.this, AccountsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    private class LookUpAsyncTask extends AsyncTask<Void, Void, Void> {
        private String _error = "";
        private Context ctx;
        private ProgressDialog pDialog;
        private String result;
        private String action="GET_LOOKUPS";
        JSONObject responseData;
        public LookUpAsyncTask(Context ctx) {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FrmClientManagementActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url= TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);

            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }

            String jsonString = "{\"payload_base64\":\"" + "PGRhdGE+CiAgICA8bG9va3Vwcz4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5NT0RFX09GX09QRVJBVElPTjwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5CUkFOQ0g8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+VElUTEU8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+UklTS19QQVJBTUVURVI8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+U1RBVEU8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+Q0lUWTwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5ET0NfVFlQRV9JRF9QUk9PRjwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5ET0NfVFlQRV9BRERSRVNTX0NPUlJFU1BPTkRFTkNFPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPkRPQ19UWVBFX0FERFJFU1NfUEVSTUFORU5UPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPk5PTl9JTkRJX0RPQ19UWVBFX0FERFJFU1NfUEVSTUFORU5UPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPk5PTl9JTkRJX0RPQ19UWVBFX0FERFJFU1NfQ09SUkVTUE9OREVOQ0U8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+Tk9OX0lORElfRE9DX1RZUEVfSURfUFJPT0Y8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+Tk9OX0lORElfUklTS19QQVJBTUVURVI8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CgkJPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+VFJBTlNGRVJfRlJFUVVFTkNZPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPlBFUklPRF9VTklUPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPlJFTEFUSU9OPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPkZEX1NDSEVNRTwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KCQk8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5SRF9TQ0hFTUU8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CgkJPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+U0JfU0NIRU1FPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPklORElfQ0xJRU5UX0NBVEVHT1JZPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPk5PTl9JTkRJX0NMSUVOVF9DQVRFR09SWTwvY29kZT4KICAgICAgICA8L2xvb2t1cD4JCQogICAgPC9sb29rdXBzPgo8L2RhdGE+" + "\"}";
            try {
                result = HttpClientWrapper.postWithActionAuthToken(url,jsonString,action, AppConstants.getAuth_token());
                Log.e("result",result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if(responseCode.equals("1")){
                    responseData = jsonObject.getJSONObject("response").getJSONObject("data");
                } else {
                    String errorCode = responseData.has("error_code") ? responseData.getString("error_code") : "NA";
                    this._error = responseData.has("error_message") ? responseData.getString("error_message") : "NA";
                }

                if(responseData!=null) {
                    modeofoperationmap=new HashMap<>();
                    JSONArray modeOfOperationArray = responseData.getJSONObject("list_mode_of_operation").getJSONArray("mode_of_operation");
                    modeofoperationlist = new ArrayList<>();
                    modeofoperationlist.add("Please Select");
                    for (int i = 0; i < modeOfOperationArray.length(); i++) {
                        JSONObject modeOfOperation = modeOfOperationArray.getJSONObject(i);
                        String valueField = modeOfOperation.getString("value_field");
                        String textField = modeOfOperation.getString("text_field");

                        modeofoperationlist.add(textField);
                        modeofoperationmap.put(textField,valueField);
                    }

                    JSONArray branchArray = responseData.getJSONObject("list_branch").getJSONArray("branch");
                    branchmap=new HashMap<>();
                    branchlist.add("Please Select");
                    for (int i = 0; i < branchArray.length(); i++) {
                        JSONObject branch = branchArray.getJSONObject(i);
                        String valueField = branch.getString("value_field");
                        String textField = branch.getString("text_field");
                        branchlist.add(textField);
                        branchmap.put(textField, valueField);
                    }

                    JSONArray categoryArray;
                    if(AppConstants.getClient_type().equals("0")) {
                        categoryArray = responseData.getJSONObject("list_indi_client_category").getJSONArray("indiv_category");
                    }else{
                        categoryArray = responseData.getJSONObject("list_non_indi_client_category").getJSONArray("non_indiv_category");
                    }

                    categorymap=new HashMap<>();
                    categorylist= new ArrayList<>();
                    categorylist.add("Please Select");
                    for (int i = 0; i < categoryArray.length(); i++) {
                        JSONObject branch = categoryArray.getJSONObject(i);
                        String valueField = branch.getString("value_field");
                        String textField = branch.getString("text_field");
                        categorylist.add(textField);
                        categorymap.put(textField, valueField);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (this._error != "" && !TextUtils.isEmpty(_error)) {
                    TrustMethods.showSnackBarMessage(this._error, coordinatorLayout);
                    LogMessage("error", this._error);
                    return;
                }
                else {
                    setLookUpSpinners();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class CreateProfileAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        AccountDetailsModel accountDetailsModel;
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "GET_ACCOUNT_DETAILS";
        String mAccNo, result, mOrgelementId;
        ArrayList<AccountDetailsModel> accountDetailsModelArrayList = new ArrayList<>();
        private String errorCode;

        public CreateProfileAsyncTask(Context ctx, String accNo) {
            this.ctx = ctx;
            this.mAccNo = accNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FrmClientManagementActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getAccountDetails(mAccNo);

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
                    JSONArray accountJsonArray = jsonResponse.getJSONObject("response").getJSONArray("account");
                    JSONObject accountJsonObject = accountJsonArray.getJSONObject(0);

                    String mName = accountJsonObject.has("name") ? accountJsonObject.getString("name") : "";    //branch name
                    balance = accountJsonObject.has("balance") ? accountJsonObject.getString("balance") : "";
                    mOrgelementId = accountJsonObject.has("orgelementId") ? accountJsonObject.getString("orgelementId") : "";    //brandID

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
                if(error.isEmpty()){
                    branchId = mOrgelementId;
                }else{
                    TrustMethods.showSnackBarMessage(error, coordinatorLayout);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class GetModeOfOperationAsync extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        private String _error = "",action="GET_MODE_OF_OPERATION",result;
        private Context ctx;
        JSONArray respTableOne;
        String categoryId="";

        public GetModeOfOperationAsync(Context ctx, String categoryId) {
            this.ctx = ctx;
            this.categoryId = categoryId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FrmClientManagementActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url=TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);
            JSONObject jObj=new JSONObject();
            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }
            byte[] data = new byte[0];
            try
            {
                jObj.put("categoryid",categoryId);
                jObj.put("tag","MODEOFOPRETION");
            } catch ( JSONException e) {
                throw new RuntimeException(e);
            }
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            String jsonString = jObj.toString();

            try
            {
                result = HttpClientWrapper.postWithActionAuthToken(url,jsonString,action, AppConstants.getAuth_token());
                Log.e("result",result);
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if(responseCode.equals("1")) {
                    try {
                        respTableOne = jsonObject.getJSONObject("response").getJSONArray("Table");
                    }
                    catch(Exception e)
                    {
                        _error = "Please Select Valid Details";
                    }
                } else {
                    String errorCode = jsonObject.has("error_code") ? jsonObject.getString("error_code") : "NA";
                    _error = jsonObject.has("error_message") ? jsonObject.getString("error_message") : "NA";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if(!_error.equalsIgnoreCase("")){
                TrustMethods.showSnackBarMessage(_error, coordinatorLayout);
            } else {
                try
                {
                    modeofoperationmap.clear();
                    modeofoperationlist.clear();
                    modeofoperationlist.add("Please Select");
                    for (int i = 0; i < respTableOne.length(); i++) {
                        JSONObject modeOfOperation = respTableOne.getJSONObject(i);
                        String valueField = modeOfOperation.getString("codeid");
                        String textField = modeOfOperation.getString("description");
                        modeofoperationlist.add(textField);
                        modeofoperationmap.put(textField,valueField);
                    }
                    if(modeofoperationlist.size()>0)
                    {
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(FrmClientManagementActivity.this, android.R.layout.simple_spinner_item, modeofoperationlist);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        nonind_modeofoperation_spinner.setAdapter(spinnerArrayAdapter);

                        nonind_modeofoperation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try
                                {
                                    if (position != 0)
                                    {
                                        String Select = (String) parent.getItemAtPosition(position);
                                        modeofoperation=modeofoperationmap.get(Select);
                                        Toast.makeText(ctx, modeofoperation+" -- Non_individual", Toast.LENGTH_SHORT).show();
                                        if(nonind_modeofoperation_spinner.getSelectedItem().toString().equalsIgnoreCase("Self"))
                                        {
                                            joinDtlLayout.setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            joinDtlLayout.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            if (this._error != "" && !TextUtils.isEmpty(_error)) {
                Toast.makeText(this.ctx, this._error, Toast.LENGTH_SHORT).show();
                LogMessage("error", this._error);
                return;
            }
        }
    }

    private class GetClientNameAsync extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        private String _error = "",action="GET_CLIENT_NAME",result;
        private Context ctx;
        JSONObject respTableOne;
        String clientId="",nameFor="";

        public GetClientNameAsync(Context ctx, String clientId,String nameFor) {
            this.ctx = ctx;
            this.clientId = clientId;
            this.nameFor = nameFor;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FrmClientManagementActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url=TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);
            JSONObject jObj=new JSONObject();
            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }
            byte[] data = new byte[0];
            try
            {
                jObj.put("client_id", clientId);
                jObj.put("action", "CLIENT");
                jObj.put("client_type",AppConstants.getClient_type());

            } catch ( JSONException e) {
                throw new RuntimeException(e);
            }
            String jsonString = jObj.toString();

            try
            {
                result = HttpClientWrapper.postWithActionAuthToken(url,jsonString,action, AppConstants.getAuth_token());
                Log.e("result",result);
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if(responseCode.equals("1")) {
                    try {
                        respTableOne = jsonObject.getJSONObject("response").getJSONArray("Table").getJSONObject(0);
                    }
                    catch(Exception e)
                    {
                        _error = "Please Select Valid Details";
                    }
                } else {
                    String errorCode = jsonObject.has("error_code") ? jsonObject.getString("error_code") : "NA";
                    _error = jsonObject.has("error_message") ? jsonObject.getString("error_message") : "NA";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if(!_error.equalsIgnoreCase("")){
                TrustMethods.showSnackBarMessage( _error, coordinatorLayout);
            } else {
                try {
                    String item = respTableOne.getString("customer_nm");
                    if(item.indexOf("NA~")>-1)
                    {
                        custFound=false;
                        Toast.makeText(FrmClientManagementActivity.this, item.split("~")[1], Toast.LENGTH_LONG).show();
                    }
                    else if(!item.equalsIgnoreCase("NA"))
                    {
                        custFound=true;
                        OldJoinListModel obj = new OldJoinListModel(clientId, item);
                        jointList.add(obj);
                        jedcustomerid.setText("");
                        Toast.makeText(FrmClientManagementActivity.this, "Joinee Added", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        custFound=false;
                        TrustMethods.showSnackBarMessage("Invalid Customer Id", coordinatorLayout);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void setLookUpSpinners() {
        if(branchlist.size() !=0){
            ArrayAdapter<String> spinnerBranchArrayAdapter = new ArrayAdapter<String>(FrmClientManagementActivity.this, android.R.layout.simple_spinner_item, branchlist);
            spinnerBranchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerBranch.setAdapter(spinnerBranchArrayAdapter);
            spinnerBranch.setSelection(0);
            spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        if (position != 0) {
                            String Select = (String) parent.getItemAtPosition(position);
                            branchId=branchmap.get(Select);
                            ll_category.setVisibility(View.VISIBLE);
                        }else{
                            branchId="";
                            ll_category.setVisibility(View.GONE);
                        }
                        categorySpinner.setSelection(0);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if(categorylist.size()!=0){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(FrmClientManagementActivity.this, android.R.layout.simple_spinner_item, categorylist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);
            categorySpinner.setSelection(0);

            categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        if (position != 0) {
                            String Select = (String) parent.getItemAtPosition(position);
                            selectedCategory=categorymap.get(Select);
                            ll_modeofoperation.setVisibility(View.VISIBLE);
                            btn_save.setVisibility(View.VISIBLE);
                            btn_cancel.setVisibility(View.VISIBLE);
                            jedcustomerid.setText("");
                            if(AppConstants.getClient_type().equals("0")){
                                ind_modeofoperation_spinner.setVisibility(View.VISIBLE);
                                nonind_modeofoperation_spinner.setVisibility(View.GONE);

                                if(categorySpinner.getSelectedItem().toString().equals("INDIVIDUAL")){
                                    ind_modeofoperation_spinner.setSelection(modeofoperationlist.indexOf("SELF"));
                                    ind_modeofoperation_spinner.setEnabled(false);
                                }else{
                                    ind_modeofoperation_spinner.setSelection(0);
                                    ind_modeofoperation_spinner.setEnabled(true);
                                }
                            }else{
                                ind_modeofoperation_spinner.setVisibility(View.GONE);
                                nonind_modeofoperation_spinner.setVisibility(View.VISIBLE);
                                new GetModeOfOperationAsync(FrmClientManagementActivity.this, selectedCategory).execute();
                            }
                        }else{
                            selectedCategory="";
                            ind_modeofoperation_spinner.setSelection(0);
                            ll_modeofoperation.setVisibility(View.GONE);
                            joinDtlLayout.setVisibility(View.GONE);
                            btn_save.setVisibility(View.GONE);
                            btn_cancel.setVisibility(View.GONE);
                        }
                        jedcustomerid.setText("");
                        joinDtlLayout.setVisibility(View.GONE);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if(modeofoperationlist.size() != 0) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(FrmClientManagementActivity.this, android.R.layout.simple_spinner_item, modeofoperationlist);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ind_modeofoperation_spinner.setAdapter(spinnerArrayAdapter);

            ind_modeofoperation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try
                    {
                        if (position != 0)
                        {
                            String Select = (String) parent.getItemAtPosition(position);
                            modeofoperation=modeofoperationmap.get(Select);
                            jedcustomerid.setText("");
                            if(ind_modeofoperation_spinner.getSelectedItem().toString().equalsIgnoreCase("Self"))
                            {
                                joinDtlLayout.setVisibility(View.GONE);

                            }
                            else
                            {
                                joinDtlLayout.setVisibility(View.VISIBLE);

                            }
                        }else{
                            joinDtlLayout.setVisibility(View.GONE);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

    }

    private void AddJoinDetails()
    {
        if(jedcustomerid.getText().toString().trim().equals("")) {
            TrustMethods.showSnackBarMessage("Please Enter Customer Id For Join", coordinatorLayout);
        }else {
            if(jointList==null)
            {
                jointList=new ArrayList<OldJoinListModel>();
            }

            boolean custFound=false;
            String clientId = jedcustomerid.getText().toString().trim();
            for(int i=0;i<jointList.size();i++)
            {
                if(jointList.get(i).getClientId().equals(clientId))
                {
                    custFound=true;
                    break;
                }
            }

            if(custFound)
            {
                TrustMethods.showSnackBarMessage("Id Already Added", coordinatorLayout);
            }
            else {
                if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FrmClientManagementActivity.this)) {
                    if (NetworkUtil.getConnectivityStatus(FrmClientManagementActivity.this)) {
                        new GetClientNameAsync(FrmClientManagementActivity.this, clientId, "JOIN").execute();
                    } else {
                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                    }
                } else {
                    TrustMethods.displaySimErrorDialog(FrmClientManagementActivity.this);
                }
            }

        }
    }

    private void ViewJoinDetails()
    {
        viewJoinList=new ArrayList<>();

        if(jointList!=null) {
            for (int i = 0; i < jointList.size(); i++) {
                ViewJoinModel ViewJoinModel = new ViewJoinModel("Old",jointList.get(i).getClientId(), jointList.get(i).getClientName());
                viewJoinList.add(ViewJoinModel);
            }
        }

        if(viewJoinList.size()>0) {
            ViewOldJoineeDialog cdd = new ViewOldJoineeDialog(this, viewJoinList,jointList);
            cdd.show();
        }else{
            TrustMethods.showSnackBarMessage("No Join Added", coordinatorLayout);
        }
    }

    private void save()
    {
        if(joinDtlLayout.getVisibility() == View.VISIBLE && (jointList==null || jointList.isEmpty())){
            TrustMethods.showSnackBarMessage("Please Enter Join Details", coordinatorLayout);

        }else if (ind_modeofoperation_spinner.getVisibility() == View.VISIBLE && ind_modeofoperation_spinner.getSelectedItem().toString().equalsIgnoreCase("Please Select")){
            TrustMethods.showSnackBarMessage("Please Select Mode of Operation", coordinatorLayout);

        }else if (nonind_modeofoperation_spinner.getVisibility() == View.VISIBLE && nonind_modeofoperation_spinner.getSelectedItem().toString().equalsIgnoreCase("Please Select")){
            TrustMethods.showSnackBarMessage("Please Select Mode of Operation", coordinatorLayout);
        }
        else{
             Intent intent = new Intent(FrmClientManagementActivity.this, FdRdAccOpeningActivity.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
             intent.putExtra("mode_of_operation", modeofoperation);
             intent.putExtra("cgltype", cgltype);
             intent.putExtra("branch_id", branchId);
             intent.putExtra("from_accountNo", accountNo);
             intent.putExtra("jointList", (Serializable) jointList);
             intent.putExtra("balance", balance);
             startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(FrmClientManagementActivity.this, AccountsActivity.class);
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
        TrustMethods.showBackButtonAlert(FrmClientManagementActivity.this);
    }
}