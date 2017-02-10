package com.example.louis.ilovezappos.framework;

import com.example.louis.ilovezappos.Util.NetworkUtil;

/**
 * Created by Louis on 2/10/2017.
 */

public class ModelResponse<T> {
    private static final int RESULT_CODE_NETWORK_FAILURE = 444;
    private static final int RESULT_CODE_SUCCESS = 200;

    private int code;
    private T resultData;

    private String errorMsg;

    private ModelResponse() {

    }

    private ModelResponse(int code, T resultData) {
        this.code = code;
        this.resultData = resultData;
    }

    public static <T> ModelResponse<T> networkError() {
        return new ModelResponse<T>(RESULT_CODE_NETWORK_FAILURE, null);
    }

    public static <T> ModelResponse<T> error(retrofit2.Response response) {
        ModelResponse<T> result = new ModelResponse<>();
        result.code = response.code();
        result.errorMsg = NetworkUtil.getErrorMsg(response);

        return result;
    }

    public static <T> ModelResponse<T> error(ModelResponse modelResponse) {
        ModelResponse<T> result = new ModelResponse<>();
        result.code = modelResponse.code;
        result.errorMsg = modelResponse.errorMsg;

        return result;
    }

    public static <T> ModelResponse<T> copy(ModelResponse modelResponse, T data) {
        ModelResponse<T> result = new ModelResponse<>();
        result.code = modelResponse.code;
        result.errorMsg = modelResponse.errorMsg;
        result.resultData = data;
        return result;
    }

    public static <T> ModelResponse<T> success(T data) {
        return new ModelResponse<T>(RESULT_CODE_SUCCESS, data);
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return resultData;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
