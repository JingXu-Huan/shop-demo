package com.jingxu.shopdemo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jingxu.shopdemo.domain.entity.OrderItems;
import com.jingxu.shopdemo.domain.vo.OrderShowVO;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
public interface OrderService extends IService<OrderItems> {
    OrderShowVO queryAllOrders();
}
