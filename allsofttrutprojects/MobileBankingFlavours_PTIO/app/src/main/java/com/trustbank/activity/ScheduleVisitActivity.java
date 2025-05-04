package com.trustbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogListener;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ScheduleVisitActivity extends AppCompatActivity implements View.OnClickListener , AlertDialogListener, AlertDialogOkListener {

    EditText et_name,et_email,et_contact,editText_remarks;
    Spinner branch_spinner;
    TextView editText_Date,editText_Timing;
    AlertDialogListener alertDialogListener=this;
    TrustMethods methods;
    Button btn_continue;
    private AlertDialogOkListener alertDialogOkListener = this;
    String productvalue,name,email,contact,date,time,remarks,branchid;


    private TextView toolbar;
    private ImageView backButton_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(ScheduleVisitActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(ScheduleVisitActivity.this, false);
        setContentView(R.layout.activity_schedule_visit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.ScheduleVisit);
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
        methods=new TrustMethods(ScheduleVisitActivity.this);

        backButton_new = findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);

        editText_Date=findViewById(R.id.editText_Date);
        editText_Date.setOnClickListener(this);
        editText_Timing=findViewById(R.id.editText_Timing);
        editText_Timing.setOnClickListener(this);
        branch_spinner=findViewById(R.id.product_spinner);
        et_name=findViewById(R.id.et_name);
        editText_remarks=findViewById(R.id.editText_remarks);
        et_email=findViewById(R.id.et_email);
        et_contact=findViewById(R.id.et_contact);
        btn_continue=findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);
        et_name.setText(AppConstants.getUSERNAME());
        et_email.setText(AppConstants.getUSEREMAILADDRESS());
        et_contact.setText(AppConstants.getMobileno());
        spinnerInit();
    }

    private void spinnerInit() {
        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(ScheduleVisitActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(ScheduleVisitActivity.this)) {
                new AsyncTaskGetSavinglookups(ScheduleVisitActivity.this).execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            TrustMethods.displaySimErrorDialog(ScheduleVisitActivity.this);
        }
    }


    @Override
    public void onBackPressed() {
        TrustMethods.showBackButtonAlert(ScheduleVisitActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backButton_new:
                for(DynamicMenuModel menuModel : AppConstants.getParentlist()) {
                    if (menuModel.getMenucode().equalsIgnoreCase("mnu_schedule_visit")) {
                        Intent intent = new Intent(ScheduleVisitActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //finish();
                    }
                }
                Intent intent = new Intent(ScheduleVisitActivity.this, VisitUsMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
                break;

            case R.id.editText_Date:
                methods.datePickerymd(ScheduleVisitActivity.this,editText_Date);
                break;
            case R.id.editText_Timing:
                methods.timePicker(ScheduleVisitActivity.this,editText_Timing);
                break;
            case R.id.btn_continue:
                save();
                break;
        }
    }

    private void save() {
        name=et_name.getText().toString();
        email=et_email.getText().toString();
        contact=et_contact.getText().toString();
        date=editText_Date.getText().toString();
        time=editText_Timing.getText().toString();
        remarks=editText_remarks.getText().toString();

        AlertDialogMethod.alertDialog(ScheduleVisitActivity.this, "",getResources().getString(R.string.appointment)+"\n" +
                getResources().getString(R.string.WithPTIOat)+" "+time+" on "+date+"."+" "+getResources().getString(R.string.ConAppointment)+"\n " +
                getResources().getString(R.string.CANCELTocancel),getResources().getString(R.string.Confirm),getResources().getString(R.string.btn_cancel),1,true,alertDialogListener);

    }

    @Override
    public void onDialogOk(int resultCode) {
        if(resultCode==1){
            new AsyncTaskCreateSchedule(ScheduleVisitActivity.this,name,email,contact,branchid,date,time,remarks).execute();
        }
        else if(resultCode==2){
            Intent intent = new Intent(ScheduleVisitActivity.this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onDialogCancel(int resultCode) {
        if(resultCode==1){

        }
    }
    private class AsyncTaskGetSavinglookups extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        TrustMethods methods;
        JSONArray data;
        JSONArray branches;
        String result;
        List<String> branchlist;
        HashMap<String,String> branchmap;
        String actionName ="GET_SAVING_LOOKUPS";

        public AsyncTaskGetSavinglookups(Context ctx) {
            this.error = "";
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetCatalogue();
                if (!url.equals("")) {
                    result = HttpClientWrapper.getResponseGET(url, actionName, AppConstants.getAuth_token());
                }
                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));
                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {
                    data = jsonResponse.getJSONObject("response").getJSONArray("data");
                    if (data.length() == 0) {
                        error = AppConstants.NO_RECORDS_FOUND;
                        return error;
                    }
                    if (data != null && data.length() > 0) {

                        branches = jsonResponse.getJSONObject("response").getJSONArray("Table2");
                        branchlist = new ArrayList<>();
                        branchmap = new HashMap<>();
                        branchlist.add(0,getResources().getString(R.string.SelectBranchType));
                        if (branches != null && branches.length() > 0) {
                            for (int i = 0; i < branches.length(); i++) {
                                JSONObject JsonObject = branches.getJSONObject(i);
                                //IdentificationType
                                String schemeidID = JsonObject.has("valueid") ? JsonObject.getString("valueid") : "NA";
                                String schemename = JsonObject.has("branchname") ? JsonObject.getString("branchname") : "NA";
                                branchlist.add(schemename);
                                branchmap.put(schemename, schemeidID);
                            }
                        }
                    } else {
                        String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (this.error != "") {
                methods.message(this.ctx, error);
                return;
            }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, branchlist);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            branch_spinner.setAdapter(adapter1);
            branch_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){
                        String selectedbrach = (String) parent.getItemAtPosition(position);
                        branchid=branchmap.get(selectedbrach);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    private class AsyncTaskCreateSchedule extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        String response;
        JSONArray data;
        String result,name,email,contact,date,time,branch,remarks;
        String actionName ="SCHEDULE_VISIT";

        public AsyncTaskCreateSchedule(Context ctx, String name, String email, String contact,String branch, String date, String time,String remarks) {

            this.error = "";
            this.ctx = ctx;
            this.name = name;
            this.email = email;
            this.contact = contact;
            this.branch = branch;
            this.date = date;
            this.time = time;
            this.remarks = remarks;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ScheduleVisitActivity.this);
            pDialog.setMessage(getResources().getText(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetSchedule(name,email,contact,branch,date,time,AppConstants.getCLIENTID(),remarks);
                if (!url.equals("")) {
                    result = HttpClientWrapper.getResponseGET(url, actionName, AppConstants.getAuth_token());
                }
                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));
                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {

                }else {
                    String errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                    error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (this.error != "") {
                AlertDialogMethod.alertDialogOk(ScheduleVisitActivity.this, " ", this.error, getResources().getString(R.string.btn_ok), 2, false, alertDialogOkListener);
                return;
            }
            Intent i=new Intent(ScheduleVisitActivity.this,SuccessSplashActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("Title",getResources().getString(R.string.AppointmentScheduled));
            i.putExtra("Activity", "ScheduleVisitActivity");
            startActivity(i);
        }
    }

}
