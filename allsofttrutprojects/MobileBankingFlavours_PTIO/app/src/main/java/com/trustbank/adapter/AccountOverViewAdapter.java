package com.trustbank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.trustbank.Model.AccountOverviewModel;
import com.trustbank.R;
import com.trustbank.interfaces.AccountOverviewListener;

import java.util.ArrayList;

public class AccountOverViewAdapter extends ArrayAdapter<AccountOverviewModel> {

    ArrayList<AccountOverviewModel> itemList;
    Context context;
    int layoutResource;
    AccountOverviewListener accountOverviewListener;
    public AccountOverViewAdapter(@NonNull Context context, int resource, ArrayList<AccountOverviewModel> itemList, AccountOverviewListener accountOverviewListener) {
        super(context, resource, itemList);
        this.context = context;
        this.layoutResource = resource;
        this.itemList = itemList;
        this.accountOverviewListener = accountOverviewListener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (row == null) {
            row = inflater.inflate(layoutResource, null);
        }
        AccountOverviewModel currentItem = itemList.get(position);

        ImageView imageView = row.findViewById(R.id.imageView);
        TextView txtView = row.findViewById(R.id.txtView);
        Spinner spinnerAccountOverview = row.findViewById(R.id.spinnerAccountOverview);

        imageView.setImageResource(currentItem.getImage());
        txtView.setText(currentItem.getText());
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, currentItem.getSpinnerItems());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountOverview.setAdapter(spinnerArrayAdapter);
        spinnerAccountOverview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                try {
                    if (position != 0) {
                        String selectedAccNo = (String) arg0.getItemAtPosition(position);
                        ((TextView) arg0.getChildAt(0)).setTextColor(Color.GRAY);
                        ((TextView) arg0.getChildAt(0)).setTextSize(15f);
                        accountOverviewListener.selectedAccount(selectedAccNo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        return  row;
    }
}
