package dev.suncha.myleads;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Sunny on 5/29/2015.
 */
public class RecheckDateDialog extends DialogFragment {
    LinearLayout parentview;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // parentview=(LinearLayout) parentview.findViewById();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_date_mismatch, null);
        //builder.setView(layoutInflater.inflate(R.layout.layout_date_mismatch, null))
        parentview = (LinearLayout) view.findViewById(R.id.myparentlayout);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), R.string.datenoset, Toast.LENGTH_SHORT).show();
                    }
                });
        setCancelable(false);

        return builder.create();
    }
}
