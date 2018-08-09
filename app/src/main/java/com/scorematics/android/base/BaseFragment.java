package com.scorematics.android.base;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.scorematics.android.R;
import com.scorematics.android.interfaces.OnAlertDialogFragmentListener;
import com.scorematics.android.utils.ImagePickerUtils;
import com.scorematics.android.utils.Utilities;

public abstract class BaseFragment extends Fragment implements
        OnAlertDialogFragmentListener,
        OnClickListener, Utilities.DatePickerdialogListener,
        Utilities.OnSpinnerDialogListener, Utilities.OnTimePickerDialogListener, ImagePickerUtils.ImagePickerListener {
    // private HeaderFragment mheaderFragment;
    private Utilities mUtilities;
    private ImagePickerUtils mImagePickerUtils;
    private FragmentTransaction mFragmentTransaction;
    private Activity mActivity;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreateView(inflater, container, savedInstanceState);

        mUtilities = Utilities.getInstance(mActivity);
        mImagePickerUtils = ImagePickerUtils.getInstance(this, this);
        mFragmentTransaction = getFragmentManager().beginTransaction();
        // mheaderFragment = getHeaderFragment();
        return setContentView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onResume() {

        Utilities.getInstance(mActivity).showHideKeyboard(false);

        super.onResume();


    }

    @Override
    public void onBackPress(int id) {
        // TODO Auto-generated method stub
        popFragment(id, mActivity);

    }

    public void showProgressbar() {
        dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void hideProgressbar() {
        if (dialog != null)
            dialog.dismiss();
    }


    public String createXml(String[] keys, String[] values) {
        return mUtilities.createXML(keys, values);
    }

    @Override
    public void onTimeChanged(int hourOfDay, int minute, String formattedValue,
                              int callBackId) {
        // TODO Auto-generated method stub

    }

    public SpannableString underLineText(String textToUnderLine) {
        SpannableString spanStr = new SpannableString(textToUnderLine);
        spanStr.setSpan(new UnderlineSpan(), 0, spanStr.length(), 0);
        return spanStr;
    }


    public String showFirstNameandLastName(String firstName, String LastName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LastName);
        if (!checkIfStringIsNullOrEmpty(firstName)) {
            stringBuilder.append(", ");
            stringBuilder.append(firstName);
        }
        return stringBuilder.toString();
    }

    public String getDateandTime(String dateTimeString) {
        if (dateTimeString != null) {
            return mUtilities.getDateAndTime(dateTimeString);

        }
        return null;
    }

    public String getCurrentDateAndTime(boolean isDateOnly) {
        return mUtilities.getCurrentDateAndTime(false);
    }

    public String set15MinutesPrior(String dateandTime) {
        return mUtilities.set15MinutePrior(dateandTime);
    }

    public boolean isEmailValid(String emailAddress) {
        return mUtilities.isEmailValid(emailAddress);
    }

    public void showDialog(String title, String string) {
        final Dialog alertDialog = new Dialog(mActivity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_single_button_confirmation);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        try {
            TextView alertTittle = (TextView)alertDialog.findViewById(R.id.alertTittle);
            TextView alertMsg =(TextView)alertDialog.findViewById(R.id.alertMsg);
            Button btnOk = (Button)alertDialog.findViewById(R.id.btnOk);
            //btnOk.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "Roboto-Light_0.ttf"));
            alertTittle.setText(title);
            alertMsg.setText(string);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        alertDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        alertDialog.show();

    }


    public boolean iValidNumber(String number) {
        return mUtilities.isValidNumber(number);
    }

    public boolean checkDateContraints(String startDate, String endDate) {
        return mUtilities.checkEndDateConstraints(startDate,
                endDate);
    }

    public ImagePickerUtils getmImagePickerUtilInstance() {
        return mImagePickerUtils;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ImagePickerUtils.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                mImagePickerUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImagePickerUtils.REQUEST_CAMERA:
            case ImagePickerUtils.SELECT_FILE:
                mImagePickerUtils.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public abstract View setContentView(LayoutInflater inflater,
                                        ViewGroup container, Bundle savedInstanceState);


    public void showToast(String message, int duration) {
        mUtilities.showToast(message, duration);
    }


    public void ShowThreeButtonDialog(String title, String msg, String button1,
                                      String button2, String button3, int iconResId,
                                      OnAlertDialogFragmentListener listener, int activityId) {
        mUtilities.showThreeOption_AlertDialog(title, msg,
                button1, button2, button3, iconResId, listener, activityId);


    }

    public void ShowDoubleButtonDialog(String title, String msg, String button1,
                                       String button2, int iconResId,
                                       OnAlertDialogFragmentListener listener, int activityId) {
        mUtilities.showDoubleOption_AlertDialog(title, msg,
                button1, button2, iconResId, listener, activityId);
    }


    public void ShowDoubleButtonCustomDialog(String title, String msg,
                                             OnAlertDialogFragmentListener listener, int activityId) {
        mUtilities.showDoubleOptionCustomAlertDialog(title, msg, listener, activityId);
    }


    public void ShowSingleButtonDialog(String title, String msg, String button1,
                                       int iconResId,
                                       OnAlertDialogFragmentListener listener, int activityId) {
        mUtilities.showSingleOption_AlertDialog(title, msg,
                button1, iconResId, listener, activityId);
    }

    public void replaceFragment(int replaceId, Fragment fragment,
                                String fragmentTag, boolean isBackStack, Context activityContext) {

        mUtilities.replaceFragment(replaceId, fragment,
                fragmentTag, isBackStack, activityContext);
    }


    public void addFragment(int replaceId, Fragment fragment,
                            String fragmentTag, boolean isBackStack, Context activityContext) {
        mUtilities.addFragment(replaceId, fragment,
                fragmentTag, isBackStack, activityContext);
    }

    public void popFragment(int replaceId, Context activityContext) {
        mUtilities.popFragment(replaceId, activityContext);
    }

    public void clearBackStack(Context activityContext) {
        mUtilities.clearBackStack(activityContext);
    }

    public void popBackStack(Context activityContext, String tag) {
        mUtilities.popBackStack(activityContext, tag);
    }


    public void showDatePicker(int callBackId) {
        mUtilities.showDatePickerDialog(this, callBackId);
    }

    public void showTimePicker(int callBackId) {
        mUtilities.ShowTimePicker(this, callBackId);
    }

    public boolean checkIfStringIsNullOrEmpty(String value) {
        return mUtilities.checkIfStringIsNullOrEmpty(value);
    }


    public void showSpinnerDialog(ArrayAdapter<String> listAdapter,
                                  String title, int callBackId) {
        mUtilities.showSpinnerDialog(listAdapter, title, this,
                callBackId);
    }


    public void showSpinnerDialog1(ArrayAdapter<?> listAdapter, String title,
                                   int callBackId) {
        mUtilities.showSpinnerDialog1(listAdapter, title, this,
                callBackId);
    }

    public boolean isValidUrl(String url) {
        return mUtilities.isValidUrl(url);

    }

    public void showLog(Utilities.Type logType, String message) {
        mUtilities.showLog(logType, this.getClass().getSimpleName(), message);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPositiveButtonClick(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNegativeButtonClick(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNeutralizeButtonClick(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSpinnerDialogClick(int position, int callBackId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void interfaceAttachError(int fragmentId, String errorResponse) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDateChanged(int year, int monthOfYear, int dayOfMonth,
                              int callBackId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onImageSelected(Uri uri) {

    }

    @Override
    public void onImageSelevtionError() {

    }

    public boolean isCallPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1009);
                return false;
            }
        } else {
            return true;
        }
    }

    public VectorDrawableCompat setVectorBackground(int drawable) {
        return VectorDrawableCompat.create(mActivity.getResources(), drawable, null);
    }
    public void setVectorDrawableColor(EditText editText, int drawable, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,setVectorBackgroundWithColor(drawable, color),null);
        }else {
            editText.setCompoundDrawables(null,null,mActivity.getResources().getDrawable(drawable),null);


        }
    }
    public VectorDrawableCompat setVectorBackgroundWithColor(int drawable, int color) {
        VectorDrawableCompat drawableCompat = VectorDrawableCompat.create(mActivity.getResources(), drawable, null);
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(color,
                PorterDuff.Mode.SRC_ATOP);
        drawableCompat.setColorFilter(new
                PorterDuffColorFilter(ContextCompat.getColor(mActivity, color), PorterDuff.Mode.SRC_ATOP));
        return drawableCompat;
    }
}
