package com.example.cartcountitems;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_cart;
    Button btn_next;
    HashMap<String,Integer> map=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }
    private void Init() {
        rv_cart=findViewById(R.id.rv_cart);
        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Map.Entry<String, Integer> entry : map.entrySet()){

                }
            }
        });

        List<String> list=new ArrayList<>();
        list.add("Bat");
        list.add("Car");
        list.add("Top");
        list.add("Hat");
        list.add("Gloves");
        list.add("Scarf");
        list.add("Sunglasses");
        list.add("Watch");
        list.add("Belt");
        list.add("Necklace");
        list.add("Bracelet");
        list.add("Ring");
        list.add("Earrings");
        list.add("Purse");
        list.add("Wallet");
        list.add("Shoes");
        list.add("Bag");
        list.add("Tie");
        list.add("Headband");
        list.add("Cufflinks");
        list.add("Pocket Square");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        rv_cart.setLayoutManager(mLayoutManager);
        CartAdapter cartadapter = new CartAdapter(MainActivity.this, list,map);
        rv_cart.setAdapter(cartadapter);
        rv_cart.setHasFixedSize(true);
        rv_cart.setItemViewCacheSize(list.size());
    }
}