package com.example.orcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class Registeration_form extends AppCompatActivity implements View.OnClickListener {

    EditText etName,etmobno,etdob,etvehicleNo,etgroup;
    Button bt_register;
    DatePick datePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_form);

        datePick=new DatePick();
        etName=findViewById(R.id.etName);
        etmobno=findViewById(R.id.etmobno);
        etdob=findViewById(R.id.etdob);
        etvehicleNo=findViewById(R.id.etvehicleNo);
        etgroup=findViewById(R.id.etgroup);
        bt_register=findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        etdob.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.etdob:
                datePick.selectDate(Registeration_form.this,etdob);
                break;
            case R.id.bt_register:
                String name= etName.getText().toString();
                String mobileno= etmobno.getText().toString();
                String dob= etdob.getText().toString();
                String vehicle= etvehicleNo.getText().toString();
                String group= etgroup.getText().toString();

                if(Validate()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\nName="+name+"\nMobileNo="+mobileno+"\nDate_of_birth="+dob+"\nVehicleNo="+vehicle+"\nGroup="+group+"\n");
                    Intent i = new Intent(Registeration_form.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("QrString", (CharSequence) sb);
                    startActivity(i);
                    break;
                }
        }
    }
    private boolean Validate() {
        if (etName.length() == 0) {
            etName.setError("This field is required");
            return false;
        }

        if (etmobno.length() == 0) {
            etmobno.setError("This field is required");
            return false;
        }

        if (etdob.length() == 0) {
            etdob.setError("DOB is required");
            return false;
        }

        if (etvehicleNo.length() == 0) {
            etvehicleNo.setError("VehicleNo is required");
            return false;
        }

        if (etgroup.length() == 0) {
            etgroup.setError("Group is required");
            return false;
        }
        return true;
    }
}