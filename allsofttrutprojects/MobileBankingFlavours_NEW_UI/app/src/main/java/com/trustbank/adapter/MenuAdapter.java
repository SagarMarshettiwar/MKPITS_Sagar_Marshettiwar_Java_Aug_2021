package com.trustbank.adapter;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.activity.AboutUsActivity;
import com.trustbank.activity.AccountDetailsActivity;
import com.trustbank.activity.AccountsActivity;
import com.trustbank.activity.AmortizationChartActivity;
import com.trustbank.activity.BBPSBillerCategoryActivity;
import com.trustbank.activity.BBPSBillerCategoryFinacusActivity;
import com.trustbank.activity.BBPSFinacusActivity;
import com.trustbank.activity.BalanceEnquiryActivity;
import com.trustbank.activity.BalanceEnquiryCBSActivity;
import com.trustbank.activity.BlockDebitCardActivity;
import com.trustbank.activity.BlockDebitCardSwitchActivity;
import com.trustbank.activity.BranchesActivity;
import com.trustbank.activity.Cards;
import com.trustbank.activity.ChequeStatusActivity;
import com.trustbank.activity.ChequebookRequestActivity;
import com.trustbank.activity.ContactUsActivity;
import com.trustbank.activity.CreateQRUPIActivity;
import com.trustbank.activity.DebitCardPinGenerationActivity;
import com.trustbank.activity.FAQActivity;
import com.trustbank.activity.FrmAccountStatement;
import com.trustbank.activity.FrmPPSServiceRequestEnquiry;
import com.trustbank.activity.GetQRUPIActivity;
import com.trustbank.activity.IMPSTransferToAccount;
import com.trustbank.activity.IMPSTransferToMobile;
import com.trustbank.activity.ImpsCheckTransactionRequest;
import com.trustbank.activity.LastFiveImpsTransactionActivity;
import com.trustbank.activity.LastFiveImpsTransactionCBSActivity;
import com.trustbank.activity.LocateAtmsActivity;
import com.trustbank.activity.LocateUs;
import com.trustbank.activity.ManageBeneficiaryActivity;
import com.trustbank.activity.MandateCancelActivity;
import com.trustbank.activity.MiniStatementActivity;
import com.trustbank.activity.MiniStatementCBSActivity;
import com.trustbank.activity.NEFTTransferToAccount;
import com.trustbank.activity.NeedHelp;
import com.trustbank.activity.NeftEnquiryActivity;
import com.trustbank.activity.PPSRequestActivity;
import com.trustbank.activity.PrivacyPolicyActivity;
import com.trustbank.activity.RdFdRecknorActivity;
import com.trustbank.activity.SelfTransferToAccountActivity;
import com.trustbank.activity.ServiceRequest;
import com.trustbank.activity.SettingActivity;
import com.trustbank.activity.ShowMMIDActivity;
import com.trustbank.activity.ShowMMIDCBSActivity;
import com.trustbank.activity.StopChequeRequestActivity;
import com.trustbank.activity.UPIActivityMenu;
import com.trustbank.activity.UPICollectActivity;
import com.trustbank.activity.UPIToUPITransactions;
import com.trustbank.activity.WithinBankActivity;
import com.trustbank.util.AppConstants;
import com.trustbank.util.TrustMethods;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private static final String TAG = MenuAdapter.class.getSimpleName();
    private List<ImageTextMenuModel> itemsData;
    Activity mActivity;
    HashMap<String, List<DynamicMenuModel>> submenumap;
    TrustMethods trustMethods;

    public MenuAdapter(Activity activity, List<ImageTextMenuModel> itemsData, HashMap<String, List<DynamicMenuModel>> submenumap) {
        this.itemsData = itemsData;
        this.mActivity = activity;
        this.submenumap = submenumap;
        trustMethods = new TrustMethods(mActivity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_demo, null);
       /* RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.w, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);*/

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.imgCrdView.setBackgroundColor(mActivity.getResources().getColor(R.color.transparant));
        if (itemsData.get(position).getLableName() != null && !TextUtils.isEmpty(itemsData.get(position).getLableName())) {
            viewHolder.txtViewTitle.setText(itemsData.get(position).getLableName());
        }
        if (itemsData.get(position).getImage() != 0) {
            viewHolder.imgViewIcon.setImageResource(itemsData.get(position).getImage());
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final RelativeLayout linear_menu;
        public TextView txtViewTitle;
        public ImageView imgViewIcon;
        private CardView imgCrdView;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewTitle = itemLayoutView.findViewById(R.id.txt_state_name);
            imgViewIcon = itemLayoutView.findViewById(R.id.departmentIV);
            linear_menu = itemLayoutView.findViewById(R.id.linear_menu);
            imgCrdView = itemLayoutView.findViewById(R.id.imgCrdViewId);
        }
        @Override
        public void onClick(View view) {
            switch (itemsData.get(getAdapterPosition()).getItemName().trim()) {

                case "Accounts":
                    if (submenumap.containsKey("mnu_accounts")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_accounts");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=true;
                        AppConstants.upi_group=false;
                        AppConstants.service_group=false;
                        AppConstants.cards_group=false;
                        AppConstants.locate_us_group=false;
                        AppConstants.need_help_group=false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentAccounts = new Intent(view.getContext(), AccountsActivity.class);
                    intentAccounts.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccounts);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Within Bank Transfer":
                    Intent intentWithinBank = new Intent(view.getContext(), WithinBankActivity.class);
                    intentWithinBank.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentWithinBank);
                    trustMethods.activityOpenAnimation();
                    break;

                case "NEFT Transfer To Account":
                    Intent intentNEFTTransferToAccount = new Intent(view.getContext(), NEFTTransferToAccount.class);
                    intentNEFTTransferToAccount.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentNEFTTransferToAccount);
                    trustMethods.activityOpenAnimation();
                    break;

                case "IMPS Transfer To Account":
                    Intent intentImpsToAccountNo = new Intent(view.getContext(), IMPSTransferToAccount.class);
                    intentImpsToAccountNo.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentImpsToAccountNo);
                    trustMethods.activityOpenAnimation();
                    break;

                case "IMPS Transfer To Mobile Phone":
                    Intent intentImpsTophone = new Intent(view.getContext(), IMPSTransferToMobile.class);
                    intentImpsTophone.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentImpsTophone);
                    trustMethods.activityOpenAnimation();
                    break;

                case "UPI":
                    if (submenumap.containsKey("mnu_upi")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_upi");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.upi_group=true;
                        AppConstants.service_group=false;
                        AppConstants.cards_group=false;
                        AppConstants.locate_us_group=false;
                        AppConstants.need_help_group=false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentFundTransferAccount = new Intent(view.getContext(), UPIActivityMenu.class);
                    intentFundTransferAccount.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentFundTransferAccount);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Self Transfer To Account":
                    Intent intentSelfTransferToAccount = new Intent(view.getContext(), SelfTransferToAccountActivity.class);
                    intentSelfTransferToAccount.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentSelfTransferToAccount);
                    trustMethods.activityOpenAnimation();
                    break;

                case "mPassBook":
                    Intent intentAccountStatement = new Intent(view.getContext(), FrmAccountStatement.class);
                    intentAccountStatement.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccountStatement);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Statement Request CBS":
                    Intent intentMiniStatementCBS = new Intent(view.getContext(), MiniStatementCBSActivity.class);
                    intentMiniStatementCBS.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentMiniStatementCBS);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Statement Request":
                    Intent intentMiniStatement = new Intent(view.getContext(), MiniStatementActivity.class);
                    intentMiniStatement.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentMiniStatement);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Manage Beneficiaries":
                    Intent intentBeneficiaries = new Intent(view.getContext(), ManageBeneficiaryActivity.class);
                    intentBeneficiaries.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBeneficiaries);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Service Request":
                    if (submenumap.containsKey("mnu_Services")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_Services");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.upi_group=false;
                        AppConstants.service_group=true;
                        AppConstants.cards_group=false;
                        AppConstants.locate_us_group=false;
                        AppConstants.need_help_group=false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentService = new Intent(view.getContext(), ServiceRequest.class);
                    intentService.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentService);
                    trustMethods.activityOpenAnimation();

                    break;

                case "Settings":
                    Intent intentChangeTpin = new Intent(view.getContext(), SettingActivity.class);
                    intentChangeTpin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentChangeTpin);
                    trustMethods.activityOpenAnimation();
                    break;

                /*case "Bill Pay":
                    Intent intentBillPay = new Intent(view.getContext(), BBPSBillerCategoryActivity.class);
                    intentBillPay.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBillPay);
                    trustMethods.activityOpenAnimation();
                    break;*/

                case "Need Help":
                    if (submenumap.containsKey("mnu_need_help")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_need_help");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.upi_group=false;
                        AppConstants.service_group=false;
                        AppConstants.cards_group=false;
                        AppConstants.locate_us_group=false;
                        AppConstants.need_help_group=true;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentHelp = new Intent(view.getContext(), NeedHelp.class);
                    intentHelp.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentHelp);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Locate US":
                    if (submenumap.containsKey("mnu_locate_us")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_locate_us");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.upi_group=false;
                        AppConstants.service_group=false;
                        AppConstants.cards_group=false;
                        AppConstants.locate_us_group=true;
                        AppConstants.need_help_group=false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentLocate = new Intent(view.getContext(), LocateUs.class);
                    intentLocate.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocate);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Cards":
                    if (submenumap.containsKey("mnu_cards")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_cards");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.upi_group=false;
                        AppConstants.service_group=false;
                        AppConstants.cards_group=true;
                        AppConstants.locate_us_group=false;
                        AppConstants.need_help_group=false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentCards = new Intent(view.getContext(), Cards.class);
                    intentCards.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentCards);
                    trustMethods.activityOpenAnimation();
                    break;



                //mpassbook Area
                case "Account Details":
                    Intent intentAccDetails = new Intent(view.getContext(), AccountDetailsActivity.class);
                    intentAccDetails.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccDetails);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Balance Enquiry":
                    Intent intentBalEnquiry = new Intent(view.getContext(), BalanceEnquiryActivity.class);
                    intentBalEnquiry.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBalEnquiry);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Last 5 Transactions":
                    Intent intentLastFiveTrans = new Intent(view.getContext(), LastFiveImpsTransactionActivity.class);
                    intentLastFiveTrans.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLastFiveTrans);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Show MMID":
                    Intent intentShowMmid = new Intent(view.getContext(), ShowMMIDActivity.class);
                    intentShowMmid.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentShowMmid);
                    trustMethods.activityOpenAnimation();
                    break;

                case "NEFT Enquiry":
                    Intent intentNeftEnquiry = new Intent(view.getContext(), NeftEnquiryActivity.class);
                    intentNeftEnquiry.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentNeftEnquiry);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Balance Enquiry CBS":
                    Intent intentBalEnquiryCBS = new Intent(view.getContext(), BalanceEnquiryCBSActivity.class);
                    intentBalEnquiryCBS.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBalEnquiryCBS);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Last 5 Transactions CBS":
                    Intent intentLastFiveTransCBS = new Intent(view.getContext(), LastFiveImpsTransactionCBSActivity.class);
                    intentLastFiveTransCBS.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLastFiveTransCBS);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Show MMID CBS":
                    Intent intentShowMmidCBS = new Intent(view.getContext(), ShowMMIDCBSActivity.class);
                    intentShowMmidCBS.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentShowMmidCBS);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Check Transaction Status":
                    Intent intentImpsTransctionRequest = new Intent(view.getContext(), ImpsCheckTransactionRequest.class);
                    intentImpsTransctionRequest.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentImpsTransctionRequest);
                    trustMethods.activityOpenAnimation();
                    break;

                case "FD/RD Reckoner":
                    Intent intentreckoner= new Intent(view.getContext(), RdFdRecknorActivity.class);
                    intentreckoner.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentreckoner);
                    trustMethods.activityOpenAnimation();
                    break;

                case "EMI Calculator":
                    Intent intentamortization = new Intent(view.getContext(), AmortizationChartActivity.class);
                    intentamortization.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentamortization);
                    trustMethods.activityOpenAnimation();
                    break;


                case "UPI Transfer":
                    Intent intentUPITransactions = new Intent(view.getContext(), UPIToUPITransactions.class);
                    intentUPITransactions.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentUPITransactions);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Collect Money":
                    Intent intentUPICollectMoneyTransactions = new Intent(view.getContext(), UPICollectActivity.class);
                    intentUPICollectMoneyTransactions.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentUPICollectMoneyTransactions);
                    trustMethods.activityOpenAnimation();
                    break;
                case "Create New QR Code":
                    Intent intentUPIGenearteBarcode = new Intent(view.getContext(), CreateQRUPIActivity.class);
                    intentUPIGenearteBarcode.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentUPIGenearteBarcode);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Display QR Code":
                    Intent intentUPIRetrieveBarcode = new Intent(view.getContext(), GetQRUPIActivity.class);
                    intentUPIRetrieveBarcode.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentUPIRetrieveBarcode);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Locate ATM":
                    Intent intentLocateatm = new Intent(view.getContext(), LocateAtmsActivity.class);
                    intentLocateatm.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocateatm);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Locate Branch":
                    Intent intentLocateBranch = new Intent(view.getContext(), BranchesActivity.class);
                    intentLocateBranch.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocateBranch);
                    trustMethods.activityOpenAnimation();
                    break;

                case "FAQs":
                    Intent intentFAQs = new Intent(view.getContext(), FAQActivity.class);
                    intentFAQs.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentFAQs);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Contact US":
                    Intent intentContactus = new Intent(view.getContext(), ContactUsActivity.class);
                    intentContactus.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentContactus);
                    trustMethods.activityOpenAnimation();
                    break;

                case "About US":
                    Intent intentAboutUs = new Intent(view.getContext(), AboutUsActivity.class);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAboutUs);
                    trustMethods.activityOpenAnimation();
                    break;
                case "Cheque Book Request":
                    Intent intentChequebookrequest = new Intent(view.getContext(), ChequebookRequestActivity.class);
                    intentChequebookrequest.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentChequebookrequest);
                    trustMethods.activityOpenAnimation();
                    break;
                case "Cheque Status":
                    Intent intentChequeStatus = new Intent(view.getContext(), ChequeStatusActivity.class);
                    intentChequeStatus.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentChequeStatus);
                    trustMethods.activityOpenAnimation();
                    break;
                case "Stop Cheque":
                    Intent intentStopCheque = new Intent(view.getContext(), StopChequeRequestActivity.class);
                    intentStopCheque.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentStopCheque);
                    trustMethods.activityOpenAnimation();
                    break;
                case "Positive Pay Request":
                    Intent intentPostivePayRequest = new Intent(view.getContext(), PPSRequestActivity.class);
                    intentPostivePayRequest.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentPostivePayRequest);
                    trustMethods.activityOpenAnimation();
                    break;
                case "Positive Pay Enquiry":
                    Intent intentPositivePayStatus = new Intent(view.getContext(), FrmPPSServiceRequestEnquiry.class);
                    intentPositivePayStatus.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentPositivePayStatus);
                    trustMethods.activityOpenAnimation();
                    break;
                case "Block Debit Card":
                    Intent intentBlockDebitCard = new Intent(view.getContext(), BlockDebitCardActivity.class);
                    intentBlockDebitCard.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBlockDebitCard);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Block Debit Card CBS":
                    Intent intentBlockDebitCardcbs = new Intent(view.getContext(), BlockDebitCardSwitchActivity.class);
                    intentBlockDebitCardcbs.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBlockDebitCardcbs);
                    trustMethods.activityOpenAnimation();
                    break;

                case "ECS Mandate Cancellation":
                    Intent intentECSManditCancellation = new Intent(view.getContext(), MandateCancelActivity.class);
                    intentECSManditCancellation.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentECSManditCancellation);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Block Debit Card Switch":
                    Intent intentBlockDebitCardswitch = new Intent(view.getContext(), BlockDebitCardSwitchActivity.class);
                    intentBlockDebitCardswitch.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBlockDebitCardswitch);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Debit Card Pin Generation":
                    Intent intentDebitPinGeneration = new Intent(view.getContext(), DebitCardPinGenerationActivity.class);
                    intentDebitPinGeneration.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentDebitPinGeneration);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Bill Pay":
                    if (submenumap.containsKey("mnu_bbps_finacus")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_bbps_finacus");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.upi_group=false;
                        AppConstants.service_group=false;
                        AppConstants.cards_group=false;
                        AppConstants.locate_us_group=false;
                        AppConstants.need_help_group=false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentbbps = new Intent(view.getContext(), BBPSFinacusActivity.class);
                    intentbbps.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentbbps);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Privacy Policy":
                    Intent intent = new Intent(view.getContext(), PrivacyPolicyActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intent);
                    trustMethods.activityOpenAnimation();
                    break;

                default:
                    break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
