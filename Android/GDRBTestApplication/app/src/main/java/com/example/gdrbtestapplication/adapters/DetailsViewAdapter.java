package com.example.gdrbtestapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gdrbtestapplication.Model.UserModel;
import com.example.gdrbtestapplication.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DetailsViewAdapter extends RecyclerView.Adapter<DetailsViewAdapter.ViewHolder> {
    Context context;
    ArrayList<UserModel> dataList;
    public DetailsViewAdapter(Context context, ArrayList<UserModel> dataList) {
        this.context=context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public DetailsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobileview, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new DetailsViewAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewAdapter.ViewHolder viewHolder, int position) {

        viewHolder.tv_id.setText(dataList.get(position).getUserId());
        viewHolder.tv_name.setText(dataList.get(position).getFirstName()+" "+dataList.get(position).getLastName());
        viewHolder.tv_email.setText(dataList.get(position).getEmail());
        viewHolder.tv_phone.setText(dataList.get(position).getPhone());
        viewHolder.tv_state.setText(dataList.get(position).getState());
        viewHolder.tv_city.setText(dataList.get(position).getCity());
        Picasso.get().load(dataList.get(position).getImageUrl()).into(viewHolder.image);


    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_name, tv_email, tv_phone, tv_state, tv_city;
        ImageView image;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tv_id= itemLayoutView.findViewById(R.id.tv_id);
            tv_name= itemLayoutView.findViewById(R.id.tv_name);
            tv_email= itemLayoutView.findViewById(R.id.tv_email);
            tv_phone= itemLayoutView.findViewById(R.id.tv_phone);
            tv_state= itemLayoutView.findViewById(R.id.tv_state);
            tv_city= itemLayoutView.findViewById(R.id.tv_city);
            image= itemLayoutView.findViewById(R.id.image);
        }
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}