package dev.suncha.myleads;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

/**
 * Created by Sunny on 5/28/2015.
 */
public class saveDialogFragment extends DialogFragment {

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.layout_discard_draft, null))
                .setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("CONTINUE EDITING", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
