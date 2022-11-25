package com.example.dialogtrial;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dialogtrial.Dialogbox.AlertDialogMethod;
import com.example.dialogtrial.Interfaces.AlertDialogOkListener;

public class MainActivity extends AppCompatActivity implements AlertDialogOkListener {
    Button button;
    private AlertDialogOkListener alertDialogOkListener = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogMethod.alertDialogOk(MainActivity.this,
                        getResources().getString(R.string.message), "hellow everyone ", getResources().getString(R.string.btn_ok), 10, false, alertDialogOkListener);
            }
        });
    }

    @Override
    public void onDialogOk(int resultCode) {
        if(resultCode ==10){
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
        }
    }
}