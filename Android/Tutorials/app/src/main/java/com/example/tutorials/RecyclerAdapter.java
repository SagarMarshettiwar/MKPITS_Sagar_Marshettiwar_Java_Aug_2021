package com.example.tutorials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private final Context context;
    private final ArrayList<UPPojo> upPojoList;

    public RecyclerAdapter(Context context, ArrayList<UPPojo> upPojoList) {
        this.context = context;
        this.upPojoList = upPojoList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contaillist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerViewHolder holder, int position) {
            UPPojo upPojo=upPojoList.get(position);
            holder.text1.setText(upPojo.getUser());
            holder.text2.setText(upPojo.getPass());
    }

    @Override
    public int getItemCount() {
        return upPojoList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView text1,text2;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=(TextView)itemView.findViewById(R.id.list_text1);
            text2=(TextView)itemView.findViewById(R.id.list_text2);
        }
    }
}
