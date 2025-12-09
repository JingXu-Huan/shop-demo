package com.jingxu.shopdemo.exception;


import lombok.Getter;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Getter
public enum FailCodeEnums {
    // 通用错误
    SYSTEM_ERROR(500, "服务器开小差啦"),
    BAD_REQUEST(400, "请求参数不合法"),

    // 用户相关
    USER_NOT_LOGIN(4001, "用户未登录"),

    // 购物车相关
    CART_ITEM_EXISTS(4101, "该商品已经在购物车中了"),
    CART_ITEM_NOT_FOUND(4102, "购物车中未找到该商品"),

    // 商品相关
    PRODUCT_NOT_FOUND(4201, "商品不存在"),
    STOCK_NOT_ENOUGH(4202, "库存不足");

    private final int code;
    private final String msg;

    FailCodeEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
