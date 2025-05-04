package com.trustbank.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.SlabDetailsModel;
import com.trustbank.R;

import java.util.List;


public class AccountsBalanceAdapter extends RecyclerView.Adapter<AccountsBalanceAdapter.ViewHolder> {
    Activity activity;
    List<SlabDetailsModel> alldata;
    public AccountsBalanceAdapter(Activity activity, List<SlabDetailsModel> alldata) {
        this.activity=activity;
        this.alldata=alldata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.only_accounts_balance_details, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.product_name.setText(alldata.get(position).getSchemeName());
        holder.acc_num.setText(alldata.get(position).getAcno());
        holder.acc_bal.setText(alldata.get(position).getBalance());
        holder.min_bal.setText(alldata.get(position).getMinimumbalance());
        holder.max_bal.setText(alldata.get(position).getMaxtranbalanceamount());

        if (alldata.get(position).getSlablevels() != null) {
            SlabAdapter adapter=new SlabAdapter(activity,alldata.get(position).getSlablevels());
            holder.rv_slab_level.setHasFixedSize(true);
            holder.rv_slab_level.setLayoutManager(new LinearLayoutManager(activity));
            holder.rv_slab_level.setAdapter(adapter);
        }
    }
    @Override
    public int getItemCount() {
        return alldata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView product_name, acc_num, acc_bal, min_bal, max_bal;
        RecyclerView rv_slab_level;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            product_name=itemLayoutView.findViewById(R.id.product_name);
            acc_num=itemLayoutView.findViewById(R.id.acc_num);
            acc_bal=itemLayoutView.findViewById(R.id.acc_bal);
            min_bal=itemLayoutView.findViewById(R.id.min_bal);
            max_bal=itemLayoutView.findViewById(R.id.max_bal);
            rv_slab_level=itemLayoutView.findViewById(R.id.rv_slab_level);

        }
    }
}
