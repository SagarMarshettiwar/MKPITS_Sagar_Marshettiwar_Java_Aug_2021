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
import android.view.WindowManager;
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

import com.google.gson.JsonObject;
import com.trustbank.BuildConfig;
import com.trustbank.Model.BottomDynamicMenuModel;
import com.trustbank.Model.CheckSimInfoModel;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.fragment.WelcomeMessageDiagligFragment;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.trustbank.util.AppConstants.isAutoReadOTPEnabled;
import static com.trustbank.util.MBank.loadAppLogo;

public class LockActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogOkListener {
    @BindViews({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_clear})
    List<View> btnNumPads;

    @BindViews({R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4,R.id.dot_5,R.id.dot_6})
    List<EditText> dots;

    private ImageView ivAppLogo, imgClient;
    private static int MAX_LENGHT = 0;
    private String codeString = "";
    private CoordinatorLayout coordinatorLayout;
    private TrustMethods method;
    private SessionManager sessionManager;
    private TextView txtShowHide, textClient, txtVersionNameId, bank_address, bank_name;
    private EditText mPin1;
    private EditText mPin2;
    private ImageView btnClientIdOk;
    private EditText mPin3,mPin5,mPin6;
    private EditText mPin4, etClient;
    private String versionName;
    private String simErrorMsg;
    private TrustMethods trustMethods;
    private LinearLayout linearClient;
    private RelativeLayout linearParentClientId;
    AlertDialogOkListener alertDialogOkListener = this;
    private SharePreferenceUtils sharePreferenceUtils;
    private AutoCompleteTextView autoCompleteTextViewClient_Id;
    private LinearLayout linearAutoComplete,ll_fp;
    private ArrayAdapter<String> mAdapter = null;
    private  List<String> clientIdList = null;
    private ImageView btnEditClientID;
    private ImageView btn_scanfingure;
    ArrayList<DynamicMenuModel> DynamicMenuModels;
    ArrayList<DynamicMenuModel> DynamicSubMenuModels;
    HashMap<String,List<DynamicMenuModel>>SubMenuMap;
    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    ArrayList<BottomDynamicMenuModel> bottomDynamicMenuModels;
    ArrayList<BottomDynamicMenuModel> BottomDynamicSubMenuModel;
    HashMap<String,List<BottomDynamicMenuModel>>BottomSubMenuMap;

    //private boolean notify=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        if(TrustMethods.isEmulator())
        {
            TrustMethods.message(this, this.getResources().getString(R.string.device_emulator_message));
        }
        SetTheme.changeToTheme(LockActivity.this, true);
        setContentView(R.layout.activity_lock_new);
        ButterKnife.bind(this);
        trustMethods = new TrustMethods(LockActivity.this);
        bank_name=findViewById(R.id.bank_name);
        bank_address=findViewById(R.id.bank_address);
        bank_name.setText(AppConstants.getBank_name());
        bank_address.setText(AppConstants.getBank_address());

