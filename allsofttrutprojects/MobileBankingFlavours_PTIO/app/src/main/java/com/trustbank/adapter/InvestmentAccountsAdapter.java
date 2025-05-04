package com.trustbank.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.trustbank.Model.InvestSlabModel;
import com.trustbank.R;
import java.util.List;

public class InvestmentAccountsAdapter extends RecyclerView.Adapter<InvestmentAccountsAdapter.ViewHolder> {
    Activity activity;
    List<InvestSlabModel> alldata;
    public InvestmentAccountsAdapter(Activity activity, List<InvestSlabModel> alldata) {
        this.activity=activity;
        this.alldata=alldata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.investaccount_slab, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_acc_num.setText(alldata.get(position).getAcno());
        holder.txt_prodName.setText(alldata.get(position).getSchemeName());
        holder.txt_depositAmt.setText(alldata.get(position).getDepositamt());
        holder.txt_maturityAmt.setText(alldata.get(position).getMaturityamt());
        holder.txt_intrst_rate.setText(alldata.get(position).getInterestrate());
        holder.txt_depositDate.setText(alldata.get(position).getDepositdate());
        holder.maturityDate.setText(alldata.get(position).getMaturitydate());
    }
    @Override
    public int getItemCount() {
        return alldata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txt_acc_num,txt_prodName,txt_depositAmt,txt_maturityAmt,txt_intrst_rate,txt_depositDate,maturityDate;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txt_acc_num=itemLayoutView.findViewById(R.id.txt_acc_num);
            txt_prodName=itemLayoutView.findViewById(R.id.txt_prodName);
            txt_depositAmt=itemLayoutView.findViewById(R.id.txt_depositAmt);
            txt_maturityAmt=itemLayoutView.findViewById(R.id.txt_maturityAmt);
            txt_intrst_rate=itemLayoutView.findViewById(R.id.txt_intrst_rate);
            txt_depositDate=itemLayoutView.findViewById(R.id.txt_depositDate);
            maturityDate=itemLayoutView.findViewById(R.id.maturityDate);
        }
    }
}
