package com.example.louis.ilovezappos.framework;

import android.support.v4.app.Fragment;
import android.content.Context;
import com.example.louis.ilovezappos.Util.L;
import java.util.ArrayList;
import rx.Subscription;

/**
 * Created by Louis on 2/2/2017.
 */
public class PresenterFragmentImpl<V extends IView, M extends IModel> extends Fragment implements IPresenter<V, M> {
    private static final String TAG = PresenterFragmentImpl.class.getSimpleName();
    protected V mIView;
    protected M mIModel;
    protected ArrayList<Subscription> subscriptionList;

    public PresenterFragmentImpl() {

    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(TAG, "onDestroy: " + getActivity() + "   " + getParentFragment() + "  " + getTag());
        if(subscriptionList != null) {
            for(Subscription s: subscriptionList) {
                if(s != null && !s.isUnsubscribed()) {
                    s.unsubscribe();
                }
            }
        }
        subscriptionList = null;
        mIView = null;
        mIModel = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Add subscriptions to a list, so presenter will take charge of canceling the task when exiting
     * @param subscription
     */
    protected void addSubscription(Subscription subscription) {
        if(subscriptionList == null) {
            subscriptionList = new ArrayList<>();
        }
        subscriptionList.add(subscription);
    }

    @Override
    public void setIView(V view) {
        mIView = view;
    }

    @Override
    public void setIModel(M model) {
        mIModel = model;
    }

    @Override
    public Context getContext() {
        return mIView.getContext();
    }
}
