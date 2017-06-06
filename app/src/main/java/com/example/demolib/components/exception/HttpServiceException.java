package com.example.demolib.components.exception;

/**
 * Created by john on 2017/6/6.
 */

public class HttpServiceException extends BaseException {

    private int mCode;


    private String mMessage;

    public HttpServiceException(int code, String message) {
        super(message);
        this.mCode = code;
        this.mMessage = message;
    }


    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    @Override
    public String toString() {
        return "HttpServiceException{" +
                "mCode=" + mCode +
                ", mMessage='" + mMessage + '\'' +
                '}';
    }
}
