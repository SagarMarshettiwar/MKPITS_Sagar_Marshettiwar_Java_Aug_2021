package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.trustbank.Model.BBPSComplaintHistoryModelFinacus;
import com.trustbank.Model.BBPSTransactionInquiryFinacusModel;
import com.trustbank.R;
import com.trustbank.adapter.BBPSDisplayComplaintHistoryFinacusAdapter;
import com.trustbank.adapter.BBPSDisplayTransactionInquiryFinacusAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.util.ArrayList;
import java.util.List;

public class BBPSDisplayTransactionInquiryFinacusActivity extends AppCompatActivity {

    private String TAG = BBPSDisplayTransactionInquiryFinacusActivity.class.getSimpleName();
    TrustMethods methods;
    RecyclerView recycler_view;

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
                        TrustMethods.naviagteToSplashScreen(BBPSDisplayTransactionInquiryFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSDisplayTransactionInquiryFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpsdiaplay_transaction_inquiry_finacus);

        initCompnonet();
    }

    private void initCompnonet() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        methods = new TrustMethods(BBPSDisplayTransactionInquiryFinacusActivity.this);
        recycler_view = findViewById(R.id.recycler_view);

        Intent intent = getIntent();
        List<BBPSTransactionInquiryFinacusModel> transInquiryList = (ArrayList<BBPSTransactionInquiryFinacusModel>)intent.getSerializableExtra("Transaction Inquiry List");

        BBPSDisplayTransactionInquiryFinacusAdapter adapter = new BBPSDisplayTransactionInquiryFinacusAdapter(BBPSDisplayTransactionInquiryFinacusActivity.this, transInquiryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BBPSDisplayTransactionInquiryFinacusActivity.this, BBPSFinacusActivity.class);
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
        TrustMethods.showBackButtonAlert(BBPSDisplayTransactionInquiryFinacusActivity.this);
    }
}