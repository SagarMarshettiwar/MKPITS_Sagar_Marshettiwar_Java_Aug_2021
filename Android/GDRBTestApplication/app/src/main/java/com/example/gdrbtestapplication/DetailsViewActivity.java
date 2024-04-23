package com.example.gdrbtestapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gdrbtestapplication.Model.UserModel;
import com.example.gdrbtestapplication.adapters.DetailsViewAdapter;

import java.util.ArrayList;

public class DetailsViewActivity extends AppCompatActivity {

    RecyclerView rv_mobile;
    private ArrayList<UserModel> DataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        inIt();
    }

    private void inIt() {
        rv_mobile=findViewById(R.id.rv_mobile);

        DataList = (ArrayList<UserModel>) getIntent().getSerializableExtra("DetailList");

        DetailsViewAdapter adapter=new DetailsViewAdapter(DetailsViewActivity.this,DataList);
        rv_mobile.setHasFixedSize(true);
        rv_mobile.setLayoutManager(new LinearLayoutManager(DetailsViewActivity.this));
        rv_mobile.setAdapter(adapter);
    }
}