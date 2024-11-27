package com.trustbank.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.SavingAccLevelModel;
import com.trustbank.Model.SlabDetailsModel;
import com.trustbank.R;
import java.util.List;

public class SlabAdapter extends RecyclerView.Adapter<SlabAdapter.ViewHolder> {
    Activity activity;
    List<SavingAccLevelModel> slablevels;
    public SlabAdapter(Activity activity, List<SavingAccLevelModel> slablevels) {
        this.activity=activity;
        this.slablevels=slablevels;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slab_level_details, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_interestRate.setText(slablevels.get(position).getFromLevel() +"-"+slablevels.get(position).getToLevel());
        holder.interestrate.setText(slablevels.get(position).getInterestRate()+"%");
    }
    @Override
    public int getItemCount() {
        return slablevels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
    TextView txt_interestRate,interestrate;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txt_interestRate=itemLayoutView.findViewById(R.id.txt_interestRate);
            interestrate=itemLayoutView.findViewById(R.id.interestrate);
        }
    }
}
