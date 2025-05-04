package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.trustbank.Model.BBPSComplaintHistoryModelFinacus;
import com.trustbank.Model.BBPSTransactionHistoryFinacusModel;
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

public class BBPSComplaintHistoryFinacusActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogOkListener {

    private String TAG = BBPSComplaintHistoryFinacusActivity.class.getSimpleName();
    TrustMethods method;
    TextView txt_FromDate, txt_ToDate;
    Button btn_view;
    CoordinatorLayout coordinatorLayout;
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
                        TrustMethods.naviagteToSplashScreen(BBPSComplaintHistoryFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSComplaintHistoryFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpscomplaint_history_finacus);
        initCompnonet();
    }

    private void initCompnonet() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        method = new TrustMethods(BBPSComplaintHistoryFinacusActivity.this);
        txt_FromDate = findViewById(R.id.txt_FromDate);
        txt_ToDate = findViewById(R.id.txt_ToDate);
        btn_view = findViewById(R.id.btn_view);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        btn_view.setOnClickListener(this);
        txt_FromDate.setOnClickListener(this);
        txt_ToDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.txt_FromDate:
                    method.datePickerymd(BBPSComplaintHistoryFinacusActivity.this, txt_FromDate);
                    break;
                case R.id.txt_ToDate:
                    method.datePickerymd(BBPSComplaintHistoryFinacusActivity.this, txt_ToDate);
                    break;
                case R.id.btn_view:
                    String fromDate= txt_FromDate.getText().toString();
                    String toDate= txt_ToDate.getText().toString();

                    if (TextUtils.isEmpty(fromDate)){
                        TrustMethods.showSnackBarMessage("Please Select From Date", coordinatorLayout);
                    }else if (TextUtils.isEmpty(toDate)){
                        TrustMethods.showSnackBarMessage("Please Select To Date", coordinatorLayout);
                    }else {
                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(BBPSComplaintHistoryFinacusActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(BBPSComplaintHistoryFinacusActivity.this)) {
                                new FetchComplaintHistoryAsyncTask(BBPSComplaintHistoryFinacusActivity.this, fromDate, toDate).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(BBPSComplaintHistoryFinacusActivity.this);
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FetchComplaintHistoryAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result, fromDate, toDate;
        List<BBPSComplaintHistoryModelFinacus> complaintHistList;
        private String errorCode;
        TMessage responseMsg;

        public FetchComplaintHistoryAsyncTask(Context ctx, String fromDate, String toDate) {
            this.ctx = ctx;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSComplaintHistoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSComplaintHistoryFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data  = "<data><from_date>"+fromDate+"</from_date><to_date>"+toDate+"</to_date></data>";

                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();

                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSComplaintHistoryDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
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
                            NodeList nodeList = document.getElementsByTagName("complaint");

                            complaintHistList = new ArrayList<>();

                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                String complaint_type = element.getElementsByTagName("complaint_type").item(0).getTextContent();
                                String participation_type = element.getElementsByTagName("participation_type").item(0).getTextContent();
                                String txn_ref_id = element.getElementsByTagName("txn_ref_id").item(0).getTextContent();
                                String agent_id = element.getElementsByTagName("agent_id").item(0).getTextContent();
                                String biller_id = element.getElementsByTagName("biller_id").item(0).getTextContent();
                                String disposition = element.getElementsByTagName("disposition").item(0).getTextContent();
                                String service_reason = element.getElementsByTagName("service_reason").item(0).getTextContent();
                                String description = element.getElementsByTagName("description").item(0).getTextContent();
                                String complaint_id = element.getElementsByTagName("complaint_id").item(0).getTextContent();
                                String complaint_status = element.getElementsByTagName("complaint_status").item(0).getTextContent();

                                BBPSComplaintHistoryModelFinacus model = new BBPSComplaintHistoryModelFinacus(complaint_type , participation_type , txn_ref_id , agent_id , biller_id , disposition , service_reason , description , complaint_id,
                                        complaint_status);
                                complaintHistList.add(model);
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
                txt_FromDate.setText("");
                txt_ToDate.setText("");

                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(BBPSComplaintHistoryFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    }
                } else {

                    if (complaintHistList != null && complaintHistList.size() != 0) {

                        Intent intent = new Intent(BBPSComplaintHistoryFinacusActivity.this, BBPSDisplayComplaintHistoryFinacusActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Complaint History List", (Serializable) complaintHistList);
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
                Intent intent = new Intent(BBPSComplaintHistoryFinacusActivity.this, BBPSFinacusActivity.class);
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
        TrustMethods.showBackButtonAlert(BBPSComplaintHistoryFinacusActivity.this);
    }
}