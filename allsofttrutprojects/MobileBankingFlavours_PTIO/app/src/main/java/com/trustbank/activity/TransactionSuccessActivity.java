package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trustbank.R;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.SessionManager;
import com.trustbank.util.TrustMethods;

public class TransactionSuccessActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager session;
    TextView description;
    private TextView toolbar;
    private ImageView backButton_new;
    private String title;
    Button btnActivate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_success);

        description = findViewById(R.id.description);
        ImageView success_logo = findViewById(R.id.success_logo);
        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        backButton_new = findViewById(R.id.backButton_new);
        btnActivate=findViewById(R.id.btnActivate);
        btnActivate.setOnClickListener(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        String descr = intent.getStringExtra("Description");
        description.setText(descr);

        toolbar.setText(title);
        new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));
        Glide.with(this).load(R.drawable.success_logo).into(success_logo);

    }

    @Override
    public void onClick(View v) {
        if (NetworkUtil.getConnectivityStatus(TransactionSuccessActivity.this)) {
             Intent i = new Intent(TransactionSuccessActivity.this, TransactionMenu.class);
             i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
             startActivity(i);
        } else {
            TrustMethods.message(TransactionSuccessActivity.this, getResources().getString(R.string.error_check_internet));
        }
    }
}