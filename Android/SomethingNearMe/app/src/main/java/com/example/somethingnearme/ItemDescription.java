package com.example.somethingnearme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class ItemDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_desciption);

        // Get Card Data from Main Page
        DBHandler.ContentModel cardData = (DBHandler.ContentModel) getIntent().getSerializableExtra("cardData");

        ImageView contentDescImage = findViewById(R.id.contentDescImage);
        TextView nameTextview = findViewById(R.id.nameTextview);
        TextView ratingTextview = findViewById(R.id.ratingTextview);
        TextView locationTextview = findViewById(R.id.locationTextview);
        TextView priceTextview = findViewById(R.id.priceTextview);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        Button rateItemButton = findViewById(R.id.rateItemButton);

        contentDescImage.setImageBitmap(cardData.getImage());
        nameTextview.setText(cardData.getName());
        ratingTextview.setText(String.valueOf(cardData.getRating()));
        locationTextview.setText(cardData.getLocation());
        priceTextview.setText(String.valueOf(cardData.getPrice()));

        placeOrderButton.setOnClickListener(View -> {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
            finish();
        });

        rateItemButton.setOnClickListener(View -> {

        });

    }
}