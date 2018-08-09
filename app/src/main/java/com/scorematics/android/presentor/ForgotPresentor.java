package com.scorematics.android.presentor;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by sameer.madaan on 08/08/2018.
 */

public interface ForgotPresentor {

    public interface OnForgotPasswordComplete{
        public void onSuccess(String message);
        public void onFail(String error);
    }

    public void validate(EditText etEmail, TextInputLayout ilEmail);
}
