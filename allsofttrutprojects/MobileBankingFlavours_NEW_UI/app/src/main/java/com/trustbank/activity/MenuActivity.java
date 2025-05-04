package com.trustbank.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.trustbank.util.barcode.ScannedBarcodeActivity;
import com.viewpagerindicator.CirclePageIndicator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import static com.trustbank.util.AlertDialogMethod.alertDialogOk;
import static com.trustbank.util.MBank.loadAppLogo;


public class MenuActivity extends AppCompatActivity implements AlertDialogOkListener, AlertDialogListener, View.OnClickListener {

    private TrustMethods method;
    private SharedPreferences sharedPreferences;
    private SessionManager sessionManager;
    ImageView ivAppLogo;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "BankApp";
    private String customerName;
   // private ProfilePictureCapture profilePictureCapture;
    boolean isShow = false;
    private int currentPage = 0;
    private TextView textViewUserName,textViewlast_login, bank_name,bank_address;
    private AlertDialogOkListener alertDialogOkListener = this;
    private AlertDialogListener alertDialogListener = this;
    private FloatingActionButton logOutButton;
    private FloatingActionButton upiScan;
    private RecyclerView recyclerViewHoriListId;
    private int BARCODE_READER_REQUEST_CODE=1;
    ImageTextMenuModel[] newitemData = null;
    int RESULT_LOAD_IMAGE=2;
    ImageView imageViewProfile;
    List<DynamicMenuModel> getParentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.getIs_screenshotenable().equals("1")) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            );
        }
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
            ivAppLogo=findViewById(R.id.ivAppLogo);
            loadAppLogo(ivAppLogo);
            imageViewProfile = findViewById(R.id.imageViewProfile_Id);
            textViewlast_login = findViewById(R.id.textViewlast_login);
            textViewUserName=findViewById(R.id.textViewUserName_Id);
            logOutButton=findViewById(R.id.fabLogOutButton_Id);
            upiScan=findViewById(R.id.fabScanQR_Id);
            bank_name=findViewById(R.id.bank_name);
            bank_address=findViewById(R.id.bank_address);

            bank_name.setText(AppConstants.getBank_name());
            bank_address.setText(AppConstants.getBank_address());

            String encoded=method.GetProfilepicture(MenuActivity.this);
            byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
            imageViewProfile.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
           // profilePictureCapture = new ProfilePictureCapture(this, imageViewProfile);
            imageViewProfile.setOnClickListener(this);
            horizontalRecyclerView();

            /*getParentlist=AppConstants.getParentlist();*/

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
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
            //profilePictureCapture.loadExistingProfile();

            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
                if ((!TextUtils.isEmpty(AppConstants.getPlay_store_validate())) && AppConstants.getPlay_store_validate().equalsIgnoreCase("1")) {
                    ArrayList<String> stringArrayList = new ArrayList<>();
                    stringArrayList.add("/Advertisment/netbank.png");
                    stringArrayList.add("/Advertisment/netbank.png");
                    viewPager(stringArrayList);
                }/*else if(AppConstants.getUSERMOBILENUMBER().equalsIgnoreCase(AppConstants.playStoreDemoUserMobile) &&
                        AppConstants.getCLIENTID().equalsIgnoreCase(AppConstants.playStoreDemoPasswordClientid)) {
                    ArrayList<String> stringArrayList = new ArrayList<>();
                    stringArrayList.add("/Advertisment/netbank.png");
                    stringArrayList.add("/Advertisment/netbank.png");
                    viewPager(stringArrayList);
                    List<ImageTextMenuModel> newitemData = Arrays.asList(staticData());
                    MenuAdapter adapter = new MenuAdapter(MenuActivity.this, newitemData,AppConstants.getSubmenu());
                    ItemOffsetDecoration itemDecoration1 = new ItemOffsetDecoration(MenuActivity.this, R.dimen.item_offset);
                    recyclerViewMenu.addItemDecoration(itemDecoration1);
                    recyclerViewMenu.setAdapter(adapter);
                }*/ else {
                    if (NetworkUtil.getConnectivityStatus(MenuActivity.this)) {
                        if (!TextUtils.isEmpty(AppConstants.getAuth_token())) {
                            if(AppConstants.getMpassbook_menu().equalsIgnoreCase("1")){
                                upiScan.setVisibility(View.GONE);
                            }else {
                                if (AppConstants.getMnu_fundtransfer_upi().equalsIgnoreCase("1")) {
                                    upiScan.setVisibility(View.VISIBLE);
                                } else {
                                    upiScan.setVisibility(View.GONE);
                                }
                            }
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

            upiScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this, ScannedBarcodeActivity.class);
                    startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                }
            });

            logOutButton.setOnClickListener(v -> {
                AlertDialogMethod.alertDialog(MenuActivity.this, "",
                        getResources().getString(R.string.message_sure_wnt_to_logount),
                        getResources().getString(R.string.btn_yes), getResources().getString(R.string.btn_cancel),
                        12, false, alertDialogListener);
            });
            getUserProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private ImageTextMenuModel[] staticData() {
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
                   // method.message(this.ctx, this.error);
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
                new AuthenticateUserAsyncTask(MenuActivity.this, AppConstants.getUSERMOBILENUMBER()).execute();
            } else {
                AlertDialogMethod.alertDialog(MenuActivity.this, getResources().getString(R.string.error_session_expire),
                        getResources().getString(R.string.msg_refresh_token),
                        getResources().getString(R.string.btn_reload), getResources().getString(R.string.btn_cancel),
                        11, false, alertDialogListener);
                //TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
            }
        } else {
            TrustMethods.displaySimErrorDialog(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewProfile_Id:
                //profilePictureCapture.selectTheImage();
                try{
                    Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    photoPickerIntent.setType("image//*");
                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                }catch(Exception exp){
                    Log.i("Error",exp.toString());
                }
                break;
            default:
                break;
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
        private String  loginDateTime, logintime, loginDate;
        private String actionName = "GET_PROFILE";
        private ArrayList<GetUserProfileModal> getUserProfileList;
        private ArrayList<String> getbbpsfavList;
        private Map<String,String> impsRange;
        private String errorCode;
        String last_login;
        GetUserProfileModal getUserProfileModal;

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
                    last_login = profileJsonObject.has("last_login") ? profileJsonObject.getString("last_login") : "NA";
                    String client_type = profileJsonObject.has("client_type") ? profileJsonObject.getString("client_type") : "NA";
                    String atm_card_not_managed = profileJsonObject.has("atm_card_not_managed") ? profileJsonObject.getString("atm_card_not_managed") : "0";

                    AppConstants.setProfileID(profileId);
                    AppConstants.setUSEREMAILADDRESS(emailId);
                    AppConstants.setUSERNAME(customerName);
                    AppConstants.setCLIENTID(clientId);
                    AppConstants.setClient_type(client_type);   //0-Individual, 1-Non-Individual.
                    AppConstants.setAtm_card_not_managed(atm_card_not_managed);
                    method.setProfileId(MenuActivity.this, profileId);

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

                            if (accountsJsonArray.length() > 0) {
                                for (int i = 0; i < accountsJsonArray.length(); i++) {
                                    JSONObject dataJsonObject = accountsJsonArray.getJSONObject(i);

                                    String accNo = dataJsonObject.has("acno") ? dataJsonObject.getString("acno") : "NA";
                                    String mmid = dataJsonObject.has("mmid") ? dataJsonObject.getString("mmid") : "NA";
                                    String name = dataJsonObject.has("name") ? dataJsonObject.getString("name") : "NA";
                                    String pan = dataJsonObject.has("pan") ? dataJsonObject.getString("pan") : "NA";
                                    String dob = dataJsonObject.has("dob") ? dataJsonObject.getString("dob") : "NA";
                                    String acType = dataJsonObject.has("ac_type") ? dataJsonObject.getString("ac_type") : "";
                                    String ac_status = dataJsonObject.has("ac_status") ? dataJsonObject.getString("ac_status") : "";
                                    String is_imps_reg = dataJsonObject.has("is_imps_reg") ? dataJsonObject.getString("is_imps_reg") : "";
                                    String accountid = dataJsonObject.has("accountid") ? dataJsonObject.getString("accountid") : "";
                                    String orgelementid = dataJsonObject.has("orgelementid") ? dataJsonObject.getString("orgelementid") : "";
                                    String ac_type_code = dataJsonObject.has("ac_type_code") ? dataJsonObject.getString("ac_type_code") : "";
                                    String head_code = dataJsonObject.has("head_code") ? dataJsonObject.getString("head_code") : "";
                                    String cardActive = dataJsonObject.has("card_active") ? dataJsonObject.getString("card_active") : "";
                                    Log.e("head_code", head_code);

                                    getUserProfileModal = new GetUserProfileModal();
                                    getUserProfileModal.setAccNo(accNo);
                                    getUserProfileModal.setMmid(mmid);
                                    getUserProfileModal.setName(name);
                                    getUserProfileModal.setActType(acType);
                                    getUserProfileModal.setAc_status(ac_status);
                                    getUserProfileModal.setIs_imps_reg(is_imps_reg);
                                    getUserProfileModal.setAccountid(accountid);
                                    getUserProfileModal.setOrgelementid(orgelementid);
                                    getUserProfileModal.setAcTypeCode(ac_type_code);
                                    getUserProfileModal.setHeadid(head_code);
                                    getUserProfileModal.setCardActive(cardActive);
                                    AppConstants.setBankcustname(name);
                                    AppConstants.setBankcustPAN(pan);
                                    AppConstants.setBankcustDob(dob);
                                    getUserProfileList.add(getUserProfileModal);
                                }
                            }
                        }
                            JSONObject favsJsonObject = profileJsonObject.has("bbpsFavlist") ? profileJsonObject.getJSONObject("bbpsFavlist") : null;
                            if (favsJsonObject != null) {
                                if (favsJsonObject.has("error")) {
                                    error = favsJsonObject.getString("error");
                                    return error;
                                }
                                getbbpsfavList=new ArrayList<>();
                                //TODO.....................................................................
                                JSONArray favJsonArray = favsJsonObject.has("bbpsfav") ? favsJsonObject.getJSONArray("bbpsfav") : null;
                                if (favJsonArray != null) {
                                    if (favJsonArray.length() > 0) {
                                        getbbpsfavList.add("Select Your Favorites");
                                        for (int i = 0; i < favJsonArray.length(); i++) {
                                            JSONObject favJsonObject = favJsonArray.getJSONObject(i);
                                            String nick_name = favJsonObject.has("nick_name") ? favJsonObject.getString("nick_name") : "NA";
                                            getbbpsfavList.add(nick_name);
                                        }
                                    }
                                }
                            }
                            JSONObject impsrangelistJsonObject = profileJsonObject.has("impsrangelist") ? profileJsonObject.getJSONObject("impsrangelist") : null;
                            if (impsrangelistJsonObject != null) {
                                if (impsrangelistJsonObject.has("error")) {
                                    error = impsrangelistJsonObject.getString("error");
                                    return error;
                                }
                                impsRange=new HashMap<>();
                                JSONArray impsrangeJsonArray = impsrangelistJsonObject.has("impsrange") ? impsrangelistJsonObject.getJSONArray("impsrange") : null;
                                if (impsrangeJsonArray != null) {
                                    if (impsrangeJsonArray.length() > 0) {
                                        for (int i = 0; i < impsrangeJsonArray.length(); i++) {
                                            JSONObject favJsonObject = impsrangeJsonArray.getJSONObject(i);
                                            String range = favJsonObject.has("range") ? favJsonObject.getString("range") : "NA";
                                            String amount = favJsonObject.has("amount") ? favJsonObject.getString("amount") : "NA";
                                            impsRange.put(range,amount);
                                        }
                                        AppConstants.setImpsrangelist(impsRange);
                                    }
                                }
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
                        //    TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    }
                } else {
                    if(last_login.equals("null") || last_login.equals("NA")){
                        textViewlast_login.setVisibility(View.GONE);
                    }else {
                        loginDate= last_login.substring(0, 11);
                        logintime= last_login.substring(12).trim();
                        textViewlast_login.setText(" Last Login- " + loginDate + "," + logintime);
                    }

                    if (getUserProfileList != null && getUserProfileList.size() != 0) {
                        textViewUserName.setText(customerName);
                        method.saveArrayList(MenuActivity.this, getUserProfileList, "AccountListPref");
                        method.saveBBpsfavList(MenuActivity.this, getbbpsfavList, "BBPSFavlist");
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
    private class LogoutUserAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        String result;
        private String errorCode;

        public LogoutUserAsyncTask(Context ctx) {
            this.ctx = ctx;
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
                String url = TrustURL.LogoutUserUrl();

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWitAuthHeader(url, "", AppConstants.getAuth_token());
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
                    response = "true";
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
                    if (TrustMethods.isLogoutSessionExpired(errorCode)) {
                        alertDialogOk(MenuActivity.this, getResources().getString(R.string.error_session_expire),
                                "", getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        //TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                    }

                } else {
                    if (response != null) {
                        method.clearAccountsArrayList(MenuActivity.this);
                        sessionManager.logoutUser();
                    } else {
                        //TrustMethods.showSnackBarMessage("Logout failed", coordinatorLayout);
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
        } else if (resultCode == 12) {
            //sessionManager.logoutUser();
            if (TrustMethods.isSimAvailable(getApplicationContext()) && TrustMethods.isSimVerified(this)) {
                if (NetworkUtil.getConnectivityStatus(MenuActivity.this)) {
                    new LogoutUserAsyncTask(MenuActivity.this).execute();
                } else {
                    //TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                }
            } else {
                TrustMethods.displaySimErrorDialog(this);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                String upi_id = data.getStringExtra("upiid");
                String upi_name = data.getStringExtra("upi_name");
                if (upi_id != null) {
                    Intent i=new Intent(MenuActivity.this,UPIToUPITransactions.class);
                    i.putExtra("upi_id_mn",upi_id);
                    i.putExtra("upi_name_mn",upi_name);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_barcode_captured, Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode == RESULT_LOAD_IMAGE ){
            try {
                if (resultCode == Activity.RESULT_OK) {
                    Uri imageUri = data.getData();
                    if(imageUri != null){
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        method.setProfilepicture(MenuActivity.this,selectedImage);
                        MenuActivity.this.finish();
                        MenuActivity.this.startActivity(MenuActivity.this.getIntent());
                    }else{
                        Toast.makeText(MenuActivity.this, "Image not Selected", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MenuActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onDialogCancel(int resultCode) {
        //Toast.makeText(getApplicationContext(), "Dialog Cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
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
                    menulist.add(new ImageTextMenuModel("Accounts", R.drawable.accounts2, AppConstants.getMnu_accounts(), "Accounts"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_fundtransfer_ownbank")) {
                    menulist.add(new ImageTextMenuModel("Within Bank Transfer", R.drawable.within3, AppConstants.getMnu_fundtransfer_ownbank(), "Within Bank"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_fundtransfer_nefttoaccount")) {
                    menulist.add(new ImageTextMenuModel("NEFT Transfer To Account", R.drawable.neft2, AppConstants.getMnu_fundtransfer_nefttoaccount(), "NEFT"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_fundtransfer_impstoaccount")) {
                    menulist.add(new ImageTextMenuModel("IMPS Transfer To Account", R.drawable.imps2, AppConstants.getMnu_fundtransfer_impstoaccount(), "IMPS"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_fundtransfer_impstomobile")) {
                    menulist.add(new ImageTextMenuModel("IMPS Transfer To Mobile Phone", R.drawable.imps2, AppConstants.getMnu_fundtransfer_impstomobile(), "IMPS P2P"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_fundtransfer_menu_selftrf")) {
                    menulist.add(new ImageTextMenuModel("Self Transfer To Account", R.drawable.self3, AppConstants.getMnu_self_transfer_to_account(), "Self Transfer"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_upi")) {
                    menulist.add(new ImageTextMenuModel("UPI", R.drawable.upi, AppConstants.getMnu_UPI(), "UPI"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_bill")) {
                    menulist.add(new ImageTextMenuModel("Bill Pay", R.drawable.bbps, AppConstants.getMnu_bill(), "Bill pay"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_acc_stmnt")) {
                    menulist.add(new ImageTextMenuModel("mPassBook", R.drawable.mpassbook, AppConstants.getMnu_account_statement(), "mPassBook"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_ministatemnt")) {
                    menulist.add(new ImageTextMenuModel("Statement Request", R.drawable.minista, AppConstants.getMnu_accounts_menu_ministatemnt(), "Mini Statement"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_ministatemnt_cbs")) {
                    menulist.add(new ImageTextMenuModel("Statement Request CBS", R.drawable.minista, AppConstants.getMnu_accounts_menu_ministatemnt_cbs(), "Mini Statement"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_cards")) {
                    menulist.add(new ImageTextMenuModel("Cards", R.drawable.cards5, AppConstants.getMnu_cards(), "Cards"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_Services")) {
                    menulist.add(new ImageTextMenuModel("Service Request", R.drawable.service1, AppConstants.getMnu_services(), "Services"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_fundtransfer_mngbenefeciaries")) {
                    menulist.add(new ImageTextMenuModel("Manage Beneficiaries", R.drawable.ben, AppConstants.getMnu_fundtransfer_mngbenefeciaries(), "Beneficiaries"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_settings")) {
                    menulist.add(new ImageTextMenuModel("Settings", R.drawable.setting3, AppConstants.getMnu_setting(), "Settings"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_locate_us")) {
                    menulist.add(new ImageTextMenuModel("Locate US", R.drawable.location2, AppConstants.getMnu_locate_us(), "Locate US"));
                } else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_need_help")) {
                    menulist.add(new ImageTextMenuModel("Need Help", R.drawable.i1, AppConstants.getMnu_need_help(), "Need Help"));
                }
                else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_accountdetails")) {
                    menulist.add(new ImageTextMenuModel("Account Details", R.drawable.accountsd, AppConstants.getMnu_accounts_menu_accountdetails(), "Account Details"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_balenquiry")) {
                    menulist.add(new ImageTextMenuModel("Balance Enquiry", R.drawable.balance, AppConstants.getMnu_accounts_menu_balenquiry(), "Balance Enquiry"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_balenquiry_cbs")) {
                    menulist.add(new ImageTextMenuModel("Balance Enquiry CBS", R.drawable.balance, AppConstants.getMnu_accounts_menu_balenquiry_cbs(), "Balance Enquiry"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_last5imps")) {
                    menulist.add(new ImageTextMenuModel("Last 5 Transactions", R.drawable.last5, AppConstants.getMnu_accounts_menu_last5imps(), "Last 5 Transactions"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_last5imps_cbs")) {
                    menulist.add(new ImageTextMenuModel("Last 5 Transactions CBS", R.drawable.last5, AppConstants.getMnu_accounts_menu_last5imps_cbs(), "Last 5 Transactions"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_showmmid")) {
                    menulist.add(new ImageTextMenuModel("Show MMID", R.drawable.mmid, AppConstants.getMnu_accounts_menu_showmmid(), "Show MMID"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_showmmid_cbs")) {
                    menulist.add(new ImageTextMenuModel("Show MMID CBS", R.drawable.mmid, AppConstants.getMnu_accounts_menu_showmmid_cbs(), "Show MMID"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_neftenquiry")) {
                    menulist.add(new ImageTextMenuModel("NEFT Enquiry", R.drawable.neftenquery, AppConstants.getMnu_accounts_menu_neftenquiry(), "NEFT Enquiry"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_imps_transction_status")) {
                    menulist.add(new ImageTextMenuModel("Check Transaction Status", R.drawable.check_trx_status, AppConstants.getMnu_check_imps_transaction_status(), "Check Transaction Status"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_fundtransfer_upi")) {
                    menulist.add(new ImageTextMenuModel("UPI Transfer", R.drawable.upi, AppConstants.getMnu_fundtransfer_upi(),"UPI Transfer"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_create_new_qr")) {
                    menulist.add(new ImageTextMenuModel("Create New QR Code", R.drawable.create_new, AppConstants.getMnu_fundtransfer_upi_create_qr(),"Create QR"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_fetch_qr")) {
                    menulist.add(new ImageTextMenuModel("Display QR Code", R.drawable.display_qr, AppConstants.getMnu_fundtransfer_upi_get_qr(),"Display QR"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_cheqbkreq")) {
                    menulist.add(new ImageTextMenuModel("Cheque Book Request", R.drawable.chique, AppConstants.getMnu_checkbook_request(),"Cheque Book Request"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_chq_status")) {
                    menulist.add(new ImageTextMenuModel("Cheque Status", R.drawable.chiquesta,AppConstants.getInqueriChquebookStatus(),"Cheque Status"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_stop_chq")) {
                    menulist.add(new ImageTextMenuModel("Stop Cheque", R.drawable.stopchique,AppConstants.getStopChequebookStatus(),"Stop Cheque"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_pps_request")) {
                    menulist.add(new ImageTextMenuModel("Positive Pay Request", R.drawable.last5,AppConstants.getMnu_pps_request(),"Positive Pay Request"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_accounts_menu_pps_request_enquiry")) {
                    menulist.add(new ImageTextMenuModel("Positive Pay Enquiry", R.drawable.mmid,AppConstants.getMnu_pps_request_enquiry(),"Positive Pay Enquiry"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_mandate_cancel")) {
                    menulist.add(new ImageTextMenuModel("ECS Mandate Cancellation", R.drawable.ecs, AppConstants.getMnu_mandate_cancel(),"ECS Cancellation") );
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_rd_fd_reckoner")) {
                    menulist.add(new ImageTextMenuModel("FD/RD Reckoner", R.drawable.rdfd_recknor, AppConstants.getMnu_rd_fd_reckoner(),"Reckoner") );
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_amortization_chart")) {
                    menulist.add(new ImageTextMenuModel("EMI Calculator", R.drawable.amortization_chart, AppConstants.getMnu_amortization_chart(),"EMI Calculator"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_FAQ")) {
                    menulist.add(new ImageTextMenuModel("FAQs", R.drawable.faqs, AppConstants.getMnu_faq(),"FAQs"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_contact_us")) {
                    menulist.add(new ImageTextMenuModel("Contact US", R.drawable.contactus, AppConstants.getMnu_contact_us(),"Contact US"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_about_us")) {
                    menulist.add(new ImageTextMenuModel("About US", R.drawable.aboutus, AppConstants.getMnu_about_us(),"About US"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_locate_atms")) {
                    menulist.add(new ImageTextMenuModel("Locate ATM", R.drawable.locateatm,AppConstants.getMnu_locate_atms(),"Locate ATM") );
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_locate_branch")) {
                    menulist.add(new ImageTextMenuModel("Locate Branch", R.drawable.locatebranch,AppConstants.getMnu_locate_branch() ,"Locate Branch"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_block_debit_card_cbs")) {
                    menulist.add(new ImageTextMenuModel("Block Debit Card CBS", R.drawable.block_debit_card_cbs, AppConstants.getMnu_block_debit_card_cbs(),"Block Debit Card CBS") );
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_account_block_debit_card")) {
                    menulist.add(new ImageTextMenuModel("Block Debit Card", R.drawable.block_debit_card, AppConstants.getMnu_block_debit_card(),"Block Debit Card"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_debit_card_pin_generation")) {
                    menulist.add(new ImageTextMenuModel("Debit Card Pin Generation", R.drawable.debit_card_pin_generation,AppConstants.getMnu_debit_card_pin_generation(),"Debit Card Pin Generation"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_block_debit_card_switch")) {
                    menulist.add(new ImageTextMenuModel("Block Debit Card Switch", R.drawable.block_debit_card, AppConstants.getMnu_block_debit_card_switch(),"Block Debit Card Switch"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_bbps_finacus")) {
                    menulist.add(8, new ImageTextMenuModel("Bill Pay", R.drawable.bbps_mnemonic, AppConstants.getMnu_bbps(),"Bill Pay"));
                }else if (parentmenu.getMenucode().equalsIgnoreCase("mnu_privacypolicy")) {
                    menulist.add(new ImageTextMenuModel("Privacy Policy", R.drawable.aboutus, AppConstants.getMnu_privacypolicy(),"Privacy Policy"));
                }else {

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
