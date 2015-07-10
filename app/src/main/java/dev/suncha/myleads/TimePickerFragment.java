package dev.suncha.myleads;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Sunny on 7/10/2015.
 */
public class TimePickerFragment extends DialogFragment {
    int hour, min;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, min, false);
    }

//    private TimePickerDialog.OnTimeSetListener timePickerListener=
//            new TimePickerDialog.OnTimeSetListener(){
//
//                @Override
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                    hour=hourOfDay;
//                    min=minute;
//                }
//            };
}
