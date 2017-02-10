package com.example.louis.ilovezappos.bindingobject;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.louis.ilovezappos.Util.L;
import com.example.louis.ilovezappos.rest.model.ZapposProduct;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Louis on 2/9/2017.
 */

public class Product {

    public final String productName;
    public final String productBrandName;
    public ObservableField<Drawable> img;
    private BindableFieldTarget bindableFieldTarget;
    public final String origionalPrice;
    public final String price;
    public final String percentOff;
    public final int showDiscount;
    public final int showOrigPrice;


    public static final String A2URL = "http://a2.zassets.com";
    public static final String LargeImageURL = "-p-MULTIVIEW.jpg";
    public static final String smallImageURL = "-t-THUMBNAIL.jpg";


    public Product(ZapposProduct product, Context context) {
        this.productName = product.getProductName();
        this.productBrandName = product.getBrandName();
        this.price = "Best Price: " + product.getPrice();
        this.percentOff = "Discount: " + product.getPercentOff();

        this.showDiscount = getDiscountVisibility(product);
        this.showOrigPrice = flipVisibitily(this.showDiscount);
        if(this.showDiscount == View.VISIBLE){
            this.origionalPrice = "Original Price: " + product.getOriginalPrice();
        }
        else{
            this.origionalPrice = "Price: " + product.getOriginalPrice();
        }

        img = new ObservableField<>();
        bindableFieldTarget = new BindableFieldTarget(img, context.getResources());
        L.tmp(getA2URL(product.getThumbnailImageUrl()));
        Picasso.with(context).load(getA2URL(product.getThumbnailImageUrl()))
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(bindableFieldTarget);
    }

    public String getA2URL(String url){
        int start = url.indexOf("/images/");
        int end = url.indexOf(smallImageURL);
        if(start ==-1 || end == -1){
            return url;
        }
        return A2URL + url.substring(start, end) + LargeImageURL;
    }

    public int getDiscountVisibility(ZapposProduct product){
        Double origP = Double.parseDouble(product.getOriginalPrice().substring(1));
        Double p = Double.parseDouble(product.getPrice().substring(1));

        return (origP < p) ? View.VISIBLE : View.GONE;
    }

    public int flipVisibitily(int visibility){
        return (visibility == View.VISIBLE) ? View.GONE : View.VISIBLE;
    }

    public class BindableFieldTarget implements Target {

        private ObservableField<Drawable> observableField;
        private Resources resources;

        public BindableFieldTarget(ObservableField<Drawable> observableField, Resources resources) {
            this.observableField = observableField;
            this.resources = resources;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            observableField.set(new BitmapDrawable(resources, bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            observableField.set(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            observableField.set(placeHolderDrawable);
        }
    }
}
