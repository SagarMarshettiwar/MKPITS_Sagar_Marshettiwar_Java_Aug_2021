package com.example.dbhelpertest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editname,editphone;
    Button btnsave,btnshow,btnupdate;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editname=findViewById(R.id.editname);
        editphone=findViewById(R.id.editphone);
        btnupdate=findViewById(R.id.btnupdate);
        btnshow=findViewById(R.id.btnshow);
        btnsave=findViewById(R.id.btnsave);

        DbHandler db=new DbHandler(MainActivity.this);
        Intent intent = getIntent();
        int i=intent.getIntExtra("edit",-1);
        if(i!=-1){
            List<model> clientlist=db.editClientshow(i);
            for (model cn1 :clientlist) {
                editname.setText(cn1.getName());
                editphone.setText(cn1.getPhone());
                id=String.valueOf(cn1.getId());
                btnshow.setVisibility(View.GONE);
                btnsave.setVisibility(View.GONE);
            }
        }else{
            btnshow.setVisibility(View.VISIBLE);
            btnsave.setVisibility(View.VISIBLE);
            btnupdate.setVisibility(View.GONE);
        }

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=editname.getText().toString();
                String Phone=editphone.getText().toString();
                int s=db.updateClient(new model(Name,Phone), Integer.parseInt(id));
                if(s!=0){
                    Toast.makeText(MainActivity.this, "Updated Successfully !!!!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Updated Failed !!!!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=editname.getText().toString();
                String Phone=editphone.getText().toString();
                db.addClient(new model(Name,Phone));
                Log.d("Reading: ", "Reading all contacts..");
                editname.setText("");
                editphone.setText("");
            }
        });

        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<model> clientlist=db.getAllClient();
                Intent i=new Intent(MainActivity.this,DisplayActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("mylist", (Serializable) clientlist);
                startActivity(i);

            }
        });
    }
}