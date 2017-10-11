package com.decom.exception;

/**
 * Created by Administrator on 2017/9/19.
 */
public class RepeatAppointException extends RuntimeException{
    public RepeatAppointException(String message) {
        super(message);
    }

    public RepeatAppointException(String message, Throwable cause) {
        super(message, cause);
    }
}
