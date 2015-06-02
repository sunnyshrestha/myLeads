package dev.suncha.myleads;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Sunny on 5/30/2015.
 */
public class contactLeadDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setTitle(R.string.select_action)
                .setItems(R.array.lead_action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri number = Uri.parse("tel:" + getArguments().getString("phone_number"));
                        switch (which) {
                            case 0:
                                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                                PackageManager packageManager = getActivity().getPackageManager();
                                List activities = packageManager.queryIntentActivities(callIntent, PackageManager.MATCH_DEFAULT_ONLY);
                                boolean isIntentSafe = activities.size() > 0;
                                if (isIntentSafe)
                                    startActivity(callIntent);
                                else
                                    Toast.makeText(getActivity(), R.string.no_package, Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Intent messageIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", getArguments().getString("phone_number"), null));
                                PackageManager packageManager_sms = getActivity().getPackageManager();
                                List activities_message = packageManager_sms.queryIntentActivities(messageIntent, PackageManager.MATCH_DEFAULT_ONLY);
                                boolean isIntentSafeForMessaging = activities_message.size() > 0;
                                if (isIntentSafeForMessaging)
                                    startActivity(messageIntent);
                                else
                                    Toast.makeText(getActivity(), R.string.no_package, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create();
    }
}
