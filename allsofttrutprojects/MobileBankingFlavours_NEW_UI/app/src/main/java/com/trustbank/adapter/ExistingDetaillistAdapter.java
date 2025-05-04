package com.trustbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trustbank.Model.SIModel;
import com.trustbank.R;
import com.trustbank.activity.StandingInstructionActivity;

import java.util.List;

public class ExistingDetaillistAdapter  extends ArrayAdapter<SIModel> {
    Context context;
    List<SIModel> siModels;

    public ExistingDetaillistAdapter(Context context, List<SIModel> siModels) {
        super(context,0,siModels);
        this.context=context;
        this.siModels=siModels;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.existing_si_listview, parent, false);
        }

        TextView exec_date=currentItemView.findViewById(R.id.exec_date);
        TextView Loan_amt=currentItemView.findViewById(R.id.Loan_amt);

        exec_date.setText(siModels.get(position).getScheduleExecutionDate().split("T")[0]);
        Loan_amt.setText(siModels.get(position).getLoanAmount());

        // then return the recyclable view
        return currentItemView;
    }
}
