package com.scorematics.android.interactorimpl;

import android.app.Activity;

import com.scorematics.android.R;
import com.scorematics.android.api.ApiEndpointInterface;
import com.scorematics.android.api.RetroUtils;
import com.scorematics.android.interactor.AllArticleInteractor;
import com.scorematics.android.model.AllEventsResponse;
import com.scorematics.android.model.ForgotPasswordResponse;
import com.scorematics.android.presentor.AllArticlesPresentor;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AllArticleInteractorImpl implements AllArticleInteractor {
    @Override
    public void getArticles(final Activity activity, String period, String apiKey, final AllArticlesPresentor.OnComplete onComplete) {
        RetroUtils.getHostAdapter(activity, ApiEndpointInterface.URL).create(ApiEndpointInterface.class).getAllArticles(new Callback<AllEventsResponse>(){
            @Override
            public void success(AllEventsResponse allEventsResponse, Response response) {
                if(allEventsResponse.getResults()!=null && allEventsResponse.getResults().size()>0){
                    onComplete.onSuccess(allEventsResponse.getResults());
                }else{
                    onComplete.onFail(allEventsResponse.getStatus());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onComplete.onFail(activity.getString(R.string.server_error));
            }
        });
    }
}
