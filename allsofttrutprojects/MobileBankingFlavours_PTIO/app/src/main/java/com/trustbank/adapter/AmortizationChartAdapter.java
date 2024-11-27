package com.trustbank.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.AmortizationChartModel;
import com.trustbank.Model.SlabDetailsModel;
import com.trustbank.R;

import java.util.List;

public class AmortizationChartAdapter extends RecyclerView.Adapter<AmortizationChartAdapter.ViewHolder> {
    Activity activity;
    List<AmortizationChartModel> amortChartList;
    public AmortizationChartAdapter(Activity activity, List<AmortizationChartModel> amortChartList) {
        this.activity=activity;
        this.amortChartList=amortChartList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.amortization_chart, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.inst_num.setText(amortChartList.get(position).getInstNo());
        holder.expected_date.setText(amortChartList.get(position).getDuedate());
        holder.intrst_amt.setText(amortChartList.get(position).getIntInstAmt());
        holder.principal_amt.setText(amortChartList.get(position).getPrInstAmt());
        holder.inst_amt.setText(amortChartList.get(position).getTotInstAmt());
        holder.insurance_amt.setText(amortChartList.get(position).getInsAmt());
        holder.exp_bal.setText(amortChartList.get(position).getPrBalAmt());
    }
    @Override
    public int getItemCount() {
        return amortChartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView inst_num, expected_date, intrst_amt, principal_amt, inst_amt, insurance_amt, exp_bal;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            inst_num = itemLayoutView.findViewById(R.id.inst_num);
            expected_date = itemLayoutView.findViewById(R.id.expected_date);
            intrst_amt = itemLayoutView.findViewById(R.id.intrst_amt);
            principal_amt = itemLayoutView.findViewById(R.id.principal_amt);
            inst_amt = itemLayoutView.findViewById(R.id.inst_amt);
            insurance_amt = itemLayoutView.findViewById(R.id.insurance_amt);
            exp_bal = itemLayoutView.findViewById(R.id.exp_bal);
        }
    }
}
