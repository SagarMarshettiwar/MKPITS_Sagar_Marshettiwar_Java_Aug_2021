package com.trustbank.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.R;
import com.trustbank.adapter.ViewIdDocumentAdapter;
import com.trustbank.adapter.ViewUploadDocumentAdapter;
import com.trustbank.model.DocumentModel;

import java.util.List;

public class ViewUploadDocuments extends Dialog
{
    Context context;
    List<DocumentModel> documentModelList;
    RecyclerView rv_upload_doc;

    public ViewUploadDocuments(@NonNull Context context, List<DocumentModel> documentModelList)
    {
        super(context);
        this.context=context;
        this.documentModelList=documentModelList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.upload_doc_dialog);
        getWindow().setLayout(500, 850);
        rv_upload_doc = findViewById(R.id.rv_upload_doc);

        ViewUploadDocumentAdapter viewUploadDocumentAdapter=new ViewUploadDocumentAdapter(context,documentModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        rv_upload_doc.setLayoutManager(linearLayoutManager);
        rv_upload_doc.setAdapter(viewUploadDocumentAdapter);
    }
}
