package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.AskPermissions;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SessionManager;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SuccessSplashActivity extends AppCompatActivity {
    SessionManager session;
    TextView Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_splash);
        Title = findViewById(R.id.Title);
        Intent intent = getIntent();
        String t = intent.getStringExtra("Title");
        String activity = intent.getStringExtra("Activity");
        Title.setText(t);

        session = new SessionManager(SuccessSplashActivity.this);
        if (NetworkUtil.getConnectivityStatus(SuccessSplashActivity.this)) {
            Thread background;
            background = new Thread() {
                public void run() {
                    try {
                        sleep(2000);
                        switch(Objects.requireNonNull(activity)){
                            case "OpenSavingsAccountActivity":
                                Intent i=new Intent(SuccessSplashActivity.this,SaveMenu.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(i);
                                break;

                            case "OpenLoanAccountActivity":
                                Intent il=new Intent(SuccessSplashActivity.this,BorrowMenu.class);
                                il.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(il);
                                break;

                            case "OpenInvestmentAccountActivity":
                                Intent inv=new Intent(SuccessSplashActivity.this,InvestmentMenus.class);
                                inv.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(inv);
                                break;

                            case "ScheduleVisitActivity":
                                Intent sch=new Intent(SuccessSplashActivity.this,VisitUsMenu.class);
                                sch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(sch);
                                break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            background.start();
        } else {
            TrustMethods.message(SuccessSplashActivity.this, getResources().getString(R.string.error_check_internet));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

}