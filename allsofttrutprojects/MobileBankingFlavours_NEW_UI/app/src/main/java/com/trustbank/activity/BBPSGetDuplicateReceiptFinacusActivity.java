package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BBPSGetDuplicateReceiptFinacusActivity extends AppCompatActivity implements AlertDialogOkListener {

    private String TAG = BBPSGetDuplicateReceiptFinacusActivity.class.getSimpleName();
    private TrustMethods method;
    CoordinatorLayout coordinatorLayoutId;
    EditText et_transc_id;
    Button btn_search, btn_share;
    AlertDialogOkListener alertDialogOkListener = this;
    CardView cv_dup_receipt;
    TextView txt_transaction_ref_id, txt_payment_ref_id, txt_transaction_id, txt_customer_name, txt_customer_mobile_no, txt_consumer_number, txt_agent_id,
            tx_biller_id, txt_biller_name, txt_bill_number, txt_bill_date;
    TextInputLayout ll_transc_id;

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
                        TrustMethods.naviagteToSplashScreen(BBPSGetDuplicateReceiptFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSGetDuplicateReceiptFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpsget_duplicate_receipt_finacus);

        initCompnonet();
    }

    private void initCompnonet() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        method = new TrustMethods(BBPSGetDuplicateReceiptFinacusActivity.this);
        coordinatorLayoutId = findViewById(R.id. coordinatorLayoutId);
        et_transc_id = findViewById(R.id. et_transc_id);
        btn_search = findViewById(R.id. btn_search);
        cv_dup_receipt = findViewById(R.id. cv_dup_receipt);
        txt_transaction_ref_id = findViewById(R.id. txt_transaction_ref_id);
        txt_payment_ref_id = findViewById(R.id. txt_payment_ref_id);
        txt_transaction_id = findViewById(R.id. txt_transaction_id);
        txt_customer_name = findViewById(R.id. txt_customer_name);
        txt_customer_mobile_no = findViewById(R.id. txt_customer_mobile_no);
        /*txt_consumer_number = findViewById(R.id.txt_consumer_number);
        txt_agent_id = findViewById(R.id.txt_agent_id);
        tx_biller_id = findViewById(R.id. tx_biller_id);*/
        txt_biller_name = findViewById(R.id. txt_biller_name);
        txt_bill_number = findViewById(R.id. txt_bill_number);
        txt_bill_date = findViewById(R.id. txt_bill_date);
        ll_transc_id = findViewById(R.id. ll_transc_id);
        btn_share = findViewById(R.id. btn_share);

        cv_dup_receipt.setVisibility(View.GONE);
        btn_share.setVisibility(View.GONE);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String transcId = et_transc_id.getText().toString();
                if (TextUtils.isEmpty(transcId)) {
                    et_transc_id.setError("Enter Transaction ID");

                }else {
                    TrustMethods.hideSoftKeyboard(BBPSGetDuplicateReceiptFinacusActivity.this);

                    if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(BBPSGetDuplicateReceiptFinacusActivity.this)) {
                        if (NetworkUtil.getConnectivityStatus(BBPSGetDuplicateReceiptFinacusActivity.this)) {
                            new GetDuplicateReceiptAsyncTask(BBPSGetDuplicateReceiptFinacusActivity.this, transcId).execute();
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayoutId);
                        }
                    } else {
                        TrustMethods.displaySimErrorDialog(BBPSGetDuplicateReceiptFinacusActivity.this);
                    }
                }
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf(cv_dup_receipt);
            }
        });
    }

    public void sharePdf(View view){
        int height = view.getHeight();
        int width = view.getWidth();

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(width, height, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        /*view.layout(0, 0 , width, height)*/;
        view.draw(canvas);
        pdfDocument.finishPage(page);
        File pdfFile = new File(getExternalFilesDir(null), "DuplicateReceipt.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pdfDocument.close();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("application/pdf");
        Uri pdfUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", pdfFile);
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        startActivity(Intent.createChooser(intent, "Select"));
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

    private class GetDuplicateReceiptAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result;
        String transcId,BBPS_RESPONSE_DATA;
        private String errorCode;
        String transaction_ref_id, payment_ref_id, transaction_id, customer_name, customer_mobile_no, consumer_number, agent_id, biller_category,
                biller_id, biller_name, bill_number, bill_period, bill_date, bill_due_date, payment_channel, payment_mode, payment_status, total_bill_amount, conv_fees, bill_pay_date_time;
        TMessage responseMsg;
        public GetDuplicateReceiptAsyncTask(Context ctx, String transcId) {
            this.ctx = ctx;
            this.transcId=transcId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSGetDuplicateReceiptFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSGetDuplicateReceiptFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data  = "<data><ref_id>"+transcId+"</ref_id></data>";
                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();
                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSGetDuplicateReceiptDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
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
                            transaction_ref_id = document.getElementsByTagName("transaction_ref_id").item(0).getTextContent();
                            payment_ref_id = document.getElementsByTagName("payment_ref_id").item(0).getTextContent();
                            transaction_id = document.getElementsByTagName("transaction_id").item(0).getTextContent();
                            customer_name = document.getElementsByTagName("customer_name").item(0).getTextContent();
                            customer_mobile_no = document.getElementsByTagName("customer_mobile_no").item(0).getTextContent();
                            consumer_number = document.getElementsByTagName("consumer_number").item(0).getTextContent();
                            agent_id = document.getElementsByTagName("agent_id").item(0).getTextContent();
                            biller_category = document.getElementsByTagName("biller_category").item(0).getTextContent();
                            biller_id = document.getElementsByTagName("biller_id").item(0).getTextContent();
                            biller_name = document.getElementsByTagName("biller_name").item(0).getTextContent();
                            bill_number = document.getElementsByTagName("bill_number").item(0).getTextContent();
                            bill_period = document.getElementsByTagName("bill_period").item(0).getTextContent();
                            bill_date = document.getElementsByTagName("bill_date").item(0).getTextContent();
                            bill_due_date = document.getElementsByTagName("bill_due_date").item(0).getTextContent();
                            payment_channel = document.getElementsByTagName("payment_channel").item(0).getTextContent();
                            payment_mode = document.getElementsByTagName("payment_mode").item(0).getTextContent();
                            payment_status = document.getElementsByTagName("payment_status").item(0).getTextContent();
                            total_bill_amount = document.getElementsByTagName("total_bill_amount").item(0).getTextContent();
                            conv_fees = document.getElementsByTagName("conv_fees").item(0).getTextContent();
                            bill_pay_date_time = document.getElementsByTagName("bill_pay_date_time").item(0).getTextContent();

                            NodeList nodeList = document.getElementsByTagName("customer_param");
                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                String name = element.getElementsByTagName("name").item(0).getTextContent();
                                String value = element.getElementsByTagName("value").item(0).getTextContent();

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
                        AlertDialogMethod.alertDialogOk(BBPSGetDuplicateReceiptFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, coordinatorLayoutId);
                    }
                } else {
                    if(TextUtils.isEmpty(BBPS_RESPONSE_DATA)) {
                        TrustMethods.showSnackBarMessage(responseMsg.ActCodeDesc.Value, coordinatorLayoutId);
                    }else {
                        ll_transc_id.setVisibility(View.GONE);
                        btn_search.setVisibility(View.GONE);
                        cv_dup_receipt.setVisibility(View.VISIBLE);
                        btn_share.setVisibility(View.VISIBLE);

                        txt_transaction_ref_id.setText(transaction_ref_id);
                        txt_payment_ref_id.setText(payment_ref_id);
                        txt_transaction_id.setText(transaction_id);
                        txt_customer_name.setText(customer_name);
                        txt_customer_mobile_no.setText(customer_mobile_no);
                        txt_biller_name.setText(biller_name);
                        txt_bill_number.setText(bill_number);
                        txt_bill_date.setText(bill_date);
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
                Intent intent = new Intent(BBPSGetDuplicateReceiptFinacusActivity.this, BBPSFinacusActivity.class);
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
        TrustMethods.showBackButtonAlert(BBPSGetDuplicateReceiptFinacusActivity.this);
    }
}