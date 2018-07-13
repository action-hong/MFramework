package com.jornco.mframework.libs.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kkopite on 2018/7/13.
 */

public class BaseEntry<T> {

//    public abstract String code();
//    public abstract String message();
//    public abstract T data();
//
//    @AutoValue.Builder
//    public interface Builder {
//        Builder code(String __);
//        Builder message(String __);
//        Builder data(T __);
//
//    }

    /**
     * 服务器返回code
     */
    @SerializedName("code")
    private int code;

    /**
     * 服务返回message
     */
    @SerializedName("message")
    private String message;

    /**
     * 具体需要的数据类型
     */
    @SerializedName("data")
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BaseEntry{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
