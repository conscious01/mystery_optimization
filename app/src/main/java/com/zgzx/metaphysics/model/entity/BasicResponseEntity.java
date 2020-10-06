package com.zgzx.metaphysics.model.entity;

/**
 * Author: LJD
 * Date: 2019/9/5
 * Desc: 基础响应模型
 */
public class BasicResponseEntity<T> {

    private T data;
    private int code;
    private String msg;

    public BasicResponseEntity() {
    }

    public BasicResponseEntity(T data) {
        this.code = 0;
        this.data = data;
    }

    public BasicResponseEntity(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public boolean isSucceed() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BasicResponseEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
