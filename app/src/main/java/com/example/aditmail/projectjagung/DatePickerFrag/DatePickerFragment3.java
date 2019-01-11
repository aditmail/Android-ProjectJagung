package com.example.aditmail.projectjagung.DatePickerFrag;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.aditmail.projectjagung.R;

import java.util.Calendar;

/**
 * Created by ADITMAIL on 30/01/2018.
 */

public class DatePickerFragment3 extends DialogFragment
        implements DatePickerDialog.OnDateSetListener  {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        EditText tv3= (EditText) getActivity().findViewById(R.id.editText_tanggalDimulaiPanen);
        if(view.getMonth() == 0) {
            tv3.setText(view.getDayOfMonth() + "/" + 1 + "/" + view.getYear());
        }
        else if (view.getMonth() == 1) {
            tv3.setText(view.getDayOfMonth() + "/" + 2 + "/" + view.getYear());
        }
        else if (view.getMonth() == 2) {
            tv3.setText(view.getDayOfMonth() + "/" + 3 + "/" + view.getYear());
        }
        else if (view.getMonth() == 3) {
            tv3.setText(view.getDayOfMonth() + "/" + 4 + "/" + view.getYear());
        }
        else if (view.getMonth() == 4) {
            tv3.setText(view.getDayOfMonth() + "/" + 5 + "/" + view.getYear());
        }
        else if (view.getMonth() == 5) {
            tv3.setText(view.getDayOfMonth() + "/" + 6 + "/" + view.getYear());
        }
        else if (view.getMonth() == 6) {
            tv3.setText(view.getDayOfMonth() + "/" + 7 + "/" + view.getYear());
        }
        else if (view.getMonth() == 7) {
            tv3.setText(view.getDayOfMonth() + "/" + 8 + "/" + view.getYear());
        }
        else if (view.getMonth() == 8) {
            tv3.setText(view.getDayOfMonth() + "/" + 9 + "/" + view.getYear());
        }
        else if (view.getMonth() == 9) {
            tv3.setText(view.getDayOfMonth() + "/" + 10 + "/" + view.getYear());
        }
        else if (view.getMonth() == 10) {
            tv3.setText(view.getDayOfMonth() + "/" + 11 + "/" + view.getYear());
        }
        else if (view.getMonth() == 11) {
            tv3.setText(view.getDayOfMonth() + "/" + 12 + "/" + view.getYear());
        }

    }
}


