package com.trustbank.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.R;
import com.trustbank.fragment.SimSelectorDialogFragment;
import com.trustbank.fragment.WelcomeMessageDiagligFragment;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;


import static com.trustbank.util.AppConstants.LoginInfo_isSetServerEnabled;
import static com.trustbank.util.AppConstants.isAutoReadOTPEnabled;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WelcomeScreen extends AppCompatActivity implements AlertDialogOkListener {

    static String TAG = WelcomeScreen.class.getSimpleName();
    private SharedPreferences sharedpreferences;
    private TrustMethods trustMethods;
    private String simErrorMsg;
    private Button setServerButton;
    private TextView txtVersionNameId,tv_simName;
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
        SetTheme.changeToTheme(WelcomeScreen.this, true);
        setContentView(R.layout.activity_welcome_screen);

        if (!TrustMethods.isRooted(getApplicationContext())) {
            if (!TrustMethods.isHookUpDevice(getApplicationContext())) {
                initcomponent();
            } else {
                TrustMethods.message(getApplicationContext(), getResources().getString(R.string.device_hooked_up_message));
                finish();
            }
        } else {
            TrustMethods.message(getApplicationContext(), getResources().getString(R.string.device_rooted_message));
            finish();
        }
    }

    private void initcomponent() {
        TextView txt_wlcm_title1= findViewById(R.id.txt_wlcm_title1);
        TextView txt_wlcm_title2= findViewById(R.id.txt_wlcm_title2);
        TextView txt_wlcm_title3= findViewById(R.id.txt_wlcm_title3);
        TextView txt_wlcm_title4 = findViewById(R.id.txt_wlcm_title4);
        TextView wlcm_heading = findViewById(R.id.wlcm_heading);
        trustMethods = new TrustMethods(WelcomeScreen.this);
        Button btnActivate = findViewById(R.id.btnActivate);
        setServerButton = findViewById(R.id.setServerButton);
        txtVersionNameId = findViewById(R.id.txtVersionNameId);
        sharedpreferences = getSharedPreferences(AppConstants.MyPREFERENCES, Context.MODE_PRIVATE);

        if(AppConstants.getIsMPassbook().equals("1")){
            txt_wlcm_title4.setVisibility(View.GONE);
            txt_wlcm_title2.setVisibility(View.GONE);
            txt_wlcm_title3.setVisibility(View.GONE);
            txt_wlcm_title1.setText(getResources().getString(R.string.txt_welcome_title11));
            wlcm_heading.setText("Welcome to \n MPassbook App");
        }else{
            txt_wlcm_title4.setVisibility(View.VISIBLE);
        }

        if (LoginInfo_isSetServerEnabled) {
            setServerButton.setVisibility(View.VISIBLE);
        } else {
            setServerButton.setVisibility(View.GONE);
        }

        if (isAutoReadOTPEnabled) {
            Intent intent = getIntent();
            if (intent.getStringExtra(AppConstants.SIM_ERROR_MSG) != null) {
                simErrorMsg = intent.getStringExtra(AppConstants.SIM_ERROR_MSG);
                assert simErrorMsg != null;
                if (simErrorMsg.equalsIgnoreCase(AppConstants.SIM_NOT_EXISTS)) {
                    AlertDialogMethod.alertDialogOk(WelcomeScreen.this, "NO SIM Card", "No " +
                                    "Sim Card Not Detected. Please insert the sim in slot and try again",
                            getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
                }
            }
        }


        btnActivate.setOnClickListener(view -> {
            if (!TrustMethods.isRooted(getApplicationContext())) {
                if (!trustMethods.packageoneIdentify(WelcomeScreen.this)) {
                        if(AppConstants.getMobile_number_verify().equalsIgnoreCase("true")) {//todo
                            DialogFragment dialogFragment = new SimSelectorDialogFragment(WelcomeScreen.this);
                            dialogFragment.show(getSupportFragmentManager(), "My  Fragment");
                        }else {
                            if (!TrustMethods.isHookUpDevice(getApplicationContext())) {
                                Intent intent = new Intent(WelcomeScreen.this, VerifyMobileNumber.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                trustMethods.activityOpenAnimation();

                            } else {
                                TrustMethods.message(getApplicationContext(), getResources().getString(R.string.device_hooked_up_message));
                                finish();
                            }
                        }

                } else {
                    AlertDialogMethod.alertDialogOk(WelcomeScreen.this, "Security Alert", "Security Alert Following installed apps can be used by " +
                                    "fraudsters to steal your money Please uninstall Remote Access Apps to continue using Mbank",
                            getResources().getString(R.string.btn_ok), 1, false, alertDialogOkListener);
                }
            } else {
                TrustMethods.message(getApplicationContext(), getResources().getString(R.string.device_rooted_message));
            }
        });

        setServerButton.setOnClickListener(v -> serverSettings());
        txtVersionNameId.setText("Application Version " + TrustMethods.getVersionName(WelcomeScreen.this));
    }

    @Override
    public void onDialogOk(int resultCode) {
        if (resultCode == 1) {
            finish();
        }
    }

    protected void serverSettings() {
        Intent intent = new Intent(this, FrmServer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

