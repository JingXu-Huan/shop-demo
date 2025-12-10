package com.jingxu.shopdemo.utils;

public class UserContext {
    // 保存用户信息的 ThreadLocal
    private static final ThreadLocal<Long> currentUser = new ThreadLocal<>();
    // 设置用户信息
    public static void set(Long userId) {
        currentUser.set(userId);
    }

    // 获取用户信息
    public static Long get() {
        return currentUser.get();
    }

    // 清理线程变量，防止内存泄漏
    public static void remove() {
        currentUser.remove();
    }
}
