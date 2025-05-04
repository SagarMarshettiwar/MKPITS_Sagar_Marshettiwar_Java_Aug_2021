package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trustbank.R;
import com.trustbank.fragment.AddBeneficiallyFormFragment;
import com.trustbank.interfaces.AddBeneficiaryFragmentListener;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

public class BeneficiaryTypeListActivity extends AppCompatActivity implements AddBeneficiaryFragmentListener, View.OnClickListener {

    CardView cvForOwnBank, cvForImpsToAccountNeftRtgs, cvForImpsToMobile,cvbenForUPIId;
    private TextView tvImpsNeft;
    private ImageView backButton_new;
    private TextView toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                if (savedInstanceState != null) {
                    Object currentPID = String.valueOf(android.os.Process.myPid());
                    // Check current PID with old PID
                    if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                        // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                        TrustMethods.naviagteToSplashScreen(BeneficiaryTypeListActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SetTheme.changeToTheme(BeneficiaryTypeListActivity.this, false);
        setContentView(R.layout.activity_beneficiary_type_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.lbl_types_beneficiary);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

        init();
        listener();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {

        backButton_new = findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);
      /*  cvForImpsToAccountNeftRtgs = findViewById(R.id.cvForImpsToAccountNeftRtgs);*/
        cvForOwnBank = findViewById(R.id.cvForOwnBank);
   /*     cvForImpsToMobile = findViewById(R.id.cvForImpsToMobile);
        tvImpsNeft = findViewById(R.id.tvImpsNeftId);
        cvbenForUPIId = findViewById(R.id.cvbenForUPIId);*/

       /* if (AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1")) {
            cvForImpsToAccountNeftRtgs.setVisibility(View.VISIBLE);
            String neftImpsText = "For ";
            if (AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1")) {
                neftImpsText += "IMPS";
            }
            if (AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1") && AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1")) {
                neftImpsText += "/";
            }
            if (AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1")) {
                neftImpsText += "NEFT";
            }
            neftImpsText += " to Account";
            tvImpsNeft.setText(neftImpsText);
        } else {
            cvForImpsToAccountNeftRtgs.setVisibility(View.GONE);
        }*/

        if (AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1")) {
            cvForOwnBank.setVisibility(View.VISIBLE);
        } else {
            cvForOwnBank.setVisibility(View.GONE);
        }

       /* if (AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1")) {
            cvForImpsToMobile.setVisibility(View.VISIBLE);
        } else {
            cvForImpsToMobile.setVisibility(View.GONE);
        }

       if (AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1")) {
           cvbenForUPIId.setVisibility(View.VISIBLE);
        } else {
           cvbenForUPIId.setVisibility(View.GONE);
        }*/

    }

    private void listener() {

        cvForOwnBank.setOnClickListener(v -> {
            FragmentManager manager = this.getSupportFragmentManager();
            DialogFragment newFragment = AddBeneficiallyFormFragment.newInstance("1");
            newFragment.show(manager, "dialog");
        });

       /* cvForImpsToAccountNeftRtgs.setOnClickListener(v -> {
            FragmentManager manager = this.getSupportFragmentManager();
            DialogFragment newFragment = AddBeneficiallyFormFragment.newInstance("2");
            newFragment.show(manager, "dialog");
        });

        cvForImpsToMobile.setOnClickListener(v -> {
            FragmentManager manager = this.getSupportFragmentManager();
            DialogFragment newFragment = AddBeneficiallyFormFragment.newInstance("3");
            newFragment.show(manager, "dialog");
        });

        cvbenForUPIId.setOnClickListener(v -> {
            FragmentManager manager = this.getSupportFragmentManager();
            DialogFragment newFragment = AddBeneficiallyFormFragment.newInstance("4");
            newFragment.show(manager, "dialog");
        });*/

    }

    @Override
    public void addBenficiaryClick() {

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent i=new Intent(BeneficiaryTypeListActivity.this,ManageBeneficiaryActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(BeneficiaryTypeListActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton_new:
                Intent intent = new Intent(BeneficiaryTypeListActivity.this, ManageBeneficiaryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}