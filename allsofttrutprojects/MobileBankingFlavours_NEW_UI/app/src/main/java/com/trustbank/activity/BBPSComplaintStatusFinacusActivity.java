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
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BBPSComplaintStatusFinacusActivity extends AppCompatActivity implements AlertDialogOkListener {

    private String TAG = BBPSComplaintStatusFinacusActivity.class.getSimpleName();
    private TrustMethods method;
    CoordinatorLayout coordinatorLayout;
    Spinner sp_complaint_type;
    EditText et_complaint_id;
    Button btn_submit;
    String selectedComplaintType;
    AlertDialogOkListener alertDialogOkListener = this;

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
                        TrustMethods.naviagteToSplashScreen(BBPSComplaintStatusFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSComplaintStatusFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpscomplaint_status_finacus);
        initCompnonet();
    }

    private void initCompnonet() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        method = new TrustMethods(BBPSComplaintStatusFinacusActivity.this);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        sp_complaint_type = findViewById(R.id.sp_complaint_type);
        et_complaint_id = findViewById(R.id.et_complaint_id);
        btn_submit = findViewById(R.id.btn_submit);

        List<String> complaintTypeList = new ArrayList<>();
        complaintTypeList.add("Select Complaint Type");
        complaintTypeList.add("Transaction");
        complaintTypeList.add("Service");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSComplaintStatusFinacusActivity.this, android.R.layout.simple_spinner_item, complaintTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_complaint_type.setAdapter(adapter);

        sp_complaint_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    et_complaint_id.setText("");
                    if(pos!=0) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        selectedComplaintType = (String) adapterView.getItemAtPosition(pos);
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
                String complaintID = et_complaint_id.getText().toString();

                if (sp_complaint_type.getSelectedItem().equals("Select Complaint Type")) {
                    TrustMethods.showSnackBarMessage("Select Complaint Type", coordinatorLayout);
                } else if (TextUtils.isEmpty(complaintID)) {
                    et_complaint_id.setError("Enter Complaint ID");
                }else{
                    if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(BBPSComplaintStatusFinacusActivity.this)) {
                        if (NetworkUtil.getConnectivityStatus(BBPSComplaintStatusFinacusActivity.this)) {
                            new ComplaintStatusAsyncTask(BBPSComplaintStatusFinacusActivity.this, selectedComplaintType, complaintID).execute();
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                        }
                    } else {
                        TrustMethods.displaySimErrorDialog(BBPSComplaintStatusFinacusActivity.this);
                    }
                }
            }
        });
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

    private class ComplaintStatusAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result;
        String selectedComplaintType, complaintID;
        private String errorCode;
        String complaint_id, complaint_status, BBPS_RESPONSE_DATA;
        TMessage responseMsg;

        public ComplaintStatusAsyncTask(Context ctx, String selectedComplaintType, String complaintID) {
            this.ctx = ctx;
            this.selectedComplaintType=selectedComplaintType;
            this.complaintID=complaintID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSComplaintStatusFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSComplaintStatusFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data = "<data><complaint_type>"+selectedComplaintType+"</complaint_type><complaint_id>"+complaintID+"</complaint_id></data>";
                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();
                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSComplaintStatusDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
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
                        AlertDialogMethod.alertDialogOk(BBPSComplaintStatusFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    }
                } else {
                    if(responseMsg.ActCode.Value.equals("000") && !TextUtils.isEmpty(BBPS_RESPONSE_DATA)) {
                        AlertDialogMethod.alertDialogOk(BBPSComplaintStatusFinacusActivity.this,
                                "Complaint Status", "Complaint ID :"+complaint_id+"\n" + "Complaint Status :"+complaint_status , getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    }else{
                        AlertDialogMethod.alertDialogOk(BBPSComplaintStatusFinacusActivity.this,
                                "Complaint Status", responseMsg.ActCodeDesc.Value , getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    }
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
                Intent intent = new Intent(BBPSComplaintStatusFinacusActivity.this, BBPSFinacusActivity.class);
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
        TrustMethods.showBackButtonAlert(BBPSComplaintStatusFinacusActivity.this);
    }
}