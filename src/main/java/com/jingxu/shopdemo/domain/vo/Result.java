package com.jingxu.shopdemo.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String msg;
    private Integer status;

    public static Result ok(String msg, Integer status) {
        return new Result(msg, status);
    }

    public static Result ok() {
        return new Result();
    }

    public static Result ok(String msg) {
        return new Result(msg);
    }

    public static Result fail(String msg , Integer status){
        return new Result(msg, status);
    }

    public static Result fail(String msg){
        return new Result(msg);
    }

    public Result(String msg) {
        this.msg = msg;
    }
}
