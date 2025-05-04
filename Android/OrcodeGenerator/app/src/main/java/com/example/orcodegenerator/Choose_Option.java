package com.example.orcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose_Option extends AppCompatActivity implements View.OnClickListener {

    Button bt_Registration,bt_ScanQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);

        bt_Registration=findViewById(R.id.bt_Registration);
        bt_Registration.setOnClickListener(this);
        bt_ScanQR=findViewById(R.id.bt_ScanQR);
        bt_ScanQR.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_Registration:
                Intent i=new Intent(Choose_Option.this,Registeration_form.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(i);
                break;
            case R.id.bt_ScanQR:
                Intent j=new Intent(Choose_Option.this,ScanQRcode.class);
                j.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(j);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Choose_Option.this,Sign_in.class);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(i);
    }
}