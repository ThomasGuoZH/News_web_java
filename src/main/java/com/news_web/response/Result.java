package com.news_web.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;
    private String msg;
    private  Object data;

    public Result(int i, String msg, Object data) {
        this.code=i;
        this.msg=msg;
        this.data=data;
    }

    public static Result success(String msg, Object data){
        return new Result(200,msg,data);
    }


    public static Result fail(String msg){
        return new Result(400,msg,null);
    }

}
