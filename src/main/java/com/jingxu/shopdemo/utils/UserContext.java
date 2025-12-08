package com.jingxu.shopdemo.utils;

import com.jingxu.shopdemo.domain.entity.Users;

public class UserContext {
    // 保存用户信息的 ThreadLocal
    private static final ThreadLocal<Users> currentUser = new ThreadLocal<>();
    // 设置用户信息
    public static void set(Users user) {
        currentUser.set(user);
    }

    // 获取用户信息
    public static Users get() {
        return currentUser.get();
    }

    // 清理线程变量，防止内存泄漏
    public static void remove() {
        currentUser.remove();
    }
}
