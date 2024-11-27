package com.trustbank.adapter;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.activity.AccountDetailsActivity;
import com.trustbank.activity.FrmAccountStatement;
import com.trustbank.util.TrustMethods;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {
    private static final String TAG = AccountsAdapter.class.getSimpleName();
    Activity mActivity;
    TrustMethods trustMethods;
    private ImageTextMenuModel[] accountItemsData;
    private String newName = "Name";

    public AccountsAdapter(Activity activity, ImageTextMenuModel[] accountItemsData) {
        this.mActivity = activity;
        this.accountItemsData = accountItemsData;
        trustMethods = new TrustMethods(mActivity);

        Log.d("NewerName", newName);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_accounts, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.accountMenuName.setText(accountItemsData[position].getLableName());
        viewHolder.imageiconview.setImageResource(accountItemsData[position].getImage());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView accountMenuName;
        public ImageView imageiconview;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            accountMenuName = itemLayoutView.findViewById(R.id.accountMenuNameId);
            imageiconview=itemLayoutView.findViewById(R.id.image_icon_view);
        }

        @Override
        public void onClick(View view) {
            switch (accountItemsData[getAdapterPosition()].getItemName().trim()) {

                case "Account Details":
                    Intent intentAccDetails = new Intent(view.getContext(), AccountDetailsActivity.class);
                    intentAccDetails.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccDetails);
                    trustMethods.activityOpenAnimation();
                    break;


                case "mPassBook":
                    Intent intentAccountStatement = new Intent(view.getContext(), FrmAccountStatement.class);
                    intentAccountStatement.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccountStatement);
                    trustMethods.activityOpenAnimation();
                    break;
                    
                default:
                    break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return accountItemsData.length;
    }
}
