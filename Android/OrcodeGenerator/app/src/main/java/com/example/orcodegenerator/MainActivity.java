package com.example.orcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int QRcodeWidth = 500;
    Button bt_button,bt_button_share;
    ImageView iv_qr_code;
    String questions;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_qr_code = findViewById(R.id.iv_qr_code);
        bt_button_share=findViewById(R.id.bt_button_share);
        bt_button_share.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            questions = bundle.getString("QrString");
        }
         generateQR(questions);
    }
    private void generateQR(String questions) {
        bitmap=TextToImageEncode(questions);
        iv_qr_code.setImageBitmap(bitmap);
    }
    private Bitmap TextToImageEncode(String questions) {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(questions.replace("\n", "\r\n"), BarcodeFormat.QR_CODE, QRcodeWidth, QRcodeWidth, null);
            int bitMatrixWidth = bitMatrix.getWidth();
            int bitMatrixHeight = bitMatrix.getHeight();
            int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

            for (int y = 0; y < bitMatrixHeight; y++) {
                int offset = y * bitMatrixWidth;

                for (int x = 0; x < bitMatrixWidth; x++) {

                    pixels[offset + x] = bitMatrix.get(x, y) ?
                            getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
            bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_button_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(MainActivity.this, bitmap));
                sendIntent.setType("image/jpeg");
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(sendIntent);
        }
    }
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "QR Code", null);
        return Uri.parse(path);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(MainActivity.this,Choose_Option.class);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(i);
    }
}