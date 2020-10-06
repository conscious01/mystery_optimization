package com.zgzx.metaphysics.network.exceptions;

/**
 * 身份过期
 */
public class UnauthorizedException extends Exception {

    public UnauthorizedException() {

    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

}
