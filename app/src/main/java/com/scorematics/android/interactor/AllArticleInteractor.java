package com.scorematics.android.interactor;

import android.app.Activity;

import com.scorematics.android.presentor.AllArticlesPresentor;

public interface AllArticleInteractor {
    void getArticles(Activity activity, String period, String apiKey, AllArticlesPresentor.OnComplete onComplete);
}
