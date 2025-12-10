package com.jingxu.shopdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingxu.shopdemo.domain.dto.UserDto;
import com.jingxu.shopdemo.domain.entity.Admins;
import com.jingxu.shopdemo.domain.entity.Orders;
import com.jingxu.shopdemo.domain.entity.Products;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.mapper.AdminsMapper;
import com.jingxu.shopdemo.mapper.OrdersMapper;
import com.jingxu.shopdemo.service.AdminService;
import com.jingxu.shopdemo.service.ProductService;
import com.jingxu.shopdemo.utils.UserContext;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements AdminService {
    private final ProductService productService;
    private final OrdersMapper ordersMapper;

    @Override
    public Result adminLogin(UserDto userDto, HttpSession httpSession) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        Admins admin = this.lambdaQuery().eq(Admins::getUsername, username).one();
        if (admin != null && admin.getPassword().equals(password)) {
            httpSession.setAttribute("userId", admin.getAdminId());
            return Result.ok(username + "登录成功");
        } else return Result.fail(username + "登录失败");
    }

    @Override
    public Result addItems(List<Products> products) {
        if (UserContext.get() == null) {
            return Result.fail("请先登录");
        }
        //批量属性赋值
        if (products != null) {
            products.forEach(item -> item.setCreatedAt(LocalDateTime.now()));
        }
        productService.saveBatch(products);
        return Result.ok("商品新增成功");
    }

    @Override
    public List<Orders> queryAllUsersOrder() {
        List<Orders> orders = ordersMapper.selectList(new LambdaQueryWrapper<>());
        Map<Long, List<Orders>> collect = orders.stream().collect(Collectors.groupingBy(Orders::getUserId));
        return collect.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Result delivery(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.fail("商品列表为空");
        }
        ordersMapper.deliveryAll(ids);
        return Result.ok("发货成功");
    }
}
