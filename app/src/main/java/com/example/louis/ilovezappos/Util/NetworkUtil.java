package com.example.louis.ilovezappos.Util;

import android.content.Context;

import com.example.louis.ilovezappos.app.Config;
import com.example.louis.ilovezappos.rest.service.ZapposService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Louis on 2/8/2017.
 */

public class NetworkUtil {

    private static OkHttpClient client = new OkHttpClient();
    private static ZapposService zapposService;

    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;

    private NetworkUtil() {}

    public static ZapposService getZapposService() {
        return getNewZapposService();
    }

    private static ZapposService getNewZapposService() {
        if(zapposService == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("key", Config.ZAPPOS_SEARCH_KEY)
                            .build();

                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            if(Config.DEBUG) {
                enableLogging(httpClientBuilder);
            }
            OkHttpClient client = httpClientBuilder.build();

            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client);
            Retrofit retrofit = retrofitBuilder.build();
            zapposService = retrofit.create(ZapposService.class);
        }

        return zapposService;
    }

    private static void enableLogging(OkHttpClient.Builder httpClientBuilder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(logging);
    }

    public static HashMap<String, Object> wrapMap(Object... content) {
        if(content.length % 2 != 0) {
            throw new IllegalArgumentException("Argument should be paired");
        }
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < content.length; i += 2) {
            map.put((String) content[i], content[i + 1]);
        }
        return map;
    }
    public static String getErrorMsg(retrofit2.Response response) {
        Gson gson = new Gson();
        String msg = response.code() + " " + response.message();
        try {
            String jsonStr = response.errorBody().string();
            JsonElement jsonElement = gson.fromJson(jsonStr, JsonElement.class);
            if(jsonElement != null) {
                if(jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonElement je = jsonObject.get("_debug");
                    if (je != null) {
                        je = je.getAsJsonObject().get("messages");
                        if (je != null) {
                            je = je.getAsJsonObject().get("errors");
                            if (je != null) {
                                JsonArray jsonArray = je.getAsJsonArray();
                                if (jsonArray != null && jsonArray.size() > 0) {
                                    msg += " : " + jsonArray.get(jsonArray.size() - 1).getAsString();
                                }
                            }
                        }
                    }
                } else if (jsonElement.isJsonPrimitive()) {
                    JsonPrimitive jp = jsonElement.getAsJsonPrimitive();
                    msg += jp.getAsInt();
                }

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JsonSyntaxException e1) {

        }

        return msg;
    }

}

