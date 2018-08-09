package com.scorematics.android.presentorimpl;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;


import com.scorematics.android.R;
import com.scorematics.android.interactor.ForgotPasswordInteractor;
import com.scorematics.android.interactorimpl.ForgotPasswordInteractorImpl;
import com.scorematics.android.model.ForgotPasswordParam;
import com.scorematics.android.presentor.ForgotPresentor;
import com.scorematics.android.utils.CommonUtils;
import com.scorematics.android.utils.Utilities;
import com.scorematics.android.view.ForgotView;

/**
 * Created by sameer.madaan on 08/08/2018.
 */

public class ForgotPasswordPresentorImpl implements ForgotPresentor, ForgotPresentor.OnForgotPasswordComplete {

    Activity activity;
    ForgotView forgotView;
    ForgotPasswordInteractor forgotPasswordInteractor;

    public ForgotPasswordPresentorImpl(Activity activity, ForgotView forgotView){
        this.activity = activity;
        this.forgotView = forgotView;
        forgotPasswordInteractor = new ForgotPasswordInteractorImpl();
    }

    @Override
    public void validate(EditText etEmail, TextInputLayout ilEmail) {
        ilEmail.setError(null);
            if(etEmail.getText().toString().length()>0){
                if(Utilities.isValidEmail(etEmail.getText().toString())) {
                    if (CommonUtils.isOnline(activity)) {
                        ForgotPasswordParam forgotPasswordParam = new ForgotPasswordParam();
                        forgotPasswordInteractor.forgotPassword(activity, forgotPasswordParam, this);
                    } else {
                        forgotView.noInternet();
                    }
                }
                else {
                    forgotView.showError(etEmail, activity.getString(R.string.alert_valid_email), ilEmail);
                }

            }else{
                forgotView.showError(etEmail,activity.getString(R.string.enter_email_fp),ilEmail);
            }
    }

    @Override
    public void onSuccess(String message) {
        forgotView.goNext(message);
    }

    @Override
    public void onFail(String error) {
        forgotView.showMessage(error);
    }
}
