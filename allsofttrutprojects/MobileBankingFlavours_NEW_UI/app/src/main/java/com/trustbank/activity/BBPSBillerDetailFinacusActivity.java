package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.trustbank.Model.BBPSBillDetailFinacusModel;
import com.trustbank.Model.BBPSCustomerParamFinacusModel;
import com.trustbank.Model.GenerateStanRRNModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogListener;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.tcpconnection.tdto.MessageDtoBuilder;
import com.trustbank.tcpconnection.tmessage.TMessage;
import com.trustbank.tcpconnection.tmessage.TMessageUtil;
import com.trustbank.tcpconnection.util.ResponseEntity;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BBPSBillerDetailFinacusActivity extends AppCompatActivity implements AlertDialogOkListener, AlertDialogListener {

    private String TAG = BBPSBillerDetailFinacusActivity.class.getSimpleName();
    TextView txt_customer_name, txt_bill_number, txt_bill_period, txt_bill_date, txt_bill_due_date, txt_bill_amount,
            txt_customer_params_value, txt_customer_params_name, txt_convenience_fees;
    TrustMethods method;
    Spinner spinnerFrmAct;
    Button btnPayBillsId;
    CoordinatorLayout searchbillerCoordinatorLayoutId;
    AlertDialogOkListener alertDialogOkListener = this;
    AlertDialogListener alertDialogListener = this;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    String accountNo, remitterAccName, convenience_fees, biller_id, bill_amount, bill_number, transaction_id, category, biller_name;
    List<BBPSCustomerParamFinacusModel> customerParamsList=new ArrayList<>();

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
                        TrustMethods.naviagteToSplashScreen(BBPSBillerDetailFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSBillerDetailFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpsbiller_detail_finacus);

        initCompnonet();
    }

    private void initCompnonet() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        method = new TrustMethods(BBPSBillerDetailFinacusActivity.this);
        txt_customer_name = findViewById(R.id.txt_customer_name);
        txt_bill_number = findViewById(R.id.txt_bill_number);
        txt_bill_period = findViewById(R.id.txt_bill_period);
        txt_bill_date = findViewById(R.id.txt_bill_date);
        txt_bill_due_date = findViewById(R.id.txt_bill_due_date);
        txt_bill_amount = findViewById(R.id.txt_bill_amount);
        txt_customer_params_value = findViewById(R.id.txt_customer_params_value);
        txt_customer_params_name = findViewById(R.id.txt_customer_params_name);
        searchbillerCoordinatorLayoutId = findViewById(R.id.searchbillerCoordinatorLayoutId);
        txt_convenience_fees = findViewById(R.id.txt_convenience_fees);
        spinnerFrmAct = findViewById(R.id.spinnerFrmAct);
        btnPayBillsId = findViewById(R.id.btnPayBillsId);
