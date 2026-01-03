package com.jingxu.shopdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingxu.shopdemo.domain.entity.OrderItems;
import com.jingxu.shopdemo.domain.entity.Orders;
import com.jingxu.shopdemo.domain.entity.Products;
import com.jingxu.shopdemo.domain.vo.ItemVO;
import com.jingxu.shopdemo.domain.vo.OrderShowVO;
import com.jingxu.shopdemo.domain.vo.OrdersVO;
import com.jingxu.shopdemo.mapper.OrderItemsMapper;
import com.jingxu.shopdemo.mapper.OrdersMapper;
import com.jingxu.shopdemo.mapper.ProductsMapper;
import com.jingxu.shopdemo.service.OrderService;
import com.jingxu.shopdemo.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class OrderServiceImpl extends ServiceImpl<OrderItemsMapper, OrderItems> implements OrderService {
    private final OrdersMapper ordersMapper;
    private final ProductsMapper productsMapper;

    @Override
    public OrderShowVO queryAllOrders() {
        Long userId = UserContext.get();
        OrderShowVO orderShowVO = new OrderShowVO();
        if (userId == null) {
            return orderShowVO;
        }
        // 查询该用户所有订单
        LambdaQueryWrapper<Orders> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(Orders::getUserId, userId);
        List<Orders> ordersList = ordersMapper.selectList(orderWrapper);
        if (ordersList.isEmpty()) {
            return orderShowVO;
        }
        // 收集所有订单中的商品 ID，批量查询商品信息
        List<Long> allProductIds = new ArrayList<>();
        List<OrderItems> allOrderItems = new ArrayList<>();
        ordersList.forEach(order -> {
            List<OrderItems> items = this.lambdaQuery()
                    .eq(OrderItems::getOrderId, order.getOrderId())
                    .list();
            allOrderItems.addAll(items);
            items.forEach(item -> allProductIds.add(item.getProductId()));
        });
        if (allOrderItems.isEmpty()) {
            return orderShowVO;
        }
        // 批量查询商品
        List<Products> productsList = allProductIds.isEmpty()
                ? new ArrayList<>()
                : productsMapper.selectList(new LambdaQueryWrapper<Products>()
                        .in(Products::getProductId, allProductIds));
        // 用 Map 快速查找
        Map<Long, Products> productMap = productsList.stream()
                .collect(Collectors.toMap(p -> p.getProductId(), p -> p));

        // 遍历订单，组装 OrdersVO
        List<OrdersVO> voList = new ArrayList<>();
        for (Orders order : ordersList) {
            OrdersVO ordersVO = new OrdersVO();
            ordersVO.setOrderId(order.getOrderId());
            ordersVO.setTotal(order.getTotalAmount());
            ordersVO.setStatus(order.getStatus());
            ordersVO.setOrderedTime(order.getCreatedAt());

            List<ItemVO> itemVOList = new ArrayList<>();
            for (OrderItems item : allOrderItems) {
                if (!item.getOrderId().equals(order.getOrderId())) continue;
                Products product = productMap.get(item.getProductId());
                if (product != null) {
                    itemVOList.add(new ItemVO(
                            product.getProductId(),
                            product.getName(),
                            product.getDescription(),
                            item.getQuantity(),
                            item.getPrice()
                    ));
                }
            }
            ordersVO.setItemsList(itemVOList);
            voList.add(ordersVO);
        }
        orderShowVO.setVoList(voList);
        return orderShowVO;
    }
}
