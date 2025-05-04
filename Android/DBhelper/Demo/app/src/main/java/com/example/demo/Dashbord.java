package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Dashbord extends AppCompatActivity {
    TextView UNametext,UPasstext,UEmailtext,UAddresstext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        UNametext=(TextView) findViewById(R.id.db_username);
        UPasstext=(TextView) findViewById(R.id.db_password);
        UEmailtext=(TextView) findViewById(R.id.db_email);
        UAddresstext=(TextView) findViewById(R.id.db_address);


        String name=getIntent().getStringExtra("username");
        String pass=getIntent().getStringExtra("userpass");
        String email=getIntent().getStringExtra("useremail");
        String address=getIntent().getStringExtra("useraddress");


        UNametext.setText(name);
        UPasstext.setText(pass);
        UEmailtext.setText(email);
        UAddresstext.setText(address);


    }
}