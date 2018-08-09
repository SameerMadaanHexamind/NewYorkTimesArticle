package com.scorematics.android.view;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by sameer.madaan on 08/08/2018.
 */

public interface ForgotView {
    public void showProgress();
    public void hideProgress();

    public void goNext(String message);
public void showError(EditText editText, String message, TextInputLayout ilEmail);
 public void showMessage(String message);

    void noInternet();
}
