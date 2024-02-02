package com.example.spanishdatepicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button button;
    public String months;
    public int mYear;
    public int mMonth;
    public int mDay;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Context context = this; // Use the activity context here
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(config, displayMetrics);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;

            switch (mMonth) {
                case 1:
                    months = "Jan";
                    break;
                case 2:
                    months = "Feb";
                    break;
                case 3:
                    months = "Mar";
                    break;
                case 4:
                    months = "Apr";
                    break;
                case 5:
                    months = "May";
                    break;
                case 6:
                    months = "Jun";
                    break;
                case 7:
                    months = "Jul";
                    break;
                case 8:
                    months = "Aug";
                    break;
                case 9:
                    months = "Sep";
                    break;
                case 10:
                    months = "Oct";
                    break;
                case 11:
                    months = "Nov";
                    break;
                case 12:
                    months = "Dec";
                    break;
                default:
                    break;
            }
            if (view.isShown()) {
                String day = String.valueOf(mDay).trim();
                String month = String.valueOf(mMonth).trim();
                if (day.length() == 1) {
                    day = "0" + day;
                }
                if (month.length() == 1) {
                    month = "0" + month;
                }
                textView.setText(mYear + "/" + month + "/" + day);
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

}
