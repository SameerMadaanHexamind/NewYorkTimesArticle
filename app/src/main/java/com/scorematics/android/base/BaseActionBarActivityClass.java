package com.scorematics.android.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.scorematics.android.interfaces.OnAlertDialogFragmentListener;
import com.scorematics.android.utils.Utilities;


/**
 * BaseActivity Class extends from Activity. All the Activities will extend this
 * baseclass to use the methods implemented in it.
 *
 */
public abstract class BaseActionBarActivityClass extends AppCompatActivity
        implements
        OnAlertDialogFragmentListener, OnClickListener, OnFocusChangeListener

{
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mPrefseditor;
    public Intent mFocusIntent;
    private Utilities mUtilities;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        mUtilities = Utilities.getInstance(this);
        mSharedPreferences = getSharedPreferences("abcshared", MODE_PRIVATE);
        mPrefseditor = mSharedPreferences.edit();

    }




    @Override
    public void onBackPress(int id) {
        // TODO Auto-generated method stub
        popFragment(id, this);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        if (v instanceof EditText) {
            // Do nothing
        } else {
            Utilities.getInstance(this).showHideKeyboard(false);
        }

    }

    @Override
    protected void onStart() {

        // applicationWillEnterForeground();

        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (!isScreenOn) {
            // logoutSession();
        }

    }

    BroadcastReceiver powerOffBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.i("PowerOff", "intent = " + intent.getAction());
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                // logoutSession();
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean checkIfStringIsNullOrEmpty(String value) {
        return mUtilities.checkIfStringIsNullOrEmpty(value);
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }


    public void showToast(String message, int duration) {
        mUtilities.showToast(message, duration);
    }


    public void replaceFragment(int replaceId, Fragment fragment,
                                String fragmentTag, boolean isBackStack, Context activityContext) {

        mUtilities.replaceFragment(replaceId, fragment, fragmentTag,
                isBackStack, activityContext);
    }


    public void addFragment(int replaceId, Fragment fragment,
                            String fragmentTag, boolean isBackStack, Context activityContext) {
        mUtilities.addFragment(replaceId, fragment, fragmentTag, isBackStack,
                activityContext);
    }

    public void popFragment(int replaceId, Context activityContext) {
        mUtilities.popFragment(replaceId, activityContext);
    }

    public void clearBackStack(Context activityContext) {
        mUtilities.clearBackStack(activityContext);
    }


    public String createXml(String[] keys, String[] values) {
        return mUtilities.createXML(keys, values);
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
    public void interfaceAttachError(int fragmentId, String errorResponse) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onPause() {
        super.onPause();
    }



}
