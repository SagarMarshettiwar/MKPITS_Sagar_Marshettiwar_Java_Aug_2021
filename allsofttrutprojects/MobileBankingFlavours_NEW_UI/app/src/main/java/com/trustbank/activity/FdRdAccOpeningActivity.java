package com.trustbank.activity;

import static com.trustbank.util.TrustMethods.LogMessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.pdf.FdfReader;
import com.trustbank.Model.AccountDetailsModel;
import com.trustbank.Model.FdSchemeModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.Model.OldJoinListModel;
import com.trustbank.Model.ViewJoinModel;
import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class FdRdAccOpeningActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AlertDialogOkListener {

    private static final String TAG = FrmAccountStatement.class.getSimpleName();
    private TrustMethods trustMethods;
    AlertDialogOkListener alertDialogOkListener=this;
    private CoordinatorLayout coordinatorLayout;
    LinearLayout FDdetails_ll, RDdetails_ll, transferfreq_ll, transfermode_layout, accountno_layout, periodinterest_layout,fdperioddays_layout;
    Spinner FDDepositschemespinner, Transfermodespinner, RDDepositschemespinner, Transferfreqspinner, FDperiodunitspinner, RDperiodunitspinner;
    EditText et_fdDepositperiod, et_fdDepositamunt,et_fdinterestrate, et_fdcertificatenumber, et_fdextrainterestrate,et_fdinterestamount,fd_maturitydate,
             et_fdmaturityamt, et_RDinstallmentamount, et_RDDepositperiod,et_RDinterest_rate, et_rdextrainterestrate, et_rdinterestamount,rd_maturitydate,
            et_rdmaturityamount, rd_duedate, et_fdeffctivedate, et_RDEffectivedate, et_fdDepositperioddays, et_periodinterestamt, et_Transfertoaccount;
    TextView tv_accountname,txt_rd_balance, txt_fd_balance;
    Button btn_getfdmaturitydtl, btn_getrdmaturitydtl, btn_rdreset, btn_fdreset, btn_save;
    String cgltype="", branchId="", gl_type="", transferfreq="", fdperiodunit="", rdperiodunit="", fdscheme="", transferToAccount="",transferMode="",
            rdscheme="", calcMethod="", is_renewable="", is_int_renewable="", modeOfOperation, from_accountNo="", balance="";
    ArrayList<String> transferfreqlist=new ArrayList<>();
    HashMap<String,String> transferfreqmap=new HashMap<>();
    ArrayList<String> fdschemelist=new ArrayList<>();
    ArrayList<FdSchemeModel> fdSchemeArrayList=new ArrayList<>();
    HashMap<String,String> fdschememap=new HashMap<>();
    ArrayList<String> rdschemelist=new ArrayList<>();
    HashMap<String,String> rdschememap=new HashMap<>();
    ArrayList<String> periodunitlist=new ArrayList<>();
    HashMap<String,String> periodunitmap=new HashMap<>();
    CheckBox cb_isrenewble,cb_isintrenewble;
    ArrayList<OldJoinListModel> jointList;

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
                        TrustMethods.naviagteToSplashScreen(FdRdAccOpeningActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(FdRdAccOpeningActivity.this, false);
        setContentView(R.layout.activity_fd_rd_acc_opening);

        Intent intent = getIntent();
        modeOfOperation = intent.getStringExtra("mode_of_operation");
        cgltype = intent.getStringExtra("cgltype");
        branchId = intent.getStringExtra("branch_id");
        from_accountNo = intent.getStringExtra("from_accountNo");
        jointList = (ArrayList<OldJoinListModel>) intent.getSerializableExtra("jointList");
        balance = intent.getStringExtra("balance");

        init();
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

    private void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        trustMethods = new TrustMethods(FdRdAccOpeningActivity.this);
        trustMethods.activityOpenAnimation();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        FDdetails_ll = findViewById(R.id.FDdetails_ll);
        RDdetails_ll = findViewById(R.id.RDdetails_ll);
        transferfreq_ll = findViewById(R.id.transferfreq_ll);
        transfermode_layout = findViewById(R.id.transfermode_layout);
        periodinterest_layout = findViewById(R.id.periodinterest_layout);
        FDDepositschemespinner = findViewById(R.id.FDDepositschemespinner);
        Transfermodespinner = findViewById(R.id.Transfermodespinner);
        RDDepositschemespinner = findViewById(R.id.RDDepositschemespinner);
        Transferfreqspinner = findViewById(R.id.Transferfreqspinner);
        FDperiodunitspinner = findViewById(R.id.FDperiodunitspinner);
        fdperioddays_layout = findViewById(R.id.fdperioddays_layout);
        RDperiodunitspinner = findViewById(R.id.RDperiodunitspinner);
        et_fdDepositperiod = findViewById(R.id.et_fdDepositperiod);
        et_fdDepositamunt = findViewById(R.id.et_fdDepositamunt);
        et_fdinterestrate = findViewById(R.id.et_fdinterestrate);
        et_fdcertificatenumber = findViewById(R.id.et_fdcertificatenumber);
        et_fdextrainterestrate = findViewById(R.id.et_fdextrainterestrate);
        et_fdinterestamount = findViewById(R.id.et_fdinterestamount);
        fd_maturitydate = findViewById(R.id.fd_maturitydate);
        et_fdmaturityamt = findViewById(R.id.et_fdmaturityamt);
        et_RDinstallmentamount = findViewById(R.id.et_RDinstallmentamount);
        et_RDDepositperiod = findViewById(R.id.et_RDDepositperiod);
        et_RDinterest_rate = findViewById(R.id.et_RDinterest_rate);
        et_rdextrainterestrate = findViewById(R.id.et_rdextrainterestrate);
        et_rdinterestamount = findViewById(R.id.et_rdinterestamount);
        rd_maturitydate = findViewById(R.id.rd_maturitydate);
        et_rdmaturityamount = findViewById(R.id.et_rdmaturityamount);
        rd_duedate = findViewById(R.id.rd_duedate);
        et_fdeffctivedate = findViewById(R.id.et_fdeffctivedate);
        et_RDEffectivedate = findViewById(R.id.et_RDEffectivedate);
        accountno_layout = findViewById(R.id.accountno_layout);
        et_fdDepositperioddays = findViewById(R.id.et_fdDepositperioddays);
        cb_isrenewble=findViewById(R.id.cb_isrenewble);
        cb_isintrenewble=findViewById(R.id.cb_isintrenewble);
        tv_accountname=findViewById(R.id.tv_accountname);
        et_periodinterestamt=findViewById(R.id.et_periodinterestamt);
        et_Transfertoaccount=findViewById(R.id.et_Transfertoaccount);
        btn_getfdmaturitydtl=findViewById(R.id.btn_getfdmaturitydtl);
        btn_getrdmaturitydtl=findViewById(R.id.btn_getrdmaturitydtl);
        btn_rdreset=findViewById(R.id.btn_rdreset);
        btn_fdreset=findViewById(R.id.btn_fdreset);
        btn_save=findViewById(R.id.btn_save);
        txt_rd_balance=findViewById(R.id.txt_rd_balance);
        txt_fd_balance=findViewById(R.id.txt_fd_balance);

        txt_rd_balance.setText(" Balance: "+balance);
        txt_fd_balance.setText(" Balance: "+balance);

        rd_duedate.setOnClickListener(this);
        rd_maturitydate.setOnClickListener(this);
        fd_maturitydate.setOnClickListener(this);
        btn_getfdmaturitydtl.setOnClickListener(this);
        btn_getrdmaturitydtl.setOnClickListener(this);
        btn_rdreset.setOnClickListener(this);
        btn_fdreset.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        FDDepositschemespinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        Transfermodespinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        RDDepositschemespinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        et_fdeffctivedate.setText(df.format(c));
        et_fdeffctivedate.setEnabled(false);
        et_RDEffectivedate.setText(df.format(c));
        et_RDEffectivedate.setEnabled(false);

        is_renewable="0";
        is_int_renewable="0";

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FdRdAccOpeningActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(FdRdAccOpeningActivity.this)) {
                new LookUpAsyncTask(FdRdAccOpeningActivity.this).execute();
            } else {
                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
            }
        } else {
            TrustMethods.displaySimErrorDialog(FdRdAccOpeningActivity.this);
        }

        cb_isrenewble.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    is_renewable="1";
                }else{
                    is_renewable="0";
                }
            }
        });

        cb_isintrenewble.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    is_int_renewable="1";
                }else{
                    is_int_renewable="0";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rd_duedate:
                trustMethods.datePickerymd(FdRdAccOpeningActivity.this, rd_duedate);
                break;

            case R.id.rd_maturitydate:
                trustMethods.datePickerymd(FdRdAccOpeningActivity.this,rd_maturitydate);
                break;

            case R.id.fd_maturitydate:
                trustMethods.datePickerymd(FdRdAccOpeningActivity.this,fd_maturitydate);
                break;

            case R.id.btn_getaccname:
                if(TextUtils.isEmpty(et_Transfertoaccount.getText().toString().trim()))
                    TrustMethods.showSnackBarMessage("Please Enter Account No.", coordinatorLayout);
                else
                {
                    if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FdRdAccOpeningActivity.this)) {
                        if (NetworkUtil.getConnectivityStatus(FdRdAccOpeningActivity.this)) {
                            new GetClientName(FdRdAccOpeningActivity.this, et_Transfertoaccount.getText().toString(), "ACCOUNT").execute();
                        } else {
                            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                        }
                    } else {
                        TrustMethods.displaySimErrorDialog(FdRdAccOpeningActivity.this);
                    }
                }
                break;

            case R.id.btn_save:
                Save();
                break;

            case R.id.btn_getfdmaturitydtl:
                if(et_fdDepositperiod.getText().toString().length()>0 && et_fdDepositamunt.getText().toString().length()>0)
                {
                    String fdEffectiveDate = et_fdeffctivedate.getText().toString();
                    String fdDepositPeriod = et_fdDepositperiod.getText().toString();
                    String fdDepositAmount = et_fdDepositamunt.getText().toString();
                    if(fdscheme.length()>0 && fdperiodunit.length()>0
                            &&  fdEffectiveDate.length()>0 &&  fdDepositPeriod.length()>0
                            &&  fdDepositAmount.length()>0 && Double.parseDouble(fdDepositAmount)>0)
                    {
                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FdRdAccOpeningActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(FdRdAccOpeningActivity.this)) {
                                new GeTFDRDMatDetails(FdRdAccOpeningActivity.this).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(FdRdAccOpeningActivity.this);
                        }
                    }
                }
                break;

            case R.id.btn_getrdmaturitydtl:
                if(et_RDDepositperiod.getText().toString().length()>0 && et_RDinstallmentamount.getText().toString().length()>0)
                {
                    String rdEffectiveDate = et_RDEffectivedate.getText().toString();
                    String rdDepositPeriod = et_RDDepositperiod.getText().toString();
                    String rdDepositAmount = et_RDinstallmentamount.getText().toString();
                    if(rdscheme.length()>0 && rdperiodunit.length()>0
                            &&  rdEffectiveDate.length()>0 &&  rdDepositPeriod.length()>0
                            &&  rdDepositAmount.length()>0 && Double.parseDouble(rdDepositAmount)>0)
                    {
                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FdRdAccOpeningActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(FdRdAccOpeningActivity.this)) {
                                new GeTFDRDMatDetails(FdRdAccOpeningActivity.this).execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(FdRdAccOpeningActivity.this);
                        }
                    }
                }
                break;

            case R.id.btn_fdreset:
                FDDepositschemespinner.setSelection(0);
                FDperiodunitspinner.setSelection(0);
                et_fdDepositperiod.setText("");
                et_fdDepositperioddays.setText("");
                et_fdDepositamunt.setText("");
                et_fdinterestrate.setText("");
                et_fdextrainterestrate.setText("");
                et_fdinterestamount.setText("");
                fd_maturitydate.setText("");
                et_fdmaturityamt.setText("");
                et_fdcertificatenumber.setText("");
                cb_isrenewble.setChecked(false);
                cb_isintrenewble.setChecked(false);
                FDperiodunitspinner.setEnabled(true);
                et_fdDepositperiod.setEnabled(true);
                et_fdDepositperioddays.setEnabled(true);
                et_fdDepositamunt.setEnabled(true);
                break;

            case R.id.btn_rdreset:
                RDperiodunitspinner.setSelection(0);
                et_RDDepositperiod.setText("");
                et_RDinstallmentamount.setText("");
                et_RDinterest_rate.setText("");
                et_rdextrainterestrate.setText("");
                rd_maturitydate.setText("");
                et_rdmaturityamount.setText("");

                RDperiodunitspinner.setEnabled(true);
                et_RDDepositperiod.setEnabled(true);
                et_RDinstallmentamount.setEnabled(true);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.FDDepositschemespinner:
                try
                {
                    if (position != 0)
                    {
                        String Select = (String) parent.getItemAtPosition(position);
                        fdscheme=fdschememap.get(Select);
                        et_fdDepositperiod.setText("");
                        et_fdDepositamunt.setText("");
                        //FDperiodunitspinner.setSelection(0);
                        et_fdinterestrate.setText("");
                        et_fdcertificatenumber.setText("");
                        et_fdextrainterestrate.setText("");
                        et_fdinterestamount.setText("");
                        fd_maturitydate.setText("");
                        et_fdmaturityamt.setText("");
                        for(int i=0;i<fdSchemeArrayList.size();i++)
                        {
                            if(fdscheme.equals(fdSchemeArrayList.get(i).getFdSchemeId()))
                            {
                                transferToAccount=fdSchemeArrayList.get(i).getFdTransferToAccount();
                            }
                        }
                        if(transferToAccount.equals("1"))
                        {
                            transferfreq_ll.setVisibility(View.VISIBLE);
                            transfermode_layout.setVisibility(View.VISIBLE);
                            accountno_layout.setVisibility(View.VISIBLE);
                            periodinterest_layout.setVisibility(View.VISIBLE);
                        }

                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FdRdAccOpeningActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(FdRdAccOpeningActivity.this)) {
                                new GetPeriodUnitAsyncTask(FdRdAccOpeningActivity.this,fdscheme,"FDPERIODUNIT").execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(FdRdAccOpeningActivity.this);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case R .id.Transfermodespinner:
                transferMode="0";
                break;

            case R.id.RDDepositschemespinner:
                try
                {
                    if (position != 0)
                    {
                        String Select = (String) parent.getItemAtPosition(position);
                        rdscheme=rdschememap.get(Select);
                        et_RDinstallmentamount.setText("");
                        et_RDDepositperiod.setText("");
                        et_RDinterest_rate.setText("");
                        et_rdextrainterestrate.setText("");
                        et_rdinterestamount.setText("");
                        rd_maturitydate.setText("");
                        et_rdmaturityamount.setText("");
                        rd_duedate.setText("");

                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FdRdAccOpeningActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(FdRdAccOpeningActivity.this)) {
                                new GetPeriodUnitAsyncTask(FdRdAccOpeningActivity.this,rdscheme,"RDPERIODUNIT").execute();
                            } else {
                                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                            }
                        } else {
                            TrustMethods.displaySimErrorDialog(FdRdAccOpeningActivity.this);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDialogOk(int resultCode) {
        switch(resultCode){
            case 1:
                Intent intent = new Intent(FdRdAccOpeningActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    private class LookUpAsyncTask extends AsyncTask<Void, Void, Void> {
        private String _error = "";
        private Context ctx;
        private ProgressDialog pDialog;
        private String result;
        private String action="GET_LOOKUPS";
        JSONObject responseData;
        public LookUpAsyncTask(Context ctx) {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FdRdAccOpeningActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url= TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);

            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }

            String jsonString = "{\"payload_base64\":\"" + "PGRhdGE+CiAgICA8bG9va3Vwcz4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5NT0RFX09GX09QRVJBVElPTjwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5CUkFOQ0g8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+VElUTEU8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+UklTS19QQVJBTUVURVI8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+U1RBVEU8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+Q0lUWTwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5ET0NfVFlQRV9JRF9QUk9PRjwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5ET0NfVFlQRV9BRERSRVNTX0NPUlJFU1BPTkRFTkNFPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPkRPQ19UWVBFX0FERFJFU1NfUEVSTUFORU5UPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPk5PTl9JTkRJX0RPQ19UWVBFX0FERFJFU1NfUEVSTUFORU5UPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPk5PTl9JTkRJX0RPQ19UWVBFX0FERFJFU1NfQ09SUkVTUE9OREVOQ0U8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+Tk9OX0lORElfRE9DX1RZUEVfSURfUFJPT0Y8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+Tk9OX0lORElfUklTS19QQVJBTUVURVI8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CgkJPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+VFJBTlNGRVJfRlJFUVVFTkNZPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPlBFUklPRF9VTklUPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPlJFTEFUSU9OPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPkZEX1NDSEVNRTwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KCQk8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5SRF9TQ0hFTUU8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CgkJPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+U0JfU0NIRU1FPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPklORElfQ0xJRU5UX0NBVEVHT1JZPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgoJCTxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPk5PTl9JTkRJX0NMSUVOVF9DQVRFR09SWTwvY29kZT4KICAgICAgICA8L2xvb2t1cD4JCQogICAgPC9sb29rdXBzPgo8L2RhdGE+" + "\"}";
            try {
                result = HttpClientWrapper.postWithActionAuthToken(url,jsonString,action, AppConstants.getAuth_token());
                Log.e("result",result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if(responseCode.equals("1")){
                    responseData = jsonObject.getJSONObject("response").getJSONObject("data");
                } else {
                    String errorCode = responseData.has("error_code") ? responseData.getString("error_code") : "NA";
                    this._error = responseData.has("error_message") ? responseData.getString("error_message") : "NA";
                }

                if(responseData!=null) {
                    JSONArray transferfrequencyarray = responseData.getJSONObject("list_transfer_frequency").getJSONArray("transfer_frequency");
                    transferfreqmap=new HashMap<>();
                    transferfreqlist = new ArrayList<>();
                    transferfreqlist.add("Please Select");
                    for (int i = 0; i < transferfrequencyarray.length(); i++) {
                        JSONObject tf = transferfrequencyarray.getJSONObject(i);
                        String valueField = tf.getString("value_field");
                        String textField = tf.getString("text_field");
                        transferfreqlist.add(textField);
                        transferfreqmap.put(textField,valueField);
                    }

                    JSONArray periodunitArray = responseData.getJSONObject("list_period_unit").getJSONArray("period_unit");
                    periodunitmap=new HashMap<>();
                    periodunitlist = new ArrayList<>();
                    periodunitlist.add("Please Select");
                    for (int i = 0; i < periodunitArray.length(); i++) {
                        JSONObject pu = periodunitArray.getJSONObject(i);
                        String valueField = pu.getString("value_field");
                        String textField = pu.getString("text_field");
                        periodunitlist.add(textField);
                        periodunitmap.put(textField,valueField);
                    }

                    if (responseData.get("list_fd_scheme") instanceof JSONArray) {
                        JSONArray fdscheamArray = responseData.getJSONObject("list_fd_scheme").getJSONArray("fd_scheme");
                        fdschememap = new HashMap<>();
                        fdschemelist = new ArrayList<>();
                        fdschemelist.add("Please Select");
                        for (int i = 0; i < fdscheamArray.length(); i++) {
                            JSONObject fd = fdscheamArray.getJSONObject(i);
                            String valueField = fd.getString("value_field");
                            String textField = fd.getString("text_field");

                            fdschemelist.add(textField);
                            fdschememap.put(textField, valueField);
                            fdSchemeArrayList.add(new FdSchemeModel(valueField,textField,fd.getString("tran_to_account")));
                        }
                    }
                    else if (responseData.get("list_fd_scheme") instanceof JSONObject)
                    {
                        fdschememap=new HashMap<>();
                        JSONObject jObj=responseData.getJSONObject("list_fd_scheme");
                        if (jObj.get("fd_scheme") instanceof JSONArray)
                        {
                            JSONArray fdschemeArray = jObj.getJSONArray("fd_scheme");
                            fdschemelist = new ArrayList<>();
                            fdschemelist = new ArrayList<>();
                            fdschemelist.add("Please Select");
                            for (int i = 0; i < fdschemeArray.length(); i++) {
                                JSONObject fd = fdschemeArray.getJSONObject(i);
                                String valueField = fd.getString("value_field");
                                String textField = fd.getString("text_field");
                                fdschemelist.add(textField);
                                fdschememap.put(textField,valueField);
                                if(fd.has("tran_to_account"))
                                    fdSchemeArrayList.add(new FdSchemeModel(valueField,textField, fd.getString("tran_to_account")));
                                else
                                    fdSchemeArrayList.add(new FdSchemeModel(valueField,textField, ""));
                            }
                        }
                        else
                        {
                            fdschemelist = new ArrayList<>();
                            fdschemelist.add("Please Select");
                            Log.e("FDRD","list_fd_scheme : "+responseData.getJSONObject("list_fd_scheme"));
                            JSONObject fd = responseData.getJSONObject("list_fd_scheme").getJSONObject("fd_scheme");
                            String valueField = fd.getString("value_field");
                            String textField = fd.getString("text_field");
                            fdschemelist.add(textField);
                            fdschememap.put(textField,valueField);
                            if(fd.has("tran_to_account"))
                                fdSchemeArrayList.add(new FdSchemeModel(valueField,textField, fd.getString("tran_to_account")));
                            else
                                fdSchemeArrayList.add(new FdSchemeModel(valueField,textField, ""));
                        }
                    }

                    if (responseData.get("list_rd_scheme") instanceof JSONArray)
                    {
                        rdschememap=new HashMap<>();
                        JSONArray rdschemeArray = responseData.getJSONObject("list_rd_scheme").getJSONArray("rd_scheme");
                        rdschemelist = new ArrayList<>();
                        rdschemelist.add("Please Select");
                        for (int i = 0; i < rdschemeArray.length(); i++) {
                            JSONObject rd = rdschemeArray.getJSONObject(i);
                            String valueField = rd.getString("value_field");
                            String textField = rd.getString("text_field");
                            rdschemelist.add(textField);
                            rdschememap.put(textField,valueField);
                        }
                    }
                    else if (responseData.get("list_rd_scheme") instanceof JSONObject)
                    {
                        rdschememap=new HashMap<>();
                        JSONObject jObj=responseData.getJSONObject("list_rd_scheme");
                        if (jObj.get("rd_scheme") instanceof JSONArray)
                        {
                            JSONArray rdschemeArray = jObj.getJSONArray("rd_scheme");
                            rdschemelist = new ArrayList<>();
                            rdschemelist.add("Please Select");
                            for (int i = 0; i < rdschemeArray.length(); i++) {
                                JSONObject rd = rdschemeArray.getJSONObject(i);
                                String valueField = rd.getString("value_field");
                                String textField = rd.getString("text_field");
                                rdschemelist.add(textField);
                                rdschememap.put(textField,valueField);
                            }
                        }
                        else
                        {
                            rdschemelist = new ArrayList<>();
                            rdschemelist.add("Please Select");
                            Log.e("FDRD","list_rd_scheme : "+responseData.getJSONObject("list_rd_scheme"));
                            JSONObject rd = responseData.getJSONObject("list_rd_scheme").getJSONObject("rd_scheme");
                            String valueField = rd.getString("value_field");
                            String textField = rd.getString("text_field");
                            rdschemelist.add(textField);
                            rdschememap.put(textField,valueField);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (this._error != "" && !TextUtils.isEmpty(_error)) {
                    TrustMethods.showSnackBarMessage(this._error, coordinatorLayout);
                    LogMessage("error", this._error);
                    return;
                }
                else {
                    setLookUpSpinners();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class GetPeriodUnitAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        private String _error = "";
        private Context ctx;
        private String result;
        private String action="GET_PERIOD_UNIT";
        String schemeId,tag;
        public GetPeriodUnitAsyncTask(Context ctx,String schemeId,String tag) {
            this.ctx=ctx;
            this.schemeId=schemeId;
            this.tag=tag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FdRdAccOpeningActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            calcMethod="";
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String url= TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);
            JSONObject jObj=new JSONObject();
            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }
            byte[] data = new byte[0];
            try
            {
                jObj.put("schemeid", schemeId);
                jObj.put("tag",tag);
            } catch ( JSONException e) {
                throw new RuntimeException(e);
            }
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            Log.e("encrypted",base64);
            String jsonString = jObj.toString();

            try
            {
                Log.e("jsonString",jsonString);
                result = HttpClientWrapper.postWithActionAuthToken(url,jsonString,action, AppConstants.getAuth_token());
                Log.e("result",result);
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if(responseCode.equals("1"))
                {
                    try
                    {
                        periodunitlist.clear();
                        periodunitmap=new HashMap<>();
                        periodunitlist = new ArrayList<>();
                        periodunitlist.add("Please Select");
                        //{"response_code":1,"response":{"Table":[{"codeid":69,"description":"Months"},{"codeid":68,"description":"Days"}]},"error_code":null,"error_message":null}
                        JSONArray periodUnitArray;

                        periodUnitArray = jsonObject.getJSONObject("response").getJSONArray("Table");
                        Log.e("PERIODUNIT","periodUnitArray : "+periodUnitArray);
                        for(int i=0;i<periodUnitArray.length();i++)
                        {
                            JSONObject periodUnitJson = periodUnitArray.getJSONObject(i);
                            String valueField = periodUnitJson.getString("codeid");
                            String textField = periodUnitJson.getString("description");
                            periodunitlist.add(textField);
                            periodunitmap.put(textField,valueField);
                        }
                    }
                    catch(Exception e)
                    {
                        _error = "Please Select Valid Details";
                    }
                }
                else
                {
                    String errorCode = jsonObject.has("error_code") ? jsonObject.getString("error_code") : "NA";
                    _error = jsonObject.has("error_message") ? jsonObject.getString("error_message") : "NA";
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (!_error.equalsIgnoreCase("")) {
                    TrustMethods.showSnackBarMessage(_error, coordinatorLayout);
                }else{
                    setPeriodUnitSpin();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private class GeTFDRDMatDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        private String _error = "";
        private Context ctx;
        private String result;
        private String action="GET_MATURITY_DETAILS";
        JSONObject respTableOne,respTableTwo,respTableThree,respTableFour;
        public GeTFDRDMatDetails(Context ctx) {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FdRdAccOpeningActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            calcMethod="";
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url= TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);
            JSONObject jObj=new JSONObject();
            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }
            byte[] data = new byte[0];
            try
            {
                if(gl_type.equals("FD"))
                {
                    String fdEffectiveDate = et_fdeffctivedate.getText().toString();
                    String fdDepositPeriod = et_fdDepositperiod.getText().toString();
                    String fdDepositAmount = et_fdDepositamunt.getText().toString();
                    String fdDepositPeriodDay = et_fdDepositperioddays.getText().toString();

                    fdEffectiveDate=fdEffectiveDate.split("/")[0]+"-"+fdEffectiveDate.split("/")[1]+"-"+fdEffectiveDate.split("/")[2];
                    jObj.put("scheme_id", fdscheme);
                    jObj.put("scheme_date", fdEffectiveDate);
                    jObj.put("period_unit", fdperiodunit);
                    jObj.put("deposit_period", fdDepositPeriod);
                    jObj.put("fd_deposit_period_days", fdDepositPeriodDay);
                    jObj.put("amount", fdDepositAmount);
                    jObj.put("payment_frequency", "72");
                }
                else if(gl_type.equals("RD"))
                {
                    String rdEffectiveDate = et_RDEffectivedate.getText().toString();
                    String rdDepositPeriod = et_RDDepositperiod.getText().toString();
                    String rdDepositAmount = et_RDinstallmentamount.getText().toString();
                    rdEffectiveDate=rdEffectiveDate.split("/")[0]+"-"+rdEffectiveDate.split("/")[1]+"-"+rdEffectiveDate.split("/")[2];
                    jObj.put("scheme_id", rdscheme);
                    jObj.put("scheme_date", rdEffectiveDate);
                    jObj.put("period_unit", rdperiodunit);
                    jObj.put("deposit_period", rdDepositPeriod);
                    jObj.put("fd_deposit_period_days", rdDepositPeriod);
                    jObj.put("amount", rdDepositAmount);
                    jObj.put("payment_frequency", "72");
                }

                jObj.put("client_id", AppConstants.getCLIENTID());
                jObj.put("action",gl_type);
            } catch ( JSONException e) {
                throw new RuntimeException(e);
            }
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            Log.e("encrypted",jObj.toString());
            String jsonString = jObj.toString();// "{\"agent_id\":\"" + GlobalVarHolder.Agent_id + "\", \"payload_base64\":\"" + base64 + "\"}";

            try
            {
                Log.e("jsonString",jsonString);
                result = HttpClientWrapper.postWithActionAuthToken(url,jsonString,action, AppConstants.getAuth_token());
                Log.e("result",result);
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if(responseCode.equals("1"))
                {
                    try
                    {
                        respTableOne = jsonObject.getJSONObject("response").getJSONArray("Table").getJSONObject(0);
                        respTableTwo = jsonObject.getJSONObject("response").getJSONArray("Table1").getJSONObject(0);
                        respTableThree = jsonObject.getJSONObject("response").getJSONArray("Table2").getJSONObject(0);
                        respTableFour = jsonObject.getJSONObject("response").getJSONArray("Table3").getJSONObject(0);
                    }
                    catch(Exception e)
                    {
                        if(jsonObject.getJSONObject("response").has("data"))
                        {
                            respTableOne=new JSONObject();
                            respTableTwo=new JSONObject();
                            respTableThree=new JSONObject();
                            respTableFour=new JSONObject();
                            JSONObject jsonData=jsonObject.getJSONObject("response").getJSONObject("data");
                            respTableOne.put("MaturityDate",jsonData.get("MaturityDate"));
                            respTableTwo.put("InterestRate",jsonData.get("InterestRate"));
                            respTableTwo.put("ExtraInterestRate",jsonData.get("ExtraInterestRate"));
                            respTableThree.put("InterestCalculationMethod",jsonData.get("InterestCalculationMethod"));
                            respTableFour.put("interest_amount",jsonData.get("interest_amount"));
                            respTableFour.put("maturity_amount",jsonData.get("maturity_amount"));
                            respTableFour.put("period_interest_amount",jsonData.get("period_interest_amount"));
                        }
                        else
                            _error = "Please Select Valid Details";
                    }
                }
                else
                {
                    String errorCode = jsonObject.has("error_code") ? jsonObject.getString("error_code") : "NA";
                    _error = jsonObject.has("error_message") ? jsonObject.getString("error_message") : "NA";
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (!_error.equalsIgnoreCase("")) {
                TrustMethods.showSnackBarMessage(_error, coordinatorLayout);

            } else {
                try {
                    if(gl_type.equals("FD"))
                    {
                        fd_maturitydate.setText(respTableOne.getString("MaturityDate"));
                        et_fdinterestrate.setText(respTableTwo.getString("InterestRate"));
                        et_fdextrainterestrate.setText(respTableTwo.getString("ExtraInterestRate"));
                        et_fdinterestamount.setText(respTableFour.getString("interest_amount"));
                        et_fdmaturityamt.setText(respTableFour.getString("maturity_amount"));
                        et_periodinterestamt.setText(respTableFour.getString("period_interest_amount"));

                        FDperiodunitspinner.setEnabled(false);
                        et_fdDepositperiod.setEnabled(false);
                        et_fdDepositperioddays.setEnabled(false);
                        et_fdDepositamunt.setEnabled(false);
                        fd_maturitydate.setEnabled(false);
                        et_fdinterestrate.setEnabled(false);
                        et_fdinterestamount.setEnabled(false);
                        et_fdmaturityamt.setEnabled(false);
                        et_fdextrainterestrate.setEnabled(false);
                    }
                    else if(gl_type.equals("RD"))
                    {
                        et_rdinterestamount.setText(respTableFour.getString("interest_amount"));
                        et_rdmaturityamount.setText(respTableFour.getString("maturity_amount"));
                        rd_duedate.setText(respTableOne.getString("MaturityDate"));
                        et_RDinterest_rate.setText(respTableTwo.getString("InterestRate"));
                        rd_maturitydate.setText(respTableOne.getString("MaturityDate"));

                        et_rdinterestamount.setEnabled(false);
                        et_rdmaturityamount.setEnabled(false);
                        et_RDinterest_rate.setEnabled(false);
                        rd_duedate.setEnabled(false);
                        rd_maturitydate.setEnabled(false);
                        et_rdextrainterestrate.setEnabled(false);

                        RDperiodunitspinner.setEnabled(false);
                        et_RDDepositperiod.setEnabled(false);
                        et_RDinstallmentamount.setEnabled(false);
                    }

                    calcMethod=respTableThree.getString("InterestCalculationMethod");
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (this._error != "" && !TextUtils.isEmpty(_error)) {
                Toast.makeText(this.ctx, this._error, Toast.LENGTH_SHORT).show();
                LogMessage("error", this._error);
                return;
            }
        }
    }

    private void setPeriodUnitSpin() {
        if(periodunitlist.size() != 0) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(FdRdAccOpeningActivity.this, android.R.layout.simple_spinner_item, periodunitlist);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            FDperiodunitspinner.setAdapter(spinnerArrayAdapter);
            FDperiodunitspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    try
                    {
                        if (position != 0)
                        {
                            String Select = (String) parent.getItemAtPosition(position);
                            fdperiodunit=periodunitmap.get(Select);
                            if(FDperiodunitspinner.getSelectedItem().toString().trim().equalsIgnoreCase("Both"))
                            {
                                fdperioddays_layout.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                fdperioddays_layout.setVisibility(View.GONE);
                            }

                            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FdRdAccOpeningActivity.this)) {
                                if (NetworkUtil.getConnectivityStatus(FdRdAccOpeningActivity.this)) {
                                    new GetRangeDataAsyncTask(FdRdAccOpeningActivity.this,fdscheme,et_fdeffctivedate.getText().toString(), fdperiodunit,"FD").execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                }
                            } else {
                                TrustMethods.displaySimErrorDialog(FdRdAccOpeningActivity.this);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if(periodunitlist.size() != 0)
        {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(FdRdAccOpeningActivity.this, android.R.layout.simple_spinner_item, periodunitlist);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            RDperiodunitspinner.setAdapter(spinnerArrayAdapter);
            RDperiodunitspinner.setSelection(0);
            RDperiodunitspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    try
                    {
                        if (position != 0)
                        {
                            String Select = (String) parent.getItemAtPosition(position);
                            rdperiodunit=periodunitmap.get(Select);

                            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(FdRdAccOpeningActivity.this)) {
                                if (NetworkUtil.getConnectivityStatus(FdRdAccOpeningActivity.this)) {
                                    new GetRangeDataAsyncTask(FdRdAccOpeningActivity.this,rdscheme,et_RDEffectivedate.getText().toString(),rdperiodunit,"RD").execute();
                                } else {
                                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                                }
                            } else {
                                TrustMethods.displaySimErrorDialog(FdRdAccOpeningActivity.this);
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private class GetRangeDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        private String _error = "";
        private Context ctx;
        private String result;
        private String action="GET_RANGE_DATA";
        JSONObject respTableOne;
        String schemeId, depositDate, periodUnit,tag;
        public GetRangeDataAsyncTask(Context ctx, String schemeId, String depositDate,String periodUnit,String tag) {
            this.ctx=ctx;
            this.schemeId=schemeId;
            this.depositDate=depositDate;
            this.periodUnit=periodUnit;
            this.tag=tag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FdRdAccOpeningActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            calcMethod="";
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);

            JSONObject jObj = new JSONObject();
            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }
            byte[] data = new byte[0];
            try {
                if (periodUnit.equals("6968"))
                    periodUnit = "69";
                jObj.put("schemeid", schemeId);
                jObj.put("deposit_date", depositDate);
                jObj.put("period_unit", periodUnit);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            Log.e("encrypted", base64);
            String jsonString = jObj.toString();// "{\"agent_id\":\"" + GlobalVarHolder.Agent_id + "\", \"payload_base64\":\"" + base64 + "\"}";

            try {
                Log.e("jsonString", jsonString);
                result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, action, AppConstants.getAuth_token());
                Log.e("result", result);
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if (responseCode.equals("1")) {
                    try {
                        respTableOne = jsonObject.getJSONObject("response").getJSONArray("Table").getJSONObject(0);
                    } catch (Exception e) {
                        _error = "Please Select Valid Details1";
                    }
                } else {
                    String errorCode = jsonObject.has("error_code") ? jsonObject.getString("error_code") : "NA";
                    _error = jsonObject.has("error_message") ? jsonObject.getString("error_message") : "NA";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if(!_error.equalsIgnoreCase("")){
                TrustMethods.showSnackBarMessage(this._error, coordinatorLayout);
            }
            else
            {
                try
                {
                    if(tag.equals("FD")) {
                        et_fdDepositperiod.setHint("Range [" + respTableOne.getString("minperiod") + " to " + respTableOne.getString("maxperiod") + "]");
                        et_fdDepositamunt.setHint("Range [" + respTableOne.getString("minamt") + " to " + respTableOne.getString("maxamt") + "]");
                    }
                    else if(tag.equals("RD")){
                        et_RDDepositperiod.setHint("Range [" + respTableOne.getString("minperiod") + " to " + respTableOne.getString("maxperiod") + "]");
                        et_RDinstallmentamount.setHint("Range [" + respTableOne.getString("minamt") + " to " + respTableOne.getString("maxamt") + "]");
                    }
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e);
                }
            }
            if (this._error != "" && !TextUtils.isEmpty(_error)) {
                Toast.makeText(this.ctx, this._error, Toast.LENGTH_SHORT).show();
                LogMessage("error", this._error);
                return;
            }
        }
    }

    private class GetClientName extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        private String _error = "";
        private Context ctx;
        private String result;
        private String action="GET_CLIENT_NAME";
        JSONObject respTableOne;
        String clientId="",nameFor="";
        public GetClientName(Context ctx, String clientId,String nameFor) {
            this.ctx = ctx;
            this.clientId = clientId;
            this.nameFor = nameFor;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FdRdAccOpeningActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url=TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);
            JSONObject jObj=new JSONObject();
            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }
            byte[] data = new byte[0];
            try
            {
                jObj.put("client_id", clientId);
                jObj.put("action", nameFor);
            } catch ( JSONException e) {
                throw new RuntimeException(e);
            }
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            Log.e("encrypted",base64);
            String jsonString = jObj.toString();

            try
            {
                result = HttpClientWrapper.postWithActionAuthToken(url,jsonString,action, AppConstants.getAuth_token());
                Log.e("result",result);
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if(responseCode.equals("1"))
                {
                    try
                    {
                        respTableOne = jsonObject.getJSONObject("response").getJSONArray("Table").getJSONObject(0);
                    }
                    catch(Exception e)
                    {
                        _error = "Please Select Valid Details";
                    }
                } else {
                    String errorCode = jsonObject.has("error_code") ? jsonObject.getString("error_code") : "NA";
                    _error = jsonObject.has("error_message") ? jsonObject.getString("error_message") : "NA";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if(!_error.equalsIgnoreCase("")){
                TrustMethods.showSnackBarMessage(_error, coordinatorLayout);
            } else {
                try {
                    String item = respTableOne.getString("customer_nm");
                    if(item.indexOf("NA~")==-1)
                    {
                        tv_accountname.setText(item);
                    }
                    else
                    {
                        Toast.makeText(FdRdAccOpeningActivity.this, item.split("~")[1], Toast.LENGTH_LONG).show();
                        tv_accountname.setText("");
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            if (this._error != "" && !TextUtils.isEmpty(_error)) {
                TrustMethods.showSnackBarMessage(this._error, coordinatorLayout);
                LogMessage("error", this._error);
                return;
            }
        }
    }
    private void setLookUpSpinners()
    {
        Log.e("","selacc = "+cgltype);
        if(cgltype.equalsIgnoreCase("FD"))
        {
            FDdetails_ll.setVisibility(View.VISIBLE);
            RDdetails_ll.setVisibility(View.GONE);
            transferfreq_ll.setVisibility(View.GONE);
            transfermode_layout.setVisibility(View.GONE);
            accountno_layout.setVisibility(View.GONE);
            periodinterest_layout.setVisibility(View.GONE);
            gl_type="FD";
            if(fdschemelist.size() != 0) {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(FdRdAccOpeningActivity.this, android.R.layout.simple_spinner_item, fdschemelist);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                FDDepositschemespinner.setAdapter(spinnerArrayAdapter);
            }

            ArrayList<String> transferModeList =new ArrayList<>();
            transferModeList.add("Select Transfer Mode");
            transferModeList.add("Transfer To A/C No");
            ArrayAdapter<String> transferModeAdapter = new ArrayAdapter<String>(FdRdAccOpeningActivity.this, android.R.layout.simple_spinner_item, transferModeList);
            transferModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Transfermodespinner.setAdapter(transferModeAdapter);
        }
        else if(cgltype.equalsIgnoreCase("RD"))
        {
            gl_type="RD";
            FDdetails_ll.setVisibility(View.GONE);
            RDdetails_ll.setVisibility(View.VISIBLE);
            transferfreq_ll.setVisibility(View.GONE);
            transfermode_layout.setVisibility(View.GONE);
            accountno_layout.setVisibility(View.GONE);
            periodinterest_layout.setVisibility(View.GONE);
            if(rdschemelist.size() != 0) {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(FdRdAccOpeningActivity.this, android.R.layout.simple_spinner_item, rdschemelist);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                RDDepositschemespinner.setAdapter(spinnerArrayAdapter);
            }
        }

        if(transferfreqlist.size() != 0)
        {
            ArrayAdapter<String> transferfreqencyArrayAdapter = new ArrayAdapter<String>(FdRdAccOpeningActivity.this, android.R.layout.simple_spinner_item, transferfreqlist);
            transferfreqencyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Transferfreqspinner.setAdapter(transferfreqencyArrayAdapter);
            Transferfreqspinner.setSelection(0);
            Transferfreqspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        if (position != 0) {
                            String Select = (String) parent.getItemAtPosition(position);
                            transferfreq=transferfreqmap.get(Select);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void Save() {
        if (validate()) {
            StringWriter writer = new StringWriter();

            try {
                // Create an XmlSerializer
                XmlSerializer xmlSerializer = Xml.newSerializer();
                xmlSerializer.setOutput(writer);
                // Start document with the given encoding and standalone value
                xmlSerializer.startDocument("UTF-8", true);
                // Start the root element
                xmlSerializer.startTag("", "data");
                // Start account_details
                xmlSerializer.startTag("", "account_details");

                // Write branch_id
                xmlSerializer.startTag("", "branch_id");
                xmlSerializer.text(branchId);
                xmlSerializer.endTag("", "branch_id");
                // Write gl_type
                xmlSerializer.startTag("", "gl_type");
                xmlSerializer.text(cgltype);
                xmlSerializer.endTag("", "gl_type");
                // Write mode_of_operation
                xmlSerializer.startTag("", "mode_of_operation");
                xmlSerializer.text(modeOfOperation);//todo
                xmlSerializer.endTag("", "mode_of_operation");

                if(gl_type.equalsIgnoreCase("FD")) {
                    String fdEffectiveDate = et_fdeffctivedate.getText().toString();
                    String fdDepositPeriod = et_fdDepositperiod.getText().toString();
                    String fdDepositPeriodDays = et_fdDepositperioddays.getText().toString();
                    String fdDepositAmount = et_fdDepositamunt.getText().toString();
                    String fdInterestRate = et_fdinterestrate.getText().toString();
                    String fdCertificateNo = et_fdcertificatenumber.getText().toString();

                    String fdExtraInterestRate = et_fdextrainterestrate.getText().toString();
                    String fdInterestAmount = et_fdinterestamount.getText().toString();
                    String fdMaturityDate = fd_maturitydate.getText().toString();
                    String fdMaturityAmount = et_fdmaturityamt.getText().toString();
                    String transferAccount = et_Transfertoaccount.getText().toString();
                    String periodInterestAmount = et_periodinterestamt.getText().toString();
                    //Start fd_details
                    xmlSerializer.startTag("", "fd_details");

                    //deposit_scheme
                    xmlSerializer.startTag("", "deposit_scheme");
                    xmlSerializer.text(fdscheme);
                    xmlSerializer.endTag("", "deposit_scheme");

                    //effective_date
                    xmlSerializer.startTag("", "effective_date");
                    xmlSerializer.text(fdEffectiveDate);
                    xmlSerializer.endTag("", "effective_date");

                    //period_unit
                    xmlSerializer.startTag("", "period_unit");
                    xmlSerializer.text(fdperiodunit);
                    xmlSerializer.endTag("", "period_unit");

                    //deposit_period
                    xmlSerializer.startTag("", "deposit_period");
                    xmlSerializer.text(fdDepositPeriod);
                    xmlSerializer.endTag("", "deposit_period");

                    //deposit_period_days
                    xmlSerializer.startTag("", "deposit_period_days");
                    xmlSerializer.text(fdDepositPeriodDays);
                    xmlSerializer.endTag("", "deposit_period_days");

                    //deposit_amount
                    xmlSerializer.startTag("", "deposit_amount");
                    xmlSerializer.text(fdDepositAmount);
                    xmlSerializer.endTag("", "deposit_amount");

                    //interest_rate
                    xmlSerializer.startTag("", "interest_rate");
                    xmlSerializer.text(fdInterestRate);
                    xmlSerializer.endTag("", "interest_rate");

                    //certificate_number
                    xmlSerializer.startTag("", "certificate_number");
                    xmlSerializer.text(fdCertificateNo);
                    xmlSerializer.endTag("", "certificate_number");

                    //transfer_to_account
                    xmlSerializer.startTag("", "transfer_to_account");
                    xmlSerializer.text(transferAccount);
                    xmlSerializer.endTag("", "transfer_to_account");

                    //is_allow_renewable
                    xmlSerializer.startTag("", "is_allow_renewable");
                    xmlSerializer.text(is_renewable);
                    xmlSerializer.endTag("", "is_allow_renewable");

                    //extra_interest_rate
                    xmlSerializer.startTag("", "extra_interest_rate");
                    xmlSerializer.text(fdExtraInterestRate);
                    xmlSerializer.endTag("", "extra_interest_rate");

                    //interest_amount
                    xmlSerializer.startTag("", "interest_amount");
                    xmlSerializer.text(fdInterestAmount);
                    xmlSerializer.endTag("", "interest_amount");

                    //maturity_date
                    xmlSerializer.startTag("", "maturity_date");
                    xmlSerializer.text(fdMaturityDate);
                    xmlSerializer.endTag("", "maturity_date");

                    //maturity_amount
                    xmlSerializer.startTag("", "maturity_amount");
                    xmlSerializer.text(fdMaturityAmount);
                    xmlSerializer.endTag("", "maturity_amount");

                    //transfer_frequency
                    xmlSerializer.startTag("", "transfer_frequency");
                    xmlSerializer.text(transferfreq);
                    xmlSerializer.endTag("", "transfer_frequency");

                    //transfer_mode
                    xmlSerializer.startTag("", "transfer_mode");
                    xmlSerializer.text(transferMode);
                    xmlSerializer.endTag("", "transfer_mode");

                    //ben_ifsc_code
                    xmlSerializer.startTag("", "ben_ifsc_code");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "ben_ifsc_code");

                    //ben_account_no
                    xmlSerializer.startTag("", "ben_account_no");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "ben_account_no");

                    //ben_add
                    xmlSerializer.startTag("", "ben_add");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "ben_add");

                    //ben_name
                    xmlSerializer.startTag("", "ben_name");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "ben_name");

                    //ben_remark
                    xmlSerializer.startTag("", "ben_remark");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "ben_remark");

                    //pay_frequency
                    xmlSerializer.startTag("", "pay_frequency");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "pay_frequency");

                    //period_interest_amount
                    xmlSerializer.startTag("", "period_interest_amount");
                    xmlSerializer.text(periodInterestAmount);
                    xmlSerializer.endTag("", "period_interest_amount");

                    //calc_method
                    xmlSerializer.startTag("", "calc_method");
                    xmlSerializer.text(calcMethod);
                    xmlSerializer.endTag("", "calc_method");

                    //is_allow_int_renewable
                    xmlSerializer.startTag("", "is_allow_int_renewable");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "is_allow_int_renewable");

                    //End fd_details
                    xmlSerializer.endTag("", "fd_details");
                }
                else if(gl_type.equalsIgnoreCase("RD"))
                {
                    String rdEffectiveDate = et_RDEffectivedate.getText().toString();
                    String rdDepositPeriod = et_RDDepositperiod.getText().toString();
                    String rdInstallmentAmount = et_RDinstallmentamount.getText().toString();
                    String rdInterestRate = et_RDinterest_rate.getText().toString();
                    String rdExtraInterestRate = et_rdextrainterestrate.getText().toString();
                    String rdInterestAmount = et_rdinterestamount.getText().toString();
                    String rdMaturityDate = rd_maturitydate.getText().toString();
                    String rdMaturityAmount = et_rdmaturityamount.getText().toString();
                    String rdDueRate = rd_duedate.getText().toString();

                    // Start RD
                    xmlSerializer.startTag("", "rd_details");

                    xmlSerializer.startTag("", "deposit_scheme");
                    xmlSerializer.text(rdscheme);
                    xmlSerializer.endTag("", "deposit_scheme");

                    xmlSerializer.startTag("", "effective_date");
                    xmlSerializer.text(rdEffectiveDate);
                    xmlSerializer.endTag("", "effective_date");

                    xmlSerializer.startTag("", "period_unit");
                    xmlSerializer.text(rdperiodunit);
                    xmlSerializer.endTag("", "period_unit");

                    xmlSerializer.startTag("", "deposit_period");
                    xmlSerializer.text(rdDepositPeriod);
                    xmlSerializer.endTag("", "deposit_period");

                    xmlSerializer.startTag("", "installment_amount");
                    xmlSerializer.text(rdInstallmentAmount);
                    xmlSerializer.endTag("", "installment_amount");

                    xmlSerializer.startTag("", "interest_rate");
                    xmlSerializer.text(rdInterestRate);
                    xmlSerializer.endTag("", "interest_rate");

                    xmlSerializer.startTag("", "transfer_frequency");
                    xmlSerializer.text(transferfreq);
                    xmlSerializer.endTag("", "transfer_frequency");

                    xmlSerializer.startTag("", "transfer_to_account");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "transfer_to_account");

                    //-----------------------------------Added By SSP

                    xmlSerializer.startTag("", "due_date");
                    xmlSerializer.text(rdDueRate);
                    xmlSerializer.endTag("", "due_date");

                    xmlSerializer.startTag("", "extra_interest_rate");
                    xmlSerializer.text(rdExtraInterestRate);
                    xmlSerializer.endTag("", "extra_interest_rate");

                    xmlSerializer.startTag("", "maturity_date");
                    xmlSerializer.text(rdMaturityDate);
                    xmlSerializer.endTag("", "maturity_date");

                    xmlSerializer.startTag("", "maturity_amount");
                    xmlSerializer.text(rdMaturityAmount);
                    xmlSerializer.endTag("", "maturity_amount");

                    xmlSerializer.startTag("", "interest_amount");
                    xmlSerializer.text(rdInterestAmount);
                    xmlSerializer.endTag("", "interest_amount");

                    xmlSerializer.startTag("", "installment_frequency");
                    xmlSerializer.text("71");
                    xmlSerializer.endTag("", "installment_frequency");

                    xmlSerializer.endTag("", "rd_details");
                    //end rd
                }

                //End of Account details
                xmlSerializer.endTag("", "account_details");

                //Start customer_details
                xmlSerializer.startTag("", "customer_details");

                xmlSerializer.startTag("", "customer_id");
                xmlSerializer.text(AppConstants.getCLIENTID());
                xmlSerializer.endTag("", "customer_id");

                //End customer_details
                xmlSerializer.endTag("", "customer_details");

                //Start joint_details
                xmlSerializer.startTag("", "joint_details");

                if(jointList!=null) {
                    for (int i = 0; i < jointList.size(); i++) {

                        // Start joint
                        xmlSerializer.startTag("", "joint");
                        // Start customer_details
                        xmlSerializer.startTag("", "customer_details");
                        // Write customer_id
                        xmlSerializer.startTag("", "customer_id");
                        xmlSerializer.text(jointList.get(i).getClientId());
                        xmlSerializer.endTag("", "customer_id");
                        // End customer_details inside joint
                        xmlSerializer.endTag("", "customer_details");
                        // End joint
                        xmlSerializer.endTag("", "joint");
                    }
                }

                //End customer_details
                xmlSerializer.endTag("", "joint_details");

                // End the root element
                xmlSerializer.endTag("", "data");

                // End the document
                xmlSerializer.endDocument();

            }catch(Exception e){
                e.printStackTrace();
            }

            String xmlString = writer.toString();
            Log.e("xmlString", xmlString);

            String amount="";
            if(cgltype.equals("FD")){
                amount = et_fdDepositamunt.getText().toString();;
            }else{
                amount = et_RDinstallmentamount.getText().toString();
            }

            Intent intent = new Intent(FdRdAccOpeningActivity.this, OtpVerificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("checkTransferType", "RdFdAccOpening");
            intent.putExtra("xmlString", xmlString);
            intent.putExtra("from_accountNo", from_accountNo);
            intent.putExtra("cgltype", cgltype);
            intent.putExtra("amount", amount);
            startActivity(intent);
        }
    }

    private boolean validate()
    {
        if (gl_type.equals("FD") && FDDepositschemespinner.getSelectedItemPosition()==0) {
            TrustMethods.showSnackBarMessage("Please Select Deposit Scheme", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && TextUtils.isEmpty(et_fdeffctivedate.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Please Enter FD Effective Date", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && FDperiodunitspinner.getSelectedItemPosition()==0) {
            TrustMethods.showSnackBarMessage("Please Select FD Period Unit", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && TextUtils.isEmpty(et_fdDepositperiod.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Please Enter FD Deposit Period", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && Integer.parseInt(et_fdDepositperiod.getText().toString().trim())<=0) {
            TrustMethods.showSnackBarMessage( "Please Enter Valid FD Deposit Period", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && TextUtils.isEmpty(et_fdDepositamunt.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Please Enter FD Deposit Amount", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && Double.parseDouble(et_fdDepositamunt.getText().toString().trim())<=0) {
            TrustMethods.showSnackBarMessage("Please Enter Valid FD Deposit Amount", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && TextUtils.isEmpty(et_fdinterestrate.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage( "Please Enter FD Interest Rate", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && transferToAccount.equals("1") && Transferfreqspinner.getSelectedItemPosition()==0) {
            TrustMethods.showSnackBarMessage( "Please Select Transfer Frequency", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && transferToAccount.equals("1") && Transfermodespinner.getSelectedItemPosition()==0) {
            TrustMethods.showSnackBarMessage("Please Select Transfer Mode", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && transferToAccount.equals("1") && TextUtils.isEmpty(et_Transfertoaccount.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Please Enter Account No to Transfer", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && transferToAccount.equals("1") && !TextUtils.isEmpty(et_Transfertoaccount.getText().toString().trim()) && TextUtils.isEmpty(tv_accountname.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage( "Please Validate Account No to Transfer", coordinatorLayout);
            return false;
        } else if (gl_type.equals("FD") && Double.parseDouble(balance) < Double.parseDouble(et_fdDepositamunt.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Insufficient account balance", coordinatorLayout);
            return false;
        } else if (gl_type.equals("RD") && RDDepositschemespinner.getSelectedItemPosition()==0) {
            TrustMethods.showSnackBarMessage( "Please Select Deposit Scheme", coordinatorLayout);
            return false;
        } else if (gl_type.equals("RD") && TextUtils.isEmpty(et_RDEffectivedate.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Please Enter RD Effective Date",coordinatorLayout);
            return false;
        } else if (gl_type.equals("RD") && RDperiodunitspinner.getSelectedItemPosition()==0) {
            TrustMethods.showSnackBarMessage("Please Select RD Period Unit", coordinatorLayout);
            return false;
        } else if (gl_type.equals("RD") && TextUtils.isEmpty(et_RDDepositperiod.getText().toString().trim())) {
           TrustMethods.showSnackBarMessage("Please Enter RD Deposit Period", coordinatorLayout);
            return false;
        } else if (gl_type.equals("RD") && Integer.parseInt(et_RDDepositperiod.getText().toString().trim())<=0) {
            TrustMethods.showSnackBarMessage( "Please Enter Valid RD Deposit Period", coordinatorLayout);
            return false;
        } else if (gl_type.equals("RD") && TextUtils.isEmpty(et_RDinstallmentamount.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Please Enter RD Installment Amount", coordinatorLayout);
            return false;
        } else if (gl_type.equals("RD") && Double.parseDouble(et_RDinstallmentamount.getText().toString().trim())<=0) {
            TrustMethods.showSnackBarMessage( "Please Enter Valid RD Installment Amount", coordinatorLayout);
            return false;
        } else if (gl_type.equals("RD") && TextUtils.isEmpty(et_RDinterest_rate.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Please Enter RD Interest Rate", coordinatorLayout);
            return false;
        }else if (gl_type.equals("RD") && Double.parseDouble(balance) < Double.parseDouble(et_RDinstallmentamount.getText().toString().trim())) {
            TrustMethods.showSnackBarMessage("Insufficient account balance", coordinatorLayout);
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(FdRdAccOpeningActivity.this, FrmClientManagementActivity.class);
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
        TrustMethods.showBackButtonAlert(FdRdAccOpeningActivity.this);
    }
}