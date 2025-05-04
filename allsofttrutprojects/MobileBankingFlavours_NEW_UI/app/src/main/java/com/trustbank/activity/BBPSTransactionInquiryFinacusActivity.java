package com.trustbank.activity;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.trustbank.Model.BBPSComplaintHistoryModelFinacus;
import com.trustbank.Model.BBPSTransactionInquiryFinacusModel;
import com.trustbank.Model.GenerateStanRRNModel;
import com.trustbank.R;
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

public class BBPSTransactionInquiryFinacusActivity extends AppCompatActivity implements AlertDialogOkListener{

    private String TAG = BBPSTransactionInquiryFinacusActivity.class.getSimpleName();
    private TrustMethods method;
    CoordinatorLayout coordinatorLayout;
    Spinner sp_status_by;
    EditText et_transaction_ref_id;
    Button btn_submit;
    String statusById;
    AlertDialogOkListener alertDialogOkListener = this;
    TextInputLayout ll_transac_ref_id;
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
                        TrustMethods.naviagteToSplashScreen(BBPSTransactionInquiryFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSTransactionInquiryFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpstransaction_inquiry_finacus);

        initCompnonet();
    }

    private void initCompnonet() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        method = new TrustMethods(BBPSTransactionInquiryFinacusActivity.this);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        sp_status_by = findViewById(R.id.sp_status_by);
        et_transaction_ref_id = findViewById(R.id.et_transaction_ref_id);
        btn_submit = findViewById(R.id.btn_submit);
        ll_transac_ref_id = findViewById(R.id.ll_transac_ref_id);

        List<String> statusByList = new ArrayList<>();
       // 1(By TxnRefId), 2(By Mobile No)
        statusByList.add("Select Status By");
        statusByList.add("TxnRefId");
        statusByList.add("Mobile No");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSTransactionInquiryFinacusActivity.this, android.R.layout.simple_spinner_item, statusByList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_status_by.setAdapter(adapter);

        sp_status_by.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    if(pos!=0) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        String selected = (String) adapterView.getItemAtPosition(pos);
                        et_transaction_ref_id.setText("");

                        if(selected.equals("TxnRefId")){
                            statusById = "1";
                            ll_transac_ref_id.setHint("Transaction Ref ID");

                        } else if (selected.equals("Mobile No")) {
                            statusById = "2";
                            ll_transac_ref_id.setHint("Mobile No.");
                        }
                    }else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transcID = et_transaction_ref_id.getText().toString();

                if (sp_status_by.getSelectedItem().equals("Select Status By")) {
                    TrustMethods.showSnackBarMessage("Select Select Status By", coordinatorLayout);
                } else if (TextUtils.isEmpty(transcID)) {
                    if(sp_status_by.getSelectedItem().equals("TxnRefId")) {
                        et_transaction_ref_id.setError("Enter Transaction Ref ID");
                    }else{
                        et_transaction_ref_id.setError("Enter Mobile Number");
                    }
                }else{
                    if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(BBPSTransactionInquiryFinacusActivity.this)) {
                        if (NetworkUtil.getConnectivityStatus(BBPSTransactionInquiryFinacusActivity.this)) {
                            new TransactionInquiryAsyncTask(BBPSTransactionInquiryFinacusActivity.this, statusById, transcID).execute();
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                        }
                    } else {
                        TrustMethods.displaySimErrorDialog(BBPSTransactionInquiryFinacusActivity.this);
                    }
                }
            }
        });
    }

    private class TransactionInquiryAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result;
        String statusById, transcID;
        private String errorCode;
        TMessage responseMsg;
        List<BBPSTransactionInquiryFinacusModel> transInquiryList;

        public TransactionInquiryAsyncTask(Context ctx, String  statusById, String transcID) {
            this.ctx = ctx;
            this.statusById=statusById;
            this.transcID=transcID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSTransactionInquiryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSTransactionInquiryFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data = "<data><status_by>"+statusById+"</status_by><tran_status_param>"+transcID+"</tran_status_param></data>";
                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();
                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSTransactionInquiryDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
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
                        responseMsg = (TMessage) resParse.response;

                        String BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            String transaction_id = document.getElementsByTagName("transaction_id").item(0).getTextContent();
                            String customer_name = document.getElementsByTagName("customer_name").item(0).getTextContent();
                            String email = document.getElementsByTagName("email").item(0).getTextContent();
                            String mobile = document.getElementsByTagName("mobile").item(0).getTextContent();

                            NodeList nodeList = document.getElementsByTagName("tran");
                            transInquiryList = new ArrayList<>();

                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                String tran_Ref_Id = element.getElementsByTagName("tran_Ref_Id").item(0).getTextContent();
                                String agent_id = element.getElementsByTagName("agent_id").item(0).getTextContent();
                                String biller_id = element.getElementsByTagName("biller_id").item(0).getTextContent();
                                String amount = element.getElementsByTagName("amount").item(0).getTextContent();
                                String tran_date = element.getElementsByTagName("tran_date").item(0).getTextContent();
                                String status = element.getElementsByTagName("status").item(0).getTextContent();

                                BBPSTransactionInquiryFinacusModel model = new BBPSTransactionInquiryFinacusModel(tran_Ref_Id, agent_id, biller_id, amount, tran_date, status);
                                transInquiryList.add(model);
                            }
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
                        AlertDialogMethod.alertDialogOk(BBPSTransactionInquiryFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    }
                } else {
                    if (transInquiryList != null && transInquiryList.size() != 0) {

                        Intent intent = new Intent(BBPSTransactionInquiryFinacusActivity.this, BBPSDisplayTransactionInquiryFinacusActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Transaction Inquiry List", (Serializable) transInquiryList);
                        startActivity(intent);
                    } else {
                        TrustMethods.showSnackBarMessage( responseMsg.ActCodeDesc.Value, coordinatorLayout);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        if (resultCode == 0) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            method.activityCloseAnimation();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BBPSTransactionInquiryFinacusActivity.this, BBPSFinacusActivity.class);
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
        TrustMethods.showBackButtonAlert(BBPSTransactionInquiryFinacusActivity.this);
    }
}