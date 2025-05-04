package com.example.orcodegenerator;

import static com.example.orcodegenerator.RSAEncryptionUtils.decrypt;
import static com.example.orcodegenerator.RSAEncryptionUtils.encrypt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orcodegenerator.HttpWraper.HttpOperation;
import com.example.orcodegenerator.HttpWraper.RandomNumberGenerator;
import com.example.orcodegenerator.Interfaces.AlertDialogOKListner;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ScanQRcode extends AppCompatActivity implements AlertDialogOKListner {
    TextView messageFormat;
    String Rno,decrypted;
    String responseCode;
    public static String imeiName;
    AlertDialogOKListner alertDialogOKListner=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        messageFormat = findViewById(R.id.textFormat);
        Rno=RandomNumberGenerator.generateRandomAlphanumericNumber();
        Log.e("rno",Rno);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
        intentIntegrator.initiateScan();
        imeiName = trustM.getIMEINumber(ScanQRcode.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                    //messageFormat.setText(intentResult.getContents().replace("\n", System.getProperty("line.separator")));;
                try {
                    decrypted =decrypt(intentResult.getContents(),BuildConfig.private_key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ( NetworkUtil.getConnectivityStatus(ScanQRcode.this) ){
                    new SendDataTask(ScanQRcode.this,decrypted,Rno).execute();

                }else{

                    Toast.makeText(this, "Check internet Connection--", Toast.LENGTH_SHORT).show();
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDialogOk(int resultCode) {
        if(resultCode==2){
            Intent i = new Intent(ScanQRcode.this, Choose_Option.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        if(resultCode==3){
            Intent i = new Intent(ScanQRcode.this, Choose_Option.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }


    public class SendDataTask extends AsyncTask<Void, Void, String> {

        private final String API_URL =getResources().getString(R.string.set_server_IP)+"/qrapi";// "https://192.168.0.16:8095/qrapi";
        Context ctx;
        String result;
        String error;
        String QRData;
        String rno,error_message;
        String tok,encrypted,json_enc,stringWithoutNewLines;
        String action="verify_qr_for_login";
        TrustMethods trustMethods=new TrustMethods();

        public SendDataTask(Context ctx, String QRData, String rno) {
            this.ctx=ctx;
            this.QRData = QRData;
            this.rno = rno;
            tok=trustMethods.retreveqr_token(ScanQRcode.this,"Token");
        }

        @Override
        protected String doInBackground(Void... voids) {
         String jsonbody="{\n" +
                 "    \"qr_code\": \""+QRData+"\",\n" +
                 "    \"token\": \""+tok+"\",\n" +
                 "    \"source_info\": {\n" +
                 "        \"ip\": \"192.168.0.1\",\n" +
                 "        \"user_agent\": \"Dalvik/2.1.0 (Linux; U; Android 12; RMP2108 Build/RKQ1.211119.001)\",\n" +
                 "        \"device_info\": {\n" +
                 "            \"imei\": \"\",\n" +
                 "            \"icc_no\": \"\",\n" +
                 "            \"android_id\": \""+imeiName+"\",\n" +
                 "            \"application_version\": \"1.0.0\",\n" +
                 "            \"android_version\": 12\n" +
                 "        }\n" +
                 "    }\n" +
                 "}";
            try {
                encrypted = encrypt(jsonbody, BuildConfig.public_key);
                json_enc="{\"d\":\""+encrypted+"\"}";
                stringWithoutNewLines = json_enc.replaceAll("\\r|\\n", "");
                URL url = new URL(API_URL);
                result= HttpOperation.post(String.valueOf(url),stringWithoutNewLines,action,rno);
                Log.e("response",result);
                try {
                    JSONObject responseObj = new JSONObject(result);
                     responseCode = responseObj.getString("response_code");
                    if(responseCode.equals("1")) {
                        Toast.makeText(ScanQRcode.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        error_message = responseObj.getString("error_message");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(responseCode.equals("1")) {
                TrustMethods.alertDialogOk(ScanQRcode.this, "","Login Successfully","ok", 2, false, alertDialogOKListner);
            } else {
                TrustMethods.alertDialogOk(ScanQRcode.this, "",error_message,"ok", 3, false, alertDialogOKListner);

            }
        }
    }
}