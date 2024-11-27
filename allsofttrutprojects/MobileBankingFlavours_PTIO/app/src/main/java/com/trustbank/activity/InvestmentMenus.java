package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.adapter.FundsTransferAdapter;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.util.Arrays;

public class InvestmentMenus extends AppCompatActivity implements View.OnClickListener {
    private TrustMethods method;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerAccounts;
    RecyclerView recyclerViewHoriListId;
    TrustMethods trustMethods;
    FloatingActionButton scan;
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
                        TrustMethods.naviagteToSplashScreen(InvestmentMenus.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(InvestmentMenus.this, false);
        setContentView(R.layout.activity_cards);
        trustMethods=new TrustMethods(InvestmentMenus.this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.Investments);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

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
            backButton_new = findViewById(R.id.backButton_new);
            backButton_new.setOnClickListener(this);
            method = new TrustMethods(InvestmentMenus.this);
            coordinatorLayout = findViewById(R.id.coordinatorLayout);
            recyclerAccounts = findViewById(R.id.recyclerAccountsId);
            scan = findViewById(R.id.scan);
            scan.setOnClickListener(this);
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
        TrustMethods.Submenuselectorfund(InvestmentMenus.this,recyclerAccounts);
    }
    private void staticData(RecyclerView.LayoutManager mLayoutManager) {

        ImageTextMenuModel[] oldItemData = {
                new ImageTextMenuModel("IMPS Transfer To Account", R.drawable.manage_acc, "1"),
                new ImageTextMenuModel("NEFT Transfer To Account", R.drawable.manage_acc, "1"),
                new ImageTextMenuModel("IMPS Transfer To Mobile Phone", R.drawable.change_password, "1"),
                new ImageTextMenuModel("Self Transfer To Account", R.drawable.change_password, "1"),
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
        FundsTransferAdapter fundsTransferAdapter = new FundsTransferAdapter(InvestmentMenus.this, Arrays.asList(newitemData));
        recyclerAccounts.setAdapter(fundsTransferAdapter);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                method.activityCloseAnimation();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(InvestmentMenus.this);
    }

    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        trustMethods.horizontalRecyclerView(InvestmentMenus.this, recyclerViewHoriListId);
    }

    //back button event
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton_new:
                Intent intent = new Intent(InvestmentMenus.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.scan:
                Intent intent1 = new Intent(InvestmentMenus.this, SelfTransferToAccountActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                break;

        }
    }
}