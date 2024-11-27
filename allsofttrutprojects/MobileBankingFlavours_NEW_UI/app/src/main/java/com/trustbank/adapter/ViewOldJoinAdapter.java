package com.trustbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.OldJoinListModel;
import com.trustbank.Model.ViewJoinModel;
import com.trustbank.R;

import java.util.ArrayList;

public class ViewOldJoinAdapter extends RecyclerView.Adapter<ViewOldJoinAdapter.ViewHolder>
{
    private final Context context;
    private final ArrayList<ViewJoinModel> joineeModelArrayList;
    private ArrayList<OldJoinListModel> jointList;
    private View.OnClickListener onClickListener;

    public ViewOldJoinAdapter(Context context, ArrayList<ViewJoinModel> joineeModelArrayList, ArrayList<OldJoinListModel> jointList) {
        this.context = context;
        this.joineeModelArrayList = joineeModelArrayList;
        this.jointList = jointList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.join_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewJoinModel model = joineeModelArrayList.get(position);
        holder.tv_id.setText(model.getClientId());
        holder.tv_name.setText(model.getClientName());
    }

    @Override
    public int getItemCount() {
        return joineeModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tv_name,tv_id;
        private Button btn_remove;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_id = itemView.findViewById(R.id.tv_id);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            btn_remove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()== R.id.btn_remove)
            {
                int count=-1;
                for(int i=0;i<joineeModelArrayList.size();i++)
                {
                    if(tv_name.getText().equals(joineeModelArrayList.get(i).getClientName()))
                    {
                        count=i;
                        break;
                    }
                }
                joineeModelArrayList.remove(count);

                for(int i=0;i<jointList.size();i++)
                {
                    if(tv_name.getText().equals(jointList.get(i).getClientName()))
                    {
                        count=i;
                        break;
                    }
                }
                jointList.remove(count);

                notifyItemRemoved(count);
                notifyDataSetChanged();
            }
        }
    }
}
