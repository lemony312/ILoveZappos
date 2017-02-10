package com.example.louis.ilovezappos.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Louis on 2/6/2017.
 */

public class FragmentPresenterHost extends PresenterHost {

    private Fragment fragment;

    public FragmentPresenterHost(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected FragmentManager getFragmentManager() {
        return fragment.getChildFragmentManager();
    }
}