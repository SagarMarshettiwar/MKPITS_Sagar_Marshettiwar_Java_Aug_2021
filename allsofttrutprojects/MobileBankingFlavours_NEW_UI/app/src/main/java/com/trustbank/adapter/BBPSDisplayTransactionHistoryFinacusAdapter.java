package com.trustbank.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.BBPSTransactionHistoryFinacusModel;
import com.trustbank.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BBPSDisplayTransactionHistoryFinacusAdapter extends RecyclerView.Adapter<BBPSDisplayTransactionHistoryFinacusAdapter.ViewHolder> {
    private Context mContext;
    private List<BBPSTransactionHistoryFinacusModel> mTransacHistoryList;

    public BBPSDisplayTransactionHistoryFinacusAdapter(Context context, List<BBPSTransactionHistoryFinacusModel> transacHistorylist) {
        this.mContext = context;
        this.mTransacHistoryList = transacHistorylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bbps_display_transaction_history, null);
        RecyclerView.LayoutParams lp;
        lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BBPSTransactionHistoryFinacusModel transaction = mTransacHistoryList.get(position);

        holder.txt_biller_name.setText(transaction.getBiller_name());
        holder.txt_payment_date.setText(transaction.getPayment_date());
        holder.txt_amt.setText(transaction.getAmount());
        holder.txt_payment_mode.setText(transaction.getPayment_mode());

        int color = transaction.getResponse_code().equals("SUCCESS") ? R.color.colorGreen : R.color.design_default_color_error;
        holder.txt_status.setTextColor(mContext.getResources().getColor(color));
        holder.txt_status.setText(transaction.getResponse_code());

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf(holder.share_ll);
            }
        });
    }

    private void sharePdf(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.buildDrawingCache(true);

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth(), view.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        view.draw(canvas);
        pdfDocument.finishPage(page);

        File pdfFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "BillPayReceipt.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
            pdfDocument.close();

            Uri pdfUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", pdfFile);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            mContext.startActivity(Intent.createChooser(intent, "Select"));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Error sharing PDF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return mTransacHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_biller_name, txt_payment_date, txt_amt, txt_payment_mode, txt_status;
        LinearLayout share_ll;
        Button btn_share;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_biller_name = itemView.findViewById(R.id.txt_biller_name);
            txt_payment_date = itemView.findViewById(R.id.txt_payment_date);
            txt_amt = itemView.findViewById(R.id.txt_amt);
            txt_payment_mode = itemView.findViewById(R.id.txt_payment_mode);
            txt_status = itemView.findViewById(R.id.txt_status);
            share_ll = itemView.findViewById(R.id.share_ll);
            btn_share = itemView.findViewById(R.id.btn_share);
        }
    }
}
