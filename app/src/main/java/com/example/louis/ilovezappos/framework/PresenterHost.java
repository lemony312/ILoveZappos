package com.example.louis.ilovezappos.framework;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Louis on 2/2/2017.
 */

public abstract class PresenterHost {
    private static final String TAG = PresenterHost.class.getSimpleName();
    private static final String PRESENTER_TAG = "presenter_";

    protected abstract FragmentManager getFragmentManager();

    protected <V extends IView, M extends IModel> void addPresenter(IPresenter<V , M> presenter, V view, M model) {
        addPresenter(presenter, view, model, 0);
    }
    protected <V extends IView, M extends IModel> void addPresenter(IPresenter<V , M> presenter, V view, M model, int index) {
        FragmentManager manager = getFragmentManager();
        String tag = PRESENTER_TAG + index;
        FragmentTransaction transaction = manager.beginTransaction();
        PresenterFragmentImpl presenterImpl = (PresenterFragmentImpl) manager.findFragmentByTag(tag);
        if(presenterImpl != null) {
            transaction.remove(presenterImpl);
        }

        presenter.setIModel(model);
        presenter.setIView(view);

        transaction.add((PresenterFragmentImpl) presenter, tag).commit();

        presenter.onAdded();
    }

    // Don't use this for now
    protected <P extends IPresenter<V, M>, V extends IView, M extends IModel> P getPresenter(Class<? extends PresenterFragmentImpl<V, M>> presenterCls, V view, M model, int index) {
        FragmentManager manager = getFragmentManager();
        String tag = PRESENTER_TAG + index;

        P presenterImpl = (P) manager.findFragmentByTag(tag);

        if(presenterImpl == null) {
            try {
                presenterImpl = (P) presenterCls.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            presenterImpl.setIModel(model);
            presenterImpl.setIView(view);

            manager.beginTransaction().add((PresenterFragmentImpl) presenterImpl, tag).commit();
            return presenterImpl;
        }

        return presenterImpl;
    }
}
