package com.example.louis.ilovezappos.mvpmodel;

import android.support.annotation.NonNull;

import com.example.louis.ilovezappos.Util.JsonUtil;
import com.example.louis.ilovezappos.Util.L;
import com.example.louis.ilovezappos.Util.NetworkUtil;
import com.example.louis.ilovezappos.rest.model.ZapposProduct;
import com.example.louis.ilovezappos.rest.service.ZapposService;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Louis on 2/9/2017.
 */

public class ProductModel implements IProductModel{
    @Override
    public List<ZapposProduct> getAllProductsFromServer(String term) {
        ZapposService service = NetworkUtil.getZapposService();

        Response<JsonElement> response = null;
        try {
            response = service.getProducts(term).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response == null){
            return null;
        }

        if (!response.isSuccessful()) {
            return null;
        }
        List<ZapposProduct> data = JsonUtil.parseJsonOrJsonArray(response.body(), "results", ZapposProduct.class);

        return data;
    }

    @Override
    public ZapposProduct getFirstZapposProduct(String term) {
        List<ZapposProduct> products = getAllProductsFromServer(term);
        if(products.size() > 0) {
            return products.get(0);
        }
        return null;
    }
}
