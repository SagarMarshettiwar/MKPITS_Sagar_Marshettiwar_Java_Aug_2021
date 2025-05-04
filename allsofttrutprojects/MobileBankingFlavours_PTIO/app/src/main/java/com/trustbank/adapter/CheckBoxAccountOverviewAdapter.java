package com.trustbank.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.CheckBoxAccountOverviewModel;
import com.trustbank.R;

import java.util.ArrayList;

public class CheckBoxAccountOverviewAdapter extends RecyclerView.Adapter<CheckBoxAccountOverviewAdapter.ViewHolder> {

    ArrayList<CheckBoxAccountOverviewModel> itemList;
    ArrayList<CheckBoxAccountOverviewModel> checkedCheckBoxes = new ArrayList<>();
    Context context;

    public CheckBoxAccountOverviewAdapter(Context context, ArrayList<CheckBoxAccountOverviewModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkbox_account_overview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckBoxAccountOverviewModel item = itemList.get(position);
        holder.checkBox.setChecked(item.isSelected());
        holder.textView.setText(item.getText());
        int[][] states = {{android.R.attr.state_checked}, {}};
        int[] colors = {ContextCompat.getColor(context, R.color.colorPrimaryBlue), ContextCompat.getColor(context, R.color.colorPrimaryBlue)};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        holder.checkBox.setButtonTintList(colorStateList);
        holder.checkBox.setEnabled(false);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);
            updateCheckedBoxList(item, isChecked);
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public void updateCheckedBoxList(CheckBoxAccountOverviewModel item, boolean isChecked){
        if (isChecked){
            checkedCheckBoxes.add(item);
        }
        else{
            checkedCheckBoxes.remove(item);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            textView = itemView.findViewById(R.id.textView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

        }
    }
}
