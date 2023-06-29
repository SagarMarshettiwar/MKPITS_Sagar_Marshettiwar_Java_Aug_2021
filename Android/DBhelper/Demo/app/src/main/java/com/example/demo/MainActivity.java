package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button Login,Register;
    EditText Uname,Upass,Uemail,Uaddress;
    DBHelper DB;
    //SharedPreferences sharedPreferences;


    /*private static final String SHARED_PREF_NAME="mypref";
    *//*private static final String KEY_UNAME="NAME";
    private static final String KEY_UPASS="PASS";*//*
    private static final String KEY_UEMAIL="EMAIL";
    private static final String KEY_UADDRESS="ADDRESS";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login=(Button) findViewById(R.id.btn_login);
        Register=(Button) findViewById(R.id.btn_register);
        Uname=(EditText) findViewById(R.id.re_username);
        Upass=(EditText) findViewById(R.id.re_password);
        Uemail=(EditText) findViewById(R.id.re_email);
        Uaddress=(EditText) findViewById(R.id.re_address);
        DB = new DBHelper(this);


       // sharedPreferences=getSharedPreferences("SHARED_PREF_NAME",MODE_PRIVATE);

        /*String name=sharedPreferences.getString(KEY_UNAME,null);
        if(name != null){
            Intent i2 = new Intent(MainActivity.this, Login.class);
            startActivity(i2);
        }
*/

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*SharedPreferences.Editor editor=sharedPreferences.edit();
               *//*editor.putString(KEY_UNAME,Uname.getText().toString());
               editor.putString(KEY_UPASS,Upass.getText().toString());*//*
               editor.putString(KEY_UEMAIL,Uemail.getText().toString());
               editor.putString(KEY_UADDRESS,Uaddress.getText().toString());
               editor.apply();*/

                String user = Uname.getText().toString();
                String pass = Upass.getText().toString();
                String email = Uemail.getText().toString();
                String address=Uaddress.getText().toString();

                if(user.equals("")||pass.equals("")||email.equals("")||address.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    boolean isValid=  DB.insertData(user,pass,email,address);
                    if(isValid==true) {
                        Toast.makeText(MainActivity.this, "Registered Success", Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(MainActivity.this, Login.class);
                        startActivity(i1);
                    }else{
                        Toast.makeText(MainActivity.this, "Register Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}