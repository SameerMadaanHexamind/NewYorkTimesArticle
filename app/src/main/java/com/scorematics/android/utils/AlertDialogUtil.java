package com.scorematics.android.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.scorematics.android.R;
import com.scorematics.android.interfaces.OnAlertDialogFragmentListener;


/*
 * This Class is used to create custom alert dialog. You can create single option, double option and three options alert dialog.
 */
class AlertDialogUtil {

    private OnAlertDialogFragmentListener alertDialogListener;
    private Context context;

    /**
     * Get new instance of single option alert dialog **Note - User can pass
     * null for String value and 0 for integer value for unwanted parameters.
     *
     * @return alertDialog
     */
    protected AlertDialogUtil() {

    }

    public Dialog singleOptionAlertDialog(Context ctx, String title,
                                          String msg, String button1, int iconResId,
                                          OnAlertDialogFragmentListener listener, final int activityId) {
        this.context = ctx;
        final Dialog alertDialog = new Dialog(ctx);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_single_button_confirmation);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        try {
            this.alertDialogListener = listener;
            TextView alertTittle = (TextView)alertDialog.findViewById(R.id.alertTittle);
            TextView alertMsg =(TextView)alertDialog.findViewById(R.id.alertMsg);
            Button btnOk = (Button)alertDialog.findViewById(R.id.btnOk);
            btnOk.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light_0.ttf"));


            alertTittle.setText(title);
            alertMsg.setText(msg);

            alertTittle.setText(title);
            alertMsg.setText(msg);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialogListener != null) {
                        alertDialogListener
                                .onPositiveButtonClick(activityId);
                        alertDialog.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        alertDialog.show();

        return alertDialog;
    }

    /**
     * Get new instance of Double Option alert dialog **Note - User can pass
     * null for String value and 0 for integer value for unwanted parameters.
     *
     * @param ctx        of Activity to which dialog is related
     * @param title      for dialog
     * @param msg        to print
     * @param button1    name of first(Positive) button
     * @param button2    name of second(Negative) button
     * @param iconResId  for Icon to set on Dialog
     * @param listener   OnALertFragmentListener to perform action on button clicked.
     * @param activityId to identify functionality of first button
     * @return alertDialog
     */

    public AlertDialog doubleOptionAlertDialog(Context ctx, String title,
                                               String msg, String button1, String button2, int iconResId,
                                               OnAlertDialogFragmentListener listener, final int activityId) {




        AlertDialog alertDialog = null;
        this.context = ctx;
        try {
            this.alertDialogListener = listener;
            alertDialog = new AlertDialog.Builder(ctx)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(button1,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    if (alertDialogListener != null) {
                                        alertDialogListener
                                                .onPositiveButtonClick(activityId);

                                    }

                                }
                            })
                    .setNegativeButton(button2,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    if (alertDialogListener != null) {
                                        alertDialogListener
                                                .onNegativeButtonClick(activityId);
                                    }
                                }
                            }).create();
            if (null == title) {
                alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            } else {
                alertDialog.setTitle(title);
            }
            if (iconResId != 0) {
                alertDialog.setIcon(iconResId);
            }
            alertDialog.setCanceledOnTouchOutside(false);

            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alertDialog;

    }
    public Dialog doubleOptionCustomAlertDialog(Context ctx, String title,
                                                String msg,
                                                OnAlertDialogFragmentListener listener, final int activityId) {

        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        try {
            this.alertDialogListener = listener;

            TextView alertTittle = (TextView)dialog.findViewById(R.id.alertTittle);
            TextView alertMsg =(TextView)dialog.findViewById(R.id.alertMsg);
            Button btnOk = (Button)dialog.findViewById(R.id.btnOk);
            Button btnCancel = (Button)dialog.findViewById(R.id.btnCancel);

            btnCancel.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light_0.ttf"));
            btnOk.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light_0.ttf"));


            alertTittle.setText(title);
            alertMsg.setText(msg);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialogListener != null) {
                        alertDialogListener
                                .onPositiveButtonClick(activityId);
                        dialog.dismiss();
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialogListener != null) {
                        alertDialogListener
                                .onNegativeButtonClick(activityId);
                        dialog.dismiss();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.show();














        return dialog;
    }
    /**
     * Get new instance of Double Option alert dialog **Note - User can pass
     * null for String value and 0 for integer value for unwanted parameters.
     *
     * @param ctx        of Activity to which dialog is related
     * @param title      for dialog
     * @param msg        to print
     * @param button1    name of first(Positive) button
     * @param button2    name of second(Negative) button
     * @param button3    name of third(Neutral) button
     * @param iconResId  for Icon to set on Dialog
     * @param listener   OnALertFragmentListener to perform action on button clicked.
     * @param activityId to identify functionality of first button
     * @return alertDialog
     */
    public AlertDialog threeOptionAlertDialog(Context ctx, String title,
                                              String msg, String button1, String button2, String button3,
                                              int iconResId, OnAlertDialogFragmentListener listener,
                                              final int activityId) {
        AlertDialog alertDialog = null;
        this.context = ctx;
        try {
            this.alertDialogListener = listener;
            alertDialog = new AlertDialog.Builder(ctx)
                    .setMessage(msg)
                    .setPositiveButton(button1,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    if (alertDialogListener != null) {
                                        alertDialogListener
                                                .onPositiveButtonClick(activityId);
                                    }

                                }
                            })
                    .setNegativeButton(button2,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    if (alertDialogListener != null) {
                                        alertDialogListener
                                                .onNegativeButtonClick(activityId);
                                    }
                                }
                            })
                    .setNeutralButton(button3,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    if (alertDialogListener != null) {
                                        alertDialogListener
                                                .onNeutralizeButtonClick(activityId);
                                    }
                                }
                            }).create();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == title) {
                alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            } else {
                alertDialog.setTitle(title);
            }
            if (iconResId != 0) {
                alertDialog.setIcon(iconResId);
            }
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
        return alertDialog;

    }

}