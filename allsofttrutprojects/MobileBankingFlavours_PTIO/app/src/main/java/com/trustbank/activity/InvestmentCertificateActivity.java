package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.AmortizationChartModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.R;
import com.trustbank.adapter.AmortizationChartAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvestmentCertificateActivity extends AppCompatActivity implements View .OnClickListener{
    TextView toolbar, Cert_title, certi_num,prod_name, deposit_date, expiry_date, saving_account, payment_freq, deposit_amt, interest, subtotal,
            taxes, total_receivable, agency, employee;
    Spinner spinnerFrmAct;
    private List<String> accountList;
    LinearLayout download_ll;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    TrustMethods method;
    ImageView backButton_new;
    Button btn_download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(InvestmentCertificateActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(InvestmentCertificateActivity.this, false);
        setContentView(R.layout.activity_investment_certificate);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.InvestmentCertificate);
        Init();
    }

    private void Init() {

        Cert_title=findViewById(R.id.Cert_title);
        method=new TrustMethods(InvestmentCertificateActivity.this);
        spinnerFrmAct=findViewById(R.id.spinnerFrmActId);
        download_ll=findViewById(R.id.download_ll);
        backButton_new = findViewById(R.id.backButton_new);
        certi_num = findViewById(R.id.certi_num);
        prod_name = findViewById(R.id.prod_name);
        deposit_date = findViewById(R.id.deposit_date);
        expiry_date = findViewById(R.id.expiry_date);
        saving_account = findViewById(R.id.saving_account);
        payment_freq = findViewById(R.id.payment_freq);
        deposit_amt = findViewById(R.id.deposit_amt);
        interest = findViewById(R.id.interest);
        subtotal = findViewById(R.id.subtotal);
        taxes = findViewById(R.id.taxes);
        total_receivable = findViewById(R.id.total_receivable);
        agency = findViewById(R.id.agency);
        employee = findViewById(R.id.employee);
        btn_download = findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);
        backButton_new.setOnClickListener(this);
        btn_download.setEnabled(false);
        accNumberSpinner();
    }

    private void accNumberSpinner() {
        try {
            accountsArrayList = method.getArrayList(InvestmentCertificateActivity.this, "AccountListPref");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (accountsArrayList != null && accountsArrayList.size() > 0) {
            accountList = new ArrayList<>();
            accountList.add(0, getResources().getString(R.string.SelectAccountNumber));
            for (int i = 0; i < accountsArrayList.size(); i++) {
                GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                if (TrustMethods.investmentACC(getUserProfileModal.getActType())) {
                    String accNo = getUserProfileModal.getAccNo();
                    String accTypeCode = getUserProfileModal.getAcTypeCode();
                    accountList.add(accNo);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(InvestmentCertificateActivity.this, android.R.layout.simple_spinner_item, accountList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFrmAct.setAdapter(adapter);
        }

        spinnerFrmAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        String selectedAccNo = (String) adapterView.getItemAtPosition(position);
                        String accNo = "";
                        if (selectedAccNo.contains("-")) {
                            String[] accounts = selectedAccNo.split("-");
                            accNo = accounts[0];
                        } else {
                            accNo = selectedAccNo;
                        }

                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(InvestmentCertificateActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(InvestmentCertificateActivity.this)) {
                                new AsyncTaskGetCertificate(InvestmentCertificateActivity.this, accNo).execute();
                            } else {
                                Toast.makeText(InvestmentCertificateActivity.this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(InvestmentCertificateActivity.this);
                        }
                    } else {
                         download_ll.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton_new:
                Intent intent = new Intent(InvestmentCertificateActivity.this, InvestmentMenus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_download:
                createPdf(download_ll);
                break;
        }
    }
    private void createPdf(View view) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth(), view.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        view.draw(canvas);
        document.finishPage(page);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "InvestmentCertificate" + timestamp + ".pdf";

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
        }

        try {
            if (uri != null) {
                OutputStream outputStream = contentResolver.openOutputStream(uri);
                document.writeTo(outputStream);
                document.close();
                outputStream.close();
                Toast.makeText(this, getResources().getString(R.string.DownloadedSuccessfully), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.WentWrong), Toast.LENGTH_SHORT).show();
        }
    }
    private class AsyncTaskGetCertificate extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response,  AccountId, AccountNumber, AccountNumberForDisplay, AMTInWords,Capital, Cedula, CertificateNumber, ClientId, ClientName, FromDate,
                InterestAmount, IntRate, NetReceive, NoOfDays, Orgelement, PaymentFrequency, SchemeName, SubTotal, TDSAmount, ToDate, UserName, workingDate;
        TrustMethods methods;
        JSONArray data;
        String result, accno;
        String actionName ="GET_INVESTMENT_CERTIFICATE";

        public AsyncTaskGetCertificate(Context ctx, String accno) {
            this.error = "";
            this.ctx=ctx;
            this.accno=accno;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InvestmentCertificateActivity.this);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetCertificate(accno,"99");
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
                    data = jsonResponse.getJSONObject("response").getJSONArray("data");
                    if (data.length() == 0) {
                        error = getResources().getString(R.string.Nocerti);
                    }

                    for(int i=0; i<data.length(); i++){
                        JSONObject jsonObject = data.getJSONObject(i);
                        AccountId = jsonObject.has("AccountId") ? jsonObject.getString("AccountId"):"NA";
                        AccountNumber = jsonObject.has("AccountNumber")?jsonObject.getString("AccountNumber"):"NA";
                        AccountNumberForDisplay = jsonObject.has("AccountNumberForDisplay")?jsonObject.getString("AccountNumberForDisplay"):"NA";
                        AMTInWords = jsonObject.has("AMTInWords")?jsonObject.getString("AMTInWords"):"NA";
                        Capital = jsonObject.has("Capital")?jsonObject.getString("Capital"):"NA";
                        Cedula = jsonObject.has("Cedula")?jsonObject.getString("Cedula"):"NA";
                        CertificateNumber = jsonObject.has("CertificateNumber")?jsonObject.getString("CertificateNumber"):"NA";
                        ClientId = jsonObject.has("ClientId")?jsonObject.getString("ClientId"):"NA";
                        ClientName = jsonObject.has("ClientName")?jsonObject.getString("ClientName"):"NA";
                        FromDate = jsonObject.has("FromDate")?jsonObject.getString("FromDate"):"NA";
                        InterestAmount = jsonObject.has("InterestAmount")?jsonObject.getString("InterestAmount"):"NA";
                        IntRate = jsonObject.has("IntRate")?jsonObject.getString("IntRate"):"NA";
                        NetReceive = jsonObject.has("NetReceive")?jsonObject.getString("NetReceive"):"NA";
                        NoOfDays = jsonObject.has("NoOfDays")?jsonObject.getString("NoOfDays"):"NA";
                        Orgelement = jsonObject.has("Orgelement")?jsonObject.getString("Orgelement"):"NA";
                        PaymentFrequency = jsonObject.has("PaymentFrequency")? jsonObject.getString("PaymentFrequency"):"NA";
                        SchemeName =  jsonObject.has("SchemeName")?jsonObject.getString("SchemeName"):"NA";
                        SubTotal = jsonObject.has("SubTotal")? jsonObject.getString("SubTotal"):"NA";
                        TDSAmount =  jsonObject.has("TDSAmount")?jsonObject.getString("TDSAmount"):"NA";
                        ToDate =  jsonObject.has("ToDate")?jsonObject.getString("ToDate"):"NA";
                        UserName = jsonObject.has("UserName")? jsonObject.getString("UserName"):"NA";
                        workingDate =  jsonObject.has("workingDate")?jsonObject.getString("workingDate"):"NA";
                    }
                }else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                    error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (this.error != "") {
                methods.message(this.ctx, error);
                download_ll.setVisibility(View.GONE);
                btn_download.setEnabled(false);
                return;
            }else{
                download_ll.setVisibility(View.VISIBLE);
                btn_download.setEnabled(true);
                certi_num.setText(CertificateNumber);
                prod_name.setText(SchemeName);
                deposit_date.setText(FromDate);
                expiry_date.setText(ToDate);
                saving_account.setText(AccountNumberForDisplay);
                payment_freq.setText(PaymentFrequency);
                deposit_amt.setText("$"+Capital);
                interest.setText("$"+IntRate);
                subtotal.setText("$"+SubTotal);
                taxes.setText("$"+TDSAmount);
                total_receivable.setText("$"+NetReceive);
                agency.setText(Orgelement);
                employee.setText(UserName);
            }
        }
    }
}