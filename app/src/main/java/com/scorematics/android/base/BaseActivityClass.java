package com.scorematics.android.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;

import com.scorematics.android.interfaces.OnAlertDialogFragmentListener;
import com.scorematics.android.utils.Utilities;

/**
 * BaseActivity Class extends from Activity. All the Activities will extend this
 * baseclass to use the methods implemented in it.
 *
 * @author siddharth.brahmi
 */
public abstract class BaseActivityClass extends Activity implements
        OnAlertDialogFragmentListener,
        OnClickListener, OnFocusChangeListener, OnTouchListener

{
    @Override
    public void onBackPress(int id) {
        // TODO Auto-generated method stub

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

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mPrefseditor;

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

    public Intent mFocusIntent;
    public static String ACTIVITY_IN_BACKGROUND = "set_to_background";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {

            View view = getWindow().getDecorView();

            view.setOnTouchListener(this);
        } catch (Exception e) {
            // TODO: handle exception
        }

        mUtilities = Utilities.getInstance(this);

        mSharedPreferences = getSharedPreferences("abcshared", MODE_PRIVATE);
        mPrefseditor = mSharedPreferences.edit();

    }

    @Override
    protected void onResume() {
      mUtilities.showHideKeyboard(false);
        super.onResume();

    }


    private Utilities mUtilities;



    public void showToast(String message, int duration) {
        mUtilities.showToast(message, duration);
    }


    public void ShowThreeButtonDialog(String title, String msg, String button1,
                                      String button2, String button3, int iconResId,
                                      OnAlertDialogFragmentListener listener, int activityId) {
        mUtilities.showThreeOption_AlertDialog(title, msg, button1, button2,
                button3, iconResId, listener, activityId);
    }

    public void replaceFragment(int replaceId, android.support.v4.app.Fragment fragment,
                                String fragmentTag, boolean isBackStack, Context activityContext) {

        mUtilities.replaceFragment(replaceId, fragment, fragmentTag,
                isBackStack, activityContext);
    }

    public void addFragment(int replaceId, android.support.v4.app.Fragment fragment,
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
        // TODO Auto-generated method stub

        // isLoginActivityCall = true;
        super.onPause();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mUtilities.showHideKeyboard(false);
                break;

            default:
                break;
        }
        return false;
    }
}
