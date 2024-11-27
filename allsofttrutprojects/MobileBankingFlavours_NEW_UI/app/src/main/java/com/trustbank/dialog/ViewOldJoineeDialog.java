package com.trustbank.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.OldJoinListModel;
import com.trustbank.Model.ViewJoinModel;
import com.trustbank.R;
import com.trustbank.adapter.ViewOldJoinAdapter;

import java.util.ArrayList;

public class ViewOldJoineeDialog extends Dialog
{
    private Context context;
    ArrayList<ViewJoinModel> viewJoinList;
    private RecyclerView joinListRv;
    private ArrayList<OldJoinListModel> jointList;

    public ViewOldJoineeDialog(@NonNull Context context, ArrayList<ViewJoinModel> viewJoinList, ArrayList<OldJoinListModel> jointList) {
        super(context);
        this.context=context;
        this.viewJoinList=viewJoinList;
        this.jointList = jointList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_join_list);
        joinListRv = findViewById(R.id.rv_join_list);

        ArrayList<ViewJoinModel> joinModelArrayList = new ArrayList<ViewJoinModel>();
        for(int i=0;i<viewJoinList.size();i++) {
            joinModelArrayList.add(new ViewJoinModel(viewJoinList.get(i).getJoinType(), viewJoinList.get(i).getClientId(),viewJoinList.get(i).getClientName()));
        }
        ViewOldJoinAdapter viewJoinAdapter = new ViewOldJoinAdapter(context,joinModelArrayList,jointList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        joinListRv.setLayoutManager(linearLayoutManager);
        joinListRv.setAdapter(viewJoinAdapter);
    }
}
