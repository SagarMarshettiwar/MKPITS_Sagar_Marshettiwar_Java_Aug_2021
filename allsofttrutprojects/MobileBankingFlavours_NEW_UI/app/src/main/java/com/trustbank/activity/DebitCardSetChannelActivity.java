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
import android.widget.Spinner;
import android.widget.TextView;

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

public class DebitCardSetChannelActivity extends AppCompatActivity implements AlertDialogOkListener{

    private TrustMethods method;
    private Spinner spinnerFrmAct, spinnerDebitCardId, spinnerDebitCardlimittype, spinnerActionId;
    private ArrayList<GetUserProfileModal> accountsArrayList;
    private List<String> accountList;
    private CardView debitCardView, debitCardlimittypeViewId, submitCardViewId,actionCardViewId;
    private AlertDialogOkListener alertDialogOkListener = this;
    CoordinatorLayout coordinatorLayout;
    private Button btnSubmit;
    private String cardStatus="", accNo="";
    List<DebitCardModels> debitCardDetailsList;
    TextView tv_card_status;

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
                        TrustMethods.naviagteToSplashScreen(DebitCardSetChannelActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SetTheme.changeToTheme(DebitCardSetChannelActivity.this, false);
        setContentView(R.layout.activity_debit_card_set_channel);
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
        method = new TrustMethods(DebitCardSetChannelActivity.this);
        debitCardlimittypeViewId = findViewById(R.id.debitCardlimittypeViewId);
        spinnerFrmAct = findViewById(R.id.spinnerFrmActId);
        spinnerDebitCardId = findViewById(R.id.spinnerDebitCardId);
        debitCardView = findViewById(R.id.debitCardViewId);
        spinnerDebitCardlimittype = findViewById(R.id.spinnerDebitCardlimittype);
        submitCardViewId = findViewById(R.id.submitCardViewId);
        btnSubmit = findViewById(R.id.btnSubmit);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        tv_card_status = findViewById(R.id.tv_card_status);
        actionCardViewId = findViewById(R.id.actionCardViewId);
        spinnerActionId = findViewById(R.id.spinnerActionId);

        debitCardView.setVisibility(View.GONE);
        debitCardlimittypeViewId.setVisibility(View.GONE);
        submitCardViewId.setVisibility(View.GONE);
        tv_card_status.setVisibility(View.GONE);
        actionCardViewId.setVisibility(View.GONE);

        accNumberSpinner();

        spinnerDebitCardId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != 0) {
                        String selectedCard = parent.getSelectedItem().toString();
                        cardStatus = debitCardDetailsList.get(position-1).getStatus();
                        debitCardlimittypeViewId.setVisibility(View.VISIBLE);
                        submitCardViewId.setVisibility(View.GONE);
                        actionCardViewId.setVisibility(View.VISIBLE);
                        tv_card_status.setVisibility(View.VISIBLE);
                        tv_card_status.setText(" Card Status: "+ debitCardDetailsList.get(position-1).getStatus());
                        spinnerChannelType();
                    }else{
                        cardStatus="";
                        tv_card_status.setText("");
                        actionCardViewId.setVisibility(View.GONE);
                        tv_card_status.setVisibility(View.GONE);
                        debitCardlimittypeViewId.setVisibility(View.GONE);
                        submitCardViewId.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinnerChannelType() {
        List<String> channelsList = new ArrayList<>();
        channelsList.add("Select Channel");
        channelsList.add("ATM");
        channelsList.add("POS");
        channelsList.add("ECOM");
        channelsList.add("Contactless");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(DebitCardSetChannelActivity.this, android.R.layout.simple_spinner_item, channelsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDebitCardlimittype.setAdapter(adapter);

        spinnerDebitCardlimittype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != 0) {
                        String selected = parent.getSelectedItem().toString();
                        if(selected.equalsIgnoreCase("ATM")){
                            if(debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getATMStatus().equalsIgnoreCase("Active")){
                                spinnerActionId.setSelection(2);
                            }else{
                                spinnerActionId.setSelection(1);
                            }
                        } else if(selected.equalsIgnoreCase("POS")){
                            if(debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getPOSStatus().equalsIgnoreCase("Active")){
                                spinnerActionId.setSelection(2);
                            }else{
                                spinnerActionId.setSelection(1);
                            }
                        }else if(selected.equalsIgnoreCase("ECOM")){
                            if(debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getECOMStatus().equalsIgnoreCase("Active")){
                                spinnerActionId.setSelection(2);
                            }else{
                                spinnerActionId.setSelection(1);
                            }
                        }else if(selected.equalsIgnoreCase("Contactless")){
                            if(debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getContactlessStatus().equalsIgnoreCase("Active")){
                                spinnerActionId.setSelection(2);
                            }else{
                                spinnerActionId.setSelection(1);
                            }
                        }
                        spinnerActionId.setEnabled(false);
                        actionCardViewId.setVisibility(View.VISIBLE);
                        submitCardViewId.setVisibility(View.VISIBLE);
                    }else {
                        submitCardViewId.setVisibility(View.GONE);
                        actionCardViewId.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        List<String> actionList = new ArrayList<>();
        actionList.add("Select Action");
        actionList.add("Active");
        actionList.add("Deactive");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(DebitCardSetChannelActivity.this, android.R.layout.simple_spinner_item, actionList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActionId.setAdapter(adapter1);

        spinnerActionId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != 0) {
                        String selected = parent.getSelectedItem().toString();
                        submitCardViewId.setVisibility(View.VISIBLE);
                    }else{
                        submitCardViewId.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardNo = debitCardDetailsList.get(spinnerDebitCardId.getSelectedItemPosition()-1).getDebitCardNo();
                String selectedChannel = spinnerDebitCardlimittype.getSelectedItem().toString();

                if(selectedChannel.equals("Select Channel")){
                    TrustMethods.showSnackBarMessage("Please Select Card Limit Type", coordinatorLayout);
                }else if(spinnerActionId.getSelectedItem().equals("Select Action")){
                    TrustMethods.showSnackBarMessage("Please Select Action", coordinatorLayout);
                }else{
                    Intent intent = new Intent(DebitCardSetChannelActivity.this, OtpVerificationActivity.class);
                    intent.putExtra("checkTransferType", "DebitCardSetChannel");
                    intent.putExtra("accountNo", accNo);
                    intent.putExtra("cardNo", cardNo);
                    intent.putExtra("Channel", selectedChannel);
                    intent.putExtra("Action", spinnerActionId.getSelectedItem().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private void accNumberSpinner() {
        try {
            accountsArrayList = method.getArrayList(DebitCardSetChannelActivity.this, "AccountListPref");
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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(DebitCardSetChannelActivity.this, android.R.layout.simple_spinner_item, accountList);
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

                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(DebitCardSetChannelActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(DebitCardSetChannelActivity.this)) {
                                new LoadDebitCardSwitchAsyncTask(DebitCardSetChannelActivity.this, accNo).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(DebitCardSetChannelActivity.this);
                        }

                    }else{
                        accNo="";
                        debitCardView.setVisibility(View.GONE);
                    }

                    spinnerDebitCardId.setSelection(0);
                    spinnerActionId.setSelection(0);
                    spinnerDebitCardlimittype.setSelection(0);
                    submitCardViewId.setVisibility(View.GONE);
                    debitCardlimittypeViewId.setVisibility(View.GONE);
                    actionCardViewId.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
            pDialog = new ProgressDialog(DebitCardSetChannelActivity.this);
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
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(DebitCardSetChannelActivity.this, jsonString,
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
                                String atm_status = element.getElementsByTagName("atm_status").item(0).getTextContent();
                                String pos_status = element.getElementsByTagName("pos_status").item(0).getTextContent();
                                String ecom_status = element.getElementsByTagName("ecom_status").item(0).getTextContent();
                                String contactless_status = element.getElementsByTagName("contactless_status").item(0).getTextContent();

                                StringBuilder maskedCardNo= new StringBuilder(card_no);
                                for (int i = 4; i < maskedCardNo.length()-4; i++) {
                                    maskedCardNo.setCharAt(i, 'x');
                                }

                                DebitCardModels model = new DebitCardModels();
                                model.setDebitCardNo(card_no);
                                model.setStatus(card_status);
                                model.setATMStatus(atm_status);
                                model.setPOSStatus(pos_status);
                                model.setECOMStatus(ecom_status);
                                model.setContactlessStatus(contactless_status);
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
                        AlertDialogMethod.alertDialogOk(DebitCardSetChannelActivity.this, getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(DebitCardSetChannelActivity.this, "ERROR!!!", error, "Ok",-1,false,alertDialogOkListener);
                    }
                }else {
                    if(debitCardList!= null && !debitCardList.isEmpty()) {
                        debitCardView.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DebitCardSetChannelActivity.this, android.R.layout.simple_spinner_item, debitCardList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDebitCardId.setAdapter(adapter);
                    }else{
                        debitCardView.setVisibility(View.GONE);
                        AlertDialogMethod.alertDialogOk(DebitCardSetChannelActivity.this, "", responseMsg.ActCodeDesc.Value, "Ok",-1,false,alertDialogOkListener);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDialogOk(int resultCode) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_debit_card_set_channel")) {
                        Intent intent = new Intent(DebitCardSetChannelActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }

                if(AppConstants.account_group){
                    Intent intent = new Intent(DebitCardSetChannelActivity.this, AccountsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.upi_group) {
                    Intent intent = new Intent(DebitCardSetChannelActivity.this, UPIActivityMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.service_group) {
                    Intent intent = new Intent(DebitCardSetChannelActivity.this, ServiceRequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.cards_group) {
                    Intent intent = new Intent(DebitCardSetChannelActivity.this, Cards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.locate_us_group) {
                    Intent intent = new Intent(DebitCardSetChannelActivity.this, LocateUs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }else if (AppConstants.need_help_group) {
                    Intent intent = new Intent(DebitCardSetChannelActivity.this, NeedHelp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }

                Intent intent = new Intent(DebitCardSetChannelActivity.this, Cards.class);
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
        TrustMethods.showBackButtonAlert(DebitCardSetChannelActivity.this);
    }
}