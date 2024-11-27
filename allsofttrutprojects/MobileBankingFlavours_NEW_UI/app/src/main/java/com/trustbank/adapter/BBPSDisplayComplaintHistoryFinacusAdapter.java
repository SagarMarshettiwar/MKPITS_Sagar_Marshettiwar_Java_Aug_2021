package com.trustbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.BBPSComplaintHistoryModelFinacus;
import com.trustbank.R;
import java.util.List;

public class BBPSDisplayComplaintHistoryFinacusAdapter extends RecyclerView.Adapter<BBPSDisplayComplaintHistoryFinacusAdapter.ViewHolder> {
    private Context mContext;
    private List<BBPSComplaintHistoryModelFinacus> complaintHistoryList;

    public BBPSDisplayComplaintHistoryFinacusAdapter(Context context, List<BBPSComplaintHistoryModelFinacus> complaintHistoryList) {
        this.mContext = context;
        this.complaintHistoryList = complaintHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bbps_display_complaint_history, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BBPSComplaintHistoryModelFinacus model = complaintHistoryList.get(position);

        holder.txt_complaint_type.setText(model.getComplaint_type());
        holder.txt_txn_ref_id.setText(model.getTxn_ref_id());
        holder.txt_description.setText(model.getDescription());
        holder.txt_complaint_id.setText(model.getComplaint_id());
        holder.txt_complaint_status.setText(model.getComplaint_status());
    }

    @Override
    public int getItemCount() {
        return complaintHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_complaint_type, txt_txn_ref_id, txt_description, txt_complaint_id, txt_complaint_status;
        LinearLayout share_ll;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_complaint_type = itemView.findViewById(R.id.txt_complaint_type);
            txt_txn_ref_id = itemView.findViewById(R.id.txt_txn_ref_id);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_complaint_id = itemView.findViewById(R.id.txt_complaint_id);
            txt_complaint_status = itemView.findViewById(R.id.txt_complaint_status);
            share_ll = itemView.findViewById(R.id.share_ll);
        }
    }

}
