package com.example.louis.ilovezappos.ui.productpage.presenter;

import com.example.louis.ilovezappos.framework.ModelResponse;
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

        Subscription sub = Observable.create(new Observable.OnSubscribe<ModelResponse<ZapposProduct>>() {
            @Override
            public void call(Subscriber<? super ModelResponse<ZapposProduct>> subscriber) {
                ModelResponse<ZapposProduct> response = mIModel.getFirstZapposProduct(term);
                subscriber.onNext(response);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ModelResponse<ZapposProduct>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) { // shouldnt be called
                        e.printStackTrace();
                        mIView.onProductLoadError("Error, Please try again later");
                    }

                    @Override
                    public void onNext(ModelResponse<ZapposProduct> zapposProduct) {
                        if(zapposProduct.isSuccessful()){
                            if(zapposProduct.getData() != null){
                                mIView.onProductLoaded(zapposProduct.getData(), false);
                            }
                            else{
                                mIView.onProductLoaded(null, true);
                            }
                        }
                        else{
                           mIView.onProductLoadError(zapposProduct.getErrorMsg());
                       }
                    }
                });
        addSubscription(sub);
    }
}
