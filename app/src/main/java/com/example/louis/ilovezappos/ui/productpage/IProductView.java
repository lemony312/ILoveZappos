package com.example.louis.ilovezappos.ui.productpage;

import com.example.louis.ilovezappos.framework.IView;
import com.example.louis.ilovezappos.rest.model.ZapposProduct;

/**
 * Created by Louis on 2/9/2017.
 */

public interface IProductView extends IView{
    void onProductLoaded(ZapposProduct zapposProduct, boolean noResults);
    void onProductLoadError(String errMsg);
}
