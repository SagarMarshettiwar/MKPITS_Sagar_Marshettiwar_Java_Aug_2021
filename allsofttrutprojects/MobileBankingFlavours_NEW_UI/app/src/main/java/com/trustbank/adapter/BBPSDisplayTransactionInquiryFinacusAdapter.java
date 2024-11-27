package com.trustbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.BBPSTransactionInquiryFinacusModel;
import com.trustbank.R;

import java.util.List;

public class BBPSDisplayTransactionInquiryFinacusAdapter extends RecyclerView.Adapter<BBPSDisplayTransactionInquiryFinacusAdapter.ViewHolder> {
    private Context mContext;
    private List<BBPSTransactionInquiryFinacusModel> transInquiryList;

    public BBPSDisplayTransactionInquiryFinacusAdapter(Context context, List<BBPSTransactionInquiryFinacusModel> transInquiryList) {
        this.mContext = context;
        this.transInquiryList = transInquiryList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bbps_display_transaction_inquiry_finacus_adapter, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BBPSTransactionInquiryFinacusModel model = transInquiryList.get(position);

        holder.txt_txn_date.setText(model.getTran_date());
        holder.txt_amount.setText(model.getAmount());
        holder.txt_txn_ref_id.setText(model.getTran_Ref_Id());
        holder.txt_status.setText(model.getStatus());
    }

    @Override
    public int getItemCount() {
        return transInquiryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_txn_date, txt_amount, txt_txn_ref_id, txt_status;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_txn_date = itemView.findViewById(R.id.txt_txn_date);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_txn_ref_id = itemView.findViewById(R.id.txt_txn_ref_id);
            txt_status = itemView.findViewById(R.id.txt_status);
        }
    }
}
