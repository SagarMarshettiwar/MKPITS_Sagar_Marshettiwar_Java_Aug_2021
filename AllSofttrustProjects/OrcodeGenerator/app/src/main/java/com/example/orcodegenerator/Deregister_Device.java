
package com.example.orcodegenerator;

        import static com.example.orcodegenerator.RSAEncryptionUtils.encrypt;

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

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.view.ContentInfoCompat;

        import com.example.orcodegenerator.HttpWraper.HttpOperation;
        import com.example.orcodegenerator.HttpWraper.RandomNumberGenerator;
        import com.example.orcodegenerator.Interfaces.AlertDialogOKListner;
        import com.google.zxing.integration.android.IntentIntegrator;
        import com.google.zxing.integration.android.IntentResult;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.net.URL;

public class Deregister_Device extends AppCompatActivity implements View.OnClickListener, AlertDialogOKListner {
    TextView messageFormat;
    EditText username,password;
    Button bt_submit;
    String ausername,apassword,responseCode;
    TrustMethods trustMethods;
    AlertDialogOKListner alertDialogOKListner=this;

    String Rno;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in3);
        trustMethods=new TrustMethods();
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        Rno= RandomNumberGenerator.generateRandomAlphanumericNumber();
        bt_submit=findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Device Deregisterd", Toast.LENGTH_SHORT).show();

                /*new SendDataTask(intentResult.getContents()).execute();*/

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_submit:
                ausername =username.getText().toString();
                apassword =password.getText().toString();
                if (NetworkUtil.getConnectivityStatus(Deregister_Device.this)){
                new Deregister_Device.SendDataTask(Deregister_Device.this, Rno, ausername, apassword).execute();
            }else{
                    Toast.makeText(this, "Check internet Connection--", Toast.LENGTH_SHORT).show();
                }

        }
    }

    @Override
    public void onDialogOk(int resultCode) {
         if(resultCode==5){
             SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
             SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.clear();
             editor.apply();
             editor.commit();
             Intent i=new Intent(Deregister_Device.this,Choose_Option.class);
             i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(i);
         }
         else if(resultCode==6) {
             SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
             SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.clear();
             editor.apply();
             editor.commit();
             Intent i = new Intent(Deregister_Device.this, Choose_Option.class);
             i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(i);
         }
    }


    public class SendDataTask extends AsyncTask<Void, Void, String> {
        private final String API_URL =getResources().getString(R.string.set_server_IP)+"/qrapi";
        Context ctx;
        String result;
        String error;

        String rno,error_message;
        String u,p,token,encrypted,json_enc,stringWithoutNewLines;
        String action="deregister_device";
        TrustMethods trustMethods=new TrustMethods();

        public SendDataTask(Context ctx, String rno,String u,String p) {
            this.ctx=ctx;
            this.rno = rno;
            this.u = u;
            this.p = p;
            this.token=trustMethods.retreveqr_token(Deregister_Device.this,"Token");
        }

        @Override
        protected String doInBackground(Void... voids) {
            String jsonbody="{    \n" +
                    "    \"user_name\": \""+u+"\",\n" +
                    "    \"token\":\""+token+"\",\n" +
                    "    \"password\": \""+p+"\"\n" +
                    "\n" +
                    "}";


            try {
                URL url = new URL(API_URL);
                encrypted = encrypt(jsonbody, BuildConfig.public_key);
                json_enc="{\"d\":\""+encrypted+"\"}";
                stringWithoutNewLines = json_enc.replaceAll("\\r|\\n", "");
                Log.e("stringWithoutNewLines",stringWithoutNewLines);
                result= HttpOperation.post(String.valueOf(url),stringWithoutNewLines,action,rno);
                Log.e("response",result);

                try {
                    JSONObject responseObj = new JSONObject(result);
                     responseCode = responseObj.getString("response_code");
                    if(responseCode.equals("1")) {
                        JSONObject res=responseObj.getJSONObject("response");
                        String verfication=res.getString("verification_code");
                        Toast.makeText(ctx, verfication, Toast.LENGTH_SHORT).show();
                    }else{
                        error_message = responseObj.getString("error_message");
                        //error = responseObj.has("error_message") ? responseObj.getString("error_message") : "NA";
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
                TrustMethods.alertDialogOk(Deregister_Device.this, "","Your device deregistered successfully...","ok", 5, false, alertDialogOKListner);
            } else {
                TrustMethods.alertDialogOk(Deregister_Device.this, "",error_message,"ok", 6, false, alertDialogOKListner);


            }
        }
    }
}






