package com.scorematics.android.activity;


import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.scorematics.android.R;
import com.scorematics.android.base.BaseActionBarActivityClass;
import com.scorematics.android.fragment.LoginFragment;
import com.scorematics.android.utils.ActivitySplitAnimationUtil;
import com.scorematics.android.utils.PreferenceHandler;
import com.scorematics.android.utils.Utilities;

public class LoginActivity extends BaseActionBarActivityClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
        replaceFragment(R.id.activity_login_framelayout, new LoginFragment(), LoginFragment.class.getName(), false, this);



    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.activity_login_framelayout);

        if (currentFragment != null && (currentFragment instanceof LoginFragment))
            super.onBackPressed();
        else
            onBackPress(R.id.activity_login_framelayout);

    }
}
