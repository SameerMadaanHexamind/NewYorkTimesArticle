package com.scorematics.android.view;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by sameer.madaan on 08/08/2018.
 */
public interface LoginView {

    public void goNext();
    public void setError(EditText editText, String message, TextInputLayout inputLayoutEmail);
    public void noInternet();
    public void showMessage(String message);
    public void showProgress();
    public void hideProgress();

}
