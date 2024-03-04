package com.example.dbhelpertest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dbhelpertest.DbHandler;
import com.example.dbhelpertest.MainActivity;
import com.example.dbhelpertest.R;
import com.example.dbhelpertest.model;
import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder>{
    Context ctx;
    List<model> list;
    public DisplayAdapter(Context ctx, List<model> list) {
        this.ctx=ctx;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_display, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getId()));
        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getPhone());

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHandler db=new DbHandler(ctx);
                db.delete(list.get(position).getId());
                Intent i=new Intent(ctx,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(i);
            }
        });

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ctx,MainActivity.class);
                i.putExtra("edit",list.get(position).getId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView phone,name,id;
        Button Edit,Delete;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            phone=itemLayoutView.findViewById(R.id.phone);
            id=itemLayoutView.findViewById(R.id.id);
            name=itemLayoutView.findViewById(R.id.name);
            Edit=itemLayoutView.findViewById(R.id.Edit);
            Delete=itemLayoutView.findViewById(R.id.Delete);
        }
    }
}
