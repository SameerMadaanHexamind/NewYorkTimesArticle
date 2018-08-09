package com.scorematics.android.presentorimpl;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.scorematics.android.R;
import com.scorematics.android.interactor.LoginInteractor;
import com.scorematics.android.interactorimpl.LoginInteractorImpl;
import com.scorematics.android.model.LoginParam;
import com.scorematics.android.presentor.LoginPresentor;
import com.scorematics.android.utils.CommonUtils;
import com.scorematics.android.utils.Constants;
import com.scorematics.android.utils.PreferenceHandler;
import com.scorematics.android.utils.Utilities;
import com.scorematics.android.view.LoginView;


/**
 * Created by sameer.madaan on 08/08/2018.
 */

public class LoginPresentorImpl implements LoginPresentor,LoginPresentor.OnLoginResponse {

    Activity activity;
    LoginView loginView;
    LoginInteractor loginInteractor;

    public LoginPresentorImpl(Activity activity, LoginView loginView){
        this.activity = activity;
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl();

    }


    @Override
    public void validate(EditText etEmail, EditText password, TextInputLayout inputLayoutEmail, TextInputLayout inputLayoutPassword) {
        loginView.showProgress();
        inputLayoutEmail.setError(null);
        inputLayoutPassword.setError(null);

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            etEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_email,0);
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.mipmap.ic_password,0);

        }else {
            etEmail.setCompoundDrawables(null,null,activity.getResources().getDrawable(R.drawable.ic_email),null);
            password.setCompoundDrawables(null,null,activity.getResources().getDrawable(R.mipmap.ic_password),null);

        }*/


        if(etEmail.getText().toString().length()>0){
            if(Utilities.isValidEmail(etEmail.getText().toString().trim())){
            if(password.getText().toString().length()>0) {

                if (CommonUtils.isOnline(activity)) {
                    LoginParam loginParam = new LoginParam();
                    loginParam.setUsername(etEmail.getText().toString().trim());
                    loginParam.setPassword(password.getText().toString().trim());
                    loginInteractor.login(activity, loginParam, this);
                } else {
                    loginView.noInternet();
                }
            }
                else
            {
                loginView.setError(password,activity.getString(R.string.validate_password),inputLayoutPassword);
            }


            }else{
                loginView.setError(etEmail,activity.getString(R.string.alert_valid_email),inputLayoutEmail);
            }

        }else{
            loginView.setError(etEmail,activity.getString(R.string.validate_email),inputLayoutEmail);
        }
    }

    @Override
    public void onSuccessfullyLogin(String userID) {
        loginView.goNext();

    }

    @Override
    public void onFail(String message) {
        loginView.hideProgress();
        loginView.showMessage(message);
    }


}
