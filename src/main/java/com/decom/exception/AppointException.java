package com.decom.exception;

/**
 * Created by Administrator on 2017/9/19.
 */
public class AppointException extends RuntimeException {
    public AppointException(String message) {
        super(message);
    }

    public AppointException(String message, Throwable cause) {
        super(message, cause);
    }
}
