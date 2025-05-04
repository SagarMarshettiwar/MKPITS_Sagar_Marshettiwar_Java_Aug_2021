package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.adapter.AccountsAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.recyclerAccountsId)
    RecyclerView recyclerAccounts;
    private TrustMethods trustMethods;
    RecyclerView recyclerViewHoriListId;
    private ImageView backButton_new;
    private TextView toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(ProfileSettingsActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(ProfileSettingsActivity.this, false);
        setContentView(R.layout.activity_profile_settings);
        ButterKnife.bind(this);
        trustMethods = new TrustMethods(ProfileSettingsActivity.this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.ProfileSettings);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

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
        horizontalRecyclerView();
        try {
            backButton_new = findViewById(R.id.backButton_new);
            backButton_new.setOnClickListener((View.OnClickListener) this);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

            recyclerAccounts.setNestedScrollingEnabled(false);
            recyclerAccounts.setItemAnimator(new DefaultItemAnimator());
            recyclerAccounts.setHasFixedSize(true);
            recyclerAccounts.setLayoutManager(gridLayoutManager);

            if ((!TextUtils.isEmpty(AppConstants.getPlay_store_validate())) && AppConstants.getPlay_store_validate().equalsIgnoreCase("1")) {
                staticData(gridLayoutManager);
            } else if (AppConstants.getUSERMOBILENUMBER().equalsIgnoreCase(AppConstants.getPlayStoreDemoUserMobile()) &&
                    AppConstants.getCLIENTID().equalsIgnoreCase(AppConstants.getPlayStoreDemoPasswordClientid())) {
                staticData(gridLayoutManager);
            } else {
                Submenuselectorfund();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Submenuselectorfund() {
        TrustMethods.Submenuselectorfund(ProfileSettingsActivity.this,recyclerAccounts);
    }

    private void staticData(GridLayoutManager mLayoutManager) {
        ImageTextMenuModel[] oldItemData = {

                new ImageTextMenuModel("Statement Request", R.drawable.change_password, "1", "Mini Statement"),
                new ImageTextMenuModel("Statement Request CBS", R.drawable.change_password, AppConstants.getMnu_contact(), "Mini Statement"),
                new ImageTextMenuModel("Show MMID", R.drawable.change_password, AppConstants.getMnu_contact(), "Show MMID"),
                new ImageTextMenuModel("Show MMID CBS", R.drawable.change_password, AppConstants.getMnu_contact(), "Show MMID"),
                new ImageTextMenuModel("NEFT Enquiry", R.drawable.change_password, "1", "NEFT Enquiry"),
                new ImageTextMenuModel("Chequebook Request", R.drawable.change_password, AppConstants.getMnu_contact(), "Chequebook Request"),
                new ImageTextMenuModel("mPassBook", R.drawable.change_password, AppConstants.getMnu_contact(), "mPassBook"),
                new ImageTextMenuModel("Cheque Status", R.drawable.change_password, AppConstants.getMnu_contact(), "Cheque Status"),
                new ImageTextMenuModel("Stop Cheque Request", R.drawable.change_password, AppConstants.getMnu_contact(), "Stop Cheque Request"),
                new ImageTextMenuModel("PPS Request", R.drawable.change_password, AppConstants.getMnu_contact(), "Positive Pay Request"),
                new ImageTextMenuModel("PPS Request Enquiry", R.drawable.change_password, AppConstants.getMnu_contact(), "Positive Pay Request Enquiry"),
                new ImageTextMenuModel("Block Debit Card", R.drawable.change_password, AppConstants.getMnu_contact(), "Block Debit Card"),
                new ImageTextMenuModel("Check Transaction Status", R.drawable.change_password, AppConstants.getMnu_contact(), "Check Transaction Status")
        };

        int menuSize = 0;
        for (ImageTextMenuModel itemDatum : oldItemData) {
            if (!TextUtils.isEmpty(itemDatum.getIsEnabled())) {
                if (itemDatum.getIsEnabled().trim().equals("1")) {
                    menuSize++;
                }
            }
        }

        int j = 0;
        ImageTextMenuModel[] newitemData = new ImageTextMenuModel[menuSize];
        for (ImageTextMenuModel oldItemDatum : oldItemData) {
            if (!TextUtils.isEmpty(oldItemDatum.getIsEnabled())) {
                if (oldItemDatum.getIsEnabled().trim().equals("1")) {
                    newitemData[j] = oldItemDatum;
                    j++;
                }
            }
        }

        recyclerAccounts.setLayoutManager(mLayoutManager);
        AccountsAdapter accountsAdapter = new AccountsAdapter(ProfileSettingsActivity.this, newitemData);
        recyclerAccounts.setAdapter(accountsAdapter);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(AccountsActivity.this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(ProfileSettingsActivity.this);
    }

    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        trustMethods.horizontalRecyclerView(ProfileSettingsActivity.this, recyclerViewHoriListId);
    }

    //back button event
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ProfileSettingsActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}