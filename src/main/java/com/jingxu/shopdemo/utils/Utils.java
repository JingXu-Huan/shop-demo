package com.jingxu.shopdemo.utils;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
public class Utils {
    // BIT 类型字段：1 表示 true，0 表示 false
    public static String updateSql = "UPDATE user_signin SET status = 1 WHERE userId = ?";
    public static String querySql = "SELECT COUNT(*) FROM user_signin WHERE userId = ?";
    public static String insertSql = "INSERT INTO user_signin(userId, status) VALUES (?, ?)";
}

