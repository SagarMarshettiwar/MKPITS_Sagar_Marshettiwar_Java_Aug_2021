package com.example.orcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class Choose_Option extends AppCompatActivity implements View.OnClickListener {

    Button bt_Registration,bt_ScanQR,bt_deregister;
    String token;
    TrustMethods trustMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);
        String s=getResources().getString(R.string.set_server_IP);
        Log.e("myip",s);
        bt_Registration=findViewById(R.id.bt_Registration);
        bt_Registration.setOnClickListener(this);
        bt_ScanQR=findViewById(R.id.bt_ScanQR);
        bt_ScanQR.setOnClickListener(this);
        bt_deregister=findViewById(R.id.bt_deregister);
        bt_deregister.setOnClickListener(this);
        trustMethods=new TrustMethods();
        token=trustMethods.retreveqr_token(Choose_Option.this,"Token");
        if(token.equals("")){
            bt_ScanQR.setVisibility(View.GONE);
          //  bt_deregister.setVisibility(View.GONE);
        }else {
            bt_ScanQR.setVisibility(View.VISIBLE);
            bt_deregister.setVisibility(View.VISIBLE);
            bt_Registration.setVisibility(View. GONE);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_Registration:
                Intent i=new Intent(Choose_Option.this,Register_Device.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(i);
                break;
            case R.id.bt_ScanQR:
                Intent j=new Intent(Choose_Option.this,ScanQRcode.class);
                j.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(j);
                break;
            case R.id.bt_deregister:
                Intent k=new Intent(Choose_Option.this,Deregister_Device.class);
                k.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(k);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Choose_Option.this,Choose_Option.class);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(i);
    }
}