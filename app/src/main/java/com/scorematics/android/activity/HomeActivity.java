package com.scorematics.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.scorematics.android.R;
import com.scorematics.android.base.BaseActionBarActivityClass;
import com.scorematics.android.fragment.AllArticlesFragment;

public class HomeActivity extends BaseActionBarActivityClass {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        replaceFragment(R.id.main_frame_layout_home,new AllArticlesFragment(),AllArticlesFragment.class.getSimpleName(),false,this);
    }

    @Override
    public void onClick(View view) {

    }
}
