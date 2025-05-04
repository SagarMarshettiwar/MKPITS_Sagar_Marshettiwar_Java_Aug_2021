package com.example.cartcountitems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    Context mainActivity;
    List<String> list;
    int count;
    HashMap<String, Integer> map;
    public CartAdapter(Context mainActivity, List<String> list, HashMap<String, Integer> map) {
        this.mainActivity = mainActivity;
        this.list = list;
        this.map = map;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_design, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TextView.setText(list.get(position));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manage("Add",holder,position);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manage("Minus",holder, position);
            }
        });
    }

    private void manage(String op, ViewHolder holder, int position) {
        if(op.equals("Add")){
            count= Integer.parseInt(String.valueOf(holder.countval.getText()));
            count++;
            holder.countval.setText("" + count);
            map.put(list.get(position), Integer.parseInt(holder.countval.getText().toString()));
        }else{
            count= Integer.parseInt(String.valueOf(holder.countval.getText()));
            if (count == 0) {
                holder.countval.setText("0");
            } else {
                count -= 1;
                holder.countval.setText("" + count);
            }
            map.put(list.get(position), Integer.parseInt(holder.countval.getText().toString()));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView TextView,countval;
    public ImageView minus,add;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            TextView=itemLayoutView.findViewById(R.id.TextView);
            countval=itemLayoutView.findViewById(R.id.count);
            minus=itemLayoutView.findViewById(R.id.minus);
            add=itemLayoutView.findViewById(R.id.add);
        }
    }
}

