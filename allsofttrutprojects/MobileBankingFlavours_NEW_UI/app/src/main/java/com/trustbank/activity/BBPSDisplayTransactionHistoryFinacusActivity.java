package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.trustbank.Model.BBPSTransactionHistoryFinacusModel;
import com.trustbank.R;

import com.trustbank.adapter.BBPSDisplayTransactionHistoryFinacusAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.util.List;

public class BBPSDisplayTransactionHistoryFinacusActivity extends AppCompatActivity {

    private static final String TAG = BBPSDisplayTransactionHistoryFinacusActivity.class.getSimpleName();
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
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(BBPSDisplayTransactionHistoryFinacusActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(BBPSDisplayTransactionHistoryFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpsdisplay_transaction_history_finacus);
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
        methods = new TrustMethods(BBPSDisplayTransactionHistoryFinacusActivity.this);
        recycler_view = findViewById(R.id.recycler_view);

        Intent intent =getIntent();
        List<BBPSTransactionHistoryFinacusModel> transactionHistoryList = (List<BBPSTransactionHistoryFinacusModel>) intent.getSerializableExtra("Transaction History List");

        BBPSDisplayTransactionHistoryFinacusAdapter adapter = new BBPSDisplayTransactionHistoryFinacusAdapter(BBPSDisplayTransactionHistoryFinacusActivity.this, transactionHistoryList);
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
                methods.activityCloseAnimation();
                Intent intent = new Intent(BBPSDisplayTransactionHistoryFinacusActivity.this, BBPSFinacusActivity.class);
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
        super.onBackPressed();
        methods.showBackButtonAlert(BBPSDisplayTransactionHistoryFinacusActivity.this);
    }
}