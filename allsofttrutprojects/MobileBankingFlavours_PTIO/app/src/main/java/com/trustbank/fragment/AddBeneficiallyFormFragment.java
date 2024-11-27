package com.trustbank.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.trustbank.Model.GenerateStanRRNModel;
import com.trustbank.Model.GetUserProfileModal;
import com.trustbank.R;
import com.trustbank.activity.BeneficiaryTypeListActivity;
import com.trustbank.activity.LockActivity;
import com.trustbank.activity.MenuActivity;
import com.trustbank.activity.OtpVerificationActivity;
import com.trustbank.interfaces.AddBeneficiaryFragmentListener;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.tcpconnection.tdto.MessageDtoBuilder;
import com.trustbank.tcpconnection.tmessage.TMessage;
import com.trustbank.tcpconnection.tmessage.TMessageUtil;
import com.trustbank.tcpconnection.util.ResponseEntity;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class AddBeneficiallyFormFragment extends DialogFragment implements AlertDialogOkListener {

    private CoordinatorLayout coordinatorLayout;
    private EditText etNickName, etAccName, etAccNum, etIfscCode, etMobileNum, etMmid, etConfirmAccNumId, etConfirmMmidId, etUpiId, etUpiAccountNameId;
    private TextInputLayout ifscInputLayout;
    AddBeneficiaryFragmentListener addBeneficiallyFormFragment;
    private TrustMethods trustMethods;
    private TextView tvOwnBankIMPStoAccountTitle;
    private LinearLayout layoutForImpsToAccountNeftRtgs, layoutForImpsToMobile, verifyBenfLL, layoutForUPIId;
    private String ben_type;
    private ImageView searchIfscCodeId;
    private AlertDialogOkListener alertDialogOkListener = this;
    CardView cardbankDetailsId, cardVerifyBenDetailsId;
    TextView txtBankNameId, txtBranchNameId, txtBranchAddressId, txtVerifyBenId;
    private Button btnSaveBenef, bnVerifyBenefId,btnValidateBenefId;
    private ImageView backButton_new;
    private TextView toolbar;

    public static DialogFragment newInstance(String beneficiaryType) {
        AddBeneficiallyFormFragment fragment = new AddBeneficiallyFormFragment();
        Bundle args = new Bundle();
        args.putString("ben_type", beneficiaryType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // creating the fullscreen dialog
        Dialog dialogFragment = new Dialog(Objects.requireNonNull(getActivity()));
        dialogFragment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFragment.setContentView(root);
//        Objects.requireNonNull(dialogFragment.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_beneficially_form_fragment, container, false);

        Toolbar new_toolbar = view.findViewById(R.id.new_toolbar);
        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setText(R.string.txt_add_ben);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));

        inIt(view);
        return view;
    }

    private void inIt(View view) {
        try {
            trustMethods = new TrustMethods(getActivity());

            addBeneficiallyFormFragment = (AddBeneficiaryFragmentListener) getActivity();
//          ImageView cancelDialogue = view.findViewById(R.id.cancelDialogue);
            backButton_new = view.findViewById(R.id.backButton_new);
            backButton_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });
            etNickName = view.findViewById(R.id.etNickNameId);
            Switch switchWithinBank = view.findViewById(R.id.switchWithinBankId);

            etAccName = view.findViewById(R.id.etAccNameId);
            etAccNum = view.findViewById(R.id.etAccNumId);
            btnSaveBenef = view.findViewById(R.id.btnSaveBenefId);
            etIfscCode = view.findViewById(R.id.etIfscCodeId);
            etMobileNum = view.findViewById(R.id.etMobileNumId);
            etMmid = view.findViewById(R.id.etMmidId);
            ifscInputLayout = view.findViewById(R.id.ifscInputLayoutId);
            etConfirmAccNumId = view.findViewById(R.id.etConfirmAccNumId);
            etConfirmMmidId = view.findViewById(R.id.etConfirmMmidId);
            coordinatorLayout = view.findViewById(R.id.coordinatorLayout);

            searchIfscCodeId = view.findViewById(R.id.searchIfscCodeId);
            tvOwnBankIMPStoAccountTitle = view.findViewById(R.id.tvOwnBankIMPStoAccountTitle);
            layoutForImpsToAccountNeftRtgs = view.findViewById(R.id.layoutForImpsToAccountNeftRtgs);
            layoutForImpsToMobile = view.findViewById(R.id.layoutForImpsToMobile);

            cardbankDetailsId = view.findViewById(R.id.cardbankDetailsId);
            txtBankNameId = view.findViewById(R.id.txtBankNameId);
            txtBranchNameId = view.findViewById(R.id.txtBranchNameId);
            txtBranchAddressId = view.findViewById(R.id.txtBranchAddressId);
            verifyBenfLL = view.findViewById(R.id.verifyBenfLL);
            bnVerifyBenefId = view.findViewById(R.id.bnVerifyBenefId);
            cardVerifyBenDetailsId = view.findViewById(R.id.cardVerifyBenDetailsId);
            txtVerifyBenId = view.findViewById(R.id.txtVerifyBenId);

            layoutForUPIId = view.findViewById(R.id.layoutForUPIID);
            etUpiId = view.findViewById(R.id.etUpiId);
            etUpiAccountNameId = view.findViewById(R.id.etUpiAccountNameId);
            btnValidateBenefId = view.findViewById(R.id.btnValidateBenefId);

            assert getArguments() != null;
            if (getArguments().containsKey("ben_type")) {
                ben_type = getArguments().getString("ben_type");
                assert ben_type != null;
                switch (ben_type) {
                    case "1":
                        ifscInputLayout.setVisibility(View.GONE);
                        tvOwnBankIMPStoAccountTitle.setText(getActivity().getResources().getString(R.string.txt_intra_bank));
                        layoutForImpsToAccountNeftRtgs.setVisibility(View.VISIBLE);
                        layoutForImpsToMobile.setVisibility(View.GONE);
                        layoutForUPIId.setVisibility(View.GONE);
                        bnVerifyBenefId.setVisibility(View.GONE);
                        btnValidateBenefId.setVisibility(View.VISIBLE);
                        btnSaveBenef.setVisibility(View.GONE);
                        break;
                }
            }

                TrustMethods.setEditTextMaxLength(21, etAccNum);
                etAccNum.setRawInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etAccNum.setSingleLine(true);
                etAccNum.setTransformationMethod(PasswordTransformationMethod.getInstance());
                TrustMethods.setEditTextMaxLength(21, etConfirmAccNumId);
                etConfirmAccNumId.setInputType(InputType.TYPE_CLASS_TEXT);

            btnValidateBenefId.setOnClickListener(v -> {
                try {
                    if (!TextUtils.isEmpty(etNickName.getText().toString().trim())) {
                        if (ben_type.equals("1")) {
                            if (validateForOwnBank()) {
                                validateWithinBen(etAccNum.getText().toString().trim(), ben_type);
                            }
                        }
                    }else {
                        TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_blank_nickname), coordinatorLayout);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            btnSaveBenef.setOnClickListener(view1 -> {
                TrustMethods.hideSoftKeyboard(getActivity());

                if (!TextUtils.isEmpty(etNickName.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(etNickName.getText().toString().trim())) {
                        if (ben_type.equals("1")) {
                            addBeneficiary(AppConstants.getUSERMOBILENUMBER(), etNickName.getText().toString().trim(), etAccName.getText().toString().trim(), etAccNum.getText().toString().trim(), "", "", "", false, ben_type, "");
                        }
                    }
                } else {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_blank_nickname), coordinatorLayout);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBeneficiary(String mobileNo, String nickname, String accName, String accNo,
                                String ifscCode, String benfMobNo, String mmid, boolean withinChecked, String ben_type,
                                String upiId) {

        if (NetworkUtil.getConnectivityStatus(getActivity())) {
            if (TrustMethods.isSimAvailable(getActivity().getApplicationContext()) && TrustMethods.isSimVerified(getActivity())) {
                Intent intent = new Intent(getActivity(), OtpVerificationActivity.class);
                intent.putExtra("checkTransferType", "addBeneficiary");
                intent.putExtra("mobileNo", mobileNo);
                intent.putExtra("nickname", nickname);
                intent.putExtra("accName", accName);
                intent.putExtra("accNo", accNo);
                intent.putExtra("ifscCode", ifscCode);
                intent.putExtra("benfMobNo", benfMobNo);
                intent.putExtra("mmid", mmid);
                intent.putExtra("withinChecked", withinChecked);
                intent.putExtra("ben_type", ben_type);
                intent.putExtra("upi_id", upiId);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                getActivity().startActivity(intent);
                trustMethods.activityOpenAnimation();


            } else {
                TrustMethods.displaySimErrorDialog(getActivity());
            }
        } else {
            TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
        }

    }

    private boolean validateForOwnBank() {

       /* if (TextUtils.isEmpty(etAccName.getText().toString().trim())) {
            etAccName.setError("Required Account Name");
            return false;
        } else */
            if (TextUtils.isEmpty(etAccNum.getText().toString().trim())) {
            etAccNum.setError("Required Account No.");
            return false;
        } else if (!trustMethods.isValidAccNo(etAccNum.getText().toString().trim(), getActivity())) {
            etAccNum.setError("Enter Valid Account Number");
            return false;
        } else if (TextUtils.isEmpty(etConfirmAccNumId.getText().toString().trim())) {
            etConfirmAccNumId.setError("Required Confirm Account No.");
            return false;
        } else if (!etAccNum.getText().toString().trim().equals(etConfirmAccNumId.getText().toString().trim())) {
            etConfirmAccNumId.setError("Confirm Account No. not matched");
            return false;
        }else {
            return true;
        }

    }

    @Override
    public void onDialogOk(int resultCode) {
        if (resultCode == 1) {
            addBeneficiallyFormFragment.addBenficiaryClick();
            Intent fundTransferMenuIntent = new Intent(getActivity(), MenuActivity.class);
            fundTransferMenuIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(fundTransferMenuIntent);
            trustMethods.activityOpenAnimation();
            Objects.requireNonNull(getDialog()).dismiss();
        } else if (resultCode == 2) {
            //Dismiss alert dialog here.
        } else if (resultCode == 3) {
            //Dismiss alert dialog here.
        } else if (resultCode == 0) {
            Intent intent = new Intent(getActivity(), LockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            trustMethods.activityCloseAnimation();
        }else if(resultCode==55){
            //Dismiss alert dialog here.
        }

    }

    private void validateWithinBen(String accountNo, String ben_type) {
        try {
            if (TrustMethods.isSimAvailable(getActivity()) && TrustMethods.isSimVerified(getActivity())) {
                if (NetworkUtil.getConnectivityStatus(Objects.requireNonNull(getActivity()))) {
                    new BeneficieryAddValidationAsyncTask(getActivity(), accountNo, ben_type, "").execute();
                } else {
                    TrustMethods.showSnackBarMessage(getResources().getString(R.string.error_check_internet), coordinatorLayout);
                }
            } else {
                TrustMethods.displaySimErrorDialog(Objects.requireNonNull(getActivity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //check validation api call.
    @SuppressLint("StaticFieldLeak")
    private class BeneficieryAddValidationAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String finalResponse,accname;
        ProgressDialog pDialog;
        String response;
        String actionName = "VALIDATE_BEN";
        String result;
        private String errorCode;
        private String accountNo, benType, ifscCode;


        public BeneficieryAddValidationAsyncTask(Context ctx, String accountNo, String benType, String ifscCode) {
            this.ctx = ctx;
            this.accountNo = accountNo;
            this.benType = benType;
            this.ifscCode = ifscCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                String url = TrustURL.getURLForFundTransferOwnAndNeft();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ben_ac_no", accountNo);
                jsonObject.put("ben_type", benType);
                jsonObject.put("ben_ifsc", ifscCode);
                if (!url.equals("")) {
                    result = HttpClientWrapper.postWithActionAuthToken(url, jsonObject.toString(), actionName, AppConstants.getAuth_token());

                }
                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));
                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }
                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";

                if (responseCode.equals("1")) {
                    finalResponse = jsonResponse.has("response") ? jsonResponse.getString("response") : "NA";
                   JSONObject jsonObjectRes = jsonResponse.has("response") ? jsonResponse.getJSONObject("response"):null;
                   if (jsonObjectRes != null){
                       JSONArray tablejsonarray= jsonObjectRes.has("Table")? jsonObjectRes.getJSONArray("Table"):null;
                       if (tablejsonarray != null){
                           accname = tablejsonarray.getJSONObject(0).getString("accname");
                       }
                   }


                    //accname = jsonResponse.has("accname") ? jsonResponse.getString("accname") : "";
                } else {
                    errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                    error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                }

            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (!this.error.equals("")) {
                    if (TrustMethods.isSessionExpired(errorCode)) {
                        AlertDialogMethod.alertDialogOk(getActivity(), getResources().getString(R.string.error_session_expire),
                                "",
                                getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        //TrustMethods.showSnackBarMessage(this.error, coordinatorLayout);
                        AlertDialogMethod.alertDialogOk(getActivity()," ",
                                this.error,
                                getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }

                } else {
                    if (benType.equalsIgnoreCase("1")) {
                        btnSaveBenef.setVisibility(View.VISIBLE);
                        etAccName.setText(accname);
                        etAccName.setClickable(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
