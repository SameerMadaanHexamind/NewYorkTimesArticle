package com.scorematics.android.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.scorematics.android.R;
import com.scorematics.android.adapter.AllEventAdapter;
import com.scorematics.android.base.BaseFragment;
import com.scorematics.android.presentor.AllArticlesPresentor;
import com.scorematics.android.presentorimpl.AllArticlePresentorImpl;
import com.scorematics.android.view.AllArtcileView;

public class AllArticlesFragment extends BaseFragment implements AllArtcileView {
    View mAllEventFragmentsView;
    AllArticlesPresentor allArticlesPresentor;
    RecyclerView rvAllArticles;
    TextView txtNoArticle;

    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAllEventFragmentsView = inflater.inflate(R.layout.frag_all_events,null);
        allArticlesPresentor = new AllArticlePresentorImpl(getActivity(),this);
        initView();

        return mAllEventFragmentsView;
    }

    private void initView() {
        allArticlesPresentor.getAllArticles("all-sections","30");
        rvAllArticles = (RecyclerView)mAllEventFragmentsView.findViewById(R.id.rvAllArticles);
        txtNoArticle = (TextView)mAllEventFragmentsView.findViewById(R.id.txtNoArticle);
    }

    @Override
    public void showMessage(String string) {

        txtNoArticle.setVisibility(View.VISIBLE);
        rvAllArticles.setVisibility(View.GONE);
        showToast(string, Toast.LENGTH_LONG);
    }

    @Override
    public void setAdapter(AllEventAdapter allEventAdapter) {
        txtNoArticle.setVisibility(View.GONE);
        rvAllArticles.setVisibility(View.VISIBLE);
        rvAllArticles.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAllArticles.setAdapter(allEventAdapter);
    }
}
