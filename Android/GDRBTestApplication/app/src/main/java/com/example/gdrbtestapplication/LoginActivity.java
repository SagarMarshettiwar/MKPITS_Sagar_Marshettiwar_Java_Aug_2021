package com.example.gdrbtestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gdrbtestapplication.Model.UserModel;
import com.example.gdrbtestapplication.WapperClass.HttpWapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_username;
    EditText et_password;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inIt();
    }
    private void inIt() {
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username=et_username.getText().toString();
        String password=et_password.getText().toString();
        new VerifyLoginAsyncTask(LoginActivity.this,"surez.sbi23@gmail.com","12121212","exzcde").execute();

    }
    @SuppressLint("StaticFieldLeak")
    private class VerifyLoginAsyncTask extends AsyncTask<Void, Integer, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        List<UserModel> list;
        String username, password ,deviceid;
        String result;

        public VerifyLoginAsyncTask(Context ctx, String username, String password,String deviceid) {
            this.ctx = ctx;
            this.username = username;
            this.password = password;
            this.deviceid = deviceid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMax(45);
            pDialog.setMessage("Verifying User Credentials");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                String url ="https://telugumatches.in/api/login";
                String jsonString = "{\"email\":\"" + username + "\",\"password\":\"" + password + "\",\"device_id\":\"" + deviceid +"\"}";
                result = HttpWapper.post(url, jsonString);
                list=new ArrayList<>();
                JSONObject jsonObject = (new JSONObject(result));
                String status = jsonObject.getString("status");
                String message = jsonObject.getString("message");
                String token = jsonObject.getString("token");
                JSONObject userObject = jsonObject.getJSONObject("user");
                String userId = userObject.getString("id");
                String firstName = userObject.getString("first_name");
                String lastName = userObject.getString("last_name");
                String email = userObject.getString("email");
                String phone = userObject.getString("phone");
                String blocked = userObject.getString("blocked");
                String deactivated = userObject.getString("deactivated");
                String approved = userObject.getString("approved");
                String userProfile = userObject.getString("user_profile");
                String country = userObject.getString("country");
                String state = userObject.getString("state");
                String city = userObject.getString("city");
                JSONArray jsonArray=jsonObject.getJSONArray("package_details");
                JSONObject packageObject=jsonArray.getJSONObject(0);
                String packageName = packageObject.getString("package_name");
                String packageValidity = packageObject.getString("package_validity");
                String remainingChat = packageObject.getString("remaining_chat");
                String remainingInterest = packageObject.getString("remaining_interest");
                String remainingContactView = packageObject.getString("remaining_contact_view");
                String autoProfileMatch = packageObject.getString("auto_profile_match");
                String profileHighlight = packageObject.getString("profile_highlight");
                String imageUrl = packageObject.getString("image_url");
                UserModel model=new UserModel(status,message,token,userId,firstName,lastName,email,phone,blocked,deactivated,approved,userProfile,country,state,city,packageName,packageValidity,remainingChat,remainingInterest,remainingContactView,autoProfileMatch,profileHighlight,imageUrl);
                UserModel model1=new UserModel(status,message,token,userId,firstName,lastName,email,phone,blocked,deactivated,approved,userProfile,country,state,city,packageName,packageValidity,remainingChat,remainingInterest,remainingContactView,autoProfileMatch,profileHighlight,imageUrl);
                UserModel model2=new UserModel(status,message,token,userId,firstName,lastName,email,phone,blocked,deactivated,approved,userProfile,country,state,city,packageName,packageValidity,remainingChat,remainingInterest,remainingContactView,autoProfileMatch,profileHighlight,imageUrl);
                UserModel model3=new UserModel(status,message,token,userId,firstName,lastName,email,phone,blocked,deactivated,approved,userProfile,country,state,city,packageName,packageValidity,remainingChat,remainingInterest,remainingContactView,autoProfileMatch,profileHighlight,imageUrl);
                UserModel model4=new UserModel(status,message,token,userId,firstName,lastName,email,phone,blocked,deactivated,approved,userProfile,country,state,city,packageName,packageValidity,remainingChat,remainingInterest,remainingContactView,autoProfileMatch,profileHighlight,imageUrl);
                list.add(model);
                list.add(model1);
                list.add(model2);
                list.add(model3);
                list.add(model4);
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            }
            return response;
        }
        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            pDialog.dismiss();
            if (result == null || result.equals("")) {
                error = "Invalid credentials";
                Toast.makeText(ctx, error, Toast.LENGTH_SHORT).show();
            }else{
                Intent i=new Intent(LoginActivity.this,DetailsViewActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("DetailList",(Serializable) list);
                startActivity(i);
            }
        }
    }
}