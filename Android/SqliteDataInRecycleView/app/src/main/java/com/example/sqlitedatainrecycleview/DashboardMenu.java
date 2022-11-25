package com.example.sqlitedatainrecycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class DashboardMenu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<DashDatapojo> DataArrayList;
    private  DashDatapojo dashDatapojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardmenu);
        recyclerView=findViewById(R.id.idCourseRV);

        //created new array list..
        DataArrayList=new ArrayList<>();

        //added data to array list

        dashDatapojo=new DashDatapojo("Account",R.drawable.account);
        dashDatapojo=new DashDatapojo("Account Statement",R.drawable.statement);
        dashDatapojo=new DashDatapojo("RD/FD Recknor", R.drawable.recknor);
        dashDatapojo=new DashDatapojo("Amortization Chart",R.drawable.chart);
        dashDatapojo=new DashDatapojo("Branches",R.drawable.branches);
        dashDatapojo=new DashDatapojo("Add Account",R.drawable.addaccount);
        dashDatapojo=new DashDatapojo("Contact Us",R.drawable.contactus);
        dashDatapojo=new DashDatapojo("About Us",R.drawable.aboutus);

        DataArrayList.add(dashDatapojo);
        //added data from arraylist to adapter class.
        DashRecyclerAdapter adapter=new DashRecyclerAdapter(DataArrayList, this);
        //setting grid layout manager to implement grid view.
        // in this method '2' represents number of colums to be displayed in grid view.
        GridLayoutManager layoutManager=new GridLayoutManager(DashboardMenu.this,4);
        //at last set adapter to recycler view.




        //horizontal Gride
       /* LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
        recyclerView.setLayoutManager(layoutManager1);*/


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}