package com.example.i_tainh.demoorderfood.FragmentApp;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.i_tainh.demoorderfood.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        EditText txtDob = getActivity().findViewById(R.id.txtDob);

        String dob = dayOfMonth + " / " +(month+1)+" / " + year;

        txtDob.setText(dob);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int iYear = calendar.get(Calendar.YEAR);
        int iMonth = calendar.get(Calendar.MONTH);
        int iDate = calendar.get(Calendar.DAY_OF_MONTH);
        return  new DatePickerDialog(getActivity(),this,iDate,iMonth,iYear);
    }
}
