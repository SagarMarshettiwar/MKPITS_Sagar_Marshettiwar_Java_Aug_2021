package com.example.setcheckall;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView rv_collectionDeposit;
CheckBox checkBoxSelectAll;
boolean sall=false;
    CartAdapter cartadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    private void Init() {
        rv_collectionDeposit=findViewById(R.id.rv_collectionDeposit);
        checkBoxSelectAll=findViewById(R.id.checkBoxSelectAll);

        ArrayList<ItemModel> list=new ArrayList<>();
        ItemModel i=new ItemModel();
        i.setValue("10");
        i.setChecked(false);
        list.add(i);
        ItemModel i1=new ItemModel();
        i1.setValue("20");
        i1.setChecked(false);
        list.add(i1);
        ItemModel i2=new ItemModel();
        i2.setValue("30");
        i2.setChecked(false);
        list.add(i2);
        ItemModel i3=new ItemModel();
        i3.setValue("40");
        i3.setChecked(false);
        list.add(i3);

        checkBoxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    cartadapter.selectAll(b);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        rv_collectionDeposit.setLayoutManager(mLayoutManager);
        cartadapter = new CartAdapter(MainActivity.this, list);
        rv_collectionDeposit.setAdapter(cartadapter);
        rv_collectionDeposit.setHasFixedSize(true);
        rv_collectionDeposit.setItemViewCacheSize(list.size());
    }
}