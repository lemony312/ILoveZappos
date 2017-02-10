package com.example.louis.ilovezappos.mvpmodel;

import android.support.annotation.NonNull;

import com.example.louis.ilovezappos.Util.JsonUtil;
import com.example.louis.ilovezappos.Util.L;
import com.example.louis.ilovezappos.Util.NetworkUtil;
import com.example.louis.ilovezappos.framework.ModelResponse;
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
    public ModelResponse<List<ZapposProduct>> getAllProductsFromServer(String term) {
        ZapposService service = NetworkUtil.getZapposService();

        Response<JsonElement> response = null;
        try {
            response = service.getProducts(term).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response == null){
            return ModelResponse.networkError();
        }

        if (!response.isSuccessful()) {
            return ModelResponse.error(response);
        }
        List<ZapposProduct> data = JsonUtil.parseJsonOrJsonArray(response.body(), "results", ZapposProduct.class);

        return ModelResponse.success(data);
    }

    @Override
    public ModelResponse<ZapposProduct> getFirstZapposProduct(String term) {
        ModelResponse<List<ZapposProduct>> products = getAllProductsFromServer(term);
        if(products.isSuccessful()) {
            if (products.getData() != null && products.getData().size() > 0) {
                return ModelResponse.copy(products, products.getData().get(0));
            }
            else{
                return ModelResponse.copy(products, null);
            }
        }
        else{
            return ModelResponse.copy(products, null);
        }
    }
}
