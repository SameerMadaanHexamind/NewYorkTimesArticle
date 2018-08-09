package com.scorematics.android.fragment;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.scorematics.android.R;
import com.scorematics.android.base.BaseFragment;
import com.scorematics.android.presentor.ForgotPresentor;
import com.scorematics.android.presentorimpl.ForgotPasswordPresentorImpl;
import com.scorematics.android.view.ForgotView;


/**
 * Created by sameer.madaan on 08/08/2018.
 */

public class ForgotPasswordFragment extends BaseFragment implements ForgotView {
    private View mForgotPasswordView;
    private EditText mEmailAddress;
    private Button mSend;
    private ForgotPresentor forgotPresenter;
    private TextInputLayout ilEmail;
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mForgotPasswordView == null) {
           mForgotPasswordView = inflater.inflate(R.layout.frag_forgot_password, null);
            initializeComponents();
        }
        return mForgotPasswordView;
    }

    private void initializeComponents() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

        }
    }


    @Override
    public void showProgress() {
        showProgressbar();
    }

    @Override
    public void hideProgress() {
        hideProgressbar();
    }

    @Override
    public void goNext(String message) {
        hideProgressbar();
        showToast(message, Toast.LENGTH_LONG);
        replaceFragment(R.id.activity_login_framelayout, new LoginFragment(), LoginFragment.class.getName(), false, getActivity());
    }

    @Override
    public void showError(EditText editText, String message, TextInputLayout inputLayout) {
        hideProgressbar();
        inputLayout.setError(message);
        inputLayout.requestFocus();



    }

    @Override
    public void showMessage(String message) {
        hideProgressbar();
            showToast(message, Toast.LENGTH_LONG);
    }

    @Override
    public void noInternet() {
        showToast(getString(R.string.no_internet), Toast.LENGTH_LONG);
    }


}
