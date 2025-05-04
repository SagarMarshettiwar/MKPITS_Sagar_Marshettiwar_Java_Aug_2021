package com.trustbank.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.BottomDynamicMenuModel;
import com.trustbank.Model.CheckSimInfoModel;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.fragment.WelcomeMessageDiagligFragment;
import com.trustbank.helper.LocaleHelper;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SessionManager;
import com.trustbank.util.SetTheme;
import com.trustbank.util.SharePreferenceUtils;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.trustbank.util.AppConstants.isAutoReadOTPEnabled;
import static com.trustbank.util.MBank.loadAppLogo;

public class LockActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogOkListener {
    @BindViews({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_clear})
    List<View> btnNumPads;

    @BindViews({R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4})
    List<EditText> dots;

    private ImageView ivAppLogo, imgClient,btn_scanfingure;
    private static final int MAX_LENGHT = 4;
    private String codeString = "";
    private CoordinatorLayout coordinatorLayout;
    private TrustMethods method;
    private SessionManager sessionManager;
    private TextView txtShowHide, textClient, txtVersionNameId;
    private EditText mPin1;
    private EditText mPin2;
    private ImageView btnClientIdOk;
    private EditText mPin3;
    private EditText mPin4, etClient;
    private String versionName;
    private String simErrorMsg;
    private LinearLayout linearClient, ll_fp;
    private RelativeLayout linearParentClientId;
    AlertDialogOkListener alertDialogOkListener = this;
    private SharePreferenceUtils sharePreferenceUtils;
    private AutoCompleteTextView autoCompleteTextViewClient_Id;
    private LinearLayout linearAutoComplete;
    private ArrayAdapter<String> mAdapter = null;
    private  List<String> clientIdList = null;
    private ImageView btnEditClientID;
    ArrayList<DynamicMenuModel> DynamicMenuModels;
    ArrayList<DynamicMenuModel> DynamicSubMenuModels;
    HashMap<String,List<DynamicMenuModel>>SubMenuMap;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.changeToTheme(LockActivity.this, true);
        setContentView(R.layout.activity_lock_new);

