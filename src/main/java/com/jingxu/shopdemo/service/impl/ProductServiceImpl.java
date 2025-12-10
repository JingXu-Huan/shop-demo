package com.jingxu.shopdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingxu.shopdemo.domain.dto.ProductDto;
import com.jingxu.shopdemo.domain.dto.ProductListDto;
import com.jingxu.shopdemo.domain.entity.CartItems;
import com.jingxu.shopdemo.domain.entity.OrderItems;
import com.jingxu.shopdemo.domain.entity.Orders;
import com.jingxu.shopdemo.domain.entity.Products;
import com.jingxu.shopdemo.domain.vo.ItemVO;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.exception.BusinessException;
import com.jingxu.shopdemo.exception.FailCodeEnums;
import com.jingxu.shopdemo.mapper.CartItemsMapper;
import com.jingxu.shopdemo.mapper.OrderItemsMapper;
import com.jingxu.shopdemo.mapper.OrdersMapper;
import com.jingxu.shopdemo.mapper.ProductsMapper;
import com.jingxu.shopdemo.service.ProductService;
import com.jingxu.shopdemo.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    /*开一个五个线程的线程池*/
    public static final ExecutorService pool = Executors.newFixedThreadPool(5);
    private final CartItemsMapper cartItemsMapper;
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

    /**
     * 此方法会在异步线程中开启任务,完成订单表的写入和订单详情表的写入。
     *
     * @param userId    用户的ID
     * @param total     所有商品的总金额
     * @param productId 当前要处理的订单id
     * @param num1      当前订单下的商品总数
     * @param price     当前订单下的商品总金额
     *
     */
    private void Async(Integer userId, BigDecimal total, Integer productId, int num1, BigDecimal price) {
        pool.submit(() -> {
            //写入订单表
            log.debug("用户id {}", UserContext.get());
            UserContext.set(userId);
            log.debug("用户id {}", UserContext.get());
            Orders orders = new Orders()
                    .setUserId(UserContext.get())
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
        if (items == null || items.isEmpty()) {
            return Result.fail("购物车为空");
        }
        // 计算总金额 & 检查库存
        BigDecimal total = BigDecimal.ZERO;
        for (ProductDto item : items) {
            Products product = this.lambdaQuery()
                    .eq(Products::getProductId, item.getProductId())
                    .one();
            if (item.getNums() > product.getStock()) {
                throw new BusinessException(FailCodeEnums.STOCK_NOT_ENOUGH, "商品数量不足");
            }
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getNums())));
        }
        //生成订单
        Orders orders = new Orders()
                .setUserId(userId)
                .setStatus("未支付")
                .setTotalAmount(total)
                .setCreatedAt(LocalDateTime.now());
        ordersMapper.insert(orders);
        Integer orderId = orders.getOrderId();
        //扣减库存 & 写入订单明细
        for (ProductDto item : items) {
            this.lambdaUpdate()
                    .eq(Products::getProductId, item.getProductId())
                    .setSql("stock = stock - " + item.getNums())
                    .update();
            OrderItems orderItem = new OrderItems()
                    .setOrderId(orderId)
                    .setProductId(item.getProductId())
                    .setQuantity(item.getNums())
                    .setPrice(this.lambdaQuery()
                            .eq(Products::getProductId, item.getProductId())
                            .one().getPrice());
            orderItemsMapper.insert(orderItem);
        }
        // 清除购物车(若有)
        List<Integer> ids = new ArrayList<>();
        pool.submit(() -> {
            items.forEach(it -> {
                ids.add(it.getProductId());
            });
            for (Integer id : ids) {
                LambdaQueryWrapper<CartItems> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CartItems::getProductId, id);
                cartItemsMapper.delete(queryWrapper);
            }
        });
        return Result.ok("下单成功");
    }

    public String findName(Integer productId) {
        return this.lambdaQuery().eq(Products::getProductId, productId).one().getName();
    }

    @Override
    public List<ItemVO> queryAllItem(int pageNum, int pageSize) {
        Page<Products> page = this.page(new Page<>(pageNum, pageSize));

        //总页数
        long total = page.getTotal();
        //当前页数
        long current = page.getCurrent();

        List<Products> records = page.getRecords();
        List<ItemVO> voList = new ArrayList<>();
        records.forEach(record -> {
            ItemVO itemVO = new ItemVO();
            BeanUtils.copyProperties(record, itemVO);
            voList.add(itemVO);
        });

        return voList;
    }
}
