package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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

import com.trustbank.Model.BlockDebitReason;
import com.trustbank.Model.DebitCardModels;
import com.trustbank.Model.DynamicMenuModel;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DebitCardLimitAtivity extends AppCompatActivity implements AlertDialogOkListener{
    private TrustMethods method;
    private Spinner spinnerFrmAct, spinnerDebitCardId, spinnerDebitCardlimittype;
    private TextView txtCustName, txtCustMobNo, txtCustEmail, tv_limit;
    private ArrayList<GetUserProfileModal> accountsArrayList;

    private List<String> accountList;
    private List<String> channelsList;
    private CardView debitCardView, submitCardViewId, reasonOtherTypeCardViewId,debitCardChannelview;
    private CoordinatorLayout debitCardBlockCLId;
    private LinearLayout limitlayout;
    private AlertDialogOkListener alertDialogOkListener = this;
    private Button btnSubmitCardDetails;
    private String selectedChannel, accNo="";
    private EditText limitchange;
    private DebitCardModels debitCardModels;
    List<DebitCardModels> debitCardDetailsList;

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
                        TrustMethods.naviagteToSplashScreen(DebitCardLimitAtivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SetTheme.changeToTheme(DebitCardLimitAtivity.this, false);
        setContentView(R.layout.activity_debit_card_limit_ativity);
        inIt();
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

    private void inIt() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        method = new TrustMethods(DebitCardLimitAtivity.this);
        debitCardBlockCLId = findViewById(R.id.debitCardBlockCLId);
        spinnerFrmAct = findViewById(R.id.spinnerFrmActId);
        spinnerDebitCardId = findViewById(R.id.spinnerDebitCardId);
        debitCardView = findViewById(R.id.debitCardViewId);
        debitCardChannelview = findViewById(R.id.debitCardlimittypeViewId);
        tv_limit = findViewById(R.id.tv_limit);

        submitCardViewId = findViewById(R.id.submitCardViewId);
        spinnerDebitCardlimittype = findViewById(R.id.spinnerDebitCardlimittype);
        reasonOtherTypeCardViewId = findViewById(R.id.reasonOtherTypeCardViewId);
        btnSubmitCardDetails = findViewById(R.id.btnSubmitCardDetails);
        limitchange = findViewById(R.id.ed_cardlimit);
        debitCardChannelview.setVisibility(View.GONE);
        reasonOtherTypeCardViewId.setVisibility(View.GONE);
        tv_limit.setVisibility(View.GONE);

        channelsList = new ArrayList<>();
        channelsList.add("Select Channel");
        channelsList.add("ATM");
        channelsList.add("POS");
        channelsList.add("ECOM");
        channelsList.add("Contactless");

        accNumberSpinner();

        ArrayAdapter<String> adapterchannellist = new ArrayAdapter<>(DebitCardLimitAtivity.this, android.R.layout.simple_spinner_item, channelsList);
        adapterchannellist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDebitCardlimittype.setAdapter(adapterchannellist);

        spinnerDebitCardId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != 0) {

                        if(AppConstants.getSwitchForCardNo().equals("0")) {
                            debitCardModels = (DebitCardModels) parent.getSelectedItem();
                            String status = debitCardModels.getStatus();
                            if (status.equalsIgnoreCase("Blocked") || (status.equalsIgnoreCase("Temp Blocked"))) {
                                btnSubmitCardDetails.setBackgroundColor(getResources().getColor(R.color.silverColor));
                                btnSubmitCardDetails.setClickable(false);
                                debitCardChannelview.setVisibility(View.GONE);
                                btnSubmitCardDetails.setVisibility(View.GONE);
                                reasonOtherTypeCardViewId.setVisibility(View.GONE);

                            } else {
                                btnSubmitCardDetails.setBackgroundColor(TrustMethods.getColorPrimary(DebitCardLimitAtivity.this));
                                btnSubmitCardDetails.setClickable(true);
                                debitCardChannelview.setVisibility(View.VISIBLE);
                                btnSubmitCardDetails.setVisibility(View.VISIBLE);
                            }
                        }else{
                            String selected = (String) parent.getSelectedItem();
                            btnSubmitCardDetails.setBackgroundColor(TrustMethods.getColorPrimary(DebitCardLimitAtivity.this));
                            btnSubmitCardDetails.setClickable(true);
                            reasonOtherTypeCardViewId.setVisibility(View.GONE);
                            submitCardViewId.setVisibility(View.GONE);
                            debitCardChannelview.setVisibility(View.VISIBLE);
                            reasonOtherTypeCardViewId.setVisibility(View.GONE);
                            spinnerDebitCardlimittype.setSelection(0);
                            limitchange.setText("");
                        }
                    }else{
                        submitCardViewId.setVisibility(View.GONE);
                        reasonOtherTypeCardViewId.setVisibility(View.GONE);
                        debitCardChannelview.setVisibility(View.GONE);
                        limitchange.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDebitCardlimittype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        selectedChannel = (String) adapterView.getItemAtPosition(position);
                        Log.e("test debitcardlimit", selectedChannel);
                        reasonOtherTypeCardViewId.setVisibility(View.VISIBLE);
                        submitCardViewId.setVisibility(View.VISIBLE);
                        tv_limit.setVisibility(View.VISIBLE);
                        limitchange.setText("");

                        if(selectedChannel.equals("ATM")){
                            tv_limit.setText("ATM Current Limit- " + debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getATMLimit());
                        } else if (selectedChannel.equals("POS")) {
                            tv_limit.setText("POS Current Limit- " + debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getPOSLimit());
                        } else if (selectedChannel.equals("ECOM")) {
                            tv_limit.setText("ECOM Current Limit- " + debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getECOMLimit());
                        } else if (selectedChannel.equals("Contactless")) {
                            tv_limit.setText("Contactless Current Limit- " + debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getContactlessLimit());
                        }
                    } else {
                        submitCardViewId.setVisibility(View.GONE);
                        reasonOtherTypeCardViewId.setVisibility(View.GONE);
                        limitchange.setText("");
                        tv_limit.setVisibility(View.GONE);
                        tv_limit.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case where nothing is selected in the spinner.
            }
        });

        btnSubmitCardDetails.setOnClickListener(v -> {
            String cardNo;
            if(AppConstants.getSwitchForCardNo().equals("1")){
                cardNo = debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getDebitCardNo();
            }else {
                cardNo = debitCardModels.getDebitCardNo();
            }
            String amountlimit = "";
            if (reasonOtherTypeCardViewId.getVisibility() == View.VISIBLE) {
                if (TextUtils.isEmpty(limitchange.getText().toString())) {
                    TrustMethods.showSnackBarMessage("Please enter Debit Card Limit", debitCardBlockCLId);
                    return;
                }else {
                    amountlimit = limitchange.getText().toString();
                }
            }else if (spinnerDebitCardlimittype.getSelectedItem().toString().equals("Select Channel")) {
                TrustMethods.showSnackBarMessage("Please Select Channel", debitCardBlockCLId);
                return;
            }

            if (!TextUtils.isEmpty(cardNo) && !TextUtils.isEmpty(accNo) && !cardNo.equals("Select Debit Card")) {
                Intent intent = new Intent(DebitCardLimitAtivity.this, OtpVerificationActivity.class);
                intent.putExtra("checkTransferType", "DebitCardLimit");
                intent.putExtra("accountNo", accNo);
                intent.putExtra("cardNo", cardNo);
                intent.putExtra("amountlimit", amountlimit);
                intent.putExtra("Channel", selectedChannel);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                TrustMethods.showSnackBarMessage("Account No or Card No cannot be empty.", debitCardBlockCLId);
            }
        });
    }
    private void accNumberSpinner() {
        try {
            accountsArrayList = method.getArrayList(DebitCardLimitAtivity.this, "AccountListPref");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (accountsArrayList != null && accountsArrayList.size() > 0) {
            accountList = new ArrayList<>();
            accountList.add(0, "Select Account Number");
            for (int i = 0; i < accountsArrayList.size(); i++) {
                GetUserProfileModal getUserProfileModal = accountsArrayList.get(i);
                if(AppConstants.getAtm_card_not_managed().equals("1")){
                    if (TrustMethods.isAccountTypeValidCard(getUserProfileModal.getHeadid(), AppConstants.getCard_list())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo + " - " + accTypeCode);
                    }
                }else {
                    if (TrustMethods.isAccountTypeValidcard(getUserProfileModal.getHeadid(), AppConstants.getCard_list(), getUserProfileModal.getCardActive())) {
                        String accNo = getUserProfileModal.getAccNo();
                        String accTypeCode = getUserProfileModal.getAcTypeCode();
                        accountList.add(accNo + " - " + accTypeCode);
                    }
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(DebitCardLimitAtivity.this, android.R.layout.simple_spinner_item, accountList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFrmAct.setAdapter(adapter);
        }

        spinnerFrmAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    if (position != 0) {
                        String selectedAccNo = (String) adapterView.getItemAtPosition(position);
                        accNo = "";
                        if (selectedAccNo.contains("-")) {
                            String[] accounts = selectedAccNo.split("-");
                            accNo = accounts[0];
                        } else {
                            accNo = selectedAccNo;
                        }

                        if(AppConstants.getSwitchForCardNo().equals("1")){
                            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(DebitCardLimitAtivity.this)) {
                                if (NetworkUtil.getConnectivityStatus(DebitCardLimitAtivity.this)) {
                                    new LoadDebitCardSwitchAsyncTask(DebitCardLimitAtivity.this, accNo).execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), debitCardBlockCLId);
                                }
                            } else {
                                TrustMethods.displaySimErrorDialog(DebitCardLimitAtivity.this);
                            }
                        }else {
                            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(DebitCardLimitAtivity.this)) {
                                if (NetworkUtil.getConnectivityStatus(DebitCardLimitAtivity.this)) {
                                    new LoadDebitCardDetailsAsyncTask(DebitCardLimitAtivity.this, accNo).execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), debitCardBlockCLId);
                                }
                            } else {
                                TrustMethods.displaySimErrorDialog(DebitCardLimitAtivity.this);
                            }
                        }

                    }else{
                        accNo="";
                        debitCardView.setVisibility(View.GONE);
                    }
                    limitchange.setText("");
                    spinnerDebitCardId.setSelection(0);
                    submitCardViewId.setVisibility(View.GONE);
                    reasonOtherTypeCardViewId.setVisibility(View.GONE);
                    debitCardChannelview.setVisibility(View.GONE);
                    spinnerDebitCardlimittype.setSelection(0);
                    tv_limit.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }



    @Override
    public void onDialogOk(int resultCode) {
        switch (resultCode) {
            case 0:
                Intent intentLogin = new Intent(getApplicationContext(), LockActivity.class);
                intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLogin);
                method.activityCloseAnimation();
                break;
            default:
                break;
        }

    }
    private class LoadDebitCardDetailsAsyncTask extends AsyncTask<Void, Void, String> {
        private String error = "";
        private Context ctx;
        private String response;
        private ProgressDialog pDialog;
        private String actionName = "DEBIT_CARDS_GET";
        private String mAccNo;
        private String result;
        private ArrayList<DebitCardModels> debitCardDetailsModelArrayList = new ArrayList<>();
        private List<BlockDebitReason> blockDebitReasonList = new ArrayList<>();
        private String errorCode;

        public LoadDebitCardDetailsAsyncTask(Context ctx, String accNo) {
            this.ctx = ctx;
            this.mAccNo = accNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DebitCardLimitAtivity.this);
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
                    JSONArray accountJsonArray = jsonResponse.getJSONObject("response").getJSONArray("card_details");


                    DebitCardModels debitCardModel = new DebitCardModels();
                    debitCardModel.setDebitCardNo("");
                    debitCardModel.setStatus("");
                    debitCardModel.setDebitCardNoForDisplay("Select Debit Card");
                    debitCardDetailsModelArrayList.add(0, debitCardModel);

                    for (int i = 0; i < accountJsonArray.length(); i++) {
                        JSONObject cardDeatils = accountJsonArray.getJSONObject(i);
                        String cardNo = cardDeatils.has("card_no") ? cardDeatils.getString("card_no") : "";
                        String cardStatus = cardDeatils.has("card_status") ? cardDeatils.getString("card_status") : "";
                        String maskedCardNo = cardDeatils.has("masked_card_no") ? cardDeatils.getString("masked_card_no") : "";
                        DebitCardModels debitCardModels = new DebitCardModels();
                        debitCardModels.setDebitCardNo(cardNo);
                        debitCardModels.setStatus(cardStatus);
                        debitCardModels.setDebitCardNoForDisplay(maskedCardNo + " - " + cardStatus);
                        debitCardDetailsModelArrayList.add(debitCardModels);
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
        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (debitCardDetailsModelArrayList != null && debitCardDetailsModelArrayList.size() != 0) {
                    debitCardView.setVisibility(View.VISIBLE);
                    submitCardViewId.setVisibility(View.GONE);
                    ArrayAdapter<DebitCardModels> adapter = new ArrayAdapter<>(DebitCardLimitAtivity.this, android.R.layout.simple_spinner_item, debitCardDetailsModelArrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDebitCardId.setAdapter(adapter);
                } else {
                    if (!this.error.equals("")) {
                        if (TrustMethods.isSessionExpired(errorCode)) {
                            AlertDialogMethod.alertDialogOk(DebitCardLimitAtivity.this,
                                    getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                    0, false, alertDialogOkListener);
                        } else {
                            TrustMethods.showSnackBarMessage(this.error, debitCardBlockCLId);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class LoadDebitCardSwitchAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        ProgressDialog pDialog;
        private TMessage msg;
        String response,accno;
        String result;
        private String customerId = AppConstants.getCLIENTID();
        private String errorCode;
        GenerateStanRRNModel generateStanRRNModel;
        TMessage responseMsg;
        List<String> debitCardList=null;

        public LoadDebitCardSwitchAsyncTask(Context ctx, String accno) {
            this.ctx = ctx;
            this.accno = accno;;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DebitCardLimitAtivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try {
                // String jsonString = "{\"filter\":[\"stan\"]}";
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(DebitCardLimitAtivity.this, jsonString,
                        TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());

                String url = TrustURL.httpCallUrl();
                MessageDtoBuilder msgDto = new MessageDtoBuilder();

                if (generateStanRRNModel.getError() != null) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                } else {
                    msg = msgDto.getCardNumberDto(accno.trim(), generateStanRRNModel.getChannel_ref_no());

                    Log.d("msg.GetXml():", msg.GetXml());
                    String base64EncodedRequestJson = Base64.encodeToString(msg.GetXml().getBytes(),Base64.NO_WRAP);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", base64EncodedRequestJson);

                    if (!url.equals("")) {
                        TrustMethods.LogMessage("URL", "URL:-" + url);
                        result = HttpClientWrapper.postWitInstituteAuthHeader(url, jsonObject.toString()
                                , AppConstants.getAuth_token(), AppConstants.INSTITUTION_ID);
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
                        response = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";

                        String responseValue = TrustMethods.decodeBase64(response);
                        Log.d("responseValue", responseValue);

                        ResponseEntity resParse = TMessage.ParseMessage(responseValue);
                        responseMsg = (TMessage) resParse.response;
                        String data = TrustMethods.decodeBase64(responseMsg.Data.Value);

                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(data)));
                            NodeList nodeList = document.getElementsByTagName("card");
                            debitCardList = new ArrayList<>();
                            debitCardList.add("Select Debit Card");
                            debitCardDetailsList=new ArrayList<>();
                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                String card_no = element.getElementsByTagName("card_no").item(0).getTextContent();
                                String card_status = element.getElementsByTagName("card_status").item(0).getTextContent();
                                String ATMLimit = element.getElementsByTagName("atm_limit").item(0).getTextContent();
                                String POSLimit = element.getElementsByTagName("pos_limit").item(0).getTextContent();
                                String ECOMLimit = element.getElementsByTagName("ecom_limit").item(0).getTextContent();
                                String ContactlessLimit = element.getElementsByTagName("contactless_limit").item(0).getTextContent();

                                StringBuilder maskedCardNo= new StringBuilder(card_no);
                                for (int i = 4; i < maskedCardNo.length()-4; i++) {
                                    maskedCardNo.setCharAt(i, 'x');
                                }

                                DebitCardModels model = new DebitCardModels();
                                model.setDebitCardNo(card_no);
                                model.setStatus(card_status);
                                model.setATMLimit(ATMLimit);
                                model.setPOSLimit(POSLimit);
                                model.setECOMLimit(ECOMLimit);
                                model.setContactlessLimit(ContactlessLimit);
                                model.setMaskedCardNo(maskedCardNo.toString());
                                debitCardList.add(maskedCardNo.toString());
                                debitCardDetailsList.add(model);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
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
                if (!error.equals("")) {
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(DebitCardLimitAtivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(DebitCardLimitAtivity.this, "ERROR!!!", error, "Ok",-1,false,alertDialogOkListener);
                    }
                }else {
                    if(debitCardList!= null && !debitCardList.isEmpty()) {
                        debitCardView.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DebitCardLimitAtivity.this, android.R.layout.simple_spinner_item, debitCardList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDebitCardId.setAdapter(adapter);
                    }else{
                        debitCardView.setVisibility(View.GONE);
                        AlertDialogMethod.alertDialogOk(DebitCardLimitAtivity.this, "", responseMsg.ActCodeDesc.Value, "Ok",-1,false,alertDialogOkListener);
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
                for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_debit_card_limit") || menuModel.getMenucode().equalsIgnoreCase("mnu_block_debit_card_cbs")) {
                        Intent intent = new Intent(DebitCardLimitAtivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }

                if(AppConstants.account_group){
                    Intent intent = new Intent(DebitCardLimitAtivity.this, AccountsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.upi_group) {
                    Intent intent = new Intent(DebitCardLimitAtivity.this, UPIActivityMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.service_group) {
                    Intent intent = new Intent(DebitCardLimitAtivity.this, ServiceRequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.cards_group) {
                    Intent intent = new Intent(DebitCardLimitAtivity.this, Cards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.locate_us_group) {
                    Intent intent = new Intent(DebitCardLimitAtivity.this, LocateUs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }else if (AppConstants.need_help_group) {
                    Intent intent = new Intent(DebitCardLimitAtivity.this, NeedHelp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }

                Intent intent = new Intent(DebitCardLimitAtivity.this, Cards.class);
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
        TrustMethods.showBackButtonAlert(DebitCardLimitAtivity.this);
    }
}