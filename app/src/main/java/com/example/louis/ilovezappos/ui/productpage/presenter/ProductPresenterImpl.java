package com.example.louis.ilovezappos.ui.productpage.presenter;

import com.example.louis.ilovezappos.Util.L;
import com.example.louis.ilovezappos.framework.PresenterFragmentImpl;
import com.example.louis.ilovezappos.mvpmodel.IProductModel;
import com.example.louis.ilovezappos.rest.model.ZapposProduct;
import com.example.louis.ilovezappos.ui.productpage.IProductPresenter;
import com.example.louis.ilovezappos.ui.productpage.IProductView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2/9/2017.
 */

public class ProductPresenterImpl extends PresenterFragmentImpl<IProductView, IProductModel> implements IProductPresenter {
    @Override
    public void loadProducts(final String term) {

        Subscription sub = Observable.create(new Observable.OnSubscribe<ZapposProduct>() {
            @Override
            public void call(Subscriber<? super ZapposProduct> subscriber) {
                ZapposProduct response = mIModel.getFirstZapposProduct(term);
                subscriber.onNext(response);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZapposProduct>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mIView.onProductLoaded(null);
                    }

                    @Override
                    public void onNext(ZapposProduct zapposProduct) {
                       if(zapposProduct != null){
                           L.tmp(zapposProduct.getProductName());
                           mIView.onProductLoaded(zapposProduct);
                       }
                        else{
                           L.tmp("failed");
                           mIView.onProductLoaded(null);
                       }
                    }
                });
        addSubscription(sub);

    }
}