//        biller_id, bill_amount, bill_number, transaction_id
        Intent intent = getIntent();
        transaction_id = intent.getStringExtra("transaction_id");
        String customer_name = intent.getStringExtra("customer_name");
        bill_number =intent.getStringExtra("bill_number");
        String bill_period =intent.getStringExtra("bill_period");
        String bill_date =intent.getStringExtra("bill_date");
        String bill_due_date = intent.getStringExtra("bill_due_date");
        bill_amount = intent.getStringExtra("bill_amount");
        String name =intent.getStringExtra("customer_param_name");
        String value = intent.getStringExtra("customer_param_value");
        biller_id = intent.getStringExtra("biller_id");
        category = intent.getStringExtra("category");
        biller_name = intent.getStringExtra("biller_name");
        customerParamsList = (List<BBPSCustomerParamFinacusModel>) intent.getSerializableExtra("customerParamsList");

        txt_customer_name.setText(customer_name);
        txt_bill_number.setText(bill_number);
        txt_bill_period.setText(bill_period);
        txt_bill_date.setText(bill_date);
        txt_bill_due_date.setText(bill_due_date);
        txt_bill_amount.setText(bill_amount);
        txt_customer_params_name.setText(name);
        txt_customer_params_value.setText(value);

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(BBPSBillerDetailFinacusActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(BBPSBillerDetailFinacusActivity.this)) {
                new ConvenienceFeesAsyncTask(BBPSBillerDetailFinacusActivity.this, biller_id, bill_amount).execute();
            } else {
                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), searchbillerCoordinatorLayoutId);
            }
        } else {
            TrustMethods.displaySimErrorDialog(BBPSBillerDetailFinacusActivity.this);
        }

        setAccountNoSpinner();

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

        btnPayBillsId.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   AlertDialogMethod.alertDialog(BBPSBillerDetailFinacusActivity.this,
                           "Confirmation Screen", "Customer Mobile No :"+ AppConstants.getUSERMOBILENUMBER()+"\nBill Amount: INR "+bill_amount+"\nDisclaimer : Your selected transaction will be executed on a real time basis. You will not be able to stop this transaction after completing the transaction. Please select Yes to proceed.", getResources().getString(R.string.btn_yes),
                           getResources().getString(R.string.btn_cancel),1, false, alertDialogListener);
            }
       });
    }

    private void setAccountNoSpinner() {
        try {
            accountsArrayList = method.getArrayList(BBPSBillerDetailFinacusActivity.this, "AccountListPref");

            if (accountsArrayList != null && accountsArrayList.size() > 0) {
                List<String> accountList = new ArrayList<>();
                accountList.add(0, "Select Account Number");
                for (int i = 0; i < accountsArrayList.size(); i++) {
                    GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                    if (TrustMethods.isAccountTypeIsImpsRegValid(getUserProfileModal.getHeadid(), getUserProfileModal.getIs_imps_reg(),AppConstants.getImpslist())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo + " - " + accTypeCode);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSBillerDetailFinacusActivity.this, android.R.layout.simple_spinner_item, accountList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFrmAct.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        try{
            switch (resultCode){
                case 0:
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    method.activityCloseAnimation();
                    break;

                case 1:
                    if(spinnerFrmAct.getSelectedItem().equals("Select Account Number")){
                        TrustMethods.showSnackBarMessage("Please Select Account Number", searchbillerCoordinatorLayoutId);
                    }else {
                        Intent intent1 = new Intent(BBPSBillerDetailFinacusActivity.this, OtpVerificationActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.putExtra("checkTransferType", "billPayTransactionsFinacus");
                        intent1.putExtra("accountNo", accountNo);
                        intent1.putExtra("biller_id", biller_id);
                        intent1.putExtra("bill_amount", bill_amount);
                        intent1.putExtra("bill_number", bill_number);
                        intent1.putExtra("transaction_id", transaction_id);
                        intent1.putExtra("convenience_fees", convenience_fees);
                        intent1.putExtra("category", category);
                        intent1.putExtra("biller_name", biller_name);
                        intent1.putExtra("customerParamsList", (Serializable) customerParamsList);
                        startActivity(intent1);
                        method.activityOpenAnimation();
                    }
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogCancel(int resultCode) {

    }

    private class ConvenienceFeesAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result;
        String billerId, bill_amount;
        private String errorCode;

        public ConvenienceFeesAsyncTask(Context ctx, String billerId, String bill_amount) {
            this.ctx = ctx;
            this.billerId=billerId;
            this.bill_amount=bill_amount;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerDetailFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSBillerDetailFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data  = "<data><biller_id>"+billerId+"</biller_id><bill_amount>"+bill_amount+"</bill_amount></data>";
                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();
                if (generateStanRRNModel.getError() != null) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSfetchBillerDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
                            AppConstants.getUSERMOBILENUMBER(), "", AppConstants.getUSERNAME(), "", "", "", "", "", "",
                            AppConstants.INSTITUTION_ID, generateStanRRNModel.getChannel_ref_no());//TMessageUtil.MSG_INSTITUTION_ID);
                    Log.d("msg.GetXml()" + "" + ":", requestXmlMsg.GetXml());

                    String base64EncodedRequestJson = Base64.encodeToString(requestXmlMsg.GetXml().getBytes(), Base64.NO_WRAP);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", base64EncodedRequestJson);

                    if (!url.equals("")) {
                        TrustMethods.LogMessage(TAG, "URL:-" + url);
                        result = HttpClientWrapper.postWitAuthHeader(url, jsonObject.toString(), AppConstants.getAuth_token());
                        TrustMethods.LogMessage(TAG, "Frm Enquiry details response-->" + response);
                    }
                    if (result == null || result.equals("")) {
                        error = AppConstants.SERVER_NOT_RESPONDING;
                        return error;
                    }
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.has("error")) {
                        error = jsonResponse.getString("error");
                        return error;
                    }

                    String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                    if (responseCode.equals("1")) {
                        response = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";

                        String responseValue = TrustMethods.decodeBase64(response);
                        Log.d("responseValue", responseValue);

                        ResponseEntity resParse = TMessage.ParseMessage(responseValue);
                        TMessage responseMsg = (TMessage) resParse.response;

                        String BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            convenience_fees = document.getElementsByTagName("convenience_fees").item(0).getTextContent();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(BBPSBillerDetailFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, searchbillerCoordinatorLayoutId);
                    }
                } else {
                    txt_convenience_fees.setText(convenience_fees);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BBPSBillerDetailFinacusActivity.this, BBPSBillerCategoryFinacusActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(BBPSBillerDetailFinacusActivity.this);
    }
}