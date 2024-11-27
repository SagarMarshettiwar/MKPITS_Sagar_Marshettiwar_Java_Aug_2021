package com.trustbank.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.trustbank.R;
import com.trustbank.activity.LockActivity;
import com.trustbank.activity.SplashScreenActivity;
import com.trustbank.activity.VerifyMobileNumber;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.SessionManager;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SimSelectorDialogFragment extends DialogFragment implements View.OnClickListener , AlertDialogOkListener {
    ImageButton iv_sim2,iv_sim1;
    TextView textView;
    SessionManager session;
    AlertDialogOkListener alertDialogOkListener=this;
    String deviceId;
    TextView textView1;
    Context context;
    Button close_button;
    String UUIno;
    SubscriptionManager subscriptionManager;
    List<SubscriptionInfo> subscriptionInfoList;
    String stringCarrierName;
    SubscriptionInfo subscriptionInfo;
    TrustMethods trustMethods;
    CountDownTimer countDownTimer;

    public SimSelectorDialogFragment(Context context) {
        this.context=context;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialogFragment = new Dialog(Objects.requireNonNull(getActivity()));
        dialogFragment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFragment.setCanceledOnTouchOutside(false);
        return dialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sim_selector, container, false);
        inIt(view);
        return view;
    }

    private void inIt(View view) {
        trustMethods=new TrustMethods(context);
        UUIno= String.valueOf(UUID.randomUUID());
        iv_sim2=view.findViewById(R.id.iv_sim2);
        iv_sim2.setOnClickListener(this);
        iv_sim1=view.findViewById(R.id.iv_sim1);
        close_button=view.findViewById(R.id.close_button);
        iv_sim1.setOnClickListener(this);
        textView=view.findViewById(R.id.textView);
        textView1=view.findViewById(R.id.textView1);
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS
        }, PackageManager.PERMISSION_GRANTED);
        try {
            subscriptionManager = (SubscriptionManager) context.getSystemService(context.TELEPHONY_SUBSCRIPTION_SERVICE);
            subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            int num = 1;
            int num2 = 2;
            if (num == 1) {
                subscriptionInfo = subscriptionInfoList.get(Integer.valueOf(num) - 1);
                stringCarrierName = (String) subscriptionInfo.getCarrierName();
                textView.setText(stringCarrierName);
            }

            if (num2 == 2) {
                subscriptionInfo = subscriptionInfoList.get(Integer.valueOf(num2) - 1);
                stringCarrierName = (String) subscriptionInfo.getCarrierName();
                textView1.setText(stringCarrierName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendSms(SubscriptionInfo subscriptionInfo, String message) {
        int subId = subscriptionInfo.getSubscriptionId();
        trustMethods.setSubscripId(getActivity(), Integer.toString(subId));

        SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(subId);
        String phoneNumber = AppConstants.getSms_verify_number();
        try {
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            ProgressDialog pDialog1 = new ProgressDialog(getActivity());;
            pDialog1.setMessage("Please wait... Mobile number is verifying with Bank");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(false);

            countDownTimer = new CountDownTimer(15000, 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    pDialog1.show();
                    Log.e("TICK", Long.toString(millisUntilFinished/1000)+"sec");
                }

                @Override
                public void onFinish() {
                    if (pDialog1.isShowing()) {
                        pDialog1.dismiss();
                    }
                    Log.e("TIMER FINISH", "ASYNCTASK ");
                    new VerifySinasync(context,UUIno,deviceId).execute();
                }
            }.start();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to send SMS: ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sim1:
                getSimName("1");
                break;
            case R.id.iv_sim2:
                getSimName("2");
                break;
            case R.id.close_button:

                break;
        }
    }

    private void getSimName(String s) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            textView.setText("READ PHONE STATE Permission not granted");
            return;
        }
        try {
            subscriptionInfo = subscriptionInfoList.get(Integer.valueOf(s) - 1);// stringCarrierName = (String) subscriptionInfo.getCarrierName();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

            int subId = subscriptionInfo.getSubscriptionId();
            trustMethods.setSubscripId(getActivity(),Integer.toString(subId));

            String smsdata = ""+UUIno+"|||"+deviceId+"";
//           String smsdata = "f1a6f312-c8b5-4021-9385-2a03ad561189|||"+deviceId+"";
            Log.e("smsdata", smsdata );
            byte[] bytesToEncode = smsdata.getBytes();
            String encodedData = Base64.encodeToString(bytesToEncode, Base64.DEFAULT);
            sendSms(subscriptionInfo, "MBANKREG "+encodedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        try {
            switch (resultCode) {

                case 0:
                    Intent intent = new Intent(context, LockActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    trustMethods.activityCloseAnimation();
                    break;

                case 55:
                    dismiss();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class VerifySinasync extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        String errorCode = "";
        String mobileNumber;
        String deviceId;
        String randomUUID;
        String action = "VERIFY_SMS_MOBILE_NUMBER";
        ProgressDialog pDialog;


        public VerifySinasync(Context ctx, String randomUUID, String deviceId) {
            this.ctx = ctx;
            this.randomUUID = randomUUID;
            this.deviceId = deviceId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = TrustURL.MobileNoVerifyUrl();//"B942F6E9-2927-4D1F-8EB1-4391526489F2"
                String jsonString = "{\"refid\":\"" + randomUUID+ "\",\"imei\":\"" + "" + "\",\"icc_id\":\"" + "" + "\", \"android_id\":\"" +deviceId+  "\"}";
//               String jsonString = "{\"refid\":\"" +"f1a6f312-c8b5-4021-9385-2a03ad561189"+ "\",\"imei\":\"" + "" + "\",\"icc_id\":\"" + "" + "\", \"android_id\":\"" +deviceId+  "\"}";

                if (!url.equals("")) {
                    response = HttpClientWrapper.post(url,jsonString,action);
                }
                if (response == null || response.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }
                JSONObject jsonResult = (new JSONObject(response));
                if (jsonResult.has("error")) {
                    error = jsonResult.getString("error");
                    return error;
                }
                String responseCode = jsonResult.has("response_code") ? jsonResult.getString("response_code") : "NA";
                if(responseCode.equals("1")){

                    JSONObject response = jsonResult.getJSONObject("response");
                    JSONObject misc = response.getJSONObject("misc");
                    mobileNumber = misc.getString("p_out_mobile_number");
                    //Toast.makeText(WelcomeScreen.this, ""+mobileNumber, Toast.LENGTH_SHORT).show();
                }else{
                    errorCode = jsonResult.has("error_code") ? jsonResult.getString("error_code") : "NA";
                    error = jsonResult.has("error_message") ? jsonResult.getString("error_message") : "NA";
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
                        AlertDialogMethod.alertDialogOk(context, getResources().getString(R.string.error_session_expire),
                                "",
                                getResources().getString(R.string.btn_ok), 0, false, alertDialogOkListener);
                    } else {
                        AlertDialogMethod.alertDialogOk(context, " ",
                                this.error,
                                getResources().getString(R.string.btn_ok), 55, false, alertDialogOkListener);
                    }
                }else {
                    Toast.makeText(getActivity(), "Mobile Number Verified", Toast.LENGTH_SHORT).show();
                    Thread background;
                    background = new Thread() {
                        public void run() {
                            try {
                                sleep(2000);
                                Intent intent = new Intent(context, VerifyMobileNumber.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                intent.putExtra("simnumber", mobileNumber);
                                startActivity(intent);
                                trustMethods.activityOpenAnimation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    /// start thread
                    background.start();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
