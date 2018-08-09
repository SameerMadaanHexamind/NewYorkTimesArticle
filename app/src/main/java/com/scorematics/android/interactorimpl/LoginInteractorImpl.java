package com.scorematics.android.interactorimpl;

import android.app.Activity;


import com.scorematics.android.R;
import com.scorematics.android.api.ApiEndpointInterface;
import com.scorematics.android.api.RetroUtils;
import com.scorematics.android.interactor.LoginInteractor;
import com.scorematics.android.model.LoginParam;
import com.scorematics.android.model.LoginResponse;
import com.scorematics.android.presentor.LoginPresentor;
import com.scorematics.android.utils.Constants;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sameer.madaan on 08/08/2018.
 */

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(final Activity activity, final LoginParam loginParam, final LoginPresentor.OnLoginResponse onLoginResponse) {


        RetroUtils.getHostAdapter(activity, ApiEndpointInterface.URL).create(ApiEndpointInterface.class).login(loginParam, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //  onLoginResponse.onFail("The UserName you entered is incorrect.");
                if (retrofitError.getResponse() != null) {
                  //  ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                  //  onLoginResponse.onFail(body.getError_description());
                } else
                    onLoginResponse.onFail(activity.getString(R.string.server_error));
            }


        });
    }


}
