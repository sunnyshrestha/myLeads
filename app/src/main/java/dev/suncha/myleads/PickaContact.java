package dev.suncha.myleads;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Sunny on 5/29/2015.
 */
public class PickaContact extends DialogFragment {

    private OnListDialogItemSelect listener;
    private String title;
    private String[] list;

    public PickaContact(OnListDialogItemSelect listener, String title, String[] list) {
        this.listener = listener;
        this.title = title;
        this.list = list;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setTitle(title)
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listener.onListItemSelected(list[which]);
                        getDialog().dismiss();
                        PickaContact.this.dismiss();
                    }
                })
                .create();
    }

    public interface OnListDialogItemSelect {
        public void onListItemSelected(String selection);
    }
}
