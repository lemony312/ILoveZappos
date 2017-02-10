package com.example.louis.ilovezappos.mvpmodel;

import android.support.annotation.NonNull;

import com.example.louis.ilovezappos.framework.IModel;
import com.example.louis.ilovezappos.rest.model.ZapposProduct;

import java.util.List;

/**
 * Created by Louis on 2/9/2017.
 */

public interface IProductModel extends IModel{
    @NonNull
    List<ZapposProduct> getAllProductsFromServer(String term);

    @NonNull
    ZapposProduct getFirstZapposProduct(String term);
}
