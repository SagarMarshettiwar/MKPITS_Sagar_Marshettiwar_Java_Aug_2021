package com.example.dial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class passcode_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
    }
    @Override
    public void onBackPressed() {
        // this method is used to finish the activity
        // when user enters the correct password
        this.finishAffinity();
    }
}