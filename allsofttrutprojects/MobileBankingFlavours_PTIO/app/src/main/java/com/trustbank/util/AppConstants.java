package com.trustbank.util;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.trustbank.BuildConfig;
import com.trustbank.Model.BottomDynamicMenuModel;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;
import com.trustbank.activity.FrmServer;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AppConstants {


    public static boolean home_scanner = true;
    public static boolean account_group = false;
    public static boolean menu_group = false;
    public static boolean investments_group = false;
    public static boolean transactions_group = false;
    public static boolean save_group = false;
    public static boolean borrow_group = false;
    public static boolean visitus_group = false;

    public static boolean profile_group = false;
    public static boolean isInterceptorEnabled = false;
    public static boolean isReadDeviceIDFeatureEnabled = false;
    public static boolean isAutoReadOTPEnabled = false;
    public static boolean LoginInfo_isSetServerEnabled = false;
    public static boolean LoginInfo_isLogEnabled = false;
    public static boolean LoginInfo_isRootDetectionEnabled = false;
    public static boolean LoginInfo_isHookedDeviceDetectionEnabled = false;
    public static boolean isAutoReadOTPReadOnlyRegisterMobile = false;
    public static String IP = "";
    public static String INSTITUTION_ID = "";
    public static String API_KEY = "";

    public static String mnu_accounts;
    public static String mnu_account_overview;
    public static String mnu_account_details;
    public static String mnu_statement;
    public static String mnu_borrow;
    public static String mnu_loan_catalogue;
    public static String mnu_loan_eligibility;
    public static String mnu_loan_simulator;
    public static String mnu_save;
    public static String mnu_savings_account;
    public static String mnu_open_saving_account;
    public static String mnu_cheque_book_request;
    public static String mnu_stop_cheque;
    public static String mnu_cheque_status;
    public static String mnu_debit_card;
    public static String mnu_investment;
    public static String mnu_investment_account;
    public static String mnu_open_investment_account;
    public static String mnu_investment_certificate;
    public static String mnu_close_investment_account;
    public static String mnu_investment_simulator;
    public static String mnu_transactions;
    public static String mnu_loan_repayment;
    public static String mnu_intra_bank_transfer;
    public static String mnu_self_account_transfer;
    public static String mnu_interbank_transfer;
    public static String mnu_manage_beneficiaries;
    public static String mnu_cheque;
    public static String mnu_debit_card1;
    public static String mnu_bills_payment;
    public static String mnu_visit_us;
    public static String mnu_locate_agencies;
    public static String mnu_locate_atm;
    public static String mnu_contact;
    public static String mnu_schedule_visit;
    public static String mnu_profile_settings;

    public static String mnu_personal_profile;
    public static String mnu_security_center;
    public static String mnu_transaction_limit;
    public static String mnu_feedback;
    public static String mnu_saving_catalogue;
    public static String mnu_credit_catalogue;
    public static String mnu_investment_catalogue;
    public static String mnu_open_loan_account;
    private static String mnu_fingure_print;
    //Dynamic Menu Flags

    public static String getMnu_fingure_print() {
        return mnu_fingure_print;
    }

    public static void setMnu_fingure_print(String mnu_fingure_print) {
        AppConstants.mnu_fingure_print = mnu_fingure_print;
    }
    public static String getMnu_open_loan_account() {
        return mnu_open_loan_account;
    }

    public static void setMnu_open_loan_account(String mnu_open_loan_account) {
        AppConstants.mnu_open_loan_account = mnu_open_loan_account;
    }

    public static String getMnu_investment_catalogue() {
        return mnu_investment_catalogue;
    }

    public static void setMnu_investment_catalogue(String mnu_investment_catalogue) {
        AppConstants.mnu_investment_catalogue = mnu_investment_catalogue;
    }

    public static String getMnu_credit_catalogue() {
        return mnu_credit_catalogue;
    }

    public static void setMnu_credit_catalogue(String mnu_credit_catalogue) {
        AppConstants.mnu_credit_catalogue = mnu_credit_catalogue;
    }

    public static String getMnu_accounts() {
        return mnu_accounts;
    }

    public static void setMnu_accounts(String mnu_accounts) {
        AppConstants.mnu_accounts = mnu_accounts;
    }

    public static String getMnu_account_overview() {
        return mnu_account_overview;
    }

    public static void setMnu_account_overview(String mnu_account_overview) {
        AppConstants.mnu_account_overview = mnu_account_overview;
    }

    public static String getMnu_account_details() {
        return mnu_account_details;
    }

    public static void setMnu_account_details(String mnu_account_details) {
        AppConstants.mnu_account_details = mnu_account_details;
    }

    public static String getMnu_statement() {
        return mnu_statement;
    }

    public static void setMnu_statement(String mnu_statement) {
        AppConstants.mnu_statement = mnu_statement;
    }

    public static String getMnu_borrow() {
        return mnu_borrow;
    }

    public static void setMnu_borrow(String mnu_borrow) {
        AppConstants.mnu_borrow = mnu_borrow;
    }

    public static String getMnu_loan_catalogue() {
        return mnu_loan_catalogue;
    }

    public static void setMnu_loan_catalogue(String mnu_loan_catalogue) {
        AppConstants.mnu_loan_catalogue = mnu_loan_catalogue;
    }

    public static String getMnu_loan_eligibility() {
        return mnu_loan_eligibility;
    }

    public static void setMnu_loan_eligibility(String mnu_loan_eligibility) {
        AppConstants.mnu_loan_eligibility = mnu_loan_eligibility;
    }

    public static String getMnu_loan_simulator() {
        return mnu_loan_simulator;
    }

    public static void setMnu_loan_simulator(String mnu_loan_simulator) {
        AppConstants.mnu_loan_simulator = mnu_loan_simulator;
    }

    public static String getMnu_save() {
        return mnu_save;
    }

    public static void setMnu_save(String mnu_save) {
        AppConstants.mnu_save = mnu_save;
    }

    public static String getMnu_savings_account() {
        return mnu_savings_account;
    }

    public static void setMnu_savings_account(String mnu_savings_account) {
        AppConstants.mnu_savings_account = mnu_savings_account;
    }

    public static String getMnu_open_saving_account() {
        return mnu_open_saving_account;
    }

    public static void setMnu_open_saving_account(String mnu_open_saving_account) {
        AppConstants.mnu_open_saving_account = mnu_open_saving_account;
    }

    public static String getMnu_cheque_book_request() {
        return mnu_cheque_book_request;
    }

    public static void setMnu_cheque_book_request(String mnu_cheque_book_request) {
        AppConstants.mnu_cheque_book_request = mnu_cheque_book_request;
    }

    public static String getMnu_stop_cheque() {
        return mnu_stop_cheque;
    }

    public static void setMnu_stop_cheque(String mnu_stop_cheque) {
        AppConstants.mnu_stop_cheque = mnu_stop_cheque;
    }

    public static String getMnu_cheque_status() {
        return mnu_cheque_status;
    }

    public static void setMnu_cheque_status(String mnu_cheque_status) {
        AppConstants.mnu_cheque_status = mnu_cheque_status;
    }

    public static String getMnu_debit_card() {
        return mnu_debit_card;
    }

    public static void setMnu_debit_card(String mnu_debit_card) {
        AppConstants.mnu_debit_card = mnu_debit_card;
    }

    public static String getMnu_investment() {
        return mnu_investment;
    }

    public static void setMnu_investment(String mnu_investment) {
        AppConstants.mnu_investment = mnu_investment;
    }

    public static String getMnu_investment_account() {
        return mnu_investment_account;
    }

    public static void setMnu_investment_account(String mnu_investment_account) {
        AppConstants.mnu_investment_account = mnu_investment_account;
    }

    public static String getMnu_open_investment_account() {
        return mnu_open_investment_account;
    }

    public static void setMnu_open_investment_account(String mnu_open_investment_account) {
        AppConstants.mnu_open_investment_account = mnu_open_investment_account;
    }

    public static String getMnu_investment_certificate() {
        return mnu_investment_certificate;
    }

    public static void setMnu_investment_certificate(String mnu_investment_certificate) {
        AppConstants.mnu_investment_certificate = mnu_investment_certificate;
    }

    public static String getMnu_close_investment_account() {
        return mnu_close_investment_account;
    }

    public static void setMnu_close_investment_account(String mnu_close_investment_account) {
        AppConstants.mnu_close_investment_account = mnu_close_investment_account;
    }

    public static String getMnu_investment_simulator() {
        return mnu_investment_simulator;
    }

    public static void setMnu_investment_simulator(String mnu_investment_simulator) {
        AppConstants.mnu_investment_simulator = mnu_investment_simulator;
    }

    public static String getMnu_transactions() {
        return mnu_transactions;
    }

    public static void setMnu_transactions(String mnu_transactions) {
        AppConstants.mnu_transactions = mnu_transactions;
    }

    public static String getMnu_loan_repayment() {
        return mnu_loan_repayment;
    }

    public static void setMnu_loan_repayment(String mnu_loan_repayment) {
        AppConstants.mnu_loan_repayment = mnu_loan_repayment;
    }

    public static String getMnu_intra_bank_transfer() {
        return mnu_intra_bank_transfer;
    }

    public static void setMnu_intra_bank_transfer(String mnu_intra_bank_transfer) {
        AppConstants.mnu_intra_bank_transfer = mnu_intra_bank_transfer;
    }

    public static String getMnu_self_account_transfer() {
        return mnu_self_account_transfer;
    }

    public static void setMnu_self_account_transfer(String mnu_self_account_transfer) {
        AppConstants.mnu_self_account_transfer = mnu_self_account_transfer;
    }

    public static String getMnu_interbank_transfer() {
        return mnu_interbank_transfer;
    }

    public static void setMnu_interbank_transfer(String mnu_interbank_transfer) {
        AppConstants.mnu_interbank_transfer = mnu_interbank_transfer;
    }

    public static String getMnu_manage_beneficiaries() {
        return mnu_manage_beneficiaries;
    }

    public static void setMnu_manage_beneficiaries(String mnu_manage_beneficiaries) {
        AppConstants.mnu_manage_beneficiaries = mnu_manage_beneficiaries;
    }

    public static String getMnu_cheque() {
        return mnu_cheque;
    }

    public static void setMnu_cheque(String mnu_cheque) {
        AppConstants.mnu_cheque = mnu_cheque;
    }

    public static String getMnu_debit_card1() {
        return mnu_debit_card1;
    }

    public static void setMnu_debit_card1(String mnu_debit_card1) {
        AppConstants.mnu_debit_card1 = mnu_debit_card1;
    }

    public static String getMnu_bills_payment() {
        return mnu_bills_payment;
    }

    public static void setMnu_bills_payment(String mnu_bills_payment) {
        AppConstants.mnu_bills_payment = mnu_bills_payment;
    }

    public static String getMnu_visit_us() {
        return mnu_visit_us;
    }

    public static void setMnu_visit_us(String mnu_visit_us) {
        AppConstants.mnu_visit_us = mnu_visit_us;
    }

    public static String getMnu_locate_agencies() {
        return mnu_locate_agencies;
    }

    public static void setMnu_locate_agencies(String mnu_locate_agencies) {
        AppConstants.mnu_locate_agencies = mnu_locate_agencies;
    }

    public static String getMnu_locate_atm() {
        return mnu_locate_atm;
    }

    public static void setMnu_locate_atm(String mnu_locate_atm) {
        AppConstants.mnu_locate_atm = mnu_locate_atm;
    }

    public static String getMnu_contact() {
        return mnu_contact;
    }

    public static void setMnu_contact(String mnu_contact) {
        AppConstants.mnu_contact = mnu_contact;
    }

    public static String getMnu_schedule_visit() {
        return mnu_schedule_visit;
    }

    public static void setMnu_schedule_visit(String mnu_schedule_visit) {
        AppConstants.mnu_schedule_visit = mnu_schedule_visit;
    }

    public static String getMnu_profile_settings() {
        return mnu_profile_settings;
    }

    public static void setMnu_profile_settings(String mnu_profile_settings) {
        AppConstants.mnu_profile_settings = mnu_profile_settings;
    }

    public static String getMnu_personal_profile() {
        return mnu_personal_profile;
    }

    public static void setMnu_personal_profile(String mnu_personal_profile) {
        AppConstants.mnu_personal_profile = mnu_personal_profile;
    }

    public static String getMnu_security_center() {
        return mnu_security_center;
    }

    public static void setMnu_security_center(String mnu_security_center) {
        AppConstants.mnu_security_center = mnu_security_center;
    }

    public static String getMnu_transaction_limit() {
        return mnu_transaction_limit;
    }

    public static void setMnu_transaction_limit(String mnu_transaction_limit) {
        AppConstants.mnu_transaction_limit = mnu_transaction_limit;
    }

    public static String getMnu_feedback() {
        return mnu_feedback;
    }

    public static void setMnu_feedback(String mnu_feedback) {
        AppConstants.mnu_feedback = mnu_feedback;
    }

    public static String getMnu_saving_catalogue() {
        return mnu_saving_catalogue;
    }

    public static void setMnu_saving_catalogue(String mnu_saving_catalogue) {
        AppConstants.mnu_saving_catalogue = mnu_saving_catalogue;
    }

    //Menu section end

    public static String playStoreDemoUserMobile;
    public static String playStoreDemoPasswordClientid;

    public static final String SMS_ORIGIN = "ANHIVE";

    public static final String OTP_MESSAGE = "OTP_MESSAGE";
    public static final String SUB_ID = "SUB_ID";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";
    public static final String SIM_NUMBER = "SIM_NUMBER";
    public static final String SLOT_INDEX = "SLOT_INDEX";
    public static final String SIM_SUBSCRIPTION_ID = "SIM_SUBSCRIPTION_ID";
    public static final String MOB_NO = "MOB_NO";

    // special character to prefix the otp. Make sure this character appears only once in the sms.
    public static final String OTP_DELIMITER = ":";
    public static final String OTP_MSG = "OTP";
    public static final String REG_CODE_MSG = "Registration code";

    public static final String SERVER_NOT_RESPONDING = "Server not responding,Your internet connectivity is poor, please check connection and try again.";
    public static final String NO_RECORDS_FOUND = "No records found";
    public static final String MyPREFERENCES = "myprefrences";
    public static String USERMOBILENUMBER;
    public static String USEREMAILADDRESS;
    public static String USERNAME;
    public static String Mobileno;
    public static String CEDULAID;
    public static String EmailID;
    public static String CLIENTID;
    public static String Address;
    public static String ANOTHERCLIENTID;
    public static String ANOTHERCUSTOMERNAME;
    public static String SERVER_OTP = "";
    public static String SERVER_ERROR;

    public static boolean IS_CHECK_ACCESS_BROKEN;

    public static final String PREFERENCES = "preferences";
    public static final String STORE_IP = "storeIp";
    public static final String PID = "pid";

    public static final String AUTH_TOKEN = "AUTH_TOKEN";

    public static final String FILE_PATH = "/MBank";
    public static final String STATEMENTS = FILE_PATH + "/Statements/";
    public static final String PROFILE_IMAGE = FILE_PATH + "/ProfileImg/";
    public static final String NO_MEDIA = "/.nomedia";
    public static final String STATEMENT_SHEET = "Statement sheet";
    public static final String STATMENT_PDF_SHEET = STATEMENTS + "/PDF FILE/";
    public static final String STATMENT_EXCEL_SHEET = STATEMENTS + "/EXCEL FILE/";
    public static final String EXCEL_STATEMENT_FILE_NAME = "AccountStatement.xls";
    public static final String PDF_STATEMENT_FILE_NAME = "/AccountStatement.pdf";
    public static final String IMAGE_FILE_AUTHORITIES = "com.trustbank.fileprovider";
    public static final String ACC_PREFERENCES = "addNewAccPref";

    public static final int PICK_FROM_CAMERA_Photo = 1;
    public static final int PICK_FROM_FILE_GALLERY = 2;
    public static final String PROFILE_NAME = "Img_Profile.jpeg";
    public static final String THEME_CHANGE = "select_theme";

    public static final String SIM_ERROR_MSG = "SIM_ERROR_MSG";
    public static final String SIM_NOT_EXISTS = "SIM_NOT_EXISTS";
    public static final String SIM_MISMATCH = "SIM_MISMATCH";
    public static final String DOUBTFULL_TRANSACTION = "DOUBTFULL_TRANSACTION";
    public static final String DOUBTFULL = "DOUBTFUL";

    public static String profileID = "";
    public static String auth_token;
    public static String tpin;
    public static String securityCodeHint;
    public static int autoReadOtpTimeout;
    public static String play_store_validate;
    //mainScreen
    public static HashMap<String, List<DynamicMenuModel>> submenu = new HashMap<>();
    public static ArrayList<DynamicMenuModel> parentlist=new ArrayList<>();
    public static List<DynamicMenuModel>submenuList=new ArrayList<>();
    //Bottom
    public static HashMap<String, List<BottomDynamicMenuModel>> bottomsubmenu = new HashMap<>();
    public static ArrayList<BottomDynamicMenuModel> bottomparentlist=new ArrayList<>();
    public static List<BottomDynamicMenuModel>bottomsubmenuList=new ArrayList<>();

    // public static int timeOut = 150000;  //150 second.
    public static int timeOut = 1500000;  //150 second.
//    public static int timeOut = 1500;  //150 second.


    byte[] sessionKey;




    public static List<DynamicMenuModel> getSubmenuList() {
        return submenuList;
    }

    public static void setSubmenuList(List<DynamicMenuModel> submenuList) {
        AppConstants.submenuList = submenuList;
    }

    public static HashMap<String, List<DynamicMenuModel>> getSubmenu() {
        return submenu;
    }

    public static void setSubmenu(HashMap<String, List<DynamicMenuModel>> submenu) {
        AppConstants.submenu = submenu;
    }

    public static ArrayList<DynamicMenuModel> getParentlist() {
        return parentlist;
    }

    public static void setParentlist(ArrayList<DynamicMenuModel> parentlist) {
        AppConstants.parentlist = parentlist;
    }


    public static String getPlayStoreDemoUserMobile() {
        return playStoreDemoUserMobile;
    }

    public static void setPlayStoreDemoUserMobile(String playStoreDemoUserMobile) {
        AppConstants.playStoreDemoUserMobile = playStoreDemoUserMobile;
    }

    public static String getPlayStoreDemoPasswordClientid() {
        return playStoreDemoPasswordClientid;
    }

    public static void setPlayStoreDemoPasswordClientid(String playStoreDemoPasswordClientid) {
        AppConstants.playStoreDemoPasswordClientid = playStoreDemoPasswordClientid;
    }


    public static String getPlay_store_validate() {
        return play_store_validate;
    }

    public static void setPlay_store_validate(String play_store_validate) {
        AppConstants.play_store_validate = play_store_validate;
    }

    public static int getAutoReadOtpTimeout() {
        return autoReadOtpTimeout;
    }

    public static void setAutoReadOtpTimeout(int autoReadOtpTimeout) {
        AppConstants.autoReadOtpTimeout = autoReadOtpTimeout;
    }

    public static String getANOTHERCUSTOMERNAME() {
        return ANOTHERCUSTOMERNAME;
    }

    public static void setANOTHERCUSTOMERNAME(String ANOTHERCUSTOMERNAME) {
        AppConstants.ANOTHERCUSTOMERNAME = ANOTHERCUSTOMERNAME;
    }

    public static String getANOTHERCLIENTID() {
        return ANOTHERCLIENTID;
    }

    public static void setANOTHERCLIENTID(String ANOTHERCLIENTID) {
        AppConstants.ANOTHERCLIENTID = ANOTHERCLIENTID;
    }

    public static String getSecurityCodeHint() {
        return securityCodeHint;
    }

    public static void setSecurityCodeHint(String securityCodeHint) {
        AppConstants.securityCodeHint = securityCodeHint;
    }

    public static String getCLIENTID() {
        return CLIENTID;
    }

    public static void setCLIENTID(String CLIENTID) {
        AppConstants.CLIENTID = CLIENTID;
    }

    public static String getAddress() {
        return Address;
    }

    public static void setAddress(String address) {
        Address = address;
    }

    public static String getProfileID() {
        return profileID;
    }

    public static void setProfileID(String profileID) {
        AppConstants.profileID = profileID;
    }

    public static String getAuth_token() {
        SharePreferenceUtils sharePreferenceUtils = new SharePreferenceUtils(MBank.getInstance().getApplicationContext());
        return sharePreferenceUtils.getString(AppConstants.AUTH_TOKEN);
        //  return auth_token;
    }
    public static void setAuth_token(String auth_token) {

        AppConstants.auth_token = auth_token;
    }

    public static void setTPin(String TPin) {
        AppConstants.tpin = TPin;
    }

    public static String getTPIn() {
        return tpin;
    }

    public static String getUSERMOBILENUMBER() {
        return USERMOBILENUMBER;
    }

    public static void setUSERMOBILENUMBER(String USERMOBILENUMBER) {
        AppConstants.USERMOBILENUMBER = USERMOBILENUMBER;
    }

    public static String getUSEREMAILADDRESS() {
        return USEREMAILADDRESS;
    }

    public static void setUSEREMAILADDRESS(String USEREMAILADDRESS) {
        AppConstants.USEREMAILADDRESS = USEREMAILADDRESS;
    }

    public static String getEmailID() {
        return EmailID;
    }

    public static void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public static String getCEDULAID() {
        return CEDULAID;
    }

    public static void setCEDULAID(String CEDULAID) {
        AppConstants.CEDULAID = CEDULAID;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        AppConstants.USERNAME = USERNAME;
    }

    public static String getMobileno() {
        return Mobileno;
    }

    public static void setMobileno(String mobileno) {
        Mobileno = mobileno;
    }

    public static String getServerOtp() {
        return SERVER_OTP;
    }

    public static void setServerOtp(String serverOtp) {
        SERVER_OTP = serverOtp;
    }

    public static String getServerError() {
        return SERVER_ERROR;
    }

    public static void setServerError(String serverError) {
        SERVER_ERROR = serverError;
    }

    //****************Menu Flags Getters & Setters***************************//


    //****************Menu Flags Getters & Setters***************************//


    public static void setLogo(MBank mInstance, ImageView ivAppLogo) {
        if (mInstance.getPackageName().equals("com.trustbank.ptio")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_ptio));
        }
    }

    public static void setUniqueKeys(MBank mInstance) {
        if (mInstance.getPackageName().equals("com.trustbank.ptio")) {
            INSTITUTION_ID = "406";
            API_KEY = BuildConfig.api_key;
        }
    }

    public static void setIP(SharePreferenceUtils sharePreferenceUtils, Resources resources) {

        String stringIP;
        if (LoginInfo_isSetServerEnabled) {
            sharePreferenceUtils.putString(AppConstants.STORE_IP, resources.getString(R.string.set_server_IP)); //TODO For Sadhana updatation made this, above make commented.
            stringIP = sharePreferenceUtils.getString(AppConstants.STORE_IP);
            AppConstants.IP = stringIP;
        } else {
            stringIP = resources.getString(R.string.set_server_IP);
            AppConstants.IP = stringIP;
        }
    }

   /* public static void  printData(){
        try{

            String sercretKey = "a11b4f86cf186d127caa05db929d6f41";
            String message = "YB31YB41MOB521611569";
            String base64EncryptedString = "";
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digestOfPassword = md.digest(sercretKey.getBytes("UTF-8"));
            byte[] keyBytes =	Arrays.copyOf(digestOfPassword, 24);
            byte[] iv = Arrays.copyOf(digestOfPassword, 16);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher= Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new 	IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] plainTextBytes = message.getBytes("UTF-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            String result =  Base64.encodeToString(buf, Base64.DEFAULT);
            Log.e("result",result);
            //byte[] base64Bytes =  En
        }catch (Exception e){
            e.printStackTrace();
        }*/


    //base64EncryptedString = new String(base64Bytes);

    //System.out.println(base64EncryptedString);
    //}
}
