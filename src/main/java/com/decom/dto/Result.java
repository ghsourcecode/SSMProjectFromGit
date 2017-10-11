package com.decom.dto;

/**
 * Created by Administrator on 2017/9/19.
 */
public class Result<T> {
    private boolean sucess;
    private T data;
    private String error;

    public Result(){

    }

    public Result(boolean success, T data){
        this.sucess = success;
        this.data = data;
    }

    public Result(boolean sucess, String error){
        this.sucess = sucess;
        this.error = error;
    }

}
