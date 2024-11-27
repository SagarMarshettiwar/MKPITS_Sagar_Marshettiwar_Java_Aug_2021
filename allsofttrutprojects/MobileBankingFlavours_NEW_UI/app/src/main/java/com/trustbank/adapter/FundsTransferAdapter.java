package com.trustbank.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.activity.AboutUsActivity;
import com.trustbank.activity.AccountDetailsActivity;
import com.trustbank.activity.AmortizationChartActivity;
import com.trustbank.activity.BBPSBillerCategoryFinacusActivity;
import com.trustbank.activity.BBPSComplaintHistoryFinacusActivity;
import com.trustbank.activity.BBPSComplaintManagementFinacusActivity;
import com.trustbank.activity.BBPSComplaintStatusFinacusActivity;
import com.trustbank.activity.BBPSGetDuplicateReceiptFinacusActivity;
import com.trustbank.activity.BBPSPaymentDetailsFinacusActivity;
import com.trustbank.activity.BBPSTransactionHistoryFinacusActivity;
import com.trustbank.activity.BBPSTransactionInquiryFinacusActivity;
import com.trustbank.activity.BalanceEnquiryActivity;
import com.trustbank.activity.BalanceEnquiryCBSActivity;
import com.trustbank.activity.BlockDebitCardActivity;
import com.trustbank.activity.BlockDebitCardSwitchActivity;
import com.trustbank.activity.BlockDebitCardSwitchTempActivity;
import com.trustbank.activity.BranchesActivity;
import com.trustbank.activity.ChequeStatusActivity;
import com.trustbank.activity.ChequebookRequestActivity;
import com.trustbank.activity.ContactUsActivity;
import com.trustbank.activity.CreateQRUPIActivity;
import com.trustbank.activity.DebitCardLimitAtivity;
import com.trustbank.activity.DebitCardPinGenerationActivity;
import com.trustbank.activity.DebitCardPinVerifyFinacusActivity;
import com.trustbank.activity.DebitCardSetChannelActivity;
import com.trustbank.activity.FAQActivity;
import com.trustbank.activity.FdRdAccOpeningActivity;
import com.trustbank.activity.Form15GHActivity;
import com.trustbank.activity.FrmClientManagementActivity;
import com.trustbank.activity.FrmPPSServiceRequestEnquiry;
import com.trustbank.activity.GetQRUPIActivity;
import com.trustbank.activity.IMPSTransferToAccount;
import com.trustbank.activity.IMPSTransferToMobile;
import com.trustbank.activity.ImpsCheckTransactionRequest;
import com.trustbank.activity.LastFiveImpsTransactionActivity;
import com.trustbank.activity.LastFiveImpsTransactionCBSActivity;
import com.trustbank.activity.LocateAtmsActivity;
import com.trustbank.activity.LockActivity;
import com.trustbank.activity.MandateCancelActivity;
import com.trustbank.activity.MenuActivity;
import com.trustbank.activity.NEFTTransferToAccount;
import com.trustbank.activity.NeftEnquiryActivity;
import com.trustbank.activity.NomineeActivity;
import com.trustbank.activity.PPSRequestActivity;
import com.trustbank.activity.RdFdRecknorActivity;
import com.trustbank.activity.SelfTransferToAccountActivity;
import com.trustbank.activity.ShowMMIDActivity;
import com.trustbank.activity.ShowMMIDCBSActivity;
import com.trustbank.activity.StandingInstructionActivity;
import com.trustbank.activity.StopChequeRequestActivity;
import com.trustbank.activity.UPICollectActivity;
import com.trustbank.activity.UPIToUPITransactions;
import com.trustbank.activity.WithinBankActivity;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FundsTransferAdapter extends RecyclerView.Adapter<FundsTransferAdapter.ViewHolder>{
    private static final String TAG = FundsTransferAdapter.class.getSimpleName();
    private Activity mActivity;
    private TrustMethods trustMethods;
    private List<ImageTextMenuModel> accountItemsData;
    private ArrayList<BeneficiaryModal> beneficiaryList;

    public CoordinatorLayout dataLayout;

    public FundsTransferAdapter(Activity activity, List<ImageTextMenuModel> accountItemsData) {
        this.mActivity = activity;
        this.accountItemsData = accountItemsData;
        trustMethods = new TrustMethods(mActivity);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_accounts, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.accountMenuName.setText(accountItemsData.get(position).getItemName());
        viewHolder.imageiconview.setImageResource(accountItemsData.get(position).getImage());

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView accountMenuName;
        public ImageView imageiconview;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            dataLayout = itemLayoutView.findViewById(R.id.dataLayoutId);
            accountMenuName = itemLayoutView.findViewById(R.id.accountMenuNameId);
            imageiconview= itemLayoutView.findViewById(R.id.image_icon_view);

        }

        @Override
        public void onClick(View view) {
            switch (accountItemsData.get(getAdapterPosition()).getItemName().trim()) {
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

                    //back action not done as per new menu not in json also
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
                    Intent intentBlockDebitCardcbs = new Intent(view.getContext(), BlockDebitCardActivity.class);
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

                case "Permanent Block Debit Card Switch":
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

                case "Nominee Management":
                    Intent intentNominee = new Intent(view.getContext(), NomineeActivity.class);
                    intentNominee.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentNominee);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Debit Card Limit":
                    Intent intentdcl = new Intent(view.getContext(), DebitCardLimitAtivity.class);
                    intentdcl.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentdcl);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Temporary Block Debit Card":
                    Intent intentdct = new Intent(view.getContext(), BlockDebitCardSwitchTempActivity.class);
                    intentdct.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentdct);
                    trustMethods.activityOpenAnimation();
                    break;

                case "BBPS Bill Payment":
                    Intent intentbp = new Intent(view.getContext(), BBPSBillerCategoryFinacusActivity.class);
                    intentbp.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentbp);
                    trustMethods.activityOpenAnimation();
                    break;

                case "BBPS Transaction History":
                    Intent intentbpr = new Intent(view.getContext(), BBPSTransactionHistoryFinacusActivity.class);
                    intentbpr.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentbpr);
                    trustMethods.activityOpenAnimation();
                    break;

                case "BBPS Complaint Management":
                    Intent intentcm = new Intent(view.getContext(), BBPSComplaintManagementFinacusActivity.class);
                    intentcm.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcm);
                    trustMethods.activityOpenAnimation();
                    break;

                case "BBPS Complaint Status":
                    Intent intentcs = new Intent(view.getContext(), BBPSComplaintStatusFinacusActivity.class);
                    intentcs.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcs);
                    trustMethods.activityOpenAnimation();
                    break;

                case "BBPS Complaint History":
                    Intent intentch = new Intent(view.getContext(), BBPSComplaintHistoryFinacusActivity.class);
                    intentch.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentch);
                    trustMethods.activityOpenAnimation();
                    break;

                case "BBPS Transaction Inquiry":
                    Intent intentti = new Intent(view.getContext(), BBPSTransactionInquiryFinacusActivity.class);
                    intentti.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentti);
                    trustMethods.activityOpenAnimation();
                    break;

                case "BBPS Duplicate Receipt":
                    Intent intentdr = new Intent(view.getContext(), BBPSGetDuplicateReceiptFinacusActivity.class);
                    intentdr.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentdr);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Debit Card Pin Verification":
                    Intent intentpv = new Intent(view.getContext(), DebitCardPinVerifyFinacusActivity.class);
                    intentpv.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentpv);
                    trustMethods.activityOpenAnimation();
                    break;

                case "RD/FD Acc Opening":
                    Intent intentacc = new Intent(view.getContext(), FrmClientManagementActivity.class);
                    intentacc.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentacc);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Debit Card Set Channel":
                    Intent intentchan = new Intent(view.getContext(), DebitCardSetChannelActivity.class);
                    intentchan.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentchan);
                    trustMethods.activityOpenAnimation();
                    break;

                    case "Form 15GH":
                    Intent intent15GH = new Intent(view.getContext(), Form15GHActivity.class);
                    intent15GH.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intent15GH);
                    trustMethods.activityOpenAnimation();
                    break;


                    case "Standing Instruction":
                    Intent intentSI = new Intent(view.getContext(), StandingInstructionActivity.class);
                        intentSI.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentSI);
                    trustMethods.activityOpenAnimation();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return accountItemsData.size();
    }

}
