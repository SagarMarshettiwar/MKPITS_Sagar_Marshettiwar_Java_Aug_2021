package com.trustbank.activity;

import static com.trustbank.util.MBank.loadAppLogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.BBPSBillDetailFinacusModel;
import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BBPSPaymentDetailsFinacusActivity extends AppCompatActivity implements AlertDialogOkListener {

    private String TAG = BBPSPaymentDetailsFinacusActivity.class.getSimpleName();
    private TrustMethods method;
    TextView txt_trans_ref_id, txt_payment_ref_id, txt_customer_name, txt_cust_mobile_no, txt_consumer_number, txt_agent_id, txt_biller_category,
            txt_billerId, txt_biller_name, txt_bill_number, txt_bill_period, txt_bill_date,txt_bill_due_date, txt_bill_amount, txt_payment_channel,
            txt_payment_mode, txt_payment_status, txt_conv_fees, txt_total_bill_amount, txt_bill_pay_date_time;
    private TextView txtEmailAddr, txtContactNo;
    Button btn_save, btn_share;
    CardView cv_bill_detail;
    private ImageView ivAppLogo;
    CoordinatorLayout coordinatorLayoutId;
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
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(BBPSPaymentDetailsFinacusActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(BBPSPaymentDetailsFinacusActivity.this, false);
        setContentView(R.layout.activity_payment_details);

        ivAppLogo = findViewById(R.id.ivAppLogo);
        loadAppLogo(ivAppLogo);

        inIt();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }

    private void inIt() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        method = new TrustMethods(BBPSPaymentDetailsFinacusActivity.this);
        txt_trans_ref_id = findViewById(R.id.txt_trans_ref_id);
//        txt_payment_ref_id = findViewById(R.id.txt_payment_ref_id);
        txt_customer_name = findViewById(R.id.txt_customer_name);
        txt_cust_mobile_no = findViewById(R.id.txt_cust_mobile_no);
        /*txt_consumer_number = findViewById(R.id.txt_consumer_number);
        txt_agent_id = findViewById(R.id.txt_agent_id);*/
        txt_biller_category = findViewById(R.id.txt_biller_category);
//        txt_billerId = findViewById(R.id.txt_billerId);
        txt_biller_name = findViewById(R.id.txt_biller_name);
        txt_bill_number = findViewById(R.id.txt_bill_number);
        /*txt_bill_period = findViewById(R.id.txt_bill_period);
        txt_bill_date = findViewById(R.id.txt_bill_date);
        txt_bill_due_date = findViewById(R.id.txt_bill_due_date);*/
        txt_bill_amount = findViewById(R.id.txt_bill_amount);
//        txt_payment_channel = findViewById(R.id.txt_payment_channel);
        txt_payment_mode = findViewById(R.id.txt_payment_mode);
        txt_payment_status = findViewById(R.id.txt_payment_status);
        txt_conv_fees = findViewById(R.id.txt_conv_fees);
        txt_total_bill_amount = findViewById(R.id.txt_total_bill_amount);
        txt_bill_pay_date_time = findViewById(R.id.txt_bill_pay_date_time);
        btn_save = findViewById(R.id.btn_save);
        btn_share = findViewById(R.id.btn_share);
        cv_bill_detail = findViewById(R.id.cv_bill_detail);
        txtEmailAddr = findViewById(R.id.txtEmailAddrId);
        txtContactNo = findViewById(R.id.txtContactNoId);
        coordinatorLayoutId = findViewById(R.id.coordinatorLayoutId);

        Intent intent =getIntent();
        List<BBPSBillDetailFinacusModel> billDetail = (List<BBPSBillDetailFinacusModel>) intent.getSerializableExtra("Bill Detail");

        //todo- added code
        if(billDetail!=null){
            String transaction_ref_id = billDetail.get(0).getTransaction_ref_id();
            String payment_ref_id =billDetail.get(0).getPayment_ref_id();
            String customer_name = billDetail.get(0).getCustomer_name();
            String customer_mobile_no = billDetail.get(0).getCustomer_mobile_no();
            String consumer_number =billDetail.get(0).getConsumer_number();
            String agent_id = billDetail.get(0).getAgent_id();
            String biller_category = billDetail.get(0).getBiller_category();
            String billerId =billDetail.get(0).getBillerId();
            String biller_name = billDetail.get(0).getBiller_name();
            String bill_number = billDetail.get(0).getBill_number();
            String bill_period = billDetail.get(0).getBill_period();
            String bill_date = billDetail.get(0).getBill_date();
            String bill_due_date = billDetail.get(0).getBill_due_date();
            String bill_amount = billDetail.get(0).getBill_amount();
            String payment_channel = billDetail.get(0).getPayment_channel();
            String payment_mode = billDetail.get(0).getPayment_mode();
            String payment_status = billDetail.get(0).getPayment_status();
            String conv_fees = billDetail.get(0).getConv_fees();
            String total_bill_amount = billDetail.get(0).getTotal_bill_amount();
            String bill_pay_date_time =billDetail.get(0).getBill_pay_date_time();
            HashMap<String, String> customerParamsList = billDetail.get(0).getCustParam();

            txt_trans_ref_id.setText(transaction_ref_id);
    //        txt_payment_ref_id.setText(payment_ref_id);
            txt_customer_name.setText(customer_name);
            txt_cust_mobile_no.setText(customer_mobile_no);
            txt_biller_category.setText(biller_category);
            txt_biller_name.setText(biller_name);
            txt_bill_number.setText(bill_number);
            txt_bill_amount.setText(bill_amount);
            txt_payment_mode.setText(payment_mode);
            txt_payment_status.setText(payment_status);
            txt_conv_fees.setText(conv_fees);
            txt_total_bill_amount.setText(total_bill_amount);
            txt_bill_pay_date_time.setText(bill_pay_date_time);
        }

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
            if (NetworkUtil.getConnectivityStatus(BBPSPaymentDetailsFinacusActivity.this)) {
                new LoadContactAddressAsyncTask(BBPSPaymentDetailsFinacusActivity.this).execute();
            } else {
                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayoutId);
            }
        } else {
            TrustMethods.displaySimErrorDialog(this);
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf(cv_bill_detail);
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf(cv_bill_detail);
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
        File pdfFile = new File(getExternalFilesDir(null), "BillPayReceipt.pdf");
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
    private void createPdf(View view) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth(), view.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        view.draw(canvas);
        document.finishPage(page);
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "BillPaymentReceipt" + timestamp + ".pdf";
        File file = new File(downloadsDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "Downloaded Successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LoadContactAddressAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String headOffice;
        String email;
        String telephoneNo;
        String website;
        private String errorCode = "";

        public LoadContactAddressAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSPaymentDetailsFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.ContactUsUrl() + "?rnd=" + String.valueOf(Math.random());
                if (!url.equals("")) {
                    TrustMethods.LogMessage(TAG, "URL:-" + url);
                    response = HttpClientWrapper.getResponceDirectalyGET(url, AppConstants.getAuth_token());
                    TrustMethods.LogMessage(TAG, "Frm Enquiry details response-->" + response);
                }
                if (response == null || response.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResult = (new JSONObject(response));
                if (jsonResult.has("error")) {
                    error = jsonResult.getString("error");
                    return error;
                }

                JSONObject contactDetObject = (new JSONObject(jsonResult.getString("contact_details")));
                if (contactDetObject.has("error")) {
                    error = contactDetObject.getString("error");
                    return error;
                }
                headOffice = contactDetObject.has("head_office") ? contactDetObject.getString("head_office") : "NA";
                email = contactDetObject.has("email") ? contactDetObject.getString("email") : "NA";
                telephoneNo = contactDetObject.has("telephone_no") ? contactDetObject.getString("telephone_no") : "NA";
                website = contactDetObject.has("website") ? contactDetObject.getString("website") : "NA";

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
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(BBPSPaymentDetailsFinacusActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else if (TrustMethods.isSessionExpiredWithString(error)){
                        AlertDialogMethod.alertDialogOk(BBPSPaymentDetailsFinacusActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    }else {
                        TrustMethods.showSnackBarMessage(this.error, coordinatorLayoutId);
                    }

                } else {
                    if (!TextUtils.isEmpty(email)) {
                        txtEmailAddr.setText(email);
                    }
                    if (!TextUtils.isEmpty(telephoneNo)) {
                        txtContactNo.setText(telephoneNo);
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
            Intent intentLogin = new Intent(getApplicationContext(), BBPSFinacusActivity.class);
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentLogin);
            method.activityCloseAnimation();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BBPSPaymentDetailsFinacusActivity.this, BBPSFinacusActivity.class);
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
        TrustMethods.showBackButtonAlert(BBPSPaymentDetailsFinacusActivity.this);
    }
}