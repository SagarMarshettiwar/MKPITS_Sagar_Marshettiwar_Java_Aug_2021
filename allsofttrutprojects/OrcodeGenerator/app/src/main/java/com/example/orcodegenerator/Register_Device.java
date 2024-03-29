package com.example.orcodegenerator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static  com.example.orcodegenerator.RSAEncryptionUtils.decrypt;
import static  com.example.orcodegenerator.RSAEncryptionUtils.encrypt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class Register_Device extends AppCompatActivity implements View.OnClickListener, AlertDialogOKListner {
        TextView messageFormat;
        EditText username,password;
        Button bt_QR;
    public static String imeiName;
        String ausername,apassword,input;
    String vercode;


    TrustMethods trustMethods;
        AlertDialogOKListner alertDialogOKListner=this;

        String Rno;
        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_in2);
            trustMethods=new TrustMethods();
            username=findViewById(R.id.username);
            password=findViewById(R.id.password);
            Rno= RandomNumberGenerator.generateRandomAlphanumericNumber();
            bt_QR=findViewById(R.id.bt_QR);
            bt_QR.setOnClickListener(this);
            Log.e("rno",Rno);
            imeiName = trustM.getIMEINumber(Register_Device.this);

        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String decrypted = null;
            try {
                Log.e("out",intentResult.getContents());
                decrypted =decrypt(intentResult.getContents(),BuildConfig.private_key);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("RSAEncryptionUtils", "Decrypted: " + decrypted);
             input = decrypted;
            String pattern = "[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}";

            if (input.matches(pattern)) {
                trustMethods.qr_token(Register_Device.this,"Token",input);
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                editor.commit();
                Log.e("invalid pattern","input");
            }

            if (intentResult != null) {
                if (intentResult.getContents() == null) {
                    Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                } else if ( NetworkUtil.getConnectivityStatus(Register_Device.this) ){

                    new Register_Device.RegesterDataTask(Register_Device.this,trustMethods.retreveqr_token(Register_Device.this,"Token"),Rno,ausername,apassword).execute();
                }else{
                    Toast.makeText(this, "Check internet Connection--", Toast.LENGTH_SHORT).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_QR:
                    IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                    intentIntegrator.setOrientationLocked(false);
                    intentIntegrator.setPrompt("Scan a barcode or QR Code");
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
                    intentIntegrator.initiateScan();
                    ausername =username.getText().toString();
                    apassword =password.getText().toString();
            }
        }

        @Override
        public void onDialogOk(int resultCode) {
            if(resultCode==1) {
                new Register_Device.registerdevicecheck(Register_Device.this,input).execute();

            }
            else if(resultCode==2) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                editor.commit();
                Intent i = new Intent(Register_Device.this, Choose_Option.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            else if(resultCode==3) {
                Intent i = new Intent(Register_Device.this, Choose_Option.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
            else if(resultCode==4) {
                Intent i = new Intent(Register_Device.this, Choose_Option.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                editor.commit();

            }
        }


        public class RegesterDataTask extends AsyncTask<Void, Void, String> {
            private final String API_URL = getResources().getString(R.string.set_server_IP)+"/qrapi";
            Context ctx;
            String result;
            String error;
            String token;
            String rno;
            String verfication;
            String u,p;
            String encrypted;
            String json_enc,decrypted;
            String error_message;

            String stringWithoutNewLines;
            String action="register_device";


            public RegesterDataTask(Context ctx, String token, String rno,String u,String p) {
                this.ctx=ctx;
                this.token = token;
                this.rno = rno;
                this.u = u;
                this.p = p;
            }

            @Override
            protected String doInBackground(Void... voids) {
                String jsonbody="{\n" +
                        "    \"user_name\": \""+u+"\",\n" +
                        "    \"token\":\""+token+"\",\n" +
                        "    \"password\": \""+p+"\",    \n" +
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
                    Log.e("json_enc",stringWithoutNewLines);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    URL url = new URL(API_URL);
                    // Encrypt using the public key
                    result= HttpOperation.post(String.valueOf(url),stringWithoutNewLines,action,rno);
                    Log.e("response",result);
                        JSONObject responseObj = new JSONObject(result);
                        String responseCode = responseObj.getString("response_code");
                        if(responseCode.equals("1")) {
                            String response = responseObj.getString("response");
                            decrypted = decrypt(response, BuildConfig.private_key);
                            Log.e("responseCode", decrypted);
                            JSONObject jsonObject = new JSONObject(decrypted);
                            vercode = jsonObject.getString("verification_code");
                        }else{
                            error_message = responseObj.getString("error_message");
                        }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (vercode != null) {
                    TrustMethods.alertDialogOk(Register_Device.this, "","Your Verification code is="+vercode,"ok", 1, false, alertDialogOKListner);
                } else {
                    TrustMethods.alertDialogOk(Register_Device.this, "", error_message, "ok", 2, false, alertDialogOKListner);
                }
            }
        }

        public class registerdevicecheck extends AsyncTask<Void, Void, String> {
        private final String API_URL = getResources().getString(R.string.set_server_IP)+"/qrapi";
        Context ctx;
        String result;
        String error;
        String token;
        String rno;
        String verfication;
        String u,p;
        String encrypted;
        String json_enc,decrypted;
        String error_message;

        String stringWithoutNewLines,responseCode;
        String action="register_device_check";


        public registerdevicecheck(Context ctx, String token) {
            this.ctx=ctx;
            this.token = token;

        }

        @Override
        protected String doInBackground(Void... voids) {
            String jsonbody="{\"token\":\""+token+"\"}";
            try {
                encrypted = encrypt(jsonbody, BuildConfig.public_key);
                json_enc="{\"d\":\""+encrypted+"\"}";
                stringWithoutNewLines = json_enc.replaceAll("\\r|\\n", "");
                Log.e("json_enc",stringWithoutNewLines);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(API_URL);
                // Encrypt using the public key
                result= HttpOperation.post(String.valueOf(url),stringWithoutNewLines,action,rno);
                Log.e("response",result);
                JSONObject responseObj = new JSONObject(result);
                 responseCode = responseObj.getString("response_code");
                if(responseCode.equals("1")) {
                    Log.e("responseCode", responseCode);
                }else{
                    error_message = responseObj.getString("error_message");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if (responseCode.equals("1")) {
                TrustMethods.alertDialogOk(Register_Device.this, "","Device Registered Successfully...","ok", 3, false, alertDialogOKListner);
            } else {
                TrustMethods.alertDialogOk(Register_Device.this, "", error_message, "ok", 4, false, alertDialogOKListner);
            }
        }
    }
    }






