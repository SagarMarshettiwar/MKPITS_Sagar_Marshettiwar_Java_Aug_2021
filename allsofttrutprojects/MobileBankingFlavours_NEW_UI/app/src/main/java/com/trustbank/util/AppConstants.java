package com.trustbank.util;

import android.content.res.Resources;
import android.widget.ImageView;

import com.trustbank.BuildConfig;
import com.trustbank.Model.BottomDynamicMenuModel;
import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConstants {


    public static boolean home_scanner = true;
    public static boolean account_group = false;
    public static boolean cards_group = false;
    public static boolean service_group = false;
    public static boolean need_help_group = false;
    public static boolean locate_us_group = false;
    public static boolean upi_group = false;

    public static String  filelocation= "Barcode_" + "${System.currentTimeMillis()}.jpg";
    public static boolean isInterceptorEnabled = false;
    public static boolean isReadDeviceIDFeatureEnabled = false;
    public static boolean isAutoReadOTPEnabled = false;
    public static boolean LoginInfo_isSetServerEnabled = false;
    public static boolean LoginInfo_isLogEnabled = false;
    public static boolean LoginInfo_isRootDetectionEnabled = false;
    public static boolean Detuct_Application = false;
    public static boolean LoginInfo_isHookedDeviceDetectionEnabled = false;
    public static boolean isAutoReadOTPReadOnlyRegisterMobile = false;
    public static String IP = "";
    public static String INSTITUTION_ID = "";
    public static String API_KEY = "";

    //Dynamic Menu Flags
    private static String mnu_privacypolicy;
    private static Map<String, String> impsrangelist;
    private static String mnu_accounts;

    private static String mnu_bbps_finacus;
    private static String mnu_bbps_bill_payment_finacus;
    private static String mnu_bbps_transaction_history_finacus;
    private static String mnu_bbps_complaint_management_finacus;
    private static String mnu_bbps_complaint_status_finacus;
    private static String mnu_bbps_complaint_history_finacus;
    private static String mnu_bbps_transaction_inquiry_finacus;
    private static String mnu_bbps_get_duplicate_receipt_finacus;
    private static String mnu_UPI;
    private static String mnu_bill;
    private static String welcome_message;
    private static String mnu_fundtransfer;
    private static String mnu_locate_atms;
    private static String mnu_locate_branch;
    private static String mnu_contact_us;
    private static String mnu_about_us;
    private static String mnu_accounts_menu_accountdetails;
    private static String mnu_accounts_menu_balenquiry;
    private static String mnu_accounts_menu_ministatemnt;
    private static String mnu_accounts_menu_showmmid;
    private static String mnu_accounts_menu_last5imps;
    private static String Mnu_fundtransfer_upi_get_qr;

    private static String mnu_accounts_menu_balenquiry_cbs;
    private static String mnu_accounts_menu_ministatemnt_cbs;
    private static String mnu_accounts_menu_showmmid_cbs;
    private static String mnu_accounts_menu_last5imps_cbs;

    private static String mnu_accounts_menu_neftenquiry;
    private static String mnu_fundtransfer_ownbank;
    private static String mnu_fundtransfer_impstoaccount;
    private static String mnu_fundtransfer_nefttoaccount;
    private static String mnu_fundtransfer_impstomobile;
    private static String mnu_fundtransfer_upi;
    private static String mpassbook_menu;
    private static String mnu_fundtransfer_mngbenefeciaries;
    private static String mnu_self_transfer_to_account;
    private static String mnu_check_imps_transaction_status;
    private static String mnu_neft_trans_switch_transaction;
    private static String mnu_bill_pay;
    private static String mnu_fundtransfer_upi_create_qr;
    private static String mnu_fundtransfer_upi_get_qr;

    private static String mnu_rd_fd_reckoner;

    private static String mnu_amortization_chart;

    private static String mnu_bill_pay_complaint_management;
    private static String mnu_bill_pay_register_complaints;
    private static String mnu_bill_pay_track_complaints;


    private static String mnu_faq;
    private static String mnu_checkbook_request;
    private static String mnu_account_statement;

    private static String mnu_setting;
    private static String mnu_cards;
    private static String mnu_debit_card_set_channel;
    private static String mnu_form15GH;
    private static String mnu_standing_instructions;
    private static String mnu_services;
    private static String mnu_locate_us;
    private static String mnu_need_help;
    private static String mnu_nominee_management;
    private static String mnu_fingure_print;
    private static String mnu_Change_MPin;
    private static String mnu_Change_TPin;
    private static String mnu_Reset_TPin;
    private static String mnu_Limit_Transaction;
    private static String mnu_beneficiary_own_bank;
    private static String mnu_beneficiary_imps_neft_account_bank;
    private static String mnu_beneficiary_imps_mobile_bank;
    private static String stopChequebookStatus;
    private static String inqueriChquebookStatus;
    private static String mnu_pps_request;
    private static String mnu_pps_request_enquiry;
    private static String mnu_block_debit_card;
    private static String checkImpsTransStatusFundTransfer;
    private static String mnu_verify_beneficiary_name;
    private static String mnu_fundtransfer_upi_collect_money; //TODO need to update on procedure.
    private static String mnu_block_debit_card_switch; //TODO Need to update on proc.
    private static String mnu_block_debit_card_cbs;
    private static String mnu_debit_card_pin_generation;
    private static String mnu_debit_card_limit;
    private static String mnu_temp_block_debit_card;
    private static String mnu_debit_card_pin_verify_finacus;
    private static String mnu_rdfd_acc_open;


    public static Map<String, String> getImpsrangelist() {
        return impsrangelist;
    }

    public static void setImpsrangelist(Map<String, String> impsrangelist) {
        AppConstants.impsrangelist = impsrangelist;
    }

    public static String getMnu_temp_block_debit_card() {
        return mnu_temp_block_debit_card;
    }

    public static void setMnu_temp_block_debit_card(String mnu_temp_block_debit_card) {
        AppConstants.mnu_temp_block_debit_card = mnu_temp_block_debit_card;
    }

    public static String getMnu_standing_instructions() {
        return mnu_standing_instructions;
    }

    public static void setMnu_standing_instructions(String mnu_standing_instructions) {
        AppConstants.mnu_standing_instructions = mnu_standing_instructions;
    }

    public static String getMnu_form15GH() {
        return mnu_form15GH;
    }

    public static void setMnu_form15GH(String mnu_form15GH) {
        AppConstants.mnu_form15GH = mnu_form15GH;
    }

    private static String mnu_mandate_cancel;
    //Menu section end
    public static String playStoreDemoUserMobile;
    public static String playStoreDemoPasswordClientid;
    public static String restrictedapppackage1;
    public static String restrictedapppackage2;
    public static boolean Dynamic_keybord_enable;
    public static boolean four_digit_pin_enable ;

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
    public static String CLIENTID;
    public static String client_type;
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
    public static String mobile_number_verify;
    public static String isMPassbook;

    public static String bank_name;
    public static String bankcustname;
    public static String bankcustPAN;
    public static String bankcustDob;
    public static String bank_address;
    public static int  registrationTime;
    public static String  switch_for_card_no;
    public static String  atm_card_not_managed;
    public static String  crypt_algo;
    public static String  imps_charges_msg;
    public static String getIs_screenshotenable() {
        return is_screenshotenable;
    }

    public static void setIs_screenshotenable(String is_screenshotenable) {
        AppConstants.is_screenshotenable = is_screenshotenable;
    }

    public static String is_screenshotenable;

    public static String sms_verify_number;

    public static List<String> neft_list;

    public static String getBankcustname() {
        return bankcustname;
    }

    public static void setBankcustname(String bankcustname) {
        AppConstants.bankcustname = bankcustname;
    }

    public static List<String> getCard_list() {
        return card_list;
    }

    public static void setCard_list(List<String> card_list) {
        AppConstants.card_list = card_list;
    }

    public static List<String> card_list;

    public static List<String> getMpassbook_list() {
        return mpassbook_list;
    }

    public static void setMpassbook_list(List<String> mpassbook_list) {
        AppConstants.mpassbook_list = mpassbook_list;
    }

    public static List<String> mpassbook_list;

    public static List<String> getMiniststement_list() {
        return ministstement_list;
    }

    public static void setMiniststement_list(List<String> ministstement_list) {
        AppConstants.ministstement_list = ministstement_list;
    }

    public static List<String> ministstement_list;

    public static List<String> getBalance_list() {
        return balance_list;
    }

    public static void setBalance_list(List<String> balance_list) {
        AppConstants.balance_list = balance_list;
    }

    public static String getBankcustPAN() {
        return bankcustPAN;
    }

    public static void setBankcustPAN(String bankcustPAN) {
        AppConstants.bankcustPAN = bankcustPAN;
    }

    public static String getBankcustDob() {
        return bankcustDob;
    }

    public static void setBankcustDob(String bankcustDob) {
        AppConstants.bankcustDob = bankcustDob;
    }

    public static List<String> balance_list;

    public static List<String> getImpslist() {
        return impslist;
    }

    public static void setImpslist(List<String> impslist) {
        AppConstants.impslist = impslist;
    }

    public static List<String> impslist;

    public static List<String> getUpilist() {
        return upilist;
    }

    public static void setUpilist(List<String> upilist) {
        AppConstants.upilist = upilist;
    }

    public static List<String> upilist;

    public static List<String> getSelf_dr() {
        return self_dr;
    }

    public static void setSelf_dr(List<String> self_dr) {
        AppConstants.self_dr = self_dr;
    }

    public static List<String> self_dr;

    public static List<String> getSelf_cr() {
        return self_cr;
    }

    public static void setSelf_cr(List<String> self_cr) {
        AppConstants.self_cr = self_cr;
    }

    public static List<String> self_cr;

    public static List<String> getWithin_cr() {
        return within_cr;
    }

    public static void setWithin_cr(List<String> within_cr) {
        AppConstants.within_cr = within_cr;
    }

    public static List<String> within_cr;
    public static List<String> within_list;
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
    public static String privacypolicyurl;
    public static String IMPS_registered;
    public static String show_tpin;

    public static String getShow_tpin() {
        return show_tpin;
    }

    public static void setShow_tpin(String show_tpin) {
        AppConstants.show_tpin = show_tpin;
    }

    public static String getIMPS_registered() {
        return IMPS_registered;
    }

    public static void setIMPS_registered(String IMPS_registered) {
        AppConstants.IMPS_registered = IMPS_registered;
    }

    public static String getPrivacypolicyurl() {
        return privacypolicyurl;
    }

    public static void setPrivacypolicyurl(String privacypolicyurl) {
        AppConstants.privacypolicyurl = privacypolicyurl;
    }
    public static String getIsMPassbook() {
        return isMPassbook;
    }

    public static void setIsMPassbook(String isMPassbook) {
        AppConstants.isMPassbook = isMPassbook;
    }

    public static int getRegistrationTime() {
        return registrationTime;
    }

    public static void setRegistrationTime(int registrationTime) {
        AppConstants.registrationTime = registrationTime;
    }
    public static String getBank_name() {
        return bank_name;
    }

    public static void setBank_name(String bank_name) {
        AppConstants.bank_name = bank_name;
    }

    public static String getBank_address() {
        return bank_address;
    }

    public static void setBank_address(String bank_address) {
        AppConstants.bank_address = bank_address;
    }

    public static String getSwitchForCardNo() {
        return switch_for_card_no;
    }

    public static void setSwitchForCardNo(String switch_for_card_no) {
        AppConstants.switch_for_card_no = switch_for_card_no;
    }

    public static String getImps_charges_msg() {
        return imps_charges_msg;
    }

    public static void setImps_charges_msg(String imps_charges_msg) {
        AppConstants.imps_charges_msg = imps_charges_msg;
    }

    public static String getCrypt_algo() {
        return crypt_algo;
    }

    public static void setCrypt_algo(String crypt_algo) {
        AppConstants.crypt_algo = crypt_algo;
    }

    public static String getAtm_card_not_managed() {
        return atm_card_not_managed;
    }

    public static void setAtm_card_not_managed(String atm_card_not_managed) {
        AppConstants.atm_card_not_managed = atm_card_not_managed;
    }

    public static List<String> getNeft_list() {
        return neft_list;
    }

    public static void setNeft_list(List<String> neft_list) {
        AppConstants.neft_list = neft_list;
    }

    public static List<String> getWithin_list() {
        return within_list;
    }

    public static void setWithin_list(List<String> within_list) {
        AppConstants.within_list = within_list;
    }

    public static String getMobile_number_verify() {
        return mobile_number_verify;
    }

    public static void setMobile_number_verify(String mobile_number_verify) {
        AppConstants.mobile_number_verify = mobile_number_verify;
    }

    public static String getSms_verify_number() {
        return sms_verify_number;
    }

    public static void setSms_verify_number(String sms_verify_number) {
        AppConstants.sms_verify_number = sms_verify_number;
    }

    public static String getMnu_debit_card_limit() {
        return mnu_debit_card_limit;
    }

    public static void setMnu_debit_card_limit(String mnu_debit_card_limit) {
        AppConstants.mnu_debit_card_limit = mnu_debit_card_limit;
    }


/* public static HashMap<String, List<BottomDynamicMenuModel>> getBottomsubmenu() {
        return bottomsubmenu;
    }

    public static void setBottomsubmenu(HashMap<String, List<BottomDynamicMenuModel>> bottomsubmenu) {
        AppConstants.bottomsubmenu = bottomsubmenu;
    }*/

    public static ArrayList<BottomDynamicMenuModel> getBottomparentlist() {
        return bottomparentlist;
    }

    public static void setBottomparentlist(ArrayList<BottomDynamicMenuModel> bottomparentlist) {
        AppConstants.bottomparentlist = bottomparentlist;
    }

   /* public static List<BottomDynamicMenuModel> getBottomsubmenuList() {
        return bottomsubmenuList;
    }

    public static void setBottomsubmenuList(List<BottomDynamicMenuModel> bottomsubmenuList) {
        AppConstants.bottomsubmenuList = bottomsubmenuList;
    }*/

    public static String getMnu_debit_card_pin_verify_finacus() {
        return mnu_debit_card_pin_verify_finacus;
    }

    public static void setMnu_debit_card_pin_verify_finacus(String mnu_debit_card_pin_verify_finacus) {
        AppConstants.mnu_debit_card_pin_verify_finacus = mnu_debit_card_pin_verify_finacus;
    }

    public static String getMnu_rdfd_acc_open() {
        return mnu_rdfd_acc_open;
    }

    public static void setMnu_rdfd_acc_open(String mnu_rdfd_acc_open) {
        AppConstants.mnu_rdfd_acc_open = mnu_rdfd_acc_open;
    }

    public static String getMnu_bbps_bill_payment_finacus() {
        return mnu_bbps_bill_payment_finacus;
    }

    public static void setMnu_bbps_bill_payment_finacus(String mnu_bbps_bill_payment_finacus) {
        AppConstants.mnu_bbps_bill_payment_finacus = mnu_bbps_bill_payment_finacus;
    }

    public static String getMnu_bbps_transaction_history_finacus() {
        return mnu_bbps_transaction_history_finacus;
    }

    public static void setMnu_bbps_transaction_history_finacus(String mnu_bbps_transaction_history_finacus) {
        AppConstants.mnu_bbps_transaction_history_finacus = mnu_bbps_transaction_history_finacus;
    }

    public static String getMnu_bbps_complaint_management_finacus() {
        return mnu_bbps_complaint_management_finacus;
    }

    public static void setMnu_bbps_complaint_management_finacus(String mnu_bbps_complaint_management_finacus) {
        AppConstants.mnu_bbps_complaint_management_finacus = mnu_bbps_complaint_management_finacus;
    }

    public static String getMnu_bbps_complaint_status_finacus() {
        return mnu_bbps_complaint_status_finacus;
    }

    public static void setMnu_bbps_complaint_status_finacus(String mnu_bbps_complaint_status_finacus) {
        AppConstants.mnu_bbps_complaint_status_finacus = mnu_bbps_complaint_status_finacus;
    }

    public static String getMnu_bbps_complaint_history_finacus() {
        return mnu_bbps_complaint_history_finacus;
    }

    public static void setMnu_bbps_complaint_history_finacus(String mnu_bbps_complaint_history_finacus) {
        AppConstants.mnu_bbps_complaint_history_finacus = mnu_bbps_complaint_history_finacus;
    }

    public static String getMnu_bbps_transaction_inquiry_finacus() {
        return mnu_bbps_transaction_inquiry_finacus;
    }

    public static void setMnu_bbps_transaction_inquiry_finacus(String mnu_bbps_transaction_inquiry_finacus) {
        AppConstants.mnu_bbps_transaction_inquiry_finacus = mnu_bbps_transaction_inquiry_finacus;
    }

    public static String getMnu_bbps_get_duplicate_recipt_finacus() {
        return mnu_bbps_get_duplicate_receipt_finacus;
    }

    public static void setMnu_bbps_get_duplicate_recipt_finacus(String mnu_bbps_get_duplicate_receipt_finacus) {
        AppConstants.mnu_bbps_get_duplicate_receipt_finacus = mnu_bbps_get_duplicate_receipt_finacus;
    }

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

    public static String getRestrictedapppackage1() {
        return restrictedapppackage1;
    }

    public static void setRestrictedapppackage1(String restrictedapppackage1) {
        AppConstants.restrictedapppackage1 = restrictedapppackage1;
    }

    public static String getRestrictedapppackage2() {
        return restrictedapppackage2;
    }

    public static void setRestrictedapppackage2(String restrictedapppackage2) {
        AppConstants.restrictedapppackage2 = restrictedapppackage2;
    }

    public static String getMnu_cards() {
        return mnu_cards;
    }

    public static void setMnu_cards(String mnu_cards) {
        AppConstants.mnu_cards = mnu_cards;
    }

    public static String getMnu_debit_card_set_channel() {
        return mnu_debit_card_set_channel;
    }

    public static void setMnu_debit_card_set_channel(String mnu_debit_card_set_channel) {
        AppConstants.mnu_debit_card_set_channel = mnu_debit_card_set_channel;
    }
    public static String getMnu_services() {
        return mnu_services;
    }

    public static void setMnu_services(String mnu_services) {
        AppConstants.mnu_services = mnu_services;
    }

    public static String getMnu_locate_us() {
        return mnu_locate_us;
    }

    public static void setMnu_locate_us(String mnu_locate_us) {
        AppConstants.mnu_locate_us = mnu_locate_us;
    }

    public static String getMnu_fingure_print() {
        return mnu_fingure_print;
    }

    public static void setMnu_fingure_print(String mnu_fingure_print) {
        AppConstants.mnu_fingure_print = mnu_fingure_print;
    }

    public static String getMnu_nominee_management() {
        return mnu_nominee_management;
    }

    public static void setMnu_nominee_management(String mnu_nominee_management) {
        AppConstants.mnu_nominee_management = mnu_nominee_management;
    }

    public static String getMnu_need_help() {
        return mnu_need_help;
    }

    public static void setMnu_need_help(String mnu_need_help) {
        AppConstants.mnu_need_help = mnu_need_help;
    }
    public static String getMnu_block_debit_card_cbs() {
        return mnu_block_debit_card_cbs;
    }

    public static void setMnu_block_debit_card_cbs(String mnu_block_debit_card_cbs) {
        AppConstants.mnu_block_debit_card_cbs = mnu_block_debit_card_cbs;
    }

    public static String getMpassbook_menu() {
        return mpassbook_menu;
    }

    public static void setMpassbook_menu(String mpassbook_menu) {
        AppConstants.mpassbook_menu = mpassbook_menu;
    }

    public static String getMnu_rd_fd_reckoner() {
        return mnu_rd_fd_reckoner;
    }

    public static void setMnu_rd_fd_reckoner(String mnu_rd_fd_reckoner) {
        AppConstants.mnu_rd_fd_reckoner = mnu_rd_fd_reckoner;
    }

    public static String getMnu_amortization_chart() {
        return mnu_amortization_chart;
    }

    public static void setMnu_amortization_chart(String mnu_amortization_chart) {
        AppConstants.mnu_amortization_chart = mnu_amortization_chart;
    }

    public static String getMnu_UPI() {
        return mnu_UPI;
    }

    public static void setMnu_UPI(String mnu_UPI) {
        AppConstants.mnu_UPI = mnu_UPI;
    }

    public static String getMnu_bbps() {
        return mnu_bbps_finacus;
    }

    public static void setMnu_bbps(String mnu_bbps_finacus) {
        AppConstants.mnu_bbps_finacus = mnu_bbps_finacus;
    }
    public static String getMnu_bill() {
        return mnu_bill;
    }

    public static void setMnu_bill(String mnu_bill) {
        AppConstants.mnu_bill = mnu_bill;
    }

    public static String getMnu_fundtransfer_upi_create_qr() {
        return mnu_fundtransfer_upi_create_qr;
    }

    public static void setMnu_fundtransfer_upi_create_qr(String mnu_fundtransfer_upi_create_qr) {
        AppConstants.mnu_fundtransfer_upi_create_qr = mnu_fundtransfer_upi_create_qr;
    }

    public static String getMnu_fundtransfer_upi_get_qr() {
        return mnu_fundtransfer_upi_get_qr;
    }

    public static void setMnu_fundtransfer_upi_get_qr(String mnu_fundtransfer_upi_get_qr) {
        AppConstants.mnu_fundtransfer_upi_get_qr = mnu_fundtransfer_upi_get_qr;
    }



    public static String getMnu_mandate_cancel() {
        return mnu_mandate_cancel;
    }

    public static void setMnu_mandate_cancel(String mnu_mandate_cancel) {
        AppConstants.mnu_mandate_cancel = mnu_mandate_cancel;
    }

    public static String getMnu_block_debit_card_switch() {
        return mnu_block_debit_card_switch;
    }

    public static void setMnu_block_debit_card_switch(String mnu_block_debit_card_switch) {
        AppConstants.mnu_block_debit_card_switch = mnu_block_debit_card_switch;
    }

    public static String getMnu_debit_card_pin_generation() {
        return mnu_debit_card_pin_generation;
    }

    public static void setMnu_debit_card_pin_generation(String mnu_debit_card_pin_generation) {
        AppConstants.mnu_debit_card_pin_generation = mnu_debit_card_pin_generation;
    }

    public static String getMnu_fundtransfer_upi_collect_money() {
        return mnu_fundtransfer_upi_collect_money;
    }

    public static void setMnu_fundtransfer_upi_collect_money(String mnu_fundtransfer_upi_collect_money) {
        AppConstants.mnu_fundtransfer_upi_collect_money = mnu_fundtransfer_upi_collect_money;
    }

    public static String getCheckImpsTransStatusFundTransfer() {
        return checkImpsTransStatusFundTransfer;
    }

    public static void setCheckImpsTransStatusFundTransfer(String checkImpsTransStatusFundTransfer) {
        AppConstants.checkImpsTransStatusFundTransfer = checkImpsTransStatusFundTransfer;
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

    public static String getMnu_fundtransfer_upi() {
        return mnu_fundtransfer_upi;
    }

    public static void setMnu_fundtransfer_upi(String mnu_fundtransfer_upi) {
        AppConstants.mnu_fundtransfer_upi = mnu_fundtransfer_upi;
    }

    public static String getMnu_bill_pay() {
        return mnu_bill_pay;
    }

    public static void setMnu_bill_pay(String mnu_bill_pay) {
        AppConstants.mnu_bill_pay = mnu_bill_pay;
    }

    public static String getMnu_neft_trans_switch_transaction() {
        return mnu_neft_trans_switch_transaction;
    }

    public static void setMnu_neft_trans_switch_transaction(String mnu_neft_trans_switch_transaction) {
        AppConstants.mnu_neft_trans_switch_transaction = mnu_neft_trans_switch_transaction;
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

    public static String getMnu_block_debit_card() {
        return mnu_block_debit_card;
    }

    public static void setMnu_block_debit_card(String mnu_block_debit_card) {
        AppConstants.mnu_block_debit_card = mnu_block_debit_card;
    }

    public static String getMnu_pps_request() {
        return mnu_pps_request;
    }

    public static void setMnu_pps_request(String mnu_pps_request) {
        AppConstants.mnu_pps_request = mnu_pps_request;
    }

    public static String getMnu_pps_request_enquiry() {
        return mnu_pps_request_enquiry;
    }

    public static void setMnu_pps_request_enquiry(String mnu_pps_request_enquiry) {
        AppConstants.mnu_pps_request_enquiry = mnu_pps_request_enquiry;
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

    public static String getMnu_beneficiary_own_bank() {
        return mnu_beneficiary_own_bank;
    }

    public static void setMnu_beneficiary_own_bank(String mnu_beneficiary_own_bank) {
        AppConstants.mnu_beneficiary_own_bank = mnu_beneficiary_own_bank;
    }

    public static String getMnu_beneficiary_imps_neft_account_bank() {
        return mnu_beneficiary_imps_neft_account_bank;
    }

    public static void setMnu_beneficiary_imps_neft_account_bank(String mnu_beneficiary_imps_neft_account_bank) {
        AppConstants.mnu_beneficiary_imps_neft_account_bank = mnu_beneficiary_imps_neft_account_bank;
    }

    public static String getMnu_beneficiary_imps_mobile_bank() {
        return mnu_beneficiary_imps_mobile_bank;
    }

    public static void setMnu_beneficiary_imps_mobile_bank(String mnu_beneficiary_imps_mobile_bank) {
        AppConstants.mnu_beneficiary_imps_mobile_bank = mnu_beneficiary_imps_mobile_bank;
    }

    public static String getMnu_accounts_menu_balenquiry_cbs() {
        return mnu_accounts_menu_balenquiry_cbs;
    }

    public static void setMnu_accounts_menu_balenquiry_cbs(String mnu_accounts_menu_balenquiry_cbs) {
        AppConstants.mnu_accounts_menu_balenquiry_cbs = mnu_accounts_menu_balenquiry_cbs;
    }

    public static String getMnu_accounts_menu_ministatemnt_cbs() {
        return mnu_accounts_menu_ministatemnt_cbs;
    }

    public static void setMnu_accounts_menu_ministatemnt_cbs(String mnu_accounts_menu_ministatemnt_cbs) {
        AppConstants.mnu_accounts_menu_ministatemnt_cbs = mnu_accounts_menu_ministatemnt_cbs;
    }

    public static String getMnu_accounts_menu_showmmid_cbs() {
        return mnu_accounts_menu_showmmid_cbs;
    }

    public static void setMnu_accounts_menu_showmmid_cbs(String mnu_accounts_menu_showmmid_cbs) {
        AppConstants.mnu_accounts_menu_showmmid_cbs = mnu_accounts_menu_showmmid_cbs;
    }

    public static String getMnu_accounts_menu_last5imps_cbs() {
        return mnu_accounts_menu_last5imps_cbs;
    }

    public static void setMnu_accounts_menu_last5imps_cbs(String mnu_accounts_menu_last5imps_cbs) {
        AppConstants.mnu_accounts_menu_last5imps_cbs = mnu_accounts_menu_last5imps_cbs;
    }

    public static String getSecurityCodeHint() {
        return securityCodeHint;
    }

    public static void setSecurityCodeHint(String securityCodeHint) {
        AppConstants.securityCodeHint = securityCodeHint;
    }

    public static String getInqueriChquebookStatus() {
        return inqueriChquebookStatus;
    }

    public static void setInqueriChquebookStatus(String inqueriChquebookStatus) {
        AppConstants.inqueriChquebookStatus = inqueriChquebookStatus;
    }

    public static String getStopChequebookStatus() {
        return stopChequebookStatus;
    }

    public static void setStopChequebookStatus(String stopChequebookStatus) {
        AppConstants.stopChequebookStatus = stopChequebookStatus;
    }

    public static String getClient_type() {
        return client_type;
    }

    public static void setClient_type(String client_type) {
        AppConstants.client_type = client_type;
    }

    public static String getCLIENTID() {
        return CLIENTID;
    }

    public static void setCLIENTID(String CLIENTID) {
        AppConstants.CLIENTID = CLIENTID;
    }

    public static String getMnu_Change_MPin() {
        return mnu_Change_MPin;
    }

    public static void setMnu_Change_MPin(String mnu_Change_MPin) {
        AppConstants.mnu_Change_MPin = mnu_Change_MPin;
    }

    public static String getMnu_Change_TPin() {
        return mnu_Change_TPin;
    }

    public static void setMnu_Change_TPin(String mnu_Change_TPin) {
        AppConstants.mnu_Change_TPin = mnu_Change_TPin;
    }

    public static String getMnu_Reset_TPin() {
        return mnu_Reset_TPin;
    }

    public static void setMnu_Reset_TPin(String mnu_Reset_TPin) {
        AppConstants.mnu_Reset_TPin = mnu_Reset_TPin;
    }

    public static String getMnu_Limit_Transaction() {
        return mnu_Limit_Transaction;
    }

    public static void setMnu_Limit_Transaction(String mnu_Limit_Transaction) {
        AppConstants.mnu_Limit_Transaction = mnu_Limit_Transaction;
    }

    public static String getMnu_setting() {
        return mnu_setting;
    }

    public static void setMnu_setting(String mnu_setting) {
        AppConstants.mnu_setting = mnu_setting;
    }

    public static String getMnu_self_transfer_to_account() {
        return mnu_self_transfer_to_account;
    }

    public static void setMnu_self_transfer_to_account(String mnu_self_transfer_to_account) {
        AppConstants.mnu_self_transfer_to_account = mnu_self_transfer_to_account;
    }

    public static String getMnu_account_statement() {
        return mnu_account_statement;
    }

    public static void setMnu_account_statement(String mnu_account_statement) {
        AppConstants.mnu_account_statement = mnu_account_statement;
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


    public static String getMnu_checkbook_request() {
        return mnu_checkbook_request;
    }

    public static void setMnu_checkbook_request(String mnu_checkbook_request) {
        AppConstants.mnu_checkbook_request = mnu_checkbook_request;
    }

    public static String getMnu_faq() {
        return mnu_faq;
    }


    public static void setMnu_faq(String mnu_faq) {
        AppConstants.mnu_faq = mnu_faq;
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

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        AppConstants.USERNAME = USERNAME;
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

    public static String getMnu_privacypolicy() {
        return mnu_privacypolicy;
    }

    public static void setMnu_privacypolicy(String mnu_privacypolicy) {
        AppConstants.mnu_privacypolicy = mnu_privacypolicy;
    }
    public static String getMnu_accounts() {
        return mnu_accounts;
    }

    public static void setMnu_accounts(String mnu_accounts) {
        AppConstants.mnu_accounts = mnu_accounts;
    }

    public static String getMnu_fundtransfer() {
        return mnu_fundtransfer;
    }

    public static void setMnu_fundtransfer(String mnu_fundtransfer) {
        AppConstants.mnu_fundtransfer = mnu_fundtransfer;
    }

    public static String getMnu_locate_atms() {
        return mnu_locate_atms;
    }

    public static void setMnu_locate_atms(String mnu_locate_atms) {
        AppConstants.mnu_locate_atms = mnu_locate_atms;
    }

    public static String getMnu_locate_branch() {
        return mnu_locate_branch;
    }

    public static void setMnu_locate_branch(String mnu_locate_branch) {
        AppConstants.mnu_locate_branch = mnu_locate_branch;
    }

    public static String getMnu_contact_us() {
        return mnu_contact_us;
    }

    public static void setMnu_contact_us(String mnu_contact_us) {
        AppConstants.mnu_contact_us = mnu_contact_us;
    }

    public static String getMnu_about_us() {
        return mnu_about_us;
    }

    public static void setMnu_about_us(String mnu_about_us) {
        AppConstants.mnu_about_us = mnu_about_us;
    }

    public static String getMnu_accounts_menu_accountdetails() {
        return mnu_accounts_menu_accountdetails;
    }

    public static void setMnu_accounts_menu_accountdetails(String mnu_accounts_menu_accountdetails) {
        AppConstants.mnu_accounts_menu_accountdetails = mnu_accounts_menu_accountdetails;
    }

    public static String getMnu_accounts_menu_balenquiry() {
        return mnu_accounts_menu_balenquiry;
    }

    public static void setMnu_accounts_menu_balenquiry(String mnu_accounts_menu_balenquiry) {
        AppConstants.mnu_accounts_menu_balenquiry = mnu_accounts_menu_balenquiry;
    }

    public static String getMnu_accounts_menu_ministatemnt() {
        return mnu_accounts_menu_ministatemnt;
    }

    public static void setMnu_accounts_menu_ministatemnt(String mnu_accounts_menu_ministatemnt) {
        AppConstants.mnu_accounts_menu_ministatemnt = mnu_accounts_menu_ministatemnt;
    }

    public static String getMnu_accounts_menu_showmmid() {
        return mnu_accounts_menu_showmmid;
    }

    public static void setMnu_accounts_menu_showmmid(String mnu_accounts_menu_showmmid) {
        AppConstants.mnu_accounts_menu_showmmid = mnu_accounts_menu_showmmid;
    }

    public static String getMnu_fundtransfer_ownbank() {
        return mnu_fundtransfer_ownbank;
    }

    public static void setMnu_fundtransfer_ownbank(String mnu_fundtransfer_ownbank) {
        AppConstants.mnu_fundtransfer_ownbank = mnu_fundtransfer_ownbank;
    }

    public static String getMnu_fundtransfer_impstoaccount() {
        return mnu_fundtransfer_impstoaccount;
    }

    public static void setMnu_fundtransfer_impstoaccount(String mnu_fundtransfer_impstoaccount) {
        AppConstants.mnu_fundtransfer_impstoaccount = mnu_fundtransfer_impstoaccount;
    }

    public static String getMnu_fundtransfer_nefttoaccount() {
        return mnu_fundtransfer_nefttoaccount;
    }

    public static void setMnu_fundtransfer_nefttoaccount(String mnu_fundtransfer_nefttoaccount) {
        AppConstants.mnu_fundtransfer_nefttoaccount = mnu_fundtransfer_nefttoaccount;
    }

    public static String getMnu_fundtransfer_impstomobile() {
        return mnu_fundtransfer_impstomobile;
    }

    public static void setMnu_fundtransfer_impstomobile(String mnu_fundtransfer_impstomobile) {
        AppConstants.mnu_fundtransfer_impstomobile = mnu_fundtransfer_impstomobile;
    }

    public static String getMnu_fundtransfer_mngbenefeciaries() {
        return mnu_fundtransfer_mngbenefeciaries;
    }

    public static void setMnu_fundtransfer_mngbenefeciaries(String mnu_fundtransfer_mngbenefeciaries) {
        AppConstants.mnu_fundtransfer_mngbenefeciaries = mnu_fundtransfer_mngbenefeciaries;
    }

    public static String getMnu_accounts_menu_last5imps() {
        return mnu_accounts_menu_last5imps;
    }

    public static void setMnu_accounts_menu_last5imps(String mnu_accounts_menu_last5imps) {
        AppConstants.mnu_accounts_menu_last5imps = mnu_accounts_menu_last5imps;
    }

    public static String getMnu_accounts_menu_neftenquiry() {
        return mnu_accounts_menu_neftenquiry;
    }

    public static void setMnu_accounts_menu_neftenquiry(String mnu_accounts_menu_neftenquiry) {
        AppConstants.mnu_accounts_menu_neftenquiry = mnu_accounts_menu_neftenquiry;
    }

    public static String getMnu_verify_beneficiary_name() {
        return mnu_verify_beneficiary_name;
    }

    public static void setMnu_verify_beneficiary_name(String mnu_verify_beneficiary_name) {
        AppConstants.mnu_verify_beneficiary_name = mnu_verify_beneficiary_name;
    }


    public static String getMnu_check_imps_transaction_status() {
        return mnu_check_imps_transaction_status;
    }

    public static void setMnu_check_imps_transaction_status(String mnu_check_imps_transaction_status) {
        AppConstants.mnu_check_imps_transaction_status = mnu_check_imps_transaction_status;
    }


    public static String getMnu_bill_pay_complaint_management() {
        return mnu_bill_pay_complaint_management;
    }

    public static void setMnu_bill_pay_complaint_management(String mnu_bill_pay_complaint_management) {
        AppConstants.mnu_bill_pay_complaint_management = mnu_bill_pay_complaint_management;
    }

    public static String getMnu_bill_pay_register_complaints() {
        return mnu_bill_pay_register_complaints;
    }

    public static void setMnu_bill_pay_register_complaints(String mnu_bill_pay_register_complaints) {
        AppConstants.mnu_bill_pay_register_complaints = mnu_bill_pay_register_complaints;
    }

    public static String getMnu_bill_pay_track_complaints() {
        return mnu_bill_pay_track_complaints;
    }

    public static void setMnu_bill_pay_track_complaints(String mnu_bill_pay_track_complaints) {
        AppConstants.mnu_bill_pay_track_complaints = mnu_bill_pay_track_complaints;
    }

    //****************Menu Flags Getters & Setters***************************//

    public static void setLogo(MBank mInstance, ImageView ivAppLogo) {
        if (mInstance.getPackageName().equals("com.trustbank.trustmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_launcher));

        } else if (mInstance.getPackageName().equals("com.trustbank.trustdemombankapp")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_launcher));
        } else if (mInstance.getPackageName().equals("com.trustbank.sadhnambank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.sadhna_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.pucbmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.puc_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.pdccbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.pdcc_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.bucbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_brahmpuri_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.shivajibank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.shivaji_bank_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.vmucbbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.vmucb_app_icon));
        } else if (mInstance.getPackageName().equals("com.trustbank.punepeoplesbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ppc_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.gondiamahilabank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.gondia_mahila_bank));
        } else if (mInstance.getPackageName().equals("com.trustbank.smritimbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.smriti_bank));
        } else if (mInstance.getPackageName().equals("com.trustbank.manndeshibank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.mandeshi_bank_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.hedgewarmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.hedgewar_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.janataajarambank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.janata_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.parijatmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.parijat_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.kucbmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.kucb_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.mdccmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.mdcc_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.anuradhambankbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.anuradha_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.vnspmmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.vnspm_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.mdccmbankapp")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.mdcc_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.nccsmbankapp")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_nccs_logo));
        } else if (mInstance.getPackageName().equals("com.trustbank.motimbankapp")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_moti_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.nagpursahakarimbankapp")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_moti_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.itparkbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_moti_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.cucbmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.cucb_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.lymsbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_lspma_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.ambikamahilasahakarimbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_ambika_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.balitikurimbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_balitikuri_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.cdccbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_cdcc_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.ashirwadmahila")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_ashirwadmahila_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.amarnath")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_amarnath_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.latur")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_latur_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.shramjivi")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_shramji_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.bhausahebsahakari")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_bhausaheb_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.borgaonurban")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_borgaon_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.bantra")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.bantra_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.ratnasundar")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ratnasundar_logo1));
        }else if (mInstance.getPackageName().equals("com.trustbank.hoogly")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.hoogly_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.subhedar")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.subhedar_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.shikshak")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.shikshak));
        }else if (mInstance.getPackageName().equals("com.trustbank.shriveerseva")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.veerseva));
        }else if (mInstance.getPackageName().equals("com.trustbank.gautam")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.gautambank));
        }else if (mInstance.getPackageName().equals("com.trustbank.bhausahebbirajdar")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.bhausahebbirajdar));
        }else if (mInstance.getPackageName().equals("com.trustbank.konkan")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.kfcb_logo_new));
        }else if (mInstance.getPackageName().equals("com.trustbank.priyadarshani")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.priydarshani));
        }else if (mInstance.getPackageName().equals("com.trustbank.panjabrao")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.panjabrao_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.bramhapuri")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.bramhapuri_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.palus")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.palus_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.prathamik")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.prathamik_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.osmanabad")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.osmanabad_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.navinsubhedarmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ic_subhedar_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.bilaspur")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.bilaspur_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.gdccmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.gdcc_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.ahmednagar")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.ahmednagar_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.alavi")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.alavi_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.alavimbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.alavi_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.unionmbank")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.union_logo));
        }else if (mInstance.getPackageName().equals("com.trustbank.mkrishnaparaspar")) {
            ivAppLogo.setImageDrawable(mInstance.getResources().getDrawable(R.drawable.nidhibank_logo));
        }
    }

    public static void setUniqueKeys(MBank mInstance) {
        if (mInstance.getPackageName().equals("com.trustbank.trustmbank")) {  //for demo
            INSTITUTION_ID = "406";//"530";//PUCB---"406"//SADHANA
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.trustdemombankapp")) {  //for development
            INSTITUTION_ID = "406";//"530";//PUCB---"406"//SADHANA
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.pdccbank")) {
            INSTITUTION_ID = "406";//"530";//PUCB---"406"//SADHANA
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.sadhnambank")) {
            INSTITUTION_ID = "406";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.pucbmbank")) {
            INSTITUTION_ID = "530";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.bucbank")) { // brahmpuri bank.
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.shivajibank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.vmucbbank")) {
            INSTITUTION_ID = "541";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.punepeoplesbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.gondiamahilabank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.smritimbank")) {
            INSTITUTION_ID = "64";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.manndeshibank")) {
            INSTITUTION_ID = "281";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.hedgewarmbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.janataajarambank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.parijatmbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.kucbmbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.mdccmbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.anuradhambankbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.vnspmmbank")) {
            INSTITUTION_ID = "406";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.mdccmbankapp")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.nccsmbankapp")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        } else if (mInstance.getPackageName().equals("com.trustbank.motimbankapp")) {
            INSTITUTION_ID = "761";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.nagpursahakarimbankapp")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.itparkbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.cucbmbank")) {
            INSTITUTION_ID = "628";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.lymsbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.ambikamahilasahakarimbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.balitikurimbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.cdccbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.ashirwadmahila")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.amarnath")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.latur")) {
            INSTITUTION_ID = "830";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.shramjivi")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.bhausahebsahakari")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.borgaonurban")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.bantra")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.ratnasundar")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.hoogly")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.subhedar")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.shikshak")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.shriveerseva")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.gautam")) {
            INSTITUTION_ID = "857";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.bhausahebbirajdar")) {
//            INSTITUTION_ID = "479";
            INSTITUTION_ID = "848";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.konkan")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.priyadarshani")) {
            INSTITUTION_ID = "409";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.panjabrao")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.bramhapuri")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.palus")) {
            INSTITUTION_ID = "310";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.prathamik")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.osmanabad")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.navinsubhedarmbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.bilaspur")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.gdccmbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.ahmednagar")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.alavi")) {
            INSTITUTION_ID = "741";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.alavimbank")) {
            INSTITUTION_ID = "741";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.unionmbank")) {
            INSTITUTION_ID = "479";
            API_KEY = BuildConfig.api_key;
        }else if (mInstance.getPackageName().equals("com.trustbank.mkrishnaparaspar")) {
            INSTITUTION_ID = "479";
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



}
