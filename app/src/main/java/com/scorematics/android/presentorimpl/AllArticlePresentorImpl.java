package com.scorematics.android.presentorimpl;

import android.app.Activity;

import com.scorematics.android.R;
import com.scorematics.android.adapter.AllEventAdapter;
import com.scorematics.android.interactor.AllArticleInteractor;
import com.scorematics.android.interactorimpl.AllArticleInteractorImpl;
import com.scorematics.android.model.AllEventsResponse;
import com.scorematics.android.presentor.AllArticlesPresentor;
import com.scorematics.android.utils.CommonUtils;
import com.scorematics.android.view.AllArtcileView;

import java.util.List;

public class AllArticlePresentorImpl implements AllArticlesPresentor, AllArticlesPresentor.OnComplete {
    Activity activity;
    AllArtcileView allArtcileView;
    AllArticleInteractor allArticleInteractor;

    public AllArticlePresentorImpl(Activity activity, AllArtcileView allArtcileView){
      this.activity =  activity;
      this.allArtcileView = allArtcileView;
      allArticleInteractor = new AllArticleInteractorImpl();
    }

    @Override
    public void getAllArticles(String section, String period) {
        if(CommonUtils.isOnline(activity)){
            allArticleInteractor.getArticles(activity,period,"",this);
        }else {
            allArtcileView.showMessage(activity.getString(R.string.no_internet));
        }


    }

    @Override
    public void onSuccess(List<AllEventsResponse.Result> allEvents) {
        AllEventAdapter allEventAdapter = new AllEventAdapter(activity,allEvents,allArtcileView);
        allArtcileView.setAdapter(allEventAdapter);

    }

    @Override
    public void onFail(String status) {
        allArtcileView.showMessage(status);
    }
}
