package com.example.louis.ilovezappos.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Louis on 2/9/2017.
 */

public class JsonUtil {

    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public static <T extends Object> Type getType(T type) {
        return new TypeToken<T>(){}.getType();
    }

    public static <T> List<T> parseJsonOrJsonArray(JsonElement jsonElement, String keyOfArray, Class<T> cls) {
        List<T> result;
        if(jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if(keyOfArray != null) {
                if(jsonObject.has(keyOfArray)) {
                    JsonElement arrayOrAField = jsonObject.get(keyOfArray); // The value to keyOfArray may be only a field in the jsonElement when the jsonElement is actual a single object
                    if(arrayOrAField.isJsonArray()) {
                        JsonArray array = arrayOrAField.getAsJsonArray();
                        result = new ArrayList<>(array.size());
                        for(JsonElement je: array) {
                            T t = gson.fromJson(je, cls);
                            result.add(t);
                        }
                        return result;
                    }
                }
            }
            result = new ArrayList<>(1);
            T t = gson.fromJson(jsonObject, cls);
            result.add(t);

            return result;

        } else if(jsonElement.isJsonArray()) { // normally, this won't happen if you use this method correctly
            JsonArray array = jsonElement.getAsJsonArray();
            result = new ArrayList<>(array.size());
            for(JsonElement je: array) {
                T t = gson.fromJson(je, cls);
                result.add(t);
            }
            return result;
        }
        return null;
    }
}
