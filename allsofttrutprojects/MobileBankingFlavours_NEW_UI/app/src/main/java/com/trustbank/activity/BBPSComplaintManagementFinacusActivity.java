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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.trustbank.Model.GenerateStanRRNModel;
import com.trustbank.Model.GetUserProfileModal;
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
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BBPSComplaintManagementFinacusActivity extends AppCompatActivity implements AlertDialogOkListener {

    private String TAG = BBPSComplaintManagementFinacusActivity.class.getSimpleName();
    Spinner sp_complaint_type, sp_complaint_reason, sp_participation_type, sp_service_reason;
    EditText et_transaction_ref_id, et_complaint_descrp, et_biller_id, et_service_descrp;
    private TrustMethods method;
    CoordinatorLayout coordinatorLayout;
    Button btn_submit;
    AlertDialogOkListener alertDialogOkListener = this;
    String selectedComplaintType, selectedComplaintReason, selectedParticipationType, selectedServiceReason;
    TextInputLayout ll_transac_ref_id, ll_comp_desc, ll_biller_id, ll_service_desc;
    LinearLayout ll_serv_reas, ll_participation_type, ll_comp_reas;

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
                        TrustMethods.naviagteToSplashScreen(BBPSComplaintManagementFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSComplaintManagementFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpscompaint_management_finacus);
        initCompnonet();
    }

    private void initCompnonet() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        method = new TrustMethods(BBPSComplaintManagementFinacusActivity.this);
        sp_complaint_type= findViewById(R.id.sp_complaint_type);
        sp_complaint_reason= findViewById(R.id.sp_complaint_reason);
        et_transaction_ref_id= findViewById(R.id.et_transaction_ref_id);
        et_complaint_descrp= findViewById(R.id.et_complaint_descrp);
        coordinatorLayout= findViewById(R.id.coordinatorLayout);
        btn_submit= findViewById(R.id.btn_submit);
        ll_transac_ref_id= findViewById(R.id.ll_transac_ref_id);
        ll_comp_reas= findViewById(R.id.ll_comp_reas);
        ll_comp_desc= findViewById(R.id.ll_comp_desc);
        sp_participation_type= findViewById(R.id.sp_participation_type);
        ll_participation_type= findViewById(R.id.ll_participation_type);
        et_biller_id= findViewById(R.id.et_biller_id);
        ll_biller_id= findViewById(R.id.ll_biller_id);
        ll_serv_reas= findViewById(R.id.ll_serv_reas);
        sp_service_reason= findViewById(R.id.sp_service_reason);
        ll_service_desc= findViewById(R.id.ll_service_desc);
        et_service_descrp= findViewById(R.id.et_service_descrp);

        List<String> complaintTypeList = new ArrayList<>();
        complaintTypeList.add("Select Complaint Type");
        complaintTypeList.add("Transaction Based");
        complaintTypeList.add("Service Based");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSComplaintManagementFinacusActivity.this, android.R.layout.simple_spinner_item, complaintTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_complaint_type.setAdapter(adapter);

        ll_transac_ref_id.setVisibility(View.GONE);
        ll_comp_reas.setVisibility(View.GONE);
        ll_comp_desc.setVisibility(View.GONE);
        ll_participation_type.setVisibility(View.GONE);
        ll_biller_id.setVisibility(View.GONE);
        ll_serv_reas.setVisibility(View.GONE);
        ll_service_desc.setVisibility(View.GONE);
        btn_submit.setVisibility(View.GONE);

        sp_complaint_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    et_transaction_ref_id.setText("");
                    et_complaint_descrp.setText("");
                    sp_complaint_reason.setSelection(0);
                    sp_participation_type.setSelection(0);
                    et_biller_id.setText("");
                    sp_service_reason.setSelection(0);
                    et_service_descrp.setText("");

                    if(pos!=0) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        selectedComplaintType = (String) adapterView.getItemAtPosition(pos);
                        btn_submit.setVisibility(View.VISIBLE);
                        spinner(selectedComplaintType);
                    }else {
                        ll_transac_ref_id.setVisibility(View.GONE);
                        ll_comp_reas.setVisibility(View.GONE);
                        ll_comp_desc.setVisibility(View.GONE);
                        ll_participation_type.setVisibility(View.GONE);
                        ll_biller_id.setVisibility(View.GONE);
                        ll_serv_reas.setVisibility(View.GONE);
                        ll_service_desc.setVisibility(View.GONE);
                        btn_submit.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sp_complaint_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    et_complaint_descrp.setText("");
                    if(pos!=0) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        selectedComplaintReason = (String) adapterView.getItemAtPosition(pos);
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

        sp_participation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    et_biller_id.setText("");
                    if(pos!=0) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        selectedParticipationType = (String) adapterView.getItemAtPosition(pos);
                        spinnerServiceBased(selectedParticipationType);
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

        sp_service_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    et_service_descrp.setText("");
                    if(pos!=0) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        selectedServiceReason = (String) adapterView.getItemAtPosition(pos);
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
                String tranRefId = et_transaction_ref_id.getText().toString();
                String compDesc = et_complaint_descrp.getText().toString();
                String servDesc= et_service_descrp.getText().toString();
                String billerId = et_biller_id.getText().toString();
                if(sp_complaint_type.getSelectedItem().equals("Select Complaint Type")){
                    TrustMethods.showSnackBarMessage("Please Select Complaint Type", coordinatorLayout);

                }else {
                    if(selectedComplaintType.equals("Transaction Based")){
                        if(TextUtils.isEmpty(tranRefId)){
                            et_transaction_ref_id.setError("Enter Transaction Ref ID");
                        } else if (sp_complaint_reason.getSelectedItem().equals("Select Complaint Reason")) {
                            TrustMethods.showSnackBarMessage("Select Complaint Reason", coordinatorLayout);
                        } else if (TextUtils.isEmpty(compDesc)) {
                            et_complaint_descrp.setError("Enter Complaint Description");
                        }else{
                            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(BBPSComplaintManagementFinacusActivity.this)) {
                                if (NetworkUtil.getConnectivityStatus(BBPSComplaintManagementFinacusActivity.this)) {
                                    new TxnComplaintRegisterAsyncTask(BBPSComplaintManagementFinacusActivity.this, tranRefId, selectedComplaintReason, compDesc).execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                }
                            } else {
                                TrustMethods.displaySimErrorDialog(BBPSComplaintManagementFinacusActivity.this);
                            }
                        }

                    } else if (selectedComplaintType.equals("Service Based")) {
                        if(sp_participation_type.getSelectedItem().equals("Select Participation Type")){
                            TrustMethods.showSnackBarMessage("Select Participation Type", coordinatorLayout);;
                        } else if (TextUtils.isEmpty(billerId)) {
                            et_biller_id.setError("Enter Biller ID");
                        } else if (sp_service_reason.getSelectedItem().equals("Select Service Reason")){
                            TrustMethods.showSnackBarMessage("Select Service Reason", coordinatorLayout);
                        }else if (TextUtils.isEmpty(servDesc)){
                            et_service_descrp.setError("Enter Service Description");

                        }else{
                            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(BBPSComplaintManagementFinacusActivity.this)) {
                                if (NetworkUtil.getConnectivityStatus(BBPSComplaintManagementFinacusActivity.this)) {
                                    new ServiceComplaintRegisterAsyncTask(BBPSComplaintManagementFinacusActivity.this, selectedParticipationType, selectedServiceReason, servDesc, billerId).execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                }
                            } else {
                                TrustMethods.displaySimErrorDialog(BBPSComplaintManagementFinacusActivity.this);
                            }
                        }
                    }
                }
            }
        });
    }

    public void spinner(String selectedComplaintType){
        if(selectedComplaintType.equals("Transaction Based")){

            ll_transac_ref_id.setVisibility(View.VISIBLE);
            ll_comp_reas.setVisibility(View.VISIBLE);
            ll_comp_desc.setVisibility(View.VISIBLE);
            ll_participation_type.setVisibility(View.GONE);
            ll_biller_id.setVisibility(View.GONE);
            ll_serv_reas.setVisibility(View.GONE);
            ll_service_desc.setVisibility(View.GONE);

            List<String> complaintReasList = new ArrayList<>();
            complaintReasList.add("Select Complaint Reason");
            complaintReasList.add("Transaction Successful, account not updated");
            complaintReasList.add("Amount deducted, biller account credited but transaction ID not received");
            complaintReasList.add("Amount deducted, biller account not credited & transaction ID not received");
            complaintReasList.add("Amount deducted multiple times");
            complaintReasList.add("Double payment updated");
            complaintReasList.add("Errorneously paid in wrong account");
            complaintReasList.add("Others, provide details in description");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSComplaintManagementFinacusActivity.this, android.R.layout.simple_spinner_item, complaintReasList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_complaint_reason.setAdapter(adapter);

        } else if (selectedComplaintType.equals("Service Based")) {
            ll_participation_type.setVisibility(View.VISIBLE);
            ll_biller_id.setVisibility(View.VISIBLE);
            ll_transac_ref_id.setVisibility(View.GONE);
            ll_comp_reas.setVisibility(View.GONE);
            ll_comp_desc.setVisibility(View.GONE);

            List<String> participationTypeList = new ArrayList<>();
            participationTypeList.add("Select Participation Type");
            participationTypeList.add("AGENT");
            participationTypeList.add("BILLER");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSComplaintManagementFinacusActivity.this, android.R.layout.simple_spinner_item, participationTypeList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_participation_type.setAdapter(adapter);
        }
    }

    public void spinnerServiceBased(String participationType){
        ll_serv_reas.setVisibility(View.VISIBLE);
        ll_service_desc.setVisibility(View.VISIBLE);
        List<String> servReasList = new ArrayList<>();
        servReasList.add("Select Service Reason");

        if(participationType.equals("AGENT")){
            servReasList.add("Agent not willing to print receipt");
            servReasList.add("Agent misbehaved");
            servReasList.add("Agent outlet closed");
            servReasList.add("Agent denying registration of complaint");
            servReasList.add("Agent not accepting certain bills");
            servReasList.add("Agent overcharging");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSComplaintManagementFinacusActivity.this, android.R.layout.simple_spinner_item, servReasList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_service_reason.setAdapter(adapter);

        } else if (participationType.equals("BILLER")) {

            servReasList.add("Biller available. Unable to transact");
            servReasList.add("Multiple failure for same biller");
            servReasList.add("Denomination not available");
            servReasList.add("Incorrect bill details displayed");
            servReasList.add("Incomplete / No details reflecting");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSComplaintManagementFinacusActivity.this, android.R.layout.simple_spinner_item, servReasList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_service_reason.setAdapter(adapter);
        }
    }
    private class TxnComplaintRegisterAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result;
        String tranRefId, selectedComplaintReason, compDesc, BBPS_RESPONSE_DATA;
        private String errorCode;
        String complaint_id, complaint_status, open_complaint;
        TMessage responseMsg;

        public TxnComplaintRegisterAsyncTask(Context ctx, String tranRefId, String selectedComplaintReason, String compDesc) {
            this.ctx = ctx;
            this.tranRefId=tranRefId;
            this.selectedComplaintReason=selectedComplaintReason;
            this.compDesc=compDesc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSComplaintManagementFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSComplaintManagementFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data = "<data><tran_ref_id>"+tranRefId+"</tran_ref_id><mobileNumber>"+AppConstants.getUSERMOBILENUMBER()+"</mobileNumber><disposition>"+selectedComplaintReason+"</disposition><desc>"+compDesc+"</desc></data>";
                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();
                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSTxnComplaintRegDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
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

                        BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            complaint_id = document.getElementsByTagName("complaint_id").item(0).getTextContent();
                            complaint_status = document.getElementsByTagName("complaint_status").item(0).getTextContent();
                            open_complaint = document.getElementsByTagName("open_complaint").item(0).getTextContent();
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
                        AlertDialogMethod.alertDialogOk(BBPSComplaintManagementFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    }
                } else {
                    if(responseMsg.ActCode.Value.equals("000")){
                        AlertDialogMethod.alertDialogOk(BBPSComplaintManagementFinacusActivity.this,
                                "Complaint Management", "Your Complaint is Registered Successfully.\nYour Complaint ID is:\n" + complaint_id, getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    }else {
                        AlertDialogMethod.alertDialogOk(BBPSComplaintManagementFinacusActivity.this,
                                "Complaint Management", responseMsg.ActCodeDesc.Value , getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ServiceComplaintRegisterAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result;
        String selectedParticipationType, selectedServiceReason, servDesc, billerId, BBPS_RESPONSE_DATA;
        private String errorCode;
        String complaint_id, complaint_status, open_complaint;
        TMessage responseMsg;

        public ServiceComplaintRegisterAsyncTask(Context ctx, String selectedParticipationType, String selectedServiceReason, String servDesc, String billerId) {
            this.ctx = ctx;
            this.selectedParticipationType=selectedParticipationType;
            this.selectedServiceReason=selectedServiceReason;
            this.servDesc=servDesc;
            this.billerId=billerId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSComplaintManagementFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSComplaintManagementFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data = "<data><biller_id>"+billerId+"</biller_id><mobileNumber>"+AppConstants.getUSERMOBILENUMBER()+"</mobileNumber><desc>"+servDesc+"</desc><service_reason>"+selectedServiceReason+"</service_reason><participation_type>"+selectedParticipationType+"</participation_type></data>";
                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();
                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSServiceComplaintRegDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
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

                        BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            complaint_id = document.getElementsByTagName("complaint_id").item(0).getTextContent();
                            complaint_status = document.getElementsByTagName("complaint_status").item(0).getTextContent();
                            open_complaint = document.getElementsByTagName("open_complaint").item(0).getTextContent();
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
                        AlertDialogMethod.alertDialogOk(BBPSComplaintManagementFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    }
                } else {
                    if(responseMsg.ActCode.Value.equals("000")){
                        AlertDialogMethod.alertDialogOk(BBPSComplaintManagementFinacusActivity.this,
                                "Complaint Management", "Your Complaint is Registered Successfully.\nYour Complaint ID is:\n" + complaint_id, getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    }else {
                        TrustMethods.showSnackBarMessage(responseMsg.ActCodeDesc.Value, coordinatorLayout);
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
                Intent intent = new Intent(BBPSComplaintManagementFinacusActivity.this, BBPSFinacusActivity.class);
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
        TrustMethods.showBackButtonAlert(BBPSComplaintManagementFinacusActivity.this);
    }
}