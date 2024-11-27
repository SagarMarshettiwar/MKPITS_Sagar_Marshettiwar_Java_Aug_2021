package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.trustbank.Model.BBPSBillDetailFinacusModel;
import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SessionManager;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BBPSPaymentSuccessfulFinacusActivity extends AppCompatActivity {
    private TrustMethods method;

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
                    TrustMethods.naviagteToSplashScreen(BBPSPaymentSuccessfulFinacusActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(BBPSPaymentSuccessfulFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpspayment_successful_finacus);

        method = new TrustMethods(BBPSPaymentSuccessfulFinacusActivity.this);
        Intent intent = getIntent();
        List<BBPSBillDetailFinacusModel> billDetailList = (List<BBPSBillDetailFinacusModel>) intent.getSerializableExtra("Bill Detail");

        if (NetworkUtil.getConnectivityStatus(BBPSPaymentSuccessfulFinacusActivity.this)) {
            Thread background;
            background = new Thread() {
                public void run() {
                    try {
                        sleep(3000);

                        Intent intent11 = new Intent(getApplicationContext(), BBPSPaymentDetailsFinacusActivity.class);
                        intent11.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        intent11.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent11.putExtra("Bill Detail", (Serializable) billDetailList);
                        startActivity(intent11);
                        finish();
                        method.activityCloseAnimation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            background.start();
        } else {
            TrustMethods.message(BBPSPaymentSuccessfulFinacusActivity.this, getResources().getString(R.string.error_check_internet));
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }
}