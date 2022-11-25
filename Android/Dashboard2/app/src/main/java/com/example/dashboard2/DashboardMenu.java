package com.example.dashboard2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DashboardMenu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<DashDatapojo> DataArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardmenu);
        recyclerView=findViewById(R.id.idCourseRV);

        //created new array list..
        DataArrayList=new ArrayList<>();

        //added data to array list
        DataArrayList.add(new DashDatapojo("Account",R.drawable.account));
        DataArrayList.add(new DashDatapojo("Account\nStatement",R.drawable.statement));
        DataArrayList.add(new DashDatapojo("RD/FD Recknor", R.drawable.recknor));
        DataArrayList.add(new DashDatapojo("Amortization Chart",R.drawable.chart));
        DataArrayList.add(new DashDatapojo("Branches",R.drawable.branches));
        DataArrayList.add(new DashDatapojo("Add Account",R.drawable.addaccount));
        DataArrayList.add(new DashDatapojo("Contact Us",R.drawable.contactus));
        DataArrayList.add(new DashDatapojo("About Us",R.drawable.aboutus));
        DataArrayList.add(new DashDatapojo("About Us",R.drawable.aboutus));

        //added data from arraylist to adapter class.
        DashRecyclerAdapter adapter=new DashRecyclerAdapter(DataArrayList, this);
        //setting grid layout manager to implement grid view.
        // in this method '2' represents number of colums to be displayed in grid view.
        //GridLayoutManager layoutManager=new GridLayoutManager(DashboardMenu.this,4);
        //at last set adapter to recycler view.

        //horizontal Gride
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager1);


        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(adapter);
    }
}