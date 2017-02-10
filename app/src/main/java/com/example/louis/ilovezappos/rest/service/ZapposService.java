package com.example.louis.ilovezappos.rest.service;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Louis on 2/8/2017.
 */

public interface ZapposService {
    //sub urls
    String SEARCH = "Search";

    @GET(SEARCH)
    Call<JsonElement> getProducts(@Query("term") String term);
}
