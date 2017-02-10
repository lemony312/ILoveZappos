package com.example.louis.ilovezappos.ui.productpage;

import com.example.louis.ilovezappos.framework.IPresenter;
import com.example.louis.ilovezappos.mvpmodel.IProductModel;

/**
 * Created by Louis on 2/9/2017.
 */

public interface IProductPresenter extends IPresenter<IProductView, IProductModel>{
    void loadProducts(String term);
}
