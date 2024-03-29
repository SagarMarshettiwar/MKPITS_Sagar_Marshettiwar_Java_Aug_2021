package com.example.orcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sign_in extends AppCompatActivity implements View.OnClickListener {
    EditText etMobileNo,etpassword;
    Spinner spinnew;
    Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etMobileNo=findViewById(R.id.etMobileNo);
        etpassword=findViewById(R.id.etpassword);
        spinnew=findViewById(R.id.spinnew);
        bt_login=findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        LoadSpinner();
    }

    private void LoadSpinner() {
        List<String> method=new ArrayList<>();
        method.add("Select Method");
        method.add("Hi");
        method.add("Hello");
        method.add("Fine");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Sign_in.this, android.R.layout.simple_spinner_item, method);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnew.setAdapter(spinnerArrayAdapter);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_login:
                String mobileno =etMobileNo.getText().toString();
                String password =etpassword.getText().toString();
                if(mobileno.equalsIgnoreCase("1") && password.equalsIgnoreCase("a")){
                    Intent i=new Intent(Sign_in.this,Choose_Option.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity(i);
                }else{
                    if(etMobileNo.getText().toString().equals("")){
                        etMobileNo.setError("field cannot be blank");
                    }else {
                        etpassword.setError("field cannot be blank");
                    }
                }
        }
    }
}