        if (trustMethods.packageoneIdentify(LockActivity.this)) {
            AlertDialogMethod.alertDialogOk(LockActivity.this, "Security Alert", "Security Alert Following installed apps can be used by " +
                            "fraudsters to steal your money Please uninstall Remote Access Apps to continue using Mbank",
                    getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
        }else{
            inIt();
            loadKeyboard();
        }
    }
    private void loadKeyboard()
    {
        if(AppConstants.Dynamic_keybord_enable) {
            Random random = new Random();
            String str = "";
            int i = 0;
            while (i < 10)//4
            {
                int nextInt = random.nextInt(10);
                if (!str.contains(nextInt + "")) {
                    str = str + nextInt;
                    if (i == 0)
                        btn0.setText(String.valueOf(nextInt));
                    else if (i == 1)
                        btn1.setText(String.valueOf(nextInt));
                    else if (i == 2)
                        btn2.setText(String.valueOf(nextInt));
                    else if (i == 3)
                        btn3.setText(String.valueOf(nextInt));
                    else if (i == 4)
                        btn4.setText(String.valueOf(nextInt));
                    else if (i == 5)
                        btn5.setText(String.valueOf(nextInt));
                    else if (i == 6)
                        btn6.setText(String.valueOf(nextInt));
                    else if (i == 7)
                        btn7.setText(String.valueOf(nextInt));
                    else if (i == 8)
                        btn8.setText(String.valueOf(nextInt));
                    else if (i == 9)
                        btn9.setText(String.valueOf(nextInt));
                    System.out.println(nextInt);
                } else {
                    i--;
                }
                i++;

            }
        }else{
            btn0.setText(String.valueOf(0));
            btn1.setText(String.valueOf(1));
            btn2.setText(String.valueOf(2));
            btn3.setText(String.valueOf(3));
            btn4.setText(String.valueOf(4));
            btn5.setText(String.valueOf(5));
            btn6.setText(String.valueOf(6));
            btn7.setText(String.valueOf(7));
            btn8.setText(String.valueOf(8));
            btn9.setText(String.valueOf(9));
        }
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
            ll_fp=findViewById(R.id.ll_fp);
            btn_scanfingure=findViewById(R.id.btn_scanfingure);
            btn_scanfingure.setOnClickListener(this);

            ImageView btnLogin = findViewById(R.id.btn_space);
            mPin1 = findViewById(R.id.dot_1);
            mPin2 = findViewById(R.id.dot_2);
            mPin3 = findViewById(R.id.dot_3);
            mPin4 = findViewById(R.id.dot_4);
            mPin5 = findViewById(R.id.dot_5);
            mPin6 = findViewById(R.id.dot_6);
            //todo

            btn0 = findViewById(R.id.btn0);
            btn1 = findViewById(R.id.btn1);
            btn2 = findViewById(R.id.btn2);
            btn3 = findViewById(R.id.btn3);
            btn4 = findViewById(R.id.btn4);
            btn5 = findViewById(R.id.btn5);
            btn6 = findViewById(R.id.btn6);
            btn7 = findViewById(R.id.btn7);
            btn8 = findViewById(R.id.btn8);
            btn9 = findViewById(R.id.btn9);

            if(AppConstants.four_digit_pin_enable){
                MAX_LENGHT=4;
                mPin5.setVisibility(View.GONE);
                mPin6.setVisibility(View.GONE);
            }else{
                MAX_LENGHT=6;
                mPin5.setVisibility(View.VISIBLE);
                mPin6.setVisibility(View.VISIBLE);
            }
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

            btnEditClientID=findViewById(R.id.imgEditClient);
            btnEditClientID.setOnClickListener(this);


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
                        textClient.setText("Customer ID : XXXX" + customerId);
                    } else {
                        String clientid = customerId.substring(customerId.length() - 2);
                        textClient.setText("Customer ID : XXXX" + clientid);
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
                    if (getPackageName().equals("com.trustbank.sadhnambank")) {
                        linearAutoComplete.setVisibility(View.VISIBLE);
                        autoCompleteTextViewClient_Id.setVisibility(View.VISIBLE);
                        etClient.setVisibility(View.GONE);
                        Set<String> mySetClientList = sessionManager.getClientListIds();
                        clientIdList = new ArrayList<>();
                        clientIdList.addAll(mySetClientList);
                        autoCompleteTextViewClient_Id.setText(AppConstants.getCLIENTID());
                        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, clientIdList);
                        autoCompleteTextViewClient_Id.setThreshold(1);
                        autoCompleteTextViewClient_Id.setAdapter(mAdapter);

                    } else {
                        etClient.setVisibility(View.VISIBLE);
                        etClient.setText(AppConstants.getCLIENTID());
                        linearAutoComplete.setVisibility(View.GONE);
                        autoCompleteTextViewClient_Id.setVisibility(View.GONE);
                    }


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
                            textClient.setText("Customer ID : XXXX" + customerId);
                        } else {
                            String clientid = customerId.substring(customerId.length() - 2);
                            textClient.setText("Customer ID : XXXX" + clientid);
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
                       /* Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        method.activityOpenAnimation();*/
                        AppConstants.IP=BuildConfig.IP;
                        new AuthenticateUserAsyncTask(LockActivity.this, AppConstants.getUSERMOBILENUMBER(), codeString, clientId).execute();

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
                codeString += btn0.getText();// "0";
                break;
            case R.id.btn1:
                codeString += btn1.getText();// "1";
                break;
            case R.id.btn2:
                codeString += btn2.getText();// "2";
                break;
            case R.id.btn3:
                codeString += btn3.getText();// "3";
                break;
            case R.id.btn4:
                codeString += btn4.getText();// "4";
                break;
            case R.id.btn5:
                codeString += btn5.getText();// "5";
                break;
            case R.id.btn6:
                codeString += btn6.getText();// "6";
                break;
            case R.id.btn7:
                codeString += btn7.getText();// "7";
                break;
            case R.id.btn8:
                codeString += btn8.getText();// "8";
                break;
            case R.id.btn9:
                codeString += btn9.getText();// "9";
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
        if(AppConstants.four_digit_pin_enable) {
            if (codeString.length() < 4) {
                for (int j = codeString.length(); j < 4; j++) {
                    dots.get(j).setText("");
                }
            }
        }else{
            if (codeString.length() < 6) {
                for (int j = codeString.length(); j < 6; j++) {
                    dots.get(j).setText("");
                }
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
                    mPin5.setTextSize(25);
                    mPin6.setTextSize(25);
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
                    mPin5.setTextSize(35);
                    mPin6.setTextSize(35);
                }
                break;

            case R.id.imgEditClient:

                if (clientIdList != null && clientIdList.size() > 0) {
                    // show all suggestions
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
                                    AppConstants.IP= BuildConfig.IP;
                                new AuthenticateUserAsyncTask(LockActivity.this, AppConstants.getUSERMOBILENUMBER(), codeString, clientId).execute();

                            } else {

                                new AuthenticateUserAsyncTask(LockActivity.this, AppConstants.getUSERMOBILENUMBER(), codeString, clientId).execute();
                            }
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
                String jsonString = "{\"mobile_number\":\"" + mMobileNo + "\",\"login_type\":\""+0+"\",\"mpin\":\"" + mMpin + "\", \"custid\":\"" + mClientId + "\"}";
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

                        if (getPackageName().equals("com.trustbank.sadhnambank")) {
                            Set<String> mySetClientList = sessionManager.getClientListIds();
                            if (mySetClientList != null){
                                mySetClientList.add(mClientId.trim());
                            }else {
                                mySetClientList = new HashSet<>();
                                mySetClientList.add(mClientId.trim());
                            }
                            sessionManager.storeclientList(mySetClientList);
                        }
                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(LockActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(LockActivity.this)) {
                                new GetMenuListAsyncTask(LockActivity.this, mClientId).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(LockActivity.this);
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
    private class GetGLCodeAsync extends AsyncTask<Void, Void, String> {
        private String error = "";
        private Context ctx;
        List<String> neftlist,within_dr,within_cr,impslist,upilist,self_dr,self_cr,card_list,balance_list,ministatement_list,mpassbook_list;
        private ProgressDialog pDialog;
        private String result;
        private String mClientId;
        private String neft_tran,within_dr_tran,self_dr_tran,self_cr_tran,upi_tran,within_cr_tran,imps_tran,card_tran,ministatement_tran,mpassbook_tran,balance_tran;
        private String actionName = "GET_GLCODE";

        public GetGLCodeAsync(Context ctx, String mClientId) {
            this.ctx = ctx;
            this.mClientId = mClientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(ctx.getResources().getString(R.string.loading_wait));
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
                JSONObject jsonResponse = new JSONObject(result);

                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }

                neftlist = new ArrayList<>();
                within_dr = new ArrayList<>();
                impslist  = new ArrayList<>();
                upilist   = new ArrayList<>();
                self_dr   = new ArrayList<>();
                self_cr   = new ArrayList<>();
                within_cr = new ArrayList<>();
                card_list = new ArrayList<>();
                balance_list= new ArrayList<>();
                ministatement_list= new ArrayList<>();
                mpassbook_list= new ArrayList<>();
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {
                    JSONObject dataObject = jsonResponse.getJSONObject("response");

                    if (dataObject.has("error")) {
                        error = dataObject.getString("error");
                        return error;
                    }

                    JSONObject rowJsonObject = dataObject.getJSONObject("data");

                    if (rowJsonObject.has("error")) {
                        error = rowJsonObject.getString("error");
                        return error;
                    }

                    if (rowJsonObject.has("gl_allowed")) {
                        JSONObject glTypeData = rowJsonObject.getJSONObject("gl_allowed");
                        neft_tran = glTypeData.optString("neft_tran", "");

                        imps_tran = glTypeData.optString("imps_tran", "");

                        within_dr_tran = glTypeData.optString("within_dr_tran", "");

                        self_dr_tran = glTypeData.optString("self_dr_tran", "");

                        self_cr_tran = glTypeData.optString("self_cr_tran", "");

                        upi_tran = glTypeData.optString("upi_tran", "");

                        within_cr_tran = glTypeData.optString("within_cr_tran", "");

                        card_tran = glTypeData.optString("card_tran", "");
                        ministatement_tran = glTypeData.optString("mini_tran", "");
                        mpassbook_tran = glTypeData.optString("mpassbook_tran", "");
                        balance_tran = glTypeData.optString("bal_tran", "");

                        String[] res = neft_tran.split(",", 0);
                        String[] res1 = within_dr_tran.split(",", 0);
                        String[] res2 = self_dr_tran.split(",", 0);
                        String[] res3 = self_cr_tran.split(",", 0);
                        String[] res4 = upi_tran.split(",", 0);
                        String[] res5 = within_cr_tran.split(",", 0);
                        String[] res6 = imps_tran.split(",", 0);
                        String[] res7 = card_tran.split(",", 0);
                        String[] res8 = ministatement_tran.split(",", 0);
                        String[] res9 = mpassbook_tran.split(",", 0);
                        String[] res10 = balance_tran.split(",", 0);

                        neftlist.addAll(Arrays.asList(res));
                        within_dr.addAll(Arrays.asList(res1));
                        impslist.addAll(Arrays.asList(res6));
                        upilist.addAll(Arrays.asList(res4));
                        self_dr.addAll(Arrays.asList(res2));
                        self_cr.addAll(Arrays.asList(res3));
                        within_cr.addAll(Arrays.asList(res5));
                        card_list.addAll(Arrays.asList(res7));
                        mpassbook_list.addAll(Arrays.asList(res9));
                        ministatement_list.addAll(Arrays.asList(res8));
                        balance_list.addAll(Arrays.asList(res10));

                        Log.e("mpassbook",mpassbook_tran);
                        Log.e("bal",balance_tran);
                        Log.e("mini",ministatement_tran);

                    }
                    AppConstants.setNeft_list(neftlist);
                    AppConstants.setWithin_list(within_dr);
                    AppConstants.setImpslist(impslist);
                    AppConstants.setSelf_dr(self_dr);
                    AppConstants.setUpilist(upilist);
                    AppConstants.setSelf_cr(self_cr);
                    AppConstants.setWithin_cr(within_cr);
                    AppConstants.setCard_list(card_list);
                    AppConstants.setMpassbook_list(mpassbook_list);
                    AppConstants.setMiniststement_list(ministatement_list);
                    AppConstants.setBalance_list(balance_list);

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
            return error;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!error.equals("")) {
                    TrustMethods.showSnackBarMessage(error, coordinatorLayout);

                    if (error.equalsIgnoreCase("auth token expired.")) {
                        AlertDialogMethod.alertDialogOk(ctx, ctx.getResources().getString(R.string.error_session_expire),
                                "", ctx.getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(ctx, error, "", ctx.getResources().getString(R.string.btn_ok),
                                1, false, alertDialogOkListener);
                    }
                }else{
                    new GetWelcomeMessageAsyncTask(LockActivity.this, mClientId).execute();
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
//                String url = TrustURL.GetMenusUrl(AppConstants.getCLIENTID());
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
                    AppConstants.setMnu_fundtransfer(rowJsonObject.has("mnu_fundtransfer") ? rowJsonObject.getString("mnu_fundtransfer") : "0");
                    AppConstants.setMnu_locate_atms(rowJsonObject.has("mnu_locate_atms") ? rowJsonObject.getString("mnu_locate_atms") : "0");
                    AppConstants.setMnu_locate_branch(rowJsonObject.has("mnu_locate_branch") ? rowJsonObject.getString("mnu_locate_branch") : "0");
                    AppConstants.setMnu_contact_us(rowJsonObject.has("mnu_contact_us") ? rowJsonObject.getString("mnu_contact_us") : "0");
                    AppConstants.setMnu_about_us(rowJsonObject.has("mnu_about_us") ? rowJsonObject.getString("mnu_about_us") : "0");
                    AppConstants.setMnu_accounts_menu_accountdetails(rowJsonObject.has("mnu_accounts_menu_accountdetails") ? rowJsonObject.getString("mnu_accounts_menu_accountdetails") : "0");
                    AppConstants.setMnu_accounts_menu_balenquiry(rowJsonObject.has("mnu_accounts_menu_balenquiry") ? rowJsonObject.getString("mnu_accounts_menu_balenquiry") : "0");
                    AppConstants.setMnu_accounts_menu_ministatemnt(rowJsonObject.has("mnu_accounts_menu_ministatemnt") ? rowJsonObject.getString("mnu_accounts_menu_ministatemnt") : "0");
                    AppConstants.setMnu_accounts_menu_showmmid(rowJsonObject.has("mnu_accounts_menu_showmmid") ? rowJsonObject.getString("mnu_accounts_menu_showmmid") : "0");
                    AppConstants.setMnu_accounts_menu_last5imps(rowJsonObject.has("mnu_accounts_menu_last5imps") ? rowJsonObject.getString("mnu_accounts_menu_last5imps") : "0");
                    AppConstants.setMnu_accounts_menu_neftenquiry(rowJsonObject.has("mnu_accounts_menu_neftenquiry") ? rowJsonObject.getString("mnu_accounts_menu_neftenquiry") : "0");
                    AppConstants.setMnu_fundtransfer_ownbank(rowJsonObject.has("mnu_fundtransfer_ownbank") ? rowJsonObject.getString("mnu_fundtransfer_ownbank") : "0");
                    AppConstants.setMnu_fundtransfer_impstoaccount(rowJsonObject.has("mnu_fundtransfer_impstoaccount") ? rowJsonObject.getString("mnu_fundtransfer_impstoaccount") : "0");
                    AppConstants.setMnu_fundtransfer_nefttoaccount(rowJsonObject.has("mnu_fundtransfer_nefttoaccount") ? rowJsonObject.getString("mnu_fundtransfer_nefttoaccount") : "0");
                    AppConstants.setMnu_fundtransfer_impstomobile(rowJsonObject.has("mnu_fundtransfer_impstomobile") ? rowJsonObject.getString("mnu_fundtransfer_impstomobile") : "0");
                    AppConstants.setMnu_fundtransfer_upi_collect_money(rowJsonObject.has("mnu_fundtransfer_upi_collect_money") ? rowJsonObject.getString("mnu_fundtransfer_upi_collect_money") : "0");
                    AppConstants.setMnu_fundtransfer_mngbenefeciaries(rowJsonObject.has("mnu_fundtransfer_mngbenefeciaries") ? rowJsonObject.getString("mnu_fundtransfer_mngbenefeciaries") : "0");
                    AppConstants.setMnu_faq(rowJsonObject.has("mnu_FAQ") ? rowJsonObject.getString("mnu_FAQ") : "0");
                    AppConstants.setMnu_checkbook_request(rowJsonObject.has("mnu_accounts_menu_cheqbkreq") ? rowJsonObject.getString("mnu_accounts_menu_cheqbkreq") : "0");
                    AppConstants.setMnu_account_statement(rowJsonObject.has("mnu_accounts_menu_acc_stmnt") ? rowJsonObject.getString("mnu_accounts_menu_acc_stmnt") : "0");
                    AppConstants.setMnu_self_transfer_to_account(rowJsonObject.has("mnu_fundtransfer_menu_selftrf") ? rowJsonObject.getString("mnu_fundtransfer_menu_selftrf") : "0");
                    AppConstants.setMnu_Change_MPin(rowJsonObject.has("mnu_settings_menu_change_mpin") ? rowJsonObject.getString("mnu_settings_menu_change_mpin") : "0");
                    AppConstants.setMnu_Change_TPin(rowJsonObject.has("mnu_settings_menu_change_tpin") ? rowJsonObject.getString("mnu_settings_menu_change_tpin") : "0");
                    AppConstants.setMnu_Reset_TPin(rowJsonObject.has("mnu_settings_menu_reset_tpin") ? rowJsonObject.getString("mnu_settings_menu_reset_tpin") : "0");
                    AppConstants.setMnu_Limit_Transaction(rowJsonObject.has("mnu_settings_menu_change_limit") ? rowJsonObject.getString("mnu_settings_menu_change_limit") : "0");
                    AppConstants.setMnu_setting(rowJsonObject.has("mnu_settings") ? rowJsonObject.getString("mnu_settings") : "0");
                    AppConstants.setStopChequebookStatus(rowJsonObject.has("mnu_accounts_stop_chq") ? rowJsonObject.getString("mnu_accounts_stop_chq") : "0");
                    AppConstants.setInqueriChquebookStatus(rowJsonObject.has("mnu_accounts_chq_status") ? rowJsonObject.getString("mnu_accounts_chq_status") : "0");
                    AppConstants.setMnu_accounts_menu_balenquiry_cbs(rowJsonObject.has("mnu_accounts_menu_balenquiry_cbs") ? rowJsonObject.getString("mnu_accounts_menu_balenquiry_cbs") : "0");
                    AppConstants.setMnu_accounts_menu_ministatemnt_cbs(rowJsonObject.has("mnu_accounts_menu_ministatemnt_cbs") ? rowJsonObject.getString("mnu_accounts_menu_ministatemnt_cbs") : "0");
                    AppConstants.setMnu_accounts_menu_showmmid_cbs(rowJsonObject.has("mnu_accounts_menu_showmmid_cbs") ? rowJsonObject.getString("mnu_accounts_menu_showmmid_cbs") : "0");
                    AppConstants.setMnu_accounts_menu_last5imps_cbs(rowJsonObject.has("mnu_accounts_menu_last5imps_cbs") ? rowJsonObject.getString("mnu_accounts_menu_last5imps_cbs") : "0");
                    AppConstants.setMnu_beneficiary_own_bank(rowJsonObject.has("mnu_beneficiary_own_bank") ? rowJsonObject.getString("mnu_beneficiary_own_bank") : "0");
                    AppConstants.setMnu_beneficiary_imps_neft_account_bank(rowJsonObject.has("mnu_beneficiary_imps_neft_account_bank") ? rowJsonObject.getString("mnu_beneficiary_imps_neft_account_bank") : "0");
                    AppConstants.setMnu_beneficiary_imps_mobile_bank(rowJsonObject.has("mnu_beneficiary_imps_mobile_bank") ? rowJsonObject.getString("mnu_beneficiary_imps_mobile_bank") : "0");
                    AppConstants.setMnu_pps_request(rowJsonObject.has("mnu_accounts_menu_pps_request") ? rowJsonObject.getString("mnu_accounts_menu_pps_request") : "0");
                    AppConstants.setMnu_pps_request_enquiry(rowJsonObject.has("mnu_accounts_menu_pps_request_enquiry") ? rowJsonObject.getString("mnu_accounts_menu_pps_request_enquiry") : "0");
                    AppConstants.setMnu_block_debit_card(rowJsonObject.has("mnu_account_block_debit_card") ? rowJsonObject.getString("mnu_account_block_debit_card") : "0");
                    AppConstants.setMnu_verify_beneficiary_name(rowJsonObject.has("mnu_verify_beneficiary_name") ? rowJsonObject.getString("mnu_verify_beneficiary_name") : "0");
                    AppConstants.setMnu_check_imps_transaction_status(rowJsonObject.has("mnu_imps_transction_status") ? rowJsonObject.getString("mnu_imps_transction_status") : "0");
                    AppConstants.setMnu_neft_trans_switch_transaction(rowJsonObject.has("mnu_neft_switch_transaction") ? rowJsonObject.getString("mnu_neft_switch_transaction") : "0");
                    AppConstants.setMnu_bill_pay(rowJsonObject.has("mnu_bill_pay") ? rowJsonObject.getString("mnu_bill_pay") : "0");
                    AppConstants.setMnu_bill_pay_complaint_management(rowJsonObject.has("mnu_bill_pay_complaint_management") ? rowJsonObject.getString("mnu_bill_pay_complaint_management") : "0");
                    AppConstants.setMnu_bill_pay_register_complaints(rowJsonObject.has("mnu_bill_pay_register_complaints") ? rowJsonObject.getString("mnu_bill_pay_register_complaints") : "0");
                    AppConstants.setMnu_bill_pay_track_complaints(rowJsonObject.has("mnu_bill_pay_track_complaints") ? rowJsonObject.getString("mnu_bill_pay_track_complaints") : "0");
                    AppConstants.setMnu_fundtransfer_upi(rowJsonObject.has("mnu_fundtransfer_upi") ? rowJsonObject.getString("mnu_fundtransfer_upi") : "0");
                    AppConstants.setCheckImpsTransStatusFundTransfer(rowJsonObject.has("imps_trans_status_ft") ? rowJsonObject.getString("imps_trans_status_ft") : "0"); //after successfull imps transaction call transaction status apis.
                    AppConstants.setMnu_block_debit_card_switch(rowJsonObject.has("mnu_block_debit_card_switch") ? rowJsonObject.getString("mnu_block_debit_card_switch") : "0"); //TODO
                    AppConstants.setMnu_block_debit_card_cbs(rowJsonObject.has("mnu_block_debit_card_cbs") ? rowJsonObject.getString("mnu_block_debit_card_cbs") : "0"); //TODO
                    AppConstants.setMnu_debit_card_pin_generation(rowJsonObject.has("mnu_debit_card_pin_generation") ? rowJsonObject.getString("mnu_debit_card_pin_generation") : "0");//TODO
                    AppConstants.setMnu_mandate_cancel(rowJsonObject.has("mnu_mandate_cancel") ? rowJsonObject.getString("mnu_mandate_cancel") : "0");
                    AppConstants.setMnu_fundtransfer_upi_get_qr(rowJsonObject.has("mnu_fetch_qr") ? rowJsonObject.getString("mnu_fetch_qr") : "0");
                    AppConstants.setMnu_fundtransfer_upi_create_qr(rowJsonObject.has("mnu_create_new_qr") ? rowJsonObject.getString("mnu_create_new_qr") : "0");
                    AppConstants.setMnu_UPI(rowJsonObject.has("mnu_upi") ? rowJsonObject.getString("mnu_upi") : "0");
                    AppConstants.setMnu_bill(rowJsonObject.has("mnu_bill") ? rowJsonObject.getString("mnu_bill") : "0");
                    AppConstants.setMnu_rd_fd_reckoner(rowJsonObject.has("mnu_rd_fd_reckoner") ? rowJsonObject.getString("mnu_rd_fd_reckoner") : "0");
                    AppConstants.setMnu_amortization_chart(rowJsonObject.has("mnu_amortization_chart") ? rowJsonObject.getString("mnu_amortization_chart") : "0");
                    AppConstants.setMnu_cards(rowJsonObject.has("mnu_cards") ? rowJsonObject.getString("mnu_cards") : "0");
                    AppConstants.setMnu_services(rowJsonObject.has("mnu_Services") ? rowJsonObject.getString("mnu_Services") : "0");
                    AppConstants.setMnu_locate_us(rowJsonObject.has("mnu_locate_us") ? rowJsonObject.getString("mnu_locate_us") : "0");
                    AppConstants.setMnu_need_help(rowJsonObject.has("mnu_need_help") ? rowJsonObject.getString("mnu_need_help") : "0");
                    AppConstants.setMnu_nominee_management(rowJsonObject.has("mnu_nominee_management") ? rowJsonObject.getString("mnu_nominee_management") : "0");
                    AppConstants.setMnu_fingure_print(rowJsonObject.has("mnu_setting_fingure_print") ? rowJsonObject.getString("mnu_setting_fingure_print") : "0");

                    AppConstants.setMpassbook_menu(rowJsonObject.has("mpassbook_menu") ? rowJsonObject.getString("mpassbook_menu") : "0");
                    AppConstants.setMnu_debit_card_limit(rowJsonObject.has("mnu_debit_card_limit") ? rowJsonObject.getString("mnu_debit_card_limit") : "0");
                    AppConstants.setMnu_temp_block_debit_card(rowJsonObject.has("mnu_temp_block_debit_card") ? rowJsonObject.getString("mnu_temp_block_debit_card") : "0");

                    AppConstants.setMnu_bbps(rowJsonObject.has("mnu_bbps_finacus") ? rowJsonObject.getString("mnu_bbps_finacus") : "0");
                    AppConstants.setMnu_bbps_bill_payment_finacus(rowJsonObject.has("mnu_bbps_bill_payment_finacus") ? rowJsonObject.getString("mnu_bbps_bill_payment_finacus") : "0");
                    AppConstants.setMnu_bbps_transaction_history_finacus(rowJsonObject.has("mnu_bbps_transaction_history_finacus") ? rowJsonObject.getString("mnu_bbps_transaction_history_finacus") : "0");
                    AppConstants.setMnu_bbps_complaint_management_finacus(rowJsonObject.has("mnu_bbps_complaint_management_finacus") ? rowJsonObject.getString("mnu_bbps_complaint_management_finacus") : "0");
                    AppConstants.setMnu_bbps_complaint_status_finacus(rowJsonObject.has("mnu_bbps_complaint_status_finacus") ? rowJsonObject.getString("mnu_bbps_complaint_status_finacus") : "0");
                    AppConstants.setMnu_bbps_complaint_history_finacus(rowJsonObject.has("mnu_bbps_complaint_history_finacus") ? rowJsonObject.getString("mnu_bbps_complaint_history_finacus") : "0");
                    AppConstants.setMnu_bbps_transaction_inquiry_finacus(rowJsonObject.has("mnu_bbps_transaction_inquiry_finacus") ? rowJsonObject.getString("mnu_bbps_transaction_inquiry_finacus") : "0");
                    AppConstants.setMnu_bbps_get_duplicate_recipt_finacus(rowJsonObject.has("mnu_bbps_get_duplicate_receipt_finacus") ? rowJsonObject.getString("mnu_bbps_get_duplicate_receipt_finacus") : "0");

                    AppConstants.setMnu_debit_card_pin_verify_finacus(rowJsonObject.has("mnu_debit_card_pin_verify_finacus") ? rowJsonObject.getString("mnu_debit_card_pin_verify_finacus") : "0");

                    AppConstants.setMnu_rdfd_acc_open(rowJsonObject.has("mnu_rdfd_acc_open") ? rowJsonObject.getString("mnu_rdfd_acc_open") : "0");
                    AppConstants.setMnu_privacypolicy(rowJsonObject.has("mnu_privacypolicy") ? rowJsonObject.getString("mnu_privacypolicy") : "0");
                    AppConstants.setMnu_debit_card_set_channel(rowJsonObject.has("mnu_debit_card_set_channel") ? rowJsonObject.getString("mnu_debit_card_set_channel") : "0");
                    AppConstants.setMnu_form15GH(rowJsonObject.has("mnu_form15GH") ? rowJsonObject.getString("mnu_form15GH") : "0");
                    AppConstants.setMnu_standing_instructions(rowJsonObject.has("mnu_standing_instructions") ? rowJsonObject.getString("mnu_standing_instructions") : "0");

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
                    JSONObject bottommenu=rowJsonObject.getJSONObject("bottom_menus");
                    JSONArray botommenus=bottommenu.getJSONArray("menu");
                    if(botommenus != null) {
                        bottomDynamicMenuModels=new ArrayList<>();
                        BottomSubMenuMap=new HashMap<>();
                        for (int i = 0; i < botommenus.length(); i++) {
                            JSONObject bottommenuarry = botommenus.getJSONObject(i);
                            String bmenuCode = bottommenuarry.getString("menu_code");
                            String bcaption = bottommenuarry.getString("menu_caption");

                            BottomDynamicMenuModel bottomDynamicMenuModel=new BottomDynamicMenuModel();
                            bottomDynamicMenuModel.setBottommenucode(bmenuCode);
                            bottomDynamicMenuModel.setBottomcaption(bcaption);
                            bottomDynamicMenuModels.add(bottomDynamicMenuModel);


                            if (bottommenuarry.has("sub_menus")) {
                                JSONObject bottomsubmenus = bottommenuarry.getJSONObject("sub_menus");
                                JSONArray bottomsubmenusarr=bottomsubmenus.getJSONArray("menu");
                                if(bottomsubmenusarr != null){
                                    BottomDynamicSubMenuModel=new ArrayList<>();
                                    for(int j=0;j<bottomsubmenusarr.length();j++){
                                        JSONObject submenuarry = bottomsubmenusarr.getJSONObject(j);
                                        String bsubmenuCode = submenuarry.getString("menu_code");
                                        String bsubcaption = submenuarry.getString("menu_caption");

                                        BottomDynamicMenuModel bottomdynamicsubMenuModel=new BottomDynamicMenuModel();
                                        bottomdynamicsubMenuModel.setBottomsubmenucode(bsubmenuCode);
                                        bottomdynamicsubMenuModel.setBottomcaption(bsubcaption);
                                        BottomDynamicSubMenuModel.add(bottomdynamicsubMenuModel);
                                    }
                                    if(bottomDynamicMenuModels.size()>0){
                                        BottomSubMenuMap.put(bmenuCode,BottomDynamicSubMenuModel);
                                        //AppConstants.setBottomsubmenu(BottomSubMenuMap);
                                    }
                                }
                            }
                        }
                        AppConstants.setBottomparentlist(bottomDynamicMenuModels);
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
                    new GetGLCodeAsync(LockActivity.this, mClientId).execute();
                        /*sessionManager.createLoginSession(AppConstants.getUSERMOBILENUMBER(), mClientId);
                        AppConstants.setCLIENTID(sessionManager.getClientId());
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        method.activityOpenAnimation();*/
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