package com.jingxu.shopdemo.controller;

import com.jingxu.shopdemo.domain.vo.OrderShowVO;
import com.jingxu.shopdemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@RestController
@RequestMapping("/ordered")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 用户查询自己所有订单
     */
    @GetMapping("/queryAll")
    public OrderShowVO queryAllOrders() {
        return orderService.queryAllOrders();
    }
}
