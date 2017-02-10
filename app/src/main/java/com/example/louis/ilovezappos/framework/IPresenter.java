package com.example.louis.ilovezappos.framework;

import android.content.Context;

/**
 * Created by Louis on 2/2/2017.
 */
public interface IPresenter<V extends IView, M extends IModel> {
    void onAdded();
    void setIView(V view);
    void setIModel(M model);
    Context getContext();
}
