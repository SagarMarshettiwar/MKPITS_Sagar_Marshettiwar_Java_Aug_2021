package com.trustbank.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.trustbank.R;
import com.trustbank.activity.IntraBankTransferActivity;
import com.trustbank.activity.TransactionSuccessActivity;
import com.trustbank.interfaces.GetAmountInterface;
import com.trustbank.util.TrustMethods;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class EnterAmountFragment extends DialogFragment implements View.OnClickListener {

    Button btn_cancel, btn_confirm;
    private TrustMethods method;
    EditText et_amount,etRemarks;
    TextView txt_benAccNo;
    String formattedString,benaccno;
    GetAmountInterface getAmountInterface;
    Context ctx;
    private DecimalFormat decimalFormat;

    public EnterAmountFragment(Context ctx, GetAmountInterface getAmountInterface, String benaccno) {
        this.getAmountInterface=getAmountInterface;
        this.ctx=ctx;
        this.benaccno=benaccno;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_self_account_transfer, container, false);
        inIt(view);
        return view;
    }

    private void inIt(View view) {
        method=new TrustMethods(getActivity());
        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        txt_benAccNo = view.findViewById(R.id.txt_benAccNo);
        et_amount = view.findViewById(R.id.et_amount);
        etRemarks = view.findViewById(R.id.etRemarks);

        txt_benAccNo.setText(benaccno);

        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        // Set up DecimalFormat for US style
        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###.##");

        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                et_amount.removeTextChangedListener(this);

                try {
                    String originalString = editable.toString();
                    Log.e("Original", originalString);

                    originalString = originalString.replaceAll(",", "");

                    int decimalIndex = originalString.indexOf(".");
                    if (decimalIndex != -1) {
                        String decimalPart = originalString.substring(decimalIndex);
                        String integerPart = originalString.substring(0, decimalIndex);
                        double doubleValue = Double.parseDouble(integerPart);
                        formattedString = decimalFormat.format(doubleValue) + decimalPart;
                        et_amount.setText(formattedString);
                    } else {
                        double doubleValue = Double.parseDouble(originalString);
                        formattedString = decimalFormat.format(doubleValue);
                        et_amount.setText(formattedString);
                    }
                    et_amount.setSelection(et_amount.getText().length());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                et_amount.addTextChangedListener(this);
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialogFragment = new Dialog(Objects.requireNonNull(getActivity()));
        dialogFragment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFragment.setCanceledOnTouchOutside(false);
        return dialogFragment;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_confirm:
                try{
                    if(formattedString == null){
                        formattedString="0";
                    }
                    String remark=etRemarks.getText().toString();
                    if(!TextUtils.isEmpty(remark)){
                        getAmountInterface.getAmount(formattedString.replace(",",""),remark);
                        dismiss();
                    }else{
                        etRemarks.setError("Remark cannot be blank");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                break;

            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
    @Override public void onAttach(Context context)
    {
        super.onAttach(context);
        getAmountInterface = (GetAmountInterface) context;

    }
}