package com.scorematics.android.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.content.res.AppCompatResources;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scorematics.android.R;
import com.scorematics.android.base.BaseFragment;
import com.scorematics.android.presentor.LoginPresentor;
import com.scorematics.android.presentorimpl.LoginPresentorImpl;
import com.scorematics.android.utils.PreferenceHandler;
import com.scorematics.android.view.LoginView;

/**
 * Created by same on 08/08/2018.
 */

public class LoginFragment extends BaseFragment implements LoginView {

    private View mLoginView;

    private static final String TAG = LoginFragment.class.getSimpleName();

    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (mLoginView == null) {
            mLoginView = inflater.inflate(R.layout.frag_login, null);
            initializeComponents();
        }
        return mLoginView;
    }

    /**
     * Used to initialize the components
     */
    private void initializeComponents() {

    }

    /**
     * Handles the click listener for mLoginButton, mForgotPasswordTextView and mRegisterTextView
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }


    @Override
    public void goNext() {
        hideProgress();


    }

    @Override
    public void setError(EditText editText, String message, TextInputLayout inputLayout) {

    }

    @Override
    public void noInternet() {
        hideProgress();
        showToast(getString(R.string.no_internet), Toast.LENGTH_LONG);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgress() {
        showProgressbar();
    }

    @Override
    public void hideProgress() {
            hideProgressbar();
    }




}

