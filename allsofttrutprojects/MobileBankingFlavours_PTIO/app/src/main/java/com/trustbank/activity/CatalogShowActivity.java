package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.trustbank.R;

import java.nio.charset.StandardCharsets;

public class CatalogShowActivity extends AppCompatActivity implements View.OnClickListener{
    String catalogdata,catalogname;

    private ImageView backButton_new;
    private TextView toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_show);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar new_toolbar = findViewById(R.id.new_toolbar);
        toolbar=findViewById(R.id.toolbar);
        backButton_new = findViewById(R.id.backButton_new);
        backButton_new.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            Intent i = getIntent();
            catalogdata = i.getStringExtra("htmldisplay");
            catalogname = i.getStringExtra("namedisplay");

            toolbar.setText(catalogname);
            new_toolbar.setBackground(getResources().getDrawable(R.drawable.corner));
            Log.e("catalogdata",catalogdata);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading Data...");
            progressDialog.setCancelable(false);
            WebView web_view = findViewById(R.id.web_view);
            WebSettings webSettings = web_view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            web_view.loadDataWithBaseURL(null, catalogdata, "text/html", "UTF-8", null);
            web_view.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100) {
                        progressDialog.show();
                    }
                    if (progress == 100) {
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    //back button event
    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }
}