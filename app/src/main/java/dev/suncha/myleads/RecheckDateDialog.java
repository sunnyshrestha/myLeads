package dev.suncha.myleads;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Sunny on 5/29/2015.
 */
public class RecheckDateDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle("Date mismatch")
                .setMessage("Follow up date needs to be after the meeting date.")
                .setPositiveButton("OK", null)
                .create();
    }
}
