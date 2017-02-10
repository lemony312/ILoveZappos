package com.example.louis.ilovezappos.framework;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Louis on 2/2/2017.
 */

public class ActivityPresenterHost extends PresenterHost{

    private FragmentActivity activity;

    public ActivityPresenterHost(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    protected FragmentManager getFragmentManager() {
        return activity.getSupportFragmentManager();
    }
}
