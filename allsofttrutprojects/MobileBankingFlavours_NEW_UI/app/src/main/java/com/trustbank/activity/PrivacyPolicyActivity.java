package com.trustbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

public class PrivacyPolicyActivity extends AppCompatActivity {

    TrustMethods trustMethodse;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        if (AppConstants.IS_CHECK_ACCESS_BROKEN){
            if (savedInstanceState != null){
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(PrivacyPolicyActivity.this);
                }
            }
        }

        SetTheme.changeToTheme(PrivacyPolicyActivity.this, false);
        setContentView(R.layout.activity_privacy_policy);
        trustMethodse = new TrustMethods(PrivacyPolicyActivity.this);
        webView = (WebView) findViewById(R.id.webView);
        try {
            if (!AppConstants.getPrivacypolicyurl().equals("NA")) {
                webView.loadUrl(AppConstants.getPrivacypolicyurl());
            } else {
                Toast.makeText(PrivacyPolicyActivity.this, "No privacy Policy", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(PrivacyPolicyActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PrivacyPolicyActivity.this, MenuActivity.class);
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
        TrustMethods.showBackButtonAlert(PrivacyPolicyActivity.this);
    }
}