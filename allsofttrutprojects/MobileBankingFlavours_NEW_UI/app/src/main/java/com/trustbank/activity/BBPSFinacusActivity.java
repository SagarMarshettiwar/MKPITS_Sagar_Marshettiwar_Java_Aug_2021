package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;

import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.adapter.FundsTransferAdapter;
import com.trustbank.adapter.MenuAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.util.ArrayList;
import java.util.Arrays;

public class BBPSFinacusActivity extends AppCompatActivity {

    TrustMethods method;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerAccounts;
    RecyclerView recyclerViewHoriListId;

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
                        TrustMethods.naviagteToSplashScreen(BBPSFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpsfinacus);

        method = new TrustMethods(BBPSFinacusActivity.this);
        inIt();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        try {
            if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
                bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inIt() {
        horizontalRecyclerView();
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            coordinatorLayout = findViewById(R.id.coordinatorLayout);
            recyclerAccounts = findViewById(R.id.recyclerAccountsId);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

            recyclerAccounts.setNestedScrollingEnabled(false);
            recyclerAccounts.setItemAnimator(new DefaultItemAnimator());
            recyclerAccounts.setHasFixedSize(true);
            recyclerAccounts.setLayoutManager(gridLayoutManager);


            /*if ((!TextUtils.isEmpty(AppConstants.getPlay_store_validate())) && AppConstants.getPlay_store_validate().equalsIgnoreCase("1")) {

                staticData(gridLayoutManager);
            } else if (AppConstants.getUSERMOBILENUMBER().equalsIgnoreCase(AppConstants.getPlayStoreDemoUserMobile()) &&
                    AppConstants.getCLIENTID().equalsIgnoreCase(AppConstants.getPlayStoreDemoPasswordClientid())) {
                staticData(gridLayoutManager);
            } else {
                Submenuselectorfund();
            }*/
            Submenuselectorfund();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Submenuselectorfund() {
        TrustMethods.Submenuselectorfund(BBPSFinacusActivity.this,recyclerAccounts);
    }

    private void staticData(RecyclerView.LayoutManager mLayoutManager) {

        ImageTextMenuModel[] oldItemData = {
                new ImageTextMenuModel("Accounts", R.drawable.accounts2, "1","Accounts"),
                new ImageTextMenuModel("Within Bank Transfer", R.drawable.within3, "1", "Within Bank"),
                new ImageTextMenuModel("NEFT Transfer To Account", R.drawable.neft2, "1", "NEFT"),
                new ImageTextMenuModel("IMPS Transfer To Account", R.drawable.imps2, "1", "IMPS"),
                new ImageTextMenuModel("IMPS Transfer To Mobile Phone", R.drawable.imps2, "1", "IMPS P2P"),
                new ImageTextMenuModel("Self Transfer To Account", R.drawable.self3, "1", "Self Transfer"),
                new ImageTextMenuModel("UPI", R.drawable.upi, "1", "UPI"),
                new ImageTextMenuModel("Bill Pay", R.drawable.bbps, "1", "Bill pay"),
                new ImageTextMenuModel("mPassBook", R.drawable.mpassbook, "1", "mPassBook"),
                new ImageTextMenuModel("Statement Request", R.drawable.minista,"1", "Mini Statement"),
                new ImageTextMenuModel("Statement Request CBS", R.drawable.minista, "1", "Mini Statement"),
                new ImageTextMenuModel("Cards", R.drawable.cards5, "1", "Cards"),
                new ImageTextMenuModel("Service Request", R.drawable.service1, "1", "Services"),
                new ImageTextMenuModel("Manage Beneficiaries", R.drawable.ben, "1", "Beneficiaries"),
                new ImageTextMenuModel("Settings", R.drawable.setting3, "1", "Settings"),
                new ImageTextMenuModel("Locate US", R.drawable.location2, "1", "Locate US"),
                new ImageTextMenuModel("Need Help", R.drawable.i1, "1", "Need Help")
        };

        int menuSize = 0;
        for (int k = 0; k < oldItemData.length; k++) {
            if (!TextUtils.isEmpty(oldItemData[k].getIsEnabled())) {
                if (oldItemData[k].getIsEnabled().trim().equals("1")) {
                    menuSize++;
                }
            }

        }
        int j = 0;
        ImageTextMenuModel[] newitemData = new ImageTextMenuModel[menuSize];
        for (int i = 0; i < oldItemData.length; i++) {
            if (!TextUtils.isEmpty(oldItemData[i].getIsEnabled())) {
                if (oldItemData[i].getIsEnabled().trim().equals("1")) {
                    newitemData[j] = oldItemData[i];
                    j++;
                }
            }
        }
        recyclerAccounts.setLayoutManager(mLayoutManager);
        FundsTransferAdapter fundsTransferAdapter = new FundsTransferAdapter(BBPSFinacusActivity.this, Arrays.asList(newitemData));
        recyclerAccounts.setAdapter(fundsTransferAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(BBPSFinacusActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(BBPSFinacusActivity.this);
    }

    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        method.horizontalRecyclerView(BBPSFinacusActivity.this, recyclerViewHoriListId);
    }
}