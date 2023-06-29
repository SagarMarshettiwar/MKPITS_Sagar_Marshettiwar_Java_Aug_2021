package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button Login1,Register1;
    EditText Uname1,Upass1;
    DBHelper DB;
   // SharedPreferences sharedPreferences;


   /* private static final String SHARED_PREF_NAME="mypref";
    *//*private static final String KEY_UNAME="NAME";
    private static final String KEY_UPASS="PASS";*//*
    private static final String KEY_UEMAIL="EMAIL";
    private static final String KEY_UADDRESS="ADDRESS";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login1=(Button) findViewById(R.id.lo_login);
        Register1=(Button) findViewById(R.id.lo_register);
        Uname1=(EditText) findViewById(R.id.lo_username);
        Upass1=(EditText) findViewById(R.id.lo_password);
        DB=new DBHelper(this);

        /*sharedPreferences=getSharedPreferences("SHARED_PREF_NAME",MODE_PRIVATE);
        *//*String name=sharedPreferences.getString(KEY_UNAME,null);
        String pass=sharedPreferences.getString(KEY_UPASS,null);*//*
        String email=sharedPreferences.getString(KEY_UEMAIL,null);
        String address=sharedPreferences.getString(KEY_UADDRESS,null);*/

            Login1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            String usernameValue = Uname1.getText().toString().trim();
                            String passwordValue = Upass1.getText().toString().trim();
                            /*if (usernameValue.equals(name) && passwordValue.equals(pass)) {
                               Toast.makeText(Login.this , "Success !", Toast.LENGTH_SHORT).show();
                                System.out.println("Success");
                                Intent i = new Intent(Login.this, Dashbord.class);
                                i.putExtra("username",usernameValue);
                                i.putExtra("userpass",passwordValue);
                                i.putExtra("useremail",email);
                                i.putExtra("useraddress",address);
                                startActivity(i);
                            } else {
                                Toast.makeText(Login.this , "Not Valid", Toast.LENGTH_SHORT).show();
                                System.out.println("Not Success");
                            }*/

                    if(usernameValue.equals("")||passwordValue.equals("")) {
                        Uname1.setError("username is null");
                        Upass1.setError("password is null");
                        Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    }else{
                        Boolean verify=DB.verify(usernameValue,passwordValue);
                        if(verify==true) {
                            Toast.makeText(Login.this, "Sign in successfull", Toast.LENGTH_SHORT).show();

                            /*Cursor res=DB.show(usernameValue);
                            if(res.getCount()==0){
                                Toast.makeText(Login.this, "No DATA", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            while(res.moveToNext()){
                                Intent i = new Intent(Login.this, Dashbord.class);
                                i.putExtra("username", res.getString(0));
                                i.putExtra("userpass", res.getString(1));
                                i.putExtra("useremail", res.getString(2));
                                i.putExtra("useraddress", res.getString(3));
                                startActivity(i);
                            }*/
                            Cursor res=DB.showAll();
                            if(res.getCount()==0){
                                Toast.makeText(Login.this, "No DATA", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            while(res.moveToNext()){
                                Intent i = new Intent(Login.this, Dashbord.class);
                                i.putExtra("username", res.getString(0));
                                i.putExtra("userpass", res.getString(1));
                                i.putExtra("useremail", res.getString(2));
                                i.putExtra("useraddress", res.getString(3));
                                startActivity(i);
                            }
                        }else{
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            Register1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                }
            });
    }
}