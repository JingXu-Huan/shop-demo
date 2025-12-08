package com.jingxu.shopdemo.controller;


import com.jingxu.shopdemo.domain.dto.ProductDto;
import com.jingxu.shopdemo.domain.dto.UserDto;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    public Result signUp(@RequestBody UserDto userDto) {
        return userService.singUp(userDto);
    }

    @GetMapping
    public Result singIn(@RequestBody UserDto userDto){
        return userService.singIn(userDto);
    }

}
