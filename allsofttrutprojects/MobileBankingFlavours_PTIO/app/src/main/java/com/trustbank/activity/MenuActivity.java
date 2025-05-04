package com.trustbank.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.adapter.MenuAdapter;
import com.trustbank.adapter.SlidingImageAdapter;
import com.trustbank.interfaces.AlertDialogListener;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.ItemOffsetDecoration;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SessionManager;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;
import com.viewpagerindicator.CirclePageIndicator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MenuActivity extends AppCompatActivity implements AlertDialogOkListener, AlertDialogListener, View.OnClickListener{

    private TrustMethods method;
    private SharedPreferences sharedPreferences;
    private SessionManager sessionManager;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "BankApp";
    private String customerName;
    GridLayoutManager gridLayoutManager;
    boolean isShow = false;
    private int currentPage = 0;
    private TextView textViewUserName;
    private AlertDialogOkListener alertDialogOkListener = this;
    private AlertDialogListener alertDialogListener = this;

    private RecyclerView recyclerViewHoriListId;
    private int BARCODE_READER_REQUEST_CODE=1;
    ImageTextMenuModel[] newitemData = null;
    int RESULT_LOAD_IMAGE=2;
    FloatingActionButton imageViewProfile;
    FloatingActionButton scan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(MenuActivity.this);
                }
            }
        }
        SetTheme.changeToTheme(MenuActivity.this, true);
        setContentView(R.layout.activity_menu);


        init();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            bundle.putString(AppConstants.PID, String.valueOf(android.os.Process.myPid()));
        }
    }
    @SuppressLint("CommitPrefEdits")
    private void init() {
        try {
            method = new TrustMethods(MenuActivity.this);
            sharedPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            sessionManager = new SessionManager(MenuActivity.this);
            imageViewProfile = findViewById(R.id.imageViewProfile_Id);
            textViewUserName=findViewById(R.id.textViewUserName_Id);
            scan = findViewById(R.id.scan);
            scan.setOnClickListener((View.OnClickListener) this);


            horizontalRecyclerView();

          /*  getParentlist=AppConstants.getParentlist();*/
            gridLayoutManager = new GridLayoutManager(this, 3);
            RecyclerView recyclerViewMenu = findViewById(R.id.recyclerListId);
            recyclerViewMenu.setNestedScrollingEnabled(false);
            recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
            recyclerViewMenu.setHasFixedSize(true);
            recyclerViewMenu.setLayoutManager(gridLayoutManager);
            List<ImageTextMenuModel> newitemData1 = listItems();
            MenuAdapter adapter1 = new MenuAdapter(MenuActivity.this, newitemData1,AppConstants.getSubmenu());
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(MenuActivity.this, R.dimen.item_offset);
            recyclerViewMenu.addItemDecoration(itemDecoration);
            recyclerViewMenu.setAdapter(adapter1);

            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
                if ((!TextUtils.isEmpty(AppConstants.getPlay_store_validate())) && AppConstants.getPlay_store_validate().equalsIgnoreCase("1")) {
                    ArrayList<String> stringArrayList = new ArrayList<>();
                    stringArrayList.add("/Advertisment/netbank.png");
                    stringArrayList.add("/Advertisment/netbank.png");
                    viewPager(stringArrayList);
                   /* List<ImageTextMenuModel> newitemData = Arrays.asList(staticData());
                    MenuAdapter adapter = new MenuAdapter(MenuActivity.this, newitemData,AppConstants.getSubmenu());
                    ItemOffsetDecoration itemDecoratio = new ItemOffsetDecoration(MenuActivity.this, R.dimen.item_offset);
                    recyclerViewMenu.addItemDecoration(itemDecoratio);
                    recyclerViewMenu.setAdapter(adapter);*/

                } else if (AppConstants.getUSERMOBILENUMBER().equalsIgnoreCase(AppConstants.playStoreDemoUserMobile) && AppConstants.getCLIENTID().equalsIgnoreCase(AppConstants.playStoreDemoPasswordClientid)) {
                    ArrayList<String> stringArrayList = new ArrayList<>();
                    stringArrayList.add("/Advertisment/netbank.png");
                    stringArrayList.add("/Advertisment/netbank.png");
                    viewPager(stringArrayList);
                    List<ImageTextMenuModel> newitemData = Arrays.asList(staticData());
                    MenuAdapter adapter = new MenuAdapter(MenuActivity.this, newitemData,AppConstants.getSubmenu());
                    ItemOffsetDecoration itemDecoratio = new ItemOffsetDecoration(MenuActivity.this, R.dimen.item_offset);
                    recyclerViewMenu.addItemDecoration(itemDecoratio);
                    recyclerViewMenu.setAdapter(adapter);
                } else {
                    if (NetworkUtil.getConnectivityStatus(MenuActivity.this)) {
                        if (!TextUtils.isEmpty(AppConstants.getAuth_token())) {
                            new AsyncTaskGetBankDetails(MenuActivity.this).execute();
                        } else {
                            AlertDialogMethod.alertDialog(MenuActivity.this, getResources().getString(R.string.error_session_expire),
                                    getResources().getString(R.string.msg_refresh_token),
                                    getResources().getString(R.string.btn_reload), getResources().getString(R.string.btn_cancel),
                                    11, false, alertDialogListener);
                        }
                    } else {
                        Toast.makeText(MenuActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                TrustMethods.displaySimErrorDialog(this);
            }

            getUserProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan:
                AppConstants.menu_group=true;
                Intent intent1 = new Intent(MenuActivity.this, SelfTransferToAccountActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                break;
        }
    }

    private ImageTextMenuModel[] staticData() {
        ImageTextMenuModel[] oldItemData = {
                new ImageTextMenuModel("Accounts", R.drawable.change_password, "1","Accounts"),
                new ImageTextMenuModel("NEFT", R.drawable.manage_acc, "1","NEFT"),
                new ImageTextMenuModel("IMPS", R.drawable.manage_acc, "1","IMPS"),
                /*   new ImageTextMenuModel("IMPS Transfer To Mobile Phone", R.drawable.change_password, "1"),*/
                new ImageTextMenuModel("Self Transfer", R.drawable.manage_acc, "1","Self Transfer"),
                new ImageTextMenuModel("Bill Pay", R.drawable.change_password, "1","Bill Pay"),
                /*  new ImageTextMenuModel("Funds Transfer", R.drawable.fund_transfer_one, "1"),*/
                new ImageTextMenuModel("Locate ATMs", R.drawable.manage_acc, "1","Locate ATMs"),
                new ImageTextMenuModel("Locate Branches", R.drawable.change_password, "1","Locate Branches"),
                new ImageTextMenuModel("FAQs", R.drawable.change_password, "1","FAQs"),
                new ImageTextMenuModel("Settings", R.drawable.manage_acc, "1","Settings"),
                new ImageTextMenuModel("Contact Us", R.drawable.change_password, "1","Contact Us"),
                new ImageTextMenuModel("About Us", R.drawable.manage_acc, "1","About Us"),

        };

        int menuSize = 0;
        for (ImageTextMenuModel oldItemDatum : oldItemData) {
            if (!TextUtils.isEmpty(oldItemDatum.getIsEnabled())) {
                if (oldItemDatum.getIsEnabled().trim().equals("1")) {
                    menuSize++;
                }
            }
        }

        int j = 0;
        newitemData = new ImageTextMenuModel[menuSize];
        for (ImageTextMenuModel oldItemDatum : oldItemData) {
            if (!TextUtils.isEmpty(oldItemDatum.getIsEnabled())) {
                if (oldItemDatum.getIsEnabled().trim().equals("1")) {
                    newitemData[j] = oldItemDatum;
                    j++;
                }
            }
        }

        return newitemData;
    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskGetBankDetails extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response = null;
        ProgressDialog pDialog;
        private String result = null;
        private ArrayList<String> stringArrayList;

        public AsyncTaskGetBankDetails(Context ctx) {
            this.ctx = ctx;
            stringArrayList = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MenuActivity.this);
            pDialog.setMessage("Loading Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.getAdevertisement();
                if (!url.equals("")) {
                    response = HttpClientWrapper.getResponceDirectalyGET(url, AppConstants.getAuth_token());
                    TrustMethods.LogMessage("response-->", response);

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray imageJsonArray = jsonObject.has("files") ? jsonObject.getJSONArray("files") : null;
                    if (imageJsonArray != null) {
                        for (int i = 0; i < imageJsonArray.length(); i++) {
                            stringArrayList.add(imageJsonArray.getString(i));
                        }
                        result = "Success";
                    }

                }
            } catch (Exception ex) {
                TrustMethods.LogMessage("Exception ", ex.getMessage());
                error = ex.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                getUserProfile();
                if (this.error != "") {
                    method.message(this.ctx, this.error);
                    return;
                } else {
                    if (TextUtils.isEmpty(result)) {
                    } else if (result.equalsIgnoreCase("Success")) {
                        viewPager(stringArrayList);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void viewPager(ArrayList<String> stringArrayList) {
        try {
            float density = getResources().getDisplayMetrics().density;
            final int NUM_PAGES = stringArrayList.size();

            final ViewPager mPager = findViewById(R.id.pager);
            mPager.setAdapter(new SlidingImageAdapter(MenuActivity.this, stringArrayList));

            CirclePageIndicator indicator = findViewById(R.id.indicator);
            indicator.setViewPager(mPager);
            indicator.setRadius(5 * density);

            final Handler handler = new Handler();
            final Runnable Update = () -> {
                if (currentPage == NUM_PAGES) currentPage = 0;
                mPager.setCurrentItem(currentPage++, true);
            };

            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 4000, 4000);

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int pos) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserProfile() {
        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
            if (NetworkUtil.getConnectivityStatus(MenuActivity.this)) {
                method.clearAccountsArrayList(MenuActivity.this);
                if (!TextUtils.isEmpty(AppConstants.getAuth_token())) {
                    new AuthenticateUserAsyncTask(MenuActivity.this, AppConstants.getUSERMOBILENUMBER()).execute();
                } else {
                    AlertDialogMethod.alertDialog(MenuActivity.this, getResources().getString(R.string.error_session_expire),getResources().getString(R.string.msg_refresh_token),getResources().getString(R.string.btn_reload), getResources().getString(R.string.btn_cancel),11, false, alertDialogListener);
                }
            } else {
                //TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
            }
        } else {
            TrustMethods.displaySimErrorDialog(this);
        }
    }



    @SuppressLint("StaticFieldLeak")
    private class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, String> {
        private String error = "";
        private Context ctx;
        private String response;
        private ProgressDialog pDialog;
        private String result;
        private String mMobileNumber;
        private String actionName = "GET_PROFILE";
        private ArrayList<GetUserProfileModal> getUserProfileList;
        private String errorCode;

        public AuthenticateUserAsyncTask(Context ctx, String mobileNumber) {
            this.ctx = ctx;
            this.mMobileNumber = mobileNumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MenuActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.GetProfileAccountsAndChequeBookDetailsUrl(mMobileNumber, AppConstants.getCLIENTID());
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
                    JSONObject dataObject = jsonResponse.getJSONObject("response");
                    if (dataObject.has("error")) {
                        error = dataObject.getString("error");
                        return error;
                    }

                    JSONObject profileJsonObject = dataObject.getJSONObject("profile");
                    if (profileJsonObject.has("error")) {
                        error = profileJsonObject.getString("error");
                        return error;
                    }
                    String profileId = profileJsonObject.has("profileid") ? profileJsonObject.getString("profileid") : "NA";
                    String mobileNo = profileJsonObject.has("mobileno") ? profileJsonObject.getString("mobileno") : "NA";
                    customerName = profileJsonObject.has("cust_name") ? profileJsonObject.getString("cust_name") : "NA";
                    String emailId = profileJsonObject.has("email") ? profileJsonObject.getString("email") : "NA";
                    String clientId = profileJsonObject.has("clientid") ? profileJsonObject.getString("clientid") : "NA";
                    String address = profileJsonObject.has("address") ? profileJsonObject.getString("address") : "NA";

                    AppConstants.setProfileID(profileId);
                    AppConstants.setMobileno(mobileNo);
                    AppConstants.setUSEREMAILADDRESS(emailId);
                    AppConstants.setCLIENTID(clientId);
                    AppConstants.setAddress(address);


                    JSONObject accountsJsonObject = profileJsonObject.has("accounts") ? profileJsonObject.getJSONObject("accounts") : null;
                    if (accountsJsonObject != null) {
                        if (accountsJsonObject.has("error")) {
                            error = accountsJsonObject.getString("error");
                            return error;
                        }
                        getUserProfileList = new ArrayList<>();
                        //TODO.....................................................................
                        JSONArray accountsJsonArray = accountsJsonObject.has("account") ? accountsJsonObject.getJSONArray("account") : null;
                        if (accountsJsonArray != null) {
                            String name="";
                            if (accountsJsonArray.length() > 0) {
                                for (int i = 0; i < accountsJsonArray.length(); i++) {
                                    JSONObject dataJsonObject = accountsJsonArray.getJSONObject(i);

                                    String accNo = dataJsonObject.has("acno") ? dataJsonObject.getString("acno") : "NA";
                                    String mmid = dataJsonObject.has("mmid") ? dataJsonObject.getString("mmid") : "NA";
                                    name = dataJsonObject.has("name") ? dataJsonObject.getString("name") : "NA";
                                    String acType = dataJsonObject.has("ac_type") ? dataJsonObject.getString("ac_type") : "";
                                    String ac_status = dataJsonObject.has("ac_status") ? dataJsonObject.getString("ac_status") : "";
                                    String is_imps_reg = dataJsonObject.has("is_imps_reg") ? dataJsonObject.getString("is_imps_reg") : "";
                                    String accountid = dataJsonObject.has("accountid") ? dataJsonObject.getString("accountid") : "";
                                    String orgelementid = dataJsonObject.has("orgelementid") ? dataJsonObject.getString("orgelementid") : "";
                                    String ac_type_code = dataJsonObject.has("ac_type_code") ? dataJsonObject.getString("ac_type_code") : "";
                                    String agency = dataJsonObject.has("agency") ? dataJsonObject.getString("agency") : "";

                                    GetUserProfileModal getUserProfileModal = new GetUserProfileModal();
                                    getUserProfileModal.setAccNo(accNo);
                                    getUserProfileModal.setMmid(mmid);
                                    getUserProfileModal.setName(name);
                                    getUserProfileModal.setActType(acType);
                                    getUserProfileModal.setAc_status(ac_status);
                                    getUserProfileModal.setIs_imps_reg(is_imps_reg);
                                    getUserProfileModal.setAccountid(accountid);
                                    getUserProfileModal.setOrgelementid(orgelementid);
                                    getUserProfileModal.setAcTypeCode(ac_type_code);
                                    getUserProfileModal.setAgency(agency);

                                    getUserProfileList.add(getUserProfileModal);
                                }
                                textViewUserName.setText(name);
                                AppConstants.setUSERNAME(name);
                            }
                        } else {
                            error = "Accounts details not found";
                            return error;
                        }
                    } else {
                        error = "Accounts details not found";
                        return error;
                    }
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
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialog(MenuActivity.this, getResources().getString(R.string.error_session_expire),
                                getResources().getString(R.string.msg_refresh_token),
                                getResources().getString(R.string.btn_reload), getResources().getString(R.string.btn_cancel),
                                11, false, alertDialogListener);
                    } else {
                        /*AlertDialogMethod.alertDialog(MenuActivity.this,"Error !", this.error,"OK","",
                                55, false, alertDialogListener);*/
                    }
                } else {
                    if (getUserProfileList != null && getUserProfileList.size() != 0) {
                        method.saveArrayList(MenuActivity.this, getUserProfileList, "AccountListPref");
                        if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(MenuActivity.this)) {
                            if (NetworkUtil.getConnectivityStatus(getApplicationContext())) {
                                if (!TextUtils.isEmpty(AppConstants.getAuth_token())) {
                                    new GetBanaficiaryAsyncTask(MenuActivity.this, AppConstants.getUSERMOBILENUMBER()).execute();
                                } else {
                                    AlertDialogMethod.alertDialogOk(MenuActivity.this, MenuActivity.this.getResources().getString(R.string.error_session_expire),
                                            "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                                }
                            } else {
                                // TrustMethods.showSnackBarMessage(mActivity.getResources().getString(R.string.error_check_internet), dataLayout);
                            }
                        } else {
                            //  TrustMethods.displaySimErrorDialog(activity);
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Refresh Token Alert Dialog....................
    @Override
    public void onDialogOk(int resultCode) {
        if (resultCode == 0) {
            Intent intent = new Intent(MenuActivity.this, LockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            method.activityCloseAnimation();
            finish();
        } else if (resultCode == 11) {
            method.refreshToken(MenuActivity.this);
        }else if (resultCode == 55) {
            Intent intent = new Intent(MenuActivity.this, LockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            method.activityCloseAnimation();
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageViewProfile.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MenuActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDialogCancel(int resultCode) {

    }


    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(MenuActivity.this, LockActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();*/
        TrustMethods.showBackButtonAlert(MenuActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("StaticFieldLeak")
    public class GetBanaficiaryAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        String mobileNumber;
        String result;
        ProgressDialog pDialog;
        String actionName = "LIST_BENEFICIARY";
        private String errorCode;
        ArrayList<BeneficiaryModal> beneficiaryList = null;

        public GetBanaficiaryAsyncTask(Context ctx, String mobileNumber) {
            this.ctx = ctx;
            this.mobileNumber = mobileNumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MenuActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();

                String jsonString = "{\"mobile_number\":\"" + mobileNumber + "\"}";
                TrustMethods.LogMessage("", "json string for change pass : " + jsonString);

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonString, actionName, AppConstants.getAuth_token());
                }
                //  AppConstants.setAuth_token("");
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
                    JSONObject responseJsonObject = jsonResponse.getJSONObject("response");
                    if (responseJsonObject.has("error")) {
                        error = responseJsonObject.getString("error");
                        return error;
                    }
                    JSONArray jsonArray = responseJsonObject.getJSONArray("ben_list");
                    beneficiaryList = new ArrayList<>();

                    beneficiaryList.clear();

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject dataJsonObject = jsonArray.getJSONObject(i);
                            String benId = dataJsonObject.has("benid") ? dataJsonObject.getString("benid") : "NA";
                            String benType = dataJsonObject.has("ben_type") ? dataJsonObject.getString("ben_type") : "NA";
                            String benName = dataJsonObject.has("ben_nickname") ? dataJsonObject.getString("ben_nickname") : "NA";
                            String benAccName = dataJsonObject.has("ben_ac_name") ? dataJsonObject.getString("ben_ac_name") : "NA";
                            String benAccNo = dataJsonObject.has("ben_ac_no") ? dataJsonObject.getString("ben_ac_no") : "NA";
                            String benIfscCode = dataJsonObject.has("ben_ifsc") ? dataJsonObject.getString("ben_ifsc") : "NA";
                            String benMobNo = dataJsonObject.has("ben_mobile_number") ? dataJsonObject.getString("ben_mobile_number") : "NA";
                            String benMmid = dataJsonObject.has("ben_mmid") ? dataJsonObject.getString("ben_mmid") : "NA";
                            String benUpiId = dataJsonObject.has("ben_upi_id") ? dataJsonObject.getString("ben_upi_id") : "NA";
                            String agencyName = dataJsonObject.has("agency_name") ? dataJsonObject.getString("agency_name") : "NA";

                            BeneficiaryModal beneficiaryModal = new BeneficiaryModal();
                            beneficiaryModal.setBenId(benId);
                            beneficiaryModal.setBenType(benType);
                            beneficiaryModal.setBenNickname(benName);
                            beneficiaryModal.setBanAccName(benAccName);
                            beneficiaryModal.setBenAccNo(benAccNo);
                            beneficiaryModal.setBenIfscCode(benIfscCode);
                            beneficiaryModal.setBenMobNo(benMobNo);
                            beneficiaryModal.setBenMmid(benMmid);
                            beneficiaryModal.setBenUpiId(benUpiId);
                            beneficiaryModal.setAgncyname(agencyName);

                            beneficiaryList.add(beneficiaryModal);
                        }
                    }
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
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(MenuActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(MenuActivity.this, this.error, "", getResources().getString(R.string.btn_ok),
                                1, false, alertDialogOkListener);
                    }
                } else {
                    method.saveBenArrayList(MenuActivity.this, beneficiaryList, "BenAccList");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void horizontalRecyclerView() {

        try {
            recyclerViewHoriListId = findViewById(R.id.recyclerViewHoriListId);
            method.horizontalRecyclerView(this, recyclerViewHoriListId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<ImageTextMenuModel> listItems() {
        ArrayList<ImageTextMenuModel> collectlist = null;
        try {
            List<ImageTextMenuModel> menulist = new ArrayList<>();
            for (DynamicMenuModel parentmenu : AppConstants.getParentlist()) {
                if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts")) {
                    menulist.add(new ImageTextMenuModel(getResources().getString(R.string.Accounts), R.drawable.accountptio, AppConstants.getMnu_accounts(), getResources().getString(R.string.Accounts)));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_investment")) {
                    menulist.add(new ImageTextMenuModel(getResources().getString(R.string.Investments), R.drawable.investmentptio, AppConstants.getMnu_investment(), getResources().getString(R.string.Investments)));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_transactions")) {
                    menulist.add(new ImageTextMenuModel(getResources().getString(R.string.Transactions), R.drawable.transactionsptio, AppConstants.getMnu_transactions(), getResources().getString(R.string.Transactions)));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_save")) {
                    menulist.add(new ImageTextMenuModel(getResources().getString(R.string.Save), R.drawable.saveptio, AppConstants.getMnu_save(), getResources().getString(R.string.Save)));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_borrow")) {
                    menulist.add(new ImageTextMenuModel(getResources().getString(R.string.Borrow), R.drawable.borrowptio, AppConstants.getMnu_borrow(), getResources().getString(R.string.Borrow)));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_profile_settings")) {
                    menulist.add(new ImageTextMenuModel(getResources().getString(R.string.ProfileSettings), R.drawable.profilesettingptio, AppConstants.getMnu_profile_settings(), getResources().getString(R.string.ProfileSettings)));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_visit_us")) {
                    menulist.add(new ImageTextMenuModel(getResources().getString(R.string.VisitUs), R.drawable.visitusptio, AppConstants.getMnu_visit_us(), getResources().getString(R.string.VisitUs)));
                }
            }

            collectlist = new ArrayList<>();
            for (ImageTextMenuModel menuModel : menulist) {
                if(menuModel.getIsEnabled().trim().equals("1")) {
                    collectlist.add(menuModel);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return collectlist;
    }
}
