package com.trustbank.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.activity.AccountDetailsActivity;
import com.trustbank.activity.AccountOverviewActivity;
import com.trustbank.activity.CreditCatalogueActivity;
import com.trustbank.activity.CreditRepaymentActivity;
import com.trustbank.activity.CreditSimulatorActivity;
import com.trustbank.activity.FeedbackActivity;
import com.trustbank.activity.FrmAccountStatement;
import com.trustbank.activity.IntraBankTransferActivity;
import com.trustbank.activity.InvestmentAccountActivity;
import com.trustbank.activity.InvestmentCatalogueActivity;
import com.trustbank.activity.InvestmentCertificateActivity;
import com.trustbank.activity.InvestmentClosureActivity;
import com.trustbank.activity.InvestmentSimulatorActivity;
import com.trustbank.activity.LocateAgencyActivity;
import com.trustbank.activity.ContactUsActivity;
import com.trustbank.activity.LocateAtmsActivity;
import com.trustbank.activity.ManageBeneficiaryActivity;
import com.trustbank.activity.OpenInvestmentAccountActivity;
import com.trustbank.activity.OpenLoanAccountActivity;
import com.trustbank.activity.OpenSavingsAccountActivity;
import com.trustbank.activity.SaveCatalogueActivity;
import com.trustbank.activity.SavingsAccountActivity;
import com.trustbank.activity.ScheduleVisitActivity;
import com.trustbank.activity.SecurityCenterActivity;
import com.trustbank.activity.SelfTransferToAccountActivity;
import com.trustbank.activity.TransactionLimitActivity;
import com.trustbank.util.TrustMethods;
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

                case "Account Overview":
                    Intent intentAccov = new Intent(view.getContext(), AccountOverviewActivity.class);
                    intentAccov.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccov);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Statement":
                    Intent intentstatement= new Intent(view.getContext(), FrmAccountStatement.class);
                    intentstatement.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentstatement);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Saving Catalogue":
                    Intent intentsavecat= new Intent(view.getContext(), SaveCatalogueActivity.class);
                    intentsavecat.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentsavecat);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Investment Simulator":
                    Intent intentinvstsim= new Intent(view.getContext(), InvestmentSimulatorActivity.class);
                    intentinvstsim.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentinvstsim);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Locate ATM":
                    Intent intentLocateatm = new Intent(view.getContext(), LocateAtmsActivity.class);
                    intentLocateatm.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocateatm);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Locate Agencies":
                    Intent intentLocateBranch = new Intent(view.getContext(), LocateAgencyActivity.class);
                    intentLocateBranch.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocateBranch);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Contact":
                    Intent intentContactus = new Intent(view.getContext(), ContactUsActivity.class);
                    intentContactus.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentContactus);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Schedule Visit":
                    Intent intentAboutUs = new Intent(view.getContext(), ScheduleVisitActivity.class);
                    intentAboutUs.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAboutUs);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Credit Catalogue":
                    Intent intentcrecat = new Intent(view.getContext(), CreditCatalogueActivity.class);
                    intentcrecat.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcrecat);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Investment Catalogue":
                    Intent intentinvstcat = new Intent(view.getContext(), InvestmentCatalogueActivity.class);
                    intentinvstcat.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentinvstcat);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Open Saving Account":
                    Intent intentosa = new Intent(view.getContext(), OpenSavingsAccountActivity.class);
                    intentosa.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentosa);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Open Investment Account":
                    Intent intentoia = new Intent(view.getContext(), OpenInvestmentAccountActivity.class);
                    intentoia.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentoia);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Open Credit Request":
                    Intent intentola = new Intent(view.getContext(), OpenLoanAccountActivity.class);
                    intentola.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentola);
                    trustMethods.activityOpenAnimation();
                    break;
                    
                case "Manage Beneficiaries":
                    Intent intentmagben = new Intent(view.getContext(), ManageBeneficiaryActivity.class);
                    intentmagben.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentmagben);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Intra BankTransfer":
                    Intent intentibt = new Intent(view.getContext(), IntraBankTransferActivity.class);
                    intentibt.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentibt);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Security Center":
                    Intent intentsc = new Intent(view.getContext(), SecurityCenterActivity.class);
                    intentsc.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentsc);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Transaction Limit":
                    Intent intenttl = new Intent(view.getContext(),  TransactionLimitActivity.class);
                    intenttl.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intenttl);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Feedback":
                    Intent intentFeedback = new Intent(view.getContext(),  FeedbackActivity.class);
                    intentFeedback.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentFeedback);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Self AccountTransfer":
                    Intent intentSAT = new Intent(view.getContext(),  SelfTransferToAccountActivity.class);
                    intentSAT.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentSAT);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Savings Account":
                    Intent intentSA = new Intent(view.getContext(), SavingsAccountActivity.class);
                    intentSA.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentSA);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Investment Account":
                    Intent intentIA = new Intent(view.getContext(), InvestmentAccountActivity.class);
                    intentIA.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentIA);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Credit Simulator":
                    Intent intentcs = new Intent(view.getContext(), CreditSimulatorActivity.class);
                    intentcs.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcs);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Investment Certificate":
                    Intent intentic = new Intent(view.getContext(), InvestmentCertificateActivity.class);
                    intentic.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentic);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Credit Repayment":
                    Intent intentcr = new Intent(view.getContext(), CreditRepaymentActivity.class);
                    intentcr.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcr);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Close Investment Account":
                    Intent intentcia = new Intent(view.getContext(),  InvestmentClosureActivity.class);
                    intentcia.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcia);
                    trustMethods.activityOpenAnimation();
                    break;

                case "detalles de la cuenta":
                    Intent intentAccDetails1 = new Intent(view.getContext(), AccountDetailsActivity.class);
                    intentAccDetails1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccDetails1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Descripción de cuenta":
                    Intent intentAccov1 = new Intent(view.getContext(), AccountOverviewActivity.class);
                    intentAccov1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccov1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Declaración":
                    Intent intentstatement1= new Intent(view.getContext(), FrmAccountStatement.class);
                    intentstatement1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentstatement1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Guardar catálogo":
                    Intent intentsavecat1= new Intent(view.getContext(), SaveCatalogueActivity.class);
                    intentsavecat1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentsavecat1);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Simulador de inversiones":
                    Intent intentinvstsim1= new Intent(view.getContext(), InvestmentSimulatorActivity.class);
                    intentinvstsim1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentinvstsim1);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Localizar cajero automático":
                    Intent intentLocateatm1 = new Intent(view.getContext(), LocateAtmsActivity.class);
                    intentLocateatm1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocateatm1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Localizar Agencias":
                    Intent intentLocateBranch1 = new Intent(view.getContext(), LocateAgencyActivity.class);
                    intentLocateBranch1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocateBranch1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Contacto":
                    Intent intentContactus1 = new Intent(view.getContext(), ContactUsActivity.class);
                    intentContactus1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentContactus1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Programar visita":
                    Intent intentAboutUs1 = new Intent(view.getContext(), ScheduleVisitActivity.class);
                    intentAboutUs1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAboutUs1);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Catálogo de crédito":
                    Intent intentcrecat1 = new Intent(view.getContext(), CreditCatalogueActivity.class);
                    intentcrecat1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcrecat1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Catálogo de inversiones":
                    Intent intentinvstcat1 = new Intent(view.getContext(), InvestmentCatalogueActivity.class);
                    intentinvstcat1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentinvstcat1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Abrir cuenta de ahorro":
                    Intent intentosa1 = new Intent(view.getContext(), OpenSavingsAccountActivity.class);
                    intentosa1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentosa1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Abrir cuenta de inversión":
                    Intent intentoia1 = new Intent(view.getContext(), OpenInvestmentAccountActivity.class);
                    intentoia1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentoia1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Solicitud de crédito abierta":
                    Intent intentola1 = new Intent(view.getContext(), OpenLoanAccountActivity.class);
                    intentola1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentola1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Administrar beneficiarios":
                    Intent intentmagben1 = new Intent(view.getContext(), ManageBeneficiaryActivity.class);
                    intentmagben1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentmagben1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Transferencia intrabancaria":
                    Intent intentibt1 = new Intent(view.getContext(), IntraBankTransferActivity.class);
                    intentibt1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentibt1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Centro de Seguridad":
                    Intent intentsc1 = new Intent(view.getContext(), SecurityCenterActivity.class);
                    intentsc1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentsc1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Límite de transacciones":
                    Intent intenttl1 = new Intent(view.getContext(),  TransactionLimitActivity.class);
                    intenttl1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intenttl1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Comentario":
                    Intent intentFeedback1 = new Intent(view.getContext(),  FeedbackActivity.class);
                    intentFeedback1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentFeedback1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Transferencia de cuenta propia":
                    Intent intentSAT1 = new Intent(view.getContext(),  SelfTransferToAccountActivity.class);
                    intentSAT1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentSAT1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Cuenta de ahorros":
                    Intent intentSA1 = new Intent(view.getContext(), SavingsAccountActivity.class);
                    intentSA1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentSA1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Cuenta de inversión":
                    Intent intentIA1 = new Intent(view.getContext(), InvestmentAccountActivity.class);
                    intentIA1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentIA1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Certificado de Inversión":
                    Intent intentCdi = new Intent(view.getContext(), InvestmentCertificateActivity.class);
                    intentCdi.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentCdi);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Pago de crédito":
                    Intent intentcr1 = new Intent(view.getContext(),CreditRepaymentActivity.class);
                    intentcr1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcr1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Cerrar cuenta de inversión":
                    Intent intentcia1 = new Intent(view.getContext(),InvestmentClosureActivity.class);
                    intentcia1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcia1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Simulador de crédito":
                    Intent intentcs1 = new Intent(view.getContext(), CreditSimulatorActivity.class);
                    intentcs1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentcs1);
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
