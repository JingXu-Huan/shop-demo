package com.jingxu.shopdemo.controller;

import com.jingxu.shopdemo.domain.dto.UserDto;
import com.jingxu.shopdemo.domain.entity.Orders;
import com.jingxu.shopdemo.domain.entity.Products;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public Result adminLogin(@RequestBody UserDto userDto, HttpSession httpSession) {
        return adminService.adminLogin(userDto, httpSession);
    }

    /**
     * 管理员上架商品
     */
    @PutMapping("/add")
    public Result addItems(@RequestBody List<Products> products) {
        return adminService.addItems(products);
    }

    /**
     * 管理员查询所有用户的订单
     */
    @GetMapping("/query")
    public List<Orders> queryAllUsersOrder() {
        return adminService.queryAllUsersOrder();
    }

    /**
     * 管理员发货
     */
    @PostMapping("/delivery")
    public Result delivery(@RequestBody List<Long> ids) {
        return adminService.delivery(ids);
    }
}
