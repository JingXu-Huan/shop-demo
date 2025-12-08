package com.jingxu.shopdemo.utils;


import java.util.UUID;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
public class Utils {
    public static String updateSql = "UPDATE user_signin SET status = true WHERE userId = ?";
    public static String querySql = "select count(*) from user_signin where userId = ?";
    public static String insertSql = "insert into user_signin values (?,?)";
}
