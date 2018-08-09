package com.scorematics.android.api;

import com.scorematics.android.model.AllEventsResponse;
import com.scorematics.android.model.ForgotPasswordParam;
import com.scorematics.android.model.ForgotPasswordResponse;
import com.scorematics.android.model.LoginParam;
import com.scorematics.android.model.LoginResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiEndpointInterface {
    public static final String DEV_URL = "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/";

    public static final String PRODUCTION_URL = "";
    public static final String URL = DEV_URL;

    @POST("/Login")
    void login(@Body LoginParam loginParam, Callback<LoginResponse> callback);

    @POST("/ForgotPassword")
    void forgotPassword(ForgotPasswordParam forgotPasswordParam, Callback<ForgotPasswordResponse> callback);

    @GET("/all-sections/30.json?api-key=4f0345d7a72c4f4789bc65272ca091f1")
    void getAllArticles(Callback<AllEventsResponse> callback);
}




