package com.example.apireadertest;

import android.app.Activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Activity mActivity;

    ArrayList<PlaystoreModel> playstoreModel;

    public MyAdapter(Activity activity, ArrayList<PlaystoreModel> playstoreModel) {
        this.mActivity = activity;
        this.playstoreModel = playstoreModel;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new MyAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int position) {

        viewHolder.catalogname.setText(playstoreModel.get(position).getName());
        String imageUrl = playstoreModel.get(position).getThumb_image();
        ImageView imageView = viewHolder.cata_icon;
        Picasso.get()
                .load(imageUrl)
                .into(imageView);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= viewHolder.catalogname.getText().toString();
                Toast.makeText(mActivity, ""+name, Toast.LENGTH_SHORT).show();
                if(playstoreModel.get(position).getName().equals(name)){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setData(playstoreModel.get(position).getApp_link());
                    mActivity.startActivity(i);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogname;
        public TextView catasubtitle;
        public ImageView cata_icon;
        public Button button;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            catalogname = itemLayoutView.findViewById(R.id.catalogname);
            cata_icon = itemLayoutView.findViewById(R.id.cata_icon);
            catasubtitle=itemLayoutView.findViewById(R.id.catasubtitle);
            button=itemLayoutView.findViewById(R.id.button);
        }
    }

    @Override
    public int getItemCount() {
        return playstoreModel.size();
    }
}