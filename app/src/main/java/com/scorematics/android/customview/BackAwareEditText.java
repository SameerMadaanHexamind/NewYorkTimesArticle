package com.scorematics.android.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by sameer.madaan on 10/26/2017.
 */

public class BackAwareEditText extends EditText {
    OnKeyboardHidden mOnKeyboardHidden;

    public BackAwareEditText(Context context)
    {
        super(context);
        init();
    }

    public BackAwareEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BackAwareEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    { }

    public interface OnKeyboardHidden {
        public void onKeyboardHidden();
    }

    public void setOnKeyboardHidden(OnKeyboardHidden action) {
        mOnKeyboardHidden = action;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // User has pressed Back key. So hide the keyboard
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
            mOnKeyboardHidden.onKeyboardHidden();
        }
        return true;
    }

}