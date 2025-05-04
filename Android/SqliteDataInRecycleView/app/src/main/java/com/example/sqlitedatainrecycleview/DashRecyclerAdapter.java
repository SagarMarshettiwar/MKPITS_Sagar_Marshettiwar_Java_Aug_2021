package com.example.sqlitedatainrecycleview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DashRecyclerAdapter extends RecyclerView.Adapter<DashRecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<DashDatapojo> courseDataArrayList;
    private Context mcontext;


    public DashRecyclerAdapter(ArrayList<DashDatapojo> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashbord_list, parent, false);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        DashDatapojo dashDatapojo = courseDataArrayList.get(position);
        holder.courseTV.setText(dashDatapojo.getTitle());
        holder.courseimg.setImageResource(dashDatapojo.getImgid());

    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }


    //View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView courseTV;
        private ImageView courseimg;
        //LinearLayout linearLayout;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            courseTV = itemView.findViewById(R.id.idTVCourse);
            courseimg = itemView.findViewById(R.id.idIVcourseIV);
           // linearLayout=itemView.findViewById(R.id.layout_rec);
        }

        //on click recycle grid
        @Override
        public void onClick(View v) {
            String pos = courseDataArrayList.get(getAdapterPosition()).getTitle().trim();
            Log.e("position",pos);
            switch(pos){
                case "Account":
                    Toast.makeText(mcontext, "account "+pos, Toast.LENGTH_SHORT).show();
                    /*Intent intent=new Intent(mcontext,null);
                    intent.putExtra("account",courseDataArrayList.get(pos).getTitle());
                    mcontext.startActivity(intent);*/

                    break;
                case "Account Statement":
                    Toast.makeText(mcontext, "AStatntement account "+pos, Toast.LENGTH_SHORT).show();
                    /*Intent intent1=new Intent(mcontext,null);
                    intent1.putExtra("AStatntement account",courseDataArrayList.get(pos).getTitle());
                    mcontext.startActivity(intent1);*/

                    break;
            }

        }
    }
}
