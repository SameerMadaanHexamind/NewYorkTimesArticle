package com.scorematics.android.presentor;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by sameer.madaan on 08/08/2018.
 */

public interface LoginPresentor {

    public interface OnLoginResponse{
        public void onSuccessfullyLogin(String userID);
        public void onFail(String message);
    }

    public void validate(EditText email, EditText password, TextInputLayout inputLayoutEmail, TextInputLayout inputLayoutPassword);
}
