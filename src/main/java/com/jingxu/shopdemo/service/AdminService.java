package com.jingxu.shopdemo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jingxu.shopdemo.domain.dto.UserDto;
import com.jingxu.shopdemo.domain.entity.Admins;
import com.jingxu.shopdemo.domain.entity.Orders;
import com.jingxu.shopdemo.domain.entity.Products;
import com.jingxu.shopdemo.domain.vo.Result;
import jakarta.servlet.http.HttpSession;

import java.util.List;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
public interface AdminService extends IService<Admins> {
    Result adminLogin(UserDto userDto, HttpSession httpSession);

    Result addItems(List<Products> itemsDto);

    List<Orders> queryAllUsersOrder();

    Result delivery(List<Long> ids);
}
