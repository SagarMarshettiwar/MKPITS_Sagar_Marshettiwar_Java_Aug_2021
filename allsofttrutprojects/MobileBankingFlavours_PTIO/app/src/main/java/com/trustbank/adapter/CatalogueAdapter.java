package com.trustbank.adapter;

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

import com.trustbank.Module.ImageCatalogueModel;
import com.trustbank.R;
import com.trustbank.activity.CatalogShowActivity;
import com.trustbank.util.TrustMethods;

import java.util.ArrayList;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {
    Activity mActivity;
    TrustMethods trustMethods;
    int pos;
    ArrayList<ImageCatalogueModel> imageCatalogueModels;

    public CatalogueAdapter(Activity activity, ArrayList<ImageCatalogueModel> imageCatalogueModels) {
        this.mActivity = activity;
        this.imageCatalogueModels = imageCatalogueModels;
        trustMethods = new TrustMethods(mActivity);

    }

    @NonNull
    @Override
    public CatalogueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_catalogueview, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new CatalogueAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogueAdapter.ViewHolder viewHolder, int position) {

        viewHolder.catalogname.setText(imageCatalogueModels.get(position).getCataname());
        viewHolder.catasubtitle.setText(imageCatalogueModels.get(position).getCatasubname());
        viewHolder.cata_icon.setImageBitmap(imageCatalogueModels.get(position).getCataicon());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i=new Intent(mActivity, CatalogShowActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("htmldisplay",imageCatalogueModels.get(position).getCatadata());
                    i.putExtra("namedisplay",imageCatalogueModels.get(position).getCataname());
                    mActivity.startActivity(i);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogname;
        public TextView catasubtitle;
        public ImageView cata_icon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            catalogname = itemLayoutView.findViewById(R.id.catalogname);
            cata_icon = itemLayoutView.findViewById(R.id.cata_icon);
            catasubtitle=itemLayoutView.findViewById(R.id.catasubtitle);
        }
    }

    @Override
    public int getItemCount() {
        return imageCatalogueModels.size();
    }
}