package com.trustbank.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.trustbank.Model.AccountDetailsModel;
import com.trustbank.Model.BBPSCategoryModel;
import com.trustbank.Model.BBPSComplaintHistoryModelFinacus;
import com.trustbank.Model.BBPSCoverageModel;
import com.trustbank.Model.BBPSCustomeParamater;
import com.trustbank.Model.BBPSCustomerParamFinacusModel;
import com.trustbank.Model.GenerateStanRRNModel;
import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.tcpconnection.tdto.MessageDtoBuilder;
import com.trustbank.tcpconnection.tmessage.TMessage;
import com.trustbank.tcpconnection.tmessage.TMessageUtil;
import com.trustbank.tcpconnection.util.ResponseEntity;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BBPSBillerCategoryFinacusActivity extends AppCompatActivity implements AlertDialogOkListener {

    private String TAG = BBPSBillerCategoryFinacusActivity.class.getSimpleName();
    RecyclerView recyclerViewHoriListId;
    private TrustMethods method;
    Spinner sp_coverage, sp_categories,sp_favorites;
    CoordinatorLayout searchbillerCoordinatorLayoutId;
    AlertDialogOkListener alertDialogOkListener = this;
    HashMap<String, String> coverageMap, categoryMap, billerMap;
    String coverageId, billerId;
    EditText et_cust_param1, et_cust_param2, et_cust_param3, et_cust_param4, et_cust_param5,et_nickname;
    LinearLayout ll_biller_category, ll_biller_name,ll_fav;
    TextInputLayout ll_cust_param1, ll_cust_param2, ll_cust_param3, ll_cust_param4, ll_cust_param5,ll_nickname;
    Button btn_search;
    List<BBPSCustomerParamFinacusModel> bbpsCustomerParamsList = new ArrayList<>();
    TextView txt_biller_name;
    List<String> bbpsBillerList = null;
    ImageView iv_removefav;
    String billerName;
    CheckBox ck_Addtofav;
    boolean duplicatedata=false;
    boolean fav=false;
    String selectNM;
    List<String> loadfav;
    int count=0;

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
                        TrustMethods.naviagteToSplashScreen(BBPSBillerCategoryFinacusActivity.this);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetTheme.changeToTheme(BBPSBillerCategoryFinacusActivity.this, false);
        setContentView(R.layout.activity_bbpsbiller_category_finacus);

        initCompnonet();
    }

    private void initCompnonet() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        method = new TrustMethods(BBPSBillerCategoryFinacusActivity.this);
        sp_coverage= findViewById(R.id.sp_coverage);
        searchbillerCoordinatorLayoutId= findViewById(R.id.searchbillerCoordinatorLayoutId);
        sp_categories= findViewById(R.id.sp_categories);
        txt_biller_name= findViewById(R.id.txt_biller_name);
        et_cust_param1= findViewById(R.id.et_cust_param1);
        ll_biller_category= findViewById(R.id.ll_biller_category);
        ll_biller_name= findViewById(R.id.ll_biller_name);
        ll_cust_param1= findViewById(R.id.ll_cust_param1);
        btn_search= findViewById(R.id.btn_search);
        ll_cust_param2= findViewById(R.id.ll_cust_param2);
        ll_cust_param3= findViewById(R.id.ll_cust_param3);
        ll_cust_param4= findViewById(R.id.ll_cust_param4);
        ll_cust_param5= findViewById(R.id.ll_cust_param5);
        et_cust_param2= findViewById(R.id.et_cust_param2);
        et_cust_param3= findViewById(R.id.et_cust_param3);
        et_cust_param4= findViewById(R.id.et_cust_param4);
        et_cust_param5= findViewById(R.id.et_cust_param5);
        ck_Addtofav= findViewById(R.id.ck_Addtofav);
        et_nickname= findViewById(R.id.et_nickname);
        ll_nickname= findViewById(R.id.ll_nickname);
        sp_favorites= findViewById(R.id.sp_favorites);
        iv_removefav= findViewById(R.id.iv_removefav);
        ll_fav= findViewById(R.id.ll_fav);

        ll_biller_category.setVisibility(View.GONE);
        ll_biller_name.setVisibility(View.GONE);
        ll_cust_param1.setVisibility(View.GONE);
        btn_search.setVisibility(View.GONE);
        ll_cust_param2.setVisibility(View.GONE);
        ll_cust_param3.setVisibility(View.GONE);
        ll_cust_param4.setVisibility(View.GONE);
        ll_cust_param5.setVisibility(View.GONE);
        ll_nickname.setVisibility(View.GONE);
        ck_Addtofav.setVisibility(View.GONE);

        ck_Addtofav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll_nickname.setVisibility(View.VISIBLE);
                } else {
                    ll_nickname.setVisibility(View.GONE);
                }
            }
        });

        horizontalRecyclerView();
        loadBBPSFav();

        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(BBPSBillerCategoryFinacusActivity.this)) {
            if (NetworkUtil.getConnectivityStatus(BBPSBillerCategoryFinacusActivity.this)) {
                new FetchCovergaeAsyncTask(BBPSBillerCategoryFinacusActivity.this).execute();
            } else {
                TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), searchbillerCoordinatorLayoutId);
            }
        } else {
            TrustMethods.displaySimErrorDialog(BBPSBillerCategoryFinacusActivity.this);
        }

        iv_removefav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1 = new Dialog(BBPSBillerCategoryFinacusActivity.this);
                dialog1.setContentView(R.layout.remove_bbps_fav);
                ListView removelist =dialog1.findViewById(R.id.removelist);

                ArrayAdapter<String> arr1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,loadfav );
                removelist.setAdapter(arr1);
                dialog1.show();
                removelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(position!=0){
                            String fAVName = parent.getItemAtPosition(position).toString();
                            //Toast.makeText(BBPSBillerCategoryFinacusActivity.this, billerName, Toast.LENGTH_SHORT).show();
                            //dialog.dismiss();
                            try {

                                AlertDialog.Builder builder = new AlertDialog.Builder(BBPSBillerCategoryFinacusActivity.this);
                                builder.setMessage(fAVName);
                                builder.setTitle("Are you sure to remove favorites");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                                   /* loadfav.remove(position);
                                    arr1.notifyDataSetChanged();*/
                                    new DeleteFavAsyncTask(BBPSBillerCategoryFinacusActivity.this,fAVName).execute();
                                    dialog.dismiss();
                                    dialog1.dismiss();
                                    Intent intent =new Intent(BBPSBillerCategoryFinacusActivity.this,MenuActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    method.activityCloseAnimation();
                                });

                                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                                    dialog.cancel();
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        });

        sp_coverage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    txt_biller_name.setText("");
                    if(pos!=0) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        String coverageName = (String) adapterView.getItemAtPosition(pos);
                        coverageId = coverageMap.get(coverageName);
                        new FetchBillerCategoryAsyncTask(BBPSBillerCategoryFinacusActivity.this, coverageId).execute();
                        ll_biller_name.setVisibility(View.GONE);
                        ll_cust_param1.setVisibility(View.GONE);
                        btn_search.setVisibility(View.GONE);
                        ll_cust_param2.setVisibility(View.GONE);
                        ll_cust_param3.setVisibility(View.GONE);
                        ll_cust_param4.setVisibility(View.GONE);
                        ll_cust_param5.setVisibility(View.GONE);
                    }else {
                        ll_biller_category.setVisibility(View.GONE);
                        ll_biller_name.setVisibility(View.GONE);
                        ll_cust_param1.setVisibility(View.GONE);
                        btn_search.setVisibility(View.GONE);
                        ll_cust_param2.setVisibility(View.GONE);
                        ll_cust_param3.setVisibility(View.GONE);
                        ll_cust_param4.setVisibility(View.GONE);
                        ll_cust_param5.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sp_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    txt_biller_name.setText("");
                    if(pos!=0){
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        String category = (String) adapterView.getItemAtPosition(pos);
                        String categoryId = categoryMap.get(category);
                        new FetchBillerDetailAsyncTask(BBPSBillerCategoryFinacusActivity.this, categoryId, coverageId).execute();

                        ll_cust_param1.setVisibility(View.GONE);
                        btn_search.setVisibility(View.GONE);
                        ll_cust_param2.setVisibility(View.GONE);
                        ll_cust_param3.setVisibility(View.GONE);
                        ll_cust_param4.setVisibility(View.GONE);
                        ll_cust_param5.setVisibility(View.GONE);
                    }else {
                        ll_biller_name.setVisibility(View.GONE);
                        ll_cust_param1.setVisibility(View.GONE);
                        btn_search.setVisibility(View.GONE);
                        ll_cust_param2.setVisibility(View.GONE);
                        ll_cust_param3.setVisibility(View.GONE);
                        ll_cust_param4.setVisibility(View.GONE);
                        ll_cust_param5.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        txt_biller_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cust_param1 = et_cust_param1.getText().toString();
                String cust_param2 = et_cust_param2.getText().toString();
                String cust_param3 = et_cust_param3.getText().toString();
                String cust_param4 = et_cust_param4.getText().toString();
                String cust_param5 = et_cust_param5.getText().toString();
                billerName = txt_biller_name.getText().toString();
                if (billerName.equals("")) {
                    txt_biller_name.setError("Select Biller Name");
                }else if (TextUtils.isEmpty(cust_param1)) {
                    et_cust_param1.setError("Enter Number");
                }else {
                    if(ck_Addtofav.isChecked()){
                        if(!TextUtils.isEmpty(et_nickname.getText().toString())){
                            String fav="1";//todo
                            new ADDtoFavAsyncTask(BBPSBillerCategoryFinacusActivity.this, sp_coverage.getSelectedItem().toString(),sp_categories.getSelectedItem().toString(),txt_biller_name.getText().toString(), cust_param1, cust_param2, cust_param3, cust_param4, cust_param5,et_nickname.getText().toString()).execute();
                        }else{
                            et_nickname.setError("Please Enter NickName To Add as Favorites");
                        }
                    }else{
                        new CustomerDetailAsyncTask(BBPSBillerCategoryFinacusActivity.this, billerId, bbpsCustomerParamsList, cust_param1, cust_param2, cust_param3, cust_param4, cust_param5, sp_categories.getSelectedItem().toString(), billerName).execute();

                    }
                }

            }
        });
    }
    private class DeleteFavAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "BBPS_ADDFAV";
        String Coverage;
        String Category;
        String Billername;
        String custParam1;
        String custParam2;
        String custParam3;
        String custParam4;
        String custParam5;
        String nickname;
        String result;
        private String errorCode;

        public DeleteFavAsyncTask(Context ctx,String nickname) {
            this.ctx = ctx;
            this.nickname = nickname;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerCategoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.removeBBPSADDTOFAV(AppConstants.getCLIENTID(),"","","","","","","","",nickname);

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
                    response="Favorites Removed Successfully";
                } else {
                    errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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
            super.onPostExecute(value);
            try {
                pDialog.dismiss();
                if (!this.error.equals("")) {
                    Toast.makeText(ctx,error, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx,response , Toast.LENGTH_SHORT).show();
                    new CustomerDetailAsyncTask(BBPSBillerCategoryFinacusActivity.this, billerId, bbpsCustomerParamsList, custParam1, custParam2, custParam3, custParam4, custParam5, sp_categories.getSelectedItem().toString(), billerName).execute();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void loadBBPSFav() {
        loadfav=method.getBBpsfavList(BBPSBillerCategoryFinacusActivity.this,"BBPSFavlist");
        if(loadfav != null && loadfav.size()>0){
            ll_fav.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSBillerCategoryFinacusActivity.this, android.R.layout.simple_spinner_item, loadfav);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_favorites.setAdapter(adapter);
        }else{
            ll_fav.setVisibility(View.GONE);
        }


        sp_favorites.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    if(pos!=0){
                        fav=true;
                        ck_Addtofav.setVisibility(View.GONE);
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                        selectNM = (String) adapterView.getItemAtPosition(pos);
                        new GetFavAsyncTask(BBPSBillerCategoryFinacusActivity.this,selectNM).execute();
                    }else{
                        fav=false;
                        sp_coverage.setSelection(0);
                        //ck_Addtofav.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private class GetFavAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "BBPS_GETFAV";
        String nickname;
        String coverage;
        String biller_category;
        String biller_name;
        String result;
        String param1,param2,param3,param4,param5;
        private String errorCode;

        public GetFavAsyncTask(Context ctx, String nickname) {
            this.ctx = ctx;
            this.nickname = nickname;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerCategoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getBBPSADDTOFAV(AppConstants.getCLIENTID(),nickname);

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
                    JSONArray accountJsonArray = jsonResponse.getJSONObject("response").getJSONArray("Table");
                    JSONObject accountJsonObject = accountJsonArray.getJSONObject(0);

                    coverage = accountJsonObject.has("coverage") ? accountJsonObject.getString("coverage") : "";
                    biller_category = accountJsonObject.has("biller_category") ? accountJsonObject.getString("biller_category") : "";
                    biller_name = accountJsonObject.has("biller_name") ? accountJsonObject.getString("biller_name") : "";
                    param1 = accountJsonObject.has("param1") ? accountJsonObject.getString("param1") : "";
                    param2 = accountJsonObject.has("param2") ? accountJsonObject.getString("param2") : "";
                    param3 = accountJsonObject.has("param3") ? accountJsonObject.getString("param3") : "";
                    param4 = accountJsonObject.has("param4") ? accountJsonObject.getString("param4") : "";
                    param5 = accountJsonObject.has("param5") ? accountJsonObject.getString("param5") : "";


                } else {
                    errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (!this.error.equals("")) {
                    if (error.equalsIgnoreCase("auth token expired.")) {
                        AlertDialogMethod.alertDialogOk(BBPSBillerCategoryFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(BBPSBillerCategoryFinacusActivity.this, this.error, "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    }
                }else{
                    sp_coverage.setSelection(((ArrayAdapter<String>)sp_coverage.getAdapter()).getPosition(coverage));
                    sp_categories.setSelection(((ArrayAdapter<String>)sp_categories.getAdapter()).getPosition(biller_category));
                    txt_biller_name.setText(biller_name);
                    billerId = billerMap.get(biller_name);
                    if(!TextUtils.isEmpty(billerId)){
                        new FetchCustomerParamsAsyncTask(BBPSBillerCategoryFinacusActivity.this, billerId).execute();
                    }
                    et_cust_param1.setText(param1);
                    et_cust_param2.setText(param2);
                    et_cust_param3.setText(param3);
                    et_cust_param4.setText(param4);
                    et_cust_param5.setText(param5);
                    btn_search.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class ADDtoFavAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String actionName = "BBPS_ADDFAV";
        String Coverage;
        String Category;
        String Billername;
        String custParam1;
        String custParam2;
        String custParam3;
        String custParam4;
        String custParam5;
        String nickname;
        String result;
        private String errorCode;

        public ADDtoFavAsyncTask(Context ctx, String Coverage, String Category, String Billername, String custParam1, String custParam2, String custParam3, String custParam4, String custParam5,String nickname) {
            this.ctx = ctx;
            this.Coverage = Coverage;
            this.Category = Category;
            this.Billername = Billername;
            this.custParam1 = custParam1;
            this.custParam2 = custParam2;
            this.custParam3 = custParam3;
            this.custParam4 = custParam4;
            this.custParam5 = custParam5;
            this.nickname = nickname;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerCategoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.saveBBPSADDTOFAV(AppConstants.getCLIENTID(),Coverage,Category,Billername,custParam1,custParam2,custParam3,custParam4,custParam5,nickname);

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
                    response="Favorites Added Successfully";
                } else {
                    errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
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
            super.onPostExecute(value);
            try {
                pDialog.dismiss();
                if (!this.error.equals("")) {
                    Toast.makeText(ctx,error, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx,response , Toast.LENGTH_SHORT).show();
                    new CustomerDetailAsyncTask(BBPSBillerCategoryFinacusActivity.this, billerId, bbpsCustomerParamsList, custParam1, custParam2, custParam3, custParam4, custParam5, sp_categories.getSelectedItem().toString(), billerName).execute();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void showSearchDialog() {
        Dialog dialog = new Dialog(BBPSBillerCategoryFinacusActivity.this);
        dialog.setContentView(R.layout.search_biller_bbps_finacus);
        EditText et_search_biller = dialog.findViewById(R.id.et_search_biller);
        ListView lv_biller =dialog.findViewById(R.id.lv_biller);

        ArrayAdapter<String> arr1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, bbpsBillerList);
        lv_biller.setAdapter(arr1);
        dialog.show();

        et_search_biller.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                List<String> dupList = new ArrayList<>();
                if (!s.toString().equals("")) {
                    for (String item : bbpsBillerList) {
                        if (item.toLowerCase().startsWith(s.toString().toLowerCase())) {
                            dupList.add(item);
                        }
                    }
                    ArrayAdapter<String> arr1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, dupList);
                    lv_biller.setAdapter(arr1);
                } else {
                    ArrayAdapter<String> arr1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bbpsBillerList);
                    lv_biller.setAdapter(arr1);
                }
            }
        });

        lv_biller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String billerName = parent.getItemAtPosition(position).toString();
                txt_biller_name.setText(parent.getItemAtPosition(position).toString());
                dialog.dismiss();
                try {
                    if(!txt_biller_name.getText().toString().equals("")) {
                        billerId = billerMap.get(billerName);

                        new FetchCustomerParamsAsyncTask(BBPSBillerCategoryFinacusActivity.this, billerId).execute();

                        et_cust_param1.setText("");
                        et_cust_param2.setText("");
                        et_cust_param3.setText("");
                        et_cust_param4.setText("");
                        et_cust_param5.setText("");
                        et_cust_param1.setError(null);
                        ck_Addtofav.setVisibility(View.VISIBLE);
                        btn_search.setVisibility(View.VISIBLE);
                    }else {
                        ll_cust_param1.setVisibility(View.GONE);
                        btn_search.setVisibility(View.GONE);
                        ll_cust_param2.setVisibility(View.GONE);
                        ll_cust_param3.setVisibility(View.GONE);
                        ll_cust_param4.setVisibility(View.GONE);
                        ll_cust_param5.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDialogOk(int resultCode) {
        if (resultCode == 0) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            method.activityCloseAnimation();
        }
    }

    private class FetchCovergaeAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String result;
        GenerateStanRRNModel generateStanRRNModel;
        List<String> bbpsCoverageModelList;
        private String errorCode;
        TMessage responseMsg;
        public FetchCovergaeAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerCategoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSBillerCategoryFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();

                MessageDtoBuilder msgDto = new MessageDtoBuilder();

                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPScoverageDto(TMessageUtil.GetLocalTxnDtTime(), generateStanRRNModel.getStan(),
                            AppConstants.getUSERMOBILENUMBER(), "", AppConstants.getUSERNAME(), "", "", "", "", "", "",
                            AppConstants.INSTITUTION_ID, generateStanRRNModel.getChannel_ref_no());//TMessageUtil.MSG_INSTITUTION_ID);
                    Log.d("msg.GetXml()" + "" + ":", requestXmlMsg.GetXml());

                    String base64EncodedRequestJson = Base64.encodeToString(requestXmlMsg.GetXml().getBytes(), Base64.NO_WRAP);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", base64EncodedRequestJson);

                    if (!url.equals("")) {
                        TrustMethods.LogMessage(TAG, "URL:-" + url);
                        result = HttpClientWrapper.postWitAuthHeader(url, jsonObject.toString(), AppConstants.getAuth_token());
                        TrustMethods.LogMessage(TAG, "Frm Enquiry details response-->" + response);
                    }
                    if (result == null || result.equals("")) {
                        error = AppConstants.SERVER_NOT_RESPONDING;
                        return error;
                    }
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.has("error")) {
                        error = jsonResponse.getString("error");
                        return error;
                    }

                    String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                    if (responseCode.equals("1")) {
                        response = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";

                        String responseValue = TrustMethods.decodeBase64(response);
                        Log.d("responseValue", responseValue);

                        ResponseEntity resParse = TMessage.ParseMessage(responseValue);
                        responseMsg = (TMessage) resParse.response;

                        String BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            NodeList nodeList = document.getElementsByTagName("coverage");
                            bbpsCoverageModelList = new ArrayList<>();
                            coverageMap = new HashMap<>();
                            bbpsCoverageModelList.add("Select Coverage");
                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                String id = element.getElementsByTagName("id").item(0).getTextContent();
                                String name = element.getElementsByTagName("name").item(0).getTextContent();

                                if(!name.equals(" ")) {
                                    bbpsCoverageModelList.add(name);
                                    coverageMap.put(name, id);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
                }
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (!this.error.equals("")) {
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(BBPSBillerCategoryFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, searchbillerCoordinatorLayoutId);
                    }
                } else {
                    if (bbpsCoverageModelList != null && bbpsCoverageModelList.size() != 1) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSBillerCategoryFinacusActivity.this, android.R.layout.simple_spinner_item, bbpsCoverageModelList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_coverage.setAdapter(adapter);
                    } else {
                        TrustMethods.showSnackBarMessage( responseMsg.ActCodeDesc.Value, searchbillerCoordinatorLayoutId);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class FetchBillerCategoryAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result, coverageId;
        List bbpsCategoryModelList;
        private String errorCode;
        TMessage responseMsg;
        public FetchBillerCategoryAsyncTask(Context ctx, String coverageId) {
            this.ctx = ctx;
            this.coverageId = coverageId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerCategoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSBillerCategoryFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data  = "<data><coverage>"+coverageId+"</coverage></data>";
                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();

                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPScategoriesDto(TMessageUtil.GetLocalTxnDtTime(), bbps_data, generateStanRRNModel.getStan(),
                            AppConstants.getUSERMOBILENUMBER(), "", AppConstants.getUSERNAME(), "", "", "", "", "", "",
                            AppConstants.INSTITUTION_ID, generateStanRRNModel.getChannel_ref_no());//TMessageUtil.MSG_INSTITUTION_ID);
                    Log.d("msg.GetXml()" + "" + ":", requestXmlMsg.GetXml());

                    String base64EncodedRequestJson = Base64.encodeToString(requestXmlMsg.GetXml().getBytes(), Base64.NO_WRAP);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", base64EncodedRequestJson);

                    if (!url.equals("")) {
                        TrustMethods.LogMessage(TAG, "URL:-" + url);
                        result = HttpClientWrapper.postWitAuthHeader(url, jsonObject.toString(), AppConstants.getAuth_token());
                        TrustMethods.LogMessage(TAG, "Frm Enquiry details response-->" + response);
                    }
                    if (result == null || result.equals("")) {
                        error = AppConstants.SERVER_NOT_RESPONDING;
                        return error;
                    }
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.has("error")) {
                        error = jsonResponse.getString("error");
                        return error;
                    }

                    String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                    if (responseCode.equals("1")) {
                        response = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";

                        String responseValue = TrustMethods.decodeBase64(response);
                        Log.d("responseValue", responseValue);

                        ResponseEntity resParse = TMessage.ParseMessage(responseValue);
                        responseMsg = (TMessage) resParse.response;

                        String BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            NodeList nodeList = document.getElementsByTagName("category");
                            bbpsCategoryModelList = new ArrayList<>();
                            categoryMap = new HashMap<>();
                            bbpsCategoryModelList.add("Select Biller Category");
                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                String id = element.getElementsByTagName("id").item(0).getTextContent();
                                String name = element.getElementsByTagName("name").item(0).getTextContent();

                                if (!name.equals(" ")) {
                                    bbpsCategoryModelList.add(name);
                                    categoryMap.put(name, id);
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
                }
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(BBPSBillerCategoryFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, searchbillerCoordinatorLayoutId);
                    }
                } else {
                    if (bbpsCategoryModelList != null && bbpsCategoryModelList.size() != 1) {
                        ll_biller_category.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(BBPSBillerCategoryFinacusActivity.this, android.R.layout.simple_spinner_item, bbpsCategoryModelList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_categories.setAdapter(adapter);
                    } else {
                        ll_biller_category.setVisibility(View.GONE);
                        TrustMethods.showSnackBarMessage(responseMsg.ActCodeDesc.Value, searchbillerCoordinatorLayoutId);
                    }
                    if(fav){
                        new GetFavAsyncTask(BBPSBillerCategoryFinacusActivity.this,selectNM).execute();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class FetchBillerDetailAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result, categoryId,coverageId;
        private String errorCode;
        TMessage responseMsg;
        public FetchBillerDetailAsyncTask(Context ctx, String categoryId, String coverageId) {
            this.ctx = ctx;
            this.categoryId = categoryId;
            this.coverageId = coverageId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerCategoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSBillerCategoryFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data  = "<data><category_id>"+ categoryId.trim()+"</category_id><coverage>"+ coverageId.trim()+"</coverage></data>";

                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();

                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSbillerDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
                            AppConstants.getUSERMOBILENUMBER(), "", AppConstants.getUSERNAME(), "", "", "", "", "", "",
                            AppConstants.INSTITUTION_ID, generateStanRRNModel.getChannel_ref_no());//TMessageUtil.MSG_INSTITUTION_ID);
                    Log.d("msg.GetXml()" + "" + ":", requestXmlMsg.GetXml());

                    String base64EncodedRequestJson = Base64.encodeToString(requestXmlMsg.GetXml().getBytes(), Base64.NO_WRAP);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", base64EncodedRequestJson);

                    if (!url.equals("")) {
                        TrustMethods.LogMessage(TAG, "URL:-" + url);
                        result = HttpClientWrapper.postWitAuthHeader(url, jsonObject.toString(), AppConstants.getAuth_token());
                        TrustMethods.LogMessage(TAG, "Frm Enquiry details response-->" + response);
                    }
                    if (result == null || result.equals("")) {
                        error = AppConstants.SERVER_NOT_RESPONDING;
                        return error;
                    }
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.has("error")) {
                        error = jsonResponse.getString("error");
                        return error;
                    }

                    String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                    if (responseCode.equals("1")) {
                        response = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";

                        String responseValue = TrustMethods.decodeBase64(response);
                        Log.d("responseValue", responseValue);

                        ResponseEntity resParse = TMessage.ParseMessage(responseValue);
                        responseMsg = (TMessage) resParse.response;

                        String BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            NodeList nodeList = document.getElementsByTagName("biller");
                            bbpsBillerList = new ArrayList<>();
                            billerMap = new HashMap<>();

                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                String id = element.getElementsByTagName("id").item(0).getTextContent();
                                String category = element.getElementsByTagName("category").item(0).getTextContent();
                                String category_desc = element.getElementsByTagName("category_desc").item(0).getTextContent();
                                String biller_mode = element.getElementsByTagName("biller_mode").item(0).getTextContent();
                                String biller_id = element.getElementsByTagName("biller_id").item(0).getTextContent();
                                String biller_name = element.getElementsByTagName("biller_name").item(0).getTextContent();
                                String accept_adhoc_payment = element.getElementsByTagName("accept_adhoc_payment").item(0).getTextContent();
                                String payment_amount_exactness = element.getElementsByTagName("payment_amount_exactness").item(0).getTextContent();

                                bbpsBillerList.add(biller_name);
                                billerMap.put(biller_name, biller_id);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
                }
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(BBPSBillerCategoryFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, searchbillerCoordinatorLayoutId);
                    }
                } else {
                    if (bbpsBillerList != null && bbpsBillerList.size() != 1) {
                        ll_biller_name.setVisibility(View.VISIBLE);
                    } else {
                        ll_biller_name.setVisibility(View.GONE);
                        TrustMethods.showSnackBarMessage(responseMsg.ActCodeDesc.Value, searchbillerCoordinatorLayoutId);
                    }
                    if(fav){
                        new GetFavAsyncTask(BBPSBillerCategoryFinacusActivity.this,selectNM).execute();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class FetchCustomerParamsAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result, billerId;
        private String errorCode, name, min_length, max_length, field_type, is_mandatory, regex;
        TMessage responseMsg;
        public FetchCustomerParamsAsyncTask(Context ctx, String billerId) {
            this.ctx = ctx;
            this.billerId = billerId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerCategoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSBillerCategoryFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();
                String bbps_data  = "<data><biller_id>"+billerId+"</biller_id></data>";

                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();

                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPScustomerParamsDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
                            AppConstants.getUSERMOBILENUMBER(), "", AppConstants.getUSERNAME(), "", "", "", "", "", "",
                            AppConstants.INSTITUTION_ID, generateStanRRNModel.getChannel_ref_no());//TMessageUtil.MSG_INSTITUTION_ID);
                    Log.d("msg.GetXml()" + "" + ":", requestXmlMsg.GetXml());

                    String base64EncodedRequestJson = Base64.encodeToString(requestXmlMsg.GetXml().getBytes(), Base64.NO_WRAP);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", base64EncodedRequestJson);

                    if (!url.equals("")) {
                        TrustMethods.LogMessage(TAG, "URL:-" + url);
                        result = HttpClientWrapper.postWitAuthHeader(url, jsonObject.toString(), AppConstants.getAuth_token());
                        TrustMethods.LogMessage(TAG, "Frm Enquiry details response-->" + response);
                    }
                    if (result == null || result.equals("")) {
                        error = AppConstants.SERVER_NOT_RESPONDING;
                        return error;
                    }
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.has("error")) {
                        error = jsonResponse.getString("error");
                        return error;
                    }

                    String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                    if (responseCode.equals("1")) {
                        response = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";

                        String responseValue = TrustMethods.decodeBase64(response);
                        Log.d("responseValue", responseValue);

                        ResponseEntity resParse = TMessage.ParseMessage(responseValue);
                        responseMsg = (TMessage) resParse.response;

                        String BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            NodeList nodeList = document.getElementsByTagName("customer_param");
                            bbpsCustomerParamsList = new ArrayList<>();

                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Element element = (Element) nodeList.item(j);
                                name = element.getElementsByTagName("name").item(0).getTextContent();
                                min_length = element.getElementsByTagName("min_length").item(0).getTextContent();
                                max_length = element.getElementsByTagName("max_length").item(0).getTextContent();
                                field_type = element.getElementsByTagName("field_type").item(0).getTextContent();
                                is_mandatory = element.getElementsByTagName("is_mandatory").item(0).getTextContent();
                                regex = element.getElementsByTagName("regex").item(0).getTextContent();

                                BBPSCustomerParamFinacusModel bbpsCustomeParamater = new BBPSCustomerParamFinacusModel();
                                bbpsCustomeParamater.setParamName(name);
                                bbpsCustomeParamater.setMin_length(min_length);
                                bbpsCustomeParamater.setMax_length(max_length);
                                bbpsCustomeParamater.setRegex(regex);
                                bbpsCustomeParamater.setField_type(field_type);
                                bbpsCustomeParamater.setIs_mandatory(is_mandatory);
                                bbpsCustomeParamater.setRegex(regex);
                                bbpsCustomerParamsList.add(bbpsCustomeParamater);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
                }
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            try {

                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(BBPSBillerCategoryFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, searchbillerCoordinatorLayoutId);
                    }
                }else{
                    if(!bbpsCustomerParamsList.isEmpty()) {
                        ll_cust_param1.setVisibility(View.VISIBLE);
                        ll_cust_param1.setHint(bbpsCustomerParamsList.get(0).getParamName());

                        if(bbpsCustomerParamsList.size()>=2){
                            ll_cust_param2.setVisibility(View.VISIBLE);
                            ll_cust_param2.setHint(bbpsCustomerParamsList.get(1).getParamName());
                        }
                        if(bbpsCustomerParamsList.size()>=3){
                            ll_cust_param3.setVisibility(View.VISIBLE);
                            ll_cust_param3.setHint(bbpsCustomerParamsList.get(2).getParamName());
                        }
                        if(bbpsCustomerParamsList.size()>=4){
                            ll_cust_param4.setVisibility(View.VISIBLE);
                            ll_cust_param4.setHint(bbpsCustomerParamsList.get(3).getParamName());
                        }
                        if(bbpsCustomerParamsList.size()>=5){
                            ll_cust_param5.setVisibility(View.VISIBLE);
                            ll_cust_param5.setHint(bbpsCustomerParamsList.get(4).getParamName());
                        }
                    }else{
                        TrustMethods.showSnackBarMessage("No Customer Parameters Found", searchbillerCoordinatorLayoutId);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class CustomerDetailAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        GenerateStanRRNModel generateStanRRNModel;
        String result, billerId, cust_param1, BBPS_RESPONSE_DATA, cust_param2, cust_param3, cust_param4, cust_param5, bbps_data, category, biller_name;
        String transaction_id, customer_name, bill_number, bill_period, bill_date, bill_due_date, bill_amount, name, customer_param_value;
        List<BBPSCustomerParamFinacusModel> bbpsCustomerParamsList;
        private String errorCode;
        TMessage responseMsg;
        List<BBPSCustomerParamFinacusModel> customerParamsList = new ArrayList<>();
        public CustomerDetailAsyncTask(Context ctx, String billerId, List<BBPSCustomerParamFinacusModel> bbpsCustomerParamsList, String cust_param1, String cust_param2, String cust_param3, String cust_param4, String cust_param5, String category, String biller_name) {
            this.ctx = ctx;
            this.billerId=billerId;
            this.bbpsCustomerParamsList=bbpsCustomerParamsList;
            this.cust_param1=cust_param1;
            this.cust_param2=cust_param2;
            this.cust_param3=cust_param3;
            this.cust_param4=cust_param4;
            this.cust_param5=cust_param5;
            this.category=category;
            this.biller_name=biller_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BBPSBillerCategoryFinacusActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                String jsonString = "{\"filter\":[\"stan\",\"channel_ref_no\"]}";
                generateStanRRNModel = TMessageUtil.GetNextStanRrn(BBPSBillerCategoryFinacusActivity.this, jsonString, TrustURL.GenerateStanRrnUrl(), AppConstants.getAuth_token());
                String url = TrustURL.httpCallUrl();

                if(bbpsCustomerParamsList.size()==1){
                    bbps_data  = "<data><biller_id>"+billerId+"</biller_id><customer_params><customer_param><name>"+bbpsCustomerParamsList.get(0).getParamName()+"</name><value>"+cust_param1+"</value></customer_param></customer_params></data>";
                } else if (bbpsCustomerParamsList.size()==2) {
                    bbps_data  = "<data><biller_id>"+billerId+"</biller_id><customer_params><customer_param><name>"+bbpsCustomerParamsList.get(0).getParamName()+"</name><value>"+cust_param1+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(1).getParamName()+"</name><value>"+cust_param2+"</value></customer_param></customer_params></data>";
                } else if (bbpsCustomerParamsList.size()==3) {
                    bbps_data  = "<data><biller_id>"+billerId+"</biller_id><customer_params><customer_param><name>"+bbpsCustomerParamsList.get(0).getParamName()+"</name><value>"+cust_param1+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(1).getParamName()+"</name><value>"+cust_param2+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(2).getParamName()+"</name><value>"+cust_param3+"</value></customer_param></customer_params></data>";
                }else if (bbpsCustomerParamsList.size()==4) {
                    bbps_data  = "<data><biller_id>"+billerId+"</biller_id><customer_params><customer_param><name>"+bbpsCustomerParamsList.get(0).getParamName()+"</name><value>"+cust_param1+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(1).getParamName()+"</name><value>"+cust_param2+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(2).getParamName()+"</name><value>"+cust_param3+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(3).getParamName()+"</name><value>"+cust_param4+"</value></customer_param></customer_params></data>";
                }else if (bbpsCustomerParamsList.size()==5) {
                    bbps_data  = "<data><biller_id>"+billerId+"</biller_id><customer_params><customer_param><name>"+bbpsCustomerParamsList.get(0).getParamName()+"</name><value>"+cust_param1+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(1).getParamName()+"</name><value>"+cust_param2+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(2).getParamName()+"</name><value>"+cust_param3+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(3).getParamName()+"</name><value>"+cust_param4+"</value></customer_param><customer_param><name>"+bbpsCustomerParamsList.get(4).getParamName()+"</name><value>"+cust_param5+"</value></customer_param></customer_params></data>";
                }


                bbps_data= TrustMethods.encodeBase64(bbps_data);
                MessageDtoBuilder msgDto = new MessageDtoBuilder();
                if (generateStanRRNModel.getError() != null) {
                    error = generateStanRRNModel.getError();
                    return error;
                } else {
                    TMessage requestXmlMsg = null;
                    requestXmlMsg = msgDto.GetBBPSbillerDetailsDto(TMessageUtil.GetLocalTxnDtTime(),bbps_data, generateStanRRNModel.getStan(),
                            AppConstants.getUSERMOBILENUMBER(), "", AppConstants.getUSERNAME(), "", "", "", "", "", "",
                            AppConstants.INSTITUTION_ID, generateStanRRNModel.getChannel_ref_no());//TMessageUtil.MSG_INSTITUTION_ID);
                    Log.d("msg.GetXml()" + "" + ":", requestXmlMsg.GetXml());

                    String base64EncodedRequestJson = Base64.encodeToString(requestXmlMsg.GetXml().getBytes(), Base64.NO_WRAP);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", base64EncodedRequestJson);

                    if (!url.equals("")) {
                        TrustMethods.LogMessage(TAG, "URL:-" + url);
                        result = HttpClientWrapper.postWitAuthHeader(url, jsonObject.toString(), AppConstants.getAuth_token());
                        TrustMethods.LogMessage(TAG, "Frm Enquiry details response-->" + response);
                    }
                    if (result == null || result.equals("")) {
                        error = AppConstants.SERVER_NOT_RESPONDING;
                        return error;
                    }
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.has("error")) {
                        error = jsonResponse.getString("error");
                        return error;
                    }

                    String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                    if (responseCode.equals("1")) {
                        response = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";

                        String responseValue = TrustMethods.decodeBase64(response);
                        Log.d("responseValue", responseValue);

                        ResponseEntity resParse = TMessage.ParseMessage(responseValue);
                        responseMsg = (TMessage) resParse.response;

                        BBPS_RESPONSE_DATA = TrustMethods.decodeBase64(responseMsg.BBPS_RESPONSE_DATA.Value);
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(BBPS_RESPONSE_DATA)));
                            transaction_id = document.getElementsByTagName("transaction_id").item(0).getTextContent();
                            customer_name = document.getElementsByTagName("customer_name").item(0).getTextContent();
                            bill_number = document.getElementsByTagName("bill_number").item(0).getTextContent();
                            bill_period = document.getElementsByTagName("bill_period").item(0).getTextContent();
                            bill_date = document.getElementsByTagName("bill_date").item(0).getTextContent();
                            bill_due_date = document.getElementsByTagName("bill_due_date").item(0).getTextContent();
                            bill_amount = document.getElementsByTagName("bill_amount").item(0).getTextContent();

                            NodeList nodeList7 = document.getElementsByTagName("customer_params_details");
                            for (int j = 0; j < nodeList7.getLength(); j++) {
                                Element element1 = (Element) nodeList7.item(j);
                                name = element1.getElementsByTagName("name").item(j).getTextContent();
                                customer_param_value = element1.getElementsByTagName("value").item(j).getTextContent();

                                BBPSCustomerParamFinacusModel customeParamater = new BBPSCustomerParamFinacusModel();
                                customeParamater.setParamName(name);
                                customeParamater.setValue(customer_param_value);
                                customerParamsList.add(customeParamater);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                        errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                        error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!this.error.equals("")) {
                    if (!TextUtils.isEmpty(errorCode) && TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(BBPSBillerCategoryFinacusActivity.this,
                                getResources().getString(R.string.error_session_expire), "", getResources().getString(R.string.btn_ok),
                                0, false, alertDialogOkListener);
                    } else {
                        TrustMethods.showSnackBarMessage(this.error, searchbillerCoordinatorLayoutId);
                    }
                } else {
                    if(BBPS_RESPONSE_DATA.equals("")){
                        TrustMethods.showSnackBarMessage(responseMsg.ActCodeDesc.Value, searchbillerCoordinatorLayoutId);
                    }else {
                        sp_coverage.setSelection(0);
                       // ck_Addtofav.setChecked(false);

                        Intent intent = new Intent(BBPSBillerCategoryFinacusActivity.this, BBPSBillerDetailFinacusActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        intent.putExtra("transaction_id", transaction_id);
                        intent.putExtra("customer_name", customer_name);
                        intent.putExtra("bill_number", bill_number);
                        intent.putExtra("bill_period", bill_period);
                        intent.putExtra("bill_date", bill_date);
                        intent.putExtra("bill_due_date", bill_due_date);
                        intent.putExtra("bill_amount", bill_amount);
                        intent.putExtra("customer_param_name", name);
                        intent.putExtra("customer_param_value", customer_param_value);
                        intent.putExtra("biller_id", billerId);
                        intent.putExtra("category", category);
                        intent.putExtra("biller_name", biller_name);
                        intent.putExtra("customerParamsList", (Serializable) customerParamsList);
                        startActivity(intent);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BBPSBillerCategoryFinacusActivity.this, BBPSFinacusActivity.class);
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
        TrustMethods.showBackButtonAlert(BBPSBillerCategoryFinacusActivity.this);
    }
    private void horizontalRecyclerView() {
        recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
        method.horizontalRecyclerView(BBPSBillerCategoryFinacusActivity.this, recyclerViewHoriListId);
    }
}