package com.lzp.ganhuo.api;

import android.util.Log;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;

public class ApiCallException extends Exception {
    public enum Type {
        TYPE_NETWORK,
        TYPE_HTTP,
        TYPE_API,
        TYPE_UNEXPECTED
    }

    private Type mType = Type.TYPE_UNEXPECTED;
    private int mCode;
    private String mErrorBodyJsonString;

    private ApiCallException(Response response) {
        mCode = response.code();
        try {
            mErrorBodyJsonString = response.errorBody().string();
            mType = Type.TYPE_API;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ApiCallException(Type type, Throwable cause) {
        super(cause.getMessage(), cause);
        this.mType = type;
    }

    public static ApiCallException from(Throwable throwable) {
        Log.d("Test", "from() called with: throwable = [" + throwable + "]");

        if (throwable instanceof ApiCallException) {
            return (ApiCallException) throwable;
        }
        if (throwable instanceof HttpException) {
            //return new ApiCallException(Type.TYPE_HTTP, throwable);

            return new ApiCallException(((HttpException) throwable).response());

        }
        // A network error happened
        if (throwable instanceof IOException) {
            return new ApiCallException(Type.TYPE_NETWORK, throwable);
        }

        // We don't know what happened. We need to simply convert to an unknown error
        return new ApiCallException(Type.TYPE_UNEXPECTED, throwable);
    }

    public Type getType() {
        return mType;
    }

    public int getCode() {
        return mCode;
    }

    public String getErrorBodyJsonString() {
        return mErrorBodyJsonString;
    }
}
