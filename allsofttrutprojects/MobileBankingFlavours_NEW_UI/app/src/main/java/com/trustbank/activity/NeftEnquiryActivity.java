package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;


public class NeftEnquiryActivity extends AppCompatActivity implements View.OnClickListener {

    private TrustMethods method;
    private LinearLayout layoutSearchByTransactionID, layoutSearchByDate;
    private EditText edTransactionID;
    private TextView txtFrmDate, txtToDate, textNote;
    private RadioGroup rgSearchBy;
    private RadioButton rbDate, rbTransactionId;
    private String searchBy = "";
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
                        TrustMethods.naviagteToSplashScreen(NeftEnquiryActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(NeftEnquiryActivity.this, false);
        setContentView(R.layout.activity_neft_enquiry);

        inIt();
        listener();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }

    private void inIt() {
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            method = new TrustMethods(NeftEnquiryActivity.this);

            CoordinatorLayout layoutNEFT = findViewById(R.id.layoutNEFT);
            layoutSearchByTransactionID = findViewById(R.id.layoutSearchByTransactionID);
            layoutSearchByDate = findViewById(R.id.layoutSearchByDate);

            rgSearchBy = findViewById(R.id.rgSearchBy);
            rbDate = findViewById(R.id.rbDate);
            rbTransactionId = findViewById(R.id.rbTransactionId);

            edTransactionID = findViewById(R.id.edTransactionID);
            txtFrmDate = findViewById(R.id.txtFrmDateId);
            txtToDate = findViewById(R.id.txtToDateId);
            textNote = findViewById(R.id.textNoteId);
            Button btnCancel = findViewById(R.id.btnCancel);
            Button btnSave = findViewById(R.id.btnSave);

            txtFrmDate.setOnClickListener(this);
            txtToDate.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            btnSave.setOnClickListener(this);

            //Default search by date selected
            searchBy = "1";
            layoutSearchByTransactionID.setVisibility(View.GONE);
            layoutSearchByDate.setVisibility(View.VISIBLE);
            setDate();
            horizontalRecyclerView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDate() {
        try {
            txtFrmDate.setText(TrustMethods.getCurrentMonthFirstDate());
            txtToDate.setText(TrustMethods.getMonthYear());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void listener() {
        try {
            rgSearchBy.setOnCheckedChangeListener((group, checkedId) -> {

                if (rbDate.isChecked()) {
                    searchBy = "1";
                    layoutSearchByTransactionID.setVisibility(View.GONE);
                    layoutSearchByDate.setVisibility(View.VISIBLE);
                    textNote.setVisibility(View.VISIBLE);
                } else if (rbTransactionId.isChecked()) {
                    searchBy = "2";
                    layoutSearchByTransactionID.setVisibility(View.VISIBLE);
                    layoutSearchByDate.setVisibility(View.GONE);
                    textNote.setVisibility(View.GONE);
                } else {
                    searchBy = "";
                    layoutSearchByTransactionID.setVisibility(View.GONE);
                    layoutSearchByDate.setVisibility(View.GONE);
                    textNote.setVisibility(View.GONE);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtFrmDateId:
                method.datePickerDisableFuturesDate(NeftEnquiryActivity.this, txtFrmDate);
                break;
            case R.id.txtToDateId:
                method.datePickerDisableFuturesDate(NeftEnquiryActivity.this, txtToDate);
                break;
            case R.id.btnCancel:
                Intent i = new Intent(getApplicationContext(), AccountsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            case R.id.btnSave:
                if (validateEntries()) {

                    String transactionId = edTransactionID.getText().toString();
                    String fromDate = txtFrmDate.getText().toString();
                    String toDate = txtToDate.getText().toString();

                    Intent intent = new Intent(NeftEnquiryActivity.this, NeftTransactionListActivity.class);
                    intent.putExtra("EnquiryCode", searchBy);
                    intent.putExtra("FromDate", fromDate);
                    intent.putExtra("ToDate", toDate);
                    intent.putExtra("TransactionId", transactionId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    method.activityOpenAnimation();

                }
                break;
            default:
                break;
        }
    }

    private boolean validateEntries() {

        String fromDate = txtFrmDate.getText().toString();
        String toDate = txtToDate.getText().toString();
        if (searchBy.equals("")) {
            TrustMethods.message(NeftEnquiryActivity.this, "Select Search By");
            return false;
        } else if (fromDate.isEmpty() && searchBy.equals("1")) {
            TrustMethods.message(NeftEnquiryActivity.this, "Select From Date");
            return false;
        } else if (toDate.isEmpty() && searchBy.equals("1")) {
            TrustMethods.message(NeftEnquiryActivity.this, "Select To Date");
            return false;
        } else if (!TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate) && searchBy.equals("1")) {
            if (TrustMethods.isFromDateGreaterThanToDate(fromDate.trim(), toDate.trim())) {
                TrustMethods.message(getApplicationContext(), "From date cannot be greater than to date.");
                return false;
            } else {
                return true;
            }
        } else if (edTransactionID.getText().toString().isEmpty() && searchBy.equals("2")) {
            TrustMethods.message(NeftEnquiryActivity.this, "Enter Transaction ID");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_accounts_menu_neftenquiry")) {
                        Intent intent = new Intent(NeftEnquiryActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }

                if(AppConstants.account_group){
                    Intent intent = new Intent(NeftEnquiryActivity.this, AccountsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.upi_group) {
                    Intent intent = new Intent(NeftEnquiryActivity.this, UPIActivityMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.service_group) {
                    Intent intent = new Intent(NeftEnquiryActivity.this, ServiceRequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.cards_group) {
                    Intent intent = new Intent(NeftEnquiryActivity.this, Cards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (AppConstants.locate_us_group) {
                    Intent intent = new Intent(NeftEnquiryActivity.this, LocateUs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }else if (AppConstants.need_help_group) {
                    Intent intent = new Intent(NeftEnquiryActivity.this, NeedHelp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }

                Intent intent = new Intent(NeftEnquiryActivity.this, AccountsActivity.class);
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
        TrustMethods.showBackButtonAlert(NeftEnquiryActivity.this);
    }

    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        method.horizontalRecyclerView(NeftEnquiryActivity.this, recyclerViewHoriListId);
    }
}