package com.scorematics.android.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.scorematics.android.interfaces.OnAlertDialogFragmentListener;

/*
 * This class is used for creating Custom Alert Dialog using Fragments. You can create custom Alert Dialog and use it on fragments. 
 */
public class AlertDialogWithFragments extends DialogFragment {

    private String message, title;
    private AlertDialogWithFragments alertObj;
    private int IconResId, button1, button2, fragmentID;
    private AlertType alertType;
    private OnAlertDialogFragmentListener alertDialogListener;
    private Context context;

	/*
     * Use enum to select type of Dialog when using getAlertDialog Method
	 */

    public static enum AlertType {
        SINGLEOPTION, DOUBLEOPTION, CUSTOM
    }

    ;

    public AlertDialogWithFragments() {

    }


    protected static AlertDialogWithFragments getAlertDialog() {
        return new AlertDialogWithFragments();
    }

    public void setDialogArguments(String title, String msg, int button1,
                                   int button2, int iconResId, AlertType alertType,
                                   OnAlertDialogFragmentListener listener, int fragmentID) {
        try {

            Bundle bundle = new Bundle();
            bundle.putString("message", msg);
            bundle.putString("title", title);
            bundle.putInt("button1", button1);
            bundle.putInt("button2", button2);
            bundle.putInt("iconresId", iconResId);
            bundle.putSerializable("alertType", alertType);
            bundle.putInt("fragmentID", fragmentID);
            this.setArguments(bundle);
            alertDialogListener = listener;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to create dialog with arguments passed in the
     * setDialogArgument method.
     */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        try {
            this.message = getArguments().getString("message");
            this.title = getArguments().getString("title");
            this.IconResId = getArguments().getInt("iconresId");
            this.button1 = getArguments().getInt("button1");
            this.button2 = getArguments().getInt("button2");
            this.alertType = (AlertType) getArguments().getSerializable(
                    "alertType");
            this.fragmentID = getArguments().getInt("fragmentID");
            if (alertType == AlertType.SINGLEOPTION) {

                return new AlertDialog.Builder(getActivity())
                        .setIcon(IconResId)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(getString(button1),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {

                                        if (alertDialogListener != null) {
                                            alertDialogListener
                                                    .onPositiveButtonClick(fragmentID);
                                        } else {
                                            alertDialogListener
                                                    .interfaceAttachError(
                                                            fragmentID,
                                                            "Error in interface attach");
                                        }
                                        // dismiss();
                                    }
                                }).create();
            } else if (alertType == AlertType.DOUBLEOPTION) {
                return new AlertDialog.Builder(getActivity())
                        .setIcon(IconResId)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(getString(button1),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        if (alertDialogListener != null) {
                                            alertDialogListener
                                                    .onPositiveButtonClick(fragmentID);
                                        }
                                    }
                                })
                        .setNegativeButton(getString(button2),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        if (alertDialogListener != null) {
                                            alertDialogListener
                                                    .onNegativeButtonClick(fragmentID);
                                        }
                                    }
                                }).create();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
