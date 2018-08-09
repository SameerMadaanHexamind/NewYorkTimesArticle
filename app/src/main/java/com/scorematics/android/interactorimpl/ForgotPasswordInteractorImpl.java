package com.scorematics.android.interactorimpl;

import android.app.Activity;


import com.scorematics.android.R;
import com.scorematics.android.api.ApiEndpointInterface;
import com.scorematics.android.api.RetroUtils;
import com.scorematics.android.interactor.ForgotPasswordInteractor;
import com.scorematics.android.model.ForgotPasswordParam;
import com.scorematics.android.model.ForgotPasswordResponse;
import com.scorematics.android.presentor.ForgotPresentor;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sameer.madaan on 08/08/2018.
 */

public class ForgotPasswordInteractorImpl implements ForgotPasswordInteractor {
    @Override
    public void forgotPassword(final Activity activity, final ForgotPasswordParam forgotPasswordParam, final ForgotPresentor.OnForgotPasswordComplete onForgotPasswordComplete) {
        RetroUtils.getHostAdapter(activity, ApiEndpointInterface.URL).create(ApiEndpointInterface.class).forgotPassword(forgotPasswordParam, new Callback<ForgotPasswordResponse>() {
            @Override
            public void success(ForgotPasswordResponse forgotPasswordResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onForgotPasswordComplete.onFail(activity.getString(R.string.server_error));
            }
        });

          }
}
