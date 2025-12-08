package com.jingxu.shopdemo.utils;

public class UserContext {
    // 保存用户信息的 ThreadLocal
    private static final ThreadLocal<Integer> currentUser = new ThreadLocal<>();
    // 设置用户信息
    public static void set(Integer userId) {
        currentUser.set(userId);
    }

    // 获取用户信息
    public static Integer get() {
        return currentUser.get();
    }

    // 清理线程变量，防止内存泄漏
    public static void remove() {
        currentUser.remove();
    }
}
