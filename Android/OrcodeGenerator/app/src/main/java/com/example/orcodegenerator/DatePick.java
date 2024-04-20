package com.example.orcodegenerator;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class DatePick {
    public int mYear;
    public int mMonth;
    public int mDay;
    public String months;
    public TextView editTextDate;

    public  void selectDate(Context context, EditText editText){
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        editTextDate = editText;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;

            switch (mMonth) {
                case 0:
                    months = "Jan";
                    break;
                case 1:
                    months = "Feb";
                    break;
                case 2:
                    months = "Mar";
                    break;
                case 3:
                    months = "Apr";
                    break;
                case 4:
                    months = "May";
                    break;
                case 5:
                    months = "Jun";
                    break;
                case 6:
                    months = "Jul";
                    break;
                case 7:
                    months = "Aug";
                    break;
                case 8:
                    months = "Sep";
                    break;
                case 9:
                    months = "Oct";
                    break;
                case 10:
                    months = "Nov";
                    break;
                case 11:
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
                editTextDate.setText(day + "/" + month + "/" + mYear);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
