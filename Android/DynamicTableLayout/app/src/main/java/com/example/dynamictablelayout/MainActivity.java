package com.example.dynamictablelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TableLayout;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TableLayout mTableLayout;
    ProgressDialog mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = new ProgressDialog(this);
        mTableLayout = (TableLayout) findViewById(R.id.tableInvoices);
        mTableLayout.setStretchAllColumns(true);
        startLoadData();
    }
    public void startLoadData() {
        mProgressBar.setCancelable(false);
        mProgressBar.setMessage("Fetching Invoices..");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
        new LoadDataTask().execute(0);
    }
    public void loadData(List<InvoiceData> tablelist) {
        int leftRowMargin=0;
        int topRowMargin=0;
        int rightRowMargin=0;
        int bottomRowMargin = 0;
        int textSize = 0, smallTextSize =0, mediumTextSize = 0;
        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);
        mediumTextSize = (int)
                getResources().getDimension(R.dimen.font_size_medium);
        /*Invoices invoices = new Invoices();
        InvoiceData[] data = invoices.getInvoices();*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        int rows = tablelist.size();
        getSupportActionBar().setTitle("Invoices (" + String.valueOf(rows) + ")");
        TextView textSpacer = null;
        mTableLayout.removeAllViews();
        // -1 means heading row
            for (int i = -1; i < rows; i++) {
                InvoiceData row = null;
                if (i > -1)
                    row = tablelist.get(i);
                else {
                    textSpacer = new TextView(this);
                    textSpacer.setText("");
                }
                // data columns
                final TextView tv = new TextView(this);
                tv.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.LEFT);
                tv.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv.setText("Inv.#");
                    tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    tv.setText(String.valueOf(row.getInvoiceNumber()));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                final TextView tv2 = new TextView(this);
                if (i == -1) {
                    tv2.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv2.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                tv2.setGravity(Gravity.LEFT);
                tv2.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv2.setText("Date");
                    tv2.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else {
                    tv2.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv2.setTextColor(Color.parseColor("#000000"));
                    tv2.setText(dateFormat.format(row.getInvoiceDate()));
                }
                final LinearLayout layCustomer = new LinearLayout(this);
                layCustomer.setOrientation(LinearLayout.VERTICAL);
                layCustomer.setPadding(0, 10, 0, 10);
                layCustomer.setBackgroundColor(Color.parseColor("#f8f8f8"));
                final TextView tv3 = new TextView(this);
                if (i == -1) {
                    tv3.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv3.setPadding(5, 5, 0, 5);
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv3.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv3.setPadding(5, 0, 0, 5);
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                tv3.setGravity(Gravity.TOP);
                if (i == -1) {
                    tv3.setText("Customer");
                    tv3.setBackgroundColor(Color.parseColor("#f0f0f0"));
                } else {
                    tv3.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    tv3.setTextColor(Color.parseColor("#000000"));
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tv3.setText(row.getCustomerName());
                }
                layCustomer.addView(tv3);
                if (i > -1) {
                    final TextView tv3b = new TextView(this);
                    tv3b.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv3b.setGravity(Gravity.RIGHT);
                    tv3b.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    tv3b.setPadding(5, 1, 0, 5);
                    tv3b.setTextColor(Color.parseColor("#aaaaaa"));
                    tv3b.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    tv3b.setText(row.getCustomerAddress());
                    layCustomer.addView(tv3b);
                }
                final LinearLayout layAmounts = new LinearLayout(this);
                layAmounts.setOrientation(LinearLayout.VERTICAL);
                layAmounts.setGravity(Gravity.RIGHT);
                layAmounts.setPadding(0, 10, 0, 10);
                layAmounts.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                final TextView tv4 = new TextView(this);
                if (i == -1) {
                    tv4.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv4.setPadding(5, 5, 1, 5);
                    layAmounts.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else {
                    tv4.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv4.setPadding(5, 0, 1, 5);
                    layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                tv4.setGravity(Gravity.RIGHT);
                if (i == -1) {
                    tv4.setText("Inv.Amount");
                    tv4.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv4.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv4.setTextColor(Color.parseColor("#000000"));
                    tv4.setText(row.getInvoiceAmount());
                    tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                layAmounts.addView(tv4);
                if (i > -1) {
                    final TextView tv4b = new TextView(this);
                    tv4b.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv4b.setGravity(Gravity.RIGHT);
                    tv4b.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    tv4b.setPadding(2, 2, 1, 5);
                    tv4b.setTextColor(Color.parseColor("#00afff"));
                    tv4b.setBackgroundColor(Color.parseColor("#ffffff"));
                    String due = "";
                    due = "Due:" + row.getAmountDue();
                    due = due.trim();
                    tv4b.setText(due);
                    layAmounts.addView(tv4b);
                }
                final TextView tv5 = new TextView(this);
                if (i == -1) {
                    tv5.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
                    tv5.setPadding(100, 5, 0, 5);
                    tv5.setTextColor(Color.parseColor("#00afff"));
                    tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv5.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                tv5.setGravity(Gravity.RIGHT);
               /* tv5.setPadding(5, 15, 0, 15);*/
                if (i == -1) {
                    tv5.setText("sample");
                    tv5.setBackgroundColor(Color.parseColor("#8A0022"));
                } else {
                    tv5.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv5.setTextColor(Color.parseColor("#000000"));
                    tv5.setText(String.valueOf(row.getId()));
                }
                // add table row
                final TableRow tr = new TableRow(this);
                tr.setId(i + 1);
                TableLayout.LayoutParams trParams = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin,
                        bottomRowMargin);
                tr.setPadding(0, 0, 0, 0);
                tr.setLayoutParams(trParams);
                tr.addView(tv);
                tr.addView(tv2);
                tr.addView(layCustomer);
                tr.addView(layAmounts);
                tr.addView(tv5);
                if (i > -1) {
                    tr.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            TableRow tr = (TableRow) v;
                        }
                    });
                }
                mTableLayout.addView(tr, trParams);
                if (i > -1) {
                    // add separator row
                    final TableRow trSep = new TableRow(this);
                    TableLayout.LayoutParams trParamsSep = new
                            TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    trParamsSep.setMargins(leftRowMargin, topRowMargin,
                            rightRowMargin, bottomRowMargin);
                    trSep.setLayoutParams(trParamsSep);
                    TextView tvSep = new TextView(this);
                    TableRow.LayoutParams tvSepLay = new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT);
                    tvSepLay.span = 4;
                    tvSep.setLayoutParams(tvSepLay);
                    tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                    tvSep.setHeight(1);
                    trSep.addView(tvSep);
                    mTableLayout.addView(trSep, trParamsSep);
                }
            }
    }
    class LoadDataTask extends AsyncTask<Integer, Integer, String> {
        List<InvoiceData> tablelist;
        @Override
        protected String doInBackground(Integer... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tablelist=new ArrayList<>();
            tablelist.add(new InvoiceData(11,1,new Date(),"sagar1","ayyappanagar1","123.4561","123.41"));
            tablelist.add(new InvoiceData(22,2,new Date(),"sagar2","ayyappanagar2","123.4562","123.42"));
            tablelist.add(new InvoiceData(33,3,new Date(),"sagar3","ayyappanagar3","123.4563","123.43"));
            tablelist.add(new InvoiceData(44,4,new Date(),"sagar4","ayyappanagar4","123.4564","123.44"));
            tablelist.add(new InvoiceData(55,5,new Date(),"sagar5","ayyappanagar5","123.4565","123.45"));
            tablelist.add(new InvoiceData(66,6,new Date(),"sagar6","ayyappanagar6","123.4566","123.46"));
            tablelist.add(new InvoiceData(77,7,new Date(),"sagar7","ayyappanagar7","123.4567","123.47"));
            tablelist.add(new InvoiceData(88,8,new Date(),"sagar8","ayyappanagar8","123.4568","123.48"));
            tablelist.add(new InvoiceData(99,9,new Date(),"sagar9","ayyappanagar9","123.4569","123.49"));
            tablelist.add(new InvoiceData(100,10,new Date(),"sagar10","ayyappanagar10","123.45610","123.410"));
            tablelist.add(new InvoiceData(111,11,new Date(),"sagar11","ayyappanagar11","123.45611","123.411"));

            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            mProgressBar.hide();
            loadData(tablelist);
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }
}