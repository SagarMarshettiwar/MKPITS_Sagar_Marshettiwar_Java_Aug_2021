package com.trustbank.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trustbank.Model.BeneficiaryModal;
import com.trustbank.R;
import com.trustbank.activity.IntraBankTransferActivity;
import com.trustbank.interfaces.GetBeneficiaryListInterface;
import com.trustbank.util.AppConstants;
import com.trustbank.util.TrustMethods;

import java.util.ArrayList;
import java.util.Locale;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.ViewHolder> {
    private static final String TAG = BeneficiaryAdapter.class.getSimpleName();
    Activity mActivity;
    TrustMethods trustMethods;
    private ArrayList<BeneficiaryModal> beneficiaryList;
    private CoordinatorLayout coordinatorLayout;
    GetBeneficiaryListInterface getBeneficiaryListInterface;
    private ArrayList<BeneficiaryModal> arraylist;

    public BeneficiaryAdapter(Activity activity, ArrayList<BeneficiaryModal> beneficiaryList) {
        this.mActivity = activity;
        this.beneficiaryList = beneficiaryList;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(beneficiaryList);
        trustMethods = new TrustMethods(mActivity);
        getBeneficiaryListInterface = (GetBeneficiaryListInterface) activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_beneficiary_data, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final BeneficiaryModal beneficiaryModal = beneficiaryList.get(position);
        viewHolder.txtBenf.setText(beneficiaryModal.getBenId());

        if (!TextUtils.isEmpty(beneficiaryModal.getBenNickname().trim())) {
            viewHolder.txtNickname.setText(beneficiaryModal.getBenNickname());
            viewHolder.txtNickname.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtNickname.setVisibility(View.GONE);
        }

        if (beneficiaryModal.getBenType().equals("1")) {

            viewHolder.txtAccName.setText(beneficiaryModal.getBanAccName());
            viewHolder.txtAccNumber.setText(beneficiaryModal.getBenAccNo());
            viewHolder.txtagencyname.setText(beneficiaryModal.getAgncyname());


            viewHolder.accNameLayout.setVisibility(View.VISIBLE);
            viewHolder.accNoLayout.setVisibility(View.VISIBLE);
            viewHolder.mobNoLayout.setVisibility(View.GONE);

            if (AppConstants.getMnu_manage_beneficiaries().equalsIgnoreCase("1")) {
                viewHolder.btnWithIn.setVisibility(View.VISIBLE);
            }

        }

        final String benfId = viewHolder.txtBenf.getText().toString().trim();
        viewHolder.txtNickname.setOnClickListener(view -> confimationRemoveDialog(benfId));



       /* viewHolder.btnWithIn.setOnClickListener(v -> {
            try {
                if (!TextUtils.isEmpty(beneficiaryModal.getBanAccName().trim())) {
                    Intent intent = new Intent(mActivity, IntraBankTransferActivity.class);
                    intent.putExtra("beneficiaryList", beneficiaryList);
                    intent.putExtra("beneficiaryNickName", beneficiaryModal.getBenNickname());
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mActivity.startActivity(intent);
                    trustMethods.activityOpenAnimation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Button btnWithIn;
        public TextView txtBenf, txtNickname,txtagencyname, txtAccName, txtAccNumber, txtMobNo,txtUpicustomerName;
        private LinearLayout accNameLayout, accNoLayout, mobNoLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtBenf = itemLayoutView.findViewById(R.id.txtBenfId);
            txtNickname = itemLayoutView.findViewById(R.id.txtNicknameId);
            txtAccName = itemLayoutView.findViewById(R.id.txtAccNameId);
            txtAccNumber = itemLayoutView.findViewById(R.id.txtAccNumberId);
            txtMobNo = itemLayoutView.findViewById(R.id.txtMobNoId);
            txtagencyname = itemLayoutView.findViewById(R.id.txtagencyname);
//            imgDeleteBeneficiary = itemLayoutView.findViewById(R.id.imgDeleteBeneficiaryId);
            coordinatorLayout = itemLayoutView.findViewById(R.id.coordinatorLayout);
            btnWithIn = itemLayoutView.findViewById(R.id.btnWithInId);
            accNameLayout = itemLayoutView.findViewById(R.id.accNameLayoutId);
            accNoLayout = itemLayoutView.findViewById(R.id.accNoLayoutId);
            mobNoLayout = itemLayoutView.findViewById(R.id.mobNoLayoutId);
        }
    }

    @Override
    public int getItemCount() {
        return beneficiaryList.size();
    }

    private void confimationRemoveDialog(final String benfId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
        alert.setTitle(mActivity.getString(R.string.app_name));
        alert.setMessage(mActivity.getResources().getString(R.string.toremove));
        alert.setPositiveButton(mActivity.getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                getBeneficiaryListInterface.GetBeneficiaryClick(benfId);
                dialog.dismiss();
            }
        });

        alert.setNegativeButton(mActivity.getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        beneficiaryList.clear();
        if (charText.length() == 0) {
            beneficiaryList.addAll(arraylist);
        } else {
            for (BeneficiaryModal wp : arraylist) {
                if (wp.getBenNickname().toLowerCase(Locale.getDefault()).startsWith(charText) || wp.getBenIfscCode().toLowerCase(Locale.getDefault()).startsWith(charText)) {
                    beneficiaryList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
