package com.jingxu.shopdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingxu.shopdemo.domain.dto.ProductDto;
import com.jingxu.shopdemo.domain.entity.Products;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.mapper.ProductsMapper;
import com.jingxu.shopdemo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductsMapper, Products> implements ProductService {
    public static final ExecutorService pool = Executors.newFixedThreadPool(5);
    @Override
    @Transactional
    public Result orderItems(ProductDto productDto) {
        if (productDto == null) {
            return Result.fail("商品信息为空");
        }
        Integer productId = productDto.getProductId();
        int num = productDto.getNums();
        Integer stock = this.lambdaQuery()
                .select(Products::getProductId,Products::getStock)
                .one().getStock();
        if (stock >= num) {
            try {
                this.lambdaUpdate().eq(Products::getProductId, productId)
                        .setSql("stock = stock - " + num).update();
                //异步写入订单表
                pool.submit(()->{

                });
            } catch (Exception e) {
                log.error("扣减库存失败");
            }
        } else {
            return Result.fail(productId + "库存不足");
        }
        return Result.ok("下单成功");
    }
}
