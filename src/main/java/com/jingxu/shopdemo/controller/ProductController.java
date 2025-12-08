package com.jingxu.shopdemo.controller;

import com.jingxu.shopdemo.domain.dto.ProductDto;
import com.jingxu.shopdemo.domain.dto.ProductListDto;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**对于单商品直接下单时，调用此接口*/
    @PostMapping("/one")
    public Result orderItems(@RequestBody ProductDto productDto) {
        return productService.orderItems(productDto);
    }
    /**对于多商品直接下单时，调用此接口*/
    @PostMapping("/list")
    public Result orderItemsByList(@RequestBody ProductListDto productListDto){
        return productService.orderItemsByList(productListDto);
    }
}
