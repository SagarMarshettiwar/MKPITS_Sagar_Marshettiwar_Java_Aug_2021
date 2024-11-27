package com.trustbank.activity;

import static com.trustbank.util.MBank.loadAppLogo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.SetTheme;
import com.trustbank.util.TrustMethods;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class PaymentSuccessfullShowActivity extends AppCompatActivity {

    String Title,RefNo,RemName,Faccno,Amount,BenName,Taccno,Tmobno,Utrno;
    Button btn_save,btn_share;
    CardView cv_bill_detail;
    LinearLayout ll_utrno,ll_benname,ll_customer_name;
    TrustMethods method;
    StringBuilder maskedto;
    ImageView ivAppLogo;
    TextView txt_ref_id,txt_Utrno,txt_date,tv_toaccno,Title_name,txt_customer_name,txt_toaccnoo,txt_Amount,txt_benname,txt_toaccnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.changeToTheme(PaymentSuccessfullShowActivity.this, false);
        setContentView(R.layout.activity_payment_successfull_show);
        Init();
    }
    private void Init() {
        method=new TrustMethods(PaymentSuccessfullShowActivity.this);
        ivAppLogo = findViewById(R.id.ivAppLogo);
        loadAppLogo(ivAppLogo);
        txt_ref_id=findViewById(R.id.txt_ref_id);
        Title_name=findViewById(R.id.Title_name);
        txt_customer_name=findViewById(R.id.txt_customer_name);
        txt_toaccnoo=findViewById(R.id.txt_toaccnoo);
        txt_Amount=findViewById(R.id.txt_Amount);
        txt_benname=findViewById(R.id.txt_benname);
        txt_toaccnumber=findViewById(R.id.txt_toaccnumber);
        btn_save=findViewById(R.id.btn_save);
        btn_share=findViewById(R.id.btn_share);
        cv_bill_detail=findViewById(R.id.cv_bill_detail);
        tv_toaccno=findViewById(R.id.tv_toaccno);
        ll_utrno=findViewById(R.id.ll_utrno);
        txt_Utrno=findViewById(R.id.txt_Utrno);
        ll_customer_name=findViewById(R.id.ll_customer_name);
        ll_benname=findViewById(R.id.ll_benname);
        txt_date=findViewById(R.id.txt_date);

        Title = getIntent().getStringExtra("Title");
        RefNo = getIntent().getStringExtra("RefNo");
        RemName = getIntent().getStringExtra("RemName");
        Faccno = getIntent().getStringExtra("Faccno");
        Amount = getIntent().getStringExtra("Amount");
        BenName = getIntent().getStringExtra("BenName");
        Taccno = getIntent().getStringExtra("Taccno");
        Tmobno = getIntent().getStringExtra("Tmobno");
        Utrno = getIntent().getStringExtra("Utrno");


        StringBuilder maskedfrom= new StringBuilder(Faccno);
        for (int i =0; i < maskedfrom.length()-4; i++) {
            maskedfrom.setCharAt(i, 'X');
        }

        if(!TextUtils.isEmpty(Taccno)) {
            maskedto = new StringBuilder(Taccno);
            for (int l = 0; l < maskedto.length() - 4; l++) {
                maskedto.setCharAt(l, 'X');
            }
        }


        txt_ref_id.setText(RefNo);
        Title_name.setText(Title);
        txt_customer_name.setText(RemName);
        txt_toaccnoo.setText(maskedfrom);
        txt_Amount.setText(Amount);
        txt_benname.setText(BenName);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        txt_date.setText(dtf.format(now));

        if(!TextUtils.isEmpty(Utrno)){
            ll_utrno.setVisibility(View.VISIBLE);
            txt_Utrno.setText(Utrno);
        }else{
            ll_utrno.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(Tmobno)){
            tv_toaccno.setText("To Mobile Number");
            txt_toaccnumber.setText(Tmobno);
        }else{
            tv_toaccno.setText("To Account Number");
            txt_toaccnumber.setText(maskedto);
        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf(cv_bill_detail);
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf(cv_bill_detail);
            }
        });
    }

    private void createPdf(View view) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth(), view.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        view.draw(canvas);
        document.finishPage(page);
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "PaymentReceipt" +Title + timestamp + ".pdf";
        File file = new File(downloadsDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "Downloaded Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            method.activityCloseAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sharePdf(View view){
        int height = view.getHeight();
        int width = view.getWidth();

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(width, height, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        /*view.layout(0, 0 , width, height)*/;
        view.draw(canvas);
        pdfDocument.finishPage(page);
        File pdfFile = new File(getExternalFilesDir(null), "Receipt.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pdfDocument.close();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("application/pdf");
        Uri pdfUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", pdfFile);
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        startActivity(Intent.createChooser(intent, "Select"));

    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(PaymentSuccessfullShowActivity.this,MenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}