package com.example.somethingnearme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;


public class MainLandingPage extends AppCompatActivity {

    String imageSourceLink = "https://source.unsplash.com/400x250/?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing_page);
        DBHandler dbHandler = new DBHandler(MainLandingPage.this);


        // EditTexts
        EditText nameEdittext = findViewById(R.id.nameEdittext);
        EditText locationEdittext = findViewById(R.id.locationEdittext);
        EditText tagEdittext = findViewById(R.id.tagEdittext);
        EditText priceEdittext = findViewById(R.id.priceEdittext);
        EditText ratingEdittext = findViewById(R.id.ratingEdittext);
        // Buttons
        Button openAddContentLayoutButton = findViewById(R.id.openAddContentLayoutButton);
        Button setNewContentButton = findViewById(R.id.setNewContentButton);
        Button cancelNewContentButton = findViewById(R.id.cancelNewContentButton);
        // Linear Layouts
        LinearLayout addContentLayout = findViewById(R.id.addContentLayout);


        try {
            setRecyclerView(dbHandler);
        } catch (Exception e) {
            Toast.makeText(MainLandingPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Error" , e.getMessage());
        }

        openAddContentLayoutButton.setOnClickListener(View -> {
            addContentLayout.setVisibility(android.view.View.VISIBLE);
            openAddContentLayoutButton.setVisibility(android.view.View.GONE);
        });

        setNewContentButton.setOnClickListener(View -> {
            String name = nameEdittext.getText().toString();
            String location = locationEdittext.getText().toString();
            String tag = tagEdittext.getText().toString();
            double price = Double.parseDouble(priceEdittext.getText().toString());
            double rating = Double.parseDouble(ratingEdittext.getText().toString());

            if(name.length()==0 || location.length()==0 || tag.length()==0 || price==0 || rating==0)
                Toast.makeText(MainLandingPage.this, "Invalid Input", Toast.LENGTH_SHORT).show();
            else
                dbHandler.addNewContent(name, location , tag, price, rating);

            nameEdittext.setText("");
            locationEdittext.setText("");
            tagEdittext.setText("");
            priceEdittext.setText("");
            ratingEdittext.setText("");

            openAddContentLayoutButton.setVisibility(android.view.View.VISIBLE);
            addContentLayout.setVisibility(android.view.View.GONE);

            try {
                setRecyclerView(dbHandler);
            } catch (Exception e) {
                Toast.makeText(MainLandingPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error" , e.getMessage());
            }
        });

        cancelNewContentButton.setOnClickListener(View -> {
            nameEdittext.setText("");
            locationEdittext.setText("");
            tagEdittext.setText("");
            priceEdittext.setText("");
            ratingEdittext.setText("");

            openAddContentLayoutButton.setVisibility(android.view.View.VISIBLE);
            addContentLayout.setVisibility(android.view.View.GONE);
        });
    }

    // Async task to download the image
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        DBHandler.ContentModel card;
        ImageView imageView;

        public DownloadImageFromInternet(DBHandler.ContentModel card, ImageView imageView) {
            this.card = card;
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bitImage = null;

            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bitImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return bitImage;
        }

        protected void onPostExecute(Bitmap result) {
            card.setImage(result);
            imageView.setImageBitmap(result);
        }
    }


    // Recycler View Functions

    // Create Recycler View From Spinners
    public void setRecyclerView(DBHandler dbHandler) {

        // Set up the RecyclerView layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView docSubmissionRecyclerView = findViewById(R.id.mainContentRecyclerview);
        docSubmissionRecyclerView.setLayoutManager(layoutManager);
        // Create sample data for the cards
        ArrayList<DBHandler.ContentModel> recyclerViewList = dbHandler.readContentData();

        // Set up the RecyclerView adapter
        CardAdapter adapter = new CardAdapter(recyclerViewList);
        docSubmissionRecyclerView.setAdapter(adapter);
    }


    // Function to create new card in recycler view.
    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

        ArrayList<DBHandler.ContentModel> recyclerViewList;

        // Default constructor
        public CardAdapter(ArrayList<DBHandler.ContentModel> recyclerViewList) {
            this.recyclerViewList = recyclerViewList;
        }

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_content_card, parent, false);
            return new CardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            DBHandler.ContentModel cardData = recyclerViewList.get(position);

            // Bind data to views
            new DownloadImageFromInternet(cardData,holder.contentImageview).execute(imageSourceLink + cardData.getTag());

            holder.contentNameTextview.setText(cardData.getName());
            holder.contentLocationTextview.setText(cardData.getLocation());
            holder.contentPriceTextview.setText(String.valueOf(cardData.getPrice()));
            holder.contentRatingTextview.setText(String.valueOf(cardData.getRating()));

            holder.buyItemButton.setOnClickListener(View -> {
                try {
                    Intent itemDescription = new Intent(MainLandingPage.this, ItemDescription.class);
                    itemDescription.putExtra("cardData", cardData);
                    startActivity(itemDescription);
                } catch (Exception e) {
                    Log.d("Error",e.getMessage());
                }
            });

            holder.wishlistItemButton.setOnClickListener(View -> {

            });

            holder.deleteItemButton.setOnClickListener(View -> {
                DBHandler dbHandler = new DBHandler(MainLandingPage.this);
                try {
                    dbHandler.deleteContent(cardData.getName());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error",e.getMessage());
                }
                setRecyclerView(dbHandler);
            });
        }

        @Override
        public int getItemCount() {
            return recyclerViewList.size();
        }

        // Card class to store card data
        public class CardViewHolder extends RecyclerView.ViewHolder {
            // Card variables
            ImageView contentImageview;
            TextView contentLocationTextview;
            TextView contentNameTextview;
            TextView contentPriceTextview;
            TextView contentRatingTextview;
            Button buyItemButton;
            Button wishlistItemButton;
            Button deleteItemButton;

            // Default constructor
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                // ImageView
                contentImageview = itemView.findViewById(R.id.contentImageview);
                //TextView
                contentLocationTextview = itemView.findViewById(R.id.contentLocationTextview);
                contentNameTextview = itemView.findViewById(R.id.contentNameTextview);
                contentPriceTextview = itemView.findViewById(R.id.contentPriceTextview);
                contentRatingTextview = itemView.findViewById(R.id.contentRatingTextview);
                // Button
                buyItemButton = itemView.findViewById(R.id.buyItemButton);
                wishlistItemButton = itemView.findViewById(R.id.wishlistItemButton);
                deleteItemButton = itemView.findViewById(R.id.deleteItemButton);
            }
        }
    }
}