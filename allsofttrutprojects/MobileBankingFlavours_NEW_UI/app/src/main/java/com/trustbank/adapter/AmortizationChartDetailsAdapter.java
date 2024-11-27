//AmortizationChartDetailsAdapter

package com.trustbank.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trustbank.Model.AmortizationChartDetailsListInfo;
import com.trustbank.R;
import com.trustbank.util.TrustMethods;

import java.util.ArrayList;
import java.util.List;

public class AmortizationChartDetailsAdapter extends RecyclerView.Adapter<AmortizationChartDetailsAdapter.FrmShowReportHolder> {

    private static final String TAG = AmortizationChartDetailsAdapter.class.getSimpleName();
    Activity mContext;
    TrustMethods trustMethods;
    List<AmortizationChartDetailsListInfo> mAmortizationChartDetails = new ArrayList<AmortizationChartDetailsListInfo>();


    public AmortizationChartDetailsAdapter(Activity context, ArrayList<AmortizationChartDetailsListInfo> amortizationChartDetailsList) {
        this.mContext = context;
        this.mAmortizationChartDetails = amortizationChartDetailsList;
        TrustMethods.LogMessage(TAG, "mAmortizationChartDetailsListInfo -->" + mAmortizationChartDetails.size());
        trustMethods = new TrustMethods(mContext);
    }

    @Override
    public FrmShowReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_amortization_details, null);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        return new FrmShowReportHolder(layoutView);
    }


    @Override
    public void onBindViewHolder(FrmShowReportHolder holder, int position) {

        String expectedDate = mAmortizationChartDetails.get(position).getExpectedDate();

        String trDateString = expectedDate.substring(0, expectedDate.indexOf("T"));
        String trDateStringTokenArray[];

        if (trDateString.contains("-")) {
            trDateStringTokenArray = trDateString.split("-");
        } else {
            trDateStringTokenArray = trDateString.split("/");
        }

        TrustMethods.LogMessage(TAG, "trainingDate-->" + expectedDate);
        String trDateString1 = trDateStringTokenArray[1] + "/" + trDateStringTokenArray[0] + "/" + trDateStringTokenArray[2];
        TrustMethods.LogMessage(TAG, "trDateString1-->" + trDateString1);


        holder.textInstallmentNo.setText("" + mAmortizationChartDetails.get(position).getInstallmentNo());
        holder.textExpectedDate.setText("" + trDateStringTokenArray[1] + "/" + trDateStringTokenArray[0] + "/" + trDateStringTokenArray[2]);
        holder.textInterestAmount.setText("" + mAmortizationChartDetails.get(position).getInterestAmount());
        holder.textPrincipleAmount.setText("" + mAmortizationChartDetails.get(position).getPrincipleAmount());
        holder.textInstallmentAmount.setText("" + mAmortizationChartDetails.get(position).getInstallmentAmount());
        holder.textViewBalance.setText("" + mAmortizationChartDetails.get(position).getBalance());
    }

    @Override
    public int getItemCount() {
        return this.mAmortizationChartDetails.size();
    }

    public static final class FrmShowReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView textInstallmentNo;
        public TextView textExpectedDate;
        public TextView textInterestAmount;
        public TextView textPrincipleAmount;
        public TextView textInstallmentAmount;
        public TextView textViewBalance;

        public FrmShowReportHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textInstallmentNo = (TextView) itemView.findViewById(R.id.textViewInstallmentNo);
            textExpectedDate = (TextView) itemView.findViewById(R.id.textViewExpectedDate);
            textInterestAmount = (TextView) itemView.findViewById(R.id.textViewInteresAmount);
            textPrincipleAmount = (TextView) itemView.findViewById(R.id.textViewPrincipalAmount);
            textInstallmentAmount = (TextView) itemView.findViewById(R.id.textViewInstallmentAmount);
            textViewBalance = (TextView) itemView.findViewById(R.id.textViewBalance);

        }

        @Override
        public void onClick(View v) {

        }
    }
}


