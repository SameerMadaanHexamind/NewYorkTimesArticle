package com.scorematics.android.interactor;

import android.app.Activity;


import com.scorematics.android.model.LoginParam;
import com.scorematics.android.presentor.LoginPresentor;

/**
 * Created by sameer.madaan on 08/08/2018.
 */

public interface LoginInteractor {
    public void login(Activity activity, LoginParam loginParam, LoginPresentor.OnLoginResponse onLoginResponse);


}
