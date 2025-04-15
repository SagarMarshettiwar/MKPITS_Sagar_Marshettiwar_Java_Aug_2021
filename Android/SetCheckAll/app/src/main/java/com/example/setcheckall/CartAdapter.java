package com.example.setcheckall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ListIterator;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    Context mainActivity;
    List<ItemModel> list;
    int total=0;
    boolean sall;
    int count;
    public CartAdapter(Context mainActivity, List<ItemModel> list) {
        this.mainActivity = mainActivity;
        this.list = list;
    }
    public void selectAll(boolean b){
        /*for(ItemModel i :list){
            i.setChecked(b);
        }*/
        sall=b;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel currentItem = list.get(position);
        holder.dep_repay_amt.setText(currentItem.getValue());

        if(sall){
            holder.checkrow.setChecked(true);
        }else{
            holder.checkrow.setChecked(false);
        }

        //holder.checkrow.setChecked(currentItem.getChecked());
        holder.checkrow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int value = Integer.parseInt(currentItem.getValue().toString());
                if (b) {
                    total += value;
                } else {
                    total -= value;
                }
                  currentItem.setChecked(b);
                Log.e("Total :",String.valueOf(total));
            }
        });

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
    public TextView dep_repay_amt;
    CheckBox checkrow;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            dep_repay_amt=itemLayoutView.findViewById(R.id.dep_repay_amt);
            checkrow=itemLayoutView.findViewById(R.id.checkrow);
        }
    }
}

