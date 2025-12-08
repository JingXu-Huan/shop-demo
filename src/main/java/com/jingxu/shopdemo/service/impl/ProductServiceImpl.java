package com.jingxu.shopdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingxu.shopdemo.domain.dto.ProductDto;
import com.jingxu.shopdemo.domain.dto.ProductListDto;
import com.jingxu.shopdemo.domain.entity.OrderItems;
import com.jingxu.shopdemo.domain.entity.Orders;
import com.jingxu.shopdemo.domain.entity.Products;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.mapper.OrderItemsMapper;
import com.jingxu.shopdemo.mapper.OrdersMapper;
import com.jingxu.shopdemo.mapper.ProductsMapper;
import com.jingxu.shopdemo.service.ProductService;
import com.jingxu.shopdemo.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductsMapper, Products> implements ProductService {

    public static final ExecutorService pool = Executors.newFixedThreadPool(5);
    private final OrdersMapper ordersMapper;
    private final OrderItemsMapper orderItemsMapper;

    /**
     * 此方法针对单商品直接下单
     *
     */
    @Override
    @Transactional
    public Result orderItems(ProductDto productDto) {
        if (productDto == null) {
            return Result.fail("商品信息为空");
        }
        Integer productId = productDto.getProductId();
        int num1 = productDto.getNums();
        BigDecimal num = BigDecimal.valueOf(productDto.getNums());
        Integer stock = this.lambdaQuery()
                .eq(Products::getProductId, productId)
                .select(Products::getProductId, Products::getStock)
                .one().getStock();
        BigDecimal price = this.lambdaQuery()
                .eq(Products::getProductId, productId)
                .select(Products::getPrice)
                .one().getPrice();
        BigDecimal total = num.multiply(price);
        Integer userId = UserContext.get();
        System.out.println(userId);
        if (stock >= num1) {
            try {
                this.lambdaUpdate().eq(Products::getProductId, productId)
                        .setSql("stock = stock - " + num).update();
                //异步写入订单表
                Async(userId, total, productId, num1, price);
            } catch (Exception e) {
                log.error("扣减库存失败");
                return Result.fail("下单失败，请重试");
            }
        } else {
            return Result.fail(productId + "库存不足");
        }
        return Result.ok("下单成功");
    }

    private void Async(Integer userId, BigDecimal total, Integer productId, int num1, BigDecimal price) {
        pool.submit(() -> {
            //写入订单表
            log.debug("用户id {}", UserContext.get());
            UserContext.set(userId);
            log.debug("用户id {}", UserContext.get());
            Orders orders = new Orders().setUserId(UserContext.get())
                    .setStatus("未支付")
                    .setTotalAmount(total)
                    .setCreatedAt(LocalDateTime.now());
            ordersMapper.insert(orders);
            //返回自增主键
            Integer orderId = orders.getOrderId();
            //写入订单明细表
            OrderItems items = new OrderItems()
                    .setOrderId(orderId)
                    .setProductId(productId)
                    .setQuantity(num1)
                    .setPrice(price);
            orderItemsMapper.insert(items);
        });
    }

    @Override
    @Transactional
    public Result orderItemsByList(ProductListDto productListDto) {
        Integer userId = UserContext.get();
        List<ProductDto> items = productListDto.getItems();

        BigDecimal total = BigDecimal.ZERO;
        for (ProductDto item : items) {
            BigDecimal price = this.lambdaQuery()
                    .eq(Products::getProductId, item.getProductId())
                    .one()
                    .getPrice();
            BigDecimal nums1 = BigDecimal.valueOf(item.getNums());
            total = total.add(price.multiply(nums1));
        }

        BigDecimal finalTotal = total;
        items.forEach(item -> {
            Integer productId = item.getProductId();
            int nums = item.getNums();
            Integer stock = this.lambdaQuery().eq(Products::getProductId, productId).one().getStock();
            if (nums >= stock) {
                log.warn("{}库存不足", productId);
                return;

            } else {
                //计算金额
                BigDecimal price = this.lambdaQuery()
                        .eq(Products::getProductId, productId).one().getPrice();
                BigDecimal money = BigDecimal.valueOf(nums).multiply(price);
                //扣减数据库库存
                this.lambdaUpdate().eq(Products::getProductId, productId)
                        .setSql("stock = stock - " + nums).update();
                Async(userId, finalTotal, productId, nums, money);
            }

        });
        return Result.ok("下单成功");
    }
}
