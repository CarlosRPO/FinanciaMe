package co.com.carlosrestrepo.financiame.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import co.com.carlosrestrepo.financiame.R;

/**
 *
 * @author  Carlos Restrepo
 * @created Octubre 07 de 2015
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public interface SelectDateDialogListener {
        void onFinishSelectDateDialog(String date);
    }

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
        month++;
        String mes = month < 10 ? "0" + (month) : String.valueOf(month);
        String dia = String.valueOf(day).length() == 1 ? "0" + day : String.valueOf(day);
        InfoMovimientoFragment imf = (InfoMovimientoFragment) getFragmentManager().getFragments().get(0);
        imf.onFinishSelectDateDialog(dia + "/" + mes + "/" + year);
        this.dismiss();
    }
}