package com.scorematics.android.interactor;

import android.app.Activity;


import com.scorematics.android.model.ForgotPasswordParam;
import com.scorematics.android.presentor.ForgotPresentor;

/**
 * Created by sameer.madaan on 08/08/2018.
 */

public interface ForgotPasswordInteractor {

    public void forgotPassword(Activity activity, ForgotPasswordParam forgotPasswordParam, ForgotPresentor.OnForgotPasswordComplete onForgotPasswordComplete);

}
