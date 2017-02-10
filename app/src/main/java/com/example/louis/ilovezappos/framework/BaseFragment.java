package com.example.louis.ilovezappos.framework;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.louis.ilovezappos.ui.IMainActivity;

/**
 * Created by Louis on 2/6/2017.
 */

public abstract class BaseFragment extends Fragment {

    private static final String PRESENTER_TAG = "presenter_";
    private static final String PROGRESS_DIALOG_TAG = "progress_dialog";

    private PresenterHost presenterHost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterHost = new FragmentPresenterHost(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!(getActivity() instanceof IMainActivity)) {
            throw new IllegalStateException("Only fragments acting as tabs in MainActivity should extend BaseFragment");
        }
        IMainActivity mainActivity = (IMainActivity) getActivity();
        mainActivity.setToolbarHidden(hideToolbar(), !getStatusBarTransparent());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * @return If you want to hide toolbar in MainActivity, override this method and return true
     */
    protected boolean hideToolbar() {
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected boolean getStatusBarTransparent() {
        return false;
    }

    protected <V extends IView, M extends IModel> void addPresenter(IPresenter<V , M> presenter, V view, M model) {
        presenterHost.addPresenter(presenter, view, model, 0);
    }

    protected <V extends IView, M extends IModel> void addPresenter(IPresenter<V , M> presenter, V view, M model, int index) {
        presenterHost.addPresenter(presenter, view, model, index);
    }
}
