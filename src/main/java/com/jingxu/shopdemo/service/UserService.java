package com.jingxu.shopdemo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jingxu.shopdemo.domain.dto.UserDto;
import com.jingxu.shopdemo.domain.entity.Users;
import com.jingxu.shopdemo.domain.vo.Result;
import jakarta.servlet.http.HttpSession;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
public interface UserService extends IService<Users> {
    Result singUp(UserDto userDto);

    Result logIn(UserDto userDto, HttpSession httpSession);
}
