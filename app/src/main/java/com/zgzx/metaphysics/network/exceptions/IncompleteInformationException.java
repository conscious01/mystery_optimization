package com.zgzx.metaphysics.network.exceptions;

/**
 * 用户信息不完整
 */
@Deprecated
public class IncompleteInformationException extends Exception {

    private String token;

    public IncompleteInformationException(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
