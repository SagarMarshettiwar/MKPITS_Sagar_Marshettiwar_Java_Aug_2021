package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.TrustMethods;

public class BBPSDynamicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
        setContentView(R.layout.activity_b_b_p_s_dynamic);
    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(BBPSDynamicActivity.this);
    }
}