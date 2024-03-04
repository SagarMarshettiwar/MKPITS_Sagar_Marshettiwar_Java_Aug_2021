package com.example.dbhelpertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dbhelpertest.Adapter.DisplayAdapter;

import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private List<model> list;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        rv=findViewById(R.id.rv);

        Intent intent = getIntent();
        if (intent.hasExtra("mylist")) {
            list = (List<model>) intent.getSerializableExtra("mylist");
            DisplayAdapter adapter=new DisplayAdapter(DisplayActivity.this,list);
            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(DisplayActivity.this));
            rv.setAdapter(adapter);
        }
    }
}