package com.scorematics.android.presentor;

import com.scorematics.android.model.AllEventsResponse;

import java.util.List;

public interface AllArticlesPresentor {
   public void getAllArticles(String section,String period);

   public interface OnComplete{
      public void onSuccess(List<AllEventsResponse.Result> allEvents);
      public void onFail(String status);
   }

}
