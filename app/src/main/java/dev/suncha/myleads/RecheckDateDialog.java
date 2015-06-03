package dev.suncha.myleads;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Toast;

/**
 * Created by Sunny on 5/29/2015.
 */
public class RecheckDateDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return new android.support.v7.app.AlertDialog.Builder(getActivity())
//                .setCancelable(false)
//                .setTitle("Date mismatch")
//                .setMessage("Follow up date needs to be after the meeting date.")
//                .setPositiveButton("OK", null)
//                .create();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.layout_discard_draft, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), R.string.datenoset, Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }
}