        ButterKnife.bind(this);
        inIt();
    }

    private void inIt() {
        try {
            ivAppLogo = findViewById(R.id.ivAppLogo);
            loadAppLogo(ivAppLogo);

            method = new TrustMethods(LockActivity.this);
            sessionManager = new SessionManager(LockActivity.this);
            TextView txtForgotPin = findViewById(R.id.txtForgotPinId);
            txtShowHide = findViewById(R.id.txtShowHideId);
            coordinatorLayout = findViewById(R.id.coordinatorLayoutId);
            ImageView btnLogin = findViewById(R.id.btn_space);
            mPin1 = findViewById(R.id.dot_1);
            mPin2 = findViewById(R.id.dot_2);
            mPin3 = findViewById(R.id.dot_3);
            mPin4 = findViewById(R.id.dot_4);

            sharePreferenceUtils = new SharePreferenceUtils(LockActivity.this);
            sharePreferenceUtils.clearValue(AppConstants.AUTH_TOKEN);
            textClient = findViewById(R.id.textClientId);
            imgClient = findViewById(R.id.imgClientId);
            etClient = findViewById(R.id.etClientId);
            btnClientIdOk = findViewById(R.id.btnClientIdOk);
            linearClient = findViewById(R.id.linearClientId);
            linearParentClientId = findViewById(R.id.linearParentClientId);
            txtVersionNameId = findViewById(R.id.txtVersionNameId);
            autoCompleteTextViewClient_Id = findViewById(R.id.autoCompleteTextViewClient_Id);
            linearAutoComplete=findViewById(R.id.layoutAutoComplete);
            ll_fp=findViewById(R.id.ll_fp);
            btn_scanfingure=findViewById(R.id.btn_scanfingure);
            btnEditClientID=findViewById(R.id.imgEditClient);

            btnEditClientID.setOnClickListener(this);
            btn_scanfingure.setOnClickListener(this);


            versionName = TrustMethods.getVersionName(LockActivity.this);
            txtVersionNameId.setText("Application Version " + versionName);
            AppConstants.setUSERMOBILENUMBER(sessionManager.getMobileNUmber(SessionManager.KEY_MOBILE_NO, SessionManager.KEY_MOBILE_NO_OLD));
            if (!TextUtils.isEmpty(AppConstants.getCLIENTID())) {
                AppConstants.setCLIENTID(AppConstants.getCLIENTID());
            } else {
                AppConstants.setCLIENTID(sessionManager.getClientId());
            }

            txtForgotPin.setOnClickListener(this);
            btnLogin.setOnClickListener(this);
            txtShowHide.setOnClickListener(this);

            if (!TextUtils.isEmpty(AppConstants.getCLIENTID())) {
                linearParentClientId.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(AppConstants.getCLIENTID())) {
                    String customerId = AppConstants.getCLIENTID();
                    if (customerId.length() == 1) {
                        textClient.setText(LockActivity.this.getResources().getString(R.string.CustomerIDXXXX)+ customerId);
                    } else {
                        String clientid = customerId.substring(customerId.length() - 2);
                        textClient.setText(LockActivity.this.getResources().getString(R.string.CustomerIDXXXX)+ clientid);
                    }
                }
            }

            if (isAutoReadOTPEnabled) {
                Intent intent = getIntent();
                if (intent.getStringExtra(AppConstants.SIM_ERROR_MSG) != null) {
                    simErrorMsg = intent.getStringExtra(AppConstants.SIM_ERROR_MSG);
                    if (simErrorMsg.equalsIgnoreCase(AppConstants.SIM_NOT_EXISTS)) {
                        AlertDialogMethod.alertDialogOk(LockActivity.this, "NO SIM Card", "No " +
                                        "Sim Card Not Detected. Please insert the sim in slot and try again",
                                getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
                    }
                } else if (!TrustMethods.isSimVerified(this)) {
                    CheckSimInfoModel checkSimInfoModel = TrustMethods.getSimDetails(this);
                    AlertDialogMethod.alertDialogOk(LockActivity.this, "SIM not detected for mobile number " + checkSimInfoModel.getMobileNumber(), "" +
                                    "Click ok to register with new number",
                            getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                }
            }

            imgClient.setOnClickListener(v -> {
                try {
                    linearClient.setVisibility(View.GONE);
                    etClient.setVisibility(View.VISIBLE);
                    etClient.setText(AppConstants.getCLIENTID());
                    linearAutoComplete.setVisibility(View.GONE);
                    autoCompleteTextViewClient_Id.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            btnClientIdOk.setOnClickListener(v -> {
                try {
                    if (TextUtils.isEmpty(etClient.getText().toString().trim())) {
                        TrustMethods.message(LockActivity.this, "Client id cannot be empty.");
                    } else {
                        TrustMethods.hideSoftKeyboard(LockActivity.this);
                        etClient.clearFocus();
                        linearClient.setVisibility(View.VISIBLE);
                        etClient.setVisibility(View.GONE);
                        btnClientIdOk.setVisibility(View.GONE);
                        linearParentClientId.setVisibility(View.VISIBLE);
                        String customerId = etClient.getText().toString().trim();
                        if (customerId.length() == 1) {
                            textClient.setText(LockActivity.this.getResources().getString(R.string.CustomerIDXXXX)+ customerId);
                        } else {
                            String clientid = customerId.substring(customerId.length() - 2);
                            textClient.setText(LockActivity.this.getResources().getString(R.string.CustomerIDXXXX)+ clientid);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if(method.Getfingureprintpref(LockActivity.this)){
                ll_fp.setVisibility(View.VISIBLE);
            }else{
                ll_fp.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @OnClick(R.id.btn_clear)
    public void onClear() {
        if (codeString.length() > 0) {
            codeString = removeLastChar(codeString);
            setDotImagesState();
        }
    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void onClick(Button button) {


        TrustMethods.hideSoftKeyboard(LockActivity.this);

        getStringCode(button.getId());

        if (codeString.length() == MAX_LENGHT) {
            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
                if (NetworkUtil.getConnectivityStatus(LockActivity.this)) {
                    String clientId = "";
                    if (etClient.getVisibility() == View.VISIBLE) {
                        clientId = etClient.getText().toString().trim();
                    } else if (autoCompleteTextViewClient_Id.getVisibility() == View.VISIBLE) {
                        clientId = autoCompleteTextViewClient_Id.getText().toString();
                    } else {
                        clientId = AppConstants.getCLIENTID();
                    }
//                    if (!TextUtils.isEmpty(clientId)) {


                    if ((!TextUtils.isEmpty(AppConstants.getPlay_store_validate())) && AppConstants.getPlay_store_validate().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        method.activityOpenAnimation();
                    } else if (AppConstants.getUSERMOBILENUMBER().equalsIgnoreCase(AppConstants.getPlayStoreDemoUserMobile()) &&
                            clientId.equalsIgnoreCase(AppConstants.getPlayStoreDemoPasswordClientid())) {
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        method.activityOpenAnimation();
                    } else {

                        new AuthenticateUserAsyncTask(LockActivity.this, AppConstants.getUSERMOBILENUMBER(), codeString, clientId).execute();
                    }

                  /*  } else {
                        TrustMethods.showSnackBarMessage("Client Id not found, please provide customer id.", coordinatorLayout);
                    }*/

                } else {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                }
            } else {
                TrustMethods.displaySimErrorDialog(this);
            }
        } else if (codeString.length() > MAX_LENGHT) {
            //reset the input code
            codeString = "";
            getStringCode(button.getId());
        }
        setDotImagesState();
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        findViewById(R.id.dot_layout).startAnimation(shake);
        clearAllMpinField();
    }

    private void getStringCode(int buttonId) {
        switch (buttonId) {
            case R.id.btn0:
                codeString += "0";
                break;
            case R.id.btn1:
                codeString += "1";
                break;
            case R.id.btn2:
                codeString += "2";
                break;
            case R.id.btn3:
                codeString += "3";
                break;
            case R.id.btn4:
                codeString += "4";
                break;
            case R.id.btn5:
                codeString += "5";
                break;
            case R.id.btn6:
                codeString += "6";
                break;
            case R.id.btn7:
                codeString += "7";
                break;
            case R.id.btn8:
                codeString += "8";
                break;
            case R.id.btn9:
                codeString += "9";
                break;
            default:
                break;
        }
    }

    private void setDotImagesState() {
        char[] charArray = codeString.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            String char2 = String.valueOf(charArray[i]);
            TrustMethods.LogMessage("char1", String.valueOf(charArray[i]));
            dots.get(i).setText(char2);
        }
        if (codeString.length() < 4) {
            for (int j = codeString.length(); j < 4; j++) {
                dots.get(j).setText("");
            }
        }
    }

    public void clearAllMpinField() {
        for (int j = 0; j < dots.size(); j++) {
            dots.get(j).setText("");
        }
    }

    private String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scanfingure:
                Intent btn_scan = new Intent(LockActivity.this, FingurePrintActivity.class);
                startActivity(btn_scan);
                method.activityOpenAnimation();

            case R.id.btn_space:
                loginCheck();
                break;

            case R.id.txtForgotPinId:
                Intent pinActivationIntent = new Intent(LockActivity.this, PinActivation.class);
                pinActivationIntent.putExtra("operationType", "forgotMpin");
                startActivity(pinActivationIntent);
                method.activityOpenAnimation();
                break;

            case R.id.txtShowHideId:
                if (txtShowHide.getText().toString().equalsIgnoreCase("Show")) {
                    txtShowHide.setText("Hide");
                    txtShowHide.setCompoundDrawablesWithIntrinsicBounds(R.drawable.eye_off, 0, 0, 0);
                    mPin1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    mPin2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    mPin3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    mPin4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    mPin1.setTextSize(25);
                    mPin2.setTextSize(25);
                    mPin3.setTextSize(25);
                    mPin4.setTextSize(25);
                } else {
                    txtShowHide.setText("SHOW");
                    txtShowHide.setCompoundDrawablesWithIntrinsicBounds(R.drawable.eye, 0, 0, 0);
                    mPin1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPin2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPin3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPin4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPin1.setTextSize(35);
                    mPin2.setTextSize(35);
                    mPin3.setTextSize(35);
                    mPin4.setTextSize(35);
                }
                break;

            case R.id.imgEditClient:

                if (clientIdList != null && clientIdList.size() > 0) {
                    if (!autoCompleteTextViewClient_Id.getText().toString().equals(""))
                        mAdapter.getFilter().filter(null);
                    autoCompleteTextViewClient_Id.showDropDown();
                }

                break;

            default:
                break;
        }
    }

    private void loginCheck() {
        try {
            TrustMethods.hideSoftKeyboard(LockActivity.this);
            if (codeString.length() == MAX_LENGHT) {
                if (!TextUtils.isEmpty(codeString)) {
                    if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
                        if (NetworkUtil.getConnectivityStatus(LockActivity.this)) {
                            String clientId = "";
                            if (etClient.getVisibility() == View.VISIBLE) {

                                clientId = etClient.getText().toString().trim();

                            } else if (autoCompleteTextViewClient_Id.getVisibility() == View.VISIBLE) {
                                clientId = autoCompleteTextViewClient_Id.getText().toString().trim();
                            } else {
                                clientId = AppConstants.getCLIENTID();
                            }
//                            if (!TextUtils.isEmpty(clientId)) {


                            if ((!TextUtils.isEmpty(AppConstants.getPlay_store_validate())) && AppConstants.getPlay_store_validate().equalsIgnoreCase("1")) {
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                method.activityOpenAnimation();
                            } else if (AppConstants.getUSERMOBILENUMBER().equalsIgnoreCase(AppConstants.getPlayStoreDemoUserMobile()) &&
                                    clientId.equalsIgnoreCase(AppConstants.getPlayStoreDemoPasswordClientid())) {
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                method.activityOpenAnimation();
                            } else {
                                new AuthenticateUserAsyncTask(LockActivity.this, AppConstants.getUSERMOBILENUMBER(), codeString, clientId).execute();
                            }
                            /*} else {
                                TrustMethods.showSnackBarMessage("Client Id not found, " +
                                        "please provide customer id.", coordinatorLayout);
                            }*/
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                        }
                    } else {
                        TrustMethods.displaySimErrorDialog(this);
                    }
                } else {
                    TrustMethods.showSnackBarMessage("Enter Valid 4 digit MPIN", coordinatorLayout);
                }
            } else {
                TrustMethods.showSnackBarMessage("Enter Valid 4 digit MPIN", coordinatorLayout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDialogOk(int resultCode) {
        switch (resultCode) {
            case 0:
                Intent intent = new Intent(LockActivity.this, VerifyMobileNumber.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case 1:
                finish();
                break;
            default:
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String mMobileNo;
        String mMpin, mClientId;
        String result;

        public AuthenticateUserAsyncTask(Context ctx, String mobileNo, String mpin, String mClientId) {
            this.ctx = ctx;
            this.mMobileNo = mobileNo;
            this.mMpin = mpin;
            this.mClientId = mClientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LockActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.AuthenticateUserUrl();
                String jsonString = "{\"mobile_number\":\"" + mMobileNo + "\", \"mpin\":\"" + mMpin + "\", \"custid\":\"" + mClientId + "\"}";
                TrustMethods.LogMessage("", "json string for change pass : " + jsonString);

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithoutHeader(url, jsonString);
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
                    JSONObject dataObject = jsonResponse.getJSONObject("response");
                    response = dataObject.has("auth_token") ? dataObject.getString("auth_token") : "NA";
                    AppConstants.setAuth_token(response);
                    sharePreferenceUtils.putString(AppConstants.AUTH_TOKEN, response);
                } else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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

                if (!this.error.equals("")) {
                    TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    shakeAnimation();
                } else {
                    if (response != null) {
                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(LockActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(LockActivity.this)) {
                                new GetMenuListAsyncTask(LockActivity.this, mClientId).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(LockActivity.this);
                            //   }
                        }
                    } else {
                        TrustMethods.showSnackBarMessage("Wrong Pass code", coordinatorLayout);

                        //vibrate the dots layout
                        shakeAnimation();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetMenuListAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String result, mClientId;
        String actionName = "GET_MENUS";

        public GetMenuListAsyncTask(Context ctx, String mClientId) {
            this.ctx = ctx;
            this.mClientId = mClientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LockActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetMenuListUrl();
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
                    JSONObject dataObject = jsonResponse.getJSONObject("response");
                    if (dataObject.has("error")) {
                        error = dataObject.getString("error");
                        return error;
                    }

                    JSONObject rowJsonObject = dataObject.getJSONObject("row");
                    if (rowJsonObject.has("error")) {
                        error = rowJsonObject.getString("error");
                        return error;
                    }


                    AppConstants.setMnu_accounts(rowJsonObject.has("mnu_accounts") ? rowJsonObject.getString("mnu_accounts") : "0");
                    AppConstants.setMnu_account_overview(rowJsonObject.has("mnu_account_overview") ? rowJsonObject.getString("mnu_account_overview") : "0");
                    AppConstants.setMnu_account_details(rowJsonObject.has("mnu_account_details") ? rowJsonObject.getString("mnu_account_details") : "0");
                    AppConstants.setMnu_statement(rowJsonObject.has("mnu_statement") ? rowJsonObject.getString("mnu_statement") : "0");
                    AppConstants.setMnu_borrow(rowJsonObject.has("mnu_borrow") ? rowJsonObject.getString("mnu_borrow") : "0");
                    AppConstants.setMnu_loan_catalogue(rowJsonObject.has("mnu_loan_catalogue") ? rowJsonObject.getString("mnu_loan_catalogue") : "0");
                    AppConstants.setMnu_open_loan_account(rowJsonObject.has("mnu_open_loan_account") ? rowJsonObject.getString("mnu_open_loan_account") : "1");//todo in db
                    AppConstants.setMnu_loan_eligibility(rowJsonObject.has("mnu_loan_eligibility") ? rowJsonObject.getString("mnu_loan_eligibility") : "0");
                    AppConstants.setMnu_loan_simulator(rowJsonObject.has("mnu_loan_simulator") ? rowJsonObject.getString("mnu_loan_simulator") : "0");
                    AppConstants.setMnu_save(rowJsonObject.has("mnu_save") ? rowJsonObject.getString("mnu_save") : "0");
                    AppConstants.setMnu_savings_account(rowJsonObject.has("mnu_savings_account") ? rowJsonObject.getString("mnu_savings_account") : "0");
                    AppConstants.setMnu_open_saving_account(rowJsonObject.has("mnu_open_saving_account") ? rowJsonObject.getString("mnu_open_saving_account") : "0");
                    AppConstants.setMnu_cheque_book_request(rowJsonObject.has("mnu_cheque_book_request") ? rowJsonObject.getString("mnu_cheque_book_request") : "0");
                    AppConstants.setMnu_stop_cheque(rowJsonObject.has("mnu_stop_cheque") ? rowJsonObject.getString("mnu_stop_cheque") : "0");
                    AppConstants.setMnu_cheque_status(rowJsonObject.has("mnu_cheque_status") ? rowJsonObject.getString("mnu_cheque_status") : "0");
                    AppConstants.setMnu_debit_card(rowJsonObject.has("mnu_save_debit_card") ? rowJsonObject.getString("mnu_save_debit_card") : "0");
                    AppConstants.setMnu_investment(rowJsonObject.has("mnu_investment") ? rowJsonObject.getString("mnu_investment") : "0");
                    AppConstants.setMnu_investment_account(rowJsonObject.has("mnu_investment_account") ? rowJsonObject.getString("mnu_investment_account") : "0");
                    AppConstants.setMnu_open_investment_account(rowJsonObject.has("mnu_open_investment_account") ? rowJsonObject.getString("mnu_open_investment_account") : "0");
                    AppConstants.setMnu_investment_certificate(rowJsonObject.has("mnu_investment_certificate") ? rowJsonObject.getString("mnu_investment_certificate") : "0");
                    AppConstants.setMnu_close_investment_account(rowJsonObject.has("mnu_close_investment_account") ? rowJsonObject.getString("mnu_close_investment_account") : "0");
                    AppConstants.setMnu_investment_simulator(rowJsonObject.has("mnu_investment_simulator") ? rowJsonObject.getString("mnu_investment_simulator") : "0");
                    AppConstants.setMnu_saving_catalogue(rowJsonObject.has("mnu_saving_catalogue") ? rowJsonObject.getString("mnu_saving_catalogue") : "0");
                    AppConstants.setMnu_investment_catalogue(rowJsonObject.has("mnu_investment_catalogue") ? rowJsonObject.getString("mnu_investment_catalogue") : "0");
                    AppConstants.setMnu_transactions(rowJsonObject.has("mnu_transactions") ? rowJsonObject.getString("mnu_transactions") : "0");
                    AppConstants.setMnu_loan_repayment(rowJsonObject.has("mnu_loan_repayment") ? rowJsonObject.getString("mnu_loan_repayment") : "0");
                    AppConstants.setMnu_intra_bank_transfer(rowJsonObject.has("mnu_intra_bank_transfer") ? rowJsonObject.getString("mnu_intra_bank_transfer") : "0");
                    AppConstants.setMnu_self_account_transfer(rowJsonObject.has("mnu_self_account_transfer") ? rowJsonObject.getString("mnu_self_account_transfer") : "0");
                    AppConstants.setMnu_interbank_transfer(rowJsonObject.has("mnu_interbank_transfer") ? rowJsonObject.getString("mnu_interbank_transfer") : "0");
                    AppConstants.setMnu_manage_beneficiaries(rowJsonObject.has("mnu_manage_beneficiaries") ? rowJsonObject.getString("mnu_manage_beneficiaries") : "0");
                    AppConstants.setMnu_cheque(rowJsonObject.has("mnu_cheque") ? rowJsonObject.getString("mnu_cheque") : "0");
                    AppConstants.setMnu_debit_card1(rowJsonObject.has("mnu_trans_debit_card") ? rowJsonObject.getString("mnu_trans_debit_card") : "0");
                    AppConstants.setMnu_bills_payment(rowJsonObject.has("mnu_bills_payment") ? rowJsonObject.getString("mnu_bills_payment") : "0");
                    AppConstants.setMnu_visit_us(rowJsonObject.has("mnu_visit_us") ? rowJsonObject.getString("mnu_visit_us") : "0");
                    AppConstants.setMnu_locate_agencies(rowJsonObject.has("mnu_locate_agencies") ? rowJsonObject.getString("mnu_locate_agencies") : "0");
                    AppConstants.setMnu_locate_atm(rowJsonObject.has("mnu_locate_atm") ? rowJsonObject.getString("mnu_locate_atm") : "0");
                    AppConstants.setMnu_contact(rowJsonObject.has("mnu_contact") ? rowJsonObject.getString("mnu_contact") : "0");
                    AppConstants.setMnu_schedule_visit(rowJsonObject.has("mnu_schedule_visit") ? rowJsonObject.getString("mnu_schedule_visit") : "0");
                    AppConstants.setMnu_profile_settings(rowJsonObject.has("mnu_profile_settings") ? rowJsonObject.getString("mnu_profile_settings") : "0");
                    AppConstants.setMnu_personal_profile(rowJsonObject.has("mnu_personal_profile") ? rowJsonObject.getString("mnu_personal_profile") : "0");
                    AppConstants.setMnu_security_center(rowJsonObject.has("mnu_security_center") ? rowJsonObject.getString("mnu_security_center") : "0");
                    AppConstants.setMnu_transaction_limit(rowJsonObject.has("mnu_transaction_limit") ? rowJsonObject.getString("mnu_transaction_limit") : "0");
                    AppConstants.setMnu_feedback(rowJsonObject.has("mnu_feedback") ? rowJsonObject.getString("mnu_feedback") : "0");
                    AppConstants.setMnu_fingure_print(rowJsonObject.has("mnu_setting_fingure_print") ? rowJsonObject.getString("mnu_setting_fingure_print") : "0");

                    JSONObject mainscreenmenus=rowJsonObject.getJSONObject("main_screen_menus");
                    JSONArray menus=mainscreenmenus.getJSONArray("menu");
                    if(menus != null) {
                        DynamicMenuModels=new ArrayList<>();
                        SubMenuMap=new HashMap();
                        for (int i = 0; i < menus.length(); i++) {
                            JSONObject menuarry = menus.getJSONObject(i);
                            String menuCode = menuarry.getString("menu_code");
                            String caption = menuarry.getString("menu_caption");

                            DynamicMenuModel dynamicMenuModel=new DynamicMenuModel();
                            dynamicMenuModel.setMenucode(menuCode);
                            dynamicMenuModel.setCaption(caption);
                            DynamicMenuModels.add(dynamicMenuModel);

                            if (menuarry.has("sub_menus")) {
                                JSONObject mainscreensubmenus = menuarry.getJSONObject("sub_menus");
                                JSONArray submenus=mainscreensubmenus.getJSONArray("menu");
                                if(submenus != null){
                                    DynamicSubMenuModels=new ArrayList<>();
                                    for(int j=0;j<submenus.length();j++){
                                        JSONObject submenuarry = submenus.getJSONObject(j);
                                        String submenuCode = submenuarry.getString("menu_code");
                                        String subcaption = submenuarry.getString("menu_caption");

                                        DynamicMenuModel dynamicsubMenuModel=new DynamicMenuModel();
                                        dynamicsubMenuModel.setSubmenucode(submenuCode);
                                        dynamicsubMenuModel.setCaption(subcaption);
                                        DynamicSubMenuModels.add(dynamicsubMenuModel);
                                    }
                                    if(DynamicMenuModels.size()>0){
                                        SubMenuMap.put(menuCode,DynamicSubMenuModels);
                                        AppConstants.setSubmenu(SubMenuMap);
                                    }
                                }
                            }
                        }
                        AppConstants.setParentlist(DynamicMenuModels);
                    }

                } else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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

                if (!this.error.equals("")) {
                    TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    if (error.equalsIgnoreCase("auth token expired.")) {
                        AlertDialogMethod.alertDialogOk(LockActivity.this,
                                getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(LockActivity.this, this.error, "", getResources().getString(R.string.btn_ok),
                                1, false, alertDialogOkListener);
                    }
                } else {
                    //.......................TODO..................... 13-12-2018
                    //sagarM
                    new GetWelcomeMessageAsyncTask(LockActivity.this, mClientId).execute();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //sagar
    @SuppressLint("StaticFieldLeak")
    private class GetWelcomeMessageAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        JSONArray welcome_res;
        ProgressDialog pDialog;
        String result, mClientId;
        String actionName = "GET_WELCOME_MESSAGE";

        public GetWelcomeMessageAsyncTask(Context ctx, String mClientId) {
            this.ctx = ctx;
            this.mClientId = mClientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LockActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetMenuListUrl();
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
                    welcome_res = jsonResponse.getJSONObject("response").getJSONArray("welcome");
                    Log.e("welcome_res", String.valueOf(welcome_res));

                }else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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

                if (!this.error.equals("")) {
                    TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    if (error.equalsIgnoreCase("auth token expired.")) {
                        AlertDialogMethod.alertDialogOk(LockActivity.this,
                                getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(LockActivity.this, this.error, "", getResources().getString(R.string.btn_ok),
                                1, false, alertDialogOkListener);
                    }
                } else {
                    //.......................TODO..................... 13-12-2018
                    sessionManager.createLoginSession(AppConstants.getUSERMOBILENUMBER(), mClientId);
                    AppConstants.setCLIENTID(sessionManager.getClientId());
                    String welcomemessage="";
                    String welcomemessageexitflag="";

                    if(welcome_res != null && welcome_res.length() > 0) {
                        for(int i=0;i<welcome_res.length();i++)
                        {
                            JSONObject data = welcome_res.getJSONObject(i);
                            welcomemessage= data.getString("welcome_message");
                            welcomemessageexitflag= data.getString("welcome_message_exit_flag");
                            DialogFragment dialogFragment=new WelcomeMessageDiagligFragment(welcomemessage,welcomemessageexitflag);
                            dialogFragment.show(getSupportFragmentManager(),"My  Fragment");
                        }
                    }else {
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        method.activityOpenAnimation();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        clearAllMpinField();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        method.activityCloseAnimation();
    }


}