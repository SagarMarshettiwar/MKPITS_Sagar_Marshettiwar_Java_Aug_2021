package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.adapter.AccountsAdapter;
import com.trustbank.adapter.MenuAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.ItemOffsetDecoration;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountsActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.recyclerAccountsId)
    RecyclerView recyclerAccounts;
    private TrustMethods trustMethods;
    RecyclerView recyclerViewHoriListId;
    FloatingActionButton scan;
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
                    TrustMethods.naviagteToSplashScreen(AccountsActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(AccountsActivity.this, false);
        setContentView(R.layout.activity_accounts);
        ButterKnife.bind(this);
        trustMethods = new TrustMethods(AccountsActivity.this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.Accounts);
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
            scan = findViewById(R.id.scan);
            scan.setOnClickListener((View.OnClickListener) this);

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
        TrustMethods.Submenuselectorfund(AccountsActivity.this,recyclerAccounts);
    }

    private void staticData(GridLayoutManager mLayoutManager) {
        ImageTextMenuModel[] oldItemData = {

                new ImageTextMenuModel("Statement Request", R.drawable.change_password, "1", "Mini Statement"),
                new ImageTextMenuModel("Statement Request CBS", R.drawable.change_password, "1", "Mini Statement"),
                new ImageTextMenuModel("Show MMID", R.drawable.change_password, "1", "Show MMID"),
                new ImageTextMenuModel("Show MMID CBS", R.drawable.change_password, "1", "Show MMID"),
                new ImageTextMenuModel("NEFT Enquiry", R.drawable.change_password, "1", "NEFT Enquiry"),
                new ImageTextMenuModel("Chequebook Request", R.drawable.change_password, "1", "Chequebook Request"),
                new ImageTextMenuModel("mPassBook", R.drawable.change_password, "1", "mPassBook"),
                new ImageTextMenuModel("Cheque Status", R.drawable.change_password,"1", "Cheque Status"),
                new ImageTextMenuModel("Stop Cheque Request", R.drawable.change_password, "1", "Stop Cheque Request"),
                new ImageTextMenuModel("PPS Request", R.drawable.change_password, "1", "Positive Pay Request"),
                new ImageTextMenuModel("PPS Request Enquiry", R.drawable.change_password, "1", "Positive Pay Request Enquiry"),
                new ImageTextMenuModel("Block Debit Card", R.drawable.change_password, "1", "Block Debit Card"),
                new ImageTextMenuModel("Check Transaction Status", R.drawable.change_password, "1", "Check Transaction Status")
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
        AccountsAdapter accountsAdapter = new AccountsAdapter(AccountsActivity.this, newitemData);
        recyclerAccounts.setAdapter(accountsAdapter);
    }



    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(AccountsActivity.this);
    }

    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        trustMethods.horizontalRecyclerView(AccountsActivity.this, recyclerViewHoriListId);
    }

    //back button event
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton_new:
                Intent intent = new Intent(AccountsActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.scan:
                AppConstants.menu_group=true;
                Intent intent1 = new Intent(AccountsActivity.this, SelfTransferToAccountActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                break;

        }
    }
}